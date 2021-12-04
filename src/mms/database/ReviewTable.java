package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IReviewTable;
import mms.exception.AlreadyExistsException;
import mms.model.Review;
/**
 * Table for the Review.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class ReviewTable extends AbstractTable implements IReviewTable {
	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public ReviewTable() throws SQLException {
		super("RECENSIONE");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ( " 
						+ "Username VARCHAR(30) NOT NULL, "
						+ "CodFilm INT NOT NULL, "
						+ "Intestazione VARCHAR(100) NOT NULL, "
						+ "Testo VARCHAR(8000) NOT NULL, "
						+ "Valutazione INT NOT NULL, "
						+ "CONSTRAINT IDRECENSIONE PRIMARY KEY (CodFilm, Username),"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException, AlreadyExistsException {
		final Review review = (Review) record;
		if (findByPrimaryKey(review.getUsername(), review.getCodFilm()) != null) {
			throw new AlreadyExistsException();
		}
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (Username, CodFilm, Intestazione, Testo, Valutazione) values (?,?,?,?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setString(1, review.getUsername());
		statement.setInt(2, review.getCodFilm());
		statement.setString(3, review.getHeader());
		statement.setString(4, review.getText());
		statement.setInt(5, review.getEvaluation());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		final Review review = (Review) record;
		final Review oldReview = findByPrimaryKey(review.getUsername(), review.getCodFilm());
		if (oldReview != null) {
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final String insert = "update " + this.tableName + " set Intestazione = ?, Testo = ?, Valutazione = ? where Username = ? and CodFilm = ?";
			final PreparedStatement statement = this.connection.prepareStatement(insert);
			statement.setString(1, review.getHeader());
			statement.setString(2, review.getText());
			statement.setInt(3,  review.getEvaluation());
			statement.setString(4, review.getUsername());
			statement.setInt(5, review.getCodFilm());
			statement.executeUpdate();
			this.closeConnection();
		}
	}

	@Override
	public void delete(final Object record) throws SQLException {
		final Review review = (Review) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where Username = ? and CodFilm = ?";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setString(1, review.getUsername());
		statement.setInt(2, review.getCodFilm());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Review findByPrimaryKey(final String username, final int code) throws SQLException {

		Review review = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where Username = ? and CodFilm = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, username);
		statement.setInt(2, code);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			review = new Review();
			review.setUsername(result.getString("Username"));
			review.setCodFilm(result.getInt("CodFilm"));
			review.setHeader(result.getString("Intestazione"));
			review.setText(result.getString("Testo"));
			review.setEvaluation(result.getInt("Valutazione"));
		}
		statement.close();
		this.closeConnection();
		return review;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKriferita "
						+ "FOREIGN KEY (CodFilm) "
						+ "REFERENCES FILM "
						+ "ON DELETE CASCADE"
				);
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKscrive "
						+ "FOREIGN KEY (Username) "
						+ "REFERENCES CLIENTE "
						+ "ON DELETE NO ACTION"
				);
		statement.close();
		this.closeConnection();

	}

	@Override
	public List<Review> getReviewsOfAFilm(final int filmCode) throws SQLException {
		List<Review> reviews = null;
		Review review = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select RECENSIONE.* from RECENSIONE"
				+ " where RECENSIONE.CodFilm = ?"
				+ " order by RECENSIONE.CodFilm";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, filmCode);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			reviews = new LinkedList<Review>();
			review = new Review();
			review.setUsername(result.getString("Username"));
			review.setCodFilm(result.getInt("CodFilm"));
			review.setHeader(result.getString("Intestazione"));
			review.setText(result.getString("Testo"));
			review.setEvaluation(result.getInt("Valutazione"));
			reviews.add(review);
		}
		while (result.next()) {
			review = new Review();
			review.setUsername(result.getString("Username"));
			review.setCodFilm(result.getInt("CodFilm"));
			review.setHeader(result.getString("Intestazione"));
			review.setText(result.getString("Testo"));
			review.setEvaluation(result.getInt("Valutazione"));
			reviews.add(review);
		}
		statement.close();
		this.closeConnection();
		return reviews;
	}

	@Override
	public void deleteAllReviewOfACustomer(final String username) throws SQLException {
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "delete from " + this.tableName + " where RECENSIONE.Username = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setString(1, username);
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public Integer getAverageOfAFilm(final Integer filmCode) throws SQLException {
		Integer avg = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select AVG(Valutazione)"
				+ " from RECENSIONE"
				+ " where CodFilm = ?";		
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, filmCode);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			avg = result.getInt(1);
		}
		statement.close();
		this.closeConnection();
		return avg;
	}

}
