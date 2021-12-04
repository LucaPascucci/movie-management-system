package mms.controller.society;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.JFrame;

import mms.controller.AbstractController;
import mms.controller.interfaces.IEditableShopController;
import mms.database.AdministratorTable;
import mms.database.ShopTable;
import mms.model.Administrator;
import mms.model.IModel;
import mms.model.Shop;
import mms.view.society.EditableShopView;

/**
 * The controller for the editable shop view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class EditableShopController extends AbstractController implements IEditableShopController {

	/** The Constant INVALID_NUMBER. */
	private static final String INVALID_NUMBER = "Insert a correct phone number!";

	/** The editable shop. */
	private final Integer editableShop;

	/** The username admin. */
	private String usernameAdmin;

	/**
	 * Constructor for this class.
	 * @param mod the model
	 * @param code the code
	 */
	public EditableShopController(final IModel mod, final Integer code) {
		super(mod, EDITABLE_SHOP_VIEW);
		this.editableShop = code;
		this.usernameAdmin = null;
	}

	@Override
	public Shop getEditableShop() {
		try {
			if (this.editableShop != null) {
				final Shop shop = new ShopTable().findByPrimaryKey(this.editableShop);
				this.usernameAdmin = shop.getUsernameAdmin();
				return shop;
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
		return null;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((EditableShopView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MANAGE_SHOP_VIEW, null);
	}

	@Override
	public void doFill() {
		try {
			final Iterator<Administrator> administrators = new AdministratorTable().findAll().iterator();
			Administrator curr;
			Integer index = 1;
			while (administrators.hasNext()) {
				curr = administrators.next();
				if (this.usernameAdmin != null) {
					if (curr.getUsername().equals(this.usernameAdmin)) {
						((EditableShopView) this.frame).fillAdminCMB(new String[]{curr.getUsername(), curr.getName(), curr.getSurname()}, index);
					} else {
						((EditableShopView) this.frame).fillAdminCMB(new String[]{curr.getUsername(), curr.getName(), curr.getSurname()}, null);
					}
					index++;
				} else {
					((EditableShopView) this.frame).fillAdminCMB(new String[]{curr.getUsername(), curr.getName(), curr.getSurname()}, null);
				}

			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		} catch (NullPointerException exc) {
			this.saveError(exc);
		}
	}

	@Override
	public void doConfirm(final String address, final String telephone, final String admin) {
		try {
			if (address.length() > 0 && address.length() < 100 && telephone.length() > 0 && admin != null) {
				Integer.parseInt(telephone);
				final Shop shop = new Shop();
				shop.setAddress(address);
				shop.setTelephone(telephone);
				shop.setUsernameAdmin(admin);
				if (this.editableShop != null) {
					shop.setCodeShop(this.editableShop);
					new ShopTable().update(shop);
				} else {
					new ShopTable().persist(shop);
					final Integer shopCode = new ShopTable().getMaxShopCode();
					new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/" + shopCode + "/").mkdir();
				}
				this.doBack();
			} else {
				this.showErrorDialog(ERROR_INSERT);
				((EditableShopView) this.frame).resetField();
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} catch (NumberFormatException exc) {
			this.saveError(exc);
			this.showErrorDialog(INVALID_NUMBER);
		}
	}

}
