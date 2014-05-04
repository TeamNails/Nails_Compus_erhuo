package com.erhuo.message;

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

import com.erhuo.app.util.Tools;
import com.erhuo.collection.collectionList;
import com.erhuo.forsale.Myadapter;
import com.erhuo.main.R;
import com.erhuo.manage.ManageInfo;
import com.erhuo.salegoods.GoodsActivity;
import com.erhuo.salegoods.introduceInput;
import com.erhuo.server.MyMessage;
import com.erhuo.server.NewsS;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class message extends ListActivity {
	private String userid;
	int start;
	ArrayList Listgoodsid = new ArrayList();
	ArrayList<Integer> Liststate = new ArrayList();
	ArrayList Listnews = new ArrayList();
	ArrayList Listnewstime = new ArrayList();
	ArrayList Listuserid2 = new ArrayList();
	private LayoutInflater mInflater;
	View convertView;
	ListView lv;
	Myadapter listAdapter;
//	private ImageView read;
	List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message);

		// userid = this.getIntent().getStringExtra("userid");
		userid = Tools.userid;
		this.mInflater = LayoutInflater.from(message.this);
		convertView = mInflater.inflate(R.layout.message_show, null);
//		read = (ImageView) convertView.findViewById(R.id.read);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 不自动显示软键盘
		lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListenerImpg());
		
		lv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.setHeaderTitle("是否删除");

				menu.add(1, 1, 0, "删除");
			}
		});
		
		getnews();
		showlist();
		notifym();

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
				Dialog dialog = new AlertDialog.Builder(message.this)
						.setTitle("删除")
						.setMessage("确定要删除吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									
										if(deletemessage(Tools.userid,(Integer)Listgoodsid.get(start),(String)Listuserid2.get(start))){
										list.remove(start);
										listAdapter.notifyDataSetChanged();}
									}
								}).setNegativeButton("取消", null).create();
				dialog.show();
				break;

			}

			return false;
		}


	private class OnItemClickListenerImpg implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) { // 选项单击事件
			changestate(position);
			if ((Integer) Listgoodsid.get(position)==0) {
				Intent intent = new Intent(message.this, ChatMain.class);
				intent.putExtra("userid2",(String)Listuserid2.get(position));
					message.this.startActivityForResult(intent, 1);
			}else {

				Intent intent = new Intent(message.this, GoodsActivity.class);
			intent.putExtra("goodsid", (Integer) Listgoodsid.get(position));
				intent.putExtra("type", "collect");
				message.this.startActivityForResult(intent, 1);
			}

		}
	}

	public void getnews() {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			// message.setValue(value)
			message.setType(message.DOWNNEWS);
			Hashtable table = new Hashtable();
			table.put("userid", userid);
			message.setValue(table);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<NewsS> vv = (Vector<NewsS>) message.getReturnValue()
						.get("news");
				for (final NewsS news : vv) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					Listgoodsid.add(news.getGoodsid());
					Listnews.add(news.getNews());
					Listnewstime.add(news.getNewstime());
					Liststate.add(news.getState());
					Listuserid2.add(news.getUserid2());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// 删除信息
		public boolean deletemessage(String userid,int goodsid,String userid2) {
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
				table.put("goodsid", goodsid);
				table.put("userid2", userid2);
				m1.setValue(table);
				m1.setType(m1.MESSAGEDELETE);
				oout.writeObject(m1);
				oout.flush();
				m1 = (MyMessage) oin.readObject();
				if (m1.getReturnValue().get("message").toString()
						.equalsIgnoreCase("ok")) {
					Toast.makeText(message.this, "删除成功!".toString(),
							Toast.LENGTH_LONG).show();
					b = true;
				} else {
					Toast.makeText(message.this, "删除失败!".toString(),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(message.this, "网络不通!", Toast.LENGTH_LONG).show();

			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
			return b;

		}

	public void name() {

	}

	public void back_OnClick(View v) {

		
		this.finish();
		overridePendingTransition(R.anim.bottom_top, R.anim.bottom_top2);

	}

	public void showlist() {
		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < Listgoodsid.size(); i++) {
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("message_neirong", "      "+Listnews.get(i));
			map1.put("message_time", Listnewstime.get(i));
			if (Liststate.get(i) == 0) {
				map1.put("message_State", "0");
			} else {
				map1.put("message_State", "1");
			}
			list.add(map1);
		}
		listAdapter = new Myadapter(this, this.list,
				R.layout.message_show, new String[] { "message_neirong",
						"message_time", "message_State" }, new int[] {
						R.id.message_neirong, R.id.message_time, R.id.read },
				6, Liststate);
		setListAdapter(listAdapter);
	}

	public int messagenum() {
		int num = 0;
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			Hashtable table = new Hashtable();
			table.put("userid", userid);
			message.setValue(table);
			message.setType(message.STATENUM);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();

			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				num = (Integer) message.getReturnValue().get("num");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;

	}

	public void changestate(int position) {
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
			table.put("goodsid", (Integer)Listgoodsid.get(position));
			m1.setValue(table);
			m1.setType(m1.CHANGESTATE);

			oout.writeObject(m1);
			oout.flush();
			m1 = (MyMessage) oin.readObject();
			if (m1.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				if (Tools.num != 0) {
					Tools.num2 = Tools.num - 1;
				}
				Liststate.set(position, 1);
			} else {
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	public void notifym() {
		// 获得通知管理器
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// 构建一个通知对象
		Notification notification = new Notification(R.drawable.icon, "新消息",
				System.currentTimeMillis());

		PendingIntent pendingIntent = PendingIntent.getActivity(message.this,
				0, new Intent(message.this, message.class), 0);
		notification.setLatestEventInfo(getApplicationContext(), "新信息",
				"通知显示的内容", pendingIntent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL; // 自动终止
		notification.defaults |= Notification.DEFAULT_SOUND; // 默认声音
		Tools.context = getApplicationContext();
		Tools.manager = manager;
		Tools.notification = notification;
		Tools.pendingIntent = pendingIntent;
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

	@Override
	protected void onResume() {
		showlist();
		notifym();
		super.onResume();
	}
}
