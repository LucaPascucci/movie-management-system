package mms.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import mms.controller.interfaces.IDetailsFilmController;
import mms.database.ActorTable;
import mms.database.CardTable;
import mms.database.DiscountTable;
import mms.database.FavoriteTable;
import mms.database.FileFilmTable;
import mms.database.FilmCopyTable;
import mms.database.FilmTable;
import mms.database.PurchaseTable;
import mms.database.ReviewTable;
import mms.database.TypologyTable;
import mms.exception.AlreadyExistsException;
import mms.mediaworker.MediaPlayer;
import mms.model.Actor;
import mms.model.Card;
import mms.model.Discount;
import mms.model.Favorite;
import mms.model.FileFilm;
import mms.model.Film;
import mms.model.FilmCopy;
import mms.model.IModel;
import mms.model.Purchase;
import mms.model.Typology;
import mms.view.DetailsFilmView;

/**
 * This class manage the functions of the details of a film.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class DetailsFilmController extends AbstractController implements IDetailsFilmController {

	/** The Constant DUPLICATE_BUY. */
	private static final String DUPLICATE_BUY = "This film is already buyed!";

	/** The Constant SUCCESS_BUY. */
	private static final String SUCCESS_BUY = "Movie purchased!";

	/** The Constant SUCCESS_ZIP. */
	private static final String SUCCESS_ZIP = "Zip file created!";

	/** The Constant ERROR_ZIP. */
	private static final String ERROR_ZIP = "Error during creation Zip file";

	/** The Constant ERROR_FILENAME. */
	private static final String ERROR_FILENAME = "File name can't contain this char (.)";

	/** The Constant INFO_COVER. */
	private static final String INFO_COVER = "This film contain only default cover";

	/** The Constant NO_TRAILER. */
	private static final String NO_TRAILER = "This film don't have a trailer";

	/** The Constant DEFAULT_PATH. */
	private static final String DEFAULT_PATH = DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/";

	/** The Constant VIDEO_FORMAT. */
	private static final String VIDEO_FORMAT = "flv";

	/** The Constant VIDEO_NAME. */
	private static final Integer VIDEO_NAME = 0;

	/** The Constant IMAGE_FORMAT. */
	private static final String IMAGE_FORMAT = "png";

	/** The secondary thread. */
	private Thread secondaryThread = new Thread();

	/** The view trailer. */
	private MediaPlayer viewTrailer;

	/** The film code. */
	private final Integer filmCode;

	/** The return view. */
	private final Integer returnView;

	/** The current film. */
	private Film currentFilm;

	/** The shop code. */
	private final Integer shopCode;

	/** The cycle cover. */
	private boolean cycleCover = true;

	/** The it cover. */
	private Iterator<FileFilm> itCover;

	/** The covers. */
	private List<FileFilm> covers;

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param view this parameter pass the correct view that will be shown.
	 * @param film this parameter pass the code of the film that will be shown.
	 * @param realshopCode realshopCode  
	 */
	public DetailsFilmController(final IModel mod, final Integer view, final Integer film, final Integer realshopCode) {
		super(mod, DETAILS_FILM_VIEW);
		this.filmCode = film;
		this.returnView = view;
		this.shopCode = realshopCode;
		this.covers = null;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((DetailsFilmView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		if (!this.controllThread()) {
			this.changeView(this.activeView, this.returnView, null);
		}
	}

	@Override
	public void doDownloadCover() {
		if (this.covers != null) {
			if (this.fileDialog.showSaveDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
				this.downloadCovers(this.fileDialog.getSelectedFile().getPath(), this.fileDialog.getSelectedFile());
				this.showInfoDialog(SUCCESS_ZIP);
			}

		} else {
			this.showInfoDialog(INFO_COVER);
		}
	}

	@Override
	public void doTrailerView() {
		try {
			final FileFilm checkVideo = new FileFilmTable().findByPrimaryKey(shopCode, filmCode, VIDEO_NAME);
			if (checkVideo != null) {
				this.viewTrailer = new MediaPlayer(DEFAULT_PATH + this.shopCode + "/" + filmCode + "." + VIDEO_NAME + "." + VIDEO_FORMAT);
				if (!this.secondaryThread.isAlive()) {
					this.secondaryThread = new Thread() {
						public void run() {
							viewTrailer.startTrailer(currentFilm.getTitle());
						}
					};
					this.secondaryThread.start();
				}
			} else {
				this.showInfoDialog(NO_TRAILER);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}

	}

	@Override
	public boolean controllThread() {
		return this.secondaryThread.isAlive();
	}

	@Override
	public void doBuy() {
		try {
			final Purchase purchase = new Purchase();
			purchase.setCodMoviePurch(this.filmCode);
			purchase.setCodShopPurch(this.shopCode);
			purchase.setDatePurchase(new Date());
			final float cost = getTotalCostOfTheFilm();
			purchase.setPrice(cost);
			purchase.setUsername(this.model.getCurrentUser().getUsername());
			new PurchaseTable().persist(purchase);
			final FilmCopy copy = new FilmCopyTable().findByPrimaryKey(this.shopCode, this.filmCode);
			copy.setNumPurchases(copy.getNumPurchases() + 1);
			new FilmCopyTable().update(copy);
			Card card = new CardTable().findByUsername(this.model.getCurrentUser().getUsername());
			if (card != null) {
				card.setScoreCard(card.getScoreCard() + (int) cost);
				new CardTable().update(card);
			}
			this.showInfoDialog(SUCCESS_BUY);
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} catch (AlreadyExistsException e) {
			this.saveError(e);
			this.showWarningDialog(DUPLICATE_BUY);
		}
	}

	/**
	 * Gets the total cost of the film.
	 *
	 * @return the total cost of the film
	 */
	private float getTotalCostOfTheFilm() {
		float cost = 0;
		Card card = null;
		try {
			final Film film = new FilmTable().findByPrimaryKey(this.filmCode);
			cost = film.getPrice();
			card = new CardTable().findByUsername(this.model.getCurrentUser().getUsername());
			if (card != null) {
				final Typology typology = new TypologyTable().findByPrimaryKey(card.getNumCard());
				if (typology != null) {
					final Discount discount = new DiscountTable().findByPrimaryKey(typology.getCodDiscount());
					final float percentage = discount.getPercentage();
					cost =  cost - (cost * percentage) / 100;	
				}
				card.setScoreCard(card.getScoreCard() + (int) cost);
				new CardTable().update(card);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
		return cost;
	}

	@Override
	public boolean doFavorite() {
		final Favorite favorite = new Favorite();
		favorite.setCodFilm(this.filmCode);
		favorite.setUsername(this.model.getCurrentUser().getUsername());
		try {
			new FavoriteTable().persist(favorite);
			return true;
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		} catch (AlreadyExistsException exc) {
			this.saveError(exc);
			try {
				new FavoriteTable().delete(favorite);
				return false;
			} catch (SQLException exc1) {
				this.saveError(exc1);
				this.showErrorDialog(SQL_ERROR);
			}
		}
		return false;
	}

	@Override
	public Film getFilm() {
		try {
			this.currentFilm = new FilmTable().findByPrimaryKey(this.filmCode);
			if (!this.checkSociety()) {
				this.covers = new FileFilmTable().getFilmCoversinShop(this.shopCode, this.filmCode, IMAGE_FORMAT);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
		return currentFilm;
	}

	@Override
	public void doShowActors() {
		try {
			final Iterator<Actor> actors = new ActorTable().getActorsStarringInAFilm(this.filmCode).iterator();
			Actor curr;
			Object[] obj;
			while (actors.hasNext()) {
				curr = actors.next();
				obj = new Object[]{curr.getName(), curr.getSurname()};
				((DetailsFilmView) this.frame).newRow(obj);
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} catch (NullPointerException e) {
			this.saveError(e);
		}
	}

	@Override
	public void doVisit() {
		try {
			final FilmCopy filmCopy = new FilmCopyTable().findByPrimaryKey(this.shopCode, this.filmCode);
			filmCopy.setNumViews(filmCopy.getNumViews() + 1);
			new FilmCopyTable().update(filmCopy);
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
	}

	@Override
	public Icon getNextCover() {
		if (this.covers == null) {
			return new ImageIcon(this.getClass().getResource("/" + DEFAULT_COVER));
		} else {
			FileFilm curr;
			if (this.cycleCover) {
				itCover = this.covers.iterator();
				this.cycleCover = false;
			}
			curr = itCover.next();
			if (!itCover.hasNext()) {
				this.cycleCover = true;
			}
			return new ImageIcon(DEFAULT_PATH + curr.getCodShop() + "/" + curr.getCodFilm() + "." + curr.getName() + "." + curr.getExtension());
		}
	}

	@Override
	public boolean isFavorite() {
		try {
			if (new FavoriteTable().findByPrimaryKey(this.filmCode, this.model.getCurrentUser().getUsername()) != null) {
				return true;
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
		return false;
	}

	/**
	 * This Method downloads the covers of a film selected by the parameter.
	 * @param directory this parameter pass the path where will be found the file.
	 * @param filePath this parameter identify the file in the computer.
	 */
	private void downloadCovers(final String directory, final File filePath) {
		try {
			final String fileName = filePath.getName();
			if (!fileName.contains(".")) {
				// definiamo l'output previsto che sara'  un file in formato zip
				final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(directory + ZIP_FORMAT)));

				// definiamo il buffer per lo stream di bytes
				final byte[] data = new byte[BUFFER_SIZE];
				for (final FileFilm currFile : this.covers) {
					final BufferedInputStream in = new BufferedInputStream(new FileInputStream(DEFAULT_PATH + currFile.getCodShop() + "/" + currFile.getCodFilm() + "." + currFile.getName() + "." + currFile.getExtension()));
					int count;

					// processo di compressione
					out.putNextEntry(new ZipEntry(currFile.getCodFilm() + "." + currFile.getName() + "." + currFile.getExtension()));
					while ((count = in.read(data)) > 0) {
						out.write(data, 0, count);
					}
					in.close();
				}
				out.flush();
				out.close();
			} else {
				this.showErrorDialog(ERROR_FILENAME);
			}
		} catch (FileNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_ZIP);
		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_ZIP);
		}

	}

	@Override
	public void showReviews() {
		this.frame.setName(shopCode.toString() + this.returnView);
		this.changeView(DETAILS_FILM_VIEW, REVIEW_VIEW, this.filmCode);
	}

	@Override
	public boolean checkAdmin() {
		return this.model.getUserType().equals("admin");
	}

	@Override
	public boolean checkSociety() {
		return this.model.getUserType().equals("society");
	}

	@Override
	public void getAverage(final int codeFilm) {
		try {
			Integer avg = new ReviewTable().getAverageOfAFilm(filmCode);
			int i = 1;
			while (avg > 1) {
				((DetailsFilmView) this.frame).setAverageStar(i);
				i++;
				avg = avg - 2;
			}
			if (avg == 1) {
				((DetailsFilmView) this.frame).setAverageStar(i + 5);
			}


		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
	}
}
