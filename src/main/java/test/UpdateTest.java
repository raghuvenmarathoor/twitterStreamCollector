package test;

import java.util.List;

import org.bson.Document;

import twitter.Tweet;

import com.mongodb.MongoClient;

import db.MongoDbConnector;

public class UpdateTest {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		MongoDbConnector connector = new MongoDbConnector(client, "mydb");
		List<Document> documents = connector.find(new Document(), Tweet.TABLE_NAME);
		Tweet tweet = Tweet.fromDocument(documents.get(0));
		System.out.println(documents.get(0).toString());
		tweet.setProcessedStatus(true);
		connector.update(documents.get(0), tweet.getDocument(), Tweet.TABLE_NAME);
		System.out.println(connector.find(new Document("_id", documents.get(0).get("_id")), Tweet.TABLE_NAME));
		
	}
}
