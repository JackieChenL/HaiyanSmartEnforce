package com.kas.clientservice.haiyansmartenforce.Module.Welcome;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Login.LoginActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Update.UpdateManager;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.NetUtils;

public class WelcomeActivity extends BaseActivity {

    private Handler handler = new Handler() {
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
    private boolean isNetUsable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)
        {
            finish();
            return;
        }
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        checkUpdate();

    }

    private void checkUpdate() {
        isNetUsable = NetUtils.isConnected(mContext);

        if (isNetUsable) {
            Log.i(TAG, "has network");
            final UpdateManager manager = new UpdateManager(mContext);
            manager.flag = true;
//            manager.setLoginListener(this);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    //检查软件更新
                    manager.checkUpdate();
                }
            });

            t.start();

        }
    }

}
