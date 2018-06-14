package com.kas.clientservice.haiyansmartenforce.Module.HuochaiCredit;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.HuoChaiCredit;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuochaiCreditActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.et_credit_id)
    EditText et_id;
    @BindView(R.id.et_credit_name)
    EditText et_name;
    @BindView(R.id.et_credit_phone)
    EditText et_phone;
    @BindView(R.id.et_credit_reason)
    EditText et_reason;
    @BindView(R.id.tv_credit_btn)
    TextView tv_btn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huochai_credit;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("火柴信用");
        iv_back.setOnClickListener(this);
        tv_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_credit_btn:
                if (et_name.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext,"请输入姓名");
                    return;
                }
                if (et_id.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext,"请输入身份证号");
                    return;
                }
                if (et_phone.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext,"请输入手机号");
                    return;
                }
                if (et_reason.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext,"请输入申请理由");
                    return;
                }
                commit();
                break;
        }
    }

    private void commit() {
        RetrofitClient.createService(HuoChaiCredit.class,"http://117.149.146.131:6111/")
                .httpCreditCommit(et_name.getText().toString(),
                        et_id.getText().toString(),
                        et_phone.getText().toString(),
                        et_reason.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                        Log.i(TAG, "onError: "+responeThrowable.toString());
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (baseEntity.isState()) {
                            ToastUtils.showToast(mContext,"提交成功，请等待审核");
                            finish();
                        }else {
                            ToastUtils.showToast(mContext,baseEntity.ErrorMsg);
                        }

                    }
                });
    }
}
