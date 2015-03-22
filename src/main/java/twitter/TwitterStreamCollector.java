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
	private int timeIntervalInSeconds = 450;
	private String[] keywordList = null;
	private int keywordFilterCount = 10;
	private boolean waitFlag = false;
	private TwitterStreamThread twitterStreamThread = null;

	public TwitterStreamCollector(TwitterStream twitterStream,
			MongoDbConnector connector, String[] keywordList) {
		this.twitterStream = twitterStream;
		this.connector = connector;
		this.keywordList = keywordList;
		twitterStreamThread = new TwitterStreamThread();
		this.twitterStream.addListener(new TwitterStatusListenerMongoSave(
				this.connector, this));
		this.twitterStream.addRateLimitStatusListener(new TwitterRateLimitStatusLimiter(this));
		this.twitterStream.addConnectionLifeCycleListener(new TwitterConnectionLifeCycleListener(this));
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
		while (count <= total_count) {
			int flg = 0;
			while (waitFlag == true) {
				if (flg == 0) {
				System.out.println(Thread.currentThread().getName());
				System.out.println("WAITING");
				flg = 1;
				}
			}
			flg = 0;
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
			twitterStreamThread = new TwitterStreamThread(stringList,
					twitterStream);
			twitterStreamThread.start();
			while (DateTime.now().isBefore(endTime) == true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			twitterStreamThread.cleanUpAndShutDown();
			for (String value : stringList) {
				Document doc = new Document(TwitterKeywords.TWITTER_KEYWORD_VALUE, value);
				List<Document> docs = connector.find(doc, TwitterKeywords.TWITTER_KEYWORD_COLLECTION_NAME);
				if (docs.size() > 0) {
					doc.append(TwitterKeywords.LAST_RUN_TIME, now.toString());
					connector.update(new Document(), doc, TwitterKeywords.TWITTER_KEYWORD_COLLECTION_NAME);	
				} else {
					//log information === not found
				}
				
			}
		}
	}
	
	public void stopAndWait(int seconds) {
		twitterStreamThread.cleanUpAndShutDown();
		
		waitFlag = true;
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitFlag = false;
		//System.out.println(String.valueOf(waitFlag));
	}

	public static void main(String[] args) throws InterruptedException {
		TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
		MongoDbConnector connector = new MongoDbConnector(new MongoClient(),
				"mydb");
		List<Document> docList = connector.find(new Document(), "keywords");
		String[] stringArray = new String[docList.size()];
		int count = 0;
		for (Document doc : docList) {
			stringArray[count] = doc.getString("value");
			count++;
		}
		TwitterStreamCollector collector = new TwitterStreamCollector(
				twitterStream, connector, stringArray);
		collector.run();
	}
}
