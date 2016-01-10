package hu.engard.xpenses.util;

/**
 * Created by fery on 1/4/16.
 */
public abstract class CachedObjectBase<T> {
  public CachedObjectBase() {
    dirty=true;
  }

  public void invalidate() {
    this.dirty=true;
  }

  public T get() {
    if (dirty) {
      updateCache();
      dirty=false;
    }
    return cachedObject;
  }

  protected T cachedObject;
  protected abstract void updateCache();

  private boolean dirty;
}
