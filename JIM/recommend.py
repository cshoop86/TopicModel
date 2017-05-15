__author__ = 'Yanmei'
import math
import random
import fileinput
import pickle
import numpy as np


def rad(d):
    return d*math.pi/180.0

def getDistance(lat1,lng1,lat2,lng2):
    radLat1 = rad(lat1)
    radLat2 = rad(lat2)
    EARTH_RADIUS = 6378.137

    rad_dist_lat = radLat1 - radLat2
    rad_dist_lng = rad(lng1) - rad(lng2)

    s = 2*math.asin(math.sqrt(math.pow(math.sin(rad_dist_lat/2),2) + math.cos(radLat1)*math.cos(radLat2)*math.pow(math.sin(rad_dist_lng/2),2)))

    s = s * EARTH_RADIUS
    s= round(s*10000) /10000
    return s

def gaussian_random(lat,lng,d):
    sigma_lat = d * 1.0/111
    sigma_lng = 111 * math.cos(lat)

    x_lat = random.gauss(lat,sigma_lat)
    y_lng = random.gauss(lng,sigma_lng)

    while(getDistance(lat,lng,x_lat,y_lng) > d):
        x_lat = random.gauss(lat,sigma_lat)
        y_lng = random.gauss(lng,sigma_lng)

    return x_lat,y_lng

def read_data(document_path):
    users = set()
    locations = set()
    user_location = {}
    count_cubic_u = {}

    for line in fileinput.input(document_path):
        line = line.replace('\n','')
        splits = line.split('\t')
        user_id = splits[5]
        business_id = splits[0]
        # text = splits[4]
        # text = text.split(' ')
        latitude = splits[2]
        longitude = splits[1]
        categories = splits[3]

        categories = categories.replace('[','')
        categories = categories.replace(']','')
        categories = categories.split(', ')

        users.add(user_id)
        locations.add(business_id)

        count_cubic_u.setdefault(user_id,{})
        count_cubic_u[user_id].setdefault(business_id,{})
        count_cubic_u[user_id][business_id]['latitude'] = float(latitude)
        count_cubic_u[user_id][business_id]['longitude'] = float(longitude)
        count_cubic_u[user_id][business_id]['categories'] = categories

        user_location.setdefault(user_id,[])
        user_location[user_id].append(business_id)
    return user_location,count_cubic_u

def gaussian_probability(l,r,par):
    if par['sigma'][r][0] == 0 and par['sigma'][r][1] ==0:
        return 1.0
    elif par['sigma'][r][0] ==0 or par['sigma'][r][1] ==0:
        par['sigma'][r][0] = (par['sigma'][r][0] + par['sigma'][r][1])/2
        par['sigma'][r][1] = par['sigma'][r][0]
    normalization = 1.0/(2*math.sqrt(par['sigma'][r][0])*math.sqrt(par['sigma'][r][1]))
    x = l[0]-par['mu'][r][0]
    y = l[1]-par['mu'][r][1]
    body = (x*x)/(-2*par['sigma'][r][0])+(y*y)/(-2*par['sigma'][r][1])
    mainv = math.exp(body)
    probability = normalization*mainv
    return probability

def top_k_list(par,nearplace,user_id,lat,lon,contents,k):
    prob_dist = {}
    l_q = [lat,lon]

    for place in nearplace:
        place_id = par['location_id'][place]
        place_lat = par['location_lola'][place][1]
        place_lon = par['location_lola'][place][0]
        l = [place_lat,place_lon]

        prob_r = 0.0
        prob_z = 0.0

        for r in range(par['R']):

            prob_r += (par['ur'][user_id][r]*gaussian_probability(l,r,par)*gaussian_probability(l_q,r,par)*par['rv'][r][place_id])
        for z in range(par['T']):
            temp_words = 1.0
            for word in contents:
                word_id = par['categories_id'][word]
                temp_words *= par['zw'][z][word_id]
            temp_words = temp_words**(1/(len(contents)))
            prob_z += temp_words*par['uz'][user_id][z]

        prob_dist[place] = prob_z * prob_r
    #print prob_dist
    prob_dist = sorted(iter(prob_dist.items()),key = lambda prob_dist:prob_dist[1],reverse= True)
    #print prob_dist
    keys = [item[0] for item in prob_dist]
    m = []

    if len(keys) < k:
        return keys
    else:
        for i in range(k):
            m.append(keys[i])
        return m

def get_nearplace(par,lat,lon,d):
    near_place = []
    locations = par['locations']
    for location in locations:
        lon2 = par['location_lola'][location][0]
        lat2 = par['location_lola'][location][1]
        distance =getDistance(lat,lon,lat2,lon2)

        if distance < d:
            near_place.append(location)

    return near_place


def get_candidate(filename):
    v_candis = {}
    with open(filename) as f:
        for line in f:
            vid, candis = line.strip('\n').split('\t')
            candi = candis.split(', ')
            v_candis[vid] = candi
    return v_candis


def evaluate(user_location,count_cubic_u,d,k):
    resultspath  = 'results/'
    model_pickle_path = resultspath + 'model.pickle'
    model_pickle = open(model_pickle_path,'rb')
    par = pickle.load(model_pickle)
    print('loading is done....')
    normalized_parameters(par)

    # print 'hometown pre.....'
    # print par['users'][0]
    # print par['users'][1]
    # print par['user_hometown'][0]
    # print par['user_hometown'][1]

    computehomelocation(par)

    POI_candi = get_candidate('test_candidate.dat')

    users = par['users']
    hometown_total = 0
    outtown_total = 0
    hometown_correct = 0
    outtown_correct = 0
    total = 0
    hit = 0
    # print len(users)
    # print len(user_location)
    num = 0
    for user in user_location:
        user_id = par['user_id'][user]
        try:
            locations = user_location[user]
        except:
            print('wrong')
            continue
        for location in locations:
            # print '^^^^', location
            lat = count_cubic_u[user][location]['latitude']
            lon = count_cubic_u[user][location]['longitude']
            contents = count_cubic_u[user][location]['categories']
            test_lat, test_lon = gaussian_random(lat,lon,d)
            user_home_lat = par['user_hometown'][user_id][0]
            user_home_lon = par['user_hometown'][user_id][1]
            # near_place = get_nearplace(par,test_lat,test_lon,d)
            near_place = POI_candi[location]
            near_place.append(location)
            print("near_place:"+str(len(near_place)))
            num += 1
            results_list = top_k_list(par,near_place,user_id,test_lat,test_lon,contents,k)
            # print 'topk_list....'
            # print results_list
            total += 1
            if location in results_list:
                hit += 1

            '''if getDistance(test_lat,test_lon,user_home_lat,user_home_lon)<100:
                # print '.......<d'
                hometown_total += 1
                if location in results_list:
                    hometown_correct += 1
            else:
                # print '.....>d'
                outtown_total += 1
                if location in results_list:
                    outtown_correct += 1
            if num % 100 == 0:
                print 'the recommender num....:'+str(num)
                print 'hometown corrct:'+str(hometown_correct*1.0/hometown_total)
                try:
                    print 'outtown correct:'+str(outtown_correct*1.0/outtown_total)
                except:
                    print 'outtown correct:'+str('0.00')'''

    print('topk... ', k, '....accuracy.... ', str(hit*1.0/total))
    # print 'hometown accuracy:' + str(hometown_correct *1.0/hometown_total)



def normalized_parameters(par):
    theta = (par['uz'])
    vartheta = (par['ur'])
    # omega = (par['zs'])
    psi = (par['zw'])
    phi = (par['rv'])
    # varphi = (par['zsc'])
    # varphi_sum = (par['zsc_sum'])

    for u in range(par['U']):
        if sum(theta[u]) == 0:
            theta[u] = np.asarray([1.0/len(theta[u]) for _ in range(len(theta[u]))])
        else:
            theta[u] = np.asarray(theta[u])
            theta[u] = 1.0*theta[u] +par['alpha'][0]
            theta[u] = 1.0*theta[u]/sum(theta[u])

    for u in range(par['U']):
        if sum(vartheta[u]) == 0:
            vartheta[u] = np.asarray([1.0/len(vartheta[u]) for _ in range(len(vartheta[u]))])
        else:
            vartheta[u] = np.asarray(vartheta[u])
            vartheta[u] = 1.0*vartheta[u] +par['gamma'][0]
            vartheta[u] = 1.0*vartheta[u]/sum(vartheta[u])

    # for m in range(par['T']):
    #     if sum(omega[m]) == 0:
    #         omega[m] = np.asarray([1.0/len(omega[m]) for _ in range(len(omega[m]))])
    #     else:
    #         omega[m] = np.asarray(omega[m])
    #         omega[m][0] = 1.0*omega[m][0] +par['delta'][0]
    #         omega[m][1] = 1.0*omega[m][1] +par['delta'][1]
    #         total = omega[m][0] + omega[m][1]
    #         omega[m][0] = omega[m][0]*1.0/total
    #         omega[m][1] = omega[m][1]*1.0/total

    for t in range(par['T']):
        if sum(psi[t]) == 0:
            psi[t] = np.asarray([1.0/len(psi[t]) for _ in range(len(psi[t]))])
        else:
            psi[t] = np.asarray(psi[t])
            psi[t] = 1.0*psi[t] +par['eta'][0]
            psi[t] = 1.0*psi[t]/sum(psi[t])

    for r in range(par['R']):
        if sum(phi[r]) == 0:
            phi[r] = np.asarray([1.0/len(phi[r]) for _ in range(len(phi[r]))])
        else:
            phi[r] = np.asarray(phi[r])
            phi[r] = 1.0*phi[r] +par['tau'][0]
            phi[r] = 1.0*phi[r]/sum(phi[r])

    # for m in range(par['T']):
    #     for s in range(par['S']):
    #         for c in range(par['C']):
    #             if varphi_sum[m][s] == 0:
    #                 varphi[m][s][c] = 1.0/par['C']
    #             else:
    #                 varphi[m][s][c] = (varphi[m][s][c] + par['beta'][c])*1.0/(varphi_sum[m][s]+par['C'])
def approximation(value,num):
    return round(value,1)

def computehomelocation(par):
    num = 1

    for u in range(par['U']):
        result = {}
        # result.setdefault()
        for i in range(par['N1'][u]):
            lat = par['lv'][u][i][0]
            lon = par['lv'][u][i][1]
            x = approximation(lat,num)
            y = approximation(lon,num)
            if x in result:
                if y in result[x]:
                    # result[x][y].setdefault([])
                    result[x][y].append(par['lv'][u][i])
                else:
                    result[x][y] =[par['lv'][u][i]]
            else:
                temp_dict = {}
                temp_dict[y] = [par['lv'][u][i]]
                result[x] = temp_dict
        max = 0
        home = []
        for x,y_value in list(result.items()):
            for y,locations in list(y_value.items()):
                if len(result[x][y]) > max:
                    max = len(result[x][y])
                    home_t = []
                    home_t.append(x)
                    home_t.append(y)
                    home = home_t
        sum_x = 0
        sum_y = 0
        if home[0] in result and home[1] in result[home[0]]:

            l = result[home[0]][home[1]]
            for xx in l:
                sum_x += xx[0]
                sum_y += xx[1]
        if not max == 1:

            par['user_hometown'][u][0] = sum_x*1.0/max
            par['user_hometown'][u][1] = sum_y*1.0/max

if __name__ == '__main__':
    user_location,count_cubic_u = read_data('test.dat')
    topk = [1, 5, 10, 15, 20]
    for k in topk:
        evaluate(user_location,count_cubic_u,4,k)








