package mms.controller.interfaces;

import mms.controller.admin.ManageCopyFilmController;

/**
 * Interface that define the {@link ManageCopyFilmController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IManageCopyFilmController {

	/**
	 * Generates the tables of the film present in the society and in the shop.
	 * @param society society
	 */
	void generateTables(boolean society);

	/**
	 * Adds the film code to the previous list or the new one.
	 * @param data the film code
	 * @param control control
	 */
	void fillCopyFilmList(Integer data, boolean control);

	/**
	 * Adds or removes the film copy in the shop.
	 */
	void doManageCopyFilm();


}
