package com.example.beats_by_296;

//import com.example.nativeaudio.R;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	
	MediaPlayer mediaPlayer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((Button) findViewById(R.id.play)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            	if (mediaPlayer == null || mediaPlayer.isPlaying() == false) {
            		mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.background);
            		mediaPlayer.start(); // no need to call prepare(); create() does that for you
            	}
            	
            }
        });
		
		((Button) findViewById(R.id.stop)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            	if (mediaPlayer != null && mediaPlayer.isPlaying() ) {
            		mediaPlayer.stop();
            		mediaPlayer = null;
            	}
            	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
