package org.audiolord.model;


/**
 * Artist
 * @author Jacek Furmankiewicz
 */
public class Artist {
    private int id;
    private String name;
    private String uniqueKey;
    private String searchKey;
    private boolean variousArtists = false;
    private int rating = 0;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the uniqueKey
	 */
	public String getUniqueKey() {
		return uniqueKey;
	}
	/**
	 * @param uniqueKey the uniqueKey to set
	 */
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}
	/**
	 * @param searchKey the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	/**
	 * @return the variousArtists
	 */
	public boolean isVariousArtists() {
		return variousArtists;
	}
	/**
	 * @param variousArtists the variousArtists to set
	 */
	public void setVariousArtists(boolean variousArtists) {
		this.variousArtists = variousArtists;
	}
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
}
