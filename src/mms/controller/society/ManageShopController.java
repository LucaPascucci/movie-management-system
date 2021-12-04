package mms.controller.society;

import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import mms.controller.AbstractController;
import mms.controller.interfaces.IManageShopController;
import mms.database.AdministratorTable;
import mms.database.DiscountTable;
import mms.database.ShopTable;
import mms.model.Administrator;
import mms.model.Discount;
import mms.model.IModel;
import mms.model.Shop;
import mms.view.society.ManageShopView;

/**
 * Controller for the manage shop view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ManageShopController extends AbstractController implements IManageShopController {

	/**
	 * Constructor for this class.
	 * @param mod the model
	 */
	public ManageShopController(final IModel mod) {
		super(mod, MANAGE_SHOP_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((ManageShopView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void doAdd(final String currentTable) {
		if (currentTable.equals("Admins")) {
			this.changeView(this.activeView, EDITABLE_PERSON_VIEW, null);
		} else if (currentTable.equals("Shops")) {
			this.changeView(this.activeView, EDITABLE_SHOP_VIEW, null);
		} else if (currentTable.equals("Discounts")) {
			this.changeView(this.activeView, EDITABLE_DISCOUNT_VIEW, null);
		}
	}

	@Override
	public void doEdit(final String currentTable, final Object code) {
		if (currentTable.equals("Admins")) {
			this.changeView(this.activeView, EDITABLE_PERSON_VIEW, code);
		} else if (currentTable.equals("Shops")) {
			this.changeView(this.activeView, EDITABLE_SHOP_VIEW, code);
		} else if (currentTable.equals("Discounts")) {
			this.changeView(this.activeView, EDITABLE_DISCOUNT_VIEW, code);
		}

	}

	@Override
	public void generateTable(final String currentTable) {
		try {
			if (currentTable.equals("Admins")) {
				final Iterator<Administrator> admins = new AdministratorTable().findAll().iterator();
				Administrator curr;
				while (admins.hasNext()) {
					curr = admins.next();
					final Object[] obj = new Object[]{curr.getUsername(), curr.getPassword(), curr.getName(), curr.getSurname()};
					((ManageShopView) this.frame).newRow(obj);
				}
			} else if (currentTable.equals("Shops")) {
				final Iterator<Shop> shops = new ShopTable().findAll().iterator();
				Shop curr;
				while (shops.hasNext()) {
					curr = shops.next();
					final Object[] obj = new Object[]{curr.getCodeShop(), curr.getAddress(), curr.getTelephone(), curr.getUsernameAdmin()};
					((ManageShopView) this.frame).newRow(obj);
				}
			} else if (currentTable.equals("Discounts")) {
				final Iterator<Discount> discounts = new DiscountTable().findAll().iterator();
				Discount curr;
				while (discounts.hasNext()) {
					curr = discounts.next();
					final Object[] obj = new Object[]{curr.getDiscountCode(), curr.getPercentage(), curr.getPeriod(), curr.getDiscountScore()};
					((ManageShopView) this.frame).newRow(obj);
				}
			}
			((ManageShopView) this.frame).setTableToolTipText("Double click to edit");
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		} catch (NullPointerException exc) {
			((ManageShopView) this.frame).setTableToolTipText(null);
		}
	}

	@Override
	public boolean doDelete(final String currentTable, final Object primaryKey) { 
		try {
			if (currentTable.equals("Admins")) {
				final List<Integer> shopsCode = new ShopTable().getShopCodesOfAnAdmin((String) primaryKey);
				if (shopsCode != null) {
					final Iterator<Integer> it = shopsCode.iterator();
					while (it.hasNext()) {
						this.deleteAllFiles(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/" + it.next() + "/"));
					}
				}
				new AdministratorTable().delete(new AdministratorTable().findByPrimaryKey((String) primaryKey));
			} else if (currentTable.equals("Shops")) {
				new ShopTable().delete(new ShopTable().findByPrimaryKey((Integer) primaryKey));
				this.deleteAllFiles(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/" + (Integer) primaryKey + "/"));
			} else if (currentTable.equals("Discounts")) {
				new DiscountTable().delete(new DiscountTable().findByPrimaryKey((Integer) primaryKey));
			}
			return true;
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog("Problem during the deletion");
		}
		return false;

	}

}
