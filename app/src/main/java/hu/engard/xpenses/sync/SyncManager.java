package hu.engard.xpenses.sync;

import hu.engard.xpenses.MyApplication;
import hu.engard.xpenses.dao.AbstractDao;
import hu.engard.xpenses.dao.AccountDao;
import hu.engard.xpenses.dao.TagDao;
import hu.engard.xpenses.dao.TransDao;
import hu.engard.xpenses.model.XpensesDbHelper;
import hu.engard.xpenses.util.ModalDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SyncManager {
	private static final String serverHost="192.168.0.2:8080";
	private static final String SERVER_UUID_KEY = "serverUUID";
	private static final String LAST_ANCHOR_KEY ="lastAnchor";

	private static final String serverUUID="f720da70-a4b8-11e3-a5e2-0800200c9a66";

	private static ObjectMapper mapper=new ObjectMapper();
	private static AccountDao accountDao=new AccountDao();
	private static TagDao tagDao=TagDao.instance();
	private static TransDao transDao=new TransDao();

	//	private static final String CMD_FULL_SYNC="fullSync";
	//	private static final String CMD_GET_CHANGES="getChanges";
	//	private static final String CMD_SEND_CHANGES="sendChanges";

	public SyncManager() {
	}

	// Wipe off everything in the DB, and download everything from server.
	public void fullSync(Context context) {
		this.context=context;
		Log.i("fullSync", "called");
		if (!isNetAvailable()) {
			Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
			return;
		}
		ModalDialog.areYouSure(context, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which==DialogInterface.BUTTON_POSITIVE) fullSyncConfirmed(); 
			}
		});
	}

	private void fullSyncConfirmed() {
		lastCmd=Command.FULL_SYNC;
		pDialog=ProgressDialog.show(context, "Full sync...", "Please wait", true,false);
		new JsonDownloaderTask("GET", "http://"+serverHost+"/expenses/rest/sync/fullSync", null).execute();
	}

	private Context context;
	private ProgressDialog pDialog;
	private enum Command {FULL_SYNC};
	private Command lastCmd;

	private static int counter=0;
	
	private void doFullSync(String response) {
		if (response.length()==0) return;
		try {
			JsonNode root=mapper.readTree(response);
			Log.i("SyncManager", "Recreating database");
			MyApplication.instance().deleteDb();
			if (counter++>2) {
				Toast.makeText(context, "Counter", Toast.LENGTH_SHORT).show();
				counter=0;
				return;
			}
			SQLiteDatabase db=MyApplication.instance().getDbHelper().getWritableDatabase();
			Log.i("SyncManager", "DB created. Server uuid: "+root.path(SERVER_UUID_KEY).textValue());
			MyApplication.instance().getPrefs().edit().putString(MyApplication.PREF_SERVER_UUID, root.path(SERVER_UUID_KEY).textValue());
			for (JsonNode node : root.path("accounts")) {
				Log.i("SyncManager", "node: "+node);
				accountDao.insert(node);
			}
			for (JsonNode node : root.path("tags")) {
				Log.i("SyncManager", "node: "+node);
				tagDao.insert(node);
			}
			for (JsonNode node : root.path("transactions")) {
				Log.i("SyncManager", "node: "+node);
				transDao.insert(node);
			}
		} catch (JsonProcessingException e) {
			Log.e("SyncManager", "Bad answer for fullSync from server", e);
		} catch (IOException e) {
			Log.e("SyncManager", "IO exception", e);
		}
	}


	private void processResponse(String response) {
		switch (lastCmd) {
		case FULL_SYNC:
			doFullSync(response);
			break;
		default:
			break;
		}
		if (pDialog!=null) {
			pDialog.dismiss();
			pDialog=null;
		}
	}

	public boolean isNetAvailable() {
		ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	/**
	 * Async task for calling the REST service.
	 * Calls back the processResponse() with the response content.
	 *
	 */
	private class JsonDownloaderTask extends AsyncTask<String, Void, String> {
		//	    private final WeakReference<ImageView> imageViewReference;
		private String method;
		private String url;
		private byte[] requestBody;
		private String error;

		public JsonDownloaderTask(String method, String url, byte[] requestBody) {
			super();
			this.url = url;
			this.requestBody = requestBody;
			this.method=method;
		}

		@Override
		protected String doInBackground(String... params) {
			Log.i("JsonDownloaderTask", "doInBackground");
			String response=downloadHttp(method, url, requestBody);
//			Log.i("JsonDownloaderTask", "result: "+response);
			return response;
		}

		@Override
		// Once the image is downloaded, associates it to the imageView
		protected void onPostExecute(String result) {
			if (error!=null) {
				Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
			}
			if (isCancelled()) result="";
			processResponse(result);
		}

		private String downloadHttp(String method, String urlString, byte[] requestBody) {
			Authenticator.setDefault(new HttpAuthenticator(
					MyApplication.instance().getPrefs().getString(MyApplication.PREF_USERNAME, ""),
					MyApplication.instance().getPrefs().getString(MyApplication.PREF_PASSWORD, "")));
			InputStream is = null;
			try {
				//				Log.i("downloadFullSync", "Opening URL: "+urlString);
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(5000 /* milliseconds */);
				conn.setConnectTimeout(5000 /* milliseconds */);
				conn.setRequestMethod(method);
				conn.setDoInput(true);
				Log.i("JsonDownloaderTask", "1");
				if (requestBody!=null) {
					conn.setDoOutput(true);
					OutputStream out=conn.getOutputStream();
					out.write(requestBody);
				} else {
					Log.i("JsonDownloaderTask", "2");
					conn.connect();
				}
				Log.i("JsonDownloaderTask", "3");
				int http_status = conn.getResponseCode();
				Log.i("JsonDownloaderTask", "4");
				Log.d("SyncManager", "The status is: " + http_status);
				if (http_status / 100 != 2) {
					error="http connection status "+http_status;
					return "";
				}
				is = conn.getInputStream();

				// Convert the InputStream into a string
				@SuppressWarnings("resource")
				Scanner scanner=new Scanner(is,"UTF-8");
				String contentAsString = scanner.useDelimiter("\\A").next();
				Log.d("SyncManager", "Response:\n" + contentAsString);
				return contentAsString;
			} catch (IOException e) {
				Log.i("downloadFullSync", "Error", e);
				error=e.toString();
				return "";
			} finally {
				if (is != null) {
					try { is.close(); } catch (IOException e) {}
				} 
			}
		}
	}

	public static class HttpAuthenticator extends Authenticator {
		HttpAuthenticator(String id, String password) {
			mID = id;
			mPassword = password;
		}
		private int mRetryCount = 0;
		private final int mMaxRetryCount = 3;
		private String mID;
		private String mPassword;
		protected PasswordAuthentication getPasswordAuthentication() {
			if (mRetryCount < mMaxRetryCount) {
				mRetryCount++;
				return new PasswordAuthentication(mID , mPassword.toCharArray());
			}
			return null;
		}
	}
}
