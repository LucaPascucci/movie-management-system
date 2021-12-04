package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Film;
import mms.model.Purchase;

/**
 * Interface of purchaseTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IPurchaseTable {
	/**
	 * Find by the key.
	 * @param codMoviePurch codMoviePurch
	 * @param codShopPurch codShopPurch
	 * @param username username
	 * @return the value if it was find in the database
	 * @throws SQLException SQLException
	 */
	Purchase findByPrimaryKey(final int codMoviePurch, final int codShopPurch, final String username) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;
	/**
	 * Get the films purchased by the customer.
	 * @param currentUser currentUser
	 * @return the list of films
	 * @throws SQLException SQLException
	 */
	List<Film> getPurchased(final String currentUser) throws SQLException;
	/**
	 * Get the films purchased by the customer in a shop.
	 * @param currShop currShop
	 * @param currentUser currentUser
	 * @return the list of films
	 * @throws SQLException SQLException
	 */
	List<Film> getPurchasedOfACustomerInAShop(final Integer currShop, final String currentUser) throws SQLException;
	/**
	 * Get purchased by the customer in an administrator shop.
	 * @param currentUser currentUser
	 * @param customerID customerID
	 * @return the list of the films
	 * @throws SQLException SQLException
	 */
	List<Film> getPurchasedInAdminShop(final String currentUser, final String customerID) throws SQLException;
	/**
	 * Get total incomes of an administrator.
	 * @param username username
	 * @return incomes
	 * @throws SQLException SQLException
	 */
	float getTotalIncomesOfAnAdmin(final String username) throws SQLException;
	/**
	 * Get total incomes of a shop.
	 * @param currShop currShop
	 * @return incomes
	 * @throws SQLException SQLException
	 */
	float getTotalIncomesOfAShop(final Integer currShop) throws SQLException;
	/**
	 * Get total purchased of a customer in a shop.
	 * @param currShop currShop
	 * @param currentUser currentUser
	 * @return purchase purchase
	 * @throws SQLException SQLException
	 */
	float getPurchasedInAShop(final Integer currShop, final String currentUser) throws SQLException;
	/**
	 * Get the purchased of a customer in an administrator shop.
	 * @param currShop currShop
	 * @param customerID customerID
	 * @param admin admin
	 * @return purchase
	 * @throws SQLException SQLException
	 */
	float getPurchasedInAShopOfTheAdmin(final Integer currShop, final String customerID, final String admin) throws SQLException;	
}