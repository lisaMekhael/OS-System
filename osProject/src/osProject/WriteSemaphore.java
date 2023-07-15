package osProject;

import osProject.OperatingSystem;

import java.util.LinkedList;
import java.util.Queue;

public class WriteSemaphore{
	static int value ;
	static Queue<Process> queue= new LinkedList<Process>();
	
	public WriteSemaphore() {
		value=1;	
	}

	public static void semWriteWait(Process p) {         
		if( value <=0) {
			value--;
			queue.add(p);
			p.state=States.Blocked;
			System.out.println("process: "+p.ProcessID);
			p.getState();
			System.out.println("stuck in semaphore write");
			while(queue.contains(p)); 
		}else {
			System.out.println("process: "+p.ProcessID);
			p.getState();
			p.state=States.Ready;
			value--;
		}
		p.state=States.Ready;
	}
    //using the print
	
	public static void semWriteSingal(Process p) {
		value++;
		queue.remove();
		p.state=States.Ready;
		System.out.println("process: "+p.ProcessID);
		p.getState();
		System.out.println("out of semaphore write");
	}
	
}
