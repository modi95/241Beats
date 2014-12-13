package com.akmodi2.beats241;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends Activity {
	static {
	    System.loadLibrary("ndk1");
	}
	private native String receiver(int sockfd);
	private native void sender(int sock_fd, String stdata);
	private native int buildClient(String URL, String PORT);
	
	String command = "";
	String[] lookup;
	int[] lookend;
	TextView bug;
	int sock;
	int nres=0;

	MediaPlayer mp;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(MainActivity.this, R.raw.reflektor);
        nres = R.raw.joan_of_arc;
        sock = buildClient(null, null);
        
        initlookup();
        mp = MediaPlayer.create(MainActivity.this, R.music.);
        
        new Thread(new Runnable() {
            public void syncher() {
                while(true)
                {
                	command = receiver(sock);
                	if (command.equals("play"))
                		play(null);
                	if (command.equals("pause"))
                		pause(null);
                	if (command.startsWith("add"))
                		add(command.split("add")[1]);
                }
            }

			@Override
			public void run() {
				syncher();
			}
        }).start();
        bug = (TextView)findViewById(R.id.debug);
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
    
    public void pause(View V)
    {
    	mp.pause();
    	bug.setText("USER-PAUSE");
    	Log.i("USER", "PAUSE");
    	sender(sock, "pause");
    }
    
    public void next(View V)
    {
    	mp.pause();
    	MediaPlayer pp;
    	pp = MediaPlayer.create(MainActivity.this, nres);
    	pp.start();
    	mp = pp;
    	bug.setText("USER-NEXT");
    	TextView tex = (TextView)findViewById(R.id.curr);
    	TextView pre = (TextView)findViewById(R.id.song1);
    	tex.setText(pre.getText());
    	pre.setText("empty");
    	sender(sock, "pause");
    }
    
    public void add(View v)
    {
    	bug.setText("USER-ADD");
    	//;
    	TextView tex = (TextView)findViewById(R.id.filePath);
    	TextView nex = (TextView)findViewById(R.id.song1);
    	String name = tex.getText().toString();
    	bug.setText("usr-"+name);
    	int test = 1;
    	test= look(name);
    	if (test == -1){
    		bug.setText("add-badname");
    		Log.i("add", name);
    		return;
    	}
    	if (test == -2){
    		bug.setText("add-oddity");
    		Log.i("add", name);
    		return;
    	}
    	nres = test;
    	nex.setText(name);
    	
    }
    
    public void addS(String a)
    {
    	nres = look(a);
    	TextView nex = (TextView)findViewById(R.id.song1);
    	bug.setText("SERVER-ADD");
    	nex.setText(a);
    }
    
    public void play(View V)
    {
    	//initlookup();
    	mp.start();
    	int dur = mp.getDuration();
    	TextView bug = (TextView)findViewById(R.id.debug);
    	bug.setText("USER-PLAY");
    	Log.i("USER", "PLAY");
    	sender(sock, "play");
    }
    
    public void initlookup()
    {
    	Log.i("look", "CREATING LOOKUP TABLES "+lookup.length);
    	int size = 20;
    	lookup = new String[size];
    	lookend = new int[size];
    	lookup[1] = new String("reflektor");
    	lookend[1] = R.raw.reflektor;
    	lookup[2] = new String("afterlife");
    	lookend[2] = R.raw.afterlife;
    	lookup[3] = new String("joan of arc");
    	lookend[3] = R.raw.joan_of_arc;
    	Log.i("look", "Lookup created "+lookup.length);
    }
    
    public int look(String comp)
    {
    	if (comp == null)
    	{
    		return -2;
    	}
    	for (int i=0; i<lookup.length; i++)
    	{
    		if (lookup[i] == null) return -1;
    		Log.i("look", "at "+lookup[i]);
    		if (comp.startsWith(lookup[i]))
    			return lookend[i];
    	}
    	return -2;
    }
    
}
