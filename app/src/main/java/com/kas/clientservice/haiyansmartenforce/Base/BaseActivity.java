package com.kas.clientservice.haiyansmartenforce.Base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = "@Author:chenlong_" + getTAG();

    protected abstract int getLayoutId();

    protected abstract String getTAG();


    protected Gson gson = new Gson();

    protected Context mContext;

    //关键的是否登录 由父类提供
    public boolean isLogin = false;
    //关键的https联网字段 由父类提供
    public String mAuthorization;


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }


    ProgressDialog loadingDialog;

    public void showLoadingDialog() {

        if (loadingDialog == null) {

            loadingDialog = new ProgressDialog(this);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setMessage("加载中...");
        }
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {

        if (loadingDialog != null) {

            loadingDialog.dismiss();

        }


    }


    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (s == null) {
            return;
        }

        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mContext = this;
//        StatusBarUtil.StatusBarLightMode(this);
        setContentView(getLayoutId());
        Log.e("layout_id", getTAG() + getLayoutId());

        ButterKnife.bind(this);


        Window window = getWindow();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // Translucent status bar
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }


        //调整软键盘
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        initResAndListener();

    }


    //空方法 规定子类 初始化监听器 和定义显示资源 的步骤
    protected void initResAndListener() {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 注册友盟统计
     */

    public RequestBody parseBodyToJson(Object entity) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(entity));
    }

    public void startActivityWithoutBack(Context context, Class activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        Activity ac = (Activity) context;
        ac.finish();
    }

    public void showNetErrorToast() {
        ToastUtils.showToast(mContext, "网络错误");
    }

    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public String formLocation(String longitude, String latitude) {

        return longitude + "," + latitude;
    }



}