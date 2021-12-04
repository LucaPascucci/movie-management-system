package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IFilmTable;
import mms.model.Film;
import mms.model.FilmType;

/**
 * Table fot the film.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FilmTable extends AbstractTable implements IFilmTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public FilmTable() throws SQLException {
		super("FILM");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "CodiceFilm INT IDENTITY NOT NULL,"
						+ "Titolo VARCHAR(100) NOT NULL, " 
						+ "Genere VARCHAR(30) NOT NULL, "
						+ "Prezzo FLOAT NOT NULL,"
						+ "AnnoUscita INT NOT NULL,"
						+ "Trama VARCHAR(8000) NOT NULL, "
						+ "CONSTRAINT IDFILM PRIMARY KEY (CodiceFilm)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException {
		final Film film = (Film) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Titolo, Genere, Prezzo, AnnoUscita, Trama) values (?,?,?,?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setString(1, film.getTitle());
		statement.setString(2, film.getGenre());
		statement.setDouble(3, film.getPrice());
		statement.setInt(4, film.getReleaseYear());
		statement.setString(5, film.getPlot());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		final Film film = (Film) record;
		final Film oldFilm = findByPrimaryKey(film.getCodeFilm());
		if (oldFilm != null) {
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final String insert = "update " + this.tableName 
					+ " set Titolo = ?, Genere = ?, Prezzo = ?, AnnoUscita = ?, Trama = ? where CodiceFilm = ?";
			final PreparedStatement statement = this.connection.prepareStatement(insert);
			statement.setString(1, film.getTitle());
			statement.setString(2, film.getGenre());
			statement.setFloat(3, film.getPrice());
			statement.setInt(4, film.getReleaseYear());
			statement.setString(5, film.getPlot());
			statement.setInt(6, oldFilm.getCodeFilm());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {
		final Film film = (Film) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodiceFilm = ?";
		PreparedStatement statement = this.connection.prepareStatement(insert);
		statement = connection.prepareStatement(insert);
		statement.setInt(1, film.getCodeFilm());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Film findByPrimaryKey(final int code) throws SQLException {
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodiceFilm = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, code);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
		}
		statement.close();
		this.closeConnection();
		return film;
	}

	@Override
	public List<Film> findAll() throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " order by CodiceFilm";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		result.close();
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public int getMaxFilmCode() throws SQLException {
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select MAX(CodiceFilm) as MaxCodiceFilm from " + this.tableName;
		final PreparedStatement statement = connection.prepareStatement(query);
		final ResultSet result = statement.executeQuery();
		result.next();
		final int code = result.getInt("MaxCodiceFilm");
		statement.close();
		this.closeConnection();
		return code;
	}

	@Override
	public List<Film> findMissingFilm(final boolean society, final int codShop) throws SQLException {
		List<Film> films = null;
		Film film = null;
		String query = null;
		if (society) {
			this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
			query = "select * from FILM"
					+ " where FILM.CodiceFilm not in (select COPIA_FILM.CodFilm from COPIA_FILM"
					+ " where COPIA_FILM.CodNegozio = ?)";
		} else {
			this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
			query = "select FILM.* from FILM, COPIA_FILM"
					+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm "
					+ " and COPIA_FILM.CodNegozio = ?";
		}
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codShop);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public List<Film> findFilmOfAShop(final int codeShop) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select FILM.* from FILM, COPIA_FILM"
				+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
				+ " and COPIA_FILM.CodNegozio = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codeShop);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;

	}

	@Override
	public List<Film> orderByViewsOrPurchases(final int codeShop, final boolean viewedOrBought) throws SQLException {
		List<Film> films = null;
		Film film = null;
		String toQuery = null;
		if (viewedOrBought) {
			toQuery = "NumeroVisualizzazioni";
		} else {
			toQuery = "NumeroAcquisti";
		}
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select FILM.* from FILM, COPIA_FILM, NEGOZIO"
				+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
				+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and NEGOZIO.CodiceNegozio = ?"
				+ " order by COPIA_FILM." + toQuery + " desc";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codeShop);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;

	}

	@Override
	public List<Film> orderByInsertionDate(final int codeShop, final java.util.Date date) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select FILM.* from FILM, COPIA_FILM, NEGOZIO "
				+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
				+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and NEGOZIO.CodiceNegozio = ?"
				+ " and COPIA_FILM.DataInserimento >= ?"
				+ " order by COPIA_FILM.DataInserimento desc";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codeShop);
		statement.setDate(2, new java.sql.Date(date.getTime()));
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public List<Film> getCorrectFilmList(final int codeShop, final Object[] filter, final int control, final java.util.Date date) throws SQLException {
		List<Film> films = null;
		Film film = null;
		if (filter == null) {
			if (control == 1) {
				films = this.findFilmOfAShop(codeShop);
				return films;
			} else if (control == 2) {
				films = this.orderByViewsOrPurchases(codeShop, true);
				return films;
			} else if (control == 3) {
				films = this.orderByViewsOrPurchases(codeShop, false);
				return films;
			} else if (control == 4) {
				films = this.orderByInsertionDate(codeShop, date);
			}
		} else {
			final String titleOrLetters = (String) filter[0];
			String genre = null;
			if (filter[1] != null) {
				genre = filter[1].toString();
			}
			final Integer price1 = (Integer) filter[2];
			final Integer price2 = (Integer) filter[3];
			final Integer year = (Integer) filter[4];
			String toQueryTitleOrLetters = "";
			String toQueryGenre = "";
			String toQueryPrice = "";
			String toQueryYear = "";
			if (titleOrLetters != null) {
				toQueryTitleOrLetters = " and FILM.Titolo like '" + titleOrLetters + "%'";
			}
			if (genre != null) {
				toQueryGenre = " and FILM.Genere = '" + genre + "'";
			}
			if (price1 != null && price2 != null) {
				toQueryPrice = " and FILM.Prezzo between " + price1 + " and " + price2;
			}
			if (year != null) {
				toQueryYear = " and FILM.AnnoUscita = " + year;
			}
			this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
			PreparedStatement statement = null;
			if (control == 1) {
				final String query = "select FILM.* from FILM, COPIA_FILM, NEGOZIO"
						+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
						+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
						+ " and NEGOZIO.CodiceNegozio = ?"
						+ toQueryTitleOrLetters
						+ toQueryGenre
						+ toQueryPrice
						+ toQueryYear
						+ " order by FILM.CodiceFilm";
				statement = this.connection.prepareStatement(query);
				statement.setInt(1, codeShop);
			} else if (control == 2) {
				final String query = "select FILM.* from FILM, COPIA_FILM, NEGOZIO"
						+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
						+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
						+ " and NEGOZIO.CodiceNegozio = ?"
						+ toQueryTitleOrLetters
						+ toQueryGenre
						+ toQueryPrice
						+ toQueryYear
						+ " order by COPIA_FILM.NumeroVisualizzazioni desc";
				statement = this.connection.prepareStatement(query);
				statement.setInt(1, codeShop);
			} else if (control == 3) {
				final String query = "select FILM.* from FILM, COPIA_FILM, NEGOZIO"
						+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
						+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
						+ " and NEGOZIO.CodiceNegozio = ?"
						+ toQueryTitleOrLetters
						+ toQueryGenre
						+ toQueryPrice
						+ toQueryYear
						+ " order by COPIA_FILM.NumeroAcquisti desc";
				statement = this.connection.prepareStatement(query);
				statement.setInt(1, codeShop);
			} else if (control == 4) {
				final String query = "select FILM.* from FILM, COPIA_FILM, NEGOZIO"
						+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
						+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
						+ " and NEGOZIO.CodiceNegozio = ?"
						+ " and COPIA_FILM.DataInserimento >= ?"
						+ toQueryTitleOrLetters
						+ toQueryGenre
						+ toQueryPrice
						+ toQueryYear
						+ " order by COPIA_FILM.DataInserimento desc";
				statement = this.connection.prepareStatement(query);
				statement.setInt(1, codeShop);
				statement.setDate(2, new java.sql.Date(date.getTime()));
			}
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				films = new LinkedList<Film>();
				film = new Film();
				film.setCodeFilm(result.getInt("CodiceFilm"));
				film.setTitle(result.getString("Titolo"));
				film.setGenre(FilmType.valueOf(result.getString("Genere")));
				film.setPrice(result.getFloat("Prezzo"));
				film.setReleaseYear(result.getInt("AnnoUscita"));
				film.setPlot(result.getString("Trama"));
				films.add(film);
			}
			while (result.next()) {
				film = new Film();
				film.setCodeFilm(result.getInt("CodiceFilm"));
				film.setTitle(result.getString("Titolo"));
				film.setGenre(FilmType.valueOf(result.getString("Genere")));
				film.setPrice(result.getFloat("Prezzo"));
				film.setReleaseYear(result.getInt("AnnoUscita"));
				film.setPlot(result.getString("Trama"));
				films.add(film);
			}
			statement.close();
		}
		this.closeConnection();
		return films;

	}

	@Override
	public List<Film> getAllFilmsOfAnAdmin(final String usernameAmm) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select distinct FILM.* from FILM, COPIA_FILM, NEGOZIO"
				+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
				+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and NEGOZIO.Username_Amm = ?"
				+ " order by FILM.CodiceFilm";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, usernameAmm);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public List<Film> getAllFilmsOfAnAdminOrderedByInsDate(final String usernameAmm, final java.util.Date date) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select distinct FILM.*, max(COPIA_FILM.DataInserimento) as datamax from FILM, COPIA_FILM, NEGOZIO"
				+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
				+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and NEGOZIO.Username_Amm = ?"
				+ " and COPIA_FILM.DataInserimento >= ?"
				+ " group by FILM.CodiceFilm, FILM.Titolo, FILM.Genere, FILM.AnnoUscita, FILM.Prezzo, FILM.Trama"
				+ " order by datamax desc";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, usernameAmm);
		statement.setDate(2, new java.sql.Date(date.getTime()));
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public List<Film> getAllFilmsOfAnAdminOrderedByPurchases(final String usernameAmm) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select distinct FILM.*, sum(COPIA_FILM.NumeroAcquisti) as SommaDegliAcquisti from FILM, COPIA_FILM, NEGOZIO"
				+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
				+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and NEGOZIO.Username_Amm = ?"
				+ " group by FILM.AnnoUscita, FILM.CodiceFilm, FILM.Genere, FILM.Prezzo, FILM.Titolo, FILM.Trama"
				+ " order by SommaDegliAcquisti desc";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, usernameAmm);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public List<Film> getAllFilmsOfAnAdminOrderedByViews(final String usernameAmm) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select distinct FILM.*, sum(COPIA_FILM.NumeroVisualizzazioni) as SommaDelleViews from FILM, COPIA_FILM, NEGOZIO"
				+ " where FILM.CodiceFilm = COPIA_FILM.CodFilm"
				+ " and COPIA_FILM.CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and NEGOZIO.Username_Amm = ?"
				+ " group by FILM.AnnoUscita, FILM.CodiceFilm, FILM.Genere, FILM.Prezzo, FILM.Titolo, FILM.Trama"
				+ " order by SommaDelleViews desc";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, usernameAmm);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}
}
