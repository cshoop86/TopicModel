package Location_LDA;

import input_output_interface.data_storage;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import Location_LDA.caseStudy.pair;
import Location_LDA.caseStudy.paircomparator;

public class Evaluation {
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
	
	public static void load_data(ILCA_LDA ula, String path, String model_path, 
			UserTagPostMap utpm,
			HashMap<Integer, UserProfile> user_item){
// read maps
int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CATE=0;
int[] ret = call_ILCA_LDA.read_maps_cat(path);
SIZE_OF_USER = ret[0];
SIZE_OF_ITEM = ret[1];
SIZE_OF_LOC = ret[2];
SIZE_OF_CATE=ret[3];

// read user_item
String filename = path + "user_item_traincate";
call_ILCA_LDA.read_data_ILCA(filename, user_item, path, utpm,
			SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC,SIZE_OF_CATE);

// load model
ula.outputPath = model_path;
Read_LDA.load_model(ula);




}

	
	
	public static void load_data(ULA_LDA ula, String path, String model_path, 
								UserTagPostMap utpm,
								HashMap<Integer, UserProfile> user_item){
		// read maps
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = call_LA_LDAs.read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		// read user_item
		String filename = path + "user_item_train";
		call_LA_LDAs.read_data(filename, user_item, path, utpm,
								SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);
		
		// load model
		ula.outputPath = model_path;
		Read_LDA.load_model(ula);
		
	}
	
	public static void load_data(LDA ula, String path, String model_path, 
			UserTagPostMap utpm,
			HashMap<Integer, UserProfile> user_item){
// read maps
int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
int[] ret = call_LA_LDAs.read_maps(path);
SIZE_OF_USER = ret[0];
SIZE_OF_ITEM = ret[1];
SIZE_OF_LOC = ret[2];

// read user_item
String filename = path + "user_item_train";
call_LA_LDAs.read_data_LDA(filename, user_item, path, utpm,
			SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);

// load model
ula.outputPath = model_path;
Read_LDA.load_model(ula);

}
	
	public static void load_data(ILA_LDA ila, String path, String model_path, 
								UserTagPostMap utpm,
								HashMap<Integer, UserProfile> user_item){
		// read maps
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = call_LA_LDAs.read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		// read user_item
		String filename = path + "user_item_train";
		call_LA_LDAs.read_data_ILA(filename, user_item, path, utpm,
								SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);
		
		// load model
		ila.outputPath = model_path;
		Read_LDA.load_model(ila);
		
	}
	
	
	public static void load_data(UILA_LDA uila, String path, String model_path, 
								UserTagPostMap utpm,
								HashMap<Integer, UserProfile> user_item){
		// read maps
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = call_LA_LDAs.read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		// read user_item
		String filename = path + "user_item_train";
		call_LA_LDAs.read_data_UILA(filename, user_item, path, utpm,
									SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC, SIZE_OF_LOC);
		
		// load model
		uila.outputPath = model_path;
		Read_LDA.load_model(uila);
		
	}
	
	
	
	
	
	
	
	
	public static void load_test_data(HashMap<Integer, UserProfile> user_item,
									  UserTagPostMap utpm,
									  String path){
		// read user_item_test
		String filename = path + "user_item_test";
		
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
			
			int userMapId = utpm.get_map1(userId);
			if(userMapId < 0) continue;
			int itemMapId = utpm.get_map2(itemId);
			if(itemMapId < 0) continue;
			int userMapLoc = utpm.get_map3(userLoc);
			if(userMapLoc < 0) continue;
			int itemMapLoc = utpm.get_map3(itemLoc);
			if(itemMapLoc < 0) continue;

			
			if(user_item.containsKey(userMapId)){
				user_item.get(userMapId).addItem(itemMapId, itemMapLoc);
				// target
				user_item.get(userMapId).addTarget(itemMapId);
			}
			else{
				UserProfile up = new UserProfile(userMapId, userMapLoc);
				up.addItem(itemMapId, itemMapLoc);
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
	
	public static void load_test_data_Cate(HashMap<Integer, UserProfile> user_item,
			  UserTagPostMap utpm,
			  String path){
// read user_item_test
String filename = path + "user_item_testcate";

BufferedReader ratingReader= data_storage.file_handle_read(filename);
String line;
int count = 0;

try{			
while((line = ratingReader.readLine())!=null)
{
String part[] = line.split("\t");

int userId = Integer.parseInt(part[0]);

int itemId = Integer.parseInt(part[1]);
int itemLoc = Integer.parseInt(part[2]);
int itemCate=Integer.parseInt(part[3]);

int user_loc=Integer.parseInt(part[4]);

int userMapId = utpm.get_map1(userId);
if(userMapId < 0) continue;
int itemMapId = utpm.get_map2(itemId);
if(itemMapId < 0) continue;

int itemMapLoc = utpm.get_map3(itemLoc);
if(itemMapLoc < 0) continue;

int userMapLoc=utpm.get_map3(user_loc);
if(userMapLoc < 0) continue;

int itemMapCate = utpm.get_map4(itemCate);
if(itemMapCate < 0) continue;

if(user_item.containsKey(userMapId)){
user_item.get(userMapId).addItem(itemMapId, itemMapLoc,itemMapCate);
// target
user_item.get(userMapId).addTarget(itemMapId);
}
else{
UserProfile up = new UserProfile(userMapId);
up.location=userMapLoc;
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
	
	// strategy==1, sample new city events
	// strategy==0, sample home city events
	// strategy==others, randomly sample events
		public static void randomTestItems(HashMap<Integer, UserProfile> user_item_test,
											HashMap<Integer, UserProfile> user_item_train,
				 							UserTagPostMap utpm,
				 							String path,
				 							HashMap<Integer, Integer> item_loc,int strategy){
			
			final int ITEM_COUNT = 1000;
			int item_size = utpm.size2;
			int count_user=0;
			// each user
			for(int userId : user_item_test.keySet()){
				UserProfile user_train = user_item_train.get(userId);
				UserProfile user_test = user_item_test.get(userId);
				int user_loc=user_train.location;
				
				int targetid=user_test.targets.get(0);
				// random ITEM_COUNT items not in 
				// user_item_test[i] and user_item_train[i]
				int count = 0;
				int count1=0;
				HashSet<Integer> candi=new HashSet<Integer>();
				candi.add(targetid);
				while(count < ITEM_COUNT){
					int itemMapId = (int) (Math.random() * item_size);
					
					if(!user_train.containsItem(itemMapId) && !user_test.containsItem(itemMapId)){
						int itemId = utpm.reverse_map2[itemMapId];
						int itemLoc = item_loc.get(itemId);
						int itemMapLoc = utpm.get_map3(itemLoc);
						
						
						if(count1>100000)
						{
							break;
						}
						//home city events test
						if(strategy==0)
						{    if(!candi.contains(itemMapId))
							count1++;
							if(user_loc!=itemMapLoc)
							{  
								continue;
							}
							
						}
						
						// sample events in new cities
						if(strategy==1)	
						{   if(!candi.contains(itemMapId))
							count1++;
							if(user_loc==itemMapLoc)
							{   
								continue;
							}
						}
						
						if(!candi.contains(itemMapId))
						{
						 candi.add(itemMapLoc);
						user_test.addItem(itemMapId, itemMapLoc);
						
						count++;
						
						
						}
					}
				}
				
				count_user++;
				//System.out.println("count of users:"+count_user);
			}
		}
		
	
	
	
	
	

	// 
	public static void randomTestItems(HashMap<Integer, UserProfile> user_item_test,
										HashMap<Integer, UserProfile> user_item_train,
			 							UserTagPostMap utpm,
			 							String path,
			 							HashMap<Integer, Integer> item_loc){
		
		final int ITEM_COUNT = 1000;
		int item_size = utpm.size2;
		
		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_train = user_item_train.get(userId);
			UserProfile user_test = user_item_test.get(userId);
			//int targetid=user_test.targets.get(0);
			// random ITEM_COUNT items not in 
			// user_item_test[i] and user_item_train[i]
			int count = 0;
			HashSet<Integer> candi=new HashSet<Integer>();
			for(int targetid:user_test.targets)
			{
			candi.add(targetid);
			}
			while(count < ITEM_COUNT){
				int itemMapId = (int) (Math.random() * item_size);
				
				if(!user_train.containsItem(itemMapId) && !user_test.containsItem(itemMapId)){
					int itemId = utpm.reverse_map2[itemMapId];
					int itemLoc = item_loc.get(itemId);
					int itemMapLoc = utpm.get_map3(itemLoc);
					if(!candi.contains(itemMapId))
					{
					candi.add(itemMapId);
					user_test.addItem(itemMapId, itemMapLoc);
					
					count++;
					}
				}
			}
		}
	}
	
	// 
	public static void randomTestItems_UILA(HashMap<Integer, UserProfile> user_item_test,
										HashMap<Integer, UserProfile> user_item_train,
			 							UserTagPostMap utpm,
			 							String path,
			 							HashMap<Integer, Integer> item_loc){
		
		final int ITEM_COUNT = 1000;
		int item_size = utpm.size2;
		
		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_train = user_item_train.get(userId);
			UserProfile user_test = user_item_test.get(userId);
			//int target_id=user_test.targets.get(0);
			// random ITEM_COUNT items not in 
			// user_item_test[i] and user_item_train[i]
			int count = 0;
			HashSet<Integer> candi=new HashSet<Integer>();
			for(int targetid:user_test.targets)
			{
			candi.add(targetid);
			}
		
			while(count < ITEM_COUNT){
				int itemMapId = (int) (Math.random() * item_size);
				
				if(!user_train.containsItem(itemMapId) && !user_test.containsItem(itemMapId)){
					int itemId = utpm.reverse_map2[itemMapId];
					int itemLoc = item_loc.get(itemId);
					int itemMapLoc = utpm.get_map3(itemLoc);
					
					if(!candi.contains(itemMapId))
					{
						
					candi.add(itemMapId);	
					user_test.addItem(itemMapId, itemMapLoc);
					
					count++;
					}
				}
			}
		}
	}
	
	public static void randomTestItems_ILCA(HashMap<Integer, UserProfile> user_item_test,
			HashMap<Integer, UserProfile> user_item_train,
				UserTagPostMap utpm,
				String path,
				HashMap<Integer, Integer> item_loc,HashMap<Integer,Integer> item_cate){

      final int ITEM_COUNT = 1000;
     int item_size = utpm.size2;

// each user
for(int userId : user_item_test.keySet()){
UserProfile user_train = user_item_train.get(userId);
UserProfile user_test = user_item_test.get(userId);
int targetMapid=user_test.targets.get(0);
// random ITEM_COUNT items not in 
// user_item_test[i] and user_item_train[i]
int count = 0;
HashSet<Integer> candi=new HashSet<Integer>();
candi.add(targetMapid);
while(count < ITEM_COUNT){
int itemMapId = (int) (Math.random() * item_size);

if(!user_train.containsItem(itemMapId) && !user_test.containsItem(itemMapId)){
int itemId = utpm.reverse_map2[itemMapId];
int targetId=utpm.reverse_map2[targetMapid];

int itemLoc = item_loc.get(itemId);
int targetitemLoc=item_loc.get(targetId);
/*if(itemLoc!=targetitemLoc)
{
 continue;	
}*/


int itemCate=item_cate.get(itemId);
int itemMapLoc = utpm.get_map3(itemLoc);



int itemMapCate = utpm.get_map4(itemCate);
if(!candi.contains(itemMapId))
{
	candi.add(itemMapId);
user_test.addItem(itemMapId, itemMapLoc,itemMapCate);

count++;
}
}
}
}
}
//strategy_id==0, home city
//stragegy_id=1, new city
	public static void randomTestItems_ILCA(HashMap<Integer, UserProfile> user_item_test,
			HashMap<Integer, UserProfile> user_item_train,
				UserTagPostMap utpm,
				String path,
				HashMap<Integer, Integer> item_loc,
				HashMap<Integer,Integer> item_cate,int strategy_id){

      final int ITEM_COUNT = 1000;
     int item_size = utpm.size2;

// each user
for(int userId : user_item_test.keySet()){
UserProfile user_train = user_item_train.get(userId);
UserProfile user_test = user_item_test.get(userId);
int targetid=user_test.targets.get(0);
// random ITEM_COUNT items not in 
// user_item_test[i] and user_item_train[i]
int count = 0;
int user_loc=user_train.location;
int targetMapid=user_train.targets.get(0);
HashSet<Integer> candi=new HashSet<Integer>();
candi.add(targetid);
while(count < ITEM_COUNT){
int itemMapId = (int) (Math.random() * item_size);

if(!user_train.containsItem(itemMapId) && !user_test.containsItem(itemMapId)){
int itemId = utpm.reverse_map2[itemMapId];
int targetId=utpm.reverse_map2[targetMapid];
int itemLoc = item_loc.get(itemId);
int targetitemLoc=item_loc.get(targetId);
if(itemLoc!=targetitemLoc)
{
 continue;	
}

int itemCate=item_cate.get(itemId);
int itemMapLoc = utpm.get_map3(itemLoc);

if(strategy_id==0&&itemMapLoc!=user_loc)
{
	 continue;
}
if(strategy_id==1&&itemMapLoc==user_loc)
{
	 continue;
}

int itemMapCate = utpm.get_map4(itemCate);
user_test.addItem(itemMapId, itemMapLoc,itemMapCate);

count++;
}
}
}
}

	public static void randomTestItems_UILA(HashMap<Integer, UserProfile> user_item_test,
			HashMap<Integer, UserProfile> user_item_train,
				UserTagPostMap utpm,
				String path,
				HashMap<Integer, Integer> item_loc,int strategy_id){

final int ITEM_COUNT = 1000;
int item_size = utpm.size2;

// each user
for(int userId : user_item_test.keySet()){
UserProfile user_train = user_item_train.get(userId);
UserProfile user_test = user_item_test.get(userId);
int user_loc=user_train.location;
int target_id=user_test.targets.get(0);
// random ITEM_COUNT items not in 
// user_item_test[i] and user_item_train[i]
int count = 0;
int count1=0;
HashSet<Integer> candi=new HashSet<Integer>();
candi.add(target_id);
while(count < ITEM_COUNT){
int itemMapId = (int) (Math.random() * item_size);

if(!user_train.containsItem(itemMapId) && !user_test.containsItem(itemMapId)){
int itemId = utpm.reverse_map2[itemMapId];
int itemLoc = item_loc.get(itemId);
int itemMapLoc = utpm.get_map4(itemLoc);


if(count1>100000)
{
	break;
}
//home city events test
if(strategy_id==0)
{    if(!candi.contains(itemMapId))
	count1++;
	if(user_loc!=itemMapLoc)
	{  
		continue;
	}
	
}

// sample events in new cities
if(strategy_id==1)	
{   if(!candi.contains(itemMapId))
	count1++;
	if(user_loc==itemMapLoc)
	{   
		continue;
	}
}


if(!candi.contains(itemMapId))
{
	
candi.add(itemMapId);	
user_test.addItem(itemMapId, itemMapLoc);

count++;
}
}
}
}
}

	
	//
	public void recommend_evaluation(ULA_LDA ula, 
									HashMap<Integer, UserProfile> user_item_test,
									HashMap<Integer, Integer> result){
		
		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_test = user_item_test.get(userId);
			int locId = user_test.location;
			int size = user_test.length;
			
			pair item_probability[] = new pair[size];

			// item
			for(int j=0; j<size; j++){
				int itemId = user_test.getItem(j);
				item_probability[j] = new pair();
				
				item_probability[j].stamp_id = itemId;
				// score
				double user_p = 0;
				double loc_p = 0;
				for(int k=0; k<ula.K; k++){
					user_p += ula.userTopicDistribution[userId][k] * ula.topicItemDistribution[k][itemId];
					loc_p += ula.locTopicDistribution[locId][k] * ula.topicItemDistribution[k][itemId];
				}
				item_probability[j].probability 
						= ula.lambda_u[userId] * user_p
						+ (1 - ula.lambda_u[userId]) * loc_p;
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
	public void recommend_evaluation(ILCA_LDA ula, 
			HashMap<Integer, UserProfile> user_item_test,
			HashMap<Integer, Integer> result){

// each user
for(int userId : user_item_test.keySet()){
UserProfile user_test = user_item_test.get(userId);
//int locId = user_test.location;
int size = user_test.length;

pair item_probability[] = new pair[size];

// item
for(int j=0; j<size; j++){
int itemId = user_test.getItem(j);
int locId=user_test.getItemLoc(j);
int cateId=user_test.getItemCategory(j);
item_probability[j] = new pair();

item_probability[j].stamp_id = itemId;
// score
double user_p = 0;
double loc_p = 0;
for(int k=0; k<ula.K; k++){
user_p += ula.userTopicDistribution[userId][k] * ula.topicItemDistribution[k][itemId]
		*ula.topicCategoryDistribution[k][cateId];
loc_p += ula.locTopicDistribution[locId][k] * ula.topicItemDistribution[k][itemId]
		*ula.topicCategoryDistribution[k][cateId];;
}
item_probability[j].probability 
= ula.lambda_u[userId] * user_p
+ (1 - ula.lambda_u[userId]) * loc_p;
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
	public void recommend_evaluation(LDA ula, 
			HashMap<Integer, UserProfile> user_item_test,
			HashMap<Integer, Integer> result){

// each user
for(int userId : user_item_test.keySet()){
UserProfile user_test = user_item_test.get(userId);
int locId = user_test.location;
int size = user_test.length;

pair item_probability[] = new pair[size];

// item
for(int j=0; j<size; j++){
int itemId = user_test.getItem(j);
item_probability[j] = new pair();

item_probability[j].stamp_id = itemId;
// score
double user_p = 0;
//double loc_p = 0;
for(int k=0; k<ula.K; k++){
user_p += ula.userTopicDistribution[userId][k] * ula.topicItemDistribution[k][itemId];
//loc_p += ula.locTopicDistribution[locId][k] * ula.topicItemDistribution[k][itemId];
}
item_probability[j].probability 
=  user_p;
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
	public void recommend_evaluation(ILA_LDA ila, 
									HashMap<Integer, UserProfile> user_item_test,
									HashMap<Integer, Integer> result){
		
		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_test = user_item_test.get(userId);
			//int locId = user_test.location;
			int size = user_test.length;
			
			pair item_probability[] = new pair[size];

			// item
			for(int j=0; j<size; j++){
				int itemId = user_test.getItem(j);
				int itemLoc = user_test.getItemLoc(j);
				item_probability[j] = new pair();
				
				item_probability[j].stamp_id = itemId;
				// score
				double item_p = 0;
				//double loc_p = 0;
				for(int k=0; k<ila.K; k++){
					item_p += ila.userTopicDistribution[userId][k] * ila.topicItemDistribution[k][itemId]
								* ila.topicLocDistribution[k][itemLoc];
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
	
	
	public void recommend_evaluation(UILA_LDA uila, 
									HashMap<Integer, UserProfile> user_item_test,
									HashMap<Integer, Integer> result){
		
		// each user
		for(int userId : user_item_test.keySet()){
			UserProfile user_test = user_item_test.get(userId);
			int locId = user_test.location;
			int size = user_test.length;
			
			pair item_probability[] = new pair[size];

			// item
			for(int j=0; j<size; j++){
				int itemId = user_test.getItem(j);
				int itemLoc = user_test.getItemLoc(j);
				item_probability[j] = new pair();
				
				item_probability[j].stamp_id = itemId;
				// score
				double user_p = 0;
				double loc_p = 0;
				for(int k=0; k<uila.K; k++){
					user_p += uila.userTopicDistribution[userId][k] * uila.topicItemDistribution[k][itemId]
								* uila.topicLocDistribution[k][itemLoc];
					loc_p += uila.locTopicDistribution[locId][k] * uila.topicItemDistribution[k][itemId]
								* uila.topicLocDistribution[k][itemLoc];
				}
				item_probability[j].probability 
						= uila.lambda_u[userId] * user_p
						+ (1 - uila.lambda_u[userId]) * loc_p;
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
	
	public void evaluate_ULA(){
		// load model
		System.out.println("load model ULA ... ");
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		ULA_LDA ula = new ULA_LDA();
		String model_path = path + "ULA/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		load_data(ula, path, model_path, utpm, user_item);
		
		// load test data 
		System.out.println("load test data ... ");
		
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		load_test_data(user_item_test, utpm, path);
		
		// random
		System.out.println("random ... ");
		
		randomTestItems(user_item_test, user_item, utpm, path, call_LA_LDAs.item_loc);
		
		// evaluation
		System.out.println("evaluation ... ");
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(ula, user_item_test, result);
		
		
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

	public void evaluate_LDA(){
		// load model
		System.out.println("load model LDA ... ");
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		LDA ula = new LDA();
		String model_path = path + "LDA/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		load_data(ula, path, model_path, utpm, user_item);
		
		// load test data 
		System.out.println("load test data ... ");
		
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		load_test_data(user_item_test, utpm, path);
		
		// random
		System.out.println("random ... ");
		
		randomTestItems(user_item_test, user_item, utpm, path, call_LA_LDAs.item_loc);
		
		// evaluation
		System.out.println("evaluation ... ");
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(ula, user_item_test, result);
		
		
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

	
	
	
	
	public void evaluate_ILA(){
		// load model
		System.out.println("load model ILA ... ");
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		ILA_LDA ila = new ILA_LDA();
		String model_path = path + "ILA/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		load_data(ila, path, model_path, utpm, user_item);
		
		// load test data 
		System.out.println("load test data ... ");
		
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		load_test_data(user_item_test, utpm, path);
		
		// random
		System.out.println("random ... ");
		
		randomTestItems(user_item_test, user_item, utpm, path, call_LA_LDAs.item_loc);
		
		// evaluation
		System.out.println("evaluation ... ");
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(ila, user_item_test, result);
		
		
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
	
	

	public void evaluate_UILA(){
		// load model
		System.out.println("load model UILA ... ");
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		UILA_LDA uila = new UILA_LDA();
		String model_path = path + "UILA/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		load_data(uila, path, model_path, utpm, user_item);
		
		// load test data 
		System.out.println("load test data ... ");
		
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		load_test_data(user_item_test, utpm, path);
		
		// random
		System.out.println("random ... ");
		
		randomTestItems(user_item_test, user_item, utpm, path, call_LA_LDAs.item_loc);
		
		// evaluation
		System.out.println("evaluation ... ");
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(uila, user_item_test, result);
		
		
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
	
	
	public void evaluate_ILCA(){
		// load model
		System.out.println("load model ILCA ... ");
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		ILCA_LDA uila = new ILCA_LDA();
		String model_path = path + "ILCA/";
		UserTagPostMap utpm = new UserTagPostMap();
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		load_data(uila, path, model_path, utpm, user_item);
		
		// load test data 
		System.out.println("load test data ... ");
		
		HashMap<Integer, UserProfile> user_item_test = new HashMap<Integer, UserProfile>();
		load_test_data_Cate(user_item_test, utpm, path);
		
		// random
		System.out.println("random ... ");
		
		randomTestItems_ILCA(user_item_test, user_item, utpm, path, call_ILCA_LDA.item_loc,call_ILCA_LDA.item_cat);
		
		// evaluation
		System.out.println("evaluation ... ");
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		recommend_evaluation(uila, user_item_test, result);
		
		
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
		Evaluation eva = new Evaluation();
		
		   //eva.evaluate_ULA();
		   // eva.evaluate_ILA();
		 eva.evaluate_UILA();
		 // eva.evaluate_LDA();
		//eva.evaluate_ILCA();
		
	}
	
}
