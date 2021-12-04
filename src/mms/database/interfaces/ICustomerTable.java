package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Customer;

/**
 * Interface of the customerTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface ICustomerTable {
	/**
	 * Find the customer by the username.
	 * @param username username
	 * @return the customer if it was find in the database
	 * @throws SQLException SQLException
	 */
	Customer findByPrimaryKey(final String username) throws SQLException;
	/**
	 * Find the customer subscribed to a shop.
	 * @param codeShop codeShop
	 * @return the list of the customer
	 * @throws SQLException SQLException
	 */
	List<Customer> findUsersSubscribedToAShop(final Integer codeShop) throws SQLException;
	/**
	 * Find the customer subscribed to the shops of an administrator.
	 * @param currAdmin currAdmin
	 * @return the list of the customer
	 * @throws SQLException SQLException
	 */
	List<Customer> findUsersSubscribedToAdminShops(final String currAdmin) throws SQLException;

}
