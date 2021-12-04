package mms.model;

import java.util.Date;

/**
 * Create a card for the discounts.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Card {

	/** The num card. */
	private int numCard;

	/** The username. */
	private String username;

	/** The release date. */
	private Date releaseDate;

	/** The score card. */
	private int scoreCard;

	/**
	 * Get card number.
	 * @return int
	 */
	public int getNumCard() {
		return this.numCard;
	}

	/**
	 * Set card number.
	 * @param newNumCard new card number
	 */
	public void setNumCard(final int newNumCard) {
		this.numCard = newNumCard;
	}

	/**
	 * Get owner's card username.
	 * @return String
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Set owner's card username.
	 * @param newUsername new owner's card username
	 */
	public void setUsername(final String newUsername) {
		this.username = newUsername;
	}

	/**
	 * Get card release date.
	 * @return Date
	 */
	public Date getReleaseDate() {
		return this.releaseDate;
	}

	/**
	 * Set card release date.
	 * @param newReleaseDate new card release date
	 */
	public void setReleaseDate(final Date newReleaseDate) {
		this.releaseDate = newReleaseDate;
	}

	/**
	 * Get card score.
	 * @return int
	 */
	public int getScoreCard() {
		return this.scoreCard;
	}

	/**
	 * Set card score.
	 * @param newScoreCard new card score
	 */
	public void setScoreCard(final int newScoreCard) {
		this.scoreCard = newScoreCard;
	}

}
