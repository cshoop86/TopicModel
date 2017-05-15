#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = ''
__author__ = 'pi'
__mtime__ = '3/10/2015-010'
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
from Colors import REDL, DEFAULT, GREENL
from os import listdir
from os.path import join
from math import log

from matplotlib.widgets import Cursor
from matplotlib import pyplot as plt

from GlobalOption import GlobalOption
from TopicModel.AMC.ReadAndWrite import get_word_id, get_topic_list_from_col, load_word_occur_mat


def transform_topic_list(topic_list, vocab_filename):
    '''
    将topic_list中的topic_word转换成topic_wordid存入topic_wid_list
    :param topic_list:
    :param vocab_filename:
    :return:
    '''
    word_id_dict = get_word_id(vocab_filename)
    topic_wid_list = [[word_id_dict[word] for word in topic] for topic in topic_list]
    # print(topic_wid_list)
    return topic_wid_list


def cal_domain_topic_coherence(twords_filename, vocab_filename, word_cooccur_filename, get_topic_list):
    '''
    计算某个domain的topic coherence
    :param twords_filename:
    :return:
    '''
    word_cooccurs = load_word_occur_mat(word_cooccur_filename)

    topic_list = get_topic_list(twords_filename)
    topic_wid_list = transform_topic_list(topic_list, vocab_filename)
    # print(topic_wid_list)

    topic_coher = 0
    for topic in topic_wid_list:
        '''  计算某个topic下词之间的coherence  '''
        # topic_coher = 0
        word_num = len(topic)
        for i in range(1, word_num):
            for j in range(0, i):
                # print(list(word_id_dict.keys())[list(word_id_dict.values()).index(topic[line])], list(word_id_dict.keys())[list(word_id_dict.values()).index(topic[j])])
                if ( word_cooccurs[topic[j], topic[j]] ) < 0:
                    print(word_cooccur_filename)
                    print(topic[j], topic[j], word_cooccurs[topic[j], topic[j]])
                topic_coher += log((word_cooccurs[topic[i], topic[j]] + 1) / word_cooccurs[topic[j], topic[j]])
                # print(topic_coher)
    topic_num = len(topic_wid_list)
    topic_coher /= topic_num
    # print(topic_coher)
    return topic_coher


def cal_domain_topic_coherence_all(TOPIC_OUTPUT_DIR, WORD_COOCCUR_DIR, get_topic_list=get_topic_list_from_col,
                                   merged_flage=False, print_each_coher_flag=False):
    '''
    计算所有domains的主题相合性
    :param TOPIC_OUTPUT_DIR:
    :param WORD_COOCCUR_DIR:
    :param get_topic_list:
    :return:
    '''
    if merged_flage:
        post_fix = '.merged_twords'
    else:
        post_fix = '.twords'
    domain_basenames = listdir(TOPIC_OUTPUT_DIR)
    domain_file_dirs = [join(TOPIC_OUTPUT_DIR, domain_name) for domain_name in domain_basenames]
    total_topic_coher = 0
    domain_topic_coher = []
    for domain_name, domain_filedir in zip(domain_basenames, domain_file_dirs):
        twords_filename = join(domain_filedir, domain_name) + post_fix
        vocab_filename = join(domain_filedir, domain_name) + '.vocab'
        word_cooccur_filename = WORD_COOCCUR_DIR + domain_name + '_word_cooccur_file.txt'
        topic_coher = cal_domain_topic_coherence(twords_filename, vocab_filename, word_cooccur_filename, get_topic_list)
        domain_topic_coher.append((domain_name, topic_coher))

        if print_each_coher_flag:
            print(REDL, 'topic_coher : ', topic_coher, DEFAULT)
        total_topic_coher += topic_coher
    average_topic_coher = total_topic_coher / len(domain_basenames)
    print(GREENL, 'average topic coherence is : ', average_topic_coher, DEFAULT)
    return domain_topic_coher, average_topic_coher


def draw_topic_coherence11(*lists):
    '''
    绘制所有list的折线图
    :param list:
    :return:
    '''
    ax = plt.figure().add_subplot(111)
    for list in lists:
        x = [i[0] for i in list]
        ax.set_xticklabels(x, rotation='vertical')
        # x_new = range(len(list))
        y = [i[1] for i in list]
        ax.plot(y)
    # ax.title('title')
    # ax.xlabel('domain names')
    # ax.ylabel('topic coherence')
    # ax.legend('KBTM', 'LDA')
    plt.show()


def draw_topic_coherence(aver_hlines, topic_coher_list):
    '''
    绘制所有list的折线图
    :param list:
    :return:
    '''
    # plt.figure(figsize=(48,30))
    line_labels = ['KBTM', 'LDA']
    line_styles = ['-', '--']
    line_colors = ['answers', 'g']
    for list, label, line_style, color, hline in zip(topic_coher_list, line_labels, line_styles, line_colors,
                                                     aver_hlines):
        x = [i[0] for i in list]
        plt.xticks(range(len(list)), x, rotation='vertical')
        y = [i[1] for i in list]
        plt.plot(y, linestyle=line_style, label=label)
        plt.hlines(hline, xmin=plt.gca().get_xlim()[0], xmax=plt.gca().get_xlim()[1], linestyles=line_style,
                   colors=color)
    plt.title('Topic Coherence In All Domains')
    plt.xlabel('Domain Names')
    plt.ylabel('Topic Coherence')
    plt.legend()
    Cursor(plt.gca(), vertOn=False, color='r', lw=1)
    plt.show()


option = GlobalOption()
if __name__ == '__main__':
    # AMC_OUTPUT_DIR = r'E:\mine\java_workspace\AMC_master\Data\Output\AMC\100Reviews\DomainModels/'
    AMC_OUTPUT_DIR = option.AMC_DOMAINS100
    LDA_OUTPUT_DIR = option.LDA_DOMAINS100

    amc_domain_topic_coher, amc_average_topic_coher = cal_domain_topic_coherence_all(AMC_OUTPUT_DIR,
                                                                                     WORD_COOCCUR_DIR='./word_cooccur/',
                                                                                     get_topic_list=get_topic_list_from_col,
                                                                                     merged_flage=False,
                                                                                     print_each_coher_flag=False)

    lda_domain_topic_coher, lda_average_topic_coher = cal_domain_topic_coherence_all(LDA_OUTPUT_DIR,
                                                                                     WORD_COOCCUR_DIR='./word_cooccur/',
                                                                                     get_topic_list=get_topic_list_from_col,
                                                                                     merged_flage=False,
                                                                                     print_each_coher_flag=False)
    # pprint(amc_domain_topic_coher)
    # pprint(lda_domain_topic_coher)
    aver_hlines = [amc_average_topic_coher, lda_average_topic_coher]
    topic_coher_list = [amc_domain_topic_coher, lda_domain_topic_coher]
    draw_topic_coherence(aver_hlines, topic_coher_list)

