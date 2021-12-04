package mms.view.admin;

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

import mms.controller.admin.ManageCustomerController;
import mms.view.AbstractView;

/**
 * Permit to the administrator to manage the customers's informations.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class ManageCustomerView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant COLUMN_NAME_USER. */
	private static final String[] COLUMN_NAME_USER = new String[]{"Username", "Password", "Name", "Surname", "Date"};

	/** The Constant DEFAULT_SHOP. */
	private static final String DEFAULT_SHOP = "Code Shop";

	/** The Constant SELECT_USER. */
	private static final String SELECT_USER = "Select an user!";

	/** The Constant CMB_ROW_COUNT. */
	private static final int CMB_ROW_COUNT = 5;

	/** The Constant LEFT_BORDER_BUTTON. */
	private static final int LEFT_BORDER_BUTTON = 40;

	/** The Constant RIGHT_BORDER_BUTTON. */
	private static final int RIGHT_BORDER_BUTTON = 160;

	/** The Constant VERTICAL_GAP. */
	private static final int VERTICAL_GAP = 20;

	/** The Constant MAX_VERTICAL_GAP. */
	private static final int MAX_VERTICAL_GAP = 75;

	/** The Constant MENU_IDX_CONTACT_SOCIETY. */
	private static final Integer MENU_IDX_CONTACT_SOCIETY = 0;

	/** The Constant MENU_IDX_CONTACT_DEVELOPERS. */
	private static final int MENU_IDX_CONTACT_DEVELOPERS = 1;

	/** The Constant MENU_IDX_ABOUT. */
	private static final Integer MENU_IDX_ABOUT = 2;

	/** The loading gif. */
	private final JLabel loadingGif = new JLabel(new ImageIcon(this.getClass().getResource("/Loading.gif")));

	/** The exit item. */
	private final JMenuItem exitItem;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The lbl shops. */
	private final JLabel lblShops = new JLabel("Select shop:");

	/** The shops. */
	private final JComboBox<String> shops = new JComboBox<String>();

	/** The delete. */
	private final JButton delete = new JButton("Delete User", new ImageIcon(this.getClass().getResource("/Delete.png")));

	/** The edit. */
	private final JButton edit = new JButton("Edit User", new ImageIcon(this.getClass().getResource("/Edit_File.png")));

	/** The stats. */
	private final JButton stats = new JButton("User Stats", new ImageIcon(this.getClass().getResource("/Stats.png")));

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The table. */
	private final JTable table;

	/** The table data. */
	private final Object[][] tableData = new Object[][]{};

	/** The curr shop. */
	private Integer currShop;

	/** The observer. */
	private ManageCustomerController observer;

	/**
	 * Create a new ManageCustomerView.
	 */
	public ManageCustomerView() {

		super();

		this.setSize(800, 450); //frame size: 800 width, 450 height.
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

		this.table = new JTable(new DefaultTableModel(this.tableData, COLUMN_NAME_USER) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		});
		this.table.getColumn(COLUMN_NAME_USER[0]).setPreferredWidth(125); //set the column size to 125
		this.table.getColumn(COLUMN_NAME_USER[1]).setPreferredWidth(125); //set the column size to 125
		this.table.getColumn(COLUMN_NAME_USER[2]).setPreferredWidth(125); //set the column size to 125
		this.table.getColumn(COLUMN_NAME_USER[3]).setPreferredWidth(125); //set the column size to 125
		this.table.getColumn(COLUMN_NAME_USER[4]).setPreferredWidth(82); //set the column size to 82
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
		//600 is the width of the scroll.

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
						setTitle("Manage Customer (All Shops)");
						currShop = null;
						observer.generateTable(currShop);
					} else {
						currShop = Integer.parseInt((String) shops.getSelectedItem());
						setTitle("Manage Customer (Shop: " + currShop + ")");
						observer.generateTable(currShop);
					}
				}
			}
		});

		this.add(this.edit);
		layout.putConstraint(SpringLayout.NORTH, this.edit, MAX_VERTICAL_GAP, SpringLayout.SOUTH, shops);
		layout.putConstraint(SpringLayout.WEST, this.edit, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.edit, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.delete);
		layout.putConstraint(SpringLayout.NORTH, this.delete, VERTICAL_GAP, SpringLayout.SOUTH, this.edit);
		layout.putConstraint(SpringLayout.WEST, this.delete, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.delete, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.stats);
		layout.putConstraint(SpringLayout.NORTH, this.stats, VERTICAL_GAP, SpringLayout.SOUTH, this.delete);
		layout.putConstraint(SpringLayout.WEST, this.stats, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.stats, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.loadingGif, 1, 0);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.loadingGif, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.loadingGif, 0, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		this.loadingGif.setVisible(false);

		this.edit.addActionListener(this);
		this.delete.addActionListener(this);
		this.stats.addActionListener(this);
		this.back.addActionListener(this);
		this.table.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				setTitle("Manage Customer (All Shops)");
				observer.doFillCmb();
				observer.generateTable(currShop);
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
		if (selection.equals(this.edit)) {
			try {
				final String userId = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doEditUser(userId);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_USER);
			}

		} else if (selection.equals(delete)) {
			try {
				final String userId = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
				if (this.observer.doDeleteUser(userId)) {
					((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
				}
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_USER);
			}

		} else if (selection.equals(this.stats)) {
			try {
				final String selectedUser = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doUserStats(selectedUser);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_USER);
			}
		} else if (selection.equals(this.back)) {
			this.observer.doBack();

		} else if (selection.equals(this.exitItem)) {
			this.observer.doExit();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS])) {
			this.observer.showFeedbackView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_SOCIETY])) {
			this.observer.contactSociety();
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed()) {
			e.consume();
			try {
				final String selectedUser = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doUserStats(selectedUser);
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_USER);
			}
		}
	}

	/**
	 * Fill the combobox with the codes of the shops.
	 * @param code of the shop
	 */
	public void fillAdminCMB(final String code) {
		this.shops.addItem(code);
	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param manageUserController controller for this view
	 */
	public void attachObserver(final ManageCustomerController manageUserController) {
		this.observer = manageUserController;		
	}

	/**
	 * Add to the table the raw passed as parameter.
	 * @param obj new row to add to the table
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);

	}

	/**
	 * Clean the table before add new row.
	 */
	public void refreshTable() {
		((DefaultTableModel) this.table.getModel()).getDataVector().clear();
		((DefaultTableModel) this.table.getModel()).fireTableDataChanged();
	}

	/**
	 * Set JLable visibility.
	 */
	public void setUnsetLoadingVisible() {
		if (this.loadingGif.isVisible()) {
			this.loadingGif.setVisible(false);
		} else {
			this.loadingGif.setVisible(true);
		}
	}
}
