package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.Area_Item;
import com.kas.clientservice.haiyansmartenforce.Entity.Vp_Area;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Wedge.MyListView;

import java.util.List;


public class Area_Adapter extends BaseAdapter {
	private Context context;
	private List<Vp_Area> list;
	private String start;
	private String end;
	public Area_Adapter(Context context,List<Vp_Area> list,String start,String end){
		this.context=context;
		this.list=list;
		this.start=start;
		this.end=end;
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
		final ViewHoder hoder; 
		if (view == null) {
			hoder = new ViewHoder();
			view=View.inflate(context, R.layout.area_list_item, null);
			hoder.tv_area_name=(TextView) view.findViewById(R.id.tv_area_name);
			hoder.tv_area_num=(MyListView) view.findViewById(R.id.tv_area_num);
			view.setTag(hoder);
		} else {
			hoder = (ViewHoder) view.getTag();
		}
		
		hoder.tv_area_name.setText(list.get(position).getDepName());
		String name = hoder.tv_area_name.getText().toString();
		
		List<Area_Item> list2 = list.get(position).getAreas();
		Area_Adapter_Item adapter_Item=new Area_Adapter_Item(context, list2,start,end,name);
		hoder.tv_area_num.setAdapter(adapter_Item);
//		ListViewFitParent.setListViewHeightBasedOnChildren(hoder.tv_area_num);
		return view;
	}

	class ViewHoder {
		TextView tv_area_name;
		MyListView tv_area_num;
	}

}
