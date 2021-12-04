package mms.model;

/**
 * Defines an user.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IUser {

	/**
	 * Returns the user's ID.
	 * @return String
	 */
	String getUsername();

	/**
	 * Returns the user's password.
	 * @return String
	 */
	String getPassword();

}