package twitter;

import org.bson.Document;

import db.MongoDbConnector;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;

public class TwitterStatusListenerMongoSave implements StatusListener {

	MongoDbConnector connector = null;
	Thread thisThread = null;
	TwitterStreamCollector collector = null;

	public TwitterStatusListenerMongoSave(MongoDbConnector connector) {
		this.connector = connector;
	}

	public TwitterStatusListenerMongoSave(MongoDbConnector connector,
			TwitterStreamCollector collector) {
		this.connector = connector;
		this.collector = collector;
	}

	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		arg0.printStackTrace();
		System.out.println("ON EXCEPTION");
		try {
		TwitterException twitterException = (TwitterException) arg0;
		handleTwitterException(twitterException);
		} catch (ClassCastException classCastException) {
			handleOtherExceptions(arg0);
		}
		
		
	}
	
	private void handleOtherExceptions(Exception exception) {
		System.out.println("waiting 100 seconds");
		collector.setNextTwitterAccount();
		collector.stopAndWait(100);
	}
	private void handleTwitterException(TwitterException twitterException) {
		int retryAfterSeconds = twitterException.getRetryAfter();
		System.out.println(String.valueOf(retryAfterSeconds));
		if (retryAfterSeconds > 0) {
			collector.stopAndWait(retryAfterSeconds);
		}
		int errorCode = twitterException.getStatusCode();
		System.out.println("Twitter Exception status code :" + String.valueOf(errorCode));
		if (errorCode == 420) {
			System.out.println("ErrorCode 420");
			collector.setNextTwitterAccount();
			System.out.println("Next account set");
			collector.stopAndWait(5);
		}
	}

	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		System.out.println("DELETION NOTICE");
	}

	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		System.out.println("SCRUB GEO");
	}

	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
		System.out.println("STALL WARNING");
	}

	public void onStatus(Status arg0) {
		Document tweetDoc = Tweet.fromStatus(arg0).getDocument();
		connector.insert(tweetDoc, "tweet_dump");
	}

	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("TRACK LIMITATION NOTICE:" + String.valueOf(arg0));
	}

}
