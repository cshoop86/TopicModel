package Location_LDA;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PostCode2LocationService {

	/**
	 * @param args
	 */
	public static HashMap<Integer,String> postcode_state=new HashMap<Integer,String>();
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String file="locationpostcode";
		load(file);
		System.out.println(postcode_state.size());

	}
	
	/*
	 * 0-CA
	 */
	
  public static void load(String file)
  {
	  BufferedReader reader;
		try{
			
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String line=reader.readLine();
			while(line!=null)
			{
			 String[] tokens=line.split(":");
			 String state=tokens[0];
			 String[] tks=tokens[2].split("、");
			 for(String token:tks)
			 {   
				 if(token.equals("")||token.equals(" ")||token==null)
				 {
					 
				 }
				 else
				 {
				 if(token.contains("~"))
				 {
					String[] ts=token.split("~");
					int begin=Integer.parseInt(ts[0]);
					int end=Integer.parseInt(ts[ts.length-1]);
					for(int i=begin;i<=end;i++)
					{
						postcode_state.put(i,state);
					}
				 }
				 else
				 {
					int postcod=Integer.parseInt(token);
					postcode_state.put(postcod,state);
				 }
				 }
			 }
			 
			 line=reader.readLine();
			}
			}catch(IOException e)
			{
				e.printStackTrace();
			}
  }
}
