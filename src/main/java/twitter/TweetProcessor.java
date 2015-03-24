package twitter;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;

import db.MongoDbConnector;

public class TweetProcessor {
	
	MongoDbConnector connector = null;
	RankingConfiguration configuration = new RankingConfiguration();
	
	/**
	 * @return the configuration
	 */
	public RankingConfiguration getConfiguration() {
		return configuration;
	}


	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(RankingConfiguration configuration) {
		this.configuration = configuration;
	}


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
		Long userId = document.getUserId();
		Document userSearchQuery = new Document();
		userSearchQuery.append(TwitterUser.ID_FIELD_NAME, userId);
		List<Document> documents = connector.find(userSearchQuery, TwitterUser.USER_TABLE_NAME);
		Long points = findPoints(document, configuration);
		String deviceType = findDeviceType(document.getSource()).toString();
		if (documents.size() == 0) {
			TwitterUser newUser = new TwitterUser();
			newUser.setId(userId);
			newUser.setPoints(points);
			newUser.addDeviceType(deviceType);
			Document userDocument = newUser.getDocument();
			connector.insert(userDocument, TwitterUser.USER_TABLE_NAME);
		} else {
			TwitterUser existingUser = TwitterUser.fromDocument(documents.get(0));
			List<String> deviceTypes = existingUser.getPostingMethod();
			if (deviceTypes.contains(deviceType) == false) {
				existingUser.addDeviceType(deviceType);
			}
			existingUser.setPoints(existingUser.getPoints() + points); 
			Document updateQuery = new Document().append(TwitterUser.ID_FIELD_NAME, existingUser.getId());
			System.out.println(existingUser.getDocument().toString());
			connector.update(updateQuery, existingUser.getDocument(), TwitterUser.USER_TABLE_NAME, false);
			
		}
		connector.update(document.getDocument(), new Document(Tweet.PROCESSED_STATUS_FIELD_NAME, true), Tweet.TABLE_NAME);
	}
	
	private Long findPoints(Tweet document, RankingConfiguration configuration) {
		Long points = 0l;
		points += (document.getFavouriteCount() * configuration.getPointForFavourites());
		points += (document.getRetweetCount() * configuration.getPointForRetweets());
		String sourceString = document.getSource();
		DeviceType deviceType = DeviceType.fromSource(sourceString);
		if (deviceType.toString() == DeviceType.ANDROID) {
			points += configuration.getPointForAndroidUser();
		}
		return points;
	}
	private DeviceType findDeviceType(String source) {
		return DeviceType.fromSource(source);
	}
	
	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		MongoDbConnector connector = new MongoDbConnector(client, "mydb");
		TweetProcessor processor = new TweetProcessor(connector);
		Document query = new Document(Tweet.PROCESSED_STATUS_FIELD_NAME, new Document("$exists", 0));
		List<Document> docs = connector.find(query, Tweet.TABLE_NAME);
		processor.processTweets(docs);
	}
	

}
