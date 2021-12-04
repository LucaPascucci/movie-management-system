package mms.controller.customer;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mms.controller.AbstractController;
import mms.controller.interfaces.IEditableCardController;
import mms.database.CardTable;
import mms.database.DiscountTable;
import mms.database.TypologyTable;
import mms.model.Card;
import mms.model.Discount;
import mms.model.IModel;
import mms.model.Typology;
import mms.view.customer.EditableCardView;

/**
 * Controller for the editable card view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class EditableCardController extends AbstractController implements IEditableCardController {

	/** The Constant DISCOUNT_ALREADY_ACTIVE. */
	private static final String DISCOUNT_ALREADY_ACTIVE = "This discount is already active";

	/** The Constant INSUFFICIENT_SCORE. */
	private static final String INSUFFICIENT_SCORE = "Insufficient score in the card!";

	/** The Constant CHANGE_DISCOUNT. */
	private static final String CHANGE_DISCOUNT = "Do you want to change discount?";

	/** The return view. */
	private final Integer returnView;

	/** The customer card. */
	private final Card customerCard;

	/** The active discount. */
	private Typology activeDiscount;

	/**
	 * Constructor for this class.
	 * @param mod the model
	 * @param card the card
	 * @param fromView fromView
	 */
	public EditableCardController(final IModel mod, final Card card, final Integer fromView) {
		super(mod, EDITABLE_CARD_VIEW);
		this.returnView = fromView;
		this.customerCard = card;
		this.activeDiscount = null;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((EditableCardView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(EDITABLE_CARD_VIEW, this.returnView, null);
	}

	@Override
	public Card getCard() {
		try {
			this.activeDiscount = new TypologyTable().findByPrimaryKey(this.customerCard.getNumCard());
			return this.customerCard;
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
		return null;
	}

	@Override
	public void generateTable() {
		try {
			final Iterator<Discount> it = new DiscountTable().findAll().iterator();
			Discount curr;
			while (it.hasNext()) {
				curr = it.next();
				final Object[] obj = new Object[]{curr.getDiscountCode(), curr.getPeriod(), curr.getPercentage(), curr.getDiscountScore()};
				if (this.activeDiscount != null) {
					if (this.activeDiscount.getCodDiscount() == curr.getDiscountCode()) {
						((EditableCardView) this.frame).showActiveDiscount(this.activeDiscount, curr);
						((EditableCardView) this.frame).addRow(obj, true);
					} else {
						((EditableCardView) this.frame).addRow(obj, false);
					}
				} else {
					((EditableCardView) this.frame).addRow(obj, false);
				}
			}
			((EditableCardView) this.frame).setToolTipText("Choose a discount");
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		} catch (NullPointerException exc) {
			this.saveError(exc);
		}
	}

	@Override
	public void setDiscount(final Integer value) {
		try {
			final Discount selectedDiscount = new DiscountTable().findByPrimaryKey(value);
			if (this.activeDiscount != null) {
				if (this.activeDiscount.getCodDiscount() == value) {
					this.showInfoDialog(DISCOUNT_ALREADY_ACTIVE);
				} else if (this.customerCard.getScoreCard() < selectedDiscount.getDiscountScore()) {
					this.showWarningDialog(INSUFFICIENT_SCORE);
				} else { 
					if (this.showQuestionDialog(CHANGE_DISCOUNT) == JOptionPane.YES_OPTION) {
						this.activeDiscount.setStartDateDiscount(new Date());
						this.activeDiscount.setCodDiscount(value);
						new TypologyTable().update(this.activeDiscount);
						this.customerCard.setScoreCard(this.customerCard.getScoreCard() - selectedDiscount.getDiscountScore());
						new CardTable().update(this.customerCard);
						this.showInfoDialog("The discount " + value + " was applied to the card " + this.customerCard.getNumCard());
						this.doBack();
					}
				}
			} else {
				if (this.customerCard.getScoreCard() < selectedDiscount.getDiscountScore()) {
					this.showWarningDialog(INSUFFICIENT_SCORE);
				} else {
					final Typology typology = new Typology();
					typology.setCodDiscount(value);
					typology.setNumCard(this.customerCard.getNumCard());
					typology.setStartDateDiscount(new Date());
					new TypologyTable().persist(typology);
					this.customerCard.setScoreCard(this.customerCard.getScoreCard() - selectedDiscount.getDiscountScore());
					new CardTable().update(this.customerCard);
					this.showInfoDialog("The discount " + value + " was applied to the card " + this.customerCard.getNumCard());
					this.doBack();
				}
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}

	}

}
