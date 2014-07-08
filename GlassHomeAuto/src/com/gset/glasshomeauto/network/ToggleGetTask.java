/**
 * 
 */
package com.gset.glasshomeauto.network;

import com.victor.kaiser.pendergrast.glass.notes.api.GetNotesTask.OnGetNotesListener;

import android.os.AsyncTask;

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
		// TODO Auto-generated method stub
		return null;
	}

}
