package com.kas.clientservice.haiyansmartenforce.API;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-04-24
 * 公司：COMS
 */

public interface IllegalParkingAPI {
    //http://111.1.31.210:88/system/theme/anjuan/WFHandler.ashx?ZFRYID=&WFtime=&WFaddress=&WFAddressZB=&WFimg=&Carnum=

    @GET("/system/theme/anjuan/WFHandler.ashx")
    Observable<String> httpCommitParking(@Query("ZFRYID") int id,
                                         @Query("WFtime") String time,
                                         @Query("WFaddress")String address,
                                         @Query("WFAddressZB")String jingweidu,
                                         @Query("WFimg") String img,
                                         @Query("Carnum")String carNum);
}
