package mms.view.society;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import mms.controller.society.EditableDiscountController;
import mms.model.Discount;
import mms.view.AbstractView;

/**
 * Create a new discount or edit a discount already created.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class EditableDiscountView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant BORDER_GAP. */
	private static final int BORDER_GAP = 25;

	/** The Constant HORIZONTAL_GAP. */
	private static final int HORIZONTAL_GAP = 125;

	/** The Constant MINIMUM_GAP. */
	private static final int MINIMUM_GAP = 15;

	/** The lbl percentage. */
	private final JLabel lblPercentage = new JLabel("Percentage: ");

	/** The lbl period. */
	private final JLabel lblPeriod = new JLabel("Period: ");

	/** The lbl discount score. */
	private final JLabel lblDiscountScore = new JLabel("Discount score: ");

	/** The txt percentage. */
	private final JTextField txtPercentage = new JTextField(6);

	/** The txt period. */
	private final JTextField txtPeriod = new JTextField(6);

	/** The txt discount score. */
	private final JTextField txtDiscountScore = new JTextField(6);

	/** The confirm. */
	private final JButton confirm = new JButton(new ImageIcon(this.getClass().getResource("/Save.png")));

	/** The cancel. */
	private final JButton cancel = new JButton("Cancel", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The observer. */
	private EditableDiscountController observer;

	/**
	 * The constructor of the view.
	 */
	public EditableDiscountView() {

		super();

		this.setSize(225, 225);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.lblPeriod);
		layout.putConstraint(SpringLayout.WEST, this.lblPeriod, BORDER_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblPeriod, BORDER_GAP, SpringLayout.NORTH, this.getContentPane());
		this.add(this.txtPeriod);
		layout.putConstraint(SpringLayout.WEST, this.txtPeriod, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.txtPeriod, BORDER_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.lblPercentage);
		layout.putConstraint(SpringLayout.WEST, this.lblPercentage, BORDER_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblPercentage, BORDER_GAP, SpringLayout.SOUTH, this.txtPeriod);
		this.add(this.txtPercentage);
		layout.putConstraint(SpringLayout.WEST, this.txtPercentage, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.txtPercentage, BORDER_GAP, SpringLayout.SOUTH, this.txtPeriod);

		this.add(this.lblDiscountScore);
		layout.putConstraint(SpringLayout.WEST, this.lblDiscountScore, BORDER_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblDiscountScore, BORDER_GAP, SpringLayout.SOUTH, this.txtPercentage);
		this.add(this.txtDiscountScore);
		layout.putConstraint(SpringLayout.WEST, this.txtDiscountScore, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.txtDiscountScore, BORDER_GAP, SpringLayout.SOUTH, this.txtPercentage);

		this.add(this.confirm);
		layout.putConstraint(SpringLayout.EAST, this.confirm, -MINIMUM_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.confirm, -MINIMUM_GAP, SpringLayout.SOUTH, this.getContentPane());
		this.add(this.cancel);
		layout.putConstraint(SpringLayout.EAST, this.cancel, -MINIMUM_GAP, SpringLayout.WEST, this.confirm);
		layout.putConstraint(SpringLayout.SOUTH, this.cancel, -MINIMUM_GAP, SpringLayout.SOUTH, this.getContentPane());

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final Discount discount = observer.getEditableDiscount();
				if (discount != null) {
					txtPeriod.setText("" + discount.getPeriod());
					txtPercentage.setText("" + discount.getPercentage());
					txtDiscountScore.setText("" + discount.getDiscountScore());
					setTitle("Edit Discount: " + discount.getDiscountCode());
					confirm.setText("Save");
				} else {
					setTitle("New Discount");
					confirm.setText("Register");
				}
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}
		});
		this.confirm.addActionListener(this);
		this.cancel.addActionListener(this);
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(this.confirm)) {
			this.observer.doRegister(this.txtPercentage.getText(), this.txtPeriod.getText(), this.txtDiscountScore.getText());
		} else if (source.equals(this.cancel)) {
			this.observer.doBack();
		}

	}

	/**
	 * Reset the fields.
	 */
	public void resetField() {
		this.txtPeriod.setText("");
		this.txtPercentage.setText("");
		this.txtDiscountScore.setText("");
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param editableDiscountController controller for this view
	 */
	public void attachObserver(final EditableDiscountController editableDiscountController) {
		this.observer = editableDiscountController;
	}
}
