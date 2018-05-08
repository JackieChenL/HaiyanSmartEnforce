package com.kas.clientservice.haiyansmartenforce.tcsf.util;


import android.graphics.Bitmap;

import com.alibaba.fastjson.JSON;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.CpBean;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class TestUtil {
    private final static String url = "http://jisucpsb.market.alicloudapi.com/licenseplaterecognition/recognize";
    private final  static String AppKey = "24553193";
    private final static  String AppCode = "APPCODE 2e476d97d6994a489afb3491b44a2578";
    public static void testBitmapSm(Bitmap bmp){
        final String base64bmp = ImgUtil.bitmapToBase64(bmp);
        OkHttpUtils.post().url(url)
                .addHeader("X-Ca-Key", AppKey)
                .addHeader("Authorization", AppCode)
                .addParams("pic", base64bmp).build().execute(new StringCallback() {
                                                                 @Override
                                                                 public void onError(Request request, Exception e) {
                                                                     LogUtil.e("ERRO", "onError");
                                                                 }

                                                                 @Override
                                                                 public void onResponse(String response) {
                                                                     LogUtil.e("SUCCESSS", response);
                                                                 }

                                                             }

        );




    }



}
