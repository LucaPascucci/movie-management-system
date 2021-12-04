package mms.controller.interfaces;

import java.util.Calendar;

/**
 * Interface that define {@link mms.controller.EditablePersonController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IEditablePersonController {

	/**
	 * Returns a generic user, it may be an admin or a custmer.
	 * @return an admin or customer instance
	 */
	Object getEditableRecord();

	/**
	 * Method that manages and controls the registration of a new generic user.	
	 * @param name the name of the user.
	 * @param surname the surname of the user.
	 * @param username the identification code of the user.
	 * @param password the password chosen from the user. 
	 * @param birthDate birth date of the user.
	 */
	void doRegisterUser(final String name, final String surname, final String username, final String password, final Calendar birthDate);

	/**
	 * Method that manages and controls the registration of a new actor.	
	 * @param name the actor's name
	 * @param surname the actor's surname
	 * @param nazionality the actor's nationality
	 * @param birthDate the actor's birthdate
	 */
	void doRegisterActor(final String name, final String surname, final String nazionality, final Calendar birthDate);

	/**
	 * Checks if the program may register a new actor.
	 * @return boolean
	 */
	boolean checkActor();

	/**
	 * Checks if the program may register or find an admin.
	 * @return boolean
	 */
	boolean checkAdmin();

	/**
	 * Fills the combo box with all the shop codes available.
	 */
	void doFillCMB();

	/**
	 * Adds the shop's code in which a user wants to subscribe to, to a list.
	 * @param codeShop the shop code
	 */
	void subscribeAtShop(final Integer codeShop);

	/**
	 * Checks if the current user is an admin.
	 * @return boolean
	 */
	boolean isAdmin();
}
