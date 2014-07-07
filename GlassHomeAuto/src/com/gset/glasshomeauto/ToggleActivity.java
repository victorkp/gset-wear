package com.gset.glasshomeauto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.glass.view.WindowUtils;
public class ToggleActivity extends Activity {
	
	private enum Stage {
		Start, Option1, Option2, End
	}

	private Stage mCurrentStage;
	
	private TextView mText1;
	
	/*
	private TextView mText2;
	private TextView mText3;
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_toggle);
		
		/*
		mText1 = (TextView) findViewById(R.id.text1);
		mText1.setText("Light Toggle");
		mText2 = (TextView) findViewById(R.id.text2);
		mText2.setText("TV Toggle");
		mText3 = (TextView) findViewById(R.id.text3);
		mText3.setText("A/C Toggle");
		*/
		
		mCurrentStage = Stage.Start;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.toggle, menu);
		return true;
	}
	/*
	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			getMenuInflater().inflate(R.menu.toggle, menu);
			return true;
		}

		return super.onCreatePanelMenu(featureId, menu);
	}
	*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			openOptionsMenu();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	    if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL) {
	    	if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL) {
				int id = item.getItemId();
				switch(id)	{
				case R.id.light_toggle_menu:
					//mText1.setText(getString(R.string.light_toggle));
					Intent streamIntent = new Intent(ToggleActivity.this, LiveStreamingActivity.class);
					startActivity(streamIntent);
					break;
				case R.id.tv_toggle_menu:
					//mText1.setText(getString(R.string.tv_toggle));
					Intent streamIntent2 = new Intent(ToggleActivity.this, LiveStreamingActivity.class);
					startActivity(streamIntent2);
					break;
				case R.id.ac_toggle_menu:
					//mText1.setText(getString(R.string.ac_toggle));
					Intent streamIntent3 = new Intent(ToggleActivity.this, LiveStreamingActivity.class);
					startActivity(streamIntent3);
					break;
	    	//FILL IN IF STATEMENTS HERE
	    }
	    	}
	    return super.onMenuItemSelected(featureId, item);
	    }
		return false;
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
	
	

}
