/**
 * 
 */
package com.gset.glasshomeauto;

import com.gset.glasshomeauto.network.States;
import com.gset.glasshomeauto.network.ToggleGetTask;
import com.gset.glasshomeauto.network.ToggleJsonParser;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Roland
 * Polls the server for new motion detected
 */
public class ToggleGetService extends Service implements ToggleGetTask.OnToggleGetListener {

	public class MyBinder extends Binder	{}
	
	private final MyBinder mbinder = new MyBinder();
	private ToggleGetTask tgt;
	private long lastMotion = -1;
	private Context context = this;

	@Override
	public IBinder onBind(Intent intent) {
		return mbinder;
	}

	@Override
	public void onCreate(){
		// Do some small init stuff if needed
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d("INFO", "Started thread");
		new Thread(new Runnable(){
			public void run() {
				while(true)
			    {
					tgt = new ToggleGetTask();
					tgt.setListener((ToggleGetTask.OnToggleGetListener) context);
					tgt.execute();
					if(lastMotion < 0)	{
						lastMotion = States.getMotion();
					}
					else if(lastMotion != States.getMotion())	{
						lastMotion = States.getMotion();
						Log.i("TOGGLESERVICE", "New Motion time: "+lastMotion+" millis");
						Toast.makeText(context, "New Motion time: "+lastMotion+" millis", Toast.LENGTH_LONG).show();
					}
					Log.i("TOGGLESERVICE", "Service checking");
			       try {
			    	   Thread.sleep(5 * 60 * 1000);
			    	   //Thread.sleep(60*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			       //REST OF CODE HERE//
			    }
		    }
		}).start();
		
		return START_STICKY;
	}

	@Override
	public void onDestroy(){
		// Stop doing stuff, if needed
	}
	
	@Override
	public void onReceiveStates(boolean success, String response) {
		Log.i("TOGGLESERVICE", "Success: "+success+"/Response: "+response);
		if(success)	{
			ToggleJsonParser tjp = new ToggleJsonParser(response);
			States.setLights(tjp.getLights());
			States.setA_c(tjp.getA_C());
			States.setMotion(tjp.getMotion());
			
			Log.i("TOGGLESERVICE", "LIGHTS: "+States.isLights()+" AC: "+States.isA_c()+" Motion: "+States.getMotion());
		}
	}

}
