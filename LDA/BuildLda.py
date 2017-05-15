#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
__title__ = 'topic model - build lda - 20news dataset'
__author__ = 'pi'
__mtime__ = '12/26/2014-026'
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
from collections import defaultdict
from os import listdir, makedirs, path
import re
import datetime
from sys import maxsize

from sklearn import datasets
import nltk
from gensim import corpora, models
import numpy as np
from scipy import spatial

from ReadAndWrite import get_topic_list_from_row
from AMC_evaluate import cal_domains_topic_coherence
import GlobalOption


def load_texts(dataset_type='train', groups='small'):
    """
    load datasets to bytes list
    :return:train_dataset_bunch.items bytes list
    """
    if groups == 'small':
        groups = ['comp.graphics', 'rec.motorcycles', 'talk.politics.guns']  # 仅用于小数据测试时用, #1368
    elif groups == 'medium':
        groups = ['comp.graphics', 'comp.os.ms-windows.misc', 'comp.sys.ibm.pc.hardware', 'comp.sys.ma c.hardware',
                  'comp.windows.X', 'sci.space']  # 中量数据时用    #3414
    train_dataset_bunch = datasets.load_mlcomp('20news-18828', dataset_type, mlcomp_root='../datasets',
                                               categories=groups)  # 13180
    texts = preprocess_texts(train_dataset_bunch.data)  # bunch.items list of text bytes
    return texts


def load_texts2(filename):
    '''
    从domain filename中读取每个domain的doc到一个列表中
    :return:
    '''
    texts = [line.strip().split() for line in open(filename)]
    # pprint(texts)
    return texts


def preprocess_texts(texts, MIN_WORD_LEN=3):
    """
    texts preprocessing
    :param texts: bytes list
    :return:bytes list
    """
    # texts = utils.simple_preprocess(texts)
    texts = [t.decode(errors='ignore') for t in texts]  # bytes2str

    # lower str & split str 2 word list with sep=... & delete None
    SEPS = '[\s()-/,:.?!\|]\s*'
    texts = [re.split(SEPS, t.lower()) for t in texts]
    for t in texts:
        while '' in t:
            t.remove('')

    # stemming & del stopwords
    # nltk.download()   #then choose the corpus.stopwords
    stopwords = set(nltk.corpus.stopwords.words('english'))  # #127
    stopwords.update(['from', 'subject', 'writes'])  # #129

    word_usage = defaultdict(int)
    COMMON_LINE = len(texts) / 10
    too_common_words = set()
    for t in texts:
        for w in t:
            word_usage[w] += 1
        too_common_words.update([w for w in t if word_usage[w] > COMMON_LINE])  # set(too_common_words)
    # print('too_common_words: #', len(too_common_words), '\n', too_common_words)   #68
    stopwords.update(too_common_words)
    # print('stopwords: #', len(stopwords), '\n', stopwords)  #   #147

    english_stemmer = nltk.SnowballStemmer('english')
    texts = [[english_stemmer.stem(w) for w in t if
              not set(w) & set('@+>0123456789*') and w not in stopwords and len(w) >= MIN_WORD_LEN] for t in
             texts]  # set('+-.?!()>@0123456789*/')
    # print(REDH, 'texts[%d] delete ^alphanum & stopwords & len<%d & stemmed: #' % (test_doc_id, MIN_WORD_LEN),
    # len(texts[test_doc_id]), '\n', texts[test_doc_id])
    return texts


def build_corpus(texts):
    """
    build corpora
    :param texts: bytes list
    :return: corpus DirectTextCorpus(corpora.TextCorpus)
    """

    class DirectTextCorpus(corpora.TextCorpus):  # why do this?
        def get_texts(self):
            return self.input

        def __len__(self):
            return len(self.input)

    corpus = DirectTextCorpus(texts)
    return corpus


def build_id2word(corpus):
    """
    from corpus build id2word=dict
    :param corpus:
    :return:dict = corpus.dictionary
    """
    dict = corpus.dictionary  # gensim.corpora.dictionary.Dictionary
    try:
        dict['anything']
    except:
        pass
        # print("dict.id2token is not {} now")    #why!!!!????
    # print(dict.id2token)
    return dict


def save_corpus_dict(dict, corpus, dict_filename, corpus_filename, print_message_flag=True):
    dict.save(dict_filename)
    if print_message_flag:
        print('dict saved into %s successfully ...' % dict_filename)
    corpora.MmCorpus.serialize(corpus_filename, corpus)
    if print_message_flag:
        print('corpus saved into %s successfully ...' % corpus_filename)
        # corpus.save(fname='./LDA/corpus.mm')  # stores only the (tiny) iteration object


def load_corpus_dict(dict_filename, corpus_filename, print_message_flag=True):
    dict = corpora.Dictionary.load(fname=dict_filename)
    if print_message_flag:
        print('dict load from %s successfully ...' % dict_filename)
    # dict = corpora.Dictionary.load_from_text('./id_word.txt')
    corpus = corpora.MmCorpus(corpus_filename)  # corpora.mmcorpus.MmCorpus
    if print_message_flag:
        print('corpus load from %s successfully ...' % corpus_filename)
    return dict, corpus


def build_doc_word_mat(corpus, model, num_topics):
    """
    build doc_word_mat in topic space
    :param corpus:
    :param model:
    :param num_topics: int
    :return:doc_word_mat np.array (len(topics) * num_topics)
    """
    topics = [model[c] for c in corpus]  # (word_id, weight) list
    doc_word_mat = np.zeros((len(topics), num_topics))
    for doc, topic in enumerate(topics):
        for word_id, weight in topic:
            doc_word_mat[doc, word_id] += weight
    return doc_word_mat


def compute_pairwise_dist(doc_word_mat):
    """
    compute pairwise dist
    :param doc_word_mat: np.array (len(topics) * num_topics)
    :return:pairwise_dist <class 'numpy.ndarray'>
    """
    pairwise_dist = spatial.distance.squareform(spatial.distance.pdist(doc_word_mat))
    max_weight = pairwise_dist.max() + 1
    for i in list(range(len(pairwise_dist))):
        pairwise_dist[i, i] = max_weight
    return pairwise_dist


def closest_texts(corpus, model, original_texts, num_topics, test_doc_id=1, topn=5):
    """
    find the closest_doc_ids for  doc_line[test_doc_id]
    :param corpus:
    :param model:
    :param num_topics:
    :param test_doc_id:
    :param topn:
    :return:
    """
    doc_word_mat = build_doc_word_mat(corpus, model, num_topics)
    pairwise_dist = compute_pairwise_dist(doc_word_mat)
    # print(REDH, 'original texts[%d]: ' % test_doc_id, '\n', original_texts[test_doc_id])
    closest_doc_ids = pairwise_dist[test_doc_id].argsort()
    # return closest_doc_ids[:topn]
    for closest_doc_id in closest_doc_ids[:topn]:
        print('closest doc_line[%d]' % closest_doc_id, '\n', original_texts[closest_doc_id])


def evaluate_model(model):
    """
    計算模型在test data的Perplexity
    :param model:
    :return:model.log_perplexity float
    """
    test_texts = load_texts(dataset_type='test', groups='small')
    test_texts = preprocess_texts(test_texts)
    test_corpus = build_corpus(test_texts)
    return model.log_perplexity(test_corpus)


def test_num_topics():
    dict, corpus = load_corpus_dict()
    print("#corpus_items:", len(corpus))
    for num_topics in [3, 5, 10, 30, 50, 100, 150, 200, 300]:
        start_time = datetime.datetime.now()
        model = models.LdaModel(corpus, num_topics=num_topics, id2word=dict)
        end_time = datetime.datetime.now()
        print("total running time = ", end_time - start_time)
        print('model.log_perplexity for test_texts with num_topics=%d : ' % num_topics, evaluate_model(model))


def build_domain_lda_model(domain_filename, basename, dict_corp_load_flag, lda_save_flag):
    '''
    对某个领域进行lda建模
    :param domain_filename:
    :param basename:
    :return:
    '''
    # texts = load_texts()

    vocab_filename = path.join(path.join(option.TOPICS_DIR, basename), basename) + '.vocab'
    corpus_filename = path.join(path.join(option.TOPICS_DIR, basename), basename) + '.corp'
    if path.exists(vocab_filename) and path.exists(corpus_filename) and dict_corp_load_flag:
        dict, corpus = load_corpus_dict(vocab_filename, corpus_filename, print_message_flag=False)
    else:
        texts = load_texts2(domain_filename)

        corpus = build_corpus(texts=texts)

        dict = build_id2word(corpus)
        tfidf = models.TfidfModel(corpus)
        corpus = tfidf[corpus]  # corpus_tfidf

        if not path.exists(path.join(option.TOPICS_DIR, basename)):
            makedirs(path.join(option.TOPICS_DIR, basename))
        save_corpus_dict(dict, corpus, vocab_filename, corpus_filename)

    # with time_block():
    lda_model = models.LdaModel(corpus, id2word=dict, num_topics=option.num_topics, iterations=option.iterations)
    # lda_model = models.LdaMulticore(corpus, id2word=dict, num_topics=option.num_topics,
    # iterations=option.iterations)

    if not path.exists(option.LDA_MODEL_DIR):
        makedirs(option.LDA_MODEL_DIR)
    lda_model_filename = path.join(option.LDA_MODEL_DIR, basename)
    if not path.exists(lda_model_filename) or lda_save_flag:
        lda_model.save(lda_model_filename)
        # print('lda_model saved into %s successfully ...' % lda_model_filename)

        # print('model.log_perplexity for test_texts', lda_model.log_perplexity(corpus))


# @time_process
def build_domain_lda_models(dict_corp_load_flag=True, lda_save_flag=False):
    DOMAINS_DIR_NAME = option.DOMAINS_DIR
    for basename in listdir(DOMAINS_DIR_NAME):
        abs_filename = path.join(DOMAINS_DIR_NAME, basename)
        build_domain_lda_model(abs_filename, basename, dict_corp_load_flag, lda_save_flag)


def get_domain_lda_topics(lda_model_filename, topic_words_filename):
    '''
    读取文件中的LDA模型，计算主题并存入文件中
    :param lda_model_filename:
    :param topic_words_filename:
    :return:
    '''
    lda_model = models.LdaModel.load(fname=lda_model_filename)
    topics = lda_model.show_topics(num_topics=-1, num_words=option.num_twords, formatted=False)
    # pprint(topics)
    topic_words = [[w_w[1] for w_w in topic] for topic in topics]
    # print(topic_words)
    with open(topic_words_filename, 'w') as topic_words_file:
        for topic in topic_words:
            topic_words_file.write(' '.path.join(topic) + '\n')
            # closest_texts(corpus, model, num_topics, test_doc_id=1, topn=3)


def get_domains_lda_topics():
    '''
    读取domains中所有文件中的LDA模型，计算主题并存入文件中
    :return:
    '''
    lda_model_basenames = [path.splitext(basename)[0] for basename in listdir(option.LDA_MODEL_DIR) if
                           path.splitext(basename)[1] is not '.state']
    if lda_model_basenames == []:
        print('Error : lda_model_basename is []')
        exit()
    for basename in lda_model_basenames:
        LDA_TWORDS_DIR = path.join(option.TOPICS_DIR, basename)
        if not path.exists(LDA_TWORDS_DIR):
            makedirs(LDA_TWORDS_DIR)
        get_domain_lda_topics(path.join(option.LDA_MODEL_DIR, basename),
                              path.join(LDA_TWORDS_DIR, basename) + '.twords')


option = GlobalOption()
if __name__ == '__main__':
    # logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
    # np.random.seed(option.SOME_FIXED_SEED)
    min_coher = maxsize
    for seed in range(1000, 100000, 2000):
        np.random.seed(seed)

        build_domain_lda_models(lda_save_flag=True)

        get_domains_lda_topics()
        average_coher = cal_domains_topic_coherence(option.TOPICS_DIR, option.WORD_COOCCUR_DIR, get_topic_list_from_row)
        if average_coher > min_coher:
            min_coher = average_coher
            optimal_seed = seed
    print(min_coher, optimal_seed)
