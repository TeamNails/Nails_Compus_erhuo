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

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.forSale;
import com.erhuo.main.R;
import com.erhuo.message.message;

public class about extends Activity {
	@SuppressWarnings("unused")
	private EditText hobbyEditText=null;
	@SuppressWarnings("unused")
	private Button baocunButton=null;
	private TextView zhutiTextView,showTextView=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		notifym();
	
//		hobbyEditText=(EditText)findViewById(R.id.info);
//		zhutiTextView=(TextView)findViewById(R.id.zhutitv);
//		showTextView=(TextView)findViewById(R.id.showView);
//		zhutiTextView.setText("���밮��");
//		showTextView.setText("д����İ��ã�ϵͳ�����õ�Ϊ���Ƽ���");
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
		

	


}
	public void back_btn(View v){
	

		this.finish();
		overridePendingTransition(R.anim.right_left2, R.anim.right_left);
	}
	
	public void notifym() {
		//���֪ͨ������
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//����һ��֪ͨ����
		 Notification notification = new Notification(R.drawable.icon, "����Ϣ", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 about.this, 0, new Intent(about.this,message.class),0);
		 notification.setLatestEventInfo(getApplicationContext(),"����Ϣ", "֪ͨ��ʾ������", pendingIntent);
		
			notification.flags|=Notification.FLAG_AUTO_CANCEL; //�Զ���ֹ
			notification.defaults |= Notification.DEFAULT_SOUND; //Ĭ������	
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
