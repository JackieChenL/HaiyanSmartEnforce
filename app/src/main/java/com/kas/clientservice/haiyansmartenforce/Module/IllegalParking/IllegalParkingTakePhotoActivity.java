package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IllegalParkingTakePhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, IllegalParkingCommitImgRvAdapter.OnImageAddClickListener, IllegalParkingCommitImgRvAdapter.OnImagelickListener, View.OnClickListener {

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
    List<Bitmap> arr_image;
    IllegalParkingCommitImgRvAdapter adapter;
    private CompressConfig compressConfig;
    Uri uri;
    String time;
    String position;
    String carNum;
    String code;


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
//        Log.i(TAG, "onActivityResult: "+data.toString());
        Log.i(TAG, "onActivityResult: " + requestCode + "  " + resultCode);
//        if (requestCode == Constants.RESULTCODE_TIANDITU) {
//            longitude = data.getStringExtra("Longitude");
//            latitude = data.getStringExtra("Latitude");
//            Log.i(TAG, "onActivityResult: "+longitude+"  "+latitude);
//        }
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

        tv_title.setText("违法照片");
        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

        arr_image = new ArrayList<>();
        adapter = new IllegalParkingCommitImgRvAdapter(arr_image, mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnImageAddClickListener(this);
        adapter.setOnImagelickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void takeSuccess(String imagePath) {
        Log.i(TAG, "takeSuccess: " + imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath

        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 6, getResources().getColor(R.color.orange), 5, 5);
        Log.i(TAG, "takeSuccess: length=" + water_bitmap.getByteCount() / 1024);
        arr_image.add(water_bitmap);
//        setRecyclerViewHeight(arr_image.size());
        adapter.notifyDataSetChanged();
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
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        101);
            } else {
                uri = getImageCropUri();
//                cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
                //设置压缩参数
                compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(400).create();
                takePhoto.onEnableCompress(compressConfig, true); //设置为需要压缩
                takePhoto.onPickFromCapture(uri);
            }
        }
    }

    @Override
    public void onImageClick(int p) {
        Bitmap bmp = arr_image.get(p);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

//        Bundle b = new Bundle();
//        b.putByteArray("bitmap", bytes);
        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra("image", bytes);
        startActivity(intent);

    }

    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_parking_takePhoto_submit:

                commit();

                break;
            case R.id.tv_parking_takePhoto_back:
//                startActivityWithoutBack(mContext, MainActivity.class,null);
                finish();
                break;

        }
    }

    class ImgEntity {
        String WFimg;

        public ImgEntity(String WFimg) {
            this.WFimg = WFimg;
        }
    }

    private void commit() {
        String bitmap_string = BitmapToBase64.bitmapListToBase64(arr_image);
        OkHttpUtils.post().url("http://111.1.31.210:88/system/theme/anjuan/WFHandler.ashx")
                .addParams("ZFRYID", "1")
                .addParams("WFtime", time)
                .addParams("WFaddress", position)
                .addParams("WFAddressZB", "123.00,111.00")
                .addParams("Carnum", carNum)
                .addParams("Img", bitmap_string)
                .addParams("UpType", "enterprise")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
            }
        });
//                .

    }


}
