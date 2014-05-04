package com.erhuo.forsale;

//import com.example.goodsui.R;

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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.salegoods.Change_goods_information;
import com.erhuo.salegoods.goods_information;
import com.erhuo.salegoods.introduceInput;
import com.erhuo.server.MyMessage;

import com.erhuo.userInfo.setting;

public class forsaleChange extends Activity {
	// public RelativeLayout user_headLayout=null;

	// private ImageView headImageView =null;

	private RelativeLayout user_nameLayout, goods_nameLayout,
			goods_catagoryLayout, phoneLayout, introducerl
			/* goods_priceLayout */= null;
//	private EditText introduceEditText;
	private Button tijiaoButton = null;
	private TextView user_nameTextView, nameTextView, catagoryTextView,
			phoneTextView, priceTextView, biaotiTextView, introducetv;
	int actionType = 0;
	private int askbuyid;

	public static final int USER_NAME = 1;
	public static final int GOODS_NAME = 2;
	public static final int GOODS_CATAGORY = 3;
	public static final int PHONE = 4;
	public static final int INTRODUCE = 5;

	public static final int PICTURE01 = 7;
	public static final int PICTURE02 = 8;

	public static final int PICTURE03 = 9;
	int screenWidth, screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题栏
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 不自动显示软键盘
		setContentView(R.layout.forsale_input);
		
		notifym();

		findView();
		tijiaoButton.setVisibility(tijiaoButton.INVISIBLE);

		askbuyid = this.getIntent().getIntExtra("askbuyid", 0);
		Log.v("22222222222222", askbuyid + "");
		getInfo();
		//biaotiTextView.setText("修改信息");
		// userid = this.getIntent().getStringExtra("userid");
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels; // 屏幕宽度（像素）
		screenHeight = metric.heightPixels;
		onclick();
	}

	void getInfo() {
		Socket socket = null;
		try {
			socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());

			MyMessage m1 = new MyMessage();
			Hashtable table = new Hashtable();
			table.put("askbuyid", askbuyid);
			m1.setValue(table);
			m1.setType(m1.CHANGEASKFORINFO);
			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				user_nameTextView.setText(m1.getReturnValue()
						.get("userqiuname").toString());
				phoneTextView.setText(m1.getReturnValue().get("askbuyphone")
						.toString());
				catagoryTextView.setText(m1.getReturnValue().get("agoodsclass")
						.toString());
				nameTextView.setText(m1.getReturnValue().get("agoodsname")
						.toString());
				introducetv.setText(m1.getReturnValue().get("askbuycontent")
						.toString());

			} else {
				Toast.makeText(forsaleChange.this,
						m1.getReturnValue().get("message").toString(),
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(forsaleChange.this, "网络不通!" + e.getMessage(),
					Toast.LENGTH_LONG).show();

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	public void back_OnClick(View v) {
		forsaleChange.this.getIntent().putExtra("info","qiugou");
	
		forsaleChange.this.setResult(RESULT_OK,
				forsaleChange.this.getIntent());
		this.finish();
 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}

	void findView() {
		user_nameLayout = (RelativeLayout) findViewById(R.id.user_namerl);
		user_nameTextView = (TextView) findViewById(R.id.userNametv2);
		phoneLayout = (RelativeLayout) findViewById(R.id.phonerl);
		phoneTextView = (TextView) findViewById(R.id.phonetv2);
		goods_catagoryLayout = (RelativeLayout) findViewById(R.id.catagoryrl);
		catagoryTextView = (TextView) findViewById(R.id.catagorytv2);
		goods_nameLayout = (RelativeLayout) findViewById(R.id.goods_namerl);
		nameTextView = (TextView) findViewById(R.id.goods_nametv2);
		introducerl = (RelativeLayout) findViewById(R.id.jianjierl);
		introducetv = (TextView) findViewById(R.id.jianjietv);
		tijiaoButton = (Button) findViewById(R.id.tijiao_btn);
//		biaotiTextView = (TextView) findViewById(R.id.biaoti);
	}

	void onclick() {
		user_nameLayout.setOnClickListener(new user_nameLayoutOnclickImp());

		goods_nameLayout.setOnClickListener(new goods_nameLayoutOnclickImp());

		goods_catagoryLayout
				.setOnClickListener(new goods_catagoryLayoutOnclickImp());

		phoneLayout.setOnClickListener(new phoneLayoutOnclickImp());
		
		introducerl.setOnClickListener(new introducerlOnclickImp());
		
		
	}

	// 货主名字输入
	class user_nameLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = USER_NAME;
			// TODO Auto-generated method stub
			Intent intent = new Intent(forsaleChange.this,
					com.erhuo.salegoods.userNameInput.class);
			intent.putExtra("userName", user_nameTextView.getText());
			forsaleChange.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}
	// 内容输入
		class introducerlOnclickImp implements OnClickListener {

			@Override
			public void onClick(View v) {
				actionType = INTRODUCE;
				// TODO Auto-generated method stub
				Intent intent = new Intent(forsaleChange.this,
						introduceInput.class);
				intent.putExtra("introduce", introducetv.getText());
				forsaleChange.this.startActivityForResult(intent, 1);
				overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
			}

		}

	// 货物名字输入
	class goods_nameLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = GOODS_NAME;
			// TODO Auto-generated method stub
			Intent intent = new Intent(forsaleChange.this,
					com.erhuo.salegoods.goodsNameInput.class);
			intent.putExtra("goodsName", nameTextView.getText());
			forsaleChange.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 类别触发 （未完）
	class goods_catagoryLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = GOODS_CATAGORY;
			// TODO Auto-generated method stub
			Intent intent = new Intent(forsaleChange.this, goods_catagory.class);
			forsaleChange.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 手机号触发
	class phoneLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PHONE;
			// TODO Auto-generated method stub
			Intent intent = new Intent(forsaleChange.this,
					com.erhuo.salegoods.phoneInput.class);
			intent.putExtra("phone", phoneTextView.getText());
			forsaleChange.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			if (actionType == GOODS_NAME) {
				if (changeaskbuy("agoodsname",
						data.getStringExtra("retgoodsName"))) {
					nameTextView.setText(data.getStringExtra("retgoodsName"));
				}
			} else if (actionType == GOODS_CATAGORY) {
				if (changeaskbuy("agoodsclass", data.getStringExtra("catagory"))) {
					catagoryTextView.setText(data.getStringExtra("catagory"));
				}

			} else if (actionType == PHONE) {
				if (changeaskbuy("askbuyphone", data.getStringExtra("retphone"))) {
					phoneTextView.setText(data.getStringExtra("retphone"));
				}

			} else if (actionType == USER_NAME) {
				if (changeaskbuy("userqiuname",
						data.getStringExtra("retuserName"))) {
					user_nameTextView.setText(data
							.getStringExtra("retuserName"));
				}

			} else if (actionType == INTRODUCE) {
				if (changeaskbuy("askbuycontent",
						data.getStringExtra("retintroduce"))) {
					introducetv.setText(data
							.getStringExtra("retintroduce"));
				}

			}
			break;
		case RESULT_CANCELED:
			break;
		default:
			break;
		}

	}

	public boolean changeaskbuy(String type, String value) {
		boolean b = true;
		Socket socket = null;
		try {
			socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());

			MyMessage m1 = new MyMessage();
			Hashtable table = new Hashtable();
			table.put("type", type);
			table.put("value", value);
			table.put("askbuyid", askbuyid);
			m1.setValue(table);
			m1.setType(m1.CHANGEASKBUY);
			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {

			} else {
				Toast.makeText(forsaleChange.this,
						m1.getReturnValue().get("message").toString(),
						Toast.LENGTH_LONG).show();
				b = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(forsaleChange.this, "网络不通!" + e.getMessage(),
					Toast.LENGTH_LONG).show();
			b = false;

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				b = false;
			}
		}
		return b;
	}
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 forsaleChange.this, 0, new Intent(forsaleChange.this,message.class),0);
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
