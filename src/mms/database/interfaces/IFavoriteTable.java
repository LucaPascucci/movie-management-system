package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Favorite;
import mms.model.Film;

/**
 * Interface of favoriteTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IFavoriteTable {
	/**
	 * Find the favorite by the key.
	 * @param code code
	 * @param username username
	 * @return the favorite if it was find in the database
	 * @throws SQLException SQLException
	 */
	Favorite findByPrimaryKey(final int code, final String username) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;
	/**
	 * Find the favorites of a customer.
	 * @param customer customer
	 * @return the list of the favorites of the customer
	 * @throws SQLException SQLException
	 */
	List<Film> getFavorite(final String customer) throws SQLException;
	/**
	 * Find the favorites of a customer in a shop.
	 * @param currShop currShop
	 * @param currentUser currentUser
	 * @return the list of the favorite of the customer in a shop
	 * @throws SQLException SQLException
	 */
	List<Film> getFavoriteOfACustomerInAShop(final Integer currShop, final String currentUser) throws SQLException;

}
