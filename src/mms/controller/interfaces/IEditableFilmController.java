package mms.controller.interfaces;

import mms.model.Film;
import mms.model.FilmType;

/**
 * Interface that define {@link mms.controller.society.EditableFilmController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IEditableFilmController {

	/**
	 * Returns a specific film.
	 * @return the film
	 */
	Film getEditableFilm();

	/**
	 * Method that checks that the new parameters are correct.
	 * @param title parameter that pass the name of the film.
	 * @param price parameter that pass the cost of the film.
	 * @param plot parameter that pass the description of the film.
	 * @param year parameter that pass the release date of the film.
	 * @param currType parameter that pass the gender of the film.
	 */
	void doConfirm(final String title, final String price, final String plot, final Integer year, final FilmType currType);

	/**
	 * Shows the all actors.
	 */
	void doShowActors();

	/**
	 * Adds the actors' code present in a film to the list.
	 * @param actorCode the actor code
	 * @param check if true, the actor is added to the list
	 */
	void doManageActor(final Integer actorCode, final boolean check);

}
