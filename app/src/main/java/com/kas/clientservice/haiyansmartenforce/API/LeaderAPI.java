package com.kas.clientservice.haiyansmartenforce.API;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-06-26
 * 公司：COMS
 */

public interface LeaderAPI {
    @POST("Mobile/CaseDealList.ashx")
    @FormUrlEncoded
    Observable<String> httpGetCaseDealList(@Field("UserID")String UserID,
                                               @Field("QueryTime")String QueryTime,
                                               @Field("EndTime")String EndTime,
                                               @Field("NameEC")String NameEC,
                                               @Field("Address")String Address,
                                               @Field("Number")String Number,
                                               @Field("Act")String Act);

    @POST("mobile/GetCaseInfoRead.ashx")
    @FormUrlEncoded
    Observable<String> httpGetCaseDealDetail(@Field("CaseID")String CaseID);

    @POST("Mobile/CaseSubmitOrSave.ashx")
    @FormUrlEncoded
    Observable<String> httpCaseDealSubmit(@Field("CaseID")String CaseID,
                                          @Field("UserID")String UserID,
                                          @Field("Suggest")String Suggest,
                                          @Field("SubmitOrSave")String SubmitOrSave);

}
