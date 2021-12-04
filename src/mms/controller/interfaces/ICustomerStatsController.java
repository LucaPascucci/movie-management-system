package mms.controller.interfaces;

import javax.swing.ImageIcon;

import mms.model.Customer;
import mms.view.customer.EditableCardView;

/**
 * Interface that define the {@link mms.controller.customer.CustomerStatsController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface ICustomerStatsController {

	/**
	 * Gets the correct customer.
	 * @return the customer
	 */
	Customer getCustomer();

	/**
	 * This method return the details of the film selected by the variable idFilm.
	 * @param filmCode the film code.
	 * @param shopCode the shop code
	 */
	void showDetailsFilm(final Integer filmCode, final Integer shopCode);

	/**
	 * Method that allows to the current user to edit the info of the customer.
	 */
	void doEditUser();

	/**
	 * Method that allows to the current customer to delete the film selected.
	 * @param filmCode the film code
	 * @param shopCode the shop code
	 * @return boolean
	 */
	boolean doDelete(final Integer filmCode, final Integer shopCode);

	/**
	 * Method that returns the ImageIcon.
	 * @return ImageIcon
	 */
	ImageIcon getProfileImage();

	/**
	 * Method that allows to the current user to change the ProfileImage.
	 * @return boolean
	 */
	boolean changeProfileImage();

	/**
	 * Method that generate the list of film bought by the current customer.
	 * @param currShop the current shop
	 */
	void generateTable(final Integer currShop);

	/**
	 * Method that check if the current user is the admin or a customer. 
	 * @return boolean
	 */
	boolean checkCustomer();

	/**
	 * Fills the combo box with the shop codes.
	 */
	void doFillCmb();

	/**
	 * Method that creates a new card.
	 */
	void getNewCard();

	/**
	 * Method that opens up the {@link EditableCardView}.
	 */
	void selectDiscount();

	/**
	 * Method that updates the label representing the money spent by a customer in a shop.
	 *
	 * @param currShop the code shop
	 */
	void getTotPurchase(final Integer currShop);

}
