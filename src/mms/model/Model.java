package mms.model;

import java.io.Serializable;

/**
 * The model of the application. Contains the files that aren't in the database.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Model implements IModel, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The society. */
	private Society society;

	/** The current user. */
	private IUser currentUser;

	/** The user type. */
	private String userType;

	/**
	 * The constructor of the model.
	 */
	public Model() {
		this.society = new Society("admin", "admin", new String(), new String(), new String(), new String(), new String(), new String(), new String(), false);
		this.setCurrentUser(null);
		this.setUserType(null);
	}

	@Override
	public Society getSociety() {
		return this.society;
	}

	@Override
	public void editSociety(final String username, final String password, final String name, final String address, final String town, final String partitaIVA, final String telephone, final String mail, final String webAddress) {
		this.society = new Society(username, password, name, address, town, partitaIVA, telephone, mail, webAddress, this.society.isLogo());
	}

	@Override
	public void insertSocietyLogo() {
		this.society.setLogo(true);
	}

	@Override
	public IUser getCurrentUser() {
		return this.currentUser;
	}

	@Override
	public String getUserType() {
		return this.userType;
	}

	@Override
	public void setCurrentUser(final IUser newCurrentUser) {
		this.currentUser = newCurrentUser;
	}

	@Override
	public void setUserType(final String newUserType) {
		this.userType = newUserType;
	}

}
