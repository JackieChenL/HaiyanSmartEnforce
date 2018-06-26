package smartenforce.aty.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.NetResultBean;
import smartenforce.bean.tcsf.TcListBeanResult;

import com.zhy.http.okhttp.OkHttpUtils;

import smartenforce.base.HttpApi;
import smartenforce.base.ShowTitleActivity;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.zxing.ScanActivity;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * 离开详情页面
 */
public class HistoryActivity extends ShowTitleActivity {
    private TextView tev_cphm, tev_trsj, tev_pwbh, tev_lksj, tev_tcfy;
    private LinearLayout llt_footer;
    private TextView tev_left, tev_right;
    private TcListBeanResult bean;
    private int rePayMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

    }

    @Override
    protected void findViews() {
        tev_cphm = (TextView) findViewById(R.id.tev_cphm);
        tev_trsj = (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh = (TextView) findViewById(R.id.tev_pwbh);
        tev_lksj = (TextView) findViewById(R.id.tev_lksj);
        tev_tcfy = (TextView) findViewById(R.id.tev_tcfy);


        llt_footer = (LinearLayout) findViewById(R.id.llt_footer);
        tev_left = (TextView) findViewById(R.id.tev_left);
        tev_right = (TextView) findViewById(R.id.tev_right);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("详情");
        bean = (TcListBeanResult) getIntent().getSerializableExtra("TcListBeanResult");
        boolean isRepay = getIntent().getBooleanExtra("ISREPAY", false);
        tev_cphm.setText(bean.carnum);
        tev_pwbh.setText(bean.Berthname);
        tev_trsj.setText(bean.starttime);
        tev_lksj.setText(bean.stoptime);
        tev_tcfy.setText(bean.money);
        if (isRepay) {
            llt_footer.setVisibility(View.VISIBLE);
            tev_left.setText("微信补收");
            tev_right.setText("现金补收");
            rePayMoney = Integer.valueOf(bean.money.replaceAll("元", ""));
            tev_left.setOnClickListener(noFastClickLisener);
            tev_right.setOnClickListener(noFastClickLisener);
        } else {
            llt_footer.setVisibility(View.GONE);
        }

    }



    private NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            switch (v.getId()) {
                case R.id.tev_left:
                    startActivityForResult(new Intent(aty, ScanActivity.class), REQUEST_CODE);
                    break;
                case R.id.tev_right:
                    doSuccessInfo("-1");
                    break;
            }

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (scanResult != null) {
                    String authCode = scanResult.getContents();
                    doSuccessInfo(authCode);
                } else {
                    warningShow("扫描失败，请重新扫描");
                }
            }
        }

    }

    private void doSuccessInfo(String authCode) {
        OkHttpUtils.post().url(HttpApi.URL_WXPAY)
                .addParams("auth_code", authCode)
                .addParams("body", bean.carnum + "停车收费")
                .addParams("fee", rePayMoney*100  + "")
                .addParams("btid", bean.btid + "")
                .build().execute(new BeanCallBack(aty, "收款中") {

            @Override
            public void handleBeanResult(NetResultBean netResultBean) {
                if (netResultBean.State) {
                    show("收款成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    warningShow(netResultBean.ErrorMsg);
                }
            }
        });
    }
}