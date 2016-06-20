#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = ''
__author__ = 'pi'
__mtime__ = '5/12/2015-012'
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
from itertools import chain
from os import listdir, path
from pprint import pprint

from numpy import loadtxt, savetxt, log, sum, argmin, min, square, sqrt, array
from scipy import spatial
from scipy.special._ufuncs import kl_div
from GlobalOption import GlobalOption

from TopicModel.AMC.ReadAndWrite import get_topic_list_from_col


def kl(P, Q):
    '''
    计算两个ndarray的kl散度
    KL散度仅当概率P和Q各自总和均为1，且对于任何i皆满足Q(i)>0及P(i)>0时，才有定义
    '''
    return sum(P * log(P / Q)) if (P > 0).all() and (Q > 0).all() else None
    # return sum(where((P > 0).all() and (Q > 0).all(), P * log(P / Q), None), axis=0)


def cal_topics_similar(tw_dist_filename, topic_similar_mat_filename=None):
    '''
    计算topic_word分布矩阵所有topic_word分布两两之间的相似度-kl散度
    :param tw_dist_filename:
    :param topic_similar_mat_filename:
    :return:
    '''
    tw_dist_ndaray = loadtxt(tw_dist_filename)

    # 方法1（pdist只能计算对称kl散度）
    topic_similar_mat = spatial.distance.pdist(tw_dist_ndaray,
                                               metric=lambda P, Q: ((sum(kl_div(P, Q)) + sum(kl_div(Q, P))) / 2))

    # # #方法2（对称kl散度，自定义kl函数）
    # topic_similar_mat = spatial.distance.pdist(tw_dist_ndaray,
    # metric=lambda P, Q: ((kl(P, Q) + kl(Q, P)) / 2))

    # # 方法3（非对称kl散度）
    # topic_similar_mat = zeros([len(tw_dist_ndaray), len(tw_dist_ndaray)])
    # for i_id, i in enumerate(tw_dist_ndaray):
    # for j_id, j in enumerate(tw_dist_ndaray):
    # if i_id != j_id:
    # topic_similar_mat[i_id, j_id] = stats.entropy(tw_dist_ndaray[i_id], tw_dist_ndaray[j_id])
    # # topic_similar_mat[i_id, j_id] = sum(kl_div(tw_dist_ndaray[i_id], tw_dist_ndaray[j_id]))

    topic_similar_mat = spatial.distance.squareform(topic_similar_mat)
    max_dist = topic_similar_mat.max()
    for i in range(len(topic_similar_mat)):
        topic_similar_mat[i, i] = max_dist + 1
    # print(topic_similar_mat)

    # savetxt(topic_similar_mat_filename, topic_similar_mat, fmt='%.8f')
    return topic_similar_mat


def merge_similar_topics(topic0_list, topic0_dist, topic1_list, topic1_dist):
    '''
    将两个相似的topic合并(只合并了前option.num_twords个词！)，并返回合并且按分布排序后前option.num_twords个topic及新的topic分布
    :param topic0_list:
    :param topic0_dist:
    :param topic1_list:
    :param topic1_dist:
    :return:
    '''
    topic_dict = dict(zip(topic0_list, topic0_dist))
    topic1_dict = dict(zip(topic1_list, topic1_dist))
    for word in topic1_dict:
        if word in topic_dict:
            topic_dict[word] = sqrt((square(topic1_dict[word]) + square(topic_dict[word])) / 2)
            # topic_dict[word] = max(topic1_dict[word], topic_dict[word])
        else:
            topic_dict[word] = topic1_dict[word]
    d = sorted(topic_dict.items(), key=lambda x: x[1], reverse=True)[0:option.num_twords]
    merged_topic_list = [item[0] for item in d]
    merged_topic_dist = [item[1] for item in d]
    topic_dist_sum = sum(merged_topic_dist)
    merged_topic_dist = [i / topic_dist_sum for i in merged_topic_dist]
    # print(d, merged_topic_list, merged_topic_dist)
    return merged_topic_list, merged_topic_dist


def merge_similar_topics_all(domain_dirs):
    '''
    合并所有domain下相似主题，并存入.merged_twords文件中
    :return:
    '''
    for domain_name in listdir(domain_dirs):
        domain_name_prefix = path.join(path.join(domain_dirs, domain_name), domain_name)
        twdist_filename = domain_name_prefix + '.twdist'

        # topic_similar_mat_filename = domain_name_prefix + '.tsimilar'
        topic_similar_mat = cal_topics_similar(twdist_filename)
        # print(topic_similar_mat)

        # 寻找相互最相似的两个主题pair的list
        tmp_most_similar = argmin(topic_similar_mat, axis=1)
        # print(tmp_most_similar)
        tmp_most_similar_value = min(topic_similar_mat, axis=1)
        # print(tmp_most_similar_value)
        KL_SIMILAR_TRESHOLE = 3.0
        most_similar_topic_pair = [(i, tmp_most_similar[i]) for i in range(len(tmp_most_similar)) if
                                   i == tmp_most_similar[tmp_most_similar[i]] and tmp_most_similar_value[
                                       i] < KL_SIMILAR_TRESHOLE and i < tmp_most_similar[i]]
        # print(domain_name, ' most_similar_topic_pairs : ', most_similar_topic_pair)

        top_topic_list = get_topic_list_from_col(domain_name_prefix + '.twords')
        new_top_topic_list = [list(top_topic_list[i]) for i in range(len(top_topic_list)) if
                              i not in set(chain.from_iterable(most_similar_topic_pair))]

        # 相似度低，不用合并的主题
        twdist_ndarray = loadtxt(domain_name_prefix + '.twdist_sorted')
        new_top_twdist_list = [list(twdist_ndarray[i])[0:option.num_twords] for i in range(len(top_topic_list)) if
                                  i not in set(chain.from_iterable(most_similar_topic_pair))]

        # 合并最相似的两个主题pair并添加到新top_topic_list中
        for i, j in most_similar_topic_pair:
            merged_topic_list, merged_topic_dist = merge_similar_topics(top_topic_list[i], twdist_ndarray[i],
                                                                        top_topic_list[j], twdist_ndarray[j])
            new_top_topic_list.append(merged_topic_list)
            new_top_twdist_list.append(merged_topic_dist)

        # 存储合并后的主题及其概率分布
        new_twords_filename = domain_name_prefix + '.merged_twords'
        # savetxt(new_twords_filename, new_top_topic_list, fmt='%s')
        new_twdist_filename = domain_name_prefix + '.merged_top_twdist'
        # pprint(new_top_twdist_list)
        savetxt(new_twdist_filename, (new_top_twdist_list[0], new_top_twdist_list[1][1:]), fmt='%f')

        # break


def cal_redundancy(domain_dirs):
    '''
    计算所有domain下主题的冗余度
    :return:
    '''
    topic_similar_sum = 0.0
    for domain_name in listdir(domain_dirs):
        domain_name_prefix = path.join(path.join(domain_dirs, domain_name), domain_name)
        twdist_filename = domain_name_prefix + '.merged_top_twdist'
        # twdist_filename = domain_name_prefix + '.twdist'
        # twdist_filename = domain_name_prefix + '.top_twdist'
        topic_similar_mat = cal_topics_similar(twdist_filename)
        topic_similar_sum += sum(topic_similar_mat) / len(topic_similar_mat)
    print(topic_similar_sum)


option = GlobalOption()
if __name__ == '__main__':
    '''
    之后可以进行TopicEvaluate或者DrawWordCloud
    '''
    merge_similar_topics_all(option.AMC_DOMAINS100)
    cal_redundancy(option.AMC_DOMAINS100)
