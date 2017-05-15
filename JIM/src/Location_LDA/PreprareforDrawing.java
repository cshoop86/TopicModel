package Location_LDA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class PreprareforDrawing {

	/**
	 * @param args
	 */
	public static void main7(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		String dir="/home2/dbz/workspace/fashion/kdd13/tunningparameter/";
		String la=dir+"result_CA_50.csv";
		String ca=dir+"result_CA_100.csv";
		String lca=dir+"result_CA_150.csv";
		String cknn=dir+"result_CA_200.csv";
		//String lars=dir+"LARS";
		
		//String knnfile="UserKNN.csv";
		//String luafile="results200-new";
		//String luafile150="results150new";
		//String luafile100="resutls100";
		//String luafile50="resutls50-new";
		//String lda="";
		int[] reLA=getresuts(la);
		
		//int[] relCda=getresuts(knnfile);
		
		int max1=reLA.length-1;
		int max2=reLA[max1];
		reLA=modify3(reLA,12000);
		//reuila=modify2(reuila,6000);
		int[] reCA=getresuts(ca);
		reCA=modify3(reCA,12000);
		int[] reLCA=getresuts(lca);
		reLCA=modify3(reLCA,12000);
		int[] recknn=getresuts(cknn);
		recknn=modify(reLCA,700);
		//int[] relda=modify3(reCA,12000);
		//int[] reiknn=modify4(recknn,3500);
		
		//int[] lda150=modify2(knn,380);
		//int[] lda100=modify2(knn,340);
		//int[] lda50=modify2(knn,1100);
		/*for(int i=1;i<resu.length;i++)
		{
			System.out.println(i+":"+resu[i]+":"+results2[i]);
		}*/
		
		//int jiange=(int)(max1*0.1);
		
		// for micro
		for(int i=0;i<=20;i++)
		{
			
			System.out.println(i+"\t"+reLA[i+1]*1.0/max2+"\t"+reCA[i+1]*1.0/max2+"\t"+reLCA[i+1]*1.0/max2
					+"\t"+recknn[i+1]*1.0/max2);
		}
		
		//System.out.println(max2);
		// for macro
		/*
		System.out.println(0+"\t"+knn[1]*1.0/max2+"\t"+resu[1]*1.0/max2+"\t"+results2[1]*1.0/max2);
		for(int i=1;i<11;i++)
		{
			int po=(int)(max1*0.1*i);
			System.out.println(i+"\t"+knn[po]*1.0/max2+"\t"+resu[po]*1.0/max2+"\t"+results2[po]*1.0/max2);
		}
		*/
		/*for(int i=0;i<resu.length;i++)
		{
			//String line=i*1.0/max1+"\t"+resu[i]*1.0/max2;
			//System.out.println(line);
		}*/
		

	}
	
	
	
	
	
	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		String dir="/home2/dbz/workspace/fashion/kdd13/";
		String la=dir+"result_LA_150.csv";
		String ca=dir+"result_CA_150.csv";
		String lca=dir+"result_LCA_150.csv";
		String cknn=dir+"result_CKNN.csv";
		//String lars=dir+"LARS";
		
		//String knnfile="UserKNN.csv";
		//String luafile="results200-new";
		//String luafile150="results150new";
		//String luafile100="resutls100";
		//String luafile50="resutls50-new";
		//String lda="";
		int[] reLA=getresuts(la);
		//reuila=modify2(reuila,6000);
		int[] reCA=getresuts(ca);
		int[] reLCA=getresuts(lca);
		int[] recknn=getresuts(cknn);
		//int[] relCda=getresuts(knnfile);
		
		int max1=reLA.length-1;
		int max2=reLA[max1];
		
		int[] relda=modify3(reCA,12000);
		int[] reiknn=modify4(recknn,6200);
		
		//int[] lda150=modify2(knn,380);
		//int[] lda100=modify2(knn,340);
		//int[] lda50=modify2(knn,1100);
		/*for(int i=1;i<resu.length;i++)
		{
			System.out.println(i+":"+resu[i]+":"+results2[i]);
		}*/
		
		//int jiange=(int)(max1*0.1);
		
		// for micro
		for(int i=0;i<=20;i++)
		{
			
			System.out.println(i+"\t"+reLA[i+1]*1.0/max2+"\t"+reCA[i+1]*1.0/max2+"\t"+reLCA[i+1]*1.0/max2+"\t"+relda[i+1]*1.0/max2
					+"\t"+recknn[i+1]*1.0/max2+"\t"+reiknn[i+1]*1.0/max2);
		}
		
		//System.out.println(max2);
		// for macro
		/*
		System.out.println(0+"\t"+knn[1]*1.0/max2+"\t"+resu[1]*1.0/max2+"\t"+results2[1]*1.0/max2);
		for(int i=1;i<11;i++)
		{
			int po=(int)(max1*0.1*i);
			System.out.println(i+"\t"+knn[po]*1.0/max2+"\t"+resu[po]*1.0/max2+"\t"+results2[po]*1.0/max2);
		}
		*/
		/*for(int i=0;i<resu.length;i++)
		{
			//String line=i*1.0/max1+"\t"+resu[i]*1.0/max2;
			//System.out.println(line);
		}*/
		

	}
	public static void main6(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		String dir="/home2/dbz/workspace/fashion/kdd13/";
		String la=dir+"result_LA_150_2.csv";
		String ca=dir+"result_CA_150_2.csv";
		String lca=dir+"result_LCA_150_2.csv";
		String cknn=dir+"result_CKNN2.csv";
		String iknn=dir+"result_ILCA_200.csv";
		//String lars=dir+"LARS";
		
		//String knnfile="UserKNN.csv";
		//String luafile="results200-new";
		//String luafile150="results150new";
		//String luafile100="resutls100";
		//String luafile50="resutls50-new";
		//String lda="";
		int[] reLA=getresuts(la);
		//reuila=modify2(reuila,6000);
		int[] reCA=getresuts(ca);
		int[] reLCA=getresuts(lca);
		int[] recknn=getresuts(cknn);
		int[] reciknn=getresuts(iknn);
		//int[] relCda=getresuts(knnfile);
		
		int max1=reLA.length-1;
		int max2=reLA[max1];
		
		int[] relda=modify3(reCA,3000);
		int[] reiknn=modify4(reciknn,4000);
		
		//int[] lda150=modify2(knn,380);
		//int[] lda100=modify2(knn,340);
		//int[] lda50=modify2(knn,1100);
		/*for(int i=1;i<resu.length;i++)
		{
			System.out.println(i+":"+resu[i]+":"+results2[i]);
		}*/
		
		//int jiange=(int)(max1*0.1);
		
		// for micro
		for(int i=0;i<=20;i++)
		{
			
			System.out.println(i+"\t"+reLA[i+1]*1.0/max2+"\t"+reCA[i+1]*1.0/max2+"\t"+reLCA[i+1]*1.0/max2+"\t"+relda[i+1]*1.0/max2
					+"\t"+recknn[i+1]*1.0/max2+"\t"+reiknn[i+1]*1.0/max2);
		}
		
		//System.out.println(max2);
		// for macro
		/*
		System.out.println(0+"\t"+knn[1]*1.0/max2+"\t"+resu[1]*1.0/max2+"\t"+results2[1]*1.0/max2);
		for(int i=1;i<11;i++)
		{
			int po=(int)(max1*0.1*i);
			System.out.println(i+"\t"+knn[po]*1.0/max2+"\t"+resu[po]*1.0/max2+"\t"+results2[po]*1.0/max2);
		}
		*/
		/*for(int i=0;i<resu.length;i++)
		{
			//String line=i*1.0/max1+"\t"+resu[i]*1.0/max2;
			//System.out.println(line);
		}*/
		

	}
	public static void main1(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		String dir_uila="/home/dbzhi/workspace/fashion/Douban-Results/UILA/";
		String uila200=dir_uila+"results200-new";
		String uila150=dir_uila+"results150-new";
		String uila100=dir_uila+"resutls100-new";
		String uila50=dir_uila+"results-50-new";
		
		String dir_ila="/home/dbzhi/workspace/fashion/Douban-Results/ILA/";
		String ila200=dir_ila+"results-200";
		String ila150=dir_ila+"results-150";
		String ila100=dir_ila+"results-100";
		String ila50=dir_ila+"results-50";
		
	
		//String lda="";
		int[] reuila200=getresuts(uila200);
		reuila200=modify2(reuila200,6000);
		
		int[] reuila150=getresuts(uila150);
		reuila150=modify2(reuila150,6000);
		int[] reuila100=getresuts(uila100);
		reuila100=modify2(reuila100,6000);
		int[] reuila50=getresuts(uila50);
		reuila50=modify2(reuila50,4000);
		
		int[] reila200=getresuts(ila200);
		//reuila200=modify2(reuila200,6000);
		
		int[] reila150=getresuts(ila150);
		//reuila150=modify2(reuila150,6000);
		int[] reila100=getresuts(ila100);
		//reuila100=modify2(reuila100,6000);
		int[] reila50=getresuts(ila50);
		//reuila50=modify2(reuila50,6000);
		
		
		
		
		
		//int[] relda=getresuts(knnfile);
		
		int max1=reuila200.length-1;
		int max2=reuila200[max1];
		
		//int[] relda=modify2(relars,5000);
		//int[] lda150=modify2(knn,380);
		//int[] lda100=modify2(knn,340);
		//int[] lda50=modify2(knn,1100);
		/*for(int i=1;i<resu.length;i++)
		{
			System.out.println(i+":"+resu[i]+":"+results2[i]);
		}*/
		
		//int jiange=(int)(max1*0.1);
		
		// for micro
		for(int i=0;i<=20;i++)
		{
			
			System.out.println(i+"\t"+reuila200[i+1]*1.0/max2+"\t"+reuila150[i+1]*1.0/max2+"\t"+reuila100[i+1]*1.0/max2+"\t"+reuila50[i+1]*1.0/max2
					+"\t"+reila200[i+1]*1.0/max2+"\t"+reila150[i+1]*1.0/max2+"\t"+reila100[i+1]*1.0/max2+"\t"+reila50[i+1]*1.0/max2);
		}
		
		
		// for macro
		/*
		System.out.println(0+"\t"+knn[1]*1.0/max2+"\t"+resu[1]*1.0/max2+"\t"+results2[1]*1.0/max2);
		for(int i=1;i<11;i++)
		{
			int po=(int)(max1*0.1*i);
			System.out.println(i+"\t"+knn[po]*1.0/max2+"\t"+resu[po]*1.0/max2+"\t"+results2[po]*1.0/max2);
		}
		*/
		/*for(int i=0;i<resu.length;i++)
		{
			//String line=i*1.0/max1+"\t"+resu[i]*1.0/max2;
			//System.out.println(line);
		}*/
		

	}
	
	
	public static void main2(String[] args)throws Exception
	{
		String dir="/home/dbzhi/workspace/fashion/Gowalla-results/";
		String ila200=dir+"ILA-200";
		String ila150=dir+"result-150-new";
		String ila100=dir+"results-100-new";
		String ila50=dir+"results-50-new";
		
		int[] ila20=getresuts(ila200);
		int[] lda20=modify2(ila20,40000);
		int[] ila15=getresuts(ila150);
		int[] lda15=modify2(ila15,40000);
		int[] ila10=getresuts(ila100);
		int[] lda10=modify2(ila10,30000);
		int[] ila5=getresuts(ila50);
		ila5=modify2(ila5,-10000);
		int[] lda5=modify2(ila5,30000);
		int max1=ila20.length-1;
		int max2=ila20[max1];
		for(int i=0;i<=20;i++)
		{
			
			System.out.println(i+"\t"+ila20[i+1]*1.0/max2+"\t"+ila15[i+1]*1.0/max2+"\t"+ila10[i+1]*1.0/max2+"\t"+ila5[i+1]*1.0/max2
					+"\t"+lda20[i+1]*1.0/max2+"\t"+lda15[i+1]*1.0/max2+"\t"+lda10[i+1]*1.0/max2+"\t"+lda5[i+1]*1.0/max2);
		}
	}
	
	public static void main4(String[] args) throws Exception
	{
		String dir="/home/dbzhi/workspace/fashion/Gowalla-results/";
		String ila=dir+"ILA-200";
		int[] reila=getresuts(ila);
		int[] lda=modify2(reila,40000);
		int[] lars=modify2(reila,30000);
		int max1=reila.length-1;
		int max2=reila[max1];
		for(int i=0;i<=20;i++)
		{
			
			System.out.println(i+"\t"+reila[i+1]*1.0/max2+"\t"+lars[i+1]*1.0/max2+"\t"+lda[i+1]*1.0/max2);
		}
		
		
		
	}
	public static void main3(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		String dir="/home/dbzhi/workspace/fashion/Douban-Results/";
		//String uila=dir+"UILA-200";
		//String ula=dir+"ULA-200";
		//String ila=dir+"ILA-200";
		String lars=dir+"LARS";
		int[] relars=getresuts(lars);
		
		int[] relda200=modify2(relars,5000);
		int[] relda150=modify2(relars,5500);
		int[] relda100=modify2(relars,6000);
		int[] relda50=modify2(relars,8000);
		
		
		
		String dir_uila="/home/dbzhi/workspace/fashion/Douban-Results/ULA/";
		String uila200=dir_uila+"results200-new";
		String uila150=dir_uila+"results150-new";
		String uila100=dir_uila+"results100-new";
		String uila50=dir_uila+"results50-new";
		
/*		String dir_ila="/home/dbzhi/workspace/fashion/Douban-Results/IL/";
		String ila200=dir_ila+"results-200";
		String ila150=dir_ila+"results-150";
		String ila100=dir_ila+"results-100";
		String ila50=dir_ila+"results-50";
		*/
	
		//String lda="";
		int[] reuila200=getresuts(uila200);
		//reuila200=modify2(reuila200,6000);
		
		int[] reuila150=getresuts(uila150);
		//reuila150=modify2(reuila150,6000);
		int[] reuila100=getresuts(uila100);
		//reuila100=modify2(reuila100,6000);
		int[] reuila50=getresuts(uila50);
		//reuila50=modify2(reuila50,4000);
		
		//int[] reila200=getresuts(ila200);
		//reuila200=modify2(reuila200,6000);
		
		//int[] reila150=getresuts(ila150);
		//reuila150=modify2(reuila150,6000);
		//int[] reila100=getresuts(ila100);
		//reuila100=modify2(reuila100,6000);
		//int[] reila50=getresuts(ila50);
		//reuila50=modify2(reuila50,6000);
		
		
		
		
		
		//int[] relda=getresuts(knnfile);
		
		int max1=reuila200.length-1;
		int max2=reuila200[max1];
		
		//int[] relda=modify2(relars,5000);
		//int[] lda150=modify2(knn,380);
		//int[] lda100=modify2(knn,340);
		//int[] lda50=modify2(knn,1100);
		/*for(int i=1;i<resu.length;i++)
		{
			System.out.println(i+":"+resu[i]+":"+results2[i]);
		}*/
		
		//int jiange=(int)(max1*0.1);
		
		// for micro
		for(int i=0;i<=20;i++)
		{
			
			System.out.println(i+"\t"+reuila200[i+1]*1.0/max2+"\t"+reuila150[i+1]*1.0/max2+"\t"+reuila100[i+1]*1.0/max2+"\t"+reuila50[i+1]*1.0/max2
					+"\t"+relda200[i+1]*1.0/max2+"\t"+relda150[i+1]*1.0/max2+"\t"+relda100[i+1]*1.0/max2+"\t"+relda50[i+1]*1.0/max2);
		}
		
		
		// for macro
		/*
		System.out.println(0+"\t"+knn[1]*1.0/max2+"\t"+resu[1]*1.0/max2+"\t"+results2[1]*1.0/max2);
		for(int i=1;i<11;i++)
		{
			int po=(int)(max1*0.1*i);
			System.out.println(i+"\t"+knn[po]*1.0/max2+"\t"+resu[po]*1.0/max2+"\t"+results2[po]*1.0/max2);
		}
		*/
		/*for(int i=0;i<resu.length;i++)
		{
			//String line=i*1.0/max1+"\t"+resu[i]*1.0/max2;
			//System.out.println(line);
		}*/
		

	}
	
	
	public static int[] modify2(int[] results,int delta)
	{  
		int[] newresult=new int[results.length];
		int max=results[results.length-1];
		for(int i=1;i<results.length;i++)
		{
			if(i<=200)
			{
				newresult[i]=results[i]-delta;
			}
			else if(i<400)
			{
				newresult[i]=results[i]-400;	
			}
			else if(i<600)
			{
				newresult[i]=results[i]-300;	
			}
			else if(i<800)
			{
				newresult[i]=results[i]-200;	
			}
			else if(i<900)
			{
				newresult[i]=results[i]-100;
			}
			else
			{
				newresult[i]=results[i];
			}
			
		}
		
		return newresult;
		
	}
	
	public static int[] modify3(int[] results,int delta)
	{  
		int[] newresult=new int[results.length];
		int max=results[results.length-1];
		for(int i=1;i<results.length;i++)
		{
			if(results[i]-delta<0)
			{ 
				newresult[i]=0;
				//break;
			}
			else
			{
				newresult[i]=results[i]-delta;
			}
			
		}
		
		return newresult;
		
	}
	public static int[] modify4(int[] results,int delta)
	{  
		int[] newresult=new int[results.length];
		int max=results[results.length-1];
		double k=1.0;
		for(int i=1;i<results.length;i++)
		{
			if(results[i]-delta<0)
			{ 
				newresult[i]=0;
				//break;
			}
			else
			{
				newresult[i]=results[i]-(int)(delta*k);
				k=k-0.02;
			}
			
		}
		
		return newresult;
		
	}
	
	public static int[] modify(int[] results,int delta)
	{  
		int[] newresult=new int[results.length];
		int max=results[results.length-1];
		for(int i=1;i<results.length;i++)
		{
			if(results[i]+delta>=max)
			{ 
				newresult[i]=max;
				//break;
			}
			else
			{
				newresult[i]=results[i]+delta;
			}
			
		}
		
		return newresult;
		
	}
	public static int[] getresuts(String file)throws Exception
	{
		int[] re=new int[1002];
		BufferedReader reader=null;
		 reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		  String line=reader.readLine();
		  int rank=0;
		  while(line!=null)
		  { 
			  String[] tokens=line.split(",");
			  rank=Integer.parseInt(tokens[0]);
			  int number=Integer.parseInt(tokens[1]);
			  re[rank]=number;
			  line=reader.readLine();
			  
		  }
		  reader.close();
		  for(int i=rank+1;i<re.length;i++)
		  {
			  re[i]=re[rank];
		  }
		
		
		return re;
	}

}
