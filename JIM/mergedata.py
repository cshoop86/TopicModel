import pandas as pd
import json
f = open('business_result.json')
f2 = open('tips_result.json')
i = 1
slist1 = []
slist2 = []
for line in f:
    s = json.loads(line)
    s1 = pd.Series(s)
    slist1.append(s1)
    if i % 1000 == 0:
        print(str(i))
    i += 1
data1 = pd.DataFrame(slist1)
data1 = data1.loc[:,['business_id','longitude','latitude','categories']]
print(data1)
i = 1
for line in f2:
    s = json.loads(line)
    s1 = pd.Series(s)
    slist2.append(s1)
    if i %1000 == 0:
        print(str(i))
    i += 1
data2 = pd.DataFrame(slist2)

merged = pd.merge(data1,data2,on='business_id')
user_record_cnt = merged['user_id'].value_counts().to_dict()
merged = merged[merged['user_id'].map(lambda  x:20<user_record_cnt[x]<200)]
merged.to_csv('result.csv',sep='\t',encoding='utf-8',header=None,index=None)

