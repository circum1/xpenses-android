package hu.engard.xpenses.dao;

import java.util.HashMap;
import java.util.Locale;

import android.content.ContentValues;

public class AccountDao extends AbstractDao {
	public final static String C_ID="_id".toLowerCase(Locale.getDefault());
	public final static String C_UID="uid".toLowerCase(Locale.getDefault());
	public final static String C_NAME="name".toLowerCase(Locale.getDefault());
	public final static String C_COMMENT="comment".toLowerCase(Locale.getDefault());
	public final static String C_INITIALBALANCE="initialBalance".toLowerCase(Locale.getDefault());
	public final static String C_CURRENTBALANCE="currentBalance".toLowerCase(Locale.getDefault());

	@Override
	protected ContentValues convertSyncMapToCV(HashMap<String, Object> map) {
		return map2cv(map, C_UID, C_NAME, C_COMMENT, C_INITIALBALANCE, C_CURRENTBALANCE);
	}

	@Override
	public String getTableName() {
		return "Account";
	}

}
