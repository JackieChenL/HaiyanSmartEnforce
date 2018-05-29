package com.kas.clientservice.haiyansmartenforce.tcsf.intf;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.LogUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.widget.ProgressDialogUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public  abstract  class BeanCallBack extends StringCallback {
    private Activity context;
    private String msg;
    public BeanCallBack(Activity context,String msg) {
        this.context=context;
        this.msg=msg;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        LogUtil.e("onBefore:","onBefore");
        if (msg!=null)
            ProgressDialogUtil.show(context,msg+"...");
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
    }

    @Override
    public void inProgress(float progress, long total, int id) {
        super.inProgress(progress, total, id);
    }

    @Override
    public boolean validateReponse(Response response, int id) {
        return super.validateReponse(response, id);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        LogUtil.e("ERRO:",e.getMessage()+"\n"+e.getCause());
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
                handleBeanResult(new NetResultBean(false,-2,"服务器异常"));
            }
        });

    }

    @Override
    public void onResponse(final String response, int id) {
        LogUtil.e("onResponse:",response);
        context.runOnUiThread(new Runnable() {
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
