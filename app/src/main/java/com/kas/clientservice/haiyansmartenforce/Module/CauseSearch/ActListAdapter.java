package com.kas.clientservice.haiyansmartenforce.Module.CauseSearch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.ActionList;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.List;


/**
  * @类名：ShopAdapter.java
  * @功能描述: 案由查询列表显示适配器
 */
public class ActListAdapter extends BaseAdapter {

	private Context context;
	private List<ActionList> list;
	
	public ActListAdapter(Context context,List<ActionList> list){
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
			view=View.inflate(context, R.layout.act_list_item, null);
			hoder.tv_act_item_name=(TextView) view.findViewById(R.id.tv_act_item_name);
			hoder.tv_act_item_code=(TextView) view.findViewById(R.id.tv_act_item_code);
			view.setTag(hoder);
		} else {
			hoder = (ViewHoder) view.getTag();
		}
		hoder.tv_act_item_name.setText(list.get(position).getActCaA()+"");
		hoder.tv_act_item_code.setText("违法代码："+list.get(position).getCodeCaA()+"");
		hoder.tv_act_item_code.setVisibility(View.GONE);
		
		return view;
	}

	class ViewHoder {
		TextView tv_act_item_name;
		TextView tv_act_item_code;
	}

}
