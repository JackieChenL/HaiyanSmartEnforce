package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.Entity.CaseDetailsInfo;
import com.kas.clientservice.haiyansmartenforce.Entity.SourceDetailsInfo;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;

public class DetailsEndFragment extends Fragment implements AdapterView.OnItemClickListener {
	private int page = 1;
	private int count = 5;
	private int mPage = 1;

	private DetailsCaseAdapter adapter;
	private DetailsSourceAdapter adapterSource;
	private ArrayList<CaseDetailsInfo.KsBean> arrayList;
	private ArrayList<SourceDetailsInfo.KsBean> arrayListSource;
	private ListView mListView;
	private LinearLayout mLlEndView;
	
	private int what = -1;
	private String mType = " ";
	
	private String isClose = "16";
	private RotateAnimation rotate;

	//Toast
	private Toast mToast;
	//案件分类  二级界面 案源
	private String SourceStartTime;
	private String SourceEndTime;
	private String SourceSouTypeName;
	//案件分类  二级界面 案件
	private String CaseStartTime;
	private String CaseEndTime;
	private String CaseSouTypeName;
	//业务决策 总体三级界面
//	private String DecisionToEmployeeID;
	private String DecisionToActID;
	private String DecisionToStartTime;
	private String DecisionToEndTime;
	private String DecisionToDepName;
	//
	private String EmployeeID;
	private String actID;
	private String startTime;
	private String endTime;
	private String depName = "";
	//部门案件 二级界面
	private String DeDepartmentID;// 部门ID
	private String DefirstLevelID;// 违法大类id
	private String DeType;//案件类型
	private String DeStartTime;
	private String DeEndTime;
	//private String DeDepName;

	//个人工作 二级界面
	private String PeType;
	private String PeEmployeeID;
	private String PeStartTime;
	private String PeEndTime;
	String TAG = "chenlong";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mine_details_end_fragment, null);
		mLlEndView = (LinearLayout)view.findViewById(R.id.end_details_ll);
		mListView = (ListView) view.findViewById(R.id.end_details_lv);
		mListView.setOnItemClickListener(this);
		getData();
		return view;
	}
	/**
	 * 旋转动画
	 */
	private void getData(){
		Intent intent = getActivity().getIntent();
		what = intent.getIntExtra("what", 0);
        Log.i(TAG, "getData: what = "+what);
//		if(what == Constants.WhatDecisionCaseDetailsCase){
//			mType = "Case";
//			CaseStartTime = intent.getStringExtra("StartTime");
//			CaseEndTime = intent.getStringExtra("EndTime");
//			CaseSouTypeName = intent.getStringExtra("souTypeName");
//		}else if(what == Constants.WhatDecisionCaseDetailsSource){
//			mType = "Case";
//			SourceStartTime = intent.getStringExtra("StartTime");
//			SourceEndTime = intent.getStringExtra("EndTime");
//			SourceSouTypeName = intent.getStringExtra("souTypeName");
//		}eelse
		if(what == Constants.WhatDepartmentDetailsCase){
			mType = "Case";
			DeType = intent.getStringExtra("type");
			DeDepartmentID = intent.getStringExtra("DeDepartmentID");
			DefirstLevelID = intent.getStringExtra("firstLevelID");
			//DeDepName = intent.getStringExtra("bigName");
			DeStartTime = intent.getStringExtra("StartTime");
			DeEndTime = intent.getStringExtra("EndTime");
		}else if(what == Constants.WhatDepartmentDetailsSource){
			mType = "Source";
			DeType = intent.getStringExtra("type");
			DeDepartmentID = intent.getStringExtra("DeDepartmentID");
			DefirstLevelID = intent.getStringExtra("firstLevelID");
			//DeDepName = intent.getStringExtra("bigName");
			DeStartTime = intent.getStringExtra("StartTime");
			DeEndTime = intent.getStringExtra("EndTime");
		}else if(what == Constants.WhatDecisionAreaDetailsPersonage){
			mType = "Case";
			EmployeeID = intent.getStringExtra("EmployeeID");
			actID = intent.getStringExtra("actID");
			startTime = intent.getStringExtra("StartTime");
			endTime = intent.getStringExtra("EndTime");
			depName = intent.getStringExtra("depName");
		}
		else if(what == Constants.WhatDecisionTotailsDetailsPersonage){
			mType = "Case";
			DecisionToDepName = intent.getStringExtra("depName");
			DecisionToActID = intent.getStringExtra("actID");
			DecisionToEndTime = intent.getStringExtra("EndTime");
			DecisionToStartTime = intent.getStringExtra("StartTime");
		}
//		else if(what == Constants.WhatPersonageDetailsCase){
//			mType = "Case";
//			PeType = intent.getStringExtra("type");
//			PeEmployeeID = intent.getStringExtra("EmployeeID");
//			PeStartTime = intent.getStringExtra("StartTime");
//			PeEndTime = intent.getStringExtra("EndTime");
//		}else if(what == Constants.WhatPersonageDetailsSource){
//			mType = "Source";
//			PeType = intent.getStringExtra("type");
//			PeEmployeeID = intent.getStringExtra("EmployeeID");
//			PeStartTime = intent.getStringExtra("StartTime");
//			PeEndTime = intent.getStringExtra("EndTime");
//		}else{
//			return;
//		}
//		model = new ToolModel(getActivity());
		requestDate();
	}
	/**
	 * 数据请求
	 */
	private void requestDate(){
//		if(what == Constants.WhatDecisionCaseDetailsCase){
//			model.mProcSouTypeCaseStatisticsDetails(getUserId(),CaseStartTime,CaseEndTime,CaseSouTypeName,
//					isClose,page,count,new getDetailsEnd());
//		}else if(what == Constants.WhatDecisionCaseDetailsSource){
//			model.ProcSouTypeCaseStatisticsDetails(getUserId(),SourceStartTime,SourceEndTime,SourceSouTypeName,
//					isClose,page,count,new getDetailsEnd());
//		}else
		if(what == Constants.WhatDepartmentDetailsCase){
            arrayList = new ArrayList<>();
            adapter = new DetailsCaseAdapter(getActivity(),arrayList);
            mListView.setAdapter(adapter);
//			model.DepartmentDetailsCase(getUserId(), DeDepartmentID, DefirstLevelID, DeType, isClose,
//					DeStartTime, DeEndTime, page, count,new getDetailsEnd());
            loadDepartmentCase(getUserId(), DeDepartmentID, DefirstLevelID, DeType, isClose,
                    DeStartTime, DeEndTime, page, count);
		}else if(what == Constants.WhatDepartmentDetailsSource){
//			model.DepartmentDetailsSource(getUserId(), DeDepartmentID, DefirstLevelID, DeType, isClose,
//					DeStartTime, DeEndTime, page, count,new getDetailsEnd());
            arrayListSource = new ArrayList<>();
            adapterSource = new DetailsSourceAdapter(getActivity(),arrayListSource);

            mListView.setAdapter(adapterSource);
            loadDepartmentSource(getUserId(), DeDepartmentID, DefirstLevelID, DeType, isClose,
                    DeStartTime, DeEndTime, page, count);
		}
		else if(what == Constants.WhatDecisionAreaDetailsPersonage){
			arrayList = new ArrayList<>();
			adapter = new DetailsCaseAdapter(getActivity(),arrayList);
			mListView.setAdapter(adapter);
			DecisionAreaDetails(getUserId(), EmployeeID, actID,depName, isClose,
					startTime, endTime);
		}
		else if(what == Constants.WhatDecisionTotailsDetailsPersonage){
			arrayList = new ArrayList<>();
			adapter = new DetailsCaseAdapter(getActivity(),arrayList);
			mListView.setAdapter(adapter);
			DecisionTotality(getUserId(), DecisionToDepName, DecisionToActID,
					isClose, DecisionToStartTime, DecisionToEndTime);
		}
//        else if(what == Constants.WhatPersonageDetailsCase){
//			model.memberWorkCase(getUserId(), PeEmployeeID,PeType, isClose,
//					PeStartTime, PeEndTime, page, count,new getDetailsEnd());
//		}else if(what == Constants.WhatPersonageDetailsSource){
//			model.memberWorkSource(getUserId(), PeEmployeeID,PeType, isClose,
//					PeStartTime, PeEndTime, page, count,new getDetailsEnd());
//		}
	}
	public void DecisionTotality(String UserId,String depName,String actID,String CaseStatusID,
								 String startTime, String endTime
	) {
		Log.i(TAG,"业务决策--总体分析--第三级界面:"+"  "+"UserId:"+UserId
				+"depName："+depName
				+"actID："+actID
				+"CaseStatusID："+CaseStatusID
				+"startTime："+startTime
				+"endTime："+endTime);
		OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/GetDepartmentActionCaseStatisticList.ashx")
//				.addParams("useid", UserId+"")
				.addParams("depName", depName)
				.addParams("actID", actID+"")
				.addParams("CaseStatusID", CaseStatusID)
				.addParams("startTime", startTime)
				.addParams("endTime", endTime)
//				.addParams("Page", 1+"")
//				.addParams("Count", 10+"")
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: "+e.toString());

			}

			@Override
			public void onResponse(String response, int id) {
				Log.i(TAG, "onResponse: " + response);

				CaseDetailsInfo sourceDetailsInfo = new Gson().fromJson(response, CaseDetailsInfo.class);
				if (sourceDetailsInfo.getKS() != null && sourceDetailsInfo.getKS().size() > 0) {
					arrayList.addAll(sourceDetailsInfo.getKS());
					adapter.notifyDataSetChanged();
				} else {
					ToastUtils.showToast(getActivity(), "暂无数据");
				}
			}
		});
	}
	/**
	 * 业务决策--区域分析--第三级界面
	 */
	public void DecisionAreaDetails(String UserId,String EmployeeID,String actID,String depName,String CaseStatusID,
										   String startTime, String endTime
										   ) {
		Log.e("test","业务决策--区域分析--第三级界面:"+"  "+"useid:"+UserId
				+"EmployeeID"+EmployeeID
				+"actID："+actID
				+"depName："+depName
				+"CaseStatusID："+CaseStatusID
				+"startTime："+startTime
				+"endTime："+endTime);
		OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/GetDepartmentActionCaseStatisticList.ashx")
				.addParams("useid", UserId+"")
				.addParams("EmployeeID", EmployeeID)
				.addParams("actID", actID+"")
				.addParams("depName", depName)
				.addParams("CaseStatusID", CaseStatusID)
				.addParams("startTime", startTime)
				.addParams("endTime", endTime)
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: "+e.toString());
			}

			@Override
			public void onResponse(String response, int id) {
				Log.i(TAG, "loadDepartmentCase_onResponse: "+response);
				CaseDetailsInfo sourceDetailsInfo = new Gson().fromJson(response, CaseDetailsInfo.class);
				if (sourceDetailsInfo.getKS() != null && sourceDetailsInfo.getKS().size() > 0) {
					arrayList.addAll(sourceDetailsInfo.getKS());
					adapter.notifyDataSetChanged();
				} else {
					ToastUtils.showToast(getActivity(), "暂无数据");
				}
			}
		});
	}
    private void loadDepartmentSource(String UserId, String DepartmentID, String firstLevelID, String type, String CaseStatusID,
                                      String startTime, String endTime,
                                      int Page, int Count) {
        HashMap params = new HashMap();
        params.put("useid", UserId);
        params.put("DepartmentID", DepartmentID);
        params.put("firstLevelID", firstLevelID);
        params.put("type", type);
        params.put("CaseStatusID", CaseStatusID);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
//        params.put("Page", Page+"");
//        params.put("Count", Count+"");
        Log.i(TAG, "loadDepartmentSource: "+ new Gson().toJson(params));
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/GetDepartmentSourceStatisticsList.ashx")
                .params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "loadDepartmentSource_onResponse: "+response);
				SourceDetailsInfo sourceDetailsInfo = new Gson().fromJson(response, SourceDetailsInfo.class);
				if (sourceDetailsInfo.getKS() != null && sourceDetailsInfo.getKS().size() > 0) {

					arrayListSource.addAll(sourceDetailsInfo.getKS());
					adapterSource.notifyDataSetChanged();
				} else {
					ToastUtils.showToast(getActivity(), "暂无数据");
				}
            }
        });

    }

    private void loadDepartmentCase(String UserId,String DepartmentID,String firstLevelID,String type,String CaseStatusID,
                                    String startTime, String endTime,
                                    int Page,int Count) {
        HashMap params = new HashMap();
        params.put("useid", UserId);
        params.put("DepartmentID", DepartmentID);
        params.put("firstLevelID", firstLevelID);
        params.put("depName", depName);
        params.put("type", type);
        params.put("CaseStatusID", CaseStatusID);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
//        params.put("Page", Page+"");
//        params.put("Count", Count+"");
        Log.i(TAG, "loadDepartmentCase: "+ new Gson().toJson(params));
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/GetDepartmentCaseStatisticsList.ashx")
                .params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
//                ToastUtils.showToast();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "loadDepartmentCase_onResponse: "+response);
				CaseDetailsInfo sourceDetailsInfo = new Gson().fromJson(response, CaseDetailsInfo.class);
				if (sourceDetailsInfo.getKS() != null && sourceDetailsInfo.getKS().size() > 0) {
					arrayList.addAll(sourceDetailsInfo.getKS());
					adapter.notifyDataSetChanged();
				} else {
					ToastUtils.showToast(getActivity(), "暂无数据");
				}
            }
        });
    }
	private String getUserId(){
		return UserSingleton.USERINFO.getName().UserID;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(mType.equals("Case")){
			Intent intent = new Intent();
			intent.putExtra("CaseId", Integer.parseInt(arrayList.get(arg2).getCaseID()));
			intent.setClass(getActivity(),CaseDetailsActivity.class);
			startActivity(intent);
		}else if(mType.equals("Source")){
			//Intent intent = new Intent();
			//intent.putExtra("CaseId", Integer.parseInt(arrayListSource.get(arg2).getSourceID()));
			//intent.setClass(getActivity(),CaseDetailsActivity.class);
			//startActivity(intent);
		}
	}
}