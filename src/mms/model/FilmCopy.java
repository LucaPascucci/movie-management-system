package mms.model;

import java.util.Date;

/**
 * Create a copy of a film in a shop.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FilmCopy {

	/** The cod shop. */
	private int codShop;

	/** The cod film. */
	private int codFilm;

	/** The insertion date. */
	private Date insertionDate;

	/** The num purchases. */
	private int numPurchases;

	/** The num views. */
	private int numViews;

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
	 * Get film insertion date in the shop.
	 * @return Date
	 */
	public Date getInsertionDate() {
		return this.insertionDate;
	}

	/**
	 * Set film insertion date in the shop.
	 * @param newInsertionDate new film insertion date in the shop
	 */
	public void setInsertionDate(final Date newInsertionDate) {
		this.insertionDate = newInsertionDate;
	}

	/**
	 * Get number of purchases of the film.
	 * @return int
	 */
	public int getNumPurchases() {
		return this.numPurchases;
	}

	/**
	 * Set number of purchases of the film.
	 * @param newNumPurchases new number of purchases of the film
	 */
	public void setNumPurchases(final int newNumPurchases) {
		this.numPurchases = newNumPurchases;
	}

	/**
	 * Get number of views of the film.
	 * @return int
	 */
	public int getNumViews() {
		return this.numViews;
	}

	/**
	 * Set number of views of the film.
	 * @param newNumViews new number of views of the film
	 */
	public void setNumViews(final int newNumViews) {
		this.numViews = newNumViews;
	}

}
