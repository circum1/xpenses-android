package hu.engard.xpenses.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class ModalDialog {

	public static void areYouSure(Context ctx, DialogInterface.OnClickListener dialogClickListener) {
		Log.i("m", "ctx: "+ctx);
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
		    .setNegativeButton("No", dialogClickListener).show();
		}
	
//	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//	    @Override
//	    public void onClick(DialogInterface dialog, int which) {
//	        switch (which){
//	        case DialogInterface.BUTTON_POSITIVE:
//	            //Yes button clicked
//	            break;
//
//	        case DialogInterface.BUTTON_NEGATIVE:
//	            //No button clicked
//	            break;
//	        }
//	    }
//	};
}
