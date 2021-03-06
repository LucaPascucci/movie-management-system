package mms.controller.interfaces;

/**
 * Interface that define the {@link mms.controller.FeedbackController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IFeedbackController {
	/**
	 * Method that give the possibility to send message.
	 * @param object is required for define the suggestion or problem.
	 * @param text is the text of the message.
	 */
	void doSend(final String object, final String text);
}
