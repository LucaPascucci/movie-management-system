package mms.model;

/**
 * Create an actor.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Actor extends AbstractPerson {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cod actor. */
	private int codActor;

	/** The nationality. */
	private String nationality;

	/**
	 * Get actor's code.
	 * @return int
	 */
	public int getCodActor() {
		return this.codActor;
	}

	/**
	 * Set actor's code.
	 * @param newCodActor new actor's code
	 */
	public void setCodActor(final int newCodActor) {
		this.codActor = newCodActor;
	}

	/**
	 * Get actor's nationality.
	 * @return String
	 */
	public String getNationality() {
		return this.nationality;
	}

	/**
	 * Set actor's nationality.
	 * @param newNationality new actor's nationality
	 */
	public void setNationality(final String newNationality) {
		this.nationality = newNationality;
	}
}
