package com.kas.clientservice.haiyansmartenforce.tcsf.intf;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.LogUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.widget.ProgressDialogUtil;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

//TODO
public  abstract  class BeanCallBack extends StringCallback {
    private Context context;
    private String msg;
    public BeanCallBack(Context context,String msg) {
        this.context=context;
        this.msg=msg;
    }



    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
        LogUtil.e("onBefore:","onBefore");
        if (msg!=null)
        ProgressDialogUtil.show(context,msg+"...");
    }


    @Override
    public void onError(com.squareup.okhttp.Request request, Exception e) {
        LogUtil.e("ERRO:",e.getMessage()+"\n"+e.getCause());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
                handleBeanResult(new NetResultBean(false,400,"服务器异常"));
            }
        });
    }

    @Override
    public void onResponse(final String response) {
        LogUtil.e("onResponse:",response);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
                NetResultBean bean = JSON.parseObject(response, NetResultBean.class);
                handleBeanResult(bean);

            }
        });
    }



    public  abstract void handleBeanResult(NetResultBean bean);
}
