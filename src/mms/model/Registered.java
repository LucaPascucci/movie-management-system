package mms.model;

import java.util.Date;

/**
 * Create an instance of a customer registration.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Registered {

	/** The cod shop. */
	private int codShop;

	/** The username. */
	private String username;

	/** The registered date. */
	private Date registeredDate;

	/**
	 * Get shop code.
	 * @return int
	 */
	public int getCodShop() {
		return this.codShop;
	}

	/**
	 * Set shop code.
	 * @param newCodShop new shop code
	 */
	public void setCodShop(final int newCodShop) {
		this.codShop = newCodShop;
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

	/**
	 * Get customer registration date to shop.
	 * @return Date
	 */
	public Date getRegisteredDate() {
		return this.registeredDate;
	}

	/**
	 * Set customer registration date to shop.
	 * @param newRegisteredDate new customer registration date to shop
	 */
	public void setRegisteredDate(final Date newRegisteredDate) {
		this.registeredDate = newRegisteredDate;
	}

}
