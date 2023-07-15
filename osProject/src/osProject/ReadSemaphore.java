package osProject;

import java.util.LinkedList;
import java.util.Queue;

import osProject.OperatingSystem;
public class ReadSemaphore{
	static int value ;
	static Queue<Process> queue= new LinkedList<Process>();
	
	public ReadSemaphore() {
		value=1;	
	}

	public static void semReadtWait(Process p) {         
		if( value <=0) {
			value--;
			queue.add(p);
			p.state=States.Blocked;
			System.out.println("process: "+p.ProcessID);
			p.getState();
			System.out.println("stuck in semaphore read");
			while(queue.contains(p)); 
		}else {
			System.out.println("process: "+p.ProcessID);
			p.state=States.Ready;
			p.getState();
			value--;
		}
		p.state=States.Ready;
	}

	public static void semReadSingal(Process p) {
		value++;
		queue.remove();
		p.state=States.Ready;
		System.out.println("process: "+p.ProcessID);
		p.getState();
		System.out.println("out of semaphore read");
	}
	
}
