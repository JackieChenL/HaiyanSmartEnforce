package com.kas.clientservice.haiyansmartenforce.Module.Update;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.kas.clientservice.haiyansmartenforce.API.PhoneAppVersionAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.AppVersionInfo;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Utils.Logger;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import retrofit2.Call;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GetAppInfo {
    public static AppVersionInfo appServerVersion;

    /**
     * 获取APP名
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageInfo pi = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appName = pi.applicationInfo.loadLabel(context.getPackageManager()).toString();
            if (appName == null || appName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    /**
     * 获取当前APP版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageInfo pi = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取目录下的APK名字
     *
     * @param context
     * @param apkPath
     * @return
     */
    public static String getAPKPackageName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            return appInfo.packageName;
        }
        return null;
    }

    /**
     * 获取app icon
     *
     * @param context
     * @return
     */
    public static Drawable getAppIcon(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取服务器版本信息
     */
    public static void getServerAppVersionInfo(final Context context) {
        RetrofitClient.createService(PhoneAppVersionAPI.class)
                .httpsPhoneAppVersionRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseEntity<AppVersionInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(context, e.getMessage());
                    }

                    @Override
                    public void onNext(BaseEntity<AppVersionInfo> appVersionEntity) {
                        Logger.d();

                        appServerVersion = appVersionEntity.getRtn();
                    }
                });
    }

    /**
     * 获取最新版本号
     * @param context
     */
    public static AppVersionInfo getAppVersionInfo(final Context context) {
        try {
            PhoneAppVersionAPI service = RetrofitClient.createService(PhoneAppVersionAPI.class);
            Call<BaseEntity<AppVersionInfo>> call = service.httpsPhoneAppVersion();

            BaseEntity<AppVersionInfo> bean = call.execute().body();

            return bean.getRtn();
        } catch (Exception ex) {
            return null;
        }
    }

}
