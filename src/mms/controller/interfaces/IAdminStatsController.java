package mms.controller.interfaces;

/**
 * Interface that define the {@link mms.controller.admin.AdminStatsController}.  
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IAdminStatsController {

	/**
	 * This method manages the creations of the list of the film contained in the application.
	 * @param currShop the shop code
	 */
	void generateTableFilmList(final Integer currShop);

	/**
	 * This method changes the list of films with a new list ordered by number of views.
	 * @param currShop the shop code
	 */
	void generateTableTopVisited(final Integer currShop);

	/**
	 * This method changes the list of films with a new list ordered by number of purchases.
	 * @param currShop the shop code
	 */
	void generateTableTopBought(final Integer currShop);

	/**
	 * This method returns the details of the film insert by the variable filmCode.
	 * @param filmCode the film code.
	 * @param shopCode the shop code
	 */
	void showDetailsFilm(final Integer filmCode, final Integer shopCode);

	/**
	 * This method allows to change the password.
	 */
	void doChangePassword();

	/**
	 * This method fills the combo box with the shop codes of an admin.
	 */
	void doFillCmb();

	/**
	 * This method updates the label representing how many customers are subscribed to a shop.
	 * @param currShop the shop code
	 */
	void setTotalCustomer(final Integer currShop);

	/**
	 * This method updates the label representing how many money a shop earned.
	 * @param currShop the shop code
	 */
	void setTotalIncomes(final Integer currShop);

	/**
	 * This method generates a film list ordered by the insertion date.
	 * @param currShop the shop code
	 */
	void generateTableRecent(final Integer currShop);

}
