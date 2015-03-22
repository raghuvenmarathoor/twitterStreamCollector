package twitter;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;

import db.MongoDbConnector;

public class TwitterInfoLoader {

	List<String> infoList = null;
	MongoDbConnector connector = null;
	String collectionName = null;

	public TwitterInfoLoader(List<String> infoList, MongoDbConnector connector,
			String collectionName) {
		this.infoList = infoList;
		this.connector = connector;
		this.collectionName = collectionName;
	}

	public void load() {
		for (String val : infoList) {
			List<Document> docList = connector.find(new Document("value", val),
					collectionName);
			if (docList != null && docList.size() > 0) {
				System.out.println("info already exist");
			} else {
				TwitterKeywordInfoDocument document = new TwitterKeywordInfoDocument(val);
				System.out.println(document.getDocument().toString());
				connector.insert(document.getDocument(), collectionName);
			}
		}

	}
    public static void main(String[] args) {
    	
    }
}
