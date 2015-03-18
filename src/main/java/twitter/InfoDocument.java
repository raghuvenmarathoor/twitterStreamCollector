package twitter;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

public class InfoDocument {

	public int tweetCount = 0;
	public boolean active = true;
	public String val = "";
	public Map<String, String> addInfo = new HashMap<String, String>();

	public InfoDocument(String value, boolean active, int tweetCount,
			Map<String, String> addInfo) {
		this.val = value;
		this.active = active;
		this.tweetCount = tweetCount;
		this.addInfo = addInfo;
	}

	public InfoDocument(String value) {
		this.val = value;
	}

	public InfoDocument(String value, boolean active) {
		this.val = value;
		this.active = active;
	}

	public Document getDocument() {

		Document infoDocument = new Document();
		Document addInfoDocument = new Document();
		for (String info : addInfo.keySet()) {
			addInfoDocument.append(info, addInfo.get(info));
		}
		infoDocument.append("value", val);
		infoDocument.append("tweetCount", tweetCount);
		infoDocument.append("active", String.valueOf(active));
		infoDocument.append("addInfo", addInfoDocument);
		return infoDocument;
	}
}
