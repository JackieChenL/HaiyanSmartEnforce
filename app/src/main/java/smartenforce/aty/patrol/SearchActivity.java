package smartenforce.aty.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

import smartenforce.base.NetResultBean;
import smartenforce.bean.tcsf.NameValueIdBean;
import smartenforce.bean.tcsf.SearchListBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.intf.OnItemClickListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.base.HttpApi;
import smartenforce.base.ShowTitleActivity;

public class SearchActivity extends ShowTitleActivity implements View.OnClickListener {
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
    protected void findViews() {
        edt_road = (EditText) findViewById(R.id.edt_road);
        tev_query = (TextView) findViewById(R.id.tev_query);
        rv = (RecyclerView) findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(aty);
        rv.setLayoutManager(layoutManager);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("查询");
        tev_query.setOnClickListener(this);
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
        OkHttpUtils.post().url(HttpApi.URL_ROAD)
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
                new ListDialog(aty, list, new OnItemClickListener() {

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
        OkHttpUtils.post().url(HttpApi.URL_BERTHSEARCH)
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




}
