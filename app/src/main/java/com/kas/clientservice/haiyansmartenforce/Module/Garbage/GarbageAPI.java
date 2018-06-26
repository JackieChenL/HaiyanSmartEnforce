package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 12976 on 2018/5/8.
 */

public interface GarbageAPI {
    @POST("monitor/api/values/GetTrashInfo")
    @FormUrlEncoded
    Observable httpGarbage(@Field("SerialNumber") String s);
}
