package Location_LDA;

import input_output_interface.data_storage;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import OnlineRecommender.FAItem;
import OnlineRecommender.FAItemComparator;

public class CKNN {
	public int K;	// number of neighbors
	
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> user_loc_knn
		 = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
	
	
	public HashMap<Integer, ArrayList<Integer>> user_vector;
	
	public HashMap<Integer, ArrayList<Integer>> loc_users;
	
	public String path;
	
	public CKNN(){}
	
	public CKNN(int K, HashMap<Integer, ArrayList<Integer>> user_vector,
				HashMap<Integer, ArrayList<Integer>> loc_users,
				String path){
		this.K = K;
		this.user_vector = user_vector;
		this.loc_users = loc_users;
		this.path = path;
	}
	
	public double calDistance(ArrayList<Integer> v1, ArrayList<Integer> v2){
		double dist = 0;
		for(int i=0; i<v1.size(); i++){
			dist += v1.get(i) * v2.get(i);
		}
		return dist;
	}
	
	public void calUserKNN(){
		System.out.println("call user KNN ...");
		
		PriorityQueue<FAItem> PQ 
			= new PriorityQueue<FAItem>(1, new FAItemComparator());
		
		ArrayList<Integer> candi;
		for(int userId : user_vector.keySet()){
			ArrayList<Integer> uv = user_vector.get(userId);
			
			for(int locId : loc_users.keySet()){
				PQ.clear();
				
				candi = loc_users.get(locId);
				int sz = candi.size();
				for(int j=0; j<sz; j++){
					double dist
						= calDistance(user_vector.get(candi.get(j)), uv);
					
					PQ.add(new FAItem(candi.get(j), dist));
				}
				
				int knnCount = (K < PQ.size() ? K : PQ.size());
				ArrayList<Integer> knn_list = new ArrayList<Integer>();
				for(int i=0; i<knnCount; i++)
					knn_list.add(PQ.poll().id);
				
				if(!user_loc_knn.containsKey(userId))
					user_loc_knn.put(userId, new HashMap<Integer, ArrayList<Integer>>());
				user_loc_knn.get(userId).put(locId, knn_list);
			}
		}
	}
	
	public void output_model(){
		System.out.println("output model ...");
		
		try{
			String userTopic_file = path + "KNN.txt";
    		OutputStreamWriter oswpf = data_storage.file_handle(userTopic_file);
    	
    		for(int userId : user_loc_knn.keySet()){
    			oswpf.write(userId+"\n");
    			
    			for(int locId : user_loc_knn.get(userId).keySet()){
    				oswpf.write(locId+" ");
    				ArrayList knnlist = user_loc_knn.get(userId).get(locId);
    				int sz = knnlist.size();
    				for(int i=0; i<sz; i++){
    					oswpf.write(knnlist.get(i)+" ");
    				}
    				oswpf.write("\n");
    			}
    		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	public void train(){
		calUserKNN();
		output_model();
	}
}
