package mms.model;

/**
 * Create the object that have the information of the file of a film in a specific shop.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FileFilm {

	/** The cod film. */
	private int codFilm;

	/** The cod shop. */
	private int codShop;

	/** The name. */
	private int name;

	/** The extension. */
	private String extension;

	/**
	 * Get shop code.
	 * @return int
	 */
	public int getCodShop() {
		return this.codShop;
	}

	/**
	 * Set shop code.
	 * @param newCodShop new shop code
	 */
	public void setCodShop(final int newCodShop) {
		this.codShop = newCodShop;
	}

	/**
	 * Get file name.
	 * @return int 
	 */
	public int getName() {
		return this.name;
	}

	/**
	 * Set file name.
	 * @param newName new file name
	 */
	public void setName(final int newName) {
		this.name = newName;
	}

	/**
	 * Get file extension.
	 * @return String
	 */
	public String getExtension() {
		return this.extension;
	}

	/**
	 * Set file extension.
	 * @param newExtension new file extension
	 */
	public void setExtension(final String newExtension) {
		this.extension = newExtension;
	}

	/**
	 * Get film code.
	 * @return int
	 */
	public int getCodFilm() {
		return this.codFilm;
	}

	/**
	 * Set film code.
	 * @param newCodFilm new film code
	 */
	public void setCodFilm(final int newCodFilm) {
		this.codFilm = newCodFilm;
	}
}
