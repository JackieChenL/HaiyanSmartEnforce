package smartenforce.impl;


import android.app.Activity;
import android.content.Context;

import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;
import smartenforce.util.LogUtil;
import smartenforce.widget.ProgressDialogUtil;

public abstract class DownloadFileCallBack extends FileCallBack {
    private Context context;

    public DownloadFileCallBack(Context context, String destFileDir, String destFileName) {
        super(destFileDir, destFileName);
        this.context = context;
    }

    @Override
    public void onError(final Call call, Exception e, int id) {
        LogUtil.e("ERRO:", e.getMessage() + "\n" + e.getCause());
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
//                if (call.isCanceled()) {
//                } else {
                onFileDownLoadCallBack(null, "下载失败", false);
//                }
            }
        });


    }


    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        ProgressDialogUtil.show(context, "文件下载中...");
    }


    @Override
    public void inProgress(float progress, long total, int id) {
        super.inProgress(progress, total, id);
    }


    @Override
    public void onResponse(final File file, int id) {
        LogUtil.e("onResponse:", file.getAbsolutePath());
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
                onFileDownLoadCallBack(file, "下载成功", true);
            }
        });

    }

    public abstract void onFileDownLoadCallBack(File file, String msg, boolean isSuccess);
}
