package osProject;

import osProject.OperatingSystem;

import java.util.LinkedList;
import java.util.Queue;

public class AssignSemaphore{
	static int value ;
	static Queue<Process> queue= new LinkedList<Process>();
	
	public AssignSemaphore() {
		value=1;	
	}

	public static void semAssignWait(Process p) {         
		if( value <=0) {
			value--;
			queue.add(p);
			System.out.println("process: "+p.ProcessID);
			
			
//			if(p.state==States.New) {
//				OperatingSystem.newQueue.remove(p);
//			}else if(p.state==States.Ready){
//				OperatingSystem.readyQueue.remove(p);
//			}
			p.state=States.Blocked;
//			p.output();
			System.out.println("stuck in semaphore assign");
//			OperatingSystem.blockedQueue.add(p);
			while(queue.contains(p)); 
			
//			OperatingSystem.blockedQueue.remove(p);
			p.state=States.Ready;
//			OperatingSystem.readyQueue.add(p);
		}else {
			p.state=States.Ready;
			System.out.println("process: "+p.ProcessID);
//			p.output();
			System.out.println("out of semaphore assign");
//			if(p.state==States.New) {
//				OperatingSystem.newQueue.remove(p);
//			}else if(p.state==States.Ready){
//				OperatingSystem.readyQueue.remove(p);
//			}else if(p.state==States.Blocked) {
//				OperatingSystem.blockedQueue.remove(p);
//			}
//			OperatingSystem.readyQueue.add(p);
			value--;
		}
		
		
	}
    //using the print
	
	public static void semAssignSingal(Process p) {
		value++;
		if(value<=0) {
		queue.remove();
//		if(p.state==States.Running) {
//			OperatingSystem.runningQueue.remove(p);
//		}else if(p.state==States.Blocked) {
//			OperatingSystem.blockedQueue.remove(p);
//		}
//		OperatingSystem.readyQueue.add(p);
		p.state=States.Ready;
		OperatingSystem.readyQueue.add(p);
	}
	}
	
}
