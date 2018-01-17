#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Some comments from the TA

Going through all the submissions, I've notice that 
everybody have the problem of "slow" code. It might be useful to state 
in the solution a couple of guidelines:

 1) avoid as much as possible for loops and use vectorized expression
 2) use numpy function as much as possible (sum, log, mean, etc...)

 --> numpy is built on top of high perf linear algebra lib (BLAS, CBLAS, 
     LAPACK). Every time you do 1), 2) you are calling efficient 
     C/Fortran code

In particular for this assignment:

 3) you don't need to train a different model for each different 
    smoothing value. Smoothing affects only p
"""


import numpy as np
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.datasets import fetch_20newsgroups
import logging
import sys
from time import time

class MyMultinomialBayesClassifier():
    # For graduate students only
    def __init__(self, smooth=1):
        self._smooth = smooth # This is for add one smoothing, don't forget!
        self._feat_prob = []
        self._class_prob = []
        self._Ncls = []
        self._Nfeat = []

    def train(self, X, y):
        alpha_smooth = self._smooth
        cls = np.unique(y)
        Ncls, Nfeat = len(cls), X.shape[1]
        self._Ncls, self._Nfeat = Ncls, Nfeat
        self._feat_prob = np.zeros((Ncls, Nfeat))
        self._class_prob = np.zeros(Ncls)
        for cls in np.unique(y):
            cid = y == cls
            count = cid.sum()
            freq = np.sum(X[cid, :], axis=0)
            freq_denom = np.sum(freq)
            self._class_prob[cls] = float(count)/len(y)
            self._feat_prob[cls, :] = (freq.astype(float) + alpha_smooth) / (freq_denom + alpha_smooth*Nfeat)

    def predict(self, X):
        pred = []
        for x in X:
            ll = (np.log(self._class_prob) +
                  np.dot(np.log(self._feat_prob), x.T>0))
            pred.append(np.argmax(ll))
        return np.array(pred)

class MyBayesClassifier():
    # For graduate and undergraduate students
    def __init__(self, smooth=1):
        self._smooth = smooth # This is for add one smoothing, don't forget!
        self._feat_prob = []
        self._class_prob = []
        self._Ncls = []
        self._Nfeat = []

    def train(self, X, y):
        alpha_smooth = self._smooth
        cls = np.unique(y)
        Ncls, Nfeat = len(cls), X.shape[1]
        self._Ncls, self._Nfeat = Ncls, Nfeat
        self._feat_prob = np.zeros((Ncls, Nfeat))
        self._class_prob = np.zeros(Ncls)
        for cls in np.unique(y):
            cid = y == cls
            count = cid.sum()
            freq = np.sum(X[cid, :], axis=0)
            self._class_prob[cls] = float(count)/len(y)
            self._feat_prob[cls, :] = (freq.astype(float) + alpha_smooth) / (count + alpha_smooth*2)

    # according bernulli
    def predict(self, X):
        pred = []
        pclass = self._class_prob
        pfeat = self._feat_prob
        for x in X:
            ll = (np.log(pclass) +
                  np.log(pfeat * x + (1 - pfeat)*(1 - x)).sum(axis=1))
            pred.append(np.argmax(ll))
        return np.array(pred)

    @property
    def probs(self):
        return self._class_prob, self._feat_prob


""" 
Here is the calling code

"""

categories = [
        'alt.atheism',
        'talk.religion.misc',
        'comp.graphics',
        'sci.space',
    ]
remove = ('headers', 'footers', 'quotes')

data_train = fetch_20newsgroups(subset='train', categories=categories,
                                shuffle=True, random_state=42,
                                remove=remove)

data_test = fetch_20newsgroups(subset='test', categories=categories,
                               shuffle=True, random_state=42,
                               remove=remove)
print('data loaded')

y_train, y_test = data_train.target, data_test.target

print("Extracting features from the training data using a count vectorizer")
t0 = time()

vectorizer = CountVectorizer(stop_words='english', binary=True)
X_train = vectorizer.fit_transform(data_train.data).toarray()
X_test = vectorizer.transform(data_test.data).toarray()
feature_names = vectorizer.get_feature_names()

for i in range(1, 100):
    clf = MyBayesClassifier(float(i)/100);
    clf.train(X_train,y_train);
    y_pred = clf.predict(X_test)
    print '%.2f %f' %(float(i)/100, np.mean((y_test-y_pred)==0))

