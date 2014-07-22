package com.gset.glasshomeauto;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import android.os.Build;

public class LiveStreamingActivity extends Activity {

	private VideoView view1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_streaming);
		playVideo("rtsp://ec2-54-88-189-105.compute-1.amazonaws.com/live/gset-wear");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.live_streaming, menu);
		return true;
	}
	
	private void playVideo(String url) 
    {
		String link=url;
		Log.e("url",link);
		view1 = (VideoView) findViewById(R.id.myVideoView); 
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		MediaController mc = new MediaController(this); 
		mc.setMediaPlayer(view1); 

		view1.setMediaController(mc); 
		view1.setVideoURI(Uri.parse(link)); 
		view1.requestFocus(); 
		view1.start();

     }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}

}
