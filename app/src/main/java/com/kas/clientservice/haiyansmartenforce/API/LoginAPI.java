package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.User.UserInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述：
 * 时间：2018-05-03
 * 公司：COMS
 */

public interface LoginAPI {
    @POST("system/theme/anjuan/login.ashx")
    @FormUrlEncoded
    rx.Observable<BaseEntity<UserInfo>> httpLogin(@Field("Opername")String userName, @Field("OperPwd")String paw,@Field("versionNumber")String versionNumber,@Field("UniqueTer")String imei);

}
