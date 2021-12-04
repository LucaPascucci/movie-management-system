package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.ICustomerTable;
import mms.exception.AlreadyExistsException;
import mms.model.Customer;

/**
 * Table for the customer.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class CustomerTable extends AbstractTable implements ICustomerTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public CustomerTable() throws SQLException {
		super("CLIENTE");
	}

	@Override
	public void createTable() throws SQLException {

		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "Username varchar(30) NOT NULL,"
						+ "Nome VARCHAR(30) NOT NULL, " 
						+ "Cognome VARCHAR(30) NOT NULL, "
						+ "DataNascita DATE NOT NULL, "
						+ "Password VARCHAR(30) NOT NULL, "
						+ "CONSTRAINT IDCLIENTE_ID PRIMARY KEY (Username)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException, AlreadyExistsException {

		final Customer customer = (Customer) record;
		if (findByPrimaryKey(customer.getUsername()) != null) {
			throw new AlreadyExistsException();
		} 
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Username, Nome, Cognome, DataNascita, Password) values (?,?,?,?,?)";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setString(1, customer.getUsername());
		statement.setString(2, customer.getName());
		statement.setString(3, customer.getSurname());
		statement.setDate(4, new java.sql.Date(customer.getBirthDate().getTime())); //conversione da java.util.Date a java.sql.Date.
		statement.setString(5, customer.getPassword());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {

		final Customer customer = (Customer) record;
		final Customer oldCustomer = findByPrimaryKey(customer.getUsername());
		if (oldCustomer != null) {
			final String insert = "update " + this.tableName 
					+ " set Nome = ?, Cognome = ?, DataNascita = ?, Password = ? where Username = ?";
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, customer.getName());
			statement.setString(2, customer.getSurname());
			statement.setDate(3, new java.sql.Date(customer.getBirthDate().getTime())); //conversione da java.util.Date a java.sql.Date.
			statement.setString(4, customer.getPassword());
			statement.setString(5,  oldCustomer.getUsername());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {

		final Customer customer = (Customer) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + tableName + " where Username = ?";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setString(1, customer.getUsername());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Customer findByPrimaryKey(final String username) throws SQLException {

		Customer customer = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + tableName + " where Username = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			long secs;
			java.util.Date birthDate;
			customer = new Customer();
			customer.setUsername(result.getString("Username"));
			customer.setName(result.getString("Nome"));
			customer.setSurname(result.getString("Cognome"));
			secs = result.getDate("DataNascita").getTime();
			birthDate = new java.util.Date(secs);
			customer.setBirthDate(birthDate);
			customer.setPassword(result.getString("Password"));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return customer;
	}

	@Override
	public List<Customer> findUsersSubscribedToAShop(final Integer codeShop) throws SQLException {
		List<Customer> customers = null;
		Customer customer = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select CLIENTE.* from CLIENTE, ISCRITTO"
				+ " where ISCRITTO.Username = CLIENTE.Username"
				+ " and ISCRITTO.CodNegozio = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codeShop);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			customers = new LinkedList<Customer>();
			customer = new Customer();
			customer.setName(result.getString("Nome"));
			customer.setSurname(result.getString("Cognome"));
			customer.setUsername(result.getString("Username"));
			customer.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			customer.setPassword(result.getString("Password"));
			customers.add(customer);
		}
		while (result.next()) {
			customer = new Customer();
			customer.setName(result.getString("Nome"));
			customer.setSurname(result.getString("Cognome"));
			customer.setUsername(result.getString("Username"));
			customer.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			customer.setPassword(result.getString("Password"));
			customers.add(customer);
		}
		statement.close();
		this.closeConnection();
		return customers;
	}

	@Override
	public List<Customer> findUsersSubscribedToAdminShops(final String currAdmin) throws SQLException {
		List<Customer> customers = null;
		Customer customer = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select distinct CLIENTE.* from CLIENTE, ISCRITTO, NEGOZIO"
				+ " where NEGOZIO.CodiceNegozio = ISCRITTO.CodNegozio"
				+ " and ISCRITTO.Username = CLIENTE.Username"
				+ " and NEGOZIO.Username_Amm = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, currAdmin);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			customers = new LinkedList<Customer>();
			customer = new Customer();
			customer.setName(result.getString("Nome"));
			customer.setSurname(result.getString("Cognome"));
			customer.setUsername(result.getString("Username"));
			customer.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			customer.setPassword(result.getString("Password"));
			customers.add(customer);
		}
		while (result.next()) {
			customer = new Customer();
			customer.setName(result.getString("Nome"));
			customer.setSurname(result.getString("Cognome"));
			customer.setUsername(result.getString("Username"));
			customer.setBirthDate(new java.util.Date(result.getDate("DataNascita").getTime()));
			customer.setPassword(result.getString("Password"));
			customers.add(customer);
		}
		statement.close();
		this.closeConnection();
		return customers;
	}
}
