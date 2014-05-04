package com.erhuo.main;

import java.io.File;
import java.io.FileOutputStream;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenu.SateliteClickedListener;
import android.view.ext.SatelliteMenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.erhuo.app.util.Tools;
import com.erhuo.collection.collectionList;
import com.erhuo.forsale.Myadapter;
import com.erhuo.forsale.forSale;
import com.erhuo.forsale.forsaleSend;
import com.erhuo.main.R;
import com.erhuo.main.MyLinearLayout.OnScrollListener;
import com.erhuo.manage.ManageInfo;
import com.erhuo.message.message;
import com.erhuo.salegoods.GoodsActivity;
import com.erhuo.salegoods.goods_information;
import com.erhuo.search.Search;
import com.erhuo.server.MyMessage;
import com.erhuo.server.SellS;
import com.erhuo.userInfo.setting;
import com.miloisbadboy.view.PullToRefreshView;
import com.miloisbadboy.view.PullToRefreshView.OnFooterRefreshListener;
import com.miloisbadboy.view.PullToRefreshView.OnHeaderRefreshListener;

public class MainActivity extends ListActivity implements OnTouchListener,
		GestureDetector.OnGestureListener, OnItemClickListener,
		OnHeaderRefreshListener, OnFooterRefreshListener, Runnable {
	SatelliteMenu menu, menu2;
	List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
	PullToRefreshView mPullToRefreshView, mPullToRefreshView2;
	RelativeLayout.LayoutParams ly, ly2;
	String isRead;
	RelativeLayout mainlLayout, menuLayout, menLayout;
	int numm1, numm2, numm3 = 0;
	View convertView1, convertView2, converView3;
	private boolean hasMeasured = false;// 是否Measured.
	private RelativeLayout layout_left;// 左边布局
	private LinearLayout layout_right;// 右边布局
	private ImageView iv_set;// 图片
	private ListView lv_set;// 设置菜单
	/** 每次自动展开/收缩的范围 */
	private int MAX_WIDTH = 0;
	/** 每次自动展开/收缩的速度 */
	private final static int SPEED = 30;

	RelativeLayout menu1;

	private final static int sleep_time = 5;

	private GestureDetector mGestureDetector;// 手势
	private boolean isScrolling = false;
	private float mScrollX; // 滑块滑动距离
	private int window_width;// 屏幕的宽度
	private int newsnumid;
	private String TAG = "jj";

	private View view = null;// 点击的view
	private String bujutype = "平铺", showtype = "全部";
	private MyLinearLayout mylaout;
	private ImageButton catagoryButton;
	ListView lv;
	// private PopMenu2 popMenu;
	private PopMenu1 popMenu1;
	private Context context;
	private TextView textV;
	private int num = 1;
	View view2;
	View view1;
	public String userid;
	int viewType = 1;
	List<Map<String, Object>> list = null;
	private Handler mHandler;
	Myadapter listAdapter = null;
	// 显示结果的gridview
	private GridView myGridView = null; // GridView组件

	ArrayList Listphoto1 = new ArrayList();
	ArrayList Listgoodsid = new ArrayList();
	ArrayList Listselltime = new ArrayList();
	ArrayList Listthingname = new ArrayList();
	ArrayList Listthingprice = new ArrayList();
	ArrayList Listthingaddress= new ArrayList();
	ArrayList Listthinggrade = new ArrayList();

	private String title[] = { "全部","人气推荐", "文化/体育", "电子商品及其配件", "服饰", "虚拟商品", "房屋出租",
			"宠物", "车辆", "生活用品", "票务/优惠券/旅游", "免费赠送", "其它" };

	// 显示结果的list

	// 网格布局
	void show_grid() {
		
			//
			// layout_left=(RelativeLayout)findViewById(R.id.layout_left);
			convertView1 = LayoutInflater.from(context).inflate(
					R.layout.grid_layout_, null);

			//
			mainlLayout.removeView(mPullToRefreshView);

			mainlLayout.addView(convertView1);
			myGridView = (GridView) convertView1.findViewById(R.id.GridView);
			mPullToRefreshView2 = (PullToRefreshView) convertView1
					.findViewById(R.id.main_pull_refresh_view2);
			convertView1.setLayoutParams(ly);
			mainlLayout.removeView(menuLayout);
			mainlLayout.addView(menuLayout);

			mPullToRefreshView2.setOnHeaderRefreshListener(this);
			mPullToRefreshView2.setOnFooterRefreshListener(this);
			viewType = 1;
			System.out.println("110");
			list = new ArrayList<Map<String, Object>>();
			for (int x = 0; x < Listgoodsid.size(); x++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pic", "/sdcard/destDir" + "/" + Listphoto1.get(x));
				map.put("thingname", MainActivity.this.Listthingname.get(x));
				map.put("thingprice", MainActivity.this.Listthingprice.get(x));
				map.put("address", MainActivity.this.Listthingaddress.get(x));
				map.put("grade", MainActivity.this.Listthinggrade.get(x));
				list.add(map);
			}

			myGridView.setAdapter(new Myadapter(this, this.list,
					R.layout.grid_layout, new String[] { "pic", "thingname",
							"thingprice" }, new int[] { R.id.img,
							R.id.host_thing_name, R.id.host_thing_price }, 3)); // 设置图片
			myGridView.setOnItemClickListener(new OnItemClickListenerImpg());
	
	

	}

	// 列表
	void show_list() {
		
			mainlLayout.removeView(menuLayout);
			mainlLayout.addView(menuLayout);

			mPullToRefreshView.setOnHeaderRefreshListener(this);
			mPullToRefreshView.setOnFooterRefreshListener(this);
			viewType = 2;
			lv = getListView();
			list = new ArrayList<Map<String, Object>>();

			for (int x = 0; x < Listgoodsid.size(); x++) {
				System.out.println(Listgoodsid.size() + "");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pic", "/sdcard/destDir" + "/" + Listphoto1.get(x));
				map.put("name", MainActivity.this.Listthingname.get(x));
				map.put("price", MainActivity.this.Listthingprice.get(x) + "元");
				map.put("time", ((String)(MainActivity.this.Listselltime.get(x))).substring(0, 19));
				map.put("address", MainActivity.this.Listthingaddress.get(x));
//				map.put("grade", PathMenuActivity.this.Listthinggrade.get(x));
				list.add(map);
			}

			listAdapter = new Myadapter(this, this.list, R.layout.selling,
					new String[] { "pic", "name", "price", "time","address"}, new int[] {
							R.id.goodsimageshow, R.id.goodsnameshow,
							R.id.priceshow, R.id.timeshow ,R.id.addressshow}, 4);
			setListAdapter(listAdapter);

			lv.setOnItemClickListener(new OnItemClickListenerImpg());
		}
		
	

	OnItemClickListener popmenuItemClickListener1 = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			if (position == 0) {
				if (numm1 == 0 && numm3 != 0) {
					numm2 = 0;
					// 网格布局显示结果
					textV.setText("平铺");
					bujutype = "平铺";

					mainlLayout.removeView(mPullToRefreshView);

					// mainlLayout.removeView();
					// myGridView=(GridView)convertView1.findViewById(R.id.GridView);
					// mPullToRefreshView2=(PullToRefreshView)
					// convertView1.findViewById(R.id.main_pull_refresh_view2);
					mainlLayout.addView(convertView1);

					show_grid();

					Toast.makeText(context, "现在你选择了平铺", Toast.LENGTH_SHORT)
							.show();
					numm1++;
					numm3++;

				} else {
					textV.setText("平铺");
					bujutype = "平铺";
					numm3++;
				}

			} else {// 列表布局显示结果
				if (numm2 == 0) {
					numm3=1;
					numm1 = 0;
					textV.setText("列表");
					bujutype = "列表";
					// view1.setVisibility(view1.INVISIBLE);
					// mylistView.setVisibility(mylistView.VISIBLE);
					// Toast.makeText(context, "现在你选择了列表",
					// Toast.LENGTH_SHORT).show();
					mainlLayout.removeView(convertView1);
					// mPullToRefreshView.setVisibility(mPullToRefreshView.VISIBLE);
					mainlLayout.addView(mPullToRefreshView);

					show_list();
					numm2++;
				} else {
					textV.setText("列表");
					bujutype = "列表";
				}

			}
			popMenu1.dismiss();
		}
	};

	private class OnItemClickListenerImpg implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) { // 选项单击事件

			Intent intent = new Intent(MainActivity.this,
					GoodsActivity.class);
			intent.putExtra("userid", userid);
			intent.putExtra("goodsid", (Integer) Listgoodsid.get(position));
			intent.putExtra("type", "collect");

			MainActivity.this.startActivityForResult(intent, 1);
			overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);

		}
	}

/*	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) { // 选项单击事件

			Intent intent = new Intent(PathMenuActivity.this,
					GoodsActivity.class);
			intent.putExtra("userid", userid);
			intent.putExtra("goodsid", (Integer) Listgoodsid.get(position - 1));
			PathMenuActivity.this.startActivityForResult(intent, 1);
		}
	}
*/
	void initView_two() {
		// 左右滑动
		layout_left = (RelativeLayout) findViewById(R.id.layout_left);
		layout_right = (LinearLayout) findViewById(R.id.layout_right);
		catagoryButton = (ImageButton) findViewById(R.id.button2);
		lv_set = (ListView) findViewById(R.id.lv_set);
		mylaout = (MyLinearLayout) findViewById(R.id.mylaout);
		lv_set.setAdapter(new ArrayAdapter<String>(this, R.layout.fenlei_item,
				R.id.tv_item, title));

		// 左右滑动
		mylaout.setOnScrollListener(new OnScrollListener() {
			public void doScroll(float distanceX) {
				doScrolling(distanceX);
			}

			public void doLoosen() {
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
						.getLayoutParams();
				Log.e("jj", "layoutParams.leftMargin="
						+ layoutParams.leftMargin);
				// 缩回去
				if (layoutParams.leftMargin < -window_width / 2) {
					new AsynMove().execute(-SPEED);
				} else {
					new AsynMove().execute(SPEED);
				}
			}
		});
		// 点击监听
		lv_set.setOnItemClickListener(this);
		// 对于进入类别按钮
		catagoryButton.setOnTouchListener(this);
		layout_right.setOnTouchListener(this);
		layout_left.setOnTouchListener(this);
		// catagoryButton.setOnTouchListener(this);
		mGestureDetector = new GestureDetector(this);
		// 禁用长按监听
		mGestureDetector.setIsLongpressEnabled(false);
		getMAX_WIDTH();
		// 左右滑动
	}

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent intent = super.getIntent();
		userid = Tools.userid;

		MainActivity t = new MainActivity();
		Thread thread = new Thread(t);
		thread.start();
		// 消息提示的初始化
		notifym();

		int num = messagenum();
		Tools.num = num;
		Tools.num2 = num;
		if (num > 0) {
			Tools.notification.tickerText = "亲，你有" + num + "条新信息！请点击查看";
			Tools.notification.setLatestEventInfo(Tools.context, "消息", "亲，你有"
					+ num + "条信息！请点击查看", Tools.pendingIntent);
			Tools.manager.notify(0, Tools.notification);// 发起通知
		}
		// 创建文件夹
		File destDir = new File("mnt/sdcard/destDir");
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		// 获取信息
		getgoodsinfo();
		// 图片
		getphoto(Listphoto1);

		// 显示平铺和列表效果的按钮
		textV = (TextView) findViewById(R.id.pinputext);
		context = MainActivity.this;
		mainlLayout = (RelativeLayout) findViewById(R.id.layout_left);
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		// menLayout=(RelativeLayout)findViewById(R.id.)/
		ly = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		ly.addRule(RelativeLayout.BELOW, R.id.shake_title_bar);

		ly2 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		ly2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, R.id.layout_left);
		menuLayout = (RelativeLayout) findViewById(R.id.menum);
		menuLayout.setLayoutParams(ly2);

		show_grid();
		popMenu1 = new PopMenu1(context, textV);
		popMenu1.addItems(new String[] { "平铺", "列表" });
		// 菜单项点击监听器
		popMenu1.setOnItemClickListener(popmenuItemClickListener1);

		LinearLayout pinpubtn = (LinearLayout) findViewById(R.id.pinpubtn);
		pinpubtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				popMenu1.showAsDropDown(v);

			}
		});

		// myGridView.setOnItemClickListener(new OnItemClickListenerImpl());

		initView_two();
		// menLayout = (RelativeLayout) findViewById(R.id.menum);
		// 扇形主菜单
		menu = (SatelliteMenu) findViewById(R.id.menu);

		items.add(new SatelliteMenuItem(1, R.drawable.ic_1));
		items.add(new SatelliteMenuItem(3, R.drawable.ic_3));
		items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
		items.add(new SatelliteMenuItem(5, 	 R.drawable.ic_5));
		items.add(new SatelliteMenuItem(6, R.drawable.ic_6));
		items.add(new SatelliteMenuItem(2, R.drawable.ic_2));
		items.add(new SatelliteMenuItem(7, R.drawable.sat_item));
		menu.addItems(items);

		menu.setOnItemClickedListener(new SateliteClickedListener() {
			public void eventOccured(int id) {
				if (id == 1) {
					Intent intent = new Intent(MainActivity.this,
							Search.class);
					intent.putExtra("userid", userid);

					MainActivity.this.startActivityForResult(intent, 1);

					overridePendingTransition(R.anim.new_dync_out_to_left,
							R.anim.new_dync_in_from_right);

				}
				if (id == 2) {
					Intent intent = new Intent(MainActivity.this,
							setting.class);
					intent.putExtra("userid", userid);
					MainActivity.this.startActivityForResult(intent, 1);
					overridePendingTransition(R.anim.top_bottom,
							R.anim.top_bottom2);

				}
				if (id == 3) {
					Intent intent = new Intent(MainActivity.this,
							collectionList.class);
					intent.putExtra("userid", userid);
					MainActivity.this.startActivityForResult(intent, 1);
					overridePendingTransition(R.anim.new_dync_out_to_left,
							R.anim.new_dync_in_from_right);

				}
				if (id == 4) {
					Intent intent = new Intent(MainActivity.this,
							goods_information.class);
					intent.putExtra("userid", userid);
					
					MainActivity.this.startActivityForResult(intent, 1);
					overridePendingTransition(R.anim.new_dync_out_to_left,
							R.anim.new_dync_in_from_right);

				}
				if (id == 5) {
					Intent intent = new Intent(MainActivity.this,
							com.erhuo.message.message.class);
					intent.putExtra("userid", userid);
					MainActivity.this.startActivityForResult(intent, 1);
					overridePendingTransition(R.anim.new_dync_out_to_left,
							R.anim.new_dync_in_from_right);

				}
				if (id == 6) {
					Intent intent = new Intent(MainActivity.this,
							forSale.class);
					intent.putExtra("userid", userid);
					MainActivity.this.startActivityForResult(intent, 1);
					overridePendingTransition(R.anim.top_bottom,
							R.anim.top_bottom2);

				}
				if (id == 7) {
					Intent intent = new Intent(MainActivity.this,
							ManageInfo.class);
					intent.putExtra("userid", userid);

					MainActivity.this.startActivityForResult(intent, 1);
					// overridePendingTransition(R.anim.head_in,
					// R.anim.head_out);
					overridePendingTransition(R.anim.top_bottom,
							R.anim.top_bottom2);

				}
			}
		});
	}

	// 从服务器下载信息
	public void getgoodsinfo() {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			// message.setValue(value)
			message.setType(message.MAINSHOW);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<SellS> vv = (Vector<SellS>) message.getReturnValue()
						.get("sell");
				for (final SellS sell : vv) {
					Listphoto1.add(sell.getGoodsphoto1());
					Listgoodsid.add(sell.getGoodsid());
					Listselltime.add(sell.getSelltime());
					Listthingname.add(sell.getGoodsname());
					Listthingprice.add(sell.getGoodsprice());
					Listthingaddress.add(sell.getAddress());
					Listthinggrade.add(sell.getGrade());
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getnewgoodsinfo() {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			Hashtable table = new Hashtable();
			table.put("goodsid", Listgoodsid.get(0));
			message.setValue(table);
			message.setType(message.REFRESHMAINSHOW);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<SellS> vv = (Vector<SellS>) message.getReturnValue()
						.get("sell");
				for (final SellS sell : vv) {
					Listphoto1.add(0, sell.getGoodsphoto1());
					Listgoodsid.add(0, sell.getGoodsid());
					Listselltime.add(0, sell.getSelltime());
					Listthingname.add(0, sell.getGoodsname());
					Listthingprice.add(0, sell.getGoodsprice());
					Listthingaddress.add(sell.getAddress());
					Listthinggrade.add(sell.getGrade());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getmoregoodsinfo(int num) {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			// message.setValue(value)
			message.setType(message.LoadMoreMAINSHOW);
			Hashtable table = new Hashtable();
			table.put("num", num);
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
					Listphoto1.add(sell.getGoodsphoto1());
					Listgoodsid.add(sell.getGoodsid());
					Listselltime.add(sell.getSelltime());
					Listthingname.add(sell.getGoodsname());
					Listthingprice.add(sell.getGoodsprice());
					Listthingaddress.add(sell.getAddress());
					Listthinggrade.add(sell.getGrade());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//按人气显示
	public void getbypopularity() {
		Listphoto1.clear();
		Listgoodsid.clear();
		Listselltime.clear();
		Listthingname.clear();
		Listthingprice.clear();
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.MAIN_CATAGORYPOPULARITY);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<SellS> vv = (Vector<SellS>) message.getReturnValue()
						.get("sell");
				for (final SellS sell : vv) {
					Listphoto1.add(sell.getGoodsphoto1());
					Listgoodsid.add(sell.getGoodsid());
					Listselltime.add(sell.getSelltime());
					Listthingname.add(sell.getGoodsname());
					Listthingprice.add(sell.getGoodsprice());
					Listthingaddress.add(sell.getAddress());
					Listthinggrade.add(sell.getGrade());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//按大类别显示
	public void getgoodsinfobyclass(String aclass) {
		Listphoto1.clear();
		Listgoodsid.clear();
		Listselltime.clear();
		Listthingname.clear();
		Listthingprice.clear();
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.MAIN_CATAGORYSEARCH);
			Hashtable table = new Hashtable();
			table.put("catagory", aclass);
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
					Listphoto1.add(sell.getGoodsphoto1());
					Listgoodsid.add(sell.getGoodsid());
					Listselltime.add(sell.getSelltime());
					Listthingname.add(sell.getGoodsname());
					Listthingprice.add(sell.getGoodsprice());
					Listthingaddress.add(sell.getAddress());
					Listthinggrade.add(sell.getGrade());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 根据类别加载更多信息
	public void getnewgoodsinfobyclass(String aclass) {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			Hashtable table = new Hashtable();
			table.put("goodsid", Listgoodsid.get(0));
			table.put("catagory", aclass);
			message.setValue(table);
			message.setType(message.MAIN_CATAGORYSEARCHREFRESH);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<SellS> vv = (Vector<SellS>) message.getReturnValue()
						.get("sell");
				for (final SellS sell : vv) {
					Listphoto1.add(0, sell.getGoodsphoto1());
					Listgoodsid.add(0, sell.getGoodsid());
					Listselltime.add(0, sell.getSelltime());
					Listthingname.add(0, sell.getGoodsname());
					Listthingprice.add(0, sell.getGoodsprice());
					Listthingaddress.add(sell.getAddress());
					Listthinggrade.add(sell.getGrade());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 加载更多类别信息
	public void getmoregoodsinfobyclass(String aclass) {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			// message.setValue(value)
			message.setType(message.MOREMAIN_CATAGORYSEARCH);
			Hashtable table = new Hashtable();
			table.put("num", Listgoodsid.size() + 1);
			table.put("catagory", aclass);
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
					Listphoto1.add(sell.getGoodsphoto1());
					Listgoodsid.add(sell.getGoodsid());
					Listselltime.add(sell.getSelltime());
					Listthingname.add(sell.getGoodsname());
					Listthingaddress.add(sell.getAddress());
					Listthinggrade.add(sell.getGrade());
					Listthingprice.add(sell.getGoodsprice());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//人气更多
	public void getmorepopularity() {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			// message.setValue(value)
			message.setType(message.POPULARITYMORE);
			Hashtable table = new Hashtable();
			table.put("num", Listgoodsid.size() + 1);
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
					Listphoto1.add(sell.getGoodsphoto1());
					Listgoodsid.add(sell.getGoodsid());
					Listselltime.add(sell.getSelltime());
					Listthingname.add(sell.getGoodsname());
					Listthingaddress.add(sell.getAddress());
					Listthinggrade.add(sell.getGrade());
					Listthingprice.add(sell.getGoodsprice());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 从服务器下载图片
	public void getphoto(ArrayList<Object> photo) {

		for (int i = 0; i < photo.size(); i++) {
			try {
				File f = new File("/sdcard/destDir/", (String) photo.get(i));
				if (!f.exists()) {
					Socket socket = new Socket(Tools.IP, Tools.PORT_3);
					InputStream in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					out.write(("download,image," + (String) photo.get(i))
							.getBytes());
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
	}

	// 有没有求购的信息
	public void ishavenews() {
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
			message.setType(message.LOADISHAVEQIUGOU);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	/***
	 * 初始化view
	 */
	/***
	 * listview 正在滑动时执行.
	 */
	void doScrolling(float distanceX) {
		isScrolling = true;
		mScrollX += distanceX;// distanceX:向左为正，右为负

		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
				.getLayoutParams();
		layoutParams.leftMargin -= mScrollX;
		layoutParams_1.leftMargin = window_width + layoutParams.leftMargin;
		if (layoutParams.leftMargin >= 0) {
			isScrolling = false;// 拖过头了不需要再执行AsynMove了
			layoutParams.leftMargin = 0;
			layoutParams_1.leftMargin = window_width;

		} else if (layoutParams.leftMargin <= -MAX_WIDTH) {
			// 拖过头了不需要再执行AsynMove了
			isScrolling = false;
			layoutParams.leftMargin = -MAX_WIDTH;
			layoutParams_1.leftMargin = window_width - MAX_WIDTH;
		}
		Log.v(TAG, "layoutParams.leftMargin=" + layoutParams.leftMargin
				+ ",layoutParams_1.leftMargin =" + layoutParams_1.leftMargin);

		layout_left.setLayoutParams(layoutParams);
		layout_right.setLayoutParams(layoutParams_1);
	}

	/***
	 * 获取移动距离 移动的距离其实就是layout_left的宽度
	 */
	void getMAX_WIDTH() {
		ViewTreeObserver viewTreeObserver = layout_left.getViewTreeObserver();
		// 获取控件宽度
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (!hasMeasured) {
					window_width = getWindowManager().getDefaultDisplay()
							.getWidth();
					MAX_WIDTH = layout_right.getWidth();
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
							.getLayoutParams();
					RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
							.getLayoutParams();
					ViewGroup.LayoutParams layoutParams_2 = mylaout
							.getLayoutParams();
					// 注意： 设置layout_left的宽度。防止被在移动的时候控件被挤压
					layoutParams.width = window_width;
					layout_left.setLayoutParams(layoutParams);

					// 设置layout_right的初始位置.
					layoutParams_1.leftMargin = window_width;
					layout_right.setLayoutParams(layoutParams_1);
					// 注意：设置lv_set的宽度防止被在移动的时候控件被挤压
					layoutParams_2.width = MAX_WIDTH;
					mylaout.setLayoutParams(layoutParams_2);

					Log.v(TAG, "MAX_WIDTH=" + MAX_WIDTH + "width="
							+ window_width);
					hasMeasured = true;
				}
				return true;
			}
		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*
		 * if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		 * { RelativeLayout.LayoutParams layoutParams =
		 * (RelativeLayout.LayoutParams) layout_left .getLayoutParams(); if
		 * (layoutParams.leftMargin < 0) { new AsynMove().execute(SPEED); return
		 * false; } }else
		 */if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
		}
		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {

		view = v;// 记录点击的控件

		// 松开的时候要判断，如果不到半屏幕位子则缩回去，
		if (MotionEvent.ACTION_UP == event.getAction() && isScrolling == true) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			// 缩回去
			if (layoutParams.leftMargin < -window_width / 3 * 2) {
				new AsynMove().execute(-SPEED);
			} else {
				new AsynMove().execute(SPEED);
			}
		}

		return mGestureDetector.onTouchEvent(event);
	}

	public boolean onDown(MotionEvent e) {

		int position = lv_set.pointToPosition((int) e.getX(), (int) e.getY());
		if (position != ListView.INVALID_POSITION) {
			View child = lv_set.getChildAt(position
					- lv_set.getFirstVisiblePosition());
			if (child != null)
				child.setPressed(true);
		}

		mScrollX = 0;
		isScrolling = false;
		// 将之改为true，才会传递给onSingleTapUp,不然事件不会向下传递.
		return true;
	}

	public void onShowPress(MotionEvent e) {

	}

	/***
	 * 点击松开执行
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// 点击的不是layout_left
		if (view != null && view == catagoryButton) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			// 左移动
			if (layoutParams.leftMargin >= 0) {
				new AsynMove().execute(-SPEED);
				lv_set.setSelection(0);// 设置为首位.
			} else {
				// 右移动
				new AsynMove().execute(SPEED);
			}
		} else if (view != null && view == layout_left) {
			RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			if (layoutParams.leftMargin < 0) {
				// 说明layout_left处于移动最左端状态，这个时候如果点击layout_left应该直接所以原有状态.(更人性化)
				// 右移动
				new AsynMove().execute(SPEED);
			}
		}

		return true;
	}

	/***
	 * 滑动监听 就是一个点移动到另外一个点. distanceX=后面点x-前面点x，如果大于0，说明后面点在前面点的右边及向右滑动
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// 执行滑动.
		doScrolling(distanceX);
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	class AsynMove extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int times = 0;
			if (MAX_WIDTH % Math.abs(params[0]) == 0)// 整除
				times = MAX_WIDTH / Math.abs(params[0]);
			else
				times = MAX_WIDTH / Math.abs(params[0]) + 1;// 有余数

			for (int i = 0; i < times; i++) {
				publishProgress(params[0]);
				try {
					Thread.sleep(sleep_time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		/**
		 * update UI
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
					.getLayoutParams();
			RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) layout_right
					.getLayoutParams();
			// 右移动
			if (values[0] > 0) {
				layoutParams.leftMargin = Math.min(layoutParams.leftMargin
						+ values[0], 0);
				layoutParams_1.leftMargin = Math.min(layoutParams_1.leftMargin
						+ values[0], window_width);
				Log.v(TAG, "layout_left右" + layoutParams.leftMargin
						+ ",layout_right右" + layoutParams_1.leftMargin);
			} else {
				// 左移动
				layoutParams.leftMargin = Math.max(layoutParams.leftMargin
						+ values[0], -MAX_WIDTH);
				layoutParams_1.leftMargin = Math.max(layoutParams_1.leftMargin
						+ values[0], window_width - MAX_WIDTH);
				Log.v(TAG, "layout_left左" + layoutParams.leftMargin
						+ ",layout_right左" + layoutParams_1.leftMargin);
			}
			layout_right.setLayoutParams(layoutParams_1);
			layout_left.setLayoutParams(layoutParams);

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) layout_left
				.getLayoutParams();
		// 只要没有滑动则都属于点击
		if (layoutParams.leftMargin == -MAX_WIDTH) {
			Toast.makeText(MainActivity.this, title[position], 1).show();
			new AsynMove().execute(SPEED);

			showtype = title[position];
			if (showtype.equalsIgnoreCase(title[0])) {
				Listgoodsid.clear();
				Listphoto1.clear();
				Listselltime.clear();
				Listthingname.clear();
				Listthingprice.clear();
				getgoodsinfo();
			} else if(showtype.equalsIgnoreCase(title[1])){
				getbypopularity();
			}
			else {
				getgoodsinfobyclass(title[position]);
			}
			getphoto(Listphoto1);
			if (bujutype.equalsIgnoreCase("平铺")) {
				show_grid();
			} else {
				show_list();
			}

		}
	}

	/**
	 * 菜单、返回键响应
	 */
	//
	// public boolean onKeyDown_back(int keyCode, KeyEvent event) {
	// // TODO Auto-generated method stub
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// exitBy2Click(); // 调用双击退出函数
	// } else if (keyCode == KeyEvent.KEYCODE_MENU) {
	//
	// }
	// return false;
	// }

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		// TODO Auto-generated method stub
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		if (viewType == 1) {
			mPullToRefreshView2.postDelayed(new Runnable() {

				@Override
				public void run() {

					if (showtype.equalsIgnoreCase(title[0])) {
						num = Listgoodsid.size() + 1;
						// 获取更多消息
						getmoregoodsinfo(num);
					}else if(showtype.equalsIgnoreCase(title[1])){
						getmorepopularity();
					} else {
						getmoregoodsinfobyclass(showtype);
					}
					// 图片
					getphoto(Listphoto1);
					show_grid();
					// listAdapter.notifyDataSetChanged();

					mPullToRefreshView2.onFooterRefreshComplete();
				}
			}, 1000);
		} else {
			mPullToRefreshView.postDelayed(new Runnable() {

				@Override
				public void run() {

					if (showtype.equalsIgnoreCase(title[0])) {
						num = Listgoodsid.size() + 1;
						// 获取更多消息
						getmoregoodsinfo(num);
					} else if(showtype.equalsIgnoreCase(title[1])){
						getmorepopularity();
					}else {
						getmoregoodsinfobyclass(showtype);
					}
					// 图片
					getphoto(Listphoto1);
					show_list();
					// listAdapter.notifyDataSetChanged();
					mPullToRefreshView.onFooterRefreshComplete();
				}
			}, 1000);

		}

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		if (viewType == 1) {
			mPullToRefreshView2.postDelayed(new Runnable() {

				@Override
				public void run() {
					// Do work to refresh the list here.
					try {
						if (showtype.equalsIgnoreCase("全部")) {
							// 获取信息
							getnewgoodsinfo();
						} 
						else if (showtype.equalsIgnoreCase("人气推荐")) {
							getbypopularity();
						}else {
							getnewgoodsinfobyclass(showtype);
						}
						// 图片
						getphoto(Listphoto1);
						show_grid();
					} catch (Exception e) {
					}

					mPullToRefreshView2.onHeaderRefreshComplete();
				}
			}, 1000);
		} else {
			mPullToRefreshView.postDelayed(new Runnable() {

				@Override
				public void run() {

					if (showtype.equalsIgnoreCase(title[0])) {
						// 获取信息
						getnewgoodsinfo();
					}else if (showtype.equalsIgnoreCase("人气推荐")) {
						getbypopularity();
					} else {
						getnewgoodsinfobyclass(showtype);
					}
					// 图片
					getphoto(Listphoto1);
					// list.clear();
					// geneItems();
					show_list();
					listAdapter.notifyDataSetChanged();

					mPullToRefreshView.onHeaderRefreshComplete();
				}
			}, 1000);

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			if (data.getStringExtra("isdelete").equals("tru")) {
				Listgoodsid.clear();
				Listphoto1.clear();
				Listselltime.clear();
				Listthingname.clear();
				Listthingprice.clear();
				refreshgoodsinfo();
				getphoto(Listphoto1);
				if (bujutype.equalsIgnoreCase("平铺")) {
					show_grid();
				} else {
					show_list();
				}

			}else if(data.getStringExtra("ischange").equals("tru")){

				Listgoodsid.clear();
				Listphoto1.clear();
				Listselltime.clear();
				Listthingname.clear();
				Listthingprice.clear();
				refreshgoodsinfo();
				getphoto(Listphoto1);
				if (bujutype.equalsIgnoreCase("平铺")) {
					show_grid();
				} else {
					show_list();
				}

			
			}
		case RESULT_CANCELED:
			break;
		default:
			break;
		}

	}

	public void refreshgoodsinfo() {
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			// message.setValue(value)
			message.setType(message.MAINSHOW);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<SellS> vv = (Vector<SellS>) message.getReturnValue()
						.get("sell");
				for (final SellS sell : vv) {
					Listphoto1.add(sell.getGoodsphoto1());
					Listgoodsid.add(sell.getGoodsid());
					Listselltime.add(sell.getSelltime());
					Listthingname.add(sell.getGoodsname());
					Listthingprice.add(sell.getGoodsprice());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 获取提示消息的线程
	public void run() {
		try {
			System.out.println("第一次");
			Log.e("1", "第一次");
			// sys
			System.out.println("-------------------------------------------");

			Socket socket = new Socket(Tools.IP, Tools.PORT_2);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			Hashtable table = new Hashtable();
			System.out.println("userid前");
			table.put("userid", Tools.userid);
			System.out.println("userid后");
			message.setValue(table);
			oout.writeObject(message);
			oout.flush();
			System.out.println("第1.5次");
			Log.e("1", "第1.5次");
			while (true) {
				System.out.println("第二次");
				Log.e("1", "第二次");
				MyMessage m = (MyMessage) oin.readObject();
				if (m.getReturnValue().get("message").toString()
						.equalsIgnoreCase("ok")) {
					System.out.println("成功");
					Log.e("1", "成功");
					if (m.getReturnValue().get("type").toString()
						.equalsIgnoreCase("shoucang")) {
						Log.e("1", "soucang");
						String address=m.getReturnValue().get("address").toString();
						String usersex=m.getReturnValue().get("sex").toString();
						String username1=m.getReturnValue().get("username").toString();
						String goodsname1=m.getReturnValue().get("goodsname").toString();
						
						Tools.notification.tickerText = "亲！住在"+address+"的"+usersex+username1+"收藏了您的" +goodsname1;
						Tools.notification.setLatestEventInfo(Tools.context, "好信息",
								"亲！住在"+address+"的"+usersex+username1+"收藏了您的" +goodsname1,
								Tools.pendingIntent);
						Tools.manager.notify(0, Tools.notification);// 发起通知
						
					}
					else if (m.getReturnValue().get("type").toString()
						.equalsIgnoreCase("liuyan")) {
						String name=m.getReturnValue().get("username").toString();
						String goodsname=m.getReturnValue().get("goodsname").toString();
						
						Tools.notification.tickerText = "亲！"+name+"在你的" +goodsname+"留了言,赶紧去看看吧！";
						Tools.notification.setLatestEventInfo(Tools.context, "信息",
								"亲！"+name+"在你的" +goodsname+"留了言,赶紧去看看吧！",
								Tools.pendingIntent);
						Tools.manager.notify(0, Tools.notification);// 发起通知
					}
					else if (m.getReturnValue().get("type").toString()
							.equalsIgnoreCase("chat")) {
						if (Tools.isinchat==0) {
							String name=m.getReturnValue().get("username").toString();
							String content=m.getReturnValue().get("content").toString();
							
							Tools.notification.tickerText = ""+name+"说：" +content;
							Tools.notification.setLatestEventInfo(Tools.context, name,
									""+name+"说：" +content,
									Tools.pendingIntent);
							Tools.manager.notify(0, Tools.notification);// 发起通知
						}
						}
					else {
						String nameString = m.getReturnValue().get("name")
								.toString();
						
						Log.e("1", m.getReturnValue().get("name").toString());
						Tools.notification.tickerText = "亲，你求购" + nameString
								+ "的有货啦！请点击查看";
						Tools.notification.setLatestEventInfo(Tools.context, "好信息",
								"亲，你求购" + nameString + "的有货啦！请点击查看",
								Tools.pendingIntent);
						Tools.manager.notify(0, Tools.notification);// 发起通知
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void notifym() {
		// 获得通知管理器
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// 构建一个通知对象
		Notification notification = new Notification(R.drawable.icon, "新消息",
				System.currentTimeMillis());

		PendingIntent pendingIntent = PendingIntent.getActivity(
				MainActivity.this, 0, new Intent(MainActivity.this,
						message.class), 0);
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
	protected void onResume() {
		notifym();
		
		super.onResume();
	}
	

}