package hu.engard.xpenses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_add_transaction: {
        Intent intent = new Intent(this, AddTransactionActivity.class);
        startActivity(intent);
        return true;
      }
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
    public void showTransactions(View view) {
    	Intent intent = new Intent(this, TransactionListActivity.class);
    	startActivity(intent);
    }
    
    public void doTest(View view) {
    	MyApplication.instance().getSyncManager().fullSync(this);
//    	deleteDatabase(XpensesDbHelper.DATABASE_NAME);
//    	SQLiteDatabase db=new XpensesDbHelper().getReadableDatabase();
//    	Intent intent = new Intent(this, TransactionListActivity.class);
//    	startActivity(intent);
    }
}
