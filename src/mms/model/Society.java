package mms.model;

import java.io.Serializable;

/**
 * Create an instance of society.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Society implements IUser, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The username. */
	private final String username;

	/** The password. */
	private final String password;

	/** The name. */
	private final String name;

	/** The address. */
	private final String address;

	/** The town. */
	private final String town;

	/** The partita iva. */
	private final String partitaIVA;

	/** The telephone. */
	private final String telephone;

	/** The mail. */
	private final String mail;

	/** The web address. */
	private final String webAddress;

	/** The logo. */
	private boolean logo;
	/**
	 * Constructor of the society.
	 * @param newUsername newUsername
	 * @param newPassword newPassword
	 * @param newName newName
	 * @param newAddress newAddress
	 * @param newTown newTown
	 * @param newPartitaIVA newPartitaIVA
	 * @param newTelephone newTelephone
	 * @param newMail newMail
	 * @param newWebAddress newWebAddress
	 * @param checkLogo checkLogo
	 */
	public Society(final String newUsername, final String newPassword, final String newName, final String newAddress, final String newTown, final String newPartitaIVA, final String newTelephone, final String newMail, final String newWebAddress, final boolean checkLogo) {
		this.username = newUsername;
		this.password = newPassword;
		this.name = newName;
		this.address = newAddress;
		this.town = newTown;
		this.partitaIVA = newPartitaIVA;
		this.telephone = newTelephone;
		this.mail = newMail;
		this.webAddress = newWebAddress;
		this.logo =  checkLogo;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * Get society name.
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get society address.
	 * @return String
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Get society town.
	 * @return String
	 */
	public String getTown() {
		return this.town;
	}

	/**
	 * Get society VAT number.
	 * @return String
	 */
	public String getPIVA() {
		return this.partitaIVA;
	}

	/**
	 * Get society telephone number.
	 * @return String
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * Get society mail address.
	 * @return String
	 */
	public String getMail() {
		return this.mail;
	}

	/**
	 * Get society web address.
	 * @return String
	 */
	public String getWebAddress() {
		return this.webAddress;
	}

	/**
	 * Check if society logo exists.
	 * @return boolean
	 */
	public boolean isLogo() {
		return this.logo;
	}

	/**
	 * Set society logo.
	 * @param newLogo true if the logo exists
	 */
	public void setLogo(final boolean newLogo) {
		this.logo = newLogo;
	}
}
