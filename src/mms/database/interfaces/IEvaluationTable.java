package mms.database.interfaces;

import java.sql.SQLException;

import mms.model.Evaluation;
/**
 * Interface of the evaluationTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IEvaluationTable {
	/**
	 * Find the evaluation by the key.
	 * @param username username
	 * @param relCodMovie relCodMovie
	 * @param relUsername relUsername
	 * @return the evaluation if it was find in the database
	 * @throws SQLException SQLException
	 */
	Evaluation findByPrimaryKey(final String username, final int relCodMovie, final String relUsername) throws SQLException;
	/**
	 * Alter the table with the foreign key.
	 * @throws SQLException SQLException
	 */
	void alterTable() throws SQLException;
	/**
	 * Count the evaluation (positive or negative) or a review.
	 * @param filmCode filmCode
	 * @param userRec userRec
	 * @param type type
	 * @return the number of the evaluation (positive or negative)
	 * @throws SQLException SQLException
	 */
	Integer countEvaluation(final Integer filmCode, final String userRec, final Object type) throws SQLException;
}
