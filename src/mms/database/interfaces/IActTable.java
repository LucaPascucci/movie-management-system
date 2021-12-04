package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Act;

/**
 * Interface of the actTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IActTable {
	/**
	 * Find the record by code of the film and code of the actor.
	 * @param codFilm codFilm
	 * @param codActor codActor
	 * @return the record act in the database if it was find
	 * @throws SQLException SQLException
	 */
	Act findByPrimaryKey(final int codFilm, final int codActor) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;
	/**
	 * Find the record by the code.
	 * @param code code
	 * @param control true for the actor, false for the film
	 * @return the list of the code that represent the films or the actors
	 * @throws SQLException SQLException
	 */
	List<Integer> findByCode(final int code, final boolean control) throws SQLException;
	/**
	 * Find the film that have only one actor.
	 * @param codActor codActor
	 * @return list of films
	 * @throws SQLException SQLException
	 */
	List<Integer> findFilmOnlyOneActor(final int codActor) throws SQLException;

}
