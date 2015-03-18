package twitter;

import java.io.File;

import org.joda.time.DateTime;

import misc.FileHandlingHelper;
import twitter4j.ConnectionLifeCycleListener;

public class TwitterConnectionLifeCycleListener implements ConnectionLifeCycleListener {
	
	public DateTime startTime = null;
	public int timeIntervalInMinutes = 15;
	public int connectCountInPresentWindow = 0;
	public int allowedNumberOfConnections = 3;
	TwitterStreamCollector collector = null;
	public TwitterConnectionLifeCycleListener() {
		// TODO Auto-generated constructor stub
	}
	public TwitterConnectionLifeCycleListener(TwitterStreamCollector collector) {
		this.collector = collector;
	}

	public void onConnect() {
		// TODO Auto-generated method stub
		/*if (startTime == null) {
			startTime = DateTime.now();
		}
		DateTime currentTime = DateTime.now();
		 
		System.out.println("Difference in seconds :" +String.valueOf((int) ((currentTime.getMillis() - startTime.getMillis())/1000)));
		System.out.println("Connect count: " + String.valueOf(connectCountInPresentWindow));
		if (currentTime.minusMinutes(timeIntervalInMinutes).isAfter(startTime)) {
			startTime = currentTime;
			connectCountInPresentWindow = 1;
		} else {
			if (connectCountInPresentWindow >= allowedNumberOfConnections) {
				int differenceInSeconds=(int) (startTime.plusMinutes(timeIntervalInMinutes).getMillis() - currentTime.getMillis())/1000;
				System.out.println("Allowed limit of connections in this time window reached. Thread should wait " + String.valueOf(differenceInSeconds) + " seconds!");
				// code to wait
				collector.stopAndWait( differenceInSeconds);
			} else {
				connectCountInPresentWindow ++;
			}
			
		}*/
		
		FileHandlingHelper.saveToFile("\nON CONNECT" + DateTime.now().toString() , new File("log.txt"));
	}

	public void onDisconnect() {
		// TODO Auto-generated method stub
		//FileHandlingHelper.saveToFile("\nON DISCONNECT" + DateTime.now().toString() , new File("log.txt"));
	}

	public void onCleanUp() {
		// TODO Auto-generated method stub
		FileHandlingHelper.saveToFile("\nCLEAN UP", new File("log.txt"));
	}

}
