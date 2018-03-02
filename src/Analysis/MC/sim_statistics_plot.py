# -*- coding: utf-8 -*-
"""
Created on Wed Feb 24 12:57:48 2016

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

def plot_source_locations(predicted_xs, predicted_ys, actual_xs, actual_ys, error_radius=0.9, cavity_radius=14.22):
    # center marks
    #source_xs = np.hstack((predicted_xs, actual_xs))
    #source_ys = np.hstack((predicted_ys, actual_ys))

    mark_radii = np.repeat(10, len(predicted_xs))
    mark_colors = np.repeat(5, len(predicted_xs))
    plt.scatter(predicted_xs, predicted_ys, s=10, c='k', cmap=cm.Set1, alpha=1.0)

    fig = plt.gcf()
    ax = fig.gca()

    # error circles
    """
    patches = []
    for coordinate in zip(predicted_xs,predicted_ys):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, color='k', alpha=0.2)
    collection.set_array(np.repeat(0.5, len(predicted_xs)))
    ax.add_collection(collection)
    """

    # damage marks
    patches = []
    for coordinate in zip(actual_xs,actual_ys):
        patches.append(mpatches.RegularPolygon(coordinate, 5, 0.4))
    collection = mcollections.PatchCollection(patches, color='k', alpha=0.2)
    #collection.set_array(np.repeat(0.5, len(source_xs_0)))
    ax.add_collection(collection)

    # Residual Lines
    for coordinate in zip(actual_xs, actual_ys, predicted_xs, predicted_ys):
      plt.plot([coordinate[0], coordinate[2]], [coordinate[1], coordinate[3]],
               color='k', linestyle='-', linewidth=2)

    good_radius = 3  # cm
    good_ragion=plt.Circle((0,0), good_radius, color='k', fill=False)
    ax.add_artist(good_ragion)

    # cavity
    cavity_radius = 14.13  # cm
    cavity=plt.Circle((0,0), cavity_radius, color='k', alpha=0.1)
    ax.add_artist(cavity)

    plt.xlim((-16, 16))
    plt.ylim((-16, 16))

fig = plt.figure(figsize=(7,6))
fig.text(0.5, 0.05, r'x (cm)', ha='center', va='center', size=26)

data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\"
if platform.system() == 'Linux':
    data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/MC/"
plt.ylabel('y (cm)')

#spark_locations = np.load(''.join((data_dir, 'spark_locations_12.npy')))
spark_locations = np.load(''.join((data_dir, 'spark_locations_12_8MHz.npy')))
actual_locations = np.load(''.join((data_dir, 'actual_locations.npy')))
"""
spark_locations = np.load(''.join((data_dir, 'spark_locations_12_r3cm.npy')))
actual_locations = np.load(''.join((data_dir, 'actual_locations_r3cm.npy')))
"""
#plot_source_locations(spark_locations[0:10,0], spark_locations[0:10,1], actual_locations[0:10,0], actual_locations[0:10,1])
plot_source_locations(spark_locations[:,0], spark_locations[:,1], actual_locations[:,0], actual_locations[:,1])
plt.tight_layout()