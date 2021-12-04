package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IActorTable;
import mms.model.Actor;

/**
 * Table for the actor.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ActorTable extends AbstractTable implements IActorTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public ActorTable() throws SQLException {
		super("ATTORE");
	}

	@Override
	public void createTable() throws SQLException {

		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "CodiceAttore INT IDENTITY NOT NULL,"
						+ "Nome VARCHAR(30) NOT NULL, " 
						+ "Cognome VARCHAR(30) NOT NULL, "
						+ "DataNascita DATE NOT NULL, " 
						+ "Nazionalità VARCHAR(30) NOT NULL, "
						+ "CONSTRAINT IDATTORE PRIMARY KEY (CodiceAttore)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException {

		final Actor actor = (Actor) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Nome, Cognome, DataNascita, Nazionalità) values (?,?,?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setString(1, actor.getName());
		statement.setString(2, actor.getSurname());
		statement.setDate(3, new java.sql.Date(actor.getBirthDate().getTime())); //conversione da java.util.Date a java.sql.Date.
		statement.setString(4, actor.getNationality());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {

		final Actor actor = (Actor) record;
		final Actor oldActor = findByPrimaryKey(actor.getCodActor());
		if (oldActor != null) {
			final String insert = "update " + this.tableName
					+ " set Nome = ?, Cognome = ?, DataNascita = ?, Nazionalità = ? where CodiceAttore = ?";
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final PreparedStatement statement = this.connection.prepareStatement(insert);
			statement.setString(1, actor.getName());
			statement.setString(2, actor.getSurname());
			statement.setDate(3, new java.sql.Date(actor.getBirthDate().getTime())); //conversione da java.util.Date a java.sql.Date.
			statement.setString(4, actor.getNationality());
			statement.setInt(5,  oldActor.getCodActor());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}

	}

	@Override
	public void delete(final Object record) throws SQLException {
		final Actor actor = (Actor) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodiceAttore = ?";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, actor.getCodActor());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Actor findByPrimaryKey(final int code) throws SQLException {
		Actor actor = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where codiceAttore = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, code);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			actor = new Actor();
			actor.setCodActor(result.getInt("CodiceAttore"));
			actor.setName(result.getString("Nome"));
			actor.setSurname(result.getString("Cognome"));
			actor.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			actor.setNationality(result.getString("Nazionalità"));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return actor;
	}

	@Override
	public List<Actor> findAll() throws SQLException {
		List<Actor> actors = null;
		Actor actor = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " order by CodiceAttore";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			actors = new LinkedList<Actor>();
			actor = new Actor();
			actor.setCodActor(result.getInt("CodiceAttore"));
			actor.setName(result.getString("Nome"));
			actor.setSurname(result.getString("Cognome"));
			actor.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			actor.setNationality(result.getString("Nazionalità"));
			actors.add(actor);
		}
		while (result.next()) {
			actor = new Actor();
			actor.setCodActor(result.getInt("CodiceAttore"));
			actor.setName(result.getString("Nome"));
			actor.setSurname(result.getString("Cognome"));
			actor.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			actor.setNationality(result.getString("Nazionalità"));
			actors.add(actor);
		}
		result.close();
		statement.close();
		this.closeConnection();
		return actors;
	}

	@Override
	public List<Actor> getActorsStarringInAFilm(final int codeFilm) throws SQLException {
		List<Actor> actors = null;
		Actor actor = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select ATTORE.* from " + this.tableName + ", RECITA"
				+ " where ATTORE.CodiceAttore = RECITA.CodAttore"
				+ " and CodFilm = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codeFilm);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			actors = new LinkedList<Actor>();
			actor = new Actor();
			actor.setCodActor(result.getInt("CodiceAttore"));
			actor.setName(result.getString("Nome"));
			actor.setSurname(result.getString("Cognome"));
			actor.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			actor.setNationality(result.getString("Nazionalità"));
			actors.add(actor);
		}
		while (result.next()) {
			actor = new Actor();
			actor.setCodActor(result.getInt("CodiceAttore"));
			actor.setName(result.getString("Nome"));
			actor.setSurname(result.getString("Cognome"));
			actor.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			actor.setNationality(result.getString("Nazionalità"));
			actors.add(actor);
		}
		result.close();
		statement.close();
		this.closeConnection();
		return actors;
	}
}
