package twitter;

public class TwitterUserType {

	public static final String PUBLIC_FIGURE = "PUBLIC";
	public static final String NORMAL_USER = "NORMAL";
	public static final String USER_IN_SEARCH_KEYWORDS = "SEARCH_KEYWORD_USER";
	private String userType = "NORMAL";
	public TwitterUserType(String userTypeString) throws Exception {
		// TODO Auto-generated constructor stub
		if (userTypeString.equals(NORMAL_USER)){
			userType = userTypeString;
		} else if (userTypeString.equals(PUBLIC_FIGURE)) {
			userType = userTypeString;
		} else if (userTypeString.equals(USER_IN_SEARCH_KEYWORDS)) {
			userType = userTypeString;
					
		} else {
			throw new Exception("UnIdentified UserType :" + userTypeString);
		}
	}
	public TwitterUserType() {
		
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String toString() {
		return userType;
	}
	
}
