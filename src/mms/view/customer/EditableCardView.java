package mms.view.customer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import mms.controller.customer.EditableCardController;
import mms.model.Card;
import mms.model.Discount;
import mms.model.Typology;
import mms.view.AbstractView;

/**
 * Create a new card for the discount or edit a card already exists.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class EditableCardView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant COLUMN_NAME_DISCOUNT. */
	private static final String[] COLUMN_NAME_DISCOUNT = new String[]{"Code", "Period (days)", "Percentage (%)", "Score"};

	/** The Constant SELECT_DISCOUNT. */
	private static final String SELECT_DISCOUNT = "Select a discount!";

	/** The lbl username. */
	private final JLabel lblUsername = new JLabel("Username: ");

	/** The lbl release date. */
	private final JLabel lblReleaseDate = new JLabel("Release date: ");

	/** The lbl score card. */
	private final JLabel lblScoreCard = new JLabel("Score Card: ");

	/** The lbl discount typer. */
	private final JLabel lblDiscountTyper = new JLabel("Discount type: ");

	/** The lbl active discount. */
	private final JLabel lblActiveDiscount = new JLabel("", JLabel.CENTER);

	/** The username. */
	private final JLabel username = new JLabel();

	/** The release date. */
	private final JLabel releaseDate = new JLabel();

	/** The score card. */
	private final JLabel scoreCard = new JLabel();

	/** The border. */
	private final TitledBorder border = BorderFactory.createTitledBorder("Active Discount");

	/** The table. */
	private final JTable table;

	/** The scroll. */
	private final JScrollPane scroll;

	/** The cancel. */
	private final JButton cancel = new JButton("Cancel", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The confirm. */
	private final JButton confirm = new JButton("Save", new ImageIcon(this.getClass().getResource("/Save.png")));

	/** The observer. */
	private EditableCardController observer;

	/**
	 * The constructor of the view.
	 */
	public EditableCardView() {

		super();
		this.setTitle("Card: ");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.table = new JTable(new DefaultTableModel(new Object[][]{}, COLUMN_NAME_DISCOUNT)) {

			private static final long serialVersionUID = 1;

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		};
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.getColumn("Code").setPreferredWidth(40);
		this.table.getColumn("Period (days)").setPreferredWidth(60);
		this.table.getColumn("Percentage (%)").setPreferredWidth(60);
		this.table.getColumn("Score").setPreferredWidth(60);
		this.table.setFillsViewportHeight(true);
		this.table.setSelectionMode(0);
		final JLabel label = (JLabel) this.table.getDefaultRenderer(Object.class);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.scroll = new JScrollPane(this.table);
		this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		final JTableHeader th = this.table.getTableHeader();
		th.repaint();

		this.add(this.lblUsername);
		layout.putConstraint(SpringLayout.NORTH, this.lblUsername, 15, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.lblUsername, 25, SpringLayout.WEST, this.getContentPane());

		this.username.setFont(new Font(null, Font.BOLD, 15));
		this.add(this.username);
		layout.putConstraint(SpringLayout.NORTH, this.username, 13, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.username, 5, SpringLayout.EAST, this.lblUsername);

		this.add(this.lblReleaseDate);
		layout.putConstraint(SpringLayout.NORTH, this.lblReleaseDate, 15, SpringLayout.SOUTH, this.username);
		layout.putConstraint(SpringLayout.WEST, this.lblReleaseDate, 25, SpringLayout.WEST, this.getContentPane());
		this.add(this.releaseDate);
		layout.putConstraint(SpringLayout.NORTH, this.releaseDate, 15, SpringLayout.SOUTH, this.username);
		layout.putConstraint(SpringLayout.WEST, this.releaseDate, 5, SpringLayout.EAST, this.lblReleaseDate);

		this.add(this.lblScoreCard);
		layout.putConstraint(SpringLayout.NORTH, this.lblScoreCard, 15, SpringLayout.SOUTH, this.username);
		layout.putConstraint(SpringLayout.WEST, this.lblScoreCard, 225, SpringLayout.WEST, this.getContentPane());
		this.add(this.scoreCard);
		layout.putConstraint(SpringLayout.NORTH, this.scoreCard, 15, SpringLayout.SOUTH, this.username);
		layout.putConstraint(SpringLayout.WEST, this.scoreCard, 5, SpringLayout.EAST, this.lblScoreCard);

		this.border.setTitleFont(new Font(null, Font.BOLD, 13));
		this.border.setTitleJustification(TitledBorder.CENTER);
		this.add(this.lblActiveDiscount);
		layout.putConstraint(SpringLayout.NORTH, this.lblActiveDiscount, 15, SpringLayout.SOUTH, this.releaseDate);
		layout.putConstraint(SpringLayout.WEST, this.lblActiveDiscount, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.lblActiveDiscount, -25, SpringLayout.EAST, this.getContentPane());
		this.lblActiveDiscount.setBorder(this.border);
		this.lblActiveDiscount.setVisible(false);

		this.add(this.lblDiscountTyper);
		layout.putConstraint(SpringLayout.NORTH, this.lblDiscountTyper, 20, SpringLayout.SOUTH, this.lblActiveDiscount);
		layout.putConstraint(SpringLayout.WEST, this.lblDiscountTyper, 25, SpringLayout.WEST, this.getContentPane());

		this.add(this.scroll);
		layout.putConstraint(SpringLayout.NORTH, this.scroll, 10, SpringLayout.SOUTH, this.lblDiscountTyper);
		layout.putConstraint(SpringLayout.WEST, this.scroll, 25, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.scroll, -25, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.scroll, -50, SpringLayout.SOUTH, this.getContentPane());

		this.add(this.confirm);
		layout.putConstraint(SpringLayout.SOUTH, this.confirm, -20, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.confirm, -20, SpringLayout.EAST, this.getContentPane());
		this.add(this.cancel);
		layout.putConstraint(SpringLayout.SOUTH, this.cancel, -20, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.cancel, -20, SpringLayout.WEST, this.confirm);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final Card card = observer.getCard();
				setTitle(getTitle() + card.getNumCard());
				username.setText(card.getUsername());
				final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				final Date birthdate = card.getReleaseDate();
				final String date = df.format(birthdate);
				releaseDate.setText(date);
				scoreCard.setText("" + card.getScoreCard());
				observer.generateTable();
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}
		});

		this.confirm.addActionListener(this);
		this.cancel.addActionListener(this);
		this.setLocation();
		this.setIcon();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(this.confirm)) {
			try {
				final Integer codeDiscount = (Integer) this.table.getValueAt(this.table.getSelectedRow(), 0);
				this.observer.setDiscount(codeDiscount);
			} catch (ArrayIndexOutOfBoundsException exc) {
				this.observer.saveError(exc);
				this.observer.showWarningDialog(SELECT_DISCOUNT);
			}
		} else if (source.equals(this.cancel)) {
			this.observer.doBack();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) { }

	/**
	 * Attach the observer of the controller to the view.
	 * @param editableCardController controller for this view
	 */
	public void attachObserver(final EditableCardController editableCardController) {
		this.observer = editableCardController;
	}

	/**
	 * Add to the table the raw passed as parameter.
	 * @param obj new row to add to the table
	 * @param selected selected
	 */
	public void addRow(final Object[] obj, final boolean selected) {
		((DefaultTableModel) this.table.getModel()).addRow(obj);
		if (selected) {
			this.table.setRowSelectionInterval(this.table.getRowCount() - 1, this.table.getRowCount() - 1);
		}
	}

	/**
	 * Show the informations of the discount active on the card.
	 * @param activeDiscount is the discount active on the card
	 * @param discount is the discount
	 */
	public void showActiveDiscount(final Typology activeDiscount, final Discount discount) {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		final Calendar cal = Calendar.getInstance();
		cal.setTime(activeDiscount.getStartDateDiscount());
		cal.add(Calendar.DATE, discount.getPeriod());
		this.lblActiveDiscount.setText("Code: " + activeDiscount.getCodDiscount() + "  -  Percentage: " + discount.getPercentage() + "%  -  From: " + sdf.format(activeDiscount.getStartDateDiscount()) + "  - Until: " + sdf.format(cal.getTime()));
		this.lblActiveDiscount.setVisible(true);
	}

	/**
	 * Add a ToolTipText.
	 * @param text message
	 */
	public void setToolTipText(final String text) {
		this.table.setToolTipText(text);
	}
}
