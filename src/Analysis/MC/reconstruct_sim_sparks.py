# -*- coding: utf-8 -*-
"""
Created on Fri Feb 19 16:28:19 2016

@author: plane
"""
#%load_ext autoreload
#%autoreload 2

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
import Queue as queue
import pp

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

def load_signals(data_dir, data_file, dt):
  (times, signal_sets) = comsol.load_data(data_dir, data_file, dt=dt)
  #return [mc.condition_signals(times, signals, window_width=70e-6) for signals in signal_sets]
  return [(times, signals) for signals in signal_sets]

def localize_parallel(data_dir, data_file, predictions_file, v1, v2, grid_size):
  localizer_server = pp.Server()
  signal_sets = load_signals(data_dir, data_file, dt)
  spark_count = len(signal_sets)
  print 'Processing', spark_count, 'Sparks...'

  finished_count = 0
  localizer_jobs = []
  prog.update_progress(0.0)
  start_time = time.clock()
  localizer_jobs = [localizer_server.submit(
    mc.localize_spark_pp, (times, signals, v1, v2, grid_size, False),
    (mc.order_by_time, mc.octant_trilateration, mc.OctantDecisionTree,
     mc.signals_to_cwts, mc.wavelet_conditioning),
    ('math', 'numpy', 'pgl.mc', 'pgl.signal', 'scipy.signal'))
    for times,signals in signal_sets]
  spark_locations = []
  for index,localizer_job in enumerate(localizer_jobs):
    spark_locations.append(localizer_job())
    prog.update_progress(float(index+1)/spark_count)
  print
  stop_time = time.clock()
  elapsed_time = stop_time - start_time
  elapsed_minutes = int(elapsed_time / 60)
  elapsed_seconds = int(round((elapsed_time - float(elapsed_minutes))*60))
  print "Elapsed Time: %d:%d" % (elapsed_minutes, elapsed_seconds)

  localizer_server.destroy()

  np.save("".join((data_dir, predictions_file)), spark_locations)

if __name__ == '__main__':
  data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\"
  if platform.system() == 'Linux':
    data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/MC/"

  #predictions_file = "predicted_locations_400kHz_10cm.npy"
  #predictions_file = "predicted_locations_400kHz_best_mean.npy"
  #predictions_file = "predicted_locations_400kHz_best_stddev.npy"
  #predictions_file = "predicted_locations_400kHz_best_inner.npy"
  predictions_file = "predicted_locations_400kHz.npy"

  #comsol_file = "test_0_10x4_400kHz.npy"
  comsol_file = "random100_400kHz.npy"
  #comsol_file = "random100_2MHz.npy"
  #comsol_file = "random100_400kHz_10cm.npy"
  #comsol_file = "real_six.npy"
  dt = 2.5e-6
  #dt = 0.5e-6
  #(times, breakdown_signals) = comsol.load_data(data_dir, comsol_file, dt=dt)
  """
  (times, signal_sets) = comsol.load_data(data_dir, comsol_file, dt=dt)
  timed_signal_sets = [mc.condition_signals(times, signals, window_width=70e-6)
                        for signals in signal_sets]
  set_index = 90
  plt.subplot(211)
  plot.plot_signals(times*1e6, signal_sets[set_index])
  plt.subplot(212)
  plot.plot_signals(timed_signal_sets[set_index][0]*1e6, timed_signal_sets[set_index][1])
  sys.exit()
  """
  #localizer = Localizer15(v_s=2.325e5, v_p=10.0e5, grid_size=100, dt=dt)
  #localizer = Localizer15(v_s=2.325e5, v_p=4.76e5, grid_size=100, dt=dt)
  ##localizer = Localizer12(v_s=2.325e5, v_p=10.0e5, grid_size=100, dt=dt) # Best for 400kHz
  ##localizer = Localizer12(v_s=2.325e5, v_p=4.76e5, grid_size=100, dt=dt) # Best for 2MHz
  #localizer = Localizer12(v_s=1.5e5, v_p=1.5e5, grid_size=100, dt=dt)
  #localizer = Localizer12(v_s=2.325e5, v_p=4.76e5, grid_size=100, dt=dt)
  # Localize all of the sparks in breakdown_signals
  #localize(breakdown_signals, localizer, data_dir, predictions_file)
  #localize(data_dir, comsol_file, localizer, predictions_file)
  # Best mean residual for 400kHz data
  #localize_parallel(data_dir, comsol_file, predictions_file, 2.39655172e5, 1.20689655e5, 30)
  # Best residual stddev for 400kHz data
  #localize_parallel(data_dir, comsol_file, predictions_file, 1.4137931e5, 2.13793103e5, 30)
  # Best inner recon for 400kHz data
  #localize_parallel(data_dir, comsol_file, predictions_file, 1.0e5, 1.46551724e5, 30)
  #localize_parallel(data_dir, comsol_file, predictions_file, 0.708e5, 1.3e5, 40)
  #localize_parallel(data_dir, comsol_file, predictions_file, 1.0, 1.29e5, 40)
  #localize_parallel(data_dir, comsol_file, predictions_file, 1.75e5, 1.37e5, 60)
  #localize_parallel(data_dir, comsol_file, predictions_file, 1.96551724e5, 1.55172414e5, 50)
  #localize_parallel(data_dir, comsol_file, predictions_file, 2.37931034e5, 1.96551724e5, 50)
  #localize_parallel(data_dir, comsol_file, predictions_file, 3.48275862e5, 2.10344828e5, 50)
  #localize_parallel(data_dir, comsol_file, predictions_file, 1.0e5, 2.325e5, 50)
  localize_parallel(data_dir, comsol_file, predictions_file, 4.03448276e5, 3.06896552e5, 50)
  #localize_parallel(data_dir, comsol_file, predictions_file, 2.93103448e5, 2.10344828e5, 50)
