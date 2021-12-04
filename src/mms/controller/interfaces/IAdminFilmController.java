package mms.controller.interfaces;

import mms.view.DetailsFilmView;

/**
 * Interface that define the {@link mms.controller.admin.AdminFilmController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IAdminFilmController {

	/**
	 * Method that manages the insertion of a film's cover, present in a specific shop.
	 * @param filmCode the code of the film
	 * @param shopCode the code of the shop
	 */
	void doAddCover(final Integer filmCode, final Integer shopCode);

	/**
	 * Method that manages the insertion of a new film.
	 * @param currShop the shop code
	 */
	void doAddMovie(final Integer currShop);

	/**
	 * Method that manages the insertion of a new trailer.
	 * @param filmCode the code of the film
	 * @param shopCode the shop code
	 */
	void doAddTrailer(final Integer filmCode, final Integer shopCode);

	/**
	 * Method that opens up the {@link DetailsFilmView} of a specific film.
	 * @param filmCode the code of the film
	 * @param shopCode the code of the shop
	 */
	void showDetailsFilm(final Integer filmCode, final Integer shopCode);

	/**
	 * Method that creates the film list.
	 * @param currShop the shop code
	 */
	void generateTable(final int currShop);

	/**
	 * Method that fills the combo box with all the shop codes of an admin.
	 */
	void doFillCmb();

	/**
	 * Method that checks if a trailer is already present or not.	
	 * @param check determinates if the trailer is already present.
	 * @param filmCode the code of the film.
	 * @param shopCode the shop code
	 */
	void returnEncoding(final boolean check, final Integer filmCode, final Integer shopCode);

}
