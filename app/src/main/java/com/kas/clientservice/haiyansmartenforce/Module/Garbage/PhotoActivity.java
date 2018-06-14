package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.kas.clientservice.haiyansmartenforce.Utils.UriToFilePath;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PhotoActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PhotoAdapter.OnImageAddClickListener, TakePhoto.TakeResultListener, PhotoAdapter.OnImagelickListener {
    TextView tv_title_name, tv_take, tv_chose, tv_cancel;
    ImageView iv_title_back;
    List<Bitmap> arr_image;
    RecyclerView rv_illegalParkingCommit;
    Button bt_upload;
    PhotoAdapter adapter;
    TakePhoto takePhoto;
    @BindView(R.id.iv_vedio)
    ImageView iv_vedio;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    private CropOptions cropOptions;  //裁剪参数
    private CompressConfig compressConfig; //压缩参数
    PopupWindow ppw;
    Uri uri;
    int VEDIOCODE = 200;
    int TAKEVEDIO = 300;
    File file_vedio = null;
    /* 拍照的照片存储位置 */


    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected String getTAG() {
        return "PhotoActivity";
    }

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
    protected void initResAndListener() {
        super.initResAndListener();
        initRes();
        loadPPw();
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
        Log.i(TAG, "onActivityResult: " + requestCode + "  " + resultCode);
        if (requestCode == Constants.RESULTCODE_TIANDITU) {
            Log.i(TAG, "onActivityResult: " + data.getStringExtra("Longitude") + "  " + data.getStringExtra("Latitude"));
        }
        if (requestCode == VEDIOCODE) {
            if (data != null) {
                Uri uri = data.getData();
                Log.i(TAG, "onActivityResult: " + data.getData().toString());
                String path = UriToFilePath.getRealPathFromUri(mContext, uri);
                Log.i(TAG, "onActivityResult: " + path);
                file_vedio = new File(path);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
                mmr.setDataSource(file_vedio.getAbsolutePath());
                Bitmap bitmap = mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
                iv_vedio.setImageBitmap(bitmap);

            }
        }
        if (requestCode == TAKEVEDIO) {
            if (data != null) {
                Uri uri = data.getData();
                Log.i(TAG, "onActivityResult: " + data.getData().toString());
                String path = UriToFilePath.getRealPathFromUri(mContext, uri);
                Log.i(TAG, "onActivityResult: " + path);
                file_vedio = new File(path);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
                mmr.setDataSource(file_vedio.getAbsolutePath());
                Bitmap bitmap = mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
                iv_vedio.setImageBitmap(bitmap);

            }
        }
    }

    private void initRes() {
        takePhoto = new TakePhotoImpl(this, this);
        arr_image = new ArrayList<>();
        adapter = new PhotoAdapter(arr_image, mContext);
//        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
//        tv_title_name.setText("拍照评价");
//        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
//        iv_title_back.setOnClickListener(this);
        tv_title.setText("拍照评价");
        iv_back.setOnClickListener(this);
        iv_vedio.setOnClickListener(this);
        rv_illegalParkingCommit = (RecyclerView) findViewById(R.id.rv_illegalParkingCommit);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        rv_illegalParkingCommit.setLayoutManager(layoutManager);
        bt_upload = (Button) findViewById(R.id.bt_upload);
        bt_upload.setOnClickListener(this);
        setRecyclerViewHeight(arr_image.size());
        rv_illegalParkingCommit.setAdapter(adapter);

        adapter.setOnImageAddClickListener(this);
        adapter.setOnImagelickListener(this);
    }

    public void setRecyclerViewHeight(int size) {
        int height = ((size / 2) + 1) * 140 + 30;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
        layoutParams.setMargins(0, Dp2pxUtil.dip2px(mContext, 5), 0, Dp2pxUtil.dip2px(mContext, 50));
        rv_illegalParkingCommit.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            //case R.id.iv_problem_upload_picture:


            //break;
            case R.id.bt_upload:
//                intent.setClass(this, MainActivity.class);
//                startActivity(intent);
                if (file_vedio!=null) {
                    commit();
                }
                Toast.makeText(this, "您已成功进行评价", Toast.LENGTH_SHORT).show();
//                finish();
                break;
            case R.id.iv_vedio:
                Log.i(TAG, "onClick: ");
                ppw.showAtLocation(bt_upload, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_ppw_mediaType_take:
                takeVedio();
                ppw.dismiss();
                break;
            case R.id.tv_ppw_mediaType_chose:
                choseVedio();
                ppw.dismiss();
                break;
            case R.id.tv_ppw_mediaType_cancel:
                ppw.dismiss();
                break;
            default:
                break;
        }
    }

    private void commit() {
        Log.i(TAG, "commit: filepath="+file_vedio.getPath());
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_vedio);
//        OkHttpUtils.postFile()
//                .file(file_vedio)
//                .url("http://117.149.146.131/special/api/SubmitTable/MapAddUopload")
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Log.i(TAG, "onError: "+e.toString());
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Log.i(TAG, "onResponse: "+response);
//            }
//        });
        OkHttpUtils
                .post()
                .addFile("mFile","fileName",file_vedio).url("http://117.149.146.131/special/api/SpecialClass/Upload")
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
            }
        });
//        RetrofitClient.createService(UpLoadFileAPI.class)
//                .httpUploadFile(requestFile)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new MySubscriber<String>(mContext) {
//                    @Override
//                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
//                        Log.i(TAG, "onError: "+responeThrowable.toString());
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.i(TAG, "onNext: "+s);
//                    }
//                });
    }

    private void choseVedio() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent wrapperIntent = Intent.createChooser(intent, null);
        ((Activity) mContext).startActivityForResult(wrapperIntent, VEDIOCODE);
    }

    private void takeVedio() {
        int durationLimit = 300000;
//SystemProperties.getInt("ro.media.enc.lprof.duration", 60);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, sizeLimit);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationLimit);
        startActivityForResult(intent, TAKEVEDIO);
    }

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

    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }


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

    public void takeSuccess(String imagePath) {
        file_vedio = new File(imagePath);
        Log.i(TAG, "takeSuccess: " + imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath

        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 6, getResources().getColor(R.color.orange), 5, 5);
        Log.i(TAG, "takeSuccess: length=" + water_bitmap.getByteCount() / 1024);
        arr_image.add(water_bitmap);
        setRecyclerViewHeight(arr_image.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void takeFail(String msg) {
        ToastUtils.showToast(mContext, "拍摄失败");
    }

    @Override
    public void takeCancel() {
        ToastUtils.showToast(mContext, "取消拍摄");
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
                compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(400).create();
                takePhoto.onEnableCompress(compressConfig, true); //设置为需要压缩
                takePhoto.onPickFromCapture(uri);
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
