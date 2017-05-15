package Location_LDA;

import input_output_interface.data_storage;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import Location_LDA.caseStudy.pair;
import Location_LDA.caseStudy.paircomparator;
import Location_LDA.douban_data_split_cat.Pair;
import OnlineRecommender.FAItem;
import OnlineRecommender.ThresholdAlg;


public class Evaluation_cat {
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
	
	public static HashMap<Integer, HashSet<Integer>> loc_item_count
		= new HashMap<Integer, HashSet<Integer>>();


	
	public static void load_data(ILCA_LDA ilca, String path, String model_path, 
								UserTagPostMap utpm,
								HashMap<Integer, UserProfile> user_item,
								String filename){
		// read maps
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CATE=0;
		int[] ret = call_ILCA_LDA.read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CATE = ret[3];
		
		// read user_item
		//String filename = path + "user_item_train_1";
		call_ILCA_LDA.read_data_ILCA(filename, user_item, path, utpm,
									SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC,SIZE_OF_CATE);
		
		// load model
		ilca.outputPath = model_path;
		Read_LDA.load_model(ilca);

	}
	public static void load_data(LDA la, String path, String model_path, 
			UserTagPostMap utpm,
			HashMap<Integer, UserProfile> user_item,
			String filename){
   // read maps
   int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CATE=0;
   int[] ret = call_ILCA_LDA.read_maps_cat(path);
   SIZE_OF_USER = ret[0];
   SIZE_OF_ITEM = ret[1];
   SIZE_OF_LOC = ret[2];
   SIZE_OF_CATE = ret[3];

  // read user_item
  //String filename = path + "user_item_train_1";
  call_ILCA_LDA.read_data_ILCA(filename, user_item, path, utpm,
				SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC,SIZE_OF_CATE);

   // load model
    la.outputPath = model_path;
     Read_LDA.load_model(la);
}

	
	public static void load_data(LA_baseline la, String path, String model_path, 
								UserTagPostMap utpm,
								HashMap<Integer, UserProfile> user_item,
								String filename){
		// read maps
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CATE=0;
		int[] ret = call_ILCA_LDA.read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CATE = ret[3];
		
		// read user_item
		//String filename = path + "user_item_train_1";
		call_ILCA_LDA.read_data_ILCA(filename, user_item, path, utpm,
									SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC,SIZE_OF_CATE);
		
		// load model
		la.outputPath = model_path;
		Read_LDA.load_model(la);
	}
	

	
	public static void load_data(CA_baseline ca, String path, String model_path, 
								UserTagPostMap utpm,
								HashMap<Integer, UserProfile> user_item,
								String filename){
		// read maps
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CATE=0;
		int[] ret = call_ILCA_LDA.read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CATE = ret[3];
		
		// read user_item
		//String filename = path + "user_item_train_1";
		call_ILCA_LDA.read_data_ILCA(filename, user_item, path, utpm,
									SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC,SIZE_OF_CATE);
		
		// load model
		ca.outputPath = model_path;
		Read_LDA.load_model(ca);
	}
	
	
	
	public static void load_test_data_Cate(HashMap<Integer, UserProfile> user_item,
			  								UserTagPostMap utpm,
			  								String path, String filename){
		// read user_item_test
		//String filename = path + "user_item_test_1";
		
		BufferedReader ratingReader = data_storage.file_handle_read(filename);
		String line;
		int count = 0;
		
	try{			
		while((line = ratingReader.readLine())!=null)
		{
			String part[] = line.split("\t");
		
			int userId = Integer.parseInt(part[0]);
			int user_loc=Integer.parseInt(part[1]);
			int itemId = Integer.parseInt(part[2]);
			int itemLoc = Integer.parseInt(part[3]);
			int itemCate=Integer.parseInt(part[4]);

		
			int userMapId = utpm.get_map1(userId);
			if(userMapId < 0) continue;
			
			int userMapLoc=utpm.get_map3(user_loc);
			if(userMapLoc < 0) continue;
			
			int itemMapId = utpm.get_map2(itemId);
			if(itemMapId < 0) continue;
			
			int itemMapLoc = utpm.get_map3(itemLoc);
			if(itemMapLoc < 0) continue;

			int itemMapCate = utpm.get_map4(itemCate);
			if(itemMapCate < 0) continue;
		
		if(user_item.containsKey(userMapId)){
			user_item.get(userMapId).addItem(itemMapId, itemMapLoc, itemMapCate);
			// target
			user_item.get(userMapId).addTarget(itemMapId);
		}
		else{
			UserProfile up = new UserProfile(userMapId);
			up.location = userMapLoc;
			up.addItem(itemMapId, itemMapLoc,itemMapCate);
			// target
			up.addTarget(itemMapId);
		
			user_item.put(userMapId, up);
		}
		
		count++;
		}
		
		System.out.println("test user number : "+user_item.size());
		System.out.println("test votes number : "+count);
	}
	catch(Exception e){
		e.printStackTrace();
	}
		
	}
	
	
	
	public static void cal_loc_item_count(HashMap<Integer, UserProfile> user_item_test,
										  HashMap<Integer, UserProfile> user_item_train){
		
		
		for(int key : user_item_train.keySet()){	
			UserProfile user_train = user_item_train.get(key);
			int size = user_train.length;
			
			for(int i=0; i<size; i++){
				int itemId = user_train.getItem(i);
				int itemLoc = user_train.getItemLoc(i);

				if(!loc_item_count.containsKey(itemLoc)){
					HashSet<Integer> itemSet = new HashSet<Integer>();
					itemSet.add(itemId);
				}
				else{
					loc_item_count.get(itemLoc).add(itemId);
				}
			}
		}
		
		for(int key : user_item_test.keySet()){	
			UserProfile user_test = user_item_test.get(key);
			int size = user_test.length;
			
			for(int i=0; i<size; i++){
				int itemId = user_test.getItem(i);
				int itemLoc = user_test.getItemLoc(i);

				if(!loc_item_count.containsKey(itemLoc)){
					HashSet<Integer> itemSet = new HashSet<Integer>();
					itemSet.add(itemId);
					loc_item_count.put(itemLoc, itemSet);
				}
				else{
					loc_item_count.get(itemLoc).add(itemId);
				}
			}
		}
		
		System.out.println("loc_item_count size: "+loc_item_count.size());
	}
	
	
	public static void randomTestItems_ILCA(HashMap<Integer, UserProfile> user_item_test,
										HashMap<Integer, UserProfile> user_item_train,
										UserTagPostMap utpm,
										String path,
										HashMap<Integer, Integer> item_loc,
										HashMap<Integer,Integer> item_cate,
										int strategy){
		
		ArrayList<Integer> removed_users = new ArrayList<Integer>();
		if(strategy == 1 || strategy == 2){
			cal_loc_item_count(user_item_test, user_item_train);
		}
		
		final int ITEM_COUNT = 1000;
		int item_size = utpm.size2;

		int removed_user_count = 0;
		// each user
		int userCount = 0;
		for(int userId : user_item_test.keySet()){
			UserProfile user_train = user_item_train.get(userId);
			UserProfile user_test = user_item_test.get(userId);
			
			int targetMapId = user_test.targets.get(0);
			int targetId = utpm.reverse_map2[targetMapId];
			int targetLoc = item_loc.get(targetId);
			int targetMapLoc = utpm.get_map3(targetLoc);
			
			if(strategy == 1 || strategy == 2){
				if(!loc_item_count.containsKey(targetMapLoc)){
					System.out.println("skip targetMapLoc "+targetMapLoc);
					removed_user_count++;
					
					//user_item_test.remove(userId);
					removed_users.add(userId);
					
					continue;
				}
				if(loc_item_count.get(targetMapLoc).size() 
						< ITEM_COUNT + user_test.targets.size()){
					removed_user_count++;
					
					//user_item_test.remove(userId);
					removed_users.add(userId);
					
					continue;
				}
			}
				
			// random ITEM_COUNT items not in 
			// user_item_test[i] and user_item_train[i]
			// according to strategy
			int count = 0;
			HashSet<Integer> candi=new HashSet<Integer>();
			candi.add(targetMapId);
			
			while(count < ITEM_COUNT){
				int itemMapId = (int) (Math.random() * item_size);
		
				if(!user_train.containsItem(itemMapId) && !user_test.containsItem(itemMapId)){
					int itemId = utpm.reverse_map2[itemMapId];
					int itemLoc = item_loc.get(itemId);
					int itemCate = item_cate.get(itemId);
					
					// strategy 1 and 2:
					// itemLoc == targetLoc
					if(strategy == 1 || strategy == 2){
						if(itemLoc != targetLoc) continue;
					}
					
					int itemMapLoc = utpm.get_map3(itemLoc);
					int itemMapCate = utpm.get_map4(itemCate);
					
					if(!candi.contains(itemMapId))
					{
						candi.add(itemMapId);
						user_test.addItem(itemMapId, itemMapLoc, itemMapCate);
						
						count++;
					}
				}
			}
			
			userCount++;
			if(userCount % 100 == 0)
				System.out.print(".");
			if(userCount % 1000 == 0)
				System.out.println();
		}
		System.out.println();
		
		// removed users
		int sz = removed_users.size();
		for(int idx=0; idx<sz; idx++)
			user_item_test.remove(removed_users.get(idx));
		
		write_randomTest(path+"test_random_"+strategy, user_item_test);
		
		System.out.println("random done! removed "+removed_user_count+" users");
		System.out.println("random done! "+user_item_test.size()+" users left");
	}

	
	public static void write_randomTest(String filename, 
								 HashMap<Integer, UserProfile> user_item_test){
		try{
			String testFile = filename;
			OutputStreamWriter oswpf_test = data_storage.file_handle(testFile);
			
			// user size
			oswpf_test.write(user_item_test.size()+"\n");
			
			for(int key : user_item_test.keySet()){
				UserProfile up = user_item_test.get(key);
				
				// userId
				oswpf_test.write(key+"\n");
				
				// candidates
				int sz = up.length;
				oswpf_test.write(sz+"\n");
				for(int i=0; i<sz; i++){
					oswpf_test.write(up.getItem(i)+" "
				                     + up.getItemLoc(i)+" "
							         + up.getItemCategory(i)+"\n");
				}
				
				// targets
				sz = up.targets.size();
				oswpf_test.write(sz+"\n");
				for(int i=0; i<sz; i++){
					oswpf_test.write(up.targets.get(i)+"\n");
				}
			}
			
			oswpf_test.flush();
			oswpf_test.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public static void read_randomTest(String filename, 
			 					HashMap<Integer, UserProfile> user_item_test){
		
		BufferedReader ratingReader= data_storage.file_handle_read(filename);

		String line;
		
		try{
			line = ratingReader.readLine();
			int userSize = Integer.parseInt(line);
			
			// user
			for(int u=0; u<userSize; u++){
				line = ratingReader.readLine();
				int userId = Integer.parseInt(line);
				
				// no user location
				UserProfile up = new UserProfile(userId, -1);
				
				// candidates
				line = ratingReader.readLine();
				int candSize = Integer.parseInt(line);
				for(int c=0; c<candSize; c++){
					line = ratingReader.readLine();
					String[] part = line.split(" ");
					int itemId = Integer.parseInt(part[0]);
					int itemLoc = Integer.parseInt(part[1]);
					int itemCat = Integer.parseInt(part[2]);
					
					up.addItem(itemId, itemLoc, itemCat);
				}
				
				// targets
				line = ratingReader.readLine();
				int targetSize = Integer.parseInt(line);
				for(int t=0; t<targetSize; t++){
					line = ratingReader.readLine();
					int itemId = Integer.parseInt(line);
					
					up.addTarget(itemId);
				}
				
				user_item_test.put(userId, up);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		
		System.out.println("read_randomTest done. test user number "+user_item_test.size());
	}
	
	public void recommend_evaluation(ILCA_LDA ilca, 
									HashMap<Integer, UserProfile> user_item_test,
									HashMap<Integer, Integer> result){

		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_test = user_item_test.get(userId);
			int size = user_test.length;
		
			pair item_probability[] = new pair[size];
		
			// item
			for(int j=0; j<size; j++){
				int itemId = user_test.getItem(j);
				int locId = user_test.getItemLoc(j);
				int cateId = user_test.getItemCategory(j);
				
				item_probability[j] = new pair();
				item_probability[j].stamp_id = itemId;
				
				// score
				double user_p = 0;
				double loc_p = 0;
				for(int k=0; k<ilca.K; k++){
					user_p += ilca.userTopicDistribution[userId][k] 
							* ilca.topicItemDistribution[k][itemId]
							* ilca.topicCategoryDistribution[k][cateId];
					
					loc_p += ilca.locTopicDistribution[locId][k] 
							* ilca.topicItemDistribution[k][itemId]
							*ilca.topicCategoryDistribution[k][cateId];
				}
				item_probability[j].probability 
					= ilca.lambda_u[userId] * user_p
					+ (1 - ilca.lambda_u[userId]) * loc_p;
			}
			
			// sort
			Arrays.sort(item_probability, new paircomparator());
		
			/*
			for(int idx=0; idx<size; idx++){
				System.out.println(item_probability[idx].stamp_id+" "
									+item_probability[idx].probability);
			}
			System.out.println("=====================");
			System.out.println();
			*/
			
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
				}
			}
		}
	}

/*	
	// just used in strategy 1 && 2
	public void recommend_evaluation_online(ILCA_LDA ilca, 
											HashMap<Integer, UserProfile> user_item_test,
											HashMap<Integer, Integer> result){
		
		System.out.println("recommend_evaluation_online ... ");
		int userCount = 0;
		
		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_test = user_item_test.get(userId);
			int size = user_test.length;
		
			// use TA
			// candidates
			HashMap<Integer, Integer> ta_item_loc = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> ta_item_cat = new HashMap<Integer, Integer>();
			for(int j=0; j<size; j++){
				int itemId = user_test.getItem(j);
				int locId = user_test.getItemLoc(j);
				int cateId = user_test.getItemCategory(j);
				
				ta_item_loc.put(itemId, locId);
				ta_item_cat.put(itemId, cateId);
			}
			
			int topk = size;
			int loc = user_test.getItemLoc(0);
			ThresholdAlg ta = new ThresholdAlg(ilca, ta_item_loc, ta_item_cat);
			ta.offline();
			ArrayList<Integer> ret = ta.recommend(userId, loc, topk);
			
			// result for this user
			////////////////////////////////////////
			// number of targets before current target (excluding current target)
			int target_before = -1;
			////////////////////////////////////////
			for(int idx=0; idx<size; idx++){
				int rank = idx + 1;
				int itemId = ret.get(idx);
		
				if(user_test.targets.contains(itemId)){
					////////////////////////////////////////
					// subtract target_before
					target_before++;
					rank -= target_before;
					///////////////////////////////////////
		
					if(result.containsKey(rank))
						result.put(rank, result.get(rank)+1);
					else
						result.put(rank, 1);
				}
			}
			
			userCount++;
			if(userCount % 100 == 0)
				System.out.print(".");
			if(userCount % 1000 == 0)
				System.out.println();
		}
		System.out.println();
	}
*/
	

	public void recommend_evaluation(LDA la, 
									HashMap<Integer, UserProfile> user_item_test,
									HashMap<Integer, Integer> result){

		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_test = user_item_test.get(userId);
			int size = user_test.length;
		
			pair item_probability[] = new pair[size];
		
			// item
			for(int j=0; j<size; j++){
				int itemId = user_test.getItem(j);
				//int locId = user_test.getItemLoc(j);
				//int cateId = user_test.getItemCategory(j);
				
				item_probability[j] = new pair();
				item_probability[j].stamp_id = itemId;
				
				// score
				double user_p = 0;
				//double loc_p = 0;
				for(int k=0; k<la.K; k++){
					user_p += la.userTopicDistribution[userId][k] 
							* la.topicItemDistribution[k][itemId];
					
					
				}
				
				item_probability[j].probability 
					= user_p;
					
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

	public void recommend_evaluation(LA_baseline la, 
			HashMap<Integer, UserProfile> user_item_test,
			HashMap<Integer, Integer> result){

// each user
for(int userId : user_item_test.keySet()){
UserProfile user_test = user_item_test.get(userId);
int size = user_test.length;

pair item_probability[] = new pair[size];

// item
for(int j=0; j<size; j++){
int itemId = user_test.getItem(j);
int locId = user_test.getItemLoc(j);
//int cateId = user_test.getItemCategory(j);

item_probability[j] = new pair();
item_probability[j].stamp_id = itemId;

// score
double user_p = 0;
double loc_p = 0;
for(int k=0; k<la.K; k++){
user_p += la.userTopicDistribution[userId][k] 
	* la.topicItemDistribution[k][itemId];

loc_p += la.locTopicDistribution[locId][k] 
	* la.topicItemDistribution[k][itemId];
}

item_probability[j].probability 
= la.lambda_u[userId] * user_p
+ (1 - la.lambda_u[userId]) * loc_p;
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
	public void recommend_evaluation(CA_baseline ca, 
									HashMap<Integer, UserProfile> user_item_test,
									HashMap<Integer, Integer> result){

		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_test = user_item_test.get(userId);
			int size = user_test.length;
		
			pair item_probability[] = new pair[size];
		
			// item
			for(int j=0; j<size; j++){
				int itemId = user_test.getItem(j);
				int locId = user_test.getItemLoc(j);
				int cateId = user_test.getItemCategory(j);
				
				item_probability[j] = new pair();
				item_probability[j].stamp_id = itemId;
				
				// score
				double item_p = 0;
				for(int k=0; k<ca.K; k++){
					item_p += ca.userTopicDistribution[userId][k] * ca.topicItemDistribution[k][itemId]
							* ca.topicLocDistribution[k][cateId];
				}
				item_probability[j].probability = item_p;
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
				}
			}
		}
	}

	
	public void outputResult(HashMap<Integer, Integer> result, String filename){
		try{
			OutputStreamWriter oswpf = data_storage.file_handle(filename);
		
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
				oswpf.write(i+","+ret[i]+"\n");
			}
			oswpf.flush();
			oswpf.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void evaluate_ILCA(int strategy){
		// load model
		System.out.println("load model ILCA ... ");
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		ILCA_LDA ilca = new ILCA_LDA();
		String model_path = path + "ILCA_"+strategy+"_K200"+"/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		String trainFileName = path + "user_item_train_" + strategy;
		load_data(ilca, path, model_path, utpm, user_item, trainFileName);
	/*
		// load test data 
		System.out.println("load test data ... ");
		
		String testFileName = path + "user_item_test_" + strategy;
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		load_test_data_Cate(user_item_test, utpm, path, testFileName);
		
		// random
		System.out.println("random ... ");

		randomTestItems_ILCA(user_item_test, user_item, utpm, path, 
							 call_ILCA_LDA.item_loc, call_ILCA_LDA.item_cat, 
							 strategy);
	*/
		// load random test file
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		String testFileName_ran = path+"test_random_"+strategy;
		read_randomTest(testFileName_ran, user_item_test);
		
		
		// evaluation
		System.out.println("evaluation for strategy:"+strategy);
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(ilca, user_item_test, result);
		//recommend_evaluation_online(ilca, user_item_test, result);
		
		// result
		String result_file = model_path + "result.csv";
		outputResult(result, result_file);
	}
	

	public void evaluate_LA_baseline(int strategy){
		// load model
		System.out.println("load model LA baseline ... ");
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		LA_baseline la = new LA_baseline();
		String model_path = path + "LA_"+strategy+"/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		String trainFileName = path + "user_item_train_" + strategy;
		load_data(la, path, model_path, utpm, user_item, trainFileName);
		
		// load test data 
		System.out.println("load test data ... ");

		// load random test file
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		String testFileName_ran = path+"test_random_"+strategy;
		read_randomTest(testFileName_ran, user_item_test);
	
		
		
		// evaluation
		System.out.println("evaluation ... ");
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(la, user_item_test, result);
		
		
		// result
		String result_file = model_path + "result.csv";
		outputResult(result, result_file);
	}

	public void evaluate_LDA_baseline(int strategy){
		// load model
		System.out.println("load model LDA baseline ... ");
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		LDA la = new LDA();
		String model_path = path + "LDA_"+strategy+"/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		String trainFileName = path + "user_item_train_" + strategy;
		load_data(la, path, model_path, utpm, user_item, trainFileName);
		
		// load test data 
		System.out.println("load test data ... ");

		// load random test file
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		String testFileName_ran = path+"test_random_"+strategy;
		read_randomTest(testFileName_ran, user_item_test);
	
		
		
		// evaluation
		System.out.println("evaluation ... ");
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(la, user_item_test, result);
		
		
		// result
		String result_file = model_path + "result.csv";
		outputResult(result, result_file);
		
	}
	
	public void evaluate_CA_baseline(int strategy){
		// load model
		System.out.println("load model CA baseline ... ");
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		CA_baseline ca = new CA_baseline();
		String model_path = path + "CA_"+strategy+"/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		String trainFileName = path + "user_item_train_" + strategy;
		load_data(ca, path, model_path, utpm, user_item, trainFileName);
		
		// load test data 
		System.out.println("load test data ... ");

		// load random test file
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		String testFileName_ran = path+"test_random_"+strategy;
		read_randomTest(testFileName_ran, user_item_test);
	
		
		
		// evaluation
		System.out.println("evaluation ... ");
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(ca, user_item_test, result);
		
		
		// result
		String result_file = model_path + "result.csv";
		outputResult(result, result_file);
		
	}

	
	public static void create_random_test_file(int strategy){
		// load model
		System.out.println("load model ILCA ... ");
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		ILCA_LDA ilca = new ILCA_LDA();
		String model_path = path + "ILCA_"+strategy+"/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		String trainFileName = path + "user_item_train_" + strategy;
		load_data(ilca, path, model_path, utpm, user_item, trainFileName);
		
		// load test data 
		System.out.println("load test data ... ");
		
		String testFileName = path + "user_item_test_" + strategy;
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		load_test_data_Cate(user_item_test, utpm, path, testFileName);
		
		// random
		System.out.println("random ... ");
		
		randomTestItems_ILCA(user_item_test, user_item, utpm, path, 
							 call_ILCA_LDA.item_loc, call_ILCA_LDA.item_cat, 
							 strategy);
		
	}
	
	public /*ArrayList<Integer>*/
			ArrayList<FAItem> ForceRecommend(ILCA_LDA ilca, int userId, int locId, int topk,
											HashMap<Integer, Integer> ta_item_loc,
											HashMap<Integer, Integer> ta_item_cat){
		// get all items in locId
		HashSet<Integer> candi = new HashSet<Integer>();
		for(int key : ta_item_loc.keySet()){
			if(ta_item_loc.get(key) == locId)
				candi.add(key);
		}
		
		int size = candi.size();
		pair item_probability[] = new pair[size];
		Iterator<Integer> iter = candi.iterator();
		int j=0;
		while(iter.hasNext()){
			int candiId = iter.next(); 
			int candiLocId = ta_item_loc.get(candiId);
			int candiCateId = ta_item_cat.get(candiId);;
			
			item_probability[j] = new pair();
			item_probability[j].stamp_id = candiId;
			
			// score
			double user_p = 0;
			double loc_p = 0;
			for(int k=0; k<ilca.K; k++){
				user_p += ilca.userTopicDistribution[userId][k] 
						* ilca.topicItemDistribution[k][candiId]
						* ilca.topicCategoryDistribution[k][candiCateId];
				
				loc_p += ilca.locTopicDistribution[candiLocId][k] 
						* ilca.topicItemDistribution[k][candiId]
						* ilca.topicCategoryDistribution[k][candiCateId];
			}
			item_probability[j].probability 
				= ilca.lambda_u[userId] * user_p
				+ (1 - ilca.lambda_u[userId]) * loc_p;
			
			j++;
		}
		
		// sort
		Arrays.sort(item_probability, new paircomparator());
		
		//ArrayList<Integer> ret = new ArrayList<Integer>();
		ArrayList<FAItem> ret = new ArrayList<FAItem>();
		for(int i=0; i<topk; i++){
			ret.add(new FAItem(item_probability[i].stamp_id, item_probability[i].probability));
		}
		return ret;
	}
	
	
	public void OnlineRecommender(int strategy){
		System.out.println("Online Recommender ... ");
		
		// load model
		System.out.println("load model ILCA ... ");
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		ILCA_LDA ilca = new ILCA_LDA();
		String model_path = path + "ILCA_"+strategy+"/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		String trainFileName = path + "user_item_train_" + strategy;
		load_data(ilca, path, model_path, utpm, user_item, trainFileName);
		
		
		// TA
		HashMap<Integer, Integer> ta_item_loc = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> ta_item_cat = new HashMap<Integer, Integer>();
		for(int itemId : call_ILCA_LDA.item_loc.keySet()){
			int itemMapId = utpm.get_map2(itemId);
			
			int itemMapLoc = utpm.get_map3(call_ILCA_LDA.item_loc.get(itemId));
			int itemMapCat = utpm.get_map4(call_ILCA_LDA.item_cat.get(itemId));
			
			if(itemMapId < 0)
				continue;
			
			ta_item_loc.put(itemMapId, itemMapLoc);
			ta_item_cat.put(itemMapId, itemMapCat);
		}
		ThresholdAlg ta = new ThresholdAlg(ilca, ta_item_loc, ta_item_cat);
		ta.offline();
		
		// 
		int topk = 10;
		int count = 10;
		for(int userId : call_ILCA_LDA.user_loc.keySet()){
			int locId = call_ILCA_LDA.user_loc.get(userId);
			
			int userMapId = utpm.get_map1(userId);
			int locMapId = utpm.get_map3(locId);
					
			//ArrayList<Integer> ret1 = ta.recommend(userMapId, locMapId, topk);
			ArrayList<FAItem> ret1 = ta.recommend(userMapId, locMapId, topk);
		/*	ArrayList<Integer> ret2 = ForceRecommend(ilca, userMapId, locMapId, topk,
													ta_item_loc, ta_item_cat);
		*/
			ArrayList<FAItem> ret2 = ForceRecommend(ilca, userMapId, locMapId, topk,
													ta_item_loc, ta_item_cat);
			
			// check
			for(int i=0; i<topk; i++){
				if(ret1.get(i).id != ret2.get(i).id){
					System.out.println("Error mismatch "+i+": "
										+ret1.get(i).id+" "+ret2.get(i).id);
					
					for(int j=0; j<topk; j++)
						System.out.println(ret1.get(j).id+"-"+ret1.get(j).score+" ");
					System.out.println();
					
					for(int j=0; j<topk; j++)
						System.out.println(ret2.get(j).id+"-"+ret2.get(j).score+" ");
					System.out.println();
					
					break;
				}
			}
			
			if(count-- == 0)
				break;
			//break;
		}
		
		System.out.println("Online Done");
	}
	
	
	public static void main(String args[]){
		Evaluation_cat eva = new Evaluation_cat();

		// create random test file
		// should train the corresponding ILCA model first
		//(1)
		// create_random_test_file(1);
		//(2)
		//create_random_test_file(2);
		//(3)
		//create_random_test_file(3);
		
		
		// strategey 1 : var city
		eva.evaluate_ILCA(1);
		// strategy 2 : same city
		//eva.evaluate_ILCA(2);
		// strategy 3 : mix
		//eva.evaluate_ILCA(3);

		// baseline - 1
		//
		//eva.evaluate_LA_baseline(1);
		//eva.evaluate_LA_baseline(2);
		//eva.evaluate_LA_baseline(3);
		
		// baseline - 2
		//eva.evaluate_CA_baseline(1);
		//eva.evaluate_CA_baseline(2);
		//eva.evaluate_CA_baseline(3);

		// baseline - 3 
		//eva.evaluate_LDA_baseline(1);
		//eva.evaluate_LDA_baseline(2);
		
		//eva.OnlineRecommender(2);
		
	}
	
}
