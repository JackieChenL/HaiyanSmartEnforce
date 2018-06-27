package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.LeaderAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.LeaderSearchDetailEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class LeaderCaseSearchDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_case_details_source)
    TextView tv_source;
    @BindView(R.id.tv_case_details_nameECCas)
    TextView tv_nameEccas;
    @BindView(R.id.tv_case_details_add)
    TextView tv_address;
    @BindView(R.id.tv_case_details_time)
    TextView tv_time;
    @BindView(R.id.tv_case_details_big)
    TextView tv_bigClass;
    @BindView(R.id.tv_case_details_small)
    TextView tv_small;
    @BindView(R.id.tv_case_details_rightName)
    TextView tv_rightName;
    @BindView(R.id.tv_case_details_act)
    TextView tv_act;
    @BindView(R.id.tv_case_details_clause)
    TextView tv_clause;
    @BindView(R.id.tv_case_details_according)
    TextView tv_according;
    @BindView(R.id.tv_case_details_undertaker)
    TextView tv_underTaker;
    @BindView(R.id.et_examine_opinion)
    EditText et_opinion;
    @BindView(R.id.tv_case_search_detail_submit)
    TextView tv_submit;
    @BindView(R.id.tv_case_search_detail_save)
    TextView tv_save;
    @BindView(R.id.tv_case_search_detail_back)
    TextView tv_back;

    LeaderSearchDetailEntity.KSBean bean;
    String caseId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.case_examine_detailsactivity;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        caseId = getIntent().getStringExtra("caseId");
        tv_title.setText("待办详情");
        iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_back.setOnClickListener(this);
//
        loadData();
    }

    private void loadData() {
        RetrofitClient.createService(LeaderAPI.class, RequestUrl.baseUrl_leader)
                .httpGetCaseDealDetail(caseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<String>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                        LeaderSearchDetailEntity entity = gson.fromJson(s, LeaderSearchDetailEntity.class);
                        if (entity.getKS() != null && entity.getKS().size() > 0) {
                            bean = entity.getKS().get(0);
                            tv_source.setText(bean.getNameSoT());
                            tv_nameEccas.setText(bean.getNameECCas());
                            tv_time.setText(bean.getRegisterTimeCas());
                            tv_address.setText(bean.getCaseAddressCas());
                            tv_bigClass.setText(bean.getNameFiL());
                            tv_small.setText(bean.getNameSeL());
                            tv_rightName.setText(bean.getNameThL());
                            tv_act.setText(bean.getActCas());
                            tv_according.setText(bean.getPenalty());
                            tv_clause.setText(bean.getLaws());
                            tv_underTaker.setText(bean.getNameEmp());
                            tv_source.requestFocus();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_case_search_detail_submit:
                submit("submit");
                break;
            case R.id.tv_case_search_detail_save:
                submit("save");
                break;
            case R.id.tv_case_search_detail_back:
                submit("back");
                break;

        }
    }

    private void submit(String handle) {
        RetrofitClient.createService(LeaderAPI.class,RequestUrl.baseUrl_leader)
                .httpCaseDealSubmit(caseId,
                        UserSingleton.USERINFO.getName().UserID,
                        et_opinion.getText().toString(),
                        handle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<String>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: "+responeThrowable.toString());
                    }

                    @Override
                    public void onNext(String s) {

                        Log.i(TAG, "onNext: "+s);
                    }
                });
    }


}
