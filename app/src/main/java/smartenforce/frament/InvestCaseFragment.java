package smartenforce.frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import smartenforce.base.BaseFragment;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.InvestigateBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;
import smartenforce.projectutil.TimePickerUtil;
import smartenforce.util.DateUtil;
import smartenforce.widget.MyXRecyclerView;

public class InvestCaseFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    private Button btn_query;
    private MyXRecyclerView xrcv_list;


    private TimePickerView timePickerView;
    private TextView tev_starttime, tev_endtime;
    private EditText edt_dsr, edt_wh;

    private int PAGE_NUM = 1;
    private ItemAdapter adapter;
    private List<Object> list = new ArrayList<Object>();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_item_case, container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tev_starttime = (TextView)view. findViewById(R.id.tev_starttime);
        tev_endtime = (TextView)view.  findViewById(R.id.tev_endtime);
        edt_dsr = (EditText)view. findViewById(R.id.edt_dsr);
        edt_wh = (EditText) view. findViewById(R.id.edt_wh);
        btn_query = (Button)view.  findViewById(R.id.btn_query);
        xrcv_list = (MyXRecyclerView)view. findViewById(R.id.xrcv_list);

        btn_query.setOnClickListener(noFastClickLisener);
        tev_starttime.setOnClickListener(noFastClickLisener);
        tev_endtime.setOnClickListener(noFastClickLisener);
        tev_starttime.setText(DateUtil.showDate(-30));
        tev_endtime.setText(DateUtil.getFormatDate(new Date(),DateUtil.YMDHM));
        adapter = new ItemAdapter(list, aty);
        adapter.setListener(new ItemListener() {
            @Override
            public void onItemClick(int P) {
                warningShow("功能开发中");
            }
        });
        xrcv_list.setAdapter(adapter);
        xrcv_list.setLoadingListener(this);
        timePickerView= TimePickerUtil.getYMDHMTimePicker(aty);
    }
    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
    NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
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

    private void queryList() {
        String startTime = getText(tev_starttime);
        String endTime = getText(tev_endtime);
        String referenceNumber = getText(edt_wh);
        String dsr = getText(edt_dsr);
        OkHttpUtils.post().url(HttpApi.CASE_INVESTLIST_LIST).addParams("UserID", app.userID)
                .addParams("Count", "10").addParams("Page", PAGE_NUM + "")
                .addParams("StartTime", startTime).addParams("EndTime", endTime)
                .addParams("NameECC", dsr).addParams("ReferenceNumber", referenceNumber)
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
                list.addAll(bean.getResultBeanList(InvestigateBean.class));
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
