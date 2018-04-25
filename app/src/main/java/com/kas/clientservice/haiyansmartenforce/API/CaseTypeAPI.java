package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.CaseTypeEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-04-24
 * 公司：COMS
 */

public interface CaseTypeAPI {
    //http://112.13.194.180:82/Handler/CollecterApi.aspx?optionName=geteventpartbigclass&probclass=1
    @POST("/Handler/CollecterApi.aspx")
    @FormUrlEncoded
    Observable<BaseEntity<List<CaseTypeEntity>>> httpGetCaseType(@Field("optionName")String optionName, @Field("probclass") String subClass);

    @POST("/Handler/CollecterApi.aspx")
    @FormUrlEncoded
    Observable<BaseEntity<List<CaseTypeEntity>>> httpGetCaseTypeSub(@Field("optionName")String optionName, @Field("bigclassCode") String subClass);
}
