package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Call;

public class HuzhuActivity extends BaseActivity implements View.OnClickListener{
    TextView tv_title_name;
    Button bt_search;
    ImageView iv_title_back;
    ListView ltv_huzhu;
    EditText edt_hzmz;
    Intent intent=new Intent();
    ArrayList<HuZhuBean> list=new ArrayList<HuZhuBean>();
    String name;
    HuZhuAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huzhu;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }
    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        iv_title_back=(ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_name=(TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("搜索户主");
        ltv_huzhu=(ListView) findViewById(R.id.ltv_huzhu);
        bt_search=(Button) findViewById(R.id.bt_search);
        bt_search.setOnClickListener(this);
        edt_hzmz=(EditText) findViewById(R.id.edt_hzmz);
        adapter=new HuZhuAdapter(list,mContext);
        ltv_huzhu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ltv_huzhu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(mContext,RankActivity.class);
                intent.putExtra("UserName",list.get(position).UserName);
                intent.putExtra("SerialNumber",list.get(position).SerialNumber);
                intent.putExtra("ID",1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_search:
                name=edt_hzmz.getText().toString().trim();
                Data();
                break;
            default:
                break;
        }
    }

    private void Data() {
        OkHttpUtils.post().url("http://117.149.146.131:6111/monitor/api/values/gettrashinfo")
                .addParams("UserName",name)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson=new Gson();
                HuZhuBean bean=gson.fromJson(response,HuZhuBean.class);
                list.clear();
                Collections.addAll(list,bean);
                adapter.notifyDataSetChanged();
            }
        });
    }
}

