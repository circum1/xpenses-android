package hu.engard.xpenses.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fasterxml.jackson.databind.JsonNode;

import hu.engard.expenses.generated.tables.pojos.Tag;
import hu.engard.xpenses.MyApplication;
import hu.engard.xpenses.util.CachedListBase;

public class TagDao extends AbstractCachedDao {
  public final static String C_ID = "_id".toLowerCase(Locale.getDefault());
  public final static String C_UID = "uid".toLowerCase(Locale.getDefault());
  public final static String C_PARENT = "parent".toLowerCase(Locale.getDefault());
  public final static String C_CATEGORY = "category".toLowerCase(Locale.getDefault());
  public final static String C_LABEL = "label".toLowerCase(Locale.getDefault());
  public final static String C_COMMENT = "comment".toLowerCase(Locale.getDefault());

  private static TagDao _singleton = null;

  public static TagDao instance() {
    if (_singleton == null) {
      _singleton = new TagDao();
    }
    return _singleton;
  }

  private TagDao() {
    super(Tag.class, new TagCache());
  }

  @Override
  public String getTableName() {
    return "Tag";
  }

  @Override
  protected ContentValues convertSyncMapToCV(HashMap<String, Object> map) {
    // TODO C_CATEGORY
    ContentValues cv = map2cv(map, C_UID, C_LABEL, C_COMMENT);
    String parentUid = (String) map.get(C_PARENT);
    if (parentUid != null) {
      int parentId = uid2id(parentUid);
      if (parentId < 0) throw new RuntimeException("Invalid tag parent uid " + parentUid);
      cv.put(C_PARENT, parentId);
    }
    return cv;
  }

  private static class TagCache extends CachedListBase<Tag> {
    @Override
    protected void updateCache() {
      cachedObject = new ArrayList<>();
      SQLiteDatabase db = MyApplication.instance().getDbHelper().getWritableDatabase();
      Cursor c = db.query("Tag", new String[]{C_ID, C_UID, C_PARENT, C_CATEGORY, C_LABEL, C_COMMENT}, null, null, null, null, null);
      while (c.moveToNext()) {
        // TODO the user id is hardwired to 0 -- not used
        Tag t = new Tag(c.getInt(0), UUID.fromString(c.getString(1)), c.getInt(2), 0, c.getInt(3), c.getString(4), c.getString(5));
        cachedObject.add(t);
      }
    }
  }
//  private static final TagCache allTags = new TagCache();

//  // until a few dozen of tags, the linear search is about as fast as using a hashmap
//  public int uid2id(String uid) {
//    for (Tag t : allTags.get()) {
//      if (t.getUid().equals(UUID.fromString(uid))) {
//        return t.getId();
//      }
//    }
//    return -1;
//  }

//  @Override
//  public long insert(JsonNode node) {
////    allTags.invalidate();
//    invalidateCache();
//    return super.insert(node);
//  }
}
