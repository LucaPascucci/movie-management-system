package mms.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import mms.controller.interfaces.IReviewController;
import mms.database.EvaluationTable;
import mms.database.ReviewTable;
import mms.exception.AlreadyExistsException;
import mms.model.Evaluation;
import mms.model.IModel;
import mms.model.Review;
import mms.view.ReviewView;

/**
 * Controller for the review view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ReviewController extends AbstractController implements IReviewController {

	/** The Constant ANY_REVIEW_FIND. */
	private static final String ANY_REVIEW_FIND = "There are not review for this film.";

	/** The Constant ORANGE_COLOR. */
	private static final Color ORANGE_COLOR = new Color(248, 192, 61);

	/** The Constant EVALUATION_ADDED. */
	private static final String EVALUATION_ADDED = "Evaluation added to the review.";

	/** The Constant REMOVED_SELECTION. */
	private static final String REMOVED_SELECTION = "Evaluation removed.";

	/** The Constant CANNOT_VALUTATE. */
	private static final String CANNOT_VALUTATE = "The owner of the review cannot evaluate!";

	/** The film code rel. */
	private final Integer filmCodeRel;

	/** The shop code. */
	private final String shopCode;

	/**
	 * Constructor for this class.
	 * @param mod the model
	 * @param codFilm the film code
	 * @param shop the shop code
	 */
	public ReviewController(final IModel mod, final Integer codFilm, final String shop) {
		super(mod, REVIEW_VIEW);
		this.filmCodeRel = codFilm;
		this.shopCode = shop;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((ReviewView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		Object shop = shopCode.charAt(0);
		this.frame.setName(shop.toString());
		shop = shopCode.substring(1);
		this.changeView(Integer.parseInt((String) shop), DETAILS_FILM_VIEW, this.filmCodeRel);
	}

	@Override
	public Integer getFilmCode() {
		return this.filmCodeRel;
	}

	@Override
	public List<JPanel> fillView() {
		try {
			final List<JPanel> listPane = new ArrayList<>();
			final List<Review> list = new ReviewTable().getReviewsOfAFilm(this.filmCodeRel);
			final Iterator<Review> it = list.iterator();
			Review rev;
			while (it.hasNext()) {
				rev = it.next();
				if (rev.getUsername().equals(this.model.getCurrentUser().getUsername())) {
					((ReviewView) this.frame).setReviewButtonText("Edit your review");
				}
				final JPanel pane = new JPanel();
				pane.setBorder(BorderFactory.createTitledBorder("Username: " + rev.getUsername()));
				final JLabel head = new JLabel(rev.getHeader());
				final JLabel vote = new JLabel("Evaluation: " + rev.getEvaluation());
				final JTextArea review = new JTextArea(10, 30);
				review.setEditable(false);
				review.setLineWrap(true);
				review.setText(rev.getText());
				final JScrollPane reviewScroll = new JScrollPane(review);
				final JPanel evaluation = new JPanel();
				Integer value = new EvaluationTable().countEvaluation(this.filmCodeRel, rev.getUsername(), 'Y');
				final JButton like = new JButton("Like ( " + value + " )" , new ImageIcon(this.getClass().getResource("/Like.png")));
				if (!isCustomer()) {
					like.setEnabled(false);
				}
				like.setActionCommand(rev.getUsername());

				value = new EvaluationTable().countEvaluation(this.filmCodeRel, rev.getUsername(), 'N');
				final JButton notLike = new JButton("Not Like ( " + value + " )", new ImageIcon(this.getClass().getResource("/Not_Like.png")));
				notLike.setActionCommand(rev.getUsername());
				Evaluation e = new EvaluationTable().findByPrimaryKey(model.getCurrentUser().getUsername(), this.filmCodeRel, rev.getUsername());
				if (e != null) {
					if (e.getLikeOrDislike() == 'Y') {
						like.setBackground(ORANGE_COLOR);
					} else if (e.getLikeOrDislike() == 'N') {
						notLike.setBackground(ORANGE_COLOR);
					}
				}

				if (!isCustomer()) {
					notLike.setEnabled(false);
				}

				like.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							final Object username = e.getActionCommand();
							if (model.getCurrentUser().getUsername().equals(username)) { //se sono il propritario della recensione non fa niente
								showWarningDialog(CANNOT_VALUTATE);
							} else {
								final Evaluation eval = new Evaluation();
								eval.setLikeOrDislike('Y');
								eval.setRelCodFilm(Integer.parseInt(filmCodeRel.toString()));
								eval.setRelUsername((String) username);
								eval.setUsername(model.getCurrentUser().getUsername());
								try {
									new EvaluationTable().persist(eval);
									like.setBackground(ORANGE_COLOR);
									showInfoDialog(EVALUATION_ADDED);
								} catch (AlreadyExistsException e1) {
									eval.setLikeOrDislike(new EvaluationTable().findByPrimaryKey(eval.getUsername(), eval.getRelCodMovie(), eval.getRelUsername()).getLikeOrDislike());
									if (eval.getLikeOrDislike() == 'Y') {
										new EvaluationTable().delete(eval);
										like.setBackground(null);
										showInfoDialog(REMOVED_SELECTION);
									} else {
										eval.setLikeOrDislike('Y');
										new EvaluationTable().update(eval);
										final Integer v = new EvaluationTable().countEvaluation(Integer.parseInt(filmCodeRel.toString()), (String) username, 'N');
										((JButton) notLike).setText(new String("Not Like ( " + (v) + " )"));
										notLike.setBackground(null);
										like.setBackground(ORANGE_COLOR);
										showInfoDialog(EVALUATION_ADDED);
									}
								}
								final Integer v = new EvaluationTable().countEvaluation(Integer.parseInt(filmCodeRel.toString()), (String) username, 'Y');
								((JButton) like).setText(new String("Like ( " + v + " )"));
							}
						} catch (SQLException exc) {
							saveError(exc);
							showErrorDialog(SQL_ERROR);
						}
					}
				});

				notLike.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						try {
							final Object username = e.getActionCommand();
							if (model.getCurrentUser().getUsername().equals(username)) { //se sono il propritario della recensione non fa niente
								showWarningDialog(CANNOT_VALUTATE);
							} else {
								final Evaluation eval = new Evaluation();
								eval.setLikeOrDislike('N');
								eval.setRelCodFilm(Integer.parseInt(filmCodeRel.toString()));
								eval.setRelUsername((String) username);
								eval.setUsername(model.getCurrentUser().getUsername());
								try {
									new EvaluationTable().persist(eval);
									notLike.setBackground(ORANGE_COLOR);
									showInfoDialog(EVALUATION_ADDED);
								} catch (AlreadyExistsException e1) {
									eval.setLikeOrDislike(new EvaluationTable().findByPrimaryKey(eval.getUsername(), eval.getRelCodMovie(), eval.getRelUsername()).getLikeOrDislike());
									if (eval.getLikeOrDislike() == 'N') {
										new EvaluationTable().delete(eval);
										notLike.setBackground(null);
										showInfoDialog(REMOVED_SELECTION);
									} else {
										eval.setLikeOrDislike('N');
										new EvaluationTable().update(eval);
										final Integer v = new EvaluationTable().countEvaluation(Integer.parseInt(filmCodeRel.toString()), (String) username, 'Y');
										((JButton) like).setText(new String("Like ( " + (v) + " )"));
										like.setBackground(null);
										notLike.setBackground(ORANGE_COLOR);
										showInfoDialog(EVALUATION_ADDED);
									}
								}
								final Integer v = new EvaluationTable().countEvaluation(Integer.parseInt(filmCodeRel.toString()), (String) username, 'N');
								((JButton) notLike).setText(new String("Not Like ( " + v + " )"));
							}
						} catch (SQLException exc) {
							saveError(exc);
							showErrorDialog(SQL_ERROR);
						}
					}
				});
				evaluation.add(like);
				evaluation.add(notLike);
				pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
				pane.add(head);
				pane.add(vote);
				pane.add(reviewScroll);
				pane.add(evaluation);
				listPane.add(pane);
			}
			return listPane;
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} catch (NullPointerException e) {
			this.saveError(e);
			this.showWarningDialog(ANY_REVIEW_FIND);
		}
		return null;
	}

	@Override
	public void makeAReview() {
		this.frame.setName(shopCode);
		this.changeView(REVIEW_VIEW, EDITABLE_REVIEW_VIEW, this.filmCodeRel);
	}

	@Override
	public boolean isCustomer() {
		return this.model.getUserType().equals("customer");
	}
}
