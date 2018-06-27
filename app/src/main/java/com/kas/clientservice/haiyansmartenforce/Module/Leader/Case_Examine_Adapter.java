package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.LeaderSearchListEntity;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;



public class Case_Examine_Adapter extends BaseAdapter {
	private Context context;
	private List<LeaderSearchListEntity.KSBean> list;
	public Case_Examine_Adapter(Context context,List<LeaderSearchListEntity.KSBean> list){
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
			view=View.inflate(context, R.layout.case_examine_adapter, null);
			hoder.tv_examine_adapter_name=(TextView) view.findViewById(R.id.tv_examine_adapter_name);
			hoder.tv_examine_adapter_code=(TextView) view.findViewById(R.id.tv_examine_adapter_code);
			hoder.tv_examine_adapter_time=(TextView) view.findViewById(R.id.tv_examine_adapter_time);
			hoder.tv_examine_adapter_link=(TextView) view.findViewById(R.id.tv_examine_adapter_link);
			view.setTag(hoder);
		} else {
			hoder = (ViewHoder) view.getTag();
		}
		hoder.tv_examine_adapter_name.setText(list.get(position).getName());
		hoder.tv_examine_adapter_code.setText(list.get(position).getNumber());
		hoder.tv_examine_adapter_time.setText(list.get(position).getRegTime());
		hoder.tv_examine_adapter_link.setText(list.get(position).getNameCaF());
		return view;
	}

	class ViewHoder {
		TextView tv_examine_adapter_name;
		TextView tv_examine_adapter_code;
		TextView tv_examine_adapter_time;
		TextView tv_examine_adapter_link;
	}

}
