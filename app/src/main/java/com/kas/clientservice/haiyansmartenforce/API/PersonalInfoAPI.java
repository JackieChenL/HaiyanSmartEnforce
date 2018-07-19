package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.PersonalInfoEntiy;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-07-16
 * 公司：COMS
 */

public interface PersonalInfoAPI {
    /**
     * 添加身份证信息接口
     * @return
     */
//    username=1223&postiveImg=1223&oppositeImg=1223&cCardid=1223

    @GET("system/theme/anjuan/AddCertifcate.ashx")
    Observable<BaseEntity> httpIdentify(@Query("username")String username,@Query("postiveImg")String postiveImg,
                                        @Query("oppositeImg")String oppositeImg,@Query("cCardid")String cCardid);

    /**
     * 个人信息
     */
    @GET("system/theme/anjuan/PersonMessage.ashx")
    Observable<BaseEntity<PersonalInfoEntiy>> httpGetPersonalInfo(@Query("loginname")String loginname);

    /**
     * 修改个人信息
     */
    @GET("system/theme/anjuan/UpdatePersonMessage.ashx")
    Observable<BaseEntity> httpChangePersonalInfo(@Query("PersonPhoto")String PersonPhoto,
                                                  @Query("PersonSex")String PersonSex,
                                                  @Query("PersonBirth")String PersonBirth,
                                                  @Query("PersonSummary")String PersonSummary,
                                                  @Query("PersonLogName")String PersonLogName);
}
