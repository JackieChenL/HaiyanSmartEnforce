package com.kas.clientservice.haiyansmartenforce.Module.PersonalInfo;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

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
    @BindView(R.id.rl_personalInfo_qiandao)
    RelativeLayout rl_qiandao;
    @BindView(R.id.tv_personalInfo_phone)
    TextView tv_phone;
    @BindView(R.id.rl_personalInfo_myCommit)
    RelativeLayout rl_myCommit;
    @BindView(R.id.rl_personalInfo_help)
    RelativeLayout rl_help;
    @BindView(R.id.rl_personalInfo_youhuiquan)
    RelativeLayout rl_youhuiquan;
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

        tv_title.setText("个人信息");
        iv_back.setOnClickListener(this);
        rl_edit.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        rl_identify.setOnClickListener(this);
        rl_myCommit.setOnClickListener(this);
        rl_qiandao.setOnClickListener(this);
        rl_score.setOnClickListener(this);
        rl_youhuiquan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.rl_personalInfo_edit:
                startActivity(new Intent(mContext,PersonalInfoEditActivity.class));
                break;
            case R.id.rl_personalInfo_identify:
                break;
            case R.id.tv_personalinfo_qiandao:
                break;
            case R.id.rl_personalInfo_myCommit:
                break;
            case R.id.rl_personalInfo_help:
                break;
            case R.id.rl_personalInfo_youhuiquan:
                break;

        }
    }
}
