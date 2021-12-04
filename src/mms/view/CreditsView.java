package mms.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import mms.controller.CreditsController;

/**
 * Class that shows the credits of the program.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class CreditsView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant HORIZONTAL_GAP. */
	private static final int HORIZONTAL_GAP = 20;

	/** The Constant LABEL_GAP. */
	private static final int LABEL_GAP = 10;

	/** The credits icon. */
	private final JLabel creditsIcon = new JLabel(new ImageIcon(this.getClass().getResource("/Credits_Icon.png")));

	/** The developers. */
	private final JLabel developers = new JLabel("Developed by:");

	/** The pascucci. */
	private final JLabel pascucci = new JLabel("Luca Pascucci");

	/** The nicolini. */
	private final JLabel nicolini = new JLabel("Filippo Nicolini");

	/** The bagnoli. */
	private final JLabel bagnoli = new JLabel("Alessando Bagnoli");

	/** The version. */
	private final JLabel version = new JLabel("Version 1.0");

	/** The distribution. */
	private final JLabel distribution = new JLabel("Software distribuited under GNU General Public License.");

	/** The lbl code. */
	private final JLabel lblCode = new JLabel("Code is available at bitbucket link:");

	/** The link. */
	private final JLabel link = new JLabel("https://bitbucket.org/LucaPascucci/movie_management_system");

	/** The original. */
	private Font original;

	/** The observer. */
	private CreditsController observer;

	/**
	 * This constructor creates a new CreditsView.
	 */
	public CreditsView() {

		super();

		this.setSize(525, 515); //frame size: 525 width, 515 height.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle("Developers of the application");
		this.setResizable(false);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.creditsIcon);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.creditsIcon, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.creditsIcon, LABEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.version);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.version, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.version, 0, SpringLayout.SOUTH, this.creditsIcon);

		this.add(this.developers);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.developers, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.developers, LABEL_GAP, SpringLayout.SOUTH, this.version);

		this.add(this.nicolini);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.nicolini, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.nicolini, LABEL_GAP, SpringLayout.SOUTH, this.developers);
		this.nicolini.setIcon(new ImageIcon(this.getClass().getResource("/Filippo_Nicolini.png")));
		this.nicolini.setHorizontalTextPosition(JLabel.CENTER);
		this.nicolini.setVerticalTextPosition(JLabel.NORTH);
		this.nicolini.setToolTipText("filo93ps@gmail.com");

		this.add(this.pascucci);
		layout.putConstraint(SpringLayout.EAST, this.pascucci, -HORIZONTAL_GAP, SpringLayout.WEST, this.nicolini);
		layout.putConstraint(SpringLayout.NORTH, this.pascucci, LABEL_GAP, SpringLayout.SOUTH, this.developers);
		this.pascucci.setIcon(new ImageIcon(this.getClass().getResource("/Luca_Pascucci.png")));
		this.pascucci.setHorizontalTextPosition(JLabel.CENTER);
		this.pascucci.setVerticalTextPosition(JLabel.NORTH);
		this.pascucci.setToolTipText("lucapascucci19@gmail.com");

		this.add(this.bagnoli);
		layout.putConstraint(SpringLayout.WEST, this.bagnoli, HORIZONTAL_GAP, SpringLayout.EAST, this.nicolini);
		layout.putConstraint(SpringLayout.NORTH, this.bagnoli, LABEL_GAP, SpringLayout.SOUTH, this.developers);
		this.bagnoli.setIcon(new ImageIcon(this.getClass().getResource("/Alessandro_Bagnoli.png")));
		this.bagnoli.setHorizontalTextPosition(JLabel.CENTER);
		this.bagnoli.setVerticalTextPosition(JLabel.NORTH);
		this.bagnoli.setToolTipText("alessandrobagnoli@hotmail.it");

		this.add(this.distribution);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.distribution, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.distribution, LABEL_GAP, SpringLayout.SOUTH, this.nicolini);
		this.add(this.lblCode);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblCode, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblCode, 0, SpringLayout.SOUTH, this.distribution);
		this.add(this.link);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.link, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.link, 0, SpringLayout.SOUTH, this.lblCode);
		this.link.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.link.addMouseListener(this);
		this.pascucci.addMouseListener(this);
		this.nicolini.addMouseListener(this);
		this.bagnoli.addMouseListener(this);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}
		});
		this.setLocation();
		this.setIcon();

	}

	@Override
	public void mouseReleased(final MouseEvent e) {	
		if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.link)) {
			e.consume();
			this.observer.openLink(this.link.getText());
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.pascucci)) {
			e.consume();
			this.observer.openMail(this.pascucci.getToolTipText(), this.pascucci.getText());
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.nicolini)) {
			e.consume();
			this.observer.openMail(this.nicolini.getToolTipText(), this.nicolini.getText());
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.bagnoli)) {
			e.consume();
			this.observer.openMail(this.bagnoli.getToolTipText(), this.bagnoli.getText());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void mouseEntered(final MouseEvent e) {
		this.original = e.getComponent().getFont();
		final Map<TextAttribute, ? super Integer> attributes = (Map<TextAttribute, ? super Integer>) this.original.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		e.getComponent().setFont(this.original.deriveFont(attributes));
		e.getComponent().setForeground(Color.BLUE);
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		e.getComponent().setFont(this.original);
		e.getComponent().setForeground(Color.BLACK);
	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param creditsController controller for this view
	 */
	public void attachObserver(final CreditsController creditsController) {
		this.observer = creditsController;
	}

	@Override
	public void actionPerformed(final ActionEvent e) { }

}
