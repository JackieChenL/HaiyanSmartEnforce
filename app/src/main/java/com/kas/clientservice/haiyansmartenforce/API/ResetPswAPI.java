package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-06-25
 * 公司：COMS
 */

public interface ResetPswAPI {
    @POST("system/theme/anjuan/editpwd.ashx")
    @FormUrlEncoded
    Observable<BaseEntity> httpResetPsw(@Field("Opername")String Opername,
                                        @Field("NewOperPwd")String NewOperPwd);
}
