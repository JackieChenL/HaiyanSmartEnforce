package com.kas.clientservice.haiyansmartenforce.tcsf.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.adapter.ExitListAdapter;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.HTTP_HOST;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.TcListBeanResult;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

/**
 * 离开页面（占用车位列表）
 */
public class ExitListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_heaer_back;
    private TextView tv_header_title;
    private RecyclerView rv;
    private ExitListAdapter adapter;
    private ArrayList<TcListBeanResult> list=new ArrayList<TcListBeanResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_list);
        rv = (RecyclerView) findViewById(R.id.rv);
        iv_heaer_back = (ImageView) findViewById(R.id.iv_heaer_back);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText("停车列表");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(aty);
        rv.setLayoutManager(layoutManager);
        adapter = new ExitListAdapter(list, aty);
        adapter.setOnItemClickListener(new ExitListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int p) {
                startActivity(new Intent(aty,ExitActivity.class).putExtra("TcListBeanResult",list.get(p)));
            }
        });
        rv.setAdapter(adapter);
        iv_heaer_back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doNetworkList();
    }

    private void doNetworkList() {
        OkHttpUtils.post().url(HTTP_HOST.URL_PARK_LIST)
                .addParams("Opername",getOpername())
                .addParams("type","1")
                .addParams("carnum","")
                .build().execute(new BeanCallBack(aty, null) {
            @Override
            public void handleBeanResult(NetResultBean bean) {

                handleListNetResult(bean);
            }
        });
    }

    private void handleListNetResult(NetResultBean bean) {
        list.clear();
        if (bean.State) {
            if (bean.total > 0) {
             list.addAll(bean.getResultBeanList(TcListBeanResult.class));

            } else {
                //没有获取占用车辆列表
                show("获取当前车辆占用泊位为空");
            }
        } else {
            show(bean.ErrorMsg);
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_heaer_back:
                finish();
                break;
        }

    }
}
