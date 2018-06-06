package com.kas.clientservice.haiyansmartenforce.API;

import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;

import retrofit2.http.POST;

/**
 * 描述：
 * 时间：2018-05-09
 * 公司：COMS
 */

public interface PaperNumAPI {
    @POST("system/theme/anjuan/WFJDSnum.ashx")
    rx.Observable<BaseEntity<EntityBean>> httpGetPaperNum();

    public class EntityBean{
        public String jdsnum;

        public String getJdsnum() {
            return jdsnum;
        }
    }
}
