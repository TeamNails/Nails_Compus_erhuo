package com.erhuo.userInfo;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


import com.erhuo.app.util.Tools;
import com.erhuo.forsale.forSale;
import com.erhuo.main.R;
import com.erhuo.message.message;

public class PhotoActivity extends Activity {
	private Button button_takepicture,button_camera,button_cancle=null;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果

	public static final String IMAGE_UNSPECIFIED = "image/*";
	ImageView imageView_send_1 = null;
	String imageFilename = "";
	private Intent lastIntent ;
	public static Bitmap PHOTO;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.imgorphoto_head);
//		 PhotoActivity.this.setVisible(true);
//		imageView = (ImageView) findViewById(R.id.imageID);

		notifym();
		button_takepicture=(Button)findViewById(R.id.photo_Btn);
		button_camera=(Button)findViewById(R.id.picture_Btn);
		button_cancle=(Button)findViewById(R.id.exitBtn);

		//相册
		button_camera.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_UNSPECIFIED);
				startActivityForResult(intent, PHOTOZOOM);
			}
		});

		//拍照
		button_takepicture.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				imageFilename=new Date().getTime() + ".jpg";//设置拍照后的路径
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), imageFilename )));
				System.out.println("=============" + Environment.getExternalStorageDirectory());
				startActivityForResult(intent, PHOTOHRAPH);
			}
		});
		//取消
		button_cancle.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		lastIntent = getIntent();
//		PhotoActivity.this.getIntent().putExtra("BITMAP", PHOTO); //这里可以放一个bitmap  
//		PhotoActivity.this.setResult(RESULT_OK,PhotoActivity.this.getIntent());
//	    PhotoActivity.this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
		
			File picture = new File(Environment.getExternalStorageDirectory()+"/"+imageFilename);
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
			System.out.println("------------------------duqusuofang" );
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				PHOTO = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				PHOTO.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
																		// 100)压缩文件
//				imageView_send_1 .setImageBitmap(photo);
				
				System.out.println("------------------------yasuo1" );
//				Intent intent_1  = new Intent(PhotoActivity.this ,  user_information.class);  
//				imageView_send_1 .setDrawingCacheEnabled(Boolean.TRUE);  
				System.out.println("------------------------yasuo2" );
//	            intent_1.putExtra("BITMAP", photo); //这里可以放一个bitmap  
	            System.out.println("------------------------yasuo3" );
//	            startActivity(intent_1); 
//	            PhotoActivity.this.setVisible(false);
	            lastIntent.putExtra("BITMAP", PHOTO);
				setResult(Activity.RESULT_OK, PhotoActivity.this.getIntent());
	            PhotoActivity.this.finish();
	            System.out.println("------------------------end" );
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
//		finish();
	}

	
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		PhotoActivity.this.getIntent().putExtra("BITMAP", PHOTO); //这里可以放一个bitmap  
//		PhotoActivity.this.setResult(RESULT_OK,PhotoActivity.this.getIntent());
////        PhotoActivity.this.setVisible(false);
//		
//	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 64);
		intent.putExtra("outputY", 64);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}
	
	
	public void notifym() {
		//获得通知管理器
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//构建一个通知对象
		 Notification notification = new Notification(R.drawable.icon, "新消息", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 PhotoActivity.this, 0, new Intent(PhotoActivity.this,message.class),0);
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
