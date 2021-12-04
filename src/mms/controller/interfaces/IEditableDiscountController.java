package mms.controller.interfaces;

import mms.controller.society.EditableDiscountController;
import mms.model.Discount;

/**
 * Interface that defines the {@link EditableDiscountController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IEditableDiscountController {

	/**
	 * Returns a specific discount.
	 * @return the discount
	 */
	Discount getEditableDiscount();

	/**
	 * Creates a new discount.
	 * @param value the discount percentage
	 * @param period the days number of a discount's validity
	 * @param cost the discount score
	 */
	void doRegister(final String value, final String period, final String cost);
}
