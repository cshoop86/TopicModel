#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = 'https://github.com/apache/spark/blob/master/examples/src/main/python/mllib/latent_dirichlet_allocation_example.py'
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
import sys, os
os.environ['SPARK_HOME'] = '/home/pipi/ENV/spark'
os.environ['JAVA_HOME'] = '/home/pipi/ENV/jdk'
os.environ["PYSPARK_PYTHON"] = '/home/pipi/ENV/ubuntu_env/bin/python'

from pyspark import SparkContext
# $example on$
from pyspark.mllib.clustering import LDA, LDAModel
from pyspark.mllib.linalg import Vectors
# $example off$

if __name__ == "__main__":
    sc = SparkContext(appName="LatentDirichletAllocationExample")  # SparkContext

    # $example on$
    # Load and parse the items
    data = sc.textFile("/home/pipi/files/DATASETS/SparkMLlib/sample_lda_data.txt")
    parsedData = data.map(lambda line: Vectors.dense([float(x) for x in line.strip().split(' ')]))
    # Index documents with unique IDs
    corpus = parsedData.zipWithIndex().map(lambda x: [x[1], x[0]]).cache()

    # Cluster the documents into three topics using LDA
    ldaModel = LDA.train(corpus, k=3)
    exit()

    # Output topics. Each is a distribution over words (matching word count vectors)
    print("Learned topics (as distributions over vocab of " + str(ldaModel.vocabSize())
          + " words):")
    topics = ldaModel.topicsMatrix()
    for topic in range(3):
        print("Topic " + str(topic) + ":")
        for word in range(0, ldaModel.vocabSize()):
            print(" " + str(topics[word][topic]))

    # Save and load model
    ldaModel.save(sc, "target/org/apache/spark/PythonLatentDirichletAllocationExample/LDAModel")
    sameModel = LDAModel\
        .load(sc, "target/org/apache/spark/PythonLatentDirichletAllocationExample/LDAModel")
    # $example off$

    sc.stop()

