package com.praveenmax.sheetmusic;


/*
	Author : Praveenmax
	github : github.com/praveenmax

*/

import java.util.HashMap;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {
	
	private static final String APP_TAG="SheetMusic";
	private float correctAnswer, inCorrectAnswer, totalQuestions=1f;
	private int percent;
	private long timeLimit=60000;
	private boolean isR=true; 
	private Button bt_c,bt_d,bt_e,bt_f,bt_g,bt_a,bt_b;
	private TextView tv_correct,tv_incorrect,tv_percent,tv_timer;
	
	private MediaPlayer mediaPlayButtonClick, mediaPlayBG;
	private ImageView imageViewStaffSheet;
	private HashMap<String,Integer> staffSheetIdHashMap=new HashMap<String, Integer>(9);
	
	private String[] staffSheetTagNames = {"staffsheet_a","staffsheet_b","staffsheet_c",
								 "staffsheet_d","staffsheet_e","staffsheet_e1",
								 "staffsheet_f","staffsheet_f1","staffsheet_g"};
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_activity);

        mediaPlayButtonClick= MediaPlayer.create(this, R.raw.menuitem_select);
        mediaPlayBG= MediaPlayer.create(this, R.raw.bgsound_game);
        mediaPlayBG.setLooping(true);
        
        imageViewStaffSheet=(ImageView) findViewById(R.id.staffsheet);

        bt_c=(Button)findViewById(R.id.bt_c);
        bt_c.setOnClickListener(this);
        bt_d=(Button)findViewById(R.id.bt_d);
        bt_d.setOnClickListener(this);
        bt_e=(Button)findViewById(R.id.bt_e);
        bt_e.setOnClickListener(this);
        bt_f=(Button)findViewById(R.id.bt_f);
        bt_f.setOnClickListener(this);       
        bt_g=(Button)findViewById(R.id.bt_g);
        bt_g.setOnClickListener(this); 
        bt_a=(Button)findViewById(R.id.bt_a);
        bt_a.setOnClickListener(this); 
        bt_b=(Button)findViewById(R.id.bt_b);
        bt_b.setOnClickListener(this);         
        
        tv_correct=(TextView)findViewById(R.id.tv_correct);
        tv_incorrect=(TextView)findViewById(R.id.tv_incorrect);
        tv_percent=(TextView)findViewById(R.id.tv_percent);
        tv_timer=(TextView)findViewById(R.id.tv_timer);
        
        
       staffSheetIdHashMap.put("staffsheet_a", R.drawable.staffsheet_a);
       staffSheetIdHashMap.put("staffsheet_b", R.drawable.staffsheet_b);
       staffSheetIdHashMap.put("staffsheet_c", R.drawable.staffsheet_c);
       staffSheetIdHashMap.put("staffsheet_d", R.drawable.staffsheet_d);
       staffSheetIdHashMap.put("staffsheet_e", R.drawable.staffsheet_e);
       staffSheetIdHashMap.put("staffsheet_e1", R.drawable.staffsheet_e1);
       staffSheetIdHashMap.put("staffsheet_f", R.drawable.staffsheet_f);
       staffSheetIdHashMap.put("staffsheet_f1", R.drawable.staffsheet_f1);
       staffSheetIdHashMap.put("staffsheet_g", R.drawable.staffsheet_g);
        
        new CountDownTimer(timeLimit, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
            	showGameOverToast();
            	isR=false;
            }
         }.start();
         
         setNewStaffSheet();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	if(mediaPlayBG!=null)
    	{
    		mediaPlayBG.start();
    	}
    }
    
    @Override
    public void onClick(View view) 
    {   
    	if(isR)
    	{
	    	playMenuItemAudio();

	        
	        if(isCorrectAnswer(((Button)view).getTag().toString()))
	        {
	        	Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
	        }
	        else
	        {
	        	Toast.makeText(this, "Incorrect Answer! Try Again", Toast.LENGTH_SHORT).show();
	        }
    	}
    }
    
	public void onPause()
	{
		super.onPause();
		
		if(mediaPlayButtonClick!=null)
		{
			mediaPlayButtonClick.release();
			mediaPlayButtonClick=null;
		}
		
		if(mediaPlayBG!=null)
		{
			mediaPlayBG.release();
			mediaPlayBG=null;
		}
		
		Log.d(APP_TAG,"Resources released");
	}

	private void playMenuItemAudio() {

		if(mediaPlayButtonClick!=null)
		{
			mediaPlayButtonClick.start();
		}
	}
	
	private void setNewStaffSheet()
	{
		String newStaffSheet = staffSheetTagNames[(int)(Math.floor(Math.random()*(staffSheetTagNames.length-1)))];
		int newStaffSheetID=staffSheetIdHashMap.get(newStaffSheet);
		imageViewStaffSheet.setImageResource(newStaffSheetID);
		imageViewStaffSheet.setTag(""+newStaffSheet);
		
		if(newStaffSheetID==R.drawable.staffsheet_e1)
		{
			bt_e.setTag("staffsheet_e1");
			Log.d(APP_TAG,"Updated the TAG for E button to E1");
		}
		else
		{
			bt_e.setTag("staffsheet_e");
			Log.d(APP_TAG,"Updated the TAG for E button back to E");
		}
		
		if(newStaffSheetID==R.drawable.staffsheet_f1)
		{
			bt_f.setTag("staffsheet_f1");
			Log.d(APP_TAG,"Updated the TAG for F button to F1");
		}
		else
		{
			bt_f.setTag("staffsheet_f");
			Log.d(APP_TAG,"Updated the TAG for F button back to F");
		}
		
	}
	
	private boolean isCorrectAnswer(String selectedAnswer)
	{
		
        Log.d(APP_TAG,"selectedAnswer : " + selectedAnswer);
        Log.d(APP_TAG,"expectedAnswer : "+  imageViewStaffSheet.getTag().toString());
		if(selectedAnswer.equals(imageViewStaffSheet.getTag().toString()))
		{
			correctAnswer++;
			tv_correct.setText("Correct : "+(int)correctAnswer);
			
			reCalculatePercent();
			
			setNewStaffSheet();
			
			totalQuestions++;
			
			return true;
		}
		else
		{
			inCorrectAnswer++;
			tv_incorrect.setText("Incorrect : "+(int)inCorrectAnswer);
			
			reCalculatePercent();
			
			
			return false;
		}
	}
	
	private void reCalculatePercent()
	{
		Log.d(APP_TAG, correctAnswer+" , "+inCorrectAnswer+" , "+totalQuestions);
		percent =  (int) (((correctAnswer-inCorrectAnswer)/totalQuestions)*100);
		tv_percent.setText("Percent : "+percent+"%");
	}
	
	private void showGameOverToast()
	{
    	Toast.makeText(this, "Time Up. Your Percent : "+percent+"%", Toast.LENGTH_LONG).show();
	}
}
