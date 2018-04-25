package com.kas.clientservice.haiyansmartenforce.Module.Welcome;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Login.LoginActivity;
import com.kas.clientservice.haiyansmartenforce.R;

public class WelcomeActivity extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                    break;
            }
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        handler.sendEmptyMessageDelayed(0,4000);
    }
}
