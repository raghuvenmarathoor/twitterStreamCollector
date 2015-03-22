package twitter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

public class Tweet {
	public static final String TWEET_FIELD_NAME = "tweet";
	public static final String USER_ID_FIELD_NAME = "userId";
	public static final String CONTRIBUTORS_FIELD_NAME = "contributors";
	public static final String HASHTAGS_FIELD_NAME = "hashtags";
	public static final String CREATED_AT_FIELD_NAME = "createdAt";
	public static final String FAVOURITE_COUNT_FIELD_NAME = "favouriteCount";
	public static final String ID_FIELD_NAME_FIELD_NAME = "id";
	public static final String IN_REPLY_TO_USER_ID = "inreplyToUserId";
	public static final String RETWEET_COUNT_FIELD_NAME = "retweetCount";
	public static final String USER_MENTIONS_FIELD_NAME = "userMentions";
	public static final String SOURCE_FIELD_NAME = "source";
	public static final String PROCESSED_STATUS_FIELD_NAME = "processed";
	private String tweet = "";
	private Long userId = -1l;
	private List<Long> contributors = new ArrayList<Long>();
	private List<String> hashtags = new ArrayList<String>();
	private Date createdAt = null;
	private Integer favouriteCount = 0;
	private Long id = -1l;
	private Long inReplyToUserId = -1l;
	private Integer retweetCount = 0;
	private List<Long> userMentions = new ArrayList<Long>();
	private String source = "";
	private boolean processedStatus = false;

	public Tweet() {
		
	}
	public Tweet(String tweet, Long userId, List<Long> contributors,
			List<String> hashtags, Date createdAt, Integer favouriteCount,
			Long id, Long inReplyToUserId, Integer retweetCount,
			List<Long> userMentions, String source, boolean processedStatus) {
		super();
		this.tweet = tweet;
		this.userId = userId;
		this.contributors = contributors;
		this.hashtags = hashtags;
		this.createdAt = createdAt;
		this.favouriteCount = favouriteCount;
		this.id = id;
		this.inReplyToUserId = inReplyToUserId;
		this.retweetCount = retweetCount;
		this.userMentions = userMentions;
		this.source = source;
		this.processedStatus = processedStatus;
	}

	/**
	 * @return the tweet
	 */
	public String getTweet() {
		return tweet;
	}

	/**
	 * @param tweet
	 *            the tweet to set
	 */
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the contributors
	 */
	public List<Long> getContributors() {
		return contributors;
	}

	/**
	 * @param contributors
	 *            the contributors to set
	 */
	public void setContributors(List<Long> contributors) {
		this.contributors = contributors;
	}

	/**
	 * @return the hashtags
	 */
	public List<String> getHashtags() {
		return hashtags;
	}

	/**
	 * @param hashtags
	 *            the hashtags to set
	 */
	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the favouriteCount
	 */
	public Integer getFavouriteCount() {
		return favouriteCount;
	}

	/**
	 * @param favouriteCount
	 *            the favouriteCount to set
	 */
	public void setFavouriteCount(Integer favouriteCount) {
		this.favouriteCount = favouriteCount;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the inReplyToUserId
	 */
	public Long getInReplyToUserId() {
		return inReplyToUserId;
	}

	/**
	 * @param inReplyToUserId
	 *            the inReplyToUserId to set
	 */
	public void setInReplyToUserId(Long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	/**
	 * @return the retweetCount
	 */
	public Integer getRetweetCount() {
		return retweetCount;
	}

	/**
	 * @param retweetCount
	 *            the retweetCount to set
	 */
	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	/**
	 * @return the userMentions
	 */
	public List<Long> getUserMentions() {
		return userMentions;
	}

	/**
	 * @param userMentions
	 *            the userMentions to set
	 */
	public void setUserMentions(List<Long> userMentions) {
		this.userMentions = userMentions;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the processedStatus
	 */
	public boolean isProcessedStatus() {
		return processedStatus;
	}

	/**
	 * @param processedStatus
	 *            the processedStatus to set
	 */
	public void setProcessedStatus(boolean processedStatus) {
		this.processedStatus = processedStatus;
	}
    public static Tweet fromDocument(Document document) {
    	Tweet tweetDocument = new Tweet();
    	Set<String> keySet = document.keySet();
    	if (keySet.contains(CONTRIBUTORS_FIELD_NAME)) {
    		List<Long> contributors = (List<Long>) document.get(CONTRIBUTORS_FIELD_NAME);
    		tweetDocument.setContributors(contributors);
    	}
    	if (keySet.contains(CREATED_AT_FIELD_NAME)) {
    		Date date = document.getDate(CREATED_AT_FIELD_NAME);
    		tweetDocument.setCreatedAt(date);
    	}
    	if (keySet.contains(FAVOURITE_COUNT_FIELD_NAME)) {
    		tweetDocument.setFavouriteCount(document.getInteger(FAVOURITE_COUNT_FIELD_NAME));
    	}
    	if (keySet.contains(HASHTAGS_FIELD_NAME)) {
    		tweetDocument.setHashtags((List<String>) document.get(HASHTAGS_FIELD_NAME));
    	}
    	if (keySet.contains(ID_FIELD_NAME_FIELD_NAME)) {
    		tweetDocument.setId(document.getLong(ID_FIELD_NAME_FIELD_NAME));
    	}
    	if (keySet.contains(IN_REPLY_TO_USER_ID)) {
    		tweetDocument.setInReplyToUserId(document.getLong(IN_REPLY_TO_USER_ID));
    	}
    	if (keySet.contains(PROCESSED_STATUS_FIELD_NAME)) {
    		tweetDocument.setProcessedStatus(document.getBoolean(PROCESSED_STATUS_FIELD_NAME));
    	}
    	if (keySet.contains(RETWEET_COUNT_FIELD_NAME)) {
    		tweetDocument.setRetweetCount(document.getInteger(RETWEET_COUNT_FIELD_NAME));
    	}
    	if (keySet.contains(SOURCE_FIELD_NAME)) {
    		tweetDocument.setSource(document.getString(SOURCE_FIELD_NAME));
    	}
    	if (keySet.contains(TWEET_FIELD_NAME)) {
    		tweetDocument.setTweet(document.getString(TWEET_FIELD_NAME));
    	}
    	if (keySet.contains(USER_ID_FIELD_NAME)) {
    		tweetDocument.setUserId(document.getLong(USER_ID_FIELD_NAME));
    	}
    	if (keySet.contains(USER_MENTIONS_FIELD_NAME)) {
    		tweetDocument.setUserMentions((List<Long>) document.get(USER_MENTIONS_FIELD_NAME));
    	}
    	return tweetDocument;
    }
    public Document getDocument() {
    	Document document = new Document();
    	//TODO
    	if (createdAt != null) {
    		document.append(CREATED_AT_FIELD_NAME, getCreatedAt());
    	}
    	document.append(CONTRIBUTORS_FIELD_NAME, getContributors());
    	document.append(HASHTAGS_FIELD_NAME, getHashtags());
    	document.append(FAVOURITE_COUNT_FIELD_NAME, getFavouriteCount());
    	document.append(ID_FIELD_NAME_FIELD_NAME, getId());
    	document.append(IN_REPLY_TO_USER_ID, getInReplyToUserId());
    	document.append(PROCESSED_STATUS_FIELD_NAME, isProcessedStatus());
    	document.append(RETWEET_COUNT_FIELD_NAME, getRetweetCount());
    	document.append(SOURCE_FIELD_NAME, getSource());
    	document.append(USER_ID_FIELD_NAME, getUserId());
    	document.append(TWEET_FIELD_NAME, getTweet());
    	document.append(USER_MENTIONS_FIELD_NAME, getUserMentions());
    	return document;
    }
    public static Tweet fromStatus(Status status) {
    	Document tweetDoc = new Document();
		tweetDoc.append(TWEET_FIELD_NAME, status.getText());
		GeoLocation location =  status.getGeoLocation();
		tweetDoc.append(USER_ID_FIELD_NAME , status.getUser().getId());
		long[] contributors =  status.getContributors();
		List<Long> contributorsList = new ArrayList<Long>();
		for (long val : contributors) {
			contributorsList.add(Long.valueOf(val));		
		}
		if (contributors != null)
		tweetDoc.put(CONTRIBUTORS_FIELD_NAME , contributorsList);
		Date createdAt = status.getCreatedAt();
		if (createdAt != null) {
			tweetDoc.put(CREATED_AT_FIELD_NAME , createdAt);
		}
		int favCount = status.getFavoriteCount();
		tweetDoc.put(FAVOURITE_COUNT_FIELD_NAME , Integer.valueOf(favCount));
		HashtagEntity[] hashtags = status.getHashtagEntities();
		List<String> hashTagStringList = new ArrayList<String>();
		for(HashtagEntity entity: hashtags) {
			hashTagStringList.add(entity.getText());
		}
		tweetDoc.put(HASHTAGS_FIELD_NAME , hashTagStringList);
		long id = status.getId();
		tweetDoc.put(ID_FIELD_NAME_FIELD_NAME, id);
		long replyToId = status.getInReplyToUserId();
		tweetDoc.put(IN_REPLY_TO_USER_ID , replyToId);
		tweetDoc.put(RETWEET_COUNT_FIELD_NAME , status.getRetweetCount());
		UserMentionEntity[] userMentionEntities = status.getUserMentionEntities();
		List<Long> usermentionsIds = new ArrayList<Long>();
		for (UserMentionEntity entity: userMentionEntities) {
			usermentionsIds.add(entity.getId());
		}
		tweetDoc.put(USER_MENTIONS_FIELD_NAME, usermentionsIds);
		if (status.getSource()!= null) {
			tweetDoc.put(SOURCE_FIELD_NAME , status.getSource());
		}
		return Tweet.fromDocument(tweetDoc);
    }
}
