package mms.model;

/**
 * Create an instance favorite with codFilm and username.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Favorite {

	/** The cod film. */
	private int codFilm;

	/** The username. */
	private String username;

	/**
	 * Get film code.
	 * @return int
	 */
	public int getCodFilm() {
		return this.codFilm;
	}

	/**
	 * Set film code.
	 * @param newCodFilm new film code
	 */
	public void setCodFilm(final int newCodFilm) {
		this.codFilm = newCodFilm;
	}

	/**
	 * Get customer username.
	 * @return String
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Set customer username.
	 * @param newUsername new customer username
	 */
	public void setUsername(final String newUsername) {
		this.username = newUsername;
	}

}
