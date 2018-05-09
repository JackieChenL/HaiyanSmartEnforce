package com.kas.clientservice.haiyansmartenforce.Http;

import com.kas.clientservice.haiyansmartenforce.Utils.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpHelper {

    private static ProgressBean progressBean = new ProgressBean();

    private static ProgressHandler mProgressHandler;

    public static OkHttpClient.Builder addLogClient(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(logging);
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept", "*/*")
//                        .addHeader("Cookie", "add cookies here")
//                        .addHeader("Content-Type", "application/json; charset=UTF-8")
//                        .build();
//                MediaType mediaType = request.body().contentType();
//                try {
//                    Field field = mediaType.getClass().getDeclaredField("mediaType");
//                    field.setAccessible(true);
//                    field.set(mediaType, "application/json");
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                return chain.proceed(request);
//            }
//
//        });
        return builder;
    }

    public static OkHttpClient.Builder addProgressClient(OkHttpClient.Builder builder,final OnProgressResponseListener listener) {

        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Logger.d("start intercept");
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(
                        new ProgressResponseBody(originalResponse.body(), listener))
                        .build();
            }
        });
        return builder;
    }
}
