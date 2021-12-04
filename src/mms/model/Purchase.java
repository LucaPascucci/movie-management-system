package mms.model;

import java.util.Date;
/**
 * Create an instance of a customer's purchase.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Purchase {

	/** The username. */
	private String username;

	/** The cod movie purch. */
	private int codMoviePurch;

	/** The cod shop purch. */
	private int codShopPurch;

	/** The cod transaction. */
	private int codTransaction;

	/** The date purchase. */
	private Date datePurchase;

	/** The price. */
	private float price;

	/**
	 * Get customer username.
	 * @return String
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Set customer username.
	 * @param newUsername new customer username.
	 */
	public void setUsername(final String newUsername) {
		this.username = newUsername;
	}

	/**
	 * Get film code purchased.
	 * @return int
	 */
	public int getCodMoviePurch() {
		return this.codMoviePurch;
	}

	/**
	 * Set film code purchased.
	 * @param newCodMoviePurch new film code purchased
	 */
	public void setCodMoviePurch(final int newCodMoviePurch) {
		this.codMoviePurch = newCodMoviePurch;
	}

	/**
	 * Get shop code of the purchased film.
	 * @return int
	 */
	public int getCodShopPurch() {
		return this.codShopPurch;
	}

	/**
	 * Set shop code of the purchased film.
	 * @param newCodShopPurch new shop code of the purchased film
	 */
	public void setCodShopPurch(final int newCodShopPurch) {
		this.codShopPurch = newCodShopPurch;
	}

	/**
	 * Get transition code.
	 * @return int
	 */
	public int getCodTransaction() {
		return this.codTransaction;
	}

	/**
	 * Set transition code.
	 * @param newCodTransaction new transition code
	 */
	public void setCodTransaction(final int newCodTransaction) {
		this.codTransaction = newCodTransaction;
	}

	/**
	 * Get purchase date.
	 * @return Date
	 */
	public Date getDatePurchase() {
		return this.datePurchase;
	}

	/**
	 * Set purchase date.
	 * @param newDatePurchase new purchase date
	 */
	public void setDatePurchase(final Date newDatePurchase) {
		this.datePurchase = newDatePurchase;
	}

	/**
	 * Get purchase price.
	 * @return float
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * Set  purchase price.
	 * @param newPrice new purchase price
	 */
	public void setPrice(final float newPrice) {
		this.price = newPrice;
	}
}
