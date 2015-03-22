package twitter.auth;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class TwitterAuthenticationInformationCollection {
	private String name;
	private boolean debugEnabled;
	private List<TwitterAuthenticationInformation> infoCollection = new ArrayList<TwitterAuthenticationInformation>();
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the debugEnabled
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * @param debugEnabled the debugEnabled to set
	 */
	public void setDebugEnabled(boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the infoCollection
	 */
	public List<TwitterAuthenticationInformation> getInfoCollection() {
		return infoCollection;
	}
	/**
	 * @param infoCollection the infoCollection to set
	 */
	public void setInfoCollection(
			List<TwitterAuthenticationInformation> infoCollection) {
		this.infoCollection = infoCollection;
	}
	public void addToCollection(TwitterAuthenticationInformation info) {
		this.infoCollection.add(info);
	}
	public static XStream getXstream() {
		XStream xstream = new XStream();
		xstream.alias("twitterAccount", TwitterAuthenticationInformation.class);
		xstream.alias("twitterAccountCollection", TwitterAuthenticationInformationCollection.class);
		return xstream;
	}
	public static TwitterAuthenticationInformationCollection fromFile(File file) {
		XStream xstream = getXstream();
		TwitterAuthenticationInformationCollection collection = (TwitterAuthenticationInformationCollection) xstream.fromXML(file);
		return collection;
	}

}
