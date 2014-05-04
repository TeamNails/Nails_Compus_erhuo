package com.erhuo.salegoods;

//import com.example.goodsui.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.erhuo.forsale.forsaleChange;
import com.erhuo.main.MainActivity;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.server.MyMessage;

public class Change_goods_information extends Activity {
	// public RelativeLayout user_headLayout=null;

	// private ImageView headImageView =null;

	private RelativeLayout user_nameLayout, goods_nameLayout,
			goods_catagoryLayout, phoneLayout, goods_priceLayout,
			introducerl = null;
	private Button returnButton, tijiaoButton = null;
	private TextView user_nameTextView, nameTextView, catagoryTextView,
			phoneTextView, priceTextView, biaotiTextView, introducetv = null;
	int actionType = 0;
	private ImageView pictureIm01, pictureIm02, pictureIm03,titleim;
	private int goodsid;
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
		goodsid = this.getIntent().getIntExtra("goodsid", 0);

		findView();
		 notifym();
//		biaotiTextView.setText("修改信息");
		tijiaoButton.setVisibility(tijiaoButton.INVISIBLE);
		titleim.setImageDrawable(getResources().getDrawable(R.drawable.xiugaixinxi));
		getInfo();
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

		// 显示初始照片
		photoshow(photo1, pictureIm01);
		photoshow(photo2, pictureIm02);
		photoshow(photo3, pictureIm03);

		returnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Change_goods_information.this.getIntent().putExtra("info","goodsinfo");
				Change_goods_information.this.setResult(RESULT_OK,
						Change_goods_information.this.getIntent());
				Change_goods_information.this.finish();
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});

		onclick();
	
	}

	// 显示初始图片
	public void photoshow(String photo, ImageView iv) {
		if (!photo.equalsIgnoreCase("")) {
			getphoto1(photo);
			File file = new File("/sdcard/destDir/" + photo);
			iv.setImageURI(Uri.fromFile((file)));
		}
	}

	// 获得要修改的信息
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
			table.put("goodsid", goodsid);
			m1.setValue(table);
			m1.setType(m1.CHANGEGOODSINFO);
			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				user_nameTextView.setText(m1.getReturnValue()
						.get("usersellname").toString());
				phoneTextView.setText(m1.getReturnValue().get("goodsphone")
						.toString());
				catagoryTextView.setText(m1.getReturnValue().get("goodsclass")
						.toString());
				nameTextView.setText(m1.getReturnValue().get("goodsname")
						.toString());
				priceTextView.setText(m1.getReturnValue().get("goodsprice")
						.toString());
				introducetv.setText(m1.getReturnValue().get("goodsinfo")
						.toString());
				Log.v("44444444444", m1.getReturnValue().get("goodsinfo")
						.toString());
				photo1 = m1.getReturnValue().get("goodsphoto1").toString();
				photo2 = m1.getReturnValue().get("goodsphoto2").toString();
				photo3 = m1.getReturnValue().get("goodsphoto3").toString();
				Log.v("44444444444", m1.getReturnValue().get("goodsphoto1")
						.toString());
			} else {
				Toast.makeText(Change_goods_information.this,
						m1.getReturnValue().get("message").toString(),
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(Change_goods_information.this,
					"网络不通!" + e.getMessage(), Toast.LENGTH_LONG).show();

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
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
//		biaotiTextView = (TextView) findViewById(R.id.biaoti);
		tijiaoButton = (Button) findViewById(R.id.tijiao_btn);
		titleim=(ImageView)findViewById(R.id.title);

	}

	void onclick() {
		user_nameLayout.setOnClickListener(new user_nameLayoutOnclickImp());
		goods_nameLayout.setOnClickListener(new goods_nameLayoutOnclickImp());
		goods_catagoryLayout
				.setOnClickListener(new goods_catagoryLayoutOnclickImp());
		goods_priceLayout
				.setOnClickListener(new goods_priceLayoutOnclickImpl());
		phoneLayout.setOnClickListener(new phoneLayoutOnclickImp());
		introducerl.setOnClickListener(new introducerlOnclickImp());
		pictureIm01.setOnClickListener(new pictureIm01OnclickImp());
		pictureIm02.setOnClickListener(new pictureIm02OnclickImp());
		pictureIm03.setOnClickListener(new pictureIm03OnclickImp());
	}

	// 内容输入

	class introducerlOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = INTRODUCE;
			// TODO Auto-generated method stub
			Intent intent = new Intent(Change_goods_information.this,
					introduceInput.class);
			intent.putExtra("introduce", introducetv.getText());
			Change_goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

		}
		return super.onKeyDown(keyCode, event);
	}
	// 货主名字输入
	class user_nameLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = USER_NAME;
			// TODO Auto-generated method stub
			Intent intent = new Intent(Change_goods_information.this,
					userNameInput.class);
			intent.putExtra("userName", user_nameTextView.getText());
			Change_goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 货物名字输入
	class goods_nameLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = GOODS_NAME;
			// TODO Auto-generated method stub
			Intent intent = new Intent(Change_goods_information.this,
					goodsNameInput.class);
			intent.putExtra("goodsName", nameTextView.getText());
			Change_goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 类别触发 （未完）
	class goods_catagoryLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = GOODS_CATAGORY;
			// TODO Auto-generated method stub
			Intent intent = new Intent(Change_goods_information.this,
					goods_catagory.class);
			Change_goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 价格触发
	class goods_priceLayoutOnclickImpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PRICE;

			Intent intent = new Intent(Change_goods_information.this,
					priceInput.class);
			intent.putExtra("price", priceTextView.getText());
			Change_goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	// 手机号触发
	class phoneLayoutOnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PHONE;
			// TODO Auto-generated method stub
			Intent intent = new Intent(Change_goods_information.this,
					phoneInput.class);
			intent.putExtra("phone", phoneTextView.getText());
			Change_goods_information.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	class pictureIm01OnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PICTURE01;
			// TODO Auto-generated method stub
			Intent intent = new Intent(Change_goods_information.this,
					PhotoActivity.class);
			Change_goods_information.this.startActivityForResult(intent, 1);
//			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

		}

	}

	class pictureIm02OnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PICTURE02;
			// TODO Auto-generated method stub
			Intent intent = new Intent(Change_goods_information.this,
					PhotoActivity.class);
			Change_goods_information.this.startActivityForResult(intent, 1);
//			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	class pictureIm03OnclickImp implements OnClickListener {

		@Override
		public void onClick(View v) {
			actionType = PICTURE03;
			// TODO Auto-generated method stub
			Intent intent = new Intent(Change_goods_information.this,
					PhotoActivity.class);
			intent.putExtra("type", "change");
			Change_goods_information.this.startActivityForResult(intent, 1);
//			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			if (actionType == GOODS_NAME) {
				if (changesell("goodsname", data.getStringExtra("retgoodsName"))) {
					nameTextView.setText(data.getStringExtra("retgoodsName"));
				}
			} else if (actionType == PHONE) {
				if (changesell("goodsphone", data.getStringExtra("retphone"))) {
					phoneTextView.setText(data.getStringExtra("retphone"));
				}

			} else if (actionType == PICTURE01) {
				if ( data.getStringExtra("photoname").equals("")) {
					Toast.makeText(getApplicationContext(), "第一张照片不能为空", Toast.LENGTH_SHORT).show();
				}else {
					if (changesell("goodsphoto1", data.getStringExtra("photoname"))) {
						Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
						photo1 = data.getStringExtra("photoname");
						pictureIm01.setImageBitmap(bitmap);
				}
				
				}

			} else if (actionType == PICTURE02) {
				if (changesell("goodsphoto2", data.getStringExtra("photoname"))) {
					if (!data.getStringExtra("photoname").equals("")) {

						Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
						photo2 = data.getStringExtra("photoname");
						pictureIm02.setImageBitmap(bitmap);
					}
					pictureIm02.setBackgroundDrawable(getResources().getDrawable(R.drawable.picture));
					
					
				}

			} else if (actionType == PICTURE03) {
				
				if (changesell("goodsphoto3", data.getStringExtra("photoname"))) {
					if (!data.getStringExtra("photoname").equals("")) {
						Bitmap bitmap = (Bitmap) data.getParcelableExtra("BITMAP");
						photo3 = data.getStringExtra("photoname");
						pictureIm03.setImageBitmap(bitmap);
					}
					pictureIm03.setBackgroundDrawable(getResources().getDrawable(R.drawable.picture));

				}

			} else if (actionType == USER_NAME) {
				if (changesell("usersellname",
						data.getStringExtra("retuserName"))) {
					user_nameTextView.setText(data
							.getStringExtra("retuserName"));
				}
			} else if (actionType == PRICE) {
				if (changesell("goodsprice", data.getStringExtra("retprice"))) {
					priceTextView.setText(data.getStringExtra("retprice"));
				}
			} else if (actionType == GOODS_CATAGORY) {
				if (changesell("goodsclass", data.getStringExtra("catagory"))) {
					catagoryTextView.setText(data.getStringExtra("catagory"));
				}

			} else if (actionType == INTRODUCE) {
				if (changesell("goodsinfo", data.getStringExtra("retintroduce"))) {
					introducetv.setText(data.getStringExtra("retintroduce"));
				}
			}
			break;
		case RESULT_CANCELED:
			break;
		default:
			break;
		}

	}

	// 修改商品信息
	public boolean changesell(String type, String value) {
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
			table.put("goodsid", goodsid);
			m1.setValue(table);
			m1.setType(m1.CHANGEGOODS);
			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {

			} else {
				Toast.makeText(Change_goods_information.this,
						m1.getReturnValue().get("message").toString(),
						Toast.LENGTH_LONG).show();
				b = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(Change_goods_information.this,
					"网络不通!" + e.getMessage(), Toast.LENGTH_LONG).show();
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

	// 获取照片
	public void getphoto1(String photo) {
		try {
			File f = new File("/sdcard/destDir/", photo);
			if (!f.exists()) {
				Socket socket = new Socket(Tools.IP, Tools.PORT_3);
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				out.write(("download,image," + photo).getBytes());
				out.flush();
				byte[] b = new byte[1024];
				in.read(b);
				long size = Long.parseLong(new String(b).trim());

				out.write("OK".getBytes());
				out.flush();

				FileOutputStream outf = new FileOutputStream(f);
				int len = 0;
				long length = 0;
				while ((len = in.read(b)) != -1) {
					length += len;
					outf.write(b, 0, len);
					if (length >= size) {
						break;
					}
				}
				outf.close();
				in.close();
				out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
}
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 Change_goods_information.this, 0, new Intent(Change_goods_information.this,message.class),0);
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