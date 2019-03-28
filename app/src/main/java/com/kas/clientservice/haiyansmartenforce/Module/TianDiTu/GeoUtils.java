package com.kas.clientservice.haiyansmartenforce.Module.TianDiTu;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class GeoUtils {
    private final static String TAG = "GeoUtils";
    private static final int ERRO_CODE = -100;
    private static final int EMPTY_CODE = -200;
    private static final String ERRO_ADDRESS = "";
    private static final String REPLACE_CITY = "浙江省嘉兴市";
    private static GeoUtils utils;
    private static final String SearchGeoUrl = "http://api.tianditu.gov.cn/geocoder";

    private GeoUtils() {

    }


    public static GeoUtils getInstance() {
        if (utils == null) {
            synchronized (GeoUtils.class) {
                utils = new GeoUtils();
            }
        }
        return utils;

    }


    public void searchGeo(final GeoPoint geoPoint, final SimpleStringCallBack callBack) {
        if (geoPoint == null || callBack == null) {
            throw new NullPointerException("geoPoint & callBack requires not null");
        }

        double lat = (double) geoPoint.getLatitudeE6() / 1e6;
        double lon = (double) geoPoint.getLongitudeE6() / 1e6;
        OkHttpUtils.post().url(SearchGeoUrl + "?type=geocode&postStr=" + JSON.toJSONString(new RequestBean(lon, lat)) + "&tk=249de20797a29314d308fc6a38cffe67")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, e.toString());
                callBack.onEasyStringBack(ERRO_CODE, ERRO_ADDRESS);
            }

            @Override
            public void onResponse(String response, int id) {
                if (TextUtils.isEmpty(response)) {
                    callBack.onEasyStringBack(EMPTY_CODE, ERRO_ADDRESS);
                } else {
                    try {
                        SearchGeoResultBean resultBean = JSON.parseObject(response, SearchGeoResultBean.class);
                        int retCode = Integer.parseInt(resultBean.status);
                        callBack.onEasyStringBack(retCode, retCode == 0 ?
                                resultBean.result.formatted_address.
                                        replace(REPLACE_CITY, "")
                                : ERRO_ADDRESS);
                    } catch (Exception e) {
                        callBack.onEasyStringBack(ERRO_CODE, "");
                    }
                }

            }
        });


    }


    public class RequestBean {


        /**
         * lon : 120.744235
         * lat : 30.769951
         * appkey : 5d46aa412a3480e09771e3797003565b
         * ver : 1
         */

        public double lon;
        public double lat;
        public int ver = 1;


        public RequestBean(double lon, double lat) {
            this.lon = lon;
            this.lat = lat;
        }
    }

    public static class SearchGeoResultBean {


        /**
         * result : {"formatted_address":"浙江省嘉兴市南湖区禾兴北路620号申银万国证券嘉兴营业部","location":{"lon":120.744235,"lat":30.769951},"addressComponent":{"address":"禾兴北路620号","city":"浙江省嘉兴市南湖区","road":"环城北路","poi_position":"东南","address_position":"东南","road_distance":0,"poi":"申银万国证券嘉兴营业部","poi_distance":"22","address_distance":22}}
         * msg : ok
         * status : 0
         */

        public ResultBean result;
        public String msg;
        public String status;

        public static class ResultBean {
            /**
             * formatted_address : 浙江省嘉兴市南湖区禾兴北路620号申银万国证券嘉兴营业部
             * location : {"lon":120.744235,"lat":30.769951}
             * addressComponent : {"address":"禾兴北路620号","city":"浙江省嘉兴市南湖区","road":"环城北路","poi_position":"东南","address_position":"东南","road_distance":0,"poi":"申银万国证券嘉兴营业部","poi_distance":"22","address_distance":22}
             */

            public String formatted_address;
            public LocationBean location;
            public AddressComponentBean addressComponent;

            public static class LocationBean {
                /**
                 * lon : 120.744235
                 * lat : 30.769951
                 */

                public double lon;
                public double lat;
            }

            public static class AddressComponentBean {
                /**
                 * address : 禾兴北路620号
                 * city : 浙江省嘉兴市南湖区
                 * road : 环城北路
                 * poi_position : 东南
                 * address_position : 东南
                 * road_distance : 0
                 * poi : 申银万国证券嘉兴营业部
                 * poi_distance : 22
                 * address_distance : 22
                 */

                public String address;
                public String city;
                public String road;
                public String poi_position;
                public String address_position;
                public int road_distance;
                public String poi;
                public String poi_distance;
                public int address_distance;
            }
        }
    }

    public interface SimpleStringCallBack {

        void onEasyStringBack(int code, String str);
    }


    public interface onLocationSuccessCallback {

        void onSuccess(GeoBean geoBean);
    }


    private MapView mapView;
    private MyLocationOverlay overlay;


    private void initMapView(Context context) {
        if (mapView == null) {
            mapView = new MapView(context, "");
            overlay = new MyLocationOverlay(context, mapView);
            overlay.enableMyLocation();
        }
    }

    public void startLocation(Context context, final onLocationSuccessCallback callback) {

        initMapView(context);
        final GeoPoint point = overlay.getMyLocation();
        if (point != null) {
            searchGeo(point, new SimpleStringCallBack() {
                @Override
                public void onEasyStringBack(int code, String str) {
                    callback.onSuccess(new GeoBean(point, str));
                }
            });

        } else {
            callback.onSuccess(new GeoBean(null));
        }

    }

    public void startLocationGPS(Context context, final onLocationSuccessCallback callback) {
        initMapView(context);
        final GeoPoint point = overlay.getMyLocation();
        callback.onSuccess(new GeoBean(point));

    }
}
