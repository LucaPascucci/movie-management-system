package mms.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Abstract class that implements the interface {@link mms.view.IAbstractView} and the common methods of the view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public abstract class AbstractView extends JFrame implements IAbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The icon. */
	private final ImageIcon icon = new ImageIcon(this.getClass().getResource("/View_Icon.png"));

	/** The Constant OS_WITHOUT_ICON. */
	private static final String OS_WITHOUT_ICON = "Mac";

	/** The Constant COMMAND_FOR_OS_NAME. */
	private static final String COMMAND_FOR_OS_NAME = "os.name";

	@Override
	public void setLocation() {
		this.setLocationRelativeTo(null);
	}

	@Override
	public void setIcon() {
		if (!System.getProperty(COMMAND_FOR_OS_NAME).startsWith(OS_WITHOUT_ICON)) {
			this.setIconImage(this.icon.getImage());
		}
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

	@Override
	public abstract void mouseReleased(MouseEvent e);

	@Override
	public void mouseClicked(final MouseEvent e) {
		//this method is unused in all views
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		//this method is unused in all views
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		//this method is unused in all views
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		//this method is unused in all views
	}
}
