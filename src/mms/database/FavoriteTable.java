package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IFavoriteTable;
import mms.exception.AlreadyExistsException;
import mms.model.Favorite;
import mms.model.Film;
import mms.model.FilmType;

/**
 * Table for the favorite.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FavoriteTable extends AbstractTable implements IFavoriteTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public FavoriteTable() throws SQLException {
		super("PREFERITO");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + tableName + " ("
						+ "CodFilm INT NOT NULL,"
						+ "Username VARCHAR(30) NOT NULL, " 
						+ "CONSTRAINT IDPREFERITO PRIMARY KEY (CodFilm, Username)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException,	AlreadyExistsException {
		final Favorite favorite = (Favorite) record;
		if (findByPrimaryKey(favorite.getCodFilm(), favorite.getUsername()) != null) {
			throw new AlreadyExistsException();
		}
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (CodFilm, Username) values (?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, favorite.getCodFilm());
		statement.setString(2, favorite.getUsername());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		//this class not support the update
	}

	@Override
	public void delete(final Object record) throws SQLException {
		final Favorite favorite = (Favorite) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodFilm = ? and Username = ?";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, favorite.getCodFilm());
		statement.setString(2, favorite.getUsername());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Favorite findByPrimaryKey(final int code, final String username) throws SQLException  {
		Favorite favorite = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodFilm = ? and Username = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, code);
		statement.setString(2, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			favorite = new Favorite();
			favorite.setCodFilm(result.getInt("CodFilm"));
			favorite.setUsername(result.getString("Username"));
		}
		statement.close();
		this.closeConnection();
		return favorite;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKpre_CLIENTE "
						+ "FOREIGN KEY (Username) "
						+ "REFERENCES CLIENTE "
						+ "ON DELETE CASCADE"
				);
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKpre_FILM "
						+ "FOREIGN KEY (CodFilm) "
						+ "REFERENCES FILM "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public List<Film> getFavorite(final String customer) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select FILM.* from FILM, PREFERITO"
				+ " where FILM.CodiceFilm = PREFERITO.CodFilm"
				+ " and PREFERITO.Username = ?"
				+ " order by FILM.CodiceFilm";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, customer);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getInt("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getInt("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public List<Film> getFavoriteOfACustomerInAShop(final Integer currShop, final String currentUser) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select FILM.* from  COPIA_FILM, FILM, PREFERITO"
				+ " where COPIA_FILM.CodFilm = FILM.CodiceFilm"
				+ " and FILM.CodiceFilm = PREFERITO.CodFilm"
				+ " and PREFERITO.Username = ?"
				+ " and COPIA_FILM.CodNegozio = ?"
				+ " order by FILM.CodiceFilm";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, currentUser);
		statement.setInt(2, currShop);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getInt("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getInt("Prezzo"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}
}
