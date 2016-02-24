package hu.engard.xpenses.dao;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import hu.engard.xpenses.util.CachedListBase;

/**
 * Created by fery on 1/21/16.
 */
public abstract class AbstractCachedDao<T> extends AbstractDao {
  private CachedListBase<T> cache;
  private Class<T> klass;
  private Method getIdMethod;
  private Method getUidMethod;

  public AbstractCachedDao(Class<T> klass, CachedListBase<T> cache) {
    this.cache=cache;
    this.klass=klass;
    try {
      this.getIdMethod=klass.getMethod("getId", null);
      this.getUidMethod=klass.getMethod("getUid", null);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  public List<T> getAll() {
    return cache.get();
  }

  // until a few dozen of tags, the linear search is about as fast as using a hashmap
  public int uid2id(String uid) {
    UUID what=UUID.fromString(uid);
    for (T item : cache.get()) {
      try {
        if (getUidMethod.invoke(item).equals(what)) {
          return (Integer) getIdMethod.invoke(item);
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return -1;
  }

  public void invalidateCache() {
    cache.invalidate();
  }

  @Override
  public long insert(JsonNode node) {
    invalidateCache();
    return super.insert(node);
  }
}
