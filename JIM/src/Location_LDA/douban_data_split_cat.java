package Location_LDA;

import input_output_interface.data_storage;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class douban_data_split_cat {
	public class Pair{
		public int a;
		public int b;
		public int c;
		public int d;
		public int e;
		
		public Pair(int a, int b, int c, int d, int e){
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.e = e;
		}
	}
	
	
	
	
	public double percent = 0.05;
	
	public HashMap<Integer, Pair> idx_useritem
		= new HashMap<Integer, Pair>();
	
	public HashSet<Integer> test_idx
		= new HashSet<Integer>();
	
	public HashMap<Integer, ArrayList<Integer>> user_idx
		= new HashMap<Integer, ArrayList<Integer>>();
	
	
	public void data_split(){
		// read maps
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CAT=0;
		int[] ret = call_ILCA_LDA.read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CAT = ret[3];
		
		// read user_item
		read_user_item_cat(path+"user_item_LCA.txt");
		
		// (1)
		//split_data_var_city(path);
		// (2)
		split_data_same_city(path);
		// (3)
		split_data_mix(path);
	}
	
	
	
	// situation(1) var city
	public void split_data_var_city(String path){
		test_idx.clear();
		
		HashMap<Integer, Integer> test_user_itemLoc
			= new HashMap<Integer, Integer>();
		
		Pair pair;
		for(int key : idx_useritem.keySet()){
			pair = idx_useritem.get(key);
			
			// userLoc != itemLoc
			if(pair.b != pair.d){				
				if(test_user_itemLoc.containsKey(pair.a)){
					if(test_user_itemLoc.get(pair.a) == pair.d)
						test_idx.add(key);
				}
				else{
					test_user_itemLoc.put(pair.a, pair.d);
					test_idx.add(key);
				}
			}
		}
		
		System.out.println("[split_data_var_city] test_count: "+test_idx.size()
				           +" percent: "+test_idx.size()*1.0/idx_useritem.size());
		System.out.println("[split_data_var_city] test user count: "+test_user_itemLoc.size());
		
		// write
		write_test_file_cat(path+"user_item_test_1", path+"user_item_train_1");
	}
	
	
	// situation(2) same city
	public void split_data_same_city(String path){
		user_idx.clear();
		test_idx.clear();
		
		// build user_idx
		Pair pair;
		for(int key : idx_useritem.keySet()){
			pair = idx_useritem.get(key);
			
			// userLoc == itemLoc
			if(pair.b == pair.d){
				// add to user_idx
				if(!user_idx.containsKey(pair.a)){
					ArrayList<Integer> idxes = new ArrayList<Integer>();
					idxes.add(key);
					user_idx.put(pair.a, idxes);
				}
				user_idx.get(pair.a).add(key);
			}
		}
		
		// build test_idx
		double percent = 0.1;
		int test_size, ran, length;
		ArrayList<Integer> list;
		for(int userId : user_idx.keySet()){
			length = user_idx.get(userId).size();
			test_size = (int)(length * percent);
			list = user_idx.get(userId);
			
			while(test_size != 0){
				test_size--;
				
				ran = (int)(Math.random() * length);
				test_idx.add(list.get(ran));
			}
		}
		
		System.out.println("[split_data_same_city] test_count: "+test_idx.size()
				           +" percent: "+test_idx.size()*1.0/idx_useritem.size());
		
		
		write_test_file_cat(path+"user_item_test_2", path+"user_item_train_2");
	}
	
	
	// situation(3) mix
	public void split_data_mix(String path){
		user_idx.clear();
		test_idx.clear();
		
		// build user_idx
		Pair pair;
		for(int key : idx_useritem.keySet()){
			pair = idx_useritem.get(key);
			
			// add to user_idx
			if(!user_idx.containsKey(pair.a)){
				ArrayList<Integer> idxes = new ArrayList<Integer>();
				idxes.add(key);
				user_idx.put(pair.a, idxes);
			}
			user_idx.get(pair.a).add(key);	
		}
		
		// build test_idx
		double percent = 0.1;
		int test_size, ran, length;
		ArrayList<Integer> list;
		for(int userId : user_idx.keySet()){
			length = user_idx.get(userId).size();
			test_size = (int)(length * percent);
			list = user_idx.get(userId);
			
			while(test_size != 0){
				test_size--;
				
				ran = (int)(Math.random() * length);
				test_idx.add(list.get(ran));
			}
		}
		
		System.out.println("[split_data_mix] test_count: "+test_idx.size()
				           +" percent: "+test_idx.size()*1.0/idx_useritem.size());
		
		write_test_file_cat(path+"user_item_test_3", path+"user_item_train_3");
	}
	
	
	
	public void write_test_file_cat(String filename_test, String filename_train){
		try{
			String testFile = filename_test;
			OutputStreamWriter oswpf_test = data_storage.file_handle(testFile);
			
			String trainFile = filename_train;
			OutputStreamWriter oswpf_train = data_storage.file_handle(trainFile);
			
			Pair pair;
			for(int key : idx_useritem.keySet()){
				pair = idx_useritem.get(key);
				
				if(test_idx.contains(key)){
					oswpf_test.write(pair.a+"\t"+pair.b+"\t"+pair.c+"\t"+pair.d+"\t"+pair.e+"\n");
				}
				else{
					oswpf_train.write(pair.a+"\t"+pair.b+"\t"+pair.c+"\t"+pair.d+"\t"+pair.e+"\n");
				}
			}
			
			oswpf_test.flush();
			oswpf_test.close();
			
			oswpf_train.flush();
			oswpf_train.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void read_user_item_cat(String filename){
		BufferedReader ratingReader= data_storage.file_handle_read(filename);
		String line;

		int count = 0;
	try{			
		while((line = ratingReader.readLine())!=null)
		{
			String part[] = line.split("\t");
			
			int userId = Integer.parseInt(part[0]);
			int userLoc = Integer.parseInt(part[1]);
			int itemId = Integer.parseInt(part[2]);
			int itemLoc = Integer.parseInt(part[3]);
			int itemCat = Integer.parseInt(part[4]);
			
			Pair pair = new Pair(userId, userLoc, itemId, itemLoc, itemCat);
			
			idx_useritem.put(count, pair);
			count++;
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}

	}
	
	
	
	public static void main(String args[]){
		douban_data_split_cat dds = new douban_data_split_cat();
		
		dds.data_split();
	}
	
}
