package com.erhuo.userInfo;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


import com.erhuo.app.util.Tools;
import com.erhuo.login.LoginActivity;
import com.erhuo.main.MainActivity;
import com.erhuo.main.R;
import com.erhuo.message.message;

public class out_login extends Activity {
	private Button exitButton;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.outlogin);
		notifym();
exitButton=(Button)findViewById(R.id.exit_Btn);
		
		exitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				 Intent intent=new Intent(out_login.this,LoginActivity.class);
				 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 /*intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);*/
				 startActivity(intent);
				/* ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
				 am.restartPackage(getPackageName());*/
			/*
				 out_login.this.finish();*/
				System.exit(0);  
			
				 
					
			}
		});
}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	public void back_btn(View v) {
	
			this.finish();
	}
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 out_login.this, 0, new Intent(out_login.this,message.class),0);
		 notification.setLatestEventInfo(getApplicationContext(),"新信息", "通知显示的内容", pendingIntent);
		
			notification.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止
			notification.defaults |= Notification.DEFAULT_SOUND; //默认声音	
		Tools.context=getApplicationContext();
			Tools.manager=manager;
		Tools.notification=notification;
		Tools.pendingIntent=pendingIntent;
	}
	
	@Override
	protected void onResume() {
		notifym();
		super.onResume();
	}
	
	}