package com.kas.clientservice.haiyansmartenforce;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import smartenforce.bean.GroupBean;

/**
 * 描述：
 * 时间：2018-04-18
 * 公司：COMS
 */

public class MyApplication extends Application {


    public String userID;
    public String DepartmentID;
    public String NameEmp;
    public String NameDep;
    public String AddressDep;


    public int TerminalID;
    public int IntervalTimeTeI;



    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10*1000L, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        SDKInitializer.initialize(this);

    }


}
