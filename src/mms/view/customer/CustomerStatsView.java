package mms.view.customer;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import mms.controller.customer.CustomerStatsController;
import mms.model.Customer;
import mms.view.AbstractView;

/**
 * Customer view that show the information of the user and the films bought.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class CustomerStatsView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant COLUMN_NAME_FILM. */
	private static final String[] COLUMN_NAME_FILM = new String[]{"Code", "Title", "Genre", "Year", "Price (€)"};

	/** The Constant SELECT_FILM. */
	private static final String SELECT_FILM = "Select a film!";

	/** The Constant CMB_ROW_COUNT. */
	private static final int CMB_ROW_COUNT = 5;

	/** The Constant DEFAULT_SHOP. */
	private static final String DEFAULT_SHOP = "Code Shop";

	/** The Constant MENU_IDX_CONTACT_SOCIETY. */
	private static final int MENU_IDX_CONTACT_SOCIETY = 0;

	/** The Constant MENU_IDX_CONTACT_DEVELOPERS. */
	private static final int MENU_IDX_CONTACT_DEVELOPERS = 1;

	/** The Constant MENU_IDX_ABOUT. */
	private static final int MENU_IDX_ABOUT = 2;

	/** The Constant MENU_IDX_NEW_CARD. */
	private static final int MENU_IDX_NEW_CARD = 0;

	/** The Constant MENU_IDX_SELECT_DISCOUNT. */
	private static final int MENU_IDX_SELECT_DISCOUNT = 1;

	/** The Constant VERTICAL_GAP. */
	private static final int VERTICAL_GAP = 20;

	/** The Constant HORIZONTAL_FIRST_TABULATION. */
	private static final int HORIZONTAL_FIRST_TABULATION = 25;

	/** The Constant HORIZONTAL_SECOND_TABULATION. */
	private static final int HORIZONTAL_SECOND_TABULATION = 15;

	/** The Constant BUTTON_BORDER. */
	private static final int BUTTON_BORDER = 50;

	/** The Constant IMAGE_SIZE. */
	private static final int IMAGE_SIZE = 100;

	/** The file contained menu. */
	private final JMenuItem fileContainedMenu;

	/** The card menu item. */
	private final JMenuItem[] cardMenuItem;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The table data. */
	private final Object[][] tableData = new Object[][]{};

	/** The lbl shops. */
	private final JLabel lblShops = new JLabel("Select shop:");

	/** The shops. */
	private final JComboBox<String> shops = new JComboBox<String>();

	/** The lbl name. */
	private final JLabel lblName = new JLabel("Name:");

	/** The name. */
	private final JLabel name = new JLabel();

	/** The lbl surname. */
	private final JLabel lblSurname = new JLabel("Surname:");

	/** The surname. */
	private final JLabel surname = new JLabel();

	/** The lbl username. */
	private final JLabel lblUsername = new JLabel("Username:");

	/** The username. */
	private final JLabel username = new JLabel();

	/** The lbl date. */
	private final JLabel lblDate = new JLabel("Birth date: ");

	/** The date. */
	private final JLabel date = new JLabel();

	/** The lbl tot film. */
	private final JLabel lblTotFilm = new JLabel("Purchased film:");

	/** The tot film. */
	private final JLabel totFilm = new JLabel();

	/** The lbl tot purchased. */
	private final JLabel lblTotPurchased = new JLabel("Total purchase:");

	/** The tot purchased. */
	private final JLabel totPurchased = new JLabel();

	/** The delete. */
	private final JButton delete = new JButton("Delete Film", new ImageIcon(this.getClass().getResource("/Delete.png")));

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The edit. */
	private final JButton edit = new JButton("Edit User", new ImageIcon(this.getClass().getResource("/Edit.png")));

	/** The details. */
	private final JButton details = new JButton("Details Film", new ImageIcon(this.getClass().getResource("/Detail.png")));

	/** The lbl user image. */
	private final JLabel lblUserImage = new JLabel();

	/** The table. */
	private final JTable table;

	/** The observer. */
	private CustomerStatsController observer;

	/** The curr shop. */
	private Integer currShop;
	/**
	 * Create a new CustomerStatsView.
	 */
	public CustomerStatsView() {

		super();

		this.setTitle("Customer Stats");
		this.setSize(900, 550); //frame size: 900 width, 500 height.
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JMenuBar bar;
		JMenu fileMenu;
		JMenu cardMenu;
		JMenu helpMenu;
		JScrollPane scroll;

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		bar = new JMenuBar();
		fileMenu = new JMenu("File");
		cardMenu = new JMenu("Card");
		helpMenu = new JMenu("Help");
		this.fileContainedMenu = new JMenuItem("Exit", new ImageIcon(this.getClass().getResource("/Exit.png")));
		fileMenu.add(this.fileContainedMenu);
		this.fileContainedMenu.addActionListener(this);

		this.cardMenuItem = new JMenuItem[2];
		this.cardMenuItem[MENU_IDX_NEW_CARD] = new JMenuItem("New Card", new ImageIcon(this.getClass().getResource("/Card.png")));
		this.cardMenuItem[MENU_IDX_SELECT_DISCOUNT] = new JMenuItem("Select Discount", new ImageIcon(this.getClass().getResource("/Discount.png")));
		for (int i = 0; i < 2; i++) {
			cardMenu.add(this.cardMenuItem[i]);
			this.cardMenuItem[i].addActionListener(this);	
		}

		this.helpMenuItem = new JMenuItem[3];
		this.helpMenuItem[MENU_IDX_CONTACT_SOCIETY] = new JMenuItem("Contact Society", new ImageIcon(this.getClass().getResource("/Society.png")));
		this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS] = new JMenuItem("Contact Developers", new ImageIcon(this.getClass().getResource("/Developers.png")));
		this.helpMenuItem[MENU_IDX_ABOUT] = new JMenuItem("About", new ImageIcon(this.getClass().getResource("/About.png")));
		for (int i = 0; i < 3; i++) {
			helpMenu.add(this.helpMenuItem[i]);
			this.helpMenuItem[i].addActionListener(this);	
		}

		bar.add(fileMenu);
		bar.add(cardMenu);
		bar.add(helpMenu);
		setJMenuBar(bar);

		this.table = new JTable(new DefaultTableModel(tableData, COLUMN_NAME_FILM) {

			private static final long serialVersionUID = 1;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		});
		this.table.getColumn(COLUMN_NAME_FILM[0]).setPreferredWidth(40); //set the column size to 40
		this.table.getColumn(COLUMN_NAME_FILM[1]).setPreferredWidth(310); //set the column size to 310
		this.table.getColumn(COLUMN_NAME_FILM[2]).setPreferredWidth(125); //set the column size to 125
		this.table.getColumn(COLUMN_NAME_FILM[3]).setPreferredWidth(50); //set the column size to 50
		this.table.getColumn(COLUMN_NAME_FILM[4]).setPreferredWidth(57); //set the column size to 57
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setFillsViewportHeight(true);
		this.table.setSelectionMode(0);
		this.table.setToolTipText("Double Click to see details");
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		scroll = new JScrollPane(this.table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(scroll);
		layout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scroll, 0, SpringLayout.NORTH, bar);
		layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		this.add(this.lblShops);
		layout.putConstraint(SpringLayout.WEST, this.lblShops, 60, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.NORTH, this.lblShops, 10, SpringLayout.NORTH, bar);
		this.add(this.shops);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.shops, 0, SpringLayout.HORIZONTAL_CENTER, this.lblShops);
		layout.putConstraint(SpringLayout.NORTH, this.shops, 5, SpringLayout.SOUTH, this.lblShops);
		this.shops.addItem(DEFAULT_SHOP);
		this.shops.setSelectedIndex(0);
		this.shops.setMaximumRowCount(CMB_ROW_COUNT);
		this.shops.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (shops.getSelectedItem().equals(DEFAULT_SHOP)) {
						currShop = null;
						observer.generateTable(currShop);
						observer.getTotPurchase(currShop);
					} else {
						currShop = Integer.parseInt((String) shops.getSelectedItem());
						observer.generateTable(currShop);
						observer.getTotPurchase(currShop);
					}
				}
			}
		});

		this.add(this.lblName);
		layout.putConstraint(SpringLayout.NORTH, this.lblName, VERTICAL_GAP, SpringLayout.SOUTH, this.shops);
		layout.putConstraint(SpringLayout.WEST, this.lblName, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);
		this.add(this.name);
		layout.putConstraint(SpringLayout.NORTH, this.name, VERTICAL_GAP, SpringLayout.SOUTH, this.shops);
		layout.putConstraint(SpringLayout.WEST, this.name, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblName);

		this.add(this.lblSurname);
		layout.putConstraint(SpringLayout.NORTH, this.lblSurname, VERTICAL_GAP, SpringLayout.SOUTH, this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.lblSurname, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);
		this.add(this.surname);		
		layout.putConstraint(SpringLayout.NORTH, this.surname, VERTICAL_GAP, SpringLayout.SOUTH, this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.surname, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblSurname);

		this.add(this.lblUsername);
		layout.putConstraint(SpringLayout.NORTH, this.lblUsername, VERTICAL_GAP, SpringLayout.SOUTH, this.lblSurname);
		layout.putConstraint(SpringLayout.WEST, this.lblUsername, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);
		this.add(this.username);
		layout.putConstraint(SpringLayout.NORTH, this.username, VERTICAL_GAP, SpringLayout.SOUTH, this.lblSurname);
		layout.putConstraint(SpringLayout.WEST, this.username, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblUsername);

		this.add(this.lblDate);
		layout.putConstraint(SpringLayout.NORTH, this.lblDate, VERTICAL_GAP, SpringLayout.SOUTH, this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.lblDate, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);
		this.add(this.date);
		layout.putConstraint(SpringLayout.NORTH, this.date, VERTICAL_GAP, SpringLayout.SOUTH, this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.date, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblDate);

		this.add(this.lblTotFilm);
		layout.putConstraint(SpringLayout.NORTH, this.lblTotFilm, VERTICAL_GAP, SpringLayout.SOUTH, this.lblDate);
		layout.putConstraint(SpringLayout.WEST, this.lblTotFilm, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);
		this.add(this.totFilm);
		layout.putConstraint(SpringLayout.NORTH, this.totFilm, VERTICAL_GAP, SpringLayout.SOUTH, this.lblDate);
		layout.putConstraint(SpringLayout.WEST, this.totFilm, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblTotFilm);

		this.add(this.lblTotPurchased);
		layout.putConstraint(SpringLayout.NORTH, this.lblTotPurchased, VERTICAL_GAP, SpringLayout.SOUTH, this.lblTotFilm);
		layout.putConstraint(SpringLayout.WEST, this.lblTotPurchased, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, scroll);
		this.add(this.totPurchased);
		layout.putConstraint(SpringLayout.NORTH, this.totPurchased, VERTICAL_GAP, SpringLayout.SOUTH, this.lblTotFilm);
		layout.putConstraint(SpringLayout.WEST, this.totPurchased, HORIZONTAL_SECOND_TABULATION, SpringLayout.EAST, this.lblTotFilm);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());
		this.add(this.delete);
		layout.putConstraint(SpringLayout.SOUTH, this.delete, -VERTICAL_GAP, SpringLayout.NORTH, this.back);
		layout.putConstraint(SpringLayout.WEST, this.delete, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.delete, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());
		this.delete.setVisible(false);
		this.add(this.edit);
		layout.putConstraint(SpringLayout.SOUTH, this.edit, -VERTICAL_GAP, SpringLayout.NORTH, this.delete);
		layout.putConstraint(SpringLayout.WEST, this.edit, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.edit, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());
		this.edit.setVisible(false);
		this.add(this.details);
		layout.putConstraint(SpringLayout.SOUTH, this.details, -VERTICAL_GAP, SpringLayout.NORTH, this.edit);
		layout.putConstraint(SpringLayout.WEST, this.details, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());
		this.details.setVisible(false);

		this.add(this.lblUserImage);
		layout.putConstraint(SpringLayout.EAST, this.lblUserImage, -VERTICAL_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblUserImage, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.lblUserImage, -IMAGE_SIZE, SpringLayout.EAST, this.lblUserImage);
		layout.putConstraint(SpringLayout.SOUTH, this.lblUserImage, IMAGE_SIZE, SpringLayout.NORTH, this.lblUserImage);
		this.lblUserImage.setToolTipText("Double Click to change profile image");

		this.delete.addActionListener(this);
		this.edit.addActionListener(this);
		this.back.addActionListener(this);
		this.table.addMouseListener(this);
		this.details.addActionListener(this);
		this.lblUserImage.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final Customer user = observer.getCustomer();
				name.setText(user.getName());
				surname.setText(user.getSurname());
				username.setText(user.getUsername());
				final Date birthdate = user.getBirthDate();
				final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				date.setText(df.format(birthdate));
				lblUserImage.setIcon(observer.getProfileImage());
				observer.getTotPurchase(currShop);
				observer.doFillCmb();
				observer.generateTable(currShop);
				if (observer.checkCustomer()) {
					delete.setVisible(true);
					details.setVisible(true);
					edit.setVisible(true);
				}				
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.backToHome();	
			}

		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (selection.equals(this.back)) {
			this.observer.doBack();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			if (this.observer.checkCustomer()) {
				this.observer.showCreditsView();
			}
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_SOCIETY])) {
			if (this.observer.checkCustomer()) {
				this.observer.contactSociety();
			}
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS])) {
			if (this.observer.checkCustomer()) {
				this.observer.showFeedbackView();
			}
		} else if (selection.equals(this.cardMenuItem[MENU_IDX_NEW_CARD])) {
			if (this.observer.checkCustomer()) {
				this.observer.getNewCard();
			}
		} else if (selection.equals(this.cardMenuItem[MENU_IDX_SELECT_DISCOUNT])) {
			if (this.observer.checkCustomer()) {
				this.observer.selectDiscount();
			}
		} else if (selection.equals(this.fileContainedMenu)) {
			if (this.observer.checkCustomer()) {
				this.observer.doExit();
			}

		} else if (selection.equals(this.details)) {
			if (this.observer.checkCustomer()) {
				try {
					final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.showDetailsFilm(idFilm, this.currShop);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			}
		} else if (selection.equals(this.edit)) {
			this.observer.doEditUser();
		} else if (selection.equals(this.delete)) {
			try {
				final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				if (this.observer.doDelete(value, currShop)) {
					((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
				}
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (this.observer.checkCustomer()) {
			if (e.getClickCount() == 2 && !e.isConsumed() && e.getComponent().equals(this.table)) {
				e.consume();
				try {
					final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.showDetailsFilm(codFilm, this.currShop);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			}
			if (e.getClickCount() == 2 && !e.isConsumed() && e.getComponent().equals(this.lblUserImage)) {
				e.consume();
				if (this.observer.changeProfileImage()) {
					final ImageIcon newImage = this.observer.getProfileImage();
					newImage.getImage().flush();
					this.lblUserImage.setIcon(newImage);
				}

			}

		}

	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param customerStatsController controller for this view
	 */
	public void attachObserver(final CustomerStatsController customerStatsController) {
		this.observer = customerStatsController;
	}

	/**
	 * Set button visibility.
	 */
	public void setEditableVisible() {
		this.edit.setVisible(false);
	}

	/**
	 * Set the value of this field.
	 * @param value value
	 */
	public void setFilmVal(final Integer value) {
		this.totFilm.setText(value + "");
	}

	/**
	 * Set the value of this field.
	 * @param value value
	 */
	public void setPurchasedVal(final float value) {
		this.totPurchased.setText("€ " + String.format("%.2f", value));
	}

	/**
	 * Fill the combobox with the code of the shops.
	 * @param code of the shop
	 */
	public void fillShopCMB(final String code) {
		this.shops.addItem(code);
	}

	/**
	 * Add to the table the raw passed as parameter.
	 * @param obj new row to add to the table
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);		
	}

	/**
	 * Clean the table.
	 */
	public void refreshTable() {
		((DefaultTableModel) this.table.getModel()).getDataVector().clear();
		((DefaultTableModel) this.table.getModel()).fireTableDataChanged();
	}

}