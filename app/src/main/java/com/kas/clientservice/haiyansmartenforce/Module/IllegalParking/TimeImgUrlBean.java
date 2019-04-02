package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import java.util.Date;

import smartenforce.util.DateUtil;

/**
 * Created by DELL_Zjcoms02 on 2019/4/2.
 */

public class TimeImgUrlBean {
    private String url;
    private String time;

    public TimeImgUrlBean(String url) {
        this.url = url;
        this.time= DateUtil.getFormatDate(new Date(),DateUtil.MDHMS);
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }
}
