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
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.renderoption.DrawableOption;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import butterknife.BindInt;
import butterknife.BindView;

public class TiandiMapActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.main_mapview)
    MapView mMapView;
    @BindView(R.id.tev_tianditu_jingdu)
    TextView tev_jingdu;
    @BindView(R.id.tev_tianditu_weidu)
    TextView tev_weidu;
    @BindView(R.id.tv_tianditu_dingwei)
    TextView tv_dingwei;
    @BindView(R.id.tv_tianditu_location)
    TextView tv_location;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_header_title)
    TextView tv_header_title;

    @BindView(R.id.tv_header_confirm)
    TextView tv_confirm;


    MapController mMapController;


    private GeoBean geoBean;
    private GeoPoint center;
    private OverItemT mOverlay = null;
    private Drawable marker = null;
    private MyLocationOverlay myLocationOverlay;

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
        tv_header_title.setText("地图");
        tv_confirm.setVisibility(View.VISIBLE);
        tv_dingwei.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        marker = getResources().getDrawable(R.drawable.selpointflag);
        //设置启用内置的缩放控件
        mMapView.setBuiltInZoomControls(true);
        //得到mMapView的控制权,可以用它控制和驱动平移和缩放
        mMapController = mMapView.getController();
        //设置地图zoom级别
        mMapController.setZoom(18);
        mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
        mMapView.getOverlays().add(new MySelectorOverlay());
        geoBean = (GeoBean) getIntent().getSerializableExtra("GeoBean");
        initTiandiTu();

    }

    private void initTiandiTu() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请打开GPS",
                    Toast.LENGTH_SHORT).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("请打开GPS");
            dialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 0); // 设置完成后返回到原来的界面

                        }
                    });
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        } else {
            if (geoBean.lat == 0) {
                getMyLocation();
            } else {
                center = new GeoPoint(geoBean.lat, geoBean.lon);
                tev_weidu.setText((double) center.getLatitudeE6() / 1e6 + "");
                tev_jingdu.setText((double) center.getLongitudeE6() / 1e6 + "");
                tv_location.setText(geoBean.address);
                moveToPoint(center);
            }

        }
    }



    private void getMyLocation(){
        try {
            myLocationOverlay = new MyLocationOverlay(this, mMapView);
            myLocationOverlay.enableMyLocation();
            center = myLocationOverlay.getMyLocation();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (center == null) {
                center = new GeoPoint(30527890, 120942910);
            }
            searchPoint(center);
        }


    }
    private void moveToPoint(GeoPoint point){
        center=point;
        mMapController.setCenter(point);
        if (mOverlay!=null){
            mMapView.getOverlays().remove(mOverlay);
        }
        mOverlay = new OverItemT(marker);
        OverlayItem mItem = new OverlayItem(point, "Tap", point.toString());
        mOverlay.addItem(mItem);
        mMapView.addOverlay(mOverlay);
        mMapView.postInvalidate();

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tianditu_dingwei:
                getMyLocation();
                break;
            case R.id.tv_header_confirm:
                Intent intent = new Intent();
                String lng=tev_jingdu.getText().toString().trim();
                String lat=tev_weidu.getText().toString().trim();
                String address=tv_location.getText().toString().trim();
                intent.putExtra("Longitude",lng);
                intent.putExtra("Latitude",lat);
                intent.putExtra("Address",address);
                geoBean.parse(lng,lat,address);
                intent.putExtra("GeoBean",geoBean);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.iv_heaer_back:
                finish();
                break;

        }
    }


    public class MySelectorOverlay extends Overlay {

        @Override
        public boolean onTap(GeoPoint point, MapView mapView) {

            searchPoint(point);


            return true;
        }


    }

    class OverItemT extends ItemizedOverlay<OverlayItem> {
        private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
        private Drawable mMaker = null;

        public OverItemT(Drawable marker) {
            super(marker);
            mMaker = boundCenterBottom(marker);

        }

        public void addItem(OverlayItem item) {
            item.setMarker(mMaker);
            GeoList.clear();
            GeoList.add(item);
            populate();
        }

        @Override
        protected OverlayItem createItem(int i) {
            return GeoList.get(i);
        }

        @Override
        public int size() {
            return GeoList.size();
        }
    }

    private void searchPoint(final GeoPoint point) {
        moveToPoint(point);
        GeoUtils.getInstance().searchGeo(point, new GeoUtils.SimpleStringCallBack() {
            @Override
            public void onEasyStringBack(int code, String str) {
                tev_weidu.setText((double) center.getLatitudeE6() / 1e6 + "");
                tev_jingdu.setText((double) center.getLongitudeE6() / 1e6 + "");
                tv_location.setText(str);
            }
        });
    }
}
