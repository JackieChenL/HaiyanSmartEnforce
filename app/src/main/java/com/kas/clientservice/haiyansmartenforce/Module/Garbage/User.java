package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

/**
 * Created by 12976 on 2018/4/23.
 */

public class User {
    private String usreid;
    private String time;
    private String name;
    private String address;
    private String assess;

    public String getUserid() {
        return usreid;
    }

    public void setUserid(String usreid) {
        this.usreid = usreid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public User(String usreid, String time, String name, String address, String assess){
        this.usreid = usreid;
        this.time=time;
        this.name=name;
        this.address=address;
        this.assess=assess;
    }
}
