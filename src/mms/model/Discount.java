package mms.model;

/**
 * Create a discount.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Discount {

	/** The discount code. */
	private int discountCode;

	/** The percentage. */
	private int percentage;

	/** The period. */
	private int period;

	/** The discount score. */
	private int discountScore;

	/**
	 * Get discount code.
	 * @return int
	 */
	public int getDiscountCode() {
		return this.discountCode;
	}

	/**
	 * Set discount code.
	 * @param newDiscountCode new discount code
	 */
	public void setDiscountCode(final int newDiscountCode) {
		this.discountCode = newDiscountCode;
	}

	/**
	 * Get discount percentage.
	 * @return int
	 */
	public int getPercentage() {
		return this.percentage;
	}

	/**
	 * Set discount percentage.
	 * @param newPercentage new discount percentage.
	 */
	public void setPercentage(final int newPercentage) {
		this.percentage = newPercentage;
	}

	/**
	 * Get discount validity period (days).
	 * @return int
	 */
	public int getPeriod() {
		return this.period;
	}

	/**
	 * Set discount validity period (days).
	 * @param newPeriod new discount validity period (days)
	 */
	public void setPeriod(final int newPeriod) {
		this.period = newPeriod;
	}

	/**
	 * Get discount score.
	 * @return int
	 */
	public int getDiscountScore() {
		return this.discountScore;
	}

	/**
	 * Set discount score.
	 * @param newDiscountScore new discount score
	 */
	public void setDiscountScore(final int newDiscountScore) {
		this.discountScore = newDiscountScore;
	}

}
