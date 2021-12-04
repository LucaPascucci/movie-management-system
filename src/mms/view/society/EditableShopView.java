package mms.view.society;

import java.awt.Component;
import java.awt.GridLayout;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpringLayout;

import mms.controller.society.EditableShopController;
import mms.model.Shop;
import mms.view.AbstractView;

/**
 * Creates a new shops or edits a shop already created.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 */
public class EditableShopView extends AbstractView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant BORDER_GAP. */
	private static final int BORDER_GAP = 25;

	/** The Constant HORIZONTAL_GAP. */
	private static final int HORIZONTAL_GAP = 100;

	/** The Constant MEDIUM_GAP. */
	private static final int MEDIUM_GAP = 15;

	/** The Constant MINIMUM_GAP. */
	private static final int MINIMUM_GAP = 5;

	/** The Constant DEFAULT_STRING. */
	private static final String[] DEFAULT_STRING = new String[]{"Username", "Name", "Surname"};

	/** The Constant CMB_ROW_COUNT. */
	private static final int CMB_ROW_COUNT = 8;

	/** The lbl address. */
	private final JLabel lblAddress = new JLabel("Address: ");

	/** The lbl telephone. */
	private final JLabel lblTelephone = new JLabel("Telephone");

	/** The lbl admin. */
	private final JLabel lblAdmin = new JLabel("Admin");

	/** The txt address. */
	private final JTextField txtAddress = new JTextField(23);

	/** The txt telephone. */
	private final JTextField txtTelephone = new JTextField(16);

	/** The cmb admin. */
	private final JComboBox<String[]> cmbAdmin = new JComboBox<String[]>();

	/** The confirm. */
	private final JButton confirm = new JButton(new ImageIcon(this.getClass().getResource("/Save.png")));

	/** The cancel. */
	private final JButton cancel = new JButton("Cancel", new ImageIcon(this.getClass().getResource("/Back.png")));

	/** The selected admin. */
	private String selectedAdmin;

	/** The observer. */
	private EditableShopController observer;

	/**
	 * The constructor of EditableShopView.
	 */
	public EditableShopView() {

		super();

		this.setTitle("Add shop");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(325, 250);
		this.setResizable(false);

		final SpringLayout layout = new SpringLayout();
		this.setLayout(layout);

		this.add(this.lblAddress);
		layout.putConstraint(SpringLayout.WEST, this.lblAddress, BORDER_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblAddress, MEDIUM_GAP, SpringLayout.NORTH, this.getContentPane());
		this.add(this.txtAddress);
		layout.putConstraint(SpringLayout.WEST, this.txtAddress, BORDER_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.txtAddress, MINIMUM_GAP, SpringLayout.SOUTH, this.lblAddress);

		this.add(this.lblTelephone);
		layout.putConstraint(SpringLayout.WEST, this.lblTelephone, BORDER_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblTelephone, BORDER_GAP, SpringLayout.SOUTH, this.txtAddress);
		this.add(this.txtTelephone);
		layout.putConstraint(SpringLayout.WEST, this.txtTelephone, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.txtTelephone, BORDER_GAP, SpringLayout.SOUTH, this.txtAddress);

		this.add(this.lblAdmin);
		layout.putConstraint(SpringLayout.WEST, this.lblAdmin, BORDER_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.lblAdmin, BORDER_GAP, SpringLayout.SOUTH, this.txtTelephone);
		this.add(this.cmbAdmin);
		layout.putConstraint(SpringLayout.WEST, this.cmbAdmin, HORIZONTAL_GAP, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, this.cmbAdmin, BORDER_GAP, SpringLayout.SOUTH, this.txtTelephone);

		this.add(this.confirm);
		layout.putConstraint(SpringLayout.EAST, this.confirm, -MEDIUM_GAP, SpringLayout.EAST, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, this.confirm, -MEDIUM_GAP, SpringLayout.SOUTH, this.getContentPane());
		this.add(this.cancel);
		layout.putConstraint(SpringLayout.EAST, this.cancel, -MEDIUM_GAP, SpringLayout.WEST, this.confirm);
		layout.putConstraint(SpringLayout.SOUTH, this.cancel, -MEDIUM_GAP, SpringLayout.SOUTH, this.getContentPane());

		this.cmbAdmin.setRenderer(new MyRenderer());
		this.fillAdminCMB(DEFAULT_STRING, null);
		this.cmbAdmin.setSelectedIndex(0);
		this.cmbAdmin.setMaximumRowCount(CMB_ROW_COUNT);
		this.cmbAdmin.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (cmbAdmin.getSelectedItem().equals(DEFAULT_STRING)) {
					selectedAdmin = null;
				} else {
					final String[] selected = (String[]) cmbAdmin.getSelectedItem();
					selectedAdmin = selected[0];
				}
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(final WindowEvent e) {
				final Shop editableShop = observer.getEditableShop();
				if (editableShop != null) {
					txtAddress.setText(editableShop.getAddress());
					txtTelephone.setText(editableShop.getTelephone());
					setTitle("Edit shop: " + editableShop.getCodeShop());
					confirm.setText("Save");
				} else {
					confirm.setText("Register");
					setTitle("Add shop");
				}
				observer.doFill();
			}

			@Override
			public void windowClosing(final WindowEvent e) {
				observer.doBack();
			}
		});

		this.cancel.addActionListener(this);
		this.confirm.addActionListener(this);
		this.setLocation();
		this.setIcon();
	}
	/**
	 * Fill the combobox of the admin's shops.
	 * @param admin admin
	 * @param indexAdmin indexAdmin
	 */
	public void fillAdminCMB(final String[] admin, final Integer indexAdmin) {
		this.cmbAdmin.addItem(admin);
		if (indexAdmin != null) {
			this.selectedAdmin = admin[0];
			this.cmbAdmin.setSelectedIndex(indexAdmin);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();
		if (source.equals(this.cancel)) {
			this.observer.doBack();
		} else if (source.equals(this.confirm)) {
			this.observer.doConfirm(this.txtAddress.getText(), this.txtTelephone.getText(), this.selectedAdmin);
		}

	}

	@Override
	public void mouseReleased(final MouseEvent e) { }
	/**
	 * Attach the observer of the controller to the view.
	 * @param editableShopController controller for this view
	 */
	public final void attachObserver(final EditableShopController editableShopController) {
		this.observer = editableShopController;

	}

	/**
	 * Reset the fields.
	 */
	public void resetField() {
		this.txtAddress.setText("");
		this.txtTelephone.setText("");
		this.cmbAdmin.setSelectedIndex(0);
	}

	/**
	 * Create the combobox with more columns.
	 *
	 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
	 */
	private class MyRenderer extends JPanel implements ListCellRenderer<String[]> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1;

		/** The lbl. */
		private final JLabel[] lbl = new JLabel[3];

		/**
		 * Instantiates a new my renderer.
		 */
		public MyRenderer() {

			super();

			setLayout(new GridLayout(0, 3));
			for (int x = 0; x < lbl.length; x++) {
				lbl[x] = new JLabel();
				lbl[x].setOpaque(true);
				add(lbl[x]);
			}
		}

		public Component getListCellRendererComponent(final JList<? extends String[]> list, final String[] value, final int index, final boolean isSelected, final boolean cellHasFocus) {  
			for (int x = 0; x < lbl.length; x++) {
				lbl[x].setText((String) ((String[]) value)[x]);
			}
			return this;
		}
	}

}
