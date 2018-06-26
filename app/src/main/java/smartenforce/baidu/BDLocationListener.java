package smartenforce.baidu;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


public class BDLocationListener extends BDAbstractLocationListener {
   private  LocationClient mLocationClient;
   private  onLocationSuccess listener;

    public BDLocationListener(Context context ) {
        mLocationClient=new LocationClient(context);
        mLocationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");

//        option.setScanSpan(1000);
        option.setOpenGps(true);

        option.setLocationNotify(true);

        option.setIgnoreKillProcess(false);

        option.SetIgnoreCacheException(false);

        option.setWifiCacheTimeOut(5*60*1000);

        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (listener!=null){
            listener.onReceiveLocation(bdLocation);
        }
        stopLocation();
    }

    public void startLocation(){
        mLocationClient.start();
    }

    public void stopLocation(){
        mLocationClient.stop();
    }

    public void setListener(onLocationSuccess listener) {
        this.listener = listener;
    }

    public interface onLocationSuccess{
        void onReceiveLocation(BDLocation bdLocation);
    }
}
