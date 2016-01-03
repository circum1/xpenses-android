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
public interface IAccount extends java.io.Serializable {

	/**
	 * Setter for <code>PUBLIC.ACCOUNT.ID</code>.
	 */
	public void setId(java.lang.Integer value);

	/**
	 * Getter for <code>PUBLIC.ACCOUNT.ID</code>.
	 */
	public java.lang.Integer getId();

	/**
	 * Setter for <code>PUBLIC.ACCOUNT.UID</code>.
	 */
	public void setUid(java.util.UUID value);

	/**
	 * Getter for <code>PUBLIC.ACCOUNT.UID</code>.
	 */
	public java.util.UUID getUid();

	/**
	 * Setter for <code>PUBLIC.ACCOUNT.USER</code>.
	 */
	public void setUser(java.lang.Integer value);

	/**
	 * Getter for <code>PUBLIC.ACCOUNT.USER</code>.
	 */
	public java.lang.Integer getUser();

	/**
	 * Setter for <code>PUBLIC.ACCOUNT.NAME</code>.
	 */
	public void setName(java.lang.String value);

	/**
	 * Getter for <code>PUBLIC.ACCOUNT.NAME</code>.
	 */
	public java.lang.String getName();

	/**
	 * Setter for <code>PUBLIC.ACCOUNT.COMMENT</code>.
	 */
	public void setComment(java.lang.String value);

	/**
	 * Getter for <code>PUBLIC.ACCOUNT.COMMENT</code>.
	 */
	public java.lang.String getComment();

	/**
	 * Setter for <code>PUBLIC.ACCOUNT.INITIALBALANCE</code>.
	 */
	public void setInitialbalance(java.math.BigDecimal value);

	/**
	 * Getter for <code>PUBLIC.ACCOUNT.INITIALBALANCE</code>.
	 */
	public java.math.BigDecimal getInitialbalance();

	/**
	 * Setter for <code>PUBLIC.ACCOUNT.CURRENTBALANCE</code>.
	 */
	public void setCurrentbalance(java.math.BigDecimal value);

	/**
	 * Getter for <code>PUBLIC.ACCOUNT.CURRENTBALANCE</code>.
	 */
	public java.math.BigDecimal getCurrentbalance();

	// -------------------------------------------------------------------------
	// FROM and INTO
	// -------------------------------------------------------------------------

	/**
	 * Load data from another generated Record/POJO implementing the common interface IAccount
	 */
	public void from(hu.engard.expenses.generated.tables.interfaces.IAccount from);

	/**
	 * Copy data into another generated Record/POJO implementing the common interface IAccount
	 */
	public <E extends hu.engard.expenses.generated.tables.interfaces.IAccount> E into(E into);
}
