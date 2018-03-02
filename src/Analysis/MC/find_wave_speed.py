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
import Queue as queue
import pp
import mpl_toolkits.axes_grid1 as pltool
import pgl.comsol as comsol
import pgl.curve as curve
import pgl.labview as labview
import pgl.plot as plot
import pgl.signal as psig
import pgl.tektronix as tektronix
import pgl.mc as mc
import pgl.progress as prog
import pgl.cluster as clust

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
  #return [mc.condition_signals(times, signals, window_width=150e-6) for signals in signal_sets]
  return [(times, signals) for signals in signal_sets]

def localize(signal_sets, server, v1, v2, grid_size=40):
  print 'v1=', v1, 'v2=', v2
  spark_count = len(signal_sets)

  localizer_jobs = []
  prog.update_progress(0.0)
  localizer_jobs = [server.submit(
    mc.localize_spark_pp, (times,signals,v1,v2,grid_size,False),
    (mc.order_by_time, mc.octant_trilateration, mc.OctantDecisionTree, mc.signals_to_cwts),
    ('math', 'numpy', 'pgl.mc', 'pgl.signal', 'scipy.signal'))
    for times,signals in signal_sets]

  predicted_locations = []
  for index,localizer_job in enumerate(localizer_jobs):
    predicted_locations.append(localizer_job())
    prog.update_progress(float(index+1)/spark_count)

  return predicted_locations

def sweep_velocity(data_dir, data_file, extent):
  dt = 2.5e-6

  localizer_server = pp.Server()
  signal_sets = load_signals(data_dir, data_file, dt=dt)
  v1s = np.linspace(extent[0]*1e5, extent[1]*1e5, num=30)
  v2s = np.linspace(extent[2]*1e5, extent[3]*1e5, num=1)
  locations = np.array([[localize(signal_sets, localizer_server, v1, v2, grid_size=50)
                  for v2 in v2s]
                    for v1 in v1s])
  localizer_server.destroy()
  np.save(''.join((data_dir, 'velocity_sweep_400kHz_exploratory.npy')), locations)

if __name__ == '__main__':
  data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\"
  if platform.system() == 'Linux':
    data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/MC/"
  data_file = "random100_400kHz.npy"

  extent = [1.0, 5.0, 1.0, 1.0]
  #extent = [1.0e-5, 2.5, 1.0e-5, 2.5]
  sweep_velocity(data_dir, data_file, extent)

  actual_locations = np.load(''.join((data_dir, 'actual_locations.npy')))
  #actual_locations = np.load(''.join((data_dir, 'actual_locations_10cm.npy')))

  # Axis      Description
  # 0         v1
  # 1         v2
  # 2         spark
  # 3         coordinate (x,y)
  locations = np.load(''.join((data_dir, 'velocity_sweep_400kHz_exploratory.npy')))
  #locations = np.load(''.join((data_dir, 'velocity_sweep_400kHz_10cm.npy')))

  # calculate statistics for each set of sparks
  residuals = np.sqrt(np.sum((locations - actual_locations)**2, axis=3))
  mean_residuals = np.mean(residuals, axis=2)
  print mean_residuals
  residual_stddevs = np.std(residuals, axis=2)

  scales = np.array([extent[1] - extent[0], extent[3] - extent[2]])
  offsets = np.array([extent[0], extent[2]])

  min_indicies = np.unravel_index([np.argmin(mean_residuals)],
                                   np.shape(mean_residuals))
  min_indicies = np.array([min_indicies[0][0], min_indicies[1][0]])
  min_mean_vs = min_indicies * scales/(mean_residuals.shape[0]-1) + offsets
  print 'Min. Mean Residual Velocities:', min_mean_vs
  print 'Min. Mean Residual:', np.min(mean_residuals)

  min_indicies = np.unravel_index([np.argmin(residual_stddevs)],
                                   np.shape(residual_stddevs))
  min_indicies = np.array([min_indicies[0][0], min_indicies[1][0]])
  min_std_vs = min_indicies * scales/(residual_stddevs.shape[0]-1) + offsets
  print 'Min. Residual Std Dev Velocites:', min_std_vs
  print 'Min. Residual Std Dev:', np.min(residual_stddevs)

  metric = np.sqrt(mean_residuals**2 + residual_stddevs**2)
  min_indicies = np.unravel_index([np.argmin(metric)],
                                   np.shape(metric))
  min_indicies = np.array([min_indicies[0][0], min_indicies[1][0]])
  min_metric_vs = min_indicies * scales/(metric.shape[0]-1) + offsets
  print 'Min. Metric Velocities:', min_metric_vs
  print 'Min. Metric:', np.min(metric)

  fig = plt.figure(figsize=(14,7))
  plt.subplot(121)
  plt.title('Mean Residuals')
  plt.xlabel('Velocity 1 (x 1e5 cm/s)')
  plt.ylabel('Velocity 2 (x 1e5 cm/s)')
  im = plt.imshow(mean_residuals[:,::-1].transpose(), extent=extent)
  divider = pltool.make_axes_locatable(fig.axes[0])
  cax = divider.append_axes("right", size="5%", pad=0.05)
  plt.colorbar(im, cax=cax)
  #circle = mpatches.Circle(min_mean_vs, 0.01, ec="none")
  #fig.gca().add_artist(circle)
  #plt.plot(min_mean_vs[0], min_mean_vs[1], 'k*')

  plt.subplot(122)
  plt.title('Residual Std Devs')
  plt.xlabel('Velocity 1 (x 1e5 cm/s)')
  plt.ylabel('Velocity 2 (x 1e5 cm/s)')
  im = plt.imshow(residual_stddevs[:,::-1].transpose(), extent=extent)
  divider = pltool.make_axes_locatable(fig.axes[2])
  cax = divider.append_axes("right", size="5%", pad=0.05)
  plt.colorbar(im, cax=cax)

  plt.tight_layout()
