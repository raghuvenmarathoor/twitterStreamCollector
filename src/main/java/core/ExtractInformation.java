package core;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter.TwitterInfoLoader;

import com.mongodb.MongoClient;

import db.MongoDbConnector;
import au.com.bytecode.opencsv.CSVReader;

public class ExtractInformation {

	public static final Pattern USER_REGEX_PATTERN =Pattern.compile("(?<=^|(?<=[^a-zA-Z0-9-_\\.]))(@|#)([A-Za-z]+[A-Za-z0-9_]+)");
	public File file;
	public ExtractInformation(File file) {
		this.file = file;
	}
	public ExtractInformation(String file) {
		this(new File(file));
	}
	public List<String> extractFromCsv() {
		Reader fileReader;
		List<String> stringList = new ArrayList<String>();
		try {
			fileReader = new FileReader(file);
			CSVReader reader = new CSVReader(fileReader);
			String[] line = reader.readNext();
			while (line != null) {
				for (String val : line) {
					Matcher matcher = USER_REGEX_PATTERN.matcher(val);
					if (matcher.find()) {
						//System.out.println(val);
						System.out.println(matcher.group(2).toString());
						stringList.add(matcher.group(1) + matcher.group(2));
					}
				}
				line = reader.readNext();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return stringList;
	}
	public static void main(String[] args) throws IOException {
		/*ExtractInformation infoExtractor = new ExtractInformation("C:\\load_data\\rawdata.csv");
		List<String> stringList = infoExtractor.extractFromCsv();
		MongoClient client = new MongoClient();
    	MongoDbConnector connector = new MongoDbConnector(client, "mydb");
    	TwitterInfoLoader loader = new TwitterInfoLoader(stringList, connector, "keywords");
    	loader.load();*/
		File file = new File("C:\\load_data\\genre.csv");
		Reader reader = new FileReader(file);
		CSVReader csvReader = new CSVReader(reader);
		String[] line = csvReader.readNext();
		List<String> stringList = new ArrayList<String>();
		while(line != null) {
			stringList.add("#" + line[0]);
			line = csvReader.readNext();
		}
		MongoClient client = new MongoClient();
    	MongoDbConnector connector = new MongoDbConnector(client, "mydb");
    	TwitterInfoLoader loader = new TwitterInfoLoader(stringList, connector, "keywords");
    	loader.load();
    	csvReader.close();
		
	}
}
