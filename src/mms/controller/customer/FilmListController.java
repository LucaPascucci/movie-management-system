package mms.controller.customer;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JFrame;

import mms.controller.AbstractController;
import mms.controller.interfaces.IFilmListController;
import mms.database.CardTable;
import mms.database.FilmTable;
import mms.database.ShopTable;
import mms.exception.AlreadyExistsException;
import mms.model.Card;
import mms.model.Film;
import mms.model.IModel;
import mms.view.customer.FilmListView;
/**
 * This class manage the films list of the application.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FilmListController extends AbstractController implements IFilmListController {

	/** The Constant CARD_SUBSCRIBED. */
	private static final String CARD_SUBSCRIBED = "Card added to your profile.";

	/** The Constant NOT_HAVE_CARD. */
	private static final String NOT_HAVE_CARD = "To select a discount you must have a card.";

	/** The Constant DUPLICATE_CARD. */
	private static final String DUPLICATE_CARD = "You have already a shop card.";

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public FilmListController(final IModel mod) {
		super(mod, FILM_LIST_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((FilmListView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);

	}

	@Override
	public void doFillCmb() {
		try {
			final Iterator<Integer> codes = new ShopTable().getShopCodesOfACustomer(this.model.getCurrentUser().getUsername()).iterator();
			Integer curr;
			while (codes.hasNext()) {
				curr = codes.next();
				((FilmListView) this.frame).fillShopCMB(new String(curr.toString()));
			}
		} catch (SQLException e) {
			this.saveError(e);
		}
	}

	@Override
	public void generateTableFilmList(final Integer currShop, final Object[] filter) {
		if (currShop == null) {
			this.showWarningDialog("Select a Shop!");
		} else {
			((FilmListView) this.frame).refreshTable();
			try {
				final Iterator<Film> films = new FilmTable().getCorrectFilmList(currShop, filter, 1, null).iterator();
				Film curr;
				while (films.hasNext()) {
					curr = films.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((FilmListView) this.frame).newRow(obj);
				}
			} catch (SQLException e) {
				this.saveError(e);
			} catch (NullPointerException e) {
				this.showErrorDialog(NO_FILM_IN_THIS_SHOP);
			}
		}
	}

	@Override
	public void generateTableTopVisited(final Integer currShop, final Object[] filter) {
		if (currShop == null) {
			this.showWarningDialog("Select a Shop!");
		} else {
			((FilmListView) this.frame).refreshTable();
			try {
				final Iterator<Film> films = new FilmTable().getCorrectFilmList(currShop, filter, 2, null).iterator();
				Film curr;
				while (films.hasNext()) {
					curr = films.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((FilmListView) this.frame).newRow(obj);
				}
			} catch (SQLException e) {
				this.saveError(e);
			} catch (NullPointerException e) {
				this.showErrorDialog(NO_FILM_IN_THIS_SHOP);
			}
		}
	}

	@Override
	public void generateTableTopBought(final Integer currShop, final Object[] filter) {
		if (currShop == null) {
			this.showWarningDialog("Select a Shop!");
		} else {
			((FilmListView) this.frame).refreshTable();
			try {
				final Iterator<Film> films = new FilmTable().getCorrectFilmList(currShop, filter, 3, null).iterator();
				Film curr;
				while (films.hasNext()) {
					curr = films.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((FilmListView) this.frame).newRow(obj);
				}
			} catch (SQLException e) {
				this.saveError(e);
			} catch (NullPointerException e) {
				this.showErrorDialog(NO_FILM_IN_THIS_SHOP);
			}
		}
	}

	@Override
	public void generateTableRecent(final Integer currShop, final Object[] filter) {
		if (currShop == null) {
			this.showWarningDialog("Select a shop!");
		} else {
			((FilmListView) this.frame).refreshTable();
			try {
				final Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH , -1);
				final Iterator<Film> films = new FilmTable().getCorrectFilmList(currShop, filter, 4, calendar.getTime()).iterator();
				Film curr;
				while (films.hasNext()) {
					curr = films.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((FilmListView) this.frame).newRow(obj);
				}
			} catch (SQLException e) {
				this.saveError(e);
			} catch (NullPointerException e) {
				this.showErrorDialog(NO_FILM_IN_THIS_SHOP);
			}
		}

	}

	@Override
	public void showDetailsFilm(final Integer filmCode, final Integer shopCode) {
		this.frame.setName(shopCode.toString());
		this.changeView(this.activeView, DETAILS_FILM_VIEW, filmCode);

	}

	@Override
	public void getNewCard() {
		try {
			final Card card = new Card();
			card.setReleaseDate(new Date());
			card.setScoreCard(0);
			card.setUsername(this.model.getCurrentUser().getUsername());
			new CardTable().persist(card);
			this.showInfoDialog(CARD_SUBSCRIBED);
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} catch (AlreadyExistsException e) {
			this.saveError(e);
			this.showWarningDialog(DUPLICATE_CARD);
		}		
	}

	@Override
	public void selectDiscount() {
		try {
			final Card card = new CardTable().findByUsername(this.model.getCurrentUser().getUsername());
			if (card == null) {
				this.showWarningDialog(NOT_HAVE_CARD);
			} else {
				this.changeView(FILM_LIST_VIEW, EDITABLE_CARD_VIEW, card);
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
	}

}
