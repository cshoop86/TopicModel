package ancillary;
import java.util.Arrays;

import basic.matrix_function;
import basic.myarray;
import basic.mycomparator;
import exception.*;
/*
 * This class is designed to sort the dimensions of topics by descending order
 * of the weight of each terms
 */
public class sort_major {
	public void sort_second_dimension(double m[][],int len_id,myarray a[][])
	{
		int len1=m.length;
		int len2=m[0].length;
		if(len_id!=len1){
			System.out.println("Warning: length not accordance!:"+len1+" "+len2+" "+len_id+";");
		}
		for (int i=0;i<len1;i++){
			for(int j=0;j<len2;j++){
				a[i][j]=new myarray();
				a[i][j].key=j;//this dimension represent topic
				a[i][j].value=m[i][j];
			}
			Arrays.sort(a[i],0,len2,new mycomparator());
		}
	}
	public void sort_first_dimension(double m[][],int id[],int len_id,myarray a[][])
	{
		//sort the first dimension and record the keyword_id by reverse_map(id[i]), for keyword|topics output
		int len1=a[0].length;
		int len2=a.length;
		if(len_id!=len1 ||len_id>id.length)
		{
			System.out.println("warning: length not accordance!:"+len1+" "+len2+" "+id.length+" "+len_id+";");
		}
		for(int j=0;j<len2;j++)
		{
			for(int i=0;i<len1;i++)
			{
				a[j][i]=new myarray();
				a[j][i].key=id[i];
				a[j][i].value=m[i][j];
			}
			Arrays.sort(a[j],0,len1,new mycomparator());
		}
	}
	public void sort_first_dimension(double m[][],int len_id,myarray a[][])
	{
		int len1=m.length;
		int len2=m[0].length;
		if(len_id!=len1)
		{
			System.out.println("warning: length not accordance!");
		}
		for(int j=0;j<len2;j++)
		{
			a[j]=new myarray[len1];
			for(int i=0;i<len1;i++)
			{
				a[j][i]=new myarray();
				a[j][i].key=i;
				a[j][i].value=m[i][j];
			}
			Arrays.sort(a[j],0,len1,new mycomparator());
		}
	}
	private void aggregate_probability(final double input[][][],double output[][]) throws length_check_exception
	{
		int leni1=input.length;
		int leni2=input[0].length;
		int leni3=input[0][0].length;
		
		int leno1=output.length;
		int leno2=output[0].length;
		if(leni1!=leno1)
			throw new length_check_exception();
		if(leni3!=leno2)
			throw new length_check_exception();
		for(int k=0;k<leni3;k++)
		{
			for(int i=0;i<leni1;i++)
			{
				for(int j=0;j<leni2;j++)
				{
					output[i][k]+=input[i][j][k];
				}
			}
		}
		matrix_function.normalize(output);
	}
	//aggregate probability and sort!
	public void agg_sort(double m[][][],int len_id,myarray a[][])
	{
		double agg[][]=new double[m.length][m[0][0].length];
		try
		{
			aggregate_probability(m,agg);
			sort_first_dimension(agg,len_id,a);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
