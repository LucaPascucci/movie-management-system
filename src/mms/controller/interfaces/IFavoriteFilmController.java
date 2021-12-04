package mms.controller.interfaces;

/**
 * Interface that define {@link mms.controller.customer.FavoriteFilmController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IFavoriteFilmController {

	/**
	 * Method that creates the table of the favorite films of the current customer.
	 * @param currShop the shop code
	 */
	void generateTable(final Integer currShop);

	/**
	 * Method that return the view of the info of the selected film.
	 * @param filmCode the film code.
	 * @param shopCode the shop code.
	 */
	void showDetailsFilm(final Integer filmCode, final Integer shopCode);

	/**
	 * Returns a boolean that say if the selected film is not anymore one of the favorite films of the current customer.
	 * @param filmCode the film code.
	 * @return boolean
	 */
	boolean deleteFavorite(final Integer filmCode);

	/**
	 * Method that confirms that the current user bought the selected film. 
	 * @param filmCode the film code.
	 * @param currShop the shop code.
	 */
	void doBuy(final Integer filmCode, final Integer currShop);

	/**
	 * Method that shows a preview of the info of the selected film.
	 * @param filmCode the film code.
	 * @param shopCode the shop code.
	 */
	void showPreview(final Integer filmCode, final Integer shopCode);

	/**
	 * Fills the combo box with the shop codes.
	 */
	void doFillCmb();

	/**
	 * Generates a new card.
	 */
	void getNewCard();

	/**
	 * Selects the discount.
	 */
	void selectDiscount();
}
