package com.kas.clientservice.haiyansmartenforce.API;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 描述：
 * 时间：2018-04-27
 * 公司：COMS
 */

public interface CarNumScanApi {
    @POST("licenseplaterecognition/recognize")
    @FormUrlEncoded
    rx.Observable<String> httpCarNumRecognize(@Header("X-Ca-Key") String hey, @Header("Authorization") String Authorization,
                                              @Field("pic") String base64);

}
