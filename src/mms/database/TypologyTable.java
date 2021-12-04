package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.ITypologyTable;
import mms.model.Typology;
/**
 * Table fo the Typology.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class TypologyTable extends AbstractTable implements ITypologyTable {
	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public TypologyTable() throws SQLException {
		super("TIPOLOGIA");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ( " 
						+ "NumTessera INT NOT NULL, "
						+ "DataInizioSconto DATE NOT NULL, "
						+ "CodSconto INT NOT NULL, "
						+ "CONSTRAINT FKtipo_tessera_ID PRIMARY KEY (NumTessera)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException {
		final Typology typology = (Typology) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (NumTessera, DataInizioSconto, CodSconto) values (?,?,?)";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, typology.getNumCard());
		statement.setDate(2, new java.sql.Date(typology.getStartDateDiscount().getTime()));
		statement.setInt(3, typology.getCodDiscount());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		final Typology typology = (Typology) record;
		final Typology oldTypology = findByPrimaryKey(typology.getNumCard());
		if (oldTypology != null) {
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final String insert = "update " + this.tableName + " set DataInizioSconto = ?, CodSconto = ? where NumTessera = ?";
			final PreparedStatement statement = this.connection.prepareStatement(insert);
			statement.setDate(1,  new java.sql.Date(typology.getStartDateDiscount().getTime())); //conversione da java.util.Date a java.sql.Date.
			statement.setInt(2, typology.getCodDiscount());
			statement.setInt(3, oldTypology.getNumCard());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final Typology typology = (Typology) record;
		final String insert = "delete from " + this.tableName + " where NumTessera = ?";
		PreparedStatement statement = this.connection.prepareStatement(insert);
		statement = connection.prepareStatement(insert);
		statement.setInt(1, typology.getNumCard());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Typology findByPrimaryKey(final int code) throws SQLException {
		Typology typology = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where NumTessera = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, code);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			typology = new Typology();
			typology.setNumCard(result.getInt("NumTessera"));
			typology.setStartDateDiscount(new Date(result.getDate("DataInizioSconto").getTime()));
			typology.setCodDiscount(result.getInt("CodSconto"));
		}
		statement.close();
		this.closeConnection();
		return typology;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKtipo_tessera_FK "
						+ "FOREIGN KEY (NumTessera) "
						+ "REFERENCES TESSERA "
						+ "ON DELETE CASCADE"
				);
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKtipo_sconto "
						+ "FOREIGN KEY (CodSconto) "
						+ "REFERENCES SCONTO "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void checkAllDiscount() throws SQLException {
		Typology typology = null;
		List<Typology> finish = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "Select " + this.tableName + ".NumTessera," + this.tableName + ".CodSconto," + this.tableName + ".DataInizioSconto"
				+ " from " + this.tableName + ",SCONTO"
				+ " where " + this.tableName + ".CodSconto = SCONTO.CodiceSconto"
				+ " group by " + this.tableName + ".NumTessera," + this.tableName + ".DataInizioSconto," + this.tableName + ".CodSconto,SCONTO.Durata"
				+ " having DATEADD(DAY,SCONTO.Durata," + this.tableName + ".DataInizioSconto) < ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setDate(1, new java.sql.Date(new Date().getTime()));
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			typology = new Typology();
			finish = new LinkedList<>();
			typology.setNumCard(result.getInt("NumTessera"));
			typology.setCodDiscount(result.getInt("CodSconto"));
			typology.setStartDateDiscount(result.getDate("DataInizioSconto"));
			finish.add(typology);
		}
		while (result.next()) {
			typology = new Typology();
			typology.setNumCard(result.getInt("NumTessera"));
			typology.setCodDiscount(result.getInt("CodSconto"));
			typology.setStartDateDiscount(result.getDate("DataInizioSconto"));
			finish.add(typology);
		}
		statement.close();
		this.closeConnection();
		if (finish != null) {
			final Iterator<Typology> it = finish.iterator();
			while (it.hasNext()) {
				typology = it.next();
				this.delete(typology);
			}
		}
	}

}
