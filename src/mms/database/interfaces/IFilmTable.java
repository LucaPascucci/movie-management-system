package mms.database.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mms.model.Film;

/**
 * Interface of filmTable.
 * @author Utente
 *
 */
public interface IFilmTable {
	/**
	 * Find a film by code.
	 * @param code code
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	Film findByPrimaryKey(final int code) throws SQLException;
	/**
	 * Get all films.
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> findAll() throws SQLException;
	/**
	 * Get the code of the last film saved.
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	int getMaxFilmCode() throws SQLException;
	/**
	 * Get the film there are in the society but not in the shop and viceversa.
	 * @param society society
	 * @param codShop codShop
	 * @return the code of the film
	 * @throws SQLException SQLException
	 */
	List<Film> findMissingFilm(final boolean society, final int codShop) throws SQLException;
	/**
	 * Get the film of a shop.
	 * @param codeShop codShop
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> findFilmOfAShop(final int codeShop) throws SQLException;
	/**
	 * Get the list of the films ordered by purchases or views.
	 * @param codeShop codShop
	 * @param viewedOrBought viewedOrBought
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> orderByViewsOrPurchases(final int codeShop, final boolean viewedOrBought) throws SQLException;
	/**
	 * Get the list of the films ordered by insertion date.
	 * @param codeShop codeShop
	 * @param date date
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> orderByInsertionDate(final int codeShop, final Date date) throws SQLException;
	/**
	 * Get the filtered film list.
	 * @param codeShop codeShop
	 * @param filter filter
	 * @param control control
	 * @param date date
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> getCorrectFilmList(final int codeShop, final Object[] filter, final int control, final Date date) throws SQLException;
	/**
	 * Get all film of an administrator.
	 * @param usernameAmm usernameAmm
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> getAllFilmsOfAnAdmin(final String usernameAmm) throws SQLException;
	/**
	 * Get all film of an administrator ordered by insertion date.
	 * @param usernameAmm usernameAmm
	 * @param date date
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> getAllFilmsOfAnAdminOrderedByInsDate(final String usernameAmm, final Date date) throws SQLException;
	/**
	 * Get all film of an administrator ordered by purchases.
	 * @param usernameAmm usernameAmm
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> getAllFilmsOfAnAdminOrderedByPurchases(final String usernameAmm) throws SQLException;
	/**
	 * Get all film of an administrator ordered by views.
	 * @param usernameAmm usernameAmm
	 * @return the list of film
	 * @throws SQLException SQLException
	 */
	List<Film> getAllFilmsOfAnAdminOrderedByViews(final String usernameAmm) throws SQLException;
}
