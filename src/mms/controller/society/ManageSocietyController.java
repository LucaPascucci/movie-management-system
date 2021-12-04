package mms.controller.society;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import mms.controller.AbstractController;
import mms.controller.interfaces.IManageSocietyController;
import mms.database.DBConnection;
import mms.model.IModel;
import mms.model.Society;
import mms.view.society.ManageSocietyView;

/**
 * Controller for the manage society view.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ManageSocietyController extends AbstractController implements IManageSocietyController {

	/** The Constant ERROR_NUMBER. */
	private static final String ERROR_NUMBER = "Insert number in Telephone and PIVA!";

	/** The Constant ERROR_DATABASE_EXISTS. */
	private static final String ERROR_DATABASE_EXISTS = "Database already exists";

	/** The Constant ERROR_DATABASE_NOT_EXISTS. */
	private static final String ERROR_DATABASE_NOT_EXISTS = "Database not exists";

	/** The Constant INFO_DATABASE_CREATED. */
	private static final String INFO_DATABASE_CREATED = "Database succesfully created!";

	/** The Constant INFO_DATABASE_DROPPED. */
	private static final String INFO_DATABASE_DROPPED = "Database successfully dropped!";

	/** The Constant DROP_INFO. */
	private static final String DROP_INFO = "Do you really want to drop the database?";

	/** The Constant SAVING_INFO. */
	private static final String SAVING_INFO = "Do you really want to save the society?";

	/** The Constant DEFAULT_SOCIETY_IMAGE. */
	private static final String DEFAULT_SOCIETY_IMAGE = "Default_Society_Logo.png";

	/** The Constant SOCIETY_LOGO. */
	private static final String SOCIETY_LOGO = "Society_Logo.png";

	/** The Constant FILE_NOT_ACCEPTED. */
	private static final String FILE_NOT_ACCEPTED = "Thumbs.db";

	/** The Constant ERROR_DATA_FILE. */
	private static final String ERROR_DATA_FILE = "Data file doesn't exists";

	/** The Constant ERROR_SAVE_DATA. */
	private static final String ERROR_SAVE_DATA = "Error while saving data to disk";

	/** The Constant ERROR_LOAD_DATA. */
	private static final String ERROR_LOAD_DATA = "Error while loading data from disk";

	/** The Constant ERROR_FILENAME. */
	private static final String ERROR_FILENAME = "File name can't contain this char (.)";

	/** The Constant IMAGE_FORMAT. */
	private static final String[] IMAGE_FORMAT = new String[]{"jpg", "bmp", "jpeg", "wbmp", "png", "gif"};

	/**
	 * Constructor for this class.
	 * @param mod the model.
	 */
	public ManageSocietyController(final IModel mod) {
		super(mod, MANAGE_SOCIETY_VIEW);
	}

	@Override
	public void setView(final JFrame f) {
		this.frame = f;
		((ManageSocietyView) this.frame).attachObserver(this);
	}

	@Override
	public void doBack() {
		this.changeView(this.activeView, MENU_VIEW, null);
	}

	@Override
	public void doCreateDatabase() {
		try {
			new DBConnection().createDataBase();
			this.showInfoDialog(INFO_DATABASE_CREATED);
		} catch (SQLException e) {
			this.saveError(e);
			this.showWarningDialog(ERROR_DATABASE_EXISTS);
		}
	}

	@Override
	public void doDropDatabase() {
		try {
			if (this.showQuestionDialog(DROP_INFO) == JOptionPane.YES_OPTION) {
				this.deleteAllFiles(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH));
				new DBConnection().dropDataBase();
				this.showInfoDialog(INFO_DATABASE_DROPPED);
			}
		} catch (SQLException e) {
			this.saveError(e);
			this.showWarningDialog(ERROR_DATABASE_NOT_EXISTS);
		}

	}

	@Override
	public void openLink(final boolean control) {
		try {
			if (control) {
				Desktop.getDesktop().browse(new URI(this.getSociety().getWebAddress()));
			} else {
				this.contactSociety();
			}
		} catch (URISyntaxException | IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_OPEN);
		}
	}

	@Override
	public Society getSociety() {
		return this.model.getSociety();
	}

	@Override
	public ImageIcon getLogo() {
		if (this.model.getSociety().isLogo()) {
			return new ImageIcon(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + SOCIETY_LOGO);
		} else {
			return new ImageIcon(this.getClass().getResource("/" + DEFAULT_SOCIETY_IMAGE));
		}
	}

	@Override
	public void doSaveSociety(final String username, final String password, final String name, final String address, final String town, final String partitaIVA, final String telephone, final String mail, final String webAddress) {
		try {
			if (this.showQuestionDialog(SAVING_INFO) == JOptionPane.YES_OPTION) {
				if (telephone.length() != 0) {
					Integer.parseInt(telephone);
				}
				if (partitaIVA.length() != 0) {
					Long.parseLong(partitaIVA);
				}

				this.model.editSociety(username, password, name, address, town, partitaIVA, telephone, mail, webAddress);
				this.saveDataCmd();
				((ManageSocietyView) this.frame).saveSociety(this.getSociety());
			}
		} catch (NumberFormatException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_NUMBER);
			((ManageSocietyView) this.frame).resetNumericField();
		}
	}

	@Override
	public void doEditSociety() {
		((ManageSocietyView) this.frame).editSociety(this.getSociety());
	}

	@Override
	public boolean doChangeLogo() {
		try {
			this.fileDialog = new JFileChooser();
			this.fileDialog.setAcceptAllFileFilterUsed(false);
			this.fileDialog.setFileFilter(new FileNameExtensionFilter("Supported Images", IMAGE_FORMAT));
			if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
				final BufferedImage img = ImageIO.read(new File(this.fileDialog.getSelectedFile().getPath()));
				final BufferedImage scaledImage = this.getScaledImage(img);
				final File saveFile = new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + SOCIETY_LOGO);
				ImageIO.write(scaledImage, "png", saveFile);
				this.model.insertSocietyLogo();
				this.saveDataCmd();
				return true;
			}
		} catch (IOException exc) {
			this.saveError(exc);
		}
		return false;
	}

	@Override
	public void doExportSociety() {
		this.fileDialog = new JFileChooser();
		if (this.fileDialog.showSaveDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
			final String fileName = this.fileDialog.getSelectedFile().getName();
			final String path =  this.fileDialog.getSelectedFile().getPath();
			this.doSaveZipSociety(fileName, path);
		}
	}

	@Override
	public boolean doImportSociety() {
		this.fileDialog = new JFileChooser();
		this.fileDialog.setAcceptAllFileFilterUsed(false);
		this.fileDialog.setFileFilter(new FileNameExtensionFilter("Supported Data", "zip"));
		if (this.fileDialog.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
			this.doBackupSociety();
			this.removeOldData();
			this.doLoadZipSociety(this.fileDialog.getSelectedFile().getPath(), this.fileDialog.getSelectedFile().getName());
			this.loadDataCmd();
			((ManageSocietyView) this.frame).saveSociety(this.getSociety());
			return true;
		}
		return false;

	}

	/**
	 * Removes the old data.
	 */
	private void removeOldData() {
		if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH)) {
			new File(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH).delete();
		}
		if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + SOCIETY_LOGO)) {
			new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + SOCIETY_LOGO).delete();
		}
	}

	/**
	 * Do backup society.
	 */
	private void doBackupSociety() {
		final Calendar currentDay = Calendar.getInstance();
		final String zipName = currentDay.get(Calendar.DAY_OF_MONTH) + "-" + (currentDay.get(Calendar.MONTH) + 1) + "-" + currentDay.get(Calendar.YEAR) + "_" + currentDay.get(Calendar.HOUR_OF_DAY) + "-" + currentDay.get(Calendar.MINUTE) + "-" + currentDay.get(Calendar.SECOND);
		this.doSaveZipSociety(zipName, DEFAULT_USER_PATH + DEFAULT_BACKUP_PATH + zipName);
	}

	/**
	 * Do save zip society.
	 *
	 * @param fileName the file name
	 * @param path the path
	 */
	private void doSaveZipSociety(final String fileName, final String path) {
		try {
			if (this.fileExist(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH)) {
				if (!fileName.contains(".")) {
					final ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(path + ZIP_FORMAT)));
					this.compress(new File(DEFAULT_USER_PATH + DEFAULT_SAVE_PATH), "", out);
					if (this.model.getSociety().isLogo()) {
						this.compress(new File(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + SOCIETY_LOGO), "", out);
					}
					out.flush();
					out.close();
				} else {
					this.showErrorDialog(ERROR_FILENAME);
				}
			} else {
				this.showErrorDialog(ERROR_DATA_FILE);
			}
		} catch (FileNotFoundException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_SAVE_DATA);

		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_SAVE_DATA);
		}
	}

	/**
	 * Compress.
	 *
	 * @param f the f
	 * @param path the path
	 * @param zos the zos
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void compress(final File f, final String path, final ZipOutputStream zos) throws IOException {
		final String nextPath = path + f.getName() + (f.isDirectory() ? "/" : "");
		final ZipEntry zipEntry = new ZipEntry(nextPath);
		zos.putNextEntry(zipEntry);
		final FileInputStream fis = new FileInputStream(f);
		final byte[] readBuffer = new byte[BUFFER_SIZE];
		int bytesIn = 0;
		while ((bytesIn = fis.read(readBuffer)) != -1) {
			zos.write(readBuffer, 0, bytesIn);
		}
		fis.close();
	}

	/**
	 * This method load a zip file.
	 * @param path this parameter pass the path of the computer.
	 * @param nameFile this parameter pass the name of the file.
	 */
	private void doLoadZipSociety(final String path, final String nameFile) {
		try {
			final ZipFile zipFile = new ZipFile(path);
			final ZipInputStream zis = new ZipInputStream(new FileInputStream(path));
			ZipEntry zipEntry;
			boolean firstDirectory = true;
			while ((zipEntry = zis.getNextEntry()) != null) {
				final String currentPath = zipEntry.getName();
				if (zipEntry.isDirectory() && currentPath.substring(0, currentPath.length() - 1).equals(nameFile.substring(0, nameFile.length() - 4)) && firstDirectory) {
					firstDirectory = false;
				} else if (zipEntry.isDirectory()) {
					new File(DEFAULT_USER_PATH + currentPath).mkdir();
				} else if (!currentPath.contains(FILE_NOT_ACCEPTED)) {
					final InputStream is = zipFile.getInputStream(zipEntry);
					FileOutputStream fos;
					if (currentPath.contains(SOCIETY_LOGO)) {
						fos = new FileOutputStream(DEFAULT_USER_PATH + DEFAULT_RESOURCES_PATH + currentPath);
					} else {
						fos = new FileOutputStream(DEFAULT_USER_PATH + currentPath);
					}
					final byte[] byteArray = new byte[BUFFER_SIZE];
					Integer byteLetti;
					while ((byteLetti = is.read(byteArray)) != -1) {
						fos.write(byteArray, 0, byteLetti);
					}
					fos.close();
					is.close();
				}
			}
			zipFile.close();
			zis.close();
		} catch (IOException exc) {
			this.saveError(exc);
			this.showErrorDialog(ERROR_LOAD_DATA);
		}
	}

	/**
	 * This method returns a resized BufferedImage.
	 *
	 * @param originalImage the original image
	 * @return BufferedImage
	 */
	private BufferedImage getScaledImage(final BufferedImage originalImage) {

		final int maxWidth = 520;
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
