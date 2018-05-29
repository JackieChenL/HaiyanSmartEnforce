package com.kas.clientservice.haiyansmartenforce.Module.Update;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.kas.clientservice.haiyansmartenforce.Entity.AppVersionInfo;
import com.kas.clientservice.haiyansmartenforce.Module.Login.LoginActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateManager {
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    //无需下载
    private static final int DOWNLOAD_NOT = 3;
    //直接登陆
    private static final int LOGINING = 4;

    private static final int REQUEST_FOR_INSTALL = 1;

    private static final String APK_NAKE = "YUEZHAN.apk";

    private final static int REQUEST_SUCCESS = 200;

    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;

    private AppVersionInfo appVersion;

    private Handler mHandler;
    String TAG = "UpdateManager";

//    private Login mListener;

    public boolean flag = false;

    public boolean isDownloading = false;

    public UpdateManager(Context context) {
        this.mContext = context;

        this.mHandler = new Handler(mContext.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    // 正在下载
                    case DOWNLOAD:
                        // 设置进度条位置
//                        Log.i(TAG, "handleMessage: "+progress);
                        mProgress.setProgress(progress);
                        break;
                    case DOWNLOAD_FINISH:
                        // 安装文件
                        installApk();
                        break;
                    case DOWNLOAD_NOT:
                        ToastUtils.showToast(mContext, "目前已是最新版本！");
                        break;
                    case LOGINING://登陆
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        ((Activity)mContext).finish();
//                        if (mListener != null)
//                            mListener.Login();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 设置回掉
     *
     * @param listener
     */
//    public void setLoginListener(Login listener) {
//        mListener = listener;
//    }

    /**
     * 版本号比较函数
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compare(String version1, String version2) {
        String[] subVersions1 = version1.trim().split("\\.");
        String[] subVersions2 = version2.trim().split("\\.");
        int compareLength = Math.min(subVersions1.length, subVersions2.length);
        for (int i = 0; i < compareLength; i++) {
            int subVersion1 = Integer.valueOf(subVersions1[i]);
            int subVersion2 = Integer.valueOf(subVersions2[i]);
            if (subVersion1 < subVersion2) {
                return -1;
            } else if (subVersion1 > subVersion2) {
                return 1;
            }
        }
        if (subVersions1.length == subVersions2.length) {
            return 0;
        } else {
            return (subVersions1.length < subVersions2.length ? -1 : 1);
        }
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate() {
        String versionName = GetAppInfo.getAppVersionName(mContext);
        appVersion = GetAppInfo.getAppVersionInfo(mContext);
        Log.i(TAG, "checkUpdate: "+versionName);
        if (appVersion != null) {
            Looper.prepare();

            if (compare(appVersion.AppVersion, versionName) > 0) {
                Log.i(TAG, "checkUpdate: 版本不匹配");
//                if (appVersion.Update == 1) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    ActivityCompat.requestPermissions((Activity) mContext,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }
                if (!isDownloading) {
                    showNoticeDialog();
                }else {
                    ToastUtils.showToast(mContext,"下载中请勿离开当前页面");
                }
            } else {
                Log.i(TAG, "checkUpdate: 版本匹配");
                if (flag)
                    mHandler.sendEmptyMessageDelayed(LOGINING,2000);
                else
                    mHandler.sendEmptyMessage(DOWNLOAD_NOT);
            }

            Looper.loop();
        } else {
            mHandler.sendEmptyMessageDelayed(LOGINING,2000);
        }
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("检测到新版本，需要立即更新吗");
        // 更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                isDownloading = true;
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        // 稍后更新
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (appVersion.Update == 1) {
                    ((Activity) mContext).finish();
                    ToastUtils.showToast(mContext,"亲，记得回来更新喔！");
                } else if (flag)
                    mHandler.sendEmptyMessage(LOGINING);
                else
                    dialog.dismiss();
            }
        });
        AlertDialog noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        Log.i(TAG, "showDownloadDialog: ");
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新中");
        ToastUtils.showToast(mContext,"下载中请勿离开当前页面");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
                isDownloading = false;
                if (flag)
                    ((Activity) mContext).finish();
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.setCanceledOnTouchOutside(false);
        mDownloadDialog.show();
        // 下载文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, APK_NAKE);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        ((Activity) mContext).startActivityForResult(intent, REQUEST_FOR_INSTALL);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            Log.i(TAG, "run: ");
            int code = 0;

            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Log.i(TAG, "run: hasSDcard");
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";

                    Log.i(TAG, "run: "+appVersion.DownloadUrl);
                    URL url = new URL(appVersion.DownloadUrl);

                    // 创建连接
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    code = conn.getResponseCode();
                    Log.i(TAG, "run: "+code);
                    if (code == REQUEST_SUCCESS) {
                        // 获取文件大小
                        int length = conn.getContentLength();
                        // 创建输入流
                        InputStream is = conn.getInputStream();

                        File file = new File(mSavePath);
                        // 判断文件目录是否存在
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        File apkFile = new File(mSavePath, APK_NAKE);
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        // 缓存
                        byte buf[] = new byte[1024];
                        // 写入到文件中
                        do {
                            int numread = is.read(buf);
                            count += numread;
                            // 计算进度条位置
                            progress = (int) (((float) count / length) * 100);
                            // 更新进度
                            mHandler.sendEmptyMessage(DOWNLOAD);
                            if (numread <= 0) {
                                // 下载完成
                                mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            // 写入文件
                            fos.write(buf, 0, numread);
                        } while (!cancelUpdate);// 点击取消就停止下载.
                        fos.close();
                        is.close();
                    }
                    conn.disconnect();
                }else {
                    Log.i(TAG, "run: "+Environment.getExternalStorageState());
                }
            } catch (MalformedURLException e) {
                Log.i(TAG, "run: "+e.toString());
                //ToastUtils.showToast(mContext, e.getMessage());
            } catch (IOException e) {
                Log.i(TAG, "run: "+e.toString());
                //ToastUtils.showToast(mContext, e.getMessage());
            }

            // 取消下载对话框显示
            Log.i(TAG, "run: dismiss");
            mDownloadDialog.dismiss();
        }
    }
}