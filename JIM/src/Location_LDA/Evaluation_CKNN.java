package Location_LDA;

import input_output_interface.data_storage;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import Location_LDA.Evaluation_cat.pair;
import Location_LDA.Evaluation_cat.paircomparator;

public class Evaluation_CKNN {
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
	
	
	public void load_model(CKNN cknn){
		String path = cknn.path;
		String filename = path + "KNN.txt";
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		
		try{
			String line, part[];
			
			line = ratingReader.readLine();
			while(line != null){
				int userId = Integer.parseInt(line);
				HashMap<Integer,ArrayList<Integer>> loc_knn 
						= new HashMap<Integer,ArrayList<Integer>>(); 

				while(true){
					line = ratingReader.readLine();
					if(line == null) break;
					
					part = line.split(" ");
					if(part.length <= 1)
						break;
					
					int locId = Integer.parseInt(part[0]);
					loc_knn.put(locId, new ArrayList<Integer>());
					for(int i=1; i<part.length; i++){
						int neighId = Integer.parseInt(part[i]);
						
						loc_knn.get(locId).add(neighId);
					}
				}
				cknn.user_loc_knn.put(userId, loc_knn);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void read_user_item(HashMap<Integer, UserProfile> user_item, 
								String filename, String path){
		
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CAT=0;
		int[] ret = call_ILCA_LDA.read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CAT = ret[3];
		
		UserTagPostMap utpm = new UserTagPostMap();
		call_ILCA_LDA.read_data_ILCA(filename, user_item, path, utpm,
													SIZE_OF_USER, SIZE_OF_ITEM, 
													SIZE_OF_LOC, SIZE_OF_CAT);
	}
	
	

	public void recommend_evaluation(CKNN cknn, 
									HashMap<Integer, UserProfile> user_item_test,
									HashMap<Integer, Integer> result,
									HashMap<Integer, UserProfile> user_item_train){

		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_test = user_item_test.get(userId);
			int size = user_test.length;
		
			pair item_probability[] = new pair[size];
		
			// item
			for(int j=0; j<size; j++){
				int itemId = user_test.getItem(j);
				int locId = user_test.getItemLoc(j);
				
				ArrayList<Integer> knn = cknn.user_loc_knn.get(userId).get(locId);
			
				item_probability[j] = new pair();
				item_probability[j].stamp_id = itemId;
				
				// score
				double score = 0;
				int sz = knn.size();
				for(int k=0; k<sz; k++){
					if(user_item_train.get(knn.get(k)).containsItem(itemId))
						score++;
				}
				
				item_probability[j].probability = score;
			}
			
			// sort
			Arrays.sort(item_probability, new paircomparator());			
			
			// result for this user
			////////////////////////////////////////
			// number of targets before current target (excluding current target)
			int target_before = -1;
			////////////////////////////////////////
			for(int idx=0; idx<size; idx++){
				int rank = idx + 1;
				int itemId = item_probability[idx].stamp_id;
		
				if(user_test.targets.contains(itemId)){
					////////////////////////////////////////
					// substract target_before
					target_before++;
					rank -= target_before;
					///////////////////////////////////////
		
					if(result.containsKey(rank))
						result.put(rank, result.get(rank)+1);
					else
						result.put(rank, 1);
					
					//System.out.println(rank+" "+userId);
				}
			}
		}
	}
	
	
	
	public void evaluation(int strategy){
		String path = "/home/hzt/workspace5/fashion/doubanevent/CKNN_"+strategy+"/";
		
		System.out.println("load model ... ");
		CKNN cknn = new CKNN();
		cknn.path = path;
		load_model(cknn);
		
		path = "/home/hzt/workspace5/fashion/doubanevent/";
		HashMap<Integer, UserProfile> user_item_train 
						= new HashMap<Integer, UserProfile>() ;
		read_user_item(user_item_train, path+"user_item_train_"+strategy+"/", path);
		
		// load test_random data
		System.out.println("load test_random data ... ");
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		String testFileName_ran = path+"test_random_"+strategy;
		Evaluation_cat.read_randomTest(testFileName_ran, user_item_test);
		
		
		//
		System.out.println("recommend_evaluation ... ");
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(cknn, user_item_test, result, user_item_train);
		
		// result
		System.out.println("result ... ");
		int maxIdx = -1;
		for(int key : result.keySet())
			maxIdx = maxIdx < key ? key : maxIdx; 
		
		int[] ret = new int[maxIdx+1];		
		for(int key : result.keySet())
			ret[key] = result.get(key);
		
		for(int i=1; i<=maxIdx; i++){
			ret[i] += ret[i-1];
		}
		
		for(int i=0; i<=maxIdx; i++){
			System.out.println(i +","+ret[i]);
		}
		
	}
	
	
	public static void main(String args[]){
		Evaluation_CKNN ec = new Evaluation_CKNN();
		
		//ec.evaluation(1);
		//ec.evaluation(2);
	}
}
