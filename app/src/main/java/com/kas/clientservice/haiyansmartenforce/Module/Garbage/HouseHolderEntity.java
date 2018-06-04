package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

/**
 * Created by 12976 on 2018/4/26.
 */

public class HouseHolderEntity {

    /**
     * Id : 1
     * SerialNumber : 20001
     * Name : 张海交
     * Lng : 120.93857
     * Lat : 30.53268
     * CreateDate : 2018-04-18T15:27:17.517
     * CommunityCode : null
     * UserName : 张海交
     * Address : 海盐秦山加油站西南
     */

    private int Id;
    private String SerialNumber;
    private String Name;
    private double Lng;
    private double Lat;
    private String CreateDate;
    private Object CommunityCode;
    private String UserName;
    private String Address;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String SerialNumber) {
        this.SerialNumber = SerialNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double Lng) {
        this.Lng = Lng;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public Object getCommunityCode() {
        return CommunityCode;
    }

    public void setCommunityCode(Object CommunityCode) {
        this.CommunityCode = CommunityCode;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
}
