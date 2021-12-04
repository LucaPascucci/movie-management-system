package mms.controller.interfaces;

/**
 * Interface that define the {@link mms.controller.MenuController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IMenuController {

	/**
	 * Method that returns true if the current user is a customer.
	 * @return boolean
	 */
	boolean getUserControll();

	/**
	 * Method that returns true if the current user is an admin.
	 * @return boolean
	 */
	boolean getAdminControll();

	/**
	 * Method that allows the current customer to see a determinate set of view through UserControll.
	 */
	void doFirst();

	/**
	 * Method that allows the current customer to see a determinate set of view through UserControll.	
	 */
	void doSecond();

	/**
	 * Method that allows the current customer to see a determinate set of view through UserControll.
	 */
	void doThird();
}
