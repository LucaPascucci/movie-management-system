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

import mms.controller.admin.AdminFilmController;
import mms.view.AbstractView;

/**
 * Permit to the admin to handle the films list in the program.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class AdminFilmView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant COLUMN_NAME_FILM. */
	private static final String[] COLUMN_NAME_FILM = new String[]{"Code", "Title", "Genre", "Year", "Price (€)"};

	/** The Constant DEFAULT_SHOP. */
	private static final String DEFAULT_SHOP = "Code Shop";

	/** The Constant SELECT_FILM. */
	private static final String SELECT_FILM = "Select a film!";

	/** The Constant CMB_ROW_COUNT. */
	private static final int CMB_ROW_COUNT = 5;

	/** The Constant LEFT_BORDER_BUTTON. */
	private static final int LEFT_BORDER_BUTTON = 40;

	/** The Constant RIGHT_BORDER_BUTTON. */
	private static final int RIGHT_BORDER_BUTTON = 160;

	/** The Constant NORTH_GAP. */
	private static final int NORTH_GAP = 20;

	/** The Constant MAX_NORTH_GAP. */
	private static final int MAX_NORTH_GAP = 50;

	/** The Constant MENU_IDX_CONTACT_SOCIETY. */
	private static final int MENU_IDX_CONTACT_SOCIETY = 0;

	/** The Constant MENU_IDX_CONTACT_DEVELOPERS. */
	private static final int MENU_IDX_CONTACT_DEVELOPERS = 1;

	/** The Constant MENU_IDX_ABOUT. */
	private static final int MENU_IDX_ABOUT = 2;

	/** The loading gif. */
	private final JLabel loadingGif = new JLabel(new ImageIcon(this.getClass().getResource("/Loading.gif")));

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

	/** The add film. */
	private final JButton addFilm = new JButton("Manage Films", new ImageIcon(this.getClass().getResource("/Add.png")));

	/** The add trailer. */
	private final JButton addTrailer = new JButton("Add Trailer", new ImageIcon(this.getClass().getResource("/Add_Trailer.png")));

	/** The add cover. */
	private final JButton addCover = new JButton("Add Cover", new ImageIcon(this.getClass().getResource("/Cover.png")));

	/** The details. */
	private final JButton details = new JButton("Details Film", new ImageIcon(this.getClass().getResource("/Detail.png")));

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The table. */
	private final JTable table;

	/** The curr shop. */
	private Integer currShop;

	/** The observer. */
	private AdminFilmController observer;

	/**
	 * Create a new ManageFilmView.
	 */
	public AdminFilmView() {

		super();

		this.setTitle("Manage Film");
		this.setSize(800, 450); //frame size: 800 width, 450 height.
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

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

		this.table = new JTable(new DefaultTableModel(this.tableData, COLUMN_NAME_FILM) {
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
		this.table.setToolTipText("Double Click to see details");
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		scroll = new JScrollPane(this.table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(this.loadingGif, 1, 0);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.loadingGif, 0, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.loadingGif, 0, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		this.loadingGif.setVisible(false);

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
						setTitle("Manage Film");
						refreshTable();
					} else {
						currShop = Integer.parseInt((String) shops.getSelectedItem());
						setTitle("Manage Film (Shop: " + currShop + ")");
						observer.generateTable(currShop);
					}
				}
			}
		});

		this.add(this.details);
		layout.putConstraint(SpringLayout.NORTH, this.details, MAX_NORTH_GAP, SpringLayout.SOUTH, this.shops);
		layout.putConstraint(SpringLayout.WEST, this.details, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.addFilm);
		layout.putConstraint(SpringLayout.NORTH, this.addFilm, NORTH_GAP, SpringLayout.SOUTH, this.details);
		layout.putConstraint(SpringLayout.WEST, this.addFilm, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.addFilm, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.addCover);
		layout.putConstraint(SpringLayout.NORTH, this.addCover, NORTH_GAP, SpringLayout.SOUTH, this.addFilm);
		layout.putConstraint(SpringLayout.WEST, this.addCover, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.addCover, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.addTrailer);
		layout.putConstraint(SpringLayout.NORTH, this.addTrailer, NORTH_GAP, SpringLayout.SOUTH, this.addCover);
		layout.putConstraint(SpringLayout.WEST, this.addTrailer, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.addTrailer, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -NORTH_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.addCover.addActionListener(this);
		this.addFilm.addActionListener(this);
		this.addTrailer.addActionListener(this);
		this.back.addActionListener(this);
		this.details.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				observer.doFillCmb();
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				if (!loadingGif.isVisible()) {
					observer.backToHome();
				}
			}
		});
		this.table.addMouseListener(this);
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (!this.loadingGif.isVisible()) {
			if (selection.equals(this.back)) {
				this.observer.doBack();
			} else if (selection.equals(this.addCover)) {
				try {
					final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.doAddCover(value, this.currShop);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			} else if (selection.equals(this.addFilm)) {
				this.observer.doAddMovie(this.currShop);
			} else if (selection.equals(this.addTrailer)) {
				try {
					final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.doAddTrailer(value, this.currShop);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
			} else if (selection.equals(this.details)) {
				try {
					final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
					this.observer.showDetailsFilm(idFilm, this.currShop);
				} catch (ArrayIndexOutOfBoundsException exc) {
					this.observer.saveError(exc);
					this.observer.showWarningDialog(SELECT_FILM);
				}
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

	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed()  && e.getComponent().equals(this.table) && !this.loadingGif.isVisible()) {
			try {
				e.consume();
				final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(idFilm, this.currShop);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}

		}
	}

	/**
	 * Fill the combobox with the codes of the shops.
	 * @param code of the shops
	 */
	public void fillAdminCMB(final String code) {
		this.shops.addItem(code);
	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param manageFilmController controller for this view
	 */
	public void attachObserver(final AdminFilmController manageFilmController) {
		this.observer = manageFilmController;

	}
	/**
	 * Add to the table the row passed as parameter.
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
	 * Set visibility of the label.
	 */
	public void setUnsetLoadingVisible() {
		if (this.loadingGif.isVisible()) {
			this.loadingGif.setVisible(false);
		} else {
			this.loadingGif.setVisible(true);
		}
	}

}