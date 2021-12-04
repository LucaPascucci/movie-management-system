package mms.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mms.controller.ReviewController;

/**
 * Show the review of the film.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class ReviewView extends AbstractView {	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The list container. */
	private final JPanel listContainer;

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The new review. */
	private final JButton newReview = new JButton("Write a review", new ImageIcon(this.getClass().getResource("/Edit_File.png")));

	/** The button panel. */
	private final JPanel buttonPanel;

	/** The observer. */
	private ReviewController observer;

	private final JLabel firstLbl = new JLabel("Review of film: ");

	/**
	 * The constructor of the view.
	 */
	public ReviewView() {

		super();

		this.setTitle("Reviews of film: ");
		this.setSize(600, 700);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(this.firstLbl);
		firstLbl.setVisible(false);
		this.listContainer = new JPanel();
		this.listContainer.setLayout(new BoxLayout(this.listContainer, BoxLayout.Y_AXIS));
		this.buttonPanel = new JPanel();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(final WindowEvent e) {
				if (observer.isCustomer()) {
					buttonPanel.add(newReview);
				}
				buttonPanel.add(back);
				setTitle(getTitle() + observer.getFilmCode());
				firstLbl.setText(firstLbl.getText() + observer.getFilmCode());
				final List<JPanel> list = observer.fillView();
				try {
					final Iterator<JPanel> it = list.iterator();
					while (it.hasNext()) {
						final JPanel pane = it.next();
						listContainer.add(pane);
						listContainer.revalidate();
					}
				} catch (NullPointerException exc) {
					observer.saveError(exc);
				}
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}
		});
		this.add(this.listContainer);
		this.add(this.buttonPanel, BorderLayout.PAGE_END);
		this.newReview.addActionListener(this);
		this.back.addActionListener(this);
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(this.back)) {
			this.observer.doBack();
		} else if (source.equals(this.newReview)) {
			this.observer.makeAReview();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) { }

	/**
	 * Attach the observer of the controller to the view.
	 * @param reviewController controller for this view
	 */
	public void attachObserver(final ReviewController reviewController) {
		this.observer = reviewController;
	}

	/**
	 * Set newReview Button text.
	 * @param text the text for newReview Button
	 */
	public void setReviewButtonText(final String text) {
		this.newReview.setText(text);
	}

}
