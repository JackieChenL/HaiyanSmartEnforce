package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.HTTP_HOST;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.TcListBeanResult;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.DateUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.PrintUtil;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

/**
 * 离开详情页面
 */
public class HistoryActivity extends BaseActivity implements View.OnClickListener{
   private TextView tev_cphm,tev_trsj,tev_pwbh,tev_lksj,tev_tcfy;
    private ImageView iv_heaer_back;
    private TextView  tv_header_title;


    private TcListBeanResult bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        bean= (TcListBeanResult) getIntent().getSerializableExtra("TcListBeanResult");
        tev_cphm= (TextView) findViewById(R.id.tev_cphm);
        tev_trsj= (TextView) findViewById(R.id.tev_trsj);
        tev_pwbh= (TextView) findViewById(R.id.tev_pwbh);
        tev_lksj= (TextView) findViewById(R.id.tev_lksj);
        tev_tcfy= (TextView) findViewById(R.id.tev_tcfy);
        iv_heaer_back = (ImageView) findViewById(R.id.iv_heaer_back);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText("详情");
        iv_heaer_back.setOnClickListener(this);
        tev_cphm.setText(bean.carnum);
        tev_pwbh.setText(bean.Berthname);
        tev_trsj.setText(bean.starttime);
        tev_lksj.setText(bean.stoptime);
        tev_tcfy.setText(bean.money);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_heaer_back:
                finish();
                break;

        }
    }
}
