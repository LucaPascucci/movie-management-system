package mms.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import mms.controller.LoginController;

/**
 * This class creates the start view of the program.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class LoginView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant VERTICAL_LBL_GAP. */
	private static final int VERTICAL_LBL_GAP = 20;

	/** The Constant VERTICAL_TXT_GAP. */
	private static final int VERTICAL_TXT_GAP = 15;

	/** The Constant BUTTON_SOUTH_GAP. */
	private static final int BUTTON_SOUTH_GAP = 20;

	/** The Constant BUTTON_GAP_TO_FRAME. */
	private static final int BUTTON_GAP_TO_FRAME = 150;

	/** The Constant HORIZONTAL_FIRST_TABULATION. */
	private static final int HORIZONTAL_FIRST_TABULATION = 20;

	/** The Constant HORIZONTAL_SECOND_TABULATION. */
	private static final int HORIZONTAL_SECOND_TABULATION = 100;

	/** The Constant DEFAULT_GAP. */
	private static final int DEFAULT_GAP = 5;

	/** The Constant TEXTFIELD_SIZE. */
	private static final int TEXTFIELD_SIZE = 15;

	/** The Constant MENU_IDX_CONTACT_SOCIETY. */
	private static final int MENU_IDX_CONTACT_SOCIETY = 0;

	/** The Constant MENU_IDX_CONTACT_DEVELOPERS. */
	private static final int MENU_IDX_CONTACT_DEVELOPERS = 1;

	/** The Constant MENU_IDX_ABOUT. */
	private static final int MENU_IDX_ABOUT = 2;

	/** The lbl username. */
	private final JLabel lblUsername = new JLabel("Username");

	/** The lbl password. */
	private final JLabel lblPassword = new JLabel("Password");

	/** The login. */
	private final JButton login = new JButton("Login", new ImageIcon(this.getClass().getResource("/Login.png")));

	/** The new customer. */
	private final JButton newCustomer = new JButton("New Customer", new ImageIcon(this.getClass().getResource("/Add_Customer.png")));

	/** The txt username. */
	private final JTextField txtUsername = new JTextField(TEXTFIELD_SIZE);

	/** The txt password. */
	private final JPasswordField txtPassword = new JPasswordField(TEXTFIELD_SIZE);

	/** The administrator rb. */
	private final JRadioButton administratorRB = new JRadioButton("Administrator");

	/** The customer rb. */
	private final JRadioButton customerRB = new JRadioButton("Customer");

	/** The exit item. */
	private final JMenuItem exitItem;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The observer. */
	private LoginController observer;

	/**
	 * This constructor creates a new LoginView.
	 */
	public LoginView() {

		super();

		this.setTitle("Login");
		this.setSize(325, 270); //frame size: 300 width, 200 height.
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JMenuBar bar;
		JMenu fileMenu;
		JMenu helpMenu;

		bar = new JMenuBar();
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		this.exitItem = new JMenuItem("Exit", new ImageIcon(this.getClass().getResource("/Exit.png")));
		fileMenu.add(this.exitItem);
		this.exitItem.addActionListener(this);
		this.helpMenuItem = new JMenuItem[3];
		this.helpMenuItem[MENU_IDX_CONTACT_SOCIETY] = new JMenuItem("Contact Society", new ImageIcon(this.getClass().getResource("/Society.png")));
		this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS] = new JMenuItem("Contact Developers", new ImageIcon(this.getClass().getResource("/Developers.png")));
		this.helpMenuItem[MENU_IDX_ABOUT] = new JMenuItem("About", new ImageIcon(this.getClass().getResource("/About.png")));
		for (int i = 0; i < 3; i++) {
			helpMenu.add(this.helpMenuItem[i]);
			this.helpMenuItem[i].addActionListener(this);	
		}

		bar.add(fileMenu);
		bar.add(helpMenu);
		this.setJMenuBar(bar);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.lblUsername);
		layout.putConstraint(SpringLayout.NORTH, this.lblUsername, VERTICAL_LBL_GAP, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.WEST, this.lblUsername, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.txtUsername);
		this.txtUsername.setToolTipText("Insert your username");
		layout.putConstraint(SpringLayout.NORTH, this.txtUsername, VERTICAL_TXT_GAP, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.WEST, this.txtUsername, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.txtUsername, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());

		this.add(this.lblPassword);
		layout.putConstraint(SpringLayout.NORTH, this.lblPassword, VERTICAL_LBL_GAP, SpringLayout.SOUTH, this.txtUsername);
		layout.putConstraint(SpringLayout.WEST, this.lblPassword, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.txtPassword);
		this.txtPassword.setToolTipText("Insert your password");
		layout.putConstraint(SpringLayout.NORTH, this.txtPassword, VERTICAL_TXT_GAP, SpringLayout.SOUTH, this.txtUsername);
		layout.putConstraint(SpringLayout.WEST, this.txtPassword, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.txtPassword, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());

		this.add(this.administratorRB);
		this.administratorRB.setToolTipText("Select for Administrator Login");
		layout.putConstraint(SpringLayout.NORTH, this.administratorRB, VERTICAL_LBL_GAP, SpringLayout.SOUTH, this.txtPassword);
		layout.putConstraint(SpringLayout.WEST, this.administratorRB, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.customerRB);
		this.customerRB.setToolTipText("Select for Customer Login");
		layout.putConstraint(SpringLayout.NORTH, this.customerRB, DEFAULT_GAP, SpringLayout.SOUTH, this.administratorRB);
		layout.putConstraint(SpringLayout.WEST, this.customerRB, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.newCustomer);
		layout.putConstraint(SpringLayout.SOUTH, this.newCustomer, -BUTTON_SOUTH_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.newCustomer, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.newCustomer, BUTTON_GAP_TO_FRAME, SpringLayout.WEST, this.getContentPane());

		this.add(this.login);
		layout.putConstraint(SpringLayout.SOUTH, this.login, -BUTTON_SOUTH_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.login, -HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.login, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.newCustomer.addActionListener(this);
		this.login.addActionListener(this);
		this.administratorRB.addActionListener(this);
		this.customerRB.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doExit();
			}

		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (selection.equals(this.login)) {
			this.observer.doLogin(this.txtUsername.getText(), new String(this.txtPassword.getPassword()), this.administratorRB.isSelected(), this.customerRB.isSelected());
		} else if (selection.equals(this.newCustomer)) {
			this.observer.doNewUser();
		} else if (selection.equals(this.exitItem)) {
			this.observer.doBack();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS])) {
			this.observer.showFeedbackView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_SOCIETY])) {
			this.observer.contactSociety();
		} else if (selection.equals(this.administratorRB)) {
			this.customerRB.setSelected(false);
			if (this.administratorRB.isSelected()) {
				this.setTitle("Login Administrator");
			} else {
				this.setTitle("Login");
			}
		} else if (selection.equals(this.customerRB)) {
			this.administratorRB.setSelected(false);
			if (this.customerRB.isSelected()) {
				this.setTitle("Login Customer");
			} else {
				this.setTitle("Login");
			}
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param loginController controller for this view
	 */
	public void attachObserver(final LoginController loginController) {
		this.observer = loginController;
	}

	/**
	 * Set indicated fields to void.
	 */
	public void resetField() {
		this.txtUsername.setText("");
		this.txtPassword.setText("");
		this.administratorRB.setSelected(false);
		this.customerRB.setSelected(false);
	}

}
