# -*- coding: utf-8 -*-
"""
Created on Wed Feb 24 08:12:37 2016

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

lv_directory = "C:\\Users\\plane\\Desktop\\Data\\HPRF\\HPRF 20120605\sparks_29p5MV-M_15Hz\\"
#lv_file = "reduced_53_55.npz"
lv_file = "spark_53_55.npz"

#(times, signals) = labview.load_data(lv_directory, lv_file, t0=286.4e-3, t1=286.6e-3,
(times, signals) = labview.load_data(lv_directory, lv_file, t0=286.377e-3, t1=286.8e-3,
                                     channels=[0,1,2,3,4,5,6])
signal = signals[5] / np.max(signals[5])
damped_signal = [signal[x]*(math.exp(1-times[x]/2.0e-5)) for x in range(len(signal))]
damped_signal = damped_signal / np.max(damped_signal)
fig = plt.figure(figsize=(12,7))
fig.text(0.04, 0.52, 'Normalized Amplitude', ha='center', va='center', rotation='vertical')
plt.subplot(211)
plt.ylim((-1.2, 1.2))
plt.plot(times*1e6, signal)
plt.subplot(212)
plt.xlabel("Time (us)")
plt.ylim((-1.2, 1.2))
plt.plot(times*1e6, damped_signal)
plt.tight_layout()