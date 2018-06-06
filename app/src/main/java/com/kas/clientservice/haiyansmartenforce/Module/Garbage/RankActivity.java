package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kas.clientservice.haiyansmartenforce.R;

public class RankActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView iv_title_back;
    TextView tv_title_name;
    Button bt_hao, bt_yiban, bt_buhao;
    //    TextView tv_house, person_address;
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        initRes();
    }

    private void initRes() {
        bt_hao = (Button) findViewById(R.id.bt_hao);
        bt_hao.setOnClickListener(this);
        bt_yiban = (Button) findViewById(R.id.bt_yiban);
        bt_yiban.setOnClickListener(this);
        bt_buhao = (Button) findViewById(R.id.bt_buhao);
        bt_buhao.setOnClickListener(this);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("户主分类情况");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_hao:
                Toast.makeText(this, "您已成功进行好评", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.bt_yiban:
                intent.setClass(this, PhotoActivity.class);
                startActivity(intent);
                Toast.makeText(this, "您已成功进行中等评价", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.bt_buhao:
                intent.setClass(this, PhotoActivity.class);
                startActivity(intent);
                Toast.makeText(this, "您已成功进行差评", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }
}
