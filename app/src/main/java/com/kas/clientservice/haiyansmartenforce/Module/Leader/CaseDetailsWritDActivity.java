package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

public class CaseDetailsWritDActivity extends BaseActivity {
    private Context mContext = CaseDetailsWritDActivity.this;
    private String ID;
    private String type;
    private RotateAnimation rotate;
    //告知审批
    ScrollView WritTellExamineSv;
    TextView WritTellExamineCaseId;
    TextView WritTellExamineBriefSituation;
    TextView WritTellExamineFreedomDiscretion;
    TextView WritTellExamineDiscretionClause;
    TextView WritTellExamineForfeit;
    TextView WritTellExamineWarning;
    TextView WritTellExamineIsAccept;
    TextView WritTellExaminePunishContent;
    //告知回执
    ScrollView WritTellBackSv;
    TextView WritTellBackCaseId;
    TextView WritTellBackDeliveryTime;
    TextView WritTellBackDeliveryAddress;
    TextView WritTellBackDeliveryManner;
    TextView WritTellBackRecipients;
    TextView WritTellBackReceiveTime;
    TextView WritTellBackGrvePerson;
    TextView WritTellBackPartyOpinion;
    //处罚审批
    ScrollView WritPunishExamineSv;
    TextView WritPunishExamineCaseSource;
    TextView WritPunishExamineProofInventory;
    //处罚回执
    ScrollView WritPunishBrckSv;
    TextView WritPunishBrckCaseId;
    TextView WritPunishBrckName;
    TextView WritPunishBrckReceiveMan;
    TextView WritPunishBrckDeliveryPerson;
    TextView WritPunishBrckDeliveryTime;
    TextView WritPunishBrckDeliveryAddress;
    TextView WritPunishBrckDeliveryManner;
    TextView WritPunishBrckRecipients;
    TextView WritPunishBrckAddresseeDate;
    TextView WritPunishBrckRemark;
    //结案审批
    ScrollView WritEndExamineSv;
    TextView WritEndExamineCaseID;
    TextView WritEndExamineManageGoBy;
    TextView WritEndExamineAdministrativePunish;
    TextView WritEndExaminePunishExecuteManner;
    //
    LinearLayout mLoadLl;
    ImageView mLoadImg;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_header_title)
    TextView mTvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.mine_details_writ_details;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        inView();
        getDate();
    }

    private void inView() {
        //告知审批
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        WritTellExamineSv = (ScrollView) findViewById(R.id.WritTellExamineSv);
        WritTellExamineCaseId = (TextView) findViewById(R.id.WritTellExamineCaseId);
        WritTellExamineBriefSituation = (TextView) findViewById(R.id.WritTellExamineBriefSituation);
        WritTellExamineFreedomDiscretion = (TextView) findViewById(R.id.WritTellExamineFreedomDiscretion);
        WritTellExamineDiscretionClause = (TextView) findViewById(R.id.WritTellExamineDiscretionClause);
        WritTellExamineForfeit = (TextView) findViewById(R.id.WritTellExamineForfeit);
        WritTellExamineWarning = (TextView) findViewById(R.id.WritTellExamineWarning);
        WritTellExamineIsAccept = (TextView) findViewById(R.id.WritTellExamineIsAccept);
        WritTellExaminePunishContent = (TextView) findViewById(R.id.WritTellExaminePunishContent);
        //告知回执
        WritTellBackSv = (ScrollView) findViewById(R.id.WritTellBackSv);
        WritTellBackCaseId = (TextView) findViewById(R.id.WritTellBackCaseId);
        WritTellBackDeliveryTime = (TextView) findViewById(R.id.WritTellBackDeliveryTime);
        WritTellBackDeliveryAddress = (TextView) findViewById(R.id.WritTellBackDeliveryAddress);
        WritTellBackDeliveryManner = (TextView) findViewById(R.id.WritTellBackDeliveryManner);
        WritTellBackRecipients = (TextView) findViewById(R.id.WritTellBackRecipients);
        WritTellBackReceiveTime = (TextView) findViewById(R.id.WritTellBackReceiveTime);
        WritTellBackGrvePerson = (TextView) findViewById(R.id.WritTellBackGrvePerson);
        WritTellBackPartyOpinion = (TextView) findViewById(R.id.WritTellBackPartyOpinion);
        //处罚审批
        WritPunishExamineSv = (ScrollView) findViewById(R.id.WritPunishExamineSv);
        WritPunishExamineCaseSource = (TextView) findViewById(R.id.WritPunishExamineCaseSource);
        WritPunishExamineProofInventory = (TextView) findViewById(R.id.WritPunishExamineProofInventory);
        //处罚回执
        WritPunishBrckSv = (ScrollView) findViewById(R.id.WritPunishBrckSv);
        WritPunishBrckCaseId = (TextView) findViewById(R.id.WritPunishBrckCaseId);
        WritPunishBrckName = (TextView) findViewById(R.id.WritPunishBrckName);
        WritPunishBrckReceiveMan = (TextView) findViewById(R.id.WritPunishBrckReceiveMan);
        WritPunishBrckDeliveryPerson = (TextView) findViewById(R.id.WritPunishBrckDeliveryPerson);
        WritPunishBrckDeliveryTime = (TextView) findViewById(R.id.WritPunishBrckDeliveryTime);
        WritPunishBrckDeliveryAddress = (TextView) findViewById(R.id.WritPunishBrckDeliveryAddress);
        WritPunishBrckDeliveryManner = (TextView) findViewById(R.id.WritPunishBrckDeliveryManner);
        WritPunishBrckRecipients = (TextView) findViewById(R.id.WritPunishBrckRecipients);
        WritPunishBrckAddresseeDate = (TextView) findViewById(R.id.WritPunishBrckAddresseeDate);
        WritPunishBrckRemark = (TextView) findViewById(R.id.WritPunishBrckRemark);
        //处罚回执
        WritEndExamineSv = (ScrollView) findViewById(R.id.WritEndExamineSv);
        WritEndExamineCaseID = (TextView) findViewById(R.id.WritEndExamineCaseID);
        WritEndExamineManageGoBy = (TextView) findViewById(R.id.WritEndExamineManageGoBy);
        WritEndExamineAdministrativePunish = (TextView) findViewById(R.id.WritEndExamineAdministrativePunish);
        WritEndExaminePunishExecuteManner = (TextView) findViewById(R.id.WritEndExaminePunishExecuteManner);


    }

    private void getDate() {
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");
        type = intent.getStringExtra("Type");


        OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "Mobile/GetCasePartTableDetail.ashx")
                .addParams("ID", ID + "")
                .addParams("Type", type)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
                showNetErrorToast();
            }

            @Override
            public void onResponse(String r, int id) {
                Log.i(TAG, "onResponse: " + r);
                JsonObject response = null;
                try {
                    response = new JsonParser().parse(r).getAsJsonObject();;


                    Log.i(TAG, "onResponse: "+gson.toJson(response));
                    if (type.equals("告知审批")) {
                        writTellExamine(response);
                        mTvTitle.setText("告知审批");

                    } else if (type.equals("告知回执")) {
                        writTellBack(response);
                        mTvTitle.setText("告知回执");

                    } else if (type.equals("处罚审批")) {
                        writPunishExamine(response);
                        mTvTitle.setText("处罚审批");

                    } else if (type.equals("处罚回执")) {
                        writPunishBack(response);
                        mTvTitle.setText("处罚回执");

                    } else if (type.equals("结案审批")) {
                        writEndExamine(response);
                        mTvTitle.setText("结案审批");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "onResponse: "+e.toString());
                }
            }
        });
    }



    /**
     * 告知审批
     *
     * @param object
     */
    private void writTellExamine(JsonObject object) {
        try {
            //告知审批
            loadText(WritTellExamineCaseId, object.get("CaseIDPuA").getAsString());
            loadText(WritTellExamineBriefSituation, object.get("ReasonBasisPuA").getAsString());
            loadText(WritTellExamineFreedomDiscretion, object.get("PunishPuA").getAsString());
            loadText(WritTellExamineDiscretionClause, object.get("ItemPuA").getAsString());
            loadText(WritTellExamineForfeit, object.get("FinePuA").getAsString());
            loadText(WritTellExaminePunishContent, object.get("OtherPuA").getAsString());
            if (object.get("IsWarnPuA").getAsString().equals("1")) {
                WritTellExamineWarning.setText("是");
            } else {
                WritTellExamineWarning.setText("否");
            }
            if (object.get("IsWarnPuA").equals("1")) {
                WritTellExamineIsAccept.setText("是");
            } else {
                WritTellExamineIsAccept.setText("否");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Exception", e.toString());
        }
        WritTellExamineSv.setVisibility(View.VISIBLE);
    }

    /**
     * 告知回执
     *
     * @param object
     */
    private void writTellBack(JsonObject object) {
        try {
            loadText(WritTellBackCaseId, object.get("CaseIDFee").getAsString());
            loadText(WritTellBackDeliveryTime, object.get("ServiceofDateFee").getAsString());
            loadText(WritTellBackDeliveryAddress, object.get("ServiceofAddressFee").getAsString());
            loadText(WritTellBackDeliveryManner, object.get("ServiceofWayFee").getAsString());
            loadText(WritTellBackRecipients, object.get("RecipientFee").getAsString());
            loadText(WritTellBackReceiveTime, object.get("RecipientDateFee").getAsString());
            loadText(WritTellBackGrvePerson, object.get("ServiceofManFee").getAsString());
            loadText(WritTellBackPartyOpinion, object.get("RemarkFee").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.toString());
        }
        WritTellBackSv.setVisibility(View.VISIBLE);
    }

    /**
     * 处罚审批
     *
     * @param object
     */
    private void writPunishExamine(JsonObject object) {
        try {
            loadText(WritPunishExamineCaseSource, object.get("ContentOriginalPen").getAsString());
            loadText(WritPunishExamineProofInventory, object.get("EvidenceOriginalPen").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.toString());
        }
        WritPunishExamineSv.setVisibility(View.VISIBLE);
    }

    /**
     * 处罚回执
     *
     * @param object
     */
    private void writPunishBack(JsonObject object) {
        try {
            loadText(WritPunishBrckCaseId, object.get("CaseIDPro").getAsString());
            loadText(WritPunishBrckName, object.get("NamePro").getAsString());
            loadText(WritPunishBrckReceiveMan, object.get("EntOrCitNamePro").getAsString());
            loadText(WritPunishBrckDeliveryPerson, object.get("ServiceofManPro").getAsString());
            loadText(WritPunishBrckDeliveryTime, object.get("ServiceofDatePro").getAsString());
            loadText(WritPunishBrckDeliveryAddress, object.get("ServiceofAddressPro").getAsString());
            loadText(WritPunishBrckDeliveryManner, object.get("ServiceofWayPro").getAsString());
            loadText(WritPunishBrckRecipients, object.get("RecipientPro").getAsString());
            loadText(WritPunishBrckAddresseeDate, object.get("RecipientDatePro").getAsString());
            loadText(WritPunishBrckRemark, object.get("RemarkPro").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.toString());
        }
        WritPunishBrckSv.setVisibility(View.VISIBLE);
    }

    /**
     * 结案审批
     */
    private void writEndExamine(JsonObject object) {
        try {
            Log.i(TAG, "writEndExamine: "+object.get("CaseIDClo").getAsString());
            loadText(WritEndExamineCaseID, object.get("CaseIDClo").getAsString());
            loadText(WritEndExamineManageGoBy, object.get("DescribeClo").getAsString());
            loadText(WritEndExamineAdministrativePunish, object.get("PunishClo").getAsString());
            loadText(WritEndExaminePunishExecuteManner, object.get("DisposalClo").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.toString());
        }
        WritEndExamineSv.setVisibility(View.VISIBLE);
    }

    /**
     * 加载TextView数据
     *
     * @param tv
     * @param val
     */
    private void loadText(TextView tv, String val) {
        Log.i(TAG, "loadText: "+val);
        if (val.equals("") || val.equals("null")) {
            tv.setText("没有数据哦！");
        } else {
            tv.setText(val+"");
        }
    }
}