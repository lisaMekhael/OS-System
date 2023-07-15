package osProject;

import osProject.AssignSemaphore;
import osProject.PrintingSemaphore;
import osProject.ReadSemaphore;
import osProject.WriteSemaphore;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ProcessB extends Process {
	String FileName;
	String data;
	public ProcessB(Priority p, String FileName ,String data ) {
		super(p, 0);
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
//	public void output() {
//		super.output();
//		System.out.print("process type B (write data to a file)");
//	}
	
//	public static void main(String[] args) {
//		ProcessB p = new ProcessB(Priority.low, "test.txt", "aloooo");
//		ProcessB p1 = new ProcessB(Priority.low, "test.txt", "aloooo");
//		ProcessB p2 = new ProcessB(Priority.low, "test.txt", "aloooo");
//		
//		p.output();
//		p1.output();
//		p2.output();
//	}



}
