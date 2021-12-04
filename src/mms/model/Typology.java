package mms.model;

import java.util.Date;

/**
 * Create an instance of a database record which assign a discount to a card.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Typology {

	/** The num card. */
	private int numCard;

	/** The start date discount. */
	private Date startDateDiscount;

	/** The cod discount. */
	private int codDiscount;

	/**
	 * Get card number.
	 * @return int
	 */
	public int getNumCard() {
		return this.numCard;
	}

	/**
	 * Set card number.
	 * @param newNumCard new card number.
	 */
	public void setNumCard(final int newNumCard) {
		this.numCard = newNumCard;
	}

	/**
	 * Get start discount date.
	 * @return Date
	 */
	public Date getStartDateDiscount() {
		return this.startDateDiscount;
	}

	/**
	 * Set start discount date.
	 * @param newStartDateDiscount new start discount date
	 */
	public void setStartDateDiscount(final Date newStartDateDiscount) {
		this.startDateDiscount = newStartDateDiscount;
	}

	/**
	 * Get discount code.
	 * @return int
	 */
	public int getCodDiscount() {
		return this.codDiscount;
	}

	/**
	 * Set discount code.
	 * @param newCodDiscount new discount code
	 */
	public void setCodDiscount(final int newCodDiscount) {
		this.codDiscount = newCodDiscount;
	}

}
