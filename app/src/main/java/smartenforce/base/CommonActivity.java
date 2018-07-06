package smartenforce.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.User.UserInfo;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;

import java.util.List;

import smartenforce.intf.ItemListener;
import smartenforce.intf.PermissonCallBack;
import smartenforce.util.LogUtil;
import smartenforce.util.StringUtil;
import smartenforce.util.ToastUtil;
import smartenforce.widget.ProgressDialogUtil;


public abstract class CommonActivity extends AppCompatActivity {
    protected PermissonCallBack callBack;
    protected int requestCode;
    protected Activity aty;
    protected MyApplication app;
    protected final static String TAG = "TAG";
    protected Handler handler = new Handler();

    protected interface Pid {
        int CAMERA = 1001;
        int FILE = 1002;
        int LOCATION = 1003;

    }

    protected interface REQUESTCODE {
        int SCAN_IDCARD = 1;


        int LOCATION = 2;
        int DSR = 3;


        int CAMERA_PRE = 4;
        int CAMERA = 5;
        int CAMERA_AFTER = 6;


        int ALBUM_PRE = 7;
        int ALBUM = 8;
        int ALBUM_AFTER = 9;

        int TIANDITU_PICTURE = 10;

        int PERSON_SINGLE = 12;


        int CHECKED_ENTERPRISE = 13;
        int CHECKED_CITIZEN = 14;
        int LIVE_MAN = 15;
        int OTHER_MAN = 16;


        int ADD_GOODS = 17;

        int RELEASE_ENFORSE = 18;

    }


    protected String getOpername() {
        return UserSingleton.getInstance().getUserAccount(aty);
    }

    protected List<UserInfo.TollCollectorBean.RoadBean> getRoadBeanList() {
        return UserSingleton.USERINFO.getTollCollector().getRoad();
    }
    protected String getZFRYID() {
        return UserSingleton.USERINFO.getTollCollectorID();
    }

    protected void log(String msg) {
        LogUtil.e(TAG, msg + "-END-");
    }

    protected void log(String TAG, String MSG) {
        LogUtil.e(TAG, MSG + "-END-");
    }

    protected void show(String MSG) {
        ToastUtil.show(aty, MSG);
    }

    protected void warningShow(String MSG) {
        new AlertView("提醒", MSG, "确定", null, null, aty, AlertView.Style.Alert, null
        ).show();
    }

    AlertView alterView;


    protected void warningShowAndAction(final String MSG, int delay, final ItemListener listener) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alterView = new AlertView("提醒", MSG, null, null, new String[]{"取消", "确定"}, aty, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        alterView.dismiss();
                        if (position == 1 && listener != null) {
                            listener.onItemClick(1);
                        }
                    }
                }
                );
                alterView.show();
            }
        }, delay);

    }

    protected void closeKeybord() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getWindow().getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
    }

    protected void openKeybord() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


    protected String getText(TextView tev) {
        return tev.getText().toString().trim();
    }

    protected boolean isEmpty(TextView tev) {
        return tev == null || StringUtil.isEmptyString(tev.getText().toString().trim());
    }

    protected boolean isEmpty(String value) {
        return StringUtil.isEmptyString(value);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //防截屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        aty = this;
        app = (MyApplication) getApplication();
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


//    protected boolean isForeground() {
//        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
//        if (!tasks.isEmpty()) {
//            ComponentName topActivity = tasks.get(0).topActivity;
//            if (topActivity.getPackageName().equals(getPackageName())) {
//                return true;
//            }
//        }
//        return false;
//    }


    @Override
    protected void onDestroy() {
        ProgressDialogUtil.hide();
        super.onDestroy();
        closeKeybord();
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    new AlertView("提醒", "该权限为必要权限，如禁止可能导致程序异常，请手动打开权限!", "确定", null, null, aty, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            goToAppSetting(requestCode);
                        }
                    }
                    ).show();
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


    protected void requestPermissionGroup(int requestPermissionCode, PermissonCallBack callBack) {
        if (callBack == null) {
            throw new NullPointerException("请求权限回调不能为空!");
        }
        this.callBack = callBack;
        this.requestCode = requestPermissionCode;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (requestPermissionCode) {
                case Pid.CAMERA:
                    int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (camera != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, requestPermissionCode);
                    } else {
                        callBack.onPerMissionSuccess();
                    }
                    break;
                case Pid.FILE:
                    int file = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (file != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestPermissionCode);
                    } else {
                        callBack.onPerMissionSuccess();
                    }
                    break;
                case Pid.LOCATION:
                    int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    if (location != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, requestPermissionCode);
                    } else {
                        callBack.onPerMissionSuccess();
                    }
                    break;
            }
        } else {
            callBack.onPerMissionSuccess();
        }
    }


}
