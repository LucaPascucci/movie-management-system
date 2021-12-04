package mms.controller.interfaces;

/**
 * Interface that define the {@link mms.controller.LoginController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface ILoginController {

	/**
	 * Method that manage the login by the insert of an userID and a password.
	 * @param username this parameter pass the username.
	 * @param password this parameter pass the password chosen by the user.
	 * @param admin admin
	 * @param user user
	 */
	void doLogin(final String username, final String password, final boolean admin, final boolean user);

	/**
	 * Method that allows the registration of a new customer.
	 */
	void doNewUser();

	/**
	 * Method that creates folders used to save data.
	 */
	void createWorkspace();
}
