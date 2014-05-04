package com.erhuo.forsale;





import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.main.R;
import com.erhuo.message.message;

public class subcatagory extends Activity {
     String  type="";
     Intent lastIntent=null;
     LinearLayout  subcatagoryLayout,subLayout=null;
    RelativeLayout rl1,rl2,rl3,rl4,rl5,rl6=null;
     
     @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	lastIntent=super.getIntent();
    	type=  lastIntent.getStringExtra("type");
    	 subcatagoryLayout=(LinearLayout)findViewById(R.id.subcatagoryxx);
    	
    
    	 if(type.equalsIgnoreCase("CULTURE")){

		
	 subLayout=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.culture, null);

	 subcatagoryLayout.addView(subLayout);
	 rl1=(RelativeLayout)findViewById(R.id.musicrl);
	 rl2=(RelativeLayout)findViewById(R.id.sportsrl);
	 rl3=(RelativeLayout)findViewById(R.id.bangongrl);
	 rl4=(RelativeLayout)findViewById(R.id.artrl);
	 rl1.setOnClickListener( new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
	
			lastIntent.putExtra("catagory","音乐/电影/书籍");
			setResult(RESULT_OK, lastIntent);
			  finish();  
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

		}
	});
	 rl2.setOnClickListener( new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lastIntent.putExtra("catagory","运动/户外活动");
			setResult(RESULT_OK, lastIntent);
			  finish();  
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

		}
	});
	 rl3.setOnClickListener( new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lastIntent.putExtra("catagory","文具办公");
			setResult(RESULT_OK, lastIntent);
			  finish();  
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

		}
	});
	 rl4.setOnClickListener( new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lastIntent.putExtra("catagory","艺术品/收藏品");
			setResult(RESULT_OK, lastIntent);
			  finish();  	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);
		}
	});

	 

	 
	 
}else if(type.equalsIgnoreCase("DIANZI")){
	
	 subLayout=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.dianzi, null);

	 subcatagoryLayout.addView(subLayout);
	 rl1=(RelativeLayout)findViewById(R.id.computerrl);
	 rl2=(RelativeLayout)findViewById(R.id.phonerl);
	 rl3=(RelativeLayout)findViewById(R.id.dianqirl);
	 rl4=(RelativeLayout)findViewById(R.id.dianzichanpinrl);
	 rl1.setOnClickListener( new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lastIntent.putExtra("catagory","电脑/配件");
			setResult(RESULT_OK, lastIntent);
			  finish();  
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

		}
	});
	 rl2.setOnClickListener( new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lastIntent.putExtra("catagory","手机/配件");
			setResult(RESULT_OK, lastIntent);
			  finish();  
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

		}
	});
	 rl3.setOnClickListener( new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			lastIntent.putExtra("catagory","家用电器");
			setResult(RESULT_OK, lastIntent);
			  finish();  	
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);
}
	});
	 rl4.setOnClickListener( new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			lastIntent.putExtra("catagory","电子产品");		
			setResult(RESULT_OK, lastIntent);
			  finish();  
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

		}
	});

}else  if(type.equalsIgnoreCase("CLOTHER")){
	 subLayout=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.clothes, null);

	 subcatagoryLayout.addView(subLayout);
rl1=(RelativeLayout)findViewById(R.id.manrl);
rl2=(RelativeLayout)findViewById(R.id.womenrl);
rl3=(RelativeLayout)findViewById(R.id.peishirl);

rl1.setOnClickListener( new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","男装");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl2.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","女装");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl3.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","鞋包配饰");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});


}else if (type.equalsIgnoreCase("VIRTURE")) {
	 subLayout=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.virture, null);

	 subcatagoryLayout.addView(subLayout);
rl1=(RelativeLayout)findViewById(R.id.phonenumber);
rl2=(RelativeLayout)findViewById(R.id.dianka);
rl3=(RelativeLayout)findViewById(R.id.zhuangbei);
rl4=(RelativeLayout)findViewById(R.id.qq);
rl1.setOnClickListener( new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","手机号");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl2.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","点卡/账号");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl3.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","游戏装备");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});

rl4.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","QQ专区");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});

}else if (type.equalsIgnoreCase("ANIMAL")) {
	 subLayout=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.animal, null);

	 subcatagoryLayout.addView(subLayout);
rl1=(RelativeLayout)findViewById(R.id.jiaoyirl);
rl2=(RelativeLayout)findViewById(R.id.yongpinrl);
rl3=(RelativeLayout)findViewById(R.id.lingyangrl);

rl1.setOnClickListener( new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","宠物交易");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl2.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","宠物用品");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl3.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","宠物领养与赠送");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});



}
else if (type.equalsIgnoreCase("CAR")) {
	 subLayout=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.car, null);

	 subcatagoryLayout.addView(subLayout);
rl1=(RelativeLayout)findViewById(R.id.ziingrl);
rl2=(RelativeLayout)findViewById(R.id.diandongrl);
rl3=(RelativeLayout)findViewById(R.id.motuorl);
rl4=(RelativeLayout)findViewById(R.id.peijianrl);
rl5=(RelativeLayout)findViewById(R.id.zucherl);
rl1.setOnClickListener( new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","自行车");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl2.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","电动车");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl3.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","摩托车");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl4.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","配件");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl5.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		lastIntent.putExtra("catagory","租车");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});

}else if (type.equalsIgnoreCase("LIFE")) {
	 subLayout=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.life, null);

	 subcatagoryLayout.addView(subLayout);
rl1=(RelativeLayout)findViewById(R.id.jiaju);
rl2=(RelativeLayout)findViewById(R.id.riyongpin);
rl3=(RelativeLayout)findViewById(R.id.jiajushipin);
rl4=(RelativeLayout)findViewById(R.id.zhuangxiu);
rl1.setOnClickListener( new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","家具");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl2.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","日用品");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});

rl3.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","家居饰品");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl4.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","装修建材");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});

}else if (type.equalsIgnoreCase("CARD")) {
	 subLayout=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.card, null);

	 subcatagoryLayout.addView(subLayout);
rl1=(RelativeLayout)findViewById(R.id.zhekourl);
rl2=(RelativeLayout)findViewById(R.id.gouwurl);

rl3=(RelativeLayout)findViewById(R.id.chepiaorl);
rl1.setOnClickListener( new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","折扣卡/优惠劵");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl2.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","购物卡");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});
rl3.setOnClickListener( new OnClickListener() {
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		lastIntent.putExtra("catagory","车票/旅游");		
		setResult(RESULT_OK, lastIntent);
		  finish();  
	 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}
});


}
    	 }
    	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//不显示标题栏
		
		setContentView(R.layout.subcatagory);
		 notifym();
				
	}
		 public void back_btn(View v){
	    	 this.finish();
		 		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	     }

		 public void notifym() {
				//获得通知管理器
				 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

				//构建一个通知对象
				 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

				 PendingIntent pendingIntent = PendingIntent.getActivity( 
						 subcatagory.this, 0, new Intent(subcatagory.this,message.class),0);
				 notification.setLatestEventInfo(getApplicationContext(),"新信息", "通知显示的内容", pendingIntent);
				
					notification.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止
					notification.defaults |= Notification.DEFAULT_SOUND; //默认声音	
				Tools.context=getApplicationContext();
					Tools.manager=manager;
				Tools.notification=notification;
				Tools.pendingIntent=pendingIntent;
			}
			

	

	}
