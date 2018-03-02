##################################################################
#                             NOTE
# To run this script, change all instances of
# 'C:\\Users\\peter\\Development\\Dissertation\\' to the location of
# this Bazaar branch's root directory.
##################################################################

# coding: utf-8

# In[3]:

'''
get_ipython().magic(u'load_ext autoreload')
get_ipython().magic(u'autoreload 2')
get_ipython().magic(u'matplotlib inline')
'''
import math
import os
import os.path as path
import platform
import sys
import numpy as np  # NumPy (multidimensional arrays, linear algebra, ...)
import matplotlib as mpl
import matplotlib.cm as cm
import matplotlib.collections as mcollections
import matplotlib.patches as mpatches
import matplotlib.pyplot as plt  # Matplotlib's pyplot: MATLAB-like syntax

sys.path.insert(0, "C:\\Users\\peter\\Dropbox\\Library\\Python")
import scipy.signal as sig
import pgl.signal as psig
import pgl.labview as labview
import pgl.comsol as comsol
import pgl.tektronix as tektronix
import pgl.plot as plot
import pgl.curve as curve

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

# # Aluminum Mock Cavity

# ## Attenuation Analysis

# In[ ]:

cable_lengths = np.array((59.0, 100.0, 150.0, 200.0, 250.0, 300.0, 350.0, 518.0, 883.5))
cable_vpp_measurements = np.array([np.array([1.62, 1.20, 1.61, 1.66, 1.73, 1.10, 1.08, 1.14, 1.62, 1.66]),
                                   np.array([1.32, 1.37, 1.19, 1.56, 1.02, 1.43, 1.41, 1.31, 1.24, 0.920, 1.36]),
                                   np.array([0.968, 1.09, 0.952, 0.952, 1.04, 1.02, 0.792, 1.24, 0.976, 1.23]),
                                   np.array([0.920, 0.876, 0.840, 0.880, 0.864, 0.940, 0.928, 0.828, 0.672, 0.812]),
                                   np.array([0.704, 0.760, 0.644, 0.860, 0.856, 0.596, 0.576, 0.736, 0.932, 0.884]),
                                   np.array([0.784, 0.468, 0.740, 0.980, 0.676, 0.864, 0.424, 0.472, 0.516, 0.488]),
                                   np.array([0.544, 0.616, 0.580, 0.512, 0.508, 0.532, 0.580, 0.548, 0.592, 0.600]),
                                   np.array([0.588, 0.644, 0.424, 0.604, 0.668, 0.396, 0.356, 0.372, 0.476, 0.564]),
                                   np.array([0.380, 0.356, 0.408, 0.396, 0.308, 0.292, 0.320, 0.268, 0.314, 0.270])])
avg_peak_v_list = np.zeros(len(cable_lengths))
stddev_list = np.zeros(len(cable_lengths))
cable_vmax = np.zeros(np.shape(cable_vpp_measurements))
for index,vpp_list in enumerate(cable_vpp_measurements):
    peak_v_list = vpp_list / 2.0
    avg_peak_v_list[index] = np.average(np.array(peak_v_list))
    stddev_list[index] = np.std(peak_v_list)
fig = plt.figure(figsize=(14.0, 4.0))
plt.subplot(121)
plt.xlabel('Cable Length (cm)')
plt.ylabel('Average Peak Voltage (V)')
plt.errorbar(cable_lengths, avg_peak_v_list, yerr=stddev_list, marker='.')

errors = np.array(list(map(lambda vpp,stddev: 2.0 / (vpp*vpp)*stddev, avg_peak_v_list, stddev_list)))
print(errors)
(a, b) = curve.fit_polynomial(cable_lengths, 1.0/avg_peak_v_list, order=1, yerr=errors)
fit_xs = range(60, 900, 10)
fit_ys = np.array(list(map(lambda x: a + b*x, fit_xs)))
print(''.join((str(a), ' + ', str(b), '*x')))
plt.plot(fit_xs, 1.0/fit_ys)

plt.subplot(122)
plt.xlabel('Cable Length (cm)')
plt.ylabel('1/(Average Peak Voltage) (V^-1)')
plt.errorbar(cable_lengths, 1.0/avg_peak_v_list, yerr=errors, linestyle='None', marker='.')
plt.plot(fit_xs, fit_ys)

frequency = 133e3
c_m = 1.83e-9  # F
loss_factor = 0.02
mic_impedence = 1.0 / (2*math.pi*frequency*c_m*loss_factor)
print(''.join(('Mic Impedence: ', str(mic_impedence))))

cc_per_cm = 46.4e-12  # F
cable_length_domain = np.arange(59, 883)
cable_impedences = map(lambda l: 1.0 / (2*math.pi*frequency*cc_per_cm*l), cable_length_domain)
voltage_drop_factors = np.array(list(map(lambda z: z/(mic_impedence + z), cable_impedences)))
base_voltage = avg_peak_v_list[0] / voltage_drop_factors[0]
print(''.join(('Base Voltage: ', str(base_voltage))))

cp = 2.43e-12  # F
kq = 400.0e-10  # cm/V
max_disp = kq * base_voltage
print(''.join(('Max. Displacement: ', str(max_disp))))

# plt.show()


# ## Disk Comparison

# In[ ]:

def plot_all(tek_dir, tek_file, comsol_dir, comsol_file, sign):
    plt.figure(figsize=(18.9, 10.3))
    (xs, ys) = tektronix.load_csv_data(tek_dir, tek_file)
    xs = xs[0]
    ys = sign*ys[0] / np.max(sign*ys[0])
    plt.subplot(221)
    plt.xlabel('Time (s)')
    plt.ylabel('Normalized Amplitude')
    plt.xlim((0, .014))
    plt.ylim((-1.2,1.2))
    plt.plot(xs, ys)

    dt = xs[1] - xs[0]
    df = 1.0 / dt / len(xs)
    print("".join(("dt: ", str(dt), " s\tdf: ", str(df), " Hz")))
    frequency_spectrum = np.fft.fft(ys)      [:round(ys.size/2)]
    spectrum_magnitudes = np.sqrt(np.real(frequency_spectrum)**2 + np.imag(frequency_spectrum)**2)
    frequencies = np.fft.fftfreq(
      frequency_spectrum.size*2, d=dt)\
      [:round(ys.size/2)]
    plt.subplot(222)
    plt.xlabel('Frequency (Hz)')
    plt.ylabel('Normalized Magnitude')
    plt.xlim((0,7000))
    plt.ylim((0,1))
    plt.plot(frequencies, spectrum_magnitudes / np.max(spectrum_magnitudes))

    (xs, ys) = comsol.load_csv_data(comsol_dir, comsol_file)
    print(np.shape(xs))
    print(np.shape(ys))
    xs = xs[0]
    ys = ys[0] / np.max(ys[0])
    plt.subplot(223)
    plt.xlabel('Time (s)')
    plt.ylabel('Normalized Amplitude')
    plt.xlim((0,0.014))
    plt.ylim((-1.2,1.2))
    plt.plot(xs, ys)

    dt = xs[1] - xs[0]
    df = 1.0 / dt / len(xs)
    print("".join(("dt: ", str(dt), " s\tdf: ", str(df), " Hz")))
    frequency_spectrum = np.fft.fft(ys)      [:round(ys.size/2)]
    spectrum_magnitudes = np.sqrt(np.real(frequency_spectrum)**2 + np.imag(frequency_spectrum)**2)
    frequencies = np.fft.fftfreq(
      frequency_spectrum.size*2, d=dt)\
      [:round(ys.size/2)]
    plt.subplot(224)
    plt.xlabel('Frequency (Hz)')
    plt.ylabel('Normalized Magnitude')
    plt.xlim((0,7000))
    plt.ylim((0,1))
    plt.plot(frequencies, spectrum_magnitudes / np.max(spectrum_magnitudes))

tek_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\AMC\\"
tek_file = "F0011CH3.CSV"
comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\AMC\\"
comsol_file = "Dist_24cm_beta_dM_1e-6.csv"
plot_all(tek_dir, tek_file, comsol_dir, comsol_file, -1)
# plt.show()


# ## Disk Under Ring Comparison

# In[ ]:

def plot_all(tek_dir, tek_file, comsol_dir, comsol_file, sign):
    plt.figure(figsize=(18.9, 10.3))
    (xs, ys) = tektronix.load_csv_data(tek_dir, tek_file)
    xs = xs[0]
    ys = sign*ys[0] / np.max(sign*ys[0])
    plt.subplot(221)
    plt.xlabel('Time (s)')
    plt.ylabel('Normalized Amplitude')
    plt.xlim((0, .014))
    plt.ylim((-1.2,1.2))
    plt.plot(xs, ys)

    dt = xs[1] - xs[0]
    df = 1.0 / dt / len(xs)
    print("".join(("dt: ", str(dt), " s\tdf: ", str(df), " Hz")))
    frequency_spectrum = np.fft.fft(ys)      [:round(ys.size/2)]
    spectrum_magnitudes = np.sqrt(np.real(frequency_spectrum)**2 + np.imag(frequency_spectrum)**2)
    frequencies = np.fft.fftfreq(
      frequency_spectrum.size*2, d=dt)\
      [:round(ys.size/2)]
    plt.subplot(222)
    plt.xlabel('Frequency (Hz)')
    plt.ylabel('Normalized Magnitude')
    plt.xlim((0,7000))
    plt.ylim((0,1))
    plt.plot(frequencies, spectrum_magnitudes / np.max(spectrum_magnitudes))

    (xs, ys) = comsol.load_csv_data(comsol_dir, comsol_file)
    print(np.shape(xs))
    print(np.shape(ys))
    xs = xs[0]
    ys = ys[0] / np.max(ys[0])
    plt.subplot(223)
    plt.xlabel('Time (s)')
    plt.ylabel('Normalized Amplitude')
    plt.xlim((0,0.014))
    plt.ylim((-1.2,1.2))
    plt.plot(xs, ys)

    dt = xs[1] - xs[0]
    df = 1.0 / dt / len(xs)
    print("".join(("dt: ", str(dt), " s\tdf: ", str(df), " Hz")))
    frequency_spectrum = np.fft.fft(ys)      [:round(ys.size/2)]
    spectrum_magnitudes = np.sqrt(np.real(frequency_spectrum)**2 + np.imag(frequency_spectrum)**2)
    frequencies = np.fft.fftfreq(
      frequency_spectrum.size*2, d=dt)\
      [:round(ys.size/2)]
    plt.subplot(224)
    plt.xlabel('Frequency (Hz)')
    plt.ylabel('Normalized Magnitude')
    plt.xlim((0,7000))
    plt.ylim((0,1))
    plt.plot(frequencies, spectrum_magnitudes / np.max(spectrum_magnitudes))

tek_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\AMC\\"
tek_file = "F0000CH3.CSV"
comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\AMC\\"
comsol_file = "ring_under_disk_0.25ms_30.5cm_25ms_1.5e8_1.5e8_1.5e8.csv"
plot_all(tek_dir, tek_file, comsol_dir, comsol_file, -1)


# # High-Pressure Cavity

# ## HC Observed vs. Simulated RF Hammer Comparison

# In[ ]:

#get_ipython().magic(u'matplotlib qt')
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/HPRF 20120614/sparks_36MV-M_15Hz/"
lv_file = "spark_66_68.npz"
(times, real_hammer_signals) = labview.load_data(lv_dir, lv_file, t0=0.029342, t1=0.039342, channels=(0,5,3,))  # channels=(0,5,3,6,))
(frequencies, magnitudes, phases) = psig.spectra(times, real_hammer_signals)
fig = plt.figure(figsize=(20.0, 8.0))
plt.subplot(221)
#plt.xlabel('Time (s)')
plt.ylabel('Normalized Voltage')
plot.plot_signals(times*1e6, real_hammer_signals, tlim=300, ylim=1.5, norm=True)
plt.subplot(222)
#plt.xlabel('Frequency (Hz)')
plt.ylabel('Normalized Magnitude')
plot.plot_signals(frequencies*1e-3, magnitudes, ylim=(0,1.2), tlim=30, norm=True)

comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
if platform.system() == 'Linux':
    comsol_dir = "/home/lane/Data/COMSOL/HPRF/RF Hammer with Thin Elastic Layer/"
comsol_file = "\S0_TEL_r_1e9_z_21e6_S1_TEL_r_15e6_z_1e9_S5_TEL_r_1e9_z_37e6_10ms.npy"
(times, sim_hammer_signals_set) = comsol.load_data(comsol_dir, comsol_file, dt=2e-6)

sim_hammer_signals = sim_hammer_signals_set[0]
(frequencies, magnitudes, phases) = psig.spectra(times, sim_hammer_signals)
print(psig.peaks(frequencies, magnitudes[2])[:10])
plt.subplot(223)
plt.xlabel(r'Time ($\mu s$)')
plt.ylabel('Normalized Voltage')
plot.plot_signals(times*1e6, sim_hammer_signals, tlim=300, ylim=1.2, norm=True)
plt.subplot(224)
plt.xlabel(r'Frequency ($kHz$)')
plt.ylabel('Normalized Magnitude')
#plot.plot_spectra(times, sim_hammer_signals, flim=3e4, ylim=1.2, norm=True)
plot.plot_signals(frequencies*1e-3, magnitudes, ylim=(0,1.2), tlim=30, norm=True)
# plt.show()


# ## HC RF Hammer Events

# In[ ]:

#get_ipython().magic(u'matplotlib qt')
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/COMSOL/HPRF/HPRF 20120614/sparks_36MV-M_15Hz/"
lv_file = "spark_66_68.npz"
(times, real_hammer_signals) = labview.load_data(lv_dir, lv_file, t0=0.029342, t1=0.039342, channels=(0,5,3,))  # channels=(0,5,3,6,))
(frequencies, magnitudes, phases) = psig.spectra(times, real_hammer_signals)
fig = plt.figure(figsize=(20.0, 8.0))
plt.xlabel(r'Time ($\mu s$)')
plt.ylabel('Normalized Voltage')
plot.plot_signals(times*1e6, real_hammer_signals, tlim=150, ylim=1.5, norm=True)

# plt.show()


# ## HC Breakdown

# In[ ]:

#get_ipython().magic(u'matplotlib qt')
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/HPRF 20120614/sparks_36MV-M_15Hz/"
# lv_file = "spark_66_68.npz"
lv_file = "reduced_66_68.npz"
(times, real_breakdown_signals) = labview.load_data(lv_dir, lv_file, t0=0.296092, t1=0.296694, channels=(0, 1, 5, ))  # channels=(0,5,3,6,))
fig = plt.figure(figsize=(20.0, 8.0))
plt.subplot(211)
#plt.xlabel(r'Time ($\mu s$)')
plt.ylabel(r'Amplitude ($V$)')
#plt.tight_layout()
plot.plot_signals(times*1e6, real_breakdown_signals, tlim=600, ylim=4.6)

plt.subplot(212)
comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
if platform.system() == 'Linux':
    comsol_dir = "/home/lane/Data/COMSOL/HPRF/Shockwave/"
comsol_file = "HprfBreakdown2D_k_z_S0_100e5_k_r_S1_100e5_k_z_S5_100e5_T_270K.npy"
(times, sim_breakdown_signals) = comsol.load_data(comsol_dir, comsol_file, dt=2e-6)
plt.xlabel(r'Time ($\mu s$)')
plt.ylabel(r'Pressure ($Pa$)')
plot.plot_signals(times*1e6, sim_breakdown_signals[0], tlim=600, ylim=0.55)

# plt.show()


# ## HC Simulated 2D Breakdown

# In[ ]:

comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
if platform.system() == 'Linux':
    comsol_dir = "/home/lane/Data/COMSOL/HPRF/Shockwave/"
#comsol_file = "HprfBreakdown2D_k_z_S0_390e5_k_r_S1_0_k_z_S5_480e5_T_250K.npy"
comsol_file = "HprfBreakdown2D_k_z_S0_100e5_k_r_S1_100e5_k_z_S5_100e5_T_270K.npy"
(times, sim_breakdown_signals) = comsol.load_data(comsol_dir, comsol_file, dt=2e-6)
fig = plt.figure(figsize=(20.0, 8.0))
plt.xlabel(r'Time ($\mu s$)')
plt.ylabel(r'Pressure ($Pa$)')
plt.tight_layout()
plot.plot_signals(times*1e6, sim_breakdown_signals[0], tlim=600, ylim=0.75)

# plt.show()


# ## HC Simulated 3D Breakdown

# In[ ]:

comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
if platform.system() == 'Linux':
    comsol_dir = "/home/lane/Data/COMSOL/HPRF/Shockwave/"
comsol_file = "HprfBreakdown2D_k_z_S0_390e5_k_r_S1_0_k_z_S5_480e5_T_250K.npy"
#comsol_file = "HPRF Shockwave.npy"
(times, sim_breakdown_signals) = comsol.load_data(comsol_dir, comsol_file, dt=2e-6)
fig = plt.figure(figsize=(20.0, 8.0))
plt.xlabel(r'Time ($\mu s$)')
plt.ylabel(r'Pressure ($Pa$)')
plt.tight_layout()
plot.plot_signals(times*1e6, sim_breakdown_signals[0], tlim=600, ylim=1.1, norm=True)

# plt.show()


# # MICE Cavity

# ## Raw Signals

# In[ ]:

#get_ipython().magic(u'matplotlib qt')
fig = plt.figure(figsize=(20.0, 8.0))
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\MICEC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/SCM/Runs/20141117/"
fig.text(0.5, 0.05, r'Time ($\mu s$)', ha='center', va='center', size=18)

# RF Hammer
plt.subplot(121)
lv_file = "raw_2014-11-17_8-18-43.457@20_18_43.457.npz"
channels = range(0, 24)
(times, raw_hammer_signals) = labview.load_data(lv_dir, lv_file, channels=channels)  # channels=(0,5,3,6,))
#plt.xlabel(r'Time ($\mu s$)')
plt.ylabel('Amplitude (V)')
plot.plot_signals(times*1e6, raw_hammer_signals, tlim=600, ylim=5.1)

# Spark
plt.subplot(122)
lv_file = "raw_2014-11-18_10-10-20.060@10_10_20.060.npz"
(times, raw_spark_signals) = labview.load_data(lv_dir, lv_file, channels=channels)  # channels=(0,5,3,6,))
#plt.xlabel(r'Time ($\mu s$)')
plot.plot_signals(times*1e6, raw_spark_signals, tlim=600, ylim=5.1)

# plt.show()


# ## Tap Test

# In[ ]:

#get_ipython().magic(u'matplotlib qt')
fig = plt.figure(figsize=(20.0, 8.0))
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\MICEC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/SCM/Testing/20150108/"
lv_file = "Data_1448_19_29@14_52_31.057.npz"
channels = range(0, 23)
(times, raw_hammer_signals) = labview.load_data(lv_dir, lv_file, channels=channels)  # channels=(0,5,3,6,))
plt.xlabel(r'Time ($\mu s$)')
plt.ylabel('Amplitude (V)')
plt.tight_layout()
plot.plot_signals(times*1e6, raw_hammer_signals, tlim=100000, ylim=5.1)

# plt.show()


# ## RFI Test

# In[ ]:

#get_ipython().magic(u'matplotlib qt')
fig = plt.figure(figsize=(20.0, 8.0))
fig.text(0.5, 0.05, r'Time ($\mu s$)', ha='center', va='center', size=18)
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\MICEC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/SCM/Runs/2015-03-25/"
lv_file = "raw_data_2015-03-25@16_09_32.431.npz"
channels = range(0, 12)
(times, real_hammer_signals) = labview.load_data(lv_dir, lv_file, channels=channels)
(frequencies, magnitudes, phases) = psig.spectra(times, real_hammer_signals)

plt.subplot(121)
plt.ylabel('Amplitude (V)')
plot.plot_signals(times*1e6, real_hammer_signals, tlim=1500, ylim=5)

plt.subplot(122)
rfi_mic_channel = 8
plot.plot_signals(times*1e6, [real_hammer_signals[8]], tlim=1500, ylim=5)

plt.tight_layout()
# plt.show()


# ## Simulated Width vs Radius

# In[ ]:

directory = "C:\\Users\\peter\\Development\\Dissertation\\data\\MICEC\\"
if platform.system() == 'Linux':
    directory = "/home/lane/Dropbox/Research/MTA/Analysis/SCM/Frequency vs Distance Study/"
filename = "line_cut_time_series.npz"
data = np.load(''.join((directory, filename)))
keys = ['_'.join(('arr', str(i))) for i in range(len(data.keys()))]
a_vs_x_at_t = np.array([data[key][1] for key in keys])
shape = np.shape(a_vs_x_at_t)
a_vs_t_at_x = np.transpose(a_vs_x_at_t)[int(np.floor(shape[1]/2)):,:int(np.floor(shape[0]/4))]
dt = 2.e-6
num_time_steps = np.shape(a_vs_t_at_x)[1]-1
times = np.linspace(0, num_time_steps*dt, num=num_time_steps+1)*1.e6
widths = np.linspace(1, 20, num=np.size(times))
wavefront_widths = np.empty(np.shape(a_vs_t_at_x)[0])
for index,signal in enumerate(a_vs_t_at_x):
    cwtmatr = sig.cwt(signal, sig.ricker, widths)
    min_indicies = np.unravel_index(cwtmatr.argmin(), cwtmatr.shape)
    wavefront_widths[index] = widths[min_indicies[0]]
    sys.stdout.write('.'); sys.stdout.flush()


# In[ ]:

#get_ipython().magic(u'matplotlib qt')
radii = data['arr_0'][0, int(np.floor(shape[1]/2)):]*1e5
radii -= radii[0]
fig = plt.figure(figsize=(10.0, 8.0))
plt.plot(radii, wavefront_widths, 'k')
plt.xlim((0,radii[-1]))
plt.ylim((2,6))
plt.xlabel('Radius (cm)')
plt.ylabel(r'Width ($\mu s$)')
# plt.show()


# ## Simulated Window vs Body Wavefront Time

# In[ ]:

#get_ipython().magic(u'matplotlib qt')
signals = [a_vs_t_at_x[122]/abs(a_vs_t_at_x[122]).max(), a_vs_t_at_x[215]/abs(a_vs_t_at_x[215]).max()]
fig = plt.figure(figsize=(10.0, 8.0))
plot.plot_signals(times, signals, tlim=500, ylim=1.1)
plt.ylabel('Normalized Amplitude')
plt.xlabel(r'Time ($\mu s$)')
plt.tight_layout()
## plt.show()


# # Modular Cavity

# ## Quiet Signals

# In[3]:

#get_ipython().magic(u'matplotlib qt')
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\MC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/MC/2015-09-02/"
lv_file = "rf_hammer.npz"
fig = plt.figure(figsize=(20.0, 8.0))
fig.text(0.5, 0.05, r'Time ($ms$)', ha='center', va='center', size=26)

(times, passive_hammer_signals) = labview.load_data(lv_dir, lv_file, channels=[0,1,2,3,4,5,6,7])
plt.subplot(121)
plt.ylabel('Amplitude (V)')
plot.plot_signals(times*1e3, passive_hammer_signals, tlim=51.2, ylim=1.2)

(times, active_hammer_signals) = labview.load_data(lv_dir, lv_file, channels=[8,9,10])
plt.subplot(122)
plot.plot_signals(times*1e3, active_hammer_signals[(0,2),:], tlim=51.2, ylim=12)

# plt.show()


# ## Breakdown vs. Raw

# In[2]:

#get_ipython().magic(u'matplotlib qt')
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\MC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Desktop/Data/MC/2015-12-21/"
lv_files = ["raw_data_2015-12-21@02_28_40.867.npz", "reduced_data_2015-12-21@11_05_57.532.npz"]

fig = plt.figure(figsize=(20.0, 8.0))
fig.text(0.5, 0.05, r'Time ($ms$)', ha='center', va='center', size=26)

(times, preamp_hammer_signals) = labview.load_data(lv_dir, lv_files[0])
plt.subplot(121)
plt.ylabel('Amplitude (V)')
plot.plot_signals(times*1e3, preamp_hammer_signals/np.max(preamp_hammer_signals), tlim=12.8, ylim=1.5)
#plot.plot_signals(times*1e3, preamp_hammer_signals/np.max(preamp_hammer_signals), tlim=(0.04, 0.1), ylim=1.2)

(times, preamp_breakdown_signals) = labview.load_data(lv_dir, lv_files[1])
plt.subplot(122)
#plot.plot_signals(times*1e3, preamp_breakdown_signals, tlim=12.8, ylim=1.2)
plot.plot_signals(times*1e3, preamp_breakdown_signals, tlim=(0.04, 0.1), ylim=1.5)

# plt.show()


# ## Six Downstream, Breakdown Signals

# In[4]:

#get_ipython().magic(u'matplotlib qt')
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\MC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Desktop/Data/MC/2015-12-21/"
lv_files = ["reduced_data_2015-12-21@11_05_57.472.npz",
            "reduced_data_2015-12-21@11_05_57.532.npz",
            "reduced_data_2015-12-21@11_22_07.377.npz",
            "reduced_data_2015-12-21@11_33_25.640.npz",
            "reduced_data_2015-12-21@11_49_41.805.npz",
            "reduced_data_2015-12-21@12_12_39.628.npz"]

fig = plt.figure(figsize=(20.0, 8.0))
fig.text(0.5, 0.05, r'Time ($ms$)', ha='center', va='center', size=26)
fig.text(0.02, 0.5, r'Amplitude ($V$)', ha='center', va='center', size=26, rotation='vertical')
for index,lv_file in enumerate(lv_files):
    (times, breakdown_signals) = labview.load_data(lv_dir, lv_file, t0=40e-6, t1=100e-6, channels=[4,5,6,7])
    plt.subplot(231+index)
    upsampled_signals = np.zeros((np.shape(breakdown_signals)[0], np.shape(breakdown_signals)[1]*2))
    upsampled_times = np.linspace(0, times[-1], np.shape(upsampled_signals)[1])
    for i,signal in enumerate(breakdown_signals):
        upsampled_signals[i] = np.roll(sig.resample(signal, len(signal)*2), -i)
    plot.plot_signals(upsampled_times*1e6, upsampled_signals, tlim=50)


# ## 100 Simulated Sparks: predicted vs. actual

# In[9]:

#get_ipython().magic(u'matplotlib qt')
def plot_source_locations(predicted_xs, predicted_ys, actual_xs, actual_ys, error_radius=0.9, cavity_radius=14.22):
    # center marks
    source_xs = np.hstack((predicted_xs, actual_xs))
    source_ys = np.hstack((predicted_ys, actual_ys))

    mark_radii = np.repeat(10, len(predicted_xs))
    mark_colors = np.repeat(5, len(predicted_xs))
    plt.scatter(predicted_xs, predicted_ys, s=10, c='k', cmap=cm.Set1, alpha=1.0)

    fig = plt.gcf()
    ax = fig.gca()

    # error circles
    patches = []
    for coordinate in zip(predicted_xs,predicted_ys):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, color='k', alpha=0.2)
    collection.set_array(np.repeat(0.5, len(predicted_xs)))
    ax.add_collection(collection)

    # damage marks
    patches = []
    for coordinate in zip(actual_xs,actual_ys):
        patches.append(mpatches.RegularPolygon(coordinate, 5, 0.4))
    collection = mcollections.PatchCollection(patches, color='k', alpha=0.2)
    #collection.set_array(np.repeat(0.5, len(source_xs_0)))
    ax.add_collection(collection)


    # cavity
    cavity=plt.Circle((0,0),cavity_radius,color='k',alpha=0.1)
    ax.add_artist(cavity)

    plt.xlim((-16, 16))
    plt.ylim((-16, 16))

fig = plt.figure(figsize=(17,9))
fig.text(0.5, 0.05, r'x (cm)', ha='center', va='center', size=26)

plt.subplot(121)
plt.ylabel('y (cm)')
spark_locations = np.load("C:\\Users\\peter\\Development\\Dissertation\\data\\MC\\spark_locations.npy")
actual_locations = np.load("C:\\Users\\peter\\Development\\Dissertation\\data\\MC\\actual_locations.npy")
plot_source_locations(spark_locations[:,0], spark_locations[:,1], actual_locations[:,0], actual_locations[:,1])
plt.subplot(122)
spark_locations = np.load("C:\\Users\\peter\\Development\\Dissertation\\data\\MC\\spark_locations_8.npy")
plot_source_locations(spark_locations[:,0], spark_locations[:,1], actual_locations[:,0], actual_locations[:,1])


# ## B=3T Spark Damage

# In[5]:

#get_ipython().magic(u'matplotlib qt')
def plot_source_locations(predicted_xs, predicted_ys, actual_xs, actual_ys, error_radius=0.9, cavity_radius=14.22):
    # center marks
    source_xs = np.hstack((predicted_xs, actual_xs))
    source_ys = np.hstack((predicted_ys, actual_ys))

    fig = plt.gcf()
    ax = fig.gca()

    # error circles
    patches = []
    for coordinate in zip(predicted_xs,predicted_ys):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, color='k', alpha=0.1)
    collection.set_array(np.repeat(0.5, len(predicted_xs)))
    ax.add_collection(collection)

    # damage marks
    plt.scatter(actual_xs, actual_ys, s=10, c='k', cmap=cm.Set1, alpha=1.0)

    # damage stddev
    damage_stddev = 2.2
    stddev_circle=plt.Circle((1.45392578, -1.7784455),damage_stddev,color='r',alpha=0.1)
    ax.add_artist(stddev_circle)

    mark_radii = np.repeat(10, len(predicted_xs))
    mark_colors = np.repeat(5, len(predicted_xs))
    plt.scatter(predicted_xs, predicted_ys, s=10, c='w', cmap=cm.Set1, alpha=1.0)

    plt.xlim((-4, 7))
    plt.ylim((-7, 4))

DS_auto_predictions = np.array([[1.7, -2.7], [1.7, -3.0], [2.9, -0.7], [2.9, -0.3], [0.7, -0.3], [2.3, -1.0]])
DS_manual_predictions = np.array([[1.1, -3.5], [1.1, -3.4], [2.0, -0.1], [2.5, 0.1], [1.4, 0.1], [1.8, -0.8]])

directory = 'C:/Users/peter/Development/Dissertation/Data/MC/'
US_coordinate_file = ''.join((directory, 'US_B=3T_coord_in_cm_aligned.npy'))
DS_coordinate_file = ''.join((directory, 'DS_B=3T_coord_in_cm_aligned.npy'))
US_coordinates = np.load(US_coordinate_file)
DS_coordinates = np.load(DS_coordinate_file)

fig = plt.figure(figsize=(12,6))
fig.text(0.5, 0.05, r'x (cm)', ha='center', va='center', size=26)

plt.subplot(121)
plt.ylabel('y (cm)')
plot_source_locations([], [], US_coordinates[:,0], US_coordinates[:,1])

plt.subplot(122)
#plot_source_locations(DS_manual_predictions[:,0], DS_manual_predictions[:,1], DS_coordinates[:,0], DS_coordinates[:,1])
plot_source_locations(DS_auto_predictions[:,0], DS_auto_predictions[:,1], DS_coordinates[:,0], DS_coordinates[:,1])


# In[41]:

print(np.shape(DS_coordinates))
damage_centroid = np.average(DS_coordinates, axis=0)
damage_stddev = math.sqrt(np.sum(np.sum((DS_coordinates-damage_centroid)**2, axis=1)) / len(DS_coordinates))
print('Damage Centroid:', damage_centroid)
print('Damage Centroid Std. Dev.:', damage_stddev)
print('Damage Centroid Radius (cm):', math.sqrt(np.sum(damage_centroid**2)))
print('Damage Centroid Angle (deg):', 360 + math.atan2(damage_centroid[1], damage_centroid[0]) * 180 / math.pi)

prediction_centroid = np.average(DS_auto_predictions, axis=0)
prediction_stddev = math.sqrt(np.sum(np.sum((DS_auto_predictions-prediction_centroid)**2, axis=1)) / len(DS_auto_predictions))
print('Prediction Centroid:', prediction_centroid)
print('Prediction Centroid Std. Dev.:', prediction_stddev)
print('Prediction Centroid Radius (cm):', math.sqrt(np.sum(prediction_centroid**2)))
print('Prediction Centroid Angle (deg):', 360 + math.atan2(prediction_centroid[1], prediction_centroid[0]) * 180 / math.pi)
print('Centroid Distance:', math.sqrt(np.sum((damage_centroid-prediction_centroid)**2)))


# ## End Plate Transmission

# In[ ]:

fig = plt.figure(figsize=(14.0, 4.0))
fig.text(0.48, 0.05, 'Time (s)', ha='center', va='center')
fig.text(0.01, 0.52, 'Acceleration z-component (m/s^2)', ha='center', va='center', rotation='vertical')
plt.gcf().subplots_adjust(bottom=0.15, left=0.06)

comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
(times, hprf_signals) = comsol.load_data(comsol_dir, "HPRF_end_flange.csv")
plt.subplot(121)
plt.xlim([0,0.005])
plt.ylim([-4,4])
plt.plot(times, hprf_signals[0], 'k')

comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\MC\\"
(times, mc_signals) = comsol.load_data(comsol_dir, "MC_end_plate.csv")
plt.subplot(122)
plt.xlim([0,0.005])
plt.ylim([-4,4])
plt.plot(times, mc_signals[0], 'k')

# plt.show()


# # Dielectric-Loaded High Pressure Cavity

# ## 120 Degree Microphone Rotation Signal Comparison

# In[30]:

#get_ipython().magic(u'matplotlib qt')
def average_signals(directory, files, channels):
    avg_signals = np.zeros((3, 5119))
    for file in files:
        (times, signals) = labview.load_data(directory, file, channels=channels)
        avg_signals += signals
    avg_signals /= len(files)
    return (times, avg_signals)

fig = plt.figure(figsize=(14.0, 5.8))
fig.text(0.53, 0.05, r'Time ($\mu s$)', ha='center', va='center', size=24)
fig.text(0.02, 0.58, 'Normalized Amplitude (V)', ha='center', va='center', rotation='vertical', size=24)

lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\2015-11-12\\Hammer\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-12/Hammer/"
lv_files = [ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("raw_data_") and f.endswith(".npz")) ]
(times, avg_hammer_signals_0deg) = average_signals(lv_dir, lv_files, [0, 1, 2])
times = times*1e6
plt.subplot(231)
plt.xlim([0,200])
plt.ylim([-1.1,1.1])
plt.plot(times, avg_hammer_signals_0deg[0]/np.max(avg_hammer_signals_0deg[0]), 'k')
plt.text(5.0, 0.6, r'Mic #1', size=24)
plt.text(5.0, -0.9, r'$0^\circ$', size=24)
plt.subplot(232)
plt.xlim([0,200])
plt.ylim([-1.1,1.1])
plt.plot(times, avg_hammer_signals_0deg[1]/np.max(avg_hammer_signals_0deg[1]), 'k')
plt.text(5.0, 0.6, r'Mic #2', size=24)
plt.text(5.0, -0.9, r'$120^\circ$', size=24)
plt.subplot(233)
plt.xlim([0,200])
plt.ylim([-1.1,1.1])
plt.plot(times, avg_hammer_signals_0deg[2]/np.max(avg_hammer_signals_0deg[2]), 'k')
plt.text(5.0, 0.6, r'Mic #3', size=24)
plt.text(5.0, -0.9, r'$240^\circ$', size=24)

lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\2015-11-13\\Hammer\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-13/Hammer/"
lv_files = [ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("raw_data_") and f.endswith(".npz")) ]
(times, avg_hammer_signals_120deg) = average_signals(lv_dir, lv_files, [0, 1, 2])
times = times*1e6
plt.subplot(234)
plt.xlim([0,200])
plt.ylim([-1.1,1.1])
plt.plot(times, avg_hammer_signals_120deg[2]/np.max(avg_hammer_signals_120deg[2]), 'k')
plt.text(5.0, 0.6, r'Mic #3', size=24)
plt.text(5.0, -0.9, r'$0^\circ$', size=24)
plt.subplot(235)
plt.xlim([0,200])
plt.ylim([-1.1,1.1])
plt.plot(times, avg_hammer_signals_120deg[0]/np.max(avg_hammer_signals_120deg[0]), 'k')
plt.text(5.0, 0.6, r'Mic #1', size=24)
plt.text(5.0, -0.9, r'$120^\circ$', size=24)
plt.subplot(236)
plt.xlim([0,200])
plt.ylim([-1.1,1.1])
plt.plot(times, avg_hammer_signals_120deg[1]/np.max(avg_hammer_signals_120deg[1]), 'k')
plt.text(5.0, 0.6, r'Mic #2', size=24)
plt.text(5.0, -0.9, r'$240^\circ$', size=24)

plt.gcf().subplots_adjust(bottom=0.15, top=0.95, left=0.09, hspace=0.3, wspace=0.25)
# plt.show()


# ## Microphone Rotation Test Localizations

# In[50]:

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
    collection = mcollections.PatchCollection(patches, cmap=cm.brg, norm=mpl.colors.Normalize(0.,1.), alpha=0.2)
    collection.set_array(np.repeat(0.5, len(source_xs_0)))
    ax.add_collection(collection)

    patches = []
    for coordinate in zip(source_xs_120,source_ys_120):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, cmap=cm.brg, norm=mpl.colors.Normalize(0.,1.), alpha=0.2)
    collection.set_array(np.repeat(1.0, len(source_xs_120)))
    ax.add_collection(collection)

    patches = []
    for coordinate in zip(source_xs_90,source_ys_90):
        patches.append(mpatches.Circle(coordinate, error_radius))
    collection = mcollections.PatchCollection(patches, cmap=cm.brg, norm=mpl.colors.Normalize(0.,1.), alpha=0.2)
    collection.set_array(np.repeat(0.0, len(source_xs_90)))
    ax.add_collection(collection)

    # damage marks
    patches = []
    for coordinate in zip(damage_xs,damage_ys):
        coordinate = np.array(coordinate)
        #patches.append(mpatches.RegularPolygon(coordinate, 5, 0.4))
        patches.append(mpatches.Rectangle(coordinate-[0.04,0.4], 0.08, 0.8))
        patches.append(mpatches.Rectangle(coordinate-[0.4,0.05], 0.8, 0.08))
    collection = mcollections.PatchCollection(patches, color='k', alpha=1.0)
    #collection.set_array(np.repeat(0.5, len(source_xs_0)))
    ax.add_collection(collection)


    # cavity
    origin_x = np.zeros(1)
    origin_y = np.zeros(1)
    cavity_radius = 10000
    #plt.scatter([0, 0], [0, 0], s=[90000, 0], c=[3,1], cmap=cm.Set1, alpha=0.1)
    cavity=plt.Circle((0,0),11.43,color='k',alpha=0.1)
    ax.add_artist(cavity)

    plt.xlim((-16, 16))
    plt.ylim((-16, 16))


# In[32]:

#v_p_SS = 7.0e5  # cm/s
v_p_SS = 5.79e5  # cm/s
#v_p_SS = 4.5e5  # cm/s
#v_p_SS = 3.1e5  # cm/s
#v_p_SS = 2.3e5  # cm/s  Lower computational limit
radius = 11.43  # cm
thickness = 5.08  # cm
settling_time = 1.25e-6

# 0 Degrees
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\2015-11-12\\Breakdown\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-12/Breakdown/"
lv_files = sorted([ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("reduced_data_") and f.endswith(".npz")) ])
rs = [11.0, 11.0, 11.0]  # cm
thetas = np.array([11.5, 19.5, 3.5]) * math.pi / 12.0
xs = list(map(lambda r,theta: r * math.cos(theta), rs, thetas))
ys = list(map(lambda r,theta: r * math.sin(theta), rs, thetas))
mic_coordinates = np.array(list(zip(xs, ys)))
print(mic_coordinates)
spark_locations_0 = np.empty((len(lv_files), 2))
for index,lv_file in enumerate(lv_files):
    (times, breakdown_signals) = labview.load_data(lv_dir, lv_file, channels=[0,1,2])
    dt = times[1] - times[0]
    spark_location = psig.accumulated_correlation(breakdown_signals[:,5:20], dt, mic_coordinates, radius, thickness, thetas, grid_size=100, settling_time=settling_time)
    spark_locations_0[index] = spark_location
    print('.',)
print

# 120 Degrees
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\2015-11-13\\Breakdown\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-13/Breakdown/"
lv_files = sorted([ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("reduced_data_") and f.endswith(".npz")) ])
rs = [11.0, 11.0, 11.0]  # cm
thetas = np.array([19.5, 3.5, 11.5]) * math.pi / 12.0  # 120 degree rotation
xs = list(map(lambda r,theta: r * math.cos(theta), rs, thetas))
ys = list(map(lambda r,theta: r * math.sin(theta), rs, thetas))
mic_coordinates = np.array(list(zip(xs, ys)))
spark_locations_120 = np.empty((7, 2))
for index,lv_file in enumerate(lv_files[:7]):
    (times, breakdown_signals) = labview.load_data(lv_dir, lv_file, channels=[0,1,2])
    dt = times[1] - times[0]
    spark_location = psig.accumulated_correlation(breakdown_signals[:,5:20], dt, mic_coordinates, radius, thickness, thetas, grid_size=100, settling_time=settling_time)
    spark_locations_120[index] = spark_location
    print('.',)
print

# 90 Degrees
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\2015-11-13\\Breakdown\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/HPRF/2015-11-13/Breakdown/"
lv_files = sorted([ f for f in os.listdir(lv_dir) if (path.isfile(path.join(lv_dir,f)) and f.startswith("reduced_data_") and f.endswith(".npz")) ])
rs = [11.0, 11.0, 11.0]  # cm
thetas = np.array([17.5, 1.5, 9.5]) * math.pi / 12.0  # 90 degree rotation
xs = list(map(lambda r,theta: r * math.cos(theta), rs, thetas))
ys = list(map(lambda r,theta: r * math.sin(theta), rs, thetas))
mic_coordinates = np.array(list(zip(xs, ys)))
spark_locations_90 = np.empty((len(lv_files)-7, 2))
for index,lv_file in enumerate(lv_files[7:]):
    (times, breakdown_signals) = labview.load_data(lv_dir, lv_file, channels=[0,1,2])
    dt = times[1] - times[0]
    spark_location = psig.accumulated_correlation(breakdown_signals[:,5:20], dt, mic_coordinates, radius, thickness, thetas, grid_size=100, settling_time=settling_time)
    spark_locations_90[index] = spark_location
    print('.',)
print
print('Done')


# In[51]:

#get_ipython().magic(u'matplotlib qt')
fig = plt.figure(figsize=(14.0, 7.0))
fig.text(0.51, 0.02, 'x (cm)', ha='center', va='center', fontsize=26)
fig.text(0.02, 0.52, 'y (cm)', ha='center', va='center', rotation='vertical', fontsize=26)
plt.gcf().subplots_adjust(bottom=0.15, left=0.06)
plt.subplot(121)
plot_source_locations(spark_locations_0[:,0], spark_locations_0[:,1],
                      spark_locations_120[:,0], spark_locations_120[:,1],
                      spark_locations_90[:,0], spark_locations_90[:,1],
                      [0.8,-0.9,-5.4,-3.2,-4.8], [5.2,4.9,0.6,-5.6,-9.5])
plt.subplot(122)
indicies_0 = np.hstack(((9,10,11,14),))
indicies_120 = np.arange(2, len(spark_locations_120))
indicies_90 = np.hstack(((1,3),np.arange(5,11),(14,16,21,22,23)))
plot_source_locations(spark_locations_0[indicies_0,0], spark_locations_0[indicies_0,1],
                      spark_locations_120[indicies_120,0],spark_locations_120[indicies_120,1],
                      spark_locations_90[indicies_90,0], spark_locations_90[indicies_90,1],
                      [0.8,-0.9,-5.4,-3.2,-4.8], [5.2,4.9,0.6,-5.6,-9.5])


# In[4]:

np.hstack(((1,2,3),np.arange(5,11),(14,16),(21,22,23)))


# # All Seasons Cavity

# ## Distorted Hammer and Spark

# In[3]:

#%matplotlib qt
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\ASC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/ASC/20130306/"
lv_file = "hammer@18_00_00.000.npz"
(times, distorted_hammer_signals) = labview.load_data(lv_dir, lv_file, channels=[1, 2, 3, 4, 5])
fig = plt.figure(figsize=(14.0, 4.0))
#fig.suptitle("RF Hammer Signals", fontsize=12)
plt.subplot(121)
plt.xlabel('Time (s)')
plt.ylabel('Normalized Amplitude (V)')
plot.plot_signals(times, distorted_hammer_signals, tlim=(0.086900, 0.090900), ylim=1.1, norm=True)

lv_file = "spark@18_00_00.000.npz"
(times, distorted_spark_signals) = labview.load_data(lv_dir, lv_file, channels=[4])
plt.subplot(122)
plt.xlabel('Time (s)')
plt.ylabel('Normalized Amplitude (V)')
plot.plot_signals(times, distorted_spark_signals, tlim=(0.070430, 0.074430), ylim=1.1, norm=True)

# plt.show()


# ## Undistorted Hammer and Spark

# In[ ]:

#%matplotlib qt
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\ASC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/ASC/20130920/"
lv_file = "spark_1_36@18_00_00.000.npz"
(times, good_hammer_signals) = labview.load_data(lv_dir, lv_file, channels=[0,1,2,3,4,5])
ymax = np.max(good_hammer_signals)
good_hammer_signals /= ymax
fig = plt.figure(figsize=(14.0, 4.0))
#fig.suptitle("RF Hammer Signals", fontsize=12)
plt.subplot(121)
plt.xlabel('Time (s)')
plt.ylabel('Normalized Amplitude (V)')
plot.plot_signals(times, good_hammer_signals, tlim=(0.086338, 0.090338), ylim=1.1, norm=False)

lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\ASC\\"
lv_file = "spark_1_6@18_00_00.000.npz"
(times, good_spark_signals) = labview.load_data(lv_dir, lv_file, channels=[0,1,2,3,4,5])
ymax = np.max(good_spark_signals)
good_spark_signals /= ymax
plt.subplot(122)
plt.xlabel('Time (s)')
plt.ylabel('Normalized Amplitude (V)')
plot.plot_signals(times, good_spark_signals, tlim=(0.051718, 0.055718), ylim=1.1, norm=False)

# plt.show()


# ## Spark vs Reduced

# In[ ]:

#%matplotlib qt
lv_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\ASC\\"
if platform.system() == 'Linux':
    lv_dir = "/home/lane/Data/ASC/20131011/"
lv_file = "spark_1_6@18_00_00.000.npz"
(times, spark_signals) = labview.load_data(lv_dir, lv_file, channels=[0,1,2,3,4,5])
fig = plt.figure(figsize=(14.0, 4.0))
#fig.suptitle("RF Hammer Signals", fontsize=12)
plt.subplot(121)
plt.xlabel('Time (s)')
plt.ylabel('Amplitude (V)')
plot.plot_signals(times, spark_signals, tlim=(0.0518, 0.0716), ylim=12, norm=False)

lv_file = "reduced_1_6@18_00_00.000.npz"
(times, reduced_signals) = labview.load_data(lv_dir, lv_file, channels=[0,1,2,3,4,5])
plt.subplot(122)
plt.xlabel('Time (s)')
plt.ylabel('Amplitude (V)')
plot.plot_signals(times, reduced_signals, tlim=(0.0518, 0.0716), ylim=12, norm=False)

# plt.show()


# ## End Flange Transmission

# In[ ]:

fig = plt.figure(figsize=(14.0, 4.0))
fig.text(0.48, 0.05, 'Time (s)', ha='center', va='center')
fig.text(0.01, 0.52, 'Acceleration z-component (m/s^2)', ha='center', va='center', rotation='vertical')
plt.gcf().subplots_adjust(bottom=0.15, left=0.06)

comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\HC\\"
(times, hprf_signals) = comsol.load_data(comsol_dir, "HPRF_end_flange.csv")
plt.subplot(121)
plt.xlim([0,0.005])
plt.ylim([-0.5,0.5])
plt.plot(times, hprf_signals[0], 'k')

comsol_dir = "C:\\Users\\peter\\Development\\Dissertation\\data\\ASC\\"
(times, mc_signals) = comsol.load_data(comsol_dir, "ASC_end_flange.csv")
plt.subplot(122)
plt.xlim([0,0.005])
plt.ylim([-0.5,0.5])
plt.plot(times, mc_signals[0], 'k')

plt.show()


# In[ ]:



