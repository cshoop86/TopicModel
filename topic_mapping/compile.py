
import sys
import os
import inspect

cur_dir= os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
print(cur_dir, '<<')

print('compiling Infomap')

os.chdir('./Sources/infomap')

os.system('make clean')
os.system('make lib')


#print 'leaving directory: ',
#os.system('pwd')
os.chdir('../..')
#print 'moved back to directory: ',
#os.system('pwd')
os.system('rm -r bin/')
os.system('mkdir bin')


#
print('compiling topic mapping')
os.system('g++ -O3 -funroll-loops -Wall -o bin/topicmap ./Sources/TopicMapping/docmap.cpp -DNS_INFOMAP -ISources/infomap/include -LSources/infomap/lib -lInfomap -fopenmp')


if os.path.isfile('./bin/topicmap') == False:
    print('No worries. We try again')
    os.system('g++ -O3 -funroll-loops -Wall -o bin/topicmap ./Sources/TopicMapping/docmap.cpp -DNS_INFOMAP -ISources/infomap/include -LSources/infomap/lib -lInfomap')
    if os.path.isfile('./bin/topicmap'):
        print('fixed!')
    else:
        print('Ops... It did not compile. Please contact me.')
        exit(-1)





print('compiling alpha_optimization')
os.system('g++ -O3 -funroll-loops -Wall -o bin/opt_alpha ./Sources/TopicMapping/optimize_alpha.cpp')

print('compiling pajek formatter')
os.system('g++ -O3 -funroll-loops -Wall -o bin/edges2pajek ./Sources/SingleSliceInfomap/pajek_format.cpp')

print('compiling partition converter')
os.system('g++ -O3 -funroll-loops -Wall -o bin/tree2part ./Sources/SingleSliceInfomap/get_partition.cpp')

print('compiling show topic info')
os.system('g++ -O3 -funroll-loops -Wall -o bin/show_topic_info ./Sources/TopicMapping/show_topic_info.cpp')



print('\n\n\n============================================== ')
print('Running code with no arguments: ')
os.system('./bin/topicmap')

'''
print '\n\n\n============================================== '
print 'This program has been installed in:'
print cur_dir
print 'Please do not rename/move the folder "bin" and its content from there.'
print 'Since topicmap calls:'
print infomap_path
print 'renaming the executables breaks the code.'
print 'If you are not happy with the current location,'
print 'please move the entire current folder to the desired location',
print 'and run this script again.'
'''














