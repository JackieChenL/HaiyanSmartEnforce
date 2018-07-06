package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.ExamineHistoryDetailsCaseFlow;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 描述：
 * 时间：2018-07-03
 * 公司：COMS
 */
public class CaseDetailsFlowActivity extends BaseActivity implements View.OnClickListener {
    private int CaseId = 0;
    private int CaseFlowIDCas = 0;
    private RotateAnimation rotate;
    private Context mContext = CaseDetailsFlowActivity.this;

    private ListView mLvCaseFlow;
    private ImageButton mBtnBack;

    private LinearLayout CaseFlowLoadLl;
    private ImageView CaseFlowLoadImg;
    //
    //
    private LinearLayout CaseLlLoadEnd;
    ArrayList<ExamineHistoryDetailsCaseFlow.KSBean> caseFlowList = new ArrayList<>();
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_header_title)
    TextView tv_title;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_caseflow_activity;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        initView();
        initAdapter();
        getDate();
    }
    ExamineHistoryDetailsCaseFolowAdapter adapter;
    private void initAdapter() {
        adapter = new ExamineHistoryDetailsCaseFolowAdapter(mContext, caseFlowList);
        mLvCaseFlow.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void initView() {
        CaseLlLoadEnd = (LinearLayout) findViewById(R.id.CaseLlLoadEnd);

        mLvCaseFlow = (ListView) findViewById(R.id.lv_info);
        tv_title.setText("案件流");
        iv_back.setOnClickListener(this);
    }


    public void getDate() {
        Intent intent = getIntent();
        CaseId = intent.getIntExtra("CaseId", 0);
        CaseFlowIDCas = intent.getIntExtra("CaseFlowIDCas", 0);

        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/CaseStep.ashx")
                .addParams("caseid",CaseId+"")
                .addParams("CaseFlowID",CaseFlowIDCas+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                showNetErrorToast();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                ExamineHistoryDetailsCaseFlow examineHistoryDetailsCaseFlow = gson.fromJson(response,ExamineHistoryDetailsCaseFlow.class);
                if (examineHistoryDetailsCaseFlow.getKsBean()!=null) {
                    caseFlowList.addAll(examineHistoryDetailsCaseFlow.getKsBean());
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }



    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
        }
    }
}
