package mms.database.interfaces;

import java.sql.SQLException;
import java.util.List;

import mms.model.Discount;
/**
 * Interface of discountTable.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public interface IDiscountTable {
	/**
	 * Find the discount by the code.
	 * @param code code
	 * @return the discount if it was find in the database
	 * @throws SQLException SQLException
	 */
	Discount findByPrimaryKey(final int code) throws SQLException;
	/**
	 * Find all discounts in the database.
	 * @return the list of the discounts
	 * @throws SQLException SQLException
	 */
	List<Discount> findAll() throws SQLException;

}
