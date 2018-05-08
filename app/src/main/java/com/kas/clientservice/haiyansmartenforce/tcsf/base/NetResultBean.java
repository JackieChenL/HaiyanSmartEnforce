package com.kas.clientservice.haiyansmartenforce.tcsf.base;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * 网络请求返回实体基础类
 */

public class NetResultBean {

    /**
     * State : true
     * ErrorCode : 0
     * ErrorMsg :
     * Page : 0
     * Rtn : [{"code":"01","name":"市容市貌"},{"code":"02","name":"宣传广告"},{"code":"03","name":"施工管理"},{"code":"04","name":"突发事件"},{"code":"05","name":"街面秩序"},{"code":"06","name":"市政公用设施"},{"code":"07","name":"道路交通设施"},{"code":"08","name":"市容环境"},{"code":"09","name":"园林绿化"},{"code":"10","name":"房屋建筑"},{"code":"11","name":"其他设施"},{"code":"12","name":"扩充类别"},{"code":"21","name":"扩展事件"},{"code":"22","name":"违章搭建"},{"code":"23","name":"工作任务"},{"code":"33","name":"测试"}]
     * total : 0
     */

    public boolean State;
    public int ErrorCode;
    public String ErrorMsg;
    public int Page;
    public int total;
    public String Rtn;


    public NetResultBean(boolean state, int errorCode, String errorMsg) {
        State = state;
        ErrorCode = errorCode;
        ErrorMsg = errorMsg;
    }

    public NetResultBean() {
    }



    public <T> T getResultBean(Class<T> T){
        return JSON.parseObject(Rtn, T);
    }


    public <T> ArrayList<T> getResultBeanList(Class<T> T){
        return (ArrayList<T>)JSON.parseArray(Rtn,T);
    };

    @Override
    public String toString() {
        return "NetResultBean{" +
                "State=" + State +
                ", ErrorCode=" + ErrorCode +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", Page=" + Page +
                ", total=" + total +
                ", Rtn='" + Rtn + '\'' +
                '}';
    }
}
