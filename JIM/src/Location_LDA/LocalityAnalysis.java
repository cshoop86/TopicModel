package Location_LDA;
import java.util.*;
import java.io.*;
public class LocalityAnalysis {

	/**
	 * @param args
	 */
	
	public static HashMap<String,HashSet<String>> location_userlist=
			new HashMap<String,HashSet<String>>();
	
	public static HashMap<String,Event> events=new HashMap<String,Event>();
	public static HashMap<String,HashSet<String>> user_eventlist=
			new HashMap<String,HashSet<String>>();
	public static HashMap<String,HashMap<String,Integer>> location_categorycounts= 
			 new HashMap<String,HashMap<String,Integer>>(); 
	public static HashMap<String,HashMap<String,Integer>> location_locationcounts= 
			 new HashMap<String,HashMap<String,Integer>>(); 
	
	public static HashMap<String,List<Map.Entry<String,Integer>>> ranked_location_categorycounts= 
			 new HashMap<String,List<Map.Entry<String,Integer>>>(); 
	public static HashMap<String,List<Map.Entry<String,Integer>>> ranked_location_locationcounts= 
			 new HashMap<String,List<Map.Entry<String,Integer>>>(); 
	
	public static  int count_user=0;
	public static int count_event=0;
	public static int user_event=0;
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		
		
		create_dataset_interface();
		
		compute_local_preference_interface();
		
		
		
		
	
	}
	
	public static void compute_local_preference_interface()
	{
		String dir="/home/dbzhi/workspace/fashion/douban_event_data_set/location_verified_data_set/";
		String userfile=dir+"verified_users.tsv";
		String eventfile=dir+"verified_events.tsv";
		String user_events=dir+"verified_user_events.tsv";
		String preferencelocality=dir+"preferencelocality.tsv";
		String travellocality=dir+"travellocality.tsv";
		loaduserdata(userfile);
		loadevent(eventfile);
		loaduserevent(user_events);
		compute_preference_locality();
		output(preferencelocality,travellocality);
		
	}
	
	public static void create_dataset_interface()
	{
String dir="/home/dbzhi/workspace/fashion/douban_event_data_set/location_verified_data_set/";
		
		String user_locationfile1="location_0.txt";
		String user_locationfile2="location_1.txt";
		String user_locationfile3="location_2.txt";
		String user_locationfile4="location_3.txt";
		String user_locationfile5="location_4.txt";
		String user_locationfile6="location_5.txt";
		String user_locationfile7="location_6.txt";
		String user_locationfile8="location_7.txt";
		String user_locationfile9="location_8.txt";
		String user_locationfile10="location_9.txt";
		
		
		String eventfile1="event_2.txt";
		String eventfile2="event_4.txt";
		String eventfile3="event_0.txt";
		String eventfile4="event_1.txt";
		String eventfile5="event_3.txt";
		String eventfile6="event_5.txt";
		String eventfile7="event_6.txt";
		String eventfile8="event_7.txt";
		String eventfile9="event_8.txt";
		String eventfile10="event_9.txt";
		
		String usereventfile1="user_event_2.txt";
		String usereventfile2="user_event_4.txt";
		String usereventfile3="user_event_0.txt";
		String usereventfile4="user_event_3.txt";
		String usereventfile5="user_event_1.txt";
		String usereventfile6="user_event_5.txt";
		String usereventfile7="user_event_6.txt";
		String usereventfile8="user_event_7.txt";
		String usereventfile9="user_event_8.txt";
		String usereventfile10="user_event_9.txt";
		loaduserdata(dir+user_locationfile1);
		loaduserdata(dir+user_locationfile2);
		loaduserdata(dir+user_locationfile3);
		loaduserdata(dir+user_locationfile4);
		loaduserdata(dir+user_locationfile5);
		loaduserdata(dir+user_locationfile6);
		loaduserdata(dir+user_locationfile7);
		loaduserdata(dir+user_locationfile8);
		loaduserdata(dir+user_locationfile9);
		loaduserdata(dir+user_locationfile10);
		
		
		loadevent(dir+eventfile1);
		loadevent(dir+eventfile2);
		loadevent(dir+eventfile3);
		loadevent(dir+eventfile4);
		loadevent(dir+eventfile5);
		loadevent(dir+eventfile6);
		loadevent(dir+eventfile7);
		loadevent(dir+eventfile8);
		loadevent(dir+eventfile9);
		loadevent(dir+eventfile10);
	
		
	  loaduserevent(dir+usereventfile1);
	  loaduserevent(dir+usereventfile2);
	  loaduserevent(dir+usereventfile3);
	  loaduserevent(dir+usereventfile4);
	  loaduserevent(dir+usereventfile5);
	  loaduserevent(dir+usereventfile6);
	  loaduserevent(dir+usereventfile7);
	  loaduserevent(dir+usereventfile8);
	  loaduserevent(dir+usereventfile9);
	  loaduserevent(dir+usereventfile10);
	  createdinferreddataset();
			
	}
	
	
	public static void createdinferreddataset()
	{  
		HashSet<String> events2=new HashSet<String>();
		HashSet<String> events3=new HashSet<String>();
		StringBuffer buf1=new StringBuffer();
		StringBuffer buf2=new StringBuffer();
		StringBuffer buf3=new StringBuffer();
		StringBuffer buf4=new StringBuffer();
		StringBuffer buf5=new StringBuffer();
		StringBuffer buf6=new StringBuffer();
		for(String user:user_eventlist.keySet())
		{
		  if(user_eventlist.get(user).size()>=10)
		  {
			  
			  HashMap<String,Integer> ma=new HashMap<String,Integer>();
			  for(String event:user_eventlist.get(user))
			  {
			   String line1=user+"\t"+ event+"\r\n";
			    buf1.append(line1);
			    if(!events2.contains(event))
			    {
			    	events2.add(event);
			    }
			    
			    if(events.containsKey(event))
			    {
			    String loc=events.get(event).location;
			    if(ma.containsKey(loc))
			    {
			      ma.put(loc,ma.get(loc)+1);	
			    }
			    else
			    {
			    	ma.put(loc,1);
			    }
			    }
			  }
			  
			  List<Map.Entry<String,Integer>> list= rankingMap(ma);
			  String inferredlocation=list.get(0).getKey();
			  String line2=user+"\t"+inferredlocation+"\r\n";
			  buf2.append(line2);
			  if(location_userlist.containsKey(inferredlocation))
			  {
				  if(location_userlist.get(inferredlocation).contains(user))
				  {
					  String line3=user+"\t"+inferredlocation+"\r\n";
					  buf3.append(line3);
					  
					  for(String event:user_eventlist.get(user))
					  {
						  
						  String line1=user+"\t"+event+"\r\n";
						  buf5.append(line1);
						  
						  if(!events3.contains(event))
						  {
							  events3.add(event);
						  }
					  }
					  
					  
				  }
				  
				  
				  
				  
			  }
			  
					  
			  
		  }
			
		}
		
		
		for(String event:events2)
		{
			String line=event+"\t"+events.get(event).location+"\t"+events.get(event).category+"\r\n";
			buf4.append(line);
		}
		
		for(String event:events3)
		{
			String line=event+"\t"+events.get(event).location+"\t"+events.get(event).category+"\r\n";
			buf6.append(line);
		}
		outputdata(buf1,"user_events.tsv");
		outputdata(buf2,"users.tsv");
		outputdata(buf3,"verified_users.tsv");
		outputdata(buf4,"events.tsv");
		outputdata(buf5,"verified_user_events.tsv");
		outputdata(buf6,"verified_events.tsv");
		
		
	}
	
	public static void output(String file1,String file2)
	{  
		StringBuffer buf1=new StringBuffer();
		for(String city:ranked_location_categorycounts.keySet())
		{
			List<Map.Entry<String,Integer>> li=ranked_location_categorycounts.get(city);
			int sum=0;
			for(Map.Entry<String,Integer> ent:li)
			{
				sum+=ent.getValue();
			}
			
			String line=city;
			for(Map.Entry<String,Integer> ent:li)
			{
				//sum+=ent.getValue();
				line=line+"\t"+ent.getKey()+":"+ent.getValue()*1.0/sum;
			}
			line=line+"\r\n";
			buf1.append(line);
		}
		StringBuffer buf2=new StringBuffer();
		for(String city:ranked_location_locationcounts.keySet())
		{
			List<Map.Entry<String,Integer>> li=ranked_location_locationcounts.get(city);
			int sum=0;
			for(Map.Entry<String,Integer> ent:li)
			{
				sum+=ent.getValue();
			}
			
			String line=city;
			for(Map.Entry<String,Integer> ent:li)
			{
				//sum+=ent.getValue();
				line=line+"\t"+ent.getKey()+":"+ent.getValue()*1.0/sum;
			}
			line=line+"\r\n";
			buf2.append(line);
		}
		
		outputdata(buf1,file1);
		outputdata(buf2,file2);
		
	}
	
	public static void outputdata(StringBuffer buf,String file)
	{
		BufferedWriter writer=null;
		try{
			
			writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
			writer.write(buf.toString());
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static List<Map.Entry<String,Integer>> rankingMap(HashMap<String,Integer> hittingTime)
	{
		List<Map.Entry<String,Integer>> data=new ArrayList<Map.Entry<String,Integer>>(hittingTime.entrySet());
		 Collections.sort(data, new Comparator<Map.Entry<String,Integer>>()
	  	      {  
	  	          public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2)
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
	public static void compute_preference_locality()
	{
		
		for(String city:location_userlist.keySet())
		{  
			
			if(location_userlist.get(city).size()>=1)
			for(String user:location_userlist.get(city))
			{
				if(user_eventlist.containsKey(user))
				for(String event:user_eventlist.get(user))
				{
					String loc=events.get(event).location;
					String cate=events.get(event).category;
					user_event++;
					if(location_locationcounts.containsKey(city))
					{
						if(location_locationcounts.get(city).containsKey(loc))
						{
							int value=location_locationcounts.get(city).get(loc);
							location_locationcounts.get(city).put(loc,value+1);
						}
						else
						{
							location_locationcounts.get(city).put(loc,1);
						}
					}
					else
					{
						HashMap<String,Integer> ma=new HashMap<String,Integer>();
						ma.put(loc,1);
						location_locationcounts.put(city,ma);
						
					}
					
					
					
					
					
					if(location_categorycounts.containsKey(city))
					{
						if(location_categorycounts.get(city).containsKey(cate))
						{
							int value=location_categorycounts.get(city).get(cate);
							location_categorycounts.get(city).put(cate,value+1);
						}
						else
						{
							location_categorycounts.get(city).put(cate,1);
						}
					}
					else
					{
						HashMap<String,Integer> ma=new HashMap<String,Integer>();
						ma.put(cate,1);
						location_categorycounts.put(city,ma);
						
					}
					
					
				}
				
			}
		}
		
		System.out.println("statistic is successful");
		
		for(String city:location_categorycounts.keySet())
		{
			List<Map.Entry<String,Integer>> li= rankingMap(location_categorycounts.get(city));
			ranked_location_categorycounts.put(city,li);
		}
		
		for(String city:location_locationcounts.keySet())
		{
			List<Map.Entry<String,Integer>> li= rankingMap(location_locationcounts.get(city));
			ranked_location_locationcounts.put(city,li);
		}
		
		System.out.println("rank is successful");
	}
	
	public static void loaduserdata(String user_location_file)
	{
		BufferedReader reader;
		try{
			
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(user_location_file),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("\t");
				if(tokens[1].equals("")||tokens[1].equals(" ")||tokens[1]==null||tokens.length!=2||tokens[1]=="")
				{
					
				}
				else
				{
					//System.out.println(line);
					
					if(location_userlist.containsKey(tokens[1]))
					{
						if(!location_userlist.get(tokens[1]).contains(tokens[0]))
						{
							location_userlist.get(tokens[1]).add(tokens[0]);
							count_user++;
						}
					}
					else
					{
						HashSet<String> li=new HashSet<String>();
						li.add(tokens[0]);
						location_userlist.put(tokens[1],li);
						count_user++;
					}
					
				}
				
				line=reader.readLine();
			}
			reader.close();
		 System.out.println("load user data successful");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void loadevent(String event_file)
	{
		BufferedReader reader;
		try{
			
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(event_file),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("\t");
				
				Event ev=new Event();
				ev.ids=tokens[0];
				ev.location=tokens[1];
				
				ev.category=tokens[tokens.length-1];
				if(!events.containsKey(ev.ids))
				{
				events.put(ev.ids,ev);
				count_event++;
				}
				line=reader.readLine();
			}
			
			reader.close();
			 System.out.println("load event data successful");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void loaduserevent(String user_event_file)
	{
		BufferedReader reader;
		try{
			
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(user_event_file),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("\t");
				if(user_eventlist.containsKey(tokens[0]))
				{
					if(!user_eventlist.get(tokens[0]).contains(tokens[1]))
					{
						user_eventlist.get(tokens[0]).add(tokens[1]);
					}
				}
				else
				{
					HashSet<String> ls=new HashSet<String>();
					ls.add(tokens[1]);
					user_eventlist.put(tokens[0], ls);
				}
				
				line=reader.readLine();
				
			}
			
			reader.close();
			 System.out.println("load user-event data successful");
		}
		catch(IOException e)
		{
		 e.printStackTrace();
		}
		
	}
}
