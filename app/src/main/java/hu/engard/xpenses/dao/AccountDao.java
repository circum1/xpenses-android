package hu.engard.xpenses.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import hu.engard.expenses.generated.tables.pojos.Account;
import hu.engard.xpenses.MyApplication;
import hu.engard.xpenses.util.CachedListBase;

public class AccountDao extends AbstractCachedDao {
	public final static String C_ID="_id".toLowerCase(Locale.getDefault());
	public final static String C_UID="uid".toLowerCase(Locale.getDefault());
	public final static String C_NAME="name".toLowerCase(Locale.getDefault());
	public final static String C_COMMENT="comment".toLowerCase(Locale.getDefault());
	public final static String C_INITIALBALANCE="initialBalance".toLowerCase(Locale.getDefault());
	public final static String C_CURRENTBALANCE="currentBalance".toLowerCase(Locale.getDefault());

  private static AccountDao _singleton = null;

  public static AccountDao instance() {
    if (_singleton == null) {
      _singleton = new AccountDao();
    }
    return _singleton;
  }

  public AccountDao() {
    super(Account.class, new AccountCache());
  }

  @Override
	protected ContentValues convertSyncMapToCV(HashMap<String, Object> map) {
		return map2cv(map, C_UID, C_NAME, C_COMMENT, C_INITIALBALANCE, C_CURRENTBALANCE);
	}

	@Override
	public String getTableName() {
		return "Account";
	}

  private static class AccountCache extends CachedListBase<Account> {
    @Override
    protected void updateCache() {
      cachedObject = new ArrayList<>();
      SQLiteDatabase db = MyApplication.instance().getDbHelper().getWritableDatabase();
      Cursor c = db.query("Account", new String[]{C_ID, C_UID, C_NAME, C_COMMENT, C_INITIALBALANCE, C_CURRENTBALANCE}, null, null, null, null, null);
      while (c.moveToNext()) {
        // TODO the user id is hardwired to 0 -- not used
        Account item = new Account(c.getInt(0), UUID.fromString(c.getString(1)), 0, c.getString(2), c.getString(3), new BigDecimal(c.getString(4)), new BigDecimal(c.getString(5)));
        cachedObject.add(item);
      }
      cachedObject.add(new Account(0, null, 1, "egy", "cm", null, null));
      cachedObject.add(new Account(0, null, 1, "kettoooo", "cm", null, null));

      cachedObject=Collections.unmodifiableList(cachedObject);
    }
  }

}
