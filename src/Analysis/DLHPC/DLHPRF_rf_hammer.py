# -*- coding: utf-8 -*-
"""
Created on Sun Jan 25 20:32:38 2015

@author: plane
"""
import math
import numpy as np
from scipy import fftpack
import scipy.signal
import matplotlib.pyplot as plt  # Matplotlib's pyplot: MATLAB-like syntax
from bisect import bisect_left, bisect_right
from functools import partial
from operator import add
from pgl.superfish import parse_sf7
from pgl.curve import polynomials, fit_spliced_polynomials, gaussian, fit_gaussian

sf7_dir = 'C:\\Users\\plane\\Documents\\Superfish\\DL HPRF\\99.5% Donut\\'
sf7_file = 'DL_HPRF_100x100_field_map_20_MV_m.txt'
field_map = parse_sf7(''.join((sf7_dir, sf7_file)))
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
inner_radius = 11.43  # cm (radius of inner wall of cavity)
donut_radius = 3.853  # cm
inner_height = 8.128  # cm (height of center flange)
z_offset = inner_height/2.0

z_values = field_map.z_values()-z_offset;
r_values = field_map.r_values();

print ''.join(('delta_z: ', str(z_values[1]-z_values[0])))
print ''.join(('delta_r: ', str(r_values[1]-r_values[0])))
"""
end_wall_r_values = r_values[bisect_right(r_values, button_radius):]
button_z_values = z_values[:bisect_right(z_values,
                                         -inner_height/2+button_base_height)]
"""

############## Calculate Normal Forces ##############

# Ez and Er are in MV/m. H is in A/m
f_E_w = np.zeros(len(r_values))  # electric force on wall
f_H_w = np.zeros(len(r_values))  # magnetic force on wall
f_Ez_d = np.zeros(len(r_values))  # z-axis electric force on donut
f_Er_d = np.zeros(len(r_values))  # r-axis electric force on donut
# c = 2.998e10  # cm/s
epsilon_0 = 8.85418782e-12  # m^-3 kg^-1 s^4 A^2
# epsilon_r = 9.84  # 98.5% purity alumina
epsilon_r = 9.57  # 99.5% purity alumina
mu_0 = 4*math.pi*1e-7  # kg m s-2 A^-2
for index,r in enumerate(r_values):
  # Electric normal force on end walls
  field_value = field_map.E(z_values[0]+z_offset, r)
  f_E_w[index] = epsilon_0/2 * (field_value*1e6)**2

  # Magnetic normal force on end walls
  field_value = field_map.H(z_values[0]+z_offset, r)
  f_H_w[index] = -mu_0/2. * field_value**2

  last_z = 0.0
  last_E = field_map.E(z_values[5]+z_offset, r)
  for z in z_values[5:]:
    current_E = field_map.E(z+z_offset, r)
    #print abs(current_E-last_E)/current_E
    if (abs(current_E-last_E)/current_E) > 0.25:
      # print ''.join(('z: ', str(last_z), '\tr: ', str(r)))
      # last_E is the surface field entering dielectric
      Ez_out = field_map.Ez(last_z+z_offset, r)*1e6
      Ez_in = field_map.Ez(z+z_offset, r)*1e6  # approximately
      Er_out = field_map.Er(last_z+z_offset, r)*1e6
      Er_in = field_map.Er(z+z_offset, r)*1e6  # approximately
      sigma = -epsilon_0 * math.sqrt((Ez_out-Ez_in)**2 + (Er_out-Er_in)**2)
      f_Ez_d[index] = 0.5 * sigma * Ez_out
      f_Er_d[index] = 0.5 * sigma * Er_out
      break
    last_z = z
    last_E = current_E

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
for index,z in enumerate(z_values):
  # Electric normal force on circumference
  field_value = field_map.E(z+z_offset, r_values[::-1][0])
  f_E_c[index] = epsilon_0/2 * (field_value*1e6)**2

  # Magnetic normal force on circumference
  field_value = field_map.H(z+z_offset, r_values[::-1][0])
  f_H_c[index] = -mu_0/2. * field_value**2

  # Electric normal force on button
  """
  for r in r_values:
    field_value = field_map.E(z+z_offset, r)
    if (field_value > 0.):
      #       SI
      # f_E_r = -epsilon_0/2 * E^2
      f_E_b[index] = epsilon_0/2. * (field_value*1e6)**2
  """
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

############## Plynomial Curve Fitting ##############
# Electric normal pressure on end wall
orders = np.array((12, 12, 12, 12, 12, 12, 12, 12))

"""
domains = np.array((map(partial(add, r_values[22]), r_values),
                    z_values, z_values,
                    map(partial(add, r_values[22]), r_values),
                    z_values, z_values))
"""
domains = np.array((r_values, z_values, r_values,
                    r_values, z_values, r_values,
                    r_values, z_values))
# print r_values
"""
forces = np.array((np.roll(f_E_w, -23)[:-21], f_E_c, f_E_b,
                   np.roll(f_H_w, -23)[:-21], f_H_c, f_H_b))
"""
forces = np.array((f_E_w, f_E_c, f_Ez_d,
                   f_H_w, f_H_c, f_Er_d,
                   f_E_w+f_H_w, f_E_c+f_H_c))
segments_sets = [[[0,len(z_values)]],
                [[0,int(math.floor(len(z_values)/2.)-10)],
                 [int(math.floor(len(r_values)/2.)-10),int(math.floor(len(z_values)/2.)+11)],
                 [int(math.floor(len(r_values)/2.)+11),len(r_values)]],
                [[0,34], [34,42], [42,72], [72,79], [79,101]],
                [[0,len(z_values)]],
                [[0,len(r_values)]],
                [[0,34], [34,42], [42,72], [72,79], [79,101]],
                [[0,len(z_values)]],
                [[0,int(math.floor(len(z_values)/2.)-10)],
                 [int(math.floor(len(r_values)/2.)-10),int(math.floor(len(z_values)/2.)+11)],
                 [int(math.floor(len(r_values)/2.)+11),len(r_values)]]]
print segments_sets
for index,segments in enumerate(segments_sets):
  message = "["
  for segment in segments:
    message = ''.join((message, str([domains[index][segment[0]],
                                     domains[index][segment[1]-1]]),
                       ' '))
  print ''.join((message, ']'))
# print "".join(("Shape of forces: ", str(np.shape(forces))))
names = ("f_E_w", "f_E_c", "f_Ez_d",
         "f_H_w", "f_H_c", "f_Er_d",
         "f_E_w+f_H_w", "f_E_c+f_H_c")
coefficient_sets = map(fit_spliced_polynomials, domains, forces, orders,
                       segments_sets)
for i in range(np.shape(forces)[0]):
  coefficient_set = coefficient_sets[i]
  coeff_strings = []
  for coefficients in coefficient_set:
    coeff_string = ','.join(map(str, coefficients))
    coeff_strings += [''.join(('[', coeff_string, ']'))]
  message = "".join(("Polynomial coefficients for ", names[i], ":\n\t",
                     ",".join(coeff_strings)))
  print message
print len(segments_sets)
print len(coefficient_sets)
fits = map(lambda coefficient_set, segments, domain:
            polynomials(segments, coefficient_set, domain),
            coefficient_sets, segments_sets, domains)
##############   Plotting    ##############
fig = plt.figure(figsize=(16.0, 9.0))

i = int(0)
plt.subplot(3, 3, i+1)
#plt.plot(domains[i], forces[i]+forces[i+3])
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Electric Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')
#plt.plot(domains[i+3], np.abs(forces[i+3]))
#plt.plot(domains[i+3], -fits[i+3])

i += 1
plt.subplot(3, 3, i+1)
#plt.plot(domains[i], forces[i]+forces[i+3])
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Electric Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')
#plt.plot(domains[i+3], np.abs(forces[i+3]))

i += 1
plt.subplot(3, 3, i+1)
#plt.plot(domains[i], forces[i]+forces[i+3])
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Axial Electric Pressure on Donut Surface')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')
#plt.plot(domains[i+3], np.abs(forces[i+3]))

i += 1
plt.subplot(3, 3, i+1)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Magnetic Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(3, 3, i+1)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Magnetic Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(3, 3, i+1)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Radial Electric Pressure on Donut')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(3, 3, i+1)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal E&M Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(3, 3, i+1)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal E&M Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')
"""
plt.plot(domains[7], forces[7])
plt.plot(domains[7], fits[7])
plt.title('Normal E&M Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')
"""
plt.subplots_adjust(hspace=0.35, wspace=0.4)

plt.show()