#!/usr/bin/python

import json;
import numpy as np  # NumPy (multidimensional arrays, linear algebra, ...)
import numpy.linalg as linalg
import scipy as sp  # SciPy (signal and image processing library)

import os.path
import sys
import re
import math
from bisect import bisect_left, bisect_right

def sum_array(data):
  if (np.size(data) == 0):
    return 0
  return data[0] + sum_array(data[1:])

def mean(data):
  return sum_array(data) / data.size

def variance(data, mean):
  return sum_array(map((lambda x: x**2), map((lambda x: x-mean), data))) \
         / (data.size-1)

def stddev(data, mean):
  return math.sqrt(variance(data, mean))

"""
def polynomial(X, a, b):
  if (a.size == 0):
    return 0
  def polynomial_term(x, a_i, b_i, order):
    return a_i * (x-b_i)**order
  return map(lambda x: np.sum(map((lambda a_i,b_i,i:
      polynomial_term(x, a_i, b_i, i)), a, b, np.arange(a.size))), X)
"""

def polynomial(cs, xs):
  if (cs.size == 0):
    return 0
  def polynomial_term(x, c_i, order):
    return c_i * x**order
  return np.array(map(lambda x: np.sum(map((lambda c_i,i:
      polynomial_term(x, c_i, i)), cs, np.arange(cs.size))), xs))

def polynomials(segments, coefficient_sets, xs):
  poly_segs = np.empty(0)
  for segment,coefficient_set in zip(segments,coefficient_sets):
      poly_segs = np.append(poly_segs, polynomial(coefficient_set, xs[segment[0]:segment[1]]))
  return poly_segs

def gaussian(x, A, B, mu, sigma):
  return A + B/math.sqrt(2*math.pi*sigma**2) \
             * math.exp(-(x-mu)**2/(2*sigma**2))

def gaussian_ssr(gaussian_fit, X, Y):
    A = gaussian_fit[0]
    B = gaussian_fit[1]
    mu = gaussian_fit[2]
    sigma = gaussian_fit[3]
    return np.sum(np.array(map(lambda x,y: (gaussian(x, A, B, mu, sigma)-y)**2, X, Y)))

def reciprocal(cs, xs):
  if (cs.size == 0):
    return 0
  return np.array(map(lambda x: cs[0] + cs[1] / x, xs))

"""
X are parameters and Y are corresponding measurements with estimators
f(x_i) = sum_{j=1...m}(b_j h_j(x_i)), where h_j are m linearly independent
functions. To find the coefficients, b_j, we must solve the linear equation
Y = H B. The least squares solution is the Moore-Penrose Psuedo Inverse of H
times Y: B = (H^T H)^-1 H^T Y = D Y.

In this case h_j(x_i) is a Gaussian. To make the least squares method work we
must first calculate the mean and standard deviation. The mean is found by
treating y_i as a histogram count and then calculating sum(x_i y_i)/sum(y_i).
The standard deviation can be found by solving the Gaussian for it when y_i is
half the max height (when x_i = mean). Since the true mean may not be exactly
where the data mean is, the "adjust" parameter can be used to shift the
calculated mean so as to achieve a better fit.
"""
def fit_gaussian(X, Y, adjust=0):
  # Make an educated guess at the mean
  mu = sum_array(map((lambda x,y: x*y), X, Y)) / sum_array(Y)
  return fit_gaussian_with_mean(X, Y, mu, adjust)

def fit_gaussian_with_mean(X, Y, mu=mean, yerr=1, adjust=0, yoff=True):
  # Determine whether f(mu) should be a peak or trough
  dydx = np.gradient(Y)
  d2ydx2 = np.gradient(dydx)
  concavity = d2ydx2[d2ydx2.size/2]
  # Calculate sigma from average |x| value at half max
  half_max = (np.max(Y) + np.min(Y))/2
  x0 = 0.
  if (concavity < 0.):   # concave downward
    for i in range(X.size)[::-1]:
      if (Y[i] >= half_max):
        x0 = X[i]
        break
    if (x0 == mu):
      for i in range(X.size):
        if (Y[i] >= half_max):
          x0 = 2*mu - X[i]
          break
  else:               # concave upward
    for i in range(X.size)[::-1]:
      if (Y[i] <= half_max):
        x0 = X[i]
        break
    if (x0 == mu):
      for i in range(X.size):
        if (Y[i] <= half_max):
          x0 = 2*mu-X[i]
          break
  x0 += (0.081)*adjust
  # print "".join(("x0: ", str(x0)))
  sigma = (x0-mu)/math.sqrt(math.log(4))
  if sigma == 0:
    sigma = 0.1
  def theta1(x):
    return 1
  def theta2(x):
    return gaussian(x, 0, 1, mu, sigma)
  Ht = np.empty(0)
  if yoff:
    Ht = np.array(np.array([map(theta1, X),map(theta2, X)]))
  else:
    Ht = np.array(np.array([map(theta2, X),]))
  H = np.transpose(Ht)
  if type(yerr) == int:
    yerr = np.ones(len(X))
  V = yerr*yerr*np.identity(len(yerr))
  # print linalg.det(V)
  Vi = linalg.inv(V)
  D = np.dot(linalg.inv(np.dot(Ht,np.dot(Vi,H))),np.dot(Ht,Vi))
  b = np.dot(D, Y)
  parameters = np.empty(0)
  if yoff:
    parameters =  np.array((b[0], b[1], mu, sigma))
  else:
    parameters =  np.array((0, b[0], mu, sigma))
  return parameters

def fit_reciprocal(X, Y, yerr=1, adjust=0):
  def theta1(x):
    return 1.0
  def theta2(x):
    return 1.0/x
  Ht = np.array([map(theta1, X),map(theta2, X)])
  H = np.transpose(Ht)
  # D = linalg.pinv(H)
  if type(yerr) == int:
    yerr = np.ones(len(X))
  V = yerr*yerr*np.identity(len(yerr))
  Vi = linalg.inv(V)
  D = np.dot(linalg.inv(np.dot(Ht,np.dot(Vi,H))),np.dot(Ht,Vi))
  b = np.dot(D, Y)
  return np.array((b[0], b[1]))

"""
X are parameters and Y are corresponding measurements with estimators
f(x_i) = sum_{j=1...m}(b_j h_j(x_i)), where h_j are m linearly independent
functions. To find the coefficients, b_j, we must solve the linear equation
Y = H B. The least squares solution is the Moore-Penrose Psuedo Inverse of H
times Y: B = (H^T H)^-1 H^T Y = D Y.

In this case h_j(x_i) = x_i^j.
"""
def fit_polynomial(X, Y, order=2, yerr=1):
  # print "".join(("Shape of X: ", str(np.shape(X))))
  H = np.empty((X.size, order+1))
  # print "".join(("Initial shape of H: ", str(np.shape(H))))
  for j in range(order+1):
    H[:,j] = map((lambda x: x**j), X)
  # print "".join(("Final shape of H: ", str(np.shape(H))))
  # print "".join(("H:\n", str(H)))
  # return np.array((0))
  D = np.empty((0,0))
  if type(yerr) == int:
    yerr = np.ones(len(X))
    D = linalg.pinv(H)
  else:
    V = yerr*yerr*np.identity(len(yerr))
    Vi = linalg.inv(V)
    Ht = np.transpose(H)
    D = np.dot(linalg.inv(np.dot(Ht,np.dot(Vi,H))),np.dot(Ht,Vi))
  B = np.dot(D, Y)
  # print "".join(("Shape of B: ", str(np.shape(B))))
  # print B
  return B

def fit_spliced_polynomials(X, Y, order, segments):
  fits = map(lambda segment:
    fit_polynomial(X[segment[0]:segment[1]], Y[segment[0]:segment[1]], order),
    segments)
  return fits
