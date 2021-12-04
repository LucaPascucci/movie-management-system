package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mms.database.interfaces.IFilmCopyTable;
import mms.model.FilmCopy;

/**
 * Table for the film copy.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FilmCopyTable extends AbstractTable implements IFilmCopyTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public FilmCopyTable() throws SQLException {
		super("COPIA_FILM");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "CodNegozio INT NOT NULL,"
						+ "CodFilm INT NOT NULL, " 
						+ "DataInserimento DATE NOT NULL, "
						+ "NumeroAcquisti INT NOT NULL, " 
						+ "NumeroVisualizzazioni INT NOT NULL, "
						+ "CONSTRAINT IDRACCOLTA PRIMARY KEY (CodFilm, CodNegozio)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException {
		final FilmCopy filmCopy = (FilmCopy) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (CodNegozio, CodFilm, DataInserimento, NumeroAcquisti, NumeroVisualizzazioni) values (?,?,?,?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, filmCopy.getCodShop());
		statement.setInt(2, filmCopy.getCodFilm());
		statement.setDate(3, new java.sql.Date(filmCopy.getInsertionDate().getTime())); //conversione da java.util.Date a java.sql.Date.
		statement.setInt(4, filmCopy.getNumPurchases());
		statement.setInt(5, filmCopy.getNumViews());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		final FilmCopy filmCopy = (FilmCopy) record;
		final FilmCopy oldFilmCopy = findByPrimaryKey(filmCopy.getCodShop(), filmCopy.getCodFilm());
		if (oldFilmCopy != null) {
			final String insert = "update " + this.tableName 
					+ " set NumeroAcquisti = ?, NumeroVisualizzazioni = ?, DataInserimento = ? where CodNegozio = ? and CodFilm = ?";
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final PreparedStatement statement = this.connection.prepareStatement(insert);
			statement.setInt(1, filmCopy.getNumPurchases());
			statement.setInt(2, filmCopy.getNumViews());
			statement.setDate(3, new java.sql.Date(oldFilmCopy.getInsertionDate().getTime())); //conversione da java.util.Date a java.sql.Date.
			statement.setInt(4, oldFilmCopy.getCodShop());
			statement.setInt(5, oldFilmCopy.getCodFilm());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {
		final FilmCopy filmCopy = (FilmCopy) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodNegozio = ? and CodFilm = ?";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, filmCopy.getCodShop());
		statement.setInt(2,  filmCopy.getCodFilm());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public FilmCopy findByPrimaryKey(final int codShop, final int codFilm) throws SQLException  {

		FilmCopy filmCopy = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodNegozio = ? and CodFilm = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, codShop);
		statement.setInt(2, codFilm);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			long secs;
			java.util.Date birthDate;
			filmCopy = new FilmCopy();
			filmCopy.setCodShop(result.getInt("CodNegozio"));
			filmCopy.setCodFilm(result.getInt("CodFilm"));
			secs = result.getDate("DataInserimento").getTime();
			birthDate = new java.util.Date(secs);
			filmCopy.setInsertionDate(birthDate);
			filmCopy.setNumPurchases(result.getInt("NumeroAcquisti"));
			filmCopy.setNumViews(result.getInt("NumeroVisualizzazioni"));
		}
		return filmCopy;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		Statement statement = connection.createStatement();

		statement = connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKappartiene "
						+ "FOREIGN KEY (CodFilm) "
						+ "REFERENCES FILM "
						+ "ON DELETE CASCADE"
				);
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKcontiene "
						+ "FOREIGN KEY (CodNegozio) "
						+ "REFERENCES NEGOZIO "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}


}
