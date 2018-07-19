package smartenforce.aty.parking;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import smartenforce.adapter.TcsfImageAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.tcsf.CpBean;
import smartenforce.bean.tcsf.PicBean;
import smartenforce.bean.tcsf.TcListBeanResult;
import smartenforce.dialog.WarningListDialog;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.projectutil.PrintUtil;
import smartenforce.util.DateUtil;
import smartenforce.util.ImgUtil;
import smartenforce.util.UIUtil;
import smartenforce.util.WaterMaskUtil;
import smartenforce.widget.ProgressDialogUtil;

/**
 * 停车收费页面
 */
public class ParkActivity extends PrintActivity implements AdapterView.OnItemSelectedListener {

    private ImageView imv_sm;
    private Spinner sp_province, sp_ABC;
    private EditText et_cp_num;
    private TextView tev_print, tev_submit;
    private TextView tev_trsj, tev_pwbh;
    private RecyclerView rv;
    private Bitmap bmp = null;
    ArrayList<PicBean> arr_image;
    TcsfImageAdapter adapter;
    String[] arr_province;
    String[] arr_abc;
    String province = "浙";
    String A2Z = "F";

    private static final int CAMERA = 10;
    /*********车牌识别选用扫描版(新activity)，或者原生相机选择***********/
    private static final int SM_SCAN = 11;
    private static final int SM_CAMERA = 15;


    private String cphm, trsj, pwbh;

    private boolean isNeedQuery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

    }

    @Override
    protected void findViews() {
        tev_trsj = (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh = (TextView) findViewById(R.id.tev_pwbh);
        tev_print = (TextView) findViewById(R.id.tev_print);
        tev_submit = (TextView) findViewById(R.id.tev_submit);
        rv = (RecyclerView) findViewById(R.id.rv);
        imv_sm = (ImageView) findViewById(R.id.imv_sm);
        sp_province = (Spinner) findViewById(R.id.sp_province);
        sp_ABC = (Spinner) findViewById(R.id.sp_ABC);
        et_cp_num = (EditText) findViewById(R.id.et_cp_num);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("停车收费");
        arr_province = getResources().getStringArray(R.array.provinceName);
        arr_abc = getResources().getStringArray(R.array.A2Z);

        tev_trsj.setText(DateUtil.currentTime());
        arr_image = new ArrayList<PicBean>();
        adapter = new TcsfImageAdapter(arr_image, aty);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(aty, 2, LinearLayout.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new TcsfImageAdapter.OnItemClickListener() {
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


        tev_pwbh.setOnClickListener(FastClickLister);
        tev_submit.setOnClickListener(FastClickLister);
        tev_print.setOnClickListener(FastClickLister);
        tev_trsj.setOnClickListener(FastClickLister);
        imv_sm.setOnClickListener(FastClickLister);
        sp_province.setOnItemSelectedListener(this);
        sp_ABC.setOnItemSelectedListener(this);
        et_cp_num.setTransformationMethod(new UpperCaseTransform());
        et_cp_num.addTextChangedListener(watcher);
        changeState(true, tev_print, tev_submit);
    }

    @Override
    public void onPrintSuccess() {
        changeState(true, tev_print, tev_submit);
        province = "浙";
        A2Z = "F";
        et_cp_num.setText(null);
        arr_image.clear();
        adapter.notifyDataSetChanged();
        tev_pwbh.setText(null);
        tev_trsj.setText(DateUtil.currentTime());
        sp_province.setSelection(0);
        sp_ABC.setSelection(0);


    }


    public void setRecyclerViewHeight(int size) {
        int height = ((size / 2) + 1) * 140 + 10;
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UIUtil.getPx(aty, height));
        layoutParams.setMargins(0, UIUtil.getPx(aty, 5), 0, UIUtil.getPx(aty, 5));
        rv.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA) {
                Bitmap bmp = ImgUtil.getImg800_480(file.getAbsolutePath());
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
                    bmp = ImgUtil.getImg800_480(file_path);
                    doCarNum(carNum);
                    arr_image.add(0, new PicBean(true, bmp));
                    setRecyclerViewHeight(arr_image.size());
                    adapter.notifyDataSetChanged();

                }

            } else if (requestCode == SM_CAMERA) {
                recognition();
            }


        }


    }

    private void doCarNum(String carNum) {
        String province = String.valueOf(carNum.charAt(0));
        String abc = String.valueOf(carNum.charAt(1));
        String num = carNum.substring(2, carNum.length());
        for (int i = 0; i < arr_province.length; i++) {
            if (arr_province[i].equals(province)) {
                isNeedQuery = false;
                sp_province.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < arr_abc.length; i++) {
            if (arr_abc[i].equals(abc)) {
                isNeedQuery = false;
                sp_ABC.setSelection(i);
                break;
            }
        }
        et_cp_num.setText(num);

    }

    String[] arr;


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
        closeKeybord();
        switch (parent.getId()) {
            case R.id.sp_province:
                province = arr_province[position];
                break;
            case R.id.sp_ABC:
                A2Z = arr_abc[position];
                break;
        }

        if (isNeedQuery) {
            watcher.afterTextChanged(et_cp_num.getText());
        }
        isNeedQuery = true;
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
        OkHttpUtils.post().url(HttpApi.URL_PARK_LIST)
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
                    warningShow(bean.ErrorMsg);
                }


            }
        });
    }

    private final String url = "http://jisucpsb.market.alicloudapi.com/licenseplaterecognition/recognize";
    private final String AppKey = "24553193";
    private final String AppCode = "APPCODE 2e476d97d6994a489afb3491b44a2578";


    private void recognition() {
        try {
            ProgressDialogUtil.show(aty, "正在识别.....");
            final Bitmap bmp = ImgUtil.getImg800_480(file.getAbsolutePath());
            final String base64bmp = ImgUtil.bitmapToBase64(bmp);
            OkHttpUtils.post().url(url)
                    .addHeader("X-Ca-Key", AppKey)
                    .addHeader("Authorization", AppCode)
                    .addParams("pic", base64bmp).build().execute(new StringCallback() {

                                                                     @Override
                                                                     public void onError(Call call, Exception e, int id) {
                                                                         onCpjxReturn(false, "图片解析异常", null);
                                                                     }

                                                                     @Override
                                                                     public void onResponse(String response, int id) {
                                                                         log(response);
                                                                         try {
                                                                             final CpBean bean = JSON.parseObject(response, CpBean.class);
                                                                             if (bean.status.equals("0") && bean.result != null) {
                                                                                 onCpjxReturn(true, bean.result.number, bmp);
                                                                             } else {
                                                                                 onCpjxReturn(false, "车牌解析错误", null);
                                                                             }
                                                                         } catch (Exception e) {
                                                                             onCpjxReturn(false, "车牌解析错误", null);
                                                                         }

                                                                     }


                                                                 }

            );
        } catch (Exception e) {
            onCpjxReturn(false, "车牌解析异常", null);
        }

    }


    private void onCpjxReturn(final boolean isResponse, final String msg, final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressDialogUtil.hide();
                if (!isResponse) {
                    warningShow(msg);
                } else {
                    if (msg.length() != 7) {
                        warningShow("车牌识别失败");
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


            }
        });


    }

    NoFastClickLisener FastClickLister = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_pwbh:
                    doQueryEmptyList();
                    break;
                case R.id.tev_trsj:
                    break;
                case R.id.imv_sm:
                    takePhoto(SM_CAMERA);
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
                        warningShow("数据不完整");
                    }


                    break;

                case R.id.tev_submit:
                    cphm = province + A2Z + et_cp_num.getText().toString().trim();
                    trsj = tev_trsj.getText().toString().trim();
                    pwbh = tev_pwbh.getText().toString().trim();
                    if (cphm.length() != 7) {
                        warningShow("车牌号码长度错误！");
                    } else if (!(trsj.length() == 0 || pwbh.length() == 0)) {
                        String pic = getBase64bmpStr();
                        final String road = pwbh.split("--")[0];
                        final String berthName = pwbh.split("--")[1];
                        OkHttpUtils.post().url(HttpApi.URL_PARK)
                                .addParams("UCarnum", cphm).addParams("UpType", "enterprise")
                                .addParams("Road", road).addParams("StartTime", trsj)
                                .addParams("Img", pic).addParams("BerthName", berthName)
                                .addParams("SBYID", getZFRYID())
                                .build().execute(new BeanCallBack(aty, "数据提交中") {

                            @Override
                            public void handleBeanResult(NetResultBean bean) {
                                if (bean.State) {
                                    show("提交数据到服务器成功");
                                    changeState(false, tev_print, tev_submit);
                                } else {
                                    warningShow(bean.ErrorMsg);
                                }

                            }

                        });

                    } else {
                        warningShow("数据不完整！");
                    }
                    break;
            }

        }

    };

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String cphm = province + A2Z + s.toString().trim();
            if (cphm.length() == 7) {
                OkHttpUtils.post().url(HttpApi.URL_PARK_LIST)
                        .addParams("Opername", getOpername())
                        .addParams("type", "1")
                        .addParams("carnum", cphm)
                        .build().execute(new BeanCallBack(aty, null) {
                    @Override
                    public void handleBeanResult(NetResultBean bean) {
                        if (bean.State && bean.total > 0) {
                            //异常数据存在，只修改一条数据
                            final List<TcListBeanResult> list = bean.getResultBeanList(TcListBeanResult.class);
                            new WarningListDialog(aty, list, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    erroExit(list.get(0));
                                }
                            }).show();
                        }
                    }
                });
            }
        }
    };

    //异常离开:不处理具体返回值，不提示
    private void erroExitPayMoney(TcListBeanResult bean, long cost) {
        OkHttpUtils.post().url(HttpApi.URL_WXPAY)
                .addParams("auth_code", "-2")
                .addParams("body", bean.carnum)
                .addParams("fee", cost * 100 + "")
                .addParams("btid", bean.btid + "")
                .build().execute(new BeanCallBack(aty, null) {

            @Override
            public void handleBeanResult(NetResultBean bean) {
            }
        });
    }


    private void erroExit(final TcListBeanResult bean) {
        String endTime = DateUtil.currentTime();
        String lengthTime = DateUtil.getTimeLenth(endTime, bean.starttime);
        final long cost = DateUtil.calMoney(endTime, bean.starttime);
        OkHttpUtils.post().url(HttpApi.URL_PARK_EXIT)
                .addParams("Opername", getOpername())
                .addParams("type", "1")
                .addParams("stoptime", endTime)
                .addParams("money", cost + "")
                .addParams("btid", bean.btid + "")
                .addParams("LengthTime", lengthTime)
                .build().execute(new BeanCallBack(aty, null) {

            @Override
            public void handleBeanResult(NetResultBean result) {
                if (result.State) {
                    erroExitPayMoney(bean, cost);
                }

            }

        });


    }

}