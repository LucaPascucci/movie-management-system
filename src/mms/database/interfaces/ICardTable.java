package mms.database.interfaces;

import java.sql.SQLException;

import mms.model.Card;
/**
 * Interface of cardTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface ICardTable {
	/**
	 * Find the card by the code.
	 * @param code code
	 * @return return the card which corresponding to that code
	 * @throws SQLException SQLException
	 */
	Card findByPrimaryKey(final int code) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;
	/**
	 * Find the card by the username of the owner.
	 * @param username username
	 * @return the card if it was find in the database
	 * @throws SQLException SQLException
	 */
	Card findByUsername(final String username) throws SQLException;
}
