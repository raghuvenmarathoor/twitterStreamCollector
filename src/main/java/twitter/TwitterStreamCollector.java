package twitter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.bson.Document;
import org.joda.time.DateTime;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;

import db.MongoDbConnector;
import twitter.auth.TwitterAuthenticationInformation;
import twitter.auth.TwitterAuthenticationInformationCollection;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamCollector {

	public final String collection_name = "tweet_dump";
	private TwitterStream twitterStream;
	private MongoDbConnector connector;
	private int timeIntervalInSeconds = 450;
	private String[] keywordList = null;
	private int keywordFilterCount = 10;
	private boolean waitFlag = false;
	private TwitterStreamThread twitterStreamThread = null;
	private TwitterAuthenticationInformationCollection twitterAccountCollection = null;
	private Iterator<TwitterAuthenticationInformation> twitterAccountIterator = null;

	public TwitterStreamCollector(TwitterAuthenticationInformationCollection twitterAccountCollection,
			MongoDbConnector connector, String[] keywordList) {
		this.connector = connector;
		setTwitterAccountCollection(twitterAccountCollection);
		initializeTwitterStream(twitterAccountCollection);
		this.connector = connector;
		this.keywordList = keywordList;
		twitterStreamThread = new TwitterStreamThread();
		setNextTwitterAccount();
	}

	public void setTimeIntervalInSeconds(int val) {
		this.timeIntervalInSeconds = val;
	}

	public void setKeywordFilterCount(int val) {
		this.keywordFilterCount = val;
	}

	
	/**
	 * @return the twitterAccountCollection
	 */
	public TwitterAuthenticationInformationCollection getTwitterAccountCollection() {
		return twitterAccountCollection;
	}

	/**
	 * @param twitterAccountCollection the twitterAccountCollection to set
	 */
	public void setTwitterAccountCollection(
			TwitterAuthenticationInformationCollection twitterAccountCollection) {
		this.twitterAccountCollection = twitterAccountCollection;
		this.twitterAccountIterator = twitterAccountCollection.getInfoCollection().iterator();
	}
	
	public void setNextTwitterAccount() {
		if (this.twitterAccountIterator.hasNext()) {
			TwitterAuthenticationInformation info = twitterAccountIterator.next();
			setTwitterAccount(info);
		} else {
			System.out.println("resetting twitterAuthentication iterator");
			this.twitterAccountIterator = twitterAccountCollection.getInfoCollection().iterator();
			setTwitterAccount(twitterAccountIterator.next());
		}
	}
	public void setTwitterAccount(TwitterAuthenticationInformation info) {
		initializeTwitterStream(twitterAccountCollection);
		twitterStream.setOAuthConsumer(info.getConsumerKey(), info.getConsumerSecret());
		twitterStream.setOAuthAccessToken(new AccessToken(info.getAccessTocken(), info.getAccessTockenSecret()));
	}
	public void initializeTwitterStream(TwitterAuthenticationInformationCollection infoCollection) {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setDebugEnabled(twitterAccountCollection.isDebugEnabled());
		this.twitterStream = new TwitterStreamFactory(builder.build()).getInstance();
		this.twitterStream.addListener(new TwitterStatusListenerMongoSave(
				this.connector, this));
		this.twitterStream.addRateLimitStatusListener(new TwitterRateLimitStatusLimiter(this));
		this.twitterStream.addConnectionLifeCycleListener(new TwitterConnectionLifeCycleListener(this));
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
				Document doc = new Document(TwitterKeywordInfoDocument.VALUE_FIELD_NAME, value);
				List<Document> docs = connector.find(doc, TwitterKeywordInfoDocument.TWITTER_KEYWORD_COLLECTION_NAME);
				if (docs.size() > 0) {
					doc.append(TwitterKeywordInfoDocument.LAST_RUN_TIME_FIELD_NAME , now.toDate());
					connector.update(new Document(), doc, TwitterKeywordInfoDocument.TWITTER_KEYWORD_COLLECTION_NAME);	
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
		//TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
		MongoDbConnector connector = new MongoDbConnector(new MongoClient(),
				"mydb");
		TwitterAuthenticationInformationCollection infoCollection = TwitterAuthenticationInformationCollection.fromFile(new File("twitter-auth.xml"));
		
		List<Document> docList = connector.find(new Document(), "keywords");
		String[] stringArray = new String[docList.size()];
		int count = 0;
		for (Document doc : docList) {
			stringArray[count] = doc.getString("value");
			count++;
		}
		TwitterStreamCollector collector = new TwitterStreamCollector(
				infoCollection, connector, stringArray);
		collector.run();
	}
}
