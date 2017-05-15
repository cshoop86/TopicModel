import json
def parse(path):
    g = open(path,'r')
    for l in g:
        yield  json.loads(l)
def read_tips(path):
    f = open('items.csv','w')
    for l in parse(path):

        new_obj  = {item:l[item] for item in l if item not in [
            'likes','type','text'
        ]}
        f.write('\t'.join([new_obj['user_id'], str(new_obj['business_id']), str(new_obj['date'])]) + '\n')
if __name__ == '__main__':
    read_tips('yelp_academic_dataset_tip.json')



