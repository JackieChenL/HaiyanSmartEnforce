package com.kas.clientservice.haiyansmartenforce;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import android.support.multidex.MultiDex;


import com.hik.mcrsdk.MCRSDK;
import com.hik.mcrsdk.rtsp.RtspClient;
import com.hik.mcrsdk.talk.TalkClientSDK;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import io.rong.imkit.RongIM;

import okhttp3.OkHttpClient;


import smartenforce.projectutil.IDCardUtil;

import videotalk.im.SealAppContext;
import videotalk.im.SealUserInfoManager;
import videotalk.im.message.provider.ContactNotificationMessageProvider;
import videotalk.im.server.utils.NLog;
import videotalk.im.utils.SharedPreferencesContext;

/**
 * 描述：
 * 时间：2018-04-18
 * 公司：COMS
 */

public class MyApplication extends Application {


    public String userID;
    public String DepartmentID;
    public String NameEmp;
    public String NameDep;
    public String AddressDep;


    public int TerminalID;
    public int IntervalTimeTeI;



    static private Application sInstance;
    static public Application getAppContext() {
        return MyApplication.sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10*1000L, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60*1000L, TimeUnit.MILLISECONDS)
//                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                    @Override
//                    public void log(String message) {
//                        Log.e("HttpLoggingInterceptor",message);
//                    }
//                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        OkHttpUtils.initClient(okHttpClient);
        initVedio();
        //不緩存，直接在app启动初始化获取一下token
        IDCardUtil.getInstance().getAuthToken();

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            RongIM.setServerInfo("nav.cn.ronghub.com", "up.qbox.me");
            RongIM.init(this,"m7ua80gbmj3om");
            NLog.setDebug(true);
            SealAppContext.init(this);
            SharedPreferencesContext.init(this);
//            Thread.setDefaultUncaughtExceptionHandler(new RongExceptionHandler(this));

            try {
                RongIM.registerMessageTemplate(new ContactNotificationMessageProvider());


            } catch (Exception e) {
                e.printStackTrace();
            }
            SealUserInfoManager.getInstance().openDB();


        }
    }

    private void initVedio() {
        MCRSDK.init();
        RtspClient.initLib();
        MCRSDK.setPrint(1, null);
        TalkClientSDK.initLib();
        VMSNetSDK.getInstance().openLog(true);
    }






    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
