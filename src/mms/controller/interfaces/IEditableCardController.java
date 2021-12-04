package mms.controller.interfaces;

import mms.controller.society.EditableFilmController;
import mms.model.Card;

/**
 * Interface that defines the {@link EditableFilmController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IEditableCardController {

	/**
	 * Returns the card of a specific customer.
	 * @return the card
	 */
	Card getCard();

	/**
	 * Generates the JTable containing all the available discounts.
	 */
	void generateTable();

	/**
	 * Sets the discount to a customer's card.
	 * @param value the discount code
	 */
	void setDiscount(final Integer value);

}
