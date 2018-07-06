package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.CaseDatailsWrit;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

public class CaseDetailsWritActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
	private Context mContext = CaseDetailsWritActivity.this;
	private ArrayList<CaseDatailsWrit.KSBean> arrayList= new ArrayList<>();
	private int CaseId;
	private ImageButton mDetailsWritHeadBack;
	private ListView mLvWrit;
	private LinearLayout mLlWrit;
	private ImageView mImgViewWrit;
	private RotateAnimation rotate;
	
	private LinearLayout WritLlLoadEnd;
	CaseDetailsWritAdapter adapter;
	@BindView(R.id.iv_heaer_back)
	ImageView iv_back;
	@BindView(R.id.tv_header_title)
	TextView tv_title;
	@Override
	protected int getLayoutId() {
		return R.layout.mine_details_writ_activity;
	}

	@Override
	protected String getTAG() {
		return this.toString();
	}

	@Override
	protected void initResAndListener() {
		super.initResAndListener();
		mLvWrit = (ListView)findViewById(R.id.details_writ_lv);

		iv_back.setOnClickListener(this);
		tv_title.setText("文书");
		mLvWrit.setOnItemClickListener(this);

		adapter = new CaseDetailsWritAdapter(mContext,arrayList);
		mLvWrit.setAdapter(adapter);
		getDate();


	}

	private void getDate(){
		Intent intent = getIntent();
		CaseId = intent.getIntExtra("CaseId",0);

		OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"mobile/GetCaseFlowList.ashx")
				.addParams("caseid",CaseId+"")
				.build().execute(new StringCallback() {
			@Override
			public void onError(Call call, Exception e, int id) {
				Log.i(TAG, "onError: "+e.toString());
				showNetErrorToast();
			}

			@Override
			public void onResponse(String response, int id) {
				Log.i(TAG, "onResponse: "+response);
				CaseDatailsWrit caseDatailsWrit = gson.fromJson(response,CaseDatailsWrit.class);
				if (caseDatailsWrit.getKS()!=null) {
					arrayList.addAll(caseDatailsWrit.getKS());
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if(arrayList.get(arg2).getType().equals("立案审批")){
			
		}else{
//			Intent intent = new Intent();
//			intent.putExtra("ID", arrayList.get(arg2).getID()+"");
//			intent.putExtra("Type", arrayList.get(arg2).getType());
//			intent.setClass(mContext, CaseDetailsWritDActivity.class);
//			startActivity(intent);
		}
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.iv_heaer_back:
			finish();
			break;
		}
	}
}