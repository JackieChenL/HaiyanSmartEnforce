package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Entity.ExamineHistoryDetailsCaseFlow;
import com.kas.clientservice.haiyansmartenforce.R;

import java.util.ArrayList;

public class ExamineHistoryDetailsCaseFolowAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<ExamineHistoryDetailsCaseFlow.KSBean> caseFlows;
	public ExamineHistoryDetailsCaseFolowAdapter(Context mContext,ArrayList<ExamineHistoryDetailsCaseFlow.KSBean> caseFlows){
		this.caseFlows = caseFlows;
		this.mContext = mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return caseFlows.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return caseFlows.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Take take;
		ExamineHistoryDetailsCaseFlow.KSBean mCaseFlows = caseFlows.get(arg0);
		if(arg1 == null){
			take = new Take();
			//arg1 = LayoutInflater.from(mContext).inflate(R.layout.examine_history_details_caseflow_item, arg2, false);
			arg1=View.inflate(mContext, R.layout.mine_details_caseflow_item, null);
			//arg1 = LayoutInflater.from(mContext).inflate(R.layout.examine_history_details_caseflow_item,null);
			take.mNameCaF = (TextView)arg1.findViewById(R.id.examine_history_details_caseflow_item_caf);
			take.mNameEmp = (TextView)arg1.findViewById(R.id.examine_history_details_caseflow_item_emp);
			take.mSuggestCaS = (TextView)arg1.findViewById(R.id.examine_history_details_caseflow_item_suggestcas);
			arg1.setTag(take);
		}else{
			take = (Take) arg1.getTag();
		}
		take.mNameCaF.setText(mCaseFlows.getNameCaF());
		take.mNameEmp.setText(mCaseFlows.getNameEmp());
		take.mSuggestCaS.setText(mCaseFlows.getSuggestCaS());
		return arg1;
	}
	class Take{
		TextView mNameCaF;
		TextView mNameEmp;
		TextView mSuggestCaS;
	}
}