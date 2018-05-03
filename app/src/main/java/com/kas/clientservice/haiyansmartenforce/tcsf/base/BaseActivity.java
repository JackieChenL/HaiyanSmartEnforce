package com.kas.clientservice.haiyansmartenforce.tcsf.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.PermissonCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ToastUtil;


public abstract class BaseActivity extends AppCompatActivity {
    protected PermissonCallBack callBack;
    protected int requestCode;
    protected Activity aty;
    protected MyApplication app;
    protected final static String TAG="TAG";


   protected  interface Pid{
       int CAMERA=1001;
       int FILE=1002;
       int LOCATION=1003;
   }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        aty = this;
        app= (MyApplication) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    if (callBack != null)
//                      callBack.onPermissionDenied();
                      // TODO:具体权限被禁细分
                    ToastUtil.show(aty,"该权限为必要权限，如禁止可能导致程序异常");
                } else {
                    if (callBack != null)
                        callBack.onPerMissionSuccess();
                }
            }
        }
    }




    protected void goToAppSetting(int requestCode) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, requestCode);
    }


    protected void requestPermission(String permissionName, int requestPermissionCode, PermissonCallBack callBack) {
        if (callBack == null) {
            throw new NullPointerException("请求权限回调不能为空!");
        }
        this.callBack = callBack;
        this.requestCode = requestPermissionCode;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, permissionName);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permissionName}, requestPermissionCode);
            } else {
                callBack.onPerMissionSuccess();
            }
        } else {
            callBack.onPerMissionSuccess();
        }
    }


}
