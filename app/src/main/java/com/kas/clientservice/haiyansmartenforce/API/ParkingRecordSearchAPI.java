package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.ParkingSearchEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述：
 * 时间：2018-05-07
 * 公司：COMS
 */

public interface ParkingRecordSearchAPI {
    @POST(RequestUrl.illegalParkingSearch)
    @FormUrlEncoded
    rx.Observable<BaseEntity<ParkingSearchEntity>> httpParkingSearch(@Field("ZFRYID") String id,
                                                                     @Field("carnum") String carNum,
                                                                     @Field("starttime") String startTime,
                                                                     @Field("endtime") String endTime,
                                                                     @Field("WFaddress") String address);
}
