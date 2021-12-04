package mms.controller.interfaces;

/**
 * Interface that define the {@link mms.controller.CreditsController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface ICreditsController {

	/**
	 * This method opens project's homepage.
	 * @param url URL that will be opened
	 */
	void openLink(final String url);

	/**
	 * Open a new mail message for a developer.
	 * @param mail mail address
	 * @param devName name of the developer
	 */
	void openMail(final String mail, final String devName);

}
