package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.Vp_Area_Item;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

public class Area_Item_Adapter extends BaseAdapter {
	private Context context;
	private List<Vp_Area_Item> list;
	public Area_Item_Adapter(Context context,List<Vp_Area_Item> list){
		this.context=context;
		this.list=list;
		
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHoder hoder;
		if (view == null) {
			hoder = new ViewHoder();
			view=View.inflate(context, R.layout.area_list_item_adapter, null);
			hoder.tv_area_item_name=(TextView) view.findViewById(R.id.tv_area_item_name);
			hoder.tv_area_item_allnum=(TextView) view.findViewById(R.id.tv_area_item_allnum);
			hoder.tv_area_item_num=(TextView) view.findViewById(R.id.tv_area_item_num);
			view.setTag(hoder);
		} else {
			hoder = (ViewHoder) view.getTag();
		}
		hoder.tv_area_item_name.setText(list.get(position).getEmpName());
		hoder.tv_area_item_allnum.setText(list.get(position).getAllNum()+"");
		hoder.tv_area_item_num.setText(list.get(position).getEndNum()+"");
		return view;
	}

	class ViewHoder {
		TextView tv_area_item_name;
		TextView tv_area_item_allnum;
		TextView tv_area_item_num;
	}

}
