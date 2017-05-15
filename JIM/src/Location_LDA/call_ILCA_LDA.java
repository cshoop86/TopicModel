package Location_LDA;

import input_output_interface.data_storage;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;

import ancillary.matrix_and_map_2;


public class call_ILCA_LDA {
	public static String Path = "";
	
	public static HashMap<String, Integer> loc_map = new HashMap<String, Integer>();
	public static HashMap<String, Integer> item_map = new HashMap<String, Integer>();
	public static HashMap<String, Integer> user_map = new HashMap<String, Integer>();
	
	public static HashMap<Integer, Integer> user_loc = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> item_loc = new HashMap<Integer, Integer>();
	
	public static HashMap<String, Integer> cat_map = new HashMap<String, Integer>();
	public static HashMap<Integer, Integer> item_cat = new HashMap<Integer, Integer>();
	
	public static int loc_count  = 0;
	public static int cat_count  = 0;
	
	public static void user_map(String path){
		try{
			String userMapFile = path+"user_map_LCA.txt";
			OutputStreamWriter oswpf = data_storage.file_handle(userMapFile);
			
			int tot_count = 0;
			int count = 0;
			
			// user
			String userFile = path+"users.tsv";
			BufferedReader userReader= data_storage.file_handle_read(userFile);
			String line;

			while((line = userReader.readLine())!=null){
				String part[] = line.split("\t");
				
				tot_count++;
				if(part.length == 1) continue;
				if(part[1].length() == 1) continue;
				
				if(!user_map.containsKey(part[0]))
					user_map.put(part[0], count++);
				
				if(!loc_map.containsKey(part[1]))
					loc_map.put(part[1], loc_count++);
				
				user_loc.put(user_map.get(part[0]), loc_map.get(part[1]));
				
			}

			// Output
			for(String key : user_map.keySet()){
				int userId = user_map.get(key);
				int userLoc = user_loc.get(userId);
				oswpf.write(key+"\t"+userId+"\t"+userLoc+"\n");
			}
			oswpf.flush();
			oswpf.close();
			
			System.out.println("user map done. total count "
								+tot_count+" useful count "+count+"\n");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	
	// category
	public static void item_map_cat(String path){
		try{
			String itemMapFile = path+"item_map_LCA.txt";
			OutputStreamWriter oswpf = data_storage.file_handle(itemMapFile);
			
			int count = 0;
			
			// event_2
			String itemFile = path+"events.tsv";
			BufferedReader itemReader= data_storage.file_handle_read(itemFile);
			String line;

			while((line = itemReader.readLine())!=null){
				String part[] = line.split("\t");
				
				if(!item_map.containsKey(part[0]))
					item_map.put(part[0], count++);
				
				if(!loc_map.containsKey(part[1]))
					loc_map.put(part[1], loc_count++);
				
				
				if(!cat_map.containsKey(part[2]))
					cat_map.put(part[2], cat_count++);
				
				item_loc.put(item_map.get(part[0]), loc_map.get(part[1]));
				item_cat.put(item_map.get(part[0]), cat_map.get(part[2]));
			}

			// Output
			for(String key : item_map.keySet()){
				int itemId = item_map.get(key);
				int itemLoc = item_loc.get(itemId);
				int itemCat = item_cat.get(itemId);
				oswpf.write(key+"\t"+itemId+"\t"+itemLoc+"\t"+itemCat+"\n");
			}
			oswpf.flush();
			oswpf.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public static void loc_map(String path){
		try{
			String locMapFile = path+"loc_map_LCA.txt";
			OutputStreamWriter oswpf = data_storage.file_handle(locMapFile);
			
			// Output
			for(String key : loc_map.keySet()){
				oswpf.write(key+"\t"+loc_map.get(key)+"\n");
			}
			oswpf.flush();
			oswpf.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public static void cat_map(String path){
		try{
			String locMapFile = path+"cat_map_LCA.txt";
			OutputStreamWriter oswpf = data_storage.file_handle(locMapFile);
			
			// Output
			for(String key : cat_map.keySet()){
				oswpf.write(key+"\t"+cat_map.get(key)+"\n");
			}
			oswpf.flush();
			oswpf.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public static void arrange_user_item_file_cat(String path){
	try{
		String itemMapFile = path+"user_item_LCA.txt";
		OutputStreamWriter oswpf = data_storage.file_handle(itemMapFile);

		String itemFile = path+"all_user_event.tsv";
		BufferedReader itemReader= data_storage.file_handle_read(itemFile);
		String line;

		while((line = itemReader.readLine())!=null){
			String part[] = line.split("\t");
			
			if(user_map.containsKey(part[0]) && item_map.containsKey(part[1])){
				int userId = user_map.get(part[0]);
				int userLoc = user_loc.get(userId);
				int itemId = item_map.get(part[1]);
				int itemLoc = item_loc.get(itemId);
				int itemCat = item_cat.get(itemId);
				
				oswpf.write(userId+"\t"+userLoc+"\t"+itemId+"\t"+itemLoc+"\t"+itemCat+"\n");
			}
		}

		oswpf.flush();
		oswpf.close();
		
	}
	catch(Exception e){
		e.printStackTrace();
	}
}

	
	
	public static void arrange_data_cat(){
		Path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		user_map(Path);
		item_map_cat(Path);
		loc_map(Path);
		cat_map(Path);
		arrange_user_item_file_cat(Path);
	}	
	
	public static int[] read_maps_cat(String path){
		
		int SIZE_OF_USER = 0;
		int SIZE_OF_ITEM = 0;
		int SIZE_OF_LOC = 0;
		int SIZE_OF_CAT = 0;
		
		try{
			// user_map
			BufferedReader ratingReader= data_storage.file_handle_read(path+"user_map_LCA.txt");
			String line;
		
			while((line = ratingReader.readLine())!=null)
			{
				String part[] = line.split("\t");
				int userId = Integer.parseInt(part[1]);
				int userLoc = Integer.parseInt(part[2]);
				user_map.put(part[0], userId);
				user_loc.put(userId, userLoc);
				
				SIZE_OF_USER++;
			}
		
			// item_map
			ratingReader= data_storage.file_handle_read(path+"item_map_LCA.txt");
		
			while((line = ratingReader.readLine())!=null)
			{
				String part[] = line.split("\t");
				int itemId = Integer.parseInt(part[1]);
				int itemLoc = Integer.parseInt(part[2]);
				int itemCat = Integer.parseInt(part[3]);
				
				item_map.put(part[0], itemId);
				item_loc.put(itemId, itemLoc);
				item_cat.put(itemId, itemCat);
				
				SIZE_OF_ITEM++;
			}
			
			// loc_map
			ratingReader= data_storage.file_handle_read(path+"loc_map_LCA.txt");
			while((line = ratingReader.readLine())!=null)
			{
				String part[] = line.split("\t");
				int locId = Integer.parseInt(part[1]);
				loc_map.put(part[0], locId);
				
				SIZE_OF_LOC++;
			}
			
			// cat_map
			ratingReader= data_storage.file_handle_read(path+"cat_map_LCA.txt");
			while((line = ratingReader.readLine())!=null)
			{
				String part[] = line.split("\t");
				int locId = Integer.parseInt(part[1]);
				cat_map.put(part[0], locId);
				
				SIZE_OF_CAT++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("[Read_Maps_Cat] size of user "+SIZE_OF_USER);
		System.out.println("[Read_Maps_Cat] size of item "+SIZE_OF_ITEM);
		System.out.println("[Read_Maps_Cat] size of loc "+SIZE_OF_LOC);
		System.out.println("[Read_Maps_Cat] size of cat "+SIZE_OF_CAT);
		
		return new int[]{SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC, SIZE_OF_CAT};
	}
	
	
	// ILCA
	public static ILCA_LDA read_data_ILCA(String filename, HashMap<Integer, UserProfile> user_item, 
										String OutputPath, UserTagPostMap UserItemLoc,
										int SIZE_OF_USER, int SIZE_OF_ITEM,
										int SIZE_OF_LOC_ITEM, int SIZE_OF_CAT){

			BufferedReader ratingReader = data_storage.file_handle_read(filename);
			String line;
			
			HashSet<Integer> user = new HashSet<Integer>();
			HashSet<Integer> item = new HashSet<Integer>();
			HashSet<Integer> item_cat = new HashSet<Integer>();
			HashSet<Integer> item_loc = new HashSet<Integer>();
			int count = 0;
			
			UserItemLoc.initialize(SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC_ITEM, SIZE_OF_CAT);
			
		try{			
			while((line = ratingReader.readLine())!=null)
			{
				String part[] = line.split("\t");
				
				int userId = Integer.parseInt(part[0]);
				int userLoc = Integer.parseInt(part[1]);
				int itemId = Integer.parseInt(part[2]);
				int itemLoc = Integer.parseInt(part[3]);
				int itemCat = Integer.parseInt(part[4]);
				
				UserItemLoc.put_add(userId, itemId, itemLoc, itemCat);
				UserItemLoc.put_add(userId, itemId, userLoc, itemCat);
				
				int userMapId = UserItemLoc.get_map1(userId);
				int itemMapId = UserItemLoc.get_map2(itemId);
				int userMapLoc = UserItemLoc.get_map3(userLoc);
				int itemMapLoc = UserItemLoc.get_map3(itemLoc);
				int itemMapCat = UserItemLoc.get_map4(itemCat);
				
				if(user_item.containsKey(userMapId)){
					user_item.get(userMapId).addItem(itemMapId, itemMapLoc, itemMapCat);
				}
				else{
					UserProfile up = new UserProfile(userMapId, userMapLoc);
					up.addItem(itemMapId, itemMapLoc, itemMapCat);
					user_item.put(userMapId, up);
				}
				
				user.add(userId);
				item.add(itemId);
				item_cat.add(itemCat);
				item_loc.add(itemLoc);
				count++;
			}
			
			System.out.println("user number : "+user.size()+"; "+UserItemLoc.size1);
			System.out.println("item number : "+item.size()+"; "+UserItemLoc.size2);
			System.out.println("item loc number : "+item_loc.size());
			System.out.println("item cat number : "+item_cat.size());
			System.out.println("votes number : "+count);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		int topic_count = 200;
		//int topic_count = 10;
		double[] alpha = new double[topic_count];
		double[] alpha_2 = new double[topic_count];
		for(int i=0; i<topic_count; i++){
			//alpha[i] = 50.0 / topic_count;
			//alpha_2[i] = 50.0 / topic_count;
			alpha[i] = 10.0 / topic_count;
			alpha_2[i] = 10.0 / topic_count;
		}
		double[] beta = new double[item.size()];
		for(int i=0; i<item.size(); i++){
			beta[i] = 0.01;
		}
		double[] beta_2 = new double[UserItemLoc.size4];
		for(int i=0; i<UserItemLoc.size4; i++){
			beta_2[i] = 0.01;
		}
		double[] gamma = new double[2];
		gamma[0] = 0.5;
		gamma[1] = 0.5;
		
		int iterations = 500;
		int burnIn = 300;
		int sampleLag = 5;
		
		ILCA_LDA ilca = new ILCA_LDA(UserItemLoc.size2, topic_count, UserItemLoc.size1, 
								  item_loc.size(), item_cat.size(),  
								  alpha, alpha_2, beta, beta_2, gamma, iterations,
								  sampleLag, burnIn, OutputPath,
								  user_item);
		
		return ilca;
	}
	
	
	
		// baseline-1
		public static LA_baseline read_data_LA_baseline(String filename, HashMap<Integer, UserProfile> user_item, 
										String OutputPath, UserTagPostMap UserItemLoc,
										int SIZE_OF_USER, int SIZE_OF_ITEM, int SIZE_OF_LOC){

				BufferedReader ratingReader= data_storage.file_handle_read(filename);
				String line;
				
				HashSet<Integer> user = new HashSet<Integer>();
				HashSet<Integer> item = new HashSet<Integer>();
				HashSet<Integer> loc = new HashSet<Integer>();
				int count = 0;
				
				UserItemLoc.initialize(SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);
				
			try{			
				while((line = ratingReader.readLine())!=null)
				{
					String part[] = line.split("\t");
					
					int userId = Integer.parseInt(part[0]);
					int userLoc = Integer.parseInt(part[1]);
					int itemId = Integer.parseInt(part[2]);
					int itemLoc = Integer.parseInt(part[3]);
					
					UserItemLoc.put_add(userId, itemId, userLoc);
					UserItemLoc.put_add(userId, itemId, itemLoc);
					
					int userMapId = UserItemLoc.get_map1(userId);
					int itemMapId = UserItemLoc.get_map2(itemId);
					int userMapLoc = UserItemLoc.get_map3(userLoc);
					int itemMapLoc = UserItemLoc.get_map3(itemLoc);
					
					
					if(user_item.containsKey(userMapId)){
						user_item.get(userMapId).addItem(itemMapId, itemMapLoc);
					}
					else{
						UserProfile up = new UserProfile(userMapId, userMapLoc);
						up.addItem(itemMapId, itemMapLoc);
						user_item.put(userMapId, up);
					}
					
					// just for 
					user.add(userId);
					item.add(itemId);
					loc.add(userLoc);
					loc.add(itemLoc);
					count++;
				
				}
				
				System.out.println("user number : "+user.size()+"; "+UserItemLoc.size1);
				System.out.println("item number : "+item.size()+"; "+UserItemLoc.size2);
				System.out.println("user+item loc number : "+loc.size()+"; "+UserItemLoc.size3);
				System.out.println("votes number : "+count);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			int topic_count = 150;
			//int topic_count = 10;
			double[] alpha = new double[topic_count];
			double[] alpha_2 = new double[topic_count];
			for(int i=0; i<topic_count; i++){
				//alpha[i] = 50.0 / topic_count;
				//alpha_2[i] = 50.0 / topic_count;
				alpha[i] = 10.0 / topic_count;
				alpha_2[i] = 10.0 / topic_count;
			}
			double[] beta = new double[item.size()];
			for(int i=0; i<item.size(); i++){
				beta[i] = 0.01;
			}
			double[] gamma = new double[2];
			gamma[0] = 0.5;
			gamma[1] = 0.5;
			
			int iterations = 500;
			int burnIn = 300;
			int sampleLag = 5;
			
			LA_baseline la = new LA_baseline(UserItemLoc.size2, topic_count, UserItemLoc.size1, 
									  	UserItemLoc.size3, 
									  	alpha, alpha_2, beta, gamma, iterations,
									  	sampleLag, burnIn, OutputPath,
									  	user_item);

			return la;
		}
	
 //base_line 3
		public static LDA read_data_LDA_baseline(String filename, HashMap<Integer, UserProfile> user_item, 
				String OutputPath, UserTagPostMap UserItemLoc,
				int SIZE_OF_USER, int SIZE_OF_ITEM, int SIZE_OF_LOC)
		{

BufferedReader ratingReader= data_storage.file_handle_read(filename);
String line;

HashSet<Integer> user = new HashSet<Integer>();
HashSet<Integer> item = new HashSet<Integer>();
HashSet<Integer> loc = new HashSet<Integer>();
int count = 0;

UserItemLoc.initialize(SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);

try{			
while((line = ratingReader.readLine())!=null)
{
String part[] = line.split("\t");

int userId = Integer.parseInt(part[0]);
int userLoc = Integer.parseInt(part[1]);
int itemId = Integer.parseInt(part[2]);
int itemLoc = Integer.parseInt(part[3]);

UserItemLoc.put_add(userId, itemId, userLoc);
UserItemLoc.put_add(userId, itemId, itemLoc);

int userMapId = UserItemLoc.get_map1(userId);
int itemMapId = UserItemLoc.get_map2(itemId);
int userMapLoc = UserItemLoc.get_map3(userLoc);
int itemMapLoc = UserItemLoc.get_map3(itemLoc);


if(user_item.containsKey(userMapId)){
user_item.get(userMapId).addItem(itemMapId, itemMapLoc);
}
else{
UserProfile up = new UserProfile(userMapId, userMapLoc);
up.addItem(itemMapId, itemMapLoc);
user_item.put(userMapId, up);
}

// just for 
user.add(userId);
item.add(itemId);
loc.add(userLoc);
loc.add(itemLoc);
count++;

}

System.out.println("user number : "+user.size()+"; "+UserItemLoc.size1);
System.out.println("item number : "+item.size()+"; "+UserItemLoc.size2);
System.out.println("user+item loc number : "+loc.size()+"; "+UserItemLoc.size3);
System.out.println("votes number : "+count);
}
catch(Exception e){
e.printStackTrace();
}

int topic_count = 150;
//int topic_count = 10;
double[] alpha = new double[topic_count];
//double[] alpha_2 = new double[topic_count];
for(int i=0; i<topic_count; i++){
//alpha[i] = 50.0 / topic_count;
alpha[i] = 10.0 / topic_count;
}
double[] beta = new double[item.size()];
for(int i=0; i<item.size(); i++){
beta[i] = 0.01;
}
//double[] gamma = new double[2];
//gamma[0] = 0.5;
//gamma[1] = 0.5;

int iterations = 500;
int burnIn = 300;
int sampleLag = 5;
LDA la = new LDA(item.size(), topic_count, user.size(), 
		  alpha, beta, iterations,
		  sampleLag, burnIn, OutputPath,
		  user_item);

return la;
}

		
		
		// baseline-2
		public static CA_baseline read_data_CA_baseline(String filename, HashMap<Integer, UserProfile> user_item, 
										String OutputPath, UserTagPostMap UserItemLoc,
										int SIZE_OF_USER, int SIZE_OF_ITEM, int SIZE_OF_LOC,
										int SIZE_OF_CAT){

			BufferedReader ratingReader = data_storage.file_handle_read(filename);
			String line;
			
			HashSet<Integer> user = new HashSet<Integer>();
			HashSet<Integer> item = new HashSet<Integer>();
			HashSet<Integer> item_cat = new HashSet<Integer>();
			HashSet<Integer> item_loc = new HashSet<Integer>();
			int count = 0;
			
			UserItemLoc.initialize(SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC, SIZE_OF_CAT);
			
		try{			
			while((line = ratingReader.readLine())!=null)
			{
				String part[] = line.split("\t");
				
				int userId = Integer.parseInt(part[0]);
				int userLoc = Integer.parseInt(part[1]);
				int itemId = Integer.parseInt(part[2]);
				int itemLoc = Integer.parseInt(part[3]);
				int itemCat = Integer.parseInt(part[4]);
				
				UserItemLoc.put_add(userId, itemId, itemLoc, itemCat);
				UserItemLoc.put_add(userId, itemId, userLoc, itemCat);
				
				int userMapId = UserItemLoc.get_map1(userId);
				int itemMapId = UserItemLoc.get_map2(itemId);
				int userMapLoc = UserItemLoc.get_map3(userLoc);
				int itemMapLoc = UserItemLoc.get_map3(itemLoc);
				int itemMapCat = UserItemLoc.get_map4(itemCat);
				
				if(user_item.containsKey(userMapId)){
					user_item.get(userMapId).addItem(itemMapId, itemMapLoc, itemMapCat);
				}
				else{
					UserProfile up = new UserProfile(userMapId, userMapLoc);
					up.addItem(itemMapId, itemMapLoc, itemMapCat);
					user_item.put(userMapId, up);
				}
				
				user.add(userId);
				item.add(itemId);
				item_cat.add(itemCat);
				item_loc.add(itemLoc);
				count++;
			}
			
			System.out.println("user number : "+user.size()+"; "+UserItemLoc.size1);
			System.out.println("item number : "+item.size()+"; "+UserItemLoc.size2);
			System.out.println("item loc number : "+item_loc.size());
			System.out.println("item cat number : "+item_cat.size());
			System.out.println("votes number : "+count);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		int topic_count = 150;
		//int topic_count = 10;
		double[] alpha = new double[topic_count];
		//double[] alpha_2 = new double[topic_count];
		for(int i=0; i<topic_count; i++){
			//alpha[i] = 50.0 / topic_count;
			alpha[i] = 10.0 / topic_count;
		}
		double[] beta = new double[item.size()];
		for(int i=0; i<item.size(); i++){
			beta[i] = 0.01;
		}
		double[] beta_2 = new double[UserItemLoc.size3];
		for(int i=0; i<UserItemLoc.size3; i++){
			beta_2[i] = 0.01;
		}
		//double[] gamma = new double[2];
		//gamma[0] = 0.5;
		//gamma[1] = 0.5;
		
		int iterations = 500;
		int burnIn = 300;
		int sampleLag = 5;
		
		CA_baseline ca = new CA_baseline(UserItemLoc.size2, topic_count, UserItemLoc.size1, 
								  		UserItemLoc.size4, 
								  		alpha, beta, beta_2, iterations,
								  		sampleLag, burnIn, OutputPath,
								  		user_item);

		return ca;
		}
	
		public static void call_LDA(int strategy){
			Path = "/home/hzt/workspace5/fashion/doubanevent/LDA_"+strategy+"/";
			String filename = "/home/hzt/workspace5/fashion/doubanevent/user_item_train_"+strategy;
			HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
			
			
			String path = "/home/hzt/workspace5/fashion/doubanevent/";
			int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
			int[] ret = read_maps_cat(path);
			SIZE_OF_USER = ret[0];
			SIZE_OF_ITEM = ret[1];
			SIZE_OF_LOC = ret[2];
			
			
			UserTagPostMap utpm = new UserTagPostMap();
			LDA ula = read_data_LDA_baseline(filename, user_item, Path, utpm,
									SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);
			ula.train();	
		}
	
	
	
	public static void _call_ILCA_LDA(int strategy){
		Path = "/home/hzt/workspace5/fashion/doubanevent/ILCA_"+strategy+"_K200/";
		String filename = "/home/hzt/workspace5/fashion/doubanevent/user_item_train_"+strategy;
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CAT=0;
		int[] ret = read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CAT = ret[3];
		
		UserTagPostMap utpm = new UserTagPostMap();
		ILCA_LDA ilca = read_data_ILCA(filename, user_item, Path, utpm,
										SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC, 
										SIZE_OF_CAT);

		ilca.train();	
	}
	
	
	// baseline-1
	// same as ULA_LDA but use itemLoc instead of userLoc
	public static void call_LA_baseline(int strategy){
		Path = "/home/hzt/workspace5/fashion/doubanevent/LA_"+strategy+"/";
		String filename = "/home/hzt/workspace5/fashion/doubanevent/user_item_train_"+strategy;
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CAT=0;
		int[] ret = read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CAT = ret[3];
		
		UserTagPostMap utpm = new UserTagPostMap();
		LA_baseline la = read_data_LA_baseline(filename, user_item, Path, utpm,
											SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);

		la.train();	
	}
	
	
	

	// baseline-2
	// same as ULA_LDA but use itemLoc instead of userLoc
	public static void call_CA_baseline(int strategy){
		Path = "/home/hzt/workspace5/fashion/doubanevent/CA_"+strategy+"/";
		String filename = "/home/hzt/workspace5/fashion/doubanevent/user_item_train_"+strategy;
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		
		String path = "/home/hzt/workspace5/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0, SIZE_OF_CAT=0;
		int[] ret = read_maps_cat(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		SIZE_OF_CAT = ret[3];
		
		UserTagPostMap utpm = new UserTagPostMap();
		CA_baseline ca = read_data_CA_baseline(filename, user_item, Path, utpm,
											SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC,
											SIZE_OF_CAT);

		ca.train();	
	}
	
	
	public static void main(String args[])
	{	
		//arrange_data_cat();
		
		_call_ILCA_LDA(1);
		//_call_ILCA_LDA(2);
		//_call_ILCA_LDA(3);

		// strategy 1 : var city
		//call_LA_baseline(1);
		//call_LA_baseline(2);
		//call_LA_baseline(3);
		// 
		//call_CA_baseline(1);
		//call_CA_baseline(2);
		//call_CA_baseline(3);
		
		//call_LDA(1);
		//call_LDA(2);
		//call_LDA(3);
	}
	

}
