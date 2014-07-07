package com.gset.glasshomeauto;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class TaskActivity extends Activity {
	
	private enum Stage {
		Start, TaskMenu
	}
	
	private Stage mCurrentStage;
	
	private TextView mText;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	// Don't let the screen go to sleep
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_task);
        
        mText = (TextView) findViewById(R.id.text);
        
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
		return true;
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.addTask:
			//some stuff
			return true;
		case R.id.task1:
			//more stuff
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
		//AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		//audio.playSoundEffect(Sounds.TAP);
	}

    	/**
	 * Play the standard Glass tap sound
	 */
	protected void playSuccessSound() {
		//AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		//audio.playSoundEffect(Sounds.SUCCESS);
	}
}
