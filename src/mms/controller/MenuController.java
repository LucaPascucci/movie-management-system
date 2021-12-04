package mms.controller;

import javax.swing.JFrame;

import mms.controller.interfaces.IMenuController;
import mms.model.IModel;
import mms.view.MenuView;

/**
 * This class is one of the principal view of the application.
 * By the functions of this class the current user can travel in the application.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class MenuController extends AbstractController implements IMenuController {

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public MenuController(final IModel mod) {
		super(mod, MENU_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((MenuView) this.frame).attachObserver(this);

	}
	@Override
	public void backToHome() {
		this.changeView(this.activeView, LOGIN_VIEW, null);
	}

	@Override
	public boolean getUserControll() {
		return this.model.getUserType().equals("customer");
	}

	@Override
	public boolean getAdminControll() {
		return this.model.getUserType().equals("admin");
	}

	@Override
	public void doFirst() {
		if (this.getUserControll()) {
			this.changeView(this.activeView, FILM_LIST_VIEW, null);
		} else if (this.getAdminControll()) {
			this.changeView(this.activeView, ADMIN_FILMS_VIEW, null);
		} else {
			this.changeView(this.activeView, MANAGE_SOCIETY_VIEW, null);
		}
	}

	@Override
	public void doSecond() {
		if (this.getUserControll()) {
			this.changeView(this.activeView, FAVORITE_FILM_VIEW, null);
		} else if (this.getAdminControll()) {
			this.changeView(this.activeView, MANAGE_CUSTOMERS_VIEW, null);
		} else {
			this.changeView(this.activeView, SOCIETY_FILM_VIEW, null);
		}
	}

	@Override
	public void doThird() {
		if (this.getUserControll()) {
			this.changeView(this.activeView, CUSTOMER_STATS_VIEW, null);
		} else if (this.getAdminControll()) {
			this.changeView(this.activeView, ADMIN_STATS_VIEW, null);
		} else {
			this.changeView(this.activeView, MANAGE_SHOP_VIEW, null);
		}
	}

	@Override
	public void doBack() {
		this.backToHome();
	}

}
