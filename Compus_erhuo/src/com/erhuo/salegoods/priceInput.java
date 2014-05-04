package com.erhuo.salegoods;




import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
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
import com.erhuo.main.R;
import com.erhuo.message.message;

public class priceInput extends Activity {
	@SuppressWarnings("unused")
	private EditText priceEditText=null;
	@SuppressWarnings("unused")
	private Button baocunButton=null;
	private TextView zhutiTextView,showTextView,priceTextView=null;
	private ImageView titileImageView=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information_input);
	
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); 
		notifym();
		baocunButton=(Button)findViewById(R.id.btn_baocun);
		priceEditText=(EditText)findViewById(R.id.info);
//		zhutiTextView=(TextView)findViewById(R.id.zhutitv);
		showTextView=(TextView)findViewById(R.id.showView);
//		zhutiTextView.setText("输入价格");
		titileImageView=(ImageView)findViewById(R.id.zhutitv);
		titileImageView.setImageDrawable(getResources().getDrawable(R.drawable.inputjiage));
		showTextView.setText("为你的宝贝写个价格吧！");
		
		Intent intent=super.getIntent();
		priceEditText.setText(intent.getStringExtra("price"));
             priceEditText.setKeyListener(new NumberKeyListener(){
			

            	

			@Override
			protected char[] getAcceptedChars() {
				// TODO Auto-generated method stub
				char numberChars[]={'1','2','3','4','5','6','7','8','9','0','.',};
				return numberChars;
			}

			@Override
			public int getInputType() {
				// TODO Auto-generated method stub
				return 0;
			}
			});
	baocunButton.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
			
					priceInput.this.getIntent().putExtra("retprice",priceEditText.getText().toString().trim());
					priceInput.this.setResult(RESULT_OK,priceInput.this.getIntent());
					priceInput.this.finish();
					overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
	


}	
	   public void back_btn(View v){
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
					 priceInput.this, 0, new Intent(priceInput.this,message.class),0);
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
