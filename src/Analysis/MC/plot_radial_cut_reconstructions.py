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

predicted_locations = np.load(''.join((data_dir, 'predicted_locations_400kHz.npy')))

actual_locations = np.load(''.join((data_dir, 'actual_locations.npy')))
cut_count = 100
cut_radii = np.linspace(1.0, 14.22, num=cut_count)
residual_means = np.empty(cut_count)
residual_stddevs = np.empty(cut_count)
actual_radii = np.sqrt(np.sum(actual_locations**2, axis=1))
for index,cut_radius in enumerate(cut_radii):
  print cut_radius
  cut_indicies = np.where(actual_radii <= cut_radius)
  print cut_indicies
  #print cut_indicies
  cut_actual_locations = actual_locations[cut_indicies]
  cut_predicted_locations = predicted_locations[cut_indicies]
  residuals = np.sqrt(np.sum((cut_predicted_locations - cut_actual_locations)**2, axis=1))
  residual_means[index] = np.mean(residuals)
  residual_stddevs[index] = np.std(residuals)
#print residual_means
#print residual_stddevs

fig = plt.figure(figsize=(7,6))
plt.ylabel('Residual Mean\n and Std. Dev. (cm)')
plt.xlabel('Cut Radius (cm)')
plt.xlim(0,14.22)
plt.plot(cut_radii, residual_means, cut_radii, residual_stddevs)
plt.tight_layout()
