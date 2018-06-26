package smartenforce.aty.function2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import smartenforce.adapter.ItemAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.SourseBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;
import smartenforce.projectutil.TimePickerUtil;
import smartenforce.util.DateUtil;
import smartenforce.widget.MyXRecyclerView;

public class QueryListActivity extends ShowTitleActivity implements XRecyclerView.LoadingListener {
    private Button btn_query;
    private MyXRecyclerView xrcv_list;

    private TimePickerView timePickerView;


    private TextView tev_starttime, tev_endtime;
    private EditText edt_afdd, edt_dsr;

    private int PAGE_NUM = 1;
    private ItemAdapter adapter;
    private List<Object> list = new ArrayList<Object>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sourse_query);
    }

    @Override
    protected void findViews() {
        tev_starttime = (TextView) findViewById(R.id.tev_starttime);
        tev_endtime = (TextView) findViewById(R.id.tev_endtime);
        edt_afdd = (EditText) findViewById(R.id.edt_afdd);
        edt_dsr = (EditText) findViewById(R.id.edt_dsr);
        btn_query = (Button) findViewById(R.id.btn_query);
        xrcv_list = (MyXRecyclerView) findViewById(R.id.xrcv_list);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("巡查查询");
        btn_query.setOnClickListener(noFastClickLisener);
        tev_starttime.setOnClickListener(noFastClickLisener);
        tev_endtime.setOnClickListener(noFastClickLisener);
        tev_starttime.setText(DateUtil.showDate(-30));
        tev_endtime.setText(DateUtil.getFormatDate(new Date(),DateUtil.YMDHM));
        adapter = new ItemAdapter(list, aty);
        adapter.setListener(new ItemListener() {
            @Override
            public void onItemClick(int P) {
                SourseBean sourseBean = (SourseBean) list.get(P - 1);
                startActivity(new Intent(aty, QueryDetailActivity.class).putExtra("SourseBean", sourseBean));

            }
        });
        xrcv_list.setAdapter(adapter);
        xrcv_list.setLoadingListener(this);
        timePickerView= TimePickerUtil.getYMDHMTimePicker(aty);

    }



    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            switch (v.getId()) {
                case R.id.tev_starttime:
                    timePickerView.setDate(DateUtil.str2Calendar(getText(tev_starttime),DateUtil.YMDHM));
                    timePickerView.show(tev_starttime);
                    break;
                case R.id.tev_endtime:
                    timePickerView.setDate(DateUtil.str2Calendar(getText(tev_endtime),DateUtil.YMDHM));
                    timePickerView.show(tev_endtime);
                    break;
                case R.id.btn_query:
                    PAGE_NUM = 1;
                    queryList();
                    break;


            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
         onRefresh();
    }

    private void queryList() {
        closeKeybord();
        String startTime = getText(tev_starttime);
        String endTime = getText(tev_endtime);
        String dsr = getText(edt_dsr);
        String address = getText(edt_afdd);
        OkHttpUtils.post().url(HttpApi.URL_SOURCELIST)
                .addParams("UserID", app.userID).addParams("DepartmentID", app.DepartmentID)
                .addParams("Count", "10").addParams("Page", PAGE_NUM + "")
                .addParams("StartTime", startTime).addParams("EndTime", endTime)
                .addParams("CaseAddress", address).addParams("NameECC", dsr)
                .addParams("SortType", "1")//巡查发现 = 1, 线索处理 = 2, 数字城管 = 3
                .build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                handleList(bean);
            }
        });
    }

    private void handleList(NetResultBean bean) {
        xrcv_list.onComplete();
        if (PAGE_NUM == 1) {
            list.clear();
        }
        if (bean.State) {
            if (bean.total > 0) {
                list.addAll(bean.getResultBeanList(SourseBean.class));
            } else {
                if (PAGE_NUM == 1) {
                    show("查询到数据为空");
                } else {
                    show("已无更多数据");
                }
            }
        } else {
            warningShow(bean.ErrorMsg);
        }
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onRefresh() {
        PAGE_NUM = 1;
        queryList();
    }

    @Override
    public void onLoadMore() {
        PAGE_NUM++;
        queryList();
    }
}




















