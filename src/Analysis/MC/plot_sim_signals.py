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
import pgl.tree

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

def order_by_time(times, signals, live=True):
  cwts = mc.signals_to_cwts(times, signals)
  mic_tree = pgl.tree.BinarySearchTree()
  for index,cwt in enumerate(cwts):
    grad = np.gradient(cwt)
    signs = np.sign(grad)
    signs[signs==0] = -1
    peak_indicies = np.where(np.gradient(signs) < 0)
    peak_times = times[peak_indicies]
    peak_amplitudes = cwt[peak_indicies]
    max_peak_amplitude = peak_amplitudes.max()
    constrained_peak_indicies = None
    if live:
      # real data has noise before the first peak, so pick the first peak that
      # is above half the signal amplitude
      constrained_peak_indicies = peak_amplitudes > (max_peak_amplitude/2.0)
    else:
      # since simulations have no noise, just pick the first peak we see
      constrained_peak_indicies = [peak_amplitudes.argmax()]
    print 'Wavefront Time:', peak_times[constrained_peak_indicies][0]
    node = pgl.tree.BinaryTreeNode(index,
                                   peak_times[constrained_peak_indicies][0])
    mic_tree.insert(node)
  return mic_tree.walk_in_order()

data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\"
if platform.system() == 'Linux':
    data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/MC/"
plt.figure(figsize=(12,20))
#comsol_file = "test_[9]_400kHz.npy"
#comsol_file = "test_0_10x4_400kHz.npy"
#comsol_file = "test_0_10x4_2MHz.npy"
#comsol_file = "test_0_10x4_2MHz_finer.npy"
#comsol_file = "random1_8MHz.npy"
#comsol_file = "random1_40MHz.npy"
#comsol_file = "random100_4MHz.npy"
comsol_file = "random100_400kHz.npy"
#comsol_file = "random100_1MHz.npy"
#comsol_file = "random100_2MHz.npy"
#comsol_file = "random100.npy"
actual_locations = np.load("".join((data_dir, "actual_locations.npy")))[:5]
mic_coordinates = np.array(zip([5, -5, -5, 5], [6, 6, -6, -6]))
dt = 2.5e-6
(times, signal_sets) = comsol.load_data(data_dir, comsol_file, dt=dt)
#signals = signal_sets[8]
for index,signals in enumerate(signal_sets[:5]):
#for index,signals in enumerate(signal_sets):
  print actual_locations[index]
  distances = np.sqrt(np.sum((mic_coordinates-actual_locations[index])**2, axis=1))
  print 'Source-Mic Times:', distances/2.3e5
  plt.subplot(511+index)
  #plt.xlim(0,120)
  plot.plot_signals(times*1e6, signals)
  cwts = mc.signals_to_cwts(times, signals)
  #plot.plot_signals(times*1e6, cwts)
  print order_by_time(times, signals, False)

  #plot.plot_signals(times*1e6, cwts)

  #location_prediction = mc.localize_spark(times, signals, dt=dt)
  #print 'Predicted Location:', location_prediction
plt.tight_layout()



"""
location_prediction = mc.localize_spark(times, signals, dt=dt)
print 'Predicted Location:', location_prediction
"""
"""
damped_signals = np.zeros((np.shape(signals)[0], np.shape(signals)[1]))
blank_offset = int(22e-6 / dt)
blank = np.zeros(blank_offset)
for index,signal in enumerate(signals):
    max_amplitude = np.max(np.abs(signal))
    damped_signals[index] = \
      [signals[index,x]* (math.exp(1-times[x]/2.0e-5)) \
       for x in range(np.shape(signals)[1])]
    damped_signals[index,:blank_offset] = blank
"""
"""
location_prediction = mc.localize_spark_sin(times, damped_signals, dt=dt)
print 'Predicted Location:', location_prediction
"""
"""
#comsol_file = "random1_6MHz.npy"
#comsol_file = "random1_4MHz.npy"
#comsol_file = "random100_8MHz.npy"
#comsol_file = "random100_400kHz.npy"
#comsol_file = "random1_12.5MHz.npy"
#comsol_file = "random1_22MHz.npy"
#comsol_file = "random1_40MHz.npy"
#comsol_file = "random1_8MHz.npy"
#comsol_file = "random100_667kHz.npy"
dt = 2.5e-6
#(times, signal_sets) = comsol.load_data(data_dir, comsol_file, dt=dt)
signals = signal_sets[9]
#(times, signal_sets) = comsol.load_data(data_dir, comsol_file, dt=dt)
plt.subplot(212)
#plt.xlim(0,120)
#signals = signal_sets[0]
plot.plot_signals(times*1e6, signals)
"""
"""
#comsol_file = "upstream_only.npy"
#comsol_file = "source_random_50.npy"
#comsol_file = "alt_mic_config_random_100.npy"
comsol_file = "random100.npy"
#comsol_file = "random100_r3cm.npy"

(times, breakdown_signals) = comsol.load_data(data_dir, comsol_file, dt=2.5e-6)

signals = breakdown_signals[29]
lag_matrix = np.zeros((len(signals), len(signals), len(signals[0])*2-1))
for i,signal_i in enumerate(signals):
  for j,signal_j in enumerate(signals[i+1:]):
    lag_matrix[i, j+i+1] = sig.correlate(signal_i, signal_j)
    lag_matrix[j+i+1, i] = lag_matrix[i, j+i+1]
plt.figure()
plt.plot(lag_matrix[0,2])
"""
"""
point_index = 68
print "Actual Location:",actual_locations[point_index]
localizer = Localizer12()
print "Predicted Location:",localizer.localize_spark(times, breakdown_signals[point_index])
#plt.figure()
#plot.plot_signals(times*1e6, breakdown_signals[point_index])
"""

"""
predictions_files = ["spark_locations_12.npy",
                     "spark_locations_12_grid.npy",
                     "spark_locations_12_mic100.npy",
                     "spark_locations_12_r3cm.npy"]
actuals_files = ["actual_locations.npy",
                 "actual_locations_r3cm.npy"]
spark_locations = np.load("".join((data_dir, predictions_files[0])))
actual_locations = np.load("".join((data_dir, actuals_files[0])))

residuals = np.sqrt(np.sum((actual_locations - spark_locations)**2, axis=1))
#chi_squared = np.sqrt(np.sum(errors**2, axis=1))
avg_residual = np.sum(residuals) / len(residuals)
residual_std_dev = np.std(residuals)
#print np.array(sorted(np.array(zip(np.arange(len(residuals)), residuals, np.sqrt(actual_locations[:,0]**2+actual_locations[:,1]**2))), key=lambda row:row[1]))
#print np.max(np.array(zip(np.arange(len(errors)), errors)), axis=0)
print "Average Residual:", avg_residual
print "Residual Stdandard Deviation:", residual_std_dev


### residual histograms 0 <= r <= 14.22 ###

plt.figure(figsize=(18, 6))

plt.subplot(131)
plt.ylabel("Number of Predictions")
plt.xlabel("Residual (cm)")
plt.text(2.5, 32, r'All 100 Predictions', size=24)
plt.hist(residuals, 6, range=(0,12))

plt.subplot(132)
plt.xlabel("Residual (cm)")
plt.text(0.3, 11, r'radius < 3 cm', size=24)
r_sorted_residuals = np.array(sorted(np.array(zip(np.arange(len(residuals)), residuals, np.sqrt(actual_locations[:,0]**2+actual_locations[:,1]**2))), key=lambda row:row[2]))
cut_radius = 3.1 # cm
cut_index = bi.bisect_right(r_sorted_residuals[:,2], cut_radius)
plt.hist(r_sorted_residuals[:cut_index,1], 5, range=(0,1.2), normed=False)
"""
"""
plt.subplot(133)
plt.xlabel("Residual (cm)")
plt.text(3.5, 16.5, r'radius > 3.1 cm', size=24)
plt.hist(r_sorted_residuals[cut_index:,1], 12, range=(0,12), normed=False)
plt.tight_layout()
"""
"""
actual_radii = np.sqrt(actual_locations[:,0]**2+actual_locations[:,1]**2)
diffs = np.abs(actual_locations - spark_locations)
#print np.array(sorted(np.array(zip(np.arange(len(residuals)), residuals, diffs[:,0], diffs[:,1], actual_radii)), key=lambda row:row[4]))
# 78, 70
# 18, 53


spark_locations = np.load("".join((data_dir, predictions_files[3])))
actual_locations = np.load("".join((data_dir, actuals_files[1])))

residuals = np.sqrt(np.sum((actual_locations - spark_locations)**2, axis=1))
#chi_squared = np.sqrt(np.sum(errors**2, axis=1))
avg_residual = np.sum(residuals) / len(residuals)
residual_std_dev = np.std(residuals)
#print np.array(sorted(np.array(zip(np.arange(len(residuals)), residuals, np.sqrt(actual_locations[:,0]**2+actual_locations[:,1]**2))), key=lambda row:row[1]))
#print np.max(np.array(zip(np.arange(len(errors)), errors)), axis=0)
print "Average Residual:", avg_residual
print "Residual Stdandard Deviation:", residual_std_dev
"""
"""
plt.figure(figsize=(18 , 6))
plt.subplot(121)
plot.plot_signals(times*1e6, breakdown_signals[18]) #78
print "Actual Location:", actual_locations[18]
print "Predicted Location:", spark_locations[18]
localizer = Localizer12(v_s=3.11e5, v_p=3.11e5)
localizer.localize_spark(times, breakdown_signals[18])
plt.subplot(122)
plot.plot_signals(times*1e6, breakdown_signals[53]) #78
print "Actual Location:", actual_locations[53]
print "Predicted Location:", spark_locations[53]
localizer.localize_spark(times, breakdown_signals[53])
"""

### residual histograms r <= 3 cm ###
#plt.figure(figsize=(12, 6))
"""
plt.figure(figsize=(18 , 6))
plt.subplot(131)
plt.ylabel("Number of Predictions")
"""
"""
plt.subplot(133)
plt.xlabel("Residual (cm)")
plt.text(2.5, 82, r'Repeat 100 w/', size=24)
plt.text(2.5, 74, r'Radius < 3 cm', size=24)
plt.hist(residuals, 8, range=(0,8))
"""
"""
plt.subplot(132)
plt.xlabel("Residual (cm)")
plt.text(2, 40, r'radius < 2 cm', size=24)
r_sorted_residuals = np.array(sorted(np.array(zip(np.arange(len(residuals)), residuals, actual_radii)), key=lambda row:row[2]))
cut_radius = 2 # cm
cut_index = bi.bisect_right(r_sorted_residuals[:,2], cut_radius)
plt.hist(r_sorted_residuals[:cut_index,1], 8, range=(0,8), normed=False)

plt.subplot(133)
plt.xlabel("Residual (cm)")
plt.text(2, 23.5, r'radius < 1 cm', size=24)
r_sorted_residuals = np.array(sorted(np.array(zip(np.arange(len(residuals)), residuals, actual_radii)), key=lambda row:row[2]))
cut_radius = 1 # cm
cut_index = bi.bisect_right(r_sorted_residuals[:,2], cut_radius)
plt.hist(r_sorted_residuals[:cut_index,1], 8, range=(0,8), normed=False)
"""


"""
mic_coordinates = np.array(zip([5, -5, -5, 5], [6, 6, -6, -6]))
K=2
(cluster_assignments, cluster_averages, objective) \
  = clust.kmeans_signal_cluster(breakdown_signals[83,:100], K=K)
max_size = 0
max_index = 0
for i in range(K):
  cluster_size = len(np.argwhere(cluster_assignments==i).flatten())
  if cluster_size > max_size:
    max_size = cluster_size
    max_index = i
cluster_member_indicies = np.argwhere(cluster_assignments==max_index).flatten()
cluster_members = breakdown_signals[83,cluster_member_indicies]
plt.figure(figsize=(18 , 6))
plt.subplot(211)
plot.plot_signals(times*1e6, cluster_members)
print "Actual Location:",actual_locations[83]
print mic_coordinates[cluster_member_indicies]
localizer = Localizer12(coords=mic_coordinates[cluster_member_indicies])
print "Predicted Location:",localizer.localize_spark(times, cluster_members)
"""
"""
print cluster_assignments, objective
plt.figure(figsize=(18 , 6))
plt.subplot(231)
plot.plot_signals(times*1e6, breakdown_signals[83,np.argwhere(cluster_assignments==0).flatten()]) #78
plt.subplot(232)
plot.plot_signals(times*1e6, breakdown_signals[83,np.argwhere(cluster_assignments==1).flatten()]) #78
plt.subplot(233)
plot.plot_signals(times*1e6, breakdown_signals[83,np.argwhere(cluster_assignments==2).flatten()]) #78
"""
"""
(cluster_assignments, cluster_averages, objective) \
  = clust.kmeans_signal_cluster(breakdown_signals[42,:100], K=K)
max_size = 0
max_index = 0
for i in range(K):
  cluster_size = len(np.argwhere(cluster_assignments==i).flatten())
  print cluster_size
  if cluster_size > max_size:
    max_size = cluster_size
    max_index = i
cluster_member_indicies = np.argwhere(cluster_assignments==max_index).flatten()
cluster_members = breakdown_signals[42,cluster_member_indicies]
plt.subplot(212)
plot.plot_signals(times*1e6, cluster_members)
print "Actual Location:",actual_locations[42]
print mic_coordinates[cluster_member_indicies]
localizer = Localizer12(coords=mic_coordinates[cluster_member_indicies])
print "Predicted Location:",localizer.localize_spark(times, cluster_members)
"""
"""
print cluster_assignments, objective
plt.subplot(234)
plot.plot_signals(times*1e6, breakdown_signals[42,np.argwhere(cluster_assignments==0).flatten()]) #70
plt.subplot(235)
plot.plot_signals(times*1e6, breakdown_signals[42,np.argwhere(cluster_assignments==1).flatten()]) #70
plt.subplot(236)
plot.plot_signals(times*1e6, breakdown_signals[42,np.argwhere(cluster_assignments==2).flatten()]) #70
"""
"""
 # Similar radius, drastically different residuals
 [  7.80000000e+01   1.15218321e+00   7.75202299e-01   8.52401052e-01   1.27407700e+01]
 [  7.00000000e+01   6.14491577e+00   4.01134892e+00   4.65500479e+00   1.29513176e+01]
"""

#plt.tight_layout()
#plt.show()

