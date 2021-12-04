package mms.controller.customer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mms.controller.AbstractController;
import mms.controller.interfaces.IFavoriteFilmController;
import mms.database.CardTable;
import mms.database.DiscountTable;
import mms.database.FavoriteTable;
import mms.database.FileFilmTable;
import mms.database.FilmCopyTable;
import mms.database.FilmTable;
import mms.database.PurchaseTable;
import mms.database.ShopTable;
import mms.database.TypologyTable;
import mms.exception.AlreadyExistsException;
import mms.model.Card;
import mms.model.Discount;
import mms.model.Favorite;
import mms.model.FileFilm;
import mms.model.Film;
import mms.model.FilmCopy;
import mms.model.IModel;
import mms.model.Purchase;
import mms.model.Typology;
import mms.view.customer.FavoriteFilmView;
/**
 * This class manage the favorite films of an user.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FavoriteFilmController extends AbstractController implements IFavoriteFilmController {

	/** The Constant FILM_BOUGHT. */
	private static final String FILM_BOUGHT = "Film successfully purchased!";

	/** The Constant FILM_ALREADY_BOUGHT. */
	private static final String FILM_ALREADY_BOUGHT = "Film already bought!";

	/** The Constant DUPLICATE_CARD. */
	private static final String DUPLICATE_CARD = "You have already a shop card.";

	/** The Constant CARD_SUBSCRIBED. */
	private static final String CARD_SUBSCRIBED = "Card added to your profile.";

	/** The Constant NOT_HAVE_CARD. */
	private static final String NOT_HAVE_CARD = "To select a discount you must have a card.";

	/** The Constant NOT_HAVE_FAVORITE. */
	private static final String NOT_HAVE_FAVORITE = "There are any film selected as a favorite.";

	/** The Constant SELECT_SHOP. */
	private static final String SELECT_SHOP = "Select a shop.";

	/** The Constant FAVORITE_DELETED. */
	private static final String FAVORITE_DELETED = "Favorite deleted.";

	/** The Constant FAVORITE_NOT_FOUND. */
	private static final String FAVORITE_NOT_FOUND = "Favorite not found.";

	/** The Constant BUY_FILM. */
	private static final String BUY_FILM = "Do you want to buy this film?";

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public FavoriteFilmController(final IModel mod) {
		super(mod, FAVORITE_FILM_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((FavoriteFilmView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void generateTable(final Integer currShop) {
		((FavoriteFilmView) this.frame).refreshTable();
		((FavoriteFilmView) this.frame).unsetPreview();
		if (currShop == null) {
			try {
				final Iterator<Film> it = new FavoriteTable().getFavorite(this.model.getCurrentUser().getUsername()).iterator();
				Film curr;
				while (it.hasNext()) {
					curr = it.next();
					final Object[] obj = new Object[] {curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((FavoriteFilmView) this.frame).newRow(obj);
				}
			} catch (SQLException e) {
				this.saveError(e);
				this.showErrorDialog(SQL_ERROR);
			} catch (NullPointerException e) {
				this.saveError(e);
				this.showWarningDialog(NOT_HAVE_FAVORITE);
			}
		} else {
			try {
				final Iterator<Film> it = new FavoriteTable().getFavoriteOfACustomerInAShop(currShop, this.model.getCurrentUser().getUsername()).iterator();
				Film curr;
				while (it.hasNext()) {
					curr = it.next();
					final Object[] obj = new Object[] {curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
					((FavoriteFilmView) this.frame).newRow(obj);
				}
			} catch (SQLException e) {
				this.saveError(e);
				this.showErrorDialog(SQL_ERROR);
			} catch (NullPointerException e) {
				this.saveError(e);
				this.showWarningDialog(NOT_HAVE_FAVORITE);
			}
		}
	}

	@Override
	public void showDetailsFilm(final Integer filmCode, final Integer shopCode) {
		if (shopCode != null) {
			this.frame.setName(shopCode.toString());
			this.changeView(FAVORITE_FILM_VIEW, DETAILS_FILM_VIEW, filmCode);
		} else {
			this.showWarningDialog(SELECT_SHOP);
		}
	}

	@Override
	public void doBuy(final Integer filmCode, final Integer currShop) {
		try {
			final Integer answer = this.showQuestionDialog(BUY_FILM);
			if (answer == JOptionPane.YES_OPTION) {
				final Purchase purchase = new Purchase();
				purchase.setCodMoviePurch(filmCode);
				purchase.setCodShopPurch(currShop);
				purchase.setDatePurchase(new Date());
				final float cost = getTotalCostOfTheFilm(filmCode);
				purchase.setPrice(cost);
				purchase.setUsername(this.model.getCurrentUser().getUsername());
				new PurchaseTable().persist(purchase);
				final FilmCopy copy = new FilmCopyTable().findByPrimaryKey(currShop, filmCode);
				copy.setNumPurchases(copy.getNumPurchases() + 1);
				new FilmCopyTable().update(copy);
				Card card = new CardTable().findByUsername(this.model.getCurrentUser().getUsername());
				if (card  != null) {
					card.setScoreCard(card.getScoreCard() + (int) cost);
					new CardTable().update(card);
				}
				this.showInfoDialog(FILM_BOUGHT);
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		} catch (AlreadyExistsException e) {
			this.saveError(e);
			this.showWarningDialog(FILM_ALREADY_BOUGHT);
		}
	}

	/**
	 * Gets the total cost of the film.
	 *
	 * @param filmCode the film code
	 * @return the total cost of the film
	 */
	private float getTotalCostOfTheFilm(final Integer filmCode) {
		float cost = 0;
		Card card = null;
		try {
			final Film film = new FilmTable().findByPrimaryKey(filmCode);
			cost = film.getPrice();
			card = new CardTable().findByUsername(this.model.getCurrentUser().getUsername());
			if (card != null) {
				final Typology typology = new TypologyTable().findByPrimaryKey(card.getNumCard());
				if (typology != null) {
					final Discount discount = new DiscountTable().findByPrimaryKey(typology.getCodDiscount());
					final float percentage = discount.getPercentage();
					cost =  cost - (cost * percentage) / 100;	
				}
				card.setScoreCard(card.getScoreCard() + (int) cost);
				new CardTable().update(card);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
		return cost;
	}

	@Override
	public boolean deleteFavorite(final Integer filmCode) {
		try {
			final Favorite favorite = new FavoriteTable().findByPrimaryKey(filmCode, this.model.getCurrentUser().getUsername());
			if (favorite != null) {
				new FavoriteTable().delete(favorite);
				((FavoriteFilmView) this.frame).unsetPreview();
				this.showInfoDialog(FAVORITE_DELETED);
				return true;
			} else {
				this.showWarningDialog(FAVORITE_NOT_FOUND);
				return false;
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
		return false;

	}

	@Override
	public void showPreview(final Integer filmCode, final Integer codeShop) {
		try {
			final Film film = new FilmTable().findByPrimaryKey(filmCode);
			((FavoriteFilmView) this.frame).setPreview(film , this.getCoverImage(filmCode, codeShop));

		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
	}

	@Override
	public void doFillCmb() {
		try {
			final Iterator<Integer> codes = new ShopTable().getShopCodesOfACustomer(this.model.getCurrentUser().getUsername()).iterator();
			Integer curr;
			while (codes.hasNext()) {
				curr = codes.next();
				((FavoriteFilmView) this.frame).fillShopCMB(new String(curr.toString()));
			}
		} catch (SQLException e) {
			this.saveError(e);
		}
	}

	/**
	 * Gets the cover image.
	 *
	 * @param filmCode the film code
	 * @param codeShop the code shop
	 * @return the cover image
	 */
	private ImageIcon getCoverImage(final Integer filmCode, final Integer codeShop) {
		try {
			if (codeShop != null) {
				final List<FileFilm> covers = new FileFilmTable().getFilmCoversinShop(codeShop, filmCode, "png");
				if (covers != null) {
					final FileFilm firstCover = covers.remove(0);
					final BufferedImage img = ImageIO.read(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/" + firstCover.getCodShop() + "/" + firstCover.getCodFilm() + "." + firstCover.getName() + "." + firstCover.getExtension()));
					final BufferedImage scaledImage = this.getScaledImage(img);
					return new ImageIcon(scaledImage);
				}
			}
			final InputStream resource = this.getClass().getResourceAsStream("/Default_Cover.png");
			final BufferedImage img = ImageIO.read(resource);
			final BufferedImage scaledImage = this.getScaledImage(img);
			return new ImageIcon(scaledImage);

		} catch (IOException exc) {
			this.saveError(exc);
		} catch (SQLException exc) {
			this.saveError(exc);
			this.showErrorDialog(SQL_ERROR);
		}
		return null;
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
				this.changeView(FAVORITE_FILM_VIEW, EDITABLE_CARD_VIEW, card);
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showErrorDialog(SQL_ERROR);
		}
	}
}
