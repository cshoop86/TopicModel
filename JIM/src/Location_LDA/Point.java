package Location_LDA;

public class Point {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	    public int id=-1;
	   private double x;
	    private double y;
	    private boolean isKey;
	    private boolean isClassed;
	    
	    public boolean isKey() {
	      return isKey;
	    }
	    public void setKey(boolean isKey) {
	      this.isKey = isKey;
	      this.isClassed=true;
	    }
	    public boolean isClassed() {
	      return isClassed;
	    }
	    public void setClassed(boolean isClassed) {
	      this.isClassed = isClassed;
	    }
	    public double getX() {
	      return x;
	    }
	    public void setX(double x) {
	      this.x = x;
	    }
	    public double getY() {
	      return y;
	    }
	    public void setY(double y) {
	      this.y = y;
	    }
	    
	    public Point(){
	      x=0;
	      y=0;
	    }
	    public Point(double x,double y){
	      this.x=x;
	      this.y=y;
	    }
	    
	    public Point(double x,double y,int id){
		      this.x=x;
		      this.y=y;
		      this.id=id;
		    }
	    public Point(String str){
	      String[] p=str.split(",");
	      this.x=Double.parseDouble(p[0]);
	      this.y=Double.parseDouble(p[1]);
	    }
	    public String print(){
	    	if(id<0)
	      return "<"+this.x+","+this.y+">";
	    	else
	    		return "<"+this.id+","+this.x+","+this.y+">";
	    }
	    
	    
	    


}
