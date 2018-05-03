package com.kas.clientservice.haiyansmartenforce.User;

import android.content.Context;

import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.SPUtils;

public class UserSingleton {
    private Boolean isLogin;

    public volatile static UserInfo USERINFO;
    public static int tempEstateID;
    private volatile static UserSingleton instance = new UserSingleton();

    public static UserSingleton getInstance() {
        return instance;
    }

    /**
     * 获取登录名
     * @param mContext
     * @return
     */
    public String getUserAccount(Context mContext) {
        return (String) SPUtils.get(mContext, Constants.USERACCOUNT,"");
    }

    /**
     * 获取密码
     * @param mContext
     * @return
     */
    public String getUserPassword(Context mContext) {
        return (String) SPUtils.get(mContext, Constants.USERPASSWORD,"");
    }

    public UserInfo getUserInfo(Context mContext){
        return (UserInfo) SPUtils.get(mContext, Constants.USERINFO,null);
    }

    public boolean isLogin(Context mContext) {
        if (isLogin == null) {
            isLogin = (boolean) SPUtils.get(mContext, Constants.ISLOGIN, false);
        }
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    /**
     * 清空用户数据
     */
    public void clear(){
        USERINFO = new UserInfo();
    }

}
