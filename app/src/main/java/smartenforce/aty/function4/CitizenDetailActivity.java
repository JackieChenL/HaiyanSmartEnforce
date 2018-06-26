package smartenforce.aty.function4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Arrays;

import smartenforce.adapter.ImageAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.CitizenBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.projectutil.UpLoadImageUtil;
import smartenforce.util.RegexUtil;

public class CitizenDetailActivity extends ShowTitleActivity {
    private EditText edt_name, edt_card, edt_mobile, edt_address, edt_postcode;
    private TextView tev_mz, tev_sex;
    private ScrollView scv;
    private RecyclerView rv_photo;
    private ImageAdapter adapter;
    private ArrayList<String> list = new ArrayList<String>();

    private CitizenBean citizenBean;
    private AlertView alertView_sex, alertView_nation;
    private String[] arr_sex, arr_nation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
    }

    @Override
    protected void findViews() {
        rv_photo = (RecyclerView) findViewById(R.id.rv_photo);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_card = (EditText) findViewById(R.id.edt_card);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_address = (EditText) findViewById(R.id.edt_address);
        edt_postcode = (EditText) findViewById(R.id.edt_postcode);
        tev_mz = (TextView) findViewById(R.id.tev_mz);
        tev_sex = (TextView) findViewById(R.id.tev_sex);
        scv = (ScrollView) findViewById(R.id.scv);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("自然人详情");
        tev_title_right.setText("保存");
        arr_sex = getResources().getStringArray(R.array.sex);
        arr_nation = getResources().getStringArray(R.array.nations);
        tev_mz.setText("汉族");
        initPictureAdapter();
        initData();
        tev_title_right.setOnClickListener(noFastClickLisener);
        tev_mz.setOnClickListener(noFastClickLisener);
        tev_sex.setOnClickListener(noFastClickLisener);

    }


    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_mz:
                    if (alertView_nation == null) {
                        alertView_nation = getShowAlert("选择民族", arr_nation, tev_mz);
                    }
                    alertView_nation.show();
                    break;
                case R.id.tev_sex:
                    if (alertView_sex == null) {
                        alertView_sex = getShowAlert("选择性别", arr_sex, tev_sex);
                    }
                    alertView_sex.show();
                    break;
                case R.id.tev_title_right:
                    doCheck();
                    break;
            }
        }
    };


    private void initData() {
        citizenBean = (CitizenBean) getIntent().getSerializableExtra("CitizenBean");
        if (citizenBean != null) {
            edt_name.setText(citizenBean.NameCit);
            edt_card.setText(citizenBean.IdentityCardCit);
            edt_mobile.setText(citizenBean.MobileCit);
            tev_sex.setText(citizenBean.SexCit);
            tev_mz.setText(citizenBean.NationCit);
            edt_address.setText(citizenBean.AddressCit);
            edt_postcode.setText(citizenBean.PostcodeCit);
            if (!isEmpty(citizenBean.PhotoCit)) {
                String[] picArray = citizenBean.PhotoCit.split("\\|");
                list.addAll(Arrays.asList(picArray));
                adapter.notifyDataSetChanged();
            }
        }

    }


    private void initPictureAdapter() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(aty, 4, LinearLayout.VERTICAL, false);
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

    //校验字段
    private void doCheck() {
        String name = getText(edt_name);
        String iDCard = getText(edt_card);
        String mobile = getText(edt_mobile);
        if (isEmpty(name)) {
            warningShow("姓名不能为空");
        } else if (!RegexUtil.isIDCard(iDCard)) {
            warningShow("身份证号为空或格式错误");
        } else if (isEmpty(mobile)) {
            warningShow("联系电话不能为空");
        } else {
            upLoadImg();
        }
    }

    private void upLoadImg() {
        String base64FileImg = adapter.getImageFile();
        if (isEmpty(base64FileImg)) {
            doSaveOrUpdate(adapter.getImageUrlStr());
        } else {
            UpLoadImageUtil.uploadImage(aty, app.userID, "citizen", base64FileImg, new UpLoadImageUtil.onUploadImgCallBack() {
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

    private void doSaveOrUpdate(String picArray) {
        if (citizenBean == null) {
            citizenBean = new CitizenBean();
        }
        String name = getText(edt_name);
        String iDCard = getText(edt_card);
        String mobile = getText(edt_mobile);
        String sex = getText(tev_sex);
        String nation = getText(tev_mz);
        String address = getText(edt_address);
        String postCode = getText(edt_postcode);
        citizenBean.NameCit = name;
        citizenBean.IdentityCardCit = iDCard;
        citizenBean.MobileCit = mobile;
        citizenBean.SexCit = sex;
        citizenBean.NationCit = nation;
        citizenBean.AddressCit = address;
        citizenBean.PostcodeCit = postCode;
        citizenBean.PhotoCit = picArray;
        String CitizenPostData = JSON.toJSONString(citizenBean);
        OkHttpUtils.post().url(HttpApi.URL_CITIZEN_SAVE).addParams("CitizenPostData", CitizenPostData)
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

}




















