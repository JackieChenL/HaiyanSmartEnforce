package com.kas.clientservice.haiyansmartenforce.Module.TianDiTu;


import com.tianditu.android.maps.GeoPoint;

import java.io.Serializable;

public class GeoBean implements Serializable {
    public int lat = 0;
    public int lon = 0;
    public String address = "";

    public GeoBean(GeoPoint geopoint) {
        this(geopoint, null);
    }

    public GeoBean(GeoPoint geoPoint, String address) {
          if (geoPoint!=null){
              this.lat=geoPoint.getLatitudeE6();
              this.lon=geoPoint.getLongitudeE6();
              this.address=address;
          }
    }

    public int parseInt(String geo) {
      int result=0;
        try{
            result=(int)(Double.parseDouble(geo)*1e6) ;
        }catch (Exception e){
            result=0;
        }
        return result;
    }


    /**
     *
     * @param lng 120.1234
     * @param lat 30.1111
     * @param address “abcd”
     * @return
     */
    public GeoBean parse(String lng,String lat, String address) {
        this.address=address;
        try{
            this.lon=(int)(Double.parseDouble(lng)*1e6) ;
            this.lat=(int)(Double.parseDouble(lat)*1e6) ;
        }catch (Exception e){
            this.lat=0;
            this.lon=0;

        }

        return this;
    }

    /**
     * @return 120.12345,30.122343.
     */
    public String parseGpxXY(){
      return  ((double) lon) / 1e6 + "," +((double) lat) / 1e6 ;
    }

    /**
     * @return 30.122343,120.12345
     */
    public String parseGpxYX(){
        return  ((double) lat) / 1e6 + "," + ((double) lon) / 1e6;
    }
}
