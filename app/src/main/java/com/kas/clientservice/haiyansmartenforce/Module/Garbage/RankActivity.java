package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import butterknife.BindView;
import okhttp3.Call;

public class RankActivity extends BaseActivity implements View.OnClickListener{
    ImageView iv_title_back;
    TextView tv_title_name;
    TextView bt_hao, bt_yiban, bt_buhao;
    TextView tev_house, tev_address;
    Intent intent = new Intent();
    String UserName;
    HuZhuBean bean;
    String SerialNumber;
    String Address;
    String result;
    @BindView(R.id.edt_pj)
    EditText edt_pj;
    int ID;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }


    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        bt_hao = (TextView) findViewById(R.id.bt_hao);
        bt_hao.setOnClickListener(this);
        bt_yiban = (TextView) findViewById(R.id.bt_yiban);
        bt_yiban.setOnClickListener(this);
        bt_buhao = (TextView) findViewById(R.id.bt_buhao);
        bt_buhao.setOnClickListener(this);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tev_house=(TextView) findViewById(R.id.tev_house);
        tev_address=(TextView) findViewById(R.id.tev_address);
        tv_title_name.setText("户主分类情况");
        UserName=getIntent().getStringExtra("UserName");
        SerialNumber=getIntent().getStringExtra("SerialNumber");
        Address=getIntent().getStringExtra("Address");
        result=getIntent().getStringExtra("result");
        ID=getIntent().getIntExtra("ID",0);

        if(ID==1){
            HuZhuName();

        }else if(ID==2){
            HuZhuAddress();
        }else if(ID==3){
            HuZhuQR();
        }
//        Data();

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_hao:
                commit("1");

                finish();
                break;
            case R.id.bt_yiban:
//                commit("2");
                intent.setClass(this, PhotoActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_buhao:
                commit("3");
                intent.setClass(this, PhotoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    //TODO 通过姓名
    private void HuZhuName() {
        OkHttpUtils.post().url("http://117.149.146.131:6111/monitor/api/values/gettrashinfo")
                .addParams("UserName",UserName).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("tag",e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson=new Gson();
                HuZhuBean bean=gson.fromJson(response,HuZhuBean.class);
                tev_house.setText("户主:"+bean.Name);
                tev_address.setText("地址:"+bean.Address);

            }
        });
    }
    //TODO 通过地址
    private void HuZhuAddress() {
        OkHttpUtils.post().url("http://117.149.146.131:6111/monitor/api/values/gettrashinfo")
                .addParams("Address",Address)
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson=new Gson();
                HuZhuBean bean=gson.fromJson(response,HuZhuBean.class);
                tev_house.setText("户主:"+bean.Name);
                tev_address.setText("地址:"+bean.Address);
            }
        });
    }
    //TODO 通过二维码
    private void HuZhuQR() {
        OkHttpUtils.get().url(result)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("QR","");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("QR",response);
                Gson gson=new Gson();
                HuZhuBean bean=gson.fromJson(response,HuZhuBean.class);
                tev_house.setText("户主:"+bean.Name);
                tev_address.setText("地址:"+bean.Address);
                SerialNumber=bean.SerialNumber;
            }
        });
    }
    protected  MyApplication app;
    public void commit(String score) {
        OkHttpUtils.post().url("http://117.149.146.131:6111/monitor/api/values/UpdateTrashRank")
                .addParams("SerialNumber", SerialNumber)
                .addParams("Rank", score)
                .addParams("Remark",edt_pj.getText().toString())
                .addParams("CollectorId",app.userID ).build().//TODO 垃圾分类评价UserID
                execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("tag",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("tag", response);
                        if (response.contains("success")){
                            finish();
                            Toast.makeText(RankActivity.this,"评价成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RankActivity.this,"评价失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });




    }
}
