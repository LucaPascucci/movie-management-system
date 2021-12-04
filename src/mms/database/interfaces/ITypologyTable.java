package mms.database.interfaces;

import java.sql.SQLException;

import mms.model.Typology;

/**
 * Interface of TypologyTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface ITypologyTable {

	/**
	 * Finds the correct record through the primary key.
	 * @param code the primary key
	 * @return Typology
	 * @throws SQLException when there is a problem with the database.
	 */
	Typology findByPrimaryKey(final int code) throws SQLException;

	/**
	 * Alters the table for the foreign key.
	 * @throws SQLException when there is a problem with the database.
	 */
	void alterTable() throws SQLException;

	/**
	 * Checks the expired discounts and deletes them from the database.
	 * @throws SQLException when there is a problem with the database.
	 */
	void checkAllDiscount() throws SQLException;
}
