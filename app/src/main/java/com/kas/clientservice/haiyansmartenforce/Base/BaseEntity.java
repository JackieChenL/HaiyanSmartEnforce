package com.kas.clientservice.haiyansmartenforce.Base;

import java.io.Serializable;

/**
 * Created by Administrator on 16-12-2.
 */
public class BaseEntity<T> implements Serializable {

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
    public String Page;
    public String total;
    public T Rtn;

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String page) {
        Page = page;
    }

    public T getRtn() {
        return Rtn;
    }

    public void setRtn(T rtn) {
        Rtn = rtn;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
