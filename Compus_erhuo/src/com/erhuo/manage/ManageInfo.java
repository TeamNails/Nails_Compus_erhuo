package com.erhuo.manage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.Myadapter;
import com.erhuo.forsale.forSale;
import com.erhuo.forsale.forsaleChange;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.salegoods.Change_goods_information;
import com.erhuo.salegoods.GoodsActivity;
import com.erhuo.salegoods.PhotoActivity;
import com.erhuo.server.AskBuyS;
import com.erhuo.server.MyMessage;
import com.erhuo.server.SellS;

public class ManageInfo extends Activity implements OnClickListener {
	private ImageView salegoodsTextView, forsaleTextView = null;
	private ListView listView1, listView2;
	int screenWidth, screenHeight = 0;
	int start = 0;
	private int[] goodsid = new int[50];
	private int[] askbuyid = new int[50];
	private String userid;
	String isdelete="fas";
	String ischange="fas";
	Myadapter ma;
	Intent intent = null;
	List<Map<String, Object>> list = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.manage_info);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		Intent intent = super.getIntent();
		userid = intent.getStringExtra("userid");
		salegoodsTextView = (ImageView) findViewById(R.id.sale);
		forsaleTextView = (ImageView) findViewById(R.id.forsale);
		// listView=(ListView)findViewById(R.id.datalist);
		salegoodsTextView.setOnClickListener(this);
		forsaleTextView.setOnClickListener(this);
		listView1 = (ListView) findViewById(R.id.datalist);
		listView2 = (ListView) findViewById(R.id.datalist2);
		info_show();
		notifym();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			if (data.getStringExtra("info").equalsIgnoreCase("qiugou")) {
				listView1.setVisibility(listView1.INVISIBLE);
				listView2.setVisibility(listView2.VISIBLE);

				forsale_show();
			} else {
				listView2.setVisibility(listView2.INVISIBLE);
				listView1.setVisibility(listView1.VISIBLE);

				info_show();
			}
			break;
		case RESULT_CANCELED:
			break;
		default:
			break;
		}

	}

	// 长安菜单显示
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		start = info.position;

		switch (item.getItemId()) {
		case 0:// 修改卖东西
			intent = new Intent(ManageInfo.this, Change_goods_information.class);
			intent.putExtra("goodsid", goodsid[start]);
			startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);
			ischange="tru";
			break;
			

		case 1:// 删除卖东西
			Dialog dialog = new AlertDialog.Builder(ManageInfo.this)
					.setTitle("删除")
					.setMessage("确定要删除吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (deleteusergoods(goodsid[start])) {
										list.remove(start);
										ma.notifyDataSetChanged();
										isdelete="tru";
									}

								}
							}).setNegativeButton("取消", null).create();
			dialog.show();
			break;
		case 2:// 修改求购
			intent = new Intent(ManageInfo.this, forsaleChange.class);
			intent.putExtra("askbuyid", askbuyid[start]);
			startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);

			break;

		case 3:
			Dialog dialog2 = new AlertDialog.Builder(ManageInfo.this)
					.setTitle("删除")
					.setMessage("确定要删除吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (deleteaskbuy(askbuyid[start])) {
										list.remove(start);
										ma.notifyDataSetChanged();
										
									}

								}
							}).setNegativeButton("取消", null).create();
			dialog2.show();

			break;

		}

		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			this.getIntent().putExtra("isdelete", isdelete);
			this.getIntent().putExtra("ischange", "tru");

		
			setResult(Activity.RESULT_OK,ManageInfo.this.getIntent());
			
			this.finish();
			overridePendingTransition(R.anim.bottom_top,R.anim.bottom_top2);
		}
		return super.onKeyDown(keyCode, event);
	}
	public void back_OnClick(View v) {
		
		this.getIntent().putExtra("isdelete", isdelete);
		this.getIntent().putExtra("ischange", "tru");
		setResult(Activity.RESULT_OK,ManageInfo.this.getIntent());
		this.finish();
		overridePendingTransition(R.anim.bottom_top,R.anim.bottom_top2);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == salegoodsTextView) {
			listView2.setVisibility(listView2.INVISIBLE);
			listView1.setVisibility(listView1.VISIBLE);

			info_show();

		} else if (v == forsaleTextView) {
			listView1.setVisibility(listView1.INVISIBLE);
			listView2.setVisibility(listView2.VISIBLE);

			forsale_show();
		}
	}

	// 卖东西展示触发
	private class OnItemClickListenerIml implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(ManageInfo.this, GoodsActivity.class);
			intent.putExtra("userid", userid);
			intent.putExtra("goodsid", goodsid[position]);
			intent.putExtra("type", "nocollect");
			ManageInfo.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);

		}

	}

	// 卖东西显示
	public void info_show() {

		list = new ArrayList<Map<String, Object>>();

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.USERGOODSINFO);
			Hashtable table = new Hashtable();
			table.put("userid", userid);
			message.setValue(table);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<SellS> vv = (Vector<SellS>) message.getReturnValue()
						.get("sell");
				int i = 0;
				for (final SellS sell : vv) {
					String thingname = sell.getGoodsname();
					String thingprice = sell.getGoodsprice();
					goodsid[i] = sell.getGoodsid();
					String address=sell.getAddress();
//					String grade=sell.getGrade();
					String selltime = sell.getSelltime();
					String photo1 = sell.getGoodsphoto1();
					getphoto1(photo1);

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pic", "/sdcard/destDir/" + photo1);
					map.put("name", thingname);
					map.put("price", thingprice + "元");
					map.put("time", selltime.substring(0, 19));
					map.put("address", address);
					this.list.add(map);

					i++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ma = new Myadapter(this, this.list, R.layout.selling, new String[] {
				"pic", "name", "price", "time","address" }, new int[] {
				R.id.goodsimageshow, R.id.goodsnameshow, R.id.priceshow,
				R.id.timeshow ,R.id.addressshow}, 4);
		this.listView1.setAdapter(ma);
		listView1.setOnItemClickListener(new OnItemClickListenerIml());

		listView1
				.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						// TODO Auto-generated method stub
						menu.setHeaderTitle("管理");

						menu.add(0, 0, 0, "修改");
						menu.add(0, 1, 0, "删除");

					}
				});

	}

	// 求购显示
	public void forsale_show() {
		// listView1.removeAllViews();

		list = new ArrayList<Map<String, Object>>();
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.USERQIUGOUINFO);
			Hashtable table = new Hashtable();
			table.put("userid", userid);
			message.setValue(table);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<AskBuyS> vv = (Vector<AskBuyS>) message.getReturnValue()
						.get("askbuy");
				int i = 0;
				for (final AskBuyS askbuy : vv) {
					askbuyid[i] = askbuy.getAskbuyid();
					Log.v("1111111111111", askbuy.getAskbuyid() + "");
					String agoodsname = askbuy.getAgoodsname();
					String agoodsclass = askbuy.getAgoodsclass();
					String askbuycontent = askbuy.getAskbuycontent();
					String askbuyphone = askbuy.getAskbuyphone();
					String askbuytime = askbuy.getAskbuytime().substring(0, 10);
					String userqiuname = askbuy.getUserqiuname();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", agoodsname);
					map.put("type", agoodsclass);
					map.put("time", askbuytime);
					map.put("username", userqiuname);
					map.put("content", askbuycontent);
					map.put("phone", askbuyphone);
					this.list.add(map);

					i++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ma = new Myadapter(this, this.list, R.layout.forsale_show_manage,
				new String[] { "name", "type", "time", "username", "content","phone" },
				new int[] { R.id.goodsNametv, R.id.type, R.id.timetv,
						R.id.username_forsale, R.id.content,R.id.phone_forsale }, 1);
		this.listView2.setAdapter(ma);
		// listView2.setOnItemClickListener(new OnItemClickListenerIml1());

		listView2
				.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) { // TODO Auto-generated
														// // method stub
						menu.setHeaderTitle("管理");

						menu.add(2, 2, 0, "修改");
						menu.add(3, 3, 0, "删除");
					}
				});

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

	// 删除个人商品
	public boolean deleteusergoods(int x) {
		boolean b = false;
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
			table.put("goodsid", x);
			m1.setValue(table);
			m1.setType(m1.USERGOODSDELETE);
			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				Toast.makeText(ManageInfo.this, "删除成功!".toString(),
						Toast.LENGTH_LONG).show();
				b = true;
			} else {
				Toast.makeText(ManageInfo.this, "删除失败!"+ m1.getReturnValue().get("message")
						.toString(),
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(ManageInfo.this, "网络不通!", Toast.LENGTH_LONG).show();

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
		return b;

	}

	// 删除个人求购
	public boolean deleteaskbuy(int x) {
		boolean b = false;
		Socket socket = null;
		try {
			socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());

			MyMessage m1 = new MyMessage();
			Hashtable table = new Hashtable();
			table.put("askbuyid", x);
			m1.setValue(table);
			m1.setType(m1.QIUGOUDELETE);
			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				Toast.makeText(ManageInfo.this, "删除成功!".toString(),
						Toast.LENGTH_LONG).show();
				b = true;
			} else {
				Toast.makeText(ManageInfo.this, "删除失败!".toString(),
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(ManageInfo.this, "网络不通!", Toast.LENGTH_LONG).show();

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
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
				 ManageInfo.this, 0, new Intent(ManageInfo.this,message.class),0);
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
