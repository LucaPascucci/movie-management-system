package mms.view;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import mms.controller.EditablePersonController;
import mms.model.Actor;
import mms.model.Administrator;
import mms.model.Customer;
import mms.model.FilmType;

import com.toedter.calendar.JDateChooser;

/**
 * Class used to create a new user/actor or to edit a registered user/actor .
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class EditablePersonView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant VERTICAL_GAP. */
	private static final int VERTICAL_GAP = 20;

	/** The Constant MINIMUM_GAP. */
	private static final int MINIMUM_GAP = 5;

	/** The Constant HORIZONTAL_FIRST_TABULATION. */
	private static final int HORIZONTAL_FIRST_TABULATION = 25;

	/** The Constant HORIZONTAL_SECOND_TABULATION. */
	private static final int HORIZONTAL_SECOND_TABULATION = 100;

	/** The Constant TEXTFIELD_SIZE. */
	private static final int TEXTFIELD_SIZE = 15;

	/** The Constant DEFAULT_CODE. */
	private static final String DEFAULT_CODE = "Code shop";

	/** The Constant CMB_ROW_COUNT. */
	private static final int CMB_ROW_COUNT = 5;

	/** The lbl name. */
	private final JLabel lblName = new JLabel("Name");

	/** The lbl surname. */
	private final JLabel lblSurname = new JLabel("Surname");

	/** The lbl nationality. */
	private final JLabel lblNationality = new JLabel("Nazionalità");

	/** The lbl username. */
	private final JLabel lblUsername = new JLabel("Username");

	/** The lbl password. */
	private final JLabel lblPassword = new JLabel("Password");	

	/** The lbl data. */
	private final JLabel lblData = new JLabel("Birth Date");

	/** The txt name. */
	private final JTextField txtName = new JTextField(TEXTFIELD_SIZE);

	/** The txt surname. */
	private final JTextField txtSurname = new JTextField(TEXTFIELD_SIZE);

	/** The txt nationality. */
	private final JTextField txtNationality = new JTextField(TEXTFIELD_SIZE);

	/** The txt username. */
	private final JTextField txtUsername = new JTextField(TEXTFIELD_SIZE);

	/** The txt password. */
	private final JPasswordField txtPassword = new JPasswordField(TEXTFIELD_SIZE);

	/** The show password chkb. */
	private final JCheckBox showPasswordCHKB = new JCheckBox("Show Password", new ImageIcon(this.getClass().getResource("/Lock.png")));

	/** The combo shops. */
	private final JComboBox<String> comboShops = new JComboBox<String>();

	/** The date chooser. */
	private final JDateChooser dateChooser = new JDateChooser();

	/** The confirm. */
	private final JButton confirm = new JButton(new ImageIcon(this.getClass().getResource("/Save.png")));

	/** The cancel. */
	private final JButton cancel = new JButton("Cancel", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The subscribe. */
	private final JButton subscribe = new JButton("Subscribe", new ImageIcon(this.getClass().getResource("/Subscribe.png")));

	/** The observer. */
	private EditablePersonController observer;

	/** The default char. */
	private final char defaultChar = this.txtPassword.getEchoChar();

	/** The curr shop. */
	private Integer currShop;

	/**
	 * Create a new EditablePersonView.
	 */
	public EditablePersonView() {

		super();

		this.setSize(420, 350); //frame size: 420 width, 350 height.
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.lblName);
		layout.putConstraint(SpringLayout.WEST, this.lblName, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblName, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		this.add(this.txtName);
		layout.putConstraint(SpringLayout.NORTH, this.txtName, VERTICAL_GAP, SpringLayout.NORTH, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, this.txtName, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblSurname);
		layout.putConstraint(SpringLayout.WEST, this.lblSurname, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblSurname, VERTICAL_GAP, SpringLayout.SOUTH, this.txtName);
		this.add(this.txtSurname);
		layout.putConstraint(SpringLayout.NORTH, this.txtSurname, VERTICAL_GAP, SpringLayout.SOUTH, this.txtName);
		layout.putConstraint(SpringLayout.WEST, this.txtSurname, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblUsername);
		layout.putConstraint(SpringLayout.WEST, this.lblUsername, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblUsername, VERTICAL_GAP, SpringLayout.SOUTH, this.txtSurname);
		this.add(this.txtUsername);
		layout.putConstraint(SpringLayout.NORTH, this.txtUsername, VERTICAL_GAP , SpringLayout.SOUTH, this.txtSurname);
		layout.putConstraint(SpringLayout.WEST, this.txtUsername, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblNationality);
		layout.putConstraint(SpringLayout.WEST, this.lblNationality, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblNationality, VERTICAL_GAP, SpringLayout.SOUTH, this.txtUsername);
		this.add(this.txtNationality);
		layout.putConstraint(SpringLayout.NORTH, this.txtNationality, VERTICAL_GAP, SpringLayout.SOUTH, this.txtUsername);
		layout.putConstraint(SpringLayout.WEST, this.txtNationality, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.lblPassword);
		layout.putConstraint(SpringLayout.WEST, this.lblPassword, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblPassword, VERTICAL_GAP, SpringLayout.SOUTH, this.txtUsername);
		this.add(this.txtPassword);
		layout.putConstraint(SpringLayout.NORTH, this.txtPassword, VERTICAL_GAP, SpringLayout.SOUTH, this.txtUsername);
		layout.putConstraint(SpringLayout.WEST, this.txtPassword, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		this.add(this.showPasswordCHKB);
		layout.putConstraint(SpringLayout.NORTH, this.showPasswordCHKB, MINIMUM_GAP, SpringLayout.SOUTH, this.txtPassword);
		layout.putConstraint(SpringLayout.WEST, this.showPasswordCHKB, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());

		this.add(this.comboShops);
		layout.putConstraint(SpringLayout.WEST, this.comboShops, HORIZONTAL_FIRST_TABULATION, SpringLayout.EAST, this.txtPassword);
		layout.putConstraint(SpringLayout.NORTH, this.comboShops, 0, SpringLayout.NORTH, this.txtPassword);
		this.comboShops.addItem(DEFAULT_CODE);
		this.comboShops.setSelectedIndex(0);
		this.comboShops.setMaximumRowCount(CMB_ROW_COUNT);
		this.comboShops.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (comboShops.getSelectedItem().equals(FilmType.Select)) {
					currShop = null;
				} else {
					currShop = Integer.parseInt((String) comboShops.getSelectedItem());
				}
			}
		});
		this.add(this.subscribe);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.subscribe, 0, SpringLayout.HORIZONTAL_CENTER, this.comboShops);
		layout.putConstraint(SpringLayout.NORTH, this.subscribe, 10, SpringLayout.SOUTH, this.comboShops);

		this.add(this.lblData);
		layout.putConstraint(SpringLayout.WEST, this.lblData, HORIZONTAL_FIRST_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblData, VERTICAL_GAP, SpringLayout.SOUTH, this.showPasswordCHKB);
		this.add(this.dateChooser);
		layout.putConstraint(SpringLayout.NORTH, this.dateChooser, VERTICAL_GAP, SpringLayout.SOUTH, this.showPasswordCHKB);
		layout.putConstraint(SpringLayout.WEST, this.dateChooser, HORIZONTAL_SECOND_TABULATION, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.dateChooser, 125, SpringLayout.WEST, this.dateChooser);
		//125 is the width of the dataChooser

		this.add(this.confirm);
		layout.putConstraint(SpringLayout.SOUTH, this.confirm, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.confirm, -VERTICAL_GAP, SpringLayout.EAST, this.getContentPane());

		this.add(this.cancel);
		layout.putConstraint(SpringLayout.SOUTH, this.cancel, -VERTICAL_GAP, SpringLayout.SOUTH, this.getContentPane());
		layout.putConstraint(SpringLayout.EAST, this.cancel, -VERTICAL_GAP, SpringLayout.WEST, this.confirm);

		this.lblNationality.setVisible(false);
		this.txtNationality.setVisible(false);
		this.lblUsername.setVisible(false);
		this.txtUsername.setVisible(false);
		this.lblPassword.setVisible(false);
		this.txtPassword.setVisible(false);
		this.comboShops.setVisible(false);
		this.subscribe.setVisible(false);
		this.showPasswordCHKB.setVisible(false);

		this.subscribe.addActionListener(this);
		this.showPasswordCHKB.addActionListener(this);
		this.confirm.addActionListener(this);
		this.cancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(final WindowEvent e) {
				final Object editableRecord = observer.getEditableRecord();
				if (observer.checkActor()) {
					if (editableRecord != null) {
						final Actor editableActor = (Actor) editableRecord;
						txtName.setText(editableActor.getName());
						txtSurname.setText(editableActor.getSurname());
						txtNationality.setText(editableActor.getNationality());
						final Calendar userDate = Calendar.getInstance();
						userDate.setTime(editableActor.getBirthDate());
						dateChooser.setCalendar(userDate);
						confirm.setText("Save");
						setTitle("Edit Actor: " + editableActor.getCodActor());
					} else {
						confirm.setText("Register");
						setTitle("New Actor");
					}
					lblNationality.setVisible(true);
					txtNationality.setVisible(true);

				} else {
					if (observer.checkAdmin()) {
						if (editableRecord != null) {
							final Administrator editableAdministrator = (Administrator) editableRecord;
							txtName.setText(editableAdministrator.getName());
							txtSurname.setText(editableAdministrator.getSurname());
							txtUsername.setText(editableAdministrator.getUsername());
							txtPassword.setText(editableAdministrator.getPassword());
							final Calendar userDate = Calendar.getInstance();
							userDate.setTime(editableAdministrator.getBirthDate());
							dateChooser.setCalendar(userDate);
							txtUsername.setEditable(false);
							confirm.setText("Save");
							setTitle("Edit Admin: " + editableAdministrator.getUsername());
						} else {
							confirm.setText("Register");
							setTitle("New Admin");
						}
					} else {
						comboShops.setVisible(true);
						subscribe.setVisible(true);
						if (editableRecord != null) {
							final Customer editableCustomer = (Customer) editableRecord;
							txtName.setText(editableCustomer.getName());
							txtSurname.setText(editableCustomer.getSurname());
							txtUsername.setText(editableCustomer.getUsername());
							txtPassword.setText(editableCustomer.getPassword());
							final Calendar userDate = Calendar.getInstance();
							userDate.setTime(editableCustomer.getBirthDate());
							dateChooser.setCalendar(userDate);
							txtUsername.setEditable(false);
							confirm.setText("Save");
							setTitle("Edit Customer: " + editableCustomer.getUsername());
							if (observer.isAdmin()) {
								comboShops.setVisible(false);
								subscribe.setVisible(false);
							}
						} else {
							confirm.setText("Register");
							setTitle("New Customer");
						}
						observer.doFillCMB();
					}
					lblUsername.setVisible(true);
					txtUsername.setVisible(true);
					lblPassword.setVisible(true);
					txtPassword.setVisible(true);
					showPasswordCHKB.setVisible(true);
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
	public final void actionPerformed(final ActionEvent e) {
		final Object selection = e.getSource();
		if (selection.equals(this.cancel)) {
			this.observer.doBack();
		} else if (selection.equals(this.showPasswordCHKB)) {
			if (this.showPasswordCHKB.isSelected()) {
				this.txtPassword.setEchoChar('\0');
				this.showPasswordCHKB.setIcon(new ImageIcon(this.getClass().getResource("/Unlock.png")));
			} else {
				this.txtPassword.setEchoChar(this.defaultChar);
				this.showPasswordCHKB.setIcon(new ImageIcon(this.getClass().getResource("/Lock.png")));
			}
		} else if (selection.equals(this.confirm)) {
			if (this.observer.checkActor()) {
				this.observer.doRegisterActor(this.txtName.getText(), this.txtSurname.getText(), this.txtNationality.getText(), this.dateChooser.getCalendar());
			} else {
				this.observer.doRegisterUser(this.txtName.getText(), this.txtSurname.getText(), this.txtUsername.getText(), new String(this.txtPassword.getPassword()), this.dateChooser.getCalendar());
			}
		} else if (selection.equals(this.subscribe)) {
			this.observer.subscribeAtShop(this.currShop);
		}

	}

	/**
	 * Fill the coimbobox with the codes of the shop.
	 * @param code of the shop
	 */
	public void fillShopCMB(final String code) {
		this.comboShops.addItem(code);
	}

	@Override
	public void mouseReleased(final MouseEvent e) { }

	/**
	 * Attach the observer of the controller to the view.
	 * @param editablePersonController controller for this view
	 */
	public void attachObserver(final EditablePersonController editablePersonController) {
		this.observer = editablePersonController;
	}

	/**
	 * Set to null the JTextField indicated.
	 */
	public void resetUserIDField() {
		this.txtUsername.setText("");
	}

}