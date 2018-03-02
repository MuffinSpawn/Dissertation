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

def plot_source_locations(source_xs_0, source_ys_0, source_xs_120, source_ys_120, source_xs_90, source_ys_90,
                          damage_xs=[], damage_ys=[]):
    # center marks
    #source_xs = np.hstack((source_xs_0, source_xs_120, source_xs_90, damage_xs))
    #source_ys = np.hstack((source_ys_0, source_ys_120, source_ys_90, damage_ys))
    source_xs = np.hstack((source_xs_0, source_xs_120, source_xs_90))
    source_ys = np.hstack((source_ys_0, source_ys_120, source_ys_90))

    mark_radii = np.repeat(10, len(source_xs))
    mark_colors = np.repeat(5, len(source_xs))
    plt.scatter(source_xs, source_ys, s=mark_radii, c=mark_colors, cmap=cm.Set1, alpha=1.0)

    fig = plt.gcf()
    ax = fig.gca()
    error_radius = 2.9

    # error circles
    patches = []
    for coordinate in zip(source_xs_0,source_ys_0):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, cmap=cm.brg,
                                              norm=mpl.colors.Normalize(0.,1.),
                                              alpha=0.2)
    collection.set_array(np.repeat(0.5, len(source_xs_0)))
    ax.add_collection(collection)

    patches = []
    for coordinate in zip(source_xs_120,source_ys_120):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, cmap=cm.brg,
                                              norm=mpl.colors.Normalize(0.,1.),
                                              alpha=0.2)
    collection.set_array(np.repeat(1.0, len(source_xs_120)))
    ax.add_collection(collection)

    patches = []
    for coordinate in zip(source_xs_90,source_ys_90):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, cmap=cm.brg,
                                              norm=mpl.colors.Normalize(0.,1.),
                                              alpha=0.2)
    collection.set_array(np.repeat(0.0, len(source_xs_90)))
    ax.add_collection(collection)

    # damage marks
    """
    patches = []
    for coordinate in zip(damage_xs,damage_ys):
        coordinate = np.array(coordinate)
        #patches.append(mpatches.RegularPolygon(coordinate, 5, 0.4))
        patches.append(mpatches.Rectangle(coordinate-[0.04,0.4], 0.08, 0.8))
        patches.append(mpatches.Rectangle(coordinate-[0.4,0.05], 0.8, 0.08))
    collection = mcollections.PatchCollection(patches, color='k', alpha=1.0)
    #collection.set_array(np.repeat(0.5, len(source_xs_0)))
    ax.add_collection(collection)
    """


    # cavity
    origin_x = np.zeros(1)
    origin_y = np.zeros(1)
    cavity_radius = 10000
    #plt.scatter([0, 0], [0, 0], s=[90000, 0], c=[3,1], cmap=cm.Set1, alpha=0.1)
    cavity=plt.Circle((0,0),11.43,color='k',alpha=0.1)
    ax.add_artist(cavity)

    plt.xlim((-16, 16))
    plt.ylim((-16, 16))
"""
#v_p_SS = 7.0e5  # cm/s
v_p_SS = 5.79e5  # cm/s
#v_p_SS = 4.5e5  # cm/s
#v_p_SS = 3.1e5  # cm/s
#v_p_SS = 2.3e5  # cm/s  Lower computational limit
v_s_SS = 3.1e5  # cm/s
v = (v_s_SS + v_p_SS) / 2.0
radius = 11.43  # cm
thickness = 5.08  # cm
settling_time = 1.25e-6

# 0 Degrees
lv_dir = "C:\\Users\\plane\\Desktop\\Data\\HPRF\\2015-11-12\\Breakdown\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-12/Breakdown/"
lv_files = sorted([ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("reduced_data_") and f.endswith(".npz")) ])
rs = [11.0, 11.0, 11.0]  # cm
thetas = np.array([11.5, 19.5, 3.5]) * math.pi / 12.0
xs = map(lambda r,theta: r * math.cos(theta), rs, thetas)
ys = map(lambda r,theta: r * math.sin(theta), rs, thetas)
mic_coordinates = np.array(zip(xs, ys))
spark_locations_0 = np.empty((len(lv_files), 2))
prog.update_progress(0.0)
for index,lv_file in enumerate(lv_files):
    (times, breakdown_signals) = labview.load_data(lv_dir, lv_file,
                                                   channels=[0,1,2])
    dt = times[1] - times[0]
    spark_location = psig.accumulated_correlation(breakdown_signals[:,5:20],
                                                  dt, mic_coordinates, radius,
                                                  thickness, v, v,
                                                  grid_size=100,
                                                  settling_time=settling_time)
    spark_locations_0[index] = spark_location
    prog.update_progress(float(index+1)/float(len(lv_files)))

# 120 Degrees
lv_dir = "C:\\Users\\plane\\Desktop\\Data\\HPRF\\2015-11-13\\Breakdown\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-13/Breakdown/"
lv_files = sorted([ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("reduced_data_") and f.endswith(".npz")) ])
rs = [11.0, 11.0, 11.0]  # cm
thetas = np.array([19.5, 3.5, 11.5]) * math.pi / 12.0  # 120 degree rotation
xs = map(lambda r,theta: r * math.cos(theta), rs, thetas)
ys = map(lambda r,theta: r * math.sin(theta), rs, thetas)
mic_coordinates = np.array(zip(xs, ys))
spark_locations_120 = np.empty((7, 2))
prog.update_progress(0.0)
for index,lv_file in enumerate(lv_files[:7]):
    (times, breakdown_signals) = labview.load_data(lv_dir, lv_file,
                                                   channels=[0,1,2])
    dt = times[1] - times[0]
    spark_location = psig.accumulated_correlation(breakdown_signals[:,5:20],
                                                  dt, mic_coordinates, radius,
                                                  thickness, v, v,
                                                  grid_size=100,
                                                  settling_time=settling_time)
    spark_locations_120[index] = spark_location
    prog.update_progress(float(index+1)/float(len(lv_files[:7])))

# 90 Degrees
lv_dir = "C:\\Users\\plane\\Desktop\\Data\\HPRF\\2015-11-13\\Breakdown\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-13/Breakdown/"
lv_files = sorted([ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("reduced_data_") and f.endswith(".npz")) ])
rs = [11.0, 11.0, 11.0]  # cm
thetas = np.array([17.5, 1.5, 9.5]) * math.pi / 12.0  # 90 degree rotation
xs = map(lambda r,theta: r * math.cos(theta), rs, thetas)
ys = map(lambda r,theta: r * math.sin(theta), rs, thetas)
mic_coordinates = np.array(zip(xs, ys))
spark_locations_90 = np.empty((len(lv_files)-7, 2))
prog.update_progress(0.0)
for index,lv_file in enumerate(lv_files[7:]):
    (times, breakdown_signals) = labview.load_data(lv_dir, lv_file,
                                                   channels=[0,1,2])
    dt = times[1] - times[0]
    spark_location = psig.accumulated_correlation(breakdown_signals[:,5:20],
                                                  dt, mic_coordinates, radius,
                                                  thickness, v, v,
                                                  grid_size=100,
                                                  settling_time=settling_time)
    spark_locations_90[index] = spark_location
    prog.update_progress(float(index+1)/float(len(lv_files[7:])))
"""
#fig = plt.figure(figsize=(14.0, 7.0))
fig = plt.figure(figsize=(7.0, 7.0))
fig.text(0.51, 0.02, 'x (cm)', ha='center', va='center', fontsize=26)
fig.text(0.02, 0.52, 'y (cm)', ha='center', va='center', rotation='vertical', fontsize=26)
plt.gcf().subplots_adjust(bottom=0.15, left=0.06)
#plt.subplot(121)
plot_source_locations(spark_locations_0[:,0], spark_locations_0[:,1],
                      spark_locations_120[:,0], spark_locations_120[:,1],
                      spark_locations_90[:,0], spark_locations_90[:,1],
                      [0.8,-0.9,-5.4,-3.2,-4.8], [5.2,4.9,0.6,-5.6,-9.5])
"""
plt.subplot(122)
indicies_0 = np.hstack(((9,10,11,14),))
indicies_120 = np.arange(2, len(spark_locations_120))
indicies_90 = np.hstack(((1,3),np.arange(5,11),(14,16,21,22,23)))
plot_source_locations(spark_locations_0[indicies_0,0], spark_locations_0[indicies_0,1],
                      spark_locations_120[indicies_120,0],spark_locations_120[indicies_120,1],
                      spark_locations_90[indicies_90,0], spark_locations_90[indicies_90,1],
                      [0.8,-0.9,-5.4,-3.2,-4.8], [5.2,4.9,0.6,-5.6,-9.5])
"""
plt.tight_layout()