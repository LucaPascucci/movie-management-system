package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IShopTable;
import mms.model.Shop;
/**
 * Table for the Shop.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ShopTable extends AbstractTable implements IShopTable {
	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public ShopTable() throws SQLException {
		super("NEGOZIO");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "CodiceNegozio INT IDENTITY NOT NULL,"
						+ "Indirizzo VARCHAR(100) NOT NULL, " 
						+ "Telefono VARCHAR(20) NOT NULL, "
						+ "Username_Amm VARCHAR(30) NOT NULL, "
						+ "CONSTRAINT IDNEGOZIO PRIMARY KEY (CodiceNegozio)"
						+ ") "
				);
		statement.close();
		this.closeConnection();

	}

	@Override
	public void persist(final Object record) throws SQLException {
		final Shop shop = (Shop) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Indirizzo, Telefono, Username_Amm) values (?,?,?)";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setString(1, shop.getAddress());
		statement.setString(2, shop.getTelephone());
		statement.setString(3, shop.getUsernameAdmin());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		final Shop shop = (Shop) record;
		final Shop oldShop = findByPrimaryKey(shop.getCodeShop());
		if (oldShop != null) {
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final String insert = "update " + this.tableName + " set Indirizzo = ?, Telefono = ?, Username_Amm = ? where CodiceNegozio = ?";
			PreparedStatement statement = this.connection.prepareStatement(insert);
			statement = connection.prepareStatement(insert);
			statement.setString(1, shop.getAddress());
			statement.setString(2, shop.getTelephone());
			statement.setString(3, shop.getUsernameAdmin());
			statement.setInt(4,  oldShop.getCodeShop());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}

	}

	@Override
	public void delete(final Object record) throws SQLException {
		final Shop shop = (Shop) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodiceNegozio = ?";
		PreparedStatement statement = this.connection.prepareStatement(insert);
		statement = connection.prepareStatement(insert);
		statement.setInt(1, shop.getCodeShop());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public List<Shop> findAll() throws SQLException {
		List<Shop> shops = null;
		Shop shop = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " order by CodiceNegozio";
		final PreparedStatement statement = connection.prepareStatement(query);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			shops = new LinkedList<Shop>();
			shop = new Shop();
			shop.setCodeShop(result.getInt("CodiceNegozio"));
			shop.setAddress(result.getString("Indirizzo"));
			shop.setTelephone(result.getString("Telefono"));
			shop.setUsernameAdmin(result.getString("Username_Amm"));
			shops.add(shop);
		}
		while (result.next()) {
			shop = new Shop();
			shop.setCodeShop(result.getInt("CodiceNegozio"));
			shop.setAddress(result.getString("Indirizzo"));
			shop.setTelephone(result.getString("Telefono"));
			shop.setUsernameAdmin(result.getString("Username_Amm"));
			shops.add(shop);
		}
		statement.close();
		result.close();
		this.closeConnection();
		return shops;
	}

	@Override
	public Shop findByPrimaryKey(final int code)  throws SQLException {
		Shop shop = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodiceNegozio = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, code);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			shop = new Shop();
			shop.setCodeShop(result.getInt("CodiceNegozio"));
			shop.setAddress(result.getString("Indirizzo"));
			shop.setTelephone(result.getString("Telefono"));
			shop.setUsernameAdmin(result.getString("Username_Amm"));
		}
		statement.close();
		this.closeConnection();
		return shop;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKgestisce "
						+ "FOREIGN KEY (Username_Amm) "
						+ "REFERENCES AMMINISTRATORE "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public List<Integer> getShopCodesOfAnAdmin(final String username) throws SQLException {
		List<Integer> codes = null;
		Integer code = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select NEGOZIO.CodiceNegozio from NEGOZIO"
				+ " where NEGOZIO.Username_Amm  = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			codes = new LinkedList<Integer>();
			code = result.getInt("CodiceNegozio");
			codes.add(code);
		}
		while (result.next()) {
			code = result.getInt("CodiceNegozio");
			codes.add(code);
		}
		return codes;
	}

	@Override
	public List<Integer> getShopCodesOfACustomer(final String username) throws SQLException {
		List<Integer> codes = null;
		Integer code = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select ISCRITTO.CodNegozio from ISCRITTO"
				+ " where ISCRITTO.Username = ?"
				+ " order by ISCRITTO.CodNegozio";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, username);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			codes = new LinkedList<Integer>();
			code = result.getInt("CodNegozio");
			codes.add(code);
		}
		while (result.next()) {
			code = result.getInt("CodNegozio");
			codes.add(code);
		}
		return codes;
	}

	@Override
	public int getMaxShopCode() throws SQLException {
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select MAX(CodiceNegozio) as MaxCodiceNegozio from " + this.tableName;
		final PreparedStatement statement = connection.prepareStatement(query);
		final ResultSet result = statement.executeQuery();
		result.next();
		final int code = result.getInt("MaxCodiceNegozio");
		statement.close();
		this.closeConnection();
		return code;
	}

	@Override
	public List<Integer> getShopCodesOfACustomerBelonginToAnAdmin(final String customer, final String admin) throws SQLException {
		List<Integer> codes = null;
		Integer code = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select NEGOZIO.CodiceNegozio from ISCRITTO, NEGOZIO"
				+ " where ISCRITTO.CodNegozio = NEGOZIO.CodiceNegozio"
				+ " and ISCRITTO.Username = ?"
				+ " and NEGOZIO.Username_Amm  = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, customer);
		statement.setString(2, admin);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			codes = new LinkedList<Integer>();
			code = result.getInt("CodiceNegozio");
			codes.add(code);
		}
		while (result.next()) {
			code = result.getInt("CodiceNegozio");
			codes.add(code);
		}
		return codes;
	}
}
