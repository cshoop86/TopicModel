package Location_LDA;

import java.util.ArrayList;
import java.util.HashMap;

public class call_CKNN {
	
	
	public HashMap<Integer, ArrayList<Integer>> calUserVector
							(HashMap<Integer, UserProfile> user_item, int C){
		
		HashMap<Integer, ArrayList<Integer>> user_vector
			= new HashMap<Integer, ArrayList<Integer>>();
		
		for(int userId : user_item.keySet()){
			if(!user_vector.containsKey(userId)){
				ArrayList<Integer> vec = new ArrayList<Integer>();
				for(int i=0; i<C; i++)
					vec.add(0);
				
				user_vector.put(userId, vec);
			}
			
			UserProfile up = user_item.get(userId);
			
			int sz = up.length;
			for(int i=0; i<sz; i++){
				int itemCat = up.getItemCategory(i); 
				int preValue = user_vector.get(userId).get(itemCat);
				user_vector.get(userId).set(itemCat, preValue+1);
			}
		}
		
		return user_vector;
	}
	
	public HashMap<Integer, ArrayList<Integer>> calLocUsers
						(HashMap<Integer, UserProfile> user_item){
		
		HashMap<Integer, ArrayList<Integer>> loc_users 
				= new HashMap<Integer, ArrayList<Integer>>();
		
		for(int userId : user_item.keySet()){
			int locId = user_item.get(userId).location;
			if(!loc_users.containsKey(locId)){
				loc_users.put(locId, new ArrayList<Integer>());
			}
			
			loc_users.get(locId).add(userId);
		}
		
		return loc_users;
	}
	
	
	public void _call_CKNN(int strategy){
		// load model
		System.out.println("call CKNN ... ");
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		
		// read maps
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CATE=0;
		int[] ret = call_ILCA_LDA.read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CATE = ret[3];
		
		// read user_item
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		String filename = path + "user_item_train_" + strategy;
		call_ILCA_LDA.read_data_ILCA(filename, user_item, path, utpm,
									SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC,SIZE_OF_CATE);
		
		// 
		HashMap<Integer, ArrayList<Integer>> user_vector;
		user_vector = calUserVector(user_item, SIZE_OF_CATE);
		
		//
		HashMap<Integer, ArrayList<Integer>> loc_users;
		loc_users = calLocUsers(user_item);
		
		int K = 20;
		path = "/home/hzt/workspace5/fashion/doubanevent/CKNN_"+strategy+"/";
		CKNN cknn = new CKNN(K, user_vector, loc_users, path);
		
		
		cknn.train();
	}
	
	
	public static void main(String args[]){
		call_CKNN cc = new call_CKNN();
		
		//cc._call_CKNN(1);
		cc._call_CKNN(2);
	}
	
	
	
}
