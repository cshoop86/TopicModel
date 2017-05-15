package Location_LDA;

import java.util.Hashtable;

/**
 * something like matrix_and_map, but no matrix
 * 
 * @author Hu Zhiting
 *
 */

public class UserTagPostMap {
	// User
	public Hashtable <Integer,Integer> map_id1;
	// Tag
	public Hashtable <Integer,Integer> map_id2;
	// Post
	public Hashtable <Integer, Integer> map_id3;
	// extra 
	public Hashtable <Integer, Integer> map_id4;
	
	
	public int reverse_map1[];
	public int reverse_map2[];
	public int reverse_map3[];
	public int reverse_map4[];
	public int size1;
	public int size2;
	public int size3;
	public int size4;
	public int max_size1;
	public int max_size2;
	public int max_size3;
	public int max_size4;
	
	public void initialize(int s1, int s2, int s3)
	{
		reverse_map1=new int[s1];	// user
		reverse_map2=new int[s2];	// tag
		reverse_map3=new int[s3];	// post
		this.max_size1=s1;
		this.max_size2=s2;
		this.max_size3=s3;
		map_id1=new Hashtable<Integer,Integer>();
		map_id2=new Hashtable<Integer,Integer>();
		map_id3=new Hashtable<Integer,Integer>();
		
		size1 = 0;
		size2 = 0;
		size3 = 0;
	}
	
	public void initialize(int s1, int s2, int s3, int s4)
	{
		reverse_map1=new int[s1];	// user
		reverse_map2=new int[s2];	// tag
		reverse_map3=new int[s3];	// post
		reverse_map4=new int[s4];	// extra
		this.max_size1=s1;
		this.max_size2=s2;
		this.max_size3=s3;
		this.max_size4=s4;
		map_id1=new Hashtable<Integer,Integer>();
		map_id2=new Hashtable<Integer,Integer>();
		map_id3=new Hashtable<Integer,Integer>();
		map_id4=new Hashtable<Integer,Integer>();
		
		size1 = 0;
		size2 = 0;
		size3 = 0;
		size4 = 0;
	}
	
	
	private void extend_hash(int id1, int id2, int id3)
	{
		if(map_id1.get(id1)==null)
		{
			map_id1.put(id1,size1);
			reverse_map1[size1]=id1;
			size1++;
		}
		if(map_id2.get(id2)==null)
		{
			map_id2.put(id2, size2);
			reverse_map2[size2]=id2;
			size2++;
		}
		if(map_id3.get(id3)==null)
		{
			map_id3.put(id3, size3);
			reverse_map3[size3]=id3;
			size3++;
		}
	}
	
	private void extend_hash(int id1, int id2, int id3, int id4)
	{
		if(map_id1.get(id1)==null)
		{
			map_id1.put(id1,size1);
			reverse_map1[size1]=id1;
			size1++;
		}
		if(map_id2.get(id2)==null)
		{
			map_id2.put(id2, size2);
			reverse_map2[size2]=id2;
			size2++;
		}
		if(map_id3.get(id3)==null)
		{
			map_id3.put(id3, size3);
			reverse_map3[size3]=id3;
			size3++;
		}
		if(map_id4.get(id4)==null)
		{
			map_id4.put(id4, size4);
			reverse_map4[size4]=id4;
			size4++;
		}
	}
	
	
	public void put_add(int id1, int id2, int id3)
	{
		extend_hash(id1, id2, id3);
		//map1 = map_id1.get(id1);
		//map2 = map_id2.get(id2);
	}
	
	public void put_add(int id1, int id2, int id3, int id4)
	{
		extend_hash(id1, id2, id3, id4);
		//map1 = map_id1.get(id1);
		//map2 = map_id2.get(id2);
	}
	
	public int get_map1(int id1){
		if(map_id1.containsKey(id1))
			return map_id1.get(id1);
		return -1;
	}
	public int get_map2(int id2){
		if(map_id2.containsKey(id2))
			return map_id2.get(id2);
		return -1;
	}
	public int get_map3(int id3){
		if(map_id3.containsKey(id3))
			return map_id3.get(id3);
		return -1;
	}
	public int get_map4(int id4){
		if(map_id4.containsKey(id4))
			return map_id4.get(id4);
		return -1;
	}
	
	
	
}
