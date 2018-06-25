package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import java.util.ArrayList;
import java.util.List;

public class Doorplate extends AppCompatActivity implements View.OnClickListener{
    TextView tv_title_name,tv_zhangsan;
    ImageView iv_title_back;
    Intent intent=new Intent();
    Spinner sp_town;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doorplate);
        initRes();
    }
    public List<String> getDataSource(){
        List<String> list=new ArrayList<String>();
        list.add("百联村");
        list.add("超同村");
        list.add("得胜村");
        list.add("五丰村");
        list.add("胜利村");
        list.add("桃北村");
        list.add("横港村");
        list.add("新升村");
        list.add("农丰村");
        list.add("逍恬村");
        list.add("五联村");
        list.add("外浜");
        list.add("花店浜");
        list.add("俞陆门");
        list.add("百步村");
        list.add("顾家浜");
        list.add("董家门");
        list.add("朱家栅");
        list.add("金范门");
        list.add("麻夏埭");
        list.add("新升村");
        return list;
    }
    private void initRes() {
        iv_title_back=(ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_name=(TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("搜索地址");
        tv_zhangsan=(TextView) findViewById(R.id.tv_zhangsan);
        tv_zhangsan.setOnClickListener(this);
        sp_town=(Spinner) findViewById(R.id.sp_town);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                getDataSource());
        sp_town.setAdapter(adapter);

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

