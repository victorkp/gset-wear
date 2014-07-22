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
	
	private MenuItem lightText;
	private MenuItem acText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_toggle);
		
		mCurrentStage = Stage.Start;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.toggle, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//Log.i("INFO", "Executing tgt");
		tgt = new ToggleGetTask();
		tgt.setListener(this);
		tgt.execute();
		
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
		return super.onPrepareOptionsMenu(menu);
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
				Intent toggle = new Intent(this, ToggleService.class);
				switch(id)	{
				case R.id.light_toggle_menu:
					toggle.putExtra("task", 0);
					startService(toggle);
					Log.i("INFO", "hit light");
					if(States.isLights())
						lightText.setTitle("Lights: ON");
					else
						lightText.setTitle("Lights: OFF");
					break;
				case R.id.ac_toggle_menu:
					toggle.putExtra("task", 1);
					startService(toggle);
					if(States.isA_c())
						acText.setTitle("A/C: ON");
					else
						acText.setTitle("A/C: OFF");
					/*States.switchA_c();
					String lights1 = "false";
					String ac1 = "false";
					if(States.isLights()) lights1 = "true";
					if(States.isA_c()) ac1 = "true";
					tpt.setJSON("{ \"lights\": \"" + lights1 + "\"," +
							"\"ac\": \"" + ac1 + "\"," +
							"\"motion\": \"" + States.getMotion() + "\" } ");
					tpt.execute();*/
					break;
				default:
					Log.e("INFO", "error selecting task");	
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
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onReceiveStates(boolean success, String response) {
		Log.i("INFO", "Success: "+success+"/Response: "+response);
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
