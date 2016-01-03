/**
 * This class is generated by jOOQ
 */
package hu.engard.expenses.generated.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Category implements hu.engard.expenses.generated.tables.interfaces.ICategory {

	private static final long serialVersionUID = 1438013533;

	private java.lang.Integer id;
	private java.lang.String  name;
	private java.lang.String  comment;

	public Category() {}

	public Category(
		java.lang.Integer id,
		java.lang.String  name,
		java.lang.String  comment
	) {
		this.id = id;
		this.name = name;
		this.comment = comment;
	}

	@Override
	public java.lang.Integer getId() {
		return this.id;
	}

	@Override
	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	@Override
	public java.lang.String getName() {
		return this.name;
	}

	@Override
	public void setName(java.lang.String name) {
		this.name = name;
	}

	@Override
	public java.lang.String getComment() {
		return this.comment;
	}

	@Override
	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}

	// -------------------------------------------------------------------------
	// FROM and INTO
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void from(hu.engard.expenses.generated.tables.interfaces.ICategory from) {
		setId(from.getId());
		setName(from.getName());
		setComment(from.getComment());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends hu.engard.expenses.generated.tables.interfaces.ICategory> E into(E into) {
		into.from(this);
		return into;
	}
}