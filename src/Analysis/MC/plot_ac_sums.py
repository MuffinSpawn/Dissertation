# -*- coding: utf-8 -*-
"""
Created on Fri Feb 19 16:28:19 2016

@author: plane
"""
#%load_ext autoreload
#%autoreload 2

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
import Queue as queue
import pp
import mpl_toolkits.axes_grid1 as pltool

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

def accumulated_correlation(signals, dt, mic_coordinates, radius, thickness,
                            v_s, v_p, actual_location,
                            grid_size=10, settling_time=0, octant=-1):
  lag_matrix = np.zeros((len(signals), len(signals), len(signals[0])*2-1))
  for i,signal_i in enumerate(signals):
    for j,signal_j in enumerate(signals[i+1:]):
      lag_matrix[i, j+i+1] = sig.correlate(signal_i, signal_j)
      lag_matrix[j+i+1, i] = lag_matrix[i, j+i+1]

  quadrant = -1
  if octant >=0:
    quadrant = int(octant / 2)
  # - Create a zero matrix the size of the test point grid (sum matrix)
  sums = np.zeros((grid_size, grid_size))
  if quadrant >= 0:
    if (quadrant == 0) or (quadrant == 3):
      xs = np.linspace(0, radius, num=grid_size)
    else:
      xs = np.linspace(0, -radius, num=grid_size)
    if (quadrant == 0) or (quadrant == 1):
      ys = np.linspace(0, radius, num=grid_size)
    else:
      ys = np.linspace(0, -radius, num=grid_size)
  else:
    xs = np.linspace(-radius, radius, num=grid_size)
    ys = np.linspace(-radius, radius, num=grid_size)

  n0 = len(signals[0])
  ijs = []
  for i,signal_i in enumerate(signals):
    for j,signal_j in enumerate(signals[i+1:]):
      # Note: j -> j+i+1 because of the loop optimization
      ijs.append([i, j+i+1])
  ijs = np.array(ijs)

  if np.any(octant == np.array([0,1,4,5])):
    # octants 0,1,4,5
    constraint_slope = float(mic_coordinates[0,0]) / mic_coordinates[0,1]
  else:
    # octants 2,3,6,7
    constraint_slope = float(mic_coordinates[1,0]) / mic_coordinates[1,1]

  partial_sums = np.zeros((len(ijs), grid_size, grid_size))
  for a,x in enumerate(xs):
    if (quadrant >= 0):
      max_y = math.sqrt(radius**2 - x**2)
      dy = radius / (grid_size-1)
      max_b = int(round(max_y / dy))

    else:
      min_b = 0
      max_b = len(ys)
    for b,y in enumerate(ys[:max_b]):
    #for b,y in enumerate(ys):
      # - For each pair of microphones...
      for index,ij in enumerate(ijs):
        contrib_index = -1

        if (x**2 + y**2) <= (radius**2) and\
           ((octant == 0 and y <= constraint_slope*x and x >= y/constraint_slope) or\
           (octant == 1 and y >= constraint_slope*x and x <= y/constraint_slope) or\
           (octant == 2 and y >= constraint_slope*x and x >= y/constraint_slope) or\
           (octant == 3 and y <= constraint_slope*x and x <= y/constraint_slope) or\
           (octant == 4 and y >= constraint_slope*x and x <= y/constraint_slope) or\
           (octant == 5 and y <= constraint_slope*x and x >= y/constraint_slope) or\
           (octant == 6 and y <= constraint_slope*x and x <= y/constraint_slope) or\
           (octant == 7 and y >= constraint_slope*x and x >= y/constraint_slope) or\
           (octant < 0)):
          contrib_index = psig.ac_contrib_index(mic_coordinates, [x, y], thickness,
                                           ij[0], ij[1], n0, v_s, v_p, dt,
                                           settling_time)
        if contrib_index >= 0 and contrib_index < lag_matrix.shape[2]:
          partial_sums[index, a,b] += lag_matrix[ij[0],ij[1],contrib_index]

  fig = plt.figure(figsize=(15, 10))
  for index,ij in enumerate(ijs):
    plt.subplot(231+index)
    """
    plt.scatter(mic_coordinates[ij[0],0], mic_coordinates[ij[0],1],
                marker='+', c='k', s=100)
    plt.scatter(mic_coordinates[ij[1],0], mic_coordinates[ij[1],1],
                marker='+', c='k', s=100)
    """
    plt.plot(mic_coordinates[ij[0],0], mic_coordinates[ij[0],1], 'k+', markersize=20)
    plt.plot(mic_coordinates[ij[1],0], mic_coordinates[ij[1],1], 'k+', markersize=20)
    im = plt.imshow(partial_sums[index, :,::-1].transpose(), extent=[xs[0], xs[-1], ys[0], ys[-1]])

    if np.any(index == np.array([3,4,5])):
      plt.xlabel('x (cm)')
    if np.any(index == np.array([0,3])):
      plt.ylabel('y (cm)')

    divider = pltool.make_axes_locatable(fig.axes[2*index])
    cax = divider.append_axes("right", size="5%", pad=0.05)
    plt.colorbar(im, cax=cax)

    if np.any(index == np.array([0,1,2])):
      fig.axes[2*index].get_xaxis().set_visible(False)
    if np.any(index == np.array([1,2,4,5])):
      fig.axes[2*index].get_yaxis().set_visible(False)

    print 'Axis Count:', len(fig.axes)
    """
    axis_index = 4
    if axis_index < len(fig.axes):
      fig.axes[axis_index].get_xaxis().set_visible(False)
      fig.axes[axis_index].get_yaxis().set_visible(False)
    """
  plt.tight_layout()

  sums = np.sum(partial_sums, axis=0)
  fig = plt.figure(figsize=(6, 6))
  for coordinates in mic_coordinates:
    plt.plot(coordinates[0], coordinates[1], 'k+', markersize=20)
    plt.plot(actual_location[0], actual_location[1], 'k*', markersize=20)
  plt.xlabel('x (cm)')
  plt.ylabel('y (cm)')
  if xs[-1] < 0 and ys[-1] < 0:
    im = plt.imshow(sums[:,::-1].transpose()[::-1,::-1], extent=[xs[-1], xs[0], ys[-1], ys[0]])
  elif xs[-1] < 0 and ys[-1] > 0:
    im = plt.imshow(sums[:,::-1].transpose()[:,::-1], extent=[xs[-1], xs[0], ys[0], ys[-1]])
  else:
    im = plt.imshow(sums[:,::-1].transpose(), extent=[xs[0], xs[-1], ys[0], ys[-1]])
  divider = pltool.make_axes_locatable(fig.axes[0])
  cax = divider.append_axes("right", size="5%", pad=0.05)
  plt.colorbar(im, cax=cax)
  plt.tight_layout()

  # - Use the max sum matrix element to calculate the most likely source point
  max_indicies = np.unravel_index([np.argmax(sums)], np.shape(sums))
  coordinates = [xs[max_indicies[0][0]], ys[max_indicies[1][0]]]
  if coordinates[0]**2 + coordinates[1]**2 > radius**2:
    coordinates = [0.0,0.0]
  return coordinates

def localize_spark_pp(times, signals, v_s, v_p, grid_size, actual_location):
  dt = times[1] - times[0]
  mic_coordinates = np.array(zip([5, -5, -5, 5], [6, 6, -6, -6]))
  radius = 14.22  # cm
  thickness = 1.37  # cm
  ordered_mics = mc.order_by_time(times, signals, False)
  mics = sorted(ordered_mics, key=lambda mic: mic.id())
  octant = mc.octant_trilateration(signals, ordered_mics, mic_coordinates)
  print octant
  return accumulated_correlation(
                            signals, dt,
                            mic_coordinates,
                            radius, thickness,
                            v_s, v_p,
                            actual_location,
                            #grid_size=grid_size)
                            grid_size=grid_size,
                            octant=octant)

if __name__ == '__main__':
  data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\"
  if platform.system() == 'Linux':
    data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/MC/"
  comsol_file = "random100_400kHz.npy"
  dt = 2.5e-6
  signal_index = 75
  actual_location = np.load(''.join((data_dir, 'actual_locations.npy')))[signal_index]
  (times, signal_sets) = comsol.load_data(data_dir, comsol_file, dt=dt)
  signals = signal_sets[signal_index]
  print localize_spark_pp(times, signals, 4.03448276e5, 3.06896552e5, 50, actual_location)
