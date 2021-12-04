package mms.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The class that manage the database connection.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class DBConnection {

	/**
	 *  This is the name of the DATABASE.
	 */
	public static final String DATABASENAME = "MMS_DATABASE";

	/** The Constant CREATING_DATABASE. */
	private static final String CREATING_DATABASE = "Creating DATABASE: " + DATABASENAME;

	/** The Constant DROPPING_DATABASE. */
	private static final String DROPPING_DATABASE = "Dropping DATABASE: " + DATABASENAME;

	/**
	 * Establishes a connection to the database.
	 * @param dbName the database name
	 * @return the connection
	 * @throws SQLException SQLException
	 */
	public Connection getMsSQLConnection(final String dbName) throws SQLException {  

		String dbUri;
		if (dbName == null) {
			dbUri = "jdbc:sqlserver://localhost;integratedSecurity=true";
		} else {
			dbUri = "jdbc:sqlserver://localhost;" + "databaseName=" + DATABASENAME + ";integratedSecurity=true";
		}          
		Connection connection = null;
		connection = DriverManager.getConnection(dbUri);
		return connection;
	}

	/**
	 * Creates the database.
	 * @throws SQLException SQLException
	 */
	public void createDataBase() throws SQLException {
		System.out.println("Operation: " + CREATING_DATABASE);
		final Connection connection = this.getMsSQLConnection(null);
		final Statement statement = connection.createStatement();
		if (!this.checkDB()) {
			statement.executeUpdate("IF	NOT EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'" + DATABASENAME + "') "
					+ "CREATE DATABASE [" + DATABASENAME + "]");
			new ActorTable().createTable();
			new ActTable().createTable();
			new AdministratorTable().createTable();
			new CardTable().createTable();
			new CustomerTable().createTable();
			new DiscountTable().createTable();
			new EvaluationTable().createTable();
			new FavoriteTable().createTable();
			new FileFilmTable().createTable();
			new FilmCopyTable().createTable();
			new FilmTable().createTable();
			new PurchaseTable().createTable();
			new RegisteredTable().createTable();
			new ReviewTable().createTable();
			new ShopTable().createTable();
			new TypologyTable().createTable();

			new ActTable().alterTable();
			new CardTable().alterTable();
			new EvaluationTable().alterTable();
			new FavoriteTable().alterTable();
			new FileFilmTable().alterTable();
			new FilmCopyTable().alterTable();
			new PurchaseTable().alterTable();
			new RegisteredTable().alterTable();
			new ReviewTable().alterTable();
			new ShopTable().alterTable();
			new TypologyTable().alterTable();
		} else {
			throw new SQLException();
		}
		connection.close();
	}

	/**
	 * Drops the database.
	 * @throws SQLException SQLException
	 */
	public void dropDataBase() throws SQLException {
		System.out.println("Operation: " + DROPPING_DATABASE);
		final Connection connection = this.getMsSQLConnection(null);
		final Statement statement = connection.createStatement();
		if (this.checkDB()) {
			statement.executeUpdate("IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'" + DATABASENAME + "') "
					+ "DROP DATABASE [" + DATABASENAME + "]");
		} else {
			throw new SQLException();
		}
		connection.close();
	}

	/**
	 * Checks if the database exists.
	 * @return boolean
	 * @throws SQLException SQLException
	 */
	public boolean checkDB() throws SQLException {
		boolean exists = false;
		final Connection connection = this.getMsSQLConnection(null);
		final ResultSet set = connection.getMetaData().getCatalogs();
		while (set.next()) {
			final String databaseName = set.getString(1);
			if (databaseName.equals(DATABASENAME)) {
				exists = true;
			}
		}
		return exists;
	}
}