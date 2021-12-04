package mms.controller.admin;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import mms.controller.AbstractController;
import mms.controller.interfaces.IAdminStatsController;
import mms.database.AdministratorTable;
import mms.database.CustomerTable;
import mms.database.FilmTable;
import mms.database.PurchaseTable;
import mms.database.ShopTable;
import mms.model.Administrator;
import mms.model.Customer;
import mms.model.Film;
import mms.model.IModel;
import mms.view.admin.AdminStatsView;
/**
 * This class manage the functions of the statistics of the admin.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class AdminStatsController extends AbstractController implements IAdminStatsController {

	/** The Constant CHANGED_PASSWORD. */
	private static final String CHANGED_PASSWORD = "Password changed successfully";

	/** The Constant SELECT_SHOP. */
	private static final String SELECT_SHOP = "Select a shop.";

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public AdminStatsController(final IModel mod) {
		super(mod, ADMIN_STATS_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((AdminStatsView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void generateTableRecent(final Integer currShop) {
		try {
			((AdminStatsView) this.frame).refreshTable();
			List<Film> films = null;
			Iterator<Film> itFilms = null;
			Film curr;
			final Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH , -1);
			if (currShop != null) {
				films = new FilmTable().orderByInsertionDate(currShop, calendar.getTime());
				((AdminStatsView) this.frame).setTotalFilms(films.size());
				itFilms = films.iterator();
				while (itFilms.hasNext()) {
					curr = itFilms.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((AdminStatsView) this.frame).newRow(obj);
				}
			} else {
				films = new FilmTable().getAllFilmsOfAnAdminOrderedByInsDate(this.model.getCurrentUser().getUsername(), calendar.getTime());
				itFilms = films.iterator();
				while (itFilms.hasNext()) {
					curr = itFilms.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((AdminStatsView) this.frame).newRow(obj);
				}
			}
		} catch (SQLException e) {
			this.saveError(e);
		} catch (NullPointerException e) {
			((AdminStatsView) this.frame).setTotalFilms(0);
			this.showErrorDialog(NO_FILM_IN_THIS_SHOP);
		}

	}

	@Override
	public void generateTableFilmList(final Integer currShop) {
		try {
			((AdminStatsView) this.frame).refreshTable();
			List<Film> films = null;
			Iterator<Film> itFilms = null;
			Film curr;
			if (currShop != null) {
				films = new FilmTable().findFilmOfAShop(currShop);
				itFilms = films.iterator();
				while (itFilms.hasNext()) {
					curr = itFilms.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((AdminStatsView) this.frame).newRow(obj);
				}
			} else {
				films = new FilmTable().getAllFilmsOfAnAdmin(this.model.getCurrentUser().getUsername());
				itFilms = films.iterator();
				while (itFilms.hasNext()) {
					curr = itFilms.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((AdminStatsView) this.frame).newRow(obj);
				}
			}
			((AdminStatsView) this.frame).setTotalFilms(films.size());
		} catch (SQLException e) {
			this.saveError(e);
		} catch (NullPointerException e) {
			((AdminStatsView) this.frame).setTotalFilms(0);
			this.showInfoDialog(NO_FILM_IN_THIS_SHOP);
		}
	}

	@Override
	public void generateTableTopVisited(final Integer currShop) {
		try {
			((AdminStatsView) this.frame).refreshTable();
			List<Film> films = null;
			Iterator<Film> itFilms = null;
			Film curr;
			if (currShop != null) {
				films = new FilmTable().orderByViewsOrPurchases(currShop, true);
				((AdminStatsView) this.frame).setTotalFilms(films.size());
				itFilms = films.iterator();
				while (itFilms.hasNext()) {
					curr = itFilms.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((AdminStatsView) this.frame).newRow(obj);
				}
			} else {
				films = new FilmTable().getAllFilmsOfAnAdminOrderedByViews(this.model.getCurrentUser().getUsername());
				itFilms = films.iterator();
				while (itFilms.hasNext()) {
					curr = itFilms.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((AdminStatsView) this.frame).newRow(obj);
				}
			}
		} catch (SQLException e) {
			this.saveError(e);
		} catch (NullPointerException e) {
			this.showInfoDialog(NO_FILM_IN_THIS_SHOP);
		}
	}

	@Override
	public void generateTableTopBought(final Integer currShop) {
		try {
			((AdminStatsView) this.frame).refreshTable();
			List<Film> films = null;
			Iterator<Film> itFilms = null;
			Film curr;
			if (currShop != null) {
				films = new FilmTable().orderByViewsOrPurchases(currShop, false);
				((AdminStatsView) this.frame).setTotalFilms(films.size());
				itFilms = films.iterator();
				while (itFilms.hasNext()) {
					curr = itFilms.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((AdminStatsView) this.frame).newRow(obj);
				}
			} else {
				films = new FilmTable().getAllFilmsOfAnAdminOrderedByPurchases(this.model.getCurrentUser().getUsername());
				itFilms = films.iterator();
				while (itFilms.hasNext()) {
					curr = itFilms.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((AdminStatsView) this.frame).newRow(obj);
				}
			}
		} catch (SQLException exc) {
			this.saveError(exc);
		} catch (NullPointerException exc) {
			this.showInfoDialog(NO_FILM_IN_THIS_SHOP);
		}
	}

	@Override
	public void showDetailsFilm(final Integer filmCode, final Integer shopCode) {
		if (shopCode != null) {
			this.frame.setName(shopCode.toString());
			this.changeView(ADMIN_STATS_VIEW, DETAILS_FILM_VIEW, filmCode);
		} else {
			this.showWarningDialog(SELECT_SHOP);
		}
	}

	@Override
	public void doChangePassword() {
		try {
			final JPasswordField passwordField = new JPasswordField();
			final Object[] obj = {"Please enter the new password:\n", passwordField};
			final Object[] stringArray = {"OK", "Cancel"};
			if (JOptionPane.showOptionDialog(this.frame, obj, "Change password", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION && new String(passwordField.getPassword()).length() != 0) {
				final Administrator admin = new AdministratorTable().findByPrimaryKey(this.model.getCurrentUser().getUsername());
				admin.setPassword(new String(passwordField.getPassword()));
				new AdministratorTable().update(admin);
				this.showInfoDialog(CHANGED_PASSWORD);

			}
		} catch (SQLException exc) {
			this.saveError(exc);
		}

	}

	@Override
	public void doFillCmb() {
		try {
			final Iterator<Integer> codes = new ShopTable().getShopCodesOfAnAdmin(this.model.getCurrentUser().getUsername()).iterator();
			Integer curr;
			while (codes.hasNext()) {
				curr = codes.next();
				((AdminStatsView) this.frame).fillAdminCMB(new String(curr.toString()));
			}
		} catch (SQLException e) {
			this.saveError(e);
		} catch (NullPointerException ecx) {
			this.showWarningDialog("This administator haven't shops!");
		}
	}

	@Override
	public void setTotalCustomer(final Integer currShop) {
		try {
			List<Customer> customers = null;
			if (currShop != null) {
				customers = new CustomerTable().findUsersSubscribedToAShop(currShop);

			} else {
				customers = new CustomerTable().findUsersSubscribedToAdminShops(this.model.getCurrentUser().getUsername());
			}
			((AdminStatsView) this.frame).setTotalCustomer(customers.size());
		} catch (NullPointerException exc) {
			((AdminStatsView) this.frame).setTotalCustomer(0);
		} catch (SQLException exc) {
			this.saveError(exc);
		}

	}

	@Override
	public void setTotalIncomes(final Integer currShop) {
		float incomes = 0;
		if (currShop == null) {
			try {
				incomes = new PurchaseTable().getTotalIncomesOfAnAdmin(this.model.getCurrentUser().getUsername());
			} catch (SQLException e) {
				this.saveError(e);
				this.showErrorDialog(SQL_ERROR);
			}
			((AdminStatsView) this.frame).setTotalIncomes(incomes);
		} else {
			try {
				incomes = new PurchaseTable().getTotalIncomesOfAShop(currShop);
			} catch (SQLException e) {
				this.saveError(e);
				this.showErrorDialog(SQL_ERROR);
			}
			((AdminStatsView) this.frame).setTotalIncomes(incomes);
		}
	}

}