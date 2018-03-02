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

plt.figure(figsize=(12,9))

lv_directory = "C:\\Users\\plane\\Desktop\\Data\\HPRF\\HPRF 20120605\sparks_29p5MV-M_15Hz\\"
lv_file = "spark_66_68.npz"
(times, raw_signals) = labview.load_data(lv_directory, lv_file, t0=221.324e-3, t1=222.524e-3,
                                         channels=[0,1,2,6])
dt = 2.0e-6
flip_time = 310.e-6
flip_offset = int(round(flip_time / dt))
raw_signals[2,flip_offset:] = -raw_signals[2,flip_offset:]
plt.subplot(211)
plt.ylim((-1.2,1.2))
plot.plot_signals(times*1e6, raw_signals, norm=True)  # 22us wavefront delay

data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\HPRF\\"
if platform.system() == 'Linux':
    data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/HPRF/"
"""
comsol_file = "hpc_wall_shock.npy"
(times, breakdown_signals) = comsol.load_data(data_dir, comsol_file, dt=2.0e-6)
dt = times[1]
plt.subplot(212)
plt.ylim((-1.2,1.2))
plt.ylabel('Normalized Amplitude')
plot.plot_signals(times*1e6, breakdown_signals[0], norm=True)  # 22us wavefront delay
"""

plt.tight_layout()