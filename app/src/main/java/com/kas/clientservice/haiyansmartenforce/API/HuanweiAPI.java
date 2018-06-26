package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiCheckDetailEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiHandleDetailEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiListEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 描述：
 * 时间：2018-05-14
 * 公司：COMS
 */

public interface HuanweiAPI {
    @GET("system/theme/hwjg/Hxmcx.ashx")
    Observable<BaseEntity<HuanweiProjectEntity[]>> httpHuanweiProject();

    @GET("system/theme/hwjg/Hjcnrcx.ashx")
    Observable<BaseEntity<HuanweiContentEntity[]>> httpHuanweiContent(@Query("xmid") String id);

    //http://hywx.hnzhzf.top/system/theme/hwjg/Hnewsadd.ashx?hoperid=4438&hjcnrid=1&jcdd=1&jcddzb=NULL&qkms=1&UpType=enterprise&Img=
    @POST("system/theme/hwjg/Hnewsadd.ashx")
    @FormUrlEncoded
    Observable<BaseEntity> httpHuanweiCommit(@Field("hoperid") String id,
                                             @Field("hjcnrid") String hjcnrid,
                                             @Field("jcdd") String jcdd,
                                             @Field("jcddzb") String jcddzb,
                                             @Field("qkms") String qkms,
                                             @Field("UpType") String upType,
                                             @Field("townid") String townid,
                                             @Field("DeScore") String DeScore,
                                             @Field("Shstate") String Shstate,
                                             @Field("Img") String Img);

    @GET("system/theme/hwjg/HistoryjcryAdd.ashx")
//    @FormUrlEncoded
    Observable<BaseEntity> httpHuanweiReCommit(@Query("hoperid") String id,
                                               @Query("id") String caseId,
                                               @Query("hjcnrid") String hjcnrid,
                                               @Query("jcdd") String jcdd,
                                               @Query("jcddzb") String jcddzb,
                                               @Query("qkms") String qkms,
                                               @Query("UpType") String upType,
                                               @Query("townid") String townid,
                                               @Query("DeScore") String DeScore,
                                               @Query("Shstate") String Shstate,
                                               @Query("Img") String Img);

    @GET("system/theme/hwjg/Hnewsshlist.ashx")
    Observable<BaseEntity<HuanweiListEntity>> httpGetCheckList(@Query("hoperid") String id);

    @GET("system/theme/hwjg/HistorySearch.ashx")
    Observable<BaseEntity<HuanweiListEntity>> httpGetHistoryList(@Query("hoperid") String id);

    @GET("system/theme/hwjg/Hnewssh.ashx")
    Observable<BaseEntity<HuanweiCheckDetailEntity>> httpGetCheckDetail(@Query("hoperid") String userId, @Query("id") String id);

    //hoperid=4440&townid=1&ID=&Shstate=

    @GET("system/theme/hwjg/Hnewsshadd.ashx")
    Observable<BaseEntity> httpCheckCommit(@Query("hoperid") String hoperid, @Query("townid") String townid,
                                           @Query("ID") String id,
                                           @Query("Shstate") int Shstate,
                                           @Query("Checktownid") String Checktownid,
                                           @Query("DeScore") String DeScore
    );

    @GET("system/theme/hwjg/Hnewszg.ashx")
    Observable<BaseEntity<HuanweiHandleDetailEntity>> httpHandleDetail(@Query("hoperid") String hoperid, @Query("id") String id);

    @POST("system/theme/hwjg/Hnewszgadd.ashx")
    @FormUrlEncoded
    Observable<BaseEntity> httpHandleCommit(@Field("hoperid") String hoperid, @Field("id") String id, @Field("changDetail") String changDetail,
                                            @Field("UpType") String UpType, @Field("Img") String img, @Field("changState") String state);

    /*
    * 检查人员历史记录单条
    * */
    @GET("system/theme/hwjg/Historyjcry.ashx")
    Observable<BaseEntity<HistoryDetail_commitEntity>> httpGetCommitPeopleHistoryDetai(@Query("hoperid") String userId, @Query("id") String id);

    /*
    * 审核人员历史记录单条
    * */
    @GET("system/theme/hwjg/Histroyshry.ashx")
    Observable<BaseEntity<HistoryDetail_checkEntity>> httpGetCheckPeopleHistoryDetai(@Query("hoperid") String userId, @Query("id") String id);

    /*
    * 整改人员历史记录单条
    * */
    @GET("system/theme/hwjg/Historyzgry.ashx")
    Observable<BaseEntity<HistoryDetail_checkEntity>> httpGetHandlePeopleHistoryDetai(@Query("hoperid") String userId, @Query("id") String id);


    public class HuanweiProjectEntity {


        /**
         * xm : 集镇、工业园区保洁
         * xmid : 1
         */

        public String xm;
        public String xmid;
    }

    public class HuanweiContentEntity {

        /**
         * jcnr : 垃圾房
         * jcnrid : 4
         */

        public String jcnr;
        public String jcnrid;
    }

    public class HistoryDetail_commitEntity {

        /**
         * hoperid : 4438
         * ID : 1
         * XM : 集镇、工业园区保洁
         * JCNR : 日常保洁
         * JCDD : 1
         * JCDDZB :
         * QKMS : 1
         * Img : []
         * Shstate : 7
         * starttime : 2018-05-13 17:20
         */

        public String hoperid;
        public String ID;
        public String Opername;
        public String XM;
        public String JCNR;
        public String JCDD;
        public String JCDDZB;
        public String QKMS;
        public String Shstate;
        public String starttime;
        public List<HuanweiCheckDetailEntity.ImgBean> Img;
        public String Checktown;
        public String Checktownid;
        public String DeScore;
        public String JCNRID;

    }

    public class HistoryDetail_checkEntity {

        /**
         * hoperid : 4440
         * ID : 1
         * XM : 集镇、工业园区保洁
         * JCNR : 日常保洁
         * JCDD : 1
         * JCDDZB :
         * QKMS : 1
         * Img : []
         * Shstate : 7
         * starttime : 2018-05-13 17:20
         * town : 沈荡镇
         * board : [{"QKMS":"测试","Img":[{"img":"http://hywx.hnzhzf.top/MVersion/201805151104550.jpg"}],"changState":"7","addtime":"2018-05-15 11:04","town":"武原街道"}]
         */

        public String jcryname;
        public String hoperid;
        public String ID;
        public String XM;
        public String JCNR;
        public String JCDD;
        public String JCDDZB;
        public String QKMS;
        public String Shstate;
        public String starttime;
        public String town;
        public String Checktown;
        public String Checktownid;
        public String DeScore;
        public List<BoardBean.ImgBean> Img;
        public List<BoardBean> board;

        public static class BoardBean {
            /**
             * QKMS : 测试
             * Img : [{"img":"http://hywx.hnzhzf.top/MVersion/201805151104550.jpg"}]
             * changState : 7
             * addtime : 2018-05-15 11:04
             * town : 武原街道
             */

            public String QKMS;
            public String zgryname;
            public String changState;
            public String addtime;
            public String town;
            public List<ImgBean> Img;

            public static class ImgBean {
                /**
                 * img : http://hywx.hnzhzf.top/MVersion/201805151104550.jpg
                 */

                public String img;
            }
        }
    }

    public class HistoryDetail_HandleEntity {

        /**
         * hoperid : 4439
         * ID : 1
         * XM : 集镇、工业园区保洁
         * JCNR : 日常保洁
         * JCDD : 1
         * JCDDZB :
         * QKMS : 1
         * Img : []
         * Shstate : 7
         * starttime : 2018-05-13 17:20
         * town : 沈荡镇
         * board : [{"QKMS":"测试","Img":[{"img":"http://hywx.hnzhzf.top/MVersion/201805151104550.jpg"}],"changState":"7","addtime":"2018-05-15 11:04","town":"武原街道"}]
         */

        public String jcryname;
        public String hoperid;
        public String ID;
        public String XM;
        public String JCNR;
        public String JCDD;
        public String JCDDZB;
        public String QKMS;
        public String Shstate;
        public String starttime;
        public String town;
        public String Checktown;
        public String Checktownid;
        public String DeScore;
        public List<HistoryDetail_checkEntity.BoardBean.ImgBean> Img;
        public List<BoardBean> board;

        public static class BoardBean {
            /**
             * QKMS : 测试
             * Img : [{"img":"http://hywx.hnzhzf.top/MVersion/201805151104550.jpg"}]
             * changState : 7
             * addtime : 2018-05-15 11:04
             * town : 武原街道
             */

            public String QKMS;
            public String changState;
            public String addtime;
            public String town;
            public List<ImgBean> Img;

            public static class ImgBean {
                /**
                 * img : http://hywx.hnzhzf.top/MVersion/201805151104550.jpg
                 */

                public String img;
            }
        }
    }
}
