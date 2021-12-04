package mms.controller.interfaces;

import mms.controller.society.EditableShopController;
import mms.model.Shop;

/**
 * Interface that define {@link EditableShopController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IEditableShopController {

	/**
	 * Gets a specific shop.
	 * @return the correct shop
	 */
	Shop getEditableShop();

	/**
	 * Finds all the admins present and fills the combo box with their infos.
	 */
	void doFill();

	/**
	 * Creates a new shop, if it already exists it updates it.
	 * @param address the shop's address
	 * @param telephone the shop's telephone
	 * @param admin the shop's admin username
	 */
	void doConfirm(final String address, final String telephone, final String admin);

}
