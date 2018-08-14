package com.kas.clientservice.haiyansmartenforce.Module.verification;

import smartenforce.base.BaseBean;


public class VerifyBean extends BaseBean {


    /**
     * projcode : 1240
     * msgtitle : null
     * msgcontent : 请核查
     * cu_date : 2018-08-09 09:52:34
     * fid : null
     * address : 绍兴市/越城区/灵芝镇
     * state : 1
     * probdesc : 机动车乱停放
     * bigclassname : 街面秩序
     * smallclassname : 机动车乱停放
     * probclassName : 事件
     * PicFiles : 
     * erjiprojcode : null
     */

    private String projcode;
    private String msgtitle;
    private String msgcontent;
    private String cu_date;
    private String fid;
    private String address;
    private String state;
    private String probdesc;
    private String bigclassname;
    private String smallclassname;
    private String probclassName;
    private String PicFiles;
    private String erjiprojcode;

    public void setProjcode(String projcode) {
        this.projcode = projcode;
    }

    public void setMsgtitle(String msgtitle) {
        this.msgtitle = msgtitle;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public void setCu_date(String cu_date) {
        this.cu_date = cu_date;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setProbdesc(String probdesc) {
        this.probdesc = probdesc;
    }

    public void setBigclassname(String bigclassname) {
        this.bigclassname = bigclassname;
    }

    public void setSmallclassname(String smallclassname) {
        this.smallclassname = smallclassname;
    }

    public void setProbclassName(String probclassName) {
        this.probclassName = probclassName;
    }

    public void setPicFiles(String picFiles) {
        PicFiles = picFiles;
    }

    public void setErjiprojcode(String erjiprojcode) {
        this.erjiprojcode = erjiprojcode;
    }


    public String getProjcode() {
        return projcode;
    }

    public String getMsgtitle() {
        return msgtitle;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public String getCu_date() {
        return cu_date;
    }

    public String getFid() {
        return fid;
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }

    public String getProbdesc() {
        return probdesc;
    }

    public String getBigclassname() {
        return bigclassname;
    }

    public String getSmallclassname() {
        return smallclassname;
    }

    public String getProbclassName() {
        return probclassName;
    }

    public String getPicFiles() {
        return PicFiles;
    }

    public String getErjiprojcode() {
        return erjiprojcode;
    }


    @Override
    public String toString() {
        return "VerifyBean{" +
                "projcode='" + projcode + '\'' +
                ", msgtitle='" + msgtitle + '\'' +
                ", msgcontent='" + msgcontent + '\'' +
                ", cu_date='" + cu_date + '\'' +
                ", fid='" + fid + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", probdesc='" + probdesc + '\'' +
                ", bigclassname='" + bigclassname + '\'' +
                ", smallclassname='" + smallclassname + '\'' +
                ", probclassName='" + probclassName + '\'' +
                ", PicFiles='" + PicFiles + '\'' +
                ", erjiprojcode='" + erjiprojcode + '\'' +
                '}';
    }

    // TODO:TEST
    public VerifyBean() {
        projcode="1240";
        msgtitle="";
        msgcontent="请核查";
        cu_date="2018-08-09 09:52:34";
        fid="";
        address="绍兴市/越城区/灵芝镇";
        state="1";
        probdesc="机动车乱停放";
        bigclassname="街面秩序";
        smallclassname="机动车乱停放";
        probclassName="事件";
        erjiprojcode="";
        PicFiles="http://s1.dwstatic.com/group1/M00/CE/79/931f2d17b80b4f9812de38426ddb8f59.jpg,http://s1.dwstatic.com/group1/M00/CE/79/931f2d17b80b4f9812de38426ddb8f59.jpg";

    }
}
