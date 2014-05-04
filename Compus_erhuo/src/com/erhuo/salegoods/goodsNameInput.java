package com.erhuo.salegoods;

import java.security.PublicKey;

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

public class goodsNameInput extends Activity {
	private EditText new_NameEditText = null;
	private Button baocunButton = null;
	private TextView showTextView= null;
	ImageView titleImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information_input);
		notifym();
		baocunButton = (Button) findViewById(R.id.btn_baocun);
		new_NameEditText = (EditText) findViewById(R.id.info);
//		titleImageView = (ImageView) findViewById(R.id.zhutitv);
		titleImageView=(ImageView)findViewById(R.id.zhutitv);
		titleImageView.setImageDrawable(getResources().getDrawable(R.drawable.inputhuowuming));
		baocunButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.title_baocun_right));
		Intent intent = super.getIntent();
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbba1");
		new_NameEditText.setText(intent.getStringExtra("goodsName"));
		showTextView = (TextView) findViewById(R.id.showView);
		showTextView.setText("好名字可以让别人更容易记住");
	

		// new_NameEditText.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		baocunButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// String nameString=(String)new_NameEditText.getText();

			
					
			
					goodsNameInput.this.getIntent().putExtra("retgoodsName",
							new_NameEditText.getText().toString().trim());
					goodsNameInput.this.setResult(RESULT_OK,
							goodsNameInput.this.getIntent());
					goodsNameInput.this.finish();
					overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});

	}

	public void back_btn(View v) {
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
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 goodsNameInput.this, 0, new Intent(goodsNameInput.this,message.class),0);
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
