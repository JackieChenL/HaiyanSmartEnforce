package com.kas.clientservice.haiyansmartenforce.Module.Login;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.MainActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Register.RegisterActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_login_login)
    TextView tv_login;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        iv_back.setVisibility(View.GONE);
        tv_title.setText("登录");
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_login:
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
        }
    }
}
