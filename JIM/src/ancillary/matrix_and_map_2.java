package ancillary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import basic.Item;
//used for initialization, document-word matrix
public class matrix_and_map_2 {
	public Hashtable <Integer,Integer> map_id1;
	public Hashtable <Integer,Integer> map_id2;
	public int reverse_map1[];
	public int reverse_map2[];
	public int size1;
	public int size2;
	public int max_size1;
	public int max_size2;
	public int matrix[][];
	public int test_matrix[][];
	public void initialize(int s1,int s2)
	{
		matrix=new int[s1][s2];
		//test_matrix=new int[s1][s2];
		reverse_map1=new int[s1];
		reverse_map2=new int[s2];
		this.max_size1=s1;
		this.max_size2=s2;
		map_id1=new Hashtable<Integer,Integer>();
		map_id2=new Hashtable<Integer,Integer>();
		for(int i=0;i<s1;i++)
		{
			for(int j=0;j<s2;j++)
			{
				matrix[i][j]=0;
				test_matrix[i][j]=0;
			}
		}
	}
	public void initialize2(int s1,int s2)
	{
		//matrix=new int[s1][s2];
		//test_matrix=new int[s1][s2];
		reverse_map1=new int[s1];
		reverse_map2=new int[s2];
		this.max_size1=s1;
		this.max_size2=s2;
		map_id1=new Hashtable<Integer,Integer>();
		map_id2=new Hashtable<Integer,Integer>();
		
	}
	
	
	private void extend_hash(int id1,int id2)
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
	}
	public void put_replace(int id1,int id2,int value)
	{
		extend_hash(id1,id2);
		int map1=map_id1.get(id1);
		int map2=map_id2.get(id2);
		matrix[map1][map2]=value;
	}
	public void put_replace_fix(int id1,int id2,int value)//second dimension fixed
	{
		extend_hash(id1,id2);
		int map1=map_id1.get(id1);
		matrix[map1][id2]=value;
	}
	
	public void put_add(int id1,int id2,int value,int sliceid,HashMap<Integer,ArrayList<Item>> item_graph)
	{
		extend_hash(id1,id2);
		int map1=map_id1.get(id1);
		int map2=map_id2.get(id2);
		
		Item it=new Item();
		it.item_id = map1;
				
		it.slice_id=sliceid;
		it.number=value;
		if(item_graph.containsKey(map2))
		{
			item_graph.get(map2).add(it);
		}
		else
		{
			ArrayList<Item> list=new ArrayList<Item>();
			list.add(it);
			item_graph.put(map2, list);
		}
		//matrix[map1][map2]+=value;
	}
	
	
	public void put_add(int id1,int id2,int value)
	{
		extend_hash(id1,id2);
		int map1=map_id1.get(id1);
		int map2=map_id2.get(id2);
		matrix[map1][map2]+=value;
	}
	
	public void put_add_fix(int id1,int id2,int value)//second dimension fixed
	{
		extend_hash(id1,id2);
		int map1=map_id1.get(id1);
		matrix[map1][id2]+=value;
	}
	public void put_add_test(int id1,int id2,int value)
	{
		if(map_id1.get(id1)==null)
		{
			System.out.println("bad testset dimension 1! "+id1);
			return;
		}
		if(map_id2.get(id2)==null)
		{
			System.out.println("bad testset dimension 2! "+id2);
			return;
		}
		int map1=map_id1.get(id1);
		int map2=map_id2.get(id2);
		test_matrix[map1][map2]+=value;
	}
	public void put_add_test_fix(int id1,int id2,int value)
	{
		if(map_id1.get(id1)==null)
			return;
		int map1=map_id1.get(id1);
		test_matrix[map1][id2]+=value;
	}
}
