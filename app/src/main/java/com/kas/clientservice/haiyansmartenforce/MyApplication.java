package com.kas.clientservice.haiyansmartenforce;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.hik.mcrsdk.MCRSDK;
import com.hik.mcrsdk.rtsp.RtspClient;
import com.hik.mcrsdk.talk.TalkClientSDK;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.constants.ZegoAvConfig;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import videotalk.ZegoAppHelper;
import videotalk.utils.PrefUtil;
import videotalk.utils.TimeUtil;

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



    final static private long DEFAULT_ZEGO_APP_ID = ZegoAppHelper.UDP_APP_ID;
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
        initVideoTalk();
    }

    private void initVedio() {
        MCRSDK.init();
        RtspClient.initLib();
        MCRSDK.setPrint(1, null);
        TalkClientSDK.initLib();
        VMSNetSDK.getInstance().openLog(true);
    }

    //视屏通话
    private void initVideoTalk() {

        initUserInfo();
        setupZegoSDK();

    }

    private void initUserInfo() {
        String userId = PrefUtil.getInstance().getUserId();
        String userName = PrefUtil.getInstance().getUserName();
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userName)) {
            userId = TimeUtil.getNowTimeStr();
            userName = String.format("VT_%s_%s", Build.MODEL.replaceAll(",", "."), userId);

            PrefUtil.getInstance().setUserId(userId);
            PrefUtil.getInstance().setUserName(userName);
        }
    }

    private void setupZegoSDK() {

        ZegoLiveRoom.setUser(PrefUtil.getInstance().getUserId(), PrefUtil.getInstance().getUserName());
        ZegoLiveRoom.requireHardwareEncoder(PrefUtil.getInstance().getHardwareEncode());
        ZegoLiveRoom.requireHardwareDecoder(PrefUtil.getInstance().getHardwareDecode());
        ZegoLiveRoom liveRoom= ZegoAppHelper.getLiveRoom();
        liveRoom.unInitSDK();
        liveRoom.setSDKContext(new ZegoLiveRoom.SDKContext() {
            @Override
            public String getSoFullPath() {
                return null;
            }

            @Override
            public String getLogPath() {
                return null;
            }

            @Override
            public Application getAppContext() {
                return MyApplication.this;
            }
        });


        initZegoSDK(liveRoom);


        ZegoAppHelper.saveLiveRoom(liveRoom);
    }


    private void initZegoSDK(ZegoLiveRoom liveRoom) {

        ZegoLiveRoom.setUser(PrefUtil.getInstance().getUserId(), PrefUtil.getInstance().getUserName());
        ZegoLiveRoom.requireHardwareEncoder(PrefUtil.getInstance().getHardwareEncode());
        ZegoLiveRoom.requireHardwareDecoder(PrefUtil.getInstance().getHardwareDecode());
        ZegoLiveRoom.setTestEnv(PrefUtil.getInstance().getTestEncode());

        byte[] signKey;
        long appId = 0;
        String strSignKey;
        int currentAppFlavor = PrefUtil.getInstance().getCurrentAppFlavor();
        if(currentAppFlavor == 2){

            appId = PrefUtil.getInstance().getAppId();
            strSignKey = PrefUtil.getInstance().getAppSignKey();

        }else{
            switch (currentAppFlavor) {
                case 0:
                    appId = ZegoAppHelper.UDP_APP_ID;
                    break;
                case 1:
                    appId = ZegoAppHelper.INTERNATIONAL_APP_ID;
                    break;

            }
            signKey = ZegoAppHelper.requestSignKey(appId);
            strSignKey = ZegoAppHelper.convertSignKey2String(signKey);
        }


        if (appId == 0 || TextUtils.isEmpty(strSignKey)) {
            appId = DEFAULT_ZEGO_APP_ID;
            PrefUtil.getInstance().setAppId(DEFAULT_ZEGO_APP_ID);

            signKey = ZegoAppHelper.requestSignKey(DEFAULT_ZEGO_APP_ID);
            strSignKey = ZegoAppHelper.convertSignKey2String(signKey);
            PrefUtil.getInstance().setAppSignKey(strSignKey);
        } else {
            signKey = ZegoAppHelper.parseSignKeyFromString(strSignKey);
        }

        boolean success = liveRoom.initSDK(appId, signKey);
        //设置视频通话类型
        ZegoLiveRoom.setBusinessType(2);
        //设置低延迟模式
        liveRoom.setLatencyMode(ZegoConstants.LatencyMode.Low);

        if (!success) {
            Toast.makeText(this, R.string.vt_toast_init_sdk_failed, Toast.LENGTH_LONG).show();
        } else {
            ZegoAvConfig config;
            int level = PrefUtil.getInstance().getLiveQuality();
            if (level < 0 || level > ZegoAvConfig.Level.SuperHigh) {
                config = new ZegoAvConfig(ZegoAvConfig.Level.High);
                config.setVideoBitrate(PrefUtil.getInstance().getLiveQualityBitrate());
                config.setVideoFPS(PrefUtil.getInstance().getLiveQualityFps());
                int resolutionLevel = PrefUtil.getInstance().getLiveQualityResolution();

                String resolutionText = getResources().getStringArray(R.array.zg_resolutions)[resolutionLevel];
                String[] strWidthHeight = resolutionText.split("x");

                int height = Integer.parseInt(strWidthHeight[0].trim());
                int width = Integer.parseInt(strWidthHeight[1].trim());
                config.setVideoEncodeResolution(width, height);
                config.setVideoCaptureResolution(width, height);
            } else {
                config = new ZegoAvConfig(level);
            }
            liveRoom.setAVConfig(config);
        }

    }


}
