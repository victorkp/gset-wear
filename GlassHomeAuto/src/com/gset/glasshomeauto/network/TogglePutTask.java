/**
 * 
 */
package com.gset.glasshomeauto.network;

import android.os.AsyncTask;

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
		// TODO Auto-generated method stub
		return null;
	}

}
