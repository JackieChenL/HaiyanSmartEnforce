package com.kas.clientservice.haiyansmartenforce;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 描述：
 * 时间：2018-04-18
 * 公司：COMS
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10*1000L, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
