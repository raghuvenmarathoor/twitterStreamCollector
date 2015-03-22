package twitter;

import java.io.File;

import org.joda.time.DateTime;

import misc.FileHandlingHelper;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;

public class TwitterRateLimitStatusListener implements RateLimitStatusListener{
	
	TwitterStreamCollector collector;
	public TwitterRateLimitStatusListener(TwitterStreamCollector collector) {
		this.collector = collector;
	}

	public void onRateLimitStatus(RateLimitStatusEvent event) {
		// TODO Auto-generated method stub
		System.out.println("ON RATE LIMIT STATUS " + event.toString());
	}

	public void onRateLimitReached(RateLimitStatusEvent event) {
		// TODO Auto-generated method stub
		System.out.println("RATE LIMIT REACHED ");
		FileHandlingHelper.saveToFile("RATE LIMIT REACHED" + DateTime.now().toString() + "\n" , new File("log"));
		this.collector.stopAndWait(550);
	}

}
