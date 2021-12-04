package mms.controller;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.sun.mail.util.MailConnectException;

import mms.controller.interfaces.IFeedbackController;
import mms.model.IModel;
import mms.view.FeedbackView;

/**
 * This class manage the send of a feedback message.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FeedbackController extends AbstractController implements IFeedbackController {

	/** The Constant ERROR_SEND_FEEDBACK. */
	private static final String ERROR_SEND_FEEDBACK = "Error during sending feedback";

	/** The Constant DEFAULT_OBJECT. */
	private static final String DEFAULT_OBJECT = "MovieManagementSystem feedback: ";

	/** The Constant CORRECT_SEND. */
	private static final String CORRECT_SEND = "Sent message successfully";

	/** The Constant CONNECT_WEB. */
	private static final String CONNECT_WEB = "Network not available!";

	/** The Constant WRONG_PASSWORD. */
	private static final String WRONG_PASSWORD = "Wrong Password!";

	/** The Constant INSERT_PASSWORD. */
	private static final String INSERT_PASSWORD = "Insert the Password!";

	/** The Constant DEFAULT_EMAIL_ADDRESS. */
	private static final String DEFAULT_EMAIL_ADDRESS = "ingegneriapesaro@gmail.com";

	/** The Constant DEFAULT_USERNAME. */
	private static final String DEFAULT_USERNAME = "";

	/** The return view. */
	private final Integer returnView;

	/** The secondary thread. */
	private Thread secondaryThread = new Thread();
	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param view this parameter pass the correct view that will be shown.
	 */
	public FeedbackController(final IModel mod, final Integer view) {
		super(mod, FEEDBACK_VIEW);
		this.returnView = view;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((FeedbackView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, this.returnView, null);

	}

	@Override
	public void doSend(final String object, final String text) {

		final JPasswordField passwordField = new JPasswordField();
		final Object[] obj = {"Enter Feedback Password:\n", passwordField};
		final Object[] stringArray = {"OK", "Cancel"};
		if (JOptionPane.showOptionDialog(this.frame, obj, "Insert Password", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(this.getClass().getResource("/Password.png")), stringArray, obj) == JOptionPane.YES_OPTION) {
			if (new String(passwordField.getPassword()).length() != 0) {
				((FeedbackView) this.frame).setUnsetLoadingVisible();
				this.secondaryThread = new Thread() {
					public void run() {
						sendCmd(new String(passwordField.getPassword()), object, text);
					}
				};
				this.secondaryThread.start();
			} else {
				this.showInfoDialog(INSERT_PASSWORD);
			}
		}

	}
	/**
	 * This method manage the send of a message with attachments.
	 * @param password is required as an authentication.
	 * @param object is required for define the suggestion or problem.
	 * @param text is the text of the message.
	 */
	private void sendCmd(final String password, final String object, final String text) {

		final Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		final Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(DEFAULT_USERNAME, password);
			}
		});


		try {

			final Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(DEFAULT_EMAIL_ADDRESS));
			message.setSubject(DEFAULT_OBJECT + object);
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setText(text + "\n\n");

			// Create a multipar message
			final Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachments
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(DEFAULT_SAVE_PATH);
			multipart.addBodyPart(messageBodyPart);
			if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_SAVE_ERROR_FILE_PATH)) {
				messageBodyPart = new MimeBodyPart();
				source = new FileDataSource(DEFAULT_USER_PATH + DEFAULT_SAVE_ERROR_FILE_PATH);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(DEFAULT_SAVE_ERROR_FILE_PATH);
				multipart.addBodyPart(messageBodyPart);
			}
			// Send the complete message parts
			message.setContent(multipart);


			Transport.send(message);
			this.showInfoDialog(CORRECT_SEND);
			this.doBack();

		} catch (AuthenticationFailedException exc) {
			this.showErrorDialog(WRONG_PASSWORD);
			this.saveError(exc);
		} catch (MailConnectException exc) {
			this.showErrorDialog(CONNECT_WEB);
			this.saveError(exc);
		} catch (MessagingException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_SEND_FEEDBACK);

		}
		((FeedbackView) this.frame).setUnsetLoadingVisible();
		this.secondaryThread.interrupt();
	}

}
