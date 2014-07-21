package com.gset.glasshomeauto;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class LiveStreamingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_streaming);
		Uri stream = Uri.parse("rtsp://ec2-54-88-189-105.compute-1.amazonaws.com/live/gset-wear");
		Intent videointent = new Intent(Intent.ACTION_VIEW,stream); 
		startActivity(videointent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.live_streaming, menu);
		return true;
	}

}
