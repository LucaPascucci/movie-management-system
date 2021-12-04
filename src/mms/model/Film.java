package mms.model;

/**
 * Create a film.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class Film {

	/** The code film. */
	private int codeFilm;

	/** The title. */
	private String title;

	/** The genre. */
	private FilmType genre;

	/** The price. */
	private float price; 

	/** The release year. */
	private int releaseYear;

	/** The plot. */
	private String plot;

	/**
	 * Get film code.
	 * @return int
	 */
	public int getCodeFilm() {
		return this.codeFilm;
	}

	/**
	 * Set film code.
	 * @param newCodeFilm new film code
	 */
	public void setCodeFilm(final int newCodeFilm) {
		this.codeFilm = newCodeFilm;
	}

	/**
	 * Get film title.
	 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Set film title.
	 * @param newTitle new film title
	 */
	public void setTitle(final String newTitle) {
		this.title = newTitle;
	}

	/**
	 * Get film genre.
	 * @return String
	 */
	public String getGenre() {
		return this.genre.toString();
	}

	/**
	 * Set film genre.
	 * @param newGenre new film genre
	 */
	public void setGenre(final FilmType newGenre) {
		this.genre = newGenre;
	}

	/**
	 * Get film price.
	 * @return float
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * Set film price.
	 * @param newPrice new film price
	 */
	public void setPrice(final float newPrice) {
		this.price = newPrice;
	}

	/**
	 * Get film release year.
	 * @return int
	 */
	public int getReleaseYear() {
		return this.releaseYear;
	}

	/**
	 * Set film release year.
	 * @param newReleaseYear new film release year
	 */
	public void setReleaseYear(final int newReleaseYear) {
		this.releaseYear = newReleaseYear;
	}

	/**
	 * Get film plot.
	 * @return string
	 */
	public String getPlot() {
		return this.plot;
	}

	/**
	 * Set film plot.
	 * @param newPlot new film plot
	 */
	public void setPlot(final String newPlot) {
		this.plot = newPlot;
	}
}
