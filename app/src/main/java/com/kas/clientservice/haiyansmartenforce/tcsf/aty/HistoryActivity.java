package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.TitleActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.TcListBeanResult;

/**
 * 离开详情页面
 */
public class HistoryActivity extends TitleActivity {
    private TextView tev_cphm, tev_trsj, tev_pwbh, tev_lksj, tev_tcfy;


    private TcListBeanResult bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

    }


    @Override
    protected void findViewBy() {


        tev_cphm = (TextView) findViewById(R.id.tev_cphm);
        tev_trsj = (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh = (TextView) findViewById(R.id.tev_pwbh);
        tev_lksj = (TextView) findViewById(R.id.tev_lksj);
        tev_tcfy = (TextView) findViewById(R.id.tev_tcfy);


    }

    @Override
    protected void setTitleTxt() {
        tv_header_title.setText("详情");
    }

    @Override
    protected void initData() {
        bean = (TcListBeanResult) getIntent().getSerializableExtra("TcListBeanResult");
        tev_cphm.setText(bean.carnum);
        tev_pwbh.setText(bean.Berthname);
        tev_trsj.setText(bean.starttime);
        tev_lksj.setText(bean.stoptime);
        tev_tcfy.setText(bean.money);
    }
}
