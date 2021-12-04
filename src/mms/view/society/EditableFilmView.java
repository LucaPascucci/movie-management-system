package mms.view.society;

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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import mms.controller.society.EditableFilmController;
import mms.model.Film;
import mms.model.FilmType;
import mms.view.AbstractView;

/**
 * This class is used to create or edit a film.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class EditableFilmView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant FILM_TYPES. */
	private static final FilmType[] FILM_TYPES = {FilmType.Select , FilmType.ACTION , FilmType.ADVENTURE, FilmType.ANIMATIONS, FilmType.COMMEDY, FilmType.CRIME, FilmType.DOCUMENTARY, FilmType.DRAMATIC, FilmType.FANTASY, FilmType.HISTORICAL, FilmType.HORROR, FilmType.MUSICAL, FilmType.ROMANTIC, FilmType.SCIENCE_FICTION, FilmType.THRILLER, FilmType.WAR, FilmType.WESTERN};

	/** The Constant CMB_ROW_COUNT. */
	private static final int CMB_ROW_COUNT = 5;

	/** The confirm. */
	private final JButton confirm = new JButton(new ImageIcon(this.getClass().getResource("/Save.png")));

	/** The cancel. */
	private final JButton cancel = new JButton("Cancel", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The lbl title. */
	private final JLabel lblTitle = new JLabel("Title");

	/** The lbl genre. */
	private final JLabel lblGenre = new JLabel("Genre");

	/** The lbl price. */
	private final JLabel lblPrice = new JLabel("Price");

	/** The lbl plot. */
	private final JLabel lblPlot = new JLabel("Plot");

	/** The lbl release year. */
	private final JLabel lblReleaseYear = new JLabel("Release Year");

	/** The lbl actors. */
	private final JLabel lblActors = new JLabel("Actors");

	/** The txt title. */
	private final JTextField txtTitle = new JTextField(17); //TextField size: 17.

	/** The txt price. */
	private final JTextField txtPrice = new JTextField(6); //TextField size: 6.

	/** The cmb genre. */
	private final JComboBox<FilmType> cmbGenre = new JComboBox<FilmType>(FILM_TYPES);

	/** The txt plot. */
	private final JTextArea txtPlot = new JTextArea(16, 25); //TextArea size: 16 width, 25 height.

	/** The spinner years. */
	private final JSpinner spinnerYears = new JSpinner();

	/** The table. */
	private final JTable table;

	/** The scroll. */
	private final JScrollPane scroll;

	/** The curr type. */
	private FilmType currType;

	/** The observer. */
	private EditableFilmController observer;

	/**
	 * Implements a new EditableFilmView.
	 */
	public EditableFilmView() {

		super();

		this.setSize(695, 580);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		JScrollPane scrollPlot;

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		final DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{"Code", "Name", "Surname", "Check"});
		this.table = new JTable(model) {

			private static final long serialVersionUID = 1;

			@Override
			public Class<?> getColumnClass(final int column) {
				switch (column) {
				case 0:
					return Integer.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				default:
					return Boolean.class;
				}
			}
		};
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.getColumn("Code").setPreferredWidth(30);
		this.table.getColumn("Name").setPreferredWidth(105);
		this.table.getColumn("Surname").setPreferredWidth(105);
		this.table.getColumn("Check").setPreferredWidth(40);
		this.table.setFillsViewportHeight(true);
		this.table.setSelectionMode(0);
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.scroll = new JScrollPane(this.table);
		this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		final JTableHeader th = this.table.getTableHeader();
		th.repaint();

		this.cmbGenre.setSelectedIndex(0);
		this.cmbGenre.setMaximumRowCount(CMB_ROW_COUNT);
		this.cmbGenre.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbGenre.getSelectedItem().equals(FilmType.Select)) {
					currType = null;
				} else {
					currType = (FilmType) cmbGenre.getSelectedItem();
				}
			}
		});

		this.txtPlot.setLineWrap(true);
		scrollPlot = new JScrollPane(this.txtPlot);
		scrollPlot.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		this.add(this.lblTitle);
		layout.putConstraint(SpringLayout.WEST, this.lblTitle, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblTitle, 25, SpringLayout.NORTH, this.getContentPane());
		this.add(this.txtTitle);
		layout.putConstraint(SpringLayout.NORTH, this.txtTitle, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.txtTitle, 10, SpringLayout.EAST, this.lblTitle);

		this.add(this.lblPrice);
		layout.putConstraint(SpringLayout.WEST, this.lblPrice, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblPrice, 20, SpringLayout.SOUTH, this.txtTitle);
		this.add(this.txtPrice);
		layout.putConstraint(SpringLayout.NORTH, this.txtPrice, 20, SpringLayout.SOUTH, this.cmbGenre);
		layout.putConstraint(SpringLayout.WEST, this.txtPrice, 10, SpringLayout.EAST, this.lblPrice);

		this.add(this.lblActors);
		layout.putConstraint(SpringLayout.WEST, this.lblActors, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblActors, 20, SpringLayout.SOUTH, this.txtPrice);
		this.add(this.scroll);
		layout.putConstraint(SpringLayout.WEST, this.scroll, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.scroll, 10, SpringLayout.SOUTH, this.lblActors);
		layout.putConstraint(SpringLayout.EAST, this.scroll, 340, SpringLayout.WEST, this.scroll);
		layout.putConstraint(SpringLayout.SOUTH, this.scroll, -75, SpringLayout.SOUTH, this.getContentPane());

		this.add(this.lblPlot);
		layout.putConstraint(SpringLayout.WEST, this.lblPlot, 25, SpringLayout.EAST, this.scroll);
		layout.putConstraint(SpringLayout.NORTH, this.lblPlot, 20, SpringLayout.SOUTH, this.txtPrice);
		this.add(scrollPlot);
		layout.putConstraint(SpringLayout.NORTH, scrollPlot, 10, SpringLayout.SOUTH, this.lblPlot);
		layout.putConstraint(SpringLayout.WEST, scrollPlot, 25, SpringLayout.EAST, this.scroll);
		layout.putConstraint(SpringLayout.EAST, scrollPlot, 275, SpringLayout.WEST, scrollPlot);
		layout.putConstraint(SpringLayout.SOUTH, scrollPlot, -75, SpringLayout.SOUTH, this.getContentPane());

		this.add(this.lblGenre);
		layout.putConstraint(SpringLayout.WEST, this.lblGenre, 25, SpringLayout.EAST, this.scroll);
		layout.putConstraint(SpringLayout.NORTH, this.lblGenre, 25, SpringLayout.NORTH, this.getContentPane());
		this.add(this.cmbGenre);
		layout.putConstraint(SpringLayout.NORTH, this.cmbGenre, 25, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.cmbGenre, 10, SpringLayout.EAST, this.lblGenre);

		this.add(this.lblReleaseYear);
		layout.putConstraint(SpringLayout.WEST, this.lblReleaseYear, 25, SpringLayout.EAST, this.scroll);
		layout.putConstraint(SpringLayout.NORTH, this.lblReleaseYear, 20, SpringLayout.SOUTH, this.txtTitle);
		this.add(this.spinnerYears);
		layout.putConstraint(SpringLayout.NORTH, this.spinnerYears, 20, SpringLayout.SOUTH, this.cmbGenre);
		layout.putConstraint(SpringLayout.WEST, this.spinnerYears, 10, SpringLayout.EAST, this.lblReleaseYear);
		this.spinnerYears.setModel(new SpinnerNumberModel(2014, 1852, 2020, 1)); //set the values of the spinnerYears


		this.add(this.confirm);
		layout.putConstraint(SpringLayout.SOUTH, this.confirm, -20, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.confirm, -20, SpringLayout.EAST, this.getContentPane());

		this.add(this.cancel);
		layout.putConstraint(SpringLayout.SOUTH, this.cancel, -20, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.cancel, -20, SpringLayout.WEST, this.confirm);

		this.confirm.addActionListener(this);
		this.cancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final Film film = observer.getEditableFilm();
				observer.doShowActors();
				if (film != null) {
					confirm.setText("Save");
					cmbGenre.setSelectedItem(FilmType.valueOf(film.getGenre()));
					currType = (FilmType) cmbGenre.getSelectedItem();
					txtTitle.setText(film.getTitle());
					txtPrice.setText("" + film.getPrice());
					txtPlot.setText(film.getPlot());
					spinnerYears.setValue(film.getReleaseYear());
					setTitle("Edit Film: " + film.getCodeFilm());
				} else {
					setTitle("New Film");
					confirm.setText("Add Film");
				}
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}

		});
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (selection.equals(this.cancel)) {
			this.observer.doBack();
		} else if (selection.equals(this.confirm)) {			
			for (int rows = 0; rows < this.table.getRowCount(); rows++) {
				this.observer.doManageActor((Integer) table.getModel().getValueAt(rows, 0), (Boolean) table.getModel().getValueAt(rows, 3));
			}
			this.observer.doConfirm(this.txtTitle.getText(), this.txtPrice.getText(), this.txtPlot.getText(), (Integer) this.spinnerYears.getValue(), this.currType);
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) { }

	/**
	 * Attach the observer of the controller to the view.
	 * @param editableFilmController controller for this view
	 */
	public void attachObserver(final EditableFilmController editableFilmController) {
		this.observer = editableFilmController;		
	}

	/**
	 * Set indicated fields to void.
	 */
	public void resetNumberField() {
		this.txtPrice.setText("");
	}

	/**
	 * Add a row to the table.
	 * @param obj is the row which is composed by a vector of object
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
