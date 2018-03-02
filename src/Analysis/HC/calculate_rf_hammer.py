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

from pgl.superfish import parse_sf7
from pgl.curve import polynomial, fit_polynomial, gaussian, fit_gaussian
from functools import partial
from operator import add

def reset_plot_params():
    mpl.rcParams['ytick.labelsize'] = 22
    mpl.rcParams['xtick.labelsize'] = 22
    mpl.rcParams['axes.labelsize'] = 26
    mpl.rcParams['axes.titlesize'] = 26
    mpl.rcParams['font.size'] = 13
    mpl.rcParams['mathtext.default'] = 'regular'
    mpl.rcParams['figure.subplot.left'] = 0.02
    mpl.rcParams['figure.subplot.right'] = 0.98
    mpl.rcParams['figure.subplot.top'] = 0.9
    mpl.rcParams['figure.subplot.bottom'] = 0.1
    mpl.rcParams['figure.subplot.wspace'] = 0.2
    mpl.rcParams['figure.subplot.hspace'] = 0.2
reset_plot_params()

field_map = parse_sf7(''.join((
  'C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\HPRF\\',
  'HPRF_100x100_field_map_36_MV_m.txt')))
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
inner_height = 8.1  # cm (height of center flange)
button_base_height = 0.65  # cm
end_wall_r_values = r_values[bisect_right(r_values, button_radius):]
button_z_values = z_values[:bisect_right(z_values,
                                         -inner_height/2+button_base_height)]

############## Calculate Normal Forces ##############

# Ez and Er are in MV/m. H is in A/m
f_E_w = np.zeros(np.shape(r_values))
f_H_w = np.zeros(np.shape(r_values))
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
f_E_c = np.zeros(np.shape(z_values))
f_H_c = np.zeros(np.shape(z_values))
f_E_b = np.zeros(np.shape(z_values))
f_H_b = np.zeros(np.shape(z_values))
g_c = np.zeros(np.shape(z_values))
g_b = np.zeros(np.shape(z_values))
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
      if (abs(abs(z)-0.81) < 1e-2):
        # save the y value right where the gap starts to use as an offset
        gap_y_offset_E_b = f_E_b[z_index]
      if (abs(z) <= 0.81):
        # in the gap we flip the curve about the x axis and then slide
        # it back into place on the original side of the axis.
        f_E_b[z_index] = 2*gap_y_offset_E_b - f_E_b[z_index]
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
      if (abs(abs(z)-0.81) < 1e-2):
        # save the y value right where the gap starts to use as an offset
        gap_y_offset_H_b = f_H_b[z_index]
      if (abs(z) <= 0.81):
        f_H_b[z_index] = 400*(f_H_b[z_index]-gap_y_offset_H_b)
      break

  z_index += 1

############## Plynomial Curve Fitting ##############
# Electric normal pressure on end wall
orders = np.array((12, 12, 12, 6, 12, 12))

"""
domains = np.array((map(partial(add, r_values[22]), r_values),
                    z_values, z_values,
                    map(partial(add, r_values[22]), r_values),
                    z_values, z_values))
"""
domains = np.array((r_values[23:],
                    z_values, z_values,
                    r_values[23:],
                    z_values, z_values))
print r_values
"""
forces = np.array((np.roll(f_E_w, -23)[:-21], f_E_c, f_E_b,
                   np.roll(f_H_w, -23)[:-21], f_H_c, f_H_b))
"""
forces = np.array((f_E_w[23:], f_E_c, f_E_b,
                   f_H_w[23:], f_H_c, f_H_b))
# print "".join(("Shape of forces: ", str(np.shape(forces))))
names = ("f_E_w", "f_E_c", "f_E_b", "f_H_w", "f_H_c", "f_H_b")
coefficient_sets = map(fit_polynomial, domains, forces, orders)
for i in range(np.shape(forces)[0]):
  message = "".join(("Polynomial coefficients for ", names[i], ":\n\t",
                     ",".join(map(str, coefficient_sets[i]))))
  print message
fits = map((lambda coefficients,domain:
              map((lambda x:
                polynomial(coefficients, np.zeros(coefficients.size))),
                domain)), coefficient_sets, domains)
fits = [polynomial(coefficients, domain) for coefficients,domain in zip(coefficient_sets,domains)]
##############   Plotting    ##############
plt.figure(figsize=(20,9))

i = int(0)
plt.subplot(231)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
#plt.title('Normal Electric Pressure on End Wall')
plt.title('End Plate')
#plt.xlabel('Radius (cm)')
plt.ylabel('Normal Electric\nPressure (N/m^2)')

i += 1
plt.subplot(232)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
#plt.title('Normal Electric Pressure on Circumference')
plt.title('Circumference')
#plt.xlabel('z (cm)')

i += 1
plt.subplot(233)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
#plt.title('Normal Electric Pressure on Button')
plt.title('Buttons')
#plt.xlabel('z (cm)')
plt.plot([-.821,-.821], [forces[i].min(), forces[i].max()], 'k')
plt.plot([.821,.821], [forces[i].min(), forces[i].max()], 'k')
plt.text(-0.2, 6000, 'Gap', rotation='vertical', size='26')


i += 1
plt.subplot(234)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
#plt.title('Normal Magnetic Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Normal Magnetic\nPressure (N/m^2)')

i += 1
plt.subplot(235)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
#plt.title('Normal Magnetic Pressure on Circumference')
plt.xlabel('z (cm)')

i += 1
plt.subplot(236)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.plot([-.821,-.821], [forces[i].min(), forces[i].max()], 'k')
plt.plot([.821,.821], [forces[i].min(), forces[i].max()], 'k')
plt.text(-0.2, -200, 'Gap', rotation='vertical', size='26')
#plt.title('Normal Magnetic Pressure on Button')
plt.xlabel('z (cm)')

plt.tight_layout()
plt.show()