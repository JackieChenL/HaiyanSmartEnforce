package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.kas.clientservice.haiyansmartenforce.API.ZhuanXiangZhengZhiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.Img;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.MainActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.StringUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import smartenforce.base.CommonActivity;
import smartenforce.projectutil.Base64Util;
import smartenforce.util.ToastUtil;

import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.getImageCropUri;
import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.saveImageToLocal;

public class IllegalParkingTakePhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, UrlParkingAdapter.OnImageAddClickListener,View.OnClickListener{

    @BindView(R.id.rv_parking_takePhoto)
    RecyclerView recyclerView;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.tv_parking_takePhoto_submit)
    TextView tv_submit;
    @BindView(R.id.tv_parking_takePhoto_back)
    TextView tv_back;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;

    TakePhoto takePhoto;
    List<TimeImgUrlBean> arr_uri = new ArrayList<>();
    UrlParkingAdapter adapter;
    private CompressConfig compressConfig;
    Uri uri;
    String time = "";
    String position = "";
    String carNum = "";
    String code = "";
    String roadId = "";


    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = new TakePhotoImpl(this, this);
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: " + requestCode + "  " + resultCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_illegal_parking_take_photo;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        time = getIntent().getStringExtra("Time");
        carNum = getIntent().getStringExtra("CarNum");
        position = getIntent().getStringExtra("Position");
        code = getIntent().getStringExtra("Code");
        roadId = getIntent().getStringExtra("RoadId");
        tv_title.setText("违法照片");
        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);


        adapter =new UrlParkingAdapter(arr_uri, mContext);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnImageAddClickListener(this);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void takeSuccess(String imagePath) {
        Log.i(TAG, "takeSuccess: " + imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath
        Log.i(TAG, "takeSuccess: length=" + bmp.getByteCount() / 1024 / 128);
        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 8, getResources().getColor(R.color.orange), 5, 5);
        Log.i(TAG, "takeSuccess: length=" + water_bitmap.getByteCount() / 1024 / 128);
        saveImageToLocal(water_bitmap, mContext);


        uploadImg(water_bitmap);

    }

    @Override
    public void takeFail(String msg) {
        ToastUtils.showToast(mContext, "拍摄失败");
    }

    @Override
    public void takeCancel() {
        ToastUtils.showToast(mContext, "拍摄取消");
    }

    @Override
    public void onImageAddClick() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    100);
        } else {
            //查看图片权限
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        101);
            } else {
                if (arr_uri.size()>=3){
                    showToast("最多拍摄三张图片");
                }else{
                    uri = getImageCropUri();
//                cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
                    //设置压缩参数
                    compressConfig = new CompressConfig.Builder().setMaxSize(Constants.PIC_MAXSIZE * 1024).setMaxPixel(Constants.COMPRESSRATE).create();
                    takePhoto.onEnableCompress(compressConfig, true); //设置为需要压缩
                    takePhoto.onPickFromCapture(uri);
                }

            }
        }
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_parking_takePhoto_submit:
                if (arr_uri.size() <3) {
                    ToastUtils.showToast(mContext, "至少拍摄三张图片");
                }
                else {
                        String imgUrls=adapter.getUrl();
                        commit(imgUrls);
                }

                break;
            case R.id.tv_parking_takePhoto_back:
                finish();
                break;

        }
    }

    private void uploadImg(Bitmap bmp) {
      final  String img =BitmapToBase64.bitmapToBase64(bmp);
        RetrofitClient.createService(ZhuanXiangZhengZhiAPI.class, RequestUrl.baseUrl_leader)
                .httpZXZZimg(img,"-1","citizen")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<ZhuanXiangZhengZhiAPI.UploadImgEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(ZhuanXiangZhengZhiAPI.UploadImgEntity s) {
                        Log.i(TAG, "onNext: "+gson.toJson(s));
                        arr_uri.add(new TimeImgUrlBean(s.getKS().get(0)));
                        adapter.notifyDataSetChanged();

                    }
                });
    }




    private void commit(String substring) {
        showLoadingDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("ZFRYID", UserSingleton.USERINFO.getLawEnforcementOfficialsId());
        params.put("WFtime", time + ":00");
        params.put("WFaddress", position);
        params.put("WFAddressZB", roadId);
        params.put("Carnum", carNum);
        params.put("Img", substring);
        params.put("UpType", "enterprise");
        params.put("jdsnum", code);
        Log.e("paramsMap",params.toString());
        OkHttpUtils.post().url(RetrofitClient.mBaseUrl + "system/theme/anjuan/WFHandler.ashx")
            .params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
                ToastUtil.show(mContext, "上传失败");
                dismissLoadingDialog();
            }

            @Override
            public void onResponse(final String response, int id) {
                dismissLoadingDialog();
                Log.i(TAG, "onResponse: " + response);
                BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                if (baseEntity.isState()) {

                    ToastUtils.showToast(mContext, "上传成功");
                    startActivityWithoutBack(mContext, MainActivity.class, null);
                } else {
                    ToastUtils.showToast(mContext,baseEntity.ErrorMsg);
                }
            }


        });


    }


}
