package hu.engard.xpenses.dao;

import hu.engard.expenses.generated.tables.interfaces.ITransaction;
import hu.engard.expenses.generated.tables.pojos.Transaction;
import hu.engard.xpenses.MyApplication;
import hu.engard.xpenses.model.XpensesDbHelper;
import hu.engard.xpenses.util.DBUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TransDao {
	public static Cursor findAll() {
		SQLiteDatabase db=MyApplication.instance().getDbHelper().getReadableDatabase();
		return db.rawQuery("SELECT * FROM Trans", null);
	}
	
	public static ITransaction find(long id) {
		SQLiteDatabase db=MyApplication.instance().getDbHelper().getReadableDatabase();
		Cursor c=db.rawQuery("SELECT * FROM Trans WHERE _id="+id, null);
		Map<String, Object> row=DBUtils.convertFirst(c);
		Transaction res=new Transaction(((Long)row.get("_id")).intValue(), UUID.fromString(row.get("uid").toString()), ((Long)row.get("account")).intValue(), BigDecimal.valueOf((Double)row.get("amount")), Timestamp.valueOf((String)row.get("time")), (String)row.get("comment"), (String)row.get("type"));
		return res;
	}
}
