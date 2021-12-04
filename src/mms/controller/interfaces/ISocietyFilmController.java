package mms.controller.interfaces;

import mms.controller.society.SocietyFilmController;

/**
 * Interface that defines the {@link SocietyFilmController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface ISocietyFilmController {

	/**
	 * Opens up the film view or the person view to add a new film or actor, basing on the control flag.
	 * @param control the flag.
	 */
	void doAdd(final boolean control);

	/**
	 * Opens up the film view or the person view t edit a film or an actor, basing on the control flag.
	 * @param control the flag.
	 * @param code the primary key
	 */
	void doEdit(final boolean control, Integer code);

	/**
	 * Generates the correct table of films or actors, basing on the control flag.
	 * @param control the flag
	 */
	void generateTable(final boolean control);

	/**
	 * Deletes the selected film or actor, basing on the control flag.
	 * @param control the flag
	 * @param code the primary key
	 * @return boolean
	 */
	boolean doDelete(final boolean control, final Integer code);

	/**
	 * Opens up the details' film view.
	 * @param filmCode the film code
	 */
	void showDetailsFilm(final Integer filmCode);

}
