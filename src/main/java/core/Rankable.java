package core;

import org.bson.Document;

import twitter.RankingConfiguration;

public interface Rankable {

	public Document getDocument(RankingConfiguration configuration);
}
