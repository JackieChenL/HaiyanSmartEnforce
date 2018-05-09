package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.RoadSearchEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述：
 * 时间：2018-05-09
 * 公司：COMS
 */

public interface RoadSeachAPI {

    @POST("system/theme/anjuan/WFroad.ashx")
    @FormUrlEncoded
    rx.Observable<BaseEntity<RoadSearchEntity[]>> httpRoadSearch(@Field("WZroad")String road);
}
