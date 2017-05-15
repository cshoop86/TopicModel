#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = ''
__author__ = 'pi'
__mtime__ = '5/4/2015-004'
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
from os import listdir, path, makedirs
from gensim import corpora
from numpy import zeros, savetxt, loadtxt, ndarray
from numpy.ma import dot
from GlobalOption import GlobalOption
from Utility.Colors import REDL, DEFAULT


def get_id_word(vocab_filename):
    '''
    获取每个domain中id_word的对应词典
    :param domain_filename:
    :return:
    '''
    try:
        with open(vocab_filename, encoding='utf-8', errors='ignore') as domain_word_id_file:
            id_word_dict = dict()
            for line in domain_word_id_file:
                id_word_dict[int(line.strip().split(':')[0])] = line.strip().split(':')[1]
    except:
        print(REDL, 'Nocation!!! change to use corpora.Dictionary.load(%s) !!!' % vocab_filename, DEFAULT)
        id_word_dict = corpora.Dictionary.load(vocab_filename)
    return id_word_dict


def get_word_id(vocab_filename):
    '''
    获取每个domain中word_id的对应词典
    :param domain_filename:
    :return:
    '''
    id_word_dict = get_id_word(vocab_filename)
    return {v: k for k, v in id_word_dict.items()}


def get_corpus(corpus_filename):
    '''
    从corpus_filename中获取corpus，每行代表一个doc
    :param corpus_filename:
    :return:[[]]
    '''
    corpus = []
    with open(corpus_filename) as corpus_file:
        for line in corpus_file:
            doc = line.strip().split()
            corpus.append(doc)
    return corpus


def get_corpus_all(corpus_dir, postfix='.docs'):
    '''
    从corpus_dir目录下读取所有domains下的corpus
    :param corpus_dir:
    :return:
    '''
    domain_names = listdir(corpus_dir)
    return [get_corpus(path.join(path.join(corpus_dir, domain_name), domain_name + postfix)) for domain_name in
            domain_names]


def write_word_occur_mat(AMC_TEST_INPUT_DIT, WORD_COOCCUR_DIR='./word_cooccur/'):
    '''
    从测试集中找出word共现矩阵并存档
    :param AMC_TEST_INPUT_DIT:
    :return:
    '''
    domain_filenames = listdir(AMC_TEST_INPUT_DIT)
    # print(domain_filenames)
    domain_filedirs = [path.join(AMC_TEST_INPUT_DIT, domain_filename) for domain_filename in domain_filenames]

    for domain_filedir, domain_filename in zip(domain_filedirs, domain_filenames):
        word_id = get_word_id(path.join(domain_filedir, domain_filename) + '.vocab')
        word_num = len(word_id)
        # print('word_num : ', word_num)  #first doc 103

        # 获取每个domain中doc_word的对应矩阵
        # print(join(domain_filedir, domain_filename + '.docs'))
        domain_file = open(path.join(domain_filedir, domain_filename + '.docs'))
        doc_num = len(domain_file.readlines())
        domain_file.seek(0)
        doc_word_mat = zeros([doc_num, word_num], dtype='line')  # int32
        for doc_id, line in enumerate(domain_file):
            # print(doc_id, domain_filenames[doc_id], line.strip().split(' '))
            for word in line.strip().split(' '):
                doc_word_mat[doc_id][int(word)] = 1
                # doc_word_mat[doc_id][int(word)] += 1
                # print(doc_id, int(word), doc_word_mat[doc_id][int(word)])
        domain_file.close()
        # print(doc_word_mat)

        # 将doc_word矩阵存入文件
        if not path.exists('./doc_word_mat'):
            makedirs('./doc_word_mat')
        doc_word_mat_file = open('./doc_word_mat/' + domain_filename + '_doc_word_mat_file.txt', 'wb')
        savetxt(doc_word_mat_file, doc_word_mat, fmt='%d')
        doc_word_mat_file.close()

        # 计算两个词之间的共现并写入文件
        word_cooccur = dot(doc_word_mat.transpose(), doc_word_mat)
        if not path.exists('./word_cooccur'):
            makedirs('./word_cooccur')
        word_cooccur_file = open(WORD_COOCCUR_DIR + domain_filename + '_word_cooccur_file.txt', 'wb')
        savetxt(word_cooccur_file, word_cooccur, fmt='%d')
        word_cooccur_file.close()


def load_word_occur_mat(word_cooccur_filename):
    '''
    将文件中的word_word共现矩阵读入word_cooccurs矩阵中
    :param AMC_TEST_INPUT_DIT:
    :return:
    '''
    word_cooccur_file = open(word_cooccur_filename, 'rb')
    word_cooccur = loadtxt(word_cooccur_file, dtype='line')
    word_cooccur_file.close()
    # print(word_cooccur)
    return word_cooccur


def get_topic_list_from_col(twords_filename):
    '''
    将tword.txt文件(一列代表一个topic)的内容读入topic_list列表中
    :param twords_filename:
    :return:
    '''
    with open(twords_filename, 'r', encoding='utf-8') as twords_file:
        word_num = len(twords_file.readlines()) - 1  # 第一行是标注信息
        twords_file.seek(0)
        topic_num = twords_file.readline().count('Topic')  # twords_file已经向下移动了一行！

        topic_list = ndarray([topic_num, word_num], dtype=object)
        for word_id, line in enumerate(twords_file):
            for topic_id, word in enumerate(line.strip().split()):
                topic_list[topic_id][word_id] = word
        # print(topic_list)

        # topic_array = loadtxt(twords_file, dtype='S', delimiter='\t', skiprows=1).transpose()
        # print(topic_array)
        # topic_list = topic_array.tolist()
        # print(type(topic_list[0][0]), topic_list[0][0])
        return topic_list


def get_topic_list_from_row(twords_filename, topic_num=None, word_num=None):
    '''
    将tword.txt文件(一行代表一个topic)的内容读入topic_list列表中
    :param twords_filename:
    :return:
    '''
    with open(twords_filename, encoding='utf-8') as twords_file:
        if topic_num is None:
            topic_num = len(twords_file.readlines())
            twords_file.seek(0)
        if word_num is None:
            word_num = len(twords_file.readline().strip().split())
            twords_file.seek(0)
        # topic_ndarray = ndarray([topic_num, word_num], dtype=object)
        topic_list = [line.strip().split() for line in twords_file]
        # pprint(topic_list)
        return topic_list


def get_twdist_ndarray(twdist_filename):
    '''
    从.twdist文件中读取topic中的词分布矩阵并返回
    :param twdist_filename:
    :return:
    '''
    return loadtxt(twdist_filename)


def write_top_twdist(twdist_filename, top_twdist_filename, top_n=20):
    '''
    从twdist_filename中取topic前top_n个词写入top_twdist_filename文件中
    :param top_twdist_filename:
    :return:
    '''
    twndarray = get_twdist_ndarray(twdist_filename)
    savetxt(top_twdist_filename, twndarray[:, 0:top_n], fmt='%f')


def write_top_twdist_all(domain_dirs):
    for domain_name in listdir(domain_dirs):
        domain_name_prefix = path.join(path.join(domain_dirs, domain_name), domain_name)
        twdist_filename = domain_name_prefix + '.twdist_sorted'
        top_twdist_filename = domain_name_prefix + '.top_twdist'
        write_top_twdist(twdist_filename=twdist_filename, top_twdist_filename=top_twdist_filename)


option = GlobalOption()
if __name__ == '__main__':
    domain_dirs = option.AMC_DOMAINS100
    write_top_twdist_all(domain_dirs)

