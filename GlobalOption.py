#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = ''
__author__ = 'pi'
__mtime__ = '4/22/2015-022'
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


class GlobalOption():
    '''
    定义全局变量、参数
    '''

    def __init__(self):
        # 随机数种子
        self.SOME_FIXED_SEED = 100

        # 文件、目录路径
        self.TOPICS_DIR = r'./output/Electronics/'
        self.LDA_MODEL_DIR = r'./output/lda_model/'
        self.DOMAINS_DIR = r'E:\mine\python_workspace\LDA\input\100Reviews_origin\Electronics/'
        self.WORD_COOCCUR_DIR = r'../AMC/word_cooccur/'
        self.AMC_DOMAINS100 = r'E:\mine\java_workspace\AMC_master\Data\pre_output\Output0\AMC\100Reviews\DomainModels/'
        self.LDA_DOMAINS100 = r'E:\mine\java_workspace\AMC_master\Data\pre_output\Output0\LDA\100Reviews\DomainModels/'
        self.LDA_DOMAINS1000 = r'E:\mine\java_workspace\AMC_master\Data\pre_output\Output0\LDA\1000Reviews\DomainModels'
        self.LDA1000_ALARMCLOCK = r'E:\mine\java_workspace\AMC_master\Data\Input\1000Reviews\Electronics\Alarm Clock'

        self.CORPUS_NP_DIR = r'E:\mine\java_workspace\AMC_master\Data\Input\corpus_NP'
        self.LDA_NONELEC_DOMAINS1000 = r'E:\mine\java_workspace\AMC_master\Data\Input\1000Reviews\Non-Electronics/'

        # LDA训练模型参数
        self.num_topics = 15
        self.num_twords = 20
        self.alpha = 1.0
        self.eta = 0.1
        self.iterations = 50  # lda model训练迭代次数
