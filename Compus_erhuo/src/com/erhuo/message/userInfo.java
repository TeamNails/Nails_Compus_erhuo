package com.erhuo.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.login.LoginActivity;
import com.erhuo.login.RegActivity;
import com.erhuo.main.R;
import com.erhuo.server.MyMessage;

public class userInfo extends Activity {

	private RelativeLayout user_nameLayout, user_sexLayout, user_phoneLayout,
			change_passwardLayout, feedbackLayout, aboutLayout, gradeLayout,
			addressLayout ,qqLayout,emailLayout= null;

	private TextView nameTextView, phoneTextView, sexTextView, gradetv,
			addresstv,qqTextView,emailTextView;
	private Button outloginButton;
	int actionType = 0;

	public static final int USER_NAME = 2;
	public static final int USER_SEX = 3;
	public static final int USER_HOBBY = 4;
	
	public static final int ADDRESS = 5;
	public static final int GRADE = 6;
	public static final int QQ = 7;
	public static final int EMAIL =8;
	int screenWidth, screenHeight;
	private String userid;
	private String username;
	private String sex;
	private String address,grade,qq,email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题栏
		setContentView(R.layout.user_info);
		userid = Tools.userid2;
		findView();

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
			m1.setValue(table);
			m1.setType(m1.USERINFO);

			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				username = m1.getReturnValue().get("username").toString();
				sex = m1.getReturnValue().get("sex").toString();
				address = m1.getReturnValue().get("address").toString();
				grade = m1.getReturnValue().get("grade").toString();
				qq=m1.getReturnValue().get("qq").toString();
				email=m1.getReturnValue().get("email").toString();

			} else {
				Toast.makeText(userInfo.this,
						m1.getReturnValue().get("message").toString(),
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(userInfo.this, "网络不通!" + e.getMessage(),
					Toast.LENGTH_LONG).show();

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}

		nameTextView.setText(username);
		sexTextView.setText(sex);
		gradetv.setText(grade);
		addresstv.setText(address);
		
		qqTextView=(TextView)findViewById(R.id.qqshow);
		emailTextView=(TextView)findViewById(R.id.emailshow);
		qqTextView.setText(qq);
		emailTextView.setText(email);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels; // 屏幕宽度（像素）
		screenHeight = metric.heightPixels;

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(R.anim.bottom_top, R.anim.bottom_top2);
		}
		return super.onKeyDown(keyCode, event);
	}

	public void back_OnClick(View v) {
		this.finish();
		overridePendingTransition(R.anim.bottom_top, R.anim.bottom_top2);

	}

	void findView() {

		user_nameLayout = (RelativeLayout) findViewById(R.id.user_name);
		user_sexLayout = (RelativeLayout) findViewById(R.id.user_sex);
		// user_hobbyLayout=(RelativeLayout)findViewById(R.id.user_hobby);
		// user_phoneLayout=(RelativeLayout)findViewById(R.id.user_phone);
		nameTextView = (TextView) findViewById(R.id.nametv);
		sexTextView = (TextView) findViewById(R.id.sextv);
		// hobbyTextView=(TextView)findViewById(R.id.hobbytv);
//		 sexTextView=(TextView)findViewById(R.id.sex);
		change_passwardLayout = (RelativeLayout) findViewById(R.id.change_passwardrl);
		gradeLayout = (RelativeLayout) findViewById(R.id.graderl);
		addressLayout = (RelativeLayout) findViewById(R.id.addressrl);
		gradetv=(TextView)findViewById(R.id.gradetv);
		addresstv=(TextView)findViewById(R.id.addresstv);
		feedbackLayout = (RelativeLayout) findViewById(R.id.feedbackrl);
		aboutLayout = (RelativeLayout) findViewById(R.id.aboutrl);
		outloginButton = (Button) findViewById(R.id.out_login);
		qqLayout=(RelativeLayout)findViewById(R.id.qq);
		emailLayout=(RelativeLayout)findViewById(R.id.email);
		qqTextView=(TextView)findViewById(R.id.qqtv);
		emailTextView=(TextView)findViewById(R.id.emailtv);
	}
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 userInfo.this, 0, new Intent(userInfo.this,message.class),0);
		 notification.setLatestEventInfo(getApplicationContext(),"新信息", "通知显示的内容", pendingIntent);
		
			notification.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止
			notification.defaults |= Notification.DEFAULT_SOUND; //默认声音	
		Tools.context=getApplicationContext();
			Tools.manager=manager;
		Tools.notification=notification;
		Tools.pendingIntent=pendingIntent;
	}
	
	protected void onResume() {
		notifym();
		super.onResume();
	}

}
