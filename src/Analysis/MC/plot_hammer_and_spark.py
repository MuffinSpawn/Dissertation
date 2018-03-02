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
    mpl.rcParams['ytick.labelsize'] = 20
    mpl.rcParams['xtick.labelsize'] = 20
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

"""
lv_dir = "C:\\Users\\plane\\Desktop\\Data\\HPRF\\HPRF 20120614\\sparks_36MV-M_15Hz\\"
lv_file = "HPRF_dump.npz"

hammers = np.array((
  labview.load_data(lv_dir, lv_file, t0=29.342e-3, t1=29.642e-3, channels=[0,1,2,3,5])[1],
  labview.load_data(lv_dir, lv_file, t0=96.062e-3, t1=96.362e-3, channels=[0,1,2,3,5])[1],
  labview.load_data(lv_dir, lv_file, t0=162.674e-3, t1=162.974e-3, channels=[0,1,2,3,5])[1],
  labview.load_data(lv_dir, lv_file, t0=229.386e-3, t1=229.686e-3, channels=[0,1,2,3,5])[1]))
avg_hammer = np.average(hammers, axis=0)
times,spark = labview.load_data(lv_dir, lv_file, t0=296.092e-3, t1=296.392e-3, channels=[0,1,2,3,5])
"""

lv_dir = "C:\\Users\\plane\\Desktop\\Data\\MC\\2016-01-13\\"
lv_files = ["raw_data_2016-01-13@20_47_30.401.npz",
            "raw_data_2016-01-13@21_02_29.931.npz",
            "raw_data_2016-01-13@21_17_29.865.npz",
            "raw_data_2016-01-13@21_32_29.923.npz",
            "raw_data_2016-01-13@21_47_29.931.npz"]
hammers = np.array([labview.load_data(lv_dir, lv_file, t0=30e-6, t1=430e-6, channels=[6,])[1]
                      for lv_file in lv_files])
avg_hammer = np.average(hammers, axis=0)
lv_file = "reduced_data_2016-01-13@18_43_18.362.npz"
times,spark= labview.load_data(lv_dir, lv_file, t0=30e-6, t1=430e-6, channels=[6,])
times = times*1e6

fig = plt.figure(figsize=(16.0, 8.0))
#fig.text(0.5, 0.05, r'Time ($ms$)', ha='center', va='center', size=26)
fig.text(0.02, 0.5, r'Amplitude ($V$)', ha='center', va='center', size=26, rotation='vertical')
plt.subplot(311)
plt.ylim(-1.8,1.8)
plot.plot_signals(times, avg_hammer)

plt.subplot(312)
plt.ylim(-1.8,1.8)
plot.plot_signals(times, spark+avg_hammer)

plt.subplot(313)
plt.xlabel('Time ($\mu s$)')
plt.ylim(-1.8,1.8)
plot.plot_signals(times, spark)
plt.tight_layout()
