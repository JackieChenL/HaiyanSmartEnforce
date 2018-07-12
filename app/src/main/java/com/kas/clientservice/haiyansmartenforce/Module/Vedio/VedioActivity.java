package com.kas.clientservice.haiyansmartenforce.Module.Vedio;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hik.mcrsdk.rtsp.LiveInfo;
import com.hik.mcrsdk.rtsp.RtspClient;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.CameraInfoEx;
import com.hikvision.vmsnetsdk.RealPlayURL;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.hikvision.vmsnetsdk.netLayer.msp.deviceInfo.DeviceInfo;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

public class VedioActivity extends BaseActivity implements SurfaceHolder.Callback, LiveCallBack, View.OnClickListener, View.OnTouchListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.surfaceView)
    SurfaceView mSurfaceView;
    @BindView(R.id.tv_vedio_play)
    TextView tv_play;
    @BindView(R.id.tv_vedio_left)
    TextView tv_left;
    @BindView(R.id.tv_vedio_right)
    TextView tv_right;
    @BindView(R.id.tv_vedio_Top)
    TextView tv_top;
    @BindView(R.id.tv_vedio_bottom)
    TextView tv_bottom;
    @BindView(R.id.tv_vedio_leftTop)
    TextView tv_leftTop;
    @BindView(R.id.tv_vedio_rightTop)
    TextView tv_rightTop;
    @BindView(R.id.tv_vedio_leftBottom)
    TextView tv_leftBottom;
    @BindView(R.id.tv_vedio_rightBottom)
    TextView tv_rightBottm;
    @BindView(R.id.tv_vedio_stopControl)
            TextView tv_stop;
    @BindView(R.id.tv_vedio_fangda)
            TextView tv_fangda;
    @BindView(R.id.tv_vedio_suoxiao)
            TextView tv_suoxiao;

    ServInfo servInfo = new ServInfo();
    LiveControl mLiveControl;
    private ServInfo mServInfo;
    String url = "rtsp://183.249.229.227:556/realplay://001425:SUB:TCP?cnid=3&pnid=1&auth=50&redirect=0&transcode=0";
    private ResourceControl rc;
    RealPlayURL mRealPlayURL;
    private CameraInfo cameraInfo;
    private String mCameraID = null;
    private CameraInfoEx cameraInfoEx;
    private String mDeviceID = "";
    private DeviceInfo deviceInfo;
    private RtspClient mRtspHandle = null;
    /**
     * 获取监控点详情结果
     */
    private boolean getCameraDetailInfoResult = false;

    /**
     * 获取设备详情结果
     */
    private boolean getDeviceInfoResult = false;
    private VMSNetSDK mVmsNetSDK = null;
    private String mName;
    private String mPassword;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    init();
                    break;
                case 1:
                    requestList();
                    break;
            }
        }
    };


    private String mToken;

    private void requestList() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vedio;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_play.setOnClickListener(this);
        tv_leftTop.setOnTouchListener(this);
        tv_top.setOnTouchListener(this);
        tv_rightTop.setOnTouchListener(this);
        tv_left.setOnTouchListener(this);
        tv_leftBottom.setOnTouchListener(this);
        tv_right.setOnTouchListener(this);
        tv_rightBottm.setOnTouchListener(this);
        tv_bottom.setOnTouchListener(this);
        tv_stop.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_fangda.setOnTouchListener(this);
        tv_suoxiao.setOnTouchListener(this);
        init();
//        login();

    }

    private void init() {
//        mServInfo = TempData.getIns().getLoginData();
//        mVmsNetSDK = VMSNetSDK.getInstance();
//        mRealPlayURL = new RealPlayURL();
//        mToken =this.mVmsNetSDK.getPlayToken(mServInfo.getSessionID());
        mServInfo = TempData.getIns().getLoginData();
        mRealPlayURL = new RealPlayURL();
        mLiveControl = new LiveControl();
        mLiveControl.setLiveCallBack(this);
        cameraInfo = TempData.getIns().getCameraInfo();

        mCameraID = cameraInfo.getId();
        tv_title.setText(cameraInfo.getName());
        cameraInfoEx = new CameraInfoEx();
        cameraInfoEx.setId(mCameraID);

        mVmsNetSDK = VMSNetSDK.getInstance();
        if (mVmsNetSDK == null) {
            Log.e(Constants.LOG_TAG, "mVmsNetSDK is null");
            return;
        }
        String serAddr = Config.getIns().getServerAddr();
        String sessionid = mServInfo.getSessionID();

        getCameraDetailInfo(serAddr, sessionid);
        mRtspHandle = RtspClient.getInstance();
        if (null == mRtspHandle) {
            Log.e(Constants.LOG_TAG, "initialize:" + "RealPlay mRtspHandle is null!");
            return;
        }


        mSurfaceView.getHolder().addCallback(this);


//        mLiveControl.setLiveParams(url, "admin", "GDwE7ZPPaHs=");
//        mLiveControl.startLive(mSurfaceView);

    }

    /**
     * 获取监控点详情方法
     *
     * @param serAddr   服务器地址
     * @param sessionid 会话ID
     */
    private void getCameraDetailInfo(final String serAddr, final String sessionid) {
        Log.i(TAG, "getCameraDetailInfo: session" + sessionid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getCameraDetailInfoResult =
                        mVmsNetSDK.getCameraInfoEx(serAddr, sessionid, mCameraID, cameraInfoEx);
                Log.i(Constants.LOG_TAG, "result is :" + getCameraDetailInfoResult);

                mDeviceID = cameraInfoEx.getDeviceId();
                Log.i(Constants.LOG_TAG, "mDeviceID is :" + mDeviceID);
                deviceInfo = new DeviceInfo();

                // 获取设备信息
                getDeviceInfoResult =
                        mVmsNetSDK.getDeviceInfo(serAddr, sessionid, mDeviceID, deviceInfo);
                if (!getDeviceInfoResult || null == deviceInfo || TextUtils.isEmpty(deviceInfo.getLoginName())
                        || TextUtils.isEmpty(deviceInfo.getLoginPsw())) {
                    deviceInfo.setLoginName("admin");
                    deviceInfo.setLoginPsw("12345");
                }
                mName = deviceInfo.getLoginName();
                mPassword = deviceInfo.getLoginPsw();

                DebugLog.info(Constants.LOG_TAG,
                        "ret is :" + getDeviceInfoResult + "----------------" + deviceInfo.getDeviceName() + "--------"
                                + "deviceLoginName is " + mName + "---" + "deviceLoginPassword is " + mPassword + "-----"
                                + "deviceID is " + mDeviceID);

            }
        }).start();

    }

    /**
     * 启动播放 void
     *
     * @since V1.0
     */
    private int mStreamType = -1;

    private void startBtnOnClick() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                mLiveControl.setLiveParams(getPlayUrl(mStreamType), mName, mPassword);
                Log.i("tag", "userinfo: " + mName + "  " + mPassword);
                if (mLiveControl.LIVE_PLAY == mLiveControl.getLiveState()) {
                    mLiveControl.stop();
                }

                if (mLiveControl.LIVE_INIT == mLiveControl.getLiveState()) {
                    mLiveControl.startLive(mSurfaceView);
                }
            }
        }.start();
    }

    private String mUrl = "";
    private String mDeviceUserName = "";
    private String mDevicePassword = "";

    public void setLiveParams(String url, String name, String password) {
        mUrl = url;
        mDeviceUserName = name;
        mDevicePassword = password;
    }

    private String getPlayUrl(int streamType) {
        String url = "";

        if (mRealPlayURL == null) {
            return null;
        }

        // 获取播放Token
        if (mServInfo.isTokenVerify()) {
            mToken = mVmsNetSDK.getPlayToken(mServInfo.getSessionID());
            DebugLog.info(Constants.LOG_TAG, "mToken is :" + mToken);
        }
        Log.d(Constants.LOG_TAG, "generateLiveUrl MagStreamSerAddr:" + mServInfo.getMagServer().getMagStreamSerAddr());
        Log.d(Constants.LOG_TAG, "generateLiveUrl MagStreamSerPort:" + mServInfo.getMagServer().getMagStreamSerPort());
        Log.d(Constants.LOG_TAG, "generateLiveUrl cameraId:" + cameraInfoEx.getId());
        Log.d(Constants.LOG_TAG, "generateLiveUrl token:" + mToken);
        Log.d(Constants.LOG_TAG, "generateLiveUrl streamType:" + streamType);
        Log.d(Constants.LOG_TAG, "generateLiveUrl appNetId:" + mServInfo.getAppNetId());
        Log.d(Constants.LOG_TAG, "generateLiveUrl deviceNetID:" + cameraInfoEx.getDeviceNetId());
        Log.d(Constants.LOG_TAG, "generateLiveUrl userAuthority:" + mServInfo.getUserAuthority());
        Log.d(Constants.LOG_TAG, "generateLiveUrl cascadeFlag:" + cameraInfoEx.getCascadeFlag());
        Log.d(Constants.LOG_TAG, "generateLiveUrl internet:" + mServInfo.isInternet());

        LiveInfo liveInfo = new LiveInfo();
        liveInfo.setMagIp(mServInfo.getMagServer().getMagStreamSerAddr());
        liveInfo.setMagPort(mServInfo.getMagServer().getMagStreamSerPort());
        liveInfo.setCameraIndexCode(cameraInfoEx.getId());
        if (mServInfo.isTokenVerify()) {
            liveInfo.setToken(mToken);
        } else {
            liveInfo.setToken(null);
        }

        // 转码不区分主子码流
        liveInfo.setStreamType(streamType);
        liveInfo.setMcuNetID(mServInfo.getAppNetId());
        liveInfo.setDeviceNetID(cameraInfoEx.getDeviceNetId());
        liveInfo.setiPriority(mServInfo.getUserAuthority());
        liveInfo.setCascadeFlag(cameraInfoEx.getCascadeFlag());

        if (deviceInfo != null) {
            if (cameraInfoEx.getCascadeFlag() == LiveInfo.CASCADE_TYPE_YES) {
                deviceInfo.setLoginName("admin");
                deviceInfo.setLoginPsw("12345");
            }
        }

        if (mServInfo.isInternet()) {
            liveInfo.setIsInternet(LiveInfo.NETWORK_TYPE_INTERNET);
            // 获取不转码地址
            liveInfo.setbTranscode(false);
            mRealPlayURL.setUrl1(mRtspHandle.generateLiveUrl(liveInfo));

            // 获取转码地址
            // 使用默认转码参数cif 128 15 h264 ps
            liveInfo.setbTranscode(true);
            mRealPlayURL.setUrl2(mRtspHandle.generateLiveUrl(liveInfo));
        } else {
            liveInfo.setIsInternet(LiveInfo.NETWORK_TYPE_LOCAL);
            liveInfo.setbTranscode(false);
            // 内网不转码
            mRealPlayURL.setUrl1(mRtspHandle.generateLiveUrl(liveInfo));
            mRealPlayURL.setUrl2("");
        }

        Log.d(Constants.LOG_TAG, "url1:" + mRealPlayURL.getUrl1());
        Log.d(Constants.LOG_TAG, "url2:" + mRealPlayURL.getUrl2());

        url = mRealPlayURL.getUrl1();
        if (streamType == 2 && mRealPlayURL.getUrl2() != null && mRealPlayURL.getUrl2().length() > 0) {
            url = mRealPlayURL.getUrl2();
        }
        Log.i(Constants.LOG_TAG, "mRTSPUrl" + url);
        Log.i("tag", "getPlayUrl: " + url);
        return url;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onMessageCallback(int message) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_vedio_play:
                startBtnOnClick();
                break;
            case R.id.tv_vedio_stopControl:
                stopCloudCtrl();
                break;
        }
    }
    private void sendCtrlCmd(final int gestureID) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sessionID = mServInfo.getSessionID();
                // 云台控制速度 取值范围(1-10)
                int speed = 3;
//                Log.i(Constants.LOG_TAG, "ip:" + cameraInfoEx.getAcsIP() + ",port:" + cameraInfoEx.getAcsPort()
//                        + ",isPTZControl:" + mUserCap.contains(PTZ_CONTROL));
                // 发送控制命令
                boolean ret =
                        mVmsNetSDK.sendStartPTZCmd(cameraInfoEx.getAcsIP(),
                                cameraInfoEx.getAcsPort(),
                                sessionID,
                                mCameraID,
                                gestureID,
                                speed,
                                100, cameraInfoEx.getCascadeFlag() + "");
                Log.i(Constants.LOG_TAG, "sendStartPTZCmd ret:" + ret);
            }
        }).start();
    }
    private void stopCloudCtrl() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sessionID = mServInfo.getSessionID();
                boolean ret =
                        mVmsNetSDK.sendStopPTZCmd(cameraInfoEx.getAcsIP(),
                                cameraInfoEx.getAcsPort(),
                                sessionID,
                                mCameraID, cameraInfoEx.getCascadeFlag() + "");
                Log.i(Constants.LOG_TAG, "stopPtzCmd sent,ret:" + ret);
            }
        }).start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case(R.id.tv_vedio_leftTop):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(11);
                        break;
                }

                break;
            case(R.id.tv_vedio_left):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(3);
                        break;
                }
                break;
            case(R.id.tv_vedio_right):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(4);
                        break;
                }
                break;
            case(R.id.tv_vedio_Top):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(1);
                        break;
                }
                break;
            case(R.id.tv_vedio_bottom):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(2);
                        break;
                }
                break;
//            case(R.id.tv_vedio_leftTop):
//                sendCtrlCmd(11);
//                break;
            case(R.id.tv_vedio_rightTop):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(12);
                        break;
                }
                break;
            case(R.id.tv_vedio_leftBottom):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(13);
                        break;
                }
                break;
            case(R.id.tv_vedio_rightBottom):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(14);
                        break;
                }
                break;
            case(R.id.tv_vedio_fangda):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(7);
                        break;
                }
                break;
            case(R.id.tv_vedio_suoxiao):
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        stopCloudCtrl();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        sendCtrlCmd(8);
                        break;
                }
                break;
        }
        return true;
    }
}
