package com.gset.glasshomeauto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.glass.view.WindowUtils;

import com.gset.glasshomeauto.network.*;

public class ToggleActivity extends Activity implements ToggleGetTask.OnToggleGetListener, TogglePutTask.OnTogglePutListener {
	
	private enum Stage {
		Start, Option1, Option2, End
	}

	private Stage mCurrentStage;
	
	private ToggleGetTask tgt;
	private TogglePutTask tpt;
	
	private MenuItem lightText;
	private MenuItem acText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_toggle);
		
		Log.i("INFO", "Executing tgt, tpt");
		tgt = new ToggleGetTask();
		tgt.setListener(this);
		tgt.execute();
		
		mCurrentStage = Stage.Start;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.toggle, menu);
		
		lightText = menu.findItem(R.id.light_toggle_menu);
		if(States.isLights())
			lightText.setTitle("Lights: ON");
		else
			lightText.setTitle("Lights: OFF");
		acText = menu.findItem(R.id.ac_toggle_menu);
		if(States.isA_c())
			acText.setTitle("A/C: ON");
		else
			acText.setTitle("A/C: OFF");
		
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
					tpt = new TogglePutTask();
					tpt.setListener(this);
					if(States.isLights())
						lightText.setTitle("Lights: ON");
					else
						lightText.setTitle("Lights: OFF");
					States.switchLights();
					tpt.setJSON("{\"lights\": \"" + States.isLights()
							+ "\", \"a_c\": \""+States.isA_c()
							+ "\", \"motion\": \""+States.getMotion()+"\"}");
					tpt.execute();
					break;
				case R.id.ac_toggle_menu:
					tpt = new TogglePutTask();
					tpt.setListener(this);
					if(States.isA_c())
						acText.setTitle("A/C: ON");
					else
						acText.setTitle("A/C: OFF");
					States.switchA_c();
					tpt.setJSON("{\"lights\": \"" + States.isLights()
							+ "\", \"a_c\": \""+States.isA_c()
							+ "\", \"motion\": \""+States.getMotion()+"\"}");
					tpt.execute();
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

	@Override
	public void onReceiveStates(boolean success, String response) {
		if(success)	{
			ToggleJsonParser tjp = new ToggleJsonParser(response);
			States.setLights(tjp.getLights());
			States.setA_c(tjp.getA_C());
			States.setMotion(tjp.getMotion());
			
			Log.i("INFO", "LIGHTS: "+States.isLights()+" AC: "+States.isA_c()+" Motion: "+States.getMotion());
		}
	}

	@Override
	public void onResponse(boolean success, String response) {
		if(success)	{
			;
		}
	}
	
	

}
