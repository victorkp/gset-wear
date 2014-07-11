/**
 * 
 */
package com.gset.glasshomeauto.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Roland
 * Gets the states of each toggle-able item
 */
public class ToggleGetTask extends AsyncTask<String, Integer, Integer> {
	
	private static final String URL_TOGGLE_GET = "https://glass-home-auto.appspot.com/_ah/api/toggle/v1/toggle_get";
	private String mResponse = "";//response from server
	private OnToggleGetListener mListener;

	public static interface OnToggleGetListener {
		public void onReceiveStates(boolean success, String response);
	}

	public void setListener(OnToggleGetListener listener) {
		mListener = listener;
	}
	@Override
	protected Integer doInBackground(String... params) {
		try	{
			Log.i("INFO", "doing in background");
			URL urlObj = new URL(URL_TOGGLE_GET);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("GET");
			int serverCode = con.getResponseCode();
			Log.i("INFO", "toggle get servercode: "+serverCode);
			if(serverCode != 200)	{
				//get errors
				BufferedReader r = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				for (String line = r.readLine(); line != null; line = r.readLine()) {
					Log.e("INFO", line);
				}
				return 0;//fail code
			}
			else	{
				BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
				for (String line = r.readLine(); line != null; line = r.readLine()) {
					mResponse += line;
				}
				Log.i("INFO", "toggle get response: "+mResponse);
				return 1;//success code
			}
		} catch(Exception e)	{
			e.printStackTrace();
			return 0;//fail code
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		callListener(result.intValue() == 1);
	}

	private void callListener(boolean success) {
		if (mListener != null) {
			mListener.onReceiveStates(success, mResponse);
		}
	}

}
