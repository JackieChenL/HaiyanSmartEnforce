package com.kas.clientservice.haiyansmartenforce.Module.Register;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Login.LoginActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.tv_login_login)
    TextView tv_login_login;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("注册");
        tv_login_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_login_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

        }
    }
}
