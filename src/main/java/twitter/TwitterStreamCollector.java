package twitter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.bson.Document;
import org.joda.time.DateTime;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;

import mongo.MongoDbConnector;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterStreamCollector {

	public final String collection_name = "tweet_dump";
	private TwitterStream twitterStream;
	private MongoDbConnector connector;
	private int timeIntervalInSeconds = 360;
	private String[] keywordList = null;
	private int keywordFilterCount = 10;
	private TwitterStreamThread twitterStreamThread = null;
	
	public TwitterStreamCollector(TwitterStream twitterStream, MongoDbConnector connector, String[] keywordList) {
		this.twitterStream = twitterStream;
		this.connector = connector;
		this.keywordList = keywordList;
		twitterStreamThread = new TwitterStreamThread();
		this.twitterStream.addListener(new TwitterStatusListenerMongoSave(this.connector));
	}
	public void setTimeIntervalInSeconds(int val) {
		this.timeIntervalInSeconds = val;
	}
	public void setKeywordFilterCount(int val) {
		this.keywordFilterCount = val;
	}
	public void run() {
		int total_count = keywordList.length;
		int count = 0;
		while(count <= total_count) {
			String[] stringList = new String[keywordFilterCount];
			for (int i = 0; i < keywordFilterCount; i++) {
				if ((i + count) < total_count) {
					stringList[i] = keywordList[i + count];
				} else {
					break;
				}
			}
			
			count = count + keywordFilterCount;
			DateTime now = DateTime.now();
			DateTime endTime = now.plusSeconds(timeIntervalInSeconds);
			for (String str: stringList) {
				System.out.println("Keyword :" + str);
			}
			twitterStreamThread = new TwitterStreamThread(stringList,twitterStream);
			twitterStreamThread.run();
			while(DateTime.now().isBefore(endTime) == true) {
				try {
					/*System.out.println(DateTime.now().toString() + ":" + endTime.toString());
					System.out.println("THREAD IS GOING TO SLEEP");*/
					Thread.sleep(900 * timeIntervalInSeconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			/*System.out.println("TWITTER THREAD STOPPING ##################################");
			System.out.println("COunt:" + String.valueOf(count));*/
			twitterStreamThread.cleanUpAndShutDown();
			
			//twitterStreamThread.destroy();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
		MongoDbConnector connector = new MongoDbConnector(new MongoClient(), "mydb");
		List<Document> docList = connector.find(new Document(), "keywords");
		String[] stringArray = new String[ docList.size()];
		int count = 0;
		for (Document doc: docList) {
			stringArray[count] = doc.getString("value");
			count++;
		}
		
		TwitterStreamCollector collector = new TwitterStreamCollector(twitterStream, connector, stringArray);
		collector.run();
		/*DateTime time = DateTime.now();
		DateTime endDateTime = time.plusSeconds(10);
		while(DateTime.now().isBefore(endDateTime)) {
			System.out.println("Datetime before");
		}*/
		
		
		
		
	}
}
