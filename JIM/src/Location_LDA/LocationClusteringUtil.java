package Location_LDA;
import java.util.*;
import java.io.*;
public class LocationClusteringUtil {
	
	public static HashMap<Double,HashMap<Double,ArrayList<Integer>>> re=new HashMap<Double,HashMap<Double,ArrayList<Integer>>>();
	/**
	 * @param args14316959  10505284 10505284
	 */
	public static int seperate_id=1799450;
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		/*String dir="/raid5-on-2T/home/bestzhi/datasets/location_based_datasets/Gowalla/";
		String inputfile=dir+"Gowalla_totalCheckins.txt";
		String checkinfile=dir+"Gowalla_usercheckin.csv";
		String userfile=dir+"Gowalla_user.csv";
		String venuefile=dir+"Gowalla_venue.csv";
		String usermapfile=dir+"Gowalla_usermap.csv";
		String venuelmapfile=dir+"Gowalla_venuelmap.csv";
		processGowalla(inputfile,checkinfile,userfile,venuefile,usermapfile,venuelmapfile);*/
		/*
		String dir="/raid5-on-2T/home/bestzhi/datasets/location_based_datasets/Gowalla/";
		//String file=dir+"user_lon_lat.csv";
		//String userfile=dir+"meetup_users.csv";
		String eventfile=dir+"Gowalla_venue.csv";
		processMeetup(null,eventfile);
		
		//remap(file,refile,mapfile);
		
		String user_loc=dir+"meetup_users_loc.csv";
		String event_lco=dir+"Gowalla_venues_loc.csv";
		String loc=dir+"Gowalla_location.csv";
		
		output(loc,event_lco,user_loc);*/
		
		String dir="/home/dbzhi/workspace/fashion/meetup/";
		String user_map=dir+"meetup_users_map.csv";
		String item_map=dir+"meetup_events_map.csv";
		String orfile=dir+"user_event.csv";
		
		HashMap<Integer,Integer> userm=new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> eventm=new HashMap<Integer,Integer>();
		
		userm=getmap(user_map);
		eventm=getmap(item_map);
		mapMeetupUserEvent(orfile,dir+"meetup_user_event.csv",userm,eventm);

	}
	
	
	public static void processMeetup(String userfile,String eventfile)
	{
		BufferedReader reader;
		try{
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(eventfile),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split(",");
				int id=Integer.parseInt(tokens[0]);
				double longitude=Double.parseDouble(tokens[1]);
				double latitude=Double.parseDouble(tokens[2]);
				addInstace(id,latitude,longitude);
				line=reader.readLine();
			}
			reader.close();
			if(userfile!=null)
			{
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(userfile),"UTF-8"));
			
			line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split(",");
				int id=Integer.parseInt(tokens[0])+seperate_id;
				double longitude=Double.parseDouble(tokens[1]);
				double latitude=Double.parseDouble(tokens[2]);
				addInstace(id,latitude,longitude);
				line=reader.readLine();
			}
			reader.close();
			}
			
			
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	
	public static void processGowalla(String inputfile,String checkinfile,String userfile,String venuefile,String usermapfile,String venuelmapfile)
	{
		BufferedReader reader;
		StringBuffer checkinbuf=new StringBuffer();
		StringBuffer ubuf=new StringBuffer();
		StringBuffer vbuf=new StringBuffer();
		StringBuffer ma_u=new StringBuffer();;
		StringBuffer ma_v=new StringBuffer();;
		int user_id=0;
		int item_id=0;
		HashMap<Integer,Integer> usermap=new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> itemmap=new HashMap<Integer,Integer>();
		try{
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(inputfile),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("\t");
				int u_id=Integer.parseInt(tokens[0]);
				double latitude=Double.parseDouble(tokens[2]);
				double longitude=Double.parseDouble(tokens[3]);
				int v_id=Integer.parseInt(tokens[4]);
				
				if(usermap.containsKey(u_id))
				{
					u_id=usermap.get(u_id);
					
					
				}
				else
				{
					usermap.put(u_id, user_id);
					u_id=user_id;
					ubuf.append(user_id+"\r\n");
					user_id++;
				}
				
				if(itemmap.containsKey(v_id))
				{
				 v_id=itemmap.get(v_id);	
				}
				else
				{
				  itemmap.put(v_id,item_id);
				  String t=item_id+","+longitude+","+latitude+"\r\n";
				  vbuf.append(t);
				  v_id=item_id;
				  item_id++;
				}
				
				String lin=u_id+","+v_id+"\r\n";
				checkinbuf.append(lin);
				
				
				
				line=reader.readLine();
				
			}
			LocalityAnalysis.outputdata(checkinbuf,checkinfile);
			LocalityAnalysis.outputdata(ubuf,userfile);
			LocalityAnalysis.outputdata(vbuf,venuefile);
			
			
			System.out.println("count of users:"+user_id);
			System.out.println("count of items:"+item_id);
			
			for(int u_id:usermap.keySet())
			{
				String l=u_id+","+usermap.get(u_id)+"\r\n";
				ma_u.append(l);
			}
			for(int v_id:itemmap.keySet())
			{
				String li=v_id+","+itemmap.get(v_id)+"\r\n";
				ma_v.append(li);
			}
			
			LocalityAnalysis.outputdata(ma_u,usermapfile);
			LocalityAnalysis.outputdata(ma_v,venuelmapfile);
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	
	// re-assign id 
	public static void remap(String file,String refile,String mapfile)
	{
		BufferedReader reader;
		StringBuffer buf=new StringBuffer();
		HashMap<Integer,Integer> ma=new HashMap<Integer,Integer>();
		try{
			int reid=0;
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split(",");
				String reline=reid+","+tokens[1]+","+tokens[2]+"\r\n";
				buf.append(reline);
				int id=Integer.parseInt(tokens[0]);
				ma.put(id,reid);
				reid++;
				line=reader.readLine();
				
			}
			
			System.out.println("count:"+reid);
			reader.close();
			LocalityAnalysis.outputdata(buf,refile);
			
			StringBuffer buf2=new StringBuffer();
			for(int key:ma.keySet())
			{
				String lin=key+","+ma.get(key)+"\r\n";
				buf2.append(lin);
			}
			
			LocalityAnalysis.outputdata(buf2,mapfile);
			}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	//file1 to store location_id, latitude, longitude
	//file2 to store  item_id, loation_id
	//file3 to store  user_id, location_id
	public static void output(String file1,String file2,String file3)
	{
		int id=0;
		StringBuffer buf=new StringBuffer();
		StringBuffer buf2=new StringBuffer();
		StringBuffer buf3=new StringBuffer();
		for(double latitude:re.keySet())
		{
			for(double longitude:re.get(latitude).keySet())
			{
				
				String line1=id+","+longitude+","+latitude+"\r\n";
				buf.append(line1);
				
				
				for(int ids:re.get(latitude).get(longitude))
				{  
					
					
					if(ids<seperate_id)
					{
						String line2=ids+","+id+"\r\n";
					buf2.append(line2);
					}
					else
					{
						int reid=ids-seperate_id;
						String line3=reid+","+id+"\r\n";
						buf3.append(line3);
						
					}
				}
				
				id++;
				
			}
		}
		
		LocalityAnalysis.outputdata(buf,file1);
		LocalityAnalysis.outputdata(buf2,file2);
		LocalityAnalysis.outputdata(buf3,file3);
		
	}
	public static void addInstace(int id,double latitude,double longitude)
	{
		
		
		if(re.containsKey(latitude))
		{
			 if(re.get(latitude).containsKey(longitude))
			 {
				 re.get(latitude).get(longitude).add(id);
			 }
			 else
			 { 
				 ArrayList<Integer> li=new ArrayList<Integer>();
				 li.add(id);
				
				re.get(latitude).put(longitude,li);
			 }
		}
		else
		{
			HashMap<Double,ArrayList<Integer>> ma=new  HashMap<Double,ArrayList<Integer>>();
			ArrayList<Integer> li=new ArrayList<Integer>();
			 li.add(id);
			 ma.put(longitude,li);
			 re.put(latitude,ma);
		}
		
		
	}
	
	public static HashMap<Integer,Integer> getmap(String mapfile)
	{
		HashMap<Integer,Integer> ma=new HashMap<Integer,Integer>();
		BufferedReader reader;
	//	StringBuffer buf=new StringBuffer();
		//HashMap<Integer,Integer> ma=new HashMap<Integer,Integer>();
		try{
			//int reid=0;
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(mapfile),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
			 String[] tokens=line.split(",");
			 int id=Integer.parseInt(tokens[0]);
			 int maid=Integer.parseInt(tokens[1]);
			 ma.put(id,maid);
			 line=reader.readLine();
			}
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return ma;
	}
	public static void mapMeetupUserEvent(String or_user_event_file,String targetfile,HashMap<Integer,Integer> usermap,HashMap<Integer,Integer> itemmap)
	{
		BufferedReader reader;
			StringBuffer buf=new StringBuffer();
			int count=0;
			
			//HashMap<Integer,Integer> ma=new HashMap<Integer,Integer>();
			try{
				//int reid=0;
				reader=new BufferedReader(new InputStreamReader(new FileInputStream(or_user_event_file),"UTF-8"));
				String line=reader.readLine();
				while(line!=null)
				{
				 String[] tokens=line.split(",");
				 int u_id=Integer.parseInt(tokens[0]);
				 int v_id=Integer.parseInt(tokens[1]);
				 if(usermap.containsKey(u_id)&&itemmap.containsKey(v_id))
				 {
				 u_id=usermap.get(u_id);
				 v_id=itemmap.get(v_id);
				  String te=u_id+","+v_id+"\r\n";
				  buf.append(te);
				  count++;
				  
				 }
				 line=reader.readLine();
				}
				reader.close();
				System.out.println("count:"+count);
				LocalityAnalysis.outputdata(buf,targetfile);
				
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
	}

}
