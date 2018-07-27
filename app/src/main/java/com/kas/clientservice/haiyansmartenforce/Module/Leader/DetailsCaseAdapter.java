package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.CaseDetailsInfo;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.ArrayList;


public class DetailsCaseAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<CaseDetailsInfo.KsBean> mArrayList;
	public DetailsCaseAdapter(Context mContext,ArrayList<CaseDetailsInfo.KsBean> mArrayList){
		this.mContext = mContext;
		this.mArrayList = mArrayList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public void setUpDate(ArrayList<CaseDetailsInfo.KsBean> mArrayList){
		this.mArrayList = mArrayList;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		takeView take;
		CaseDetailsInfo.KsBean details = mArrayList.get(arg0);
		if(arg1 == null){
			take = new takeView();
			//arg1 = View.inflate(mContext,R.layout.sources_classify_details_item,null);
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.mine_viewpage_case_item, arg2, false);
			take.mCaseClassifyItemTvCompanyName = (TextView)arg1.findViewById(R.id.mCaseClassifyItemTvCompanyName);
			take.mCaseClassifyItemTvNumber = (TextView)arg1.findViewById(R.id.mCaseClassifyItemTvNumber);
			take.mCaseClassifyItemTvState = (TextView)arg1.findViewById(R.id.mCaseClassifyItemTvState);
			take.mCaseClassifyItemTvNowLink = (TextView)arg1.findViewById(R.id.mCaseClassifyItemTvNowLink);
			take.mCaseClassifyItemTvNnlawfulAct = (TextView)arg1.findViewById(R.id.mCaseClassifyItemTvNnlawfulAct);
			take.mCaseClassifyItemTvTime = (TextView)arg1.findViewById(R.id.mCaseClassifyItemTvTime);
			take.mCaseClassifyItemTvAddress = (TextView)arg1.findViewById(R.id.mCaseClassifyItemTvAddress);
			take.mCaseClassifyItemTvPunish = (TextView)arg1.findViewById(R.id.mCaseClassifyItemTvPunish);
			take.mCaseClassifyItemLlPunish = (LinearLayout)arg1.findViewById(R.id.mCaseClassifyItemLlPunish);
			arg1.setTag(take);
		}else{
			take = (takeView)arg1.getTag();
		}
		take.mCaseClassifyItemTvCompanyName.setText(details.getNameECCas());
		take.mCaseClassifyItemTvNumber.setText(details.getNumberCas());
		take.mCaseClassifyItemTvState.setText(details.getState());
		take.mCaseClassifyItemTvNowLink.setText(details.getNameCaF());
		take.mCaseClassifyItemTvNnlawfulAct.setText(details.getActCas());
		take.mCaseClassifyItemTvTime.setText(details.getAcceptTimeCas());
		take.mCaseClassifyItemTvAddress.setText(details.getCaseAddressCas());
		if(details.getPunishCas()!=null&&!details.getPunishCas().equals("")){
			take.mCaseClassifyItemLlPunish.setVisibility(View.VISIBLE);
			take.mCaseClassifyItemTvPunish.setText(details.getPunishCas());
		}
		return arg1;
	}
	class takeView{
		TextView mCaseClassifyItemTvCompanyName;
		TextView mCaseClassifyItemTvNumber;
		TextView mCaseClassifyItemTvState;
		TextView mCaseClassifyItemTvNowLink;
		TextView mCaseClassifyItemTvNnlawfulAct;
		TextView mCaseClassifyItemTvTime;
		TextView mCaseClassifyItemTvAddress;
		//处罚
		LinearLayout mCaseClassifyItemLlPunish;
		TextView mCaseClassifyItemTvPunish;
	}
}
