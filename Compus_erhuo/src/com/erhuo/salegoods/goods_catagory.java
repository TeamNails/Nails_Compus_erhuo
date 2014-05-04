package com.erhuo.salegoods;


//import com.example.goodsui.R;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erhuo.app.util.Tools;
import com.erhuo.forsale.forSale;
import com.erhuo.main.R;
import com.erhuo.message.message;
public class goods_catagory extends Activity {
//	public RelativeLayout  user_headLayout=null;

//	private ImageView headImageView =null;

	private RelativeLayout  culturerl,dianzirl,clotherrl, virturerl,rentrl,animalrl,carrl,liferl,cardrl,freerl,otherrl;
//	private EditText introduceEditText;
private	Button tijiaoButton=null;
//	private TextView user_nameTextView,nameTextView,catagoryTextView,phoneTextView,priceTextView;
String  actionType="";
//	 private ImageView pictureIm01,pictureIm02,pictureIm03;
	public static final String  CULTURE="CULTURE";
	public static final String 	DIANZI="DIANZI";
	public static final String  CLOTHER="CLOTHER";
	public static final String  VIRTURE="VIRTURE";
	public static final String   RENT="RENT";
	public static final String  ANIMAL="ANIMAL";
	public static final String  CAR="CAR";
	public static final String   LIFE="LIFE";

	public static final String  CARD="CARD";
	public static final String  FREE="FREE";

	public static final String  OTHER="OTHER";

	
//	int screenWidth,screenHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//不显示标题栏
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //不自动显示软键盘
		setContentView(R.layout.catagory);
		notifym();
		findView();
//		  DisplayMetrics metric = new DisplayMetrics();
//	        getWindowManager().getDefaultDisplay().getMetrics(metric);
//	        screenWidth = metric.widthPixels;     // 屏幕宽度（像素）
//	         screenHeight = metric.heightPixels;
////	        screenWidth = display.getWidth();
////		nameTextView.setText("枫");
//	         pictureIm01.setMinimumHeight(screenHeight/5);
//	         pictureIm02.setMinimumHeight(screenHeight/5);
//		      pictureIm03.setMinimumHeight(screenHeight/5);
//		         pictureIm01.setMinimumWidth(screenWidth/3);
//		         pictureIm02.setMinimumWidth(screenWidth/3);
//		         pictureIm03.setMinimumWidth(screenWidth/3);
		         

		onclick();
	
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
	 public void back_OnClick(View v){
		 this.finish();
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	 }
	void findView(){
		culturerl =(RelativeLayout)findViewById(R.id.culturerl);
	
		dianzirl=(RelativeLayout)findViewById(R.id.dianzirl);

		clotherrl=(RelativeLayout)findViewById(R.id.clothrl);
	
		virturerl=(RelativeLayout)findViewById(R.id.virturerl);


		rentrl=(RelativeLayout)findViewById(R.id.rentrl);
		animalrl=(RelativeLayout)findViewById(R.id.animalrl);
		
		carrl=(RelativeLayout)findViewById(R.id.carrl);
		liferl=(RelativeLayout)findViewById(R.id.liferl);
		cardrl=(RelativeLayout)findViewById(R.id.cardrl);
		freerl=(RelativeLayout)findViewById(R.id.freerl);
		otherrl=(RelativeLayout)findViewById(R.id.otherrl);
//		freerl=(Button)findViewById(R.id.tijiao_btn);
	
	}
	void onclick(){
		culturerl .setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=CULTURE;
				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
				intent.putExtra("type",actionType);
				startActivityForResult(intent,1);
				overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

			}
		});
//		user_nameTextView .setOnClickListener(new user_nameTextViewOnclickImp());
		dianzirl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=DIANZI;
				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
				intent.putExtra("type",DIANZI);
				startActivityForResult(intent,1);
				overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

			}
		});
//		nameTextView .setOnClickListener(new nameTextViewOnclickImp());
		clotherrl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=CLOTHER;
				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
				intent.putExtra("type",CLOTHER);
				startActivityForResult(intent,1);
				overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

			}
		});
//		catagoryTextView.setOnClickListener(new catagotyTextViewOnclickImp());
		virturerl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=VIRTURE;
				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
				intent.putExtra("type",VIRTURE);
				startActivityForResult(intent,1);
				overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

			}
		});
//		priceTextView.setOnClickListener(new priceTextViewOnclickImpl());
		rentrl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=RENT;
				
				getIntent().putExtra("catagory","房屋出租");
			setResult(RESULT_OK,goods_catagory.this.getIntent());
			finish();
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);
			}
		});
//		phoneTextView .setOnClickListener(new phoneTextViewOnclickImp());

		animalrl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=ANIMAL;
				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
				intent.putExtra("type",ANIMAL);
				startActivityForResult(intent,1);
				overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

			}
		});
		carrl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=CAR;
				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
				intent.putExtra("type",CAR);
				startActivityForResult(intent,1);
				overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

			}
		});
	liferl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=LIFE;
				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
				intent.putExtra("type",LIFE);
				startActivityForResult(intent,1);
				overridePendingTransition( R.anim.new_dync_out_to_left,R.anim.new_dync_in_from_right);

			}
		});
		otherrl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=OTHER;
				getIntent().putExtra("catagory","其他");
				setResult(RESULT_OK,goods_catagory.this.getIntent());
				finish();
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

//				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
//				intent.putExtra("type",OTHER);
//				startActivityForResult(intent,1);
			}
		});
		cardrl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=CARD;
				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
				intent.putExtra("type",CARD);
				startActivityForResult(intent,1);
		
			}
		});
freerl .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionType=FREE;
				getIntent().putExtra("catagory","免费赠送");
				setResult(RESULT_OK,goods_catagory.this.getIntent());
				finish();
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

//				Intent  intent =new Intent(goods_catagory.this,subcatagory.class);
//				intent.putExtra("type",CULTURE);
//				startActivityForResult(intent,1);
			}
		});
//		introduceEditText .setOnClickListener(new introduceEditTextOnclickImp());
//		tijiaoButton .setOnClickListener(new tijiaoButtonOnclickImp());
	}

//	class  	tijiaoButtonOnclickImp implements  OnClickListener{
//		
//		public void onClick(View v){
////			actionType=GOODS_PICTURE;
////	        Intent intent = new Intent(goods_information.this,PhotoActivity.class);  
////			
////			 goods_information.this.startActivityForResult(intent,1);
////			overridePendingTransition(R.anim.fade_in, R.anim.hold);
//		
//			 } 
//			 }

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);   
		
		
		if(data!=null)
		{		
			if(actionType==CULTURE){
				
				setResult(RESULT_OK, data);  
				  finish(); 
			}else if(actionType==DIANZI){
//				catagoryTextView.setText(data.getStringExtra("retcatagory"));
				setResult(RESULT_OK, data);  
				  finish(); 
			}else if(actionType==CLOTHER){
				setResult(RESULT_OK, data);  
				  finish(); 
//			}else if(actionType==INTRODUCE){
//				jianjieTextView.setText(data.getStringExtra("retjianjie"));
			}else if(actionType==VIRTURE){
				setResult(RESULT_OK, data);  
				  finish(); 
			}else if (actionType==ANIMAL){
				setResult(RESULT_OK, data);  
				  finish(); 
			}else if (actionType==CAR){
				setResult(RESULT_OK, data);  
				  finish(); 
			}else if (actionType==LIFE){
				setResult(RESULT_OK, data);  
				  finish(); 
				}else if (actionType==CARD){
					setResult(RESULT_OK, data);  
					  finish(); 
		
//					
					}
						
	
		}

	}	
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 goods_catagory.this, 0, new Intent(goods_catagory.this,message.class),0);
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




		 