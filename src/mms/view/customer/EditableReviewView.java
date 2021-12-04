package mms.view.customer;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import mms.controller.customer.EditableReviewController;
import mms.model.Review;
import mms.view.AbstractView;

/**
 * Create a new review or edit a review already exists.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class EditableReviewView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The lbl head. */
	private final JLabel lblHead = new JLabel("Head: ");

	/** The lbl vote. */
	private final JLabel lblVote = new JLabel("Vote: ");

	/** The lbl text. */
	private final JLabel lblText = new JLabel("Review");

	/** The head. */
	private final JTextField head = new JTextField(20);

	/** The text. */
	private final JTextArea text = new JTextArea(10, 20);

	/** The spinner vote. */
	private final JSpinner spinnerVote = new JSpinner();

	/** The scroll. */
	private final JScrollPane scroll;

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The submit. */
	private final JButton submit = new JButton("Sumbit", new ImageIcon(this.getClass().getResource("/Subscribe.png")));

	/** The observer. */
	private EditableReviewController observer;

	/**
	 * The constructor of the view.
	 */
	public EditableReviewView() {

		super();

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(400, 425);
		this.setResizable(false);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.lblHead);
		layout.putConstraint(SpringLayout.NORTH, this.lblHead, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.lblHead, 25, SpringLayout.WEST, this.getContentPane());
		this.add(this.head);
		layout.putConstraint(SpringLayout.NORTH, this.head, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.head, 5, SpringLayout.EAST, this.lblHead);

		this.add(this.lblVote);
		layout.putConstraint(SpringLayout.NORTH, this.lblVote, 20, SpringLayout.SOUTH, this.head);
		layout.putConstraint(SpringLayout.WEST, this.lblVote, 25, SpringLayout.WEST, this.getContentPane());
		this.add(this.spinnerVote);
		layout.putConstraint(SpringLayout.NORTH, this.spinnerVote, 20, SpringLayout.SOUTH, this.head);
		layout.putConstraint(SpringLayout.WEST, this.spinnerVote, 5, SpringLayout.EAST, this.lblVote);
		this.spinnerVote.setModel(new SpinnerNumberModel(5, 0, 10, 1)); //set the values of the spinnerYears

		this.add(this.lblText);
		layout.putConstraint(SpringLayout.NORTH, this.lblText, 20, SpringLayout.SOUTH, this.spinnerVote);
		layout.putConstraint(SpringLayout.WEST, this.lblText, 25, SpringLayout.WEST, this.getContentPane());
		this.text.setLineWrap(true);
		this.scroll = new JScrollPane(this.text);
		this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(this.scroll);
		layout.putConstraint(SpringLayout.NORTH, this.scroll, 10, SpringLayout.SOUTH, this.lblText);
		layout.putConstraint(SpringLayout.WEST, this.scroll, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.scroll, -25, SpringLayout.EAST, this.getContentPane());

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -25, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.back, -25, SpringLayout.EAST, this.getContentPane());
		this.add(this.submit);
		layout.putConstraint(SpringLayout.SOUTH, this.submit, -25, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.submit, -15, SpringLayout.WEST, this.back);

		this.back.addActionListener(this);
		this.submit.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final Review review = observer.getReview();
				if (review == null) {
					setTitle("New Review of film: " + observer.getCodeFilm());
				} else {
					setTitle("Edit Old Review of film: " + observer.getCodeFilm());
					head.setText(review.getHeader());
					spinnerVote.setValue(review.getEvaluation());
					text.setText(review.getText());
				}
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}
		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(this.back)) {
			this.observer.doBack();
		} else if (source.equals(this.submit)) {
			final Review review = new Review();
			review.setEvaluation((int) this.spinnerVote.getValue());
			review.setHeader(this.head.getText());
			review.setText(this.text.getText());
			this.observer.sendReview(review);
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param editableReviewController controller for this view
	 */
	public void attachObserver(final EditableReviewController editableReviewController) {
		this.observer = editableReviewController;
	}
}
