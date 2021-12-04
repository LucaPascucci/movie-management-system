package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Review;
/**
 * Interface of reviewTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IReviewTable {
	/**
	 * Find the review by the key.
	 * @param username username
	 * @param code code
	 * @return the review it it was find in the database
	 * @throws SQLException SQLException
	 */
	Review findByPrimaryKey(final String username, final int code) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;
	/**
	 * Get all the reviews of a film.
	 * @param filmCode filmCode
	 * @return the list of the reviews
	 * @throws SQLException SQLException
	 */
	List<Review> getReviewsOfAFilm(final int filmCode) throws SQLException;
	/**
	 * Delete all the reviews of a customer.
	 * @param username username
	 * @throws SQLException SQLException
	 */
	void deleteAllReviewOfACustomer(final String username) throws SQLException;
	/**
	 * Calculate the average vote for the film selected.
	 * @param filmCode filmCode
	 * @return the average vote of the film selected
	 * @throws SQLException SQLException
	 */
	Integer getAverageOfAFilm(Integer filmCode) throws SQLException;

}
