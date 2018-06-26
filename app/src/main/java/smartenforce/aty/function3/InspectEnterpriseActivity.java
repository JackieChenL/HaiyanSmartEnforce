package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.CitOrEntertrsiIdBean;
import smartenforce.bean.EnterpriseDetailBean;
import smartenforce.dialog.ListDialog;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ListItemClickLisener;

public class InspectEnterpriseActivity extends ShowTitleActivity {
    private TextView tev_search;
    private EditText edt_name, edt_operator, edt_license, edt_mobile, edt_address, edt_postcode;
    //经营者--法定代表人  经营场所--住所
    private TextView tev_operator, tev_address;
    private EnterpriseDetailBean enterpriseDetailBean;
    private List<Object> objList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_enterprise);
    }

    @Override
    protected void findViews() {
        tev_search = (TextView) findViewById(R.id.tev_search);
        tev_operator = (TextView) findViewById(R.id.tev_operator);
        tev_address = (TextView) findViewById(R.id.tev_address);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_operator = (EditText) findViewById(R.id.edt_operator);
        edt_license = (EditText) findViewById(R.id.edt_license);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_postcode = (EditText) findViewById(R.id.edt_postcode);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("被检查单位");
        tev_title_right.setText("确定");
        tev_title_right.setOnClickListener(noFastClickLisener);
        tev_search.setOnClickListener(noFastClickLisener);
        enterpriseDetailBean = (EnterpriseDetailBean) getIntent().getSerializableExtra("EnterpriseDetailBean");
        fillData();

    }

    private void fillData() {
        if (enterpriseDetailBean != null) {
            edt_name.setText(enterpriseDetailBean.NameEnt);
            edt_operator.setText(enterpriseDetailBean.LegalrepresentativeEnt);
            edt_license.setText(enterpriseDetailBean.OrganizationCodeEnt);
            edt_mobile.setText(enterpriseDetailBean.MobileEnt);
            edt_address.setText(enterpriseDetailBean.AddressEnt);
            edt_postcode.setText(enterpriseDetailBean.PostcodeEnt);
            tev_operator.setText(enterpriseDetailBean.EntUnitPropertIDEnt == 1 ? "经营者" : "法定代表人");
            tev_address.setText(enterpriseDetailBean.EntUnitPropertIDEnt == 1 ? "经营场所" : "住所");
        } else {
            edt_name.setText(null);
            edt_operator.setText(null);
            edt_license.setText(null);
            edt_mobile.setText(null);
            edt_address.setText(null);
            edt_postcode.setText(null);

        }

    }

    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            switch (v.getId()) {
                case R.id.tev_title_right:
                    saveEnterpriseInfo();
                    break;
                case R.id.tev_search:
                    doSearch();
                    break;

            }
        }
    };


    private void saveEnterpriseInfo() {
        if (enterpriseDetailBean == null) {
            warningShow("此处不允许新录企业");
            return;
        }
        String name = getText(edt_name);
        String operator = getText(edt_operator);
        String license = getText(edt_license);
        String mobile = getText(edt_mobile);
        String address = getText(edt_address);
        String postcode = getText(edt_postcode);

        if (isEmpty(name)) {
            warningShow("单位名称不能为空");
        } else if (isEmpty(operator)) {
            warningShow("经营者不能为空");
        } else if (isEmpty(license)) {
            warningShow("营业执照不能为空");
        } else if (isEmpty(mobile)) {
            warningShow("联系电话不能为空");
        } else if (isEmpty(address)) {
            warningShow("经营地点或住所不能为空");
        } else if (isEmpty(postcode)) {
            warningShow("邮政编码不能为空");
        } else {
            enterpriseDetailBean.NameEnt = name;
            enterpriseDetailBean.LegalrepresentativeEnt = operator;
            enterpriseDetailBean.MobileEnt = mobile;
            enterpriseDetailBean.AddressEnt = address;
            enterpriseDetailBean.OrganizationCodeEnt = license;
            enterpriseDetailBean.PostcodeEnt = postcode;

            String EnterprisePostData = JSON.toJSONString(enterpriseDetailBean);
            OkHttpUtils.post().url(HttpApi.URL_ENTERPRISE_SAVE).addParams("EnterprisePostData", EnterprisePostData)
                    .build().execute(new BeanCallBack(aty, null) {
                @Override
                public void handleBeanResult(NetResultBean bean) {
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

    private void saveInfoAndFinish() {
        Intent it = new Intent();
        it.putExtra("EnterpriseDetailBean", enterpriseDetailBean);
        setResult(RESULT_OK, it);
        finish();

    }


    private void doSearch() {
        String EntName = getText(edt_name);
        if (isEmpty(EntName)) {
            warningShow("查询条件企业名称为空");
            return;
        }
        OkHttpUtils.post().url(HttpApi.URL_ENTERPRISELIST).addParams("EntName", EntName)
                .addParams("Count", "20").build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    final List<EnterpriseDetailBean> list = bean.getResultBeanList(EnterpriseDetailBean.class);
                    if (list.size() == 0) {
                        warningShow("查询结果为空");
                    } else {
                        objList.clear();
                        objList.addAll(list);
                        new ListDialog(aty, objList, new ListItemClickLisener() {
                            @Override
                            public void onItemClick(int P, Object obj) {
                                enterpriseDetailBean = (EnterpriseDetailBean) obj;
                                fillData();
                            }
                        }).setTitle("请选择企业").show();

                    }

                } else {
                    warningShow(bean.ErrorMsg);
                    enterpriseDetailBean = null;
                    fillData();
                }

            }

        });

    }


}




















