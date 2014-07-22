package com.gset.glasshomeauto;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.glass.media.Sounds;

public class TaskActivity extends Activity {
	
	private enum Stage {
		Start, TaskMenu, AddTask, TimeLocation, SetHours, SetMins1, SetMins2, AM_PM, Confirm
	}
	
	private Stage mCurrentStage;
	
	private TextView mText;
	
	private int toggleID;
	private String toggleText;
	private int hour, min;
	private boolean am;
	private boolean set;
	private String fileText;
	private File dir;
	private int fileNum = 0;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	// Don't let the screen go to sleep
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_task);
        
        mText = (TextView) findViewById(R.id.text);
        dir = getFilesDir();
        
        mCurrentStage = Stage.Start;
        mText.setText(getString(R.string.menu));
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
			switch(mCurrentStage) {
			case Start:
				// We currently showing the start screen,
				// On tap we should show the next step: option1
				mCurrentStage = Stage.TaskMenu;
				playClickSound();
				break;
			case TaskMenu:
				// We are currently showing the option1 screen
				// On tap we should show the option1 menu to give the
				// user a choice
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case AddTask:
				//Show the menu for the toggles and pick one
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case TimeLocation:
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case SetHours:
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case SetMins1:
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case SetMins2:
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case AM_PM:
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case Confirm:
				playSuccessSound();
				File[] files = dir.listFiles();
				for (int i=0; i<files.length; i++) {
					Log.i("files", files[i].getName());
					if (Integer.parseInt(files[i].getName().substring(4)) != i) {
						Log.i("file number", files[i].getName().substring(4) );
						fileNum = i;
						break;
					}
				}
				if (fileNum == 0) {
					fileNum = files.length;
				}
				File file = new File(this.getFilesDir(), "task" + fileNum);
				try {
					FileOutputStream outputStream = openFileOutput("task" + fileNum, Context.MODE_PRIVATE);
					/*if (fileText == null) {
						fileText = toggleID + " - " + hour + ":";
						if (min < 10) {
							fileText = fileText + "0" + min;
						}
						else {
							fileText = fileText + min;
						}
						if (am) {
							fileText = fileText + " AM";
						}
						else {
							fileText = fileText + " PM";
						}
					}*/
					outputStream.write(fileText.getBytes());
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				finish();
			default:
				break;
			}
			
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		if(mCurrentStage == Stage.TaskMenu){
			// If we're on the color option, the menu should have
			// the colors on it
			getMenuInflater().inflate(R.menu.task_menu, menu);
		}
		else if (mCurrentStage == Stage.AddTask) {
			getMenuInflater().inflate(R.menu.toggle, menu);
		}
		else if (mCurrentStage == Stage.TimeLocation) {
			getMenuInflater().inflate(R.menu.time_location, menu);
		}
		else if(mCurrentStage == Stage.SetHours) {
			getMenuInflater().inflate(R.menu.set_hours, menu);
		}
		else if(mCurrentStage == Stage.SetMins1) {
			getMenuInflater().inflate(R.menu.set_mins1, menu);
		}
		else if(mCurrentStage == Stage.SetMins2){
			getMenuInflater().inflate(R.menu.set_mins2, menu);
		}
		else if(mCurrentStage == Stage.AM_PM) {
			getMenuInflater().inflate(R.menu.am_pm, menu);
		}
		return true;
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.addTask:
			//some stuff
			mCurrentStage = Stage.AddTask;
			setText(getString(R.string.add_tasks_stage));
			return true;
		case R.id.manageTasks:
			Log.i("made it", "go to delete task");
			Intent manageIntent = new Intent(this, ManageActivity.class);
			startActivity(manageIntent);
			return true;
			
		case R.id.light_toggle_menu: //assign lights a number 0
			toggleID = 0;
			toggleText = (String) item.getTitle();
			mCurrentStage = Stage.TimeLocation;
			setText(getString(R.string.time_location));
		case R.id.ac_toggle_menu: //assign ac a number 1
			toggleID = 1;
			toggleText = (String) item.getTitle();
			mCurrentStage = Stage.TimeLocation;
			setText(getString(R.string.time_location));
			return true;
			
		case R.id.stime:
			mCurrentStage = Stage.SetHours;
			setText(getString(R.string.hour));
			return true;
		case R.id.ctime:
			mCurrentStage = Stage.Confirm;
			Calendar c = Calendar.getInstance();
			Date d = (new java.util.Date(System.currentTimeMillis()));
			/*c.setTime(d);
			if (c.get(Calendar.HOUR_OF_DAY) > 11) {
				am = false;
				setText(toggleText + " - " + hour + ":" + min + " PM");
			}
			else {
				am = true;
				setText(toggleText + " - " + hour + ":" + min + " AM");
			}
			hour = c.get(Calendar.HOUR_OF_DAY) % 12;
			if (hour == 0) {
				hour = 12;
			}
			min = c.get(Calendar.MINUTE);*/
			SimpleDateFormat stf = new SimpleDateFormat("h:mm a");
			fileText = toggleID + " - " + stf.format(d);
			setText(toggleText + " - " + stf.format(d));
			return true;
			
		case R.id.h1:
			hour = 1;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h2:
			hour = 2;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h3:
			hour = 3;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h4:
			hour = 4;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h5:
			hour = 5;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h6:
			hour = 6;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h7:
			hour = 7;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h8:
			hour = 8;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h9:
			hour = 9;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h10:
			hour = 10;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h11:
			hour = 11;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
		case R.id.h12:
			hour = 12;
			mCurrentStage = Stage.SetMins1;
			setText(getString(R.string.min1));
			return true;
			
		case R.id.mzero:
			min = 0;
			mCurrentStage = Stage.SetMins2;
			setText(getString(R.string.min2));
			return true;
		case R.id.m10:
			min = 10;
			mCurrentStage = Stage.SetMins2;
			setText(getString(R.string.min2));
			return true;
		case R.id.m20:
			min = 20;
			mCurrentStage = Stage.SetMins2;
			setText(getString(R.string.min2));
			return true;
		case R.id.m30:
			min = 30;
			mCurrentStage = Stage.SetMins2;
			setText(getString(R.string.min2));
			return true;
		case R.id.m40:
			min = 40;
			mCurrentStage = Stage.SetMins2;
			setText(getString(R.string.min2));
			return true;
		case R.id.m50:
			min = 50;
			mCurrentStage = Stage.SetMins2;
			setText(getString(R.string.min2));
			return true;
			
		case R.id.m0:
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m1:
			min++;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m2:
			min+=2;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m3:
			min+=3;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m4:
			min+=4;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m5:
			min+=5;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m6:
			min+=6;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m7:
			min+=7;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m8:
			min+=8;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
		case R.id.m9:
			min+=9;
			mCurrentStage = Stage.AM_PM;
			setText(getString(R.string.ampm));
			return true;
			
		case R.id.am:
			am = true;
			mCurrentStage = Stage.Confirm;
			fileText = toggleID + " - " + hour + ":";
			if (min < 10) {
				fileText = fileText + "0";
			}
			fileText = fileText + min + " AM";
			String s;
			switch(toggleID) {
			case 0:
				s = "Lights";
				break;
			case 1:
				s = "AC";
				break;
			default:
				s = "";
				break;
			}
			setText(s + fileText.substring(1));
			return true;
		case R.id.pm:
			am = false;
			mCurrentStage = Stage.Confirm;
			fileText = toggleID + " - " + hour + ":";
			if (min < 10) {
				fileText = fileText + "0";
			}
			fileText = fileText + min + " PM";
			setText(fileText);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}	
	}	
	
	protected void setText(String text){
		mText.setText(Html.fromHtml(text));
		mText.setTranslationY(mText.getHeight());
		mText.setAlpha(0f);
		mText.animate().translationY(0).alpha(1f).start();
	}
    
	/**
	 * Play the standard Glass tap sound
	 */
	protected void playClickSound() {
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audio.playSoundEffect(Sounds.TAP);
	}

    	/**
	 * Play the standard Glass tap sound
	 */
	protected void playSuccessSound() {
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audio.playSoundEffect(Sounds.SUCCESS);
	}
}
