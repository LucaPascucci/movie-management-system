package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IAdministratorTable;
import mms.exception.AlreadyExistsException;
import mms.model.Administrator;

/**
 * Table for the administrator.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class AdministratorTable extends AbstractTable implements IAdministratorTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public AdministratorTable() throws SQLException {
		super("AMMINISTRATORE");
	}

	@Override
	public void createTable() throws SQLException {

		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "Username VARCHAR(30) NOT NULL,"
						+ "Nome VARCHAR(30) NOT NULL, " 
						+ "Cognome VARCHAR(30) NOT NULL, "
						+ "DataNascita DATE NOT NULL, " 
						+ "Password varchar(30) not null, "
						+ "CONSTRAINT IDAMMINISTRATORE PRIMARY KEY (Username)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException, AlreadyExistsException {

		final Administrator administrator = (Administrator) record;
		if (findByPrimaryKey(administrator.getUsername()) != null) {
			throw new AlreadyExistsException();
		}
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Username, Nome, Cognome, DataNascita, Password) values (?,?,?,?,?)";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setString(1, administrator.getUsername());
		statement.setString(2, administrator.getName());
		statement.setString(3, administrator.getSurname());
		statement.setDate(4, new java.sql.Date(administrator.getBirthDate().getTime())); //conversione da java.util.Date a java.sql.Date.
		statement.setString(5, administrator.getPassword());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {

		final Administrator administrator = (Administrator) record;
		final Administrator oldAdministrator = findByPrimaryKey(administrator.getUsername());
		if (oldAdministrator != null) {
			final String insert = "update " + this.tableName 
					+ " set Nome = ?, Cognome = ?, DataNascita = ?, Password = ? where Username = ?";
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, administrator.getName());
			statement.setString(2, administrator.getSurname());
			statement.setDate(3, new java.sql.Date(administrator.getBirthDate().getTime())); //conversione da java.util.Date a java.sql.Date.
			statement.setString(4, administrator.getPassword());
			statement.setString(5, oldAdministrator.getUsername());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {

		final Administrator administrator = (Administrator) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where Username = ?";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setString(1, administrator.getUsername());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Administrator findByPrimaryKey(final String username) throws SQLException {

		Administrator administrator = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where Username = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			long secs;
			java.util.Date birthDate; 
			administrator = new Administrator();
			administrator.setUsername(result.getString("Username"));
			administrator.setName(result.getString("Nome"));
			administrator.setSurname(result.getString("Cognome"));
			secs = result.getDate("DataNascita").getTime();
			birthDate = new java.util.Date(secs);
			administrator.setBirthDate(birthDate);
			administrator.setPassword(result.getString("Password"));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return administrator;
	}

	@Override
	public List<Administrator> findAll() throws SQLException {
		List<Administrator> administrators = null;
		Administrator administrator = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " order by Username";
		final PreparedStatement statement = connection.prepareStatement(query);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			administrators = new LinkedList<Administrator>();
			administrator = new Administrator();
			administrator.setUsername(result.getString("Username"));
			administrator.setName(result.getString("Nome"));
			administrator.setSurname(result.getString("Cognome"));
			administrator.setPassword(result.getString("Password"));
			administrator.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			administrators.add(administrator);
		}
		while (result.next()) {
			administrator = new Administrator();
			administrator.setUsername(result.getString("Username"));
			administrator.setName(result.getString("Nome"));
			administrator.setSurname(result.getString("Cognome"));
			administrator.setPassword(result.getString("Password"));
			administrator.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			administrators.add(administrator);
		}
		statement.close();
		result.close();
		this.closeConnection();
		return administrators;
	} 

}
