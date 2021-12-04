package mms.controller.interfaces;

import mms.controller.society.ManageShopController;

/**
 * Interface that define the {@link ManageShopController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IManageShopController {

	/**
	 * Changes the view basing on the currentTable string.
	 * @param currentTable the string of the current table.
	 */
	void doAdd(final String currentTable);

	/**
	 * Changes the view basing on the currentTable string.
	 * @param currentTable the string of the current table.
	 * @param code the primary key.
	 */
	void doEdit(final String currentTable, final Object code);

	/**
	 * Deletes an admin, a discount or a shop basing on the currentTable string.
	 * @param currentTable the string of the current table.
	 * @param code the primary key.
	 * @return true if the record is deleted.
	 */
	boolean doDelete(final String currentTable, final Object code);

	/**
	 * Generates the correct table with the list of admins, discounts or shops, basing on the currentTable string.
	 * @param currentTable the string of the current table.
	 */
	void generateTable(final String currentTable);

}
