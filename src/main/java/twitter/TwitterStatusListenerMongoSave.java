package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import mongo.MongoDbConnector;

import org.bson.Document;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.UserMentionEntity;

public class TwitterStatusListenerMongoSave implements StatusListener {
	
	MongoDbConnector connector = null;
	Thread thisThread = null;
	public TwitterStatusListenerMongoSave(MongoDbConnector connector) {
		this.connector = connector;
	}
    
	
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		arg0.printStackTrace();
		if (thisThread != null) {
			try {
				thisThread.sleep(1000*60*16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		System.out.println("DELETION NOTICE");
	}

	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
		System.out.println("STALL WARNING");
	}

	public void onStatus(Status arg0) {
		// TODO Auto-generated method stub
		arg0.getText();
		Document tweetDoc = new Document();
		tweetDoc.append("tweet", arg0.getText());
		GeoLocation location =  arg0.getGeoLocation();
		if (location != null) 
		tweetDoc.append("location", location.toString());
		tweetDoc.append("userId", arg0.getUser().getId());
		long[] contributors =  arg0.getContributors();
		List<Long> contributorsList = new ArrayList<Long>();
		for (long val : contributors) {
			contributorsList.add(Long.valueOf(val));		
		}
		if (contributors != null)
		tweetDoc.put("contributors" , contributorsList);
		Date createdAt = arg0.getCreatedAt();
		if (createdAt != null) {
			tweetDoc.put("createdAt", createdAt);
		}
		int favCount = arg0.getFavoriteCount();
		tweetDoc.put("favouriteCount", Integer.valueOf(favCount));
		HashtagEntity[] hashtags = arg0.getHashtagEntities();
		List<String> hashTagStringList = new ArrayList<String>();
		for(HashtagEntity entity: hashtags) {
			hashTagStringList.add(entity.getText());
		}
		tweetDoc.put("hashtags", hashTagStringList);
		long id = arg0.getId();
		tweetDoc.put("id", id);
		long replyToId = arg0.getInReplyToUserId();
		tweetDoc.put("inreplyToUserId", replyToId);
		tweetDoc.put("retweetCount", arg0.getRetweetCount());
		UserMentionEntity[] userMentionEntities = arg0.getUserMentionEntities();
		List<Long> usermentionsIds = new ArrayList<Long>();
		for (UserMentionEntity entity: userMentionEntities) {
			usermentionsIds.add(entity.getId());
		}
		tweetDoc.put("userMentions", usermentionsIds);
		if (arg0.getSource()!= null) {
			tweetDoc.put("source", arg0.getSource());
		}
		System.out.println(tweetDoc.toString());
		connector.insert(tweetDoc, "tweet_dump");
	    //System.out.println(arg0.toString());	
	}

	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("TRACK LIMITATION NOTICE:" + String.valueOf(arg0));
	}

}
