package mms.controller;

import java.io.File;
import java.sql.SQLException;

import javax.swing.JFrame;

import mms.controller.interfaces.ILoginController;
import mms.database.AdministratorTable;
import mms.database.CustomerTable;
import mms.model.Administrator;
import mms.model.Customer;
import mms.model.IModel;
import mms.view.LoginView;

/**
 * This class manage the functions of the login.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class LoginController extends AbstractController implements ILoginController {

	/** The Constant ERROR_LOGIN. */
	private static final String ERROR_LOGIN = "No user found with the provided credentials!";

	/** The Constant ERROR_PSWD. */
	private static final String ERROR_PSWD = "Incorrect password!";
	/**
	 * This is the construct of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public LoginController(final IModel mod) {
		super(mod, LOGIN_VIEW);
		this.model.setCurrentUser(null);
		this.model.setUserType(null);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((LoginView) this.frame).attachObserver(this);

	}

	@Override
	public void doLogin(final String username, final String password, final boolean admin, final boolean user) {
		try {
			if (password.length() == 0 || username.length() == 0) {
				this.showWarningDialog(ERROR_INSERT);
			} else {
				if (admin) {
					final Administrator control = new AdministratorTable().findByPrimaryKey(username);
					if (control == null) {
						this.showErrorDialog(ERROR_LOGIN);
						((LoginView) this.frame).resetField();
					} else {
						if (control.getPassword().equals(password)) {
							this.model.setCurrentUser(control);
							this.model.setUserType("admin");
							this.changeView(this.activeView, MENU_VIEW, null);
						} else {
							this.showErrorDialog(ERROR_PSWD);
							((LoginView) this.frame).resetField();
						}
					}
				} else if (user) {
					final Customer control = new CustomerTable().findByPrimaryKey(username);
					if (control == null) {
						this.showErrorDialog(ERROR_LOGIN);
						((LoginView) this.frame).resetField();
					} else {
						if (control.getPassword().equals(password)) {
							this.model.setCurrentUser(control);
							this.model.setUserType("customer");
							this.changeView(this.activeView, MENU_VIEW, null);
						} else {
							this.showErrorDialog(ERROR_PSWD);
							((LoginView) this.frame).resetField();
						}
					}
				} else {
					if (username.equals(this.model.getSociety().getUsername())) {
						if (password.equals(this.model.getSociety().getPassword())) {
							this.model.setCurrentUser(this.model.getSociety());
							this.model.setUserType("society");
							this.changeView(this.activeView, MENU_VIEW, null);
						} else {
							this.showErrorDialog(ERROR_PSWD);
							((LoginView) this.frame).resetField();
						}
					} else {
						this.showErrorDialog(ERROR_LOGIN);
						((LoginView) this.frame).resetField();
					}
				}
				this.saveDataCmd();
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
	}

	@Override
	public void doNewUser() {
		this.changeView(this.activeView, EDITABLE_PERSON_VIEW, null);
	}

	@Override
	public final void doBack() {
		this.doExit();
	}

	@Override
	public final void createWorkspace() {

		if (!this.resourcesFolderExist(DEFAULT_USER_PATH)) {
			new File(DEFAULT_USER_PATH).mkdir();
		}
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH + DEFAULT_BACKUP_PATH)) {
			new File(DEFAULT_USER_PATH + DEFAULT_BACKUP_PATH).mkdir();
		}
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH)) {
			new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH).mkdir();
		}
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/")) {
			new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/").mkdir();
		}
		if (!this.resourcesFolderExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "users/")) {
			new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "users/").mkdir();
		}
	}

}
