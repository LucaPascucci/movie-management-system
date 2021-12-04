package mms.controller.interfaces;

import java.util.List;

import javax.swing.JPanel;

import mms.controller.ReviewController;

/**
 * Interface that defines the {@link ReviewController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IReviewController {

	/**
	 * Gets the film code.
	 * @return the film code
	 */
	Integer getFilmCode();

	/**
	 * Fills the view with the list of JPanel containing the reviews.
	 * @return the JPanel list
	 */
	List<JPanel> fillView();

	/**
	 * Opens up the view where the customer can write a review.
	 */
	void makeAReview();

	/**
	 * Checks if the current user is a customer.
	 * @return boolean
	 */
	boolean isCustomer();
}
