package mms.controller.admin;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mms.controller.AbstractController;
import mms.controller.interfaces.IManageCustomerController;
import mms.database.CustomerTable;
import mms.database.ReviewTable;
import mms.database.ShopTable;
import mms.model.Customer;
import mms.model.IModel;
import mms.view.admin.ManageCustomerView;
/**
 * This class manage the editing of the data of an user. 
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ManageCustomerController extends AbstractController implements IManageCustomerController {

	/** The Constant DELETE_USER. */
	private static final String DELETE_USER = "Do you really want to delete this user?";
	/**
	 * This is the constructor of the class.	
	 * @param mod this parameter pass the model to the constructor.
	 */
	public ManageCustomerController(final IModel mod) {
		super(mod, MANAGE_CUSTOMERS_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((ManageCustomerView) this.frame).attachObserver(this);

	}

	@Override
	public void doEditUser(final String username) {
		this.changeView(this.activeView, EDITABLE_PERSON_VIEW, username);
	}

	@Override
	public boolean doDeleteUser(final String username) {
		try {
			final Integer answer = this.showQuestionDialog(DELETE_USER);
			if (answer == JOptionPane.YES_OPTION) {
				new ReviewTable().deleteAllReviewOfACustomer(username);
				new CustomerTable().delete(new CustomerTable().findByPrimaryKey(username));
				this.deleteAllFiles(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "users/" + username + ".png"));
				return true;
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
		return false;
	}

	@Override
	public void doUserStats(final String username) {
		this.changeView(this.activeView, CUSTOMER_STATS_VIEW, username);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void generateTable(final Integer currShop) {
		if (currShop == null) {
			((ManageCustomerView) this.frame).refreshTable();
			try {
				final Iterator<Customer> customers = new CustomerTable().findUsersSubscribedToAdminShops(this.model.getCurrentUser().getUsername()).iterator();
				Customer curr;
				final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				while (customers.hasNext()) {
					curr = customers.next();
					final Date birthdate = curr.getBirthDate();
					final String date = df.format(birthdate);
					final Object[] obj = new Object[]{curr.getUsername(), curr.getPassword(), curr.getName(), curr.getSurname(), date};
					((ManageCustomerView) this.frame).newRow(obj);
				}
			} catch (SQLException e) {
				this.saveError(e);
			} catch (NullPointerException e) {
				this.showInfoDialog("There are no users in the selected shop");
			}
		} else {
			((ManageCustomerView) this.frame).refreshTable();
			try {
				final Iterator<Customer> customers = new CustomerTable().findUsersSubscribedToAShop(currShop).iterator();
				Customer curr;
				final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				while (customers.hasNext()) {
					curr = customers.next();
					final Date birthdate = curr.getBirthDate();
					final String date = df.format(birthdate);
					final Object[] obj = new Object[]{curr.getUsername(), curr.getPassword(), curr.getName(), curr.getSurname(), date};
					((ManageCustomerView) this.frame).newRow(obj);
				}
			} catch (SQLException e) {
				this.saveError(e);
			} catch (NullPointerException e) {
				this.showInfoDialog("There are no users in the selected shop");
			}
		}
	}

	@Override
	public void doFillCmb() {
		try {
			final Iterator<Integer> codes = new ShopTable().getShopCodesOfAnAdmin(this.model.getCurrentUser().getUsername()).iterator();
			Integer curr;
			while (codes.hasNext()) {
				curr = codes.next();
				((ManageCustomerView) this.frame).fillAdminCMB(new String(curr.toString()));
			}
		} catch (SQLException e) {
			this.saveError(e);
		} catch (NullPointerException ecx) {
			this.showWarningDialog("This administator haven't shops!");
		}
	}
}
