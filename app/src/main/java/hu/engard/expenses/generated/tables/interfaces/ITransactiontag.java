/**
 * This class is generated by jOOQ
 */
package hu.engard.expenses.generated.tables.interfaces;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public interface ITransactiontag extends java.io.Serializable {

	/**
	 * Setter for <code>PUBLIC.TRANSACTIONTAG.TRANSACTION</code>.
	 */
	public void setTransaction(java.lang.Integer value);

	/**
	 * Getter for <code>PUBLIC.TRANSACTIONTAG.TRANSACTION</code>.
	 */
	public java.lang.Integer getTransaction();

	/**
	 * Setter for <code>PUBLIC.TRANSACTIONTAG.TAG</code>.
	 */
	public void setTag(java.lang.Integer value);

	/**
	 * Getter for <code>PUBLIC.TRANSACTIONTAG.TAG</code>.
	 */
	public java.lang.Integer getTag();

	// -------------------------------------------------------------------------
	// FROM and INTO
	// -------------------------------------------------------------------------

	/**
	 * Load data from another generated Record/POJO implementing the common interface ITransactiontag
	 */
	public void from(hu.engard.expenses.generated.tables.interfaces.ITransactiontag from);

	/**
	 * Copy data into another generated Record/POJO implementing the common interface ITransactiontag
	 */
	public <E extends hu.engard.expenses.generated.tables.interfaces.ITransactiontag> E into(E into);
}
