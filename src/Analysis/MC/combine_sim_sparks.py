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

data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\"
if platform.system() == 'Linux':
  data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/MC/"

signals_list = []
"""
filenames = ["random100_400kHz_inner.npy", "random100_400kHz_mid_inner.npy",
             "random100_400kHz_mid_outer.npy", "random100_400kHz_outer.npy"]
dt = 2.5e-6
"""
filenames = ["random100_2MHz_inner.npy", "random100_2MHz_mid_inner.npy",
             "random100_2MHz_mid_outer.npy", "random100_2MHz_outer.npy"]
dt = 0.5e-6

signals_list = np.array([comsol.load_data(data_dir, filename, dt=dt) for filename in filenames])
times = signals_list[0,0]
signals_list = signals_list[:,1]
combined_signals = np.vstack(signals_list)

#filename = "random100_400kHz.npy"
filename = "random100_2MHz.npy"
np.save(filename, combined_signals)
