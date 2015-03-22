package twitter.auth;

public class TwitterAuthenticationInformation {
private String consumerKey;
private String consumerSecret;
private String accessTocken;
private String accessTockenSecret;
/**
 * @return the consumerKey
 */
public String getConsumerKey() {
	return consumerKey;
}
/**
 * @param consumerKey the consumerKey to set
 */
public void setConsumerKey(String consumerKey) {
	this.consumerKey = consumerKey;
}
/**
 * @return the consumerSecret
 */
public String getConsumerSecret() {
	return consumerSecret;
}
/**
 * @param consumerSecret the consumerSecret to set
 */
public void setConsumerSecret(String consumerSecret) {
	this.consumerSecret = consumerSecret;
}
/**
 * @return the accessTocken
 */
public String getAccessTocken() {
	return accessTocken;
}
/**
 * @param accessTocken the accessTocken to set
 */
public void setAccessTocken(String accessTocken) {
	this.accessTocken = accessTocken;
}
/**
 * @return the accessTockenSecret
 */
public String getAccessTockenSecret() {
	return accessTockenSecret;
}
/**
 * @param accessTockenSecret the accessTockenSecret to set
 */
public void setAccessTockenSecret(String accessTockenSecret) {
	this.accessTockenSecret = accessTockenSecret;
}

}
