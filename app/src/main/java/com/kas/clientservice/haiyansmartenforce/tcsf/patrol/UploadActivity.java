package com.kas.clientservice.haiyansmartenforce.tcsf.patrol;

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

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.tcsf.adapter.ImageAdapter;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.HTTP_HOST;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.TitleActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.PatrolDetailBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.PicBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.PermissonCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.DateUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.FileProvider7;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ImgUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.UIUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.WaterMaskUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.widget.ProgressDialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.io.File;
import java.util.ArrayList;


public class UploadActivity extends TitleActivity implements View.OnClickListener {
    private RecyclerView rv;
    private ImageAdapter adapter;
    ArrayList<PicBean> arr_image;
    private File file;
    private static final int CAMERA = 10;

    private TextView tev_left, tev_right;


    private TextView tev_name, tev_time;
    private PatrolDetailBean patrolDetailBean;
    private EditText edt_describe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_upload);

    }


    @Override
    protected void findViewBy() {
        tev_left = (TextView) findViewById(R.id.tev_left);
        tev_right = (TextView) findViewById(R.id.tev_right);
        tev_name = (TextView) findViewById(R.id.tev_name);
        tev_time = (TextView) findViewById(R.id.tev_time);
        edt_describe = (EditText) findViewById(R.id.edt_describe);
        rv = (RecyclerView) findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(aty, 2, LinearLayout.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void setTitleTxt() {
        tv_header_title.setText("督查上报");
    }

    private void takePhoto(final int CODE) {
        requestPermissionGroup(Pid.CAMERA, new PermissonCallBack() {
            @Override
            public void onPerMissionSuccess() {
                requestPermissionGroup(Pid.FILE, new PermissonCallBack() {
                    @Override
                    public void onPerMissionSuccess() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                                + "/kas/img/" + System.currentTimeMillis() + ".jpg");
                        file.getParentFile().mkdirs();
                        Uri uri = FileProvider7.getUriForFile(aty, file);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, CODE);
                    }
                });
            }
        });

    }

    @Override
    protected void initData() {
        patrolDetailBean = (PatrolDetailBean) getIntent().getSerializableExtra("PatrolDetailBean");
        tev_left.setText("上报");
        tev_right.setText("返回");
        tev_left.setOnClickListener(this);
        tev_right.setOnClickListener(this);
        tev_name.setText(patrolDetailBean.TollCollector);
        tev_time.setText(DateUtil.currentTime());
        arr_image = new ArrayList<PicBean>();
        adapter = new ImageAdapter(arr_image, aty);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                takePhoto(CAMERA);

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
    }

    public void setRecyclerViewHeight(int size) {
        int height = ((size / 2) + 1) * 140 + 10;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UIUtil.getPx(aty, height));
        layoutParams.setMargins(0, UIUtil.getPx(aty, 5), 0, UIUtil.getPx(aty, 5));
        rv.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tev_left:
                upLoadProblem();
                break;
            case R.id.tev_right:
                finish();
                break;

        }
    }

    private String getBase64bmpStr() {
        String pic = "";
        if (arr_image.size() == 0) {
        } else {
            ProgressDialogUtil.show(aty, "图片处理中...");
            for (int i = 0; i < arr_image.size(); i++) {
                Bitmap bmp = arr_image.get(i).getBmp();
                pic = pic + ImgUtil.bitmapToBase64(bmp) + ",";
            }
            pic = pic.substring(0, pic.length() - 1);
            ProgressDialogUtil.hide();
        }
        return pic;
    }

    public void upLoadProblem() {
        if (TextUtil.isEmpty(getText(edt_describe))) {
            warningShow("督查情况描述不能为空");
            return;
        }
        String ParkingSupervisorId = UserSingleton.USERINFO.getParkingSupervisorId();
        OkHttpUtils.post().url(HTTP_HOST.URL_CHECKADD)
                .addParams("Btid", patrolDetailBean.Btid)
                .addParams("Addtime", getText(tev_time))
                .addParams("Situation", getText(edt_describe))
                .addParams("UpType", "enterprise")
                .addParams("Img", getBase64bmpStr())
                .addParams("ParkingSupervisorId", ParkingSupervisorId)
                .build().execute(new BeanCallBack(aty, "上报中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show(bean.ErrorMsg);
                    finish();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA) {
                Bitmap bmp = ImgUtil.getimage(file.getAbsolutePath());
//                Bitmap waterMap = WaterMaskUtil.drawTextToRightBottom(aty, bmp, DateUtil.currentTime(),
//                        UIUtil.sp2px(aty, 12f), getResources().getColor(R.color.white), 15, 15);
                arr_image.add(new PicBean(false, bmp));
                setRecyclerViewHeight(arr_image.size());
                adapter.notifyDataSetChanged();

            }


        }


    }
}
