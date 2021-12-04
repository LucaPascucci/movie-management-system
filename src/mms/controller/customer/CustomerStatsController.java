package mms.controller.customer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import mms.controller.AbstractController;
import mms.controller.interfaces.ICustomerStatsController;
import mms.database.CardTable;
import mms.database.CustomerTable;
import mms.database.PurchaseTable;
import mms.database.ShopTable;
import mms.exception.AlreadyExistsException;
import mms.model.Card;
import mms.model.Customer;
import mms.model.Film;
import mms.model.IModel;
import mms.view.customer.CustomerStatsView;
/**
 * This class manage the statistics of the user.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class CustomerStatsController extends AbstractController implements ICustomerStatsController {

	/** The Constant DELETE_FILM. */
	private static final String DELETE_FILM = "Do you really want to delete this film?";

	/** The Constant IMAGE_FORMAT. */
	private static final String[] IMAGE_FORMAT = new String[]{"jpg", "bmp", "jpeg", "wbmp", "png", "gif"};

	/** The Constant LOADING_ERROR_PURCHASE. */
	private static final String LOADING_ERROR_PURCHASE = "There are any films buyed.";

	/** The Constant DUPLICATE_CARD. */
	private static final String DUPLICATE_CARD = "You have already a shop card.";

	/** The Constant CARD_SUBSCRIBED. */
	private static final String CARD_SUBSCRIBED = "Card added to your profile.";

	/** The Constant NOT_HAVE_CARD. */
	private static final String NOT_HAVE_CARD = "To select a discount you must have a card.";

	/** The Constant SELECT_SHOP. */
	private static final String SELECT_SHOP = "Select a shop!";

	/** The customer id. */
	private final String customerID;
	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 * @param username this parameter pass the user by the userId.
	 */
	public CustomerStatsController(final IModel mod, final String username) {
		super(mod, CUSTOMER_STATS_VIEW);
		this.customerID = username;
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((CustomerStatsView) this.frame).attachObserver(this);
	}

	@Override
	public Customer getCustomer() {
		try {
			Customer customer = null;
			if (this.checkCustomer()) {
				customer = new CustomerTable().findByPrimaryKey(this.model.getCurrentUser().getUsername());
			} else {
				customer = new CustomerTable().findByPrimaryKey(this.customerID);
			}
			return customer;
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
		return null;
	}

	@Override
	public void showDetailsFilm(final Integer filmCode, final Integer shopCode) {
		if (shopCode != null) {
			this.frame.setName(shopCode.toString());
			this.changeView(CUSTOMER_STATS_VIEW, DETAILS_FILM_VIEW, filmCode);
		} else {
			this.showWarningDialog(SELECT_SHOP);
		}
	}

	@Override
	public void doBack() {
		if (!this.checkCustomer()) {
			this.changeView(this.activeView, MANAGE_CUSTOMERS_VIEW, null);
		} else {
			this.changeView(this.activeView, MENU_VIEW , null);
		}
	}

	@Override
	public boolean doDelete(final Integer filmCode, final Integer currShop) {
		try {
			if (currShop != null) {
				final Integer answer = this.showQuestionDialog(DELETE_FILM);
				if (answer == JOptionPane.YES_OPTION) {
					new PurchaseTable().delete(new PurchaseTable().findByPrimaryKey(filmCode, currShop, this.model.getCurrentUser().getUsername()));
					this.getTotPurchase(currShop);
					this.generateTable(currShop);
					return true;
				}
			} else {
				this.showWarningDialog(SELECT_SHOP);
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
		return false;

	}

	@Override
	public void doEditUser() {
		this.changeView(this.activeView, EDITABLE_PERSON_VIEW, this.model.getCurrentUser().getUsername());
	}

	@Override
	public void generateTable(final Integer currShop) {
		List<Film> list = null;
		Integer numFilm = null;
		((CustomerStatsView) this.frame).refreshTable();
		if (this.checkCustomer()) {
			if (currShop == null) {
				try {
					list = new PurchaseTable().getPurchased(this.model.getCurrentUser().getUsername());
					final Iterator<Film> it = list.iterator();
					Film curr;
					while (it.hasNext()) {
						curr = it.next();
						final Object[] obj = new Object[] {curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), String.format("%.2f", curr.getPrice())};
						((CustomerStatsView) this.frame).newRow(obj);
					}
					numFilm = list.size();
				} catch (SQLException e) {
					this.saveError(e);
					this.showErrorDialog(SQL_ERROR);
				} catch (NullPointerException e) {
					this.saveError(e);
					this.showWarningDialog(LOADING_ERROR_PURCHASE);
				}

			} else {
				try {
					list = new PurchaseTable().getPurchasedOfACustomerInAShop(currShop, this.model.getCurrentUser().getUsername());
					final Iterator<Film> it = list.iterator();
					Film curr;
					while (it.hasNext()) {
						curr = it.next();
						final Object[] obj = new Object[] {curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), String.format("%.2f", curr.getPrice())};
						((CustomerStatsView) this.frame).newRow(obj);
					}
					numFilm = list.size();
				} catch (SQLException e) {
					this.saveError(e);
					this.showErrorDialog(SQL_ERROR);
				} catch (NullPointerException e) {
					this.saveError(e);
					this.showWarningDialog(LOADING_ERROR_PURCHASE);
				}
			}
		} else {
			if (currShop == null) {
				try {
					list = new PurchaseTable().getPurchasedInAdminShop(this.model.getCurrentUser().getUsername(), this.customerID);
					final Iterator<Film> it = list.iterator();
					Film curr;
					while (it.hasNext()) {
						curr = it.next();
						final Object[] obj = new Object[] {curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), String.format("%.2f", curr.getPrice())};
						((CustomerStatsView) this.frame).newRow(obj);
					}
					numFilm = list.size();
				} catch (SQLException e) {
					this.saveError(e);
					this.showErrorDialog(SQL_ERROR);
				} catch (NullPointerException e) {
					this.saveError(e);
					this.showWarningDialog(LOADING_ERROR_PURCHASE);
				}
			} else {
				try {
					list = new PurchaseTable().getPurchasedOfACustomerInAShop(currShop, this.customerID);
					final Iterator<Film> it = list.iterator();
					Film curr;
					while (it.hasNext()) {
						curr = it.next();
						final Object[] obj = new Object[] {curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), String.format("%.2f", curr.getPrice())};
						((CustomerStatsView) this.frame).newRow(obj);
					}
					numFilm = list.size();
				} catch (SQLException e) {
					this.saveError(e);
					this.showErrorDialog(SQL_ERROR);
				} catch (NullPointerException e) {
					this.saveError(e);
					this.showWarningDialog(LOADING_ERROR_PURCHASE);
				}
			}
		}
		if (numFilm == null) {
			((CustomerStatsView) this.frame).setFilmVal(0);
		} else {
			((CustomerStatsView) this.frame).setFilmVal(numFilm);
		}

	}

	@Override
	public void getTotPurchase(final Integer currShop) {
		float totPuchase = 0;
		if (this.checkCustomer()) {
			try {
				totPuchase = new PurchaseTable().getPurchasedInAShop(currShop, this.model.getCurrentUser().getUsername());
			} catch (SQLException e) {
				this.saveError(e);
				this.showErrorDialog(SQL_ERROR);
			}
		} else {
			try {
				totPuchase = new PurchaseTable().getPurchasedInAShopOfTheAdmin(currShop, this.customerID, this.model.getCurrentUser().getUsername());
			} catch (SQLException e) {
				this.saveError(e);
				this.showErrorDialog(SQL_ERROR);
			}
		}
		((CustomerStatsView) this.frame).setPurchasedVal(totPuchase);
	}

	@Override
	public boolean checkCustomer() {
		return this.customerID == null ? true : false;
	}

	@Override
	public ImageIcon getProfileImage() {
		if (this.checkCustomer()) {
			if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + DEFAULT_USERS + this.model.getCurrentUser().getUsername() + PNG_FORMAT)) {
				return new ImageIcon(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + DEFAULT_USERS + this.model.getCurrentUser().getUsername()  + PNG_FORMAT);
			}
		} else {
			if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + DEFAULT_USERS + this.customerID  + PNG_FORMAT)) {
				return new ImageIcon(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + DEFAULT_USERS + this.customerID  + PNG_FORMAT);
			}
		}
		return new ImageIcon(this.getClass().getResource("/Default_Customer.png"));
	}

	@Override
	public void backToHome() {
		if (!this.checkCustomer()) {
			this.changeView(this.activeView, MANAGE_CUSTOMERS_VIEW, null);
		} else {
			this.changeView(this.activeView, LOGIN_VIEW, null);
		}
	}

	@Override
	public void doFillCmb() {
		try {
			Iterator<Integer> codes = null;
			if (this.checkCustomer()) {
				codes = new ShopTable().getShopCodesOfACustomer(this.model.getCurrentUser().getUsername()).iterator();
			} else {
				codes = new ShopTable().getShopCodesOfACustomerBelonginToAnAdmin(this.customerID, this.model.getCurrentUser().getUsername()).iterator();
			}
			Integer curr;
			while (codes.hasNext()) {
				curr = codes.next();
				((CustomerStatsView) this.frame).fillShopCMB(new String(curr.toString()));
			}
		} catch (SQLException exc) {
			this.saveError(exc);
		}
	}

	@Override
	public void getNewCard() {
		try {
			final Card card = new Card();
			card.setReleaseDate(new Date());
			card.setScoreCard(0);
			card.setUsername(this.model.getCurrentUser().getUsername());
			new CardTable().persist(card);
			this.showInfoDialog(CARD_SUBSCRIBED);
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} catch (AlreadyExistsException e) {
			this.saveError(e);
			this.showWarningDialog(DUPLICATE_CARD);
		}
	}

	@Override
	public void selectDiscount() {
		try {
			final Card card = new CardTable().findByUsername(this.model.getCurrentUser().getUsername());
			if (card == null) {
				this.showWarningDialog(NOT_HAVE_CARD);
			} else {
				this.changeView(CUSTOMER_STATS_VIEW, EDITABLE_CARD_VIEW, card);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
	}

	@Override
	public boolean changeProfileImage() {
		try {
			this.fileDialog.setAcceptAllFileFilterUsed(false);
			this.fileDialog.setFileFilter(new FileNameExtensionFilter("Supported Images", IMAGE_FORMAT));
			if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
				final BufferedImage img = ImageIO.read(new File(this.fileDialog.getSelectedFile().getPath()));
				final BufferedImage scaledImage = this.getScaledImage(img);
				File saveFile;
				if (this.checkCustomer()) {
					saveFile = new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + DEFAULT_USERS + this.model.getCurrentUser().getUsername() + PNG_FORMAT);
				} else {
					saveFile = new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + DEFAULT_USERS + this.customerID + PNG_FORMAT);
				}

				ImageIO.write(scaledImage, "png", saveFile);
				return true;
			}
		} catch (IOException exc) {
			this.saveError(exc);
		}
		return false;

	}

	/**
	 * This method returns a resized BufferedImage.
	 *
	 * @param originalImage the original image
	 * @return BufferedImage
	 */
	private BufferedImage getScaledImage(final BufferedImage originalImage) {

		final int maxWidth = 100;
		final int maxHeight = 100;

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		final Dimension originalDimension = new Dimension(originalImage.getWidth(), originalImage.getHeight());
		final Dimension boundaryDimension = new Dimension(maxWidth, maxHeight);
		final Dimension scalingDimension = getScaledDimension(originalDimension, boundaryDimension);

		width = (int) scalingDimension.getWidth();
		height = (int) scalingDimension.getHeight();

		final BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
		final Graphics2D g = resizedImage.createGraphics();

		g.drawImage(originalImage, 0, 0, width, height, null);

		return resizedImage;

	}

	/**
	 * This method returns the new dimension for ScaledImage.
	 *
	 * @param imgSize the img size
	 * @param boundary the boundary
	 * @return Dimension
	 */
	private Dimension getScaledDimension(final Dimension imgSize, final Dimension boundary) {

		final int originalWidth = imgSize.width;
		final int originalHeight = imgSize.height;
		final int boundWidth = boundary.width;
		final int boundHeight = boundary.height;
		int newWidth = originalWidth;
		int newHeight = originalHeight;

		// check if need to perform the scaling of witdh
		if (originalWidth > boundWidth) {

			newWidth = boundWidth;
			// scaling of height
			newHeight = (newWidth * originalHeight) / originalWidth;
		}

		// check if need to perform the scaling of height
		if (newHeight > boundHeight) {

			newHeight = boundHeight;
			// scaling of witdth
			newWidth = (newHeight * originalWidth) / originalHeight;
		}

		return new Dimension(newWidth, newHeight);
	}
}
