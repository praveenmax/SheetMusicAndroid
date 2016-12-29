package com.praveenmax.sheetmusic;

/*
	Author : Praveenmax
	github : github.com/praveenmax

*/
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener{

	private MediaPlayer mediaPlayMenuItem, mediaPlayMenubg;
	private Button bt_newGame,bt_about,bt_help;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayMenuItem= MediaPlayer.create(this, R.raw.menuitem_select);
        mediaPlayMenubg= MediaPlayer.create(this, R.raw.bgsound_menu);
        mediaPlayMenubg.setLooping(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        bt_newGame=(Button)findViewById(R.id.Button0);
        bt_newGame.setOnClickListener(this);
        bt_about=(Button)findViewById(R.id.Button2);
        bt_about.setOnClickListener(this);
        bt_help=(Button)findViewById(R.id.Button1);
        bt_help.setOnClickListener(this);
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
		if(mediaPlayMenubg!=null)
		{
			mediaPlayMenubg.start();
		}
    }
    
    @Override
    public void onClick(View view) 
    {   
    	playMenuItemAudio();
    	
    	if(view==bt_newGame)
    	{
        	Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            
          //  finish();
    	}
    	
    	if(view==bt_help)
    	{
        	Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
    	}
    	
    	if(view==bt_about)
    	{
        	Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
    	} 
    }
    
	public void onPause()
	{
		super.onPause();
		
		if(mediaPlayMenuItem!=null)
		{
			mediaPlayMenuItem.release();
			mediaPlayMenuItem=null;
		}
		
		if(mediaPlayMenubg!=null)
		{
			mediaPlayMenubg.release();
			mediaPlayMenubg=null;
		}
		
		Log.d("SheetMusic","Resources released");
	}
	
	private void playMenuItemAudio() {

    		if(mediaPlayMenuItem!=null)
    		{
    			mediaPlayMenuItem.start();
    		}
	}
}
