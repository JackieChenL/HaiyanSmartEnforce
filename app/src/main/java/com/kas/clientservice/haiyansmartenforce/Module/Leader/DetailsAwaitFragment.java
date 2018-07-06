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

import static android.content.ContentValues.TAG;

public class DetailsAwaitFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private int page = 1;
    private int mPage = 1;
    private int count = 5;

    private DetailsCaseAdapter adapter;
    private DetailsSourceAdapter adapterSource;
    private ArrayList<CaseDetailsInfo.KsBean> arrayList;
    private ArrayList<SourceDetailsInfo.KsBean> arrayListSource;
    private LinearLayout mLlEndView;

    private int what = -1;
    private String mType = " ";
    //Toast
    private Toast mToast;
    //结案状态 “”为待结案 16为结案
    private String isClose = "";
    //旋转动画
    private RotateAnimation rotate;
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
    //业务决策 区域  三级界面
    private String DecisionAreaEmployeeID;
    private String DecisionAreaactID;
    private String DecisionAreastartTime;
    private String DecisionAreaendTime;
    private String DecisionAreadepName;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.mine_details_await_fragment, null);
        mLlEndView = (LinearLayout) view.findViewById(R.id.details_await_ll);
        mListView = (ListView) view.findViewById(R.id.details_await_lv);
        mListView.setOnItemClickListener(this);
        getData();
        return view;
    }

    private void getData() {
        Intent intent = getActivity().getIntent();
        what = intent.getIntExtra("what", 0);
        Log.i(TAG, "getData: what = " + what);
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
//		}else if(what == Constants.WhatDecisionTotailsDetailsPersonage){
//			mType = "Case";
//			DecisionToDepName = intent.getStringExtra("depName");
//			DecisionToActID = intent.getStringExtra("actID");
//			DecisionToEndTime = intent.getStringExtra("EndTime");
//			DecisionToStartTime = intent.getStringExtra("mStartTime");
//		}else if(what == Constants.WhatDecisionAreaDetailsPersonage){
//			mType = "Case";
//			DecisionAreaEmployeeID = intent.getStringExtra("EmployeeID");
//			DecisionAreaactID = intent.getStringExtra("actID");
//			DecisionAreastartTime = intent.getStringExtra("StartTime");
//			DecisionAreaendTime = intent.getStringExtra("EndTime");
//			DecisionAreadepName = intent.getStringExtra("depName");
//		}else
        if (what == Constants.WhatDepartmentDetailsCase) {
            mType = "Case";
            DeType = intent.getStringExtra("type");
            DeDepartmentID = intent.getStringExtra("DeDepartmentID");
            DefirstLevelID = intent.getStringExtra("firstLevelID");
            //DeDepName = intent.getStringExtra("bigName");
            //DeType = intent.getStringExtra("type");
            DeStartTime = intent.getStringExtra("StartTime");
            DeEndTime = intent.getStringExtra("EndTime");
        } else if (what == Constants.WhatDepartmentDetailsSource) {
            mType = "Source";
            DeType = intent.getStringExtra("type");
            DeDepartmentID = intent.getStringExtra("DeDepartmentID");
            DefirstLevelID = intent.getStringExtra("firstLevelID");
            //DeDepName = intent.getStringExtra("bigName");
            DeStartTime = intent.getStringExtra("StartTime");
            DeEndTime = intent.getStringExtra("EndTime");
        }
//        else if(what == Constants.WhatPersonageDetailsCase){
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
        requestDate();
    }

    private void requestDate() {
//		if(what == Constants.WhatDecisionCaseDetailsCase){
//			model.mProcSouTypeCaseStatisticsDetails(getUserId(),CaseStartTime,CaseEndTime,CaseSouTypeName,
//					isClose,page,count,new getDetailsAwait());
//		}else if(what == Constants.WhatDecisionCaseDetailsSource){
//			model.ProcSouTypeCaseStatisticsDetails(getUserId(),SourceStartTime,SourceEndTime,SourceSouTypeName,
//					isClose,page,count,new getDetailsAwait());
//		}else if(what == Constants.WhatDecisionTotailsDetailsPersonage){
//			model.DecisionTotality(getUserId(), DecisionToDepName, DecisionToActID,
//					isClose, DecisionToStartTime, DecisionToEndTime, page, count, new getDetailsAwait());
//		}else if(what == Constants.WhatDecisionAreaDetailsPersonage){
//			model.DecisionAreaDetails(getUserId(), DecisionAreaEmployeeID, DecisionAreaactID,DecisionAreadepName,
//					isClose, DecisionAreastartTime, DecisionAreaendTime, page, count, new getDetailsAwait());
        if (what == Constants.WhatDepartmentDetailsCase) {
            arrayList = new ArrayList<>();
            adapter = new DetailsCaseAdapter(getActivity(), arrayList);
            mListView.setAdapter(adapter);
            loadDepartmentCase(getUserId(), DeDepartmentID, DefirstLevelID, DeType, isClose,
                    DeStartTime, DeEndTime, page, count);
//			model.DepartmentDetailsCase(getUserId(), DeDepartmentID, DefirstLevelID,DeType, isClose,
//					DeStartTime, DeEndTime, page, count,new getDetailsAwait());
        } else if (what == Constants.WhatDepartmentDetailsSource) {
//			model.DepartmentDetailsSource(getUserId(), DeDepartmentID, DefirstLevelID,DeType, isClose,
//					DeStartTime, DeEndTime, page, count,new getDetailsAwait());
            arrayListSource = new ArrayList<>();
            adapterSource = new DetailsSourceAdapter(getActivity(), arrayListSource);
            mListView.setAdapter(adapterSource);
            loadDepartmentSource(getUserId(), DeDepartmentID, DefirstLevelID, DeType, isClose,
                    DeStartTime, DeEndTime, page, count);
        }
//		else if(what == Constants.WhatPersonageDetailsCase){
//			model.memberWorkCase(getUserId(), PeEmployeeID,PeType, isClose,
//					PeStartTime, PeEndTime, page, count,new getDetailsAwait());
//		}else if(what == Constants.WhatPersonageDetailsSource){
//			model.memberWorkSource(getUserId(), PeEmployeeID,PeType, isClose,
//					PeStartTime, PeEndTime, page, count,new getDetailsAwait());
//		}else{
//
//		}
    }

    private String getUserId() {
        return UserSingleton.USERINFO.getName().UserID;
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
//        params.put("Page", Page + "");
//        params.put("Count", Count + "");
        Log.i(TAG, "loadDepartmentSource: " + new Gson().toJson(params));
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "Mobile/GetDepartmentSourceStatisticsList.ashx")
                .params(params)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: " + response);
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

    private void loadDepartmentCase(String UserId, String DepartmentID, String firstLevelID, String type, String CaseStatusID,
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
//        params.put("Page", Page + "");
//        params.put("Count", Count + "");
        Log.i(TAG, "loadDepartmentCase: " + new Gson().toJson(params));
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "Mobile/GetDepartmentCaseStatisticsList.ashx")
                .params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
//                ToastUtils.showToast();
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


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
//        arg2 -= 1;
        if (mType.equals("Case")) {
            Intent intent = new Intent();
            intent.putExtra("CaseId", Integer.parseInt(arrayList.get(arg2).getCaseID()));
            intent.setClass(getActivity(), CaseDetailsActivity.class);
            startActivity(intent);
        } else if (mType.equals("Source")) {
            //Intent intent = new Intent();
            //intent.putExtra("CaseId", Integer.parseInt(arrayListSource.get(arg2).getSourceID()));
            //intent.setClass(getActivity(),CaseDetailsActivity.class);
            //startActivity(intent);
        }
    }

}