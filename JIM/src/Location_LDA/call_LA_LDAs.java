package Location_LDA;

import input_output_interface.data_storage;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;

import ancillary.matrix_and_map_2;


public class call_LA_LDAs {
	public static String Path = "";
	
	public static HashMap<String, Integer> loc_map = new HashMap<String, Integer>();
	public static HashMap<String, Integer> item_map = new HashMap<String, Integer>();
	public static HashMap<String, Integer> user_map = new HashMap<String, Integer>();
	
	public static HashMap<Integer, Integer> user_loc = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> item_loc = new HashMap<Integer, Integer>();
	
	
	public static int loc_count  = 0;
	
	public static void user_map(String path){
		try{
			String userMapFile = path+"user_map.txt";
			OutputStreamWriter oswpf = data_storage.file_handle(userMapFile);
			
			int tot_count = 0;
			int count = 0;
			
			// user_2
			//String userFile = path+"user_2.txt";
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
		/*	
			// user_4
			userFile = path+"user_4.txt";
			userReader= data_storage.file_handle_read(userFile);

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
		*/
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
	
	public static void item_map(String path){
		try{
			String itemMapFile = path+"item_map.txt";
			OutputStreamWriter oswpf = data_storage.file_handle(itemMapFile);
			
			int count = 0;
			
			// event_2
			//String itemFile = path+"event_2.txt";
			String itemFile = path+"events.tsv";
			BufferedReader itemReader= data_storage.file_handle_read(itemFile);
			String line;

			while((line = itemReader.readLine())!=null){
				String part[] = line.split("\t");
				
				if(!item_map.containsKey(part[0]))
					item_map.put(part[0], count++);
				
				if(!loc_map.containsKey(part[1]))
					loc_map.put(part[1], loc_count++);
				
				item_loc.put(item_map.get(part[0]), loc_map.get(part[1]));
			}
		/*	
			// event_4
			itemFile = path+"event_4.txt";
			itemReader= data_storage.file_handle_read(itemFile);

			while((line = itemReader.readLine())!=null){
				String part[] = line.split("\t");
				
				if(!item_map.containsKey(part[0]))
					item_map.put(part[0], count++);
				
				if(!loc_map.containsKey(part[1]))
					loc_map.put(part[1], loc_count++);	
				
				item_loc.put(item_map.get(part[0]), loc_map.get(part[1]));
			}
		*/
			// Output
			for(String key : item_map.keySet()){
				int itemId = item_map.get(key);
				int itemLoc = item_loc.get(itemId);
				oswpf.write(key+"\t"+itemId+"\t"+itemLoc+"\n");
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
			String locMapFile = path+"loc_map.txt";
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
	
	
	public static void arrange_user_item_file(String path){
		try{
			String itemMapFile = path+"user_item.txt";
			OutputStreamWriter oswpf = data_storage.file_handle(itemMapFile);
			
			// 2
			//String itemFile = path+"user_event_2.txt";
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
					
					oswpf.write(userId+"\t"+userLoc+"\t"+itemId+"\t"+itemLoc+"\n");
				}
			}
		/*	
			// 4
			itemFile = path+"user_event_4.txt";
			itemReader= data_storage.file_handle_read(itemFile);

			while((line = itemReader.readLine())!=null){
				String part[] = line.split("\t");
				
				if(user_map.containsKey(part[0]) && item_map.containsKey(part[1])){
					int userId = user_map.get(part[0]);
					int userLoc = user_loc.get(userId);
					int itemId = item_map.get(part[1]);
					int itemLoc = item_loc.get(itemId);
					
					oswpf.write(userId+"\t"+userLoc+"\t"+itemId+"\t"+itemLoc+"\n");
				}
			}
		 */
			oswpf.flush();
			oswpf.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	
	/**
	 * arrange data
	 */
	public static void arrange_data(){
		Path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		user_map(Path);
		item_map(Path);
		loc_map(Path);
		arrange_user_item_file(Path);
	}
	

	public static int[] read_maps(String path){
		
		int SIZE_OF_USER = 0;
		int SIZE_OF_ITEM = 0;
		int SIZE_OF_LOC = 0;
		
		try{
			// user_map
			BufferedReader ratingReader= data_storage.file_handle_read(path+"user_map.txt");
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
			ratingReader= data_storage.file_handle_read(path+"item_map.txt");
		
			while((line = ratingReader.readLine())!=null)
			{
				String part[] = line.split("\t");
				int itemId = Integer.parseInt(part[1]);
				int itemLoc = Integer.parseInt(part[2]);
				item_map.put(part[0], itemId);
				item_loc.put(itemId, itemLoc);
				
				SIZE_OF_ITEM++;
			}
		
			// loc_map
			ratingReader= data_storage.file_handle_read(path+"loc_map.txt");
			while((line = ratingReader.readLine())!=null)
			{
				String part[] = line.split("\t");
				int locId = Integer.parseInt(part[1]);
				loc_map.put(part[0], locId);
				
				SIZE_OF_LOC++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return new int[]{SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC};
	}
	
	

	
	
	// LDA
	public static LDA read_data_LDA(String filename, HashMap<Integer, UserProfile> user_item, 
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
				
				if(userMapLoc >= 736){
					System.out.println(line+"\t"+userMapLoc);
				}
				
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
		
		int topic_count = 50;
		//int topic_count = 10;
		double[] alpha = new double[topic_count];
		double[] alpha_2 = new double[topic_count];
		for(int i=0; i<topic_count; i++){
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
		
		LDA ula = new LDA(item.size(), topic_count, user.size(), 
								
								  alpha, beta, iterations,
								  sampleLag, burnIn, OutputPath,
								  user_item);

		return ula;
	}
	
	
	// ULA
		public static ULA_LDA read_data(String filename, HashMap<Integer, UserProfile> user_item, 
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
					
					if(userMapLoc >= 736){
						System.out.println(line+"\t"+userMapLoc);
					}
					
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
			
			int topic_count = 50;
			//int topic_count = 10;
			double[] alpha = new double[topic_count];
			double[] alpha_2 = new double[topic_count];
			for(int i=0; i<topic_count; i++){
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
			
			ULA_LDA ula = new ULA_LDA(UserItemLoc.size2, topic_count, UserItemLoc.size1, 
									  UserItemLoc.size3, 
									  alpha, alpha_2, beta, gamma, iterations,
									  sampleLag, burnIn, OutputPath,
									  user_item);

			return ula;
		}
	
	
	
	
	
	// ILA
	public static ILA_LDA read_data_ILA(String filename, HashMap<Integer, UserProfile> user_item, 
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
				
				if(userMapLoc >= 736){
					System.out.println(line+"\t"+userMapLoc);
				}
				
				if(user_item.containsKey(userMapId)){
					user_item.get(userMapId).addItem(itemMapId, itemMapLoc);
				}
				else{
					UserProfile up = new UserProfile(userMapId, userMapLoc);
					up.addItem(itemMapId, itemMapLoc);
					user_item.put(userMapId, up);
				}
				
				// just for count
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
		
		int topic_count = 50;
		//int topic_count = 10;
		double[] alpha = new double[topic_count];
		//double[] alpha_2 = new double[topic_count];
		for(int i=0; i<topic_count; i++){
			alpha[i] = 10.0 / topic_count;
			//alpha_2[i] = 10.0 / topic_count;
		}
		double[] beta = new double[item.size()];
		for(int i=0; i<item.size(); i++){
			beta[i] = 0.01;
		}
		double[] beta_2 = new double[UserItemLoc.size3];
		for(int i=0; i<UserItemLoc.size3; i++){
			beta_2[i] = 0.5;
		}
		//double[] gamma = new double[2];
		//gamma[0] = 0.5;
		//gamma[1] = 0.5;
		
		int iterations = 500;
		int burnIn = 300;
		int sampleLag = 5;
		
		ILA_LDA ila = new ILA_LDA(UserItemLoc.size2, topic_count, UserItemLoc.size1, 
								  UserItemLoc.size3, 
								  alpha, beta, beta_2, iterations,
								  sampleLag, burnIn, OutputPath,
								  user_item);

		return ila;
	}
	
	
	
	
	// UILA
	public static UILA_LDA read_data_UILA(String filename, HashMap<Integer, UserProfile> user_item, 
										String OutputPath, UserTagPostMap UserItemLoc,
										int SIZE_OF_USER, int SIZE_OF_ITEM, int SIZE_OF_LOC_USER,
										int SIZE_OF_LOC_ITEM){

			BufferedReader ratingReader = data_storage.file_handle_read(filename);
			String line;
			
			HashSet<Integer> user = new HashSet<Integer>();
			HashSet<Integer> item = new HashSet<Integer>();
			HashSet<Integer> loc = new HashSet<Integer>();
			//HashSet<Integer> item_loc = new HashSet<Integer>();
			int count = 0;
			
			UserItemLoc.initialize(SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC_ITEM);
			
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
				
				user.add(userId);
				item.add(itemId);
				loc.add(userLoc);
				loc.add(itemLoc);
				count++;
			}
			
			if(loc.size()!=UserItemLoc.size3)
			{
				System.out.println("location number error!");
			}
			System.out.println("user number : "+user.size()+"; "+UserItemLoc.size1);
			System.out.println("item number : "+item.size()+"; "+UserItemLoc.size2);
			System.out.println("loc number : "+UserItemLoc.size3);
			//System.out.println("item loc number : "+UserItemLoc.size3);
			System.out.println("votes number : "+count);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		int topic_count = 250;
		//int topic_count = 10;
		double[] alpha = new double[topic_count];
		double[] alpha_2 = new double[topic_count];
		for(int i=0; i<topic_count; i++){
			alpha[i] = 10.0 / topic_count;
			alpha_2[i] = 10.0 / topic_count;
		}
		double[] beta = new double[item.size()];
		for(int i=0; i<item.size(); i++){
			beta[i] = 0.01;
		}
		double[] beta_2 = new double[UserItemLoc.size3];
		for(int i=0; i<UserItemLoc.size3; i++){
			beta_2[i] = 0.01;
		}
		double[] gamma = new double[2];
		gamma[0] = 0.5;
		gamma[1] = 0.5;
		
		int iterations = 500;
		int burnIn = 300;
		int sampleLag = 5;
		
		UILA_LDA uila = new UILA_LDA(UserItemLoc.size2, topic_count, UserItemLoc.size1, 
				UserItemLoc.size3, UserItemLoc.size3,  
								  alpha, alpha_2, beta, beta_2, gamma, iterations,
								  sampleLag, burnIn, OutputPath,
								  user_item);
		
		return uila;
	}
	
	
	
	
	public static void call_ULA_LDA(){
		Path = "/home/huzhiting/workspace3/fashion/doubanevent/ULA/";
		String filename = "/home/huzhiting/workspace3/fashion/doubanevent/user_item_train";
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		UserTagPostMap utpm = new UserTagPostMap();
		ULA_LDA ula = read_data(filename, user_item, Path, utpm,
								SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);

		ula.train();	
	}
	
	public static void call_ULA_LDA2(){
		Path = "/home/huzhiting/workspace3/fashion/doubanevent/ULA_Uniform/";
		String filename = "/home/huzhiting/workspace3/fashion/doubanevent/user_item_train";
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		UserTagPostMap utpm = new UserTagPostMap();
		ULA_LDA ula = read_data(filename, user_item, Path, utpm,
								SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);

		ula.train();	
	}
	
	
	public static void call_LDA(){
		Path = "/home/huzhiting/workspace3/fashion/doubanevent/LDA/";
		String filename = "/home/huzhiting/workspace3/fashion/doubanevent/user_item_train";
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		UserTagPostMap utpm = new UserTagPostMap();
		LDA ula = read_data_LDA(filename, user_item, Path, utpm,
								SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);

		ula.train();	
	}
	
	
	
	
	public static void call_ILA_LDA(){
		Path = "/home/huzhiting/workspace3/fashion/doubanevent/ILA/";
		String filename = "/home/huzhiting/workspace3/fashion/doubanevent/user_item_train";
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		UserTagPostMap utpm = new UserTagPostMap();
		ILA_LDA ila = read_data_ILA(filename, user_item, Path, utpm,
									SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC);

		ila.train();	
	}
	
	
	
	public static void call_UILA_LDA(){
		Path = "/home/huzhiting/workspace3/fashion/doubanevent/UILA/";
		String filename = "/home/huzhiting/workspace3/fashion/doubanevent/user_item_train";
		HashMap<Integer, UserProfile> user_item = new HashMap<Integer, UserProfile>();
		
		
		String path = "/home/huzhiting/workspace3/fashion/doubanevent/";
		int SIZE_OF_USER=0, SIZE_OF_ITEM=0, SIZE_OF_LOC=0;
		int[] ret = read_maps(path);
		SIZE_OF_USER = ret[0];
		SIZE_OF_ITEM = ret[1];
		SIZE_OF_LOC = ret[2];
		
		UserTagPostMap utpm = new UserTagPostMap();
		UILA_LDA uila = read_data_UILA(filename, user_item, Path, utpm,
										SIZE_OF_USER, SIZE_OF_ITEM, SIZE_OF_LOC, 
										SIZE_OF_LOC);

		uila.train();	
	}
	
	
	
	public static void main(String args[])
	{	
		//arrange_data();
		
		  //call_ULA_LDA2();
	       call_UILA_LDA();
		  //call_ILA_LDA();
		  //call_ULA_LDA();
		 // call_LDA();
		 //call_ULA_LDA();

	 
	}
	
	
	
}
