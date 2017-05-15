package Location_LDA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location_Analysis {
	
	public static void main(String args[])throws Exception
	{
		String dir="/home2/dbz/workspace/fashion/doubanevent/raw/";
		String eventfile=dir+"events.tsv";
		String user_event=dir+"all_user_event.tsv";
		DoingStatistics(eventfile,user_event);
		
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
	public static void DoingStatistics(String file,String user_events) throws Exception
	{
		 
		     BufferedReader reader;
		     HashMap<String,Integer> map=new HashMap<String,Integer>();
		     HashMap<String,Integer> events=Loadevents(user_events);
		     int sum=0;
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("\t");
				if(events.containsKey(tokens[0]))
				{
				if(map.containsKey(tokens[1]))
				{
					map.put(tokens[1], map.get(tokens[1])+events.get(tokens[0]));
				}
				else
				{
					map.put(tokens[1], events.get(tokens[0]));
				}
				sum=sum+events.get(tokens[0]);
				}
				
				line=reader.readLine();
			}
			
			reader.close();
			
		System.out.println("sum:"+sum);
		
		if(map.containsKey("北京"))
		{
			System.out.println("北京:"+map.get("北京")+","+map.get("北京")*1.0/sum);
			
			
		}
		
		if(map.containsKey("上海"))
		{
			System.out.println("上海:"+map.get("上海")+","+map.get("上海")*1.0/sum);
			
			
		}
		
		if(map.containsKey("广东广州"))
		{
			System.out.println("广东广州:"+map.get("广东广州")+","+map.get("广东广州")*1.0/sum);
			
			
		}
		
		if(map.containsKey("广东深圳"))
		{
			System.out.println("广东深圳:"+map.get("广东深圳")+","+map.get("广东深圳")*1.0/sum);
			
			
		}
		
		List<Map.Entry<String,Integer>> list=rankingMap(map);
		int count=0;
		for(Map.Entry<String, Integer> ent:list)
		{
			System.out.println(ent.getKey()+":"+ent.getValue()+","+ent.getValue()*1.0/sum);
			count++;
			if(count>20)
				break;
			
		}
	}
	
	public static HashMap<String,Integer> Loadevents(String file) throws Exception
	{
		 
		     BufferedReader reader;
		     HashMap<String,Integer> map=new HashMap<String,Integer>();
		     int sum=0;
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
				String[] tokens=line.split("\t");
				if(map.containsKey(tokens[1]))
				{
					map.put(tokens[1], map.get(tokens[1])+1);
				}
				else
				{
					map.put(tokens[1], 1);
				}
				sum++;
				
				line=reader.readLine();
			}
			
			reader.close();
			
			return map;
	}

}
