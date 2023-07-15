package osProject;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.State;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class OperatingSystem extends Thread {

	static Queue<Process> newQueue = new LinkedList<Process>();
	public static Queue<Process> readyQueue = new LinkedList<Process>();
	public static QueueObj readyQueuee = new QueueObj(10);
	//public static Queue<Object> readyQueuee = new LinkedList<Object>();
	static Queue<Process> blockedQueue = new LinkedList<Process>();
	static Queue<Process> terminatedQueue = new LinkedList<Process>();
	
	Process runningProcess = null;
	
    Queue<Process> lowLevelProcess = new LinkedList<Process>();
    Queue<Process> mediumLevelProcess = new LinkedList<Process>();
    Queue<Process> highLevelProcess = new LinkedList<Process>();  

	public final int Quantum=2;
	
	
	
	public OperatingSystem() {
		System.out.println("intializing os");
	}

	
	public void createProcess() { 
		int type = ((int)(Math.random()*2));
		int priorty = ((int) (Math.random()*3));
		System.out.println("type: "+type);
		Process p = new Process(Priority.low , 1);
		readyQueue.add(p);
				
	}

	public void allocateInMemory(Process P) {
		if(Memory.content[P.ID]==null) {
		    Memory.content[P.ID]=this;
		}else {
			int count = P.ID;
			while(Memory.content[count]==null) {
				count++;
			}
			if(count>1000) {
				System.out.println("no free space in memory");
			}else {
				Memory.content[count]=this;
			}
		}
		
	}


    


    public void Scheduler_RR() {
    	OperatingSystem o = new OperatingSystem();
    	while(!readyQueue.isEmpty()) {
    		if(readyQueue.peek().state==States.Ready) {
    			runningProcess=readyQueue.remove();  			
    			for(int i =0 ; i <2000 ; i++) {
        			o.start();
        			runningProcess.state=States.Running;  
    			}
    			o.isInterrupted();
				runningProcess.state=States.Ready;
				readyQueue.add(runningProcess);
				
    		}else if(readyQueue.peek().state==States.Blocked) {
    			blockedQueue.add(readyQueue.remove());
    		}
    		if(runningProcess.state==States.Running) {
    			if(runningProcess instanceof Process) {
    				for(int i =0 ; i <2000 ; i++);
    				runningProcess.state=States.Ready;
    				readyQueue.add(runningProcess);
    			}else if(runningProcess instanceof ProcessB) {
    				for(int i =0 ; i <2000 ; i++);
    				runningProcess.state=States.Ready;
    				readyQueue.add(runningProcess);
    			}
    		}
       if(runningProcess.state == States.Terminated) {   //b-check readyQueue kaman 3ashan el semaphore signal hatghyro le ready (will be used in round robin)           
    			terminatedQueue.add(runningProcess);
    			runningProcess=null;
    		}
      
    	}
    }
    public void run() {
    	Process p = readyQueue.peek();
    }

    
    public void Scheduler_MLQS() {
    	
    	while(!readyQueue.isEmpty()) {
    		Process current = readyQueue.remove();
    		
    		if(current.priority==Priority.low) {
    			lowLevelProcess.add(current);
    		}else if(current.priority==Priority.medium) {
    			mediumLevelProcess.add(current);	
    		}else if(current.priority==Priority.high) {
    			highLevelProcess.add(current);
    		}
    	}
    	
    	while(!highLevelProcess.isEmpty()) {
    		Scheduler_FCFS_Helper(highLevelProcess);
    	}
    	while(!mediumLevelProcess.isEmpty()) {
    		Scheduler_FCFS_Helper(mediumLevelProcess);
    	}
    	while(!lowLevelProcess.isEmpty()) {
    		Scheduler_FCFS_Helper(lowLevelProcess);
    	}
    }

    public void Scheduler_FCFS(){
    	Scheduler_FCFS_Helper(readyQueue);
   	}
    
    private void Scheduler_FCFS_Helper(Queue<Process> ready){
    	while(!ready.isEmpty()) {
    		if(ready.peek().state==States.Ready) {
    			runningProcess=readyQueue.remove();
    			runningProcess.state=States.Running;
    			System.out.println(runningProcess.ID);
    			
    			
    			//Get type and exdecute accordingly
    			if(runningProcess.x == 0) {
    				runningProcess.ProcessA("processA");
    			}else {
    				
    			}
    		}else if(ready.peek().state==States.Blocked) {
    			blockedQueue.add(ready.peek());
    			readyQueue.remove();
    		}
    	}
    }
	

	public static void main(String[] args) {
		OperatingSystem os = new OperatingSystem();
	    os.createProcess();
	    readyQueuee.printQueue();
	   // os.readyQueue.peek().getID();
	    os.createProcess();
	    os.createProcess();
	    os.createProcess();
	    os.createProcess();
	    os.createProcess();
	    for ( Process P : readyQueue) {
			System.out.println(P.ID);
		}

	    //os.Scheduler_FCFS_Helper(os.readyQueue);
	    
	}
}
