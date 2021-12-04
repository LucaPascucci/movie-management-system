package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mms.database.interfaces.ICardTable;
import mms.exception.AlreadyExistsException;
import mms.model.Card;

/**
 * Table for the card.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class CardTable extends AbstractTable implements ICardTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public CardTable() throws SQLException {
		super("TESSERA");
	}

	@Override
	public void createTable() throws SQLException {

		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ( " 
						+ "NumeroTessera INT IDENTITY NOT NULL, "
						+ "Username VARCHAR(30) NOT NULL, "
						+ "DataRilascio DATE NOT NULL, "
						+ "PunteggioTessera INT NOT NULL, "
						+ "CONSTRAINT IDTESSERA_SCONTO PRIMARY KEY (NumeroTessera),"
						+ "CONSTRAINT FKpossiede_ID UNIQUE (Username)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException, AlreadyExistsException {

		final Card card = (Card) record;
		if (findByUsername(card.getUsername()) != null) {
			throw new AlreadyExistsException();
		} 
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Username, DataRilascio, PunteggioTessera) values (?,?,?)";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setString(1, card.getUsername());
		statement.setDate(2, new java.sql.Date(card.getReleaseDate().getTime()));
		statement.setInt(3, card.getScoreCard());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {

		final Card card = (Card) record;
		final Card oldCar = findByPrimaryKey(card.getNumCard());
		if (oldCar != null) {
			final String insert = "update " + this.tableName 
					+ " set Username = ?, DataRilascio = ?, PunteggioTessera = ? where NumeroTessera = ?";
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, card.getUsername());
			statement.setDate(2, new java.sql.Date(card.getReleaseDate().getTime())); //conversione da java.util.Date a java.sql.Date.
			statement.setInt(3, card.getScoreCard());
			statement.setInt(4,  oldCar.getNumCard());
			statement.executeUpdate(); 
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {

		final Card card = (Card) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where NumeroTessera = ?";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, card.getNumCard());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Card findByPrimaryKey(final int code) throws SQLException  {
		Card card = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where NumeroTessera = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, code);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			long secs;
			java.util.Date birthDate;
			card = new Card();
			card.setNumCard(result.getInt("NumeroTessera"));
			card.setUsername(result.getString("Username"));
			secs = result.getDate("DataRilascio").getTime();
			birthDate = new java.util.Date(secs);
			card.setReleaseDate(birthDate);
			card.setScoreCard(result.getInt("PunteggioTessera"));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return card;
	}

	@Override
	public void alterTable() throws SQLException {

		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKpossiede_FK "
						+ "FOREIGN KEY (Username) "
						+ "REFERENCES CLIENTE "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public Card findByUsername(final String username) throws SQLException {
		Card card = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where Username = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			long secs;
			java.util.Date birthDate; 
			card = new Card();
			card.setNumCard(result.getInt("NumeroTessera"));
			card.setUsername(result.getString("Username"));
			secs = result.getDate("DataRilascio").getTime();
			birthDate = new java.util.Date(secs);
			card.setReleaseDate(birthDate);
			card.setScoreCard(result.getInt("PunteggioTessera"));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return card;
	}
}
