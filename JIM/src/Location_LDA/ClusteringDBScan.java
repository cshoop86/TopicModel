package Location_LDA;
import java.util.*;
import java.io.*;
public class ClusteringDBScan {

	/**
	 * @param args
	 */
	
	    private final static double e=200;//ε半径
	     private final static int minp=200;//密度阈值
	     private static List<Point> pointsList=new ArrayList<Point>();//存储原始样本点
	    private static List<List<Point>> resultList=new ArrayList<List<Point>>();//存储最后的聚类结果

	
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		 try {
			        //调用DBSCAN的实现算法
			  String dir="/raid5-on-2T/home/bestzhi/datasets/location_based_datasets/event_based network media/Meetup/Meetup_geo_entity/";
			  String file1=dir+"event_lon_lat.csv";
			        applyDbscan(file1);
			        
			        StringBuffer buf=new StringBuffer();
			        for(int i=0;i<resultList.size();i++)
			        {
			        	for(Point p:resultList.get(i))
			        	{
			        	  String line=p.id+","+i+","+p.getX()+","+p.getY()+"\r\n";
			        	  buf.append(line);
			        	}
			        	
			        }
			        
			        LocalityAnalysis.outputdata(buf,"meetupevents.csv");
			      display(resultList);
			      } catch (IOException e) {
			        // TODO Auto-generated catch block
			       e.printStackTrace();
			      }


	}
	// compute distance based on latitude and longtude
	// kilometers x denotes weidu/latitude;  y denotes longtude
		public static double getDistance(Point a,   
			            Point b) {   
			        Double R = new Double(6371);   
			        Double dlat = (b.getX() - a.getX()) * Math.PI / 180;   
				       Double dlon = (b.getY() - a.getY()) * Math.PI / 180;   
			        Double aDouble = Math.sin(dlat / 2) * Math.sin(dlat / 2)   
			                + Math.cos(a.getX() * Math.PI / 180)   
				               * Math.cos(b.getX() * Math.PI / 180) * Math.sin(dlon / 2)   
			               * Math.sin(dlon / 2);   
				        Double cDouble = 2 * Math.atan2(Math.sqrt(aDouble), Math   
				                .sqrt(1 - aDouble));   
				        double d = Math.round((R * cDouble) * 1000) / 1000;   
				       return d;   
				  
				    }  
		
		public static double getDistance2(Point a,   
	            Point b) {   
	        
		       return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));   
		  
		    }  
	//检测p点是不是核心点，tmpLst存储核心点的直达点
	    public static List<Point> isKeyPoint(List<Point> lst,Point p,double e,int minp){
	      int count=0;
	      List<Point> tmpLst=new ArrayList<Point>();
	      for(Iterator<Point> it=lst.iterator();it.hasNext();){
	        Point q=it.next();
	        if(getDistance(p,q)<=e){
	          ++count;
	          if(!tmpLst.contains(q)){
	            tmpLst.add(q);
	          }
	       }
	      }
	      if(count>=minp){
	        p.setKey(true);
	        return tmpLst;
	      }
	      return null;
	    }
	    
	    
	  //合并两个链表，前提是b中的核心点包含在a中
	       public static boolean mergeList(List<Point> a,List<Point> b){
	         boolean merge=false;
	         if(a==null || b==null){
	           return false;
	         }
	         for(int index=0;index<b.size();++index){
	           Point p=b.get(index);
	           if(p.isKey() && a.contains(p)){
	             merge=true;
	             break;
	          }
	        }
	         if(merge){
	           for(int index=0;index<b.size();++index){
	            if(!a.contains(b.get(index))){
	               a.add(b.get(index));
	             }
	           }
	        }
	        return merge;
	       }
    
	     //获取文本中的样本点集合
	          public static List<Point> getPointsList(String txtPath) throws IOException{
	           List<Point> lst=new ArrayList<Point>();
	            //String txtPath="src\\com\\sunzhenxing\\points.txt";
	            BufferedReader br=new BufferedReader(new FileReader(txtPath));
	            String str="";
	            while((str=br.readLine())!=null && str!=""){
	             String[] tokens=str.split(",");
	             int id=Integer.parseInt(tokens[0]);
	             double  longitude=Double.parseDouble(tokens[1]);
	             double latitude=Double.parseDouble(tokens[2]);
	             Point p=new Point();
	             p.id=id;
	             p.setX(latitude);
	             p.setY(longitude);
	             lst.add(p);
	            }
	           br.close();
	           return lst;
	          }
	          
	          
	        //显示聚类的结果
	             public static void display(List<List<Point>> resultList){
	               int index=1;
	               for(Iterator<List<Point>> it=resultList.iterator();it.hasNext();){
	                 List<Point> lst=it.next();
	                 if(lst.isEmpty()){
	                   continue;
	                 }
	                 System.out.println("-----第"+index+"个聚类-----");
	                 for(Iterator<Point> it1=lst.iterator();it1.hasNext();){
	                   Point p=it1.next();
	                   System.out.println(p.print());
	                 }
	                 index++;
	               }
	             }
	             
	      

    public  static void applyDbscan(String file) throws IOException{
	      pointsList=getPointsList(file);
	      for(int index=0;index<pointsList.size();++index){
	        List<Point> tmpLst=new ArrayList<Point>();
	        Point p=pointsList.get(index);
	        if(p.isClassed())
	         continue;
	        tmpLst=isKeyPoint(pointsList, p, e, minp);
	        if(tmpLst!=null){
	          resultList.add(tmpLst);
	        }
	      }
	     int length=resultList.size();
	      for(int i=0;i<length;++i){
	        for(int j=0;j<length;++j){
	          if(i!=j){
	            if(mergeList(resultList.get(i), resultList.get(j))){
	              resultList.get(j).clear();
	            }
	          }
	        }
	      }
	    }

}


