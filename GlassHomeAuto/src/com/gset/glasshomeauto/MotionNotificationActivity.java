package com.gset.glasshomeauto;

import java.util.Date;

import com.gset.glasshomeauto.network.States;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MotionNotificationActivity extends Activity {

	private static TextView time;
	public static String timeStamp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_motion_notification);
		time = (TextView) findViewById(R.id.tvMotionNotification);
		Date d = new Date(States.getMotion());
	    timeStamp = "Motion Detected:\n"+d.toString();
	    time.setText(timeStamp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.motion_notification, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		time = (TextView) findViewById(R.id.tvMotionNotification);
		Date d = new Date(States.getMotion());
	    timeStamp = "Motion Detected:\n"+d.toString();
	    time.setText(timeStamp);
	}

}
