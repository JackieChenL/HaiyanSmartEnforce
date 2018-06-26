package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.TownEntity;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-06-25
 * 公司：COMS
 */

public interface TownsAPI {
    @GET("system/theme/hwjg/TownList.ashx")
    Observable<BaseEntity<TownEntity>>httpgetTown();
}
