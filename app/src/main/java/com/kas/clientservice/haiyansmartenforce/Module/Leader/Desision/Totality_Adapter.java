package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.Vp_Totality;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;

public class Totality_Adapter extends BaseAdapter {
	private Context context;
	private List<Vp_Totality> list;
	public Totality_Adapter(Context context,List<Vp_Totality> list){
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
			view=View.inflate(context, R.layout.totality_list_item, null);
			hoder.tv_totality_name=(TextView) view.findViewById(R.id.tv_totality_name);
			hoder.tv_totality_num=(TextView) view.findViewById(R.id.tv_totality_num);
			view.setTag(hoder);
		} else {
			hoder = (ViewHoder) view.getTag();
		}
		hoder.tv_totality_name.setText(list.get(position).getActName());
		hoder.tv_totality_num.setText(list.get(position).getCaseNum()+"");
		return view;
	}

	class ViewHoder {
		TextView tv_totality_name;
		TextView tv_totality_num;
	}

}