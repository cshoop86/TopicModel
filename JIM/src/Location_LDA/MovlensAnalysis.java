package Location_LDA;
import java.util.*;
import java.io.*;
public class MovlensAnalysis {

	/**
	 * @param args
	 */
   
	public static HashMap<Integer,ArrayList<String>> movies=
			new HashMap<Integer,ArrayList<String>>();
	public static HashMap<Integer,String> users=new HashMap<Integer,String>();
	public static HashMap<Integer,Integer> users2=new HashMap<Integer,Integer>();
	public static HashMap<Integer, ArrayList<Pair>> user_movie=
			new HashMap<Integer, ArrayList<Pair>>();
	
	public static HashMap<String,HashMap<String,Double>> location_catetory_count=
			new HashMap<String,HashMap<String,Double>>();
	
	public static HashMap<String,HashMap<String,Integer>> location_catetory_count2=
			new HashMap<String,HashMap<String,Integer>>();
	
	public static HashMap<String,List<Map.Entry<String,Double>>> ranked_location_cate_count
	=new HashMap<String,List<Map.Entry<String,Double>>>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
     String dir="/raid5-on-2T/home/bestzhi/datasets/recommendation-dataset/movielens1M/million-ml-data/";
     
     PostCode2LocationService.load("locationpostcode");
     loadusers(dir+"users.dat");
     StringBuffer buf=new StringBuffer();
     for(int u_id:users2.keySet())
     {
    	 String line=u_id+","+users2.get(u_id)+"\r\n";
    	 buf.append(line);
     }
     LocalityAnalysis.outputdata(buf,"user_location.csv");
     
    // loadmovies(dir+"movies.dat");
    // loadusermovies(dir+"ratings.dat");
    // computepreferencelocalty();
     
	}
	
    public static void computepreferencelocalty()
    {
    	for(int u_id:user_movie.keySet())
    	{  
    		if(users.containsKey(u_id))
    		{
    		  String location=users.get(u_id);
    		  if(!location_catetory_count.containsKey(location))
    		  {
    			 HashMap<String,Double> ma=new HashMap<String,Double>();
    			 location_catetory_count.put(location,ma);
    			 
    			 HashMap<String,Integer> ma2=new HashMap<String,Integer>();
    			 location_catetory_count2.put(location,ma2);
    		  }
    		  for(Pair p:user_movie.get(u_id))
    		  {  
    			  ArrayList<String> cates=movies.get(p.item_id);
    			  for(String cate:cates)
    			  {
    			  if(location_catetory_count.get(location).containsKey(cate))
    			  {   
    				  double value=location_catetory_count.get(location).get(cate);
    				  location_catetory_count.get(location).put(cate, value+p.rating);
    				  
    				  int value2=location_catetory_count2.get(location).get(cate);
    				  location_catetory_count2.get(location).put(cate, value2+1);
    			  }
    			  else
    			  {
    				  location_catetory_count.get(location).put(cate, p.rating*1.0); 
    				  location_catetory_count2.get(location).put(cate, 1);
    			  }
    			  }
    			  
    		  }
    		}
    		
    				
    	}
    	
    	for(String location:location_catetory_count.keySet())
    	{
    		for(String cate:location_catetory_count.get(location).keySet())
    		{
    			double va=location_catetory_count.get(location).get(cate)/location_catetory_count2.get(location).get(cate);
    			location_catetory_count.get(location).put(cate,va);
    		}
    	}
    	for(String location:location_catetory_count.keySet())
    	{
    		List<Map.Entry<String,Double>> li=rankingMap(location_catetory_count.get(location));
    		ranked_location_cate_count.put(location,li);
    	}
    	
    	StringBuffer buf=new StringBuffer();
    	for(String loc:ranked_location_cate_count.keySet())
    	{
    	  /*int sum=0;
    	  for(Map.Entry<String,Integer> ent:ranked_location_cate_count.get(loc))
    	  {
    		 sum+=ent.getValue();
    	  }*/
    	  String line=loc;
    	  for(Map.Entry<String,Double> ent:ranked_location_cate_count.get(loc))
    	  {
    		  line=line+"\t"+ent.getKey()+":"+ent.getValue();
    	  }
    	  line=line+"\r\n";
    	  buf.append(line);
    	}
    	
    	LocalityAnalysis.outputdata(buf,"Movielens_Locality_Preference_rating.tsv");
    		
    }
	
    public static List<Map.Entry<String,Double>> rankingMap(HashMap<String,Double> hittingTime)
	{
		List<Map.Entry<String,Double>> data=new ArrayList<Map.Entry<String,Double>>(hittingTime.entrySet());
		 Collections.sort(data, new Comparator<Map.Entry<String,Double>>()
	  	      {  
	  	          public int compare(Map.Entry<String,Double> o1, Map.Entry<String,Double> o2)
	  	          {
	  	           if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue()>o1.getValue()){
	  	            return 1;
	  	           }else{
	  	            return -1;
	  	           }
	  	             
	  	          }
	  	      });	
		 
		 return data;

	}
	public static void loadusers(String userfile)
	{
		
		BufferedReader reader;
		try{
			
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(userfile),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("::");
				int id=Integer.parseInt(tokens[0]);
				int postcode;
				if(tokens[4].contains("-"))
				{
				 String[] ts=tokens[4].split("-");
				 postcode=Integer.parseInt(ts[0]);
				}
				else
				postcode=Integer.parseInt(tokens[4]);
				if(PostCode2LocationService.postcode_state.containsKey(postcode))
				{
					String location=PostCode2LocationService.postcode_state.get(postcode);
					users.put(id,location);
					
				}
				users2.put(id,postcode);
				line=reader.readLine();
			}
			
			reader.close();
			
			}catch(IOException e)
			{
				e.printStackTrace();
			}
	}
	
	public static void loadmovies(String moviesfile)
	{
		
		BufferedReader reader;
		try{
			
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(moviesfile),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("::");
				if(tokens.length!=3)
					continue;
				int id=Integer.parseInt(tokens[0]);
				String types=tokens[tokens.length-1];
				String[] tokes=types.split("\\|");
				ArrayList<String> li=new ArrayList<String>();
				for(String each:tokes)
				{   if(each.equals("")||each.equals(" "))
				{
					
				}
				else
					li.add(each);
				}
				movies.put(id,li);
				
				
				line=reader.readLine();
			}
			
			reader.close();
			
			}catch(IOException e)
			{
				e.printStackTrace();
			}
	}
	
	public static void loadusermovies(String usermoviesfile)
	{
		
		BufferedReader reader;
		try{
			
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(usermoviesfile),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("::");
				int user_id=Integer.parseInt(tokens[0]);
				int item_id=Integer.parseInt(tokens[1]);
				int rating=Integer.parseInt(tokens[2]);
				Pair p=new Pair();
				p.item_id=item_id;
				p.rating=rating;
				if(user_movie.containsKey(user_id))
				{
					user_movie.get(user_id).add(p);
				}
				else
				{
					ArrayList<Pair> li=new ArrayList<Pair>();
					li.add(p);
					user_movie.put(user_id,li);
				}
				
				line=reader.readLine();
				
			}
			
			reader.close();
			}
		    catch(IOException e)
			{
		    	e.printStackTrace();
			}
	}

}
