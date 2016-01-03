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
public class Tag implements hu.engard.expenses.generated.tables.interfaces.ITag {

	private static final long serialVersionUID = 1515783535;

	private java.lang.Integer id;
	private java.util.UUID    uid;
	private java.lang.Integer parent;
	private java.lang.Integer user;
	private java.lang.Integer category;
	private java.lang.String  label;
	private java.lang.String  comment;

	public Tag() {}

	public Tag(
		java.lang.Integer id,
		java.util.UUID    uid,
		java.lang.Integer parent,
		java.lang.Integer user,
		java.lang.Integer category,
		java.lang.String  label,
		java.lang.String  comment
	) {
		this.id = id;
		this.uid = uid;
		this.parent = parent;
		this.user = user;
		this.category = category;
		this.label = label;
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
	public java.util.UUID getUid() {
		return this.uid;
	}

	@Override
	public void setUid(java.util.UUID uid) {
		this.uid = uid;
	}

	@Override
	public java.lang.Integer getParent() {
		return this.parent;
	}

	@Override
	public void setParent(java.lang.Integer parent) {
		this.parent = parent;
	}

	@Override
	public java.lang.Integer getUser() {
		return this.user;
	}

	@Override
	public void setUser(java.lang.Integer user) {
		this.user = user;
	}

	@Override
	public java.lang.Integer getCategory() {
		return this.category;
	}

	@Override
	public void setCategory(java.lang.Integer category) {
		this.category = category;
	}

	@Override
	public java.lang.String getLabel() {
		return this.label;
	}

	@Override
	public void setLabel(java.lang.String label) {
		this.label = label;
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
	public void from(hu.engard.expenses.generated.tables.interfaces.ITag from) {
		setId(from.getId());
		setUid(from.getUid());
		setParent(from.getParent());
		setUser(from.getUser());
		setCategory(from.getCategory());
		setLabel(from.getLabel());
		setComment(from.getComment());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends hu.engard.expenses.generated.tables.interfaces.ITag> E into(E into) {
		into.from(this);
		return into;
	}
}