package misc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandlingHelper {

	public static void saveToFile(String data, File fileToSave) {
	  FileWriter writer;
	try {
		writer = new FileWriter(fileToSave , true);
		writer.write(data);
		writer.flush();
		writer.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	}
}
