package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mms.database.interfaces.IEvaluationTable;
import mms.exception.AlreadyExistsException;
import mms.model.Evaluation;

/**
 * Table for the evaluation.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class EvaluationTable extends AbstractTable implements IEvaluationTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public EvaluationTable() throws SQLException {
		super("APPREZZAMENTO");
	}

	@Override
	public void createTable() throws SQLException {

		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "CodFilm_Rec INT NOT NULL,"
						+ "Username_Rec VARCHAR(30) NOT NULL, " 
						+ "Username_App VARCHAR(30) NOT NULL, "
						+ "MiPiace_NonMiPiace CHAR NOT NULL, "
						+ "CONSTRAINT IDAPPREZZAMENTO PRIMARY KEY (Username_App, CodFilm_Rec, Username_Rec)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException, AlreadyExistsException {

		final Evaluation evaluation = (Evaluation) record;
		if (findByPrimaryKey(evaluation.getUsername(), evaluation.getRelCodMovie(), evaluation.getRelUsername()) != null) {
			throw new AlreadyExistsException();
		}
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (CodFilm_Rec, Username_Rec, Username_App, MiPiace_NonMiPiace) values (?,?,?,?)";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setInt(1, evaluation.getRelCodMovie());
		statement.setString(2, evaluation.getRelUsername());
		statement.setString(3, evaluation.getUsername());
		statement.setString(4, String.valueOf(evaluation.getLikeOrDislike()));
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {

		final Evaluation evaluation = (Evaluation) record;
		final Evaluation oldEvaluation = findByPrimaryKey(evaluation.getUsername(), evaluation.getRelCodMovie(), evaluation.getRelUsername());
		if (oldEvaluation != null) {
			final String insert = "update " + this.tableName 
					+ " set MiPiace_NonMipiace = ? where Username_App = ? and CodFilm_Rec = ? and Username_Rec = ?";
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, String.valueOf(evaluation.getLikeOrDislike()));
			statement.setString(2, oldEvaluation.getUsername());
			statement.setInt(3, oldEvaluation.getRelCodMovie());
			statement.setString(4, oldEvaluation.getRelUsername());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {

		final Evaluation evaluation = (Evaluation) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where Username_App = ? and CodFilm_Rec = ? and Username_Rec = ?";
		final PreparedStatement statement = connection.prepareStatement(insert);
		statement.setString(1, evaluation.getUsername());
		statement.setInt(2, evaluation.getRelCodMovie());
		statement.setString(3, evaluation.getRelUsername());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Evaluation findByPrimaryKey(final String username, final int relCodMovie, final String relUsername) throws SQLException {
		Evaluation evaluation = null;
		final String query = "select * from " + this.tableName + " where Username_App = ? and CodFilm_Rec = ? and Username_Rec = ?";
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		PreparedStatement statement = this.connection.prepareStatement(query);
		statement = connection.prepareStatement(query);
		statement.setString(1, username);
		statement.setInt(2, relCodMovie);
		statement.setString(3, relUsername);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			evaluation = new Evaluation();
			evaluation.setRelCodFilm(result.getInt("CodFilm_Rec"));
			evaluation.setRelUsername(result.getString("Username_Rec"));
			evaluation.setUsername(result.getString("Username_App"));
			evaluation.setLikeOrDislike(result.getString("MiPiace_NonMiPiace").charAt(0));
		}
		result.close();
		statement.close();
		this.closeConnection();
		return evaluation;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKesprime "
						+ "FOREIGN KEY (Username_App) "
						+ "REFERENCES CLIENTE "
						+ "ON DELETE CASCADE"
				);
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKrelativo "
						+ "FOREIGN KEY (CodFilm_Rec, Username_Rec) "
						+ "REFERENCES RECENSIONE "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public Integer countEvaluation(final Integer filmCode, final String userRec, final Object type) throws SQLException {
		Integer evaluations = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select COUNT(APPREZZAMENTO.MiPiace_NonMiPiace) from APPREZZAMENTO"
				+ " where APPREZZAMENTO.CodFilm_Rec = ?"
				+ " and APPREZZAMENTO.Username_Rec = ?"
				+ " and APPREZZAMENTO.MiPiace_NonMiPiace = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, filmCode);
		statement.setString(2, userRec);
		statement.setString(3, String.valueOf(type));
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			evaluations = result.getInt(1);
		}
		statement.close();
		this.closeConnection();
		return evaluations;
	}

}
