package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.FileFilm;

/**
 * Interface of the fileFilmTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IFileFilmTable {
	/**
	 * Find the file by the key.
	 * @param codShop codShop
	 * @param codFilm codFilm
	 * @param name name
	 * @return the fileFilm if it was find in the database
	 * @throws SQLException SQLException
	 */
	FileFilm findByPrimaryKey(final int codShop, final int codFilm, final int name) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;
	/**
	 * Gets the next name.
	 * @param codShop codShop
	 * @param codFilm codFilm
	 * @param extension extension
	 * @return the integer name.
	 * @throws SQLException SQLException
	 */
	Integer getNextName(final int codShop, final int codFilm, final String extension) throws SQLException;
	/**
	 * Get the covers of a film of a certain shop.
	 * @param codShop codShop
	 * @param codFilm codFilm
	 * @param extension extension
	 * @return the list of the covers of a film
	 * @throws SQLException SQLException
	 */
	List<FileFilm> getFilmCoversinShop(final int codShop, final int codFilm, final String extension) throws SQLException;
	/**
	 * Get the file of a film of a certain shop.
	 * @param codShop codShop
	 * @param codFilm codFilm
	 * @return the list of the files of a film
	 * @throws SQLException SQLException
	 */
	List<FileFilm> getFileofFilminShop(final int codShop, final int codFilm) throws SQLException;
	/**
	 * Get all the files of the films.
	 * @param codFilm codFilm
	 * @return the list of all file of all films
	 * @throws SQLException SQLException
	 */
	List<FileFilm> allFilesOfFilm(final int codFilm) throws SQLException;
}
