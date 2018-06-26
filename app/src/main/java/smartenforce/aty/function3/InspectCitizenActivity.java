package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.CitOrEntertrsiIdBean;
import smartenforce.bean.CitizenBean;
import smartenforce.dialog.ListDialog;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ListItemClickLisener;
import smartenforce.util.RegexUtil;

public class InspectCitizenActivity extends ShowTitleActivity {
    private TextView tev_Name_search, tev_sex, tev_nation;
    private EditText edt_Name, edt_card, edt_phone, edt_address, edt_postcode;
    private AlertView alertView_sex, alertView_nation;
    private String[] arr_sex, arr_nation;
    private CitizenBean citizenBean;
    private List<Object> objList=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_citizen);
    }

    @Override
    protected void findViews() {
        tev_Name_search = (TextView) findViewById(R.id.tev_Name_search);
        tev_sex = (TextView) findViewById(R.id.tev_sex);
        tev_nation = (TextView) findViewById(R.id.tev_nation);

        edt_Name = (EditText) findViewById(R.id.edt_Name);
        edt_card = (EditText) findViewById(R.id.edt_card);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_postcode = (EditText) findViewById(R.id.edt_postcode);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("被检查人");
        tev_title_right.setText("确定");
        arr_sex = getResources().getStringArray(R.array.sex);
        arr_nation = getResources().getStringArray(R.array.nations);
        citizenBean = (CitizenBean) getIntent().getSerializableExtra("CitizenBean");
        tev_nation.setText("汉族");
        tev_sex.setOnClickListener(noFastClickLisener);
        tev_nation.setOnClickListener(noFastClickLisener);
        tev_title_right.setOnClickListener(noFastClickLisener);
        tev_Name_search.setOnClickListener(noFastClickLisener);
        fillData();
    }

    private void fillData() {
        if (citizenBean != null) {
            edt_Name.setText(citizenBean.NameCit);
            tev_sex.setText(citizenBean.SexCit);
            tev_nation.setText(citizenBean.NationCit);
            edt_card.setText(citizenBean.IdentityCardCit);
            edt_phone.setText(citizenBean.MobileCit);
            edt_address.setText(citizenBean.AddressCit);
            edt_postcode.setText(citizenBean.PostcodeCit);
        } else {
            edt_Name.setText(null);
            tev_sex.setText(null);
            tev_nation.setText("汉族");
            edt_card.setText(null);
            edt_phone.setText(null);
            edt_address.setText(null);
            edt_postcode.setText(null);
        }

    }

    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            switch (v.getId()) {
                case R.id.tev_title_right:
                    saveCitizenInfo();
                    break;
                case R.id.tev_sex:
                    if (alertView_sex == null) {
                        alertView_sex = getShowAlert("选择性别", arr_sex, tev_sex);
                    }
                    alertView_sex.show();
                    break;
                case R.id.tev_nation:
                    if (alertView_nation == null) {
                        alertView_nation = getShowAlert("选择民族", arr_nation, tev_nation);
                    }

                    alertView_nation.show();
                    break;
                case R.id.tev_Name_search:
                    doSearch();
                    break;

            }
        }
    };


    private void doSearch() {
        String CitName = getText(edt_Name);
        if (isEmpty(CitName)) {
            warningShow("查询被检查人不能为空");
            return;
        }
        OkHttpUtils.post().url(HttpApi.URL_CITIZENLIST).addParams("CitName", CitName)
                .addParams("Count", "20").build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    final List<CitizenBean> list = bean.getResultBeanList(CitizenBean.class);
                    if (list.size() == 0) {
                        warningShow("查询结果为空");
                    } else {
                        objList.clear();
                        objList.addAll(list);
                        new ListDialog(aty, objList, new ListItemClickLisener() {
                            @Override
                            public void onItemClick(int P, Object obj) {
                                citizenBean=(CitizenBean) obj;
                                fillData();
                            }
                        }).setTitle("请选择自然人").show();
                    }
                } else {
                    citizenBean = null;
                    warningShow(bean.ErrorMsg);
                    fillData();
                }

            }

        });


    }


    private void saveCitizenInfo() {
        String name = getText(edt_Name);
        String iDCard = getText(edt_card);
        String mobile = getText(edt_phone);
        String sex = getText(tev_sex);
        String nation = getText(tev_nation);
        String address = getText(edt_address);
        String postCode = getText(edt_postcode);
        if (isEmpty(name)) {
            warningShow("被检查人姓名不能为空");
        } else if (!RegexUtil.isIDCard(iDCard)) {
            warningShow("身份证号为空或格式错误");
        } else if (isEmpty(mobile)) {
            warningShow("联系电话不能为空");
        } else if (isEmpty(sex)) {
            warningShow("性别不能为空");
        } else if (isEmpty(nation)) {
            warningShow("名族不能为空");
        } else if (isEmpty(address)) {
            warningShow("住址不能为空");
        } else if (isEmpty(postCode)) {
            warningShow("邮编不能为空");

        } else {
            if (citizenBean == null) {
                citizenBean = new CitizenBean();
            }
            citizenBean.NameCit = name;
            citizenBean.IdentityCardCit = iDCard;
            citizenBean.MobileCit = mobile;
            citizenBean.SexCit = sex;
            citizenBean.NationCit = nation;
            citizenBean.AddressCit = address;
            citizenBean.PostcodeCit = postCode;
            String CitizenPostData = JSON.toJSONString(citizenBean);
            OkHttpUtils.post().url(HttpApi.URL_CITIZENLIST).addParams("CitizenPostData", CitizenPostData)
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

    private void saveInfoAndFinish() {
        Intent it = new Intent();
        it.putExtra("CitizenBean", citizenBean);
        setResult(RESULT_OK, it);
        finish();

    }

}




















