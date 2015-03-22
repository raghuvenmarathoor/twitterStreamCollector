package twitter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

public class SearchKeyword {

	public static final String TWEET_COUNT_FIELD_NAME = "tweet_count";
	public static final String ACTIVE_FIELD_NAME = "active";
	public static final String VALUE_FIELD_NAME = "value";
	public static final String ADDITIONAL_INFO_FIELD_NAME = "add_info";
	public static final String LAST_RUN_TIME_FIELD_NAME = "last_run_time";
	public static final String OCCURANCES_FIELD_NAME = "occurances";
	public static final String TWITTER_KEYWORD_COLLECTION_NAME = "keywords";
	public int tweetCount = 0;
	public boolean active = true;
	public String val = "";
	public Map<String, String> addInfo = new HashMap<String, String>();
	public Date lastRunTime = null;
	public int occurances = 0;

	public SearchKeyword(String value, boolean active, int tweetCount,
			Map<String, String> addInfo) {
		this.val = value;
		this.active = active;
		this.tweetCount = tweetCount;
		this.addInfo = addInfo;
	}
	public SearchKeyword(String value, boolean active, int tweetCount, Map<String, String> addInfo, Date lastRunTime) {
		this.val = value;
		this.active = active;
		this.tweetCount = tweetCount;
		this.addInfo = addInfo;
		this.lastRunTime = lastRunTime;
	}

	public SearchKeyword(String value) {
		this.val = value;
	}

	public SearchKeyword(String value, boolean active) {
		this.val = value;
		this.active = active;
	}

	public Document getDocument() {
		Document infoDocument = new Document();
		Document addInfoDocument = new Document();
		for (String info : addInfo.keySet()) {
			addInfoDocument.append(info, addInfo.get(info));
		}
		infoDocument.append(VALUE_FIELD_NAME , val);
		infoDocument.append(TWEET_COUNT_FIELD_NAME , tweetCount);
		infoDocument.append(ACTIVE_FIELD_NAME , String.valueOf(active));
		infoDocument.append(ADDITIONAL_INFO_FIELD_NAME , addInfoDocument);
		infoDocument.append(OCCURANCES_FIELD_NAME, occurances);
		if (lastRunTime != null) {
			infoDocument.append(LAST_RUN_TIME_FIELD_NAME , lastRunTime);
		}
		return infoDocument;
	}
}
