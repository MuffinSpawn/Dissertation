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

def average_signals(directory, files, channels):
    avg_signals = np.zeros((3, 5119))
    for file in files:
        (times, signals) = labview.load_data(directory, file, channels=channels)
        avg_signals += signals
    avg_signals /= len(files)
    return (times, avg_signals)


fig = plt.figure(figsize=(14.0, 8.7))
fig.text(0.53, 0.05, r'Time ($\mu s$)', ha='center', va='center', size=24)
fig.text(0.02, 0.58, 'Normalized Amplitude (V)', ha='center', va='center', rotation='vertical', size=24)

lv_dir = "C:\\Users\\plane\\Desktop\\Data\\HPRF\\2015-11-12\\Hammer\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-12/Hammer/"
lv_files = [ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("raw_data_") and f.endswith(".npz")) ]
(times, avg_hammer_signals_0deg) = average_signals(lv_dir, lv_files, [0, 1, 2])
times = times*1e6
#print hpc.localize_dlhpc(times, avg_hammer_signals_0deg, 0)  # 0 deg

plt.subplot(331)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_0deg[0]/np.max(avg_hammer_signals_0deg[0]), 'k')
plt.plot(times, avg_hammer_signals_0deg[0], 'k')
plt.text(5.0, 0.6, r'Mic #1', size=24)
plt.text(5.0, -0.9, r'$0^\circ$', size=24)
plt.subplot(332)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_0deg[1]/np.max(avg_hammer_signals_0deg[1]), 'k')
plt.plot(times, avg_hammer_signals_0deg[1], 'k')
plt.text(5.0, 0.6, r'Mic #2', size=24)
plt.text(5.0, -0.9, r'$120^\circ$', size=24)
plt.subplot(333)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_0deg[2]/np.max(avg_hammer_signals_0deg[2]), 'k')
plt.plot(times, avg_hammer_signals_0deg[2], 'k')
plt.text(5.0, 0.6, r'Mic #3', size=24)
plt.text(5.0, -0.9, r'$240^\circ$', size=24)


lv_dir = "C:\\Users\\plane\\Desktop\\Data\\HPRF\\2015-11-13\\Hammer\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-13/Hammer/"
lv_files = [ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("raw_data_") and f.endswith(".npz")) ]
(times, avg_hammer_signals_120deg) = average_signals(lv_dir, lv_files[:3], [0, 1, 2])
times = times*1e6
#print hpc.localize_dlhpc(times, avg_hammer_signals_120deg, 1)  # 120 deg


plt.subplot(334)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_120deg[2]/np.max(avg_hammer_signals_120deg[2]), 'k')
plt.plot(times, avg_hammer_signals_120deg[2], 'k')
plt.text(5.0, 0.6, r'Mic #3', size=24)
plt.text(5.0, -0.9, r'$0^\circ$', size=24)
plt.subplot(335)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_120deg[0]/np.max(avg_hammer_signals_120deg[0]), 'k')
plt.plot(times, avg_hammer_signals_120deg[0], 'k')
plt.text(5.0, 0.6, r'Mic #1', size=24)
plt.text(5.0, -0.9, r'$120^\circ$', size=24)
plt.subplot(336)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_120deg[1]/np.max(avg_hammer_signals_120deg[1]), 'k')
plt.plot(times, avg_hammer_signals_120deg[1], 'k')
plt.text(5.0, 0.6, r'Mic #2', size=24)
plt.text(5.0, -0.9, r'$240^\circ$', size=24)

(times, avg_hammer_signals_90deg) = average_signals(lv_dir, lv_files[3:], [3])
times = times*1e6
#print hpc.localize_dlhpc(times, avg_hammer_signals_90deg, 2)  # 90 deg

plt.subplot(337)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_90deg[2]/np.max(avg_hammer_signals_90deg[2]), 'k')
plt.plot(times, avg_hammer_signals_90deg[2], 'k')
plt.text(5.0, 0.6, r'Mic #3', size=24)
plt.text(5.0, -0.9, r'$0^\circ$', size=24)
plt.subplot(338)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_90deg[0]/np.max(avg_hammer_signals_90deg[0]), 'k')
plt.plot(times, avg_hammer_signals_90deg[0], 'k')
plt.text(5.0, 0.6, r'Mic #1', size=24)
plt.text(5.0, -0.9, r'$120^\circ$', size=24)
plt.subplot(339)
plt.xlim([0,200])
#plt.ylim([-1.1,1.1])
#plt.plot(times, avg_hammer_signals_90deg[1]/np.max(avg_hammer_signals_90deg[1]), 'k')
plt.plot(times, avg_hammer_signals_90deg[1], 'k')
plt.text(5.0, 0.6, r'Mic #2', size=24)
plt.text(5.0, -0.9, r'$240^\circ$', size=24)

plt.gcf().subplots_adjust(bottom=0.15, top=0.95, left=0.09, hspace=0.3, wspace=0.25)
plt.show()

#plt.tight_layout()