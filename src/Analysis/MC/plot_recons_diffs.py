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

predicted_locations1 = np.load(''.join((data_dir, 'predicted_locations_400kHz_10cm_1.3e5cm_s.npy')))
predicted_locations2 = np.load(''.join((data_dir, 'predicted_locations_400kHz_10cm_2.0e5cm_s.npy')))
actual_locations = np.load(''.join((data_dir, 'actual_locations_10cm.npy')))
actual_radii = np.sqrt(np.sum(actual_locations**2, axis=1))

diffs = np.sum(np.absolute(predicted_locations1-predicted_locations2), axis=1)

y_intercept,slope = curve.fit_polynomial(actual_radii, diffs, order=1)
radii = np.linspace(0, 10, 100)
fit_points = radii*slope + y_intercept

fig = plt.figure(figsize=(7,6))
plt.scatter(actual_radii, diffs)

plt.xlabel('Radius (cm)')
plt.ylabel('Prediction Delta (cm)')
plt.plot(radii, fit_points, 'r')

plt.tight_layout()
