package mms.controller.interfaces;

/**
 * Interface that define the {@link mms.controller.customer.FilmListController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IFilmListController {

	/**
	 * This method returns the details of the film inserted.
	 * @param filmCode the film code.
	 * @param shopCode the shop code.
	 */
	void showDetailsFilm(final Integer filmCode, final Integer shopCode);

	/**
	 * This method changes the list of films with a new list ordered by number of views and filtered by the variable filter.
	 * @param currShop the shop code.
	 * @param filter this parameter pass all the filter chosen.
	 */
	void generateTableTopVisited(final Integer currShop, final Object[] filter);

	/**
	 * This method generates a film list ordered by the insertion date and filtered by the variable filter.
	 * @param currShop the shop code.
	 * @param filter this parameter pass all the filter chosen.
	 */
	void generateTableRecent(final Integer currShop, final Object[] filter);

	/**
	 * This method generates a film list filtered by the variable filter.
	 * @param currShop the shop code.
	 * @param filter this parameter pass all the filter chosen.
	 */
	void generateTableFilmList(final Integer currShop, final Object[] filter);

	/**
	 * This method changes the list of films with a new list ordered by the number of purchases and filtered by the variable filter.
	 * @param currShop the shop code.
	 * @param filter this parameter pass all the filter chosen.
	 */ 
	void generateTableTopBought(final Integer currShop, final Object[] filter);

	/**
	 * Fills the combo box with all the shop codes which the customer is subscribed to.
	 */
	void doFillCmb();

	/**
	 * If the customer has not already a card, it creates a new one.
	 */
	void getNewCard();

	/**
	 * If the customer has a card, opens up the editable card view.
	 */
	void selectDiscount();

}
