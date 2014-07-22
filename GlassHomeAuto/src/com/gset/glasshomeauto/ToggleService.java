package com.gset.glasshomeauto;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.gset.glasshomeauto.network.States;
import com.gset.glasshomeauto.network.TogglePutTask;

public class ToggleService extends Service {

	/**
	 * Ignore this binder stuff, we won't need it
	 */
	
	public class SampleBinder extends Binder { }

	private Bundle extras;
	private TogglePutTask tpt;
	
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
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		extras = intent.getExtras();
		Log.d("INFO", "Started toggle thread");
		tpt = new TogglePutTask();
		tpt.setListener(new TogglePutTask.OnTogglePutListener(){
			@Override
			public void onResponse(boolean success, String response) {
			}
		});
		new Thread(new Runnable(){
			public void run() {
				Log.i("INFO", ""+extras.getInt("task"));
				if (extras != null) {
					switch(extras.getInt("task")){
					case 0:
						Log.i("INFO", "lights");
						States.switchLights();
						break;
					case 1:
						Log.i("INFO", "ac");
						States.switchA_c();
						break;
					default:
						Log.e("INFO", "error, not valid task");
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
		
		return START_STICKY;
	}

	@Override
	public void onDestroy(){
		Log.i("INFO", "Ended Toggle Service");
		// Stop doing stuff, if needed
	}

}
