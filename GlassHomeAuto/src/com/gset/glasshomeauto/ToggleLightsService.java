package com.gset.glasshomeauto;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.gset.glasshomeauto.network.States;
import com.gset.glasshomeauto.network.ToggleGetTask;
import com.gset.glasshomeauto.network.ToggleJsonParser;
import com.gset.glasshomeauto.network.TogglePutTask;

public class ToggleLightsService extends Service implements ToggleGetTask.OnToggleGetListener {

	/**
	 * Ignore this binder stuff, we won't need it
	 */
	
	public class SampleBinder extends Binder { }

	private final SampleBinder mSampleBinder = new SampleBinder();

	/**
	 * Ignore this binder stuff, we won't need it
	 */
	@Override
	public IBinder onBind(Intent intent){
		return mSampleBinder;
	}

	@Override
	public void onCreate(){
		// Do some small init stuff if needed
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		final Bundle extras = intent.getExtras();
		// Actually do the stuff
		// i.e. the motion checks every few minutes
		new Thread(new Runnable(){
			public void run() {
				TogglePutTask tpt = new TogglePutTask();
				tpt.setListener(new TogglePutTask.OnTogglePutListener(){
					@Override
					public void onResponse(boolean success, String response) {
						// TODO Auto-generated method stub
						
					}
				});
				Log.i("task", Integer.toString(extras.getInt("task")));
				if (extras != null) {
					switch(extras.getInt("task")){
					case 0:
						Log.i("toggle", "lights");
						States.switchLights();
						break;
					case 1:
						Log.i("toggle", "ac");
						States.switchA_c();
						break;
					default:
						Log.i("screwy", "screwy");
						break;
					}
				}
				String lights = "false";
				String ac = "false";
				if(States.isLights()) lights = "true";
				if(States.isA_c()) ac = "true";
				tpt.setJSON("{ \"lights\" : \"" + lights + "\"," +
						"\"ac\" : \"" + ac + "\"," +
						"\"motion\" : \"" + States.getMotion() + "\" } ");
				tpt.execute();
			}
		}).start();
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy(){
		// Stop doing stuff, if needed
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

}
