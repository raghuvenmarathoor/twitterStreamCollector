package twitter;

public class RankingConfiguration {

	private int pointForFavourites = 5;
	private int pointForRetweets = 2;
	private int pointForMentions = 1;
	private int pointForAndroidUser = 20;
	
	
	/**
	 * @return the pointForFavourites
	 */
	public int getPointForFavourites() {
		return pointForFavourites;
	}


	/**
	 * @param pointForFavourites the pointForFavourites to set
	 */
	public void setPointForFavourites(int pointForFavourites) {
		this.pointForFavourites = pointForFavourites;
	}


	/**
	 * @return the pointForRetweets
	 */
	public int getPointForRetweets() {
		return pointForRetweets;
	}


	/**
	 * @param pointForRetweets the pointForRetweets to set
	 */
	public void setPointForRetweets(int pointForRetweets) {
		this.pointForRetweets = pointForRetweets;
	}


	/**
	 * @return the pointForMentions
	 */
	public int getPointForMentions() {
		return pointForMentions;
	}


	/**
	 * @param pointForMentions the pointForMentions to set
	 */
	public void setPointForMentions(int pointForMentions) {
		this.pointForMentions = pointForMentions;
	}


	/**
	 * @return the pointForAndroidUser
	 */
	public int getPointForAndroidUser() {
		return pointForAndroidUser;
	}


	/**
	 * @param pointForAndroidUser the pointForAndroidUser to set
	 */
	public void setPointForAndroidUser(int pointForAndroidUser) {
		this.pointForAndroidUser = pointForAndroidUser;
	}


	public RankingConfiguration() {
		
	}
}
