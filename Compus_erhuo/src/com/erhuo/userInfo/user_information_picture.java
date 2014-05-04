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
 * Androidʵ��ͼƬ��������ת��
 * @author Administrator
 *
 */
public class user_information_picture extends Activity {
//	public LinearLayout linLayout=null;
	int screenWidth,screenHeight;
    public void onCreate(Bundle icicle) {
        
        super.onCreate(icicle);
        
//        setTitle("Androidʵ��ͼƬ��������ת��");
        LinearLayout linLayout = new LinearLayout(this);
		 Intent intent = getIntent();  
		 Bitmap bitmapOrg = (Bitmap)getIntent().getParcelableExtra("BITMAP_BIG");  
        //������Ҫ������ͼƬ��������һ��ͼ
//        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),photo_big);
        
        //��ȡ���ͼƬ�Ŀ�͸�
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;     // ��Ļ��ȣ����أ�
         screenHeight = metric.heightPixels;
        //����Ԥת���ɵ�ͼƬ�Ŀ�Ⱥ͸߶�
        int newWidth =  screenWidth;   
        int newHeight = screenWidth/width*height;
        notifym();
        //���������ʣ��³ߴ��ԭʼ�ߴ�
      
        float scaleWidth =((float) newWidth) / width;;
        float scaleHeight = ((float) newHeight) / height;
        
        // ��������ͼƬ�õ�matrix����
        Matrix matrix = new Matrix();
        
        // ����ͼƬ����
        matrix.postScale(scaleWidth, scaleHeight);
        
//        //��תͼƬ ����
//        matrix.postRotate(45);
        
        // �����µ�ͼƬ
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
        width, height, matrix, true);
        
        //�����洴����Bitmapת����Drawable����ʹ�������ʹ����ImageView, ImageButton��
        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
        //����һ��ImageView
        ImageView imageView = new ImageView(this);
        
        // ����ImageView��ͼƬΪ����ת����ͼƬ
        imageView.setImageDrawable(bmd);
        
        //��ͼƬ������ʾ
        imageView.setScaleType(ScaleType.CENTER);
        
      
		//��ImageView��ӵ�����ģ����
        linLayout.addView(imageView,
        new LinearLayout.LayoutParams(
        LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT
        )
        );
        
        // ����Ϊ��activity��ģ��
        setContentView(linLayout);
        }
    public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		finish();
		return true;
	}

	public void notifym() {
		//���֪ͨ������
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//����һ��֪ͨ����
		 Notification notification = new Notification(R.drawable.icon, "����Ϣ", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 user_information_picture.this, 0, new Intent(user_information_picture.this,message.class),0);
		 notification.setLatestEventInfo(getApplicationContext(),"����Ϣ", "֪ͨ��ʾ������", pendingIntent);
		
			notification.flags|=Notification.FLAG_AUTO_CANCEL; //�Զ���ֹ
			notification.defaults |= Notification.DEFAULT_SOUND; //Ĭ������	
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

        
     


