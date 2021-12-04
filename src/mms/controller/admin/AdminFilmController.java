package mms.controller.admin;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import mms.controller.AbstractController;
import mms.controller.interfaces.IAdminFilmController;
import mms.database.FileFilmTable;
import mms.database.FilmTable;
import mms.database.ShopTable;
import mms.exception.NoVideoIconException;
import mms.exception.UnsupportedCodecException;
import mms.mediaworker.VideoFilter;
import mms.mediaworker.VideoResizer;
import mms.model.FileFilm;
import mms.model.Film;
import mms.model.IModel;
import mms.view.admin.AdminFilmView;
/**
 * This class manage the editing of the data of a film.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class AdminFilmController extends AbstractController implements IAdminFilmController {


	/** The Constant BASE_PATH. */
	private static final String BASE_PATH = DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "films/";

	/** The Constant TEMPORARY_PATH. */
	private static final String TEMPORARY_PATH = DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + "tmp";

	/** The Constant VIDEO_NAME. */
	private static final Integer VIDEO_NAME = 0;

	/** The Constant VIDEO_FORMAT. */
	private static final String VIDEO_FORMAT = "flv";

	/** The Constant IMAGE_FORMAT. */
	private static final String IMAGE_FORMAT = "png";

	/** The Constant IMAGE_FILTER. */
	private static final String[] IMAGE_FILTER = new String[]{"jpg", "bmp", "jpeg", "wbmp", "png", "gif"};

	/** The Constant SELECT_SHOP. */
	private static final String SELECT_SHOP = "Select a shop!";

	/** The Constant TRAILER_INFO. */
	private static final String TRAILER_INFO = "This film have already a trailer!\nContinue? ( Previous will be eliminated! )";

	/** The Constant SELECT_IMAGE. */
	private static final String SELECT_IMAGE = "Select an image file";

	/** The Constant COVER_ADDED. */
	private static final String COVER_ADDED = "Cover added succesfully!";

	/** The Constant TRAILER_ADDED. */
	private static final String TRAILER_ADDED = "Trailer added succesfully!";

	/** The Constant DEFAULT_FILTER. */
	private static final String DEFAULT_FILTER = "Supported Format";

	/** The Constant UNSUPPORTED_CODEC. */
	private static final String UNSUPPORTED_CODEC = "Video Resizer doesn't support an audio or video codec!";

	/** The Constant ERROR_VIDEO_ICON. */
	private static final String ERROR_VIDEO_ICON = "Cannot complete adding video icon";

	/** The secondary thread. */
	private Thread secondaryThread = new Thread();

	/** The video resizer. */
	private VideoResizer videoResizer;

	/**
	 * This is the constructor of the class.
	 * @param mod this parameter pass the model to the constructor.
	 */
	public AdminFilmController(final IModel mod) {
		super(mod, ADMIN_FILMS_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((AdminFilmView) this.frame).attachObserver(this);

	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);

	}

	@Override
	public void doAddCover(final Integer filmCode, final Integer shopCode) {
		try {
			Integer nextImage = new FileFilmTable().getNextName(shopCode, filmCode, IMAGE_FORMAT);
			nextImage++;
			this.fileDialog.setAcceptAllFileFilterUsed(false);
			this.fileDialog.setFileFilter(new FileNameExtensionFilter("Supported Images", IMAGE_FILTER));
			if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
				final BufferedImage img = ImageIO.read(new File(this.fileDialog.getSelectedFile().getPath()));
				final BufferedImage scaledImage = this.getScaledImage(img);
				final File saveFile = new File(BASE_PATH + shopCode + "/" + filmCode + "." + nextImage + "." + IMAGE_FORMAT);
				ImageIO.write(scaledImage, IMAGE_FORMAT, saveFile);
				final FileFilm fileFilm = new FileFilm();
				fileFilm.setCodFilm(filmCode);
				fileFilm.setCodShop(shopCode);
				fileFilm.setName(nextImage);
				fileFilm.setExtension(IMAGE_FORMAT);
				new FileFilmTable().persist(fileFilm);
				this.showInfoDialog(COVER_ADDED);
			}
		} catch (IOException exc) {
			this.saveError(exc);
			this.showInfoDialog(SELECT_IMAGE);
		} catch (SQLException exc) {
			this.saveError(exc);
		}

	}

	@Override
	public void doAddMovie(final Integer currShop) {
		if (currShop != null) {
			this.changeView(this.activeView, ADD_COPY_FILM_VIEW, currShop);
		} else {
			this.showWarningDialog(SELECT_SHOP);
		}
	}

	@Override
	public void doAddTrailer(final Integer filmCode, final Integer shopCode) {
		try {
			if (new FileFilmTable().findByPrimaryKey(shopCode, filmCode, VIDEO_NAME) != null) {
				final Integer answer = this.showQuestionDialog(TRAILER_INFO);
				if (answer == JOptionPane.YES_OPTION) {
					final VideoFilter filter = new VideoFilter(DEFAULT_FILTER);
					this.fileDialog.setAcceptAllFileFilterUsed(false);
					this.fileDialog.setFileFilter(filter);
					if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
						((AdminFilmView) this.frame).setUnsetLoadingVisible();
						this.videoResizer = new VideoResizer(fileDialog.getSelectedFile().getPath(), TEMPORARY_PATH + "." + VIDEO_FORMAT, this);
						this.secondaryThread = new Thread() {
							public void run() {
								try {
									videoResizer.startEncoding(BASE_PATH  + shopCode + "/" + filmCode + "." + VIDEO_NAME + "." + VIDEO_FORMAT, filmCode, shopCode);
								} catch (UnsupportedCodecException exc) {
									saveError(exc);
									showErrorDialog(UNSUPPORTED_CODEC);
									secondaryThread.interrupt();
								} catch (NoVideoIconException exc) {
									saveError(exc);
									showErrorDialog(ERROR_VIDEO_ICON);
									secondaryThread.interrupt();
								}
							}
						};
						this.secondaryThread.start();
					}
				}
			} else {
				final VideoFilter filter = new VideoFilter(DEFAULT_FILTER);
				this.fileDialog.setAcceptAllFileFilterUsed(false);
				this.fileDialog.setFileFilter(filter);
				if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
					((AdminFilmView) this.frame).setUnsetLoadingVisible();
					this.videoResizer = new VideoResizer(this.fileDialog.getSelectedFile().getPath(), TEMPORARY_PATH + "." + VIDEO_FORMAT, this);
					this.secondaryThread = new Thread() {
						public void run() {
							try {
								videoResizer.startEncoding(BASE_PATH  + shopCode + "/" + filmCode + "." + VIDEO_NAME + "." + VIDEO_FORMAT, filmCode, shopCode);
							} catch (UnsupportedCodecException exc) {
								saveError(exc);
								showErrorDialog(UNSUPPORTED_CODEC);
								secondaryThread.interrupt();
							} catch (NoVideoIconException exc) {
								saveError(exc);
								showErrorDialog(ERROR_VIDEO_ICON);
								secondaryThread.interrupt();
							}
						}
					};
					this.secondaryThread.start();
				}
			}
		} catch (SQLException exc) {
			this.saveError(exc);
		}
	}

	@Override
	public void showDetailsFilm(final Integer filmCode, final Integer shopCode) {
		this.frame.setName(shopCode.toString());
		this.changeView(this.activeView, DETAILS_FILM_VIEW, filmCode);

	}

	@Override
	public void generateTable(final int currShop) {
		((AdminFilmView) this.frame).refreshTable();
		try {
			final Iterator<Film> films = new FilmTable().findFilmOfAShop(currShop).iterator();
			Film curr;
			while (films.hasNext()) {
				curr = films.next();
				final Object[] obj = new Object[]{curr.getCodeFilm(), curr.getTitle(), curr.getGenre(), curr.getReleaseYear(), curr.getPrice()};
				((AdminFilmView) this.frame).newRow(obj);
			}
		} catch (SQLException exc) {
			this.saveError(exc);
		} catch (NullPointerException exc) {
			this.showInfoDialog("There are no films in the selected shop!");
		}

	}

	@Override
	public void doFillCmb() {
		try {
			final Iterator<Integer> codes = new ShopTable().getShopCodesOfAnAdmin(this.model.getCurrentUser().getUsername()).iterator();
			Integer curr;
			while (codes.hasNext()) {
				curr = codes.next();
				((AdminFilmView) this.frame).fillAdminCMB(new String(curr.toString()));
			}
		} catch (SQLException exc) {
			this.saveError(exc);
		} catch (NullPointerException ecx) {
			this.showWarningDialog("This administator haven't shops!");
		}
	}

	@Override
	public void returnEncoding(final boolean check, final Integer filmCode, final Integer shopCode) {
		try {
			Toolkit.getDefaultToolkit().beep();
			((AdminFilmView) this.frame).setUnsetLoadingVisible();
			if (check) {
				this.showInfoDialog(TRAILER_ADDED);
				this.secondaryThread.interrupt();
				if (new FileFilmTable().findByPrimaryKey(shopCode, filmCode, VIDEO_NAME) == null) {
					final FileFilm fileFilm = new FileFilm();
					fileFilm.setCodFilm(filmCode);
					fileFilm.setCodShop(shopCode);
					fileFilm.setName(VIDEO_NAME);
					fileFilm.setExtension(VIDEO_FORMAT);
					new FileFilmTable().persist(fileFilm);
				}
			}
		} catch (SQLException exc) {
			this.saveError(exc);
		}
	}

	/**
	 * This method returns a resized BufferedImage.
	 *
	 * @param originalImage the original image
	 * @return BufferedImage
	 */
	private BufferedImage getScaledImage(final BufferedImage originalImage) {

		final int maxWidth = 200;
		final int maxHeight = 200;

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