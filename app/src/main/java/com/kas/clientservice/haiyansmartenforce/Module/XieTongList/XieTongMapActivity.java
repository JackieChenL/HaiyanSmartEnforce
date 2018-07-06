package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.TGeoAddress;
import com.tianditu.android.maps.TGeoDecode;
import com.tianditu.android.maps.TGeoDecode.OnGeoResultListener;

import java.util.ArrayList;
import java.util.List;

import smartenforce.base.ShowTitleActivity;

public class XieTongMapActivity extends ShowTitleActivity implements OnGeoResultListener {
    private MapView mapView;
    protected MapController mController = null;

    private GeoPoint center ;
    private TGeoDecode decode = null;
    private String address = "未知地点";
    private OverItemT mOverlay = null;
    private Drawable marker = null;
    private int lat,lon;
    String fid;
    String xtaddress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tianditu);
    }

    protected void findViews() {
        mapView = (MapView) findViewById(R.id.map);
    }

    protected void initDataAndAction() {
        tev_title_right.setText("导航");
        fid=getIntent().getStringExtra("fid");
        String[] xy=fid.split(",");
        lat=(int) (Double.parseDouble(xy[0])*1E6);
        lon=(int) (Double.parseDouble(xy[1])*1E6);


        xtaddress=getIntent().getStringExtra("xtaddress");
        if ((lat==0)&&(lon==0)){
            address="未知地点";
            center = new GeoPoint(30546346, 120960597);
            ToastUtils.showToast(this,"坐标不准确");
        }else{
//            center = new GeoPoint((int) (Double.parseDouble(xy[1])*1E6),(int) (Double.parseDouble(xy[0])*1E6));
            center = new GeoPoint(lat,lon);
        }
        tev_title.setText(xtaddress);
        mapView.setPlaceName(true);
        mapView.setMapType(MapView.TMapType.MAP_TYPE_VEC);
        mapView.setBuiltInZoomControls(true);
        mapView.displayZoomControls(true);
        mController = mapView.getController();
        mapView.setMinZoomLevel(5);
        mController.setZoom(14);
        mController.setCenter(center);
        decode = new TGeoDecode(this);

        marker = getResources().getDrawable(R.drawable.mypointflag);
        mapView.getOverlays().add(new MySelectorOverlay());
        mOverlay = new OverItemT(marker);
        OverlayItem mItem = new OverlayItem(center, "Tap", center.toString());
        mOverlay.addItem(mItem);
        mapView.addOverlay(mOverlay);
        mapView.postInvalidate();


        tev_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("LAT",lat);
                intent.putExtra("LON",lon);
//                intent.putExtra("ADDRESS",address);
                intent.putExtra("fid",fid);
                intent.putExtra("xtaddress",xtaddress);
                setResult(RESULT_OK,intent);
                if(OpenMapUtils.isBaiduMapInstalled()) {
                    if(center==null||center.equals("")){
                        ToastUtils.showToast(XieTongMapActivity.this,"此数据无坐标，无法进行导航");
//                        break;
                    }else {
                        OpenMapUtils.doRouteSearch(XieTongMapActivity.this,fid,xtaddress );
//                        //table.area + table.street + table.square+table.address
                    }
                }else{
                    ToastUtils.showToast(XieTongMapActivity.this,"请先下载百度地图");
//                    break;
                }
                finish();

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }






    public class MySelectorOverlay extends Overlay {
        private OverlayItem mItem = null;
        @Override
        public boolean onTap(GeoPoint point, MapView mapView) {
            lat=point.getLatitudeE6();
            lon=point.getLongitudeE6();
            mController.animateTo(point);
            decode.search(point);
            mapView.getOverlays().remove(mOverlay);
            mOverlay = new OverItemT(marker);
            mItem = new OverlayItem(point, "Tap", point.toString());
            mOverlay.addItem(mItem);
            mapView.addOverlay(mOverlay);
            mapView.postInvalidate();
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

    @Override
    public void onGeoDecodeResult(TGeoAddress arg0, int arg1) {
        if (arg1 == 0) {
            address =arg0.getCity()+ arg0.getAddress();
        }
        tev_title.setText(address);
    }
}
