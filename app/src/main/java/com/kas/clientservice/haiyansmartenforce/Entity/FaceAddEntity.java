package com.kas.clientservice.haiyansmartenforce.Entity;

/**
 * 描述：
 * 时间：2018-06-15
 * 公司：COMS
 */

public class FaceAddEntity {

    /**
     * faceset_token : f303a4c5d30558e28befe8924750ff71
     * time_used : 688
     * face_count : 1
     * face_added : 1
     * request_id : 1529047288,258e4956-452c-4b71-928e-6d00e4a248de
     * outer_id :
     * failure_detail : []
     */

    public String faceset_token;
    public int time_used;
    public int face_count;
    public int face_added;
    public String request_id;
    public String outer_id;
//    public List<?> failure_detail;

    public String getFaceset_token() {
        return faceset_token;
    }

    public int getTime_used() {
        return time_used;
    }

    public int getFace_count() {
        return face_count;
    }

    public int getFace_added() {
        return face_added;
    }

    public String getRequest_id() {
        return request_id;
    }

    public String getOuter_id() {
        return outer_id;
    }
}
