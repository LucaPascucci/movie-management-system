package mms.view.society;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import mms.controller.society.ManageShopController;
import mms.view.AbstractView;

/**
 * Permits the managing of the administrators, shops and discounts.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class ManageShopView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant ORANGE_COLOR. */
	private static final Color ORANGE_COLOR = new Color(248, 192, 61);

	/** The Constant COLUMN_NAME_ADMIN. */
	private static final String[] COLUMN_NAME_ADMIN = new String[]{"Username", "Password", "Name", "Surname"};

	/** The Constant COLUMN_NAME_SHOP. */
	private static final String[] COLUMN_NAME_SHOP = new String[]{"Code Shop", "Address", "Telephone", "Admin's Username"};

	/** The Constant COLUMN_NAME_DISCOUNT. */
	private static final String[] COLUMN_NAME_DISCOUNT = new String[]{"Code Discount", "Percentage (%)", "Period (days)", "Discount Score"};

	/** The Constant SELECT_RECORD. */
	private static final String SELECT_RECORD = "Select a record in the table!";

	/** The Constant CHAR_FONT. */
	private static final int CHAR_FONT = 14;

	/** The Constant MINIMUM_GAP. */
	private static final int MINIMUM_GAP = 5;

	/** The Constant LABEL_GAP. */
	private static final int LABEL_GAP = 15;

	/** The Constant LEFT_BORDER_BUTTON. */
	private static final int LEFT_BORDER_BUTTON = 30;

	/** The Constant RIGHT_BORDER_BUTTON. */
	private static final int RIGHT_BORDER_BUTTON = 165;

	/** The Constant NORTH_GAP. */
	private static final int NORTH_GAP = 20;

	/** The Constant MENU_IDX_EXIT. */
	private static final int MENU_IDX_EXIT = 0;

	/** The Constant MENU_IDX_CONTACT_DEVELOPERS. */
	private static final int MENU_IDX_CONTACT_DEVELOPERS = 0;

	/** The Constant MENU_IDX_ABOUT. */
	private static final int MENU_IDX_ABOUT = 1;

	/** The file contained menu. */
	private final JMenuItem[] fileContainedMenu;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The admin. */
	private final JLabel admin = new JLabel("Admins");

	/** The shop. */
	private final JLabel shop = new JLabel("Shops");

	/** The discount. */
	private final JLabel discount = new JLabel("Discounts");

	/** The table data. */
	private final Object[][] tableData = new Object[][]{};

	/** The table. */
	private final JTable table;

	/** The add. */
	private final JButton add = new JButton("New admin", new ImageIcon(this.getClass().getResource("/Add.png")));

	/** The edit. */
	private final JButton edit = new JButton("Edit admin", new ImageIcon(this.getClass().getResource("/Edit.png")));

	/** The delete. */
	private final JButton delete = new JButton("Delete admin", new ImageIcon(this.getClass().getResource("/Delete.png")));

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The scroll. */
	private JScrollPane scroll;

	/** The current table. */
	private String currentTable = "Admins";

	/** The observer. */
	private ManageShopController observer;

	/**
	 * The constructor of the view.
	 */
	public ManageShopView() {

		super();

		this.setTitle("Manage Admins");
		this.setSize(800, 450); //frame size: 800 width, 450 height.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		final JMenuBar bar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenu helpMenu = new JMenu("Help");
		this.fileContainedMenu = new JMenuItem[1];
		this.fileContainedMenu[MENU_IDX_EXIT] = new JMenuItem("Exit", new ImageIcon(this.getClass().getResource("/Exit.png")));
		fileMenu.add(this.fileContainedMenu[MENU_IDX_EXIT]);
		this.fileContainedMenu[MENU_IDX_EXIT].addActionListener(this);

		this.helpMenuItem = new JMenuItem[2];
		this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS] = new JMenuItem("Contact Developers", new ImageIcon(this.getClass().getResource("/Developers.png")));
		this.helpMenuItem[MENU_IDX_ABOUT] = new JMenuItem("About", new ImageIcon(this.getClass().getResource("/About.png")));
		for (int i = 0; i < 2; i++) {
			helpMenu.add(this.helpMenuItem[i]);
			this.helpMenuItem[i].addActionListener(this);
		}

		bar.add(fileMenu);
		bar.add(helpMenu);
		this.setJMenuBar(bar);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.admin);
		layout.putConstraint(SpringLayout.WEST, this.admin, LABEL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.admin, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.admin.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.admin.setOpaque(true);
		this.admin.setBackground(ORANGE_COLOR);

		this.add(this.shop);
		layout.putConstraint(SpringLayout.WEST, this.shop, LABEL_GAP, SpringLayout.EAST, this.admin);
		layout.putConstraint(SpringLayout.NORTH, this.shop, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.shop.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.shop.setOpaque(true);
		this.shop.setBackground(null);

		this.add(this.discount);
		layout.putConstraint(SpringLayout.WEST, this.discount, LABEL_GAP, SpringLayout.EAST, this.shop);
		layout.putConstraint(SpringLayout.NORTH, this.discount, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.discount.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.discount.setOpaque(true);
		this.discount.setBackground(null);


		this.table = new JTable(new DefaultTableModel(this.tableData, COLUMN_NAME_ADMIN) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		});
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setFillsViewportHeight(true);
		this.table.setSelectionMode(0);
		this.table.setToolTipText("Double click to edit");
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.scroll = new JScrollPane(this.table);
		this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.scroll = new JScrollPane(this.table);
		this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(this.scroll);
		layout.putConstraint(SpringLayout.WEST, this.scroll, 0, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.scroll, MINIMUM_GAP, SpringLayout.SOUTH, this.admin);
		layout.putConstraint(SpringLayout.SOUTH, this.scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		this.add(this.add);
		layout.putConstraint(SpringLayout.SOUTH, this.add, -NORTH_GAP, SpringLayout.NORTH, this.edit);
		layout.putConstraint(SpringLayout.WEST, this.add, LEFT_BORDER_BUTTON, SpringLayout.EAST, this.scroll);
		layout.putConstraint(SpringLayout.EAST, this.add, RIGHT_BORDER_BUTTON, SpringLayout.EAST, this.scroll);
		this.add(this.edit);
		layout.putConstraint(SpringLayout.SOUTH, this.edit, 0, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.edit, LEFT_BORDER_BUTTON, SpringLayout.EAST, this.scroll);
		layout.putConstraint(SpringLayout.EAST, this.edit, RIGHT_BORDER_BUTTON, SpringLayout.EAST, this.scroll);
		this.add(this.delete);
		layout.putConstraint(SpringLayout.NORTH, this.delete, NORTH_GAP, SpringLayout.SOUTH, this.edit);
		layout.putConstraint(SpringLayout.WEST, this.delete, LEFT_BORDER_BUTTON, SpringLayout.EAST, this.scroll);
		layout.putConstraint(SpringLayout.EAST, this.delete, RIGHT_BORDER_BUTTON, SpringLayout.EAST, this.scroll);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -NORTH_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, LEFT_BORDER_BUTTON, SpringLayout.EAST, this.scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, RIGHT_BORDER_BUTTON, SpringLayout.EAST, this.scroll);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				observer.generateTable(currentTable);
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.backToHome();
			}
		});

		this.table.addMouseListener(this);
		this.admin.addMouseListener(this);
		this.shop.addMouseListener(this);
		this.discount.addMouseListener(this);
		this.add.addActionListener(this);
		this.edit.addActionListener(this);
		this.delete.addActionListener(this);
		this.back.addActionListener(this);
		this.setLocation();
		this.setIcon();

	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object buttonSelected = e.getSource();
		if (buttonSelected.equals(this.add)) {
			this.observer.doAdd(this.currentTable);
		} else if (buttonSelected.equals(this.edit)) {
			try {
				final Object value = this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doEdit(this.currentTable, value);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_RECORD);
			}
		} else if (buttonSelected.equals(this.delete)) {
			try {
				final Object value = this.table.getValueAt(this.table.getSelectedRow(), 0);
				if (this.observer.doDelete(this.currentTable, value)) {
					((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
				}
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_RECORD);
			}
		} else if (buttonSelected.equals(this.back)) {
			this.observer.doBack();
		} else if (buttonSelected.equals(this.fileContainedMenu[MENU_IDX_EXIT])) {
			this.observer.doExit();
		} else if (buttonSelected.equals(this.back)) {
			this.observer.doBack();
		} else if (buttonSelected.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		} else if (buttonSelected.equals(this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS])) {
			this.observer.showFeedbackView();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.admin) && !this.admin.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.setTitle("Manage Admins");
			this.admin.setBackground(ORANGE_COLOR);
			this.shop.setBackground(null);
			this.discount.setBackground(null);
			this.add.setText("New admin");
			this.edit.setText("Edit admin");
			this.delete.setText("Delete admin");
			this.currentTable = this.admin.getText();

			final JTableHeader th = this.table.getTableHeader();
			final TableColumnModel tcm = th.getColumnModel();
			final TableColumn column0 = tcm.getColumn(0);
			final TableColumn column1 = tcm.getColumn(1);
			final TableColumn column2 = tcm.getColumn(2);
			final TableColumn column3 = tcm.getColumn(3);
			column0.setHeaderValue(COLUMN_NAME_ADMIN[0]);
			column1.setHeaderValue(COLUMN_NAME_ADMIN[1]);
			column2.setHeaderValue(COLUMN_NAME_ADMIN[2]);
			column3.setHeaderValue(COLUMN_NAME_ADMIN[3]);
			this.table.getColumn("Username").setPreferredWidth(125); //set the column size to 125
			this.table.getColumn("Password").setPreferredWidth(125); //set the column size to 125
			this.table.getColumn("Name").setPreferredWidth(125); //set the column size to 125
			this.table.getColumn("Surname").setPreferredWidth(125); //set the column size to 125
			th.repaint();
			this.refreshTable();
			this.observer.generateTable(currentTable);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.shop) && !this.shop.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.setTitle("Manage Shops");
			this.shop.setBackground(ORANGE_COLOR);
			this.admin.setBackground(null);
			this.discount.setBackground(null);
			this.add.setText("New shop");
			this.edit.setText("Edit shop");
			this.delete.setText("Delete shop");
			this.currentTable = this.shop.getText();

			final JTableHeader th = this.table.getTableHeader();
			final TableColumnModel tcm = th.getColumnModel();
			final TableColumn column0 = tcm.getColumn(0);
			final TableColumn column1 = tcm.getColumn(1);
			final TableColumn column2 = tcm.getColumn(2);
			final TableColumn column3 = tcm.getColumn(3);
			column0.setHeaderValue(COLUMN_NAME_SHOP[0]);
			column1.setHeaderValue(COLUMN_NAME_SHOP[1]);
			column2.setHeaderValue(COLUMN_NAME_SHOP[2]);
			column3.setHeaderValue(COLUMN_NAME_SHOP[3]);
			this.table.getColumn("Code Shop").setPreferredWidth(40);
			this.table.getColumn("Address").setPreferredWidth(200);
			this.table.getColumn("Telephone").setPreferredWidth(100);
			this.table.getColumn("Admin's Username").setPreferredWidth(110);
			th.repaint();
			this.refreshTable();
			this.observer.generateTable(this.currentTable);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.discount) && !this.discount.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.setTitle("Manage Discounts");
			this.discount.setBackground(ORANGE_COLOR);
			this.admin.setBackground(null);
			this.shop.setBackground(null);
			this.add.setText("New discount");
			this.edit.setText("Edit discount");
			this.delete.setText("Delete discount");
			this.currentTable = this.discount.getText();

			final JTableHeader th = this.table.getTableHeader();
			final TableColumnModel tcm = th.getColumnModel();
			final TableColumn column0 = tcm.getColumn(0);
			final TableColumn column1 = tcm.getColumn(1);
			final TableColumn column2 = tcm.getColumn(2);
			final TableColumn column3 = tcm.getColumn(3);
			column0.setHeaderValue(COLUMN_NAME_DISCOUNT[0]);
			column1.setHeaderValue(COLUMN_NAME_DISCOUNT[1]);
			column2.setHeaderValue(COLUMN_NAME_DISCOUNT[2]);
			column3.setHeaderValue(COLUMN_NAME_DISCOUNT[3]);
			this.table.getColumn("Code Discount").setPreferredWidth(125); //set the column size to 125
			this.table.getColumn("Percentage (%)").setPreferredWidth(125); //set the column size to 125
			this.table.getColumn("Period (days)").setPreferredWidth(125); //set the column size to 125
			this.table.getColumn("Discount Score").setPreferredWidth(125); //set the column size to 125
			th.repaint();
			this.refreshTable();
			this.observer.generateTable(this.currentTable);
		} else if (e.getClickCount() == 2 && !e.isConsumed()  && e.getComponent().equals(this.table)) {
			try {
				e.consume();
				final Object value = this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doEdit(this.currentTable, value);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_RECORD);
			}
		}
	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param manageShopController controller for this view
	 */
	public void attachObserver(final ManageShopController manageShopController) {
		this.observer = manageShopController;		
	}

	/**
	 * Add a row to the table.
	 * @param obj is the row which is composed by a vector of object
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);
	}

	/**
	 * Add a ToolTipText to the table.
	 * @param text the message shown
	 */
	public void setTableToolTipText(final String text) {
		this.table.setToolTipText(text);
	}

	/**
	 * Clean the table.
	 */
	public void refreshTable() {
		((DefaultTableModel) this.table.getModel()).getDataVector().clear();
		((DefaultTableModel) this.table.getModel()).fireTableDataChanged();
	}
}
