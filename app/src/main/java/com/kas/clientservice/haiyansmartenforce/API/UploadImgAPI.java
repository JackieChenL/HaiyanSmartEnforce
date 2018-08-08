package com.kas.clientservice.haiyansmartenforce.API;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-08-01
 * 公司：COMS
 */

public interface UploadImgAPI {
    @POST("Mobile/UploadImg.ashx")
    @FormUrlEncoded
    Observable<ZhuanXiangZhengZhiAPI.UploadImgEntity> uploadImg(@Field("Img") String img, @Field("UserID")String id, @Field("UpType")String upType);
}
