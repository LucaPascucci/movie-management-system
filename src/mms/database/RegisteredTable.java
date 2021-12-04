package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IRegisteredTable;
import mms.model.Registered;

/**
 * Table for the registration.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class RegisteredTable extends AbstractTable implements IRegisteredTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public RegisteredTable() throws SQLException {
		super("ISCRITTO");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "CodNegozio INT NOT NULL,"
						+ "Username VARCHAR(30) NOT NULL, "
						+ "DataIscrizione DATE NOT NULL, " 
						+ "CONSTRAINT IDISCRITTO PRIMARY KEY (CodNegozio, Username)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException {
		final Registered registered = (Registered) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (CodNegozio, Username, DataIscrizione) values (?,?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, registered.getCodShop());
		statement.setString(2, registered.getUsername());
		statement.setDate(3, new java.sql.Date(registered.getRegisteredDate().getTime())); //conversione da java.util.Date a java.sql.Date.
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		final Registered registered = (Registered) record;
		final Registered oldRegistered = findByPrimaryKey(registered.getCodShop(), registered.getUsername());
		if (oldRegistered != null) {
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final String insert = "update " + this.tableName + " set DataIscrizione = ? where CodNegozio = ? and Username = ?";
			final PreparedStatement statement = this.connection.prepareStatement(insert);
			statement.setDate(1, new java.sql.Date(registered.getRegisteredDate().getTime())); //conversione da java.util.Date a java.sql.Date.
			statement.setInt(2, registered.getCodShop());
			statement.setString(3, registered.getUsername());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {
		final Registered registered = (Registered) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodiceAttore = ? and Username = ?";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, registered.getCodShop());
		statement.setString(2, registered.getUsername());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Registered findByPrimaryKey(final int codeShop, final String username) throws SQLException {

		Registered registered = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodNegozio = ? and Username = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codeShop);
		statement.setString(2, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			registered = new Registered();
			registered.setCodShop(result.getInt("CodNegozio"));
			registered.setUsername(result.getString("Username"));
			registered.setRegisteredDate(new java.util.Date(result.getDate("DataIscrizione").getTime()));
		}
		statement.close();
		this.closeConnection();
		return registered;
	}

	@Override
	public List<Integer> findShopByUsername(final String username) throws SQLException {
		List<Integer> codeShops = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select CodNegozio from " + this.tableName + " where Username = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			codeShops = new LinkedList<>();
			codeShops.add(result.getInt("CodNegozio"));
		}
		while (result.next()) {
			codeShops.add(result.getInt("CodNegozio"));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return codeShops;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKisc_CLI "
						+ "FOREIGN KEY (Username) "
						+ "REFERENCES CLIENTE "
						+ "ON DELETE CASCADE"
				);
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKisc_NEG "
						+ "FOREIGN KEY (CodNegozio) "
						+ "REFERENCES NEGOZIO "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

}
