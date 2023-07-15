package osProject;

import java.io.FileNotFoundException;


public class FileNotFound extends FileNotFoundException {

		public FileNotFound() {
			System.out.println("File not found");
		}
}
