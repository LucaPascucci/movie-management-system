package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IActTable;
import mms.model.Act;

/**
 * Table for the act.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ActTable extends AbstractTable implements IActTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public ActTable() throws SQLException {
		super("RECITA");
	}

	@Override
	public void createTable() throws SQLException {

		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "CodFilm INT NOT NULL,"
						+ "CodAttore INT NOT NULL, " 
						+ "CONSTRAINT IDRECITA PRIMARY KEY (CodFilm, CodAttore)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException {

		final Act act = (Act) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (CodFilm, CodAttore) values (?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, act.getCodFilm());
		statement.setInt(2, act.getCodActor());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException { }

	@Override
	public void delete(final Object record) throws SQLException {

		final Act act = (Act) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodFilm = ? and CodAttore = ?";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, act.getCodFilm());
		statement.setInt(2, act.getCodActor());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Act findByPrimaryKey(final int codFilm, final int codActor) throws SQLException {

		Act act = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where codFilm = ? and codAttore = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, codFilm);
		statement.setInt(2, codActor);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			act = new Act();
			act.setCodFilm(result.getInt("CodFilm"));
			act.setCodActor(result.getInt("CodAttore"));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return act;
	}

	@Override
	public void alterTable() throws SQLException {

		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKrec_ATT "
						+ "FOREIGN KEY (CodAttore) "
						+ "REFERENCES ATTORE "
						+ "ON DELETE CASCADE"
				);
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKrec_FIL "
						+ "FOREIGN KEY (CodFilm) "
						+ "REFERENCES FILM "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public List<Integer> findByCode(final int code, final boolean control) throws SQLException {
		List<Integer> codes = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		PreparedStatement statement;
		if (control) {
			final String query = "select CodAttore from " + this.tableName + " where codFilm = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
		} else {
			final String query = "select CodFilm from " + this.tableName + " where codAttore = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, code);
		}
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			codes = new LinkedList<>();
			if (control) {
				codes.add(result.getInt("CodAttore"));
			} else {
				codes.add(result.getInt("CodFilm"));
			}
		}
		while (result.next()) {
			if (control) {
				codes.add(result.getInt("CodAttore"));
			} else {
				codes.add(result.getInt("CodFilm"));
			}
		}
		result.close();
		statement.close();
		this.closeConnection();
		return codes;
	}

	@Override
	public List<Integer> findFilmOnlyOneActor(final int codActor) throws SQLException {
		List<Integer> codes = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select CodFilm from " + this.tableName
				+ " where CodFilm IN (select CodFilm from " + this.tableName + " group by CodFilm HAVING COUNT(CodAttore) = 1)"
				+ " and CodAttore = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codActor);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			codes = new LinkedList<>();
			codes.add(result.getInt("CodFilm"));
		}
		while (result.next()) {
			codes.add(result.getInt("CodFilm"));
		}
		return codes;
	}


}
