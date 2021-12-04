package mms.model;

/**
 * Create an instance Act with codActor and codFilm.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class Act {

	/** The cod actor. */
	private int codActor;

	/** The cod film. */
	private int codFilm;

	/**
	 * Get actor's code.
	 * @return int
	 */
	public int getCodActor() {
		return this.codActor;
	}

	/**
	 * Set new actor's code.
	 * @param newCodActor new actor's code
	 */
	public void setCodActor(final int newCodActor) {
		this.codActor = newCodActor;
	}

	/**
	 * Get film's code.
	 * @return int
	 */
	public int getCodFilm() {
		return this.codFilm;
	}

	/**
	 * Set film's code.
	 * @param newCodFilm new film's code
	 */
	public void setCodFilm(final int newCodFilm) {
		this.codFilm = newCodFilm;
	}

}
