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

data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\HPRF\\"
if platform.system() == 'Linux':
    data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/HPRF/"

comsol_file = "hpc_wall_shock.npy"
(times, breakdown_signals) = comsol.load_data(data_dir, comsol_file, dt=2.0e-6)
plt.figure(figsize=(12,9))
plt.subplot(211)
plt.ylim((-4,4))
plot.plot_signals(times*1e6, breakdown_signals[0])  # 22us wavefront delay

comsol_file = "hpc_dual_shock.npy"
(times, breakdown_signals) = comsol.load_data(data_dir, comsol_file, dt=2.0e-6)
plt.subplot(212)
plot.plot_signals(times*1e6, breakdown_signals[0])  # 22us wavefront delay


plt.tight_layout()