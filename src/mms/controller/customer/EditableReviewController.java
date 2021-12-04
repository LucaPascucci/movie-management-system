package mms.controller.customer;

import java.sql.SQLException;

import javax.swing.JFrame;

import mms.controller.AbstractController;
import mms.controller.interfaces.IEditableReviewController;
import mms.database.ReviewTable;
import mms.exception.AlreadyExistsException;
import mms.model.IModel;
import mms.model.Review;
import mms.view.customer.EditableReviewView;

/**
 * Controller for the editable review view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class EditableReviewController extends AbstractController implements IEditableReviewController {

	/** The return view. */
	private final Integer returnView;

	/** The film code. */
	private final Integer filmCode;

	/** The shop code. */
	private final String shopCode;

	/**
	 * Constructor for this class.
	 * @param mod model 
	 * @param code code
	 * @param fromView fromView
	 * @param shop shop
	 */
	public EditableReviewController(final IModel mod, final Integer code, final Integer fromView, final String shop) {
		super(mod, EDITABLE_REVIEW_VIEW);
		this.returnView = fromView;
		this.filmCode = code;
		this.shopCode = shop;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((EditableReviewView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.frame.setName(shopCode);
		this.changeView(EDITABLE_REVIEW_VIEW, this.returnView, this.filmCode);
	}

	@Override
	public Review getReview() {
		try {
			return new ReviewTable().findByPrimaryKey(this.model.getCurrentUser().getUsername(), this.filmCode);
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
		return null;
	}

	@Override
	public void sendReview(final Review r) {
		if (r.getText().length() < 8001 && r.getHeader().length() < 101) {
			try {
				final Review review = r;
				review.setCodFilm(this.filmCode);
				review.setUsername(this.model.getCurrentUser().getUsername());
				try {
					new ReviewTable().persist(review);
				} catch (AlreadyExistsException e1) {
					new ReviewTable().update(review);
				}
			} catch (SQLException e) {
				this.saveError(e);
				this.showErrorDialog(SQL_ERROR);
			}
		} else {
			this.showErrorDialog(ERROR_INSERT);
		}

		this.frame.setName(shopCode);
		this.changeView(EDITABLE_REVIEW_VIEW, this.returnView, this.filmCode);
	}

	@Override
	public String getCodeFilm() {
		return this.filmCode.toString();
	}

}
