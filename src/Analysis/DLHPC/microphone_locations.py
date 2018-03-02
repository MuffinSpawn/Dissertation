# -*- coding: utf-8 -*-
"""
Created on Wed Feb 24 18:32:40 2016

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

def plot_mic_locations(mic_xs, mic_ys):
    fig = plt.gcf()
    ax = fig.gca()
    mic_radius = 1.5

    patches = []
    for coordinate in zip(mic_xs, mic_ys):
        patches.append(mpatches.Circle(coordinate, mic_radius))
    collection = mcollections.PatchCollection(patches, cmap=cm.brg,
                                              norm=mpl.colors.Normalize(0.,1.),
                                              alpha=0.2)
    collection.set_array(np.array([0.25, 0.5, 0.75]))
    ax.add_collection(collection)


    """
    patches = []
    for coordinate in zip(source_xs_120,source_ys_120):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, cmap=cm.brg, norm=mpl.colors.Normalize(0.,1.), alpha=0.2)
    collection.set_array(np.repeat(1.0, len(source_xs_120)))
    ax.add_collection(collection)
    """

    # cavity
    origin_x = np.zeros(1)
    origin_y = np.zeros(1)
    cavity_radius = 10000
    #plt.scatter([0, 0], [0, 0], s=[90000, 0], c=[3,1], cmap=cm.Set1, alpha=0.1)
    cavity=plt.Circle((0,0),15.24,color='k',alpha=0.1)
    ax.add_artist(cavity)

    plt.xlim((-16, 16))
    plt.ylim((-16, 16))

fig = plt.figure(figsize=(21.0, 7.0))
fig.text(0.51, 0.04, 'x (cm)', ha='center', va='center', fontsize=26)
fig.text(0.02, 0.52, 'y (cm)', ha='center', va='center', rotation='vertical', fontsize=26)
plt.gcf().subplots_adjust(bottom=0.15, left=0.06)

plt.subplot(131)
plot_mic_locations([-10.91, 4.210, 6.696], [1.436, -10.16, 8.727])
plt.subplot(132)
plot_mic_locations([4.210, 6.696, -10.91], [-10.16, 8.727, 1.436])
plt.subplot(133)
plot_mic_locations([-1.436, 10.16, -8.727], [-10.91, 4.210, 6.696])

plt.tight_layout()