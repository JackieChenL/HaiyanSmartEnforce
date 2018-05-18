package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.adapter.ImageAdapter;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.HTTP_HOST;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.CpBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.PicBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.TcListBeanResult;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.PermissonCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.DateUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.FileProvider7;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ImgUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.LogUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.PrintUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.UIUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.WaterMaskUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.widget.ProgressDialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 停车收费页面
 */
public class ParkActivity extends PrintActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView imv_sm;
    private Spinner sp_province, sp_ABC;
    private EditText et_cp_num;
    private ImageView iv_heaer_back;
    private TextView tv_header_title;
    private TextView tev_print, tev_submit;
    private TextView tev_trsj, tev_pwbh;
    private File file;
    private RecyclerView rv;
    private Bitmap bmp = null;
    ArrayList<PicBean> arr_image;
    ImageAdapter adapter;
    String[] arr_province;
    String[] arr_abc;
    String province = "浙";
    String A2Z = "F";

    private static final int CAMERA = 100;
    /*********车牌识别选用扫描版(新activity)，或者原生相机选择***********/
    private static final int SM_SCAN = 101;
    private static final int SM_CAMERA = 102;


    private String cphm, trsj, pwbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parking);

        tev_trsj = (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh = (TextView) findViewById(R.id.tev_pwbh);
        tev_print = (TextView) findViewById(R.id.tev_print);
        tev_submit = (TextView) findViewById(R.id.tev_submit);
        rv = (RecyclerView) findViewById(R.id.rv);
        iv_heaer_back = (ImageView) findViewById(R.id.iv_heaer_back);
        imv_sm = (ImageView) findViewById(R.id.imv_sm);
        sp_province = (Spinner) findViewById(R.id.sp_province);
        sp_ABC = (Spinner) findViewById(R.id.sp_ABC);
        et_cp_num = (EditText) findViewById(R.id.et_cp_num);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);

        arr_province = getResources().getStringArray(R.array.provinceName);
        arr_abc = getResources().getStringArray(R.array.A2Z);
        tv_header_title.setText("停车收费");
        tev_trsj.setText(DateUtil.currentTime());
        arr_image = new ArrayList<PicBean>();
        adapter = new ImageAdapter(arr_image, aty);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(aty, 2, LinearLayout.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                requestPermissionGroup(Pid.FILE, new PermissonCallBack() {
                    @Override
                    public void onPerMissionSuccess() {
                        requestPermissionGroup(Pid.CAMERA, new PermissonCallBack() {
                            @Override
                            public void onPerMissionSuccess() {
                                takePhoto(CAMERA);
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
        tev_submit.setOnClickListener(this);
        tev_print.setOnClickListener(this);
        tev_trsj.setOnClickListener(this);
        imv_sm.setOnClickListener(this);
        iv_heaer_back.setOnClickListener(this);
        sp_province.setOnItemSelectedListener(this);
        sp_ABC.setOnItemSelectedListener(this);
        et_cp_num.setTransformationMethod(new UpperCaseTransform());
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
    private void takePhoto(int CODE) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/kas/img/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        Uri uri = FileProvider7.getUriForFile(this, file);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA) {
                Bitmap bmp = ImgUtil.getimage(file.getAbsolutePath());
                Bitmap waterMap = WaterMaskUtil.drawTextToRightBottom(aty, bmp, DateUtil.currentTime(),
                        UIUtil.sp2px(aty, 12f), getResources().getColor(R.color.white), 15, 15);
                arr_image.add(new PicBean(false, waterMap));
                setRecyclerViewHeight(arr_image.size());
                adapter.notifyDataSetChanged();

            } else if (requestCode == SM_SCAN) {
                if (data != null) {
                    String file_path = data.getStringExtra("path");
                    String carNum = data.getStringExtra("car");
                    if (bmp != null) {
                        arr_image.remove(0);
                    }
                    bmp = ImgUtil.getimage(file_path);
                    doCarNum(carNum);
                    arr_image.add(0, new PicBean(true, bmp));
                    setRecyclerViewHeight(arr_image.size());
                    adapter.notifyDataSetChanged();

                }

            }else if(requestCode==SM_CAMERA){
                Bitmap bmp = ImgUtil.getimage(file.getAbsolutePath());
                recognition(bmp);
            }


        }


    }

    private void doCarNum(String carNum) {
        String province = String.valueOf(carNum.charAt(0));
        String abc = String.valueOf(carNum.charAt(1));
        String num = carNum.substring(2, carNum.length());
        et_cp_num.setText(num);
        for (int i = 0; i < arr_province.length; i++) {
            if (arr_province[i].equals(province)) {
                sp_province.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < arr_abc.length; i++) {
            if (arr_abc[i].equals(abc)) {
                sp_ABC.setSelection(i);
                break;
            }
        }


    }

    String[] arr;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tev_pwbh:
                doQueryEmptyList();
                break;
            case R.id.iv_heaer_back:
                finish();
                break;

            case R.id.tev_trsj:

                break;
            case R.id.imv_sm:
                requestPermissionGroup(Pid.FILE, new PermissonCallBack() {
                    @Override
                    public void onPerMissionSuccess() {
                        requestPermissionGroup(Pid.CAMERA, new PermissonCallBack() {
                            @Override
                            public void onPerMissionSuccess() {
//                                startActivityForResult(new Intent(aty, SmActivity.class), SM_SCAN);
                                takePhoto(SM_CAMERA);
                            }
                        });
                    }
                });
                break;
            case R.id.tev_print:
                cphm = province + A2Z + et_cp_num.getText().toString().trim();
                trsj = tev_trsj.getText().toString().trim();
                pwbh = tev_pwbh.getText().toString().trim();
                if (!(cphm.length() == 0 || trsj.length() == 0 || pwbh.length() == 0)) {
                    String[] body = new String[]{"车牌号码：" + cphm, "停入时间：" + trsj, "泊位编号：" + pwbh};
                    ArrayList<byte[]> list = (new PrintUtil("停车收费小票", null, body, getFooterString())).getData();
                    doCheckConnection(list);
                } else {
                    show("数据不完整");
                }


                break;

            case R.id.tev_submit:
                cphm = province + A2Z + et_cp_num.getText().toString().trim();
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
                            .addParams("SBYID", getZFRYID()).build().execute(new BeanCallBack(aty, "数据提交中") {

                        @Override
                        public void handleBeanResult(NetResultBean bean) {
                            if (bean.State) {
                                show("提交数据到服务器成功");
                                tev_submit.setEnabled(false);
                                tev_submit.setBackgroundColor(getResources().getColor(R.color.grey_100));
                            } else {
                                show(bean.ErrorMsg);
                            }

                        }

                    });

                } else {

                    show("数据不完整,注意至少要提交一张图片哦！");

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_province:
                province = arr_province[position];
                break;
            case R.id.sp_ABC:
                A2Z = arr_abc[position];
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class UpperCaseTransform extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }
    }


    private void doQueryEmptyList() {
        OkHttpUtils.post().url(HTTP_HOST.URL_PARK_LIST)
                .addParams("Opername", getOpername())
                .addParams("type", "0")
                .build().execute(new BeanCallBack(aty, "获取空闲车位列表中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    if (bean.total > 0) {
                        List<TcListBeanResult> list = bean.getResultBeanList(TcListBeanResult.class);
                        arr = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            arr[i] = list.get(i).Berthname;
                        }
                        new AlertView(null, null, null, null, arr, aty, null, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                tev_pwbh.setText(arr[position]);
                            }
                        }).show();


                    } else {
                        show("可用车位为0");
                    }
                } else {
                    show(bean.ErrorMsg);
                }


            }
        });
    }

    private final String url = "http://jisucpsb.market.alicloudapi.com/licenseplaterecognition/recognize";
    private final String AppKey = "24553193";
    private final String AppCode = "APPCODE 2e476d97d6994a489afb3491b44a2578";


    private void recognition(final Bitmap bitmap) {
        ProgressDialogUtil.show(aty, "正在识别.....");
        final String base64bmp = ImgUtil.bitmapToBase64(bitmap);
        OkHttpUtils.post().url(url)
                .addHeader("X-Ca-Key", AppKey)
                .addHeader("Authorization", AppCode)
                .addParams("pic", base64bmp).build().execute(new StringCallback() {

                                                                 @Override
                                                                 public void onError(Call call, Exception e, int id) {
                                                                     onCpjxReturn(false, "图片解析异常",bitmap);
                                                                 }

                                                                 @Override
                                                                 public void onResponse(String response, int id) {
                                                                     log(response);
                                                                     final CpBean bean = JSON.parseObject(response, CpBean.class);
                                                                     if (bean.status.equals("0")&&bean.result!=null) {
                                                                         onCpjxReturn(true, bean.result.number,bitmap);
                                                                     } else {
                                                                         onCpjxReturn(false, "车牌解析错误",bitmap);
                                                                     }
                                                                 }


                                                             }

        );

    }


    private void onCpjxReturn(final boolean isResponse, final String msg,final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
                if (!isResponse) {
                    show(msg);
                } else {
                    if (bmp != null) {
                        arr_image.remove(0);
                    }
                    bmp = bitmap;
                    doCarNum(msg);
                    arr_image.add(0, new PicBean(true, bmp));
                    setRecyclerViewHeight(arr_image.size());
                    adapter.notifyDataSetChanged();
                }


            }
        });


    }


}
