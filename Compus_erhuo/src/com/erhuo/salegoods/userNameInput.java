package com.erhuo.salegoods;




import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.forSale;
import com.erhuo.main.R;
import com.erhuo.message.message;

public class userNameInput extends Activity {
	@SuppressWarnings("unused")
	private EditText new_NameEditText=null;
	@SuppressWarnings("unused")
	private Button baocunButton=null;
	private TextView showTextView,zhutiTextView=null;
	private ImageView titleImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information_input);
		baocunButton=(Button)findViewById(R.id.btn_baocun);
		new_NameEditText=(EditText)findViewById(R.id.info);
		titleImageView=(ImageView)findViewById(R.id.zhutitv);
		titleImageView.setImageDrawable(getResources().getDrawable(R.drawable.inputusername));
		
		notifym();
		Intent intent=super.getIntent();
		 System.out.println("bbbbbbbbbbbbbbbbbbbbbbba1");
		new_NameEditText.setText(intent.getStringExtra("userName"));
		showTextView=(TextView)findViewById(R.id.showView);
		showTextView.setText("�������ַ���������ϵ");
	
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
			
					userNameInput.this.getIntent().putExtra("retuserName",new_NameEditText.getText().toString().trim());
					userNameInput.this.setResult(RESULT_OK,userNameInput.this.getIntent());
					userNameInput.this.finish();
			 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
	


}   public void back_btn(View v){

	 this.finish();
		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

}	

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	if (keyCode == KeyEvent.KEYCODE_BACK) {
		this.finish();
		overridePendingTransition( R.anim.right_left2,R.anim.right_left);
	}
	return super.onKeyDown(keyCode, event);
}
public void notifym() {
	//���֪ͨ������
	 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

	//����һ��֪ͨ����
	 Notification notification = new Notification(R.drawable.icon, "����Ϣ", System.currentTimeMillis()); 

	 PendingIntent pendingIntent = PendingIntent.getActivity( 
			 userNameInput.this, 0, new Intent(userNameInput.this,message.class),0);
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
