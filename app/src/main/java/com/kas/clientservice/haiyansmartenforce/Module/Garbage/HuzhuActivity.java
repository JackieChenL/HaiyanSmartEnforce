package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

public class HuzhuActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv_title_name,tv_zhangsan;
    ImageView iv_title_back;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huzhu);
        initRes();
    }

    private void initRes() {
        iv_title_back=(ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_name=(TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("搜索户主");
        tv_zhangsan=(TextView) findViewById(R.id.tv_zhangsan);
        tv_zhangsan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_zhangsan:
                intent.setClass(this,RankActivity.class);
                startActivity(intent);
                //finish();
                break;
            default:
                break;
        }
    }
}

