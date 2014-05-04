package com.erhuo.userInfo;

import java.security.PublicKey;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.main.R;
import com.erhuo.message.message;

public class feedback extends Activity {
	@SuppressWarnings("unused")
	private EditText hobbyEditText=null;
	@SuppressWarnings("unused")
	private Button baocunButton=null;
	private TextView zhutiTextView,showTextView=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.feedback);
		notifym();
		baocunButton=(Button)findViewById(R.id.btn_baocun);
//		hobbyEditText=(EditText)findViewById(R.id.info);
//		zhutiTextView=(TextView)findViewById(R.id.zhutitv);
//		showTextView=(TextView)findViewById(R.id.showView);
//		zhutiTextView.setText("输入爱好");
//		showTextView.setText("写下你的爱好，系统将更好的为你推荐！");
//		Intent intent=super.getIntent();
//		 System.out.println("bbbbbbbbbbbbbbbbbbbbbbba1");
//		hobbyEditText.setText(intent.getStringExtra("hobby"));
	
//		new_NameEditText.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
	baocunButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				String nameString=(String)new_NameEditText.getText();
//				change_passward.this.getIntent().putExtra("rethobby",hobbyEditText.getText().toString().trim());
//				change_passward.this.setResult(RESULT_OK,change_passward.this.getIntent());
				Toast.makeText(getApplicationContext(), "您的反馈信息已经发布服务器上了！", Toast.LENGTH_LONG).show();
				feedback.this.finish();
				
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
	


}
	public void back_btn(View v){
		this.finish();
		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 feedback.this, 0, new Intent(feedback.this,message.class),0);
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
