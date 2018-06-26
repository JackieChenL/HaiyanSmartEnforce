package com.kas.clientservice.haiyansmartenforce.API;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-06-01
 * 公司：COMS
 */

public interface CaseCommit {
    @POST("handler/collecterapi.aspx/")
    @FormUrlEncoded
    Observable<String> httpCaseCommit(
            @Field("optionName") String optionName,
            @Field("typecode") String typecode,
            @Field("userid") String userid,
            @Field("bigClass") String bigClass,
            @Field("smallClass") String smallClass,
            @Field("gridcode") String gridcode,
            @Field("address") String address,
            @Field("descript") String descript,
            @Field("fid") String fid,
            @Field("picurls") String picurls,
            @Header("Content-Type") String header

//            @Body RequestBody requestBody
    );

    public class CaseCommitEntity {
        String typecode;
        String userid;
        String bigClass;
        String smallClass;
        String gridcode;
        String address;
        String descript;
        String fid;
        String picurls;

        public CaseCommitEntity(String address, String bigClass, String descript, String fid, String gridcode, String picurls, String smallClass, String typecode, String userid) {
            this.address = address;
            this.bigClass = bigClass;
            this.descript = descript;
            this.fid = fid;
            this.gridcode = gridcode;
            this.picurls = picurls;
            this.smallClass = smallClass;
            this.typecode = typecode;
            this.userid = userid;
        }
    }
}
