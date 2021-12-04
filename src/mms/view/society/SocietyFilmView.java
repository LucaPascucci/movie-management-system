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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import mms.controller.society.SocietyFilmController;
import mms.view.AbstractView;

/**
 * This view permits the handling of society's films.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class SocietyFilmView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant ORANGE_COLOR. */
	private static final Color ORANGE_COLOR = new Color(248, 192, 61);

	/** The Constant COLUMN_NAME_FILM. */
	private static final String[] COLUMN_NAME_FILM = new String[]{"Code", "Title", "Genre", "Year", "Price (€)"};

	/** The Constant COLUMN_NAME_ACTOR. */
	private static final String[] COLUMN_NAME_ACTOR = new String[]{"Code", "Name", "Surname", "BirthDate", "Nationality"};

	/** The Constant SELECT_RECORD. */
	private static final String SELECT_RECORD = "Select a record in the table!";

	/** The Constant DELETE_RECORD. */
	private static final String DELETE_RECORD = "Do you want to delete this record?";

	/** The Constant CHAR_FONT. */
	private static final int CHAR_FONT = 14;

	/** The Constant MINIMUM_GAP. */
	private static final int MINIMUM_GAP = 5;

	/** The Constant LABEL_GAP. */
	private static final int LABEL_GAP = 15;

	/** The Constant LEFT_BORDER_BUTTON. */
	private static final int LEFT_BORDER_BUTTON = 40;

	/** The Constant RIGHT_BORDER_BUTTON. */
	private static final int RIGHT_BORDER_BUTTON = 160;

	/** The Constant NORTH_GAP. */
	private static final int NORTH_GAP = 20;

	/** The Constant MENU_IDX_EXIT. */
	private static final int MENU_IDX_EXIT = 0;

	/** The Constant MENU_IDX_CONTACT_DEVELOPERS. */
	private static final int MENU_IDX_CONTACT_DEVELOPERS = 0;

	/** The Constant MENU_IDX_ABOUT. */
	private static final int MENU_IDX_ABOUT = 1;

	/** The table data. */
	private final Object[][] tableData = new Object[][]{};

	/** The file contained menu. */
	private final JMenuItem[] fileContainedMenu;

	/** The help menu item. */
	private final JMenuItem[] helpMenuItem;

	/** The film. */
	private final JLabel film = new JLabel("Films");

	/** The actor. */
	private final JLabel actor = new JLabel("Actors");

	/** The add. */
	private final JButton add = new JButton("New film", new ImageIcon(this.getClass().getResource("/Add.png")));

	/** The edit. */
	private final JButton edit = new JButton("Edit film", new ImageIcon(this.getClass().getResource("/Edit.png")));

	/** The delete. */
	private final JButton delete = new JButton("Delete film", new ImageIcon(this.getClass().getResource("/Delete.png")));

	/** The back. */
	private final JButton back = new JButton("Back", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The table. */
	private final JTable table;

	/** The observer. */
	private SocietyFilmController observer;

	/**
	 * This is the constructor of the view.
	 */
	public SocietyFilmView() {

		super();

		this.setTitle("Manage Films");
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

		JScrollPane scroll;

		this.add(this.film);
		layout.putConstraint(SpringLayout.WEST, this.film, LABEL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.film, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.film.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.film.setOpaque(true);
		this.film.setBackground(ORANGE_COLOR);

		this.add(this.actor);
		layout.putConstraint(SpringLayout.WEST, this.actor, LABEL_GAP, SpringLayout.EAST, this.film);
		layout.putConstraint(SpringLayout.NORTH, this.actor, MINIMUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.actor.setFont(new Font(null, Font.BOLD, CHAR_FONT));
		this.actor.setOpaque(true);
		this.actor.setBackground(null);

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
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		scroll = new JScrollPane(this.table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(scroll);
		layout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, scroll, MINIMUM_GAP, SpringLayout.SOUTH, this.film);
		layout.putConstraint(SpringLayout.SOUTH, scroll, 0, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, scroll, 600, SpringLayout.WEST, this.getContentPane());
		//600 is the width of the scroll

		this.add(this.add);
		layout.putConstraint(SpringLayout.SOUTH, this.add, -NORTH_GAP, SpringLayout.NORTH, this.edit);
		layout.putConstraint(SpringLayout.WEST, this.add, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.add, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		this.add(this.edit);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.edit, 0, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.edit, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.edit, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		this.add(this.delete);
		layout.putConstraint(SpringLayout.NORTH, this.delete, NORTH_GAP, SpringLayout.SOUTH, this.edit);
		layout.putConstraint(SpringLayout.WEST, this.delete, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.delete, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.add(this.back);
		layout.putConstraint(SpringLayout.SOUTH, this.back, -NORTH_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.back, LEFT_BORDER_BUTTON, SpringLayout.EAST, scroll);
		layout.putConstraint(SpringLayout.EAST, this.back, RIGHT_BORDER_BUTTON, SpringLayout.EAST, scroll);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				observer.generateTable(film.getBackground().equals(ORANGE_COLOR));
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.backToHome();
			}
		});

		this.table.addMouseListener(this);
		this.film.addMouseListener(this);
		this.actor.addMouseListener(this);
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
			this.observer.doAdd(this.film.getBackground().equals(ORANGE_COLOR));
		} else if (buttonSelected.equals(this.edit)) {
			try {
				final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doEdit(this.film.getBackground().equals(ORANGE_COLOR), value);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_RECORD);
			}
		} else if (buttonSelected.equals(this.delete)) {
			try {
				final Integer value = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				if (this.observer.showQuestionDialog(DELETE_RECORD) == JOptionPane.YES_OPTION) {
					if (this.observer.doDelete(this.film.getBackground().equals(ORANGE_COLOR), value)) {
						((DefaultTableModel) this.table.getModel()).removeRow(this.table.getSelectedRow());
					}
				}
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_RECORD);
			}
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
		if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.film) && !this.film.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.setTitle("Manage Films");
			this.film.setBackground(ORANGE_COLOR);
			this.actor.setBackground(null);
			this.add.setText("New film");
			this.edit.setText("Edit film");
			this.delete.setText("Delete film");

			final JTableHeader th = this.table.getTableHeader();
			final TableColumnModel tcm = th.getColumnModel();
			final TableColumn column0 = tcm.getColumn(0);
			final TableColumn column1 = tcm.getColumn(1);
			final TableColumn column2 = tcm.getColumn(2);
			final TableColumn column3 = tcm.getColumn(3);
			final TableColumn column4 = tcm.getColumn(4);
			column0.setHeaderValue(COLUMN_NAME_FILM[0]);
			column1.setHeaderValue(COLUMN_NAME_FILM[1]);
			column2.setHeaderValue(COLUMN_NAME_FILM[2]);
			column3.setHeaderValue(COLUMN_NAME_FILM[3]);
			column4.setHeaderValue(COLUMN_NAME_FILM[4]);
			this.table.getColumn(COLUMN_NAME_FILM[0]).setPreferredWidth(40);
			this.table.getColumn(COLUMN_NAME_FILM[1]).setPreferredWidth(310);
			this.table.getColumn(COLUMN_NAME_FILM[2]).setPreferredWidth(125);
			this.table.getColumn(COLUMN_NAME_FILM[3]).setPreferredWidth(50);
			this.table.getColumn(COLUMN_NAME_FILM[4]).setPreferredWidth(57);
			th.repaint();
			this.refreshTable();
			this.observer.generateTable(this.film.getBackground().equals(ORANGE_COLOR));

		} else if (e.getClickCount() == 1 && !e.isConsumed() && e.getComponent().equals(this.actor) && !this.actor.getBackground().equals(ORANGE_COLOR)) {
			e.consume();
			this.setTitle("Manage Actors");
			this.actor.setBackground(ORANGE_COLOR);
			this.film.setBackground(null);
			this.add.setText("New actor");
			this.edit.setText("Edit actor");
			this.delete.setText("Delete actor");

			final JTableHeader th = this.table.getTableHeader();
			final TableColumnModel tcm = th.getColumnModel();
			final TableColumn column0 = tcm.getColumn(0);
			final TableColumn column1 = tcm.getColumn(1);
			final TableColumn column2 = tcm.getColumn(2);
			final TableColumn column3 = tcm.getColumn(3);
			final TableColumn column4 = tcm.getColumn(4);
			column0.setHeaderValue(COLUMN_NAME_ACTOR[0]);
			column1.setHeaderValue(COLUMN_NAME_ACTOR[1]);
			column2.setHeaderValue(COLUMN_NAME_ACTOR[2]);
			column3.setHeaderValue(COLUMN_NAME_ACTOR[3]);
			column4.setHeaderValue(COLUMN_NAME_ACTOR[4]);
			this.table.getColumn(COLUMN_NAME_ACTOR[0]).setPreferredWidth(40);
			this.table.getColumn(COLUMN_NAME_ACTOR[1]).setPreferredWidth(125);
			this.table.getColumn(COLUMN_NAME_ACTOR[2]).setPreferredWidth(125);
			this.table.getColumn(COLUMN_NAME_ACTOR[3]).setPreferredWidth(100);
			this.table.getColumn(COLUMN_NAME_ACTOR[4]).setPreferredWidth(192);
			th.repaint();
			this.refreshTable();
			this.observer.generateTable(this.film.getBackground().equals(ORANGE_COLOR));
		} else if (e.getClickCount() == 2 && !e.isConsumed()  && e.getComponent().equals(this.table) && this.film.getBackground().equals(ORANGE_COLOR)) {
			try {
				e.consume();
				final Integer idFilm = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.showDetailsFilm(idFilm);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_RECORD);
			}
		} else if (e.getClickCount() == 2 && !e.isConsumed()  && e.getComponent().equals(this.table) && this.actor.getBackground().equals(ORANGE_COLOR)) {
			try {
				e.consume();
				final Integer actorCode = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.doEdit(this.film.getBackground().equals(ORANGE_COLOR), actorCode);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_RECORD);
			}
		}

	}

	/**
	 * Attach the observer of the controller to the view.
	 * @param societyFilmController controller for this view
	 */
	public void attachObserver(final SocietyFilmController societyFilmController) {
		this.observer = societyFilmController;		
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
