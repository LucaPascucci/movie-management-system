package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Registered;

/**
 * Interface of registeredTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IRegisteredTable {
	/**
	 * Find the instance of registered in the database by the key.
	 * @param code code
	 * @param username username
	 * @return the registration if it was find in the database
	 * @throws SQLException SQLException
	 */
	Registered findByPrimaryKey(final int code, final String username) throws SQLException;
	/**
	 * Find the shop in which the customer was subscribed by the username.
	 * @param username username
	 * @return the list of shops
	 * @throws SQLException SQLException
	 */
	List<Integer> findShopByUsername(final String username) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;
}
