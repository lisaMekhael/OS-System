package osProject;

import osProject.AssignSemaphore;
import osProject.PrintingSemaphore;
import osProject.ReadSemaphore;
import osProject.WriteSemaphore;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public  class Process extends Thread {
	static int ProcessID=1;
	int ID;
	States state;
    Priority priority; 
	int memoryAddress;
	int x;
	Thread thread = new Thread();	
	public Process(Priority p , int x) {
		this.x = x;
		this.ID = ProcessID++;
		this.thread.setName("thread"+this.ID);
		this.state=state.New;
		if(p ==Priority.low) {
			this.priority=Priority.low;
		}else if(p==Priority.medium) {
			this.priority=Priority.medium;
		}else if(p==Priority.high) {
			this.priority=Priority.high;
		}
		
//		if(x==0) {
//			this.ProcessA("test.txt");
//		}else if(x==1) {
//			this.ProcessB("testing", "test.txt");
//		}
	}
	
	public void ProcessA(String FileName) {
		
		System.out.println("process A");
		
		AssignSemaphore.semAssignWait(this);
		Map<String, String> fileName =assign("FileName" , FileName);
		AssignSemaphore.semAssignSingal(this);
		
		try {
			ReadSemaphore.semReadtWait(this);
			readFile(fileName.get("FileName"));
			ReadSemaphore.semReadSingal(this);
			
		} catch (FileNotFoundException e) {
			PrintingSemaphore.semPrintWait(this);
			Print("file not found");
			PrintingSemaphore.semPrintSingal(this);
		}
		this.state=state.Terminated;
	}
	
	public void ProcessB(String FileName ,String data ) {
		System.out.println("process B");
		AssignSemaphore.semAssignWait(this);
		Map<String, String> fileName =assign("FileName" , FileName);
		AssignSemaphore.semAssignSingal(this);
		
		AssignSemaphore.semAssignWait(this);
		Map<String, String> dataa= assign("data" , data);
		AssignSemaphore.semAssignSingal(this);
		
		try {
			WriteSemaphore.semWriteWait(this);
			writeFile(fileName.get("FileName"), dataa.get("data"));
			WriteSemaphore.semWriteSingal(this);
		} catch (IOException e1) {
			PrintingSemaphore.semPrintWait(this);
			Print("file not found");
			PrintingSemaphore.semPrintSingal(this);
		}
		this.state=state.Terminated;
	}
	
	
//	public void outputState() {
//		if(this.state==States.Blocked) {
//			System.out.println(" ,state: blocked");
//		}else if(this.state==States.New) {
//			System.out.println(" ,state : new");
//		}else if(this.state==States.Ready) {
//			System.out.println(" ,state : ready");
//		}else if(this.state==States.Running) {
//			System.out.println(" ,state : running");
//		}else if(this.state==States.Terminated) {
//			System.out.println(" ,state : Terminated");
//		}
//	}
//	
//	public void outputPriority() {
//		if(this.priority==Priority.high) {
//			System.out.print(" , priority: high ");
//		}else if(this.priority==Priority.medium) {
//			System.out.print(" , priority: medium");
//		}else if(this.priority==Priority.low) {
//			System.out.print(" , priority: low");
//		}
//	}
//	
//	public void output() {
//		System.out.print("process ID: "+this.ID);
//		outputPriority();
//		outputState();
//	}

	public void Print(Object o) {
//		this.state=States.Running;
		System.out.println(o);
	}

	
	public void getID() {
		System.out.println("process id:"+ID);
	}
	
	
	
	public void allocateInMemory() {
		if(Memory.content[this.ID]==null) {
		    Memory.content[this.ID]=this;
		}else {
			int count = this.ID;
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

	public void setState(States state) {
		this.state = state;
	}
	
	
	public Map<String, Integer> assign(String  x , int y) {
//		OperatingSystem.readyQueue.remove(this);
		this.state=States.Running;
//		OperatingSystem.runningQueue.add(this);
		Map<String, Integer> assignInt
        = new HashMap<String, Integer>();
		
		assignInt.put(x, y);    
		return assignInt;
	}
	
	
	
    public Map<String, String> assign(String x, String y ) {
    	this.state=States.Running;
    	Map<String, String> assignString
        = new HashMap<String, String>();
		
    	assignString.put(x, y);
		return assignString;
	}
	
	
	public void readFile(String Filename) throws FileNotFoundException  {
		this.state=States.Running;
		String line;
		BufferedReader br1=null;
		try {
			br1 = new BufferedReader(new FileReader(Filename));
		} catch (FileNotFoundException e) {
			Print("file not found");
		}
		try {
			while((line=br1.readLine())!=null) {
				Print(line);
			}
		} catch (IOException e) {
			Print("file not found");
		}	
	}
	
	public  void writeFile(String Filename, String data) throws IOException {
		this.state=States.Running;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Filename));
			writer.write(data);
			writer.close();
		} catch (Exception e) {
			Print("file not found");
		}
	}
	
	
//	public static void main(String[] args) {
//
//	}

	
}

