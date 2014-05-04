package com.erhuo.userInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.forSale;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.server.MyMessage;

public class changeQQ extends Activity {
	@SuppressWarnings("unused")
	private EditText new_NameEditText = null;
	@SuppressWarnings("unused")
	private Button baocunButton = null,backButton;
	private TextView showTextView, zhutiTextView = null;
	private String userid;
	private String usernametextString;
	private ImageView titileImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information_input);
		notifym();
		baocunButton = (Button) findViewById(R.id.btn_baocun);
		backButton = (Button) findViewById(R.id.btn_back);
        new_NameEditText = (EditText) findViewById(R.id.info);
        titileImageView=(ImageView)findViewById(R.id.zhutitv);
		titileImageView.setImageDrawable(getResources().getDrawable(R.drawable.inputusername));
		userid = this.getIntent().getStringExtra("userid");
		Intent intent = super.getIntent();
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbba1");
		usernametextString=intent.getStringExtra("qq");
		new_NameEditText.setText(usernametextString);
		showTextView = (TextView) findViewById(R.id.showView);
		showTextView.setText("写下QQ便于别人联系！");

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
				if (new_NameEditText.getText().toString().trim().equalsIgnoreCase("")) {
					usernametextString="";
				}else {
					usernametextString=new_NameEditText.getText().toString();
				}
				Socket socket = null;
				try {
					socket = new Socket(Tools.IP, Tools.PORT_1);
					ObjectOutputStream oout = new ObjectOutputStream(
							socket.getOutputStream());
					ObjectInputStream oin = new ObjectInputStream(
							socket.getInputStream());

					MyMessage m1 = new MyMessage();
					Hashtable table = new Hashtable();
					table.put("userid", userid);
					table.put("qq", usernametextString);
					m1.setValue(table);
					m1.setType(m1.CHANGEQQ);

					oout.writeObject(m1);
					oout.flush();
					m1 = (MyMessage) oin.readObject();
					if (m1.getReturnValue().get("message").toString()
							.equalsIgnoreCase("ok")) {
						 String username = m1.getReturnValue().get("qq")
								.toString();
							changeQQ.this.getIntent().putExtra("retqq",
									username);
							changeQQ.this.setResult(RESULT_OK,
									changeQQ.this.getIntent());
							changeQQ.this.finish();
							overridePendingTransition(R.anim.right_left2, R.anim.right_left);

					} else {
						Toast.makeText(
								changeQQ.this,
								"修改失败!"
										+ m1.getReturnValue().get("message")
												.toString(), Toast.LENGTH_LONG)
								.show();
					}

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(changeQQ.this, "网络不通!", Toast.LENGTH_LONG)
							.show();

				} finally {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		});
		backButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
			
		});
	}
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 changeQQ.this, 0, new Intent(changeQQ.this,message.class),0);
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
