import json
f = open('items.csv')
w_out = open('tips_result.json','w')
for line in f :
    line = line.replace('\n','')
    splits = line.split('\t')
    json_str = {}
    json_str['user_id'] = splits[0]
    json_str['business_id'] = splits[1]
    json_str['date'] = splits[2]
    json_str2 = json.dumps(json_str)
    w_out.write(json_str2+'\n')

# from bisect import bisect
# import numpy as np
#
# def sampling_from_dist(prob):
#         return bisect(np.cumsum(prob),np.random.rand())
# if __name__ == '__main__':
#     prob = [0.1,0.1,0.1,0.3,0.4]
#     print sampling_from_dist(prob)



