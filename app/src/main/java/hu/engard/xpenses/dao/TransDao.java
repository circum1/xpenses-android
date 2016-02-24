package hu.engard.xpenses.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import hu.engard.expenses.generated.tables.interfaces.ITransaction;
import hu.engard.expenses.generated.tables.pojos.Transaction;
import hu.engard.xpenses.MyApplication;
import hu.engard.xpenses.util.DBUtils;

public class TransDao extends AbstractDao {
  public final static String C_ID = "_id".toLowerCase(Locale.getDefault());
  public final static String C_UID = "uid".toLowerCase(Locale.getDefault());
  public final static String C_ACCOUNT = "account".toLowerCase(Locale.getDefault());
  public final static String C_AMOUNT = "amount".toLowerCase(Locale.getDefault());
  public final static String C_TIME = "time".toLowerCase(Locale.getDefault());
  public final static String C_COMMENT = "comment".toLowerCase(Locale.getDefault());
  public final static String C_TYPE = "type".toLowerCase(Locale.getDefault());

  private static AccountDao accountDao = new AccountDao();
  private static TagDao tagDao = TagDao.instance();

  @Override
  public String getTableName() {
    return "Trans";
  }

  @Override
  protected ContentValues convertSyncMapToCV(HashMap<String, Object> map) {
    ContentValues cv = map2cv(map, C_UID, C_AMOUNT, C_TIME, C_COMMENT, C_TYPE);
    String accountUid = (String) map.get(C_ACCOUNT);
    if (accountUid != null) {
      int accountId = accountDao.uid2id(accountUid);
      if (accountId < 0)
        throw new RuntimeException("Invalid transaction account uid " + accountUid);
      cv.put(C_ACCOUNT, accountId);
    }
    return cv;
  }

  @Override
  public long insert(JsonNode node) {
    long id = super.insert(node);
    HashMap<String, Object> map = json2map(node);
    String tags = (String) map.get("taguids");
    if (tags != null) {
      String[] tagsArr = tags.split(",");
      for (String tag : tagsArr) {
        ContentValues cv = new ContentValues();
        cv.put("trans", id);
        cv.put("tag", tagDao.uid2id(tag.trim()));
        Log.i("TransDao", "adding tag " + tag + " as " + cv);
        db().insert("TransactionTag", null, cv);
      }
    }
    return id;
  }

  public static Cursor findAll() {
    SQLiteDatabase db = MyApplication.instance().getDbHelper().getReadableDatabase();
    return db.rawQuery("SELECT * FROM Trans", null);
  }

  public static ITransaction find(long id) {
    SQLiteDatabase db = MyApplication.instance().getDbHelper().getReadableDatabase();
    Cursor c = db.rawQuery("SELECT * FROM Trans WHERE _id=" + id, null);
    Map<String, Object> row = DBUtils.convertFirst(c);
    Transaction res = new Transaction(((Long) row.get(C_ID)).intValue(), UUID.fromString(row.get(C_UID).toString()),
        ((Long) row.get(C_ACCOUNT)).intValue(), BigDecimal.valueOf((Double) row.get(C_AMOUNT)),
        new Timestamp(Long.parseLong((String) row.get(C_TIME))), (String) row.get(C_COMMENT), (String) row.get(C_TYPE));
    return res;
  }

}
