package com.erhuo.login;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.erhuo.app.util.Tools;
import com.erhuo.main.R;
import com.erhuo.server.MyMessage;

public class RegActivity extends Activity {
	private EditText useridText;
	private EditText userpassword1;
	private EditText userpassword2;
	private EditText usernameText;
	private EditText addressText;
	private EditText qqEditText;
	private EditText emailEditText;
	  private Spinner mySpinner;   
	  private TextView gradeTextView;
	private RadioButton manb, womenb;
	private Button submiButton, backButton;
	   private List<String> list = new ArrayList<String>();
	   private ArrayAdapter<String> adapter; 

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ʾ������
		this.setContentView(R.layout.reg);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // ���Զ���ʾ�����
		usernameText = (EditText) findViewById(R.id.uname_text);
		userpassword1 = (EditText) findViewById(R.id.password1_editText);
		userpassword2 = (EditText) findViewById(R.id.password2_editText);
		addressText = (EditText) findViewById(R.id.address_editText);
		useridText = (EditText) findViewById(R.id.uid_editText);
		manb = (RadioButton) findViewById(R.id.radio_men);
		womenb = (RadioButton) findViewById(R.id.radio_wmen);
		submiButton = (Button) findViewById(R.id.submit_button);
		backButton = (Button) findViewById(R.id.back_button);
		mySpinner=(Spinner)findViewById(R.id.spinner1);
		gradeTextView=(TextView)findViewById(R.id.age_Text);
		qqEditText=(EditText)findViewById(R.id.qqet);
		emailEditText=(EditText)findViewById(R.id.emailet);
		submiButton.setOnClickListener(new SubmitButtonOnClickListener());
		backButton.setOnClickListener(new BackButtonOnClickListener());
		 list.add("��һ");     
	        list.add("���");     
	        list.add("����");     
	        list.add("����");     
	        list.add("�о���"); 
	        list.add("����");
	        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);     
	        //��������Ϊ���������������б�����ʱ�Ĳ˵���ʽ��     
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     
	        //���Ĳ�������������ӵ������б���     
	        mySpinner.setAdapter(adapter);     
	        //���岽��Ϊ�����б����ø����¼�����Ӧ���������Ӧ�˵���ѡ��     
	        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){     
	            @SuppressWarnings("unchecked")  
	            public void onItemSelected(AdapterView arg0, View arg1, int arg2, long arg3) {     
	                // TODO Auto-generated method stub     
	                /* ����ѡmySpinner ��ֵ����myTextView ��*/    
	            	gradeTextView.setText( adapter.getItem(arg2));     
	                /* ��mySpinner ��ʾ*/    
	                arg0.setVisibility(View.VISIBLE);     
	            }     
	            @SuppressWarnings("unchecked")  
	            public void onNothingSelected(AdapterView arg0) {     
	                // TODO Auto-generated method stub     
	            	gradeTextView.setText("NONE");     
	                arg0.setVisibility(View.VISIBLE);     
	            }     
	        });     
	        
	        
	        
	        
	        /*�����˵�����������ѡ����¼�����*/    
//	        mySpinner.setOnTouchListener(new Spinner.OnTouchListener(){     
//	   
//
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					// TODO Auto-generated method stub
//					 v.setVisibility(View.INVISIBLE);     
//					return false;
//				}     
//	        });     
	        /*�����˵�����������ѡ���ı��¼�����*/    
	        mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){     
	        public void onFocusChange(View v, boolean hasFocus) {     
	        // TODO Auto-generated method stub     
	            v.setVisibility(View.VISIBLE);     
	        }     
	        });     
//	        userpassword1.addTextChangedListener(nTextWatcher);
//	        userpassword2.addTextChangedListener(mTextWatcher);
	        
	       
	        
	        
	}
//	private final TextWatcher nTextWatcher = new TextWatcher() {
//	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//	    } 
//
//	    public void onTextChanged(CharSequence s, int start, int before, int count) {
//	    } 
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			
//			
//	        if (s.length() > 0) {
//	            int pos = s.length() - 1;
//	            if(s.length()<5){
//	                Toast.makeText(RegActivity.this, "���볤������5λ��",Toast.LENGTH_SHORT).show();
//
//	            }
////	            char c = s.charAt(pos);
////	            if (c == '#') {
////	//�����������ִ����׷��#
////	                s.delete(pos,pos+1);
////	                Toast.makeText(RegActivity.this, "Error letter.",Toast.LENGTH_SHORT).show();
////	            }
//	        }
//	    }
//	};
//	private final TextWatcher mTextWatcher = new TextWatcher() {
//	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//	    } 
//
//	    public void onTextChanged(CharSequence s, int start, int before, int count) {
//	    } 
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			
//			if(!userpassword2.getText().toString().equals(userpassword1.getText().toString())){
////				userpassword2.append(text)
//				Toast.makeText(RegActivity.this,"ȷ�����벻����",Toast.LENGTH_LONG).show();
//			}
//	   
//	    }
//	};
	private boolean EmailFormat(String eMAIL1) {//�����ж�������ʽ
		Pattern pattern = Pattern
		.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher mc = pattern.matcher(eMAIL1);
		return mc.matches();
		}
	
	class SubmitButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (useridText.getText().toString()
					.equals("")) {
				Toast.makeText(RegActivity.this, "������д�˺���!", Toast.LENGTH_LONG)
				.show();
				return;
			}
			if(useridText.length()<4){
				Toast.makeText(RegActivity.this, "�˺�����4λ��!", Toast.LENGTH_LONG).show();
				return;
			}
			
			
			if (userpassword1.getText().toString()
					.equals("")) {
				Toast.makeText(RegActivity.this, "�������������ˣ�", Toast.LENGTH_LONG)
				.show();
				return;
			}
			if(userpassword1.length()<4){
				Toast.makeText(RegActivity.this, "��������4λ��!", Toast.LENGTH_LONG).show();
				return;
			}
			if (!userpassword1.getText().toString()
					.equals(userpassword2.getText().toString())) {

				Toast.makeText(RegActivity.this, "���벻һ��!", Toast.LENGTH_LONG)
						.show();

				return;
			}
			
			if(emailEditText.length()!=0){
				if(!EmailFormat(emailEditText.getText().toString())||emailEditText.length()>31){
					Toast.makeText(RegActivity.this, "�����ʽ����ȷ!", Toast.LENGTH_LONG)
					.show();
					
					return;
				}
			}
	
		
			Socket socket = null;
			try {
				socket = new Socket(Tools.IP, Tools.PORT_1);
				ObjectOutputStream oout = new ObjectOutputStream(
						socket.getOutputStream());
				ObjectInputStream oin = new ObjectInputStream(
						socket.getInputStream());

				MyMessage m1 = new MyMessage();
				Hashtable<String, Object> table = new Hashtable<String, Object>();
				table.put("userid", useridText.getText().toString());

				if (manb.isChecked()) {
					table.put("sex", "��");
				} else {

					table.put("sex", "Ů");
				}

				table.put("username", usernameText.getText().toString());
				table.put("address", addressText.getText().toString());
				table.put("userpassword", userpassword1.getText().toString());
				table.put("grade",gradeTextView.getText().toString());
				table.put("email",emailEditText.getText().toString());
				table.put("qq",qqEditText.getText().toString());
				m1.setValue(table);
				m1.setType(MyMessage.REG);
				oout.writeObject(m1);
				oout.flush();
				m1 = (MyMessage) oin.readObject();
				if (m1.getReturnValue().get("message").toString()
						.equalsIgnoreCase("ok")) {

					AlertDialog.Builder bb = new AlertDialog.Builder(
							RegActivity.this);
					bb.setTitle("��ϲ����ע��ɹ���");
					bb.setMessage("����ID�ǣ�" + m1.getReturnValue().get("userid"));
					final String userid = m1.getReturnValue().get("userid")
							.toString();

					bb.setNeutralButton("���ϵ�¼",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent in = new Intent();
									in.setClass(RegActivity.this,
											LoginActivity.class);
									in.putExtra("userid", userid);
									startActivity(in);
									RegActivity.this.finish();

								}
							});

					bb.setNegativeButton("�˳�",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									System.exit(0);

								}
							});
					bb.create().show();

				}else if (m1.getReturnValue().get("message").toString()
						.equalsIgnoreCase("havereg")) {
					Toast.makeText(
							RegActivity.this,
							"ע��ʧ�ܣ����û��ѱ�ע�ᣡ", Toast.LENGTH_LONG)
							.show();
					
				}
				else {
					Toast.makeText(
							RegActivity.this,
							"ע��ʧ��!"
									+ m1.getReturnValue().get("message").toString(), Toast.LENGTH_LONG)
							.show();
				}
				oin.close();

			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(RegActivity.this, "���粻ͨ!"+e.getMessage(), Toast.LENGTH_LONG)
						.show();

			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}

	}

	class BackButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			startActivity(new Intent(RegActivity.this, LoginActivity.class));
			finish();
		}

	}

}
