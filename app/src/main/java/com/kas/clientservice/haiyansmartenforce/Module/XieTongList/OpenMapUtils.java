package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;


import java.io.File;

import smartenforce.util.StringUtil;

public class OpenMapUtils {


    public static boolean isBaiduMapInstalled(){
        return isInstallPackage("com.baidu.BaiduMap");
    }

    private static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    public static void doRouteSearch(Context context,String LatLng, String address){
      String Url=  "baidumap://map/direction?region=嘉兴&destination=%1$s&mode=driving";
        if (!StringUtil.isEmptyString(LatLng)){
            Url=  String.format(Url,LatLng);
        }else if (!StringUtil.isEmptyString(address)){
            Url=  String.format(Url,address);
        }
        Intent i1 = new Intent();
        i1.setData(Uri.parse(Url));
        context.startActivity(i1);
    }
}
