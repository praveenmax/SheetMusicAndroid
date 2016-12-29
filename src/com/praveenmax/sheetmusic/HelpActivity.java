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

public class HelpActivity extends Activity{

	private MediaPlayer mediaPlayMenuItem, mediaPlayMenubg;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayMenuItem= MediaPlayer.create(this, R.raw.menuitem_select);
        mediaPlayMenubg= MediaPlayer.create(this, R.raw.bgsound_menu);
        mediaPlayMenubg.setLooping(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.help_activity);
        
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
}
