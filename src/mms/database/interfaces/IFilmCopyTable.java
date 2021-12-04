package mms.database.interfaces;

import java.sql.SQLException;

import mms.model.FilmCopy;

/**
 * Interface of the filmCopyTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IFilmCopyTable {
	/**
	 * Find the filmCopy by the key.
	 * @param codShop codShop
	 * @param codFilm codShop
	 * @return the filmCopy if it was find in the database
	 * @throws SQLException SQLException
	 */
	FilmCopy findByPrimaryKey(final int codShop, final int codFilm) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;

}
