package com.kas.clientservice.haiyansmartenforce;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.hik.mcrsdk.MCRSDK;
import com.hik.mcrsdk.rtsp.RtspClient;
import com.hik.mcrsdk.talk.TalkClientSDK;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.display.FadeInBitmapDisplayer;
import io.rong.imkit.RongIM;
import io.rong.imlib.ipc.RongExceptionHandler;
import okhttp3.OkHttpClient;
import videotalk.UserVideoBean;
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
//    TODO：测试
    public List<UserVideoBean> list;

    public String currentUserID;

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
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10*1000L, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(60*1000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        SDKInitializer.initialize(this);
        initVedio();
//        TODO：测试
        initUserVideoBeanList();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            RongIM.setServerInfo("nav.cn.ronghub.com", "up.qbox.me");
            RongIM.init(this,"m7ua80gbmj3om");
            NLog.setDebug(true);
            SealAppContext.init(this);
            SharedPreferencesContext.init(this);
            Thread.setDefaultUncaughtExceptionHandler(new RongExceptionHandler(this));

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


    private void initUserVideoBeanList() {
        list=new ArrayList<>();
        list.add(new UserVideoBean("001","gEwvTAxzdBvzUQSFREClN7T153YKTX/aInUs3GxmGpPYI12QAOp1SO/9iYH+MGzdT3CUW48hHVPlJxjPqhMt6w=="));
        list.add(new UserVideoBean("002","z820ghZ+/3ZYc/YB1gj5dZ/6dQSXnPdNk3BbvNp1UIBsIQBxO8LQCK+R1ng1uhrsaSz5M0ClNLQn1ORugsTclg=="));
        list.add(new UserVideoBean("003","rUILrHrBBrjG+bwG/MfF0kAx0A1DlhrUCj72qmFPybzRH+Yypo7lr9ch4NPc8I655QE+cii4W1o="));
        list.add(new UserVideoBean("004","CP1YV00QbygmFRBtwbSQb7T153YKTX/aInUs3GxmGpPYI12QAOp1SG9mgERBRvBTEDTdSDMCEBHlJxjPqhMt6w=="));
        list.add(new UserVideoBean("005","bJltAhqpP1BiXjWmjK9Cg7T153YKTX/aInUs3GxmGpPYI12QAOp1SAmQjzX5tez+Q/VjBvfi3NzlJxjPqhMt6w=="));
        list.add(new UserVideoBean("006","60TnTLtAJDIZJLu+gJTC7kAx0A1DlhrUCj72qmFPybwSEaS4qr8uBvN1JpPHJBR4IBkQWE4w5Bo="));
        list.add(new UserVideoBean("007","3F8lryZ81UvYwe7RlIcngUAx0A1DlhrUCj72qmFPybwSEaS4qr8uBlJvDh7MuauQQ7Dy44yc0Tc="));
        list.add(new UserVideoBean("008","WaxRDKkGdIPC51E4E/WEJLT153YKTX/aInUs3GxmGpPYI12QAOp1SMw/dEqQk4YtaaSN7Wls/OzlJxjPqhMt6w=="));
        list.add(new UserVideoBean("009","/ZFuskMuMzMxBKegWICPH7T153YKTX/aInUs3GxmGpPYI12QAOp1SI1EBw7e/xXsDMT2InZmmM3lJxjPqhMt6w=="));
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
