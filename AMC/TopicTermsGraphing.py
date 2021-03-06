#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = ''
__author__ = 'pi'
__mtime__ = '4/3/2015-003'
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
import linecache
from os import makedirs
from os.path import exists, dirname

import networkx as nx
import matplotlib.pyplot as plt
from numpy import zeros, argsort, loadtxt, sort, savetxt
from AMC.ReadAndWrite import get_topic_list_from_col


def write_top_topic_dist(top_n=20,
                         input_filename=r'..\java_workspace\AMC_master\Data\pre_output\Output0\AMC\100Reviews\DomainModels\CellPhone\CellPhone.twdist',
                         output_filename=r'..\java_workspace\AMC_master\Data\pre_output\Output0\AMC\100Reviews\DomainModels\CellPhone\CellPhone.top_twdist'):
    '''
    从topic_dist分布中抽取出topic最相关的top_n个word, 并写入文件
    :return:
    '''
    tword_dist_array = loadtxt(input_filename)
    tword_dist_array = -sort(-tword_dist_array, axis=1)
    savetxt(output_filename, tword_dist_array[:, 0:top_n], fmt='%f')


def get_most_overlap_topics(tword_list):
    '''
    从tword_list中找到word覆盖最多的一些topic
    :param tword_list:
    :return:
    '''
    topic_num = len(tword_list)
    # print(set(tword_list[0]), set(tword_list[10]))
    overlap_topics_cnt = zeros([topic_num, topic_num])
    for topic_id in range(topic_num):
        for topic_id_j in range(topic_id + 1, topic_num):
            # print((set(tword_list[topic_id]).intersection(set(tword_list[topic_id_j]))))
            overlap_topics_cnt[topic_id, topic_id_j] = len(
                set(tword_list[topic_id]).intersection(set(tword_list[topic_id_j])))
            overlap_topics_cnt[topic_id_j, topic_id] = len(
                set(tword_list[topic_id]).intersection(set(tword_list[topic_id_j])))
            # print(topic_id, topic_id_j, overlap_topics_cnt[topic_id, topic_id_j])
    top_overlap = overlap_topics_cnt.argmax(axis=1)
    top_overlap_cnt = [overlap_topics_cnt[line_id, i] for line_id, i in enumerate(top_overlap)]
    print([str(i) + ':' + str(j) for i, j in zip(top_overlap, top_overlap_cnt)])
    print('overlap_topics_cnt[0] : ', argsort(-overlap_topics_cnt[12]))
    return top_overlap


def graph_terms_to_topics(tword_list, topic_ids=None, savefig_filename=None, show_flag=True):
    '''
    绘制topic-terms网络图
    :param tword_list:
    :param topic_ids:
    :param savefig_filename:
    :param show_flag:
    :return:
    '''
    # create a new graph and size it
    G = nx.Graph()
    plt.figure(figsize=(10, 10))

    # generate the edges
    if topic_ids is None:
        topic_ids = range(0, tword_list.shape[0])
    for i in topic_ids:
        topicLabel = "topic " + str(i)
        for term in tword_list[i]:
            G.add_edge(topicLabel, term)

    pos = nx.spring_layout(G)  # positions for all nodes

    # we'll plot topic labels and terms labels separately to have different colours
    g = G.subgraph([topic for topic, _ in pos.items() if "topic " in topic])
    nx.draw_networkx_labels(g, pos, font_color='r',font_size=18, font_weight='bold')
    g = G.subgraph([term for term, _ in pos.items() if "topic " not in term])
    nx.draw_networkx_labels(g, pos)

    # plot edges
    nx.draw_networkx_edges(G, pos, edgelist=G.edges(), alpha=1.0, width=0.5, edge_color='g')

    plt.axis('off')
    if savefig_filename is not None:
        if not exists(dirname(savefig_filename)):
            makedirs(dirname(savefig_filename))
        plt.savefig(savefig_filename, dpi=1000, fmt='png')
    if show_flag:
        plt.show()


def get_must_links(must_links_filename):
    '''
    从文件中读取must_links对，
    :param must_links_filename:
    :return:
    '''
    lines = linecache.getlines(must_links_filename)
    must_links_list = list()
    for line in lines:
        must_links_list.append(line.strip().split())
    # print(must_links_list)
    return must_links_list


def get_cannot_links(cannot_links_filename):
    '''
    从文件中读取cannot_links对，
    :param cannot_links_filename:
    :return:
    '''
    lines = linecache.getlines(cannot_links_filename)
    cannot_links_list = list()
    for line in lines:
        cannot_links_list.append(line.strip().split())
    # print(cannot_links_list)
    return cannot_links_list


def graph_multi_links(*links_list, sub_items=None, savefig_filename=None):
    # generate sub links_list if list2 is not None
    if sub_items:
        links_list = [[link for link in links_list_i if sub_items.intersection(set(link))] for links_list_i in
                      links_list]
    # for links_list_i in links_list: print(links_list_i)

    # create a new graph and size it
    plt.figure(figsize=(15, 10))
    G = nx.Graph()

    # generate the edges for all link_lists
    for links_list_i in links_list:
        for link1, link2 in links_list_i:
            G.add_edge(link1, link2)
            # Compute the clustering coefficient for nodes
            # print(nx.clustering(G, nodes='phone'))

    # plot nodes
    pos = nx.spring_layout(G)  # positions dist for all nodes
    # pos = nx.shell_layout(G)  # positions dist for all nodes
    # pos = nx.circular_layout(G)  # positions dist for all nodes
    nx.draw_networkx_labels(G, pos)  # , font_color='r')

    # we'll plot edges in diff link_list_i separately to have different colours
    edge_color_list = ['g', 'r', 'answers', 'k']
    edge_style_list = ['solid', 'dashed'] # (solid|dashed|dotted,dashdot)
    labels = ['must-links', 'cannot-links']
    # for links_list_i, edge_color in zip(links_list, edge_color_list):
    #     nx.draw_networkx_edges(G, pos, edgelist=links_list_i, alpha=0.4, edge_color=edge_color)
    for (links_list_i, edge_color, edge_style, label) in zip(links_list, edge_color_list, edge_style_list, labels):
        nx.draw_networkx_edges(G, pos, edgelist=links_list_i, alpha=0.8, edge_color=edge_color, style=edge_style, label=label)
    plt.legend(labels)

    plt.axis('off')

    # save figure into file
    if savefig_filename is not None:
        if not exists(dirname(savefig_filename)):
            makedirs(dirname(savefig_filename))
        plt.savefig(savefig_filename, dpi=1000, fmt='png')
    plt.show()


if __name__ == '__main__':
    # 绘制topic_terms
    # twords_filename = r'E:\mine\java_workspace\AMC_master\Data\Output\AMC\100Reviews\DomainModels\foramc\t100w10.twords'
    # twords_filename = r'E:\mine\java_workspace\AMC_master\Data\pre_output\Output0\AMC\100Reviews\DomainModels\CellPhone\CellPhone.twords'
    # tword_list = get_topic_list_from_col(twords_filename)
    # savefig_filename = './graph_terms_to_topics_png/graph_terms_to_topics2'
    # graph_terms_to_topics(tword_list, topic_ids=[0, 10], savefig_filename=savefig_filename)

    # draw similar topics
    # top_overlap = get_most_overlap_topics(tword_list)
    # links_list = zip(range(len(top_overlap)), top_overlap)

    # draw links
    # must_links_filename = r'E:\mine\java_workspace\AMC_master\Data\Output\AMC\100Reviews\DomainModels\foramc\foramc.knowl_mustlinks'
    must_links_filename = r'E:\mine\java_workspace\AMC_master\Data\pre_output\Output0\AMC\100Reviews\DomainModels\CellPhone\CellPhone.knowl_mustlinks'
    must_links_list = get_must_links(must_links_filename)
    # graph_multi_links(must_links_list)
    cannot_links_filename = r'E:\mine\java_workspace\AMC_master\Data\pre_output\Output0\AMC\100Reviews\DomainModels\CellPhone\CellPhone.knowl_cannotlinks'
    cannot_links_list = get_cannot_links(cannot_links_filename)
    # other_links = [['expensive', 'charge'], ['expensive', 'pocket']]
    graph_multi_links(must_links_list, cannot_links_list, sub_items={'price', 'expensive'}, savefig_filename = './graph_links_png/graph_links_price_exp1')


def graph_must_links(must_links_list):
    '''
    :param must_links_list:
    :return:
    '''
    # create a new graph and size it
    G = nx.Graph()
    plt.figure(figsize=(15, 10))
    # generate the edges
    for must1, must2 in must_links_list:
        G.add_edge(must1, must2)
    # Compute the clustering coefficient for nodes
    # print(nx.clustering(G, nodes='phone'))
    pos = nx.spring_layout(G)  # positions for all nodes
    # we'll plot topic labels and terms labels separately to have different colours
    nx.draw_networkx_labels(G, pos)  # , font_color='r')
    # plot edges
    nx.draw_networkx_edges(G, pos, edgelist=G.edges(), alpha=0.4, edge_color='r')  # alpha:The node transparency
    plt.axis('off')
    plt.show()
