package hu.engard.xpenses;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.engard.xpenses.model.XpensesDbHelper;
import hu.engard.xpenses.sync.SyncManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MyApplication extends Application {
	public static final String PREF_USERNAME="username";
	public static final String PREF_PASSWORD="password";
	public static final String PREF_EMAIL="email";
	public static final String PREF_CLIENT_UUID="clientUUID";
	public static final String PREF_SERVER_UUID="serverUUID";
	
	public static MyApplication instance() {
		assert(myApplication!=null);
		return myApplication;
	}
	
	public void onCreate(){
        super.onCreate();
        myApplication=this;
        context = getApplicationContext();
        prefs = getSharedPreferences("hu.engard.xpenses.Prefs", Context.MODE_PRIVATE);
        if (!prefs.contains(PREF_CLIENT_UUID)) {
        	prefs.edit().putString(PREF_CLIENT_UUID, UUID.randomUUID().toString()).commit();
    		Log.i("MyApplication", "Generated new client UUID "+MyApplication.instance().getPrefs().getString(MyApplication.PREF_CLIENT_UUID, ""));
        }
        // TODO
       prefs.edit().putString(PREF_USERNAME, "fery").putString(PREF_PASSWORD, "x").putString(PREF_EMAIL, "x@x.hu").apply();
       dbHelper=new XpensesDbHelper();
    }

	private static MyApplication myApplication;
    private Context context;
	private SharedPreferences prefs;
	private SyncManager syncManager;
	private XpensesDbHelper dbHelper;
	private static ObjectMapper mapper=new ObjectMapper();

	public SyncManager getSyncManager() {
    	if (syncManager==null) syncManager=new SyncManager();
		return syncManager;
	}

	public SharedPreferences getPrefs() {
		return prefs;
	}

    public Context getContext() {
        return context;
    }

    public static ObjectMapper getMapper() {
		return mapper;
	}
    
	public XpensesDbHelper getDbHelper() {
		return dbHelper;
	}

	public void deleteDb() {
		dbHelper.close();
		deleteDatabase(XpensesDbHelper.DATABASE_NAME);
		dbHelper=new XpensesDbHelper();
	}
}