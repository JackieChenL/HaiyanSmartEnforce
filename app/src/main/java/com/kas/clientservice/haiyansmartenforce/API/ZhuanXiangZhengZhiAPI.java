package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.ZXZZclassEntity;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-06-06
 * 公司：COMS
 */

public interface ZhuanXiangZhengZhiAPI {
    @GET("special/api/SpecialClass/GetSpecialClass")
    Observable<ZXZZclassEntity> httpZXZZgetClass();

    @POST("special/api/SpecialClass/SaveSpecial")
    Observable<BaseEntity> httpZXZZcommit(@Body RequestBody special);


    @POST("Mobile/UploadImg.ashx")
    @FormUrlEncoded
    Observable<UploadImgEntity> httpZXZZimg(@Field("Img") String img,@Field("UserID")String id,@Field("UpType")String upType);


    @POST("mobile/UploadImg.ashx")
    @FormUrlEncoded
    Observable<UploadImgEntity> httpTCSFimg(@Field("Img") String img,@Field("UserID")String id,@Field("UpType")String upType,@Field("number")int number,@Field("Carnum")String Carnum);


    class UploadImgEntity{
        public List<String> KS;

        public List<String> getKS() {
            return KS;
        }
    }
}
