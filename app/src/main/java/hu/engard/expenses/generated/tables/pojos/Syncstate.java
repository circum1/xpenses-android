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
public class Syncstate implements hu.engard.expenses.generated.tables.interfaces.ISyncstate {

	private static final long serialVersionUID = 1290834919;

	private java.util.UUID    id;
	private java.lang.Integer lastanchor;
	private java.lang.Integer lastanchorfromremote;

	public Syncstate() {}

	public Syncstate(
		java.util.UUID    id,
		java.lang.Integer lastanchor,
		java.lang.Integer lastanchorfromremote
	) {
		this.id = id;
		this.lastanchor = lastanchor;
		this.lastanchorfromremote = lastanchorfromremote;
	}

	@Override
	public java.util.UUID getId() {
		return this.id;
	}

	@Override
	public void setId(java.util.UUID id) {
		this.id = id;
	}

	@Override
	public java.lang.Integer getLastanchor() {
		return this.lastanchor;
	}

	@Override
	public void setLastanchor(java.lang.Integer lastanchor) {
		this.lastanchor = lastanchor;
	}

	@Override
	public java.lang.Integer getLastanchorfromremote() {
		return this.lastanchorfromremote;
	}

	@Override
	public void setLastanchorfromremote(java.lang.Integer lastanchorfromremote) {
		this.lastanchorfromremote = lastanchorfromremote;
	}

	// -------------------------------------------------------------------------
	// FROM and INTO
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void from(hu.engard.expenses.generated.tables.interfaces.ISyncstate from) {
		setId(from.getId());
		setLastanchor(from.getLastanchor());
		setLastanchorfromremote(from.getLastanchorfromremote());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends hu.engard.expenses.generated.tables.interfaces.ISyncstate> E into(E into) {
		into.from(this);
		return into;
	}
}
