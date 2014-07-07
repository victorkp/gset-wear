package com.gset.glasshomeauto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.glass.view.WindowUtils;

/**
 * @author Roland (the most amazing person on earth)
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
	}
	
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		return super.onCreatePanelMenu(featureId, menu);
	}

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
			int id = item.getItemId();
			switch(id)	{
			case R.id.toggle:
				Intent toggleIntent = new Intent(MainActivity.this, ToggleActivity.class);
				startActivity(toggleIntent);
				break;
			case R.id.program:
				//Intent programIntent = new Intent(MainActivity.this, TaskActivity.class);
				//startActivity(streamIntent);
				break;
			case R.id.stream:
				Intent streamIntent = new Intent(MainActivity.this, LiveStreamingActivity.class);
				startActivity(streamIntent);
				break;
			}
	    }
		return super.onMenuItemSelected(featureId, item);
	}

}
