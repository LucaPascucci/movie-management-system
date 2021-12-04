package mms.model;

/**
 * Create a shop.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Shop {

	/** The code shop. */
	private int codeShop;

	/** The address. */
	private String address;

	/** The telephone. */
	private String telephone;

	/** The username admin. */
	private String usernameAdmin;

	/**
	 * Get shop code.
	 * @return int
	 */
	public int getCodeShop() {
		return this.codeShop;
	}

	/**
	 * Set shop code.
	 * @param newCodeShop new shop code
	 */
	public void setCodeShop(final int newCodeShop) {
		this.codeShop = newCodeShop;
	}

	/**
	 * Get shop address.
	 * @return String
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set shop address.
	 * @param newAddress new shop address
	 */
	public void setAddress(final String newAddress) {
		this.address = newAddress;
	}

	/**
	 * Get shop telephone.
	 * @return String
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * Set shop telephone number.
	 * @param newTelephone new shop telephone number.
	 */
	public void setTelephone(final String newTelephone) {
		this.telephone = newTelephone;
	}

	/**
	 * Get administrator username of the shop.
	 * @return String
	 */
	public String getUsernameAdmin() {
		return this.usernameAdmin;
	}

	/**
	 * Set administrator username of the shop.
	 * @param newUsernameAdmin new administrator username of the shop
	 */
	public void setUsernameAdmin(final String newUsernameAdmin) {
		this.usernameAdmin = newUsernameAdmin;
	}

}
