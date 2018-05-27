package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;

public class CenterActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout llt_park, llt_exit, llt_query, llt_bj, llt_setting;
    private ImageView iv_heaer_back;
    private TextView tv_header_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcsf_center);
        llt_park = (LinearLayout) findViewById(R.id.llt_park);
        llt_exit = (LinearLayout) findViewById(R.id.llt_exit);
        llt_query = (LinearLayout) findViewById(R.id.llt_query);
        llt_bj = (LinearLayout) findViewById(R.id.llt_bj);
        llt_setting = (LinearLayout) findViewById(R.id.llt_setting);
        iv_heaer_back = (ImageView) findViewById(R.id.iv_heaer_back);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        initAction();

    }

    private void initAction() {
        tv_header_title.setText("停车收费");
        llt_park.setOnClickListener(this);
        llt_exit.setOnClickListener(this);
        llt_query.setOnClickListener(this);
        llt_bj.setOnClickListener(this);
        llt_setting.setOnClickListener(this);
        iv_heaer_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.llt_park:
                startActivity(new Intent(aty, ParkActivity.class));
                break;


            case R.id.llt_exit:
                startActivity(new Intent(aty, ExitListActivity.class));
                break;

            case R.id.llt_query:
                startActivity(new Intent(aty, QueryActivity.class));
                break;
            case R.id.llt_bj:
                show("补缴功能开发中");
                break;
            case R.id.llt_setting:
                show("设置功能开发中");
                break;


            default:
                ;


        }

    }


}
