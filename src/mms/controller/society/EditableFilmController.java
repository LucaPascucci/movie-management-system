package mms.controller.society;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import mms.controller.AbstractController;
import mms.controller.interfaces.IEditableFilmController;
import mms.database.ActTable;
import mms.database.ActorTable;
import mms.database.FilmTable;
import mms.model.Act;
import mms.model.Actor;
import mms.model.Film;
import mms.model.FilmType;
import mms.model.IModel;
import mms.view.society.EditableFilmView;

/**
 * This class manage the functions of EditableFilmController.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class EditableFilmController extends AbstractController implements IEditableFilmController {

	/** The Constant INVALID_NUMBER. */
	private static final String INVALID_NUMBER = "Insert a number in Price and Release Year";

	/** The Constant SELECT_GENRE. */
	private static final String SELECT_GENRE = "Select a Film Genre!";

	/** The Constant INSERT_TITLE. */
	private static final String INSERT_TITLE = "Insert film title!";

	/** The Constant SELECT_ACTOR. */
	private static final String SELECT_ACTOR = "Select at least one Actor!";

	/** The editable film. */
	private final Integer editableFilm;

	/** The old actors. */
	private List<Integer> oldActors;

	/** The selected actors. */
	private final List<Integer> selectedActors;

	/**
	 * This is the construct of EditableFilmController.
	 * @param mod this parameter pass the model to the constructor.
	 * @param filmCode this parameter pass the code of the film.
	 */
	public EditableFilmController(final IModel mod, final Integer filmCode) {
		super(mod, EDITABLE_FILM_VIEW);
		this.editableFilm = filmCode;
		this.oldActors = new LinkedList<>();
		this.selectedActors = new LinkedList<>();
	}

	/* (non-Javadoc)
	 * @see mms.controller.AbstractController#setView(javax.swing.JFrame)
	 */
	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((EditableFilmView) this.frame).attachObserver(this);
	}

	/* (non-Javadoc)
	 * @see mms.controller.interfaces.IEditableFilmController#getEditableFilm()
	 */
	@Override
	public Film getEditableFilm() {
		try {
			if (this.editableFilm != null) {
				this.oldActors = new ActTable().findByCode(this.editableFilm, true);
				return new FilmTable().findByPrimaryKey(this.editableFilm);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see mms.controller.AbstractController#doBack()
	 */
	@Override
	public void doBack() {
		this.changeView(this.activeView, SOCIETY_FILM_VIEW, null);
	}

	/* (non-Javadoc)
	 * @see mms.controller.interfaces.IEditableFilmController#doConfirm(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, mms.model.FilmType)
	 */
	@Override
	public void doConfirm(final String title, final String price, final String plot, final Integer year, final FilmType currType) {
		if (plot.length() < 8001 && title.length() < 101) {
			try {
				if (title.length() != 0) {
					if (currType != null) {
						if (!this.selectedActors.isEmpty()) {
							final Film film = new Film();
							film.setGenre(currType);
							film.setPlot(plot);
							film.setReleaseYear(year);
							film.setPrice(Float.valueOf(price));
							film.setTitle(title);
							List<Integer> removeAct = null;
							List<Integer> addAct = null;
							if (this.editableFilm != null) {
								film.setCodeFilm(this.editableFilm);
								new FilmTable().update(film);
								removeAct = new LinkedList<>(this.oldActors);
								removeAct.removeAll(this.selectedActors);
								addAct = new LinkedList<>(this.selectedActors);
								addAct.removeAll(this.oldActors);
							} else {
								new FilmTable().persist(film);
							}
							Iterator<Integer> it;
							final Act act = new Act();
							if (this.editableFilm == null) {
								it = this.selectedActors.iterator();
								final int filmCode = new FilmTable().getMaxFilmCode();
								while (it.hasNext()) {
									act.setCodFilm(filmCode);
									act.setCodActor(it.next());
									new ActTable().persist(act);
								}
							} else {
								it = addAct.iterator();
								while (it.hasNext()) {
									act.setCodFilm(this.editableFilm);
									act.setCodActor(it.next());
									new ActTable().persist(act);
								}
								it = removeAct.iterator();
								while (it.hasNext()) {
									act.setCodFilm(this.editableFilm);
									act.setCodActor(it.next());
									new ActTable().delete(act);
								}
							}
							this.doBack();
						} else {
							this.showErrorDialog(SELECT_ACTOR);
						}
					} else {
						this.showErrorDialog(SELECT_GENRE);
					}
				} else {
					this.showErrorDialog(INSERT_TITLE);
				}

			} catch (NumberFormatException exc) {
				this.saveError(exc);
				this.showErrorDialog(INVALID_NUMBER);
				((EditableFilmView) this.frame).resetNumberField();
			} catch (SQLException exc) {
				this.saveError(exc);
				this.showErrorDialog(SQL_ERROR);
			}
		} else {
			this.showErrorDialog(ERROR_INSERT);
		}
	}

	/* (non-Javadoc)
	 * @see mms.controller.interfaces.IEditableFilmController#doShowActors()
	 */
	@Override
	public void doShowActors() {
		try {
			final Iterator<Actor> actors = new ActorTable().findAll().iterator();
			Actor curr;
			Object[] obj;
			while (actors.hasNext()) {
				curr = actors.next();
				if (this.oldActors.contains((Integer) curr.getCodActor())) {
					obj = new Object[]{curr.getCodActor(), curr.getName(), curr.getSurname(), Boolean.TRUE};
				} else {
					obj = new Object[]{curr.getCodActor(), curr.getName(), curr.getSurname(), Boolean.FALSE};
				}
				((EditableFilmView) this.frame).newRow(obj);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		} catch (NullPointerException exc) {
			this.saveError(exc);
		}
	}

	/* (non-Javadoc)
	 * @see mms.controller.interfaces.IEditableFilmController#doManageActor(java.lang.Integer, boolean)
	 */
	@Override
	public void doManageActor(final Integer actorCode, final boolean check) {
		if (check) {
			this.selectedActors.add(actorCode);
		}
	}
}
