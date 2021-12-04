package mms.model;

/**
 * Create an evaluation for the reviews.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Evaluation {

	/** The rel cod movie. */
	private int relCodMovie;

	/** The rel username. */
	private String relUsername;

	/** The username. */
	private String username;

	/** The like or dislike. */
	private char likeOrDislike;

	/**
	 * Get film code.
	 * @return int
	 */
	public int getRelCodMovie() {
		return this.relCodMovie;
	}

	/**
	 * Set film code.
	 * @param newRelCodMovie new film code
	 */
	public void setRelCodFilm(final int newRelCodMovie) {
		this.relCodMovie = newRelCodMovie;
	}

	/**
	 * Get customer username that wrote review.
	 * @return String
	 */
	public String getRelUsername() {
		return this.relUsername;
	}

	/**
	 * Set customer username that wrote review.
	 * @param newRelUsername new customer username
	 */
	public void setRelUsername(final String newRelUsername) {
		this.relUsername = newRelUsername;
	}

	/**
	 * Get customer username that evaluates review.
	 * @return String
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Set customer username that evaluates review.
	 * @param newUsername new customer username that evaluates review
	 */
	public void setUsername(final String newUsername) {
		this.username = newUsername;
	}

	/**
	 * Get like or dislike evaluation.
	 * @return char Y = like, N = dislike
	 */
	public char getLikeOrDislike() {
		return this.likeOrDislike;
	}

	/**
	 * Set like or dislike evaluation.
	 * @param newLikeOrDislike new like or dislike evaluation (Y = like, N = dislike)
	 */
	public void setLikeOrDislike(final char newLikeOrDislike) {
		this.likeOrDislike = newLikeOrDislike;
	}

}
