package com.erhuo.search;

import java.util.ArrayList;
import java.util.List;

import util.ImagedbUtil;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.forSale;
import com.erhuo.login.LoginActivity;

import com.erhuo.main.MainActivity;
import com.erhuo.main.R;
import com.erhuo.message.message;

public class Search extends Activity {

	ExpandableListView expandableList;
	AutoCompleteTextView autoCompleteTextView = null;
	static final String[] COUNTRLES = new String[] { "中国", "中国人", "中国人好",
			"中国人好啊", "四级", "四级作文", "四级听力", "四级写作", "四级真题" };

	//
	public int[] images = new int[] { R.drawable.png_01, R.drawable.png_02,
			R.drawable.png_03, R.drawable.png_04, R.drawable.png_05,
			R.drawable.png_06, R.drawable.png_07, R.drawable.png_08,
			R.drawable.png_09, R.drawable.png_10, R.drawable.png_11

	};
	public String[] str1 = { "文化/体育", "电子商品及其配件", "服饰", "虚拟商品", "房屋出租", "宠物",
			"车辆", "生活用品", "票务/优惠券/旅游", "免费赠送", "其它" };
	public String[][] str2 = { { "音乐/电影/书籍", "运动/户外活动", "文具办公", "艺术品/收藏品" },
			{ "电脑/配件", "手机/配件", "家用电器", "电子产品" }, { "男装", "女装", "鞋包服饰" },
			{ "手机号", "点卡/账号", "游戏装备", "QQ专区" }, { "房屋出租" },
			{ "宠物交易", "宠物用品", "宠物领养与赠送" }, { "自行车", "电动车", "摩托车", "配件" },

			{ "家具", "日用品", "家居饰品", "装修建材" }, { "折扣卡/优惠券", "购物卡", "车票/旅游" },
			{ "免费赠送" }, { "其他" } };
	private String userid;
	private int lastClick = -1;// 涓婁竴娆＄偣鍑荤殑group鐨刾osition
	private Button searchbtnButton;
	private String searchcontent="";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题栏
		setContentView(R.layout.searchable1);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 不自动显示软键盘
		notifym();
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto);
		initAutoComplete("history", autoCompleteTextView);
		searchbtnButton=(Button)findViewById(R.id.searchBtn);
//		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
//				R.layout.searchabl_text_tiaomu, COUNTRLES);
//		autoCompleteTextView.setAdapter(arrayAdapter);
		expandableList = (ExpandableListView) Search.this
				.findViewById(R.id.ExpandableListView01);
		Intent intent = super.getIntent();
		userid = intent.getStringExtra("userid");
		searchbtnButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				searchcontent=autoCompleteTextView.getText().toString();
				
				if(searchcontent.equals("")){
					Toast.makeText(getApplicationContext(), "请输入点东西在搜索吧！",Toast.LENGTH_LONG).show();
					return;
				}
				saveHistory("history", autoCompleteTextView);
				Intent intent = new Intent(Search.this, result.class);
				intent.putExtra("type","content" );
				intent.putExtra("types","全部" );

				intent.putExtra("serchcontent",searchcontent );
				intent.putExtra("userid", userid);
			
				startActivityForResult(intent, 1);
				Search.this.finish();
				
			}
		});
	
		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return str1[groupPosition];
			}

			// 鑾峰彇鐨勭兢浣撴暟閲忥紝寰楀埌str1閲屽厓绱犵殑涓暟
			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return str1.length;
			}

			// 鑾峰彇缁勫湪缁欏畾鐨勪綅缃紪鍙凤紝鍗硈tr1涓厓绱犵殑ID
			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub

				View view = LayoutInflater.from(Search.this).inflate(
						R.layout.catagory_show, null);
				ImageView logo = (ImageView) view
						.findViewById(R.id.catagoryimage);

				logo.setImageResource(images[groupPosition]);// 娣诲姞鍥剧墖

				TextView textView = (TextView) view.findViewById(R.id.catagory);// 璋冪敤瀹氫箟鐨刧etTextView()鏂规硶
				textView.setText(getGroup(groupPosition).toString());// 娣诲姞鏁版嵁

				return view;
			}

			
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub

				return str2[groupPosition][childPosition];

			}

			// 鑾峰彇鍦ㄧ粰瀹氱殑缁勭殑鍎跨鐨処D锛屽氨鏄痑rms涓厓绱犵殑ID
			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub

				return childPosition;

			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				// TextView textView = getTextView();//璋冪敤瀹氫箟鐨刧etTextView()鏂规硶
				// textView.setText(getChild(groupPosition,
				// childPosition).toString());//娣诲姞鏁版嵁
				// return textView;

				View view = LayoutInflater.from(Search.this).inflate(
						R.layout.main2, null);
				TextView textView = (TextView) view.findViewById(R.id.subc);// 璋冪敤瀹氫箟鐨刧etTextView()鏂规硶
				textView.setText(getChild(groupPosition, childPosition)
						.toString());// 娣诲姞鏁版嵁
				
				return view;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub

				return str2[groupPosition].length;

			}

			// 琛ㄧず瀛╁瓙鏄惁鍜岀粍ID鏄法鍩虹鏁版嵁鐨勬洿鏀圭ǔ瀹� @Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}

			// 瀛╁瓙鍦ㄦ寚瀹氱殑浣嶇疆鏄彲閫夌殑锛� @Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}

		};

		expandableList.setAdapter(adapter);
		expandableList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent(Search.this, result.class);
				intent.putExtra("type", "class");
				intent.putExtra("types",  str2[groupPosition][childPosition]);

				intent.putExtra("catagory", str2[groupPosition][childPosition]);
				intent.putExtra("userid", userid);
				startActivityForResult(intent, 1);
				Search.this.finish();
				overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);

				return true;
			}
		});

		expandableList.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				if (lastClick == -1) {
					expandableList.expandGroup(groupPosition);
				}

				if (lastClick != -1 && lastClick != groupPosition) {
					expandableList.collapseGroup(lastClick);
					expandableList.expandGroup(groupPosition);
				} else if (lastClick == groupPosition) {
					if (expandableList.isGroupExpanded(groupPosition))
						expandableList.collapseGroup(groupPosition);
					else if (!expandableList.isGroupExpanded(groupPosition))
						expandableList.expandGroup(groupPosition);
				}

				lastClick = groupPosition;
				return true;
			}
		});

		autoCompleteTextView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				Toast.makeText(Search.this,""+autoCompleteTextView.length(),Toast.LENGTH_LONG).show();
				if(autoCompleteTextView.length()!=0){
					ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Search.this,
							R.layout.searchabl_text_tiaomu, COUNTRLES);
					autoCompleteTextView.setAdapter(arrayAdapter);
				}
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
		
			}
		});

		
		
		
		
	}
	
/*	@Override
	protected void onResume() {
		initAutoComplete("history", autoCompleteTextView);


		
		super.onResume();
	}*/

	
	
	
	
	
	
	
	private void saveHistory(String field,
			AutoCompleteTextView autoCompleteTextView) {
		String text = autoCompleteTextView.getText().toString();
		SharedPreferences sp = getSharedPreferences("network_url", 0);
		String longhistory = sp.getString(field, "nothing");
		if (!longhistory.contains(text + ",")) {
			StringBuilder sb = new StringBuilder(longhistory);
			sb.insert(0, text + ",");
			sp.edit().putString("history", sb.toString()).commit();
		}
	}

	/**
	 * 鍒濆鍖朅utoCompleteTextView锛屾渶澶氭樉绀�椤规彁绀猴紝浣�AutoCompleteTextView鍦ㄤ竴寮�鑾峰緱鐒︾偣鏃惰嚜鍔ㄦ彁绀�	 * 
	 * @param field
	 *            淇濆瓨鍦╯haredPreference涓殑瀛楁鍚�	 * @param autoCompleteTextView
	 *            瑕佹搷浣滅殑AutoCompleteTextView
	 */
	private void initAutoComplete(String field,
			AutoCompleteTextView autoCompleteTextView) {
		SharedPreferences sp = getSharedPreferences("network_url", 0);
		String longhistory = sp.getString("history", "nothing");
		String[] histories = longhistory.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.searchabl_text_tiaomu, histories);
		// 鍙繚鐣欐渶杩戠殑50鏉＄殑璁板綍
		if (histories.length > 5) {
			String[] newHistories = new String[5];
			System.arraycopy(histories, 0, newHistories, 0, 5);
			adapter = new ArrayAdapter<String>(this,
					R.layout.searchabl_text_tiaomu, newHistories);
		}
		autoCompleteTextView.setAdapter(adapter);	
		autoCompleteTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AutoCompleteTextView view = (AutoCompleteTextView) v;
			           view.showDropDown();
				}
		});
		

	}  

	public void back_OnClick(View v) {

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
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 Search.this, 0, new Intent(Search.this,message.class),0);
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

