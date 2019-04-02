package com.kas.clientservice.haiyansmartenforce.Module.CityCheck;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.Big;
import com.kas.clientservice.haiyansmartenforce.Entity.CityCheckListEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.ImgBean;
import com.kas.clientservice.haiyansmartenforce.Entity.Small;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.IllegalParkingCommitImgRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.GeoBean;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.GeoUtils;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.TiandiMapActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.getImageCropUri;
import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.saveImageToLocal;

public class CityCheckAddActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface, TakePhoto.TakeResultListener, IllegalParkingCommitImgRvAdapter.OnImageAddClickListener, IllegalParkingCommitImgRvAdapter.OnImagelickListener, IllegalParkingCommitImgRvAdapter.OnImgDeleteClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_citySearch_add_location)
    TextView tv_location;
    @BindView(R.id.et_citySearch_add_address)
    EditText et_address;
    @BindView(R.id.et_citySearch_add_describe)
    EditText et_des;
    @BindView(R.id.et_citySearch_add_name)
    EditText et_name;
    @BindView(R.id.tv_citySearch_add_time)
    TextView tv_time;
    @BindView(R.id.rl_citySearch_add_location)
    RelativeLayout rl_location;
    @BindView(R.id.sp_citySearch_add_big)
    Spinner sp_big;
    @BindView(R.id.sp_citySearch_add_small)
    Spinner sp_small;
    @BindView(R.id.rv_citySearch_add)
    RecyclerView recyclerView;
    @BindView(R.id.tv_citySearch_add_btn)
    TextView tv_commit;
    @BindView(R.id.tv_citySearch_add_save)
    TextView tv_save;
    @BindView(R.id.activity_city_check_add)
    RelativeLayout rl_main;

    String time = "";
    List<Big> big;
    ArrayAdapter<Big> Typeadapter;
    int bigid;
    List<Small> small;
    ArrayAdapter<Small> smalladapter;
    int smallid;

    TakePhoto takePhoto;
    List<Bitmap> arr_image;
    List<ImgBean> arr_uri = new ArrayList<>();
    IllegalParkingCommitImgRvAdapter adapter;
    private CompressConfig compressConfig;
    Uri uri;
    private String longitude = "";
    private String latitude = "";
    private CityCheckListEntity.KSBean cityCheckListEntity;
    private boolean isEdit = false;
    private int oldbig = -1;
    private int oldsmall = -1;
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
        if (resultCode==RESULT_OK) {
            if (requestCode == Constants.RESULTCODE_TIANDITU) {
                if (data != null) {

                    longitude = data.getStringExtra("Longitude");
                    latitude = data.getStringExtra("Latitude");
                    Log.i(TAG, "onActivityResult: " + longitude + "  " + latitude);
                    tv_location.setText(formLocation(longitude, latitude));
                }
            }
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
                    arr_image.add(bm);
                    adapter.notifyDataSetChanged();

                    ImgBean imgBean = new ImgBean();
                    imgBean.setUri(imagePath);
                    arr_uri.add(imgBean);
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_check_add;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        iv_back.setOnClickListener(this);
        rl_location.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        timePickerDialog = new TimePickerDialog(mContext);
        time = TimeUtils.getFormedTime();
        tv_time.setText(time);

        GeoUtils.getInstance().startLocation(this, new GeoUtils.onLocationSuccessCallback() {
            @Override
            public void onSuccess(GeoBean geo) {
                geoBean = geo;
                tv_location.setText(geoBean.lat+","+geoBean.lon);
            }
        });
        loadPPw();
        String entity = getIntent().getStringExtra("json");
        if (entity != null) {
            isEdit = true;
            cityCheckListEntity = gson.fromJson(entity, CityCheckListEntity.KSBean.class);
            Log.i(TAG, "initResAndListener: " + gson.toJson(cityCheckListEntity));
            oldbig = cityCheckListEntity.getFirstLevelID();
            oldsmall = cityCheckListEntity.getSecondLevelID();
            et_address.setText(cityCheckListEntity.getAddressSou());
            et_name.setText(cityCheckListEntity.getNameECSou());
            et_des.setText(cityCheckListEntity.getContentSou());
            tv_location.setText(cityCheckListEntity.getGpsXYSou());
            tv_time.setText(cityCheckListEntity.getEntryTimeSou());
            Log.i(TAG, "initResAndListener: " + smallid);

            Log.i(TAG, "img = " + cityCheckListEntity.getAttachmentSou());
            String[] img = cityCheckListEntity.getAttachmentSou().split("\\|");
            Log.i(TAG, "imgLength: " + img.length);
            for (int i = 0; i < img.length; i++) {
                ImgBean imgBean = new ImgBean();
                imgBean.setUrl(RequestUrl.baseUrl_img + img[i]);
                arr_uri.add(imgBean);
                Log.i(TAG, "url: " + RequestUrl.baseUrl_img + img[i]);
                Glide.with(mContext).load(RequestUrl.baseUrl_img + img[i]).asBitmap().into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        arr_image.add(resource);
                        Log.i(TAG, "onResourceReady: " + arr_image.size());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
        getBig();

        sp_big.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                // TODO Auto-generated method stub
                Big item = Typeadapter.getItem(position);
                String name = item.getName();
                bigid = item.getId();
                if (bigid != -1) {
                    getSmall(bigid);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arr_image = new ArrayList<>();
        adapter = new IllegalParkingCommitImgRvAdapter(arr_image, mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnImageAddClickListener(this);
        adapter.setOnImagelickListener(this);
        adapter.setOnImgDeleteClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void getBig() {
        OkHttpUtils.get().url(RequestUrl.baseUrl_leader + "/Use/Ashx/Getfirstlevel.ashx")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
                showNetErrorToast();
            }

            @Override
            public void onResponse(String response, int j) {
                Log.i(TAG, "onResponse: " + response);
                big = new ArrayList<Big>();
                Big mbig = new Big("-----请选择-----", 0);
                big.add(mbig);
                try {
                    int position = -1;
                    JSONObject object = new JSONObject(response.toString());
                    JSONArray jsonArray = object.getJSONArray("KS");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        if (isEdit) {
                            if (id == oldbig) {
                                position = i+1;
                            }
                        }
                        String name = jsonObject.getString("name");
                        Big mybig = new Big(name, id);
                        big.add(mybig);
                    }
                    Typeadapter = new ArrayAdapter<Big>(mContext, android.R.layout.simple_spinner_item, big);
                    Typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_big.setAdapter(Typeadapter);
                    if (position != -1) {
                        sp_big.setSelection(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void getSmall(int smbigid) {
        OkHttpUtils.get().url(RequestUrl.baseUrl_leader + "/Use/Ashx/GetSecondLevel.ashx")
                .addParams("firstlevelid", smbigid + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
                showNetErrorToast();
            }

            @Override
            public void onResponse(String response, int j) {
                Log.i(TAG, "onResponse: " + response);
                small = new ArrayList<Small>();
                Small sm = new Small("-----请选择-----", 0);
                small.add(sm);
                try {
                    int position = -1;
                    JSONObject object = new JSONObject(response.toString());
                    JSONArray jsonArray = object.getJSONArray("KS");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        if (isEdit) {
                            if (id == oldsmall) {
                                position = i+1;
                            }
                        }
                        String name = jsonObject.getString("name");
                        Small mysmall = new Small(name, id);
                        small.add(mysmall);
                    }
                    smalladapter = new ArrayAdapter<Small>(mContext, android.R.layout.simple_spinner_item, small);
                    smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_small.setAdapter(smalladapter);

                    sp_small.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent,
                                                   View view, int position, long id) {
                            Small item = smalladapter.getItem(position);
                            String name = item.getName();
                            smallid = item.getId();
                            //model.Son(smallid, new getSon());

//                            Son(smallid);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (position != -1) {
                        sp_small.setSelection(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    TimePickerDialog timePickerDialog;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.rl_citySearch_add_location:
                Intent mapIntent = new Intent(this, TiandiMapActivity.class);
                mapIntent.putExtra("GeoBean", geoBean);
                startActivityForResult(mapIntent, Constants.RESULTCODE_TIANDITU);
                break;
            case R.id.tv_citySearch_add_btn:
                Log.i(TAG, "onClick: " + smallid);
                if (isEdit) {

                } else {
                    if (smallid <= 0) {
                        ToastUtils.showToast(mContext, "请选择大小类");
                        return;
                    }
                }
                if (tv_location.getText().toString().trim().equals("")) {
                    ToastUtils.showToast(mContext, "请获取坐标");
                    return;
                }
                if (et_address.getText().toString().trim().equals("")) {
                    ToastUtils.showToast(mContext, "请输入地址");
                    return;
                }
                if (et_des.getText().toString().trim().equals("")) {
                    ToastUtils.showToast(mContext, "请输入描述内容");
                    return;
                }


                commit("submit");
                break;
            case R.id.tv_citySearch_add_save:
                if (smallid <= 0) {
                    ToastUtils.showToast(mContext, "请选择大小类");
                    return;
                }
                if (tv_location.getText().toString().trim().equals("")) {
                    ToastUtils.showToast(mContext, "请获取坐标");
                    return;
                }
                if (et_address.getText().toString().trim().equals("")) {
                    ToastUtils.showToast(mContext, "请输入地址");
                    return;
                }
                if (et_des.getText().toString().trim().equals("")) {
                    ToastUtils.showToast(mContext, "请输入描述内容");
                    return;
                }
                commit("save");
                break;
            case R.id.tv_citySearch_add_time:
                timePickerDialog.showDateAndTimePickerDialog();
                break;
            case R.id.tv_ppw_mediaType_take:
//                takePhoto();
                takePhoto();
                ppw.dismiss();
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
        }
    }

    private void commit(String submit) {
        showLoadingDialog();
        final Map<String, String> map = new HashMap<>();
        if (!isEdit) {
            String name = "";
            map.put("UserID", UserSingleton.USERINFO.getName().UserID);
            map.put("DealingDepartmentID", "-1");
            map.put("EntryTime", time);
            map.put("NameTas", "");
            map.put("Address", et_address.getText().toString());
            map.put("GpsXY", tv_location.getText().toString());
            map.put("SaveOrSubmit", submit);
            map.put("img", BitmapToBase64.bitmapListToBase64(arr_image));
            map.put("RemindTime", time);
            map.put("FirstLevelID", bigid + "");
            map.put("SecondLevelID", smallid + "");
            if (et_name.getText().toString().trim().equals("")) {
                name = "匿名";
                map.put("NameEC", "匿名");
            } else {
                map.put("NameEC", et_name.getText().toString().trim());
                name = et_name.getText().toString().trim();
            }
            map.put("caseEntOrCiti", "3");
            map.put("EnterpriseID", "-1");
            map.put("Content", et_des.getText().toString());
            map.put("CitizenID", "-1");
            Log.i(TAG, "commit: " + gson.toJson(map));
//            "{\"NameTas\":\"\",\"SaveOrSubmit\":\"save\",\"SecondLevelID\":\"2\",\"EnterpriseID\":\"-1\",\"CitizenID\":\"-1\",\"img\":\"\",\"Address\":\"大家新年新\",\"UserID\":\"1290\",\"Content\":\"少女心那些\",\"EntryTime\":\"2018年07月12日 15:00:47\",\"NameEC\":\"匿名\",\"DealingDepartmentID\":\"-1\",\"RemindTime\":\"2018年07月12日 15:00:47\",\"caseEntOrCiti\":\"3\",\"FirstLevelID\":\"1\",\"GpsXY\":\"120.744468,30.769696\"}\n"
            OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "mobile/sourceadd.ashx")
                    .addParams("postData",gson.toJson(map).replaceAll("\"","\""))
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i(TAG, "onError: " + e.toString());
                    showNetErrorToast();
                    dismissLoadingDialog();
                }

                @Override
                public void onResponse(String response, int id) {
                    dismissLoadingDialog();
                    Log.i(TAG, "onResponse: " + response);
                    if (response.contains("成功")) {
                        ToastUtils.showToast(mContext, "提交成功");
                        finish();
                    } else ToastUtils.showToast(mContext, "失败");
                }
            });
        } else {
            String name = "" ;
//            map.put("SourceID", cityCheckListEntity.getSourceID() + "");
//            map.put("UserID", UserSingleton.USERINFO.getName().UserID);
//            map.put("DealingDepartmentID", "-1");
//            map.put("EntryTime", time);
//            map.put("NameTas", "");
//            map.put("Address", et_address.getText().toString());
//            map.put("GpsXY", tv_location.getText().toString());
//            map.put("SaveOrSubmit", submit);
//            map.put("img", BitmapToBase64.bitmapListToBase64(arr_image));
//            map.put("RemindTime", time);
//            map.put("FirstLevelID", bigid + "");
//            map.put("SecondLevelID", smallid + "");
//            if (et_name.getText().toString().trim().equals("")) {
//                name = "匿名";
//                map.put("NameEC", "匿名");
//            } else {
//                map.put("NameEC", et_name.getText().toString().trim());
//                name = et_name.getText().toString().trim();
//            }
//            map.put("caseEntOrCiti", "3");
//            map.put("EnterpriseID", "-1");
//            map.put("Content", et_des.getText().toString());
//            map.put("CitizenID", "-1");

//            Log.i(TAG, "commit: " + gson.toJson(map));
            OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "mobile/SourceModify.ashx")
//                    .addParams("postData",gson.toJson(map).replaceAll("\"","\""))
                    .addParams("SourceID", cityCheckListEntity.getSourceID() + "")
                    .addParams("UserID", UserSingleton.USERINFO.getName().UserID)
                    .addParams("DealingDepartmentID", "-1")
                    .addParams("EntryTime", time)
                    .addParams("NameTas", "")
                    .addParams("Address", et_address.getText().toString())
                    .addParams("GpsXY", tv_location.getText().toString())
                    .addParams("img", BitmapToBase64.bitmapListToBase64(arr_image))
                    .addParams("RemindTime", time)
                    .addParams("FirstLevelID", bigid + "")
                    .addParams("SecondLevelID", smallid + "")
                    .addParams("NameEC", name)
                    .addParams("caseEntOrCiti", "3")
                    .addParams("EnterpriseID", "-1")
                    .addParams("Content", et_des.getText().toString())
                    .addParams("CitizenID", "-1")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i(TAG, "onError: " + e.toString());
                    dismissLoadingDialog();
                    showNetErrorToast();
                }

                @Override
                public void onResponse(String response, int id) {
                    dismissLoadingDialog();
                    Log.i(TAG, "onResponse: " + response);
                    if (response.contains("成功")) {
                        ToastUtils.showToast(mContext, "提交成功");
                        finish();
                    } else ToastUtils.showToast(mContext, "失败");
                }
            });
        }
    }

    @Override
    public void positiveListener() {
        String year = timePickerDialog.getYear() + "";
        String months = timePickerDialog.getMonth() + "";
        String day = timePickerDialog.getDay() + "";
        String hour = timePickerDialog.getHour() + "";
        String minutes = timePickerDialog.getMinute() + "";
        tv_time.setText(year + "-" + months + "-" + day + " " + hour + ":" + minutes);
        time = tv_time.getText().toString();
    }

    @Override
    public void negativeListener() {

    }

    @Override
    public void takeSuccess(String imagePath) {
        Log.i(TAG, "takeSuccess: " + imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath
        Log.i(TAG, "takeSuccess: length=" + bmp.getByteCount() / 1024 / 128);
        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 8, getResources().getColor(R.color.orange), 5, 5);
        Log.i(TAG, "takeSuccess: length=" + water_bitmap.getByteCount() / 1024 / 128);
        arr_image.add(water_bitmap);
        ImgBean imgbean = new ImgBean();
        imgbean.setUri(saveImageToLocal(water_bitmap, mContext));
        arr_uri.add(imgbean);

//        String uri = String.valueOf(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), water_bitmap, null,null)));
//        setRecyclerViewHeight(arr_image.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void takeFail(String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onImageAddClick() {
        ppw.showAtLocation(rl_main, Gravity.BOTTOM, 0, 0);
    }

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
    public void onImageClick(int p) {
        Intent intent = new Intent(mContext, ImageActivity.class);
//        Log.i(TAG, "onImageClick: " + arr_uri.get(p));
        if (arr_uri.get(p).getUri() != null) {
            intent.putExtra("uri", arr_uri.get(p).getUri());
        } else if (arr_uri.get(p).getUrl() != null) {
            intent.putExtra("url", arr_uri.get(p).getUrl());
        }

        startActivity(intent);
    }

    @Override
    public void onImgDeleteClick(int position) {
        arr_image.remove(position);
        arr_uri.remove(position);
        adapter.notifyDataSetChanged();
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
}
