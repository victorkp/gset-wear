/**
 * 
 */
package com.gset.glasshomeauto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Roland
 *
 */
public class BootUpReceiver extends BroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context, ToggleGetService.class);
        context.startService(serviceIntent);
	}

}
