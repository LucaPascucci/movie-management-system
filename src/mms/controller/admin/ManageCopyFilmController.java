package mms.controller.admin;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import mms.controller.AbstractController;
import mms.controller.interfaces.IManageCopyFilmController;
import mms.database.FileFilmTable;
import mms.database.FilmCopyTable;
import mms.database.FilmTable;
import mms.model.FileFilm;
import mms.model.Film;
import mms.model.FilmCopy;
import mms.model.IModel;
import mms.view.admin.ManageCopyFilmView;

/**
 * This class controls the management of the films in a shop.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ManageCopyFilmController extends AbstractController implements IManageCopyFilmController {

	/** The old copy film list. */
	private final List<Integer> oldCopyFilmList = new ArrayList<>();

	/** The new copy film list. */
	private final List<Integer> newCopyFilmList = new ArrayList<>();

	/** The cod shop. */
	private final Integer codShop;

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param item the shop code
	 */
	public ManageCopyFilmController(final IModel mod, final Integer item) {
		super(mod, ADD_COPY_FILM_VIEW);
		this.codShop = item;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((ManageCopyFilmView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, ADMIN_FILMS_VIEW, null);

	}

	@Override
	public void generateTables(final boolean society) {
		if (society) {
			try {
				Iterator<Film> filmsInSociety;
				filmsInSociety = new FilmTable().findMissingFilm(society, codShop).iterator();
				Film curr;
				while (filmsInSociety.hasNext()) {
					curr = filmsInSociety.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((ManageCopyFilmView) this.frame).newRowSociety(obj);
				}
			} catch (SQLException exc) {
				this.saveError(exc);
				this.showErrorDialog(SQL_ERROR);
			} catch (NullPointerException exc) {
				this.saveError(exc);
			}
		} else {
			try {
				Iterator<Film> filmsInShop;
				filmsInShop = new FilmTable().findMissingFilm(society, codShop).iterator();
				Film curr;
				while (filmsInShop.hasNext()) {
					curr = filmsInShop.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((ManageCopyFilmView) this.frame).newRowShop(obj);
				}
			} catch (SQLException exc) {
				this.saveError(exc);
				this.showErrorDialog(SQL_ERROR);
			} catch (NullPointerException exc) {
				this.saveError(exc); 
			}
		}
	}

	@Override
	public void fillCopyFilmList(final Integer data, final boolean control) {
		if (control) {
			oldCopyFilmList.add(data);
		} else {
			newCopyFilmList.add(data);
		}
	}

	@Override
	public void doManageCopyFilm() {
		try {
			for (final Integer i: this.oldCopyFilmList) {
				if (!this.newCopyFilmList.contains(i)) {
					final FilmCopy copy = new FilmCopy();
					copy.setCodShop(codShop);
					copy.setCodFilm(i);
					copy.setInsertionDate(new Date());
					copy.setNumPurchases(0);
					copy.setNumViews(0);
					final List<FileFilm> files = new FileFilmTable().getFileofFilminShop(codShop, i);
					if (files != null) {
						final Iterator<FileFilm> it = files.iterator();
						while (it.hasNext()) {
							final FileFilm curr = it.next();
							this.deleteAllFiles(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/" + curr.getCodShop() + "/" + curr.getCodFilm() + "." + curr.getName() + "." + curr.getExtension()));
						}
					}
					new FilmCopyTable().delete(copy);
				}
			}
			for (final Integer i: this.newCopyFilmList) {
				if (!this.oldCopyFilmList.contains(i)) {
					final FilmCopy copy = new FilmCopy();
					copy.setCodShop(codShop);
					copy.setCodFilm(i);
					copy.setInsertionDate(new Date());
					copy.setNumPurchases(0);
					copy.setNumViews(0);
					new FilmCopyTable().persist(copy);
				}
			}
			this.changeView(this.activeView, ADMIN_FILMS_VIEW, null);
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} 
	}

}
