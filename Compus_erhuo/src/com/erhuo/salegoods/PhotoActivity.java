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
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
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
		exit_uploadButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				lastIntent.putExtra("opera","exitupload");
				  lastIntent.putExtra("BITMAP", "");
		            lastIntent.putExtra("photoname", "");
				setResult(Activity.RESULT_OK, PhotoActivity.this.getIntent());
				finish();
				
				
				
				
			}
		});
		
//		PhotoActivity.this.getIntent().putExtra("BITMAP", PHOTO); //这里可以放一个bitmap  
//		PhotoActivity.this.setResult(RESULT_OK,PhotoActivity.this.getIntent());
//	    PhotoActivity.this.finish();
	}
	
	private String getSDCardPath() { 
		String SDCardPath = null; 
		// 判断SDCard是否存在 
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
		//把位图的压缩信息写入到一个指定的输出流中 
		//第一个参数format为压缩的格式 
		//第二个参数quality为图像压缩比的值,0-100.0 意味着小尺寸压缩,100意味着高质量压缩 
		//第三个参数stream为输出流 
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
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
		ispaizhao=1;
		 picture = new File(Environment.getExternalStorageDirectory()+"/"+imageFilename);
		startPhotoZoom(Uri.fromFile(picture));
		
		}

		if (data == null)
			return;

		// 读取相册缩放图片
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
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			
			
			Bundle extras = data.getExtras();
			if (extras != null) {
				PHOTO = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				PHOTO.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
																		// 100)压缩文件
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
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 232);
		intent.putExtra("outputY", 232);
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
