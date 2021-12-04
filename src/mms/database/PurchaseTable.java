package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IPurchaseTable;
import mms.exception.AlreadyExistsException;
import mms.model.Film;
import mms.model.FilmType;
import mms.model.Purchase;

/**
 * Table fot the purchase.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class PurchaseTable extends AbstractTable implements IPurchaseTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public PurchaseTable() throws SQLException {
		super("ACQUISTO");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + tableName + " ("
						+ "Username VARCHAR(30) NOT NULL,"
						+ "Acq_CodFilm INT NOT NULL, " 
						+ "Acq_CodNegozio INT NOT NULL, " 
						+ "CodiceTransazione INT IDENTITY NOT NULL, "
						+ "DataAcquisto DATE NOT NULL, "
						+ "PrezzoPagato FLOAT NOT NULL, "
						+ "CONSTRAINT IDACQUISTO PRIMARY KEY (Acq_CodFilm, Acq_CodNegozio, Username, CodiceTransazione)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException, AlreadyExistsException {

		final Purchase purchase = (Purchase) record;
		if (findByPrimaryKey(purchase.getCodMoviePurch(), purchase.getCodShopPurch(), purchase.getUsername()) != null) {
			throw new AlreadyExistsException();
		}
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Username, Acq_CodFilm, Acq_CodNegozio, DataAcquisto, PrezzoPagato) values (?,?,?,?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setString(1, purchase.getUsername());
		statement.setInt(2, purchase.getCodMoviePurch());
		statement.setInt(3, purchase.getCodShopPurch());
		statement.setDate(4, new java.sql.Date(purchase.getDatePurchase().getTime()));
		statement.setFloat(5, purchase.getPrice());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		final Purchase purchase = (Purchase) record;
		final Purchase oldPurchase = findByPrimaryKey(purchase.getCodMoviePurch(), purchase.getCodShopPurch(), purchase.getUsername());
		if (oldPurchase != null) {
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final String insert = "update " + tableName 
					+ " set DataAcquisto = ?, PrezzoPagato = ? where Username = ? and Acq_CodFilm = ? and Acq_CodNegozio = ?"
					+ " and CodiceTransazione = ?";
			final PreparedStatement statement = this.connection.prepareStatement(insert);
			statement.setDate(1, new java.sql.Date(purchase.getDatePurchase().getTime()));
			statement.setFloat(2, purchase.getPrice());
			statement.setString(3, purchase.getUsername());
			statement.setInt(4, purchase.getCodMoviePurch());
			statement.setInt(5, purchase.getCodShopPurch());
			statement.setInt(6, purchase.getCodTransaction());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {
		final Purchase purchase = (Purchase) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where Username = ? and Acq_CodFilm = ? and Acq_CodNegozio = ?"
				+ " and CodiceTransazione = ?";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setString(1, purchase.getUsername());
		statement.setInt(2, purchase.getCodMoviePurch());
		statement.setInt(3, purchase.getCodShopPurch());
		statement.setInt(4, purchase.getCodTransaction());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Purchase findByPrimaryKey(final int codMoviePurch, final int codShopPurch, final String username) throws SQLException {
		Purchase purchase = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where Username = ? and Acq_CodFilm = ? and Acq_CodNegozio = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, username);
		statement.setInt(2, codMoviePurch);
		statement.setInt(3, codShopPurch);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			long secs;
			java.util.Date datePurchase;
			purchase = new Purchase();
			purchase.setUsername(result.getString("Username"));
			purchase.setCodMoviePurch(result.getInt("Acq_CodFilm"));
			purchase.setCodShopPurch(result.getInt("Acq_CodNegozio"));
			purchase.setCodTransaction(result.getInt("CodiceTransazione"));
			secs = result.getDate("DataAcquisto").getTime();
			datePurchase = new java.util.Date(secs);
			purchase.setDatePurchase(datePurchase);
			purchase.setPrice(result.getFloat("PrezzoPagato"));
		}
		statement.close();
		this.closeConnection();
		return purchase;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKdi "
						+ "FOREIGN KEY (Acq_CodFilm, Acq_CodNegozio) "
						+ "REFERENCES COPIA_FILM "
						+ "ON DELETE CASCADE"
				);
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKeffettua "
						+ "FOREIGN KEY (Username) "
						+ "REFERENCES CLIENTE "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public List<Film> getPurchased(final String currentUser) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select FILM.*, ACQUISTO.PrezzoPagato from  FILM, ACQUISTO"
				+ " where FILM.CodiceFilm = ACQUISTO.Acq_CodFilm"
				+ " and ACQUISTO.Username = ?"
				+ " order by FILM.CodiceFilm";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, currentUser);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			films = new LinkedList<Film>();
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("PrezzoPagato"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("PrezzoPagato"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public List<Film> getPurchasedOfACustomerInAShop(final Integer currShop, final String currentUser) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select FILM.*, ACQUISTO.PrezzoPagato from  FILM, ACQUISTO"
				+ " where FILM.CodiceFilm = ACQUISTO.Acq_CodFilm"
				+ " and ACQUISTO.Username = ?"
				+ " and ACQUISTO.Acq_CodNegozio = ?"
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
			film.setPrice(result.getFloat("PrezzoPagato"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		while (result.next()) {
			film = new Film();
			film.setCodeFilm(result.getInt("CodiceFilm"));
			film.setTitle(result.getString("Titolo"));
			film.setGenre(FilmType.valueOf(result.getString("Genere")));
			film.setPrice(result.getFloat("PrezzoPagato"));
			film.setReleaseYear(result.getInt("AnnoUscita"));
			film.setPlot(result.getString("Trama"));
			films.add(film);
		}
		statement.close();
		this.closeConnection();
		return films;
	}

	@Override
	public List<Film> getPurchasedInAdminShop(final String currentUser, final String customerID) throws SQLException {
		List<Film> films = null;
		Film film = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select FILM.* from  FILM, ACQUISTO, NEGOZIO"
				+ " where FILM.CodiceFilm = ACQUISTO.Acq_CodFilm"
				+ " and ACQUISTO.Acq_CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and ACQUISTO.Username = ?"
				+ " and NEGOZIO.Username_Amm = ?"
				+ " order by FILM.CodiceFilm";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, customerID);
		statement.setString(2, currentUser);
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
	public float getTotalIncomesOfAnAdmin(final String username) throws SQLException {
		float incomes = 0;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select sum(ACQUISTO.PrezzoPagato) from  ACQUISTO, NEGOZIO"
				+ " where ACQUISTO.Acq_CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and NEGOZIO.Username_Amm = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			incomes = result.getFloat(1);
		}
		statement.close();
		this.closeConnection();
		return incomes;
	}

	@Override
	public float getTotalIncomesOfAShop(final Integer currShop) throws SQLException {
		float incomes = 0;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select sum(ACQUISTO.PrezzoPagato) from  ACQUISTO"
				+ " where ACQUISTO.Acq_CodNegozio = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, currShop);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			incomes = result.getFloat(1);
		}
		statement.close();
		this.closeConnection();
		return incomes;
	}

	@Override
	public float getPurchasedInAShop(final Integer currShop, final String currentUser) throws SQLException {
		float incomes = 0;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		if (currShop == null) {
			final String query = "select sum(ACQUISTO.PrezzoPagato) from  ACQUISTO"
					+ " where ACQUISTO.Username = ?";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, currentUser);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				incomes = result.getFloat(1);
			}
			statement.close();
			this.closeConnection();
			return incomes;
		} else {
			final String query = "select sum(ACQUISTO.PrezzoPagato) from  ACQUISTO"
					+ " where ACQUISTO.Username = ?"
					+ " and ACQUISTO.Acq_CodNegozio = ?";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, currentUser);
			statement.setInt(2, currShop);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				incomes = result.getFloat(1);
			}
			statement.close();
			this.closeConnection();
			return incomes;
		}
	}

	@Override
	public float getPurchasedInAShopOfTheAdmin(final Integer currShop, final String customerID, final String admin) throws SQLException {
		float incomes = 0;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		if (currShop == null) {
			final String query = "select sum(ACQUISTO.PrezzoPagato) from  ACQUISTO, NEGOZIO"
					+ " where ACQUISTO.Acq_CodNegozio = NEGOZIO.CodiceNegozio"
					+ " and ACQUISTO.Username = ?"
					+ " and NEGOZIO.Username_Amm = ?";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, customerID);
			statement.setString(2, admin);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				incomes = result.getFloat(1);
			}
			statement.close();
			this.closeConnection();
			return incomes;
		} else {
			final String query = "select sum(ACQUISTO.PrezzoPagato) from  ACQUISTO"
					+ " where ACQUISTO.Username = ?"
					+ " and ACQUISTO.Acq_CodNegozio = ?";
			final PreparedStatement statement = this.connection.prepareStatement(query);
			statement.setString(1, customerID);
			statement.setInt(2, currShop);
			final ResultSet result = statement.executeQuery();
			if (result.next()) {
				incomes = result.getFloat(1);
			}
			statement.close();
			this.closeConnection();
			return incomes;
		}
	}
}
