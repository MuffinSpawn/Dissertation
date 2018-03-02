# -*- coding: utf-8 -*-
"""
Created on Sun Feb 28 17:07:25 2016

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

def plot_all(tek_dir, tek_file, comsol_dir, comsol_file, sign):
    plt.figure(figsize=(18.9, 10.3))
    (xs, ys) = tektronix.load_csv_data(tek_dir, tek_file)
    xs = xs[0]
    ys = sign*ys[0] / np.max(sign*ys[0])
    plt.subplot(221)
    plt.xlabel('Time (ms)')
    plt.ylabel('Normalized Amplitude')
    plt.xlim((0, 14))
    plt.ylim((-1.2,1.2))
    plt.plot(xs*1e3, ys)

    dt = xs[1] - xs[0]
    df = 1.0 / dt / len(xs)
    print "".join(("dt: ", str(dt), " s\tdf: ", str(df), " Hz"))
    frequency_spectrum = np.fft.fft(ys)\
      [:round(ys.size/2)]
    spectrum_magnitudes = np.sqrt(np.real(frequency_spectrum)**2 + np.imag(frequency_spectrum)**2)
    frequencies = np.fft.fftfreq(
      frequency_spectrum.size*2, d=dt)\
      [:round(ys.size/2)]
    plt.subplot(222)
    plt.xlabel('Frequency (kHz)')
    plt.ylabel('Normalized Magnitude')
    plt.xlim((0,7))
    plt.ylim((0,1))
    plt.plot(frequencies*1e-3, spectrum_magnitudes / np.max(spectrum_magnitudes))

    (xs, ys) = comsol.load_csv_data(comsol_dir, comsol_file)
    print np.shape(xs)
    print np.shape(ys)
    xs = xs[0]
    ys = ys[0] / np.max(ys[0])
    plt.subplot(223)
    plt.xlabel('Time (ms)')
    plt.ylabel('Normalized Amplitude')
    plt.xlim((0,14))
    plt.ylim((-1.2,1.2))
    plt.plot(xs*1e3, ys)

    dt = xs[1] - xs[0]
    df = 1.0 / dt / len(xs)
    print "".join(("dt: ", str(dt), " s\tdf: ", str(df), " Hz"))
    frequency_spectrum = np.fft.fft(ys)\
      [:round(ys.size/2)]
    spectrum_magnitudes = np.sqrt(np.real(frequency_spectrum)**2 + np.imag(frequency_spectrum)**2)
    frequencies = np.fft.fftfreq(
      frequency_spectrum.size*2, d=dt)\
      [:round(ys.size/2)]
    plt.subplot(224)
    plt.xlabel('Frequency (kHz)')
    plt.ylabel('Normalized Magnitude')
    plt.xlim((0,7))
    plt.ylim((0,1))
    plt.plot(frequencies*1e-3, spectrum_magnitudes / np.max(spectrum_magnitudes))

tek_dir = "C:\\Users\\plane\\Desktop\\Data\\Al Cavity\\2015-08-05\\ALL0000\\"
tek_file = "F0000CH3.CSV"
comsol_dir = "C:\\Users\\plane\\Desktop\\Data\\COMSOL\\Al Cavity\\Ring and Disk Validity Test\\"
comsol_file = "ring_under_disk_0.25ms_30.5cm_25ms_1.5e8_1.5e8_1.5e8.csv"
plot_all(tek_dir, tek_file, comsol_dir, comsol_file, -1)
plt.tight_layout()
plt.show()