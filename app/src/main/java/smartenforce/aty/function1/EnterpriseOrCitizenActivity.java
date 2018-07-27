package smartenforce.aty.function1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.CitOrEntertrsiIdBean;
import smartenforce.bean.CitizenBean;
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.dialog.ListDialog;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ListItemClickLisener;
import smartenforce.projectutil.IDCardUtil;
import smartenforce.util.ImgUtil;
import smartenforce.util.RegexUtil;
import smartenforce.zxing.ScanActivity;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;


public class EnterpriseOrCitizenActivity extends ShowTitleActivity implements RadioGroup.OnCheckedChangeListener {
    private int DSRLX_TYPE = 1;
    //切换选项之后切换企业与自然人布局选项
    private RadioGroup rg_dsrlx;
    private RadioButton rbtn_person, rbtn_qy, rbtn_secret;

    private LinearLayout llt_person;
    private EditText edt_name_p, edt_sfzh_p, edt_lxfs_p;
    private TextView tev_name_r_p;
    private CitizenBean citizenBean;


    private LinearLayout llt_qy;
    private EditText edt_qymc, edt_fzr, edt_lxfs, edt_dzmc;
    private TextView tev_ssqy, tev_dwxz, tev_dwfl;
    private String[] array_ssqy, array_dwxz, array_dwfl;
    private AlertView alertView_ssqy, alertView_dwfl;
    private TextView tev_qymc_r, tev_fzr_r;
    private EnterpriseDetailBean enterpriseDetailBean;

    private TextView tev_left, tev_right;
    private List<Object> objList=new ArrayList<>();


    private String address, nation, sex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsr);
    }

    @Override
    protected void findViews() {
        rg_dsrlx = (RadioGroup) findViewById(R.id.rg_dsrlx);
        rbtn_person = (RadioButton) findViewById(R.id.rbtn_person);
        rbtn_qy = (RadioButton) findViewById(R.id.rbtn_qy);
        rbtn_secret = (RadioButton) findViewById(R.id.rbtn_secret);


        llt_person = (LinearLayout) findViewById(R.id.llt_person);
        edt_name_p = (EditText) findViewById(R.id.edt_name_p);
        edt_sfzh_p = (EditText) findViewById(R.id.edt_sfzh_p);
        edt_lxfs_p = (EditText) findViewById(R.id.edt_lxfs_p);
        tev_name_r_p = (TextView) findViewById(R.id.tev_name_r_p);


        llt_qy = (LinearLayout) findViewById(R.id.llt_qy);
        edt_qymc = (EditText) findViewById(R.id.edt_qymc);
        edt_fzr = (EditText) findViewById(R.id.edt_fzr);
        edt_lxfs = (EditText) findViewById(R.id.edt_lxfs);
        tev_qymc_r = (TextView) findViewById(R.id.tev_qymc_r);
        tev_fzr_r = (TextView) findViewById(R.id.tev_fzr_r);
        tev_ssqy = (TextView) findViewById(R.id.tev_ssqy);
        tev_dwxz = (TextView) findViewById(R.id.tev_dwxz);
        tev_dwfl = (TextView) findViewById(R.id.tev_dwfl);
        edt_dzmc = (EditText) findViewById(R.id.edt_dzmc);

        tev_left = (TextView) findViewById(R.id.tev_left);
        tev_right = (TextView) findViewById(R.id.tev_right);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("当事人");
        tev_left.setText("扫描二维码");
        tev_right.setText("确定");
        rg_dsrlx.setOnCheckedChangeListener(this);
        tev_qymc_r.setOnClickListener(noFastClickLisener);
        tev_fzr_r.setOnClickListener(noFastClickLisener);
        tev_name_r_p.setOnClickListener(noFastClickLisener);
        tev_left.setOnClickListener(noFastClickLisener);
        tev_right.setOnClickListener(noFastClickLisener);
        tev_ssqy.setOnClickListener(noFastClickLisener);
        tev_dwxz.setOnClickListener(noFastClickLisener);
        tev_dwfl.setOnClickListener(noFastClickLisener);
        array_ssqy = getResources().getStringArray(R.array.area);
        array_dwxz = getResources().getStringArray(R.array.qyxz);
        array_dwfl = getResources().getStringArray(R.array.qyfl);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (scanResult != null) {
                    String result = scanResult.getContents();
                    getEnterpriseDetail(result, app.userID);
                }
            } else if (requestCode == REQUESTCODE.SCAN_IDCARD) {
                Bitmap bmp = ImgUtil.getFileImg(file.getAbsolutePath());
                String base64IdCard = ImgUtil.bitmapToBase64(bmp);
                getIdCardInfo(base64IdCard);

            }
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_qy:
                DSRLX_TYPE = 1;
                llt_person.setVisibility(View.GONE);
                llt_qy.setVisibility(View.VISIBLE);
                tev_left.setText("扫描二维码");
                tev_left.setVisibility(View.VISIBLE);
                break;
            case R.id.rbtn_person:
                DSRLX_TYPE = 2;
                llt_person.setVisibility(View.VISIBLE);
                llt_qy.setVisibility(View.GONE);
                tev_left.setText("扫描身份证");
                tev_left.setVisibility(View.VISIBLE);
                break;
            case R.id.rbtn_secret:
                DSRLX_TYPE = 3;
                llt_person.setVisibility(View.GONE);
                llt_qy.setVisibility(View.GONE);
                tev_left.setVisibility(View.GONE);
                break;


        }

    }

    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_ssqy:
                    if (alertView_ssqy == null) {
                        alertView_ssqy = getShowAlert("选择所属区域", array_ssqy, tev_ssqy);
                    }
                    alertView_ssqy.show();
                    break;

                case R.id.tev_dwfl:
                    if (alertView_dwfl == null) {
                        alertView_dwfl = getShowAlertRelevance("选择单位分类", array_dwfl, tev_dwfl, tev_dwxz);
                    }
                    alertView_dwfl.show();
                    break;

                case R.id.tev_dwxz:
                    final String[] arr = getSecondLevel();
                    if (arr == null) {
                        warningShow("请先选择单位分类");
                        return;
                    }
                    new AlertView("选择单位性质", null, null, null, arr, aty, AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            tev_dwxz.setText(arr[position]);

                        }
                    }).show();
                    break;

                case R.id.tev_qymc_r:
                    getEnterpriseList();
                    break;
                case R.id.tev_fzr_r:
                    getEnterpriseList();
                    break;
                case R.id.tev_name_r_p:
                    getGetCitizenList();
                    break;
                case R.id.tev_left:
                    if (DSRLX_TYPE == 1) {
                        startActivityForResult(new Intent(aty, ScanActivity.class), REQUEST_CODE);
                    } else if (DSRLX_TYPE == 2) {
                        takePhoto(REQUESTCODE.SCAN_IDCARD);
                    }

                    break;
                case R.id.tev_right:

                    if (DSRLX_TYPE == 1) {
                        saveEnterpriseInfo();
                    } else if (DSRLX_TYPE == 2) {
                        saveCitizenInfo();
                    } else {
                        saveInfoAndFinish();
                    }

                    break;
            }

        }
    };

    private void getIdCardInfo(String base64IdCard) {
        IDCardUtil.getInstance().getIdCardInfo(aty, base64IdCard, new IDCardUtil.onIdCardCallBack() {
            @Override
            public void onSuccess(String name, String idCardNum, String sex, String nation, String address) {
                edt_name_p.setText(name);
                edt_sfzh_p.setText(idCardNum);
            }

            @Override
            public void onErroMsg(String msg) {
                edt_name_p.setText(null);
                edt_sfzh_p.setText(null);
                warningShow(msg);
            }
        });


    }


    private void saveEnterpriseInfo() {
        String qymc = getText(edt_qymc);
        String fzr = getText(edt_fzr);
        String tel = getText(edt_lxfs);
        String dzmc = getText(edt_dzmc);
        String dwfl = getText(tev_dwfl);
        String dwxz = getText(tev_dwxz);
        String ssqy = getText(tev_ssqy);

        if (isEmpty(qymc)) {
            warningShow("企业名称不能为空");
        } else if (isEmpty(fzr)) {
            warningShow("企业负责人不能为空");
        } else if (isEmpty(dzmc)) {
            warningShow("店招不能为空");
        } else if (isEmpty(dwfl)) {
            warningShow("单位分类不能为空");
        } else if (isEmpty(dwxz)) {
            warningShow("单位性质不能为空");
        } else if (isEmpty(ssqy)) {
            warningShow("单位所属区域不能为空");
        } else if (isEmpty(tel)) {
            warningShow("联系电话不能为空");
        } else {
            if (enterpriseDetailBean == null) {
                enterpriseDetailBean = new EnterpriseDetailBean();
            }
            enterpriseDetailBean.NameEnt = qymc;
            enterpriseDetailBean.LegalrepresentativeEnt = fzr;
            enterpriseDetailBean.SignageEnt = dzmc;
            enterpriseDetailBean.MobileEnt = tel;
            enterpriseDetailBean.AreaIDEnt = getPosionID(ssqy, array_ssqy);
            enterpriseDetailBean.EntClassifyIDEnt = getPosionID(dwfl, array_dwfl);
            enterpriseDetailBean.EntUnitPropertIDEnt = getPosionID(dwxz, array_dwxz);
            String EnterprisePostData = JSON.toJSONString(enterpriseDetailBean);
            OkHttpUtils.post().url(HttpApi.URL_ENTERPRISE_SAVE).addParams("EnterprisePostData", EnterprisePostData)
                    .build().execute(new BeanCallBack(aty, null) {
                @Override
                public void handleBeanResult(NetResultBean bean) {
                    //不是新增不管成功与否直接返回
                    if (enterpriseDetailBean.EnterpriseID != -1) {
                        saveInfoAndFinish();
                    } else {
                        if (bean.State && bean.total == 1) {
                            enterpriseDetailBean.EnterpriseID = bean.getResultBeanList(CitOrEntertrsiIdBean.class).get(0).EnterpriseID;
                            saveInfoAndFinish();
                        } else {
                            warningShow(bean.ErrorMsg);
                        }

                    }

                }
            });
        }

    }

    private void saveCitizenInfo() {
        String Name = getText(edt_name_p);
        String IDCard = getText(edt_sfzh_p);
        String Tel = getText(edt_lxfs_p);
        if (isEmpty(Name)) {
            warningShow("自然人姓名不能为空");
        } else if (!RegexUtil.isIDCard(IDCard)) {
            warningShow("身份证号为空或格式错误");
        } else if (isEmpty(Tel)) {
            warningShow("联系电话不能为空");
        } else {
            if (citizenBean == null) {
                citizenBean = new CitizenBean();
            }
            citizenBean.NameCit = Name;
            citizenBean.IdentityCardCit = IDCard;
            citizenBean.MobileCit = Tel;
            String CitizenPostData = JSON.toJSONString(citizenBean);
            OkHttpUtils.post().url(HttpApi.URL_CITIZEN_SAVE).addParams("CitizenPostData", CitizenPostData)
                    .build().execute(new BeanCallBack(aty, null) {
                @Override
                public void handleBeanResult(NetResultBean bean) {
                    if (citizenBean.CitizenID != -1) {
                        saveInfoAndFinish();
                    } else {
                        if (bean.State && bean.total == 1) {
                            citizenBean.CitizenID = bean.getResultBeanList(CitOrEntertrsiIdBean.class).get(0).CitizenID;
                            saveInfoAndFinish();
                        } else {
                            warningShow(bean.ErrorMsg);
                        }
                    }

                }
            });
        }

    }


    private void getEnterpriseDetail(String EnterpriseID, String userID) {
        OkHttpUtils.post().url(HttpApi.URL_ENTERPRISEDETAIL).addParams("EnterpriseID", EnterpriseID)
                .addParams("UserID", userID).build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    if (bean.total == 1) {
                        enterpriseDetailBean = bean.getResultBeanList(EnterpriseDetailBean.class).get(0);
                    } else {
                        enterpriseDetailBean = null;
                        warningShow("该二维码查询结果错误");
                    }
                } else {
                    enterpriseDetailBean = null;
                    warningShow(bean.ErrorMsg);
                }
                fillDetailInformation();
            }

        });

    }

    //企业信息填写
    private void fillDetailInformation() {
        if (enterpriseDetailBean != null) {
            edt_qymc.setText(enterpriseDetailBean.NameEnt);
            edt_fzr.setText(enterpriseDetailBean.LegalrepresentativeEnt);
            edt_lxfs.setText(enterpriseDetailBean.MobileEnt);
            edt_dzmc.setText(enterpriseDetailBean.SignageEnt);
            tev_dwfl.setText(enterpriseDetailBean.EntClassifyIDEnt > 0 ? array_dwfl[enterpriseDetailBean.EntClassifyIDEnt - 1] : null);
            tev_dwxz.setText(enterpriseDetailBean.EntUnitPropertIDEnt > 0 ? array_dwxz[enterpriseDetailBean.EntUnitPropertIDEnt - 1] : null);
            tev_ssqy.setText(enterpriseDetailBean.AreaIDEnt > 0 ? array_ssqy[enterpriseDetailBean.AreaIDEnt - 1] : null);
        } else {
            edt_qymc.setText(null);
            edt_fzr.setText(null);
            edt_lxfs.setText(null);
            edt_dzmc.setText(null);
            tev_dwfl.setText(null);
            tev_dwxz.setText(null);
            tev_ssqy.setText(null);
        }

    }

    // 查询企业列表
    private void getEnterpriseList() {
        String EntName = getText(edt_qymc);
        String Legal = getText(edt_fzr);
        if (isEmpty(EntName) && isEmpty(Legal)) {
            warningShow("查询条件企业名称或负责人至少要输入一项");
            return;
        }
        OkHttpUtils.post().url(HttpApi.URL_ENTERPRISELIST).addParams("EntName", EntName)
                .addParams("Legal", Legal).addParams("Count", "20").build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total > 0) {
                    final List<EnterpriseDetailBean> list = bean.getResultBeanList(EnterpriseDetailBean.class);
                    objList.clear();
                    objList.addAll(list);
                    new ListDialog(aty, objList, new ListItemClickLisener() {
                        @Override
                        public void onItemClick(int P, Object obj) {
                            enterpriseDetailBean=(EnterpriseDetailBean) obj;
                            fillDetailInformation();
                        }
                    }).setTitle("请选择企业").show();


                } else {
                    enterpriseDetailBean = null;
                    if (bean.State) {
                        warningShow("查询结果为空");
                    } else {
                        warningShow(bean.ErrorMsg);
                    }
                    fillDetailInformation();
                }


            }

        });

    }


    //自然人信息填写
    private void fillCitiZenInformation() {
        if (citizenBean != null) {
            edt_name_p.setText(citizenBean.NameCit);
            edt_sfzh_p.setText(citizenBean.IdentityCardCit);
            edt_lxfs_p.setText(citizenBean.MobileCit);

        } else {
            edt_name_p.setText(null);
            edt_sfzh_p.setText(null);
            edt_lxfs_p.setText(null);
        }

    }


    // 查询自然人列表
    private void getGetCitizenList() {
        String CitName = getText(edt_name_p);
        if (isEmpty(CitName)) {
            warningShow("查询自然人输入不能为空");
            return;
        }
        OkHttpUtils.post().url(HttpApi.URL_CITIZENLIST).addParams("CitName", CitName)
                .addParams("Count", "20").build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total > 0) {
                    final List<CitizenBean> list = bean.getResultBeanList(CitizenBean.class);
                    objList.clear();
                    objList.addAll(list);
                    new ListDialog(aty, objList, new ListItemClickLisener() {
                        @Override
                        public void onItemClick(int P, Object obj) {
                            citizenBean=(CitizenBean) obj;
                            fillCitiZenInformation();
                        }
                    }).setTitle("请选择自然人").show();

                } else {
                    citizenBean = null;
                    if (bean.State) {
                        warningShow("查询结果为空");
                    } else {
                        warningShow(bean.ErrorMsg);
                    }
                    fillCitiZenInformation();
                }


            }


        });

    }


    private String[] getSecondLevel() {
        String dwfl = getText(tev_dwfl);
        int FL_ID = -1;
        if (isEmpty(dwfl)) {
            return null;
        }
        for (int i = 0; i < array_dwfl.length; i++) {
            if (array_dwfl[i].equals(dwfl)) {
                FL_ID = i;
                break;
            }

        }
        if (FL_ID == 0) {
            return new String[]{array_dwxz[0], array_dwxz[1]};
        } else if (FL_ID == 1) {
            return new String[]{array_dwxz[2], array_dwxz[3], array_dwxz[4]};
        } else if (FL_ID == 2) {
            return new String[]{array_dwxz[5], array_dwxz[6], array_dwxz[7], array_dwxz[8]};
        } else if (FL_ID == 3) {
            return new String[]{array_dwxz[9], array_dwxz[10], array_dwxz[11], array_dwxz[12]};
        } else {
            return new String[]{array_dwxz[13], array_dwxz[14], array_dwxz[15]};
        }
    }

    private int getPosionID(String text, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(text)) {
                return i + 1;
            }
        }
        return -1;
    }


    private void saveInfoAndFinish() {
        Intent it = new Intent();
        it.putExtra("TYPE", DSRLX_TYPE);
        if (DSRLX_TYPE == 1) {
            it.putExtra("OBJECT", enterpriseDetailBean);
        } else if (DSRLX_TYPE == 2) {
            it.putExtra("OBJECT", citizenBean);
        }
        setResult(RESULT_OK, it);
        finish();
    }


}















