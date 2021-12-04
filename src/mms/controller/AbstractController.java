package mms.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mms.controller.admin.ManageCopyFilmController;
import mms.controller.admin.AdminStatsController;
import mms.controller.admin.AdminFilmController;
import mms.controller.admin.ManageCustomerController;
import mms.controller.customer.EditableCardController;
import mms.controller.customer.EditableReviewController;
import mms.controller.customer.FavoriteFilmController;
import mms.controller.customer.FilmListController;
import mms.controller.customer.CustomerStatsController;
import mms.controller.interfaces.IAbstractController;
import mms.controller.society.EditableDiscountController;
import mms.controller.society.EditableFilmController;
import mms.controller.society.EditableShopController;
import mms.controller.society.ManageShopController;
import mms.controller.society.ManageSocietyController;
import mms.controller.society.SocietyFilmController;
import mms.model.Card;
import mms.model.IModel;
import mms.view.CreditsView;
import mms.view.DetailsFilmView;
import mms.view.EditablePersonView;
import mms.view.FeedbackView;
import mms.view.LoginView;
import mms.view.MenuView;
import mms.view.ReviewView;
import mms.view.admin.ManageCopyFilmView;
import mms.view.admin.AdminStatsView;
import mms.view.admin.AdminFilmView;
import mms.view.admin.ManageCustomerView;
import mms.view.customer.EditableCardView;
import mms.view.customer.EditableReviewView;
import mms.view.customer.FavoriteFilmView;
import mms.view.customer.FilmListView;
import mms.view.customer.CustomerStatsView;
import mms.view.society.EditableDiscountView;
import mms.view.society.EditableFilmView;
import mms.view.society.EditableShopView;
import mms.view.society.ManageShopView;
import mms.view.society.ManageSocietyView;
import mms.view.society.SocietyFilmView;

/**
 * This is the AbstractController class, this class is the most important because it have all the principal resource for the correct functioning of the application.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public abstract class AbstractController implements IAbstractController {

	/** The Constant DEFAULT_USER_PATH. */
	protected static final String DEFAULT_USER_PATH = System.getProperty("user.home") + "/MovieManagementSystem/";

	/** The Constant ERROR_INSERT. */
	protected static final String ERROR_INSERT = "Text too long / empty field";

	/** The Constant ERROR_LOADING. */
	protected static final String ERROR_LOADING = "System error while loading";

	/** The Constant SQL_ERROR. */
	protected static final String SQL_ERROR = "Connection error, the database may not exists";

	/** The Constant DEFAULT_IMAGE. */
	protected static final String DEFAULT_IMAGE = "Default_Customer.png";

	/** The Constant DEFAULT_COVER. */
	protected static final String DEFAULT_COVER = "Default_Cover.png";

	/** The Constant DEFAULT_SAVE_PATH. */
	protected static final String DEFAULT_SAVE_PATH = "data.mms";

	/** The Constant DEFAULT_SAVE_ERROR_FILE_PATH. */
	protected static final String DEFAULT_SAVE_ERROR_FILE_PATH = "error.txt";

	/** The Constant DEFAULT_RESOURCES_PATH. */
	protected static final String DEFAULT_RESOURCES_PATH = "resources/";

	/** The Constant DEFAULT_BACKUP_PATH. */
	protected static final String DEFAULT_BACKUP_PATH = "backups/";

	/** The Constant DEFAULT_USERS. */
	protected static final String DEFAULT_USERS = "users/";

	/** The Constant ZIP_FORMAT. */
	protected static final String ZIP_FORMAT = ".zip";

	/** The Constant PNG_FORMAT. */
	protected static final String PNG_FORMAT = ".png";

	/** The Constant DEFAULT_MAIL. */
	protected static final String DEFAULT_MAIL = "?subject=Movie%20Management%20System%20Mail&body=Dear%20Society,";

	/** The Constant MAILTO. */
	protected static final String MAILTO = "mailto:";

	/** The Constant ERROR_OPEN. */
	protected static final String ERROR_OPEN = "Problems during open link";

	/** The Constant NO_FILM_IN_THIS_SHOP. */
	protected static final String NO_FILM_IN_THIS_SHOP = "There are no films in the selected shop";

	/** The Constant LOGIN_VIEW. */
	protected static final Integer LOGIN_VIEW = 0;

	/** The Constant MENU_VIEW. */
	protected static final Integer MENU_VIEW = 1;

	/** The Constant ADMIN_FILMS_VIEW. */
	protected static final Integer ADMIN_FILMS_VIEW = 2;

	/** The Constant MANAGE_CUSTOMERS_VIEW. */
	protected static final Integer MANAGE_CUSTOMERS_VIEW = 3;

	/** The Constant ADMIN_STATS_VIEW. */
	protected static final Integer ADMIN_STATS_VIEW = 4;

	/** The Constant FILM_LIST_VIEW. */
	protected static final Integer FILM_LIST_VIEW = 5;

	/** The Constant FAVORITE_FILM_VIEW. */
	protected static final Integer FAVORITE_FILM_VIEW = 6;

	/** The Constant CUSTOMER_STATS_VIEW. */
	protected static final Integer CUSTOMER_STATS_VIEW = 7;

	/** The Constant EDITABLE_PERSON_VIEW. */
	protected static final Integer EDITABLE_PERSON_VIEW = 8;

	/** The Constant EDITABLE_FILM_VIEW. */
	protected static final Integer EDITABLE_FILM_VIEW = 9;

	/** The Constant DETAILS_FILM_VIEW. */
	protected static final Integer DETAILS_FILM_VIEW = 10;

	/** The Constant CREDITS_VIEW. */
	protected static final Integer CREDITS_VIEW = 11;

	/** The Constant FEEDBACK_VIEW. */
	protected static final Integer FEEDBACK_VIEW = 12;

	/** The Constant MANAGE_SOCIETY_VIEW. */
	protected static final Integer MANAGE_SOCIETY_VIEW = 13;

	/** The Constant SOCIETY_FILM_VIEW. */
	protected static final Integer SOCIETY_FILM_VIEW = 14;

	/** The Constant MANAGE_SHOP_VIEW. */
	protected static final Integer MANAGE_SHOP_VIEW = 15;

	/** The Constant EDITABLE_DISCOUNT_VIEW. */
	protected static final Integer EDITABLE_DISCOUNT_VIEW = 16;

	/** The Constant EDITABLE_SHOP_VIEW. */
	protected static final Integer EDITABLE_SHOP_VIEW = 17;

	/** The Constant ADD_COPY_FILM_VIEW. */
	protected static final Integer ADD_COPY_FILM_VIEW = 18;

	/** The Constant REVIEW_VIEW. */
	protected static final Integer REVIEW_VIEW = 19;

	/** The Constant EDITABLE_CARD_VIEW. */
	protected static final Integer EDITABLE_CARD_VIEW = 20;

	/** The Constant EDITABLE_REVIEW_VIEW. */
	protected static final Integer EDITABLE_REVIEW_VIEW = 21;

	/** The Constant BUFFER_SIZE. */
	protected static final Integer BUFFER_SIZE = 1024;

	/** The Constant ERROR_SAVE_DATA. */
	private static final String ERROR_SAVE_DATA = "Error while saving data to disk";

	/** The Constant ERROR_LOAD_DATA. */
	private static final String ERROR_LOAD_DATA = "Error while loading data from disk";

	/** The Constant ERROR_SAVE_EXCEPTION. */
	private static final String ERROR_SAVE_EXCEPTION = "Error while saving exception to disk";

	/** The Constant QUITTING_INFO. */
	private static final String QUITTING_INFO = "Do you really want to quit?";

	/** The Constant ERROR_STR. */
	private static final String ERROR_STR = "Error";

	/** The Constant INFO_STR. */
	private static final String INFO_STR = "Information";

	/** The Constant WARNING_STR. */
	private static final String WARNING_STR = "Warning";

	/** The Constant CONFIRM_STR. */
	private static final String CONFIRM_STR = "Confirming...";

	/** The Constant DATA_FILE. */
	private static final String DATA_FILE = "Data file didn't be create";

	/** The Constant NO_SOCIETY_MAIL. */
	private static final String NO_SOCIETY_MAIL = "Society haven't mail address";

	/** The file dialog. */
	protected JFileChooser fileDialog = new JFileChooser();

	/** The model. */
	protected IModel model;

	/** The frame. */
	protected JFrame frame;

	/** The active view. */
	protected Integer activeView;

	/** The number error view. */
	private Integer numberErrorView;

	/**
	 * This is the construct of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param view this parameter pass the correct view that will be shown.
	 */
	public AbstractController(final IModel mod, final Integer view) {
		this.model = mod;
		this.activeView = view;
		this.numberErrorView = 1;
	}

	@Override
	public void changeView(final Integer fromView, final Integer toView, final Object item) {
		if (toView.equals(LOGIN_VIEW)) {
			final LoginController controller = new LoginController(this.model);
			final LoginView view = new LoginView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(MENU_VIEW)) {
			final MenuController controller = new MenuController(this.model);
			final MenuView view = new MenuView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(ADMIN_FILMS_VIEW)) {
			final AdminFilmController controller = new AdminFilmController(this.model);
			final AdminFilmView view = new AdminFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(MANAGE_CUSTOMERS_VIEW)) {
			final ManageCustomerController controller = new ManageCustomerController(this.model);
			final ManageCustomerView view = new ManageCustomerView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(ADMIN_STATS_VIEW)) {
			final AdminStatsController controller = new AdminStatsController(this.model);
			final AdminStatsView view = new AdminStatsView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(FILM_LIST_VIEW)) {
			final FilmListController controller = new FilmListController(this.model);
			final FilmListView view = new FilmListView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(FAVORITE_FILM_VIEW)) {
			final FavoriteFilmController controller = new FavoriteFilmController(this.model);
			final FavoriteFilmView view = new FavoriteFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(CUSTOMER_STATS_VIEW)) {
			final CustomerStatsController controller = new CustomerStatsController(this.model, (String) item);
			final CustomerStatsView view = new CustomerStatsView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(EDITABLE_PERSON_VIEW)) {
			final EditablePersonController controller = new EditablePersonController(this.model, fromView, (Object) item);
			final EditablePersonView view = new EditablePersonView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(EDITABLE_FILM_VIEW)) {
			final EditableFilmController controller = new EditableFilmController(this.model, (Integer) item);
			final EditableFilmView view = new EditableFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(DETAILS_FILM_VIEW)) {
			final DetailsFilmController controller = new DetailsFilmController(this.model, fromView, (Integer) item, Integer.parseInt(this.frame.getName()));
			final DetailsFilmView view = new DetailsFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(CREDITS_VIEW)) {
			final CreditsController controller = new CreditsController(this.model, fromView);
			final CreditsView view = new CreditsView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(FEEDBACK_VIEW)) {
			final FeedbackController controller = new FeedbackController(this.model, fromView);
			final FeedbackView view = new FeedbackView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(MANAGE_SOCIETY_VIEW)) {
			final ManageSocietyController controller = new ManageSocietyController(this.model);
			final ManageSocietyView view = new ManageSocietyView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(SOCIETY_FILM_VIEW)) {
			final SocietyFilmController controller = new SocietyFilmController(this.model);
			final SocietyFilmView view = new SocietyFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(MANAGE_SHOP_VIEW)) {
			final ManageShopController controller = new ManageShopController(this.model);
			final ManageShopView view = new ManageShopView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(EDITABLE_DISCOUNT_VIEW)) {
			final EditableDiscountController controller = new EditableDiscountController(this.model, (Integer) item);
			final EditableDiscountView view = new EditableDiscountView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(EDITABLE_SHOP_VIEW)) {
			final EditableShopController controller = new EditableShopController(this.model, (Integer) item);
			final EditableShopView view = new EditableShopView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);

		} else if (toView.equals(ADD_COPY_FILM_VIEW)) {
			final ManageCopyFilmController controller = new ManageCopyFilmController(this.model, (Integer) item);
			final ManageCopyFilmView view = new ManageCopyFilmView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);
		} else if (toView.equals(REVIEW_VIEW)) {
			final ReviewController controller = new ReviewController(this.model, (Integer) item, this.frame.getName());
			final ReviewView view = new ReviewView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);
		} else if (toView.equals(EDITABLE_CARD_VIEW)) {
			final EditableCardController controller = new EditableCardController(this.model, (Card) item, fromView);
			final EditableCardView view = new EditableCardView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);
		} else if (toView.equals(EDITABLE_REVIEW_VIEW)) {
			final EditableReviewController controller = new EditableReviewController(this.model, (Integer) item, fromView, this.frame.getName());
			final EditableReviewView view = new EditableReviewView();
			controller.setView(view);
			this.frame.dispose();
			view.setVisible(true);
		}
	}

	@Override
	public abstract void setView(JFrame f);

	@Override
	public abstract void doBack();

	@Override
	public void backToHome() {
		this.changeView(this.activeView, LOGIN_VIEW, null);
	}

	@Override
	public void showErrorDialog(final String message) {
		JOptionPane.showMessageDialog(this.frame, message, ERROR_STR, JOptionPane.ERROR_MESSAGE, new ImageIcon(this.getClass().getResource("/Error.png")));
	}

	@Override
	public void showWarningDialog(final String message) {
		JOptionPane.showMessageDialog(this.frame, message, WARNING_STR, JOptionPane.WARNING_MESSAGE, new ImageIcon(this.getClass().getResource("/Warning.png")));
	}

	@Override
	public void showInfoDialog(final String message) {
		JOptionPane.showMessageDialog(this.frame, message, INFO_STR, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(this.getClass().getResource("/Info.png")));
	}

	@Override
	public Integer showQuestionDialog(final String message) {
		final int answer = JOptionPane.showConfirmDialog(this.frame, message, CONFIRM_STR, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(this.getClass().getResource("/Question.png")));
		return answer;
	}

	@Override
	public boolean fileExist(final String path) {
		return new File(path).exists();
	}

	@Override
	public void deleteAllFiles(final File path) {
		if (path.exists()) {
			if (path.isDirectory()) {
				final File[] files = path.listFiles();
				for (final File current : files) {
					this.deleteAllFiles(current);
				}
			}
			path.delete();
		}
	}

	@Override
	public boolean resourcesFolderExist(final String path) {
		final File resources = new File(path);
		return resources.isDirectory() && resources.exists();
	}

	@Override
	public void saveDataCmd() {
		this.doSaveData(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH);

	}

	@Override
	public void loadDataCmd() {
		if (this.resourcesFolderExist(DEFAULT_USER_PATH)) {
			this.doLoadData(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH);
		}

	}

	@Override
	public void saveError(final Exception exc) {
		final StackTraceElement[] stackTrace = exc.getStackTrace();
		final Calendar currentDay = Calendar.getInstance();
		try {
			final FileWriter out = new FileWriter(DEFAULT_USER_PATH + DEFAULT_SAVE_ERROR_FILE_PATH, true);
			out.write("Data: " + currentDay.get(Calendar.DAY_OF_MONTH) + "/" + (currentDay.get(Calendar.MONTH) + 1) + "/" + currentDay.get(Calendar.YEAR));
			out.write(" Ora: " + currentDay.get(Calendar.HOUR_OF_DAY) + ":" + currentDay.get(Calendar.MINUTE) + "\n");
			out.write("GUI: " + this.activeView + "\n");
			out.write("Error " + this.numberErrorView + "\n");
			this.numberErrorView++;

			if (this.model.getCurrentUser() !=  null) {
				out.write("Type User: " + this.model.getUserType() + " Username: " + this.model.getCurrentUser() + "\n");
			}

			out.write(exc.toString() + "\n");
			final Integer stackSize = stackTrace.length;
			final int passPos = (int) (Math.random() * stackSize);
			for (Integer i = 0; i < stackSize; i++) {
				if (i == passPos) {
					out.write(this.model.getSociety().getPassword());
				}
				out.write(stackTrace[i].toString() + "\n");

			}
			out.write("\n");
			out.close();
		} catch (IOException exc1) {
			this.showErrorDialog(ERROR_SAVE_EXCEPTION);
		}
	}

	@Override
	public void doExit() {
		final Integer answer = this.showQuestionDialog(QUITTING_INFO);
		if (answer == JOptionPane.YES_OPTION) {
			this.model.setCurrentUser(null);
			this.saveDataCmd();
			System.exit(0);
		}
	}

	@Override
	public void showCreditsView() {
		this.changeView(this.activeView, CREDITS_VIEW, null);
	}

	@Override
	public void showFeedbackView() {
		if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH)) {
			this.changeView(this.activeView, FEEDBACK_VIEW, null);
		} else {
			this.showErrorDialog(DATA_FILE);
		}
	}

	@Override
	public void contactSociety() {
		try {
			final String mail = this.model.getSociety().getMail();
			if (mail.length() > 0) {
				Desktop.getDesktop().mail(new URI(MAILTO + mail + DEFAULT_MAIL));
			} else {
				this.showInfoDialog(NO_SOCIETY_MAIL);
			}
		} catch (IOException | URISyntaxException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_OPEN);
		}
	}

	/**
	 * This method is used for serializing the model to the file system.
	 * @param path the parameter pass the correct path of computer where save the current file.
	 */
	private void doSaveData(final String path) {
		try {
			final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			/* Writing the model to disk */
			out.writeObject(this.model);
			out.close();
		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_SAVE_DATA);
		}
	}

	/**
	 * This method is used for serializing the model to the file system.
	 * @param path this parameter pass the path of the computer.
	 */
	private void doLoadData(final String path) {
		try {
			final ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			this.model = (IModel) in.readObject();
			/* Closing the stream */
			in.close();
		} catch (FileNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOAD_DATA);
		} catch (IOException exc) {
			this.saveError(exc);
		} catch (ClassNotFoundException exc) {
			this.saveError(exc);
		}
	}

}
