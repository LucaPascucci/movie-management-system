package mms.view.customer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import mms.controller.customer.FilmListController;
import mms.model.FilmType;
import mms.view.AbstractView;

/**
 * Customer view that has a list of films, showed with different type of filter and the chance to set a proper filter.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FilmListView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant COLUMN_NAME_FILM. */
	private static final String[] COLUMN_NAME_FILM = new String[]{"Cod", "Title", "Genre", "Year", "Price (€)"};

	/** The Constant ORANGE_COLOR. */
	private static final Color ORANGE_COLOR = new Color(248, 192, 61);

	/** The Constant SELECT_FILM. */
	private static final String SELECT_FILM = "Select a film!";

	/** The Constant HORIZONTAL_GAP. */
	private static final int HORIZONTAL_GAP = 10;

	/** The Constant PANEL_GAP. */
	private static final int PANEL_GAP = 5;

	/** The Constant BUTTON_GAP_TO_FRAME. */
	private static final int BUTTON_GAP_TO_FRAME = 40;

	/** The Constant VERTICAL_GAP. */
	private static final int VERTICAL_GAP = 20;

	/** The Constant CHAR_FONT. */
	private static final int CHAR_FONT = 14;

	/** The Constant DOUBLE_PANEL_GAP. */
	private static final int DOUBLE_PANEL_GAP = 10;

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

	/** The Constant ALPHABETH. */
	private static final String[] ALPHABETH = {"Select", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

	/** The Constant PRICES. */
	private static final String[] PRICES = {"Select", "0-10", "10-15", "15-20", "20-25", "25 +"};

	/** The Constant FILM_TYPES. */
	private static final FilmType[] FILM_TYPES = {FilmType.Select , FilmType.ACTION , FilmType.ADVENTURE, FilmType.ANIMATIONS, FilmType.COMMEDY, FilmType.CRIME, FilmType.DOCUMENTARY, FilmType.DRAMATIC, FilmType.FANTASY, FilmType.HISTORICAL, FilmType.HORROR, FilmType.MUSICAL, FilmType.ROMANTIC, FilmType.SCIENCE_FICTION, FilmType.THRILLER, FilmType.WAR, FilmType.WESTERN};

	/** The Constant DEFAULT_SHOP. */
	private static final String DEFAULT_SHOP = "Code shop";

	/** The Constant CMB_ROW_COUNT. */
	private static final Integer CMB_ROW_COUNT = 5;

	/** The file contained menu. */
	private final JMenuItem fileContainedMenu;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The card menu item. */
	private final JMenuItem[] cardMenuItem;

	/** The cmb genre. */
	private final JComboBox<FilmType> cmbGenre = new JComboBox<FilmType>(FILM_TYPES);

	/** The cmb price. */
	private final JComboBox<String> cmbPrice = new JComboBox<String>(PRICES);

	/** The cmb letters. */
	private final JComboBox<String> cmbLetters = new JComboBox<String>(ALPHABETH);

	/** The spinner years. */
	private final JSpinner spinnerYears = new JSpinner();

	/** The lbl shops. */
	private final JLabel lblShops = new JLabel("Select shop:");

	/** The shops. */
	private final JComboBox<String> shops = new JComboBox<String>();

	/** The lbl title. */
	private final JLabel lblTitle = new JLabel("Title");

	/** The lbl genre. */
	private final JLabel lblGenre = new JLabel("Genre");

	/** The lbl letters. */
	private final JLabel lblLetters = new JLabel("Letters");

	/** The lbl price. */
	private final JLabel lblPrice = new JLabel("Price (€)");

	/** The lbl release year. */
	private final JLabel lblReleaseYear = new JLabel("Release Year");

	/** The lbl film list. */
	private final JLabel lblFilmList = new JLabel("Film List");

	/** The lbl top view. */
	private final JLabel lblTopView = new JLabel("Top View");

	/** The lbl top bought. */
	private final JLabel lblTopBought = new JLabel("Top Bought");

	/** The lbl recent. */
	private final JLabel lblRecent = new JLabel("Recent");

	/** The txt title. */
	private final JTextField txtTitle = new JTextField(15); //TextField size: 15.

	/** The search. */
	private final JButton search = new JButton("Search", new ImageIcon(this.getClass().getResource("/Search.png")));

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The details. */
	private final JButton details = new JButton("Details Film", new ImageIcon(this.getClass().getResource("/Detail.png")));

	/** The panel filter. */
	private final JPanel panelFilter = new JPanel();

	/** The table data. */
	private final Object[][] tableData = new Object[][]{};

	/** The table. */
	private final JTable table;

	/** The filtering. */
	private Object[] filtering = new Object[] {null, null, null, null, null};

	/** The observer. */
	private FilmListController observer;

	/** The curr shop. */
	private Integer currShop;

	/** The curr table. */
	private Integer currTable = 1; //1 se è su FilmList, 2 se è su TopView, 3 se è su TopBought, 4 se è su Recent

	/**
	 * Create a new FilmListView.
	 */
	public FilmListView() {

		super();

		this.setTitle("Film List");
		this.setResizable(false);
		this.setSize(810, 550); //frame size: 810 width, 550 height.
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
		this.setJMenuBar(bar);

		this.lblFilmList.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblTopView.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblTopBought.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblRecent.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.lblFilmList.setBackground(ORANGE_COLOR);
		this.lblFilmList.setOpaque(true);
		this.lblTopBought.setOpaque(true);
		this.lblTopView.setOpaque(true);
		this.lblRecent.setOpaque(true);

		this.cmbLetters.setSelectedIndex(0);
		this.cmbLetters.setMaximumRowCount(CMB_ROW_COUNT);
		this.cmbLetters.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbLetters.getSelectedItem().equals("Select")) {
					filtering[0] = null;
				} else {
					filtering[0] = (String) cmbLetters.getSelectedItem();
				}
			}
		});

		this.cmbPrice.setSelectedIndex(0);
		this.cmbPrice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(0))) {
					filtering[2] = null;
					filtering[3] = null;
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(1))) {
					filtering[2] = 0;
					filtering[3] = 10; //0 - 10 is the price range.
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(2))) {
					filtering[2] = 10;
					filtering[3] = 15; //10 - 15 is the price range.
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(3))) {
					filtering[2] = 15;
					filtering[3] = 20; //15 - 20 is the price range.
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(4))) {
					filtering[2] = 20;
					filtering[3] = 25; //20 - 25 is the price range.
				}
				if (cmbPrice.getSelectedItem().equals(cmbPrice.getItemAt(5))) {
					filtering[2] = 25;
					filtering[3] = Integer.MAX_VALUE; //25 - MAX_VALUE is the price range.
				}
			}
		});

		this.spinnerYears.setModel(new SpinnerNumberModel(1852, 1852, 2020, 1));
		//set the values of spinnerYears.
		this.spinnerYears.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				filtering[4] = (Integer) spinnerYears.getValue();
			}
		});

		this.cmbGenre.setSelectedIndex(0);
		this.cmbGenre.setMaximumRowCount(CMB_ROW_COUNT);
		this.cmbGenre.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbGenre.getSelectedItem().equals(FilmType.Select)) {
					filtering[1] = null;
				} else {
					filtering[1] = (FilmType) cmbGenre.getSelectedItem();
				}
			}
		});

		this.cmbGenre.setToolTipText("Select genre");
		this.txtTitle.setToolTipText("Insert title");
		this.cmbPrice.setToolTipText("Select price range");
		this.spinnerYears.setToolTipText("Select year");

		this.table = new JTable(new DefaultTableModel(tableData, COLUMN_NAME_FILM) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}

		});
		this.table.getColumn(COLUMN_NAME_FILM[0]).setPreferredWidth(40); //set the column size to 40
		this.table.getColumn(COLUMN_NAME_FILM[1]).setPreferredWidth(310); //set the column size to 315
		this.table.getColumn(COLUMN_NAME_FILM[2]).setPreferredWidth(125); //set the column size to 125
		this.table.getColumn(COLUMN_NAME_FILM[3]).setPreferredWidth(50); //set the column size to 50
		this.table.getColumn(COLUMN_NAME_FILM[4]).setPreferredWidth(57); //set the column size to 52
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
		layout.putConstraint(SpringLayout.NORTH, scroll, PANEL_GAP, SpringLayout.SOUTH, this.lblFilmList);
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
						refreshTable();
						currShop = null;
					} else {
						currShop = Integer.parseInt((String) shops.getSelectedItem());
						if (currTable == 1) {
							observer.generateTableFilmList(currShop, null); 
						} else if (currTable == 2) {
							observer.generateTableTopVisited(currShop, null); 
						} else if (currTable == 3) {
							observer.generateTableTopBought(currShop, null);
						} else if (currTable == 4) {
							observer.generateTableRecent(currShop, null);
						}
					}
				}
			}
		});

		final SpringLayout panelLayout = new SpringLayout();
		this.panelFilter.setLayout(panelLayout);
		final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		this.panelFilter.setBorder(BorderFactory.createTitledBorder(blackBorder, "Filter"));
		this.add(this.panelFilter);
		layout.putConstraint(SpringLayout.WEST, this.panelFilter, HORIZONTAL_GAP, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.NORTH, this.panelFilter, PANEL_GAP, SpringLayout.SOUTH, this.shops);
		layout.putConstraint(SpringLayout.EAST, this.panelFilter, -HORIZONTAL_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.panelFilter, -VERTICAL_GAP, SpringLayout.NORTH, this.details);

		this.panelFilter.add(this.lblTitle);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblTitle, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblTitle, PANEL_GAP, SpringLayout.NORTH, this.panelFilter);

		this.panelFilter.add(this.txtTitle);
		panelLayout.putConstraint(SpringLayout.WEST, this.txtTitle, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.txtTitle, PANEL_GAP, SpringLayout.SOUTH, this.lblTitle);
		panelLayout.putConstraint(SpringLayout.EAST, this.txtTitle, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.lblLetters);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblLetters, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblLetters, PANEL_GAP, SpringLayout.SOUTH, this.txtTitle);

		this.panelFilter.add(this.cmbLetters);
		panelLayout.putConstraint(SpringLayout.WEST, this.cmbLetters, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.cmbLetters, PANEL_GAP, SpringLayout.SOUTH, this.lblLetters);
		panelLayout.putConstraint(SpringLayout.EAST, this.cmbLetters, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.lblGenre);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblGenre, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblGenre, PANEL_GAP, SpringLayout.SOUTH, this.cmbLetters);

		this.panelFilter.add(this.cmbGenre);
		panelLayout.putConstraint(SpringLayout.WEST, this.cmbGenre, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.cmbGenre, PANEL_GAP, SpringLayout.SOUTH, this.lblGenre);
		panelLayout.putConstraint(SpringLayout.EAST, this.cmbGenre, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.lblPrice);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblPrice, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblPrice, PANEL_GAP, SpringLayout.SOUTH, this.cmbGenre);

		this.panelFilter.add(this.cmbPrice);
		panelLayout.putConstraint(SpringLayout.WEST, this.cmbPrice, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.cmbPrice, PANEL_GAP, SpringLayout.SOUTH, this.lblPrice);
		panelLayout.putConstraint(SpringLayout.EAST, this.cmbPrice, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.lblReleaseYear);
		panelLayout.putConstraint(SpringLayout.WEST, this.lblReleaseYear, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblReleaseYear, PANEL_GAP, SpringLayout.SOUTH, this.cmbPrice);

		this.panelFilter.add(this.spinnerYears);
		panelLayout.putConstraint(SpringLayout.WEST, this.spinnerYears, PANEL_GAP, SpringLayout.WEST, this.panelFilter);
		panelLayout.putConstraint(SpringLayout.NORTH, this.spinnerYears, PANEL_GAP, SpringLayout.SOUTH, this.lblReleaseYear);
		panelLayout.putConstraint(SpringLayout.EAST, this.spinnerYears, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.panelFilter.add(this.search);
		panelLayout.putConstraint(SpringLayout.NORTH, this.search, DOUBLE_PANEL_GAP, SpringLayout.SOUTH, this.spinnerYears);
		panelLayout.putConstraint(SpringLayout.EAST, this.search, -PANEL_GAP, SpringLayout.EAST, this.panelFilter);

		this.add(this.lblFilmList);
		layout.putConstraint(SpringLayout.WEST, this.lblFilmList, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblFilmList, PANEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.lblTopView);
		layout.putConstraint(SpringLayout.WEST, this.lblTopView, HORIZONTAL_GAP, SpringLayout.EAST, this.lblFilmList);
		layout.putConstraint(SpringLayout.NORTH, this.lblTopView, PANEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.lblTopBought);
		layout.putConstraint(SpringLayout.WEST, this.lblTopBought, HORIZONTAL_GAP, SpringLayout.EAST, this.lblTopView);
		layout.putConstraint(SpringLayout.NORTH, this.lblTopBought, PANEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.lblRecent);
		layout.putConstraint(SpringLayout.WEST, this.lblRecent, HORIZONTAL_GAP, SpringLayout.EAST, this.lblTopBought);
		layout.putConstraint(SpringLayout.NORTH, this.lblRecent, PANEL_GAP, SpringLayout.NORTH, this.getContentPane());

		this.add(this.details);
		layout.putConstraint(SpringLayout.SOUTH, this.details, -VERTICAL_GAP, SpringLayout.NORTH, this.back);
		layout.putConstraint(SpringLayout.WEST, this.details, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, BUTTON_GAP_TO_FRAME, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, -BUTTON_GAP_TO_FRAME, SpringLayout.EAST, this.getContentPane());

		this.lblFilmList.addMouseListener(this);
		this.lblTopView.addMouseListener(this);
		this.lblTopBought.addMouseListener(this);
		this.lblRecent.addMouseListener(this);
		this.back.addActionListener(this);
		this.details.addActionListener(this);
		this.search.addActionListener(this);
		this.table.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
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
		} else if (selection.equals(this.fileContainedMenu)) {
			this.observer.doExit();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_ABOUT])) {
			this.observer.showCreditsView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_DEVELOPERS])) {
			this.observer.showFeedbackView();
		} else if (selection.equals(this.helpMenuItem[MENU_IDX_CONTACT_SOCIETY])) {
			this.observer.contactSociety();
		} else if (selection.equals(this.cardMenuItem[MENU_IDX_NEW_CARD])) {
			this.observer.getNewCard();
		} else if (selection.equals(this.cardMenuItem[MENU_IDX_SELECT_DISCOUNT])) {
			this.observer.selectDiscount();
		} else if (selection.equals(this.details)) {
			try {
				final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(idFilm, this.currShop);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (selection.equals(this.search)) {
			if (this.txtTitle.getText().length() != 0) {
				this.filtering[0] = this.txtTitle.getText();
			}
			this.refreshTable();
			if (this.lblFilmList.getBackground().equals(ORANGE_COLOR)) {
				this.observer.generateTableFilmList(this.currShop, this.filtering);
			} else if (this.lblRecent.getBackground().equals(ORANGE_COLOR)) {
				this.observer.generateTableRecent(this.currShop, this.filtering);
			} else if (this.lblTopBought.getBackground().equals(ORANGE_COLOR)) {
				this.observer.generateTableTopBought(this.currShop, this.filtering);
			} else if (this.lblTopView.getBackground().equals(ORANGE_COLOR)) {
				this.observer.generateTableTopVisited(this.currShop, this.filtering);
			}
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.getClickCount() == 2 && !e.isConsumed() && e.getComponent().equals(this.table)) {
			try {
				e.consume();
				final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(codFilm, this.currShop);
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblTopView) && !this.lblTopView.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.currTable = 2;
			this.lblTopView.setBackground(ORANGE_COLOR);
			this.lblFilmList.setBackground(null);
			this.lblTopBought.setBackground(null);
			this.lblRecent.setBackground(null);
			this.refreshTable();
			this.resetFilter();
			this.observer.generateTableTopVisited(this.currShop, this.filtering);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblTopBought) && !this.lblTopBought.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.currTable = 3;
			this.lblTopBought.setBackground(ORANGE_COLOR);
			this.lblFilmList.setBackground(null);
			this.lblTopView.setBackground(null);
			this.lblRecent.setBackground(null);
			this.refreshTable();
			this.resetFilter();
			this.observer.generateTableTopBought(this.currShop, this.filtering);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblFilmList) && !this.lblFilmList.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.currTable = 1;
			this.lblFilmList.setBackground(ORANGE_COLOR);
			this.lblTopView.setBackground(null);
			this.lblTopBought.setBackground(null);
			this.lblRecent.setBackground(null);
			this.refreshTable();
			this.resetFilter();
			this.observer.generateTableFilmList(this.currShop, this.filtering);
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.lblRecent) && !this.lblRecent.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.currTable = 4;
			this.lblRecent.setBackground(ORANGE_COLOR);
			this.lblFilmList.setBackground(null);
			this.lblTopBought.setBackground(null);
			this.lblTopView.setBackground(null);
			this.refreshTable();
			this.resetFilter();
			this.observer.generateTableRecent(this.currShop, this.filtering);
		}
	}

	/**
	 * Reset the filter.
	 */
	private void resetFilter() {

		this.cmbLetters.setSelectedIndex(0);
		this.cmbGenre.setSelectedIndex(0);
		this.cmbPrice.setSelectedIndex(0);
		this.txtTitle.setText(null);
		this.spinnerYears.setValue(1852); //is the basic value of spinnerYears.

		this.filtering[0] = null;
		this.filtering[1] = null;
		this.filtering[2] = null;
		this.filtering[3] = null;
		this.filtering[4] = null;

	}

	/**
	 * Fill the combobox of the shops.
	 * @param code of the shop
	 */
	public void fillShopCMB(final String code) {
		this.shops.addItem(code);
	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param filmListController controller for this view
	 */
	public void attachObserver(final FilmListController filmListController) {
		this.observer = filmListController;
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
