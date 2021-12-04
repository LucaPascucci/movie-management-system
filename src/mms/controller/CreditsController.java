package mms.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import mms.controller.interfaces.ICreditsController;
import mms.model.IModel;
import mms.view.CreditsView;

/**
 * This class manage the functions of the credits.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class CreditsController extends AbstractController implements ICreditsController {

	/** The Constant SUBJECT_MAIL. */
	private static final String SUBJECT_MAIL = "?subject=Movie%20Management%20System%20Mail&body=Dear%20";

	/** The return view. */
	private final Integer returnView;
	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param view this parameter pass the correct view that will be shown.
	 */
	public CreditsController(final IModel mod, final Integer view) {
		super(mod, CREDITS_VIEW);
		this.returnView = view;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((CreditsView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, this.returnView, null);
	}

	@Override
	public void openLink(final String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (URISyntaxException | IOException ex) {
			this.saveError(ex);
			this.showErrorDialog(ERROR_OPEN);
		}
	}

	@Override
	public void openMail(final String mail, final String devName) {
		try {
			Desktop.getDesktop().mail(new URI(MAILTO + mail + SUBJECT_MAIL + devName));
		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_OPEN);
		} catch (URISyntaxException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_OPEN);
		}

	}

}
