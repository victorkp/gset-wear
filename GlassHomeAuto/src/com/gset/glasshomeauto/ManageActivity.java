package com.gset.glasshomeauto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.glass.media.Sounds;

public class ManageActivity extends Activity {
	
	private enum Stage {
		Start, Tasks, Manage
	}
	
	private Stage mCurrentStage;
	
	private TextView mText;
	
	private File dir;
	private File selected;
	private File[] files;
	private ArrayList<String> menuNames = new ArrayList<String>();
	
	private int id;
	private Calendar time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage);
		dir = getFilesDir();
		
		mCurrentStage = Stage.Start;
		mText = (TextView) findViewById(R.id.text);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
			switch(mCurrentStage) {
			case Start:
				// We currently showing the start screen,
				// On tap we should show the next step: option1
				mCurrentStage = Stage.Tasks;
				playClickSound();
				break;
			case Tasks:
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case Manage:
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mCurrentStage == Stage.Tasks) {
			getMenuInflater().inflate(R.menu.manage_menu, menu);
			files = dir.listFiles();
			if(files.length == 0) {
				menu.add("Nothing to delete");
			}
			else {
				for (File f: files) {
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(f);
						byte[] b = new byte[100];
						for (int i=0; i<100; i++) {
							int val = fis.read();
							if(val >= 0) {
								b[i] = (byte) val;
							}
							else{
								break;
							}
						}
						String read = new String(b, "UTF-8");
						read = read.substring(0, 21);
						id = Integer.parseInt(read.substring(0, 1));
						SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
						time = Calendar.getInstance();
						try {
							time.setTime(sdf.parse(read.substring(4)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String s;
						switch(id) {
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
						s = s + " - " + sdf.format(time.getTime()) + " ";
						menu.add(s);
						menuNames.add(s);
					}
					catch(IOException e) {
						e.printStackTrace();
					}
					finally{
						try {
							if (fis != null)
								fis.close();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
		else if(mCurrentStage == Stage.Manage) {
			getMenuInflater().inflate(R.menu.modify_tasks_menu, menu);
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mCurrentStage == Stage.Tasks) {
			Log.i("task name", item.getTitle().toString().substring(0, 9));
			for (int i=0; i<menuNames.size(); i++) {
				Log.i("menu name", i + " - " + menuNames.get(i).substring(9));
				if(item.getTitle().toString().equals(menuNames.get(i))){
					Log.i("stuff", "happened");
					if (files.length == 0) {
						//Log.i("files", "Array is null");
					}
					else {
						//Log.i("files", "Array not null");
						selected = files[i];
						//Log.i("file", selected.toString());
					}
					mCurrentStage = Stage.Manage;
					setText("Delete the file?");
					return true;
				}
			}
		}
		else if (mCurrentStage == Stage.Manage) {
			switch(item.getItemId()) {
			case R.id.delete:
				if (selected == null) {
					//Log.i("no file", "no file");
				}
				else {
					selected.delete();
					finish();
				}
				break;
			default:
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
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
