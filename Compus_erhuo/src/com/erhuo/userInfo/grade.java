package com.erhuo.userInfo;

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
import com.erhuo.main.R;
import com.erhuo.message.message;

public class grade extends Activity {
	// public RelativeLayout user_headLayout=null;

	// private ImageView headImageView =null;

	private RelativeLayout onerl, dianzirl, tworl, threerl, fourrl, yanjiurl,
			otherrl;

	// int screenWidth,screenHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ʾ������
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // ���Զ���ʾ�����
		setContentView(R.layout.grade);

		findView();
		notifym();
		onclick();

	}

	public void back_OnClick(View v) {
		this.finish();
		overridePendingTransition(R.anim.right_left2, R.anim.right_left);

	}

	void findView() {
		onerl = (RelativeLayout) findViewById(R.id.one);

		tworl = (RelativeLayout) findViewById(R.id.two);

		threerl = (RelativeLayout) findViewById(R.id.three);

		fourrl = (RelativeLayout) findViewById(R.id.four);

		yanjiurl = (RelativeLayout) findViewById(R.id.yanjiu);
		otherrl = (RelativeLayout) findViewById(R.id.other);

	}

	void onclick() {

		// // priceTextView.setOnClickListener(new priceTextViewOnclickImpl());
		onerl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getIntent().putExtra("grade", "��һ");
				setResult(RESULT_OK, grade.this.getIntent());
				finish();
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});

		tworl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getIntent().putExtra("grade", "���");
				setResult(RESULT_OK, grade.this.getIntent());
				finish();
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
		threerl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getIntent().putExtra("grade", "����");
				setResult(RESULT_OK, grade.this.getIntent());
				finish();
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
		fourrl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getIntent().putExtra("grade", "����");
				setResult(RESULT_OK, grade.this.getIntent());
				finish();
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
		yanjiurl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getIntent().putExtra("grade", "�о���");
				setResult(RESULT_OK, grade.this.getIntent());
				finish();
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
		otherrl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getIntent().putExtra("grade", "����");
				setResult(RESULT_OK, grade.this.getIntent());
				finish();
				overridePendingTransition(R.anim.right_left2, R.anim.right_left);

			}
		});
	}

	public void notifym() {
		// ���֪ͨ������
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// ����һ��֪ͨ����
		Notification notification = new Notification(R.drawable.icon, "����Ϣ",
				System.currentTimeMillis());

		PendingIntent pendingIntent = PendingIntent.getActivity(grade.this, 0,
				new Intent(grade.this, message.class), 0);
		notification.setLatestEventInfo(getApplicationContext(), "����Ϣ",
				"֪ͨ��ʾ������", pendingIntent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL; // �Զ���ֹ
		notification.defaults |= Notification.DEFAULT_SOUND; // Ĭ������
		Tools.context = getApplicationContext();
		Tools.manager = manager;
		Tools.notification = notification;
		Tools.pendingIntent = pendingIntent;
	}

	@Override
	protected void onResume() {
		notifym();
		super.onResume();
	}

}
