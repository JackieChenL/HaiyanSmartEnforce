package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

public class Doorplate extends BaseActivity implements View.OnClickListener{
    TextView tv_title_name;
    ImageView iv_title_back;
    TextView tev_huzhuaddress;
    EditText edt_huzhuaddress;
    Button bt_search;
    Intent intent=new Intent();
    Spinner sp_town;
//    ArrayAdapter<String> adapter;
    String address;
    ListView ltv_door;
    HuZhuAdapter huzhuadapter;
    ArrayList<HuZhuBean> list=new ArrayList<HuZhuBean>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_doorplate;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

//    public List<String> getDataSource(){
//        List<String> list=new ArrayList<String>();
//        list.add("百联村");
//        list.add("超同村");
//        list.add("得胜村");
//        list.add("五丰村");
//        list.add("胜利村");
//        list.add("桃北村");
//        list.add("横港村");
//        list.add("新升村");
//        list.add("农丰村");
//        list.add("逍恬村");
//        list.add("五联村");
//        list.add("外浜");
//        list.add("花店浜");
//        list.add("俞陆门");
//        list.add("百步村");
//        list.add("顾家浜");
//        list.add("董家门");
//        list.add("朱家栅");
//        list.add("金范门");
//        list.add("麻夏埭");
//        list.add("新升村");
//        return list;
//    }
    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        iv_title_back=(ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_name=(TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("搜索地址");
        bt_search=(Button) findViewById(R.id.btn_search);
        bt_search.setOnClickListener(this);
        tev_huzhuaddress=(TextView) findViewById(R.id.tev_huzhuaddress);
        edt_huzhuaddress=(EditText) findViewById(R.id.edt_huzhuaddress) ;
//        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
//                getDataSource());
//        sp_town.setAdapter(adapter);
        ltv_door=(ListView) findViewById(R.id.ltv_door);
        huzhuadapter=new HuZhuAdapter(list,mContext);
        ltv_door.setAdapter(huzhuadapter);
        huzhuadapter.notifyDataSetChanged();
        ltv_door.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(mContext,RankActivity.class);
                intent.putExtra("Address",list.get(position).Address);
                intent.putExtra("SerialNumber",list.get(position).SerialNumber);
                intent.putExtra("ID",2);
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
            case R.id.btn_search:
                address=edt_huzhuaddress.getText().toString().trim();
                Data();
                break;
            default:
                break;
        }
    }

    private void Data() {
        OkHttpUtils.post().url("http://117.149.146.131:6111/monitor/api/values/gettrashinfo")
                .addParams("Address",address).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson=new Gson();
                HuZhuBean bean=gson.fromJson(response,HuZhuBean.class);
                list.clear();
                Collections.addAll(list,bean);
                huzhuadapter.notifyDataSetChanged();
            }
        });
    }
}

