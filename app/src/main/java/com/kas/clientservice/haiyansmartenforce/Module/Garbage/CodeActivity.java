package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class CodeActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userNameText;
    Button bt_code;
    String Userid = "123";
    ImageView iv_title_back;
    TextView tv_title_name;


    //String SerialNumber="20001";
    Intent intent = new Intent();
    List<User> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        initRes();
    }

    private void initRes() {
        userNameText = (EditText) findViewById(R.id.userNameText);
        bt_code = (Button) findViewById(R.id.bt_code);
        bt_code.setOnClickListener(this);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("搜索二维码编号");
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_code:


                String SerialNumber = userNameText.getText().toString().trim();
//                RetrofitClient.createService(GarbageAPI.class,"http://111.1.31.184:86/")
//                        .httpGarbage(SerialNumber)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new MySubscriber(CodeActivity.this) {
//                            @Override
//                            public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
//                                Log.i("tag", "onError: "+responeThrowable.toString());
//                            }
//
//                            @Override
//                            public void onNext(Object o) {
//                                Log.i("tag", "onNext: "+o.toString());
//                            }
//                        });
                OkHttpUtils.post()
                        .url("http://117.149.146.131:6111/monitor/api/values/GetTrashInfo")
                        .addParams("SerialNumber", SerialNumber)
                        .addHeader("accept","Application/json").build().

                        execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("tag",e.toString());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("tag",response);
                                Intent intent = new Intent(CodeActivity.this, HouseHolder.class);
                                // intent.putExtra("SerialNumber",address+"-"+userName);
                                intent.putExtra("houseHolderEntity",response);
                                startActivity(intent);
                            }
                        });




//                intent.setClass(CodeActivity.this,Householder.class);
//                startActivity(intent);
//                 finish();
                break;
            default:
                break;
        }
    }

}

