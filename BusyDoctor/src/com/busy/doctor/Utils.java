package com.busy.doctor;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.Display;

public class Utils {

	
	public static Boolean isNetAvailable(Context act)  {
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager)act.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
                return true;
            }
            else {
            	return false;
			}
        }
        catch(Exception e){
           e.printStackTrace();
        }
		return false;
    }
	
	/**
	 * Method to check Null values inside json object
	 * 
	 * @param parent
	 * @param name
	 * @author hari
	 * @return
	 */
	public static boolean isJsonFromObjectNotNull(JSONObject parent, String name) {
		if (parent.isNull(name)) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressLint("NewApi")
	public static int getScreenHeight(Activity context) {

		Display display = context.getWindowManager().getDefaultDisplay();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			display.getSize(size);
			return size.y;
		}
		return display.getHeight();
	}
	
	/**
	 * Method to check Null values inside json array
	 * 
	 * @param parent
	 * @param name
	 * @author hari
	 * @return
	 */

	public static boolean isJsonFromArrayNotNull(JSONArray parent, int index) {
		if (parent.isNull(index)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
     * Dismisses the alert dialog and stays on the last screen
     * 
     * @param act
     */
    public static void showError(final Context act){
    	
    	AlertDialog.Builder alertDialog=new AlertDialog.Builder(act);
    	alertDialog.setTitle("Oops");
    	alertDialog.setMessage("Something went wrong!");
    	alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
    		
    	AlertDialog ad=alertDialog.create();
    	ad.show();
    	
    }
    
    /**
     * Dismisses the alert dialog and stays on the last screen 
     * 
     * @param msg
     * @param act
     * @param title
     */
    public static void showErrorAlert(final String msg, final Activity act,final String title){
    	AlertDialog.Builder alertDialog=new AlertDialog.Builder(act);
    	alertDialog.setTitle(title);
    	alertDialog.setMessage(msg);
    	alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
    		
    	AlertDialog ad=alertDialog.create();
    	ad.setOwnerActivity(act);
    	ad.show();
    }
	
}
