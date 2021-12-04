package mms.model;

import java.io.Serializable;
import java.util.Date;

/**
 * This class models a generic Person.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public abstract class AbstractPerson implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The name. */
	private String name;

	/** The surname. */
	private String surname;

	/** The birth date. */
	private Date birthDate;

	/**
	 * Set person birthday.
	 * @param newBirthDate new person's birthday
	 */
	public void setBirthDate(final Date newBirthDate) {
		this.birthDate = newBirthDate;
	}

	/**
	 * Set person's surname.
	 * @param newSurname new person's surname
	 */
	public void setSurname(final String newSurname) {
		this.surname = newSurname;
	}

	/**
	 * Set person's name.
	 * @param newName new person's name
	 */
	public void setName(final String newName) {
		this.name = newName;
	}
	/**
	 * Get person's birthday.
	 * @return Date
	 */
	public Date getBirthDate() {
		return this.birthDate;
	}

	/**
	 * Get person's surname.
	 * @return String
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * Get person's name.
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
}
