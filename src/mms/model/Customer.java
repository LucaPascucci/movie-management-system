package mms.model;

/**
 * Create a customer.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Customer extends AbstractPerson implements IUser {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	@Override
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

	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * Set customer password.
	 * @param newPassword new customer password
	 */
	public void setPassword(final String newPassword) {
		this.password = newPassword;
	}

}
