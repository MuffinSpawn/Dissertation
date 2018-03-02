# -*- coding: utf-8 -*-
"""
Created on Sun Apr 03 22:24:00 2016

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


data_dir = "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\MC\\"
if platform.system() == 'Linux':
  data_dir = "/home/lane/Dropbox/Research/MTA/Analysis/MC/"
comsol_file = "random100_400kHz.npy"
(times, signal_sets) = comsol.load_data(data_dir, comsol_file, dt=dt)
times *= 0.5e6
signal = signal_sets[0,2]
signal /= signal.max()
shifted_signal = np.roll(signal, 10)
correlation = sig.correlate(signal, shifted_signal)
corr_times = np.hstack([times[::-1]*-1, times[1:]])
fig = plt.figure(figsize=(20.0, 8.0))

plt.subplot(211)
plt.ylim(-1.1, 1.1)
plt.xlabel('Time (t)')
plt.ylabel('Amplitude')
plt.plot(times, signal, times, shifted_signal)
plt.plot([25, 25], [-0.5, -1], color='k', linestyle='-', linewidth=2)
plt.plot([25, 28], [-0.75, -0.75], color='k', linestyle='-', linewidth=2)
plt.plot([37.5, 37.5], [-0.5, -1], color='k', linestyle='-', linewidth=2)
plt.plot([34.5, 37.5], [-0.75, -0.75], color='k', linestyle='-', linewidth=2)
plt.text(29.5, -0.9, '12.5')

plt.subplot(212)
plt.ylim(-4, 4)
plt.xlabel(r'Time Delay ($\tau$)')
plt.ylabel(r'$R_{ij}(\tau)$')
plt.plot(corr_times, correlation)
plt.plot([-12.5, -12.5], [-0.5, -2.5], color='k', linestyle='-', linewidth=2)
plt.text(-18, -3.5, '-12.5')

plt.tight_layout()
