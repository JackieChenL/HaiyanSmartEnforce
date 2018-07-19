package com.kas.clientservice.haiyansmartenforce.Module.PersonalInfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.kas.clientservice.haiyansmartenforce.API.PersonalInfoAPI;
import com.kas.clientservice.haiyansmartenforce.API.ZhuanXiangZhengZhiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.getImageCropUri;

public class IdentifyActivity extends BaseActivity implements View.OnClickListener, TakePhoto.TakeResultListener {
    @BindView(R.id.tv_header_title)
    TextView tv_titile;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_identify_commit)
    TextView tv_commit;
    @BindView(R.id.et_identify_id)
    EditText et_id;
    @BindView(R.id.iv_identify_frontPhoto)
    ImageView iv_front;
    @BindView(R.id.iv_identify_backPhoto)
    ImageView iv_backPhoto;
    @BindView(R.id.activity_identify)
    LinearLayout ll_main;
    int flag = 0;
    Bitmap bm_front, bm_back;
    List<String> list_img = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_identify;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    TakePhoto takePhoto;

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
        if (requestCode == 300) {
            if (data != null) {
                Uri uri = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(uri, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
//                showImage(imagePath);
                c.close();
                Bitmap bm = BitmapFactory.decodeFile(imagePath);

                if (flag == 1) {
                    iv_front.setImageBitmap(bm);
                    bm_front = bm;
                } else {
                    iv_backPhoto.setImageBitmap(bm);
                    bm_back = bm;
                }
            }
        }
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_titile.setText("身份验证");
        iv_back.setOnClickListener(this);
        iv_front.setOnClickListener(this);
        iv_backPhoto.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        loadPPw();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.iv_identify_frontPhoto:
                flag = 1;
                ppw.showAtLocation(ll_main, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_identify_backPhoto:
                flag = 2;
                ppw.showAtLocation(ll_main, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_ppw_mediaType_take:
                takePhoto();

                break;
            case R.id.tv_ppw_mediaType_chose:
//                choseVedio();
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 300);
                ppw.dismiss();
                break;
            case R.id.tv_ppw_mediaType_cancel:
                ppw.dismiss();
                break;
            case R.id.tv_identify_commit:
                if (et_id.getText().toString().trim().equals("")|et_id.getText().toString().trim().length()<18) {
                    showToast("请输入正确的身份证号码");
                    return;
                }
                if (bm_front==null) {
                    showToast("请拍摄身份证正面照片");
                    return;
                }
                if (bm_back==null) {
                    showToast("请拍摄身份证反面照片");
                    return;
                }
                String base64 = BitmapToBase64.bitmapToBase64(bm_front)+","+BitmapToBase64.bitmapToBase64(bm_back);
                uploadImg(base64);
        }
    }

    private void uploadImg(String img) {
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), img);
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
                        Log.i(TAG, "onNext: "+s.getKS().get(0));
                        Collections.addAll(list_img,s.getKS().get(0).split("\\|"));
                        if (list_img.size()>0) {
                            commit();
                        }
                    }
                });

    }

    private void commit() {
        RetrofitClient.createService(PersonalInfoAPI.class)
                .httpIdentify(UserSingleton.USERINFO.getUserName(),
                        list_img.get(0),list_img.get(1),
                        et_id.getText().toString().trim())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        Log.i(TAG, "onNext: "+gson.toJson(baseEntity));
                        if (baseEntity.isState()) {
                            UserSingleton.USERINFO.setHaveCertificate(true);
                            showToast("认证成功");
                            finish();
                        }else {
                            showToast(baseEntity.ErrorMsg);
                        }
                    }
                });
    }

    private TextView tv_take, tv_chose, tv_cancel;
    PopupWindow ppw;

    private void loadPPw() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_ppw_media_type, null);
        tv_take = (TextView) view.findViewById(R.id.tv_ppw_mediaType_take);
        tv_chose = (TextView) view.findViewById(R.id.tv_ppw_mediaType_chose);
        tv_cancel = (TextView) view.findViewById(R.id.tv_ppw_mediaType_cancel);
        tv_take.setOnClickListener(this);
        tv_chose.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        ppw = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ppw.setFocusable(true);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new BitmapDrawable());
    }

    Uri uri;
    CompressConfig compressConfig;

    public void takePhoto() {
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
    public void takeSuccess(String imagePath) {
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath
        Log.i(TAG, "takeSuccess: length=" + bmp.getByteCount() / 1024 / 128);
        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 8, getResources().getColor(R.color.orange), 5, 5);

        if (flag == 1) {
            iv_front.setImageBitmap(water_bitmap);
            bm_front = water_bitmap;
        } else {
            iv_backPhoto.setImageBitmap(water_bitmap);
            bm_back = water_bitmap;
        }
    }

    @Override
    public void takeFail(String msg) {

    }

    @Override
    public void takeCancel() {

    }
}
