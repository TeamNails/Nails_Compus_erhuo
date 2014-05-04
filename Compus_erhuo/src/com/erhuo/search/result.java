package com.erhuo.search;

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
import java.util.Vector;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.Myadapter;
import com.erhuo.forsale.forSale;
import com.erhuo.forsale.goods_catagory;
import com.erhuo.main.R;
import com.erhuo.message.message;
import com.erhuo.salegoods.GoodsActivity;
import com.erhuo.server.MyMessage;
import com.erhuo.server.SellS;

public class result extends Activity {
	
	private ListView listView1, listView2;
	private ImageView descTextView, ascTextView=null;
	AutoCompleteTextView auto = null;
	RelativeLayout.LayoutParams ly, ly2;
	int screenWidth, screenHeight = 0;
	int start = 0;
	private String userid = null, catagory = "", serchcontent = "",
			serachcontentString = " ", type = "";
	ArrayList Listgoodsid = new ArrayList();
	Myadapter ma;
	Intent intent = null;
	List<Map<String, Object>> list = null;
	Button searchBtn = null;
	LinearLayout liner2,resultLayout,listLayout;
	RelativeLayout mainlayout=null;
	View convertView1,convertView2=null;
	private boolean d=true;
	private boolean e=false;
	private boolean f=true;
	private Button returnbtn;
//	private boolean g=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.seach_result);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		notifym();
		auto = (AutoCompleteTextView) findViewById(R.id.auto);
		Intent intent = super.getIntent();
//		zhutitTextView=(TextView)findViewById(R.id.zhutitv);
		type = intent.getStringExtra("type");
//		zhutitTextView.setText( intent.getStringExtra("types"));
		listLayout=(LinearLayout)findViewById(R.id.listlayout);
		resultLayout=(LinearLayout)findViewById(R.id.no_result);
		convertView1 = LayoutInflater.from(result.this).inflate(
				R.layout.no_result, null);
		returnbtn=(Button)convertView1.findViewById(R.id.returnBtn);
		returnbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(result.this, Search.class);
				intent.putExtra("userid", userid);
				startActivityForResult(intent, 1);
				overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);
			}
		});
		mainlayout=(RelativeLayout)findViewById(R.id.mainlayout);
		ly = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		ly.addRule(RelativeLayout.BELOW, R.id.top_layout);
		ly.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		ly.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

//		ly.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

		convertView1.setLayoutParams(ly);
		
//		 liner2 = (LinearLayout) LayoutInflater.from(
//				this).inflate(R.layout.no_result, null);
//			resultLayout= (LinearLayout) liner2
//				.findViewById(R.id.no_result);
//		TextView leavewordtv = (TextView) linear2
//				.findViewById(R.id.leavewordtv);
//		TextView timetv = (TextView) linear2
//				.findViewById(R.id.timetv);

		if (type.equalsIgnoreCase("class")) {
			catagory = intent.getStringExtra("catagory");
			auto.setHint("在 "+catagory+" 里搜索");
		}else {
			serchcontent = intent.getStringExtra("serchcontent");
		}
		userid = intent.getStringExtra("userid")+"";
		if (serchcontent!=null) {
			serachcontentString = serchcontent;
		}
		ascTextView = (ImageView) findViewById(R.id.asc);
		descTextView = (ImageView) findViewById(R.id.desc);
		searchBtn = (Button) findViewById(R.id.searchBtn);

		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				serachcontentString = auto.getText().toString();
				
				if(serachcontentString.equalsIgnoreCase("")){
					Toast.makeText(result.this,"请输入点东西在搜索吧！",Toast.LENGTH_LONG).show();
						return;
				}
				if (type.equalsIgnoreCase("class")) {
					
					info_show(catagory, "asc", serachcontentString);
//					Toast.makeText(result.this,","+Listgoodsid.size()+","+f,Toast.LENGTH_LONG).show();
	
					if (Listgoodsid.size()==0) {
					if(	(d==true&&(e=true))||(f==true)){
							//无结果
						mainlayout.removeView(listLayout);
						Toast.makeText(result.this,"无结果！"+f,Toast.LENGTH_LONG).show();

						mainlayout.addView(convertView1);
						d=false;
						e=false;
						f=false;
					}
					return;
					}
					
					//有结果
				if(	((d==false)&&(e==false))||(f==false)){
					mainlayout.removeView(convertView1);
					mainlayout.addView(listLayout);
					d=true;
					e=true;
					f=false;
				}
				return;
				


				} else {
					content_info_show(serachcontentString, "asc");
//					Toast.makeText(result.this,","+Listgoodsid.size()+","+f,Toast.LENGTH_LONG).show();

							if (Listgoodsid.size()==0) {
								if(	(d==true&&(e=true))||(f==true)){
									//无结果
								mainlayout.removeView(listLayout);
								mainlayout.addView(convertView1);
								d=false;
								e=false;
								f=false;
							}
							return;
							}

							//有结果
							if(	((d==false)&&(e==false))||(f==false)){
								mainlayout.removeView(convertView1);
								mainlayout.addView(listLayout);
								d=true;
								e=true;
								f=true;
							}
							return;
							
				}
			}

		});

		listView1 = (ListView) findViewById(R.id.datalist);
		listView1.setOnItemClickListener(new OnItemClickListenerIml());

		if (type.equals("class")) {
			info_show(catagory, "asc", serachcontentString);
			
//			Toast.makeText(result.this,","+Listgoodsid.size()+","+f,Toast.LENGTH_LONG).show();

			if (Listgoodsid.size()==0) {

								if(	(d==true)&&(e==false)){
										//无结果
									
									mainlayout.removeView(listLayout);
									mainlayout.addView(convertView1);
									searchBtn.setEnabled(false);
									auto.setEnabled(false);
									d=false;
									e=false;
								}
								return;
								
			}
			if(	(d==false)&&(e==true)){
				mainlayout.removeView(convertView1);
				mainlayout.addView(listLayout);
				d=true;
				e=true;
				
			}
			return;
		} else {
			content_info_show(serachcontentString, "asc");
//			Toast.makeText(result.this,","+Listgoodsid.size()+","+f,Toast.LENGTH_LONG).show();

			if (Listgoodsid.size()==0) {
				if(	d==true&&(e==false)){
					//无结果
				
				mainlayout.removeView(listLayout);
				mainlayout.addView(convertView1);
				searchBtn.setEnabled(false);
				auto.setEnabled(false);

				d=false;
				e=false;
//				f=false;
			}
			return;
			
}
			if(	(d==false)&&(e==true)){
				mainlayout.removeView(convertView1);
				mainlayout.addView(listLayout);
				d=true;
				e=true;
				
			}
		}

		// 升序
		ascTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!type.equalsIgnoreCase("class")) {
					content_info_show(serachcontentString, "asc");

				} else {
					info_show(catagory, "asc", serachcontentString);

				}

			}
		});
		// 降序
		descTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!type.equalsIgnoreCase("class")) {
					content_info_show(serachcontentString, "desc");
					
				
					
					
					
				} else {
					info_show(catagory, "desc", serachcontentString);
				
				}
			}
		});

	}

	
	
	


	// 卖东西展示触发
	private class OnItemClickListenerIml implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(result.this, GoodsActivity.class);
			intent.putExtra("goodsid", (Integer) Listgoodsid.get(position));
//			intent.putExtra("userid", userid);
			intent.putExtra("type", "collect");
			result.this.startActivityForResult(intent, 1);
		}

	}

	// 卖东西显示
	public void info_show(String value, String type, String goodsn) {

		list = new ArrayList<Map<String, Object>>();
		Listgoodsid=new ArrayList<Map<String, Object>>();
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.CATAGORYSEARCH);
			Hashtable table = new Hashtable();
			table.put("catagory", value);
			table.put("type", type);
			table.put("goodsname", goodsn);
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
					Listgoodsid.add(sell.getGoodsid());
					String selltime = sell.getSelltime();
					String photo1 = sell.getGoodsphoto1();
					String address=sell.getAddress();
					String gradeString=sell.getGrade();
				
					getphoto1(photo1);

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pic", "/sdcard/destDir/" + photo1);
					map.put("name", thingname);
					map.put("price", thingprice + "元");
					map.put("time", selltime.substring(0, 19));
					map.put("address",address);
					this.list.add(map);

					i++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ma = new Myadapter(this, this.list, R.layout.selling, new String[] {
				"pic", "name", "price", "time", "address"}, new int[] {
				R.id.goodsimageshow, R.id.goodsnameshow, R.id.priceshow,
				R.id.timeshow ,R.id.addressshow}, 4);
		this.listView1.setAdapter(ma);

	}

	// 按内容搜索
	public void content_info_show(String value, String type) {

		list = new ArrayList<Map<String, Object>>();
		Listgoodsid=new ArrayList<Map<String, Object>>();
		try {
			Socket socket = new Socket(Tools.IP, Tools.PORT_1);
			ObjectOutputStream oout = new ObjectOutputStream(
					socket.getOutputStream());
			ObjectInputStream oin = new ObjectInputStream(
					socket.getInputStream());
			MyMessage message = new MyMessage();
			message.setType(message.SEARCH);
			Hashtable table = new Hashtable();
			table.put("goodsname", value);
			table.put("type", type);
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
					Listgoodsid.add(sell.getGoodsid());
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
					map.put("address",address);
					this.list.add(map);

					i++;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ma = new Myadapter(this, this.list, R.layout.selling, new String[] {
				"pic", "name", "price", "time","address", }, new int[] {
				R.id.goodsimageshow, R.id.goodsnameshow, R.id.priceshow,
				R.id.timeshow,R.id.addressshow, }, 4);
		this.listView1.setAdapter(ma);

	}

	// 求购显示

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

	public void back_OnClick(View v) {
		this.finish();
	
		Intent intent = new Intent(result.this, Search.class);
intent.putExtra("userid", userid);
		startActivityForResult(intent, 1);
		overridePendingTransition( R.anim.anim_enter,R.anim.anim_exit);

	}
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 result.this, 0, new Intent(result.this,message.class),0);
		 notification.setLatestEventInfo(getApplicationContext(),"新信息", "通知显示的内容", pendingIntent);
		
			notification.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止
			notification.defaults |= Notification.DEFAULT_SOUND; //默认声音	
		Tools.context=getApplicationContext();
			Tools.manager=manager;
		Tools.notification=notification;
		Tools.pendingIntent=pendingIntent;
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		this.finish();
		super.onRestart();
	}
	@Override
	protected void onResume() {
	
		notifym();
		super.onResume();
	}
}
