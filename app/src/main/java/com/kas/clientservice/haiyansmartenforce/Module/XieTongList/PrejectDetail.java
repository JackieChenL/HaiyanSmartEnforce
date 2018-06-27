package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

/**
 * Created by DELL_Zjcoms02 on 2018/6/26.
 */

public class PrejectDetail {
    String projcode;
    String projname1;
    String area;
    String street;
    String square;
    String probdesc1;
    String bigclassname;
    String smallclassname;
    String address;
    String fid;
    String startdate;//开始时间
    String tracetime;//最后完成时间
    String StepDate;//立案时间
    public String getStepDate() {
        return StepDate;
    }
    public void setStepDate(String stepDate) {
        StepDate = stepDate;
    }
    public String getFid() {
        return fid;
    }
    public void setFid(String fid) {
        this.fid = fid;
    }
    public String getStartdate() {
        return startdate;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getTracetime() {
        return tracetime;
    }
    public void setTracetime(String tracetime) {
        this.tracetime = tracetime;
    }
    public String getProjcode() {
        return projcode;
    }
    public void setProjcode(String projcode) {
        this.projcode = projcode;
    }
    public String getProjname1() {
        return projname1;
    }
    public void setProjname1(String projname1) {
        this.projname1 = projname1;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getSquare() {
        return square;
    }
    public void setSquare(String square) {
        this.square = square;
    }
    public String getProbdesc1() {
        return probdesc1;
    }
    public void setProbdesc1(String probdesc1) {
        this.probdesc1 = probdesc1;
    }
    public String getBigclassname() {
        return bigclassname;
    }
    public void setBigclassname(String bigclassname) {
        this.bigclassname = bigclassname;
    }
    public String getSmallclassname() {
        return smallclassname;
    }
    public void setSmallclassname(String smallclassname) {
        this.smallclassname = smallclassname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
