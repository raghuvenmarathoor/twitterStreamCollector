package twitter;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;

public class TwitterStreamThread extends Thread {

	String[] stringArray;
	TwitterStream twitterStream;
	public TwitterStreamThread() {
		
	}
	public TwitterStreamThread(String[] stringArray, TwitterStream twitterStream) {
		this.stringArray = stringArray;
		this.twitterStream = twitterStream;
	}
	
	public void setStatusListener(StatusListener listener) {
		this.twitterStream.addListener(listener);
	}
	public void run() {
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.track(stringArray);
		twitterStream.filter(filterQuery);
	}
	
	public void cleanUpAndShutDown() {
		twitterStream.cleanUp();
		twitterStream.shutdown();
	}
	
	public void waitThread(int seconds) {
		try {
			System.out.println("Twitter stream going to wait ######");
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
