package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Shop;

/**
 * Interface of ShopTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IShopTable {

	/**
	 * Finds all the shop in the database.
	 * @return the shop list
	 * @throws SQLException when there is a problem with the database.
	 */
	List<Shop> findAll() throws SQLException;

	/**
	 * Finds the correct record through the primary key.
	 * @param code teh primary key
	 * @return the correct shop
	 * @throws SQLException when there is a problem with the database.
	 */
	Shop findByPrimaryKey(final int code) throws SQLException;

	/**
	 * Alters the table for the foreign key.
	 * @throws SQLException when there is a problem with the database.
	 */
	void alterTable() throws SQLException;

	/**
	 * Gets all the shop codes of a specific admin.
	 * @param username the admin's username.
	 * @return the list of shop codes of an admin.
	 * @throws SQLException when there is a problem with the database.
	 */
	List<Integer> getShopCodesOfAnAdmin(final String username) throws SQLException;

	/**
	 * Gets the highest shop code present in the database.
	 * @return the highest shop code.
	 * @throws SQLException when there is a problem with the database.
	 */
	int getMaxShopCode() throws SQLException;

	/**
	 * Gets all shop codes where the customer is subscribed.
	 * @param username the username of the customer
	 * @return the list of shop codes
	 * @throws SQLException when there is a problem with the database.
	 */
	List<Integer> getShopCodesOfACustomer(final String username) throws SQLException;

	/**
	 * Gets all shop codes of a specific admin where the customer is subscribed.
	 * @param customer the username of the customer
	 * @param admin the username of the admin
	 * @return the list of shop codes
	 * @throws SQLException when there is a problem with the database.
	 */
	List<Integer> getShopCodesOfACustomerBelonginToAnAdmin(final String customer, final String admin) throws SQLException;

}
