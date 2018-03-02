# -*- coding: utf-8 -*-
"""
Created on Wed Feb 24 12:57:48 2016

@author: plane
"""
import math
import subprocess as proc
import os
import os.path as path
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
import pgl.hpc as hpc

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

def plot_source_locations(predicted_xs, predicted_ys, actual_xs, actual_ys, error_radius=1.1, cavity_radius=14.22):
    # center marks
    source_xs = np.hstack((predicted_xs, actual_xs))
    source_ys = np.hstack((predicted_ys, actual_ys))

    fig = plt.gcf()
    ax = fig.gca()

    # cavity
    """
    origin_x = np.zeros(1)
    origin_y = np.zeros(1)
    cavity_radius = 14.9 # cm
    cavity=plt.Circle((0,0),cavity_radius,color='k',alpha=0.1)
    ax.add_artist(cavity)
    """

    # error circles
    patches = []
    for coordinate in zip(predicted_xs,predicted_ys):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, color='k', alpha=0.1)
    collection.set_array(np.repeat(0.5, len(predicted_xs)))
    ax.add_collection(collection)

    # 1st Quadrant Microphone
    plt.plot([4.5, 5.5], [6, 6], color='k', linestyle='-', linewidth=2)
    plt.plot([5, 5], [6.5, 5.5], color='k', linestyle='-', linewidth=2)

    # 4th Quadrant Microphone
    plt.plot([4.5, 5.5], [-6, -6], color='k', linestyle='-', linewidth=2)
    plt.plot([5, 5], [-6.5, -5.5], color='k', linestyle='-', linewidth=2)


    # damage marks
    plt.scatter(actual_xs, actual_ys, s=10, c='k', cmap=cm.Set1, alpha=1.0)

    # damage stddev
    damage_stddev = 2.2
    stddev_circle=plt.Circle((1.45392578, -1.7784455),damage_stddev,color='r',alpha=0.1)
    ax.add_artist(stddev_circle)

    mark_radii = np.repeat(10, len(predicted_xs))
    mark_colors = np.repeat(5, len(predicted_xs))
    plt.scatter(predicted_xs, predicted_ys, s=10, c='w', cmap=cm.Set1, alpha=1.0)

    plt.xlim((-4, 7))
    plt.ylim((-7, 4))

directory = 'C:/Users/plane/Desktop/Data/MC/'
US_coordinate_file = ''.join((directory, 'US_B=3T_coord_in_cm_aligned.npy'))
DS_coordinate_file = ''.join((directory, 'DS_B=3T_coord_in_cm_aligned.npy'))
US_coordinates = np.load(US_coordinate_file)
DS_coordinates = np.load(DS_coordinate_file)


lv_dir = "C:\\Users\\plane\\Desktop\\Data\\MC\\2015-12-21\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Desktop/Data/MC/2015-12-21/"
lv_files = ["reduced_data_2015-12-21@11_05_57.472.npz",
            "reduced_data_2015-12-21@11_05_57.532.npz",
            "reduced_data_2015-12-21@11_22_07.377.npz",
            "reduced_data_2015-12-21@11_33_25.640.npz",
            "reduced_data_2015-12-21@11_49_41.805.npz",
            "reduced_data_2015-12-21@12_12_39.628.npz"]
"""
lv_dir = "C:\\Users\\plane\\Desktop\\Data\\MC\\2016-01-14\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Desktop/Data/MC/2016-01-14/"
lv_files = ["reduced_data_2016-01-14@03_19_08.284.npz",
            "reduced_data_2016-01-14@04_40_21.324.npz",
            "reduced_data_2016-01-14@17_04_43.028.npz",
            "reduced_data_2016-01-14@18_02_08.131.npz",
            "reduced_data_2016-01-14@18_52_48.943.npz",
            "reduced_data_2016-01-14@22_54_00.938.npz",
            "reduced_data_2016-01-14@22_54_00.998.npz"]
"""
"""
signal_sets = np.array([labview.load_data(lv_dir, lv_file, t0=40e-6, t1=90e-6, channels=[4,5,6,7])[1]
                          for lv_file in lv_files])
np.save('C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\real_six_cropped.npy', signal_sets)
"""

fig = plt.figure(figsize=(20.0, 8.0))
fig.text(0.5, 0.05, r'Time ($ms$)', ha='center', va='center', size=26)
fig.text(0.02, 0.5, r'Amplitude ($V$)', ha='center', va='center', size=26, rotation='vertical')
"""
for index,lv_file in enumerate(lv_files):
    (times, breakdown_signals) = labview.load_data(lv_dir, lv_file, t0=40e-6, t1=100e-6, channels=[4,5,6,7])
    plt.subplot(231+index)
    upsampled_signals = np.zeros((np.shape(breakdown_signals)[0], np.shape(breakdown_signals)[1]*2))
    upsampled_times = np.linspace(0, times[-1], np.shape(upsampled_signals)[1])
    for i,signal in enumerate(breakdown_signals):
        upsampled_signals[i] = np.roll(sig.resample(signal, len(signal)*2), -i)
    plot.plot_signals(upsampled_times*1e6, upsampled_signals, tlim=50)
plt.show()

fig = plt.figure(figsize=(15,10))
location_predictions = np.zeros((len(lv_files), 2))
"""
for index,lv_file in enumerate(lv_files):
  #(times, breakdown_signals) = labview.load_data(lv_dir, lv_file, t0=40e-6, t1=90e-6, channels=[4,5,6,7])
  #(times, breakdown_signals) = labview.load_data(lv_dir, lv_file, t0=40e-6, t1=90e-6, channels=[4,5,6,7])
  (times, breakdown_signals) = labview.load_data(lv_dir, lv_file, t0=40e-6, t1=130e-6, channels=[4,5,6,7])
  #(times, breakdown_signals) = labview.load_data(lv_dir, lv_file, t0=20e-6, t1=130e-6, channels=[0,1,2,3])
  #(times, breakdown_signals) = mc.condition_signals(times, breakdown_signals, window_width=200e-6)
  plt.subplot(231+index)
  """
  dt = times[1] - times[0]
  signal = breakdown_signals[3]
  frequency_spectrum = np.fft.fft(signal)[:int(round(signal.size/2))]
  spectrum_magnitudes = np.sqrt(  np.real(frequency_spectrum)**2
                                + np.imag(frequency_spectrum)**2)
  frequencies = np.fft.fftfreq(frequency_spectrum.size*2, d=dt)\
                  [:int(round(signal.size/2))]
  #plt.plot(frequencies, spectrum_magnitudes)
  f_peak = frequencies[np.argmax(spectrum_magnitudes[1:])]
  print f_peak
  fc = psig.ricker_center_freq(dt)
  target_scale = fc / f_peak
  cwt3 = sig.cwt(signal, sig.ricker, [target_scale,])[0]
  #plt.plot(times*1e6, cwt3)
  #print np.max(cwt3)
  """
  """
  freqs, fmags, fphases = psig.spectra(times, breakdown_signals)
  f_peaks = freqs[np.argmax(fmags[:,1:], axis=1)]
  #print f_peaks
  dt = times[1] - times[0]
  fc = psig.ricker_center_freq(dt)
  target_scales = fc / f_peaks
  cwts = np.array(
    [sig.cwt(breakdown_signals[i], sig.ricker, [target_scales[i],])[0]
      for i in range(len(breakdown_signals))])
  """
  """
  first_derivs = np.vstack([np.gradient(ys) for ys in breakdown_signals])
  signs = np.sign(first_derivs)
  signs[signs==0] = -1
  second_derivs = np.vstack([np.gradient(ys) for ys in first_derivs])
  first_zero_crossings = np.where(np.logical_and(np.diff(signs), (second_derivs < 0)[:,:-1]))
  peak_coords = np.unravel_index(first_zero_crossings, breakdown_signals[0].shape)[0]
  #print peak_coords
  wavelets = np.vstack([psig.translated_ricker_wavelet(np.arange(len(times)), 1.0, t)
    for t in peak_coords[1]])
  wavelet_signals = np.zeros(breakdown_signals.shape)
  for signal_index in range(len(breakdown_signals)):
    #print peak_coords[0] == signal_index
    wavelet_signals[signal_index] = np.sum(wavelets[peak_coords[0] == signal_index], axis=0)
  #print wavelet_signals
  """
  #cwts = mc.signals_to_cwts(times, breakdown_signals)
  """
  freqs, fmags, fphases = psig.spectra(times, breakdown_signals)
  f_peaks = freqs[np.argmax(fmags[:,1:], axis=1)] - 1e4
  print 'Peak FFT Frequencies:', f_peaks
  dt = times[1] - times[0]
  fc = psig.ricker_center_freq(dt)
  target_scales = fc / f_peaks
  cwts = np.array(
    #[sig.cwt(breakdown_signals[i], sig.ricker, [target_scales[i],])[0]
    [sig.cwt(breakdown_signals[i], sig.ricker, [fc / 2e4])[0]
      for i in range(len(breakdown_signals))])
  plot.plot_signals(times, np.abs(cwts))
  """


  """
  upsampled_signals = np.zeros((np.shape(breakdown_signals)[0], np.shape(breakdown_signals)[1]*2))
  upsampled_times = np.linspace(0, times[-1], np.shape(breakdown_signals)[1]*2)
  for i,signal in enumerate(breakdown_signals):
      upsampled_signals[i] = np.roll(sig.resample(signal, len(signal)*2), -i)
  times = upsampled_times
  breakdown_signals = upsampled_signals
  """
  """
  breakdown_signals = mc.signals_to_cwts(times, breakdown_signals)
  for signal in breakdown_signals:
    grad = np.gradient(signal)
    signs = np.sign(grad)
    signs[signs==0] = -1
    peak_indicies = np.where(np.diff(signs) < 0)
    peak_times = times[peak_indicies]
    peak_amplitudes = signal[peak_indicies]
    max_peak_amplitude = peak_amplitudes.max()
    constrained_peak_indicies = peak_amplitudes > (max_peak_amplitude/2.0)
    print 'Wavefront Time:', peak_times[constrained_peak_indicies][0]
  print
  """
  """
  dt = times[1] - times[0]
  fc = psig.ricker_center_freq(dt)
  scales = np.linspace(1e3, 50e3, num=50) * (1.0/fc)
  for signal in breakdown_signals:
    cwtmatr = sig.cwt(signal, sig.ricker, scales)
    #plt.imshow(cwtmatr[::-1,:], extent=[times[0]*1e6, times[-1]*1e6, 1, 50])
    max_indicies = np.unravel_index(cwtmatr.argmax(), cwtmatr.shape)
    print 'Peak Frequency:', scales[max_indicies[0]] * fc
    print 'Peak Time:', times[max_indicies[1]]
    cwt = cwtmatr[max_indicies[0]]
    #plt.plot(times, signal)
    #plt.plot(times, np.sum(cwtmatr, axis=0))
  """
  plot.plot_signals(times*1e6, breakdown_signals)
  #plot.plot_signals(times, wavelet_signals)
  #plot.plot_signals(times, derivatives)


  #new_times, conditioned_signals = mc.condition_signals(times, breakdown_signals)
  #plot.plot_signals(times*1e6, breakdown_signals)
  #plot.plot_signals(upsampled_times*1e6, upsampled_signals)
  #spark_coordinates = np.array(mc.localize_spark(times, breakdown_signals, live=True))
  #plot.plot_signals(times*1e6, breakdown_signals, norm=True)

  location_predictions[index] = np.array(\
    #mc.localize_spark_pp(upsampled_times, upsampled_signals, 1.0, 1.29e5, 40, False))
    #mc.localize_spark_pp(upsampled_times, upsampled_signals, 1.3e5, 1.3e5, 40, False))
    #mc.localize_spark_pp(times, breakdown_signals, 1.0, 1.29e5, 50, False))
    #mc.localize_spark_pp(times, cwts, 1.3e5, 1.3e5, 100, False))
    #mc.localize_spark_pp(times, wavelet_signals, 1.3e5, 1.3e5, 60, False))
    #mc.localize_spark_pp(times, breakdown_signals, 1.37e5, 1.26e5, 50, False))
    #mc.localize_spark_pp(times, breakdown_signals, 1.3e5, 1.3e5, 50, False))
    #mc.localize_spark_pp(times, breakdown_signals, 2.325e5, 2.325e5, 50, False))
    #mc.localize_spark_pp(times, breakdown_signals, 2.325e5, 1.9375e5, 50, True))
    #mc.localize_spark_pp(times, breakdown_signals, 4.03448276e5, 3.06896552e5, 50, True))
    mc.localize_spark_pp(times, breakdown_signals, 4.034e5, 3.069e5, 50, True))
    #mc.localize_spark_pp(times, breakdown_signals, 2.93103448e5, 2.10344828e5, 50, True))
  """
  location_predictions[index] = np.array(\
    mc.localize_spark_pp(times, cwts, 1.0, 1.29e5, 50, False))
  """
  mic_coordinates = np.array(zip([5, -5, -5, 5], [6, 6, -6, -6]))
  distances = np.sqrt(np.sum((mic_coordinates-location_predictions[index])**2, axis=1))
  print 'Source-Mic Times:', distances/2.3e5
plt.tight_layout()

print location_predictions

damage_centroid = np.mean(DS_coordinates, axis=0)
damage_stddev = math.sqrt(np.sum(np.std(DS_coordinates, axis=0)**2))
prediction_centroid = np.mean(location_predictions, axis=0)
prediction_stddev = math.sqrt(np.sum(np.std(location_predictions, axis=0)**2))
print 'Damage Centroid:({:1.1f}, {:1.1f})'.format(
  damage_centroid[0], damage_centroid[1])
print 'Damage Std. Dev.:{:1.1f}'.format(damage_stddev)
print 'Prediction Centroid:({:1.1f}, {:1.1f})'.format(
  prediction_centroid[0], prediction_centroid[1])
print 'Prediction Std. Dev.:{:1.1f}'.format(prediction_stddev)

fig = plt.figure(figsize=(6,6))
#fig.text(0.5, 0.02, r'x (cm)', ha='center', va='center', size=26)
#fig.text(0.02, 0.52, 'y (cm)', ha='center', va='center', rotation='vertical', fontsize=26)
plt.xlabel('x (cm)')
plt.ylabel('y (cm)')

plot_source_locations(location_predictions[:,0], location_predictions[:,1],
                      DS_coordinates[:,0], DS_coordinates[:,1])
plt.tight_layout()
