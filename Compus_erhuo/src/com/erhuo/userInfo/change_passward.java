package com.erhuo.userInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;
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
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.net.LoginClientTCP;
import com.erhuo.app.util.Tools;
import com.erhuo.forsale.forSale;
import com.erhuo.login.LoginActivity;
import com.erhuo.login.RegActivity;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.server.MyMessage;

public class change_passward extends Activity {
	private EditText oldpassward,newpassward,passwardagain;
	private Button baocunButton=null;
	private String userid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_passward);
		baocunButton=(Button)findViewById(R.id.btn_baocun);
		oldpassward=(EditText)findViewById(R.id.oldpassward);
		newpassward=(EditText)findViewById(R.id.newpassward);
		passwardagain=(EditText)findViewById(R.id.passwardagain);
		userid = this.getIntent().getStringExtra("userid");
		notifym();
	baocunButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (newpassward.getText().toString()
						.equals("")) {
					Toast.makeText(change_passward.this, "�������������ˣ�", Toast.LENGTH_LONG)
					.show();
					return;
				}
				if(newpassward.length()<5){
					Toast.makeText(change_passward.this, "��������5λ��!", Toast.LENGTH_LONG)
					.show();
					return;
				}
				
				
				if (!newpassward.getText().toString()
						.equals(passwardagain.getText().toString())) {

					Toast.makeText(change_passward.this, "���벻һ��!", Toast.LENGTH_LONG)
							.show();

					return;
				}
				
				
			/*	try {
					String message = LoginClientTCP.login(userid, oldpassward.getText().toString());
					if (message==null) {
						Toast.makeText(change_passward.this, "messgeΪ�գ�",
								Toast.LENGTH_LONG).show();
					}
					if (message.equalsIgnoreCase("ok")) {*/
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
							table.put("olduserpassword", oldpassward.getText().toString());
							table.put("userpassword", newpassward.getText().toString());
							m1.setValue(table);
							m1.setType(m1.CHANGEPASSWORD);

							oout.writeObject(m1);
							oout.flush();
							m1 = (MyMessage) oin.readObject();
							if (m1.getReturnValue().get("message").toString()
									.equalsIgnoreCase("ok")) {
								Toast.makeText(
										change_passward.this,
										"�޸ĳɹ�!".toString(), Toast.LENGTH_LONG)
										.show();
								 change_passward.this.finish();
							}else if (m1.getReturnValue().get("message").toString()
									.equalsIgnoreCase("passworderror")) {
								Toast.makeText(
										change_passward.this,
										"ԭ�������!"
												+ m1.getReturnValue().get("message")
														.toString(), Toast.LENGTH_LONG)
										.show();
							}
							else {
								Toast.makeText(
										change_passward.this,
										"�޸�ʧ��!"
												+ m1.getReturnValue().get("message")
														.toString(), Toast.LENGTH_LONG)
										.show();
							}

						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(change_passward.this, "���粻ͨ!"+e.getMessage(), Toast.LENGTH_LONG)
									.show();

						} finally {
							try {
								socket.close();
							} catch (IOException e) {
							}
						}

					/*} else if (message.equalsIgnoreCase("psswordError")) {
						Toast.makeText(change_passward.this, "ԭ�������",
								Toast.LENGTH_LONG).show();
						oldpassward.setText("");
						oldpassward.requestFocus();
					}
				} catch (Exception e) {
					
					Toast
							.makeText(change_passward.this, "��������ʧ�ܣ�"+e.getMessage(),
									Toast.LENGTH_LONG).show();
				}*/
				change_passward.this.finish();
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
	


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
				 change_passward.this, 0, new Intent(change_passward.this,message.class),0);
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
