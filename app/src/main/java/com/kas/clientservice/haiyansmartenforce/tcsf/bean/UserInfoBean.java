package com.kas.clientservice.haiyansmartenforce.tcsf.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 时间：2018-05-03
 * 公司：COMS
 */

public class UserInfoBean  {
    private String ZFRYID;
    private String type;
    private String Road;
    private String board;

    public String getRoad() {
        return Road;
    }

    public void setRoad(String road) {
        Road = road;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getZFRYID() {
        return ZFRYID;
    }

    public void setZFRYID(String ZFRYID) {
        this.ZFRYID = ZFRYID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public <T> ArrayList<T> getResultBeanList(Class<T> T , String str){
        return (ArrayList<T>) JSON.parseArray(str,T);
    };


    public static class RoadBean{
       public String  Berthname;
       public String  BerthID;

       @Override
       public String toString() {
           return "RoadBean{" +
                   "Berthname='" + Berthname + '\'' +
                   ", BerthID='" + BerthID + '\'' +
                   '}';
       }
   }
   public static class BoardBean{
       public String  url;
       public String  title;
       public int typeid;

       @Override
       public String toString() {
           return "BoardBean{" +
                   "url='" + url + '\'' +
                   ", title='" + title + '\'' +
                   ", typeid=" + typeid +
                   '}';
       }
   }

    @Override
    public String toString() {
        return "UserInfo{" +
                "ZFRYID='" + ZFRYID + '\'' +
                ", type='" + type + '\'' +
                ", Road=" + Road +
                ", BoardBean=" + board +
                '}';
    }
}
