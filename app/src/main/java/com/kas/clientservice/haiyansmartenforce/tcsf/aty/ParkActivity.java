package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.adapter.ImageAdapter;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.HTTP_HOST;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.PicBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.PermissonCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.DateUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.FileProvider7;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ImgUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ToastUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.UIUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.WaterMaskUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;

import static android.util.Log.e;

/**
 * 停车收费页面
 */
public class ParkActivity extends BaseActivity implements View.OnClickListener {
    private TextView tev_cphm;
    private TextView tev_trsj, tev_pwbh, tev_tcdy, tev_smcp;
    private File file;
    private RecyclerView rv;
    private Bitmap bmp = null;
    ArrayList<PicBean> arr_image;
    ImageAdapter adapter;


    private static final int CAMERA = 100;

    private static final int SM = 101;


    private String cphm, trsj, pwbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parking);

        tev_cphm = (EditText) findViewById(R.id.tev_cphm);
        tev_trsj = (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh = (TextView) findViewById(R.id.tev_pwbh);
        tev_tcdy = (TextView) findViewById(R.id.tev_tcdy);
        tev_smcp = (TextView) findViewById(R.id.tev_smcp);
        rv = (RecyclerView) findViewById(R.id.rv);


        arr_image = new ArrayList<PicBean>();
        adapter = new ImageAdapter(arr_image, aty);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(aty, 2, LinearLayout.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Pid.FILE, new PermissonCallBack() {
                    @Override
                    public void onPerMissionSuccess() {
                        requestPermission(Manifest.permission.CAMERA, Pid.CAMERA, new PermissonCallBack() {
                            @Override
                            public void onPerMissionSuccess() {
                                takePhoto();
                            }
                        });
                    }
                });

            }

            @Override
            public void onDelImageClick(int p) {
                arr_image.remove(p);
                setRecyclerViewHeight(arr_image.size());
                adapter.notifyDataSetChanged();
            }
        });


        setRecyclerViewHeight(arr_image.size());
        rv.setAdapter(adapter);


        tev_pwbh.setOnClickListener(this);
        tev_tcdy.setOnClickListener(this);
        tev_trsj.setOnClickListener(this);
        tev_smcp.setOnClickListener(this);
    }


    public void setRecyclerViewHeight(int size) {
        int height = ((size / 2) + 1) * 140 + 10;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UIUtil.getPx(aty, height));
        layoutParams.setMargins(0, UIUtil.getPx(aty, 5), 0, UIUtil.getPx(aty, 5));
        rv.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }


    /**
     * 打开相机拍照
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/kas/img/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();

        Uri uri = FileProvider7.getUriForFile(this, file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA) {
                Bitmap bmp = ImgUtil.getimage(file.getAbsolutePath());
               Bitmap waterMap= WaterMaskUtil.drawTextToRightBottom(aty, bmp, DateUtil.currentTime(),
                        UIUtil.sp2px(aty,12f), getResources().getColor(R.color.white),15,15);
                arr_image.add(new PicBean(false, waterMap));
                setRecyclerViewHeight(arr_image.size());
                adapter.notifyDataSetChanged();

            } else if (requestCode == SM) {
                if (data != null) {
                    String file_path = data.getStringExtra("path");
                    String carNum = data.getStringExtra("car");
                    if (bmp != null) {
                        arr_image.remove(0);
                    }
                    bmp = ImgUtil.getimage(file_path);
                    tev_trsj.setText(DateUtil.currentTime());
                    tev_cphm.setText(carNum);
                    arr_image.add(0, new PicBean(true, bmp));
                    setRecyclerViewHeight(arr_image.size());
                    adapter.notifyDataSetChanged();

                }

            }


        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tev_pwbh:
//              TODO：车位号获取应该从服务器取
                final String[] arr = app.USERINFO.getRoad().split(",");
                new AlertView(null, null, null, null, arr, aty, null, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        tev_pwbh.setText(arr[position]);
                    }
                }).show();

                break;

            case R.id.tev_trsj:
                tev_trsj.setText(DateUtil.currentTime());
                break;
            case R.id.tev_smcp:
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Pid.FILE, new PermissonCallBack() {
                    @Override
                    public void onPerMissionSuccess() {
                        requestPermission(Manifest.permission.CAMERA, Pid.CAMERA, new PermissonCallBack() {
                            @Override
                            public void onPerMissionSuccess() {

                                startActivityForResult(new Intent(aty, SmActivity.class), SM);

                            }
                        });
                    }
                });
                break;

            case R.id.tev_tcdy:
//              TODO：停车打印应该打开蓝牙连接打印机并上传数据
                cphm = tev_cphm.getText().toString().trim();
                trsj = tev_trsj.getText().toString().trim();
                pwbh = tev_pwbh.getText().toString().trim();
                String pic = getBase64bmpStr();
                if (!(cphm.length() == 0 || trsj.length() == 0 || pwbh.length() == 0 || pic.length() == 0)) {
                    final String road = pwbh.split("--")[0];
                    final String berthName = pwbh.split("--")[1];

                    OkHttpUtils.post().url(HTTP_HOST.URL_PARK)
                            .addParams("UCarnum", cphm).addParams("UpType", "enterprise")
                            .addParams("Road", road).addParams("StartTime", trsj)
                            .addParams("Img", pic).addParams("BerthName", berthName)
                            .addParams("SBYID", app.USERINFO.getZFRYID()).build().execute(new BeanCallBack(aty, "数据提交中") {

                        @Override
                        public void handleBeanResult(NetResultBean bean) {
                            if (bean.State) {
                                String[] body = new String[]{"车牌号：" + cphm, "停入时间：" + trsj, "泊位编号：" + pwbh};
                                Intent intent = new Intent(aty, PrintActivity.class).putExtra("body", body);
                                startActivity(intent);
                                finish();

                            } else {
                                ToastUtil.show(aty, "提交数据到服务器失败，请检查数据后重试");
                            }

                        }

                    });

                } else {

                    ToastUtil.show(aty, "数据不完整,注意至少要提交一张图片哦！");

                }


        }
    }

    private String getBase64bmpStr() {
        String pic = "";
        for (int i = 0; i < arr_image.size(); i++) {
            Bitmap bmp = arr_image.get(i).getBmp();
            pic = pic + ImgUtil.bitmapToBase64(bmp) + ",";
        }
        if (pic.length() > 1)
            pic = pic.substring(0, pic.length() - 1);
        return pic;
    }


}
