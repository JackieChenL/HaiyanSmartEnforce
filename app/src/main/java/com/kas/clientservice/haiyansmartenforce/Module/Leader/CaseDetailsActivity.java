package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.ExamineHistoryDetauls;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

public class CaseDetailsActivity extends BaseActivity implements View.OnClickListener{
	private Context mContext = CaseDetailsActivity.this;
	private TextView mTvSource;
	private TextView mTvParty;
	private TextView mTvTime;
	private TextView mTvAddress;
	private TextView mTvTypeBig;
	private TextView mTvTypeSmall;
	private TextView mTvRightName;
	private TextView mTvNnlawfulAct;
	private TextView mTvNnlawfulClause;
	private TextView mTvBasisPunishment;
	private TextView mTvBasisUndertaker;
	private Button mBtnExamine;
	private Button mBtnWrit;

	private ScrollView mDetailsLoadEndSv;

	private int CaseID;
	private int CaseFlowIDCas;
	//旋转动画
	private RotateAnimation rotate;

    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_header_title)
    TextView tv_title;

	@Override
	protected int getLayoutId() {
		return R.layout.mine_details_activity;
	}

	@Override
	protected String getTAG() {
		return this.toString();
	}

	//private String CaseFlow;

	@Override
	protected void initResAndListener() {
		super.initResAndListener();
		intView();
		getDate();
		tv_title.setText("案件详情");
		iv_back.setOnClickListener(this);
	}

	private void intView(){
		mTvSource = (TextView)findViewById(R.id.examine_history_details_tv_source);
		mTvParty = (TextView)findViewById(R.id.examine_history_details_tv_party);
		mTvTime = (TextView)findViewById(R.id.examine_history_details_tv_time);
		mTvAddress = (TextView)findViewById(R.id.examine_history_details_tv_address);
		mTvTypeBig = (TextView)findViewById(R.id.examine_history_details_tv_typebig);
		mTvTypeSmall = (TextView)findViewById(R.id.examine_history_details_tv_typesmall);
		mTvRightName = (TextView)findViewById(R.id.examine_history_details_tv_rightname);
		mTvNnlawfulAct = (TextView)findViewById(R.id.examine_history_details_tv_unlawful_act);
		mTvNnlawfulClause = (TextView)findViewById(R.id.examine_history_details_tv_unlawful_clause);
		mTvBasisPunishment = (TextView)findViewById(R.id.examine_history_details_tv_basis_punishment);
		mTvBasisUndertaker = (TextView)findViewById(R.id.examine_history_details_tv_undertaker);
		
		mBtnExamine = (Button)findViewById(R.id.examine_history_btn_caseflow);
		mBtnWrit = (Button)findViewById(R.id.examine_history_btn_writ);
		mDetailsLoadEndSv = (ScrollView)findViewById(R.id.mDetailsLoadEndSv);
		
		mBtnWrit.setOnClickListener(this);
		mBtnExamine.setOnClickListener(this);

	}


    private void DateLoad(ExamineHistoryDetauls.KSBean detauls){
		Log.i(TAG, "DateLoad: "+gson.toJson(detauls));
		if(detauls.getNameECCas() == null||detauls.getNameECCas().equals("")){
			return;
		}
		mTvSource.setText(detauls.getNameSoT());
		mTvParty.setText(detauls.getNameECCas());
		mTvTime.setText(detauls.getRegisterTimeCas());
		mTvAddress.setText(detauls.getCaseAddressCas());
		mTvTypeBig.setText(detauls.getNameFiL());
		mTvTypeSmall.setText(detauls.getNameSeL());
		mTvRightName.setText(detauls.getNameThL());
		mTvNnlawfulAct.setText(detauls.getActCas());
		mTvNnlawfulClause.setText(detauls.getLaws());
		mTvBasisPunishment.setText(detauls.getPenalty());
		mTvBasisUndertaker.setText(detauls.getNameEmp());
		
	}
	private void getDate(){
		Intent intent = getIntent();
		CaseID = intent.getIntExtra("CaseId", 0);

        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"/mobile/GetCaseInfoRead.ashx")
                .addParams("CaseID",CaseID+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                showNetErrorToast();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                ExamineHistoryDetauls examineHistoryDetauls = gson.fromJson(response,ExamineHistoryDetauls.class);
				Log.i(TAG, "onResponse: "+gson.toJson(examineHistoryDetauls));
				if (examineHistoryDetauls.getKS()!=null&&examineHistoryDetauls.getKS().size()>0) {
                    DateLoad(examineHistoryDetauls.getKS().get(0));
                }else {
                    ToastUtils.showToast(mContext,"数据异常");
                }
            }
        });
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.examine_history_btn_caseflow:
			Intent intent = new Intent();
			intent.setClass(mContext, CaseDetailsFlowActivity.class);
			intent.putExtra("CaseId", CaseID);
			intent.putExtra("CaseFlowIDCas", CaseFlowIDCas);
			startActivity(intent);
			break;
		case R.id.examine_history_btn_writ:
			Intent intentWrit = new Intent();
			intentWrit.setClass(mContext, CaseDetailsWritActivity.class);
			intentWrit.putExtra("CaseId", CaseID);
			intentWrit.putExtra("CaseFlowIDCas", CaseFlowIDCas);
			startActivity(intentWrit);
			break;
		case R.id.iv_heaer_back:
			finish();
			break;
		}
	}
}
