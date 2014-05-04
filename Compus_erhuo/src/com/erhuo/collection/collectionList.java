package com.erhuo.collection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.Myadapter;
import com.erhuo.forsale.forSale;
import com.erhuo.main.MainActivity;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.salegoods.GoodsActivity;
import com.erhuo.salegoods.goods_information;
import com.erhuo.server.AskBuyS;
import com.erhuo.server.CollectS;
import com.erhuo.server.MyMessage;

public class collectionList extends Activity {
	// private SimpleAdapter simpleAdapter=null;
	private ListView listView = null;
	private String userid;
	int start = 0;
	private Integer goodsid[]=new Integer[10];
	String goodsphoto1 = null;
	Myadapter myadapter = null;
	List<Map<String, Object>> list = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题栏

		setContentView(R.layout.shoucang_activity);
		notifym();
		Intent intent = super.getIntent();
		userid = intent.getStringExtra("userid");
		list = new ArrayList<Map<String, Object>>();
		try {
			Log.e("1","1");
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.SHOUCANGJ);
			Hashtable table = new Hashtable();
			table.put("userid", userid);
			message.setValue(table);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			Log.e("1","2");
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
			
				Vector<CollectS> vv = (Vector<CollectS>) message
						.getReturnValue().get("collect");
				int i=0;
				for (final CollectS collect : vv) {
					goodsid[i]=collect.getGoodsid();
					String goodsname = collect.getGoodsname();
					String goodsprice = collect.getGoodsprice();
					goodsphoto1 = collect.getGoodsphoto1();
					String collecttime = collect.getCollecttime().substring(0,19);
					String address=collect.getAddress();
					HashMap<String, Object> map1 = new HashMap<String, Object>();
					map1.put("pic", "/sdcard/destDir/" + goodsphoto1);
					map1.put("name", goodsname);
					map1.put("price", goodsprice);
					map1.put("time", collecttime);
					map1.put("address", address);
					list.add(map1);
					i++;
					Log.e("1","3");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView = (ListView) findViewById(R.id.datalist);
		myadapter = new Myadapter(this, this.list, R.layout.selling,
				new String[] { "pic", "name", "price", "time","address" }, new int[] {
						R.id.goodsimageshow, R.id.goodsnameshow,
						R.id.priceshow, R.id.timeshow ,R.id.addressshow}, 4);

		listView.setAdapter(myadapter); // 设置图片

		listView.setOnItemClickListener(new OnItemClickListenerIml());
	

		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.setHeaderTitle("删除");

				menu.add(1, 1, 0, "删除");
			}
		});
	}

	// 长安菜单显示

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		start = info.position;

		switch (item.getItemId()) {
		case 0:

			break;

		case 1:
			Dialog dialog = new AlertDialog.Builder(collectionList.this)
					.setTitle("删除")
					.setMessage("确定要删除吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									
									if(deletecollect(goodsid[start])){
									list.remove(start);
									myadapter.notifyDataSetChanged();}
								}
							}).setNegativeButton("取消", null).create();
			dialog.show();
			break;

		}

		return false;
	}

	private class OnItemClickListenerIml implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(collectionList.this,
					GoodsActivity.class);
			intent.putExtra("userid", userid);
			intent.putExtra("type", "nocollect");

			intent.putExtra("goodsid", goodsid[position]);
			collectionList.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);

		}

	}



	// 返回按钮
	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
		overridePendingTransition( R.anim.right_left2,R.anim.right_left);

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
	
	public boolean deletecollect(int x) {
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
			table.put("goodsid",x);
			m1.setValue(table);
			m1.setType(m1.DELETECOLLECT);
			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {	
				Toast.makeText(
							collectionList.this,
							"删除成功!".toString(), Toast.LENGTH_LONG)
							.show();
							b= true;
			} else {
				Toast.makeText(
						collectionList.this,
						"删除失败!".toString(), Toast.LENGTH_LONG)
						.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(collectionList.this, "网络不通!",
					Toast.LENGTH_LONG).show();

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
				 collectionList.this, 0, new Intent(collectionList.this,message.class),0);
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
