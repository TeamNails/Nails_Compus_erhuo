package com.erhuo.message;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.main.MainActivity;
import com.erhuo.main.R;
import com.erhuo.manage.ManageInfo;
import com.erhuo.salegoods.introduceInput;
import com.erhuo.server.ChatS;
import com.erhuo.server.MyMessage;
import com.erhuo.server.SellS;
public class ChatMain extends Activity implements OnClickListener,Runnable{
	ArrayList Listuserid1=new ArrayList();
	ArrayList Listuserid2=new ArrayList();
	ArrayList Listcontent=new ArrayList();
	ArrayList Listtime=new ArrayList();
	
	private Context mCon;
	private String username1,username2;
	private String userid2,userid1;
	private ViewPager viewPager;
	private ArrayList<GridView> grids;
	private int[] expressionImages;
	private String[] expressionImageNames;
	private int[] expressionImages1;
	private String[] expressionImageNames1;
	private int[] expressionImages2;
	private String[] expressionImageNames2;
	private Button mBtnSend;
	private Button mBtnBack;
	private ImageView rightBtn;
	private ImageButton voiceBtn;
	private ImageButton keyboardBtn;
	private ImageButton biaoqingBtn;
	private ImageButton biaoqingfocuseBtn;
	private LinearLayout ll_fasong;
	private LinearLayout ll_yuyin;
	private LinearLayout page_select;
	private ImageView page0;
	private ImageView page1;
	private ImageView page2;
	private EditText mEditTextContent;
	private ListView mListView;
	private GridView gView1;
	private GridView gView2;
	private GridView gView3;
	private TextView userName;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	private String[] msgArray = new String[] { "有大吗", "有！你呢？", "我也有", "那上吧",
			"打啊！你放大啊", "你tm咋不放大呢？留大抢人头那！Cao的。你个菜b", "2B不解释", "尼滚....", };

	private String[] dateArray = new String[] { "2012-12-09 18:00",
			"2012-12-09 18:10", "2012-12-09 18:11", "2012-12-09 18:20",
			"2012-12-09 18:30", "2012-12-09 18:35", "2012-12-09 18:40",
			"2012-12-09 18:50" };
	private final static int COUNT = 8;
	Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题栏
		setContentView(R.layout.chat_xiaohei);
	
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
		ChatMain t = new ChatMain();
		Thread thread = new Thread(t);
		thread.start();
		
		mCon = ChatMain.this;
		Tools.isinchat=1;
		Intent intent=super.getIntent();
		userid2 = intent.getStringExtra("userid2");
		Tools.userid2=userid2;
		userid1=Tools.userid;
		getusername();
		ll_fasong = (LinearLayout) findViewById(R.id.ll_fasong);
//		ll_yuyin = (LinearLayout) findViewById(R.id.ll_yuyin);
		page_select = (LinearLayout) findViewById(R.id.page_select);
		page0 = (ImageView) findViewById(R.id.page0_select);
		page1 = (ImageView) findViewById(R.id.page1_select);
		page2 = (ImageView) findViewById(R.id.page2_select);
		mListView = (ListView) findViewById(R.id.listview);
		// 引入表情
		expressionImages = Expressions.expressionImgs;
		expressionImageNames = Expressions.expressionImgNames;
		expressionImages1 = Expressions.expressionImgs1;
		expressionImageNames1 = Expressions.expressionImgNames1;
		expressionImages2 = Expressions.expressionImgs2;
		expressionImageNames2 = Expressions.expressionImgNames2;
		// 创建ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		// 发送
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		userName=(TextView)findViewById(R.id.user_name);
		
		// 返回
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);
		// 个人信息
		rightBtn = (ImageView) findViewById(R.id.right_btn);
//		rightBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ChatMain.this,userInfo.class);
//				intent.putExtra("userid",userid);
				ChatMain.this.startActivityForResult(intent, 1);
				
				
			}
		});
		
		
//		// 语音
//		voiceBtn = (ImageButton) findViewById(R.id.chatting_voice_btn);
//		voiceBtn.setOnClickListener(this);
//		// 键盘
//		keyboardBtn = (ImageButton) findViewById(R.id.chatting_keyboard_btn);
//		keyboardBtn.setOnClickListener(this);
		// 表情
		biaoqingBtn = (ImageButton) findViewById(R.id.chatting_biaoqing_btn);
		biaoqingBtn.setOnClickListener(this);
		biaoqingfocuseBtn = (ImageButton) findViewById(R.id.chatting_biaoqing_focuse_btn);
		biaoqingfocuseBtn.setOnClickListener(this);

		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		 //得到当前线程的Looper实例，由于当前线程是UI线程也可以通过Looper.getMainLooper()得到  	
        getchatinfo();//第一次进入下载信息
		//在这显示信息
		initData();
		initViewPager();
		
		
		  mHandler = new Handler()  
	    {  
	        public void handleMessage(Message msg)  
	        {  
	            //更新UI  
	            switch (msg.what)  
	            {  
	                case 1:  

	                    		int length=Tools.Listtime.size();
	                			getnewchatinfo((String)Tools.Listtime.get(length-1));
	                			
	                			//获得通知管理器
	                			 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

	                			//构建一个通知对象
	                			 Notification notification = new Notification(); 

	                				notification.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止
	                				notification.defaults |= Notification.DEFAULT_SOUND; //默认声音	
	                				manager.notify(0, notification);// 发起通知

	                			//在这重新显示列表
	                			initData();
	                    break;  
	            }  
	        };  
	    }; 
	    Tools.handler=mHandler;
	    	userName.setText(username2);
	}
  

	private void initViewPager() {
		LayoutInflater inflater = LayoutInflater.from(this);
		grids = new ArrayList<GridView>();
		
		gView1 = (GridView) inflater.inflate(R.layout.grid1, null);
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		// 生成24个表情
		for (int i = 0; i < 24; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("image", expressionImages[i]);
			listItems.add(listItem);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(mCon, listItems,
				R.layout.singleexpression, new String[] { "image" },
				new int[] { R.id.image });
		gView1.setAdapter(simpleAdapter);
		gView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeResource(getResources(),
						expressionImages[arg2 % expressionImages.length]);
				ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
				SpannableString spannableString = new SpannableString(
						expressionImageNames[arg2].substring(1,
								expressionImageNames[arg2].length() - 1));
				spannableString.setSpan(imageSpan, 0,
						expressionImageNames[arg2].length() - 2,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				// 编辑框设置数据
				mEditTextContent.append(spannableString);
				System.out.println("edit的内容 = " + spannableString);
			}
		});
		grids.add(gView1);

		gView2 = (GridView) inflater.inflate(R.layout.grid2, null);
		grids.add(gView2);

		gView3 = (GridView) inflater.inflate(R.layout.grid3, null);
		grids.add(gView3);
		System.out.println("GridView的长度 = " + grids.size());

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return grids.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(grids.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(grids.get(position));
				return grids.get(position);
			}

			@Override
			public void finishUpdate(View arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void restoreState(Parcelable arg0, ClassLoader arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Parcelable saveState() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void startUpdate(View arg0) {
				// TODO Auto-generated method stub
				
			}
		};

		viewPager.setAdapter(mPagerAdapter);
		// viewPager.setAdapter();

		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
	}

	private void initData() {
		Tools.Listtime=Listtime;
		for (int i = 0; i < Listuserid1.size(); i++) {
			
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(((String)Listtime.get(i)).substring(0,19));
			if (Listuserid1.get(i).equals(userid1)) {
				entity.setName(username1);
				entity.setMsgType(true);
			} else {
				entity.setName(username2);
				entity.setMsgType(false);
			}
			entity.setText(Listcontent.get(i).toString());
			mDataArrays.add(entity);
		}
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);
		return sbBuffer.toString();
	}

	@Override
	public void onClick(View v) {
		boolean isFoused = false;
		switch (v.getId()) {
		// 返回
		case R.id.btn_back:
			Tools.isinchat=0;
			Log.e("1", Tools.isinchat+"");
			finish();
			break;
		// 发送
		case R.id.btn_send:
			
			String content = mEditTextContent.getText().toString();
			System.out.println("edit.get的内容 = " + content);
			if (content.length() > 0) {
				/*ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(getDate());
				entity.setName("人马");
				entity.setMsgType(false);
				entity.setText(content);*/
				if (userid1.equals(userid2)) {
					Toast.makeText(getApplicationContext(), "亲，这是你自己的商品，不能跟自己对话哦！", Toast.LENGTH_LONG).show();
					return;
				}else {
					submitchatinfo(content);
					
					mEditTextContent.setText("");
				}
				
				
				
				
			/*	mDataArrays.add(entity);
				// 更新listview
				mEditTextContent.setText("");
				viewPager.setVisibility(ViewPager.GONE);
				page_select.setVisibility(page_select.GONE);
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mListView.getCount() - 1);*/
			} else {
				Toast.makeText(mCon, "不能发送空消息", Toast.LENGTH_LONG).show();
			}
			break;
		// 个人信息

/*		// 语音
		case R.id.chatting_voice_btn:
			voiceBtn.setVisibility(voiceBtn.GONE);
			keyboardBtn.setVisibility(keyboardBtn.VISIBLE);
			ll_fasong.setVisibility(ll_fasong.GONE);
			ll_yuyin.setVisibility(ll_yuyin.VISIBLE);
			break;
		// 键盘
		case R.id.chatting_keyboard_btn:
			voiceBtn.setVisibility(voiceBtn.VISIBLE);
			keyboardBtn.setVisibility(keyboardBtn.GONE);
			ll_fasong.setVisibility(ll_fasong.VISIBLE);
			ll_yuyin.setVisibility(ll_yuyin.GONE);
			break;*/
		// 表情
		case R.id.chatting_biaoqing_btn:
			biaoqingBtn.setVisibility(biaoqingBtn.GONE);
			biaoqingfocuseBtn.setVisibility(biaoqingfocuseBtn.VISIBLE);
			viewPager.setVisibility(viewPager.VISIBLE);
			page_select.setVisibility(page_select.VISIBLE);
			
			break;
		case R.id.chatting_biaoqing_focuse_btn:
			biaoqingBtn.setVisibility(biaoqingBtn.VISIBLE);
			biaoqingfocuseBtn.setVisibility(biaoqingfocuseBtn.GONE);
			viewPager.setVisibility(viewPager.GONE);
			page_select.setVisibility(page_select.GONE);
			break;
		}

	}

	// 点击小黑图像
	public void head_xiaohei(View v) { // 标题栏 返回按钮
	}

	// ** 指引页面改监听器 */
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			System.out.println("页面滚动" + arg0);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			System.out.println("换页了" + arg0);
		}

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));

				break;
			case 1:
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				page2.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
				// 生成24个表情
				for (int i = 0; i < 24; i++) {
					Map<String, Object> listItem = new HashMap<String, Object>();
					listItem.put("image", expressionImages1[i]);
					listItems.add(listItem);
				}

				SimpleAdapter simpleAdapter = new SimpleAdapter(mCon,
						listItems, R.layout.singleexpression,
						new String[] { "image" }, new int[] { R.id.image });
				gView2.setAdapter(simpleAdapter);
				gView2.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Bitmap bitmap = null;
						bitmap = BitmapFactory.decodeResource(getResources(),
								expressionImages1[arg2
										% expressionImages1.length]);
						ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
						SpannableString spannableString = new SpannableString(
								expressionImageNames1[arg2]
										.substring(1,
												expressionImageNames1[arg2]
														.length() - 1));
						spannableString.setSpan(imageSpan, 0,
								expressionImageNames1[arg2].length() - 2,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						// 编辑框设置数据
						mEditTextContent.append(spannableString);
						System.out.println("edit的内容 = " + spannableString);
					}
				});
				break;
			case 2:
				page2.setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
				List<Map<String, Object>> listItems1 = new ArrayList<Map<String, Object>>();
				// 生成24个表情
				for (int i = 0; i < 24; i++) {
					Map<String, Object> listItem = new HashMap<String, Object>();
					listItem.put("image", expressionImages2[i]);
					listItems1.add(listItem);
				}

				SimpleAdapter simpleAdapter1 = new SimpleAdapter(mCon,
						listItems1, R.layout.singleexpression,
						new String[] { "image" }, new int[] { R.id.image });
				gView3.setAdapter(simpleAdapter1);
				gView3.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Bitmap bitmap = null;
						bitmap = BitmapFactory.decodeResource(getResources(),
								expressionImages2[arg2
										% expressionImages2.length]);
						ImageSpan imageSpan = new ImageSpan(mCon, bitmap);
						SpannableString spannableString = new SpannableString(
								expressionImageNames2[arg2]
										.substring(1,
												expressionImageNames2[arg2]
														.length() - 1));
						spannableString.setSpan(imageSpan, 0,
								expressionImageNames2[arg2].length() - 2,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						// 编辑框设置数据
						mEditTextContent.append(spannableString);
						System.out.println("edit的内容 = " + spannableString);
					}
				});
				break;

			}
		}
	}
	
	//下载user名字
		public void getusername() {

			try {
				Socket socket = new Socket(Tools.IP, Tools.PORT_1);
				ObjectOutputStream oout = new ObjectOutputStream(
						socket.getOutputStream());
				ObjectInputStream oin = new ObjectInputStream(
						socket.getInputStream());
				MyMessage message = new MyMessage();
				Hashtable table=new Hashtable();
				table.put("userid1", userid1);
				table.put("userid2", userid2);
				message.setValue(table);
				message.setType(message.CHATNAME);
				oout.writeObject(message);
				oout.flush();
				message = (MyMessage) oin.readObject();
				if (message.getReturnValue().get("message").toString()
						.equalsIgnoreCase("ok")) {
					username1=message.getReturnValue().get("username1").toString();
					username2=message.getReturnValue().get("username2").toString();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	//下载聊天记录
	public void getchatinfo() {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			Hashtable table=new Hashtable();
			table.put("userid1",userid2 );
			table.put("userid2", userid1);
			message.setValue(table);
			message.setType(message.DOWNCHATMESSAGE);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<ChatS> vv = (Vector<ChatS>) message.getReturnValue()
						.get("chat");
				for (final ChatS chat : vv) {
					Listtime.add(chat.getTime());
					Listcontent.add(chat.getContent());
					Listuserid1.add(chat.getUserid1());
					Listuserid2.add(chat.getUserid2());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//下载新信息
	public void getnewchatinfo(String time) {

		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			Hashtable table=new Hashtable<String, Object>();
			userid1=Tools.userid;
			userid2=Tools.userid2;
			table.put("userid1", userid1);
			table.put("userid2", userid2);
			table.put("time", time);
			message.setValue(table);
			message.setType(message.REFRESHMESSAGE);
			oout.writeObject(message);
			oout.flush();
			message = (MyMessage) oin.readObject();
			if (message.getReturnValue().get("message").toString()
					.equalsIgnoreCase("ok")) {
				@SuppressWarnings("unchecked")
				Vector<ChatS> vv = (Vector<ChatS>) message.getReturnValue()
						.get("chat");
				Listcontent.clear();
				Listtime.clear();
				Listuserid1.clear();
				Listuserid2.clear();
				for (final ChatS chat : vv) {
					Listtime.add(chat.getTime());
					Listcontent.add(chat.getContent());
					Listuserid1.add(chat.getUserid1());
					Listuserid2.add(chat.getUserid2());
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	//提交信息
		public void submitchatinfo(String content) {

			try {
				Socket socket = new Socket(Tools.IP, Tools.PORT_1);
				ObjectOutputStream oout = new ObjectOutputStream(
						socket.getOutputStream());
				ObjectInputStream oin = new ObjectInputStream(
						socket.getInputStream());
				MyMessage message = new MyMessage();
				Hashtable table=new Hashtable();
				table.put("userid1", userid1);
				table.put("userid2", userid2);
				table.put("content", content);
				message.setValue(table);
				message.setType(message.FIRSTMESSAGE);
				oout.writeObject(message);
				oout.flush();
				message = (MyMessage) oin.readObject();
				if (message.getReturnValue().get("message").toString()
						.equalsIgnoreCase("ok")) {
					if (Listtime.size()==0) {
							getchatinfo();
							initData();
						
					}else {
						String timeString=(String)Listtime.get(Listtime.size()-1);
						 getnewchatinfo(timeString);
						 initData();
					}
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void run() {
			try {
				System.out.println("第一次~");
				Log.e("1", "第一次~");
				// sys
				System.out.println("-------------------------------------------");

				Socket socket = new Socket(Tools.IP, Tools.PORT_4);
				ObjectOutputStream oout = new ObjectOutputStream(
						socket.getOutputStream());
				ObjectInputStream oin = new ObjectInputStream(
						socket.getInputStream());
				MyMessage message = new MyMessage();
				Hashtable table = new Hashtable();
				System.out.println("userid前~");
				table.put("userid", Tools.userid);
				System.out.println("userid后~");
				message.setValue(table);
				oout.writeObject(message);
				oout.flush();
				System.out.println("第1.5次~");
				Log.e("1", "第1.5次~");
				while (true) {
					System.out.println("第二次~");
					Log.e("1", "第二次~");
					MyMessage m = (MyMessage) oin.readObject();
					if (m.getReturnValue().get("message").toString()
							.equalsIgnoreCase("ok")) {
						System.out.println("成功~");
						Log.e("1", "成功~");
						if (Tools.isinchat==1) {

							Message message1 = new Message();  
				            message1.what = 1;  
							Tools.handler.sendMessage(message1); 
						}
					
						
					}
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
					 ChatMain.this, 0, new Intent(ChatMain.this,message.class),0);
			 notification.setLatestEventInfo(getApplicationContext(),"新信息", "通知显示的内容", pendingIntent);
			
				notification.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止
				notification.defaults |= Notification.DEFAULT_SOUND; //默认声音	
			Tools.context=getApplicationContext();
			Tools.manager=manager;
			Tools.notification=notification;
			Tools.pendingIntent=pendingIntent;
		}
		
		protected void onResume() {
			notifym();
			super.onResume();
		}
}