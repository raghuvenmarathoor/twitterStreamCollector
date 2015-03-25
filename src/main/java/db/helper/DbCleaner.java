package db.helper;

import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.bson.Document;

import twitter.Tweet;

import com.mongodb.MongoClient;

import db.MongoDbConnector;

public class DbCleaner {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		MongoDbConnector connector = new MongoDbConnector(client,"mydb");
		List<Document> docs = connector.find(new Document(), "tweet_dump");
		for (Document doc : docs) {
			Object id = doc.get(Tweet.IN_REPLY_TO_USER_ID);
			boolean change = false;
			if (id instanceof Integer) {
				Long newId = new Long(((Integer) id).longValue());
				doc.remove(Tweet.IN_REPLY_TO_USER_ID);
				doc.append(Tweet.IN_REPLY_TO_USER_ID, newId);
				change = true;
			}
			/*Object userId = doc.get(Tweet.USER_ID_FIELD_NAME);
			if (userId instanceof Integer) {
				
				Long newUserId = new Long(((Integer) userId).longValue());
				doc.remove(Tweet.USER_ID_FIELD_NAME);
				doc.append(Tweet.USER_ID_FIELD_NAME, newUserId);
				change = true;
			}*/
			if (change = true) {
				Document idSearch = new Document("_id", doc.get("_id"));
				List<Document> documents = connector.find(idSearch, "tweet_dump");
				while(documents.size() > 0) {
				connector.remove(idSearch, "tweet_dump");
				documents = connector.find(idSearch, "tweet_dump");
				}
				try {
				connector.insert(doc, "tweet_dump");
				} catch (Exception e) {
					System.out.println(doc.toString());
				}
			}
		}
	}
}
