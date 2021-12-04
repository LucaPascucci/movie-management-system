package mms.view.customer;

import java.awt.Color;
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
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import mms.controller.customer.FavoriteFilmController;
import mms.model.Film;
import mms.view.AbstractView;

/**
 * Show the films that the user has selected as a favorite.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FavoriteFilmView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant COLUMN_NAME_FILM. */
	private static final String[] COLUMN_NAME_FILM = new String[]{"Code", "Title", "Genre", "Year", "Price (€)"};

	/** The Constant SELECT_FILM. */
	private static final String SELECT_FILM = "Select a film!";

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

	/** The Constant PANEL_MINIMUM_GAP. */
	private static final int PANEL_MINIMUM_GAP = 5;

	/** The Constant MEDIUM_GAP. */
	private static final int MEDIUM_GAP = 15;

	/** The Constant BUTTON_BORDER. */
	private static final int BUTTON_BORDER = 50;

	/** The Constant DEFAULT_SHOP. */
	private static final String DEFAULT_SHOP = "Code shop";

	/** The Constant CMB_ROW_COUNT. */
	private static final int CMB_ROW_COUNT = 5;

	/** The file contained menu. */
	private final JMenuItem fileContainedMenu;

	/** The card menu item. */
	private final JMenuItem[] cardMenuItem;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The buy. */
	private final JButton buy = new JButton("Buy Now", new ImageIcon(this.getClass().getResource("/Buy.png")));

	/** The delete. */
	private final JButton delete = new JButton("Delete Favorite", new ImageIcon(this.getClass().getResource("/Delete.png")));

	/** The details. */
	private final JButton details = new JButton("Details Film", new ImageIcon(this.getClass().getResource("/Detail.png")));

	/** The lbl shops. */
	private final JLabel lblShops = new JLabel("Select shop:");

	/** The shops. */
	private final JComboBox<String> shops = new JComboBox<String>();

	/** The table. */
	private final JTable table;

	/** The table data. */
	private final Object[][] tableData = new Object[][]{};

	/** The preview panel. */
	private final JPanel previewPanel = new JPanel();

	/** The lbl cover. */
	private final JLabel lblCover = new JLabel();

	/** The lbl title. */
	private final JLabel lblTitle = new JLabel();

	/** The lbl genre. */
	private final JLabel lblGenre = new JLabel();

	/** The lbl release year. */
	private final JLabel lblReleaseYear = new JLabel();

	/** The lbl price. */
	private final JLabel lblPrice = new JLabel();

	/** The curr shop. */
	private Integer currShop;

	/** The observer. */
	private FavoriteFilmController observer;

	/**
	 * Create a new FavoriteFilmView.
	 */
	public FavoriteFilmView() {

		super();

		this.setTitle("Favorite Films");
		this.setResizable(false);
		this.setSize(850, 550); //frame size: 850 width, 500 height;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		JMenuBar bar;
		JMenu fileMenu;
		JMenu cardMenu;
		JMenu helpMenu;
		JScrollPane scroll;

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

		this.add(scroll);
		layout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scroll, 0, SpringLayout.NORTH, this.getContentPane());
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
					} else {
						currShop = Integer.parseInt((String) shops.getSelectedItem());
						observer.generateTable(currShop);
					}
				}
			}
		});

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -30, SpringLayout.SOUTH, this.getContentPane());
		//30 is the gap from the south of the frame to the south of the back button
		layout.putConstraint(SpringLayout.WEST, this.back, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.delete);
		layout.putConstraint(SpringLayout.SOUTH, this.delete, -MEDIUM_GAP, SpringLayout.NORTH, this.back);
		layout.putConstraint(SpringLayout.WEST, this.delete, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.delete, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		this.add(this.details);
		layout.putConstraint(SpringLayout.SOUTH, this.details, -MEDIUM_GAP, SpringLayout.NORTH, this.delete);
		layout.putConstraint(SpringLayout.WEST, this.details, BUTTON_BORDER, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.details, -BUTTON_BORDER, SpringLayout.EAST, this.getContentPane());

		final SpringLayout panelLayout = new SpringLayout();
		this.previewPanel.setLayout(panelLayout);

		final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		this.previewPanel.setBorder(BorderFactory.createTitledBorder(blackBorder, "Preview"));
		this.add(this.previewPanel);
		layout.putConstraint(SpringLayout.WEST, this.previewPanel, MEDIUM_GAP, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.NORTH, this.previewPanel, 10, SpringLayout.SOUTH, this.shops);
		layout.putConstraint(SpringLayout.EAST, this.previewPanel, -MEDIUM_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.previewPanel, -MEDIUM_GAP, SpringLayout.NORTH, this.details);

		this.previewPanel.add(this.lblCover);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblCover, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblCover, PANEL_MINIMUM_GAP, SpringLayout.NORTH, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.SOUTH, this.lblCover, 125, SpringLayout.NORTH, this.lblCover);
		//125 is the height of the lblCover

		this.previewPanel.add(this.lblTitle);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblTitle, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblTitle, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblCover);

		this.previewPanel.add(this.lblGenre);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblGenre, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblGenre, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblTitle);

		this.previewPanel.add(this.lblReleaseYear);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblReleaseYear, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblReleaseYear, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblGenre);

		this.previewPanel.add(this.lblPrice);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.lblPrice, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.lblPrice, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblReleaseYear);

		this.previewPanel.add(this.buy);
		panelLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.buy, 0, SpringLayout.HORIZONTAL_CENTER, this.previewPanel);
		panelLayout.putConstraint(SpringLayout.NORTH, this.buy, PANEL_MINIMUM_GAP, SpringLayout.SOUTH, this.lblPrice);
		this.buy.setVisible(false);

		this.back.addActionListener(this);
		this.delete.addActionListener(this);
		this.details.addActionListener(this);
		this.buy.addActionListener(this);
		this.table.addMouseListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
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
		if (selection.equals(this.back)) {
			this.observer.doBack();
		} else if (selection.equals(this.delete)) {
			try {
				final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				if (this.observer.deleteFavorite(codFilm)) {
					((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
				}
			}	catch (ArrayIndexOutOfBoundsException exc) {
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
		} else if (selection.equals(this.buy)) {
			try {
				final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doBuy(codFilm, this.currShop);
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
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
		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.table)) {
			try {
				e.consume();
				final Integer codFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showPreview(codFilm, this.currShop);
			}	catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		}
	}
	/**
	 * Attach the observer of the controller to the view.
	 * @param favoriteFilmController controller for this view
	 */
	public void attachObserver(final FavoriteFilmController favoriteFilmController) {
		this.observer = favoriteFilmController;
	}
	/**
	 * Add to the table the raw passed as parameter.
	 * @param obj new row to add to the table
	 */
	public void newRow(final Object[] obj) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);
	}
	/**
	 * Set the fields to show the preview of a film.
	 * @param film	IFilm used to set fields for the preview
	 * @param cover ImageIcon to set to label for the preview
	 */
	public void setPreview(final Film film, final ImageIcon cover) {
		this.lblTitle.setText(film.getTitle());
		this.lblCover.setIcon(cover);
		this.lblGenre.setText(film.getGenre().toString());
		this.lblReleaseYear.setText(film.getReleaseYear() + "");
		this.lblPrice.setText("€ " +film.getPrice());
		if (currShop != null) {
			this.buy.setVisible(true);
		}
	}
	/**
	 * Set to void the fields to show the preview of a film.
	 */
	public void unsetPreview() {
		this.lblTitle.setText("");
		this.lblCover.setIcon(null);
		this.lblGenre.setText("");
		this.lblReleaseYear.setText("");
		this.lblPrice.setText("");
		this.buy.setVisible(false);

	}

	/**
	 * Fill the combobox of the shops.
	 * @param code of the shop
	 */
	public void fillShopCMB(final String code) {
		this.shops.addItem(code);
	}

	/**
	 * Clean the table.
	 */
	public void refreshTable() {
		((DefaultTableModel) this.table.getModel()).getDataVector().clear();
		((DefaultTableModel) this.table.getModel()).fireTableDataChanged();
	}
}
