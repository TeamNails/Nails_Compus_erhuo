package com.erhuo.salegoods;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.erhuo.app.util.Tools;
import com.erhuo.main.R;
import com.erhuo.message.message;

public class PhotoActivity extends Activity {
	private Button button_takepicture,button_camera,button_cancle,exit_uploadButton=null;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// ����
	public static final int PHOTOZOOM = 2; // ����
	public static final int PHOTORESOULT = 3;// ���
	File picture;
	public int ispaizhao=0;

	public static final String IMAGE_UNSPECIFIED = "image/*";
	ImageView imageView_send_1 = null;
	String imageFilename = "";
	private Intent lastIntent ;
	public static Bitmap PHOTO;
	private String photoname;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.imgorphoto);
//		 PhotoActivity.this.setVisible(true);
//		imageView = (ImageView) findViewById(R.id.imageID);
		lastIntent = getIntent();
		notifym();
		button_takepicture=(Button)findViewById(R.id.photo_Btn);
		button_camera=(Button)findViewById(R.id.picture_Btn);
		button_cancle=(Button)findViewById(R.id.exitBtn);
		exit_uploadButton=(Button)findViewById(R.id.exit_upload);
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
		exit_uploadButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				lastIntent.putExtra("opera","exitupload");
				  lastIntent.putExtra("BITMAP", "");
		            lastIntent.putExtra("photoname", "");
				setResult(Activity.RESULT_OK, PhotoActivity.this.getIntent());
				finish();
				
				
				
				
			}
		});
		
//		PhotoActivity.this.getIntent().putExtra("BITMAP", PHOTO); //������Է�һ��bitmap  
//		PhotoActivity.this.setResult(RESULT_OK,PhotoActivity.this.getIntent());
//	    PhotoActivity.this.finish();
	}
	
	private String getSDCardPath() { 
		String SDCardPath = null; 
		// �ж�SDCard�Ƿ���� 
		boolean IsSDcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); 
		if (IsSDcardExist) { 
		SDCardPath = Environment.getExternalStorageDirectory().toString(); 
		} 
		return SDCardPath; 
		} 
	
	private void compressAndSaveBitmapToSDCard(Bitmap rawBitmap,String fileName,int quality){
		String saveFilePaht=this.getSDCardPath()+File.separator+fileName; 
		File saveFile=new File(saveFilePaht); 
		if (!saveFile.exists()) { 
		try { 
		saveFile.createNewFile(); 
		FileOutputStream fileOutputStream=new FileOutputStream(saveFile); 
		if (fileOutputStream!=null) { 
		//imageBitmap.compress(format, quality, stream); 
		//��λͼ��ѹ����Ϣд�뵽һ��ָ����������� 
		//��һ������formatΪѹ���ĸ�ʽ 
		//�ڶ�������qualityΪͼ��ѹ���ȵ�ֵ,0-100.0 ��ζ��С�ߴ�ѹ��,100��ζ�Ÿ�����ѹ�� 
		//����������streamΪ����� 
		rawBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream); 
		} 
		fileOutputStream.flush(); 
		fileOutputStream.close(); 
		} catch (IOException e) { 
		e.printStackTrace(); 

		} 
		} 
		}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// ����
		if (requestCode == PHOTOHRAPH) {
			// �����ļ�����·��������ڸ�Ŀ¼��
		ispaizhao=1;
		 picture = new File(Environment.getExternalStorageDirectory()+"/"+imageFilename);
		startPhotoZoom(Uri.fromFile(picture));
		
		}

		if (data == null)
			return;

		// ��ȡ�������ͼƬ
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
			System.out.println("------------------------duqusuofang" );
			/*
			Uri uri = data.getData();
    		String[] proj = { MediaStore.Images.Media.DATA };
    		Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);
    		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    		actualimagecursor.moveToFirst();
    		String img_path = actualimagecursor.getString(actual_image_column_index);
    		File file = new File(img_path);*/
		}
		// ������
		if (requestCode == PHOTORESOULT) {
			
			
			Bundle extras = data.getExtras();
			if (extras != null) {
				PHOTO = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				PHOTO.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
																		// 100)ѹ���ļ�
				String nameString=new Date().getTime() + ".jpg";
				compressAndSaveBitmapToSDCard(PHOTO,nameString,100);
				File ff=new File(Environment.getExternalStorageDirectory(), nameString);
				
	    		
	    		try {
	    			Socket socket = new Socket(Tools.IP, Tools.PORT_3);
	    			InputStream in = socket.getInputStream();
	    			OutputStream out = socket.getOutputStream();
	    			out.write(("upload,image," + ff.length())
	    							.getBytes());
	    			out.flush();
	    			byte[] b = new byte[1024 * 10];
	    			in.read(b);
	    			String serverInfo = new String(b).trim();
	    			serverInfo.startsWith("ok");
	    			String[] ss = serverInfo.split(",");
	    			photoname = ss[1];
	    			FileInputStream fin = new FileInputStream(ff);

	    			int len = 0;
	    			while ((len = fin.read(b)) != -1) {
	    				out.write(b, 0, len);
	    				out.flush();

	    			}
	    			fin.close();
	    			out.close();
	    			in.close();
	    		} catch (UnknownHostException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} catch (FileNotFoundException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		
	    		
	            lastIntent.putExtra("BITMAP", PHOTO);
	            lastIntent.putExtra("photoname", photoname);
	        	lastIntent.putExtra("opera","tackpicture");
	        	 
				setResult(Activity.RESULT_OK, PhotoActivity.this.getIntent());
	            PhotoActivity.this.finish();
	            System.out.println("------------------------end" );
	          
	            ff.delete();
	            if (ispaizhao==1) {
	            	 picture.delete();
				}
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	


	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		Random random=new Random();
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 232);
		intent.putExtra("outputY", 232);
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
