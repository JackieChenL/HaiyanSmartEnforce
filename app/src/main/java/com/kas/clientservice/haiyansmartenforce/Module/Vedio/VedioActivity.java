package com.kas.clientservice.haiyansmartenforce.Module.Vedio;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hikvision.vmsnetsdk.RealPlayURL;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

public class VedioActivity extends BaseActivity implements SurfaceHolder.Callback, LiveCallBack {
    @BindView(R.id.surfaceView)
    SurfaceView mSurfaceView;
    String userName = "admin";
    String password = "Hik12345";
    String servAddr = "https://183.249.229.227.1443";
    String macAddress = "";
    ServInfo servInfo;
    LiveControl mLiveControl;
    private ServInfo mServInfo;
    //rtsp://183.249.233.76:556/realplay://001063:SUB:TCP?cnid=4&pnid=1&token=ST-42853-X3kvKe9poA4SPU5OnuiQ-cas&auth=50&redirect=0&transcode=0
    String url = "rtsp://183.249.229.227:556/realplay://001425:SUB:TCP?cnid=3&pnid=1&auth=50&redirect=0&transcode=0";
    private ResourceControl rc;
    VMSNetSDK mVmsNetSDK;
    RealPlayURL mRealPlayURL;
    Handler handler = new Handler(){
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

        login();

    }
    private void init() {
//        mServInfo = TempData.getIns().getLoginData();
//        mVmsNetSDK = VMSNetSDK.getInstance();
//        mRealPlayURL = new RealPlayURL();
//        mToken =this.mVmsNetSDK.getPlayToken(mServInfo.getSessionID());
//        servInfo = new ServInfo();
        mLiveControl = new LiveControl();
        mLiveControl.setLiveCallBack(this);
        mSurfaceView.getHolder().addCallback(this);

        mLiveControl.setLiveParams(url, "admin", "GDwE7ZPPaHs=");
        mLiveControl.startLive(mSurfaceView);

    }


    private void login() {
        macAddress = getMacAddr();
        String mDomainAddress = clearDomainAddress(servAddr);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mDomainAddress = clearDomainAddress(servAddr);
                // 登录请求
                boolean ret = VMSNetSDK.getInstance().login(servAddr, userName, password, macAddress, servInfo, mDomainAddress);

                if (ret) {
                    TempData.getInstance().setLoginData(servInfo);
                    Log.i(TAG, "run: "+gson.toJson(servInfo));
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(0);
                }

            }
        }).start();
    }

    protected String getMacAddr() {
        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac == null ? "" : mac;
    }

    private String clearDomainAddress(String domainAddress) {
        if (TextUtils.isEmpty(domainAddress)) {
            return null;
        }
        String ipAddress = "";
        //兼容http://开头的地址格式
        if (domainAddress.contains("http://") || domainAddress.contains("https://")) {
            String[] splits = domainAddress.split("//");
            if (splits.length >= 2) {
                domainAddress = splits[1];
            }
        }
        if (domainAddress.contains(":")) {//例：10.33.27.240:81或10.33.27.240:81/msp无法解析
            String[] str_address = domainAddress.split(":");
            ipAddress = str_address[0];
            return ipAddress;
        } else if (!domainAddress.contains(":") && domainAddress.contains("/")) {//例如10.33.27.240/msp无法解析
            String[] str_address = domainAddress.split("/");
            ipAddress = str_address[0];
            return ipAddress;
        }
        return domainAddress;
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


}
