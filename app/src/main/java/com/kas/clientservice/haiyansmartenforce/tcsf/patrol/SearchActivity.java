package com.kas.clientservice.haiyansmartenforce.tcsf.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.HTTP_HOST;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.NetResultBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.base.TitleActivity;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.NameValueIdBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.bean.SearchListBean;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.BeanCallBack;
import com.kas.clientservice.haiyansmartenforce.tcsf.intf.OnItemClickListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends TitleActivity implements View.OnClickListener {
    private TextView tev_query;
    private RecyclerView rv;
    private EditText edt_road;
    private SearchListAdapter adapter;
    private List<SearchListBean> list = new ArrayList<SearchListBean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_search);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tev_query:
                doQueryRoad();
                break;
        }
    }


    private void doQueryRoad() {
        String RoadName = edt_road.getText().toString().trim();
        OkHttpUtils.post().url(HTTP_HOST.URL_ROAD)
                .addParams("Road", RoadName)
                .addParams("num", "15")
                .build().execute(new BeanCallBack(aty, null) {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                showLoadList(bean);
            }
        });
    }

    private void showLoadList(NetResultBean bean) {
        if (bean.State) {
            if (bean.total > 0) {
                List<Object> list = new ArrayList<Object>();
                list.addAll(bean.getResultBeanList(NameValueIdBean.RoadBean.class));
                new ListDialog(aty, list, new com.kas.clientservice.haiyansmartenforce.tcsf.intf.OnItemClickListener() {

                    @Override
                    public void onItemClick(int p, Object obj) {
                        NameValueIdBean.RoadBean roadBean = (NameValueIdBean.RoadBean) obj;
                        edt_road.setText(roadBean.Road);
                        doQueryList(roadBean.ID);

                    }
                }).setTitle("选择路段").show();
            } else {
                show("查询结果为空");
            }

        } else {
            warningShow(bean.ErrorMsg);
        }


    }


    //当前路段下数据列表
    private void doQueryList(String id) {
        OkHttpUtils.post().url(HTTP_HOST.URL_BERTHSEARCH)
                .addParams("RoadId", id)
                .build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                showList(bean);
            }
        });


    }

    private void showList(NetResultBean bean) {
        list.clear();
        if (bean.State) {
            if (bean.total > 0) {
                list.addAll(bean.getResultBeanList(SearchListBean.class));
            } else {
                show("查询结果为空");
            }


        } else {
            warningShow(bean.ErrorMsg);
        }
        if (adapter == null) {
            adapter = new SearchListAdapter(list, aty);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int p, Object obj) {
                    SearchListBean bean = (SearchListBean) obj;
                    if (!bean.Btid.equals("0")) {
                        startActivity(new Intent(aty, DetailActivity.class).putExtra("btid", bean.Btid));
                    }
                }
            });
            rv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }


    }


    @Override
    protected void findViewBy() {
        edt_road = (EditText) findViewById(R.id.edt_road);
        tev_query = (TextView) findViewById(R.id.tev_query);
        rv = (RecyclerView) findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(aty);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void setTitleTxt() {
        tv_header_title.setText("查询");
    }

    @Override
    protected void initData() {
        tev_query.setOnClickListener(this);
    }


}
