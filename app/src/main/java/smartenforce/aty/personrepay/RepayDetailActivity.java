package smartenforce.aty.personrepay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.tcsf.ArrearsBean;
import smartenforce.impl.NoFastClickLisener;

/**
 * 离开详情页面
 */
public class RepayDetailActivity extends ShowTitleActivity {
    private TextView tev_cphm, tev_trsj, tev_pwbh, tev_lksj, tev_tcfy;
    private ArrearsBean arrearsBean;
    private TextView tev_sfy;
    private TextView tev_wx_pay;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_detail);

    }

    @Override
    protected void findViews() {
        tev_cphm = (TextView) findViewById(R.id.tev_cphm);
        tev_trsj = (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh = (TextView) findViewById(R.id.tev_pwbh);
        tev_lksj = (TextView) findViewById(R.id.tev_lksj);
        tev_tcfy = (TextView) findViewById(R.id.tev_tcfy);
        tev_sfy = (TextView) findViewById(R.id.tev_sfy);
        tev_wx_pay = (TextView) findViewById(R.id.tev_wx_pay);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("详情");
        arrearsBean = (ArrearsBean) getIntent().getSerializableExtra("ArrearsBean");
        tev_cphm.setText(arrearsBean.UCarnum);
        tev_pwbh.setText(arrearsBean.BerthName);
        tev_trsj.setText(arrearsBean.StartTime);
        tev_lksj.setText(arrearsBean.EndTime);
        tev_sfy.setText(arrearsBean.OperRealname);
        tev_tcfy.setText(Double.valueOf(arrearsBean.cash_fee) / 100+"元");
        tev_wx_pay.setOnClickListener(new NoFastClickLisener() {
            @Override
            public void onNofastClickListener(View v) {
                Intent intent = new Intent(aty, WebViewPayActivity.class);
                intent.putExtra("ArrearsBean", arrearsBean);
                startActivity(intent);
                finish();
            }
        });

    }







}