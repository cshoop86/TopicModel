#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = ''
__author__ = 'pi'
__mtime__ = '4/29/2015-029'
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
from os import makedirs, listdir
from os.path import exists, join

import matplotlib.pyplot as plt
from numpy import loadtxt

import GlobalOption


def draw_doc_topic_num(domain_doc_topic_filename, domain_doc_topic_sum_pic_filename):
    '''

    :return:
    '''
    doc_topic_dist_ndarray = loadtxt(domain_doc_topic_filename)
    doc_topic_sum = sum(doc_topic_dist_ndarray, 1)
    print(doc_topic_sum)
    index = list(range(len(doc_topic_sum)))
    plt.bar(index, doc_topic_sum)
    plt.savefig(domain_doc_topic_sum_pic_filename)
    plt.show()


option = GlobalOption()


def draw_all_doc_topic_num(suffix='.twdist'):  # '.dtopicdist'
    '''
    绘制所有domains的每个domain下topic的doc_weight
    :return:
    '''
    domain_doc_topic_sum_pic_dir = r'./graph_doc_topic_sum/'
    if not exists(r'./graph_doc_topic_sum/'):
        makedirs(r'./graph_doc_topic_sum/')

    # domain_doc_topic_dir = option.LDA_DOMAINS1000
    domain_doc_topic_dir = option.AMC_DOMAINS100
    for basename in listdir(domain_doc_topic_dir):
        domain_doc_topic_filename = join(join(domain_doc_topic_dir, basename), basename + suffix)
        # print(domain_doc_topic_filename)
        domain_doc_topic_sum_pic_filename = join(domain_doc_topic_sum_pic_dir, basename) + '.png'
        # print(domain_doc_topic_sum_pic_filename)
        draw_doc_topic_num(domain_doc_topic_filename, domain_doc_topic_sum_pic_filename)


draw_all_doc_topic_num()