package hu.engard.xpenses.dao;

import hu.engard.xpenses.MyApplication;
import hu.engard.xpenses.model.XpensesDbHelper;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

abstract public class AbstractDao {
	abstract public String getTableName();
	/**
	 * For inserting during sync. 
	 */
	abstract protected ContentValues convertSyncMapToCV(HashMap<String, Object> map);

	public AbstractDao() {
	}

	public int uid2id(String uid) {
		Cursor cursor = db().rawQuery("select _id from "+getTableName()+" where uid = ?", new String[] { uid });
		if (!cursor.moveToFirst()) return -1;
		return cursor.getInt(0);
	}

	public static ContentValues map2cv(Map<String,Object> map, String... columns) {
		ContentValues res=new ContentValues();
		if (columns.length==0) {
			columns=map.keySet().toArray(new String[0]);
		}
		for (String col: columns) {
			Object val=map.get(col);
			if (val==null) {
				res.putNull(col);
			} else {
				res.put(col, val.toString());
			}
		}
		return res;
	}

	protected SQLiteDatabase db() {
		return MyApplication.instance().getDbHelper().getWritableDatabase();
	}

	/**
	 * Insert a new row from a received full sync JSON structure.
	 * @param node
   */
	public long insert(JsonNode node) {
		HashMap<String, Object> map = json2map(node);
		ContentValues cv = convertSyncMapToCV(map);
		long ret=db().insert(getTableName(), null, cv);
		Log.i(getTableName()+"Dao", "inserting {"+cv+"}. result: "+ret);
		return ret;
	}
	
	public static HashMap<String, Object> json2map(JsonNode node) {
		HashMap<String, Object> map=MyApplication.getMapper().convertValue(node, new TypeReference<HashMap<String, Object>>() {});
		return map;
	}
}