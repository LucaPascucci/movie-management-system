package mms.controller.society;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mms.controller.AbstractController;
import mms.controller.interfaces.ISocietyFilmController;
import mms.database.ActTable;
import mms.database.ActorTable;
import mms.database.FileFilmTable;
import mms.database.FilmTable;
import mms.model.Actor;
import mms.model.FileFilm;
import mms.model.Film;
import mms.model.IModel;
import mms.view.society.SocietyFilmView;

/**
 * Controller for the society film view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class SocietyFilmController extends AbstractController implements ISocietyFilmController {

	/** The Constant DELETE_LINKED_FILMS. */
	private static final String DELETE_LINKED_FILMS = "Films will be removed\nProceed?";

	/**
	 * Constructor for this class.
	 * @param mod the model
	 */
	public SocietyFilmController(final IModel mod) {
		super(mod, SOCIETY_FILM_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((SocietyFilmView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void doAdd(final boolean control) {
		if (control) {
			this.changeView(this.activeView, EDITABLE_FILM_VIEW, null);
		} else {
			this.changeView(this.activeView, EDITABLE_PERSON_VIEW, null);
		}
	}

	@Override
	public void doEdit(final boolean control, final Integer code) {
		if (control) {
			this.changeView(this.activeView, EDITABLE_FILM_VIEW, code);
		} else {
			this.changeView(this.activeView, EDITABLE_PERSON_VIEW, code);
		}

	}

	@Override
	public void generateTable(final boolean control) {
		try {
			if (control) {
				final Iterator<Film> films = new FilmTable().findAll().iterator();
				Film curr;
				while (films.hasNext()) {
					curr = films.next();
					final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), new Float(curr.getPrice())};
					((SocietyFilmView) this.frame).newRow(obj);
				}
				((SocietyFilmView) this.frame).setTableToolTipText("Double click to see details");
			} else {
				final Iterator<Actor> actors = new ActorTable().findAll().iterator();
				Actor curr;
				final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				while (actors.hasNext()) {
					curr = actors.next();
					final Date birthdate = curr.getBirthDate();
					final String date = df.format(birthdate);
					final Object[] obj = new Object[]{curr.getCodActor(), curr.getName(), curr.getSurname(), date, curr.getNationality()};
					((SocietyFilmView) this.frame).newRow(obj);
				}
				((SocietyFilmView) this.frame).setTableToolTipText("Double click to edit");
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} catch (NullPointerException e) { //lanciata quando viene generata la tabella ma non sono presenti admin nel db
			this.saveError(e);
			((SocietyFilmView) this.frame).setTableToolTipText(null);
		}
	}

	@Override
	public boolean doDelete(final boolean control, final Integer code) {
		try {
			if (control) {
				this.deleteFilesOfaFilm(code);
				new FilmTable().delete(new FilmTable().findByPrimaryKey(code));
			} else {
				final List<Integer> linkedFIlms = new ActTable().findFilmOnlyOneActor(code);
				if (linkedFIlms != null) {
					if (this.showQuestionDialog(linkedFIlms.size() + " " + DELETE_LINKED_FILMS) == JOptionPane.YES_OPTION) {
						final Iterator<Integer> it = linkedFIlms.iterator();
						while (it.hasNext()) {
							this.deleteFilesOfaFilm(code);
							new FilmTable().delete(new FilmTable().findByPrimaryKey(it.next()));
						}
						new ActorTable().delete(new ActorTable().findByPrimaryKey(code));
					}
				} else {
					new ActorTable().delete(new ActorTable().findByPrimaryKey(code));
				}
			}
			return true;
		}	catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
		return false;
	}

	@Override
	public void showDetailsFilm(final Integer filmCode) {
		this.frame.setName("" + 0);
		this.changeView(SOCIETY_FILM_VIEW, DETAILS_FILM_VIEW, filmCode);
	}

	/**
	 * Delete files ofa film.
	 *
	 * @param codFilm the cod film
	 */
	private void deleteFilesOfaFilm(final int codFilm) {
		List<FileFilm> files;
		try {
			files = new FileFilmTable().allFilesOfFilm(codFilm);
			if (files != null) {
				final Iterator<FileFilm> it = files.iterator();
				while (it.hasNext()) {
					final FileFilm curr = it.next();
					this.deleteAllFiles(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/" + curr.getCodShop() + "/" + curr.getCodFilm() + "." + curr.getName() + "." + curr.getExtension()));
				}
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}

	}

}
