package com.kas.clientservice.haiyansmartenforce.Module.XieTong;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.kas.clientservice.haiyansmartenforce.API.ZhuanXiangZhengZhiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Base.StringAdapter;
import com.kas.clientservice.haiyansmartenforce.Entity.ZXZZclassEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.IllegalParkingCommitImgRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.GeoBean;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.GeoUtils;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.TiandiMapActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.getImageCropUri;
import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.saveImageToLocal;

public class XieTongActivity extends BaseActivity implements View.OnClickListener, TakePhoto.TakeResultListener, IllegalParkingCommitImgRvAdapter.OnImageAddClickListener, IllegalParkingCommitImgRvAdapter.OnImagelickListener, IllegalParkingCommitImgRvAdapter.OnImgDeleteClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.rl_xietong)
    RelativeLayout rl_bigClass;
    @BindView(R.id.rl_xieTong_location)
    RelativeLayout rl_lacation;
    @BindView(R.id.et_xieTong_address)
    EditText et_address;
    @BindView(R.id.et_xieTong_describe)
    EditText et_describe;
    @BindView(R.id.tv_xietong_type)
    TextView tv_type;
    @BindView(R.id.tv_xieTong_location)
    TextView tv_location;
    @BindView(R.id.tv_xietong_btn)
    TextView tv_btn;
    @BindView(R.id.rv_xietong)
    RecyclerView recyclerView;


    TakePhoto takePhoto;
    List<Bitmap> arr_image;
    List<String> arr_uri = new ArrayList<>();
    IllegalParkingCommitImgRvAdapter adapter;
    private CompressConfig compressConfig;
    Uri uri;
    PopupWindow ppw;
    StringAdapter stringAdapter;
    List<String> list_class = new ArrayList<>();
    List<ZXZZclassEntity.KSBean> list = new ArrayList<>();
    String className = "";
    int classId = 0;
    private String langitude;
    private String latitude;
    AlertDialog.Builder alertDialog;
    List<String> list_url = new ArrayList<>();
    private GeoBean geoBean = new GeoBean(null);
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
        if (resultCode==RESULT_OK) {
            if (requestCode == Constants.RESULTCODE_TIANDITU) {
                if (data != null) {

                    langitude = data.getStringExtra("Longitude");
                    latitude = data.getStringExtra("Latitude");
                    Log.i(TAG, "onActivityResult: " + langitude + "  " + latitude);
                    tv_location.setText(langitude + "," + latitude);
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xie_tong;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("专项整治");
        iv_back.setOnClickListener(this);
        rl_bigClass.setOnClickListener(this);
        rl_lacation.setOnClickListener(this);
        tv_btn.setOnClickListener(this);
        GeoUtils.getInstance().startLocation(this, new GeoUtils.onLocationSuccessCallback() {
            @Override
            public void onSuccess(GeoBean geo) {
                geoBean = geo;
                tv_location.setText(geoBean.lat+","+geoBean.lon);
            }
        });
        initPhoto();
        loadPPw();
    }

    private void initPhoto() {
        arr_image = new ArrayList<>();
        adapter = new IllegalParkingCommitImgRvAdapter(arr_image, mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnImageAddClickListener(this);
        adapter.setOnImagelickListener(this);
        adapter.setOnImgDeleteClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void loadPPw() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ppw_project, null);
        ppw = new PopupWindow(view, Dp2pxUtil.dip2px(mContext, 200), LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) view.findViewById(R.id.lv_ppw_project);
//        list_town = new ArrayList<>();
        stringAdapter = new StringAdapter(list_class, mContext);
        lv.setAdapter(stringAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                className = list_class.get(i);
                tv_type.setText(className);
                classId = list.get(i).ID;
                ppw.dismiss();
            }
        });
        ppw.setFocusable(true);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new BitmapDrawable());

//        loadProjectData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.rl_xietong:
                ppw.showAsDropDown(tv_type);
                loadType();
                break;
            case R.id.rl_xieTong_location:
                Intent mapIntent = new Intent(this, TiandiMapActivity.class);
                mapIntent.putExtra("GeoBean", geoBean);
                startActivityForResult(mapIntent, Constants.RESULTCODE_TIANDITU);
                break;
            case R.id.tv_xietong_btn:
                if (tv_type.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext, "请选择类型");
                    return;
                }
                if (tv_location.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext, "请获取定位");
                    return;
                }
                if (et_address.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext, "请输入地址");
                    return;
                }
                if (et_describe.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext, "请输入描述");
                    return;
                }
                if (arr_image.size() == 0) {
                    ToastUtils.showToast(mContext, "请拍摄照片");
                    return;
                }
                showDialog();
                break;
        }
    }

    private void loadType() {
        RetrofitClient.createService(ZhuanXiangZhengZhiAPI.class)
                .httpZXZZgetClass()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<ZXZZclassEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(ZXZZclassEntity baseEntity) {
                        list.clear();
                        list_class.clear();
                        list.addAll(baseEntity.KS);
                        Log.i(TAG, "onNext: "+list.size());
                        Log.i(TAG, "onNext: " + list.size());
                        for (int i = 0; i < list.size(); i++) {
                            list_class.add(list.get(i).Name);

                        }
                        stringAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void showDialog() {
        alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setMessage("是否提交？");
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                commit();
            }
        });
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.create().show();
    }

    private void commit() {
//        raReport = new RapidReport("0", 1, bigId, 1, tv_chengguan_CaseGps
//                .getText().toString(), et_chengguan_CaseAdd.getText()
//                .toString(),
//                et_chengguan_CaseDescribe.getText().toString(), cc,
//                backImg, app.getUserID());
        String str_img_after = "";
        String cc = "";
        for (int i = 0; i < list_url.size(); i++) {
            str_img_after += list_url.get(i) + "|";
        }
        if (str_img_after.endsWith("|")) {
            cc = str_img_after.substring(0, str_img_after.length() - 1);
        }
        Log.i(TAG, "commit: " + cc);
        ZXZZCommitEntity zxzzCommitEntity = new ZXZZCommitEntity("0", 1, classId, 1, tv_location.getText().toString(),
                et_address.getText().toString(),
                et_describe.getText().toString(), cc,
                "", UserSingleton.USERINFO.Name.UserID);
        Special special = new Special(zxzzCommitEntity);

        Log.i(TAG, "commit: " + gson.toJson(special));
        RequestBody requestBody =RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(special));
        RetrofitClient.createService(ZhuanXiangZhengZhiAPI.class)
                .httpZXZZcommit(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();

                        Log.i(TAG, "onError: " + responeThrowable.toString());
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (baseEntity.isState()) {
                            ToastUtils.showToast(mContext, "提交成功");
                        } else {
                            ToastUtils.showToast(mContext, baseEntity.getErrorMsg());
                        }
                    }
                });

    }

    @Override
    public void takeSuccess(String imagePath) {
        Log.i(TAG, "takeSuccess: " + imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath
        Log.i(TAG, "takeSuccess: length=" + bmp.getByteCount() / 1024 / 128);
        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 8, getResources().getColor(R.color.orange), 5, 5);
        Log.i(TAG, "takeSuccess: length=" + water_bitmap.getByteCount() / 1024 / 128);
        int flag = arr_image.size();
        arr_image.add(water_bitmap);
        String uri = saveImageToLocal(water_bitmap, mContext);
        Log.i(TAG, "takeSuccess: " + uri);
        arr_uri.add(uri);

//        File file = null;
//        file = new File(uri);
        uploadIMG(BitmapToBase64.bitmapToBase64(water_bitmap), flag);
//        Log.i(TAG, "takeSuccess: " + file.getPath());
        adapter.notifyDataSetChanged();
    }

    private void uploadIMG(String img, final int flag) {
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), img);
        RetrofitClient.createService(ZhuanXiangZhengZhiAPI.class, RequestUrl.baseUrl_leader)
                .httpZXZZimg(img, "-1","citizen")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<ZhuanXiangZhengZhiAPI.UploadImgEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        arr_image.remove(flag);
                        arr_uri.remove(flag);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(ZhuanXiangZhengZhiAPI.UploadImgEntity s) {
                        Log.i(TAG, "onNext: " + gson.toJson(s));
                        list_url.add(s.getKS().get(0));
                    }
                });

    }

    @Override
    public void takeFail(String msg) {

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
    public void onImageClick(int p) {
        Intent intent = new Intent(mContext, ImageActivity.class);
        Log.i(TAG, "onImageClick: " + arr_uri.get(p));
        intent.putExtra("uri", String.valueOf(arr_uri.get(p)));
        startActivity(intent);
    }

    @Override
    public void onImgDeleteClick(int position) {
        arr_image.remove(position);
        arr_uri.remove(position);
        list_url.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ppw != null) {
            ppw.dismiss();
        }
    }

    class ZXZZCommitEntity {
        String SpecialID;
        int AttributeIDSpe;//（部件0，事件1，全部-1）
        int BigClassIDSpe;
        int SmallClassIDSpe;
        String GpsXYSpe;
        String AddressSpe;
        String DescriptionSpe;
        String UploadOriginSpe;  //处理前照片
        String UploadReturnSpe;//处理后照片
        //String AcceptTimeRap;//（新增时忽略）
        //String CaseStatusIDRap;// （新增时忽略）
        //String CreateTimeRap;// （新增时忽略）
        String UserIDSpe;

        public ZXZZCommitEntity(String specialID, int attributeIDSpe, int bigClassIDSpe, int smallClassIDSpe, String gpsXYSpe, String addressSpe, String descriptionSpe, String uploadOriginSpe, String uploadReturnSpe, String userIDSpe) {
            SpecialID = specialID;
            AttributeIDSpe = attributeIDSpe;
            BigClassIDSpe = bigClassIDSpe;
            SmallClassIDSpe = smallClassIDSpe;
            GpsXYSpe = gpsXYSpe;
            AddressSpe = addressSpe;
            DescriptionSpe = descriptionSpe;
            UploadOriginSpe = uploadOriginSpe;
            UploadReturnSpe = uploadReturnSpe;
            UserIDSpe = userIDSpe;
        }
    }

    class Special {
        ZXZZCommitEntity Special;

        public Special(ZXZZCommitEntity zxzzCommitEntity) {
            this.Special = zxzzCommitEntity;
        }
    }
}
