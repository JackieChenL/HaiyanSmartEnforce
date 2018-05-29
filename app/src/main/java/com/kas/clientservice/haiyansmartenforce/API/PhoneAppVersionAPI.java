package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.AppVersionInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-05-22
 * 公司：COMS
 */

public interface PhoneAppVersionAPI {
    /**
     * 获取APP最新版本号，带是否需强制更新信息
     * @return
     */
    @GET("system/theme/AppVersion.ashx")
    Observable<BaseEntity<AppVersionInfo>> httpsPhoneAppVersionRx();

    @GET("system/theme/AppVersion.ashx")
    Call<BaseEntity<AppVersionInfo>> httpsPhoneAppVersion();
    //Call<String> httpsPhoneAppVersion();

    /**
     * 获取APP最新版本信息
     * @param AppVersion
     * @return
     */
    @GET("PhoneAppVersion/GetPhoneAppVersionInfo_Json/")
    Observable<BaseEntity<AppVersionInfo>> httpsGetPhoneAppVersionInfoRx(@Query("AppVersion") String AppVersion);
}
