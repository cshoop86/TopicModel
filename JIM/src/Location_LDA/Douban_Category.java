package Location_LDA;
import input_output_interface.data_storage;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.*;
public class Douban_Category 
{   
	public static void main(String args[])throws Exception
	{
		
		HashSet<String> set=new HashSet<String>();
		String dir="/home/huzhiting/workspace3/fashion/doubanevent/";
		String train=dir+"user_item_train";
		String outtrain=dir+"user_item_train_mymedialite.txt";
		create_dataset_mymedialite(train,outtrain);
		//String infile=dir+"user_item_test";
		//String outfile=dir+"item_mapcate.txt";
		//create_data_itemmap(outfile);
		//create_dataset_for_ICLA(infile,outfile);
		/*String filename=dir+"events.tsv";
		String output=dir+"cat_map.txt";
		BufferedReader reader= data_storage.file_handle_read(filename);
		String line=reader.readLine();
		while(line!=null)
		{
			String[] tokens=line.split("\t");
			set.add(tokens[2]);
			line=reader.readLine();
		}
		reader.close();
		
		StringBuffer buf=new StringBuffer();
		int id=0;
		for(String ca:set)
		{
			String l=ca+"\t"+id+"\r\n";
			buf.append(l);
			id++;
		}
		
		OutputStreamWriter oswpf_train = data_storage.file_handle(output);
		oswpf_train.write(buf.toString());
		
		oswpf_train.flush();
		oswpf_train.close();*/
	}

	public static void create_dataset_mymedialite(String infile,String outfile)throws Exception
	{
	// String dir="/home/huzhiting/workspace3/fashion/doubanevent/";
    HashMap<String,HashSet<String>> data=new HashMap<String,HashSet<String>>();		
	 StringBuffer buf=new StringBuffer();
	 BufferedReader reader= data_storage.file_handle_read(infile);
		String line=reader.readLine();
		while(line!=null)
		{
			String[] tokens=line.split("\t");
			
			if(data.containsKey(tokens[0]))
			{
				data.get(tokens[0]).add(tokens[2]);
			}
			else
			{
				HashSet<String> set=new HashSet<String>();
				set.add(tokens[2]);
				data.put(tokens[0],set);
			}
			//buf.append(temp);
			line=reader.readLine();
		}
		reader.close();
	 
		for(String user:data.keySet())
		{
			for(String item:data.get(user))
			{
				String li=user+","+item+","+5.0+"\r\n";
				buf.append(li);
			}
			
			String temp="\r\n";
			buf.append(temp);
		}
		OutputStreamWriter oswpf_train = data_storage.file_handle(outfile);
		oswpf_train.write(buf.toString());
		
		oswpf_train.flush();
		oswpf_train.close();
	}

 public static void create_data_itemmap(String outfile)throws Exception
{  
	String dir="/home/huzhiting/workspace3/fashion/doubanevent/";
	HashMap<String,String> catemap=readCatemap(dir+"cat_map.txt");
	HashMap<String,String> event_cat=readevents(dir+"events.tsv");
	HashMap<String,String> itemamp=readitemmap2(dir+"item_map.txt");
	StringBuffer buf=new StringBuffer();
	for(String itemid:itemamp.keySet())
	{
		String t=itemid+"\t"+itemamp.get(itemid)+"\t";
		String cate=event_cat.get(itemid);
		String catedi=catemap.get(cate);
		t=t+catedi+"\r\n";
		buf.append(t);
	}
	
	OutputStreamWriter oswpf_train = data_storage.file_handle(outfile);
	oswpf_train.write(buf.toString());
	
	oswpf_train.flush();
	oswpf_train.close();

}
	
public static void create_dataset_for_ICLA(String infile,String outfile) throws Exception
{
	BufferedReader reader= data_storage.file_handle_read(infile);
	String line=reader.readLine();
	StringBuffer buf=new StringBuffer();
	String dir="/home/huzhiting/workspace3/fashion/doubanevent/";
	HashMap<String,String> catemap=readCatemap(dir+"cat_map.txt");
	HashMap<String,String> event_cat=readevents(dir+"events.tsv");
	HashMap<String,String> itemamp=readitemmap(dir+"item_map.txt");
	while(line!=null)
	{
		String[] tokens=line.split("\t");
		String t=tokens[0]+"\t"+tokens[2]+"\t"+tokens[3]+"\t";
		String itid=itemamp.get(tokens[2]);
		String cate=event_cat.get(itid);
		String cate_id=catemap.get(cate);
		t=t+cate_id+"\t"+tokens[1]+"\r\n";
		buf.append(t);
		
		line=reader.readLine();
		
	}
	OutputStreamWriter oswpf_train = data_storage.file_handle(outfile);
	oswpf_train.write(buf.toString());
	
	oswpf_train.flush();
	oswpf_train.close();
	
	}
	
 public static HashMap<String,String> readCatemap(String file) throws Exception 
 {
	 HashMap<String,String>  ma=new HashMap<String,String>();
	 
	 BufferedReader reader= data_storage.file_handle_read(file);
		String line=reader.readLine();
		while(line!=null)
		{
			String[] tokens=line.split("\t");
			//set.add(tokens[2]);
			ma.put(tokens[0],tokens[1]);
			line=reader.readLine();
		}
		reader.close();
	 
	 return ma;
 }

 public static HashMap<String,String> readevents(String file) throws Exception 
 {
  HashMap<String,String>  ma=new HashMap<String,String>();
	 
	 BufferedReader reader= data_storage.file_handle_read(file);
		String line=reader.readLine();
		while(line!=null)
		{
			String[] tokens=line.split("\t");
			//set.add(tokens[2]);
			ma.put(tokens[0],tokens[2]);
			line=reader.readLine();
		}
		reader.close();
	 
	 return ma;
 }
 
 public static HashMap<String,String> readitemmap(String file) throws Exception
 {
    HashMap<String,String>  ma=new HashMap<String,String>();
	 
	 BufferedReader reader= data_storage.file_handle_read(file);
		String line=reader.readLine();
		while(line!=null)
		{
			String[] tokens=line.split("\t");
			//set.add(tokens[2]);
			ma.put(tokens[1],tokens[0]);
			line=reader.readLine();
		}
		reader.close();
	 
	 return ma;
 }
 
 public static HashMap<String,String> readitemmap2(String file) throws Exception
 {
    HashMap<String,String>  ma=new HashMap<String,String>();
	 
	 BufferedReader reader= data_storage.file_handle_read(file);
		String line=reader.readLine();
		while(line!=null)
		{
			String[] tokens=line.split("\t");
			//set.add(tokens[2]);
			ma.put(tokens[0],tokens[1]);
			line=reader.readLine();
		}
		reader.close();
	 
	 return ma;
 }
}
