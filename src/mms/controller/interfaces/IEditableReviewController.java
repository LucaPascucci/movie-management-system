package mms.controller.interfaces;

import mms.controller.customer.EditableReviewController;
import mms.model.Review;

/**
 * Interface that define {@link EditableReviewController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IEditableReviewController {

	/**
	 * Gets the film's review of a specific customer. 
	 * @return the review
	 */
	Review getReview();

	/**
	 * Saves the review if it doesn't already exists, otherwise updates it.
	 * @param r the review to save
	 */
	void sendReview(Review r);

	/**
	 * Gets the code of the film.
	 * @return the film code
	 */
	String getCodeFilm();

}
