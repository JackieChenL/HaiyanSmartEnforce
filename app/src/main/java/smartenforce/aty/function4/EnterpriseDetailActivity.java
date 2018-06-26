package smartenforce.aty.function4;

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
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.projectutil.UpLoadImageUtil;
import smartenforce.widget.FullyGridLayoutManager;

public class EnterpriseDetailActivity extends ShowTitleActivity {
    private EditText edt_dz, edt_dwmc, edt_zjh, edt_zczj, edt_yzbm, edt_fzr;
    private EditText edt_sfz, edt_lxfs, edt_zw, edt_hjdz, edt_dwdz, edt_jyfw, edt_dwbq;
    private TextView tev_dwfl, tev_zjlx, tev_ssqy, tev_clrq, tev_jyzk;
    private TextView tev_sex, tev_mz, tev_dwxz;
    private RecyclerView rv_photo;
    private ImageAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();
    private String[] array_ssqy, array_dwxz, array_dwfl, array_nation, array_zjlx, array_sex, array_jyzk;
    private AlertView alertView_ssqy, alertView_dwfl, alertView_nation, alertView_zjlx, alertView_sex, alertView_jyzk;
    private EnterpriseDetailBean bean;
    private ScrollView scv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qy_detail);
    }

    @Override
    protected void findViews() {
        rv_photo = (RecyclerView) findViewById(R.id.rv_photo);
        edt_dz = (EditText) findViewById(R.id.edt_dz);
        edt_dwmc = (EditText) findViewById(R.id.edt_dwmc);
        edt_zjh = (EditText) findViewById(R.id.edt_zjh);
        edt_zczj = (EditText) findViewById(R.id.edt_zczj);
        edt_yzbm = (EditText) findViewById(R.id.edt_yzbm);
        edt_dwdz = (EditText) findViewById(R.id.edt_dwdz);
        edt_fzr = (EditText) findViewById(R.id.edt_fzr);
        edt_sfz = (EditText) findViewById(R.id.edt_sfz);
        edt_lxfs = (EditText) findViewById(R.id.edt_lxfs);
        edt_hjdz = (EditText) findViewById(R.id.edt_hjdz);
        edt_zw = (EditText) findViewById(R.id.edt_zw);
        edt_jyfw = (EditText) findViewById(R.id.edt_jyfw);

        tev_dwfl = (TextView) findViewById(R.id.tev_dwfl);
        tev_dwxz = (TextView) findViewById(R.id.tev_dwxz);
        edt_dwbq = (EditText) findViewById(R.id.edt_dwbq);
        tev_zjlx = (TextView) findViewById(R.id.tev_zjlx);
        tev_ssqy = (TextView) findViewById(R.id.tev_ssqy);
        tev_clrq = (TextView) findViewById(R.id.tev_clrq);
        tev_jyzk = (TextView) findViewById(R.id.tev_jyzk);
        tev_sex = (TextView) findViewById(R.id.tev_sex);
        tev_mz = (TextView) findViewById(R.id.tev_mz);
        scv = (ScrollView) findViewById(R.id.scv);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("店铺详情");
        initPictureAdapter();
        array_ssqy = getResources().getStringArray(R.array.area);
        array_dwxz = getResources().getStringArray(R.array.qyxz);
        array_dwfl = getResources().getStringArray(R.array.qyfl);
        array_nation = getResources().getStringArray(R.array.nations);
        array_sex = getResources().getStringArray(R.array.sex);
        array_zjlx = getResources().getStringArray(R.array.zjlx);
        array_jyzk = getResources().getStringArray(R.array.jyzk);
        bean = (EnterpriseDetailBean) getIntent().getSerializableExtra("EnterpriseDetailBean");
        tev_title_right.setText("保存");
        tev_mz.setText("汉族");
        if (bean != null) {
            edt_dwmc.setText(bean.NameEnt);
            edt_fzr.setText(bean.LegalrepresentativeEnt);
            edt_dz.setText(bean.SignageEnt);
            edt_lxfs.setText(bean.MobileEnt);
            tev_dwfl.setText(bean.EntClassifyIDEnt > 0 ? array_dwfl[bean.EntClassifyIDEnt - 1] : null);
            tev_dwxz.setText(bean.EntUnitPropertIDEnt > 0 ? array_dwxz[bean.EntUnitPropertIDEnt - 1] : null);
            tev_ssqy.setText(bean.AreaIDEnt > 0 ? array_ssqy[bean.AreaIDEnt - 1] : null);
            if (bean.CertificateTypeID == 1) {
                tev_zjlx.setText("统一识别代码");
                edt_zjh.setText(bean.UniformSocialCodeEnt);
            } else if (bean.CertificateTypeID == 2) {
                tev_zjlx.setText("注册号");
                edt_zjh.setText(bean.BusinessLicenseEnt);

            } else if (bean.CertificateTypeID == 3) {
                tev_zjlx.setText("组织机构代码证");
                edt_zjh.setText(bean.OrganizationCodeEnt);
            }
            edt_dwbq.setText(bean.EntLabArray);
            edt_zczj.setText(bean.RegisteredCapitalEnt);
            tev_clrq.setText(bean.EstablishedDateEnt);
            tev_jyzk.setText(bean.EntOperatingStateEnt == 1 ? "续存" : "停止");
            edt_dwdz.setText(bean.AddressEnt);
            edt_yzbm.setText(bean.PostcodeEnt);
            tev_sex.setText(bean.SexEnt);
            tev_mz.setText(bean.NationEnt);
            edt_sfz.setText(bean.IdentityCardEnt);
            edt_zw.setText(bean.PositionEnt);
            edt_hjdz.setText(bean.CitAddressEnt);
            edt_jyfw.setText(bean.ScopeEnt);
            if (!isEmpty(bean.PhotoEnt)) {
                String[] picArray = bean.PhotoEnt.split("\\|");
                list.addAll(Arrays.asList(picArray));
                adapter.notifyDataSetChanged();
            }
        }
        tev_ssqy.setOnClickListener(noFastClickLisener);
        tev_dwfl.setOnClickListener(noFastClickLisener);
        tev_dwxz.setOnClickListener(noFastClickLisener);
        tev_zjlx.setOnClickListener(noFastClickLisener);
        tev_jyzk.setOnClickListener(noFastClickLisener);
        tev_sex.setOnClickListener(noFastClickLisener);
        tev_mz.setOnClickListener(noFastClickLisener);
        tev_title_right.setOnClickListener(noFastClickLisener);


    }

    private void initPictureAdapter() {
        FullyGridLayoutManager layoutManager = new FullyGridLayoutManager(aty, 4, LinearLayout.VERTICAL, false);
        adapter = new ImageAdapter(list, aty,scv);
        rv_photo.setLayoutManager(layoutManager);
        rv_photo.setAdapter(adapter);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onImageAddClick() {
                takePhoto(REQUESTCODE.CAMERA);
            }


            @Override
            public void onDelImageClick(int p) {
                list.remove(p);
                adapter.notifyChanged();

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE.CAMERA) {
                list.add(file.getAbsolutePath());
                adapter.notifyChanged();
            }
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
                case R.id.tev_zjlx:
                    if (alertView_zjlx == null) {
                        alertView_zjlx = getShowAlert("选择证件类型", array_zjlx, tev_zjlx);
                    }
                    alertView_zjlx.show();
                    break;

                case R.id.tev_dwfl:
                    if (alertView_dwfl == null) {
                        alertView_dwfl = getShowAlertRelevance("选择单位分类", array_dwfl, tev_dwfl, tev_dwxz);
                    }
                    alertView_dwfl.show();
                    break;
                case R.id.tev_sex:
                    if (alertView_sex == null) {
                        alertView_sex = getShowAlert("选择性别", array_sex, tev_sex);
                    }
                    alertView_sex.show();
                    break;
                case R.id.tev_jyzk:
                    if (alertView_jyzk == null) {
                        alertView_jyzk = getShowAlert("选择经营状况", array_jyzk, tev_jyzk);
                    }
                    alertView_jyzk.show();
                    break;

                case R.id.tev_mz:
                    if (alertView_nation == null) {
                        alertView_nation = getShowAlert("选择民族", array_nation, tev_mz);
                    }
                    alertView_nation.show();
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
                case R.id.tev_title_right:
                    doCheck();
                    break;


            }
        }
    };

    //校验字段
    private void doCheck() {
        String nameEnt = getText(edt_dwmc);
        String fzr = getText(edt_fzr);
        String dz = getText(edt_dz);
        String dwfl = getText(tev_dwfl);
        String dwxz = getText(tev_dwxz);
        String ssqy = getText(tev_ssqy);
        String mobile = getText(edt_lxfs);
        if (isEmpty(nameEnt)) {
            warningShow("企业名称不能为空");
        } else if (isEmpty(fzr)) {
            warningShow("企业负责人不能为空");
        } else if (isEmpty(dz)) {
            warningShow("店招不能为空");
        } else if (isEmpty(dwfl)) {
            warningShow("单位分类不能为空");
        } else if (isEmpty(dwxz)) {
            warningShow("单位性质不能为空");
        } else if (isEmpty(ssqy)) {
            warningShow("单位所属区域不能为空");
        } else if (isEmpty(mobile)) {
            warningShow("联系电话不能为空");
        } else {
            upLoadImg();
        }
    }


    //保存或修改企业信息
    private void doSaveOrUpdate(String picArray) {
        if (bean == null) {
            bean = new EnterpriseDetailBean();
        }
        bean.NameEnt = getText(edt_dwmc);
        bean.LegalrepresentativeEnt = getText(edt_fzr);
        bean.SignageEnt = getText(edt_dz);
        bean.MobileEnt = getText(edt_lxfs);
        bean.EntClassifyIDEnt = getPosionID(getText(tev_dwfl), array_dwfl);
        bean.AreaIDEnt = getPosionID(getText(tev_ssqy), array_ssqy);
        bean.EntUnitPropertIDEnt = getPosionID(getText(tev_dwxz), array_dwxz);
        bean.CertificateTypeID = getPosionID(getText(tev_zjlx), array_zjlx);
        switch (bean.CertificateTypeID) {
            case 1:
                bean.UniformSocialCodeEnt = getText(edt_zjh);
                break;
            case 2:
                bean.BusinessLicenseEnt = getText(edt_zjh);
                break;
            case 3:
                bean.OrganizationCodeEnt = getText(edt_zjh);
                break;
            case -1:
                //-1不上传证件号
                break;
        }
        bean.EntOperatingStateEnt = getPosionID(getText(tev_jyzk), array_jyzk);
       // TODO:暂时不加企业标签
//        bean.EntLabArray = getText(edt_dwbq);
        bean.RegisteredCapitalEnt = getText(edt_zczj);
        bean.EstablishedDateEnt = getText(tev_clrq);
        bean.AddressEnt = getText(edt_dwdz);
        bean.PostcodeEnt = getText(edt_yzbm);
        bean.SexEnt = getText(tev_sex);
        bean.NationEnt = getText(tev_mz);
        bean.IdentityCardEnt = getText(edt_sfz);
        bean.PositionEnt = getText(edt_zw);
        bean.CitAddressEnt = getText(edt_hjdz);
        bean.ScopeEnt = getText(edt_jyfw);
        bean.PhotoEnt = picArray;

        String EnterprisePostData = JSON.toJSONString(bean);
        OkHttpUtils.post().url(HttpApi.URL_ENTERPRISE_SAVE).addParams("EnterprisePostData", EnterprisePostData)
                .build().execute(new BeanCallBack(aty, null) {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("成功");
                    finish();
                } else {
                    warningShow(bean.ErrorMsg);
                }

            }
        });


    }


    private void upLoadImg() {
        String base64FileImg = adapter.getImageFile();
        if (isEmpty(base64FileImg)) {
            doSaveOrUpdate(adapter.getImageUrlStr());
        } else {
            UpLoadImageUtil.uploadImage(aty, app.userID, "enterprise", base64FileImg, new UpLoadImageUtil.onUploadImgCallBack() {
                @Override
                public void onSuccess(String picArray) {
                    doSaveOrUpdate(picArray + (isEmpty(adapter.getImageUrlStr()) ? "" : ("|" + adapter.getImageUrlStr())));
                }

                @Override
                public void onFail(String msg) {
                    warningShow(msg);
                }
            });

        }

    }


}




















