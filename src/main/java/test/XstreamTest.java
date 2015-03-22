package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import twitter.auth.TwitterAuthenticationInformation;
import twitter.auth.TwitterAuthenticationInformationCollection;

public class XstreamTest {
public static void main(String[] args) {
	TwitterAuthenticationInformation authentication = new TwitterAuthenticationInformation();
	authentication.setAccessTocken("abc");
	authentication.setAccessTockenSecret("cde");
	authentication.setConsumerKey("ghi");
	authentication.setConsumerSecret("jkl");
	XStream xstream = new XStream();
	xstream.alias("twitterAccount", TwitterAuthenticationInformation.class);
	xstream.alias("twitterAccountCollection", TwitterAuthenticationInformationCollection.class);
	File authFile = new File("twitter-auth.xml");
	try {
		FileReader reader = new FileReader(authFile);
		reader.read();
		TwitterAuthenticationInformationCollection collection = (TwitterAuthenticationInformationCollection) xstream.fromXML(authFile);
		System.out.println(xstream.toXML(collection));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	TwitterAuthenticationInformationCollection collection = new TwitterAuthenticationInformationCollection();
	collection.setName("twitterauthcollection");
	collection.addToCollection(authentication);
	xstream.alias("info", TwitterAuthenticationInformation.class);
	xstream.alias("twitterCollection", TwitterAuthenticationInformationCollection.class);
	collection.addToCollection(authentication);
	//System.out.println(xstream.toXML(collection));
	
}
}
