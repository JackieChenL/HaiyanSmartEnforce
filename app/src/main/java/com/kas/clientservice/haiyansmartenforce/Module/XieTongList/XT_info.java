package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

/**
 * Created by DELL_Zjcoms02 on 2018/6/27.
 */

public class XT_info {
    String projcode;
    String projname;
    String startdate;
    String doTime;
    String state;

    public XT_info(String projcode, String projname, String startdate, String doTime, String state) {
        this.projcode = projcode;
        this.projname = projname;
        this.startdate = startdate;
        this.doTime = doTime;
        this.state = state;
    }

    public String getProjcode() {
        return projcode;
    }

    public void setProjcode(String projcode) {
        this.projcode = projcode;
    }

    public String getProjname() {
        return projname;
    }

    public void setProjname(String projname) {
        this.projname = projname;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getDoTime() {
        return doTime;
    }

    public void setDoTime(String doTime) {
        this.doTime = doTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
