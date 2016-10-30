import json
from nltk.tokenize import RegexpTokenizer
import nltk
f = open('business_result.json','w')
def parse(path):
    g = open(path,'r')
    for l in g:
        yield json.loads(l)

def parse2(path):
    g = open(path,'r')
    for l in g:
        yield json.dumps(eval(l))

def category_processing():
    i = 0

    for l in parse('yelp_academic_dataset_business.json'):
        i += 1
        if i % 100 == 0:
            print i
        new_obj ={item:l[item] for item in l}
        raw = ','.join(new_obj['categories'])
        tokenizer = RegexpTokenizer(r'\w+')
        tokens =tokenizer.tokenize(raw)
        new_obj['categories'] = tokens
        json_str = json.dumps(new_obj)
        f.write(json_str +'\n')



if __name__ == '__main__':
    category_processing()


