#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = 'Spark MLlib LDA pyspark实例代码'
__author__ = 'pipi'
__mtime__ = '16-10-24'
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
from pyspark import SparkContext
from pyspark.mllib.clustering import LDA, LDAModel
from pyspark.mllib.linalg import Vectors
import os


def config():
    '''
    运行前的参数配置
    '''
    import configparser, os
    SECTION = 'dev_pipi'
    conf = configparser.ConfigParser()
    conf.read(os.path.join(os.path.split(os.path.realpath(__file__))[0], 'config.ini'))

    global corpus_filename, K, alpha, beta, max_iter, seed, checkin_point_interval, optimizer
    corpus_filename = conf.get(SECTION, 'corpus_filename')
    K = conf.getint(SECTION, 'K')
    alpha = conf.getfloat(SECTION, 'alpha')
    beta = conf.getfloat(SECTION, 'beta')
    max_iter = conf.getint(SECTION, 'max_iter')
    seed = conf.getint(SECTION, 'seed')
    checkin_point_interval = conf.getint(SECTION, 'checkin_point_interval')
    optimizer = conf.get(SECTION, 'optimizer')

    # spark environment settings
    # import sys, os
    # os.environ['SPARK_HOME'] = conf.get(SECTION, 'SPARK_HOME')
    # sys.path.append(os.path.join(conf.get(SECTION, 'SPARK_HOME'), 'python'))
    # os.environ["PYSPARK_PYTHON"] = conf.get(SECTION, 'PYSPARK_PYTHON')
    # os.environ['SPARK_LOCAL_IP'] = conf.get(SECTION, 'SPARK_LOCAL_IP')
    # os.environ['JAVA_HOME'] = conf.get(SECTION, 'JAVA_HOME')
    # os.environ['PYTHONPATH'] = '$SPARK_HOME/python/lib/py4j-0.10.3-src.zip:$PYTHONPATH'

    import logging
    logging.basicConfig(filename=os.path.join(os.path.split(os.path.realpath(__file__))[0], '/tmp/log.txt'),
                        level=logging.DEBUG)


config()


def test():
    sc = SparkContext(master='local[4]', appName='lda')
    sc.setLogLevel('ERROR')

    def train():
        data = sc.textFile(corpus_filename).map(lambda line: Vectors.dense([float(i) for i in line.strip().split()]))
        corpus = data.zipWithIndex().map(lambda x: [x[1], x[0]]).cache()
        # print(corpus.take(5))

        lda_model = LDA.train(rdd=corpus, maxIterations=max_iter, seed=seed, checkpointInterval=checkin_point_interval,
                              k=K,
                              optimizer=optimizer, docConcentration=alpha, topicConcentration=beta)
        if os.path.exists('./ldamodel'): __import__('shutil').rmtree('./ldamodel')
        lda_model.save(sc, "./ldamodel")

    # train()

    lda_model = LDAModel.load(sc, "./ldamodel")

    # topic-word分布(未归一化的dist，每列代表一个topic)
    topics = lda_model.topicsMatrix()
    # for tid in range(3):
    #     print('Topic' + str(tid) + ':')
    #     for wid in range(0, lda_model.vocabSize()):
    #         print(' ' + str(topics[wid, tid] / sum(topics[:, tid])))  # 加一个归一化
    #         # print(' ' + str(topics[wid, tid]))

    # topic-word按词序排列分布([词id，按权重从大到小排列], [词在主题上的权重])
    topics_dist = lda_model.describeTopics()
    for tid, topic in enumerate(topics_dist):
        print('Topic' + str(tid) + ':' + '\n', topic)

    # 文档的主题分布(mllib不能，ml才可以)
    # doc_topic = lda_model

    sc.stop()

    # df = pyspark.createDataFrame([[1, Vectors.dense([0.0, 1.0])], [2, SparseVector(2, {0: 1.0})],], ["id", "features"])


if __name__ == '__main__':
    test()
