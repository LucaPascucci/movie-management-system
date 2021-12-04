package mms.controller.interfaces;

import java.io.File;

import javax.swing.JFrame;

/**
 * Interface that defines the {@link mms.controller.AbstractController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IAbstractController {

	/**
	 * This method checks the existence of a file.
	 * @param path path of the file that will be controlled.
	 * @return boolean
	 */
	boolean fileExist(final String path);

	/**
	 * This method is used to clear the directory.
	 * @param path this parameter pass the path of the computer.
	 */
	void deleteAllFiles(final File path);

	/**
	 * This method checks the existence of the folder of the resources.
	 * @param path path of the folder that will be controlled.
	 * @return boolean
	 */
	boolean resourcesFolderExist(final String path);

	/**
	 * This method launches the function of save.
	 */
	void saveDataCmd();

	/**
	 * This method launches the function of load.
	 */
	void loadDataCmd();

	/**
	 * This method launches the exception that could occurs in the save function.
	 * @param exc parameter that define the exception.
	 */
	void saveError(final Exception exc);

	/**
	 * This method has the function to change the current view when requested.
	 * @param fromView this parameter defines the current view.
	 * @param toView this parameter defines the view that we want to go.
	 * @param item this parameter passes the items of the view.
	 */
	void changeView(final Integer fromView, final Integer toView,  final Object item);

	/**
	 * This method makes the view visible.
	 * @param f the correct frame.
	 */
	void setView(final JFrame f);

	/**
	 * This method shows an error message.
	 * @param message parameter that defines the text of the message.
	 */
	void showErrorDialog(final String message);

	/**
	 * This method shows a warning message.
	 * @param message parameter that defines the text of the message.
	 */
	void showWarningDialog(final String message);

	/**
	 * This method shows an info message.
	 * @param message parameter that defines the text of the message.
	 */
	void showInfoDialog(final String message);

	/**
	 * This method shows a question message.
	 * @param message parameter that defines the text of the message.
	 * @return Integer
	 */
	Integer showQuestionDialog(final String message);

	/**
	 * This method shows the {@link mms.view.CreditsView}.
	 */
	void showCreditsView();

	/**
	 * This method shows the {@link mms.view.FeedbackView}.
	 */
	void showFeedbackView();

	/**
	 * This method allows the current user to exit the application.
	 */
	void doExit();

	/**
	 * This method allows the current user to return to the {@link mms.view.LoginView}.
	 */
	void backToHome();

	/**
	 * This method allows the current User to return to the previous view.
	 */
	void doBack();

	/**
	 * This method opens up the default mail program to send an email to the society.
	 */
	void contactSociety();


}
