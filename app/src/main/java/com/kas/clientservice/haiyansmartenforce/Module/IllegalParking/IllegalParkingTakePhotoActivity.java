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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindView(R.id.edt_bz)
    EditText edt_bz;
    @BindView(R.id.lin_bz)
    LinearLayout lin_bz;
    @BindView(R.id.tev_alert)
    TextView tev_alert;

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
    String JDSnum="";
    int ID=0;//停车案件ID
    int TYPE=0;


    String CarPlateTypeCPT="";
    String  CarBodyColorCBC="";
    String CarTypeCT="";
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
        CarPlateTypeCPT=getIntent().getStringExtra("CarPlateTypeCPT");

        CarBodyColorCBC=getIntent().getStringExtra("CarBodyColorCBC");
        CarTypeCT=getIntent().getStringExtra("CarTypeCT");

        time = getIntent().getStringExtra("Time");
        carNum = getIntent().getStringExtra("CarNum");
        position = getIntent().getStringExtra("Position");
        code = getIntent().getStringExtra("Code");
        roadId = getIntent().getStringExtra("RoadId");
        ID=getIntent().getIntExtra("ID",0);
        TYPE=getIntent().getIntExtra("TYPE",0);
        JDSnum=getIntent().getStringExtra("JDSnum");
        if (TYPE==1){
            tv_title.setText("临时停车照片");
            lin_bz.setVisibility(View.VISIBLE);
        }else {
            tv_title.setText("违法照片");
            tev_alert.setVisibility(View.VISIBLE);





        }
        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);



        adapter =new UrlParkingAdapter(arr_uri, mContext,this);


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


                if (TYPE==1){



                    if (arr_uri.size()>=1) {
                        showToast("最多拍摄一张图片");
                    }else {
                        uri = getImageCropUri();
//                cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
                        //设置压缩参数
                        compressConfig = new CompressConfig.Builder().setMaxSize(Constants.PIC_MAXSIZE * 1024).setMaxPixel(Constants.COMPRESSRATE).create();
                        takePhoto.onEnableCompress(compressConfig, true); //设置为需要压缩
                        takePhoto.onPickFromCapture(uri);
                    }
                }else {

                    if (arr_uri.size()>=3){
                        showToast("最多拍摄三张图片");
                    }else {
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
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_parking_takePhoto_submit:

                if (TYPE==1){
                    if (arr_uri.size() <1) {
                        ToastUtils.showToast(mContext, "至少拍摄一张图片");
                    } else {
                        String imgUrls=adapter.getUrl();
                        commitLSTC(imgUrls);
                    }
                }else {
                    if (arr_uri.size() <3) {
                        ToastUtils.showToast(mContext, "至少拍摄三张图片");
                    } else {
                        String imgUrls=adapter.getUrl();
                        commit(imgUrls);
                    }
                }


                break;
            case R.id.tv_parking_takePhoto_back:
                finish();
                break;

        }
    }
    int number=0;
    String upType="";
    private void uploadImg(Bitmap bmp) {
        number=arr_uri.size()+1;


        if (TYPE==1){
            upType="applyparking";
        }else {
            upType="illegalparking";
        }


        HashMap<String,String> map=new HashMap<>();
        map.put("Number",number+"");
        map.put("JDSnum",JDSnum);
        map.put("upType",upType);
        map.put("carNum",carNum);
        Log.e("图片停车收费",map.toString());
      final  String img =BitmapToBase64.bitmapToBase64(bmp);
        RetrofitClient.createService(ZhuanXiangZhengZhiAPI.class, RequestUrl.baseUrl_leader)
                .httpTCSFimg(img,"-1",upType,number,carNum,JDSnum)
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
        params.put("CarPlateType",CarPlateTypeCPT);
        params.put("CarBodyColor",CarBodyColorCBC);
        params.put("Cartype",CarTypeCT);
        Log.e("违章停车MAP",params.toString());
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
                Log.e(TAG, "onResponse: " + response);
                    BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                    if (baseEntity.isState()) {

                        ToastUtils.showToast(mContext, "上传成功");
                        startActivityWithoutBack(mContext, MainActivity.class, null);
                    } else {
                        ToastUtils.showToast(mContext,baseEntity.ErrorMsg);
                    }
//                }

            }


        });


    }


    private void commitLSTC(String substring) {
        showLoadingDialog();
        HashMap<String,String> params=new HashMap<>();
        params.put("FwxxtbId", ID+"");
        params.put("img", substring);
        params.put("summary",edt_bz.getText().toString());
        Log.e("LSTCparamsMap",params.toString());
        OkHttpUtils.post().url(RetrofitClient.mBaseUrl + "system/theme/anjuan/ApplayTemporaryStop.ashx")
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
                Log.e(TAG, "onResponse: " + response);
//                if (response==null||response.equals("")){
//                    ToastUtil.show(mContext,"数据出错");
//                }else {
                BaseEntity baseEntity = gson.fromJson(response, BaseEntity.class);
                if (baseEntity.isState()) {

                    ToastUtils.showToast(mContext, "申请成功");
                    startActivityWithoutBack(mContext, MainActivity.class, null);
                } else {
                    ToastUtils.showToast(mContext,baseEntity.ErrorMsg);
                }
//                }

            }


        });


    }



}
