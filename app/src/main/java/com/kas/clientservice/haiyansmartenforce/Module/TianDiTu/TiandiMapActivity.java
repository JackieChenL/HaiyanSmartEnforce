package com.kas.clientservice.haiyansmartenforce.Module.TianDiTu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.TGeoAddress;
import com.tianditu.android.maps.TGeoDecode;
import com.tianditu.android.maps.renderoption.DrawableOption;

import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;

public class TiandiMapActivity extends BaseActivity implements View.OnClickListener, TGeoDecode.OnGeoResultListener {
    @BindView(R.id.main_mapview)
    MapView mMapView;
    @BindView(R.id.et_tianditu_jingdu)
    EditText et_jingdu;
    @BindView(R.id.et_tianditu_weidu)
    EditText et_weidu;
    @BindView(R.id.tv_tianditu_dingwei)
    TextView tv_dingwei;
    @BindView(R.id.tv_tianditu_location)
    TextView tv_location;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;

    @BindView(R.id.tv_header_confirm)
    TextView tv_confirm;

    MyLocationOverlay mMyLocation;
    MapController mMapController;
    TGeoDecode tGeoDecode;
    String Longitude = "";
    String Latitude = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tiandi_map;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_confirm.setVisibility(View.VISIBLE);
        tv_dingwei.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        initTiandiTu();

    }

    private void initTiandiTu() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager
                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请打开GPS",
                    Toast.LENGTH_SHORT).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("请打开GPS");
            dialog.setPositiveButton("确定",
                    new android.content.DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 0); // 设置完成后返回到原来的界面

                        }
                    });
            dialog.setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        } else {
            Log.i(TAG, "hasGps: ");

            tGeoDecode = new TGeoDecode(this);
            //设置启用内置的缩放控件
            mMapView.setBuiltInZoomControls(true);
            //得到mMapView的控制权,可以用它控制和驱动平移和缩放
            mMapController = mMapView.getController();
            //设置地图zoom级别
            mMapController.setZoom(100);
            mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
            mMyLocation = new MyOverlay(this, mMapView);
            mMapView.addOverlay(mMyLocation);

            mMyLocation.enableCompass();//设置指南针
            //设置定位
            Log.i(TAG, "enableMyLocation: " + mMyLocation.enableMyLocation());
//            mMyLocation.setGpsFollow(true);//设置gps追踪


            GeoPoint point = getLocation();
            refreshLocation(point);
        }
    }


    public GeoPoint getLocation() {
        GeoPoint point = mMyLocation.getMyLocation();
        tGeoDecode.search(point);
        return point;
    }

    public void refreshLocation(GeoPoint point) {
        if (point != null) {
            mMapController.setCenter(point);
            et_weidu.setText(formLocationPoint(point.getLatitudeE6()));
            et_jingdu.setText(formLocationPoint(point.getLongitudeE6()) + "");
            Log.i(TAG, "经度: " + point.getLongitudeE6() + " 纬度：" + point.getLatitudeE6());
        } else {
            ToastUtils.showToast(mContext, "GPS信号弱，无法获取坐标");
        }
    }

    private MyLocationOverlay getOverlay() {
        return mMyLocation;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tianditu_dingwei:
                refreshLocation(new GeoPoint(parseStringToGeo(et_weidu.getText().toString()), parseStringToGeo(et_jingdu.getText().toString())));
                break;
            case R.id.tv_header_confirm:
                Intent intent = new Intent();
                intent.putExtra("Longitude",et_jingdu.getText().toString());
                intent.putExtra("Latitude",et_weidu.getText().toString());
                setResult(Constants.RESULTCODE_TIANDITU,intent);
                finish();
                break;
            case R.id.iv_heaer_back:
                finish();
                break;

        }
    }

    public int parseStringToGeo(String geo) {
        try {
            return (int) (Float.parseFloat(geo) * 1E6);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUtils.showToast(mContext, "请输入正确格式的经纬度");
            return 0;
        }
    }

    @Override
    public void onGeoDecodeResult(TGeoAddress tGeoAddress, int i) {
        tv_location.setText(tGeoAddress.getAddress());
    }

    class MyOverlay extends MyLocationOverlay {
        private Drawable mDrawable;
        private GeoPoint mGeoPoint;
        private DrawableOption mOption;

        public MyOverlay(Context context, MapView mapView) {
            super(context, mapView);
            mDrawable = getResources().getDrawable(R.drawable.selpointflag);
            mOption = new DrawableOption();
            mOption.setAnchor(0.3f, 0.8f);
        }

        public void setGeoPoint(GeoPoint point) {
            mGeoPoint = point;

        }

        /*
         * 处理在"我的位置"上的点击事件
         */
        protected boolean dispatchTap() {
            Log.i(TAG, "dispatchTap: ");

            return true;
        }

        @Override
        public void onLocationChanged(Location location) {
            super.onLocationChanged(location);
            Log.i(TAG, "onLocationChanged: " + location.getLongitude() + "  " + location.getLatitude() + " " + location.getProvider());
        }

        @Override
        public boolean onTap(GeoPoint p, MapView mapView) {
            tGeoDecode.search(p);
            mGeoPoint = p;
            et_jingdu.setText("" + formLocationPoint(p.getLongitudeE6()));
            et_weidu.setText("" + formLocationPoint(p.getLatitudeE6()));
//            mCbShowView.setChecked(true);
            return true;
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event, MapView mapView) {
//            mTvTips.setText("onKeyUp:" + keyCode);
            return super.onKeyUp(keyCode, event, mapView);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event, MapView mapView) {
//            mTvTips.setText("onKeyDown:" + keyCode);
            return super.onKeyDown(keyCode, event, mapView);
        }

        @Override
        public boolean draw(GL10 gl, MapView mapView, boolean shadow, long when) {
            MapViewRender render = mapView.getMapViewRender();
            render.drawDrawable(gl, mOption, mDrawable, mGeoPoint);
            return super.draw(gl, mapView, shadow, when);
        }

        @Override
        public boolean isVisible() {
            return super.isVisible();
        }

        @Override
        public void setVisible(boolean b) {
            super.setVisible(b);
        }

        @Override
        public boolean onLongPress(GeoPoint p, MapView mapView) {
            return super.onLongPress(p, mapView);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
//                    mTvTips.setText("ACTION_DOWN" + event.getX() + ","
//                            + event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
//                    mTvTips.setText("ACTION_MOVE" + event.getX() + ","
//                            + event.getY());
                    break;
                case MotionEvent.ACTION_UP:
//                    mTvTips.setText("ACTION_UP" + event.getX() + "," + event.getY());
                    break;
                default:
                    break;
            }
            return super.onTouchEvent(event, mapView);
        }
    }

    public String formLocationPoint(int i) {
        String s = i + "";
        if (s.length() >= 7) {
            return s.substring(0, s.length() - 6) + "." + s.substring(s.length() - 6, s.length());
        } else {
            return s;
        }

    }

}
