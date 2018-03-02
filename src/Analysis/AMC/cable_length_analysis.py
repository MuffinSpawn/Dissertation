# -*- coding: utf-8 -*-
"""
Created on Fri Feb 19 16:28:19 2016

@author: plane
"""

import math
import subprocess as proc
import sys
import platform
import numpy as np  # NumPy (multidimensional arrays, linear algebra, ...)
import matplotlib as mpl
import matplotlib.cm as cm
import matplotlib.collections as mcollections
import matplotlib.patches as mpatches
import matplotlib.pyplot as plt  # Matplotlib's pyplot: MATLAB-like syntax
import scipy.signal as sig
import scipy.special as special
import bisect as bi
import time
import pgl.comsol as comsol
import pgl.curve as curve
import pgl.labview as labview
import pgl.plot as plot
import pgl.signal as psig
import pgl.tektronix as tektronix
import pgl.mc as mc
import pgl.progress as prog
import pgl.cluster as clust
from pgl.curve import fit_reciprocal, reciprocal, fit_polynomial, polynomial

def reset_plot_params():
    mpl.rcParams['ytick.labelsize'] = 22
    mpl.rcParams['xtick.labelsize'] = 22
    mpl.rcParams['axes.labelsize'] = 26
    mpl.rcParams['font.size'] = 26
    mpl.rcParams['mathtext.default'] = 'regular'
    mpl.rcParams['figure.subplot.left'] = 0.02
    mpl.rcParams['figure.subplot.right'] = 0.98
    mpl.rcParams['figure.subplot.top'] = 0.9
    mpl.rcParams['figure.subplot.bottom'] = 0.1
    mpl.rcParams['figure.subplot.wspace'] = 0.2
    mpl.rcParams['figure.subplot.hspace'] = 0.2
reset_plot_params()


# # Cable Length Attenuation Analysis

# ## September 8, 2015

cable_lengths = np.array((59.0, 390.0, 518.0, 883.5))

cable_vpp_measurements = np.array(((0.87, 1.37, 1.21, 1.03, 1.54, 1.53, 1.01, 0.84, 1.48, 1.06),
                                   (0.104, 0.062, 0.199, 0.123, 0.103, 0.147, 0.092, 0.087, 0.133),
                                   (0.145, 0.086, 0.087, 0.120, 0.070, 0.065, 0.126, 0.154, 0.165, 0.128),
                                   (0.091, 0.025, 0.020, 0.045, 0.030, 0.042, 0.057, 0.062, 0.068, 0.083)))
avg_vpp_list = np.zeros(len(cable_lengths))
stddev_list = np.zeros(len(cable_lengths))
for index,vpp_list in enumerate(cable_vpp_measurements):
    avg_vpp_list[index] = np.average(vpp_list)
    stddev_list[index] = np.std(vpp_list)
#plt.plot(cable_lengths, avg_vpp_list)bn
fig = plt.figure(figsize=(8.0, 4.0))
fig.suptitle("Vpp Measurements for Various Lengths of Microphone Cable", fontsize=12)
plt.xlabel('Cable Length (cm)')
plt.ylabel('Vpp (V)')
plt.errorbar(cable_lengths, avg_vpp_list, yerr=stddev_list)


# ## September 9 and 10, 2015
# Notes: Hot glued to Al disk surface. Impact distance is 2.8cm.

# For L=400cm the cable was spliced together from several segments. In the spliced regions the wires are separated and without a shield. This would decrease the capacitance in these regions and correspond to an increase in voltage (V=Q/C). This might account for the anomalous measurements of Vpp for this cable.

cable_lengths = np.array((59.0, 100.0, 150.0, 200.0, 250.0, 300.0, 350.0, 518.0, 883.5))
cable_vpp_measurements = np.array([np.array([1.62, 1.20, 1.61, 1.66, 1.73, 1.10, 1.08, 1.14, 1.62, 1.66]),
                                   np.array([1.32, 1.37, 1.19, 1.56, 1.02, 1.43, 1.41, 1.31, 1.24, 0.920, 1.36]),
                                   np.array([0.968, 1.09, 0.952, 0.952, 1.04, 1.02, 0.792, 1.24, 0.976, 1.23]),
                                   np.array([0.920, 0.876, 0.840, 0.880, 0.864, 0.940, 0.928, 0.828, 0.672, 0.812]),
                                   np.array([0.704, 0.760, 0.644, 0.860, 0.856, 0.596, 0.576, 0.736, 0.932, 0.884]),
                                   np.array([0.784, 0.468, 0.740, 0.980, 0.676, 0.864, 0.424, 0.472, 0.516, 0.488]),
                                   np.array([0.544, 0.616, 0.580, 0.512, 0.508, 0.532, 0.580, 0.548, 0.592, 0.600]),
                                   # np.array([0.756, 0.696, 0.608, 0.736, 0.768, 0.952, 0.848, 0.916, 0.876, 0.772]),  # L=400cm, *spliced* cable.
                                   np.array([0.588, 0.644, 0.424, 0.604, 0.668, 0.396, 0.356, 0.372, 0.476, 0.564]),
                                   np.array([0.380, 0.356, 0.408, 0.396, 0.308, 0.292, 0.320, 0.268, 0.314, 0.270])])
avg_peak_v_list = np.zeros(len(cable_lengths))
stddev_list = np.zeros(len(cable_lengths))
cable_vmax = np.zeros(np.shape(cable_vpp_measurements))
for index,vpp_list in enumerate(cable_vpp_measurements):
    peak_v_list = vpp_list / 2.0
    avg_peak_v_list[index] = np.average(np.array(peak_v_list))
    stddev_list[index] = np.std(peak_v_list)
#plt.plot(cable_lengths, avg_vpp_list)
fig = plt.figure(figsize=(14.0, 4.0))
#fig.suptitle("Vpp Measurements for Various Lengths of Microphone Cable", fontsize=12)
plt.subplot(121)
#plt.title("Vpp Measurements for Various Lengths of Microphone Cable", fontsize=12)
plt.xlabel('Cable Length (cm)')
plt.ylabel('Average Peak Voltage (V)')
plt.errorbar(cable_lengths, avg_peak_v_list, yerr=stddev_list, marker='.')

#(a, b) = fit_reciprocal(cable_lengths, avg_vpp_list, yerr=stddev_list)
#(a, b) = fit_reciprocal(cable_lengths, avg_vpp_list)
errors = np.array(map(lambda vpp,stddev: 2.0 / (vpp*vpp)*stddev, avg_peak_v_list, stddev_list))
(a, b) = fit_polynomial(cable_lengths, 1.0/avg_peak_v_list, order=1, yerr=errors)
fit_xs = range(60, 900, 10)
#fit_ys = np.array(map(lambda x: a + b/x, fit_xs))
fit_ys = np.array(map(lambda x: a + b*x, fit_xs))
#print ''.join((str(a), ' + ', str(b), '/x'))
print ''.join((str(a), ' + ', str(b), '*x'))
plt.plot(fit_xs, 1.0/fit_ys)

plt.subplot(122)
#plt.title("1/Vpp vs. Cable Length with Weighted Least Squares Fit", fontsize=12)
plt.xlabel('Cable Length (cm)')
plt.ylabel('1/(Average Peak Voltage) (V^-1)')
plt.errorbar(cable_lengths, 1.0/avg_peak_v_list, yerr=errors, linestyle='None', marker='.')

"""
(a, b) = fit_polynomial(cable_lengths, 1.0/avg_peak_v_list, order=1, yerr=errors)
# fit_xs = range(60, 900, 10)
fit_xs = range(60, 1000, 10)
fit_ys = np.array(map(lambda x: a + b*x, fit_xs))
print ''.join((str(a), ' + ', str(b), '*x'))
"""
plt.plot(fit_xs, fit_ys)

frequency = 133e3
cm = 1.83e-9  # F
#cm = 2.43e-12  # F
loss_factor = 0.02
mic_impedence = 1.0 / (2*math.pi*frequency*cm*loss_factor)
print ''.join(('Mic Impedence: ', str(mic_impedence)))

cc_per_cm = 46.4e-12  # F
#cc_per_cm = 59.0e-12  # F
cable_length_domain = np.arange(59, 883)
cable_impedences = map(lambda l: 1.0 / (2*math.pi*frequency*cc_per_cm*l), cable_length_domain)
voltage_drop_factors = np.array(map(lambda z: z/(mic_impedence + z), cable_impedences))
base_voltage = avg_peak_v_list[0] / voltage_drop_factors[0]
print ''.join(('Base Voltage: ', str(base_voltage)))

cp = 2.43e-12  # F
kq = 400.0e-10  # cm/V
max_disp = kq * base_voltage
print ''.join(('Max. Displacement: ', str(max_disp)))

"""
plt.subplot(121)
cable_lengths = np.arange(59, 883)
frequency = 400e3
# cp = 2.43e-12  # quoted for the piezo
cp = 1.83e-9
# cp = 10.0e-6  # enable a proper fit with appropriate scaling of the voltage drop factors (0.8 V)
loss_factor = 0.02
piezo_impedence = 1.0 / (2*math.pi*frequency*cp*loss_factor)
print ''.join(('Piezo Impedence: ', str(piezo_impedence)))
# cc_per_ft = 59e-12
cc_per_ft = 80.0e-16
cc_per_cm = cc_per_ft * 12 * 2.54
print ''.join(('Cable Capacitance per cm: ', str(cc_per_cm)))
cable_impedences = map(lambda l: 1.0 / (2*math.pi*frequency*cc_per_cm*l), cable_lengths)
# print ''.join(('Cable Impedences: ', str(cable_impedences)))
voltage_drop_factors = np.array(map(lambda z: z/(piezo_impedence + z), cable_impedences))
# voltage_drop_factors = voltage_drop_factors / voltage_drop_factors[0] * 0.8  # scale for cp=10.0e-6
voltage_drop_factors = voltage_drop_factors / voltage_drop_factors[0] * 0.7  # scale
slope = (1.0/voltage_drop_factors[-1] - 1.0/voltage_drop_factors[0]) / 840.0
print ''.join(('Slope: ', str(slope)))

plt.plot(cable_lengths, voltage_drop_factors, 'r')

plt.subplot(122)
recip_voltage_drop_factors = 1.0/voltage_drop_factors
(x1,y1) = (cable_lengths[0], recip_voltage_drop_factors[0])
(x2,y2) = (cable_lengths[-1], recip_voltage_drop_factors[-1])
m = (y1-y2) / (x1-x2)
b = y1-m*x1
print ''.join((str(b), ' + ', str(m), '*x'))
fit_ys = np.array(map(lambda x: b + m*x, fit_xs))
print ''.join((str(a), ' + ', str(b), '*x'))
plt.plot(fit_xs, fit_ys)
"""
plt.show()


# In[ ]:

L0 = 0.994603652368

# Mounting Method Attenuation Analysis
# ## September 8, 2015
# Notes: Freshly applied 5-minute epoxy and hot glue. Pocket scope.
# In[25]:

fig = plt.figure(figsize=(14.0, 4.0))
plt.subplot(121)
mount_vpp_measurements = np.array((np.array([0.531, 0.590, 0.680, 0.592, 0.609, 0.597, 0.573, 0.676, 0.695, 0.656]),
                                   np.array([2.23, 3.26, 2.44, 3.01, 3.53, 3.21, 3.00, 3.01, 3.66, 2.88]),
                                   np.array([0.730, 0.689, 0.763, 0.445, 0.640, 0.574, 0.794, 0.658, 0.725, 0.581]))) / 2.0
#fig.suptitle("Vpp Measurements for Double-sided Tape, Hot Glue, and 5-minute Epoxy Mountings", fontsize=12)
plt.ylabel('Peak Voltage (V)')
#plt.xlabel('Mounting Type (1. Double-sided Tape, 2. Hot Glue, 3. 5-minute Epoxy)')
plt.ylim((0.0, 2.0))
plt.boxplot(np.transpose(mount_vpp_measurements))
#plt.plot(avg_vpp_list)
"""
plt.xlim((0, 4))
plt.ylim((0.0, 4.0))
plt.errorbar((1, 2, 3), avg_vpp_list, yerr=stddev_list)
"""

plt.text(0.6, 0.1, 'Double-sided Tape')
plt.text(1.8, 0.1, 'Hot Glue')
plt.text(2.7, 0.1, '5-minute Epoxy')

plt.subplot(122)
mount_vpp_measurements = np.array((np.array([0.672, 0.728, 0.632, 0.744, 0.568, 0.688, 0.592, 0.712, 0.736, 0.536]),
                                   np.array([1.62, 1.20, 1.61, 1.66, 1.73, 1.10, 1.08, 1.14, 1.62, 1.66]),
                                   np.array([1.38, 0.992, 0.992, 1.13, 1.80, 1.26, 1.02, 1.16, 1.20, 1.46])))
#fig.suptitle("Vpp Measurements for Double-sided Tape, Hot Glue, and 5-minute Epoxy Mountings", fontsize=12)
plt.ylabel('Peak Voltage (V)')
# plt.xlabel('Mounting Type (1. Double-sided Tape, 2. Hot Glue, 3. 5-minute Epoxy)')
plt.ylim((0,2.0))
plt.boxplot(np.transpose(mount_vpp_measurements))

plt.text(0.6, 0.1, 'Double-sided Tape')
plt.text(1.8, 0.1, 'Hot Glue')
plt.text(2.7, 0.1, '5-minute Epoxy')


# ## September 9, 2015
# Notes: ~20-hours cured epoxy. Freshly applied hot glue. Tektronix TDS 2024B.

# The much longer probe leads used with the Tek scope probably account for the smaller Vpp measurements.

mount_vpp_measurements = np.array(((0.672, 0.728, 0.632, 0.744, 0.568, 0.688, 0.592, 0.712, 0.736, 0.536),
                                   (1.62, 1.20, 1.61, 1.66, 1.73, 1.10, 1.08, 1.14, 1.62, 1.66),
                                   (1.38, 0.992, 0.992, 1.13, 1.80, 1.26, 1.02, 1.16, 1.20, 1.46)))
fig = plt.figure(figsize=(8.0, 4.0))
#fig.suptitle("Vpp Measurements for Double-sided Tape, Hot Glue, and 5-minute Epoxy Mountings", fontsize=12)
plt.ylabel('Vpp (V)')
# plt.xlabel('Mounting Type (1. Double-sided Tape, 2. Hot Glue, 3. 5-minute Epoxy)')
plt.ylim((0,2.0))
plt.boxplot(np.transpose(mount_vpp_measurements))


def Z(R, L, C, f):
    XL = 2*math.pi*f*L
    XC = 1.0 / (2*math.pi*f*C)
    return math.sqrt(R**2 + (XL+XC)**2)

fig = plt.figure(figsize=(24.0, 4.0))

plt.subplot(131)
plt.title("11MOhm Cable Impedance at 12.2Hz")
plt.xlabel("Frequency (Hz)")
plt.ylabel("Cable Impedance (Ohm)")
frequencies = np.arange(10, 20, 1)
Zs = np.array(map(lambda f: Z(0.482, 3.4e-6, 1.18e-9, f), frequencies))
plt.plot(frequencies, Zs)

plt.subplot(132)
plt.title("Cable Impedances In Nominal Frequency Range")
plt.xlabel("Frequency (Hz)")
plt.ylabel("Cable Impedance (Ohm)")
frequencies = np.arange(5000, 20000, 1e3)
Zs = np.array(map(lambda f: Z(0.482, 3.4e-6, 1.18e-9, f), frequencies))
plt.plot(frequencies, Zs)

plt.subplot(133)
plt.title("Crossing Point of Inductive and Capacitive Reactance at 2.5MHz")
plt.xlabel("Frequency (Hz)")
plt.ylabel("Cable Ind. (Blue) and Cap. (Green) Reactance (Ohm)")
frequencies = np.arange(500e3, 4000e3, 1e3)
XL = np.array(map(lambda f: 2*math.pi*f*3.4e-6, frequencies))
plt.plot(frequencies, XL)
XC = np.array(map(lambda f: 1.0/(2*math.pi*f*1.18e-9), frequencies))
plt.plot(frequencies, XC)


# cable_lengths = np.array((1.0, 2.0, 4.0, 8.0, 16.0, 32.0, 59.0, 100.0, 150.0, 200.0, 250.0, 300.0, 350.0, 518.0, 883.5))  # cm
#cable_lengths = np.array((1.0, 2.0, 4.0, 8.0, 16.0, 32.0, 64.0))  # cm
cable_lengths = np.arange(59, 883)
frequency = 400e3
# cp = 2.43e-12
cp = 1.88e-9
loss_factor = 0.02
piezo_impedence = 1.0 / (2*math.pi*frequency*cp*loss_factor)
print ''.join(('Piezo Impedence: ', str(piezo_impedence)))
cc_per_ft = 59e-12
cc_per_cm = cc_per_ft * 12 * 2.54
print ''.join(('Cable Capacitance per cm: ', str(cc_per_cm)))
cable_impedences = map(lambda l: 1.0 / (2*math.pi*frequency*cc_per_cm*l), cable_lengths)
# print ''.join(('Cable Impedences: ', str(cable_impedences)))
voltage_drop_factors = np.array(map(lambda z: z/(piezo_impedence + z), cable_impedences))
voltage_drop_factors = voltage_drop_factors / voltage_drop_factors[0] * 1.44
slope = (1.0/voltage_drop_factors[-1] - 1.0/voltage_drop_factors[0]) / 840.0
print ''.join(('Slope: ', str(slope)))


fig = plt.figure(figsize=(14.0, 4.0))
#fig.suptitle("Vpp Measurements for Various Lengths of Microphone Cable", fontsize=12)
plt.subplot(121)
#plt.title("Voltage Drop Factor for Various Lengths of Microphone Cable", fontsize=12)
plt.xlabel('Cable Length (cm)')
plt.ylabel('Voltage Drop Ratio, k')
#plt.errorbar(cable_lengths, voltage_drop_factors, yerr=stddev_list, marker='.')
plt.ylim((0,1.8))
plt.plot(cable_lengths, voltage_drop_factors, 'k')

plt.subplot(122)
#plt.title("Inverse Voltage Drop Factor vs. Cable Length", fontsize=12)
plt.xlabel('Cable Length (cm)')
plt.ylabel('1/k')
plt.ylim((0,10))
recip_voltage_drop_factors = 1.0/voltage_drop_factors
plt.plot(cable_lengths, recip_voltage_drop_factors, 'k')

(x1,y1) = (cable_lengths[0], recip_voltage_drop_factors[0])
(x2,y2) = (cable_lengths[-1], recip_voltage_drop_factors[-1])
m = (y1-y2) / (x1-x2)
b = y1-m*x1
print ''.join((str(b), ' + ', str(m), '*x'))


# 2 * 12 * 2.54

xs = np.array([85.6, 66.4, 51.2, 40.8, 20.0, 15.3])
ys = np.array([11.97, 15.4, 19.6, 21.6, 53.3, 70.0])
plt.plot(xs, ys)
fit_cs = fit_reciprocal(xs, ys)
fit_xs = range(10, 90, 1)
fit_ys = reciprocal(fit_cs, fit_xs)
plt.plot(fit_xs, fit_ys)
print ''.join(('y = ', str(fit_cs[0]), ' + ', str(fit_cs[1]), '/x'))
print ''.join(('5 mV -> ', str(fit_cs[0] + fit_cs[1]*0.2), ' kOhms'))
print ''.join(('5.6 mV -> ', str(fit_cs[0] + fit_cs[1]*1.0/5.6), ' kOhms'))
