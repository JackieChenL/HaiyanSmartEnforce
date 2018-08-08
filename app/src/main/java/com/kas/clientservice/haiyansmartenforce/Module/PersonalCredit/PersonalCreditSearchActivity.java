package com.kas.clientservice.haiyansmartenforce.Module.PersonalCredit;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Module.CityCheck.CityCheckAddActivity;
import com.kas.clientservice.haiyansmartenforce.Module.HuochaiCredit.HuochaiCreditActivity;
import com.kas.clientservice.haiyansmartenforce.Module.PersonalCreditListEntity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class PersonalCreditSearchActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface {

    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_personalCredit_search_starttime)
    TextView tv_startTime;
    @BindView(R.id.tv_personalCredit_search_endtime)
    TextView tv_endTime;
    @BindView(R.id.tv_personalCredit_add)
    TextView tv_add;
    @BindView(R.id.tv_personalCredit_query)
    TextView tv_query;
    @BindView(R.id.lv_personalCredit_search)
    ListView listView;
    @BindView(R.id.et_personalCredit_search_id)
    EditText et_id;
    @BindView(R.id.tv_personalCreditList_name)
    TextView tv_name;
    @BindView(R.id.tv_personalCreditList_score)
    TextView tv_score;
    @BindView(R.id.tv_personalCredit_addScore)
    TextView tv_addScore;
    @BindView(R.id.tv_personalCredit_cutScore)
    TextView tv_cutScore;
    @BindView(R.id.tv_personalCreditList_phone)
    TextView tv_phone;
    @BindView(R.id.tv_personalCreditList_scoreBase)
    TextView tv_baseScore;
    @BindView(R.id.tv_personalCreditList_scoreAdd)
    TextView tv_addTotal;
    @BindView(R.id.tv_personalCreditList_scoreCut)
    TextView tv_cutTotal;
    @BindView(R.id.tv_personalCreditList_area)
    TextView tv_area;
    @BindView(R.id.tv_personalCreditList_law)
    TextView tv_law;
    @BindView(R.id.tv_personalCreditList_address)
    TextView tv_address;
    @BindView(R.id.tv_personalCreditList_id)
    TextView tv_id;

    TimePickerDialog timePickerDialog;
    private String startTime = "";
    private String endTime = "";
    PersonalCreditAdapter adapter;
    List<PersonalCreditListEntity.GetDetailBean> list = new ArrayList<>();
    BaseEntity<PersonalCreditListEntity> entity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_credit_search;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("个人征信");
        iv_back.setOnClickListener(this);
        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_query.setOnClickListener(this);
        tv_addScore.setOnClickListener(this);
        tv_cutScore.setOnClickListener(this);

        timePickerDialog = new TimePickerDialog(mContext);
        adapter = new PersonalCreditAdapter(mContext, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, CityCheckAddActivity.class);
                intent.putExtra("json", gson.toJson(list.get(i)));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_citySearch_list_starttime:
                choseStartTime();
                break;
            case R.id.tv_citySearch_list_endtime:
                choseEndTime();
                break;
            case R.id.tv_personalCredit_query:
                if (et_id.getText().toString().trim().equals("")) {
                    showToast("请输入身份证号");
                    return;
                }
                query();
                break;
            case R.id.tv_personalCredit_add:
                startActivity(new Intent(mContext, HuochaiCreditActivity.class));
                break;
            case R.id.tv_personalCredit_addScore:
                ChoseAdd();
                break;
            case R.id.tv_personalCredit_cutScore:
                choseCut();
                break;
        }
    }

    public void ChoseAdd() {
        tv_addScore.setTextColor(getResources().getColor(R.color.grey_200));
        tv_addScore.setBackground(new ColorDrawable(getResources().getColor(R.color.app_original_blue)));

        tv_cutScore.setTextColor(getResources().getColor(R.color.grey_800));
        tv_cutScore.setBackground(new ColorDrawable(getResources().getColor(R.color.grey_200)));

        if (entity != null) {
            list.clear();
            list.addAll(entity.getRtn().getGetDetail());
            adapter.notifyDataSetChanged();
            ListViewFitParent.setListViewHeightBasedOnChildren(listView);
        }
    }

    public void choseCut() {
        tv_cutScore.setTextColor(getResources().getColor(R.color.grey_200));
        tv_cutScore.setBackground(new ColorDrawable(getResources().getColor(R.color.app_original_blue)));

        tv_addScore.setTextColor(getResources().getColor(R.color.grey_800));
        tv_addScore.setBackground(new ColorDrawable(getResources().getColor(R.color.grey_200)));

        if (entity != null) {
            list.clear();
            list.addAll(entity.getRtn().getLostDetail());
            adapter.notifyDataSetChanged();
            ListViewFitParent.setListViewHeightBasedOnChildren(listView);
        }
    }

    private void query() {
        list.clear();
        showLoadingDialog();
        OkHttpUtils.get().url(RequestUrl.baseUrl_credit + "api/search/SearchRecordsFromApp")
                .addParams("identity", et_id.getText().toString().trim())
//                .addParams("StartTime",startTime)
//                .addParams("EndTime",endTime)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
                dismissLoadingDialog();
                showNetErrorToast();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissLoadingDialog();
                Log.i(TAG, "onResponse: " + response);
                entity = gson.fromJson(response, new TypeToken<BaseEntity<PersonalCreditListEntity>>() {
                }.getType());
                if (entity.isState()) {
                    PersonalCreditListEntity personalCreditListEntity = entity.getRtn();
                    tv_name.setText(entity.getRtn().getName());
                    tv_score.setText((entity.getRtn().getBase() + entity.getRtn().getGet() - entity.getRtn().getLost()) + "");
                    tv_phone.setText(personalCreditListEntity.getTel());
                    tv_baseScore.setText(personalCreditListEntity.getBase()+"");
                    tv_cutTotal.setText("-"+personalCreditListEntity.getLost());
                    tv_addTotal.setText(personalCreditListEntity.getGet()+"");
                    tv_area.setText(personalCreditListEntity.getAreaName());
                    tv_law.setText(personalCreditListEntity.getSchemeName());
                    tv_address.setText(personalCreditListEntity.getAddress());
                    tv_id.setText(personalCreditListEntity.getIdentity());
                    ChoseAdd();
                } else {
                    ToastUtils.showToast(mContext, entity.getErrorMsg());
                }
            }
        });
    }

    int flag = 0;

    private void choseEndTime() {
        flag = 2;
        choseTime();
    }

    private void choseStartTime() {
        flag = 1;
        choseTime();
    }

    private void choseTime() {
        timePickerDialog.showPPw(listView);
    }


    @Override
    public void positiveListener() {
        String year = timePickerDialog.getYear() + "";
        String months = timePickerDialog.getMonth() + "";
        String day = timePickerDialog.getDay() + "";
        String hour = timePickerDialog.getHour() + "";
        String minutes = timePickerDialog.getMinute() + "";
        if (flag == 1) {
            tv_startTime.setText(year + "-" + months + "-" + day + " " + hour + ":" + minutes);
            startTime = tv_startTime.getText().toString();
        }
        if (flag == 2) {
            tv_endTime.setText(year + "-" + months + "-" + day + " " + hour + ":" + minutes);
            endTime = tv_endTime.getText().toString();
        }
    }

    @Override
    public void negativeListener() {

    }
}
