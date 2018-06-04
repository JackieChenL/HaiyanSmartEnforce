package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class HouseHolder extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_title_back;
    TextView tv_title_name;
    Button bt_good, bt_commonly, bt_bad;
    TextView tv_house, person_address;
    Intent intent = new Intent();
    HouseHolderEntity houseHolderEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_holder);
        Log.i("tag", "onCreate: ");
        String response = getIntent().getStringExtra("houseHolderEntity");
        Log.i("tag", "onCreate: " + response);
        houseHolderEntity = new Gson().fromJson(response, HouseHolderEntity.class);
        initRes();


    }

    private void initRes() {
        bt_good = (Button) findViewById(R.id.bt_good);
        bt_good.setOnClickListener(this);
        bt_commonly = (Button) findViewById(R.id.bt_commonly);
        bt_commonly.setOnClickListener(this);
        bt_bad = (Button) findViewById(R.id.bt_bad);
        bt_bad.setOnClickListener(this);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("户主分类情况");
        person_address = (TextView) findViewById(R.id.tv_address);
        person_address.setText("地址：" + houseHolderEntity.getAddress());
        tv_house = (TextView) findViewById(R.id.tv_house);
        tv_house.setText("户主：" + houseHolderEntity.getUserName());
    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        finish();
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_good:
//                intent.setClass(this, MainActivity.class);
//                startActivity(intent);
                //uploadview("好评");
                commit("1");
                Toast.makeText(this, "您已成功进行好评", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.bt_commonly:
                commit("2");
//                intent.setClass(this, PhotoActivity.class);
//                startActivity(intent);
                Toast.makeText(this, "您已成功进行中等评价", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.bt_bad:
                commit("3");
//                intent.setClass(this, PhotoActivity.class);
//                startActivity(intent);
                Toast.makeText(this, "您已成功进行差评", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }

    public void commit(String score) {
        OkHttpUtils.post().url("http://111.1.31.184:86/monitor/api/values/UpdateTrashRank")
                .addParams("SerialNumber", houseHolderEntity.getSerialNumber())
                .addParams("Rank", score)
                .addParams("UserId", "1").build().
                execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("tag",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("tag", response);
                    }
                });


//                        Intent intent = new Intent(CodeActivity.this, Householder.class);
//                        // intent.putExtra("SerialNumber",address+"-"+userName);
//                        intent.putExtra("houseHolderEntity",response);
//                        startActivity(intent);


    }

}
