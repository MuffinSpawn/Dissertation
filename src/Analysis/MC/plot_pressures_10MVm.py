# -*- coding: utf-8 -*-
"""
Created on Fri Mar 11 09:53:02 2016

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
import scipy.integrate as integrate
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
"""
plt.figure(figsize=(18,6))
#plot.plot_formula('-np.sin(math.pi*x)', -0.5, 0.5)
#plot.plot_formula('np.tan(x)', -math.pi/2.1, math.pi/2.1)
plt.xlim(0,14.26)
plt.xlabel('Inner End Wall Radius (cm)')
plt.ylabel('Net Outward\nPressure (Pa)')
plot.plot_formula('-885*sp.jn(0, 0.1687*x)**2 + 443*sp.jn(1, 0.1687*x)**2',
                  0, 14.26, num=1000)
plt.tight_layout()

print 443*special.jn(1, 0.1687*14.26)**2
"""
epsilon_0 = 8.854e-12  # F/m
E_0 = 10e6 # V/m
k_rho = 16.87 #1/m
"""
R = 0.1426 # m
L = 0.13 # m
f = 805.e6  #Hz
"""
R = 0.5709 # m
L = 0.40 # m
f = 201.e6  #Hz
charge = 2 * math.pi * epsilon_0 * E_0 * \
         integrate.quad(lambda x: x*special.jn(0,k_rho*x), 0, R)[0]
print "Total Surface Charge:", charge
omega = 2 * math.pi * f
c = 2.998e8  # m/s
mu_0 = 4*math.pi*1e-7
energy = math.pi * L * omega**2 /(k_rho**2 * c**4 * mu_0) * E_0**2 * \
         integrate.quad(lambda x: x*special.jn(1,k_rho*x)**2, 0, R)[0]
print "Stored Energy (B):", energy
energy = math.pi * L * epsilon_0 * E_0**2 * \
         integrate.quad(lambda x: x*special.jn(0,k_rho*x)**2, 0, R)[0]
print "Stored Energy (E):", energy