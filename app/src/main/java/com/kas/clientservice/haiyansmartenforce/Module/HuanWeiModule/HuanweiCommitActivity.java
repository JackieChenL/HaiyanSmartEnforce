package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.kas.clientservice.haiyansmartenforce.API.HuanweiAPI;
import com.kas.clientservice.haiyansmartenforce.API.TownsAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Base.StringAdapter;
import com.kas.clientservice.haiyansmartenforce.Entity.TownEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.IllegalParkingCommitImgRvAdapter;
import com.kas.clientservice.haiyansmartenforce.Module.IllegalParking.ImageActivity;
import com.kas.clientservice.haiyansmartenforce.Module.TianDiTu.TiandiMapActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.BitmapToBase64;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.Dp2pxUtil;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.WaterMaskImageUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.kas.clientservice.haiyansmartenforce.Utils.Utils.getImageCropUri;

public class HuanweiCommitActivity extends BaseActivity implements TakePhoto.TakeResultListener, IllegalParkingCommitImgRvAdapter.OnImageAddClickListener, IllegalParkingCommitImgRvAdapter.OnImagelickListener, IllegalParkingCommitImgRvAdapter.OnImgDeleteClickListener, View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.tv_huanwei_commit_btn)
    TextView tv_btn;
    @BindView(R.id.tv_huanwei_commit_content)
    TextView tv_content;
    @BindView(R.id.et_huanwei_commit_position)
    EditText et_position;
    @BindView(R.id.iv_huanwei_commit_location)
    ImageView iv_location;
    @BindView(R.id.tv_huanwei_commit_project)
    TextView tv_project;
    @BindView(R.id.rv_huanwie_commit)
    RecyclerView recyclerView;
    @BindView(R.id.tv_huanwei_commit_town)
    TextView tv_town;
    @BindView(R.id.et_huanwei_commit_score)
    EditText et_score;
    @BindView(R.id.et_huanwei_commit_descripe)
    EditText editText;
    @BindView(R.id.activity_huanwei_commit)
    RelativeLayout rl_main;
    @BindView(R.id.tv_huanwei_save_btn)
    TextView tv_save;

    TakePhotoImpl takePhoto;
    List<Bitmap> arr_image;
    IllegalParkingCommitImgRvAdapter adapter;
    private CompressConfig compressConfig;
    List<HuanweiAPI.HuanweiProjectEntity> list;
    List<HuanweiAPI.HuanweiContentEntity> list_content = new ArrayList<>();
    Uri uri;
    String longitude = "";
    String latitude = "";
    PopupWindow ppw;
    HuanweiProjectAdapter huanweiProjectAdapter;
    String projectName = "";
    String projectId = "";
    String contentName = "";
    String contentId = "";
    PopupWindow ppw_content;
    PopupWindow ppw_town;
    HuanweiContentAdapter huanweiContentAdapter;
    String roadName = "";
    String townId;
    StringAdapter stringAdapter;
    String townName;
    String state;
    List<String> list_town = new ArrayList<>();
    List<TownEntity.TownBean> towns = new ArrayList<>();

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
        if (requestCode == Constants.RESULTCODE_TIANDITU) {
            if (data != null) {
                longitude = data.getStringExtra("Longitude");
                latitude = data.getStringExtra("Latitude");
            }
            Log.i(TAG, "onActivityResult: " + longitude + "  " + latitude);
        }
//        if (requestCode == Constants.RESULTCODE_ROAD) {
//            if (data != null) {
//                tv_position.setText(data.getStringExtra("Road"));
//                roadName = data.getStringExtra("Road");
////                roadId = data.getStringExtra("RoadId");
//            }
//        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huanwei_commit;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("上报");
        tv_project.setOnClickListener(this);
        tv_content.setOnClickListener(this);
//        tv_position.setOnClickListener(this);
        iv_location.setOnClickListener(this);
        tv_btn.setOnClickListener(this);
        tv_town.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        initList();

        loadPPw();
        loadContentPPW();
        loadTownPPW();
    }

    private void loadTownPPW() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ppw_project, null);
        ppw_town = new PopupWindow(view, Dp2pxUtil.dip2px(mContext, 200), LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) view.findViewById(R.id.lv_ppw_project);
//        list_town = new ArrayList<>();
        stringAdapter = new StringAdapter(list_town, mContext);
        lv.setAdapter(stringAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                townName = list_town.get(i);
                tv_town.setText(townName);
                townId = towns.get(i).getTownid();
                ppw_town.dismiss();
            }
        });
        ppw_town.setFocusable(true);
        ppw_town.setOutsideTouchable(true);
        ppw_town.setBackgroundDrawable(new BitmapDrawable());

        loadTowns();
    }

    private void loadTowns() {
        RetrofitClient.createService(TownsAPI.class)
                .httpgetTown()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<TownEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<TownEntity> townEntityBaseEntity) {
                        if (townEntityBaseEntity.isState()) {
                            towns.clear();
                            towns.addAll(townEntityBaseEntity.getRtn().getTown());
                            for (int i = 0; i < towns.size(); i++) {
                                list_town.add(towns.get(i).getTown());

                            }
                            stringAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void initList() {
        arr_image = new ArrayList<>();
        adapter = new IllegalParkingCommitImgRvAdapter(arr_image, mContext);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayout.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnImageAddClickListener(this);
        adapter.setOnImagelickListener(this);
        adapter.setOnImgDeleteClickListener(this);
        setRecyclerViewHeight(arr_image.size());
        recyclerView.setAdapter(adapter);
    }

    public void setRecyclerViewHeight(int size) {
        int height = ((size / 2) + 1) * 140 + 30;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Dp2pxUtil.dip2px(mContext, height));
        layoutParams.setMargins(0, Dp2pxUtil.dip2px(mContext, 5), 0, Dp2pxUtil.dip2px(mContext, 60));
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }


    @Override
    public void takeSuccess(String imagePath) {
        Log.i(TAG, "takeSuccess: " + imagePath);
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);//filePath

        Bitmap water_bitmap = WaterMaskImageUtil.drawTextToRightBottom(mContext, bmp, getTime(), 6, getResources().getColor(R.color.orange), 5, 5);
        Log.i(TAG, "takeSuccess: length=" + water_bitmap.getByteCount() / 1024);
        arr_image.add(water_bitmap);
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

    @Override
    public void onImgDeleteClick(int position) {
        arr_image.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_huanwei_commit_project:
                ppw.showAsDropDown(tv_project, 0, 0);
                loadProjectData();
                break;
            case R.id.tv_huanwei_commit_content:
                if (!projectName.equals("")) {

                    ppw_content.showAsDropDown(tv_content, 0, 0);
                    loadContentData();
                } else {
                    ToastUtils.showToast(mContext, "请选择项目");
                }
                break;
//            case R.id.tv_huanwei_commit_position:
//                startActivityForResult(new Intent(mContext, RoadSearchActivity.class), Constants.RESULTCODE_ROAD);
//                break;
            case R.id.iv_huanwei_commit_location:
                startActivityForResult(new Intent(mContext, TiandiMapActivity.class), Constants.RESULTCODE_TIANDITU);
                break;
            case R.id.tv_huanwei_commit_btn:
                roadName = et_position.getText().toString();
                if (projectName.equals("")) {
                    ToastUtils.showToast(mContext, "请选择项目");
                    break;
                }
                if (contentName.equals("")) {
                    ToastUtils.showToast(mContext, "请选择内容");
                    break;
                }
                if (roadName.equals("")) {
                    ToastUtils.showToast(mContext, "请选择路段");
                    break;
                }
                if (longitude.equals("")) {
                    ToastUtils.showToast(mContext, "请打开天地图进行定位");
                    break;
                }

                if (townId.equals("")) {
                    ToastUtils.showToast(mContext, "请选择区域");
                    break;
                }
                if (et_score.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext, "请输入扣分值");
                    break;
                }
                if (editText.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext, "请输入描述信息");
                    break;
                }
                commit("1");
                break;
            case R.id.tv_huanwei_save_btn:
                roadName = et_position.getText().toString();
                if (projectName.equals("")) {
                    ToastUtils.showToast(mContext, "请选择项目");
                    break;
                }
                if (contentName.equals("")) {
                    ToastUtils.showToast(mContext, "请选择内容");
                    break;
                }
                if (roadName.equals("")) {
                    ToastUtils.showToast(mContext, "请选择路段");
                    break;
                }
                if (longitude.equals("")) {
                    ToastUtils.showToast(mContext, "请打开天地图进行定位");
                    break;
                }

                if (townId.equals("")) {
                    ToastUtils.showToast(mContext, "请选择区域");
                    break;
                }
                if (et_score.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext, "请输入扣分值");
                    break;
                }
                if (editText.getText().toString().equals("")) {
                    ToastUtils.showToast(mContext, "请输入描述信息");
                    break;
                }
                commit("0");
            case R.id.tv_huanwei_commit_town:
                ppw_town.showAtLocation(rl_main, Gravity.CENTER, 0, 0);
                break;
        }
    }

    private void commit(String status) {
        Log.i(TAG, "commit: " + BitmapToBase64.bitmapListToBase64(arr_image));
        RetrofitClient.createService(HuanweiAPI.class)
                .httpHuanweiCommit(UserSingleton.USERINFO.getCheckNameID(),
                        contentId,
                        roadName,
                        formLocation(longitude, latitude),
                        editText.getText().toString(),
                        "enterprise",
                        townId,
                        et_score.getText().toString(),
                        state,
                        BitmapToBase64.bitmapListToBase64(arr_image))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        ToastUtils.showToast(mContext, "网络错误");
                    }

                    @Override
                    public void onNext(BaseEntity s) {
                        if (s.isState()) {
                            ToastUtils.showToast(mContext, "提交成功");
                            finish();
                        } else {
                            ToastUtils.showToast(mContext, s.getErrorMsg());
                        }
                    }
                });
    }

    private void loadContentPPW() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.ppw_project, null);
        ppw_content = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) view.findViewById(R.id.lv_ppw_project);
        huanweiContentAdapter = new HuanweiContentAdapter(list_content, mContext);
        lv.setAdapter(huanweiContentAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                contentName = list_content.get(i).jcnr;
                contentId = list_content.get(i).jcnrid;
                tv_content.setText(contentName);
                ppw_content.dismiss();
            }
        });
        ppw_content.setFocusable(true);
        ppw_content.setOutsideTouchable(true);
        ppw_content.setBackgroundDrawable(new BitmapDrawable());

    }

    private void loadPPw() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ppw_project, null);
        ppw = new PopupWindow(view, Dp2pxUtil.dip2px(mContext, 200), LinearLayout.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) view.findViewById(R.id.lv_ppw_project);
        list = new ArrayList<>();
        huanweiProjectAdapter = new HuanweiProjectAdapter(list, mContext);
        lv.setAdapter(huanweiProjectAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                projectName = list.get(i).xm;
                projectId = list.get(i).xmid;
                tv_project.setText(projectName);
                ppw.dismiss();
            }
        });
        ppw.setFocusable(true);
        ppw.setOutsideTouchable(true);
        ppw.setBackgroundDrawable(new BitmapDrawable());

    }

    private void loadProjectData() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpHuanweiProject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiAPI.HuanweiProjectEntity[]>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiAPI.HuanweiProjectEntity[]> s) {
                        list.clear();
                        huanweiProjectAdapter.notifyDataSetChanged();
                        Collections.addAll(list, s.getRtn());
                        Log.i(TAG, "onNext: " + list.size());
                        huanweiProjectAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadContentData() {
        list_content.clear();
        huanweiContentAdapter.notifyDataSetChanged();
        RetrofitClient.createService(HuanweiAPI.class)
                .httpHuanweiContent(projectId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiAPI.HuanweiContentEntity[]>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiAPI.HuanweiContentEntity[]> s) {

                        Collections.addAll(list_content, s.getRtn());
                        Log.i(TAG, "onNext: " + list_content.size());
                        huanweiContentAdapter.notifyDataSetChanged();
                    }
                });
    }
}
