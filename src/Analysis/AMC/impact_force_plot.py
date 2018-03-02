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

directory = "C:\\Users\\plane\\Desktop\\Data\\Al Cavity\\2015-07-23\\ALL0000\\"
filename = "F0000CH2.CSV"
(xs, ys) = tektronix.load_csv_data(directory, filename)
xs = xs[0]
ys = ys[0]
plt.plot(xs*1e3, ys)

xs = np.linspace(0,4.5e-3,100)
F0 = 1  # N
t0 = 2.3e-3 # s
gamma = 3
_lambda = 1
ys = np.array([F0*math.exp(-gamma/t0*t)*math.sin(2*math.pi*_lambda/t0*t) for t in xs])
plt.xlabel('Time (ms)')
plt.ylabel('Amplitude')
plt.plot(xs*1e3, ys)

plt.tight_layout()