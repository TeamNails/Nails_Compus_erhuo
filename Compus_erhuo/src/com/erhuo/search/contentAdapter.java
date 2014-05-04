package com.erhuo.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.erhuo.main.R;



public class contentAdapter extends BaseAdapter implements Filterable {
	private ArrayFilter mFilter;
	private List<Content> mList;
	private Context context;
	private ArrayList<Content> mUnfilteredData;
	
	public contentAdapter(List<Content> mList, Context context) {
		this.mList = mList;
		this.context = context;
	}

	@Override
	public int getCount() {
		
		return mList==null ? 0:mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder holder;
		if(convertView==null){
			view = View.inflate(context, R.layout.popup, null);
			
			holder = new ViewHolder();
			holder.content = (TextView) view.findViewById(R.id.mQQ);
			holder.imageButton = (ImageButton) view.findViewById(R.id.mQQDelete);
			
			
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		holder.content.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
//				pop.dismiss();
//				mAccountsEditText.setText(account[position].toString());
//				mPassEditText.setText(list.get(account[position]));
				
				return true;
			}
		});
		final Content pc = mList.get(position);
		
		holder.content.setText(" "+pc.getContent());
		
		
		
		
		holder.imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				pc[position].toString();
				mList.remove(pc.getContent());
//				adapter.notifyDataSetChanged();
				
			}
		});
		
		
		
		
		
		return view;
	}
	
	static class ViewHolder{
		public ImageButton imageButton;
		public TextView content;
//		public TextView tv_email;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}

	private class ArrayFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<Content>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<Content> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<Content> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<Content> newValues = new ArrayList<Content>(count);

                for (int i = 0; i < count; i++) {
                	Content pc = unfilteredValues.get(i);
                    if (pc != null) {
                        
                    	if(pc.getContent()!=null){
                    		
                    		newValues.add(pc);
                    	
                    
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

       
		}
			return results;
            }



		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			 //noinspection unchecked
            mList = (List<Content>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
		}
}
	}
