package mms.controller.interfaces;

import javax.swing.ImageIcon;

import mms.controller.society.ManageSocietyController;
import mms.model.Society;

/**
 * Interface that define the {@link ManageSocietyController}.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IManageSocietyController {

	/**
	 * Creates the database.
	 */
	void doCreateDatabase();

	/**
	 * Drops the database and deletes all files locally.
	 */
	void doDropDatabase();

	/**
	 * Gets the society.
	 * @return the society.
	 */
	Society getSociety();

	/**
	 * Opens up the view to edit the society infos.
	 */
	void doEditSociety();

	/**
	 * Saves the society in the model.
	 * @param username the username
	 * @param password the password
	 * @param name the name
	 * @param address the address
	 * @param town the town
	 * @param partitaIVA the VAT
	 * @param telephone the telephone
	 * @param mail the mail
	 * @param webAddress the web address
	 */
	void doSaveSociety(final String username, final String password, final String name, final String address, final String town, final String partitaIVA, final String telephone, final String mail, final String webAddress);

	/**
	 * Opens up the web address link.
	 * @param control control
	 */
	void openLink(final boolean control);

	/**
	 * Changes the society logo.
	 * @return boolean
	 */
	boolean doChangeLogo();

	/**
	 * Returns the imageicon of the society icon.
	 * @return ImageIcon
	 */
	ImageIcon getLogo();

	/**
	 * Exports the society locally.
	 */
	void doExportSociety();

	/**
	 * Imports the society from a local file.
	 * @return boolean
	 */
	boolean doImportSociety();

}
