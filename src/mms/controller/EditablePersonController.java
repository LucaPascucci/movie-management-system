package mms.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import mms.controller.interfaces.IEditablePersonController;
import mms.database.ActorTable;
import mms.database.AdministratorTable;
import mms.database.CustomerTable;
import mms.database.RegisteredTable;
import mms.database.ShopTable;
import mms.exception.AlreadyExistsException;
import mms.model.Actor;
import mms.model.Administrator;
import mms.model.Customer;
import mms.model.IModel;
import mms.model.Registered;
import mms.model.Shop;
import mms.view.EditablePersonView;
/**
 * This class check the insert of the parameter of a new user.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class EditablePersonController extends AbstractController implements IEditablePersonController {

	/** The Constant ERROR_DOUBLE. */
	private static final String ERROR_DOUBLE = "This username already exist";

	/** The Constant ERROR_DATE. */
	private static final String ERROR_DATE = "Invalid date selection";

	/** The Constant ERROR_SQL. */
	private static final String ERROR_SQL = "Connection error, the database may not exists";

	/** The Constant ALREADY_SUBSCRIBE. */
	private static final String ALREADY_SUBSCRIBE = "Customer already subscribed to this shop: ";

	/** The Constant MUST_SUBSCRIBE. */
	private static final String MUST_SUBSCRIBE = "Customer must subscribe at least one shop";

	/** The Constant CORRECT_SUBSCRIBE. */
	private static final String CORRECT_SUBSCRIBE = "Subscribed at this shop: ";

	/** The Constant SELECT_SHOP. */
	private static final String SELECT_SHOP = "Select a Shop Code!";

	/** The editable record. */
	private final Object editableRecord;

	/** The return view. */
	private final Integer returnView;

	/** The code shops. */
	private final List<Integer> codeShops;

	/** The old code shops. */
	private List<Integer> oldCodeShops;

	/**
	 * This is the constructor of the EditableUserController.
	 * @param mod this parameter pass the model to the constructor.
	 * @param fromGUI this paramater pass the correct view.
	 * @param item this parameter pass the id of the user.
	 */
	public EditablePersonController(final IModel mod, final Integer fromGUI, final Object item) {
		super(mod, EDITABLE_PERSON_VIEW);
		this.returnView = fromGUI;
		this.editableRecord = item;
		this.oldCodeShops = new LinkedList<>();
		this.codeShops = new LinkedList<>();
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((EditablePersonView) this.frame).attachObserver(this);

	}

	@Override
	public boolean checkActor() {
		if (this.returnView.equals(SOCIETY_FILM_VIEW)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkAdmin() {
		if (this.returnView.equals(MANAGE_SHOP_VIEW) || this.returnView.equals(ADMIN_STATS_VIEW)) {
			return true;
		}
		return false;
	}

	@Override
	public Object getEditableRecord() {
		try {
			if (this.editableRecord == null) {
				return null;
			} else if (this.editableRecord instanceof String) {
				if (this.checkAdmin()) {
					return new AdministratorTable().findByPrimaryKey((String) editableRecord);
				} else {
					this.oldCodeShops = new RegisteredTable().findShopByUsername((String) editableRecord);
					return new CustomerTable().findByPrimaryKey((String) editableRecord);
				}
			} else {
				return new ActorTable().findByPrimaryKey((Integer) editableRecord);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			return null;
		}
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, this.returnView, null);
	}

	@Override
	public void doRegisterUser(final String name, final String surname, final String username, final String password, final Calendar birthDate) {
		if (username.length() > 0 && username.length() < 31 && name.length() > 0 && name.length() < 31 && surname.length() > 0 && surname.length() < 31 && password.length() > 0 && password.length() < 31 && !(birthDate == null || birthDate.after(Calendar.getInstance()))) {
			try {
				if (this.checkAdmin()) {
					final Administrator administrator = new Administrator();
					administrator.setName(name);
					administrator.setSurname(surname);
					administrator.setPassword(password);
					administrator.setBirthDate(birthDate.getTime());
					if (this.editableRecord != null) {
						administrator.setUsername((String) this.editableRecord);
						new AdministratorTable().update(administrator);
					} else {
						administrator.setUsername(username);
						new AdministratorTable().persist(administrator);
					}
					this.doBack();
				} else {
					final Customer customer = new Customer();
					customer.setBirthDate(birthDate.getTime());
					customer.setName(name);
					customer.setPassword(password);
					customer.setSurname(surname);
					if (this.codeShops.size() != 0) {
						if (this.editableRecord != null) {
							customer.setUsername((String) this.editableRecord);
							new CustomerTable().update(customer);
						} else {
							customer.setUsername(username);
							new CustomerTable().persist(customer);
						}
						final Registered registered = new Registered();
						final Iterator<Integer> it = this.codeShops.iterator();
						while (it.hasNext()) {
							registered.setCodShop(it.next());
							registered.setUsername(username);
							registered.setRegisteredDate(new Date());
							new RegisteredTable().persist(registered);
						}
						this.doBack();
					} else if (this.oldCodeShops.size() != 0) {
						if (this.editableRecord != null) {
							customer.setUsername((String) this.editableRecord);
							new CustomerTable().update(customer);
						} else {
							customer.setUsername(username);
							new CustomerTable().persist(customer);
						}
						this.doBack();
					} else {
						this.showErrorDialog(MUST_SUBSCRIBE);
					}
				}
			} catch (AlreadyExistsException exc) {
				this.saveError(exc);
				this.showErrorDialog(ERROR_DOUBLE);
				((EditablePersonView) this.frame).resetUserIDField();
			} catch (SQLException exc) {
				this.saveError(exc);
				this.showErrorDialog(ERROR_SQL);
			}
		} else {
			this.showErrorDialog(ERROR_INSERT + " / \n" + ERROR_DATE);
		}
	}

	@Override
	public void doRegisterActor(final String name, final String surname, final String nazionality, final Calendar birthDate) {
		if (name.length() > 0 && name.length() < 31 && surname.length() > 0 && surname.length() < 31 && !(birthDate == null || birthDate.after(Calendar.getInstance()))) {
			try {
				final Actor actor = new Actor();
				actor.setName(name);
				actor.setSurname(surname);
				actor.setNationality(nazionality);
				actor.setBirthDate(birthDate.getTime());
				if (this.editableRecord != null) {
					actor.setCodActor((Integer) this.editableRecord);
					new ActorTable().update(actor);
				} else {
					new ActorTable().persist(actor);
				}
				this.doBack();

			} catch (SQLException exc) {
				this.saveError(exc);
				this.showErrorDialog(ERROR_SQL);
			}
		} else {
			this.showErrorDialog(ERROR_INSERT + " / \n" + ERROR_DATE);
		}

	}

	@Override
	public void doFillCMB() {
		try {
			final Iterator<Shop> shops = new ShopTable().findAll().iterator();
			Integer curr;
			while (shops.hasNext()) {
				curr = shops.next().getCodeShop();
				((EditablePersonView) this.frame).fillShopCMB(curr.toString());
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		} catch (NullPointerException exc) {
			this.saveError(exc);
		}
	}

	@Override
	public void subscribeAtShop(final Integer codeShop) {
		if (codeShop == null) {
			this.showWarningDialog(SELECT_SHOP);
		} else if (this.oldCodeShops.contains(codeShop) || this.codeShops.contains(codeShop)) {
			this.showWarningDialog(ALREADY_SUBSCRIBE + codeShop);
		} else {
			this.codeShops.add(codeShop);
			this.showInfoDialog(CORRECT_SUBSCRIBE + codeShop);
		}
	}

	@Override
	public boolean isAdmin() {
		return this.model.getUserType().equals("admin");
	}

}
