package mms.model;

/**
 * Create a film's review.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Review {

	/** The username. */
	private String username;

	/** The cod film. */
	private int codFilm;

	/** The header. */
	private String header;

	/** The text. */
	private String text;

	/** The evaluation. */
	private int evaluation;

	/**
	 * Get customer username that write review.
	 * @return String
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Set customer username that write review.
	 * @param newUsername new customer username that write review
	 */
	public void setUsername(final String newUsername) {
		this.username = newUsername;
	}

	/**
	 * Get film code of the review.
	 * @return int
	 */
	public int getCodFilm() {
		return this.codFilm;
	}

	/**
	 * Set film code of the review.
	 * @param newCodFilm new film code of the review.
	 */
	public void setCodFilm(final int newCodFilm) {
		this.codFilm = newCodFilm;
	}

	/**
	 * Get review header.
	 * @return String
	 */
	public String getHeader() {
		return this.header;
	}

	/**
	 * Set review header.
	 * @param newHeader new review header
	 */
	public void setHeader(final String newHeader) {
		this.header = newHeader;
	}

	/**
	 * Get review text.
	 * @return String
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Set review text.
	 * @param newText new review text
	 */
	public void setText(final String newText) {
		this.text = newText;
	}

	/**
	 * Get review evaluation.
	 * @return int
	 */
	public int getEvaluation() {
		return this.evaluation;
	}

	/**
	 * Set review evaluation.
	 * @param newEvaluation new review evaluation
	 */
	public void setEvaluation(final int newEvaluation) {
		this.evaluation = newEvaluation;
	}
}
