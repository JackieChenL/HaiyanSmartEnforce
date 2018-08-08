package com.kas.clientservice.haiyansmartenforce.Module.Ledger;

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

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.AreaEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.ImgBean;
import com.kas.clientservice.haiyansmartenforce.Entity.LedgerTypeEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.IllegalParkingCommitImgRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.getImageCropUri;
import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.saveImageToLocal;

public class LedgerAddActivity extends BaseActivity implements TakePhoto.TakeResultListener, IllegalParkingCommitImgRvAdapter.OnImageAddClickListener, IllegalParkingCommitImgRvAdapter.OnImagelickListener, IllegalParkingCommitImgRvAdapter.OnImgDeleteClickListener, View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.sp_ledger_add_area)
    Spinner sp_area;
    @BindView(R.id.sp_ledger_add_type)
    Spinner sp_type;
    @BindView(R.id.activity_ledger_add)
    RelativeLayout rl_main;
    @BindView(R.id.rv_ledger_add)
    RecyclerView recyclerView;
    @BindView(R.id.tv_ledger_add_btn)
    TextView tv_submit;
    @BindView(R.id.et_ledger_add_describe)
    EditText et_text;
    TakePhoto takePhoto;
    List<Bitmap> arr_image;
    List<ImgBean> arr_uri = new ArrayList<>();
    IllegalParkingCommitImgRvAdapter adapter;
    private CompressConfig compressConfig;
    Uri uri;

    String typeId = "";
    String areaId = "";

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

    private void initAdapter() {
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
                arr_image.add(bm);
                adapter.notifyDataSetChanged();

                ImgBean imgBean = new ImgBean();
                imgBean.setUri(imagePath);
                arr_uri.add(imgBean);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ledger_add;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("新增台账");
        iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);

        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeId = type.get(i).getID() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaId = area.get(i).getId() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadType();
        loadArea();
        loadPPw();
        initAdapter();
    }

    List<AreaEntity.KSBean> area = new ArrayList<>();
    ArrayAdapter<AreaEntity.KSBean> arrayAdapter;

    private void loadArea() {
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "mobile/getare.ashx")
                .addParams("UserID", "1")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id_) {
                Log.i(TAG, "onResponse: " + response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    AreaEntity.KSBean mbig = new AreaEntity.KSBean("-----全部-----", -1);
                    area.add(mbig);
                    JSONArray jsonArray = object.getJSONArray("KS");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Log.i(TAG, "onResponse: " + name);
                        AreaEntity.KSBean mybig = new AreaEntity.KSBean(name, id);
                        area.add(mybig);
                    }
                    arrayAdapter = new ArrayAdapter<AreaEntity.KSBean>(mContext, android.R.layout.simple_spinner_item, area);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_area.setAdapter(arrayAdapter);

//                    query();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    List<LedgerTypeEntity.RowsBean> type = new ArrayList<>();
    ArrayAdapter<LedgerTypeEntity.RowsBean> arrayAdapter_type;

    private void loadType() {
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "mobile/GetLedgerTypeList.ashx")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id_) {
                Log.i(TAG, "onResponse: " + response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    LedgerTypeEntity.RowsBean mbig = new LedgerTypeEntity.RowsBean("-----全部-----", -1);
                    type.add(mbig);
                    JSONArray jsonArray = object.getJSONArray("rows");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("ID");
                        String name = jsonObject.getString("Text");
                        Log.i(TAG, "onResponse: " + name);
                        LedgerTypeEntity.RowsBean mybig = new LedgerTypeEntity.RowsBean(name, id);
                        type.add(mybig);
                    }
                    arrayAdapter_type = new ArrayAdapter<LedgerTypeEntity.RowsBean>(mContext, android.R.layout.simple_spinner_item, type);
                    arrayAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_type.setAdapter(arrayAdapter_type);

//                    query();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_ledger_add_btn:
                if (typeId.equals("-1") || typeId.equals("")) {
                    showToast("请选择类型");
                    return;
                }
                if (areaId.equals("-1") || areaId.equals("")) {
                    showToast("请选择区域");
                    return;
                }
//                if (et_text.getText().toString().trim().equals("")) {
//                    showToast("请选择区域");
//                    return;
//                }
//                if (arr_image.size() == 0) {
//                    showToast("请选择照片");
//                    return;
//                }
                commit();
                break;
        }
    }

    private void commit() {
        showLoadingDialog();
        Log.i(TAG, "commit: text="+ et_text.getText().toString()+"  AreaIDLed="+areaId+"  LedgerType="+typeId+"  createuserid="+UserSingleton.USERINFO.getName().UserID);
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "mobile/AddLedger.ashx")
                .addParams("Text", et_text.getText().toString().trim())
                .addParams("UploadPicAdvArray", BitmapToBase64.bitmapListToBase64(arr_image))
                .addParams("AreaIDLed", areaId)
                .addParams("LedgerType", typeId)
                .addParams("createuserid", UserSingleton.USERINFO.getName().UserID)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
                showNetErrorToast();
                dismissLoadingDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: " + response);
                dismissLoadingDialog();
                if (response.contains("成功")) {
                    showToast("提交成功");
                    finish();
                }else {
                    showToast("提交失败");
                }
            }
        });
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
}
