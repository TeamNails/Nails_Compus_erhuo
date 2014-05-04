package com.erhuo.main;

import com.erhuo.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListBaseAdapter extends BaseAdapter{
	private Context context = null;					// Context����
	private int[] picIds = null;	// ��������ͼƬ��Դ
	private String[] thingname;
	private String[] thingprice;

	public ListBaseAdapter(Context context, int[] picIds,String[] thingname,String[] thingprice) {
		this.context = context;						// ����Context
		this.picIds = picIds;						// ����ͼƬ��Դ
		this.thingname=thingname;
		this.thingprice=thingprice;
	}

	@Override
	public int getCount() {							// ȡ�ø���
		return this.picIds.length;
	}

	@Override
	public Object getItem(int position) {			// ȡ��ÿһ�����Ϣ
		return this.picIds[position];
	}

	@Override
	public long getItemId(int position) {			// ȡ��ָ�����ID
		return this.picIds[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.data_list, null);
		ImageView img = (ImageView) view.findViewById(R.id.pic);// ����ͼƬ��ͼ
		TextView host_thingname=(TextView) view.findViewById(R.id.thingname);
		TextView host_thingprice=(TextView) view.findViewById(R.id.thingprice);
		host_thingname.setText(this.thingname[position]);
		host_thingprice.setText(this.thingprice[position]);
		img.setImageResource(this.picIds[position]); 	// ��ImageView������Դ
		img.setScaleType(ImageView.ScaleType.CENTER); 	// ������ʾ
		return view;
	}

}
