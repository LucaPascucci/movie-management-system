package mms.view.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

import mms.controller.admin.AdminStatsController;
import mms.view.AbstractView;

/**
 * Shows to the admin the list of the films with different type of filter and the statistics of the film and money incomes.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class AdminStatsView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant DEFAULT_SHOP. */
	private static final String DEFAULT_SHOP = "Code Shop";

	/** The Constant SELECT_FILM. */
	private static final String SELECT_FILM = "Select a film!";

	/** The Constant CMB_ROW_COUNT. */
	private static final int CMB_ROW_COUNT = 5;

	/** The Constant HORIZONTAL_LBL_TABULATION. */
	private static final int HORIZONTAL_LBL_TABULATION = 20;

	/** The Constant VERTICAL_GAP. */
	private static final int VERTICAL_GAP = 20;

	/** The Constant MEDIUM_VERTICAL_GAP. */
	private static final int MEDIUM_VERTICAL_GAP = 40;

	/** The Constant MINIMUM_GAP. */
	private static final int MINIMUM_GAP = 5;

	/** The Constant BUTTON_GAP_TO_FRAME. */
	private static final int BUTTON_GAP_TO_FRAME = 40;

	/** The Constant LABEL_GAP. */
	private static final int LABEL_GAP = 15;

	/** The Constant CHAR_FONT. */
	private static final int CHAR_FONT = 14;

	/** The Constant MENU_IDX_CONTACT_SOCIETY. */
	private static final int MENU_IDX_CONTACT_SOCIETY = 0;

	/** The Constant MENU_IDX_CONTACT_DEVELOPERS. */
	private static final int MENU_IDX_CONTACT_DEVELOPERS = 1;

	/** The Constant MENU_IDX_ABOUT. */
	private static final int MENU_IDX_ABOUT = 2;

	/** The Constant COLUMN_NAME_FILM. */
	private static final String[] COLUMN_NAME_FILM = new String[]{"Code", "Title", "Genre", "Year", "Price (€)"};

	/** The Constant ORANGE_COLOR. */
	private static final Color ORANGE_COLOR = new Color(248, 192, 61);

	/** The exit item. */
	private final JMenuItem exitItem;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The table data. */
	private final Object[][] tableData = new Object[][]{};

	/** The lbl shops. */
	private final JLabel lblShops = new JLabel("Select shop:");

	/** The shops. */
	private final JComboBox<String> shops = new JComboBox<String>();

	/** The lbl total films. */
	private final JLabel lblTotalFilms = new JLabel();

	/** The lbl registered customers. */
	private final JLabel lblRegisteredCustomers = new JLabel();

	/** The lbl total incomes. */
	private final JLabel lblTotalIncomes = new JLabel();

	/** The lbl film list. */
	private final JLabel lblFilmList = new JLabel("Film List");

	/** The lbl top view. */
	private final JLabel lblTopView = new JLabel("Top View");

	/** The lbl top bought. */
	private final JLabel lblTopBought = new JLabel("Top Bought");

	/** The lbl recent. */
	private final JLabel lblRecent = new JLabel("Recent");

	/** The details. */
	private final JButton details = new JButton("Details Film", new ImageIcon(this.getClass().getResource("/Detail.png")));

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The change password. */
	private final JButton changePassword = new JButton("Change Password", new ImageIcon(this.getClass().getResource("/Lock.png")));

	/** The table. */
	private final JTable table;

	/** The curr shop. */
	private Integer currShop;

	/** The curr table. */
	private Integer currTable = 1;

	/** The observer. */
	private AdminStatsController observer;

	/**
	 * Creates a new AdminStatsView.
	 */
	public AdminStatsView() {

		super();

		this.setSize(850, 450); //frame size: 850 width, 450 height.
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

		JScrollPane scroll;

		this.table = new JTable(new DefaultTableModel(tableData, COLUMN_NAME_FILM) {

			private static final long serialVersionUID = 1L;

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
		this.table.setToolTipText("Double click to see details");
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		scroll = new JScrollPane(this.table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(scroll);
		layout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scroll, MINIMUM_GAP, SpringLayout.SOUTH, this.lblFilmList);
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
						setTitle("Admin Stats (All Shops)");
					} else {
						currShop = Integer.parseInt((String) shops.getSelectedItem());
						setTitle("Admin Stats (Shop: " + currShop + ")");
					}
					observer.setTotalIncomes(currShop);
					observer.setTotalCustomer(currShop);
					if (currTable == 1) {
						observer.generateTableFilmList(currShop); 
					} else if (currTable == 2) {
						observer.generateTableTopVisited(currShop); 
					} else if (currTable == 3) {
						observer.generateTableTopBought(currShop);
					} else if (currTable == 4) {
						observer.generateTableRecent(currShop);
					}

				}
			}
		});

		this.add(this.lblRegisteredCustomers);
		layout.putConstraint(SpringLayout.NORTH, this.lblRegisteredCustomers, MEDIUM_VERTICAL_GAP, SpringLayout.SOUTH, this.shops);
		layout.putConstraint(SpringLayout.WEST, this.lblRegisteredCustomers, HORIZONTAL_LBL_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.lblTotalIncomes);
		layout.putConstraint(SpringLayout.NORTH, this.lblTotalIncomes, VERTICAL_GAP, SpringLayout.SOUTH, this.lblRegisteredCustomers);
		layout.putConstraint(SpringLayout.WEST, this.lblTotalIncomes, HORIZONTAL_LBL_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.lblTotalFilms);
		layout.putConstraint(SpringLayout.NORTH, this.lblTotalFilms, VERTICAL_GAP, SpringLayout.SOUTH, this.lblTotalIncomes);
		layout.putConstraint(SpringLayout.WEST, this.lblTotalFilms, HORIZONTAL_LBL_TABULATION, SpringLayout.EAST, scroll);

		this.add(this.lblFilmList);
		layout.putConstraint(SpringLayout.WEST, this.lblFilmList, MINIMUM_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblFilmList, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.lblFilmList.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblFilmList.setBackground(ORANGE_COLOR);
		this.lblFilmList.setOpaque(true);

		this.add(this.lblTopView);
		layout.putConstraint(SpringLayout.WEST, this.lblTopView, LABEL_GAP, SpringLayout.EAST, this.lblFilmList);
		layout.putConstraint(SpringLayout.NORTH, this.lblTopView, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.lblTopView.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblTopView.setOpaque(true);

		this.add(this.lblTopBought);
		layout.putConstraint(SpringLayout.WEST, this.lblTopBought, LABEL_GAP, SpringLayout.EAST, this.lblTopView);
		layout.putConstraint(SpringLayout.NORTH, this.lblTopBought, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.lblTopBought.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblTopBought.setOpaque(true);

		this.add(this.lblRecent);
		layout.putConstraint(SpringLayout.WEST, this.lblRecent, LABEL_GAP, SpringLayout.EAST, this.lblTopBought);
		layout.putConstraint(SpringLayout.NORTH, this.lblRecent, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.lblRecent.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblRecent.setOpaque(true);

		this.add(this.details);
		layout.putConstraint(SpringLayout.NORTH, this.details, MEDIUM_VERTICAL_GAP, SpringLayout.SOUTH, this.lblTotalFilms);
		layout.putConstraint(SpringLayout.WEST, this.details, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.add(this.changePassword);
		layout.putConstraint(SpringLayout.NORTH, this.changePassword, VERTICAL_GAP, SpringLayout.SOUTH, this.details);
		layout.putConstraint(SpringLayout.WEST, this.changePassword, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.changePassword, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.lblFilmList.addMouseListener(this);
		this.lblTopView.addMouseListener(this);
		this.lblTopBought.addMouseListener(this);
		this.lblRecent.addMouseListener(this);
		this.back.addActionListener(this);
		this.changePassword.addActionListener(this);
		this.details.addActionListener(this);
		this.table.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				setTitle("Admin Stats (All Shops)");
				observer.generateTableFilmList(currShop);
				observer.setTotalCustomer(currShop);
				observer.setTotalIncomes(currShop);
				observer.doFillCmb();
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
		} else if (selection.equals(this.exitItem)) {
			this.observer.doExit();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS])) {
			this.observer.showFeedbackView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_SOCIETY])) {
			this.observer.contactSociety();
		} else if (selection.equals(this.changePassword)) {
			this.observer.doChangePassword();
		} else if (selection.equals(this.details)) {				
			try {
				final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(idFilm, this.currShop);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed()  && e.getComponent().equals(this.table)) {
			try {
				e.consume();
				final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(idFilm, this.currShop);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblFilmList) && !this.lblFilmList.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.currTable = 1;
			this.lblTopView.setBackground(null);
			this.lblFilmList.setBackground(ORANGE_COLOR);
			this.lblTopBought.setBackground(null);
			this.lblRecent.setBackground(null);
			this.observer.generateTableFilmList(currShop);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblTopView) && !this.lblTopView.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.currTable = 2;
			this.lblTopView.setBackground(ORANGE_COLOR);
			this.lblFilmList.setBackground(null);
			this.lblTopBought.setBackground(null);
			this.lblRecent.setBackground(null);
			this.observer.generateTableTopVisited(this.currShop);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblTopBought) && !this.lblTopBought.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.currTable = 3;
			this.lblTopView.setBackground(null);
			this.lblFilmList.setBackground(null);
			this.lblTopBought.setBackground(ORANGE_COLOR);
			this.lblRecent.setBackground(null);
			this.observer.generateTableTopBought(currShop);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblRecent) && !this.lblRecent.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.currTable = 4;
			this.lblTopView.setBackground(null);
			this.lblRecent.setBackground(ORANGE_COLOR);
			this.lblTopBought.setBackground(null);
			this.lblFilmList.setBackground(null);
			this.observer.generateTableRecent(currShop);
		}
	}

	/**
	 * Fill the combobox with the code of the shops.
	 * @param code code
	 */
	public void fillAdminCMB(final String code) {
		this.shops.addItem(code);
	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param adminStatsController controller for this view
	 */
	public void attachObserver(final AdminStatsController adminStatsController) {
		this.observer = adminStatsController;
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

	/**
	 * Set the value of this label.
	 * @param value value
	 */
	public void setTotalCustomer(final int value) {
		this.lblRegisteredCustomers.setText("Registered Customers: " + value);
	}

	/**
	 * Set the value of this label.
	 * @param value value
	 */
	public void setTotalIncomes(final float value) {
		this.lblTotalIncomes.setText("Total incomes: € " + String.format("%.2f", value));
	}

	/**
	 * Set the value of this label.
	 * @param value value
	 */
	public void setTotalFilms(final int value) {
		this.lblTotalFilms.setText("Available films: " + value);
	}
}
