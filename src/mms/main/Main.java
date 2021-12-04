package mms.main;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mms.controller.LoginController;
import mms.database.DBConnection;
import mms.database.TypologyTable;
import mms.model.IModel;
import mms.model.Model;
import mms.view.LoginView;

/**
 * <h1>Movie Management System</h1>
 * Movie Management System [MMS] is an application developed
 * for the course of Data Base of Computer 
 * Science and Engineering that models a management of a video library.
 * 
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 * @version 1.0
 * @since 2014
 *
 */
public final class Main {

	/** The Constant GRAPHITE_LOOK_AND_FELL. */
	private static final String GRAPHITE_LOOK_AND_FELL = "com.jtattoo.plaf.graphite.GraphiteLookAndFeel";

	/**
	 * Constructor should never be called.
	 */
	private Main() { }

	/**
	 * The main method.
	 *
	 * @param args The command line parameters.
	 */
	public static void main(final String[] args) {

		setGUI();

		final IModel model = new Model();
		final LoginController controller = new LoginController(model);
		final LoginView view = new LoginView();
		controller.loadDataCmd();
		controller.createWorkspace();
		controller.saveDataCmd();
		controller.setView(view);
		try {
			if (new DBConnection().checkDB()) {
				new TypologyTable().checkAllDiscount();
			}
		} catch (SQLException exc) {
			controller.saveError(exc);
		}
		view.setVisible(true);

	}

	/**
	 * This method modify the look of the swing setting UIManager with a theme present in the JTattoo library.
	 */
	private static void setGUI() {
		try {
			UIManager.setLookAndFeel(GRAPHITE_LOOK_AND_FELL);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exc) {
			Logger.getLogger("Unable to load the GUI");
		}
	}

}
