package db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnector {
	MongoClient client = null;
	String dbName = null;
	MongoDatabase db = null;
	public MongoDbConnector(MongoClient client, String dbName) {
		this.client = client;
		this.dbName = dbName;
		this.db = client.getDatabase(dbName);
		
	}
	public boolean update(Document document, Document updatedDocument , String collection) {
		if (document.containsKey("_id") == false) {
			//throw new Exception("NO id found in document to update");
			return false;
		}else {
			db.getCollection(collection).updateOne(document, updatedDocument);
			return true;
		}
	}
	public boolean insert(Document document, String collection) {
		if (document != null) {
			db.getCollection(collection).insertOne(document);
			return true;
		}
		return false;
	}
	public List<Document> find(Document search, String collection) {
		if (search != null) {
		List<Document> docList = new ArrayList<Document>();	
		MongoCursor<Document> cursor= db.getCollection(collection).find(search).iterator();
		while(cursor.hasNext()) {
			docList.add(cursor.next());
			
		}
		return docList;
		}
		return null;
	}

}
