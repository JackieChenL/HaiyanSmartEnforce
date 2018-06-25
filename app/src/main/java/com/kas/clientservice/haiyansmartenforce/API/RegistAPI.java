package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;

import retrofit2.http.Query;
import retrofit2.http.GET;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-05-24
 * 公司：COMS
 */

public interface RegistAPI {
    @GET("web/carstop/UserCenter.ashx")
    Observable<BaseEntity<RegisterEntity>> httpRegist(@Query("OperName")String OperName,
                                                      @Query("Phonenum")String Phonenum,
                                                      @Query("UserName")String UserName,
                                                      @Query("UCarnum")String UCarnum,
                                                      @Query("UserCardID")String UserCardID,
                                                      @Query("Address")String Address,
                                                      @Query("Password")String Password);

    class RegisterEntity {

        /**
         * OperName : 123
         * Phonenum : 13677778888
         * UserName : 123
         * UCarnum : 123
         * UserCardID : 123
         * Address : 123
         */

        public String OperName;
        public String Phonenum;
        public String UserName;
        public String UCarnum;
        public String UserCardID;
        public String Address;
    }

}
