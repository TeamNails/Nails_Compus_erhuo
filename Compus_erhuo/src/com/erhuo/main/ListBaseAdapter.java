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
	private Context context = null;					// Context对象
	private int[] picIds = null;	// 保存所有图片资源
	private String[] thingname;
	private String[] thingprice;

	public ListBaseAdapter(Context context, int[] picIds,String[] thingname,String[] thingprice) {
		this.context = context;						// 接收Context
		this.picIds = picIds;						// 保存图片资源
		this.thingname=thingname;
		this.thingprice=thingprice;
	}

	@Override
	public int getCount() {							// 取得个数
		return this.picIds.length;
	}

	@Override
	public Object getItem(int position) {			// 取得每一项的信息
		return this.picIds[position];
	}

	@Override
	public long getItemId(int position) {			// 取得指定项的ID
		return this.picIds[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.data_list, null);
		ImageView img = (ImageView) view.findViewById(R.id.pic);// 定义图片视图
		TextView host_thingname=(TextView) view.findViewById(R.id.thingname);
		TextView host_thingprice=(TextView) view.findViewById(R.id.thingprice);
		host_thingname.setText(this.thingname[position]);
		host_thingprice.setText(this.thingprice[position]);
		img.setImageResource(this.picIds[position]); 	// 给ImageView设置资源
		img.setScaleType(ImageView.ScaleType.CENTER); 	// 居中显示
		return view;
	}

}
