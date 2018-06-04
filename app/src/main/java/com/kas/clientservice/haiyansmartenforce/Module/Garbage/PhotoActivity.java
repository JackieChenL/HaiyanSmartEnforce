package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

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
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,PhotoAdapter.OnImageAddClickListener, TakePhoto.TakeResultListener {
    TextView tv_title_name;
    ImageView iv_title_back;
    List<Bitmap> arr_image;
    RecyclerView rv_illegalParkingCommit;
    Button bt_upload;
    PhotoAdapter adapter;
    TakePhoto takePhoto;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig; //压缩参数
    Uri uri;
    /* 拍照的照片存储位置 */
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/HaiYan/DICM");

    File file;


    Intent intent = new Intent();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected String getTAG() {
        return "PhotoActivity";
    }
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= new TakePhotoImpl(this, this);
        }
        return takePhoto;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        initRes();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
//        Log.i(TAG, "onActivityResult: "+data.toString());
        Log.i(TAG, "onActivityResult: "+requestCode+ "  "+resultCode);
        if (requestCode == Constants.RESULTCODE_TIANDITU) {
            Log.i(TAG, "onActivityResult: "+data.getStringExtra("Longitude")+"  "+data.getStringExtra("Latitude"));
        }
    }
    private void initRes() {
        takePhoto = new TakePhotoImpl(this, this);
        arr_image = new ArrayList<>();
        adapter = new PhotoAdapter(arr_image, mContext);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("拍照评价");
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        rv_illegalParkingCommit=(RecyclerView)findViewById(R.id.rv_illegalParkingCommit);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        rv_illegalParkingCommit.setLayoutManager(layoutManager);
        bt_upload = (Button) findViewById(R.id.bt_upload);
        bt_upload.setOnClickListener(this);
        setRecyclerViewHeight(arr_image.size());
        rv_illegalParkingCommit.setAdapter(adapter);
    }
    public void setRecyclerViewHeight(int size){
        int height = ((size/2)+1)*140+30;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext,height));
        layoutParams.setMargins(0,Dp2pxUtil.dip2px(mContext,5),0,Dp2pxUtil.dip2px(mContext,50));
        rv_illegalParkingCommit.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            //case R.id.iv_problem_upload_picture:


            //break;
            case R.id.bt_upload:
//                intent.setClass(this, MainActivity.class);
//                startActivity(intent);
                Toast.makeText(this, "您已成功进行评价", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }
    private Uri getImageCropUri() {
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void onImageClick(int p) {
        Bitmap bmp = arr_image.get(p);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

//        Bundle b = new Bundle();
//        b.putByteArray("bitmap", bytes);
        Intent intent = new Intent(mContext,ImageActivity.class);
        intent.putExtra("image",bytes);
        startActivity(intent);
    }
    public void takeSuccess(String imagePath) {
        Log.i(TAG, "takeSuccess: "+imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath

        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext,bmp,getTime(),6,getResources().getColor(R.color.orange),5,5);
        Log.i(TAG, "takeSuccess: length="+water_bitmap.getByteCount()/1024);
        arr_image.add(water_bitmap);
        setRecyclerViewHeight(arr_image.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void takeFail(String msg) {
        ToastUtils.showToast(mContext,"拍摄失败");
    }

    @Override
    public void takeCancel() {
        ToastUtils.showToast(mContext,"取消拍摄");
    }


    @Override
    public void onImageAddClick() {
        Log.i(TAG, "onImageAddClick: ");
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
                compressConfig=new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(400).create();
                takePhoto.onEnableCompress(compressConfig,true); //设置为需要压缩
                takePhoto.onPickFromCapture(uri);
            }
        }

    }
}
