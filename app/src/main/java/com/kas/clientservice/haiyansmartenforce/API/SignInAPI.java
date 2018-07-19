package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-07-16
 * 公司：COMS
 */

public interface SignInAPI {
    @GET("system/theme/anjuan/AddSignIn.ashx")
    Observable<BaseEntity> httpSignIn(@Query("OperName")String OperName);
}
