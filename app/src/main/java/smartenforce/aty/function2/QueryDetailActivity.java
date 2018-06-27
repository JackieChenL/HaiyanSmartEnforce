package smartenforce.aty.function2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Arrays;

import smartenforce.adapter.ImageAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.InvestigateDetailBean;
import smartenforce.bean.SourcePersonAddBean;
import smartenforce.bean.SourseBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.projectutil.UpLoadImageUtil;
import smartenforce.util.DateUtil;
import smartenforce.widget.FullyGridLayoutManager;


public class QueryDetailActivity extends ShowTitleActivity {


    private TextView tev_dl, tev_wfxw, tev_afdd, tev_dsr;
    private EditText edt_rwms;
    private ScrollView scv;
    private TextView tev_status_current;
    private RecyclerView rcv_l, rcv_r;
    private ImageAdapter adapter_l, adapter_r;
    private ArrayList<String> list_l = new ArrayList<String>();
    private ArrayList<String> list_r = new ArrayList<String>();
    private String UploadOriginSou = "", UploadReturnTas = "";

    private InvestigateDetailBean investigateDetailBean;
    private SourseBean sourseBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xc_detail);
    }

    @Override
    protected void findViews() {
        tev_dl = (TextView) findViewById(R.id.tev_dl);
        tev_wfxw = (TextView) findViewById(R.id.tev_wfxw);
        tev_afdd = (TextView) findViewById(R.id.tev_afdd);
        tev_dsr = (TextView) findViewById(R.id.tev_dsr);
        tev_status_current = (TextView) findViewById(R.id.tev_status_current);
        edt_rwms = (EditText) findViewById(R.id.edt_rwms);
        rcv_l = (RecyclerView) findViewById(R.id.rcv_l);
        rcv_r = (RecyclerView) findViewById(R.id.rcv_r);
        scv = (ScrollView) findViewById(R.id.scv);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("巡查详情");
        sourseBean = (SourseBean) getIntent().getSerializableExtra("SourseBean");
        initPictureAdapter();
        getDetailInfo();
    }


    private void initPictureAdapter() {
        FullyGridLayoutManager layoutManager_l = new FullyGridLayoutManager(aty, 4, LinearLayout.VERTICAL, false);
        FullyGridLayoutManager layoutManager_r = new FullyGridLayoutManager(aty, 4, LinearLayout.VERTICAL, false);
        adapter_l = new ImageAdapter(list_l, aty, scv);
        adapter_r = new ImageAdapter(list_r, aty, scv);
        rcv_l.setLayoutManager(layoutManager_l);
        rcv_r.setLayoutManager(layoutManager_r);
        rcv_l.setAdapter(adapter_l);
        rcv_r.setAdapter(adapter_r);
        adapter_l.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                takePhoto(REQUESTCODE.CAMERA_PRE);
            }

            @Override
            public void onDelImageClick(int p) {
                list_l.remove(p);
                adapter_l.notifyChanged();


            }
        });
        adapter_r.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                takePhoto(REQUESTCODE.CAMERA_AFTER);
            }

            @Override
            public void onDelImageClick(int p) {
                list_r.remove(p);
                adapter_r.notifyChanged();


            }
        });
    }

    private void getDetailInfo() {
        OkHttpUtils.post().url(HttpApi.URL_SOURCEDETAIL).addParams("SourceID", sourseBean.SourceID + "")
                .build().execute(new BeanCallBack(aty, "获取详情中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total == 1) {
                    investigateDetailBean = bean.getResultBeanList(InvestigateDetailBean.class).get(0);
                    fillInvestigateDetail();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });
    }


    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_title_right:
                    if (list_r.size() == 0) {
                        new AlertView("提示", "尚未添加处理后图片，请确认是否提交，提交后将不能更改", null, null, new String[]{"取消", "提交"}, aty, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 1) {
                                    doUploadImg();

                                }
                            }
                        }).show();
                    } else {
                        doUploadImg();
                    }
                    break;
            }

        }
    };

    private void fillInvestigateDetail() {
        if (sourseBean.FlowID == 256) {
            tev_title_right.setText("提交");
            tev_title_right.setOnClickListener(noFastClickLisener);
        }
        tev_status_current.setText(sourseBean.NameSoF);
        tev_dl.setText(investigateDetailBean.NameFiL);
        tev_wfxw.setText(investigateDetailBean.NameThL);
        tev_afdd.setText(investigateDetailBean.AddressSou);
        tev_dsr.setText(investigateDetailBean.NameECSou);
        edt_rwms.setText(investigateDetailBean.ContentSou);
        if (!isEmpty(investigateDetailBean.UploadOriginSou)) {
            String[] url_l = investigateDetailBean.UploadOriginSou.split("\\|");
            list_l.addAll(Arrays.asList(url_l));
            adapter_l.notifyDataSetChanged();

        }
        if (!isEmpty(investigateDetailBean.UploadReturnTas)) {
            String[] url_r = investigateDetailBean.UploadReturnTas.split("\\|");
            list_r.addAll(Arrays.asList(url_r));
            adapter_r.notifyDataSetChanged();

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE.CAMERA_PRE) {
                list_l.add(file.getAbsolutePath());
                adapter_l.notifyChanged();
            } else if (requestCode == REQUESTCODE.CAMERA_AFTER) {
                list_r.add(file.getAbsolutePath());
                adapter_r.notifyChanged();
            }


        }


    }

    private void doUploadImgBefore() {
        String base64_l = adapter_l.getImageFile();
        if (!isEmpty(base64_l)) {
            String[] array = new String[]{"enterprise", "citizen", "anonymous"};
            UpLoadImageUtil.uploadImage(aty, app.userID, array[investigateDetailBean.EntOrCitiSou - 1], base64_l, new UpLoadImageUtil.onUploadImgCallBack() {
                @Override
                public void onSuccess(String picArray) {
                    UploadOriginSou = picArray;
                    doUploadImgAfter();
                }

                @Override
                public void onFail(String msg) {
                    warningShow(msg);
                }
            });

        } else {
            doUploadImgAfter();
        }


    }

    private void doUploadImgAfter() {
        String base64_r = adapter_r.getImageFile();
        if (!isEmpty(base64_r)) {
            String[] array = new String[]{"enterprise", "citizen", "anonymous"};
            UpLoadImageUtil.uploadImage(aty, app.userID, array[investigateDetailBean.EntOrCitiSou - 1], base64_r, new UpLoadImageUtil.onUploadImgCallBack() {
                @Override
                public void onSuccess(String picArray) {
                    UploadReturnTas = picArray;
                    doUploadInfo();
                }

                @Override
                public void onFail(String msg) {
                    warningShow(msg);
                }
            });

        } else {
            doUploadInfo();
        }

    }


    //上传图片
    private void doUploadImg() {
        doUploadImgBefore();

    }


    private void doUploadInfo() {
        SourcePersonAddBean bean = new SourcePersonAddBean();
        bean.AddressSou = investigateDetailBean.AddressSou;
        bean.GpsXYSou = investigateDetailBean.GpsXYSou;
        bean.EntOrCitID = investigateDetailBean.EntOrCitiID;
        bean.SubmitOrSave = "submit";
        bean.FirstLevelIDSou = investigateDetailBean.FirstLevelIDSou;
        bean.SecondLevelIDSou = investigateDetailBean.SecondLevelIDSou;
        bean.ThirdLevelIDSou = investigateDetailBean.ThirdLevelIDSou;
        bean.ContentSou = getText(edt_rwms);
        if (isEmpty(UploadOriginSou)) {
            bean.UploadOriginSou = isEmpty(adapter_l.getImageUrlStr()) ? "" : adapter_l.getImageUrlStr();
        } else {
            bean.UploadOriginSou = UploadOriginSou + (isEmpty(adapter_l.getImageUrlStr()) ? "" : "|" + adapter_l.getImageUrlStr());
        }
        if (isEmpty(UploadReturnTas)) {
            bean.UploadReturnTas = isEmpty(adapter_r.getImageUrlStr()) ? "" : adapter_r.getImageUrlStr();
        } else {
            bean.UploadReturnTas = UploadReturnTas + (isEmpty(adapter_r.getImageUrlStr()) ? "" : "|" + adapter_r.getImageUrlStr());
        }

        bean.EntOrCitiSou = investigateDetailBean.EntOrCitiSou;
        bean.EntryTimeSou = DateUtil.currentTime();
        bean.UserID = app.userID;
        bean.DepartmentID = app.DepartmentID;
        bean.NameECSou = investigateDetailBean.NameECSou;
        bean.NameTas = DateUtil.createXCName();
        bean.SourceID = sourseBean.SourceID;
        String SourcePersonPostData = JSON.toJSONString(bean);
        log(SourcePersonPostData);
        OkHttpUtils.post().url(HttpApi.URL_SOURCEPERSONADD).addParams("SourcePersonPostData", SourcePersonPostData)
                .build().execute(new BeanCallBack(aty, "数据提交中") {

            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("操作成功");
                    finish();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });


    }


}
















