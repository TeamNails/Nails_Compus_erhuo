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
	public static final int PHOTOHRAPH = 1;// ����
	public static final int PHOTOZOOM = 2; // ����
	public static final int PHOTORESOULT = 3;// ���

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

		//���
		button_camera.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_UNSPECIFIED);
				startActivityForResult(intent, PHOTOZOOM);
			}
		});

		//����
		button_takepicture.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				imageFilename=new Date().getTime() + ".jpg";//�������պ��·��
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), imageFilename )));
				System.out.println("=============" + Environment.getExternalStorageDirectory());
				startActivityForResult(intent, PHOTOHRAPH);
			}
		});
		//ȡ��
		button_cancle.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		lastIntent = getIntent();
//		PhotoActivity.this.getIntent().putExtra("BITMAP", PHOTO); //������Է�һ��bitmap  
//		PhotoActivity.this.setResult(RESULT_OK,PhotoActivity.this.getIntent());
//	    PhotoActivity.this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// ����
		if (requestCode == PHOTOHRAPH) {
			// �����ļ�����·��������ڸ�Ŀ¼��
		
			File picture = new File(Environment.getExternalStorageDirectory()+"/"+imageFilename);
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return;

		// ��ȡ�������ͼƬ
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
			System.out.println("------------------------duqusuofang" );
		}
		// ������
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				PHOTO = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				PHOTO.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
																		// 100)ѹ���ļ�
//				imageView_send_1 .setImageBitmap(photo);
				
				System.out.println("------------------------yasuo1" );
//				Intent intent_1  = new Intent(PhotoActivity.this ,  user_information.class);  
//				imageView_send_1 .setDrawingCacheEnabled(Boolean.TRUE);  
				System.out.println("------------------------yasuo2" );
//	            intent_1.putExtra("BITMAP", photo); //������Է�һ��bitmap  
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
//		PhotoActivity.this.getIntent().putExtra("BITMAP", PHOTO); //������Է�һ��bitmap  
//		PhotoActivity.this.setResult(RESULT_OK,PhotoActivity.this.getIntent());
////        PhotoActivity.this.setVisible(false);
//		
//	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 64);
		intent.putExtra("outputY", 64);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}
	
	
	public void notifym() {
		//���֪ͨ������
		 NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); 

		//����һ��֪ͨ����
		 Notification notification = new Notification(R.drawable.icon, "����Ϣ", System.currentTimeMillis()); 

		 PendingIntent pendingIntent = PendingIntent.getActivity( 
				 PhotoActivity.this, 0, new Intent(PhotoActivity.this,message.class),0);
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
