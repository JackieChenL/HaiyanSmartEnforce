package com.kas.clientservice.haiyansmartenforce.Http;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kas.clientservice.haiyansmartenforce.Utils.NetUtils;

import rx.Subscriber;

public abstract class MySubscriber<T> extends Subscriber<T> {

    private Context context;
    ProgressDialog loadingDialog;

    public MySubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoadingDialog();
        Log.i("tag","MySubscriber.onStart()");
        //接下来可以检查网络连接等操作
        if (!NetUtils.isConnected(context)) {

            Toast.makeText(context, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            // 一定好主动调用下面这一句,取消本次Subscriber订阅
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            return;
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("tag","MySubscriber.throwable ="+e.toString());
        Log.e("tag","MySubscriber.throwable ="+e.getMessage());
        dismissLoadingDialog();
        if(e instanceof Exception){
            //访问获得对应的Exception
            onError(ExceptionHandle.handleException(e,context));
        }else {
            //将Throwable 和 未知错误的status code返回
            onError(new ExceptionHandle.ResponeThrowable(e,ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable responeThrowable);

    @Override
    public void onCompleted() {
        dismissLoadingDialog();
        Log.i("tag","MySubscriber.onComplete()");
    }


    public void showLoadingDialog() {

        if (loadingDialog == null) {

            loadingDialog = new ProgressDialog(context);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setMessage("加载中...");
        }
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {

        if (loadingDialog != null) {

            loadingDialog.dismiss();

        }


    }
}