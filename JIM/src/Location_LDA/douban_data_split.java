package Location_LDA;

import input_output_interface.data_storage;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class douban_data_split {
	public class Pair{
		public int a;
		public int b;
		public int c;
		public int d;
		
		public Pair(int a, int b, int c, int d){
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
		}
	}
	
	
	
	public double percent = 0.05;
	
	public HashMap<Integer, Pair> idx_useritem
		= new HashMap<Integer, Pair>();
	
	public HashSet<Integer> test_idx
		= new HashSet<Integer>();
	
	
	public void data_split(){
		// read maps
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = call_LA_LDAs.read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		
		// read user_item
		read_user_item(path+"user_item.txt");
		
		
		// random
		int size = idx_useritem.size();
		int test_count = (int)(percent * size);
		
		int idx;
		Pair pair;
		while(test_count > 0){
			idx = (int)(Math.random() * size);
			
			pair = idx_useritem.get(idx);
			// userLoc != itemLoc, continue
			if(pair.b != pair.d)
				continue;
			
			test_count--;
			test_idx.add(idx);	
		}
		
		
		write_test_file(path+"user_item_test", path+"user_item_train");
	}
	
	
	public void write_test_file(String filename_test, String filename_train){
		try{
			String testFile = filename_test;
			OutputStreamWriter oswpf_test = data_storage.file_handle(testFile);
			
			String trainFile = filename_train;
			OutputStreamWriter oswpf_train = data_storage.file_handle(trainFile);
			
			Pair pair;
			for(int key : idx_useritem.keySet()){
				pair = idx_useritem.get(key);
				
				if(test_idx.contains(key)){
					oswpf_test.write(pair.a+"\t"+pair.b+"\t"+pair.c+"\t"+pair.d+"\n");
				}
				else{
					oswpf_train.write(pair.a+"\t"+pair.b+"\t"+pair.c+"\t"+pair.d+"\n");
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
	
	public void read_user_item(String filename){
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
			
			Pair pair = new Pair(userId, userLoc, itemId, itemLoc);
			
			idx_useritem.put(count, pair);
			count++;
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}

	}
	
	
	
	public static void main(String args[]){
		douban_data_split dds = new douban_data_split();
		
		dds.data_split();
	}
	
}
