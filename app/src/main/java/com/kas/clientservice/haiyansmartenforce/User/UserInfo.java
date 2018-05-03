package com.kas.clientservice.haiyansmartenforce.User;

/**
 * 描述：
 * 时间：2018-05-03
 * 公司：COMS
 */

public class UserInfo {
    public String ZFRYID;
    public String type;
    public String Road;

    public String getRoad() {
        return Road;
    }

    public void setRoad(String road) {
        Road = road;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZFRYID() {
        return ZFRYID;
    }

    public void setZFRYID(String ZFRYID) {
        this.ZFRYID = ZFRYID;
    }
}
