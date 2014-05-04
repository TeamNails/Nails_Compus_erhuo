package com.erhuo.login;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import util.ImagedbUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.net.LoginClientTCP;
import com.erhuo.app.util.Tools;
import com.erhuo.comein.LoadingActivity;
import com.erhuo.main.R;


public class LoginActivity extends Activity implements OnClickListener,OnFocusChangeListener {
	
	private static boolean vc=false;
	private static int user=0;
	private Context context;
	private Button signin_button = null;

	private static String user_nm="",user_ky="";
	private TextView regtexText;
	private String userid;
	LinearLayout popupLinear;
	ImageButton mPopupImageButton;
	public PopupWindow pop;
	public EditText mAccountsEditText;
	EditText mPassEditText;
	EditText Verificationcode_edit;
	CheckBox mRemPassCheck;
	Button mLoginButton;
	public myAdapter adapter;
	public HashMap<String,String> list;
	Object[] account;
//	@Override
/*	protected void onStop() {
		// TODO Auto-generated method stub
		Intent inten=new Intent(LoginActivity.this,com.erhuo.message.myservice.class);
		startService(inten);
		super.onStop();
	}*/
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.password_edit:
			if(hasFocus){//获得焦点，则获得密码
				String account=mAccountsEditText.getText().toString();
				if(account.equals("")){
					break;//
				}
				if(list.containsKey(account)){
					mPassEditText.setText(list.get(account));
				}
			}
			break;
		case R.id.username_edit:
			if(hasFocus){
				mAccountsEditText.setText("");
				mPassEditText.setText("");
			}
			break;
		}
	}
//	
	ListView listView;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.popupwindow:
			if(pop==null){
				if(adapter==null){
					adapter=new myAdapter();
					listView=new ListView(LoginActivity.this);
					pop=new PopupWindow(listView, mAccountsEditText.getWidth(), LayoutParams.WRAP_CONTENT);
					listView.setAdapter(adapter);
					pop.showAsDropDown(mAccountsEditText);
				}
				else{
					account=list.keySet().toArray();
					adapter.notifyDataSetChanged();
					pop=new PopupWindow(listView, mAccountsEditText.getWidth(), LayoutParams.WRAP_CONTENT);
					pop.showAsDropDown(mAccountsEditText);
				}
			}
			else{
				pop.dismiss();
				pop=null;
			}
			break;
		case R.id.signin_button:
			String vcc=Verificationcode_edit.getText().toString();
			if(mAccountsEditText.getText().toString().equals("")){
				break;
			}
			if(vcc.equals("")){
				Toast.makeText(LoginActivity.this, "验证码不能为空！",
						Toast.LENGTH_LONG).show();
			}
			if(!vcc.equals(BPUtil.getCode())){
				Toast.makeText(LoginActivity.this, "验证码输入错误！",
						Toast.LENGTH_LONG).show();
			}else{
				vc=true;
			}
			if(vc==false){
				break;
			}
			String account=mAccountsEditText.getText().toString();
			String pass=mPassEditText.getText().toString();
			ImagedbUtil db=new ImagedbUtil(LoginActivity.this);
			db.open();
			Cursor cursor=db.getCursorArgs(new String[]{db.getKEY()}, new String[]{account});
			int keyindex=cursor.getColumnIndexOrThrow(db.getKEY());
			
			try {
				
				String message = LoginClientTCP.login(mAccountsEditText.getText().toString(), mPassEditText.getText().toString());
				if (message==null) {
					Toast.makeText(LoginActivity.this, "messge为空！",
							Toast.LENGTH_LONG).show();
				}
				if (message.equalsIgnoreCase("ok")) {
					
					Intent intent = new Intent();
		             intent.setClass(LoginActivity.this,LoadingActivity.class);
		             intent.putExtra("userid2", mAccountsEditText.getText().toString());
		            /* this.finish();*/
		             startActivity(intent);
		         

				} else if (message.equalsIgnoreCase("error")) {
					Toast.makeText(LoginActivity.this, "新用户，没有激活！",
							Toast.LENGTH_LONG).show();
				} else if (message.equalsIgnoreCase("psswordError")) {
					Toast.makeText(LoginActivity.this, "密码错误！",
							Toast.LENGTH_LONG).show();
					mPassEditText.setText("");
					mPassEditText.requestFocus();
				} else if (message.equalsIgnoreCase("notUser")) {
					Toast.makeText(LoginActivity.this, "没有此用户！",
							Toast.LENGTH_LONG).show();
					mAccountsEditText.setText("");
					mAccountsEditText.requestFocus();
					mPassEditText.setText("");

				}
			} catch (Exception e) {
				
				Toast
						.makeText(LoginActivity.this, "服务连接失败！"+e.getMessage(),
								Toast.LENGTH_LONG).show();
			}
			
			
			
			
			if(mRemPassCheck.isChecked()){
				//保存密码
				if(cursor.getCount()>0){
					int id=cursor.getInt(keyindex);
					safeReleaseCursor(cursor);
					db.update(id, pass);
					safeReleaseDatabase(db);
				}
				else {
					safeReleaseCursor(cursor);
					db.create(account, pass);
					safeReleaseDatabase(db);
				}
				list.put(account, pass);//重新替换或者添加记录
			}
			
			else{
				//不保存密码
				if(cursor.getCount()>0){
					int id=cursor.getInt(keyindex);
					safeReleaseCursor(cursor);
					db.update(id, "");
					safeReleaseDatabase(db);
				}
				else {
					safeReleaseCursor(cursor);
					db.create(account, "");
					safeReleaseDatabase(db);
				}
			    list.put(account, "");//重新替换或者添加记录
			}
			mAccountsEditText.setText("");
			mPassEditText.setText("");
			break;
		}
	}

    /** Called when the activity is first created. */
   
    
	class RegTextOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			startActivity(new Intent(LoginActivity.this, RegActivity.class));
		
		}

	}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        prepare();
        mPopupImageButton=(ImageButton)findViewById(R.id.popupwindow);
        mRemPassCheck=(CheckBox)findViewById(R.id.auto_save_password);
        mLoginButton=(Button)findViewById(R.id.signin_button);
        mAccountsEditText=(EditText)findViewById(R.id.username_edit);
        mPassEditText=(EditText)findViewById(R.id.password_edit);
        regtexText=(TextView)findViewById(R.id.register_link);
        Verificationcode_edit=(EditText)findViewById(R.id.Verificationcode_edit);
        regtexText.setOnClickListener(new RegTextOnClickListener());
        mPassEditText.setOnFocusChangeListener(this);
        mAccountsEditText.setOnFocusChangeListener(this);
        
        mPopupImageButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        
        final ImageView imageView = (ImageView)findViewById(R.id.Verificationcode_pic);
		imageView.setImageBitmap(BPUtil.getInstance().createBitmap());
		imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imageView.setImageBitmap(BPUtil.getInstance().createBitmap());
			}
			
		});

}
    
    private void prepare(){
    	list=new HashMap<String, String>();
    	ImagedbUtil db=new ImagedbUtil(this);
    	db.open();
    	Cursor cursor=db.getCursor(db.getKEY(),db.getACCOUNTS(),db.getPASSWORD());
    	int accountsindex=cursor.getColumnIndexOrThrow(db.getACCOUNTS());
    	int passindex=cursor.getColumnIndexOrThrow(db.getPASSWORD());
    	String accounts;
    	String pass;
    	if(cursor.getCount()>0){
    		do{
    			accounts=cursor.getString(accountsindex);
    			pass=cursor.getString(passindex);
    			list.put(accounts, pass);
        	}while(cursor.moveToNext());
    	}
    	safeReleaseCursor(cursor);
    	safeReleaseDatabase(db);
    }
    
    private void safeReleaseCursor(Cursor cursor){
    	cursor.close();
    	cursor=null;
    }
    
    
    private void safeReleaseDatabase(ImagedbUtil db){
    	db.close();
    	db=null;
    }
    
    
    
    
    
    
    class myAdapter extends BaseAdapter {
    	LayoutInflater mInflater;
    	public myAdapter() {
    		mInflater=LayoutInflater.from(LoginActivity.this);
    		account=list.keySet().toArray();
    		// TODO Auto-generated constructor stub
    	}

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		return account.length;
    	}

    	@Override
    	public Object getItem(int position) {
    		// TODO Auto-generated method stub
    		return null;
    	}

    	@Override
    	public long getItemId(int position) {
    		// TODO Auto-generated method stub
    		return position;
    	}
//删除记录
    	@Override
    	public View getView(final int position, View convertView, ViewGroup parent) {
    		// TODO Auto-generated method stub
    		Holder holder=null;
    		if(convertView==null){
    			convertView=mInflater.inflate(R.layout.popup, null);
    			holder=new Holder();
    			holder.view=(TextView)convertView.findViewById(R.id.mQQ);
    			holder.button=(ImageButton)convertView.findViewById(R.id.mQQDelete);
    			convertView.setTag(holder);
    		}
    		else{
    			holder=(Holder) convertView.getTag();
    		}
    		if(holder!=null){
    			convertView.setId(position);
    			holder.setId(position);
    			holder.view.setText(account[position].toString());
    			holder.view.setOnTouchListener(new OnTouchListener() {
    				
    				@Override
    				public boolean onTouch(View v, MotionEvent event) {
    					// TODO Auto-generated method stub
    					pop.dismiss();
    					mAccountsEditText.setText(account[position].toString());
    					mPassEditText.setText(list.get(account[position]));
    					return true;
    				}
    			});
    			
    			holder.button.setOnClickListener(new OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					String accounts=account[position].toString();
    					list.remove(accounts);
    					ImagedbUtil db=new ImagedbUtil(LoginActivity.this);
    					db.open();
    					Cursor cursor=db.getCursorArgs(new String[]{db.getKEY()}, new String[]{accounts});
    					int keyindex=cursor.getColumnIndexOrThrow(db.getKEY());
    					int id=cursor.getInt(keyindex);
    					cursor.close();
    					db.delete(id);
    					account=list.keySet().toArray();
    					adapter.notifyDataSetChanged();
    				}
    			});
    		}
    		return convertView;
    	}
    	
    	class Holder{
    		TextView view;
    		ImageButton button;
    		
    		void setId(int position){
    			view.setId(position);
    			button.setId(position);
    		}
    	}

    }
    
    
    class SettingOnMenuItemClickListener implements OnMenuItemClickListener {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub

			AlertDialog.Builder buil = new AlertDialog.Builder(
					LoginActivity.this);
			buil.setTitle("设置服务器信息");
			LinearLayout line = (LinearLayout) LayoutInflater.from(
					LoginActivity.this).inflate(R.layout.set, null);

			final EditText ip = (EditText) line.findViewById(R.id.ip_editText);
			final EditText port1 = (EditText) line
					.findViewById(R.id.port1_editText);
			final EditText port2 = (EditText) line
					.findViewById(R.id.port2_editText);
			final EditText port3 = (EditText) line
					.findViewById(R.id.port3_editText);
			ip.setText(Tools.IP);
			port1.setText(Tools.PORT_1 + "");
			port2.setText(Tools.PORT_2 + "");
			port3.setText(Tools.PORT_3 + "");

			buil.setView(line);

			buil.setNeutralButton("设置", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					try {
						Tools.IP = ip.getText().toString();
						Tools.PORT_1 = Integer.parseInt(port1.getText()
								.toString());
						Tools.PORT_2 = Integer.parseInt(port2.getText()
								.toString());
						Tools.PORT_3 = Integer.parseInt(port3.getText()
								.toString());
						Toast.makeText(LoginActivity.this, "设置成功！",
								Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(LoginActivity.this, "设置失败！",
								Toast.LENGTH_LONG).show();
					}

				}
			});
			buil.setNegativeButton("关闭", null);
			buil.create().show();

			return false;
		}

	}
    protected void onNewIntent(Intent intent) {  
    	// TODO Auto-generated method stub   
    	super.onNewIntent(intent);  
    	//退出   
    	 if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {  
    		 Verificationcode_edit.setText("");
    		 final ImageView imageView = (ImageView)findViewById(R.id.Verificationcode_pic);
    			imageView.setImageBitmap(BPUtil.getInstance().createBitmap());
    			imageView.setOnClickListener(new OnClickListener(){

    				@Override
    				public void onClick(View arg0) {
    					// TODO Auto-generated method stub
    					imageView.setImageBitmap(BPUtil.getInstance().createBitmap());
    				}
    				
    			});
    	 }  
    	}  

    /** 
	 * 菜单、返回键响应 
	 */ 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //调用双击退出函数  
	       } 
	    return false;  
	}

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
    
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		menu.add("设置服务器信息").setOnMenuItemClickListener(
				new SettingOnMenuItemClickListener());
		return super.onCreateOptionsMenu(menu);
	}
    
}