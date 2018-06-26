package smartenforce.aty.function5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.EnforceListBean;
import smartenforce.bean.NextInfoBean;
import smartenforce.bean.ReleaseEnforceBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.util.DateUtil;


public class ReleaseEnforceActivity extends ShowTitleActivity {
    private EnforceListBean enforceListBean;
    private ReleaseEnforceBean releaseEnforceBean;
    private TextView tev_sqlx, tev_release_time,tev_referNumber_title;
    private EditText edt_referNumber, edt_sqyj, edt_describe,edt_suggest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_enforce);
    }

    @Override
    protected void findViews() {
        tev_sqlx = (TextView) findViewById(R.id.tev_sqlx);
        tev_release_time = (TextView) findViewById(R.id.tev_release_time);
        edt_sqyj = (EditText) findViewById(R.id.edt_sqyj);
        edt_describe = (EditText) findViewById(R.id.edt_describe);
        edt_referNumber = (EditText) findViewById(R.id.edt_referNumber);
        edt_suggest = (EditText) findViewById(R.id.edt_suggest);
        tev_referNumber_title = (TextView) findViewById(R.id.tev_referNumber_title);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("解除扣押申请");
        enforceListBean= (EnforceListBean) getIntent().getSerializableExtra("EnforceListBean");
        tev_sqlx.setText("解除扣押");
        tev_referNumber_title.setText("海综执解查扣字["+ Calendar.getInstance().get(Calendar.YEAR)+"]第");

        tev_release_time.setText(DateUtil.getFormatDate(new Date(),DateUtil.YMD));
        tev_title_right.setText("提交");
        tev_title_right.setOnClickListener(new NoFastClickLisener() {
            @Override
            public void onNofastClickListener(View v) {
                closeKeybord();
                docheck();
            }
        });
    }

   //  TODO:校验?文书格式
    private void docheck() {
        if (isEmpty(edt_sqyj)){
            warningShow("申请依据不能为空");
        }else if (isEmpty(edt_referNumber)){
            warningShow("文书编号不能为空");
        }else{
            getNextRequestID();
        }
    }



    private void getNextRequestID() {
        OkHttpUtils.post().url(HttpApi.URL_APPROVE_EXTEND)
                .addParams("ExtendType", "NextInAFlowWhenAdd")
                .addParams("UserID", app.userID)
                .addParams("DepartmentID", app.DepartmentID)
                .build().execute(new BeanCallBack(aty, "提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total > 0) {
                    try {
                        String json = bean.getResultBeanList(String.class).get(0);
                        JSONObject jo = new JSONObject(json);
                        String nextRequestID = jo.getString("id");
                        getNextInfo(nextRequestID);
                    } catch (JSONException e) {
                        warningShow("获取下步环节信息错误");
                    }
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });

    }

    //获取新增时下步审核人
    private void getNextInfo(String nextInAFlowID) {
        OkHttpUtils.post().url(HttpApi.URL_APPROVE_EXTEND)
                .addParams("ExtendType", "NextInAAuditorWhenAdd")
                .addParams("nextInAFlowID", nextInAFlowID)
                .addParams("UserID", app.userID)
                .addParams("DepartmentID", app.DepartmentID)
                .build().execute(new BeanCallBack(aty, "提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total > 0) {
                    NextInfoBean nextInfoBean = bean.getResultBeanList(NextInfoBean.class).get(0);
                    releaseEnforceBean =new ReleaseEnforceBean();
                    releaseEnforceBean.InATypeIDInA=4;
                    releaseEnforceBean.ReferenceNumberInA=getText(tev_referNumber_title)+getText(edt_referNumber)+"号";
                    releaseEnforceBean.RemoveTimeSeR=getText(tev_release_time);
                    releaseEnforceBean.ReasonBasisInA=getText(edt_describe);
                    releaseEnforceBean.ApplicationInA=getText(edt_sqyj);
                    releaseEnforceBean.SuggestCas=getText(edt_suggest);
                    releaseEnforceBean.UserID=app.userID;
                    releaseEnforceBean.InAID=enforceListBean.InternalApproveID;
                    releaseEnforceBean.SeizureID=enforceListBean.SeizureID;
                    releaseEnforceBean.CaseID=enforceListBean.CaseIDInA;
                    releaseEnforceBean.SourceID=enforceListBean.SourceIDInA;
                    releaseEnforceBean.NextInAFlow = nextInfoBean.InAFlowID;
                    releaseEnforceBean.NextInAAuditor = nextInfoBean.UserID;
                    doReleaseEnforce();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });


    }

    private void doReleaseEnforce() {
        String InAPostData= JSON.toJSONString(releaseEnforceBean);
        OkHttpUtils.post().url(HttpApi.URL_ADDAPPROVE)
                .addParams("InAPostData", InAPostData)
                .build().execute(new BeanCallBack(aty, "提交中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    show("提交成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });

    }


}




















