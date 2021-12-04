package mms.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import mms.controller.DetailsFilmController;
import mms.model.Film;

/**
 * This class shows the details of the selected film.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class DetailsFilmView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant CHAR_SIZE. */
	private static final int CHAR_SIZE = 16;

	/** The Constant COLUMN_NAME_ACTOR. */
	private static final String[] COLUMN_NAME_ACTOR = new String[]{"Name", "Surname"};

	/** The empty star. */
	private final JLabel emptyStar = new JLabel(new ImageIcon(this.getClass().getResource("/Empty_Star.png")));

	/** The full star. */
	private final JLabel fullStar = new JLabel(new ImageIcon(this.getClass().getResource("/Full_Star.png")));

	/** The download covers. */
	private final JLabel downloadCovers = new JLabel(new ImageIcon(this.getClass().getResource("/Download_Icon.png")));

	/** The lbl cod film. */
	private final JLabel lblCodFilm = new JLabel("Code Film:");

	/** The lbl title. */
	private final JLabel lblTitle = new JLabel("Title:");

	/** The lbl genre. */
	private final JLabel lblGenre = new JLabel("Genre:");

	/** The lbl release year. */
	private final JLabel lblReleaseYear = new JLabel("Release Year:");

	/** The lbl price. */
	private final JLabel lblPrice = new JLabel("Price:");

	/** The lbl plot. */
	private final JLabel lblPlot = new JLabel("Plot");

	/** The cod film. */
	private final JLabel codFilm = new JLabel();

	/** The title. */
	private final JLabel title = new JLabel();

	/** The genre. */
	private final JLabel genre = new JLabel();

	/** The release year. */
	private final JLabel releaseYear = new JLabel();

	/** The price. */
	private final JLabel price = new JLabel();

	/** The plot. */
	private final JTextArea plot = new JTextArea(12, 40); //JTextArea size: 12 width, 40 height.

	/** The plot scroll. */
	private final JScrollPane plotScroll = new JScrollPane(this.plot);

	/** The cover. */
	private final JLabel cover = new JLabel();

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The buy. */
	private final JButton buy = new JButton("Buy Film", new ImageIcon(this.getClass().getResource("/Buy.png")));

	/** The trailer. */
	private final JButton trailer = new JButton("View Trailer", new ImageIcon(this.getClass().getResource("/Play_Trailer.png")));

	/** The review. */
	private final JButton review = new JButton("Show review", new ImageIcon(this.getClass().getResource("/Review.png")));

	/** The lbl actors. */
	private final JLabel lblActors = new JLabel("Actors: ");

	/** The lbl average. */
	private final JLabel lblAverage = new JLabel("Average evaluation: ");

	/** The empty star1. */
	private final JLabel emptyStar1 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Empty_Star.png")));

	/** The empty stat2. */
	private final JLabel emptyStat2 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Empty_Star.png")));

	/** The empty star3. */
	private final JLabel emptyStar3 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Empty_Star.png")));

	/** The empty star4. */
	private final JLabel emptyStar4 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Empty_Star.png")));

	/** The empty star5. */
	private final JLabel emptyStar5 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Empty_Star.png")));

	/** The half star1. */
	private final JLabel halfStar1 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Half_Star.png")));

	/** The half star2. */
	private final JLabel halfStar2 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Half_Star.png")));

	/** The half star3. */
	private final JLabel halfStar3 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Half_Star.png")));

	/** The half star4. */
	private final JLabel halfStar4 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Half_Star.png")));

	/** The half star5. */
	private final JLabel halfStar5 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Half_Star.png")));

	/** The full star1. */
	private final JLabel fullStar1 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Full_Star.png")));

	/** The full star2. */
	private final JLabel fullStar2 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Full_Star.png")));

	/** The full star3. */
	private final JLabel fullStar3 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Full_Star.png")));

	/** The full star4. */
	private final JLabel fullStar4 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Full_Star.png")));

	/** The full star5. */
	private final JLabel fullStar5 = new JLabel(new ImageIcon(this.getClass().getResource("/Small_Full_Star.png")));

	/** The actor table. */
	private final JTable actorTable;

	/** The actor scroll. */
	private final JScrollPane actorScroll;

	/** The observer. */
	private DetailsFilmController observer;

	/**
	 * This constructor creates the view of DetailsFilmView.
	 */
	public DetailsFilmView() {

		super();

		this.setTitle("Info Film");
		this.setResizable(false);
		this.setSize(500, 700); //frame size: 500 width, 700 height.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.lblCodFilm.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblTitle.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblGenre.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblReleaseYear.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblPrice.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblPlot.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblActors.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.lblAverage.setFont(new Font(null, Font.BOLD, CHAR_SIZE));
		this.cover.setToolTipText("One Click to view other covers");
		this.downloadCovers.setToolTipText("Download covers");
		this.emptyStar.setToolTipText("Add to Favorite List");
		this.fullStar.setToolTipText("Remove from Favorite List");

		this.add(this.lblCodFilm);
		layout.putConstraint(SpringLayout.NORTH, this.lblCodFilm, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.lblCodFilm, 25, SpringLayout.WEST, this.getContentPane());
		this.add(this.codFilm);
		layout.putConstraint(SpringLayout.NORTH, this.codFilm, 28, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.codFilm, 10, SpringLayout.EAST, this.lblCodFilm);

		this.add(this.lblTitle);
		layout.putConstraint(SpringLayout.NORTH, this.lblTitle, 15, SpringLayout.SOUTH, this.lblCodFilm);
		layout.putConstraint(SpringLayout.WEST, this.lblTitle, 25, SpringLayout.WEST, this.getContentPane());
		this.add(this.title);
		layout.putConstraint(SpringLayout.NORTH, this.title, 18, SpringLayout.SOUTH, this.lblCodFilm);
		layout.putConstraint(SpringLayout.WEST, this.title, 10, SpringLayout.EAST, this.lblTitle);
		layout.putConstraint(SpringLayout.EAST, this.title, -25, SpringLayout.EAST, this.getContentPane());

		this.add(this.lblGenre);
		layout.putConstraint(SpringLayout.NORTH, this.lblGenre, 15, SpringLayout.SOUTH, this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.lblGenre, 25, SpringLayout.WEST, this.getContentPane());
		this.add(this.genre);
		layout.putConstraint(SpringLayout.NORTH, this.genre, 18, SpringLayout.SOUTH, this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.genre, 5, SpringLayout.EAST, this.lblGenre);

		this.add(this.lblReleaseYear);
		layout.putConstraint(SpringLayout.NORTH, this.lblReleaseYear, 15, SpringLayout.SOUTH, this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.lblReleaseYear, 20, SpringLayout.EAST, this.genre);
		this.add(this.releaseYear);
		layout.putConstraint(SpringLayout.NORTH, this.releaseYear, 18, SpringLayout.SOUTH, this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.releaseYear, 5, SpringLayout.EAST, this.lblReleaseYear);

		this.add(this.lblPrice);
		layout.putConstraint(SpringLayout.NORTH, this.lblPrice, 15, SpringLayout.SOUTH, this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.lblPrice, 20, SpringLayout.EAST, this.releaseYear);
		this.add(this.price);
		layout.putConstraint(SpringLayout.NORTH, this.price, 18, SpringLayout.SOUTH, this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.price, 5, SpringLayout.EAST, this.lblPrice);

		this.add(this.lblAverage);
		layout.putConstraint(SpringLayout.NORTH, this.lblAverage, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.lblAverage, -5, SpringLayout.EAST, this.lblPrice);
		this.add(this.emptyStar1);
		layout.putConstraint(SpringLayout.NORTH, this.emptyStar1, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.emptyStar1, 0, SpringLayout.EAST, this.lblPrice);
		this.add(this.emptyStat2);
		layout.putConstraint(SpringLayout.NORTH, this.emptyStat2, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.emptyStat2, 0, SpringLayout.EAST, this.emptyStar1);
		this.add(this.emptyStar3);
		layout.putConstraint(SpringLayout.NORTH, this.emptyStar3, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.emptyStar3, 0, SpringLayout.EAST, this.emptyStat2);
		this.add(this.emptyStar4);
		layout.putConstraint(SpringLayout.NORTH, this.emptyStar4, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.emptyStar4, 0, SpringLayout.EAST, this.emptyStar3);
		this.add(this.emptyStar5);
		layout.putConstraint(SpringLayout.NORTH, this.emptyStar5, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.emptyStar5, 0, SpringLayout.EAST, this.emptyStar4);

		this.add(this.halfStar1);
		layout.putConstraint(SpringLayout.NORTH, this.halfStar1, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.halfStar1, 0, SpringLayout.EAST, this.lblPrice);
		this.halfStar1.setVisible(false);
		this.add(this.halfStar2);
		layout.putConstraint(SpringLayout.NORTH, this.halfStar2, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.halfStar2, 0, SpringLayout.EAST, this.halfStar1);
		this.halfStar2.setVisible(false);
		this.add(this.halfStar3);
		layout.putConstraint(SpringLayout.NORTH, this.halfStar3, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.halfStar3, 0, SpringLayout.EAST, this.halfStar2);
		this.halfStar3.setVisible(false);
		this.add(this.halfStar4);
		layout.putConstraint(SpringLayout.NORTH, this.halfStar4, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.halfStar4, 0, SpringLayout.EAST, this.halfStar3);
		this.halfStar4.setVisible(false);
		this.add(this.halfStar5);
		layout.putConstraint(SpringLayout.NORTH, this.halfStar5, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.halfStar5, 0, SpringLayout.EAST, this.halfStar4);
		this.halfStar5.setVisible(false);

		this.add(this.fullStar1);
		layout.putConstraint(SpringLayout.NORTH, this.fullStar1, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.fullStar1, 0, SpringLayout.EAST, this.lblPrice);
		this.fullStar1.setVisible(false);
		this.add(this.fullStar2);
		layout.putConstraint(SpringLayout.NORTH, this.fullStar2, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.fullStar2, 0, SpringLayout.EAST, this.fullStar1);
		this.fullStar2.setVisible(false);
		this.add(this.fullStar3);
		layout.putConstraint(SpringLayout.NORTH, this.fullStar3, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.fullStar3, 0, SpringLayout.EAST, this.fullStar2);
		this.fullStar3.setVisible(false);
		this.add(this.fullStar4);
		layout.putConstraint(SpringLayout.NORTH, this.fullStar4, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.fullStar4, 0, SpringLayout.EAST, this.fullStar3);
		this.fullStar4.setVisible(false);
		this.add(this.fullStar5);
		layout.putConstraint(SpringLayout.NORTH, this.fullStar5, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.fullStar5, 0, SpringLayout.EAST, this.fullStar4);
		this.fullStar5.setVisible(false);

		this.add(this.lblActors);
		layout.putConstraint(SpringLayout.NORTH, this.lblActors, 15, SpringLayout.SOUTH, this.lblGenre);
		layout.putConstraint(SpringLayout.WEST, this.lblActors, 25, SpringLayout.WEST, this.getContentPane());
		this.actorTable = new JTable(new DefaultTableModel(new Object[][]{}, COLUMN_NAME_ACTOR) {
			private static final long serialVersionUID = 1;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		});
		this.actorTable.getTableHeader().setReorderingAllowed(false);
		this.actorTable.getTableHeader().setResizingAllowed(false);
		this.actorTable.setFillsViewportHeight(true);
		this.actorTable.setSelectionMode(0);
		this.actorTable.setToolTipText("Double Click to see details");
		final JLabel label = (JLabel) this.actorTable.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.actorScroll = new JScrollPane(this.actorTable);
		this.actorScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(actorScroll);
		layout.putConstraint(SpringLayout.WEST, this.actorScroll, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.actorScroll, 10, SpringLayout.SOUTH, this.lblActors);
		layout.putConstraint(SpringLayout.SOUTH, this.actorScroll, 30, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.actorScroll, 250, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		this.add(this.cover);
		layout.putConstraint(SpringLayout.NORTH, this.cover, 0, SpringLayout.NORTH, this.actorScroll);
		layout.putConstraint(SpringLayout.EAST, this.cover, -25, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.cover, -200, SpringLayout.EAST, this.cover);
		layout.putConstraint(SpringLayout.SOUTH, this.cover, 200, SpringLayout.NORTH, this.cover);

		this.add(this.emptyStar);
		layout.putConstraint(SpringLayout.NORTH, this.emptyStar, 25, SpringLayout.SOUTH, this.actorScroll);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.emptyStar, -500 / 4, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		this.emptyStar.setVisible(false);
		this.add(this.fullStar);
		layout.putConstraint(SpringLayout.NORTH, this.fullStar, 25, SpringLayout.SOUTH, this.actorScroll);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.fullStar, -500 / 4, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		this.fullStar.setVisible(false);

		this.add(this.trailer);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.trailer, 0, SpringLayout.VERTICAL_CENTER, this.emptyStar);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.trailer, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		this.trailer.setVisible(false);

		this.add(this.downloadCovers);
		layout.putConstraint(SpringLayout.NORTH, this.downloadCovers, 25, SpringLayout.SOUTH, this.actorScroll);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.downloadCovers, 500 / 4, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		this.downloadCovers.setVisible(false);

		this.add(this.lblPlot);
		layout.putConstraint(SpringLayout.NORTH, this.lblPlot, 25, SpringLayout.SOUTH, this.trailer);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblPlot, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		this.plot.setEditable(false);
		this.plot.setLineWrap(true);
		this.add(this.plotScroll);
		layout.putConstraint(SpringLayout.NORTH, this.plotScroll, 10, SpringLayout.SOUTH, this.lblPlot);
		layout.putConstraint(SpringLayout.WEST, this.plotScroll, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.plotScroll, -25, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.plotScroll, 135, SpringLayout.NORTH, this.plotScroll);
		//200 is the vertical size of the scrollPane

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -15, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.back, -25, SpringLayout.EAST, this.getContentPane());
		this.add(this.review);
		layout.putConstraint(SpringLayout.SOUTH, this.review, -15, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.review, -25, SpringLayout.WEST, this.back);
		this.add(this.buy);
		layout.putConstraint(SpringLayout.SOUTH, this.buy, -15, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.buy, -25, SpringLayout.WEST, this.review);
		this.buy.setVisible(false);

		this.back.addActionListener(this);
		this.buy.addActionListener(this);
		this.trailer.addActionListener(this);
		this.review.addActionListener(this);
		this.cover.addMouseListener(this);
		this.emptyStar.addMouseListener(this);
		this.fullStar.addMouseListener(this);
		this.downloadCovers.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final Film current = observer.getFilm();
				codFilm.setText("" + current.getCodeFilm());
				title.setText(current.getTitle());
				releaseYear.setText("" + current.getReleaseYear());
				genre.setText(current.getGenre().toString());
				plot.setText(current.getPlot());
				price.setText("€ " + current.getPrice());
				observer.doShowActors();
				cover.setIcon(observer.getNextCover());
				observer.getAverage(current.getCodeFilm());
				if (!observer.checkSociety()) {
					trailer.setVisible(true);
					downloadCovers.setVisible(true);
					if (observer.checkAdmin()) {
						buy.setVisible(false);
						emptyStar.setVisible(false);
					} else {
						buy.setVisible(true);
						emptyStar.setVisible(true);
						observer.doVisit();
						if (observer.isFavorite()) {
							emptyStar.setVisible(false);
							fullStar.setVisible(true);
						}
					}
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
		if (!this.observer.controllThread()) {
			final Object selection = e.getSource();
			if (selection.equals(this.back)) {
				this.observer.doBack();
			} else if (selection.equals(this.buy)) {
				this.observer.doBuy();
			} else if (selection.equals(this.trailer)) {
				this.observer.doTrailerView();
			} else if (selection.equals(this.review)) {
				this.observer.showReviews();
			}
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (!this.observer.controllThread()) {
			if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.cover)) {
				e.consume();
				this.cover.setIcon(this.observer.getNextCover());
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.emptyStar)) {
				e.consume();
				this.changeStar(this.observer.doFavorite());
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.fullStar)) {
				e.consume();
				this.changeStar(this.observer.doFavorite());
			} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.downloadCovers)) {
				this.observer.doDownloadCover();
			}
		}

	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param detailsFilmController controller for this view
	 */
	public void attachObserver(final DetailsFilmController detailsFilmController) {
		this.observer = detailsFilmController;
	}

	/**
	 * Set the star compared to the status of favorite of the film.
	 *
	 * @param star the star
	 */
	private void changeStar(final boolean star) {
		if (star) {
			this.emptyStar.setVisible(false);
			this.fullStar.setVisible(true);
		} else {
			this.emptyStar.setVisible(true);
			this.fullStar.setVisible(false);
		}
	}

	/**
	 * Add the row to the table.
	 * @param obj is the vector of object
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.actorTable.getModel()).addRow(obj);
	}

	/**
	 * Set the visibility of the star for the average vote of the film.
	 * @param star star
	 */
	public void setAverageStar(final int star) {
		if (star == 1) {
			this.emptyStar1.setVisible(false);
			this.fullStar1.setVisible(true);
		} else if (star == 2) {
			this.emptyStat2.setVisible(false);
			this.fullStar2.setVisible(true);
		} else if (star == 3) {
			this.emptyStar3.setVisible(false);
			this.fullStar3.setVisible(true);
		} else if (star == 4) {
			this.emptyStar4.setVisible(false);
			this.fullStar4.setVisible(true);
		} else if (star == 5) {
			this.emptyStar5.setVisible(false);
			this.fullStar5.setVisible(true);
		} else if (star == 6) {
			this.emptyStar1.setVisible(false);
			this.halfStar1.setVisible(true);
		} else if (star == 7) {
			this.emptyStat2.setVisible(false);
			this.halfStar2.setVisible(true);
		} else if (star == 8) {
			this.emptyStar3.setVisible(false);
			this.halfStar3.setVisible(true);
		} else if (star == 9) {
			this.emptyStar4.setVisible(false);
			this.halfStar4.setVisible(true);
		} else if (star == 10) {
			this.emptyStar5.setVisible(false);
			this.halfStar5.setVisible(true);
		}
	}
}
