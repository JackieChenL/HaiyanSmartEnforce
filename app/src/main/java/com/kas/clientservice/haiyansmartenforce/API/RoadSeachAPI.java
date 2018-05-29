package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.RoadSearchEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 描述：
 * 时间：2018-05-09
 * 公司：COMS
 */

public interface RoadSeachAPI {

    @GET("system/theme/anjuan/WFroad.ashx")
    rx.Observable<BaseEntity<RoadSearchEntity[]>> httpRoadSearch(@Query("WZroad") String road);
}
