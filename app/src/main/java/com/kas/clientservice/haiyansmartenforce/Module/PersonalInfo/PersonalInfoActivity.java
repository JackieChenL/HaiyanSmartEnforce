package com.kas.clientservice.haiyansmartenforce.Module.PersonalInfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kas.clientservice.haiyansmartenforce.API.SignInAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.AppVersionInfo;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.Update.GetAppInfo;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.SPUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.rl_personalInfo_edit)
    RelativeLayout rl_edit;
    @BindView(R.id.tv_personalInfo_name)
    TextView tv_name;
    @BindView(R.id.rl_personalInfo_identify)
    RelativeLayout rl_identify;
    @BindView(R.id.rl_personalInfo_score)
    RelativeLayout rl_score;
    @BindView(R.id.tv_personalinfo_qiandao)
    TextView tv_qiandao;
    @BindView(R.id.tv_personalInfo_phone)
    TextView tv_phone;
    @BindView(R.id.rl_personalInfo_myCommit)
    RelativeLayout rl_myCommit;
    @BindView(R.id.rl_personalInfo_help)
    RelativeLayout rl_help;
    @BindView(R.id.rl_personalInfo_youhuiquan)
    RelativeLayout rl_youhuiquan;
    @BindView(R.id.tv_personalInfo_signNum)
    TextView tv_signNum;
    @BindView(R.id.tv_personalInfo_renzheng)
    TextView tv_renzheng;
    @BindView(R.id.iv_personalInfo_icon)
    ImageView iv_icon;

    @BindView(R.id.tev_version)
    TextView tev_version;
    @BindView(R.id.tev_qchc)
            TextView tev_qchc;
    int signNum = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        String versionName = GetAppInfo.getAppVersionName(mContext);
        tev_version.setText("当前版本:"+versionName);
        tv_title.setText("个人信息");
        tv_name.setText(UserSingleton.USERINFO.getUserName());
        tv_phone.setText(UserSingleton.USERINFO.getPhneNum());

        if (UserSingleton.USERINFO.isHaveSign()) {
            tv_qiandao.setEnabled(false);
            tv_qiandao.setText("已签到");
        }


        iv_back.setOnClickListener(this);
        rl_edit.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        rl_identify.setOnClickListener(this);
        rl_myCommit.setOnClickListener(this);
        tv_qiandao.setOnClickListener(this);
        tev_qchc.setOnClickListener(this);
        rl_youhuiquan.setOnClickListener(this);


        try {
            if (!UserSingleton.USERINFO.getRegistNum().equals("")) {
                signNum = Integer.parseInt(UserSingleton.USERINFO.getRegistNum());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        tv_signNum.setText("已连续签到" + signNum + "天");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (UserSingleton.USERINFO.isHaveCertificate()) {
            tv_renzheng.setText("已认证");
            rl_identify.setEnabled(false);
        }
        Glide.with(mContext).load(UserSingleton.USERINFO.getPhoto()).asBitmap().error(R.drawable.loginhead).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv_icon.setImageBitmap(resource);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.rl_personalInfo_edit:
                startActivity(new Intent(mContext, PersonalInfoEditActivity.class));
                break;
            case R.id.rl_personalInfo_identify:
                startActivity(new Intent(mContext, IdentifyActivity.class));
                break;
            case R.id.tv_personalinfo_qiandao:
                sighIn();
                break;
            case R.id.rl_personalInfo_myCommit:
                break;
            case R.id.tev_qchc:
            case R.id.rl_personalInfo_help:
                showToast("清除缓存成功");
                SPUtils.clear(this);
                break;
            case R.id.rl_personalInfo_youhuiquan:
                break;

        }
    }

    private void sighIn() {
        RetrofitClient.createService(SignInAPI.class)
                .httpSignIn(UserSingleton.USERINFO.getUserName())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity s) {
                        Log.i(TAG, "onNext: " + s);
                        if (s.isState()) {
                            signNum++;
                            tv_signNum.setText("已连续签到" + signNum + "天");
                            tv_qiandao.setEnabled(false);
                            tv_qiandao.setText("已签到");
                            tv_qiandao.setBackground(getResources().getDrawable(R.color.grey_200));
                            UserSingleton.USERINFO.setRegistNum(signNum + "");
                            UserSingleton.USERINFO.setHaveSign(true);
                            ToastUtils.showToast(mContext, "签到成功");
                        }
                    }
                });
    }
}
