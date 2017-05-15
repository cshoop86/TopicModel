package Location_LDA;

import input_output_interface.data_storage;

import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class caseStudy {
	class paircomparator implements Comparator<pair>{
		public int compare(pair s1, pair s2) {
			
			if(s1.probability<s2.probability) return 1;
			else if(s1.probability>s2.probability) return -1;
			else return 0;
		}
	}
	class pair{
		int stamp_id;
		double probability;
	}
	
	

	public void ULA_topic_study(){
		// read maps
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = call_LA_LDAs.read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		// get item_reverse_map
		System.out.println(call_LA_LDAs.item_map.size());
		HashMap<Integer, String> item_reverse_map = new HashMap<Integer, String>();
		for(String key : call_LA_LDAs.item_map.keySet()){
			item_reverse_map.put(call_LA_LDAs.item_map.get(key), key);
		}
		// get loc_reverse_map
		System.out.println(call_LA_LDAs.loc_map.size());
		HashMap<Integer, String> loc_reverse_map = new HashMap<Integer, String>();
		for(String key : call_LA_LDAs.loc_map.keySet()){
			loc_reverse_map.put(call_LA_LDAs.loc_map.get(key), key);
		}
		
		
		// read user_item
		String filename = "/home/huzhiting/workspace3/fashion/doubanevent/user_item.txt";
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		UserTagPostMap utpm = new UserTagPostMap();
		call_LA_LDAs.read_data(filename, user_item, path, utpm,
								SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);
		
		// load model
		ULA_LDA ula = new ULA_LDA();
		ula.outputPath = path + "ULA_big/";
		Read_LDA.load_model(ula);
		
	try{
		System.out.println("ULA_topic_study() for each topic");
		
		String file_name = path + "topic_top20_word.txt";
		OutputStreamWriter oswpf = data_storage.file_handle(file_name);
		// each topic 
		for(int k=0; k<ula.K; k++){
			pair topic_word[] = new pair[ula.V];
			
			// word
			for(int j=0; j<ula.V; j++){
				topic_word[j] = new pair();
				
				topic_word[j].stamp_id = j;
				topic_word[j].probability = ula.topicItemDistribution[k][j];
			}
			Arrays.sort(topic_word, new paircomparator());
			
			for(int w=0; w<20; w++){
				int id = utpm.reverse_map2[topic_word[w].stamp_id];
				String item = item_reverse_map.get(id);
				
				int locId = call_LA_LDAs.item_loc.get(id);
				String loc = loc_reverse_map.get(locId);
				
				oswpf.write(item+":"+loc+"\t");
			}
			oswpf.write("\n");	
		}
		oswpf.flush();
		oswpf.close();	
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	System.out.println("ULA_topic_study() done");
	}
	
	
	
	public static void main(String args[]){
		 caseStudy cs = new caseStudy();
		 cs.ULA_topic_study();
		 
	}
	
	
	
}
