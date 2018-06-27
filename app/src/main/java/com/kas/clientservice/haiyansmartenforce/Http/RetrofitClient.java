package com.kas.clientservice.haiyansmartenforce.Http;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.kas.clientservice.haiyansmartenforce.Http.OkHttpHelper.addLogClient;
import static com.kas.clientservice.haiyansmartenforce.Http.OkHttpHelper.addProgressClient;

public class RetrofitClient {

    //所有的联网地址 统一成https
//    public static String mBaseUrl = "http://111.1.31.184:90/";
    //海盐
    public static String mBaseUrl = "http://117.149.146.131/";
//    public static String mBaseUrl = "http://hywx.hnzhzf.top/";
//    public static String mBaseUrl = "http://112.13.194.180:82/";
    private static Gson gson = new Gson();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).writeTimeout(30,TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
//                            .addHeader("Accept-Encoding", "UTF-8")
//                            .addHeader("Connection", "keep-alive")
//                            .addHeader("Accept", "*/*")
//                            .addHeader("Cookie", "add cookies here")
//                            .addHeader("Content-Type", "application/json; charset=UTF-8")
                            .build();
                    return chain.proceed(request);

                }
            });

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
    public static <S> S createService2(Class<S> serviceClass) {
        Retrofit retrofit = builder
                .baseUrl(RequestUrl.baseUrl)
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