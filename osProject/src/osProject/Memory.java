package osProject;


public class Memory {
	
	private int size;  
	static Object[] content; 
	
	public Memory() {
		this.size=1000;
		this.content = new Object[size];  //from 0-500 unpriviliged , 500-1000 priviliged 
	
	}
	
	public void initialize(Object[] c) {
		
	}
	
	public void write(String data) {
		diskStatus d = diskStatus.busy;
		for(int i = 499 ; i>=0 ; i--) {
			if(this.content[i]==null) {
				this.content[i]=data;
				break;
			}
			
		}
	}
	
	public String read(int lower , int upper) {   
		diskStatus d = diskStatus.idle;
		String s="";
		for(int i = lower ; i<=upper ; i++ ) {
			s.concat((String) this.content[i]);
			d = diskStatus.busy;
		}
		d = diskStatus.idle;
		return s;

	}
	
	public int accessPrivilged() {
		int empty=0;
		for(int i= 500 ; i<1000 ; i++) {
			if(this.content[i] == null) {
			   empty= i;
			   this.content[i]="reallocated";
			   break;
			}
		}
		return empty;
	}
	
	
	public boolean moreHeap() {   //check unprivildged
		boolean found= false;
		
		for(int i =0 ; i<500 ; i++) {
			if(this.content[i]==null) {
				this.content[i]="allocate";
				found = true;
				break;
			}
		}
		return found;
	}

	
	

}

