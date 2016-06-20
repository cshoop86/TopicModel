
# include "../standard_package/standard_include.cpp"
//# include "infomap_single_function.cpp"

bool separate_strings_tree_file(string &b, deque<string> & v) {
    	
	v.clear();
	string s1;
	
	for(int i=0; i<int(b.size()); i++) {
		
		if(b[i]==' ' || b[i]=='\t' || b[i]=='\n' || b[i]==',' || b[i]==':' || b[i]=='"') {
			if(s1.size()>0)
				v.push_back(s1);
			s1.clear();
            
		} else
			s1.push_back(b[i]);
		
		if(i==int(b.size())-1) {
        
			if(s1.size()>0)
				v.push_back(s1);			
			s1.clear();
            
		}
	}
    
	return true;
}

void get_the_partition(string tree_file, mapii & hard_memberships) {
    
    
    hard_memberships.clear();
    
    // getting the partition
    ifstream tree_in(tree_file.c_str());
    
    string gins;
    while(getline(tree_in, gins)) if(gins.size()>0 and gins[0]!='#') {
        deque<string> vss;
        separate_strings_tree_file(gins,  vss);
        if(vss.size()!=4) {
            cerr<<"error in tree file"<<endl;
            exit(-1);
        }
        int node_from_tree_file= atoi(vss[3].c_str());
        int cluster= atoi(vss[0].c_str());
        hard_memberships[node_from_tree_file] = cluster;
    }
    
    tree_in.close();
}


int main(int argc, char * argv[]) {
	
	
    if(argc<4) {
        
        cerr<<argv[0]<<" [sig_words.tree] [word_wn_count.txt] [max_words]"<<endl;
        cerr<<"output is infomap.part and infomap-words.part"<<endl;
        return -1;
    }
    
    string infile(argv[1]);
    string max_words(argv[3]);
    int max_words_int= cast_int(cast_string_to_double(max_words));
    mapii hard_mems;
    
    // hard_memberships[wn]=cluster_id
    get_the_partition(infile, hard_mems);
    
    // writing partition
    map<int, DI> partition;
    IT_loop(mapii, itm, hard_mems) {
        if(partition.count(itm->first)==0) {
            DI voiddi;
            partition.insert(make_pair(itm->second, voiddi));
        }
        partition[itm->second].push_back(itm->first);
    }
    ofstream pout("infomap.part");
    for(map<int, DI>::iterator itm= partition.begin(); itm!=partition.end(); itm++) {
        prints(itm->second, pout);
    }
    pout.close();
    cout<<"partition (word numbers) is in infomap.part"<<endl;

    
    
    // getting word information 
    mapis wn_str;
    mapii wn_occ;
    cout<<"opening "<<argv[2]<<endl;
    ifstream gin(argv[2]);
    string word_str;
    int wn, occ;
    while(gin>>word_str) {
        gin>>wn;
        gin>>occ;
        wn_str[wn]=word_str;
        wn_occ[wn]=occ;
    }
    
    // writing it in words
    pout.open("infomap-words.part");
    for(map<int, DI>::iterator itm= partition.begin(); itm!=partition.end(); itm++) {
        deque<pair<int, int> > occ_wn;
        RANGE_loop(i, itm->second) {
            occ_wn.push_back(make_pair(-wn_occ.at(itm->second[i]), itm->second[i]));
        }
        sort(occ_wn.begin(), occ_wn.end());
        
        cout<<"# words: "<<occ_wn.size()<<endl;
        for(int i=0; i<min(max_words_int, int(occ_wn.size())); i++) {
            pout<<wn_str.at(occ_wn[i].second)<<" ";
            cout<<wn_str.at(occ_wn[i].second)<<" ";
        }
        pout<<endl<<endl;
        cout<<endl<<endl;

    }
    pout.close();

    cout<<"partition (words) is in infomap-words.part"<<endl;

       
    return 0;
}

