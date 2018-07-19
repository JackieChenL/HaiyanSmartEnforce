package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.Area_Item;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

public class Area_Adapter_Item extends BaseAdapter {
	private Context context;
	private List<Area_Item> list;
	private String start;
	private String end;
	private String name;
	
	public Area_Adapter_Item(Context context,List<Area_Item> list,String start,String end,String name){
		this.context=context;
		this.list=list;
		this.start=start;
		this.end=end;
		this.name=name;
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
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHoder hoder;
		if (view == null) {
			hoder = new ViewHoder();
			view=View.inflate(context, R.layout.area_list_item_item, null);
			hoder.ll_area_item=(LinearLayout) view.findViewById(R.id.ll_area_item);
			hoder.tv_area_actName=(TextView) view.findViewById(R.id.tv_area_actName);
			hoder.tv_area_caseNum=(TextView) view.findViewById(R.id.tv_area_caseNum);
			view.setTag(hoder);
		} else {
			hoder = (ViewHoder) view.getTag();
		}
		hoder.tv_area_actName.setText(list.get(position).getActName());
		hoder.tv_area_caseNum.setText(list.get(position).getCaseNum()+"");
		hoder.ll_area_item.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				int depID = list.get(position).getDepID();
//				int actID = list.get(position).getActID();
//				Intent intent=new Intent(context, Area_Item.class);
//				intent.putExtra("depID", depID);
//				intent.putExtra("actID", actID);
//				intent.putExtra("start", start);
//				intent.putExtra("end", end);
//				intent.putExtra("name", name);
//				context.startActivity(intent);
			}
		});
		
		return view;
	}

	class ViewHoder {
		TextView tv_area_actName;
		TextView tv_area_caseNum;
		LinearLayout ll_area_item;
	}

}