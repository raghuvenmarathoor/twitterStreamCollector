package twitter;

import mongo.MongoDbConnector;

import org.bson.Document;

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
		TwitterException twitterException = (TwitterException) arg0;
		int retryAfterSeconds = twitterException.getRetryAfter();
		System.out.println(String.valueOf(retryAfterSeconds));
		if (retryAfterSeconds > 0) {
			collector.stopAndWait(retryAfterSeconds);
		}
		int errorCode = twitterException.getStatusCode();
		if (errorCode == 420) {
			System.out.println("ErrorCode 420");
			collector.stopAndWait(900);
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
		Document tweetDoc = TweetDocument.fromStatus(arg0).getDocument();
		connector.insert(tweetDoc, "tweet_dump");
	}

	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("TRACK LIMITATION NOTICE:" + String.valueOf(arg0));
	}

}
