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

from superfish import parse_sf7
from curve import polynomial, fit_polynomial, gaussian, fit_gaussian
from functools import partial
from operator import add

print sys.path

field_map = parse_sf7(r'MC_200x200_field_map.txt')
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
#z_values = z_values[108:-8]
#z_values = z_values[8:93]
#z_values = z_values[93:104]
#z_values = z_values[8:99]
z_values = z_values[100:-8]
print z_values
# print z_values

inner_radius = 14.22  # cm (radius of inner wall of cavity)
inner_height = 10.44  # cm (height of center flange)

############## Calculate Normal Forces ##############

# Ez and Er are in MV/m. H is in A/m
f_E_w = np.zeros(size(r_values))
f_H_w = np.zeros(size(r_values))
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
f_E_c = np.zeros(size(z_values))
f_H_c = np.zeros(size(z_values))
g_c = np.zeros(size(z_values))
sigma_c = 1.6
mu = 0
y_mirror = 0.0
for z in z_values:
  # Electric normal force on circumference
  field_value = field_map.E(z, r_values[-1])
  #print field_value
  f_E_c[z_index] = epsilon_0/2 * (field_value*1e6)**2

  # Magnetic normal force on circumference
  # field_value = field_map.H(z, r_values[::-1][0])
  field_value = field_map.H(z, r_values[-1])
  #print field_value
  f_H_c[z_index] = -mu_0/2. * field_value**2

  z_index += 1

############## Plynomial Curve Fitting ##############
# Electric normal pressure on end wall
orders = np.array((12, 12, 12, 5))

"""
domains = np.array((map(partial(add, r_values[22]), r_values),
                    z_values, z_values,
                    map(partial(add, r_values[22]), r_values),
                    z_values, z_values))
"""
domains = np.array((r_values, z_values, r_values, z_values))
#print r_values
forces = np.array((f_E_w, f_E_c, f_H_w, f_H_c))
# print "".join(("Shape of forces: ", str(np.shape(forces))))
names = ("f_E_w", "f_E_c", "f_H_w", "f_H_c")
coefficient_sets = map(fit_polynomial, domains, forces, orders)
for i in range(np.shape(forces)[0]):
  message = "".join(("Polynomial coefficients for ", names[i], ":\n\t",
                     ",".join(map(str, coefficient_sets[i]))))
  print message
fits = map((lambda coefficients,domain:
              map((lambda x:
                polynomial(x, coefficients, np.zeros(coefficients.size))),
                domain)), coefficient_sets, domains)

##############   Plotting    ##############
plt.figure(1)

i = int(0)
plt.subplot(221)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Electric Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(222)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Electric Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(223)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Magnetic Pressure on End Wall')
plt.xlabel('Radius (cm)')
plt.ylabel('Pressure (N/m^2)')

i += 1
plt.subplot(224)
plt.plot(domains[i], forces[i])
plt.plot(domains[i], fits[i])
plt.title('Normal Magnetic Pressure on Circumference')
plt.xlabel('z (cm)')
plt.ylabel('Pressure (N/m^2)')

plt.show()