package twitter;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class TwitterUser {

	public static final String ID_FIELD_NAME = "_id";
	public static final String POINTS_FIELD_NAME = "points";
	public static final String USER_TYPE_FIELD_NAME = "usertype";
	public static final String USER_DEVICE_TYPES = "userPostingMethod";
	private Long id = -1l;
	private Long points = 0l;
	private TwitterUserType userType = new TwitterUserType();
	private List<String> deviceTypes = new ArrayList<String>();
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the points
	 */
	public Long getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(Long points) {
		this.points = points;
	}
	/**
	 * @return the userType
	 */
	public TwitterUserType getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		try {
		this.userType = new TwitterUserType(userType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setUserType(TwitterUserType userType) {
		this.userType = userType;
	}
	/**
	 * @return the postingMethod
	 */
	public List<String> getPostingMethod() {
		return deviceTypes;
	}
	/**
	 * @param postingMethod the postingMethod to set
	 */
	public void setDeviceTypes(List<String> postingMethod) {
		this.deviceTypes = postingMethod;
	}
	public void addDeviceType(String source) {
		this.deviceTypes.add( DeviceType.fromSource(source).toString());
	}
	
	public boolean isAndroidUser() {
		for(String string: deviceTypes) {
			if(string.equals(DeviceType.ANDROID)) {
				return true;
			}
		}
		return false;
	}
	public Document getDocument() {
		Document document = new Document();
		document.append(ID_FIELD_NAME, getId());
		document.append(POINTS_FIELD_NAME, getPoints());
		document.append(USER_DEVICE_TYPES, getPostingMethod());
		document.append(USER_TYPE_FIELD_NAME, getUserType());
		return document;
	}
	public static TwitterUser fromDocument(Document document) {
		TwitterUser twitterUser = new TwitterUser();
		if (document.containsKey(ID_FIELD_NAME)) {
			twitterUser.setId(document.getLong(ID_FIELD_NAME));
		}
		if (document.containsKey(POINTS_FIELD_NAME)) {
			twitterUser.setPoints(document.getLong(POINTS_FIELD_NAME));
		}
		if (document.containsKey(USER_DEVICE_TYPES)) {
			twitterUser.setDeviceTypes((List<String>) document.get(USER_DEVICE_TYPES));
		}
		if (document.containsKey(USER_TYPE_FIELD_NAME)) {
			twitterUser.setUserType(document.getString(USER_TYPE_FIELD_NAME));
		}
		return twitterUser;
	}
}
