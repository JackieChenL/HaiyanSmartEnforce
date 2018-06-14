package com.kas.clientservice.haiyansmartenforce.API;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-06-12
 * 公司：COMS
 */

public interface UpLoadFileAPI {
    @Multipart
    @POST("special/api/SubmitTable/MapAddUopload")
    Observable<String> httpUploadFile(@Part("image") RequestBody description);
}
