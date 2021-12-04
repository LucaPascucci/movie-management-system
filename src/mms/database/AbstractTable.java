package mms.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import mms.exception.AlreadyExistsException;

/**
 * Class for a generic database table.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public abstract class AbstractTable {

	/** The Constant CREATING_TABLE. */
	protected static final String CREATING_TABLE = "Creating";

	/** The Constant DROPPING_TABLE. */
	protected static final String DROPPING_TABLE = "Dropping";

	/** The Constant ALTERING_TABLE. */
	protected static final String ALTERING_TABLE = "Altering";

	/** The Constant SAVING_RECORD. */
	protected static final String SAVING_RECORD = "Saving record in";

	/** The Constant UPDATING_RECORD. */
	protected static final String UPDATING_RECORD = "Updating record in";

	/** The Constant DELETING_RECORD. */
	protected static final String DELETING_RECORD = "Deleting record in";

	/** The Constant FINDING_RECORD. */
	protected static final String FINDING_RECORD = "Finding record in";

	/** The Constant TABLE. */
	protected static final String TABLE = "table";

	/** The data source. */
	protected final DBConnection dataSource;

	/** The connection. */
	protected Connection connection;

	/** The table name. */
	protected final String tableName;

	/**
	 * Constructor for a generic table.
	 * @param realTableName the table name
	 * @throws SQLException SQLException
	 */
	protected AbstractTable(final String realTableName) throws SQLException {
		this.dataSource = new DBConnection();
		this.tableName = realTableName;
		this.connection = this.dataSource.getMsSQLConnection(DBConnection.DATABASENAME);
		this.closeConnection();
	}

	/**
	 * Creates the table.
	 * @throws SQLException SQLException
	 */
	public abstract void createTable() throws SQLException;

	/**
	 * Saves the record in the table.
	 * @param record the record
	 * @throws SQLException SQLException
	 * @throws AlreadyExistsException if the record already exists.
	 */
	public abstract void persist(final Object record) throws SQLException, AlreadyExistsException;

	/**
	 * Updates the record in the table.
	 * @param record the record.
	 * @throws SQLException SQLException
	 */
	public abstract void update(final Object record) throws SQLException;

	/**
	 * Deletes the record from the table.
	 * @param record the record
	 * @throws SQLException SQLException
	 */
	public abstract void delete(final Object record) throws SQLException;

	/**
	 * Drops the table.
	 * @throws SQLException SQLException
	 */
	protected void dropTable() throws SQLException {
		this.startConnection(DROPPING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate("DROP TABLE " + this.tableName);
		statement.close();
		this.closeConnection();
	}

	/**
	 * Opens a connection to the database.
	 * @param operation the operation
	 * @throws SQLException SQLException
	 */
	protected void startConnection(final String operation) throws SQLException {
		System.out.println("Operation: " + operation + "\n");
		if (this.connection.isClosed()) {
			this.connection = this.dataSource.getMsSQLConnection(DBConnection.DATABASENAME);
		}
	}

	/**
	 * Closes the connection to the database.
	 * @throws SQLException SQLExceptionS
	 */
	protected void closeConnection() throws SQLException {
		if (!this.connection.isClosed()) {
			this.connection.close();
		}
	}

}
