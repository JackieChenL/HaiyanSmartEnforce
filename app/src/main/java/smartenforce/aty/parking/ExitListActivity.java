package smartenforce.aty.parking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kas.clientservice.haiyansmartenforce.R;
import smartenforce.adapter.ExitListAdapter;
import smartenforce.base.NetResultBean;
import smartenforce.bean.tcsf.TcListBeanResult;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import smartenforce.base.HttpApi;
import smartenforce.base.ShowTitleActivity;
import smartenforce.impl.BeanCallBack;

/**
 * 离开页面（占用车位列表）
 */
public class ExitListActivity extends ShowTitleActivity {
    private RecyclerView rv;
    private ExitListAdapter adapter;
    private ArrayList<TcListBeanResult> list = new ArrayList<TcListBeanResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_list);

    }

    @Override
    protected void findViews() {
        rv = (RecyclerView) findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(aty);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("停车列表");
        adapter = new ExitListAdapter(list, aty);
        adapter.setOnItemClickListener(new ExitListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int p) {
                startActivity(new Intent(aty, ExitActivity.class).putExtra("TcListBeanResult", list.get(p)));
            }
        });
        rv.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        doNetworkList();
    }

    private void doNetworkList() {
        OkHttpUtils.post().url(HttpApi.URL_PARK_LIST)
                .addParams("Opername", getOpername())
                .addParams("type", "1")
                .addParams("carnum", "")
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
            warningShow(bean.ErrorMsg);
        }

        adapter.notifyDataSetChanged();
    }


}
