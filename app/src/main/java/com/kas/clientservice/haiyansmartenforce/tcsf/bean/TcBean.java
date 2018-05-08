package com.kas.clientservice.haiyansmartenforce.tcsf.bean;


import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseBean;

public class TcBean extends BaseBean {

    private String UCarnum;
    private String Road;
    private String BerthName;
    private String StartTime;
    private String Photo;
    private String SBYID;

    public TcBean(String UCarnum, String road, String berthName, String startTime, String photo, String SBYID) {
        this.UCarnum = UCarnum;
        Road = road;
        BerthName = berthName;
        StartTime = startTime;
        Photo = photo;
        this.SBYID = SBYID;
    }

    @Override
    public String toString() {
        return "TcBean{" +
                "UCarnum='" + UCarnum + '\'' +
                ", Road='" + Road + '\'' +
                ", BerthName='" + BerthName + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", Photo='" + Photo + '\'' +
                ", SBYID='" + SBYID + '\'' +
                '}';
    }


}
