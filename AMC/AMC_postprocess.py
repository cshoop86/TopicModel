#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = ''
__author__ = 'pi'
__mtime__ = '3/5/2015-005'
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
from os import listdir, path

import GlobalOption


def twords_process(input_filename, output_filename):
    '''
    对齐格式化输出到new文件中
    :param input_file_name:
    :param output_file_name:
    :return:
    '''
    twordFile = open(input_filename)
    new_twordFile = open(output_filename, 'w')
    for line in twordFile:
        for word in line.strip().split('\t'):
            # new_twordFile.write("%-15s" % word)
            new_twordFile.write(word.ljust(15))
        new_twordFile.write('\n')
    twordFile.close()
    new_twordFile.close()


def sorted_topic_word_dist(input_filename, output_filename):
    '''
    将每个主题下的词分布降序排序，存入output_filename
    :param input_filename:
    :param output_filename:
    :return:
    '''
    with open(input_filename) as word_dist_file, open(output_filename, 'w') as sorted_word_dist_file:
        for line in word_dist_file:
            word_dist_list = line.strip().split()
            word_dist_list = sorted([float(word_dist) for word_dist in word_dist_list], reverse=True)
            word_dist_list = [str(f) for f in word_dist_list]
            # print(word_dist_list)
            word_dist_str = ' '.join(word_dist_list) + '\n'
            # print(word_dist_str)
            sorted_word_dist_file.write(word_dist_str)


def sorted_topic_word_dist_all():
    '''
    将所有domain的每个主题下的词分布降序排序，存入output_filename
    '''
    domain_dirs = option.AMC_DOMAINS100
    for domain_filename in listdir(domain_dirs):
        domain_dir = path.join(domain_dirs, domain_filename)
        sorted_topic_word_dist(path.join(domain_dir, domain_filename + '.twdist'),
                               path.join(domain_dir, domain_filename + '.twdist_sorted'))


option = GlobalOption()
if __name__ == '__main__':
    input_filename = r'E:\mine\javaworkspace\AMC_master\Data\Output\AMC\100Reviews\DomainModels\foramc\foramc.twords'
    output_filename = r'E:\mine\javaworkspace\AMC_master\Data\Output\AMC\100Reviews\DomainModels\foramc\foramc_1.twords'

