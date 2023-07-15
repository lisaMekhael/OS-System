package osProject;

import osProject.OperatingSystem;

import java.util.LinkedList;
import java.util.Queue;

public class PrintingSemaphore{
	static int value ;
	static Queue<Process> queue= new LinkedList<Process>();

	public PrintingSemaphore() {
		value=1;	
	}

	public static void semPrintWait(Process p) {         
		if( value <=0) {
			value--;
			queue.add(p);
			p.state=States.Blocked;
		
			System.out.println("process: "+p.ProcessID);
			System.out.println("stuck in semaphore print");
//			p.outputState();
			while(queue.contains(p)); 
		}else {
			System.out.println("process: "+p.ProcessID);
			p.state=States.Ready;
//			p.outputState();
			value--;
		}
	}


	public static void semPrintSingal(Process p) {
		value++;
		queue.remove();
		p.state=States.Ready;
		System.out.println("process: "+p.ProcessID);
		System.out.println("out of semaphore print");
//		p.outputState();
	}

}
