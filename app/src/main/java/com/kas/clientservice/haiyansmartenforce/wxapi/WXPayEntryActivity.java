package com.kas.clientservice.haiyansmartenforce.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import smartenforce.aty.personrepay.Constants;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }


    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp baseResp) {
        Intent intent=new Intent();
        intent.putExtra("errCode",baseResp.errCode);
        String errMsg="";
        switch (baseResp.errCode){
            case 0:
                errMsg="付款成功";
                break;
            case -2:
                errMsg="付款取消";
                break;
            case -1:
                errMsg="付款失败";
                break;
        }
        intent.putExtra("errMsg",errMsg);
        startActivity(intent);
        finish();
    }
}
