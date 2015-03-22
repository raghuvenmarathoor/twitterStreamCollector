package twitter;

import java.util.List;

import org.bson.Document;

import db.MongoDbConnector;

public class TweetProcessor {
	
	MongoDbConnector connector = null;
	public TweetProcessor (MongoDbConnector connector) {
		this.connector = connector;
	}
	
	
	public void processTweet(Document document) {
		processTweet(Tweet.fromDocument(document));
	}
	
	public void processTweets(List<Document> documents) {
		for (Document document: documents) {
			processTweet(document);
		}
	}
	public void processTweet(Tweet document) {
		
	}
	
	

}
