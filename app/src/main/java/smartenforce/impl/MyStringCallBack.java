package smartenforce.impl;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import smartenforce.util.LogUtil;


public class MyStringCallBack extends StringCallback {

    @Override
    public void onError(Call call, Exception e, int id) {
        LogUtil.e("ERRO", e.getMessage() + "\n" + e.getCause());
    }

    @Override
    public void onResponse(String response, int id) {
        LogUtil.e("onResponse", response);
    }
}
