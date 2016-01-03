package hu.engard.xpenses.util;

import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;

public class DBUtils {
	public static Map<String, Object> row2map(Cursor c) {
		Map<String, Object> res=new HashMap<String, Object>();
		for (int i=0; i<c.getColumnCount(); i++) {
			String colName=c.getColumnName(i);
			Object val=null;
			switch(c.getType(i)) {
			case Cursor.FIELD_TYPE_NULL:
				val=null;
				break;
			case Cursor.FIELD_TYPE_INTEGER:
				val=c.getLong(i);
				break;
			case Cursor.FIELD_TYPE_FLOAT:
				val=c.getDouble(i);
				break;
			case Cursor.FIELD_TYPE_STRING:
				val=c.getString(i);
				break;
			case Cursor.FIELD_TYPE_BLOB:
				// TODO
				throw new DBException();
				//val=c.getInt(i);
			}
			res.put(colName, val);
		}
		return res;
	}
	
	public static Map<String, Object> convertFirst(Cursor c) {
		if (!c.moveToFirst()) throw new DBException();
		Map<String, Object> res=row2map(c);
		if (!c.isLast()) throw new DBException();
		return res;
	}
	
	public static class DBException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
