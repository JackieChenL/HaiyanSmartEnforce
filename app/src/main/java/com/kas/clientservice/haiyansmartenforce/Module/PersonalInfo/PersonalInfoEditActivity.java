package com.kas.clientservice.haiyansmartenforce.Module.PersonalInfo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.kas.clientservice.haiyansmartenforce.API.PersonalInfoAPI;
import com.kas.clientservice.haiyansmartenforce.API.ZhuanXiangZhengZhiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.PersonalInfoEntiy;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.Login.LoginActivity;
import com.kas.clientservice.haiyansmartenforce.Module.Register.ResetPswActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.EditviewDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.getImageCropUri;

public class PersonalInfoEditActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface, TakePhoto.TakeResultListener {
    @BindView(R.id.rl_infoEdit_icon)
    RelativeLayout rl_icon;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.rl_infoEdit_sex)
    RelativeLayout rl_sex;
    @BindView(R.id.rl_infoEdit_birth)
    RelativeLayout rl_birth;
    @BindView(R.id.rl_infoEdit_describe)
    RelativeLayout rl_describe;
    @BindView(R.id.rl_infoEdit_reset)
    RelativeLayout rl_reset;
    @BindView(R.id.tv_infoEdit_exit)
    TextView tv_exit;
    @BindView(R.id.iv_infoEdit_icon)
    ImageView iv_icon;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_infoEdit_describe)
    TextView tv_des;
    @BindView(R.id.tv_infoedit_sex)
    TextView tv_sex;
    @BindView(R.id.tv_infoedit_birth)
    TextView tv_birth;
    @BindView(R.id.activity_personal_info_edit)
    LinearLayout ll_main;
    @BindView(R.id.tv_infoEdit_commit)
    TextView tv_commit;
    String describe = "";
    TimePickerDialog timePickerDialog;
    String birth = "";
    String sex = "";
    String icon = "";
    TakePhoto takePhoto;
    Bitmap img;

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
                img = bm;
                iv_icon.setImageBitmap(bm);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info_edit;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("个人信息");
        iv_back.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_birth.setOnClickListener(this);
        rl_reset.setOnClickListener(this);
        rl_describe.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        icon = UserSingleton.USERINFO.getPhoto();

        loadData();
        loadPPw();

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
        rl_icon.setOnClickListener(this);
        ppw = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ppw.setFocusable(true);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new BitmapDrawable());
    }

    private void loadData() {
        RetrofitClient.createService(PersonalInfoAPI.class)
                .httpGetPersonalInfo(UserSingleton.USERINFO.getUserName())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<PersonalInfoEntiy>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<PersonalInfoEntiy> personalInfoEntiyBaseEntity) {
                        if (personalInfoEntiyBaseEntity.isState()) {
                            PersonalInfoEntiy personalInfoEntiy = personalInfoEntiyBaseEntity.getRtn();
                            if (!personalInfoEntiy.getBirth().equals("")) {
                                tv_birth.setText(personalInfoEntiy.getBirth());
                                birth = personalInfoEntiy.getBirth();
                            }
                            if (!personalInfoEntiy.getSex().equals("")) {
                                tv_sex.setText(personalInfoEntiy.getSex());
                                sex = personalInfoEntiy.getSex();
                            }
                            if (!personalInfoEntiy.getSummary().equals("")) {
                                tv_des.setText(personalInfoEntiy.getSummary());
                                describe = personalInfoEntiy.getSummary();
                            }

                        } else {

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.rl_infoEdit_icon:
                ppw.showAtLocation(ll_main, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_infoEdit_sex:
                choseSex();
                break;
            case R.id.rl_infoEdit_birth:
                timePickerDialog = new TimePickerDialog(mContext);
                timePickerDialog.showDatePickerDialog();
                break;
            case R.id.rl_infoEdit_describe:
                showEditDialog();
                break;
            case R.id.rl_infoEdit_reset:
                startActivity(new Intent(mContext, ResetPswActivity.class));
                break;
            case R.id.tv_infoEdit_exit:
                startActivityWithoutBack(mContext, LoginActivity.class, null);
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
            case R.id.tv_infoEdit_commit:
                if (img == null) {
                    commit();
                }else {
                    uploadImg(BitmapToBase64.bitmapToBase64(img));
                }

        }
    }

    private void commit() {
        RetrofitClient.createService(PersonalInfoAPI.class)
                .httpChangePersonalInfo(icon,sex,birth,describe,UserSingleton.USERINFO.getUserName())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (baseEntity.isState()) {
                            showToast("修改成功");
                        }else showToast(baseEntity.ErrorMsg);
                    }
                });
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


    Dialog mDialog;

    private void choseSex() {
        final String[] datas = new String[]{"男", "女"};
        mDialog = new AlertDialog.Builder(this).setSingleChoiceItems(datas, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:

//                        gotoLive(info);
                        break;
                    case 1:
//                        gotoPlayback(info);
                        break;
                    default:
                        break;
                }
                sex = datas[which];
                tv_sex.setText(datas[which]);
            }

        }).create();
        mDialog.show();
    }

    private void showEditDialog() {
        EditviewDialog editviewDialog = new EditviewDialog(mContext, new EditviewDialog.OnEditviewDialogClick() {
            @Override
            public void onPositiveClick(String content) {
                tv_des.setText(content);
                describe = content;
            }
        });
        editviewDialog.showDialog();
    }

    @Override
    public void positiveListener() {
        birth = timePickerDialog.getYear() + "-" + timePickerDialog.getMonth() + "-" + timePickerDialog.getDay();
        tv_birth.setText(birth);
    }

    @Override
    public void negativeListener() {

    }

    @Override
    public void takeSuccess(String imagePath) {
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath
        img = bmp;
        iv_icon.setImageBitmap(img);
    }

    private void uploadImg(String img) {
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), img);
        RetrofitClient.createService(ZhuanXiangZhengZhiAPI.class, RequestUrl.baseUrl_leader)
                .httpZXZZimg(img, "-1", "citizen")
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
                        Log.i(TAG, "onNext: " + s.getKS().get(0));
                        icon = s.getKS().get(0);
                        UserSingleton.USERINFO.setPhoto(RequestUrl.baseUrl_img+icon);
                        commit();
                    }
                });

    }

    @Override
    public void takeFail(String msg) {

    }

    @Override
    public void takeCancel() {

    }
}
