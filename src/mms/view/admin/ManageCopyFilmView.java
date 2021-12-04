package mms.view.admin;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import mms.controller.admin.ManageCopyFilmController;
import mms.view.AbstractView;

/**
 * Manage the copy of the films in the shops.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class ManageCopyFilmView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant COLUMN_NAME_FILM. */
	private static final String[] COLUMN_NAME_FILM = new String[]{"Code", "Title", "Genre", "Year", "Price (€)"};

	/** The Constant SELECT_FILM. */
	private static final String SELECT_FILM = "Select a film!";

	/** The lbl shop. */
	private final JLabel lblShop = new JLabel("Shop:");

	/** The lbl society. */
	private final JLabel lblSociety = new JLabel("Society: ");

	/** The shop table. */
	private final JTable shopTable;

	/** The society table. */
	private final JTable societyTable;

	/** The shop scroll. */
	private final JScrollPane shopScroll;

	/** The society scroll. */
	private final JScrollPane societyScroll;

	/** The add. */
	private final JButton add = new JButton("<");

	/** The remove. */
	private final JButton remove = new JButton(">");

	/** The confirm. */
	private final JButton confirm = new JButton("Save");

	/** The cancel. */
	private final JButton cancel = new JButton("Cancel");

	/** The observer. */
	private ManageCopyFilmController observer;

	/**
	 * The constructor of the view.
	 */
	public ManageCopyFilmView() {

		super();

		this.setTitle("AddCopyFilm");
		this.setSize(1060, 500);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.shopTable = new JTable(new DefaultTableModel(new Object[][]{}, COLUMN_NAME_FILM) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		});
		this.shopTable.getColumn(COLUMN_NAME_FILM[0]).setPreferredWidth(40); //set the column size to 40
		this.shopTable.getColumn(COLUMN_NAME_FILM[1]).setPreferredWidth(195); //set the column size to 195
		this.shopTable.getColumn(COLUMN_NAME_FILM[2]).setPreferredWidth(100); //set the column size to 125
		this.shopTable.getColumn(COLUMN_NAME_FILM[3]).setPreferredWidth(50); //set the column size to 50
		this.shopTable.getColumn(COLUMN_NAME_FILM[4]).setPreferredWidth(55); //set the column size to 55
		this.shopTable.getTableHeader().setReorderingAllowed(false);
		this.shopTable.getTableHeader().setResizingAllowed(false);
		this.shopTable.setFillsViewportHeight(true);
		this.shopTable.setSelectionMode(0);
		this.shopTable.setToolTipText("Double Click to see details");
		JLabel label = (JLabel) this.shopTable.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.shopScroll = new JScrollPane(this.shopTable);
		this.shopScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.societyTable = new JTable(new DefaultTableModel(new Object[][]{}, COLUMN_NAME_FILM) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		});
		this.societyTable.getColumn(COLUMN_NAME_FILM[0]).setPreferredWidth(40); //set the column size to 40
		this.societyTable.getColumn(COLUMN_NAME_FILM[1]).setPreferredWidth(195); //set the column size to 195
		this.societyTable.getColumn(COLUMN_NAME_FILM[2]).setPreferredWidth(100); //set the column size to 100
		this.societyTable.getColumn(COLUMN_NAME_FILM[3]).setPreferredWidth(50); //set the column size to 50
		this.societyTable.getColumn(COLUMN_NAME_FILM[4]).setPreferredWidth(55); //set the column size to 55
		this.societyTable.getTableHeader().setReorderingAllowed(false);
		this.societyTable.getTableHeader().setResizingAllowed(false);
		this.societyTable.setFillsViewportHeight(true);
		this.societyTable.setSelectionMode(0);
		this.societyTable.setToolTipText("Double Click to see details");
		label = (JLabel) this.societyTable.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.societyScroll = new JScrollPane(this.societyTable);
		this.societyScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(this.lblShop);
		layout.putConstraint(SpringLayout.WEST, this.lblShop, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblShop, 25, SpringLayout.NORTH, this.getContentPane());
		this.add(this.shopScroll);
		layout.putConstraint(SpringLayout.WEST, this.shopScroll, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.shopScroll, 10, SpringLayout.SOUTH, this.lblShop);
		layout.putConstraint(SpringLayout.EAST, this.shopScroll, 458, SpringLayout.WEST, this.shopScroll);
		layout.putConstraint(SpringLayout.SOUTH, this.shopScroll, -60, SpringLayout.SOUTH, this.getContentPane());

		this.add(this.add);
		layout.putConstraint(SpringLayout.WEST, this.add, 25, SpringLayout.EAST, this.shopScroll);
		layout.putConstraint(SpringLayout.SOUTH, this.add, -25, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		this.add(this.remove);
		layout.putConstraint(SpringLayout.WEST, this.remove, 25, SpringLayout.EAST, this.shopScroll);
		layout.putConstraint(SpringLayout.NORTH, this.remove, 25, SpringLayout.VERTICAL_CENTER, this.getContentPane());

		this.add(this.lblSociety);
		layout.putConstraint(SpringLayout.WEST, this.lblSociety, 25, SpringLayout.EAST, this.remove);
		layout.putConstraint(SpringLayout.NORTH, this.lblSociety, 25, SpringLayout.NORTH, this.getContentPane());
		this.add(this.societyScroll);
		layout.putConstraint(SpringLayout.WEST, this.societyScroll, 25, SpringLayout.EAST, this.remove);
		layout.putConstraint(SpringLayout.NORTH, this.societyScroll, 10, SpringLayout.SOUTH, this.lblSociety);
		layout.putConstraint(SpringLayout.EAST, this.societyScroll, 458, SpringLayout.WEST, this.societyScroll);
		layout.putConstraint(SpringLayout.SOUTH, this.societyScroll, -60, SpringLayout.SOUTH, this.getContentPane());

		this.add(this.confirm);
		layout.putConstraint(SpringLayout.SOUTH, this.confirm, -20, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.confirm, -20, SpringLayout.EAST, this.getContentPane());
		this.add(this.cancel);
		layout.putConstraint(SpringLayout.SOUTH, this.cancel, -20, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.cancel, -20, SpringLayout.WEST, this.confirm);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				observer.generateTables(true);
				observer.generateTables(false);
				for (int rows = 0; rows < shopTable.getRowCount(); rows++) {
					observer.fillCopyFilmList((Integer) shopTable.getModel().getValueAt(rows, 0), true);
				}
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}
		});

		this.add.addActionListener(this);
		this.remove.addActionListener(this);
		this.cancel.addActionListener(this);
		this.confirm.addActionListener(this);

		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(this.add)) {
			try {
				final int row = this.societyTable.getSelectedRow();
				Object[] array = new Object[this.societyTable.getColumnCount()];
				for (int columns = 0; columns < this.societyTable.getColumnCount(); columns++) {
					array[columns] = this.societyTable.getValueAt(row, columns);
				}
				((DefaultTableModel) this.shopTable.getModel()).addRow(array);
				((DefaultTableModel) this.societyTable.getModel()).removeRow(row);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (source.equals(this.remove)) {
			try {
				final int row = this.shopTable.getSelectedRow();
				Object[] array = new Object[this.shopTable.getColumnCount()];
				for (int columns = 0; columns < this.shopTable.getColumnCount(); columns++) {
					array[columns] = this.shopTable.getValueAt(row, columns);
				}
				((DefaultTableModel) this.societyTable.getModel()).addRow(array);
				((DefaultTableModel) this.shopTable.getModel()).removeRow(this.shopTable.getSelectedRow());
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_FILM);
			}
		} else if (source.equals(this.confirm)) {
			for (int rows = 0; rows < this.shopTable.getRowCount(); rows++) {
				this.observer.fillCopyFilmList((Integer) this.shopTable.getModel().getValueAt(rows, 0), false);
			}
			this.observer.doManageCopyFilm();
		} else if (source.equals(this.cancel)) {
			this.observer.doBack();
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) { }

	/**
	 * Attach the observer of the controller to the view.
	 * @param addCopyFilmController controller for this view
	 */
	public void attachObserver(final ManageCopyFilmController addCopyFilmController) {
		this.observer = addCopyFilmController;		
	}

	/**
	 * Add a row to the table.
	 * @param obj is the row which is composed by a vector of object
	 */
	public void newRowShop(final Object[] obj) {
		((DefaultTableModel) this.shopTable.getModel()).addRow(obj);
	}

	/**
	 * Add a row to the table.
	 * @param obj is the row which is composed by a vector of object
	 */
	public void newRowSociety(final Object[] obj) {
		((DefaultTableModel) this.societyTable.getModel()).addRow(obj);
	}

	/**
	 * Clean the table.
	 * @param table table
	 */
	public void refreshTable(final JTable table) {
		((DefaultTableModel) table.getModel()).getDataVector().clear();
		((DefaultTableModel) table.getModel()).fireTableDataChanged();
	}

}
