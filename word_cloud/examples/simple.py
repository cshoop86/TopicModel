#!/usr/bin/env python2
"""
Minimal Example
===============
Generating a square wordcloud from the US constitution using default arguments.
"""

from os import path, listdir, makedirs

import matplotlib.pyplot as plt
from wordcloud import WordCloud

import GlobalOption
from ReadAndWrite import get_topic_list


def word_cloud_only():
    # Read the whole text.
    # text = open(path.join(d, 'constitution.txt')).read()
    text = open(path.join(d, 'topic_word')).read()
    wordcloud = WordCloud(font_path=r'C:\Windows\Fonts\DroidSansMono.ttf', ranks_only=True).generate(text)
    return wordcloud


def word_cloud_fre(domain_dir, domain_name):
    '''
    得到某个domain下所有主题的wordcloud对象并返回
    :param domain_dir:
    :param domain_name:
    :return:
    '''
    with open(
            path.join(domain_dir, domain_name + '.twdist_sorted')) as twdist_file:
        wordclouds = []
        texts = get_topic_list(path.join(domain_dir, domain_name + '.twords'))
        frequencys = twdist_file.readlines()
        for text, frequency in zip(texts, frequencys):
            frequency = [float(s) for s in frequency.strip().split()]
            word_fre = list(zip(text, frequency))
            # pprint(word_fre)
            wordclouds.append(
                WordCloud(font_path=r'C:\Windows\Fonts\DroidSansMono.ttf').generate_from_frequencies(word_fre))
    return wordclouds


def word_cloud_fre_all():
    '''
    通过所有domain的wordcloud对象绘制wordcloud图像并保存在相应domain下
    :return:
    '''
    domain_dirs = option.AMC_DOMAINS100
    for domain_name in listdir(domain_dirs):
        domain_dir = path.join(domain_dirs, domain_name)
        wordclouds = word_cloud_fre(domain_dir, domain_name)

        wordcloud_dir = path.join(domain_dir, domain_name + '_wordcloud/')
        if not path.exists(wordcloud_dir):
            makedirs(wordcloud_dir)
        for topic_id, wordcloud in enumerate(wordclouds):
            # dump(wordcloud, open(path.join(wordcloud_dir, 'topic' + str(topic_id)), 'wb'))
            plt.imshow(wordcloud)
            plt.axis("off")
            # plt.show()
            plt.savefig(path.join(wordcloud_dir, 'topic' + str(topic_id)))


d = path.dirname(__file__)
option = GlobalOption()
if __name__ == '__main__':
    word_cloud_fre_all()

