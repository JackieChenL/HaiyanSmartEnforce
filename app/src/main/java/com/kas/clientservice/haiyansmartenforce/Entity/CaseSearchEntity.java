package com.kas.clientservice.haiyansmartenforce.Entity;

/**
 * 描述：
 * 时间：2018-05-30
 * 公司：COMS
 */

public class CaseSearchEntity {

    /**
     * bigclass : 01
     * smallclass : 0102
     * address : szz
     * probdesc : sss
     * fid : null
     * projcode : 36008
     * projname : 海盐数管【2018】第36008号
     * name : rrr
     * startdate : 2018-05-30T16:51:23.973
     * probsource : 20
     */

    public String bigclass;
    public String smallclass;
    public String smallclassname;
    public String address;
    public String probdesc;
    public Object fid;
    public int projcode;
    public String projname;
    public String name;
    public String startdate;
    public String probsource;
    public String files;

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

    public String getBigclass() {
        return bigclass;
    }

    public void setBigclass(String bigclass) {
        this.bigclass = bigclass;
    }

    public Object getFid() {
        return fid;
    }

    public void setFid(Object fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProbdesc() {
        return probdesc;
    }

    public void setProbdesc(String probdesc) {
        this.probdesc = probdesc;
    }

    public String getProbsource() {
        return probsource;
    }

    public void setProbsource(String probsource) {
        this.probsource = probsource;
    }

    public int getProjcode() {
        return projcode;
    }

    public void setProjcode(int projcode) {
        this.projcode = projcode;
    }

    public String getProjname() {
        return projname;
    }

    public void setProjname(String projname) {
        this.projname = projname;
    }

    public String getSmallclass() {
        return smallclass;
    }

    public void setSmallclass(String smallclass) {
        this.smallclass = smallclass;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
}
