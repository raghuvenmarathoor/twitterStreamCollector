package twitter;

import java.util.List;

import mongo.MongoDbConnector;

import org.bson.Document;

public class TweetProcessor {
	
	MongoDbConnector connector = null;
	public TweetProcessor (MongoDbConnector connector) {
		this.connector = connector;
	}
	
	
	public void processTweet(Document document) {
		processTweet(TweetDocument.fromDocument(document));
	}
	
	public void processTweets(List<Document> documents) {
		for (Document document: documents) {
			processTweet(document);
		}
	}
	public void processTweet(TweetDocument document) {
		
	}
	
	

}
