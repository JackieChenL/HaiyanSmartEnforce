package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-06-12
 * 公司：COMS
 */

public interface HuoChaiCredit {
    @POST("api/manage/AddSearchFromApp")
    @FormUrlEncoded
    Observable<BaseEntity> httpCreditCommit(@Field("Name")String name,
                                            @Field("Identity")String identity,
                                            @Field("Tel")String tel,
                                            @Field("Reason")String reason);
}
