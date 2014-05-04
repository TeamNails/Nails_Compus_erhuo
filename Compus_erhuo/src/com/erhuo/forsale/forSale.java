package com.erhuo.forsale;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.erhuo.app.util.Tools;
import com.erhuo.main.MainActivity;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.salegoods.GoodsActivity;
import com.erhuo.salegoods.contact;
import com.erhuo.server.AskBuyS;
import com.erhuo.server.MyMessage;
import com.miloisbadboy.view.PullToRefreshView;
import com.miloisbadboy.view.PullToRefreshView.OnFooterRefreshListener;
import com.miloisbadboy.view.PullToRefreshView.OnHeaderRefreshListener;

public class forSale extends ListActivity implements OnHeaderRefreshListener,
		OnFooterRefreshListener {
	private String userid;
	private Button forsale_Btn = null;
	List<Map<String, Object>> list = null;
	ListView lView=null;
	PullToRefreshView mPullToRefreshView;
	ArrayList agoodsname = new ArrayList();
	ArrayList agoodsclass = new ArrayList();
	ArrayList askbuycontent = new ArrayList();
	ArrayList askbuyphone = new ArrayList();
	ArrayList askbuytime = new ArrayList();
	ArrayList userqiuname = new ArrayList();
	ArrayList askbuyid = new ArrayList();
	ArrayList userid2 = new ArrayList();
	ArrayList qq = new ArrayList();
	ArrayList email = new ArrayList();
	// private Handler mHandler;
	Myadapter listAdapter = null;
	private LayoutInflater mInflater;
	View convertView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题栏
		setContentView(R.layout.forsale01);
		forsale_Btn = (Button) findViewById(R.id.forsale_Btn);

		notifym();
		
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		forsale_Btn.setOnClickListener(new forsale_BtnListener());
		userid =Tools.userid;
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 不自动显示软键盘
		download();
		showqiugoulist();
		
	}
	
		
	private class OnItemClickListenerImpg implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) { // 选项单击事件
//
//			Intent intent = new Intent(PathMenuActivity.this,
//					GoodsActivity.class);
//			intent.putExtra("userid", userid);
//			intent.putExtra("goodsid", (Integer)Listgoodsid.get(position));
//			PathMenuActivity.this.startActivityForResult(intent, 1);
			
			
			Intent intent = new Intent(forSale.this,
					com.erhuo.forsale.contact.class);
//			intent.putExtra("userid", userid);
			intent.putExtra("phoneNumber", (String)askbuyphone.get(position));
			intent.putExtra("name", (String)agoodsname.get(position));
			intent.putExtra("qq", (String)qq.get(position));
			intent.putExtra("email", (String)email.get(position));
			intent.putExtra("userid2", (String)userid2.get(position));
			forSale.this.startActivityForResult(intent, 1);
		}
		}
	
	

	public void showqiugoulist() {
		lView=getListView();
		
		list = new ArrayList<Map<String, Object>>();
		System.out.println(askbuyid.size());
		for (int x = 0;x<askbuyid.size(); x++) {
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("agoodsname", agoodsname.get(x));
			map1.put("askbuycontent", askbuycontent.get(x));
			map1.put("agoodsclass", agoodsclass.get(x));
			map1.put("askbuyphone", askbuyphone.get(x));
			map1.put("askbuytime", ((String)askbuytime.get(x)).substring(0,10));
			map1.put("userqiuname", userqiuname.get(x));
			map1.put("qq", qq.get(x));
			map1.put("email", email.get(x));

			list.add(map1);
		}
		listAdapter = new Myadapter(this, list, R.layout.forsale_show,
				new String[] { "agoodsname", "askbuycontent", "agoodsclass",
						"askbuyphone", "askbuytime", "userqiuname","qq","email" },
				new int[] { R.id.goodsNametv, R.id.content, R.id.type,
						R.id.phone_forsale, R.id.timetv, R.id.username_forsale,R.id.qq,R.id.email }, 2);
		setListAdapter(listAdapter);
		lView.setOnItemClickListener(new OnItemClickListenerImpg());
	}

	public void download() {
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.DOWNQIUGOU);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<AskBuyS> vv = (Vector<AskBuyS>) message.getReturnValue()
						.get("askbuy");
				for (final AskBuyS askbuy : vv) {
					askbuyid.add(askbuy.getAskbuyid());
					agoodsname.add(askbuy.getAgoodsname());
					agoodsclass.add(askbuy.getAgoodsclass());
					askbuycontent.add(askbuy.getAskbuycontent());
					askbuytime.add(askbuy.getAskbuytime().substring(0, 19));
					userqiuname.add(askbuy.getUserqiuname());
					askbuyphone.add(askbuy.getAskbuyphone());
					userid2.add(askbuy.getUserid());
					qq.add(askbuy.getQq());
					email.add(askbuy.getEmail());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getnewaskbuyinfo() {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			Hashtable table = new Hashtable();
			table.put("askbuyid", askbuyid.get(0));
			message.setValue(table);
			message.setType(message.REFRESHASKBUY);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<AskBuyS> vv = (Vector<AskBuyS>) message.getReturnValue()
						.get("askbuy");
				for (final AskBuyS askbuy : vv) {
					askbuyid.add(0,askbuy.getAskbuyid());
					agoodsname.add(0,askbuy.getAgoodsname());
					agoodsclass.add(0,askbuy.getAgoodsclass());
					askbuycontent.add(0,askbuy.getAskbuycontent());
					askbuytime.add(0,askbuy.getAskbuytime().substring(0, 19));
					userqiuname.add(0,askbuy.getUserqiuname());
					askbuyphone.add(0,askbuy.getAskbuyphone());
					userid2.add(askbuy.getUserid());
					qq.add(askbuy.getQq());
					email.add(askbuy.getEmail());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getmoreaskbuyinfo(int num) {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			// message.setValue(value)
			message.setType(message.MOREASKBUY);
			Hashtable table = new Hashtable();
			table.put("num", num);
			message.setValue(table);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<AskBuyS> vv = (Vector<AskBuyS>) message.getReturnValue()
						.get("askbuy");
				for (final AskBuyS askbuy : vv) {
					askbuyid.add(askbuy.getAskbuyid());
					agoodsname.add(askbuy.getAgoodsname());
					agoodsclass.add(askbuy.getAgoodsclass());
					askbuycontent.add(askbuy.getAskbuycontent());
					askbuytime.add(askbuy.getAskbuytime().substring(0, 19));
					userqiuname.add(askbuy.getUserqiuname());
					askbuyphone.add(askbuy.getAskbuyphone());
					userid2.add(askbuy.getUserid());
					qq.add(askbuy.getQq());
					email.add(askbuy.getEmail());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class forsale_BtnListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(forSale.this, forsaleSend.class);
			intent.putExtra("userid", userid);
			forSale.this.startActivityForResult(intent, 1);
			overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

		}

	}

	public void back_OnClick(View v) {
		this.finish();
		overridePendingTransition(R.anim.bottom_top,R.anim.bottom_top2);

	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(R.anim.bottom_top,R.anim.bottom_top2);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				int num=askbuyid.size()+1;
				getmoreaskbuyinfo(num);
				showqiugoulist();
				listAdapter.notifyDataSetChanged();
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 璁剧疆鏇存柊鏃堕棿
				// mPullToRefreshView.onHeaderRefreshComplete("鏈�繎鏇存柊:01-23 12:01");

				list.clear();
				// geneItems();
				
				getnewaskbuyinfo();
				showqiugoulist();
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 1000);
	}

	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 forSale.this, 0, new Intent(forSale.this,message.class),0);
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
