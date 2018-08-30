package com.kas.clientservice.haiyansmartenforce.Module.Vedio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.ControlUnitInfo;
import com.hikvision.vmsnetsdk.RegionInfo;
import com.hikvision.vmsnetsdk.ServInfo;
import com.hikvision.vmsnetsdk.VMSNetSDK;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VedioListActivity extends BaseActivity implements MsgCallback, AdapterView.OnItemClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.lv_vedioList)
    ListView listView;

    private ResourceControl rc;
    private List mList;
    private ResourceListAdapter mAdapter;
    private static final int GOTO_LIVE_OR_PLAYBACK = 0x0b;
    private int pResType = Constants.Resource.TYPE_UNKNOWN;
    Handler handler;
    /**
     * 父控制中心的id，只有当parentResType为TYPE_CTRL_UNIT才有用
     */
    private int pCtrlUnitId;
    /**
     * 父区域的id，只有当parentResType为TYPE_REGION才有用
     */
    private int pRegionId;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_vedio_list;
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

    String userName = "admin";
    String password = "Hik12345";
    String servAddr = "https://183.249.233.76:444";
    String macAddress = "";
    LiveControl mLiveControl;
    ServInfo servInfo = new ServInfo();
    Handler mHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    init();
                    break;
            }
        }
    };
    private void login() {
        Config.getIns().setServerAddr(servAddr);
        mLiveControl = new LiveControl();
        macAddress = getMacAddr();
        String mDomainAddress = clearDomainAddress(servAddr);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mDomainAddress = clearDomainAddress(servAddr);
                // 登录请求
                Log.i(TAG, "run: servAddr="+servAddr+" userName="+userName+" password="+password+ " mDomainAddress="+mDomainAddress);
                boolean ret = VMSNetSDK.getInstance().login(servAddr, userName, password, macAddress, servInfo, mDomainAddress);
                Log.i(TAG, "run: "+ret);
                if (ret) {
                    TempData.getInstance().setLoginData(servInfo);
                    Log.i(TAG, "run: "+gson.toJson(servInfo));

                    mHanlder.sendEmptyMessage(1);
                } else {
                    ToastUtils.showToast(mContext,"登录失败");
                    mHanlder.sendEmptyMessage(0);
                }

            }
        }).start();
    }

    public void init(){
        listView.setOnItemClickListener(this);
//		sv = (ScrollView) findViewById(R.id.mScrollView);
//		sv.smoothScrollTo(0, 0);
        mList = new ArrayList();
        handler = new MsgHandler();
        rc = new ResourceControl();
        rc.setCallback(this);

        mAdapter = new ResourceListAdapter(this ,mList);
        listView.setAdapter(mAdapter);
        reqResList();
    }

    /**
     * 请求资源列表
     */
    private void reqResList() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int pId = 0;
                if (Constants.Resource.TYPE_CTRL_UNIT == pResType) {
                    pId = pCtrlUnitId;
                } else if (Constants.Resource.TYPE_REGION == pResType) {
                    pId = pRegionId;
                }
                rc.reqResList(pResType, pId);
            }
        }).start();
    }

    @Override
    public void onMsg(int msgId, Object data) {
        Message msg = Message.obtain();
        msg.what = msgId;
        msg.obj = data;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Object itemData = mAdapter.getItem(i);
//		String itemName = getItemName(itemData);

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (itemData instanceof CameraInfo) {
                    CameraInfo info = (CameraInfo) itemData;
                    //得到摄像头，进行预览或者回放
                    Log.i(Constants.LOG_TAG,"get Camera:" + info.getName() + "---" + info.getDeviceID());
                    onMsg(GOTO_LIVE_OR_PLAYBACK,info);
                }else{
                    int pId = 0;
                    if (itemData instanceof ControlUnitInfo) {
                        ControlUnitInfo info = (ControlUnitInfo) itemData;
                        pResType = Constants.Resource.TYPE_CTRL_UNIT;
                        pId = Integer.parseInt(info.getControlUnitID());
                    }

                    if (itemData instanceof RegionInfo) {
                        RegionInfo info = (RegionInfo) itemData;
                        pResType = Constants.Resource.TYPE_REGION;
                        pId = Integer.parseInt(info.getRegionID());
                    }

                    rc.reqResList(pResType, pId);
                }

            }


        }).start();
    }
    /**
     * 获取数据成功后刷新列表
     *
     * @param data
     */
    private void refreshResList(List data) {
        if (data == null || data.isEmpty()) {
            ToastUtils.showToast(this, "数据为空");
            return;
        }
//        UIUtil.showToast(this, R.string.fetch_resource_suc);
//		if(mAdapter != null){
        mAdapter.setData(data);
        ListViewFitParent.setListViewHeightBasedOnChildren(listView);
//		}
    }
    @SuppressLint("HandlerLeak")
    private final class MsgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                // 获取控制中心列表成功
                case MsgIds.GET_C_F_NONE_SUC:

                    // 从控制中心获取下级资源列表成功
                case MsgIds.GET_SUB_F_C_SUC:

                    // 从区域获取下级列表成功
                case MsgIds.GET_SUB_F_R_SUC:
                    refreshResList((List)msg.obj);
                    break;

                // 获取控制中心列表失败
                case MsgIds.GET_C_F_NONE_FAIL:

                    // 调用getControlUnitList失败
                case MsgIds.GET_CU_F_CU_FAIL:

                    // 调用getRegionListFromCtrlUnit失败
                case MsgIds.GET_R_F_C_FAIL:

                    // 调用getCameraListFromCtrlUnit失败
                case MsgIds.GET_C_F_C_FAIL:

                    // 从控制中心获取下级资源列表成失败
                case MsgIds.GET_SUB_F_C_FAIL:

                    // 调用getRegionListFromRegion失败
                case MsgIds.GET_R_F_R_FAIL:

                    // 调用getCameraListFromRegion失败
                case MsgIds.GET_C_F_R_FAIL:

                    // 从区域获取下级列表失败
                case MsgIds.GET_SUB_F_R_FAILED:
//                    onGetResListFailed();
                    showNetErrorToast();
                    break;
                case GOTO_LIVE_OR_PLAYBACK:
                    CameraInfo cInfo = (CameraInfo)msg.obj;

                    gotoLive(cInfo);
                    break;
                default:
                    break;
            }
        }
    }
    private void gotoLive(CameraInfo info) {
        // TODO Auto-generated method stub
        if(info == null){
            Log.e(Constants.LOG_TAG,"gotoLive():: fail");
            return;
        }
        Intent intent = new Intent(this, VedioActivity.class);
        intent.putExtra(Constants.IntentKey.CAMERA_ID, info.getId());
        Log.i("tag", "gotoLive: "+new Gson().toJson(info));
        TempData.getIns().setCameraInfo(info);
        startActivity(intent);
    }

    protected String getMacAddr() {
        WifiManager wm = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
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
}
