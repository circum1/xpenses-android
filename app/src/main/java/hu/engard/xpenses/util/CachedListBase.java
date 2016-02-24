package hu.engard.xpenses.util;

import java.util.List;

/**
 * Created by fery on 1/4/16.
 */
public abstract class CachedListBase<T> {
  public CachedListBase() {
    dirty=true;
  }

  public void invalidate() {
    this.dirty=true;
  }

  public List<T> get() {
    if (dirty) {
      updateCache();
      dirty=false;
    }
    return cachedObject;
  }

  protected List<T> cachedObject;
  protected abstract void updateCache();

  private boolean dirty;
}
