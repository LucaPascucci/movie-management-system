package mms.controller.interfaces;

import javax.swing.Icon;

import mms.model.Film;

/**
 * Interface that defines the DetailsFilmController{@link mms.controller.DetailsFilmController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IDetailsFilmController {

	/**
	 * Method that downloads all the files, relative to the cover, to the current computer.
	 */
	void doDownloadCover();

	/**
	 * Method that runs the video trailer of the selected film.	
	 */
	void doTrailerView();

	/**
	 * Method that allows a customer to buy a film. 	
	 */
	void doBuy();

	/**
	 * Returns if the selected film	is the favorite one of the user or not.
	 * @return boolean
	 */
	boolean doFavorite();

	/**
	 * Returns the object Film.	
	 * @return Film
	 */
	Film getFilm();

	/**
	 * Method that confirms that the visit took place.	
	 */
	void doVisit();

	/**
	 * Method that shows all the actors starring in a film.
	 */
	void doShowActors();

	/**
	 *  Get the current object cover.
	 *  @return Icon
	 */
	Icon getNextCover();

	/**
	 * Return a boolean that say if the selected film is one of the favorite films of a generic User.
	 * @return boolean
	 */
	boolean isFavorite();

	/**
	 * This method check if the secondaryThread is running.
	 * @return boolean
	 */
	boolean controllThread();

	/**
	 * Opens up the view of the reviews.
	 */
	void showReviews();

	/**
	 * Checks if the current user is an admin.
	 * @return boolean
	 */
	boolean checkAdmin();

	/**
	 * Checks if the current user is a customer.
	 * @return boolean
	 */
	boolean checkSociety();

	/**
	 * Send to the view the informations to display the stars.
	 *
	 * @param codeFilm filmCode
	 */
	void getAverage(final int codeFilm);

}
