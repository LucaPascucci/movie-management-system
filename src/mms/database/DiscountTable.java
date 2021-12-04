package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IDiscountTable;
import mms.model.Discount;

/**
 * Table for the discount.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class DiscountTable extends AbstractTable implements IDiscountTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public DiscountTable() throws SQLException {
		super("SCONTO");
	}

	@Override
	public void createTable() throws SQLException {

		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ( " 
						+ "CodiceSconto INT IDENTITY NOT NULL, "
						+ "Percentuale INT NOT NULL, "
						+ "Durata INT NOT NULL, "
						+ "PunteggioSconto INT NOT NULL, "
						+ "constraint IDSCONTO primary key (CodiceSconto)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException {

		final Discount discount = (Discount) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Percentuale, Durata, PunteggioSconto) values (?,?,?)";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, discount.getPercentage());
		statement.setInt(2, discount.getPeriod());
		statement.setInt(3, discount.getDiscountScore());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {

		final Discount discount = (Discount) record;
		final Discount oldDiscount = findByPrimaryKey(discount.getDiscountCode());
		if (oldDiscount != null) {
			final String insert = "update " + this.tableName 
					+ " set Percentuale = ?, Durata = ?, PunteggioSconto = ? where CodiceSconto = ?";
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final PreparedStatement statement = connection.prepareStatement(insert);
			statement.setInt(1, discount.getPercentage());
			statement.setInt(2, discount.getPeriod()); //conversione da java.util.Date a java.sql.Date.
			statement.setInt(3, discount.getDiscountScore());
			statement.setInt(4,  discount.getDiscountCode());
			statement.executeUpdate(); 
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {

		final Discount discount = (Discount) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodiceSconto = ?";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, discount.getDiscountCode());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Discount findByPrimaryKey(final int code) throws SQLException {

		Discount discount = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodiceSconto = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, code);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			discount = new Discount();
			discount.setDiscountCode(result.getInt("CodiceSconto"));
			discount.setPercentage(result.getInt("Percentuale"));
			discount.setPeriod(result.getInt("Durata"));
			discount.setDiscountScore(result.getInt("PunteggioSconto"));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return discount;
	}

	@Override
	public List<Discount> findAll() throws SQLException {
		List<Discount> discounts = null;
		Discount discount = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " order by CodiceSconto";
		final PreparedStatement statement = connection.prepareStatement(query);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			discounts = new LinkedList<Discount>();
			discount = new Discount();
			discount.setDiscountCode(result.getInt("CodiceSconto"));
			discount.setPercentage(result.getInt("Percentuale"));
			discount.setPeriod(result.getInt("Durata"));
			discount.setDiscountScore(result.getInt("PunteggioSconto"));
			discounts.add(discount);
		}
		while (result.next()) {
			discount = new Discount();
			discount.setDiscountCode(result.getInt("CodiceSconto"));
			discount.setPercentage(result.getInt("Percentuale"));
			discount.setPeriod(result.getInt("Durata"));
			discount.setDiscountScore(result.getInt("PunteggioSconto"));
			discounts.add(discount);
		}
		statement.close();
		result.close();
		this.closeConnection();
		return discounts;
	}
}
