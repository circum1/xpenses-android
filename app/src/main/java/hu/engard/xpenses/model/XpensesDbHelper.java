package hu.engard.xpenses.model;

import hu.engard.xpenses.MyApplication;
import hu.engard.xpenses.R;
import hu.engard.xpenses.util.SQLiteScriptRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class XpensesDbHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Xpenses.db";

//	private String schema;
	
	public XpensesDbHelper() {
		super(MyApplication.instance().getContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		InputStream stream=MyApplication.instance().getContext().getResources().openRawResource(R.raw.expenses_schema);
		String schema;
		try {
			schema = new Scanner(stream,"UTF-8").useDelimiter("\\A").next();
			stream.close();
		} catch (IOException e) {
			Log.e("XpensesDbHelper", "Missing resource expenses_schema", e);
			throw new RuntimeException("Missing resource expenses_schema", e);
		}
		SQLiteScriptRunner.run(db, schema);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("XpensesDbHelper", "onUpgrade");
		// TODO
//		db.execSQL(DB_CLEAR_SCHEMA);
		onCreate(db);
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("XpensesDbHelper", "onDowngrade");
		onUpgrade(db, oldVersion, newVersion);
	}
}