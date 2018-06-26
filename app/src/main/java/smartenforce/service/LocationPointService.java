package smartenforce.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.tianditu.android.maps.GeoPoint;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.base.HttpApi;
import smartenforce.bean.PointUpLoadBean;
import smartenforce.impl.MyStringCallBack;
import smartenforce.tianditu.TiandituUtil;
import smartenforce.util.DateUtil;


public class LocationPointService extends Service {
    private TiandituUtil tiandituUtil;
    private MyApplication app;
    private long lastTimeMillis;
    private long startTimeMillis;
    private List<PointUpLoadBean.TrajectorArryBean> list = new ArrayList();
    private PointUpLoadBean pointUpLoadBean;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            GeoPoint point = (GeoPoint) msg.obj;
            handler.postDelayed(LocationRunable, 5 * 1000);
            if (msg.what == 2) {
                upLoadPointGps();
                startTimeMillis = System.currentTimeMillis();
                list.clear();
            }

            if (point != null) {
                list.add(new PointUpLoadBean.TrajectorArryBean(((double) point.getLongitudeE6()) / 1000000 + "", ((double) point.getLatitudeE6()) / 1000000 + "", DateUtil.currentTime()));
            }

        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        app = (MyApplication) getApplication();
        handler.post(LocationRunable);
        Log.e("LocationPointService", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(LocationRunable);
        Log.e("LocationPointService", "onDestroy");

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return START_NOT_STICKY;
//    }

    private Runnable LocationRunable = new Runnable() {
        @Override
        public void run() {
            if (tiandituUtil == null) {
                tiandituUtil = new TiandituUtil(LocationPointService.this);
                tiandituUtil.setCallback(new TiandituUtil.onLocationSuccessCallback() {
                    @Override
                    public void onSuccess(GeoPoint point, String address) {
                        lastTimeMillis = System.currentTimeMillis();
                        if (startTimeMillis == 0) {
                            startTimeMillis = lastTimeMillis;
                        }
                        if (lastTimeMillis - startTimeMillis >= app.IntervalTimeTeI * 1000) {
                            handler.obtainMessage(2, point).sendToTarget();
                        } else {
                            handler.obtainMessage(1, point).sendToTarget();
                        }
                    }
                });
            }
            tiandituUtil.startLocationGPS();
        }
    };

    private void upLoadPointGps() {
        if (pointUpLoadBean == null) {
            pointUpLoadBean = new PointUpLoadBean();
        }
        pointUpLoadBean.TerminalID = app.TerminalID;
        pointUpLoadBean.UserID = app.userID;
        pointUpLoadBean.TrajectorArry = list;
        String TrajectorPostData = JSON.toJSONString(pointUpLoadBean);
        OkHttpUtils.post().url(HttpApi.URL_TRAJECTORYADD)
                .addParams("TrajectorPostData", TrajectorPostData)
                .build().execute(new MyStringCallBack() );

    }


}
