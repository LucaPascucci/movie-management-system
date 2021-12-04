package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Actor;

/**
 * Interfaces of the actorTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IActorTable {
	/**
	 * Find an actor by code.
	 * @param code code
	 * @return the actor corresponding to that code
	 * @throws SQLException SQLException
	 */
	Actor findByPrimaryKey(final int code) throws SQLException;

	/**
	 * Find all actor insert in the database.
	 * @return the list of all actor
	 * @throws SQLException SQLException
	 */
	List<Actor> findAll() throws SQLException;
	/**
	 * Find all actor that act in the film.
	 * @param codeFilm code of the film
	 * @return the list of the actor that act in the film
	 * @throws SQLException SQLException
	 */
	List<Actor> getActorsStarringInAFilm(final int codeFilm) throws SQLException;
}
