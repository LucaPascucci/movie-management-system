package mms.controller.society;

import java.sql.SQLException;

import javax.swing.JFrame;

import mms.controller.AbstractController;
import mms.controller.interfaces.IEditableDiscountController;
import mms.database.DiscountTable;
import mms.model.Discount;
import mms.model.IModel;
import mms.view.society.EditableDiscountView;

/**
 * The controller for the editable discount view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class EditableDiscountController extends AbstractController implements IEditableDiscountController {

	/** The Constant INVALID_NUMBER. */
	private static final String INVALID_NUMBER = "Invalid number!";

	/** The editable discount. */
	private final Integer editableDiscount;

	/**
	 * Constructor for this class.
	 * @param mod the model
	 * @param code the code
	 */
	public EditableDiscountController(final IModel mod, final Integer code) {
		super(mod, EDITABLE_DISCOUNT_VIEW);
		this.editableDiscount = code;
	}

	@Override
	public Discount getEditableDiscount() {
		try {
			if (this.editableDiscount != null) {
				return new DiscountTable().findByPrimaryKey(this.editableDiscount);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
		return null;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((EditableDiscountView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MANAGE_SHOP_VIEW, null);
	}

	@Override
	public void doRegister(final String value, final String period, final String cost) {
		try {
			if (value.length() != 0 || period.length() != 0 || cost.length() != 0) {
				final int realValue = Integer.parseInt(value);
				final int realPeriod = Integer.parseInt(period);
				final int realCost = Integer.parseInt(cost);
				final Discount discount = new Discount();
				discount.setDiscountScore(realCost);
				discount.setPeriod(realPeriod);
				discount.setPercentage(realValue);
				if (this.editableDiscount != null) {
					discount.setDiscountCode(this.editableDiscount);
					new DiscountTable().update(discount);
				} else {
					new DiscountTable().persist(discount);
				}
				this.doBack();
			} else {
				this.showErrorDialog(ERROR_INSERT);
				((EditableDiscountView) this.frame).resetField();
			}
		} catch (NumberFormatException exc) {
			this.saveError(exc);
			this.showErrorDialog(INVALID_NUMBER);
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
	}
}
