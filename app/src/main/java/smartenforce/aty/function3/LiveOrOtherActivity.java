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

//传一个type,判断是见证人还是现场负责人
public class LiveOrOtherActivity extends ShowTitleActivity {
    private EditText edt_name, edt_sfz, edt_address, edt_phone, edt_email;
    private TextView tev_sex, tev_mz, tev_search;
    private AlertView alertView_sex, alertView_nation;

    private String[] arr_sex, arr_nation;
    private int TYPE;

    private CitizenBean citizenBean;

    private List<Object> objList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_other);
    }

    @Override
    protected void findViews() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_sfz = (EditText) findViewById(R.id.edt_sfz);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_email = (EditText) findViewById(R.id.edt_email);
        tev_sex = (TextView) findViewById(R.id.tev_sex);
        tev_mz = (TextView) findViewById(R.id.tev_mz);
        tev_search = (TextView) findViewById(R.id.tev_search);
    }

    @Override
    protected void initDataAndAction() {
        TYPE = getIntent().getIntExtra("TYPE", -1);
        if (TYPE == REQUESTCODE.LIVE_MAN) {
            tev_title.setText("现场负责人");

        } else if (TYPE == REQUESTCODE.OTHER_MAN) {
            tev_title.setText("其他见证人");
        }
        tev_title_right.setText("确定");
        arr_sex = getResources().getStringArray(R.array.sex);
        arr_nation = getResources().getStringArray(R.array.nations);
         citizenBean= (CitizenBean) getIntent().getSerializableExtra("CitizenBean");
        tev_mz.setText("汉族");
        fillData();
        tev_sex.setOnClickListener(noFastClickLisener);
        tev_mz.setOnClickListener(noFastClickLisener);
        tev_search.setOnClickListener(noFastClickLisener);
        tev_title_right.setOnClickListener(noFastClickLisener);
    }

    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_title_right:
                    doSave();
                    break;
                case R.id.tev_sex:
                    if (alertView_sex==null){
                        alertView_sex=getShowAlert("选择性别",arr_sex,tev_sex);
                    }
                    alertView_sex.show();
                    break;
                case R.id.tev_mz:
                    if (alertView_nation==null){
                        alertView_nation=getShowAlert("选择民族",arr_nation,tev_mz);
                    }
                    alertView_nation.show();
                    break;
                case R.id.tev_search:
                    doSearch();
                    break;

            }
        }
    };


    //查询
    private void doSearch() {
        String CitName = getText(edt_name);
        if (isEmpty(CitName)) {
            warningShow("查询姓名不能为空");
            return;
        }
        OkHttpUtils.post().url(HttpApi.URL_CITIZENLIST).addParams("CitName", CitName)
                .addParams("Count", "20").build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    final List<CitizenBean> list = bean.getResultBeanList(CitizenBean.class);
                    if (list == null || list.size() == 0) {
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
    private void fillData() {
        if (citizenBean != null) {
            edt_name.setText(citizenBean.NameCit);
            tev_sex.setText(citizenBean.SexCit);
            tev_mz.setText(citizenBean.NationCit);
            edt_sfz.setText(citizenBean.IdentityCardCit);
            edt_phone.setText(citizenBean.MobileCit);
            edt_address.setText(citizenBean.AddressCit);
            edt_email.setText(citizenBean.PostcodeCit);
        } else {
            edt_name.setText(null);
            tev_sex.setText(null);
            tev_mz.setText("汉族");
            edt_sfz.setText(null);
            edt_phone.setText(null);
            edt_address.setText(null);
            edt_email.setText(null);
        }

    }






    private void doSave() {
        String name = getText(edt_name);
        String iDCard = getText(edt_sfz);
        String mobile = getText(edt_phone);
        String sex = getText(tev_sex);
        String nation = getText(tev_mz);
        String address = getText(edt_address);
        String postCode = getText(edt_email);
        if (isEmpty(name)) {
            warningShow("姓名不能为空");
        } else if (!RegexUtil.isIDCard(iDCard)) {
            warningShow("身份证号为空或格式错误");
        } else if (isEmpty(mobile)) {
            warningShow("联系电话不能为空");
        } else {
            if (citizenBean == null) {
                citizenBean = new CitizenBean();
            }
            citizenBean.NameCit = name;
            citizenBean.IdentityCardCit = iDCard;
            citizenBean.MobileCit = mobile;
            citizenBean.SexCit=sex;
            citizenBean.NationCit=nation;
            citizenBean.AddressCit=address;
            citizenBean.PostcodeCit=postCode;
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

    private void saveInfoAndFinish() {
        Intent it=new Intent();
        it.putExtra("CitizenBean",citizenBean);
        setResult(RESULT_OK,it);
        finish();

    }


}




















