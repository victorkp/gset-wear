/**
 * 
 */
package com.gset.glasshomeauto.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Roland
 * Sends the states of each toggle-able item
 */
public class TogglePutTask extends AsyncTask<String, Integer, Integer> {

	private static final String URL_TOGGLE_PUT = "https://glass-home-auto.appspot.com/_ah/api/toggle/v1/toggle_put";
	private String mResponse = "";//response from server
	private String mJSON = "";//turn into JSON, sent to server
	private OnTogglePutListener mListener;
	
	public static interface OnTogglePutListener {
		public void onResponse(boolean success, String response);
	}
	
	public void setListener(OnTogglePutListener listener) {
		mListener = listener;
	}

	public void setJSON(String json){
		mJSON = json;
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		try	{
			URL urlObject = new URL(URL_TOGGLE_PUT);

			HttpsURLConnection con = (HttpsURLConnection) urlObject.openConnection();
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			con.connect();
			OutputStreamWriter output = new OutputStreamWriter(con.getOutputStream());
			output.write(mJSON);
			output.flush();
			output.close();
			
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

}
