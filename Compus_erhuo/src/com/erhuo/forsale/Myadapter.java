package com.erhuo.forsale;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.erhuo.main.R;

import android.R.integer;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Myadapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Map<String, Object>> list;
	private int layoutID;
	private String flag[];
	private int ItemIDs[];
	int ttype;
File file=null;
ArrayList arrayList;
	public Myadapter(Context context, List<Map<String, Object>> list,
			int layoutID, String flag[], int ItemIDs[], int type) {
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		this.layoutID = layoutID;
		this.flag = flag;
		this.ItemIDs = ItemIDs;
		ttype = type;
		
	}
//	
//	Myadapter listAdapter = new Myadapter(this, this.list,
//			R.layout.message_show, new String[]{
//					"message_neirong","message_time","imageView" }, new int[] {
//					R.id.message_neirong, R.id.message_time ,R.id.read}, 6,Liststate);
	public Myadapter(Context context, List<Map<String, Object>> list,
			int layoutID, String flag[], int ItemIDs[], int type,ArrayList al) {
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		this.layoutID = layoutID;
		this.flag = flag;
		this.ItemIDs = ItemIDs;
		ttype = type;
		arrayList=al;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (ttype == 1) {
			convertView = mInflater.inflate(R.layout.forsale_show_manage, null);

			for (int i = 0; i < flag.length; i++) {// 备注1

				if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {
					TextView tv = (TextView) convertView
							.findViewById(ItemIDs[i]);
					tv.setText((String) list.get(position).get(flag[i]));

				}
			}
		}else if(ttype==2) {
			convertView = mInflater.inflate(R.layout.forsale_show, null);

			for (int i = 0; i < flag.length; i++) {// 备注1

				if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {
					TextView tv = (TextView) convertView
							.findViewById(ItemIDs[i]);
					tv.setText((String) list.get(position).get(flag[i]));

				}
			}
		} else if(ttype==3) {
	        	convertView = mInflater.inflate(R.layout.grid_layout, null);  
		        for (int i = 0; i < flag.length; i++) {//备注1  
		            if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {  
		            	
		                ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);  
		               file=new File(""+list.get(position).get(  
		                        flag[i]));
		                iv.setImageURI(Uri.fromFile((file)));
		                
		                
//		                iv.setBackgroundResource(	 );  
		            } else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {  
		                TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);  
		                tv.setText((String) list.get(position).get(flag[i]));  
		            }
			}

			}else if(ttype==4){
				convertView = mInflater.inflate(R.layout.selling, null);  
		        for (int i = 0; i < flag.length; i++) {//备注1  
		            if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {  
		            	
		            	
		                ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);  
		                
		                
		               file=new File(""+list.get(position).get(  
		                        flag[i]));
		                iv.setImageURI(Uri.fromFile((file)));
		            
		            } else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {  
		                TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);  
		                tv.setText((String) list.get(position).get(flag[i]));  
		            }
			}

			
		        

			}else if(ttype==5){

				convertView = mInflater.inflate(R.layout.forsale_show, null);  
		        for (int i = 0; i < flag.length; i++) {//备注1  
		            if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {  
		                ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);  
		                File file=new File(""+list.get(position).get(  
		                        flag[i]));
		                iv.setImageURI(Uri.fromFile((file)));
		              
		            } else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {  
		                TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);  
		                tv.setText((String) list.get(position).get(flag[i]));  
		            }
			}

			
			}else if(ttype==6){
				convertView = mInflater.inflate(R.layout.message_show, null);  
		        for (int i = 0; i < flag.length; i++) {//备注1  
		            if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {  
		            	
		            	
		                ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);  
//		 		       ImageView imageView=(ImageView) convertView.findViewById(R.id.read);
		           
		    		        if(((String) list.get(position).get(flag[i])).equals("0")){
		    		    	//	Log.e("1", "" + (Integer)arrayList.get(i));
		    		    		iv.setImageResource(R.drawable.readnot);
//		    		        	iv.setBackgroundResource(R.drawable.readnot);
		    		        }else{
		    		        //	Log.e("1", "" + (Integer)arrayList.get(i));	
		    		        	 iv.setImageBitmap(null);
//								 iv.setBackgroundResource(null);
//								 iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.readnot));
		    		        	 iv.setImageResource(R.drawable.readed);

		    		        }
		    				
		                
//		               file=new File(""+list.get(position).get(  
//		                        flag[i]));
//		                iv.setImageURI(Uri.fromFile((file)));
		            
		            } else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {  
		        		
		            	
		            	
		            	
		                TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);  
		                tv.setText((String) list.get(position).get(flag[i]));  
		            }
			}
		
		        

			
				
			}
		return convertView;

	}
}
