import numpy as np
import math
def generator(records_by_user):
    all_train_location = set()
    all_test_location = set()
    all_user = set()
    train_records = []
    test_records = []
    user_cnt = 0
    for user in records_by_user:
        user_cnt += 1
        np.random.shuffle(records_by_user[user])
        current_train_records = [record for record in records_by_user[user][
                                                      :int(math.floor(len(records_by_user[user])) * 0.7)]]
        train_records.append(current_train_records)
        for record in current_train_records:
            splits = record.split('\t')
            all_user.add(splits[5])
            all_train_location.add(splits[0])

        # current_test_records = [record for record in records_by_user[user] if record not in current_train_records]
        current_test_records = list()
        for record in records_by_user[user]:
            if record not in current_train_records:
                splits = record.split('\t')
                if splits[0] in all_train_location:
                    current_test_records.append(record)



        test_records.append(current_test_records)
        for record in current_test_records:
            splits = record.split('\t')
            all_test_location.add(splits[0])
    print(len(all_user),len(all_test_location),len(all_train_location),len(all_test_location-all_train_location))
    return all_test_location.issubset(all_train_location),train_records,test_records

def write_data(train_p,test_p,train_records,test_records):
    train_f = open(train_p,'w')
    test_f = open(test_p,'w')
    for records in train_records:
        for record in records:
            train_f.write(record+'\n')
    for records in test_records:
        for record in records:
            test_f.write(record +'\n')
    train_f.close()
    test_f.close()


def read_data(document_path):
    f = open(document_path)
    records_by_user = {}

    for line in f:
        line = line.strip('\n')
        splits = line.split('\t')
        user =splits[5]
        records_by_user.setdefault(user,[])
        records_by_user[user].append(line)
    legal ,train_records, test_records = generator(records_by_user)
    while not legal:
        print('not legal')
        legal ,train_records, test_records = generator(records_by_user)
    print('Now writing....')
    train_p = 'train.dat'
    test_p  = 'test.dat'
    write_data(train_p,test_p,train_records,test_records)
if __name__ == '__main__':
    read_data('result.csv')



