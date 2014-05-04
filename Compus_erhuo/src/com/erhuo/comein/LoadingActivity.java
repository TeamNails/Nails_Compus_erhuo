package com.erhuo.comein;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import com.erhuo.main.MainActivity;
import com.erhuo.main.R;

public class LoadingActivity extends Activity {
	private static int user = 0;
	private String useridString;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		Intent intent = super.getIntent();
		useridString = intent.getStringExtra("userid2");
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run(){
				SharedPreferences preferences = getSharedPreferences("count",MODE_WORLD_READABLE);
				int count=preferences.getInt("count",0);
				if(count==0){
					Intent intent = new Intent (LoadingActivity.this,Whatsnew.class);		
					 intent.putExtra("userid2", useridString);
					startActivity(intent);			
					LoadingActivity.this.finish();
				}else{
					Intent intent = new Intent (LoadingActivity.this,MainActivity.class);	
					 intent.putExtra("userid2", useridString);
					startActivity(intent);			
					LoadingActivity.this.finish();
				}
				Editor editor=preferences.edit();
				editor.putInt("count", ++count);
				editor.commit();
			}
		}, 200);
	}
}