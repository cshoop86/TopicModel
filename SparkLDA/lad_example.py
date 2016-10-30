#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = 'https://github.com/apache/spark/blob/master/examples/src/main/python/ml/lda_example.py'
__author__ = 'pipi'
__mtime__ = '16-10-25'
__email__ = 'pipisorry@126.com'
# code is far away from bugs with the god animal protecting
    I love animals. They taste delicious.
              ┏┓      ┏┓
            ┏┛┻━━━┛┻┓
            ┃      ☃      ┃
            ┃  ┳┛  ┗┳  ┃
            ┃      ┻      ┃
            ┗━┓      ┏━┛
                ┃      ┗━━━┓
                ┃  神兽保佑    ┣┓
                ┃　永无BUG！   ┏┛
                ┗┓┓┏━┳┓┏┛
                  ┃┫┫  ┃┫┫
                  ┗┻┛  ┗┻┛
"""
from __future__ import print_function

# $example on$
from pyspark.ml.clustering import LDA
# $example off$
from pyspark.sql import SparkSession
#
"""
An example demonstrating LDA.
Run with:
  bin/spark-submit examples/src/main/python/ml/lda_example.py
"""

if __name__ == "__main__":
    spark = SparkSession \
        .builder \
        .appName("LDAExample") \
        .getOrCreate()

    # $example on$
    # Loads data.
    dataset = spark.read.format("libsvm").load("data/mllib/sample_lda_libsvm_data.txt")

    # Trains a LDA model.
    lda = LDA(k=10, maxIter=10)
    model = lda.fit(dataset)

    ll = model.logLikelihood(dataset)
    lp = model.logPerplexity(dataset)
    print("The lower bound on the log likelihood of the entire corpus: " + str(ll))
    print("The upper bound bound on perplexity: " + str(lp))

    # Describe topics.
    topics = model.describeTopics(3)
    print("The topics described by their top-weighted terms:")
    topics.show(truncate=False)

    # Shows the result
    transformed = model.transform(dataset)
    transformed.show(truncate=False)
    # $example off$

    spark.stop()

