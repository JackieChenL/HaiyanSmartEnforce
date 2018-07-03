package smartenforce.tianditu;


import android.content.Context;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.TGeoAddress;
import com.tianditu.android.maps.TGeoDecode;

public class TiandituUtil implements TGeoDecode.OnGeoResultListener {
    private MapView mapView;
    private TGeoDecode mGeoDecode;
    private MyLocationOverlay overlay;

    public void setCallback(onLocationSuccessCallback callback) {
        this.callback = callback;
    }

    private onLocationSuccessCallback callback;
    private GeoPoint point;
    private String address;

    public TiandituUtil(Context context) {
        mapView = new MapView(context, "");
        overlay = new MyLocationOverlay(context, mapView);
        mGeoDecode = new TGeoDecode(this);
        overlay.enableMyLocation();
    }

    public void startLocation() {
        if (callback == null)
            return;
        point = overlay.getMyLocation();
        if (point != null) {
            mGeoDecode.search(point);
        } else {
            callback.onSuccess(null, null);
        }

    }

    public void startLocationGPS() {
        if (callback == null)
            return;
        point = overlay.getMyLocation();
        callback.onSuccess(point, null);

    }

    @Override
    public void onGeoDecodeResult(TGeoAddress tGeoAddress, int i) {
        if (i == 0) {
            address = tGeoAddress.getCity().replace("浙江省嘉兴市","") + tGeoAddress.getAddress();
        } else {
            address = "未知地点";
        }
        callback.onSuccess(point, address);
    }


    public interface onLocationSuccessCallback {

        void onSuccess(GeoPoint point, String address);
    }


}
