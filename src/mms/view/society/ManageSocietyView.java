package mms.view.society;

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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import mms.controller.society.ManageSocietyController;
import mms.model.Society;
import mms.view.AbstractView;

/**
 * It permits to manage the informations of the society.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class ManageSocietyView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant DEFAULT_GAP. */
	private static final int DEFAULT_GAP = 25;

	/** The Constant MINIMUM_GAP. */
	private static final int MINIMUM_GAP = 15;

	/** The Constant HORIZONTAL_GAP. */
	private static final int HORIZONTAL_GAP = 120;

	/** The Constant MAX_STRING_LENGTH. */
	private static final int MAX_STRING_LENGTH = 45;

	/** The Constant MENU_IDX_IMPORT_SOCIETY. */
	private static final int MENU_IDX_IMPORT_SOCIETY = 0;

	/** The Constant MENU_IDX_EXPORT_SOCIETY. */
	private static final int MENU_IDX_EXPORT_SOCIETY = 1;

	/** The Constant MENU_IDX_EDIT_SOCIETY. */
	private static final int MENU_IDX_EDIT_SOCIETY = 2;

	/** The Constant MENU_IDX_EXIT. */
	private static final int MENU_IDX_EXIT = 3;

	/** The Constant MENU_IDX_CREATE_DATABASE. */
	private static final int MENU_IDX_CREATE_DATABASE = 0;

	/** The Constant MENU_IDX_DROP_DATABASE. */
	private static final int MENU_IDX_DROP_DATABASE = 1;

	/** The Constant MENU_IDX_CONTACT_DEVELOPERS. */
	private static final int MENU_IDX_CONTACT_DEVELOPERS = 0;

	/** The Constant MENU_IDX_ABOUT. */
	private static final int MENU_IDX_ABOUT = 1;

	/** The file contained menu. */
	private final JMenuItem[] fileContainedMenu;

	/** The database menu item. */
	private final JMenuItem[] databaseMenuItem;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The save. */
	private final JButton save = new JButton("Save", new ImageIcon(this.getClass().getResource("/Save.png")));

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The lbl username. */
	private final JLabel lblUsername = new JLabel("Username: ");

	/** The lbl password. */
	private final JLabel lblPassword = new JLabel("Password: ");

	/** The lbl name. */
	private final JLabel lblName = new JLabel("Society name: ");

	/** The lbl address. */
	private final JLabel lblAddress = new JLabel("Address: ");

	/** The lbl town. */
	private final JLabel lblTown = new JLabel("Town: ");

	/** The lbl piva. */
	private final JLabel lblPIVA = new JLabel("P.IVA: ");

	/** The lbl telephone. */
	private final JLabel lblTelephone = new JLabel("Telephone: ");

	/** The lbl mail. */
	private final JLabel lblMail = new JLabel("E-mail: ");

	/** The lbl web address. */
	private final JLabel lblWebAddress = new JLabel("Web address: ");

	/** The username. */
	private final JLabel username = new JLabel();

	/** The password. */
	private final JLabel password = new JLabel();

	/** The name. */
	private final JLabel name = new JLabel();

	/** The address. */
	private final JLabel address = new JLabel();

	/** The town. */
	private final JLabel town = new JLabel();

	/** The partita iva. */
	private final JLabel partitaIVA = new JLabel();

	/** The telephone. */
	private final JLabel telephone = new JLabel();

	/** The mail. */
	private final JLabel mail = new JLabel();

	/** The web address. */
	private final JLabel webAddress = new JLabel();

	/** The logo. */
	private final JLabel logo = new JLabel();

	/** The txt username. */
	private final JTextField txtUsername = new JTextField(20);

	/** The txt password. */
	private final JTextField txtPassword = new JTextField(20);

	/** The txt name. */
	private final JTextField txtName = new JTextField(20);

	/** The txt address. */
	private final JTextField txtAddress = new JTextField(20);

	/** The txt town. */
	private final JTextField txtTown = new JTextField(20);

	/** The txt piva. */
	private final JTextField txtPIVA = new JTextField(20);

	/** The txt telephone. */
	private final JTextField txtTelephone = new JTextField(20);

	/** The txt mail. */
	private final JTextField txtMail = new JTextField(20);

	/** The txt web address. */
	private final JTextField txtWebAddress = new JTextField(20);

	/** The original. */
	private Font original;

	/** The observer. */
	private ManageSocietyController observer;

	/**
	 * The constructor of the view.
	 */
	public ManageSocietyView() {

		super();

		this.setTitle("Manage Society");
		this.setSize(550, 500);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		final JMenuBar bar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenu databaseMenu = new JMenu("Database");
		final JMenu helpMenu = new JMenu("Help");
		this.fileContainedMenu = new JMenuItem[4];
		this.fileContainedMenu[MENU_IDX_IMPORT_SOCIETY] = new JMenuItem("Import Society", new ImageIcon(this.getClass().getResource("/Import.png")));
		this.fileContainedMenu[MENU_IDX_EXPORT_SOCIETY] = new JMenuItem("Export Society", new ImageIcon(this.getClass().getResource("/Export.png")));
		this.fileContainedMenu[MENU_IDX_EDIT_SOCIETY] = new JMenuItem("Edit Society", new ImageIcon(this.getClass().getResource("/Edit_File.png")));
		this.fileContainedMenu[MENU_IDX_EXIT] = new JMenuItem("Exit", new ImageIcon(this.getClass().getResource("/Exit.png")));
		for (int i = 0; i < 4; i++) {
			fileMenu.add(this.fileContainedMenu[i]);
			this.fileContainedMenu[i].addActionListener(this);

		}
		this.databaseMenuItem = new JMenuItem[2];
		this.databaseMenuItem[MENU_IDX_CREATE_DATABASE] = new JMenuItem("Create", new ImageIcon(this.getClass().getResource("/Create_Database.png")));
		this.databaseMenuItem[MENU_IDX_DROP_DATABASE] = new JMenuItem("Drop", new ImageIcon(this.getClass().getResource("/Delete_Database.png")));

		this.helpMenuItem = new JMenuItem[2];
		this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS] = new JMenuItem("Contact Developers", new ImageIcon(this.getClass().getResource("/Developers.png")));
		this.helpMenuItem[MENU_IDX_ABOUT] = new JMenuItem("About", new ImageIcon(this.getClass().getResource("/About.png")));
		for (int i = 0; i < 2; i++) {
			helpMenu.add(this.helpMenuItem[i]);
			this.helpMenuItem[i].addActionListener(this);
			databaseMenu.add(this.databaseMenuItem[i]);
			this.databaseMenuItem[i].addActionListener(this);
		}

		bar.add(fileMenu);
		bar.add(databaseMenu);
		bar.add(helpMenu);
		this.setJMenuBar(bar);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);	

		this.add(this.back);
		layout.putConstraint(SpringLayout.EAST, this.back, -DEFAULT_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.back, -MINIMUM_GAP, SpringLayout.SOUTH, this.getContentPane());
		this.add(this.save);
		layout.putConstraint(SpringLayout.EAST, this.save, -DEFAULT_GAP, SpringLayout.WEST, this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.save, -MINIMUM_GAP, SpringLayout.SOUTH, this.getContentPane());

		this.add(this.logo);
		layout.putConstraint(SpringLayout.NORTH, this.logo, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.logo, MINIMUM_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.logo, -MINIMUM_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.logo, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.logo, 100, SpringLayout.NORTH, this.logo);
		this.logo.setToolTipText("Double Click to change logo");
		this.logo.addMouseListener(this);

		this.add(this.lblUsername);
		layout.putConstraint(SpringLayout.NORTH, this.lblUsername, DEFAULT_GAP, SpringLayout.SOUTH, this.logo);
		layout.putConstraint(SpringLayout.WEST, this.lblUsername, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.username);
		layout.putConstraint(SpringLayout.NORTH, this.username, DEFAULT_GAP, SpringLayout.SOUTH, this.logo);
		layout.putConstraint(SpringLayout.WEST, this.username, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtUsername);
		layout.putConstraint(SpringLayout.NORTH, this.txtUsername, DEFAULT_GAP, SpringLayout.SOUTH, this.logo);
		layout.putConstraint(SpringLayout.WEST, this.txtUsername, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblPassword);
		layout.putConstraint(SpringLayout.NORTH, this.lblPassword, MINIMUM_GAP, SpringLayout.SOUTH, this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.lblPassword, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.password);
		layout.putConstraint(SpringLayout.NORTH, this.password, MINIMUM_GAP, SpringLayout.SOUTH, this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.password, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtPassword);
		layout.putConstraint(SpringLayout.NORTH, this.txtPassword, MINIMUM_GAP, SpringLayout.SOUTH, this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.txtPassword, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblName);
		layout.putConstraint(SpringLayout.NORTH, this.lblName, MINIMUM_GAP, SpringLayout.SOUTH, this.lblPassword);
		layout.putConstraint(SpringLayout.WEST, this.lblName, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.name);
		layout.putConstraint(SpringLayout.NORTH, this.name, MINIMUM_GAP, SpringLayout.SOUTH, this.lblPassword);
		layout.putConstraint(SpringLayout.WEST, this.name, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtName);
		layout.putConstraint(SpringLayout.NORTH, this.txtName, MINIMUM_GAP, SpringLayout.SOUTH, this.lblPassword);
		layout.putConstraint(SpringLayout.WEST, this.txtName, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblAddress);
		layout.putConstraint(SpringLayout.NORTH, this.lblAddress, MINIMUM_GAP, SpringLayout.SOUTH, this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.lblAddress, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.address);
		layout.putConstraint(SpringLayout.NORTH, this.address, MINIMUM_GAP, SpringLayout.SOUTH, this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.address, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtAddress);
		layout.putConstraint(SpringLayout.NORTH, this.txtAddress, MINIMUM_GAP, SpringLayout.SOUTH, this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.txtAddress, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblTown);
		layout.putConstraint(SpringLayout.NORTH, this.lblTown, MINIMUM_GAP, SpringLayout.SOUTH, this.lblAddress);
		layout.putConstraint(SpringLayout.WEST, this.lblTown, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.town);
		layout.putConstraint(SpringLayout.NORTH, this.town, MINIMUM_GAP, SpringLayout.SOUTH, this.lblAddress);
		layout.putConstraint(SpringLayout.WEST, this.town, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtTown);
		layout.putConstraint(SpringLayout.NORTH, this.txtTown, MINIMUM_GAP, SpringLayout.SOUTH, this.lblAddress);
		layout.putConstraint(SpringLayout.WEST, this.txtTown, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblPIVA);
		layout.putConstraint(SpringLayout.NORTH, this.lblPIVA, MINIMUM_GAP, SpringLayout.SOUTH, this.lblTown);
		layout.putConstraint(SpringLayout.WEST, this.lblPIVA, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.partitaIVA);
		layout.putConstraint(SpringLayout.NORTH, this.partitaIVA, MINIMUM_GAP, SpringLayout.SOUTH, this.lblTown);
		layout.putConstraint(SpringLayout.WEST, this.partitaIVA, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtPIVA);
		layout.putConstraint(SpringLayout.NORTH, this.txtPIVA, MINIMUM_GAP, SpringLayout.SOUTH, this.lblTown);
		layout.putConstraint(SpringLayout.WEST, this.txtPIVA, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblTelephone);
		layout.putConstraint(SpringLayout.NORTH, this.lblTelephone, MINIMUM_GAP, SpringLayout.SOUTH, this.lblPIVA);
		layout.putConstraint(SpringLayout.WEST, this.lblTelephone, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.telephone);
		layout.putConstraint(SpringLayout.NORTH, this.telephone, MINIMUM_GAP, SpringLayout.SOUTH, this.lblPIVA);
		layout.putConstraint(SpringLayout.WEST, this.telephone, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtTelephone);
		layout.putConstraint(SpringLayout.NORTH, this.txtTelephone, MINIMUM_GAP, SpringLayout.SOUTH, this.lblPIVA);
		layout.putConstraint(SpringLayout.WEST, this.txtTelephone, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblMail);
		layout.putConstraint(SpringLayout.NORTH, this.lblMail, MINIMUM_GAP, SpringLayout.SOUTH, this.lblTelephone);
		layout.putConstraint(SpringLayout.WEST, this.lblMail, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.mail);
		layout.putConstraint(SpringLayout.NORTH, this.mail, MINIMUM_GAP, SpringLayout.SOUTH, this.lblTelephone);
		layout.putConstraint(SpringLayout.WEST, this.mail, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtMail);
		layout.putConstraint(SpringLayout.NORTH, this.txtMail, MINIMUM_GAP, SpringLayout.SOUTH, this.lblTelephone);
		layout.putConstraint(SpringLayout.WEST, this.txtMail, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.mail.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.mail.addMouseListener(this);

		this.add(this.lblWebAddress);
		layout.putConstraint(SpringLayout.NORTH, this.lblWebAddress, MINIMUM_GAP, SpringLayout.SOUTH, this.lblMail);
		layout.putConstraint(SpringLayout.WEST, this.lblWebAddress, DEFAULT_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.webAddress);
		layout.putConstraint(SpringLayout.NORTH, this.webAddress, MINIMUM_GAP, SpringLayout.SOUTH, this.lblMail);
		layout.putConstraint(SpringLayout.WEST, this.webAddress, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.add(this.txtWebAddress);
		layout.putConstraint(SpringLayout.NORTH, this.txtWebAddress, MINIMUM_GAP, SpringLayout.SOUTH, this.lblMail);
		layout.putConstraint(SpringLayout.WEST, this.txtWebAddress, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		this.webAddress.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.webAddress.addMouseListener(this);

		this.txtUsername.setVisible(false);
		this.txtPassword.setVisible(false);
		this.txtName.setVisible(false);
		this.txtAddress.setVisible(false);
		this.txtTown.setVisible(false);
		this.txtPIVA.setVisible(false);
		this.txtTelephone.setVisible(false);
		this.txtMail.setVisible(false);
		this.txtWebAddress.setVisible(false);
		this.save.setVisible(false);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final Society society = observer.getSociety();
				username.setText(society.getUsername());
				password.setText(society.getPassword());
				name.setText(society.getName());
				address.setText(society.getAddress());
				town.setText(society.getTown());
				partitaIVA.setText(society.getPIVA());
				telephone.setText(society.getTelephone());
				mail.setText(society.getMail());
				if (society.getWebAddress().length() > MAX_STRING_LENGTH) {
					webAddress.setText(society.getWebAddress().substring(0, MAX_STRING_LENGTH) + "...");
				} else {
					webAddress.setText(society.getWebAddress());
				}
				logo.setIcon(observer.getLogo());
			}


			@Override
			public void windowClosing(final WindowEvent e) {
				observer.backToHome();
			}
		});

		this.save.addActionListener(this);
		this.back.addActionListener(this);
		this.setIcon();
		this.setLocation();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(this.back)) {
			this.observer.doBack();
		} else if (source.equals(this.save)) {
			this.observer.doSaveSociety(this.txtUsername.getText(), this.txtPassword.getText(), this.txtName.getText(), this.txtAddress.getText(),
					this.txtTown.getText(), this.txtPIVA.getText(), this.txtTelephone.getText(), this.txtMail.getText(), this.txtWebAddress.getText());
		} else if (source.equals(this.fileContainedMenu[MENU_IDX_IMPORT_SOCIETY])) {
			if (this.observer.doImportSociety()) {
				final ImageIcon newImage = this.observer.getLogo();
				newImage.getImage().flush();
				this.logo.setIcon(newImage);
			}
		} else if (source.equals(this.fileContainedMenu[MENU_IDX_EXPORT_SOCIETY])) {
			this.observer.doExportSociety();
		} else if (source.equals(this.fileContainedMenu[MENU_IDX_EDIT_SOCIETY])) {
			this.observer.doEditSociety();
		} else if (source.equals(this.fileContainedMenu[MENU_IDX_EXIT])) {
			this.observer.doExit();
		} else if (source.equals(this.databaseMenuItem[MENU_IDX_CREATE_DATABASE])) {
			this.observer.doCreateDatabase();
		} else if (source.equals(this.databaseMenuItem[MENU_IDX_DROP_DATABASE])) {
			this.observer.doDropDatabase();
		} else if (source.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		} else if (source.equals(this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS])) {
			this.observer.showFeedbackView();
		}
	}
	/**
	 * Change the labels with textField which permits to edit the text.
	 * @param society contains all society's informations
	 */
	public void editSociety(final Society society) {
		this.txtUsername.setVisible(true);
		this.txtPassword.setVisible(true);
		this.txtName.setVisible(true);
		this.txtAddress.setVisible(true);
		this.txtTown.setVisible(true);
		this.txtPIVA.setVisible(true);
		this.txtTelephone.setVisible(true);
		this.txtMail.setVisible(true);
		this.txtWebAddress.setVisible(true);

		this.txtUsername.setText(society.getUsername());
		this.txtPassword.setText(society.getPassword());
		this.txtName.setText(society.getName());
		this.txtAddress.setText(society.getAddress());
		this.txtTown.setText(society.getTown());
		this.txtPIVA.setText(society.getPIVA());
		this.txtTelephone.setText(society.getTelephone());
		this.txtMail.setText(society.getMail());
		this.txtWebAddress.setText(society.getWebAddress());

		this.save.setVisible(true);

		this.username.setVisible(false);
		this.password.setVisible(false);
		this.name.setVisible(false);
		this.address.setVisible(false);
		this.town.setVisible(false);
		this.partitaIVA.setVisible(false);
		this.telephone.setVisible(false);
		this.mail.setVisible(false);
		this.webAddress.setVisible(false);
	}

	/**
	 * Save the changes to the society's informations.
	 * @param society contains the society's informations
	 */
	public void saveSociety(final Society society) {
		this.txtUsername.setVisible(false);
		this.txtPassword.setVisible(false);
		this.txtName.setVisible(false);
		this.txtAddress.setVisible(false);
		this.txtTown.setVisible(false);
		this.txtPIVA.setVisible(false);
		this.txtTelephone.setVisible(false);
		this.txtMail.setVisible(false);
		this.txtWebAddress.setVisible(false);
		this.save.setVisible(false);

		this.username.setText(society.getUsername());
		this.password.setText(society.getPassword());
		this.name.setText(society.getName());
		this.address.setText(society.getAddress());
		this.town.setText(society.getTown());
		this.partitaIVA.setText(society.getPIVA());
		this.telephone.setText(society.getTelephone());
		this.mail.setText(society.getMail());
		if (society.getWebAddress().length() > MAX_STRING_LENGTH) {
			this.webAddress.setText(society.getWebAddress().substring(0, MAX_STRING_LENGTH) + "...");
		} else {
			this.webAddress.setText(society.getWebAddress());
		}

		this.username.setVisible(true);
		this.password.setVisible(true);
		this.name.setVisible(true);
		this.address.setVisible(true);
		this.town.setVisible(true);
		this.partitaIVA.setVisible(true);
		this.telephone.setVisible(true);
		this.mail.setVisible(true);
		this.webAddress.setVisible(true);
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.webAddress)) {
			e.consume();
			this.observer.openLink(true);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.mail)) {
			e.consume();
			this.observer.openLink(false);
		} else if (e.getClickCount() == 2 && !e.isConsumed() && e.getComponent().equals(this.logo)) {
			e.consume();
			if (this.observer.doChangeLogo()) {
				final ImageIcon newImage = this.observer.getLogo();
				newImage.getImage().flush();
				this.logo.setIcon(newImage);
			}
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
	 * Reset the fields.
	 */
	public void resetNumericField() {
		this.txtPIVA.setText("");
		this.txtTelephone.setText("");
	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param manageSocietyController controller for this view
	 */
	public void attachObserver(final ManageSocietyController manageSocietyController) {
		this.observer = manageSocietyController;

	}
}
