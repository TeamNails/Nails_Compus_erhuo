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

public class address extends Activity {
	@SuppressWarnings("unused")
	private EditText new_NameEditText = null;
	@SuppressWarnings("unused")
	private Button baocunButton = null,backButton;
	private TextView showTextView= null;
	private ImageView zhutiTextView=null;
	private String userid;
	private String address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information_input);
		notifym();
		baocunButton = (Button) findViewById(R.id.btn_baocun);
		backButton = (Button) findViewById(R.id.btn_back);
        new_NameEditText = (EditText) findViewById(R.id.info);
		zhutiTextView = (ImageView) findViewById(R.id.zhutitv);
		zhutiTextView.setImageDrawable(getResources().getDrawable(R.drawable.shurudizhi));
//		zhutiTextView.setText("���������");
		userid = this.getIntent().getStringExtra("userid");
		Intent intent = super.getIntent();
		address=intent.getStringExtra("address");
		new_NameEditText.setText(address);
		showTextView = (TextView) findViewById(R.id.showView);
//		showTextView.setText("�����ֿ�����������Ѹ����׼�ס��");

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
					
				}else {
					address=new_NameEditText.getText().toString();
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
					table.put("address", address);
					m1.setValue(table);
					m1.setType(m1.CHANGEADDRESS);

					oout.writeObject(m1);
					oout.flush();
					m1 = (MyMessage) oin.readObject();
					if (m1.getReturnValue().get("message").toString()
							.equalsIgnoreCase("ok")) {
						 String address = m1.getReturnValue().get("address")
								.toString();
							address.this.getIntent().putExtra("retaddress",
									address);
							address.this.setResult(RESULT_OK,
									address.this.getIntent());
							address.this.finish();
							overridePendingTransition(R.anim.right_left2, R.anim.right_left);

					} else {
						Toast.makeText(
								address.this,
								"�޸�ʧ��!"
										+ m1.getReturnValue().get("message")
												.toString(), Toast.LENGTH_LONG)
								.show();
					}

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(address.this, "���粻ͨ!", Toast.LENGTH_LONG)
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
		//���֪ͨ������
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//����һ��֪ͨ����
		 Notification notification = new Notification(R.drawable.icon, "����Ϣ", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 address.this, 0, new Intent(address.this,message.class),0);
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
