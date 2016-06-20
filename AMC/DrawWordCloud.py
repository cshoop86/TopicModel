#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = ''
__author__ = 'pi'
__mtime__ = '5/5/2015-005'
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
from os import path, listdir, makedirs
from random import Random

import matplotlib.pyplot as plt
from wordcloud import WordCloud

from AMC.ReadAndWrite import get_topic_list_from_col, get_topic_list_from_row
from GlobalOption import GlobalOption


def black_color_func(word=None, font_size=None, position=None,
                     orientation=None, font_path=None, random_state=None):
    if random_state is None:
        random_state = Random()
    return "hsl(%d, 0%%, 0%%)" % 0


def word_cloud_word_only():
    # Read the whole text.
    # text = open(path.join(d, 'constitution.txt')).read()
    text = open(path.join(d, 'topic_word')).read()
    wordcloud = WordCloud(font_path=r'C:\Windows\Fonts\DroidSansMono.ttf', ranks_only=True).generate(text)
    return wordcloud


def word_cloud_frequency(domain_dir, domain_name, merged_flag):
    '''
    得到某个domain下所有主题的wordcloud对象并返回
    :param domain_dir:
    :param domain_name:
    :return:
    '''
    if merged_flag:
        twords_postfix = '.merged_twords'
        twdist_postfix = '.merged_top_twdist'
        get_topic_list_fun = get_topic_list_from_row
    else:
        twords_postfix = '.twords'
        twdist_postfix = '.twdist_sorte d'
        get_topic_list_fun = get_topic_list_from_col
    with open(
            path.join(domain_dir, domain_name + twdist_postfix)) as twdist_file:
        wordclouds = []
        texts = get_topic_list_fun(path.join(domain_dir, domain_name + twords_postfix))
        frequencys = twdist_file.readlines()
        for text, frequency in zip(texts, frequencys):
            frequency = [float(s) for s in frequency.strip().split()]
            word_fre = list(zip(text, frequency))
            word_fre.sort(key=lambda x: x[1], reverse=True)
            # pprint(word_fre)
            wordclouds.append(
                WordCloud(font_path=r'C:\Windows\Fonts\DroidSansMono.ttf', background_color='white',
                          color_func=black_color_func).generate_from_frequencies(word_fre))
            # 频率没有效果
    return wordclouds


def word_cloud_fre_all(domain_dirs, merged_flag=False):
    '''
    通过所有domain的wordcloud对象绘制wordcloud图像并保存在相应domain下
    :return:
    '''
    for domain_name in listdir(domain_dirs):
        domain_dir = path.join(domain_dirs, domain_name)
        wordclouds = word_cloud_frequency(domain_dir, domain_name, merged_flag)

        if merged_flag:
            wordcloud_dir = path.join(domain_dir, domain_name + '_merged_wordcloud1/')
        else:
            wordcloud_dir = path.join(domain_dir, domain_name + '_wordcloud1/')

        if not path.exists(wordcloud_dir):
            makedirs(wordcloud_dir)

        for topic_id, wordcloud in enumerate(wordclouds):
            # dump(wordcloud, open(path.join(wordcloud_dir, 'topic' + str(topic_id)), 'wb'))
            plt.imshow(wordcloud)
            plt.axis("off")
            # plt.show()
            plt.savefig(path.join(wordcloud_dir, 'topic' + str(topic_id)))

        print(domain_name, ' complete!!!')

        break


d = path.dirname(__file__)
option = GlobalOption()
if __name__ == '__main__':
    word_cloud_fre_all(option.AMC_DOMAINS100, merged_flag=True)

