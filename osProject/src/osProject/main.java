package osProject;


public class main {

	public static events generateRandomEvent() {
		killProcess();
		events[] Event=new events[5];
		Event[0] = events.KeyPress;
		Event[1] = events.MoreHeap;
		Event[2] = events.DivisionByZero;
		Event[3] = events.DiskController;
		Event[4] = events.AccessPriviligedMemory;
		
		int x = ((int)(Math.random()*5));
		events currentEvent = Event[4];
		return currentEvent;
			
	}
	public static Memory memory = new Memory();
	public static void main(String[] args) {
		
		Process currentProcess = new Process(Priority.high, 0);
	
		
		events current = generateRandomEvent();
		switch(current) {
		
		case KeyPress : memory.write("key press happened"); break;
		case DiskController : disk(); break;
		case DivisionByZero : killProcess(); break;
		case AccessPriviligedMemory : memory.accessPrivilged();break;
		case MoreHeap:memory.moreHeap(); break;
		}
		
		if(current==events.KeyPress) {
		System.out.println("the exception that occured was key press. "  );
		}else if(current==events.MoreHeap) {
			System.out.println("the exception that occured was requesting more heap. "  );
		}else if(current==events.DiskController) {
			System.out.println("the exception that occured was disk controller. "  );
		}else if(current==events.DivisionByZero) {
			System.out.println("the exception that occured was division by zero. "  );
		}else if(current==events.AccessPriviligedMemory) {
			System.out.println("the exception that occured trying to access privilege memory. "  );
		}
	}
	
	
	public static void disk() {
		
		int upper = ((int)(Math.random()*999));
		int lower = ((int)(Math.random()*999));		
		int operation = ((int)(Math.random()*2)+1);   //write = busy
		
		while(lower>upper) {
			 upper = ((int)(Math.random()*999));
			 lower = ((int)(Math.random()*999));
		}
		
		if(operation==1) {  //read
			String s="";
			s=memory.read(lower , upper);
			System.out.println("what is read from memory: "+s);
			
		}else {
			memory.write("disk is writing");
		}
		
	}
	
	public static void killProcess() {
		Process p = new Process();
		p.setState(States.Terminated);
	}
	
	

}
