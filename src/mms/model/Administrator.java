package mms.model;

/**
 * Create an administrator.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Administrator extends AbstractPerson implements IUser {

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
	 * Set new admin's username.
	 * @param newUsername new admin's username
	 */
	public void setUsername(final String newUsername) {
		this.username = newUsername;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	/**
	 * Set new admin's password.
	 * @param newPassword new admin's password
	 */
	public void setPassword(final String newPassword) {
		this.password = newPassword;
	}


}
