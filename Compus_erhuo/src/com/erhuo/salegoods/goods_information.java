package com.erhuo.salegoods;

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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
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
import com.erhuo.forsale.forSale;
import com.erhuo.login.RegActivity;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.server.MyMessage;

public class goods_information extends Activity {
	// public RelativeLayout user_headLayout=null;

	// private ImageView headImageView =null;

	private RelativeLayout user_nameLayout, goods_nameLayout,
			goods_catagoryLayout, phoneLayout, goods_priceLayout,
			introducerl = null;

	private Button tijiaoButton, returnButton = null;
	private TextView user_nameTextView, nameTextView, catagoryTextView,
			phoneTextView, priceTextView, biaotiTextView, introducetv;
	int actionType = 0;
	private ImageView pictureIm01, pictureIm02, pictureIm03,titleImageView;
	private String userid;
	public static final int USER_NAME = 1;
	public static final int GOODS_NAME = 2;
	public static final int GOODS_CATAGORY = 3;
	public static final int PHONE = 4;
	public static final int INTRODUCE = 5;
	public static final int PRICE = 6;
	public static final int PICTURE01 = 7;
	public static final int PICTURE02 = 8;
	public static final int PICTURE03 = 9;

	private String photo1 = "", photo2 = "", photo3 = "";
	int screenWidth, screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题栏
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 不自动显示软键盘
		setContentView(R.layout.goods_information);
		userid = this.getIntent().getStringExtra("userid");
		findView();
		 notifym();
//		 titleImageView.setImageDrawable(getResources().getDrawable(R.id.))
//		biaotiTextView.setText("发布信息");
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels; // 屏幕宽度（像素）
		screenHeight = metric.heightPixels;
		pictureIm01.setMinimumHeight(screenHeight / 5);
		pictureIm02.setMinimumHeight(screenHeight / 5);
		pictureIm03.setMinimumHeight(screenHeight / 5);
		pictureIm01.setMinimumWidth(screenWidth / 3);
		pictureIm02.setMinimumWidth(screenWidth / 3);
		pictureIm03.setMinimumWidth(screenWidth / 3);

		pictureIm01.setMaxHeight(screenHeight / 5);
		pictureIm02.setMaxHeight(screenHeight / 5);
		pictureIm03.setMaxHeight(screenHeight / 5);
		pictureIm01.setMaxWidth(screenWidth / 3);
		pictureIm02.setMaxWidth(screenWidth / 3);
		pictureIm03.setMaxWidth(screenWidth / 3);

		returnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				goods_information.this.finish();
				overridePendingTransition( R.anim.right_left2,R.anim.right_left);

			}
		});

		onclick();

	}

	void findView() {
		returnButton = (Button) findViewById(R.id.btn_back);
		user_nameLayout = (RelativeLayout) findViewById(R.id.user_namerl);
		user_nameTextView = (TextView) findViewById(R.id.userNametv2);
		phoneLayout = (RelativeLayout) findViewById(R.id.phonerl);
		phoneTextView = (TextView) findViewById(R.id.phonetv2);
		goods_catagoryLayout = (RelativeLayout) findViewById(R.id.catagoryrl);
		catagoryTextView = (TextView) findViewById(R.id.catagorytv2);
		goods_nameLayout = (RelativeLayout) findViewById(R.id.goods_namerl);
		nameTextView = (TextView) findViewById(R.id.goods_nametv2);
		goods_priceLayout = (RelativeLayout) findViewById(R.id.pricerl);
		priceTextView = (TextView) findViewById(R.id.pricetv2);
		introducerl = (RelativeLayout) findViewById(R.id.jianjierl);
		introducetv = (TextView) findViewById(R.id.introducetv);
		pictureIm01 = (ImageView) findViewById(R.id.picture01);
		pictureIm02 = (ImageView) findViewById(R.id.picture02);
		pictureIm03 = (ImageView) findViewById(R.id.picture03);
		tijiaoButton = (Button) findViewById(R.id.tijiao_btn);

	}

	void onclick() {
		user_nameLayout.setOnClickListener(new user_nameLayoutOnclickImp());
		goods_nameLayout.setOnClickListener(new goods_nameLayoutOnclickImp());
		goods_catagoryLayout
				.setOnClickListener(new goods_catagoryLayoutOnclickImp());
		goods_priceLayout
				.setOnClickListener(new goods_priceLayoutOnclickImpl());
		phoneLayout.setOnClickListener(new phoneLayoutOnclickImp());

		pictureIm01.setOnClickListener(new pictureIm01OnclickImp());
		pictureIm02.setOnClickListener(new pictureIm02OnclickImp());
		pictureIm03.setOnClickListener(new pictureIm03OnclickImp());

		tijiaoButton.setOnClickListener(new tijiaoButtonOnclickImp());
		introducerl.setOnClickListener(new introducerlOnclickImp());
	}

	class tijiaoButtonOnclickImp implements OnClickListener {

		public void onClick(View v) {
			if (nameTextView.getText().toString().equals("")) {

				Toast.makeText(goods_information.this, "商品名称不能为空!",
						Toast.LENGTH_LONG).show();

				return;
			} else if (catagoryTextView.getText().toString().equals("")) {

				Toast.makeText(goods_information.this, "商品类别不能为空!",
						Toast.LENGTH_LONG).show();

				return;
			} else if (priceTextView.getText().toString().equals("")) {

				Toast.makeText(goods_information.this, "商品价格可以免费不能为空!",
						Toast.LENGTH_LONG).show();

				return;
			} 
			else if (photo1.equals("")) {

				Toast.makeText(goods_information.this, "第一张照片不能为空!",
						Toast.LENGTH_LONG).show();

				return;
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
				table.put("goodsname", nameTextView.getText().toString());
				table.put("goodsclass", catagoryTextView.getText().toString());
				table.put("goodsprice", priceTextView.getText().toString());
				table.put("userid", userid);
				table.put("usersellname", user_nameTextView.getText()
						.toString());
				table.put("goodsphone", phoneTextView.getText().toString());
				table.put("goodsinfo", introducetv.getText().toString());
				table.put("goodsphoto1", photo1);
				table.put("goodsphoto2", photo2);
				table.put("goodsphoto3", photo3);
				m1.setValue(table);
				m1.setType(m1.ADDGOODS);

				oout.writeObject(m1);
				oout.flush();
				m1 = (MyMessage) oin.readObject();
				if (m1.getReturnValue().get("message").toString()
						.equalsIgnoreCase("ok")) {

					AlertDialog.Builder bb = new AlertDialog.Builder(
							goods_information.this);
					bb.setTitle("提交成功！");
					bb.setMessage("您可以在自己的管理里面修改和删除该商品！");
					bb.setNeutralButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					bb.create().show();

				} else {
					Toast.makeText(
							goods_information.this,
							"提交失败!"
									+ m1.getReturnValue().get("message")
											.toString(), Toast.LENGTH_LONG)
							.show();
				}

			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(goods_information.this, "网络不通!",
						Toast.LENGTH_LONG).show();

			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}

		}
	}

	// 内容输入
	class introducerlOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = INTRODUCE;
			// TODO Auto-generated method stub
			Intent intent = new Intent(goods_information.this,
					introduceInput.class);

			intent.putExtra("introduce", introducetv.getText());
			goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 货主名字输入
	class user_nameLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = USER_NAME;
			// TODO Auto-generated method stub
			Intent intent = new Intent(goods_information.this,
					userNameInput.class);

			intent.putExtra("userName", user_nameTextView.getText());
			goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 货物名字输入
	class goods_nameLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = GOODS_NAME;
			// TODO Auto-generated method stub
			Intent intent = new Intent(goods_information.this,
					goodsNameInput.class);
			intent.putExtra("goodsName", nameTextView.getText());

			goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 类别触发 （未完）
	class goods_catagoryLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = GOODS_CATAGORY;
			// TODO Auto-generated method stub
			Intent intent = new Intent(goods_information.this,
					goods_catagory.class);

			goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 价格触发
	class goods_priceLayoutOnclickImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PRICE;

			Intent intent = new Intent(goods_information.this, priceInput.class);
			intent.putExtra("price", priceTextView.getText());

			goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 手机号触发
	class phoneLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PHONE;
			// TODO Auto-generated method stub
			Intent intent = new Intent(goods_information.this, phoneInput.class);
			intent.putExtra("phone", phoneTextView.getText());

			goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	class pictureIm01OnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PICTURE01;
			// TODO Auto-generated method stub
			Intent intent = new Intent(goods_information.this,
					PhotoActivity.class);

			goods_information.this.startActivityForResult(intent, 1);
	

		}

	}

	class pictureIm02OnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PICTURE02;
			// TODO Auto-generated method stub
			Intent intent = new Intent(goods_information.this,
					PhotoActivity.class);

			goods_information.this.startActivityForResult(intent, 1);

		}

	}

	class pictureIm03OnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PICTURE03;
			// TODO Auto-generated method stub
			Intent intent = new Intent(goods_information.this,
					PhotoActivity.class);

			goods_information.this.startActivityForResult(intent, 1);
	
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			if (actionType == GOODS_NAME) {
				nameTextView.setText(data.getStringExtra("retgoodsName"));
			} else if (actionType == PHONE) {
				phoneTextView.setText(data.getStringExtra("retphone"));
			} else if (actionType == PICTURE01) {
				if (data.getStringExtra("opera").equalsIgnoreCase("exitupload")) {
					Toast.makeText(getApplicationContext(), "第一张照片不能你为空！",Toast.LENGTH_SHORT).show();
					/*Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
					photo1 = data.getStringExtra("photoname");
					pictureIm01.setImageBitmap(bitmap);
					pictureIm01.setBackgroundDrawable(getResources().getDrawable(R.drawable.picture));*/

				} else {
					Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
					photo1 = data.getStringExtra("photoname");
					if (photo1==null) {
						Toast.makeText(getApplicationContext(), "第一张照片不能为空！",Toast.LENGTH_SHORT).show();
					}else {
						pictureIm01.setImageBitmap(bitmap);
					}
					
				}

			} else if (actionType == PICTURE02) {
				if (data.getStringExtra("opera").equalsIgnoreCase("exitupload")) {
//					Toast.makeText(goods_information.this, "------", Toast.LENGTH_LONG).show();
					Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
					photo2 = data.getStringExtra("photoname");
					pictureIm02.setImageBitmap(bitmap);
//					pictureIm01.setBackgroundResource(null);
					pictureIm02.setBackgroundDrawable(getResources().getDrawable(R.drawable.picture));
//					pictureIm01.setBackgroundResource(R.drawable.picture );
				} else {
					Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
					photo2 = data.getStringExtra("photoname");
					pictureIm02.setImageBitmap(bitmap);
				}

			} else if (actionType == PICTURE03) {
				if (data.getStringExtra("opera").equalsIgnoreCase("exitupload")) {
//					Toast.makeText(goods_information.this, "------", Toast.LENGTH_LONG).show();
					Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
					photo3 = data.getStringExtra("photoname");
					pictureIm03.setImageBitmap(bitmap);
//					pictureIm01.setBackgroundResource(null);
					pictureIm03.setBackgroundDrawable(getResources().getDrawable(R.drawable.picture));
//					pictureIm01.setBackgroundResource(R.drawable.picture );
				} else {
					Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
					photo3 = data.getStringExtra("photoname");
					pictureIm03.setImageBitmap(bitmap);
				}

			} else if (actionType == USER_NAME) {
				
				user_nameTextView.setText(data.getStringExtra("retuserName"));
			} else if (actionType == PRICE) {
				priceTextView.setText(data.getStringExtra("retprice"));
			} else if (actionType == GOODS_CATAGORY) {
				catagoryTextView.setText(data.getStringExtra("catagory"));
				if(data.getStringExtra("catagory").equals("免费赠送")){
					priceTextView.setText("0");
					priceTextView.setEnabled(false);
				}else {
					priceTextView.setEnabled(true);
				}
			} else if (actionType == INTRODUCE) {
				introducetv.setText(data.getStringExtra("retintroduce"));
			}
			break;
		case RESULT_CANCELED:
			break;
		default:
			break;
		}

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
				 goods_information.this, 0, new Intent(goods_information.this,message.class),0);
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
