package com.erhuo.login;



import com.erhuo.comein.Whatsnew;
import com.erhuo.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;

 

public class Appstart extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.appstart);
		
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);   //全屏显示
		//Toast.makeText(getApplicationContext(), "孩子！好好背诵！", Toast.LENGTH_LONG).show();
		//overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
	
	new Handler().postDelayed(new Runnable(){
		@Override
		public void run(){
			Intent intent = new Intent (Appstart.this,LoginActivity.class);			
			startActivity(intent);	
			Appstart.this.finish();
		}
	}, 2000);
   }
}