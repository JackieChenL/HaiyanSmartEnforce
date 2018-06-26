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
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.MainActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import smartenforce.util.ToastUtil;

import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.getImageCropUri;
import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.saveImageToLocal;

public class IllegalParkingTakePhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, IllegalParkingCommitImgRvAdapter.OnImageAddClickListener, IllegalParkingCommitImgRvAdapter.OnImagelickListener, View.OnClickListener, IllegalParkingCommitImgRvAdapter.OnImgDeleteClickListener {

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
    List<String> arr_uri = new ArrayList<>();
    IllegalParkingCommitImgRvAdapter adapter;
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
        roadId = getIntent().getStringExtra("RoadId");
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
        adapter.setOnImgDeleteClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void takeSuccess(String imagePath) {
        Log.i(TAG, "takeSuccess: " + imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath
        Log.i(TAG, "takeSuccess: length=" + bmp.getByteCount() / 1024 / 128);
        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 8, getResources().getColor(R.color.orange), 5, 5);
        Log.i(TAG, "takeSuccess: length=" + water_bitmap.getByteCount() / 1024 / 128);
        arr_image.add(water_bitmap);
        arr_uri.add(saveImageToLocal(water_bitmap, mContext));

//        String uri = String.valueOf(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), water_bitmap, null,null)));
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
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        101);
            } else {
                uri = getImageCropUri();
//                cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
                //设置压缩参数
                compressConfig = new CompressConfig.Builder().setMaxSize(Constants.PIC_MAXSIZE * 1024).setMaxPixel(Constants.COMPRESSRATE).create();
                takePhoto.onEnableCompress(compressConfig, true); //设置为需要压缩
                takePhoto.onPickFromCapture(uri);
            }
        }
    }

    @Override
    public void onImageClick(final int p) {
//        Log.i(TAG, "onImageClick: "+p);
//                Bitmap bmp = arr_image.get(p);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte[] bytes = baos.toByteArray();
//                SPUtils.putCommit(mContext,"uri",new String(bytes));
//                Log.i(TAG, "onImageClick: "+bytes.length);
//                Bundle b = new Bundle();
//                b.putByteArray("image", bytes);
        Intent intent = new Intent(mContext, ImageActivity.class);
        Log.i(TAG, "onImageClick: "+arr_uri.get(p));
        intent.putExtra("uri", String.valueOf(arr_uri.get(p)));
        startActivity(intent);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_parking_takePhoto_submit:
                if (arr_image.size() == 0) {
                    ToastUtils.showToast(mContext, "至少拍摄一张照片");
                } else {
                    commit();

                }

                break;
            case R.id.tv_parking_takePhoto_back:
//                startActivityWithoutBack(mContext, MainActivity.class,null);
                finish();
                break;

        }
    }

    @Override
    public void onImgDeleteClick(int position) {
        arr_image.remove(position);
        arr_uri.remove(position);
        adapter.notifyDataSetChanged();
    }


    private void commit() {
        showLoadingDialog();

        String bitmap_string = BitmapToBase64.bitmapListToBase64(arr_image);
//        Log.i(TAG, "commit: ZFRYID=" + UserSingleton.USERINFO.getZFRYID() + " WFtime=" + time + " WFaddress=" + position + " WFAddressZB=" + roadId + " Carnum=" + carNum
//                + " Img=" + bitmap_string + " UpType=" + "enterprise" + " jdsnum=" + code);
        OkHttpUtils.post().url(RetrofitClient.mBaseUrl + "system/theme/anjuan/WFHandler.ashx")
                .addParams("ZFRYID", UserSingleton.USERINFO.getLawEnforcementOfficialsId())
                .addParams("WFtime", time + ":00")
                .addParams("WFaddress", position)
                .addParams("WFAddressZB", roadId)
                .addParams("Carnum", carNum)
                .addParams("Img", bitmap_string)
                .addParams("UpType", "enterprise")
                .addParams("jdsnum", code)
                .build().execute(new StringCallback() {
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
                    ToastUtil.show(mContext, "上传失败");
                }
            }


        });


    }


}
