package mms.model;
/**
 * Interface of the model of the application.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IModel {

	/**
	 * Gets the society.
	 *
	 * @return the instance of the society with the informations
	 */
	Society getSociety();

	/**
	 * Gets the current user.
	 *
	 * @return the current user
	 */
	IUser getCurrentUser();

	/**
	 * Gets the user type.
	 *
	 * @return the type of the current user
	 */
	String getUserType();
	/**
	 * Edit the informations of the society.
	 * @param username username
	 * @param password password
	 * @param name name
	 * @param address address
	 * @param town town
	 * @param partitaIVA partitaIVA
	 * @param telephone telephone
	 * @param mail mail
	 * @param webAddress webAddress
	 */
	void editSociety(String username, String password, String name, String address, String town, String partitaIVA, String telephone, String mail, String webAddress);
	/**
	 * Insert society's logo.
	 */
	void insertSocietyLogo();
	/**
	 * Set the current user of the application.
	 * @param currentUser currentUser
	 */
	void setCurrentUser(IUser currentUser);
	/**
	 * Set the type of the current user.
	 * @param userType userType
	 */
	void setUserType(String userType);

}
