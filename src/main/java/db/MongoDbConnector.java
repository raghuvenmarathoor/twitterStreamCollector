package db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

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

	/**
	 * update function, no replacement of document, will set the missing fields
	 * 
	 * @param document
	 *            - search query (A document with _id mandatory)
	 * @param updatedDocument
	 *            - document to update
	 * @param collection
	 *            - collection name
	 * @return success status
	 */
	public boolean update(Document document, Document updatedDocument,
			String collection) {
		return update(document, updatedDocument, collection, false);
	}

	/**
	 * same as update function, but with replace option
	 * 
	 * @param document
	 *            - search query (A document with _id mandatory)
	 * @param updatedDocument
	 *            - document to update
	 * @param collection
	 *            - collection name
	 * 
	 * @param replace
	 *            - replace or not boolean
	 * @return -success status
	 */
	public boolean update(Document document, Document updatedDocument,
			String collection, boolean replace) {

		if (document.containsKey("_id") == false) {
			// throw new Exception("NO id found in document to update");
			return false;
		} else {
			Document updatingDocument;
			if (replace == false) {
				updatingDocument = new Document("$set", updatedDocument);
			} else {
				updatingDocument = updatedDocument;
			}
			if (updatingDocument.containsKey("_id")) {
				updatingDocument.remove("_id");
			}
			db.getCollection(collection).updateOne(document, updatingDocument);
			return true;
		}

	}

	/**
	 * insert to mongodb
	 * 
	 * @param document
	 *            - document to insert
	 * @param collection
	 *            - collection name
	 * @return - success status
	 */
	public boolean insert(Document document, String collection) {
		if (document != null) {
			db.getCollection(collection).insertOne(document);
			return true;
		}
		return false;
	}

	/**
	 * search in mongodb
	 * 
	 * @param search
	 *            - search query document
	 * @param collection
	 *            - collectionName
	 * @return - list of document objects
	 */
	public List<Document> find(Document search, String collection) {
		if (search != null) {
			List<Document> docList = new ArrayList<Document>();
			MongoCursor<Document> cursor = db.getCollection(collection)
					.find(search).iterator();
			while (cursor.hasNext()) {
				docList.add(cursor.next());
			}
			return docList;
		}
		return null;
	}

}
