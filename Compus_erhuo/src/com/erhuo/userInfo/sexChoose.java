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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.server.MyMessage;


public class sexChoose extends Activity {
	
//
//	@SuppressWarnings("unused")
	private Button boyButton,girlButton,exitButton=null;
	private String userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sexchoose);
		notifym();
//		baocunButton=(Button)findViewById(R.id.btn_baocun);
//		new_NameEditText=(EditText)findViewById(R.id.newName);
		boyButton=(Button)findViewById(R.id.boyBtn);
		girlButton=(Button)findViewById(R.id.girlBtn);
		exitButton=(Button)findViewById(R.id.exitBtn);
		 userid = this.getIntent().getStringExtra("userid");
//		Intent intent=super.getIntent();
		 System.out.println("bbbbbbbbbbbbbbbbbbbbbbba1");
//		new_NameEditText.setText(intent.getStringExtra("userName"));
	
//		new_NameEditText.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	boyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
					table.put("sex", "��");
					m1.setValue(table);
					m1.setType(m1.CHANGESEX);

					oout.writeObject(m1);
					oout.flush();
					m1 = (MyMessage) oin.readObject();
					if (m1.getReturnValue().get("message").toString()
							.equalsIgnoreCase("ok")) {
						 String sex = m1.getReturnValue().get("sex")
								.toString();
						 sexChoose.this.getIntent().putExtra("sex","��");
							sexChoose.this.setResult(RESULT_OK,sexChoose.this.getIntent());
							sexChoose.this.finish();
					} else {
						Toast.makeText(
								sexChoose.this,
								"�޸�ʧ��!"
										+ m1.getReturnValue().get("message")
												.toString(), Toast.LENGTH_LONG)
								.show();
					}

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(sexChoose.this, "���粻ͨ!", Toast.LENGTH_LONG)
							.show();

				} finally {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}

				
				
			}
		});
	girlButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
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
				table.put("sex", "Ů");
				m1.setValue(table);
				m1.setType(m1.CHANGESEX);

				oout.writeObject(m1);
				oout.flush();
				m1 = (MyMessage) oin.readObject();
				if (m1.getReturnValue().get("message").toString()
						.equalsIgnoreCase("ok")) {
					 String sex = m1.getReturnValue().get("sex")
							.toString();
					 sexChoose.this.getIntent().putExtra("sex","Ů");
						sexChoose.this.setResult(RESULT_OK,sexChoose.this.getIntent());
						sexChoose.this.finish();
				} else {
					Toast.makeText(
							sexChoose.this,
							"�޸�ʧ��!"
									+ m1.getReturnValue().get("message")
											.toString(), Toast.LENGTH_LONG)
							.show();
				}

			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(sexChoose.this, "���粻ͨ!", Toast.LENGTH_LONG)
						.show();

			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
			
			
		}
	});
		exitButton.setOnClickListener(new OnClickListener() {
	
	@Override
		public void onClick(View v) {
		// TODO Auto-generated method stub
//		String nameString=(String)new_NameEditText.getText();
//			sexChoose.this.getIntent().putExtra("sex","��");
//			sexChoose.this.setResult(RESULT_OK,sexChoose.this.getIntent());
			sexChoose.this.finish();
	}
});

}	
	public void notifym() {
		//���֪ͨ������
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//����һ��֪ͨ����
		 Notification notification = new Notification(R.drawable.icon, "����Ϣ", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 sexChoose.this, 0, new Intent(sexChoose.this,message.class),0);
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
