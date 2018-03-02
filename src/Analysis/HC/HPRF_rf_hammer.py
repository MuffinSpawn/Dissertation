# -*- coding: utf-8 -*-
"""
Created on Sun Jan 25 20:32:38 2015

@author: plane
"""
import numpy as np
from scipy import fftpack
import scipy.signal
import matplotlib.pyplot as plt  # Matplotlib's pyplot: MATLAB-like syntax
from bisect import bisect_left, bisect_right
from functools import partial
from operator import add
from pgl.superfish import parse_sf7
from pgl.curve import polynomial, fit_polynomial, gaussian, fit_gaussian

field_map = parse_sf7(r'HPRF_100x100_field_map_36_MV_m.txt')
# print field_map.z_values()
# Calculate surface charge and current distributions from field map
# print field_map.data
# print field_map.Ez(-0.81, 0.228600)

"""
file = open('fields_values.txt', 'w')
for r in field_map.r_values():
  for z in field_map.z_values():
    file.write("".join((str(z), ' ', str(r), ' ', str(field_map.H(z, r)), '\n')))
file.close()
"""
r_values = field_map.r_values();
z_values = field_map.z_values();

inner_radius = 11.43  # cm (radius of inner wall of cavity)
button_radius = 2.54  # cm
inner_height = 8.128  # cm (height of center flange)
button_base_height = 0.635  # cm
end_wall_r_values = r_values[bisect_right(r_values, button_radius):]
button_z_values = z_values[:bisect_right(z_values,
                                         -inner_height/2+button_base_height)]

############## Calculate Normal Forces ##############

# Ez and Er are in MV/m. H is in A/m
f_E_w = np.zeros(len(r_values))
f_H_w = np.zeros(len(r_values))
r_index = int(0)
# c = 2.998e10  # cm/s
epsilon_0 = 8.85418782e-12  # m^-3 kg^-1 s^4 A^2
mu_0 = 4*math.pi*1e-7  # kg m s-2 A^-2
for r in r_values:
  # Electric normal force on end walls
  field_value = field_map.E(z_values[0], r)
  f_E_w[r_index] = epsilon_0/2 * (field_value*1e6)**2

  # Magnetic normal force on end walls
  field_value = field_map.H(z_values[0], r)
  f_H_w[r_index] = -mu_0/2. * field_value**2
  r_index += 1

z_index = int(0)
f_E_c = np.zeros(len(z_values))
f_H_c = np.zeros(len(z_values))
f_E_b = np.zeros(len(z_values))
f_H_b = np.zeros(len(z_values))
g_c = np.zeros(len(z_values))
g_b = np.zeros(len(z_values))
sigma_c = 1.6
sigma_b = 1.3
mu = 0
y_mirror = 0.0
for z in z_values:
  # Electric normal force on circumference
  field_value = field_map.E(z, r_values[::-1][0])
  f_E_c[z_index] = epsilon_0/2 * (field_value*1e6)**2

  # Magnetic normal force on circumference
  field_value = field_map.H(z, r_values[::-1][0])
  f_H_c[z_index] = -mu_0/2. * field_value**2

  # Electric normal force on button
  for r in r_values:
    field_value = field_map.E(z, r)
    if (field_value > 0.):
      #       SI
      # f_E_r = -epsilon_0/2 * E^2
      f_E_b[z_index] = epsilon_0/2. * (field_value*1e6)**2
      """
        The field in the gap between the buttons falls off a bit towards the
        center. This makes it difficult to get a good polynomial fit. If we
        flip the dip around, this provides a nice cap on the curve that
        produces a nice fit in the region we care about (on the buttons).
      """
      """
      print ''.join(("z: ", str(z)))
      if (abs(abs(z)-0.8128) < 1e-12):
        # save the y value right where the gap starts to use as an offset
        gap_y_offset_E_b = f_E_b[z_index]
        print ''.join(("gap_y_offset_E_b: ", str(gap_y_offset_E_b)))
      if (abs(z) <= 0.8128):
        # in the gap we flip the curve about the x axis and then slide
        # it back into place on the original side of the axis.
        f_E_b[z_index] = -f_E_b[z_index]+2*gap_y_offset_E_b
      """
      break

  # Magnetic normal force on button
  for r in r_values:
    field_value = field_map.H(z, r)
    if (field_value > 0.):
      #       SI
      # f_H_r = -mu_0/2 * H^2
      f_H_b[z_index] = -mu_0/2. * field_value**2
      """
        The field in the gap between the buttons creates a flat top on the
        curve. This makes it difficult to get a good polynomial fit. The flat
        top is actually slightly bowed outward, so if we push it out to smooth
        the curve out we can get a better fit in the region we care about (on
        the buttons).
      """
      """
      if (abs(abs(z)-0.8128) < 1e-12):
        # save the y value right where the gap starts to use as an offset
        gap_y_offset_H_b = f_H_b[z_index]
      if (abs(z) <= 0.8128):
        f_H_b[z_index] = 400*(f_H_b[z_index]-gap_y_offset_H_b)
      """
      break

  z_index += 1

############## Plynomial Curve Fitting ##############
# Electric normal pressure on end wall
orders = np.array((12, 12, 12, 12, 12, 12, 12, 12, 12))

"""
domains = np.array((map(partial(add, r_values[22]), r_values),
                    z_values, z_values,
                    map(partial(add, r_values[22]), r_values),
                    z_values, z_values))
"""
domains = np.array((r_values[23:],
                    z_values, z_values,
                    r_values[23:],
                    z_values, z_values,
                    r_values[23:],
                    z_values, z_values))
print r_values
"""
forces = np.array((np.roll(f_E_w, -23)[:-21], f_E_c, f_E_b,
                   np.roll(f_H_w, -23)[:-21], f_H_c, f_H_b))
"""
forces = np.array((f_E_w[23:], f_E_c, f_E_b,
                   f_H_w[23:], f_H_c, f_H_b,
                   f_E_w[23:]+f_H_w[23:], f_E_c+f_H_c, f_E_b+f_H_b))
# print "".join(("Shape of forces: ", str(np.shape(forces))))
names = ("f_E_w", "f_E_c", "f_E_b", "f_H_w", "f_H_c", "f_H_b",
         "f_E_w+f_H_w", "f_E_c+f_H_c", "f_E_b+f_H_b")
coefficient_sets = map(fit_polynomial, domains, forces, orders)
for i in range(np.shape(forces)[0]):
  message = "".join(("Polynomial coefficients for ", names[i], ":\n\t",
                     ",".join(map(str, coefficient_sets[i]))))
  print message

fits = map(lambda coefficients,domain:
            polynomial(coefficients, domain),
            coefficient_sets, domains)
print fits
##############   Plotting    ##############
fig = plt.figure(figsize=(16.0, 9.0))

i = int(0)
plt.subplot(331+i)
#plt.plot(domains[i], forces[i]+forces[i+3])
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Electric Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')
#plt.plot(domains[i+3], np.abs(forces[i+3]))
#plt.plot(domains[i+3], -fits[i+3])

i += 1
plt.subplot(331+i)
#plt.plot(domains[i], forces[i]+forces[i+3])
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Electric Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')
#plt.plot(domains[i+3], np.abs(forces[i+3]))

i += 1
plt.subplot(331+i)
#plt.plot(domains[i], forces[i]+forces[i+3])
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Electric Pressure on Button')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')
#plt.plot(domains[i+3], np.abs(forces[i+3]))

i += 1
plt.subplot(331+i)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Magnetic Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(331+i)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Magnetic Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(331+i)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Magnetic Pressure on Button')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(331+i)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal E&M Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(331+i)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal E&M Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(331+i)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal E&M Pressure on Button')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')

plt.subplots_adjust(hspace=0.35, wspace=0.4)

plt.show()