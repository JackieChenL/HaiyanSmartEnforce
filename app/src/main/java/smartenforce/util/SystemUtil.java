package smartenforce.util;


import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;


public class SystemUtil {

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }


    public static int getVersionCode(Context c) {
        int versionCode = 0;
        try {
            versionCode = c.getPackageManager().getPackageInfo(c.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }


    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
