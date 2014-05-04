package com.erhuo.salegoods;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.forSale;
import com.erhuo.main.R;
import com.erhuo.manage.ManageInfo;
import com.erhuo.message.ChatMain;
import com.erhuo.message.message;
import com.erhuo.server.MyMessage;
import com.erhuo.server.Pingluns;
import com.erhuo.server.SellS;

public class GoodsActivity extends Activity {
	public LinearLayout linear1, linear2 = null;
	private TextView phoneView, goodsName, jianjieTextView, priceTextView,
			timeTextView, sellerTextView,sexTextView,addressTextView,gradeTextView,uname,qqTextView,emailTextView=null;
	int screenWidth, screenHeight, goodsid;
	private String thingname, thingprice, selltime, sellusername, goodsphone,
	goodsinfo,thinguserid,username,sex,address,grade,qq,email ;
	String typeString,userid2;
	private String userid, leavewordc;
	private EditText et_sendmessage;
	private Button opView;
	private ImageView contact=null;
	File file = null;
	// contactDialog cDialog=null;
	@SuppressWarnings("deprecation")
	private Gallery pictureGallery = null;

	public String[] picture = null;

	String photo1, photo2, photo3 = null;
	private int index = 0;
	private Button shousang_btn, btn_send;

	// @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题栏

		setContentView(R.layout.activity_goods);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 不自动显示软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // 软键盘把布局顶起
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		Intent intent = super.getIntent();
		userid =Tools.userid;
		typeString=intent.getStringExtra("type");
		
		notifym();
		goodsid = intent.getIntExtra("goodsid", 0);
	   Log.e("1 ", ""+goodsid);

		
		
		screenWidth = metric.widthPixels; // 屏幕宽度（像素）
		screenHeight = metric.heightPixels;
		phoneView = (TextView) findViewById(R.id.phoneView);
		goodsName = (TextView) findViewById(R.id.goodsName);
		goodsName.setHorizontallyScrolling(true);

		jianjieTextView = (TextView) findViewById(R.id.jianjie);
		priceTextView = (TextView) findViewById(R.id.price);
		timeTextView = (TextView) findViewById(R.id.timetv);
		jianjieTextView.setMinHeight(screenHeight / 5);
		sellerTextView = (TextView) findViewById(R.id.sellertv);
		shousang_btn = (Button) findViewById(R.id.shousang_btn);
		et_sendmessage = (EditText) findViewById(R.id.et_sendmessage);
		btn_send = (Button) findViewById(R.id.btn_send);
		opView=(Button)findViewById(R.id.shousang_btn);
		sexTextView=(TextView)findViewById(R.id.usexshow);
		addressTextView=(TextView)findViewById(R.id.uadshow);
		gradeTextView=(TextView)findViewById(R.id.gradeshow);
		uname=(TextView)findViewById(R.id.unameshow);
		qqTextView=(TextView)findViewById(R.id.uqqshow);
		emailTextView=(TextView)findViewById(R.id.uemailshow);
		contact=(ImageView)findViewById(R.id.contact);
		contact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(GoodsActivity.this,ChatMain.class);
				intent.putExtra("userid2", userid2);
				GoodsActivity.this.startActivityForResult(intent,1);
				overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);

			}
		});
		qqTextView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Dialog dialog = new AlertDialog.Builder(GoodsActivity.this)
				.setTitle("复制")
				.setMessage("复制到粘贴板？")
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								final ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
								//在Menu事件呼叫的时候运行这一行代码 文字就复制到粘贴板了
								clipBoard.setText(qqTextView.getText());

							}
						}).setNegativeButton("取消", null).create();
		dialog.show();
				
				return false;
			}
		});
		emailTextView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Dialog dialog = new AlertDialog.Builder(GoodsActivity.this)
				.setTitle("复制")
				.setMessage("复制到粘贴板？")
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								final ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
								//在Menu事件呼叫的时候运行这一行代码 文字就复制到粘贴板了
								clipBoard.setText(emailTextView.getText());

							}
						}).setNegativeButton("取消", null).create();
		dialog.show();
				
				return false;
			}
		});
		
		
		
		if(typeString.equals("nocollect")){
			opView.setVisibility(opView.INVISIBLE);
		}
		getgoodsinfox();
		getphoto(photo1);
		getphoto(photo2);//
		getphoto(photo3);//
		phoneView.setText(goodsphone);
		jianjieTextView.setText(goodsinfo);
		goodsName.setText(thingname);
		priceTextView.setText(thingprice);
		timeTextView.setText(selltime.substring(0, 10));//
		sellerTextView.setText(sellusername);
		uname.setText(sellusername);
		sexTextView.setText(sex);
		addressTextView.setText(address);
		gradeTextView.setText(grade);
		qqTextView.setText(qq);
		
		emailTextView.setText(email);
		System.out.println(qq+"----------------");
		phoneView.setOnClickListener(new phoneViewListener());
		// 收藏物品
		shousang_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Socket socket = null;
				try {
					socket = new Socket(Tools.IP, Tools.PORT_1);
					ObjectOutputStream oout = new ObjectOutputStream(socket
							.getOutputStream());
					ObjectInputStream oin = new ObjectInputStream(socket
							.getInputStream());

					MyMessage m1 = new MyMessage();
					Hashtable table = new Hashtable();
					table.put("userid", userid);
					table.put("goodsid", goodsid);
					m1.setValue(table);
					m1.setType(m1.SHOUCANG);

					oout.writeObject(m1);
					oout.flush();
					m1 = (MyMessage) oin.readObject();
					if (m1.getReturnValue().get("message").toString()
							.equalsIgnoreCase("ok")) {

						AlertDialog.Builder bb = new AlertDialog.Builder(
								GoodsActivity.this);
						bb.setTitle("收藏成功！");
						bb.setNeutralButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
						bb.create().show();

					}else if (m1.getReturnValue().get("message").toString()
							.equalsIgnoreCase("isown")) {
						Toast.makeText(
								GoodsActivity.this,
							"对不起，您不能收藏自己的商品！", Toast.LENGTH_LONG)
								.show();
					} 
					else {
						Toast.makeText(
								GoodsActivity.this,
//								"收藏失败!"
										 m1.getReturnValue().get("message")
												.toString(), Toast.LENGTH_LONG)
								.show();
					}

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(GoodsActivity.this, "网络不通!",
							Toast.LENGTH_LONG).show();

				} finally {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}

			}
		});
		// 评论信息
		btn_send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Socket socket = null;
				try {
					socket = new Socket(Tools.IP, Tools.PORT_1);
					ObjectOutputStream oout = new ObjectOutputStream(socket
							.getOutputStream());
					ObjectInputStream oin = new ObjectInputStream(socket
							.getInputStream());

					leavewordc = et_sendmessage.getText().toString();
					if (leavewordc.equals("")) {
						Toast.makeText(GoodsActivity.this, "留点东西吧！!",
								Toast.LENGTH_LONG).show();
					}else {
						MyMessage m1 = new MyMessage();
						Hashtable table = new Hashtable();
						table.put("userid", userid);
						table.put("goodsid", goodsid);
						table.put("leavewordc", leavewordc);

						m1.setValue(table);
						m1.setType(m1.PINGLUN);

						oout.writeObject(m1);
						oout.flush();
						m1 = (MyMessage) oin.readObject();
						if (m1.getReturnValue().get("message").toString()
								.equalsIgnoreCase("ok")) {
							Toast.makeText(GoodsActivity.this, "提交成功!",
									Toast.LENGTH_LONG).show();
							linear1.removeAllViews();
							showpinglun();// 刷新评论信息
							et_sendmessage.setText("");
						} else {
							Toast.makeText(
									GoodsActivity.this,
									"评论失败!"
											+ m1.getReturnValue().get("message")
													.toString(), Toast.LENGTH_LONG)
									.show();
						}
					}
					

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(GoodsActivity.this, "网络不通!",
							Toast.LENGTH_LONG).show();

				} finally {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		});

		if (!photo1.equalsIgnoreCase("") && photo2.equalsIgnoreCase("")
				&& photo3.equalsIgnoreCase("")) {

			picture = new String[] { photo1 };

		} else if (!photo2.equalsIgnoreCase("") && photo1.equalsIgnoreCase("")
				&& photo3.equalsIgnoreCase("")) {

			picture = new String[] { photo2 };

		} else if (!photo3.equalsIgnoreCase("") && photo1.equalsIgnoreCase("")
				&& photo2.equalsIgnoreCase("")) {

			picture = new String[] { photo3 };
		} else if (photo1.equalsIgnoreCase("") && !photo2.equalsIgnoreCase("")
				&& !photo3.equalsIgnoreCase("")) {

			picture = new String[] { photo2, photo3 };

		} else if (!photo3.equalsIgnoreCase("") && !photo1.equalsIgnoreCase("")
				&& photo2.equalsIgnoreCase("")) {

			picture = new String[] { photo1, photo3 };

		} else if (photo3.equalsIgnoreCase("") && !photo1.equalsIgnoreCase("")
				&& !photo2.equalsIgnoreCase("")) {

			picture = new String[] { photo1, photo2 };
		} else {

			picture = new String[] { photo1, photo2, photo3 };
		}

		this.pictureGallery = (Gallery) findViewById(R.id.gallery);
		pictureGallery.setFadingEdgeLength(0);
		ImageAdapter adapter = new ImageAdapter(this);
		this.pictureGallery.setAdapter(adapter);
		pictureGallery
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// 这里不做响应
					}
				});
		// Toast.makeText(GoodsActivity.this,
		// "-------------",Toast.LENGTH_LONG);

		//
		Timer timer = new Timer();
		timer.schedule(task, 2000, 2000);
		//
	}

	// 下载数据
	public void getgoodsinfox() {
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.GOODSINFOSHOW);
			Hashtable table = new Hashtable();
			table.put("goodsid", goodsid);
			message.setValue(table);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<SellS> vv = (Vector<SellS>) message.getReturnValue()
						.get("sell");
				for (final SellS sell : vv) {
					thingname = sell.getGoodsname();
				
					thinguserid=sell.getUserid();
					thingprice = sell.getGoodsprice();
					selltime = sell.getSelltime();
					sellusername = sell.getUsersellname();
					goodsphone = sell.getGoodsphone();
					goodsinfo = sell.getGoodsinfo();
					userid2=sell.getUserid();
					photo1 = sell.getGoodsphoto1();
					photo2 = sell.getGoodsphoto2();
					photo3 = sell.getGoodsphoto3();
					username = sell.getUsername();
					sex = sell.getSex();
					address = sell.getAddress();
					grade =sell.getGrade();
					qq=sell.getQq();
					System.out.println(qq);
					email=sell.getEmail();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public void getphoto(String photo) {
		try {
			File f = new File("/sdcard/destDir/", photo);//
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

	public void back_btn(View v) {
		this.finish();
		overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);

	}

	/**
	 * 自定义一个图片显示适配器
	 * 
	 * @author TanRuixiang
	 * 
	 */
	class ImageAdapter extends BaseAdapter {
		private int GalleryItemBackground;
		private Context context;

		public ImageAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return picture.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("deprecation")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);

			file = new File("sdcard/destDir/" + picture[position]);
			imageView.setImageURI(Uri.fromFile(file));

			imageView.setAdjustViewBounds(true);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new Gallery.LayoutParams(
					Gallery.LayoutParams.FILL_PARENT,
					Gallery.LayoutParams.FILL_PARENT));
			imageView.setBackgroundResource(GalleryItemBackground);
			return imageView;
		}

	}

	/**
	 * 定时器 //
	 */
	private TimerTask task = new TimerTask() {

		@Override
		public void run() {
			Message message = new Message();
			message.what = 2;
			index = pictureGallery.getSelectedItemPosition();
			index++;
			if (index >= picture.length) {

				index = 0;
			}

			handler.sendMessage(message);
		}
	};

	/**
	 * Handler //
	 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				pictureGallery.setSelection(index);
				break;

			default:
				break;
			}
		}

	};

	class phoneViewListener implements OnClickListener {
		public void onClick(View v) {
			String phoneNumber = phoneView.getText().toString();
			Intent intent = new Intent();
			intent.putExtra("phoneNumber", phoneNumber);
			intent.putExtra("name", thingname);
			intent.setClass(GoodsActivity.this, contact.class);
			GoodsActivity.this.startActivity(intent);
		}
	}

	@Override
	protected void onStart() {// 显示评论
		super.onStart();
		linear1 = (LinearLayout) findViewById(R.id.pingjia_line);
		showpinglun();
	}

	public void showpinglun() {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.PINGLUN_DOWN);
			Hashtable table = new Hashtable();
			table.put("goodsid", goodsid);
			message.setValue(table);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				//
				Vector<Pingluns> vv = (Vector<Pingluns>) message
						.getReturnValue().get("pinglun");
				for (final Pingluns Pinglun : vv) {
					LinearLayout linear2 = (LinearLayout) LayoutInflater.from(
							this).inflate(R.layout.pingjia_item, null);
					TextView usernametv = (TextView) linear2
							.findViewById(R.id.usernametv);
					TextView leavewordtv = (TextView) linear2
							.findViewById(R.id.leavewordtv);
					TextView timetv = (TextView) linear2
							.findViewById(R.id.timetv);

					String username = Pinglun.getUsername();
					String leavewordc = Pinglun.getLeavewordc();
					String leavewordtime = Pinglun.getLeavewordtime().substring(0, 19);

					usernametv.setText(username);
					leavewordtv.setText(leavewordc);
					timetv.setText(leavewordtime);

					linear1.addView(linear2);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);

		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 GoodsActivity.this, 0, new Intent(GoodsActivity.this,message.class),0);
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
