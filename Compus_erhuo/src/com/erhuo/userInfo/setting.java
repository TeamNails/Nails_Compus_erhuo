package com.erhuo.userInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
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

public class setting extends Activity {

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
		setContentView(R.layout.setting);
		userid = this.getIntent().getStringExtra("userid");
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
				Toast.makeText(setting.this,
						m1.getReturnValue().get("message").toString(),
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(setting.this, "网络不通!" + e.getMessage(),
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
		qqTextView.setText(qq);
		emailTextView.setText(email);
		onclick();
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
		qqTextView=(TextView)findViewById(R.id.qqshow);
		emailTextView=(TextView)findViewById(R.id.emailshow);
	}

	void onclick() {

		user_nameLayout.setOnClickListener(new user_nameLayoutOnclickImp());
		user_sexLayout.setOnClickListener(new user_sexLayoutOnclickImp());

//		user_phoneLayout.setOnClickListener(new user_phoneLayoutOnclickImp());

		change_passwardLayout
				.setOnClickListener(new change_passwardLayoutOnclickImp());
		feedbackLayout.setOnClickListener(new feedbackLayoutOnclickImp());
		aboutLayout.setOnClickListener(new aboutLayoutOnclickImp());
		outloginButton.setOnClickListener(new outloginbuttonOnclickImp());
		addressLayout.setOnClickListener(new addressLayoutOnclickImp());

		gradeLayout.setOnClickListener(new gradeLayoutOnclickImp());

		qqLayout.setOnClickListener(new qqOnclickImp());
		emailLayout.setOnClickListener(new emailOnclickImp());
		
	}

	// 查看大图
	
	class qqOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = QQ;
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this,changeQQ.class);
			intent.putExtra("qq", qqTextView.getText());
			intent.putExtra("userid", userid);
			setting.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.new_dync_out_to_left,
					R.anim.new_dync_in_from_right);
		}

	}
	class emailOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = EMAIL;
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this,changeEmail.class);
			intent.putExtra("email", emailTextView.getText());
			intent.putExtra("userid", userid);
			setting.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.new_dync_out_to_left,
					R.anim.new_dync_in_from_right);
		}

	}
	
	// 地址触发
	class addressLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = ADDRESS;
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this,address.class);
			intent.putExtra("address", addresstv.getText());
			intent.putExtra("userid", userid);
			setting.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.new_dync_out_to_left,
					R.anim.new_dync_in_from_right);
		}

	}
	// 年纪触发
	class gradeLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = GRADE;
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this, grade.class);
//			intent.putExtra("userName", nameTextView.getText());
			intent.putExtra("userid", userid);
			setting.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.new_dync_out_to_left,
					R.anim.new_dync_in_from_right);
		}

	}
	
	
	
	
	
	
	
	
	
	// 姓名触发
	class user_nameLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = USER_NAME;
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this, changeName.class);
			intent.putExtra("userName", nameTextView.getText());
			intent.putExtra("userid", userid);
			setting.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.new_dync_out_to_left,
					R.anim.new_dync_in_from_right);
		}

	}

	// 性别触发
	class user_sexLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = USER_SEX;
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this, sexChoose.class);
			intent.putExtra("userid", userid);
			setting.this.startActivityForResult(intent, 1);
		}

	}

	

	// 修改密码
	class change_passwardLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this, change_passward.class);
			intent.putExtra("userid", userid);
			// intent.putExtra("phone",phoneTextView.getText());
			setting.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.new_dync_out_to_left,
					R.anim.new_dync_in_from_right);
		}

	}

	// 意见反馈
	class feedbackLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
		
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this, feedback.class);
//			intent.putExtra("phone", phoneTextView.getText());
			setting.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.new_dync_out_to_left,
					R.anim.new_dync_in_from_right);
		}

	}

	// 关于
	class aboutLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this, about.class);
//			intent.putExtra("phone", phoneTextView.getText());
			setting.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.new_dync_out_to_left,
					R.anim.new_dync_in_from_right);
		}

	}

	// 关于
	class outloginbuttonOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(setting.this, out_login.class);
			// intent.putExtra("phone",phoneTextView.getText());
			setting.this.startActivityForResult(intent, 1);
			// overridePendingTransition(R.anim.fade_in, R.anim.hold);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			if (actionType == USER_NAME) {
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbba1");
				nameTextView.setText(data.getStringExtra("retuserName"));
			} else if (actionType == USER_SEX) {
				sexTextView.setText(data.getStringExtra("sex"));
			} else if (actionType == GRADE) {
				if (changegrade(data.getStringExtra("grade"))) {
					gradetv.setText(data.getStringExtra("grade"));
				}
			
			} else if (actionType == ADDRESS) {
				addresstv.setText(data.getStringExtra("retaddress"));
			} else if(actionType==EMAIL){
				emailTextView.setText(data.getStringExtra("retmail"));

			}else if(actionType==QQ){
				qqTextView.setText(data.getStringExtra("retqq"));
			}
			break;
		case RESULT_CANCELED:
			break;
		default:
			break;
		}

	}
	
	public boolean changegrade(String changgrade) {
		boolean b=false;
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
			table.put("grade", changgrade);
			m1.setValue(table);
			m1.setType(m1.CHANGEGRADE);

			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				b=true;

			} else {
				Toast.makeText(
						setting.this,
						"修改失败!"
								+ m1.getReturnValue().get("message")
										.toString(), Toast.LENGTH_LONG)
						.show();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(setting.this, "网络不通!", Toast.LENGTH_LONG)
					.show();

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
		return b;
	}

}
