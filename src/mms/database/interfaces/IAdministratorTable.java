package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Administrator;

/**
 * Interface of the administratorTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IAdministratorTable {
	/**
	 * Find the administrator by username.
	 * @param username username
	 * @return the administrator if it was find in the database
	 * @throws SQLException SQLException
	 */
	Administrator findByPrimaryKey(final String username) throws SQLException;
	/**
	 * Find all administrator in the society.
	 * @return the list of all administrator
	 * @throws SQLException SQLException
	 */
	List<Administrator> findAll() throws SQLException;

}
