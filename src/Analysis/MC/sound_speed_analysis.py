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
  return [(index, times, signals)
    for index,signals in enumerate(signal_sets)]

if __name__ == '__main__':
  data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\"
  if platform.system() == 'Linux':
    data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/MC/"
  raw_data = np.load(''.join((data_dir, 'sound_speed_10MHz_diagonal_diameter.npy')))
  #data = np.array([page[0] for page in raw_data])
  data = np.array([page[0] for page in raw_data]).transpose()
  times = np.linspace(0, 40, data.shape[1])
  #distances = np.linspace(0,8.0e-2,data.shape[0])
  distances = np.linspace(-15e-2,15.0e-2,data.shape[0])
  peak_amplitudes = np.array([np.max(row) for row in data])
  """
  plt.figure()
  plt.xlabel('Distance from Spark (cm)')
  plt.ylabel('Peak Displacement (um)')
  plt.plot(distances*100, peak_amplitudes*1e6)
  plt.tight_layout()
  """
  #ratios = distances / peak_amplitudes
  #print ratios
  #arc_length = 1.37e-2 # m
  """
  arc_length = 8.0e-2 # m
  ds = arc_length / (data.shape[0]-1)
  r1 = 7.14e-2 # m
  n1 = int(round(r1/ds))
  r2 = 4.67e-2 # m
  n2 = int(round(r2/ds))
  #plt.xlim(20,25)
  #plt.ylim(0,3e-4)
  plt.plot(times, data[n1], times, data[n2])
  """
  plt.figure()
  #plt.xlabel('Time (us)')
  #plt.ylabel('Displacement (um)')
  #plot.plot_signals(times, data*1e6)
  #heatmap,xedges,yedges = np.histogram2d(times, data*1e6, bins=50)
  extent = [times[0], times[-1], distances[0]*100, distances[-1]*100]
  #plt.imshow(data*1e6, extent=extent)
  plt.xlabel('Time (us)')
  plt.ylabel('Distance (cm)')
  plt.imshow(data*1e6, extent=extent, aspect='auto')
  plt.tight_layout()