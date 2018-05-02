package com.kas.clientservice.haiyansmartenforce.Http;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.kas.clientservice.haiyansmartenforce.Http.OkHttpHelper.addLogClient;
import static com.kas.clientservice.haiyansmartenforce.Http.OkHttpHelper.addProgressClient;

public class RetrofitClient {

    //所有的联网地址 统一成https
    public static String mBaseUrl = "http://111.1.31.184:90/";
//    public static String mBaseUrl = "http://111.1.31.210:88";
//    private static String mBaseUrl = "http://test.zk.yuezhan.co:81";
//    public static String mBaseUrl = "http://test.app.api.yuezhan.co/";
    private static Gson gson = new Gson();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).writeTimeout(30,TimeUnit.SECONDS);

    public static Retrofit.Builder builder = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            //增加返回值为String的支持
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            //增加返回值为jason的支持
            .addConverterFactory(AvatarConverter.create(gson));

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .client(addLogClient(httpClient).build())
                .build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder
                .baseUrl(mBaseUrl)
                .client(addLogClient(httpClient).build())
                .build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, OnProgressResponseListener listener) {
        Retrofit retrofit = builder
                .client(addProgressClient(httpClient, listener).build())
                .build();
        return retrofit.create(serviceClass);
    }
}