package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.CaseDatailsWrit;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.ArrayList;


public class CaseDetailsWritAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<CaseDatailsWrit.KSBean> arrayList;
	public CaseDetailsWritAdapter(Context mContext,ArrayList<CaseDatailsWrit.KSBean> arrayList){
		this.mContext = mContext;
		this.arrayList = arrayList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TakeView takeView;
		CaseDatailsWrit.KSBean writ= arrayList.get(arg0);
		if(arg1 == null){
			takeView = new TakeView();
			arg1 = LinearLayout.inflate(mContext, R.layout.mine_details_writ_item,null);
			takeView.mLayout = (LinearLayout)arg1.findViewById(R.id.details_writ_item);
			takeView.mTvType = (TextView)arg1.findViewById(R.id.details_writ_item_type);
			takeView.mTvReference = (TextView)arg1.findViewById(R.id.details_writ_item_reference);
			takeView.mTvTime = (TextView)arg1.findViewById(R.id.details_writ_item_time);
			arg1.setTag(takeView);
		}else{
			takeView = (TakeView)arg1.getTag();
		}
		if(writ.getType().equals("立案审批")){
			takeView.mLayout.setBackgroundResource(R.drawable.border_writ_item_l);
		}
		takeView.mTvType.setText(writ.getType());
		takeView.mTvReference.setText(writ.getReferenceNumber());
		takeView.mTvTime.setText(writ.getHandTime());
		return arg1;
	}
	class TakeView{
		LinearLayout mLayout;
		TextView mTvType;
		TextView mTvReference;
		TextView mTvTime;
	}
}
