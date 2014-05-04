package com.erhuo.userInfo;

//import android.R;
import com.erhuo.app.util.Tools;
import com.erhuo.main.R;
import com.erhuo.message.message;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
//import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

/**
 * Android实现图片缩放与旋转。
 * @author Administrator
 *
 */
public class user_information_picture extends Activity {
//	public LinearLayout linLayout=null;
	int screenWidth,screenHeight;
    public void onCreate(Bundle icicle) {
        
        super.onCreate(icicle);
        
//        setTitle("Android实现图片缩放与旋转。");
        LinearLayout linLayout = new LinearLayout(this);
		 Intent intent = getIntent();  
		 Bitmap bitmapOrg = (Bitmap)getIntent().getParcelableExtra("BITMAP_BIG");  
        //加载需要操作的图片，这里是一张图
//        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),photo_big);
        
        //获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;     // 屏幕宽度（像素）
         screenHeight = metric.heightPixels;
        //定义预转换成的图片的宽度和高度
        int newWidth =  screenWidth;   
        int newHeight = screenWidth/width*height;
        notifym();
        //计算缩放率，新尺寸除原始尺寸
      
        float scaleWidth =((float) newWidth) / width;;
        float scaleHeight = ((float) newHeight) / height;
        
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        
//        //旋转图片 动作
//        matrix.postRotate(45);
        
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
        width, height, matrix, true);
        
        //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
        //创建一个ImageView
        ImageView imageView = new ImageView(this);
        
        // 设置ImageView的图片为上面转换的图片
        imageView.setImageDrawable(bmd);
        
        //将图片居中显示
        imageView.setScaleType(ScaleType.CENTER);
        
      
		//将ImageView添加到布局模板中
        linLayout.addView(imageView,
        new LinearLayout.LayoutParams(
        LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT
        )
        );
        
        // 设置为本activity的模板
        setContentView(linLayout);
        }
    public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}

	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 user_information_picture.this, 0, new Intent(user_information_picture.this,message.class),0);
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

        
     


