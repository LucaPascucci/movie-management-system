package mms.controller.interfaces;

/**
 * Interface that define the {@link mms.controller.admin.ManageCustomerController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IManageCustomerController {

	/**
	 * Method that allows to the admin to modify the parameter of the customer.
	 * @param username parameter that pass the username of the customer.
	 */
	void doEditUser(final String username);

	/**
	 * Method that allows to the admin to delete a customer selected by username.	
	 * @param username parameter that pass the username of the customer
	 * @return boolean
	 */
	boolean doDeleteUser(final String username);

	/**
	 * Method that allows to the admin to see the view of CustomerStats.
	 * @param username parameter that pass the username of the customer
	 */
	void doUserStats(final String username);

	/**
	 * Method that generates a customer list.
	 * @param currShop the shop code	
	 */
	void generateTable(final Integer currShop);

	/**
	 * Fills the combo box with the shop codes of an admin.
	 */
	void doFillCmb();

}
