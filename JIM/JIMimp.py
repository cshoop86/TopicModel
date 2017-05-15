__author__ = 'Yanmei'
import numpy as np
from bisect import bisect
import fileinput
from numpy import *
import math
import random
import pickle

class JIM:
    def getpnas(self,document_path):
        users = set()
        locations  = set()
        dict_categories = []

        count_cubic_u = {}
        user_location = {}
        location_longtitude_latituede = {}

        for line in fileinput.input(document_path):
            line = line.replace('\n','')
            splits = line.split('\t')
            user_id = splits[5]
            business_id = splits[0]
            checkin_time = splits[4]
            # text = text.split(' ')
            latitude = splits[2]
            longitude = splits[1]
            categories = splits[3]


            categories = categories.replace('[','')
            categories = categories.replace(']','')

            categories = categories.split(', ')



            users.add(user_id)
            locations.add(business_id)

            dict_categories.extend(categories)



            count_cubic_u.setdefault(user_id,{})
            count_cubic_u[user_id].setdefault(business_id,{})
            count_cubic_u[user_id][business_id]['latitude'] = float(latitude)
            count_cubic_u[user_id][business_id]['longitude'] = float(longitude)
            count_cubic_u[user_id][business_id]['categories'] =categories

            count_cubic_u[user_id][business_id]['checkin_time'] = checkin_time



            user_location.setdefault(user_id,[])
            user_location[user_id].append(business_id)
            if business_id not in location_longtitude_latituede:
                location_longtitude_latituede[business_id] =[float(longitude),float(latitude)]

            #print count_cubic_u
        #print len(count_cubic_u['Nf0SSRgStO8scYoPSNVqag'])
        users = list(users)
        locations =list(locations)
        dict_categories = list(set(dict_categories))

        print("length of users: "+ str(len(users)))
        print("length of locations: " + str(len(locations)))
        print("length of the dictionary of categories: " +str(len(dict_categories)))

        return count_cubic_u,users,dict_categories,locations,user_location,location_longtitude_latituede

    def InitializeParameter(self,count_cubic_u,users,dict_catagories,locations,user_location,location_longtitude_latituede):
        par = {}
        par['max_iterations'] = 500
        par['T'] = 50   # the number of topics
        par['R'] = 50   # the number of regions
        par['U'] = len(users)  # number of the users

        par['W'] = len(dict_catagories) # the number of words in dict_catagories
        # par['S'] = 2  # the number of the type of the emotions, positive,negative
        par['V'] = len(locations)
        par['alpha'] = [50.0/par['T'] for _ in range(par['T'])]
        par['gamma'] = [50.0/par['R'] for _ in range(par['R'])]


        par['eta'] = [0.01 for _ in range(par['W'])]
        # par['beta'] = [0.01 for _ in range(par['C'])]
        par['tau'] = [0.01 for _ in range(par['V'])]
        par['mu'] = [[0.0 for i in range(2)] for j in range(par['R'])]
        par['sigma'] = [[0.0 for i in range(2)] for j in range(par['R'])]

        par['zw'] = [[0 for w in range(par['W'])] for z in range(par['T'])]
        # par['zs'] = [[0 for s in range(par['S'])] for z in range(par['T'])]
        # par['zsc'] = [[[0 for c in range(par['C'])] for s in range(par['S'])] for m in range(par['T'])]
        par['rv'] = [[0 for v in range(par['V'])] for r in range(par['R'])]
        par['uz'] = [[0 for z in range(par['T'])] for u in range(par['U'])]
        par['ur'] = [[0 for r in range(par['R'])] for u in range(par['U'])]


        par['zw_sum'] = [0 for i in range(par['T'])]
        # par['zs_sum'] =  [0 for line in range(par['T'])]
        # par['zsc_sum'] =[[0 for s in range(par['S'])] for z in range(par['T'])]
        par['rv_sum'] = [0 for i in range(par['R'])]
        par['uz_sum'] = [0 for i in range(par['U'])]
        par['ur_sum'] = [0 for i in range(par['U'])]




        par['N1'] =[len(count_cubic_u[users[i]]) for i in range(len(users))]

        par['users'] = users
        par['user_id'] = {users[i]:i for i in range(len(users))}
        par['locations'] = locations
        par['location_id'] ={locations[i]:i for i in range(len(locations))}
        # par['contendword_id'] = {dict_contents[line]:line for line in range(len(dict_contents))}
        par['categories_id'] = {dict_catagories[i]:i for i in range(len(dict_catagories))}

        par['categoriesdic'] = dict_catagories
        par['location_lola'] = location_longtitude_latituede


        # par['contends'] = [[count_cubic_u[users[line]][user_location[users[line]][j]]['text'] for j in range(par['N1'][line])]for line in range(par['U'])]
        par['categories'] = [[count_cubic_u[users[i]][user_location[users[i]][j]]['categories'] for j in range(par['N1'][i])]for i in range(par['U'])]

        par['l'] = [[user_location[users[i]][j] for j in range(par['N1'][i])] for i in range(len(users))]
        par['lv']= [[[count_cubic_u[users[i]][user_location[users[i]][j]]['latitude'],count_cubic_u[users[i]][user_location[users[i]][j]]['longitude']]
                     for j in range(par['N1'][i])] for i in range(par['U'])]
        par['z'] = [[random.randrange(0,par['T']) for _ in range(par['N1'][u])] for u in range(par['U'])]   # the topic of the item
        par['r'] = [[random.randrange(0,par['R']) for _ in range(par['N1'][u])] for u in range(par['U'])]   # the region of the item
        # par['s'] = [[random.randrange(0,par['S']) for _ in range(par['N1'][u])] for u in range(par['U'])]
        par['user_hometown'] = [[0.0,0.0 ] for _ in range(par['U'])]
        self.user_hometown(par)
        self.initiate_region(par)

        np.set_printoptions(threshold = np.inf)
        np.seterr(divide = 'ignore', invalid = 'ignore')
        # print par['r']
        #print par['lv']
        return par
    def gaussian_probability(self,l,r,par):
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

    def update_parameters(self,par):
        # update the average mu
        num = [0 for _ in range(par['R'])]
        total = [[0 for _ in  range(2)] for _ in range(par['R'])]
        for u in range(par['U']):
            for i in range(par['N1'][u]):
                r = par['r'][u][i]
                total[r][0] += par['lv'][u][i][0]
                total[r][1] += par['lv'][u][i][1]
                num[r] += 1


        # update the covariance sigma
        totalsigma =[[0 for _ in range(2) ] for _ in range(par['R'])]

        for u in range(par['U']):
            for i in range(par['N1'][u]):
                r = par['r'][u][i]
                x = par['lv'][u][i][0] - par['mu'][r][0]
                x = x*x
                y = par['lv'][u][i][1] - par['mu'][r][1]
                y = y*y
                totalsigma[r][0] += x
                totalsigma[r][1] += y

        for r in range(par['R']):
            if num[r] == 0:
                par['mu'][r][0] = 0.0
                par['mu'][r][1] = 0.0
                par['sigma'][r][0] = 0.0
                par['sigma'][r][1] = 0.0
            elif num[r] == 1:
                par['mu'][r][0] = total[r][0]*1.0 /num[r]
                par['mu'][r][1] = total[r][1]*1.0 /num[r]
                par['sigma'][r][0] = 0.0
                par['sigma'][r][1] = 0.0

            else:
                par['mu'][r][0] = total[r][0]*1.0 /num[r]
                par['mu'][r][1] = total[r][1]*1.0 /num[r]
                par['sigma'][r][0] = totalsigma[r][0]*1.0/(num[r]-1)
                par['sigma'][r][1] = totalsigma[r][1]*1.0/(num[r]-1)

    def initial_counts(self,par):
        for u in range(par['U']):
            for i in range(par['N1'][u]):
                topic = par['z'][u][i]
                region = par['r'][u][i]
                # sentiment = par['s'][u][line]
                location = par['l'][u][i]
                location_id = par['location_id'][location]
                # contends = par['contends'][u][line]
                categories = par['categories'][u][i]



                par['rv'][region][location_id] += 1
                par['rv_sum'][region] += 1
                par['uz'][u][topic] += 1
                par['uz_sum'][u] += 1
                par['ur'][u][region] += 1
                par['ur_sum'][u] += 1



                for yy in range(len(categories)):
                    idy = par['categories_id'][categories[yy]]
                    par['zw'][topic][idy] += 1
                    par['zw_sum'][topic] += 1

        self.update_parameters(par)

    def initiate_region(self,par):
        dataset = []
        location_lola_dict = par['location_lola']
        locations = par['locations']
        k = par['R']
        region_cluster = []


        for l in range(len(locations)):
            business_id = locations[l]
            dataset.append(location_lola_dict[business_id])

        dataset = mat(dataset)
        centroids,clusterAssment = self.kmeans(dataset,k)


        for l in range(len(locations)):
            region_cluster.append(clusterAssment[l,0])

        #print region_cluster
        for u in range(par['U']):
            for i in range(par['N1'][u]):
                location = par['l'][u][i]
                location_id = par['location_id'][location]
                par['r'][u][i] = int(region_cluster[location_id])






    def sampling_from_dist(self,prob):
        return bisect(np.cumsum(prob),np.random.rand())

    def gibbssampling(self,par):
        for iteration in range(par['max_iterations']):
            for u in range(par['U']):
                for i in range(par['N1'][u]):
                    location = par['l'][u][i]
                    location_id = par['location_id'][location]

                    old_topic = par['z'][u][i]
                    old_region = par['r'][u][i]

                    categories = par['categories'][u][i]


                    for yy in range(len(categories)):
                        idy = par['categories_id'][categories[yy]]
                        par['zw'][old_topic][idy] -= 1
                        par['zw_sum'][old_topic] -= 1


                    # par['zs'][old_topic][old_sentiment] -= 1
                    par['rv'][old_region][location_id] -= 1
                    par['uz'][u][old_topic] -= 1
                    par['ur'][u][old_region] -= 1


                    par['rv_sum'][old_region] -=1
                    par['uz_sum'][u] -= 1
                    par['ur_sum'][u] -= 1


                    p = [0 for r in range(par['T'])]

                    for t in range(par['T']):


                        p[t] = (par['uz'][u][t]+par['alpha'][t])*1.0
                        p[t] /= (par['uz_sum'][u] + par['T']*par['alpha'][t])
                        temp2 = 1.0
                        for yy in range(len(categories)):
                            idy = par['categories_id'][categories[yy]]
                            temp2 *= (par['zw'][t][idy] + par['eta'][idy])
                            temp2 /= (par['zw_sum'][t]+par['W']*par['eta'][idy])
                        if temp2 <1e-200:

                            temp2 = 0

                        p[t] *= temp2

                    p = np.asarray(p)
                    p = p/p.sum()

                    idxx = self.sampling_from_dist(p)

                    new_topic = idxx


                    p = [0 for r in range(par['R'])]

                    for r in range(par['R']):
                        p[r] = (par['ur'][u][r] + par['gamma'][r]) * 1.0
                        p[r] /= (par['ur_sum'][u] + par['R']*par['gamma'][r])

                        p[r] *= (par['rv'][r][location_id] + par['tau'][location_id])
                        p[r] /= (par['rv_sum'][r] + par['V']*par['tau'][location_id])
                        gaupro = self.gaussian_probability(par['lv'][u][i],r,par)
                        p[r] *=p[r]*gaupro

                    p = np.asarray(p)
                    p = p/p.sum()

                    idyy = self.sampling_from_dist(p)

                    new_region = idyy


                    for yy in range(len(categories)):
                        idy = par['categories_id'][categories[yy]]
                        par['zw'][new_topic][idy] += 1
                        par['zw_sum'][new_topic] += 1

                    par['z'][u][i] = new_topic
                    par['r'][u][i] = new_region

                    par['rv'][new_region][location_id] += 1

                    par['uz'][u][new_topic] += 1
                    par['ur'][u][new_region] += 1

                    # par['zs_sum'][new_topic] +=1
                    par['rv_sum'][new_region] +=1
                    par['uz_sum'][u] += 1
                    par['ur_sum'][u] += 1



            print("iteration "+str(iteration))
            # print par['zs']
            print(par['uz'][0])
            print(par['ur'][0])

            self.update_parameters(par)
            if iteration % 100 ==0:
                print("iteration: "+str(iteration) +"has been down ")
            if iteration > 100:
                # self.output_parameters(par)
                self.output_file(par)

        # self.output_parameters(par)
        self.output_file(par)



    def output_file(self,par):
        resultspath = 'results/'
        temp_pickle_path =resultspath +'model.pickle'
        temp_pickle = open(temp_pickle_path,'wb')
        pickle.dump(par,temp_pickle)
        temp_pickle.close()

    def user_hometown(self,par):
        for u in range(par['U']):
            tempu_1 = 0.0
            tempu_2 = 0.0
            for i in range(par['N1'][u]):
                tempu_1 += par['lv'][u][i][0]
                tempu_2 += par['lv'][u][i][1]

            tempu_1 = tempu_1 /(par['N1'][u])
            tempu_2 = tempu_2 /(par['N1'][u])
            par['user_hometown'][u][0] = tempu_1
            par['user_hometown'][u][1] = tempu_2



    ##------------------------------------------------------------------------------

    # calculate Euclidean distance
    def euclDistance(self,vector1, vector2):
        return sqrt(sum(power(vector2 - vector1, 2)))


    # init centroids with random samples
    def initCentroids(self,dataSet, k):
        numSamples, dim = dataSet.shape
        centroids = zeros((k, dim))
        for i in range(k):
            index = int(random.uniform(0, numSamples))
            centroids[i, :] = dataSet[index, :]
        return centroids

    # k-means cluster
    def kmeans(self,dataSet, k):
        numSamples = dataSet.shape[0]
        # first column stores which cluster this sample belongs to,
        # second column stores the error between this sample and its centroid
        clusterAssment = mat(zeros((numSamples, 2)))
        clusterChanged = True

        ## step 1: init centroids
        centroids = self.initCentroids(dataSet, k)

        while clusterChanged:
            clusterChanged = False
            ## for each sample
            for i in range(numSamples):
                minDist  = 100000.0
                minIndex = 0
                ## for each centroid
                ## step 2: find the centroid who is closest
                for j in range(k):
                    distance = self.euclDistance(centroids[j, :], dataSet[i, :])
                    if distance < minDist:
                        minDist  = distance
                        minIndex = j

                ## step 3: update its cluster
                if clusterAssment[i, 0] != minIndex:
                    clusterChanged = True
                    clusterAssment[i, :] = minIndex, minDist**2

            ## step 4: update centroids
            for j in range(k):
                pointsInCluster = dataSet[nonzero(clusterAssment[:, 0].A == j)[0]]
                centroids[j, :] = mean(pointsInCluster, axis = 0)

        print('Congratulations, cluster complete!')
        return centroids, clusterAssment








if __name__ == '__main__':
    m = JIM()
    count_cubic_u,users,dict_categories,locations,user_location,location_longtitude_latituede=m.getpnas('train.dat')
    par = m.InitializeParameter(count_cubic_u,users,dict_categories,locations,user_location,location_longtitude_latituede)

    m.initial_counts(par)
    m.gibbssampling(par)
    m.output_parameters(par)
    m.output_file(par)













