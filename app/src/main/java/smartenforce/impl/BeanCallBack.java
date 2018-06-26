package smartenforce.impl;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import smartenforce.base.NetResultBean;
import smartenforce.util.LogUtil;
import smartenforce.widget.ProgressDialogUtil;

public abstract class BeanCallBack extends StringCallback {
    private Context context;
    private String msg;

    public BeanCallBack(Context context, String msg) {
        this.context = context;
        this.msg = msg;
    }


    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        if (msg != null)
            ProgressDialogUtil.show(context, msg + "...");
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
    public void onError(final Call call, Exception e, int id) {
        LogUtil.e("ERRO:", e.getMessage() + "\n" + e.getCause());
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
//                if (call.isCanceled()){
//
//                }else{
                handleBeanResult(new NetResultBean(false, 400, "服务器异常"));
//                }

            }
        });

    }

    @Override
    public void onResponse(final String response, int id) {
        LogUtil.e("onResponse:", response);
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
                NetResultBean bean = JSON.parseObject(response, NetResultBean.class);
                handleBeanResult(bean);
            }
        });
    }


    public abstract void handleBeanResult(NetResultBean bean);
}
