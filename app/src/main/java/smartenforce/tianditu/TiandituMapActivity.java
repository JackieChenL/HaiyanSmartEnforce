package smartenforce.tianditu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.kas.clientservice.haiyansmartenforce.R;
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

public class TiandituMapActivity extends ShowTitleActivity implements OnGeoResultListener {
    private MapView mapView;
    protected MapController mController = null;

    private GeoPoint center ;
    private TGeoDecode decode = null;
    private String address = "未知地点";
    private OverItemT mOverlay = null;
    private Drawable marker = null;
    private int lat,lon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tianditu);
    }

    protected void findViews() {
        mapView = (MapView) findViewById(R.id.map);
    }

    protected void initDataAndAction() {
        tev_title_right.setText("确定");
        lat=getIntent().getIntExtra("LAT",0);
        lon=getIntent().getIntExtra("LON",0);
        address=getIntent().getStringExtra("ADDRESS");
        if ((lat==0)&&(lon==0)){
            address="未知地点";
            center = new GeoPoint(30527890, 120942910);
        }else{
            center = new GeoPoint(lat, lon);
        }
        tev_title.setText(address);
        mapView.setPlaceName(true);
        mapView.setMapType(MapView.TMapType.MAP_TYPE_VEC);
        mapView.setBuiltInZoomControls(true);
        mapView.displayZoomControls(true);
        mController = mapView.getController();
        mapView.setMinZoomLevel(5);
        mController.setZoom(16);
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
                intent.putExtra("ADDRESS",address);
                setResult(RESULT_OK,intent);
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
            address =arg0.getCity().replace("浙江省嘉兴市","")+ arg0.getAddress();
        }
        tev_title.setText(address);
    }
}
