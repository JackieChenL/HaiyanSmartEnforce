package com.kas.clientservice.haiyansmartenforce.Base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import smartenforce.base.CommonActivity;
import smartenforce.projectutil.UpLoadImageUtil;


public abstract class AbsPhotoActivity extends CommonActivity implements TakePhoto.TakeResultListener {
    protected TextView tev_title, tev_title_right;
    protected ImageView imv_back;
    private TakePhoto takePhoto;
    protected String CURRENT_BASE64;

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = new TakePhotoImpl(this, this);
        }
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(200 * 1024).setMaxPixel(1200).create();
        takePhoto.onEnableCompress(compressConfig, true);
        return takePhoto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }




    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        findViews();
        //TODO
//        tev_title = (TextView) findViewById(R.id.tev_title);
//        imv_back = (ImageView) findViewById(R.id.imv_back);
//        tev_title_right = (TextView) findViewById(R.id.tev_title_right);
        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initDataAndAction();
        checkPermission();
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    100);
        } else {
            //查看图片权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        101);
            }
        }

    }

    private int imageType = -1;
    private AlertView alertView;
    private  onImageUpLoadSuccess uploadSuccessCallback;

    protected void showChoose(int TYPE,onImageUpLoadSuccess callback) {
        imageType = TYPE;
        uploadSuccessCallback=callback;
        if (alertView == null) {
            takePhoto = getTakePhoto();
            alertView = new AlertView.Builder().setContext(this)
                    .setStyle(AlertView.Style.ActionSheet)
                    .setTitle("选择操作")
                    .setMessage(null)
                    .setCancelText("取消")
                    .setOthers(new String[]{"拍照", "从相册中选择"})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            alertView.dismissImmediately();
                            if (position == 0) {
                                takePhoto.onPickFromCapture(getImageCropUri());
                            } else if (position == 1) {
                                takePhoto.onPickFromGallery();
                            }
                        }
                    }).build();
        }

        alertView.show();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void takeSuccess(String imagePath) {

        Log.i(TAG, "takeSuccess: " + imagePath);
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, option);//filePath
        Bitmap waterMap = WaterMaskImageUtil.drawTextToRightBottom(aty, bmp, getTime(), 8, getResources().getColor(R.color.orange), 5, 5);
//        String filePath = saveImageToLocal(waterMap, this);
        CURRENT_BASE64 = BitmapToBase64.bitmapToBase64(waterMap);
//        UpLoadImageUtil.uploadImageObj(this, UserSingleton.USERINFO.getZhzf().getUserID(), "enterprise", CURRENT_BASE64, new UpLoadImageUtil.onUploadImgCallBack() {
//            @Override
//            public void onSuccess(String picArray) {
//                if (uploadSuccessCallback!=null){
//                    uploadSuccessCallback.onSuccess(picArray,imageType);
//                }
//
//            }
//
//            @Override
//            public void onFail(String msg) {
//                show(msg);
//            }
//        });


    }

    public static Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "kas/img/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return Uri.fromFile(file);
    }

    public static String saveImageToLocal(Bitmap bitmap, Context context) {
        File file = new File(Environment.getExternalStorageDirectory(), "kas/img/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (bitmap == null) {
            ToastUtils.showToast(context,"图片不存在");
            return null;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        try {
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "saveImageToLocal: " + file.getPath());
        return file.getPath();
    }


    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    @Override
    public void takeFail(String msg) {
        Log.i(TAG, "takeFail: " + msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, "takeCancel");
    }


    protected abstract void findViews();


    protected abstract void initDataAndAction();


    public interface onImageUpLoadSuccess{
        void onSuccess(String imgUrl, int imgType);
    }
}
