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
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.ReplacementTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.kas.clientservice.haiyansmartenforce.API.IllegalParkingAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.TiandiMapActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;

import org.feezu.liuli.timeselector.TimeSelector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IllegalParkingCommitActivity extends BaseActivity implements IllegalParkingCommitImgRvAdapter.OnImageAddClickListener, IllegalParkingCommitImgRvAdapter.OnImagelickListener, TakePhoto.TakeResultListener, View.OnClickListener {
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.sp_province)
    Spinner sp_province;
    @BindView(R.id.sp_ABC)
    Spinner sp_A2Z;
    @BindView(R.id.et_illegalparkingcommit_num)
    EditText et_num;
    @BindView(R.id.rv_illegalParkingCommit)
    RecyclerView recyclerView;
    @BindView(R.id.tv_commitIllegalParking_commit)
    TextView tv_commit;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.tv_illegalParking_time)
    TextView tv_time;
    @BindView(R.id.iv_illefalParking_location)
    ImageView iv_location;
    @BindView(R.id.iv_illegalParking_choseTime)
    ImageView iv_choseTime;
    String[] arr_province;
    String[] arr_abc;
    String longitude = "";
    String latitude = "";


    List<Bitmap> arr_image;
    IllegalParkingCommitImgRvAdapter adapter;
    TakePhoto takePhoto;
    private String time_chose;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig; //压缩参数
    Uri uri;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_illegal_parking_commit;
    }
    @Override
    protected String getTAG() {
        return "IllegalParkingCommit";
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
        Log.i(TAG, "onActivityResult: "+requestCode+ "  "+resultCode);
        if (requestCode == Constants.RESULTCODE_TIANDITU) {
            longitude = data.getStringExtra("Longitude");
            latitude = data.getStringExtra("Latitude");
            Log.i(TAG, "onActivityResult: "+longitude+"  "+latitude);
        }
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("违章停车");
        takePhoto = new TakePhotoImpl(this, this);
        arr_province = getResources().getStringArray(R.array.provinceName);
        arr_abc = getResources().getStringArray(R.array.A2Z);

        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //设置自动大小写转换
        et_num.setTransformationMethod(new UpperCaseTransform());
        tv_commit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_choseTime.setOnClickListener(this);
        iv_location.setOnClickListener(this);

        arr_image = new ArrayList<>();
        adapter = new IllegalParkingCommitImgRvAdapter(arr_image, mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnImageAddClickListener(this);
        adapter.setOnImagelickListener(this);
        setRecyclerViewHeight(arr_image.size());
        recyclerView.setAdapter(adapter);
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
    private Uri getImageCropUri() {
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    @Override
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

    @Override
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

    public void setRecyclerViewHeight(int size){
        int height = ((size/2)+1)*140+30;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext,height));
        layoutParams.setMargins(0,Dp2pxUtil.dip2px(mContext,5),0,Dp2pxUtil.dip2px(mContext,50));
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commitIllegalParking_commit:
                commit();
                startActivity(new Intent(this,IllegalParkingDetailActivity.class));
                break;
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.iv_illegalParking_choseTime:
                choseTime();
                break;
            case R.id.iv_illefalParking_location:
                startActivityForResult(new Intent(mContext, TiandiMapActivity.class), Constants.RESULTCODE_TIANDITU);
                break;
        }
    }

    private void commit() {
        RetrofitClient.createService(IllegalParkingAPI.class)
                .httpCommitParking(1,"2018-04-24","jjj","10.0,10.0","abc","F1234P")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<String>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: "+responeThrowable.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: "+s);
                    }
                });
    }

    private void choseTime() {
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                time_chose = time;
                tv_time.setText(time);
            }
        }, TimeUtils.getFormedTime("yyyy-MM-dd")+" 00:00", TimeUtils.getFormedTime("yyyy-MM-dd hh:mm"));
        timeSelector.setIsLoop(true);
        timeSelector.show();
    }




    public class UpperCaseTransform extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }
    }
}
