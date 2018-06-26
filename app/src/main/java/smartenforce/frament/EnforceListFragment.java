package smartenforce.frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.TimePickerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import smartenforce.adapter.ItemAdapter;
import smartenforce.aty.function5.EnforceDetailActivity;
import smartenforce.base.BaseFragment;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.EnforceListBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;
import smartenforce.projectutil.TimePickerUtil;
import smartenforce.util.DateUtil;
import smartenforce.widget.MyXRecyclerView;

public class EnforceListFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    private View view;
    private int PAGE_NUM = 1;

    private Button btn_query;
    private TextView tev_time;
    private TextView tev_person;
    private EditText edt_referNumber;

    private AlertView queryTypeAlertView = null;


    private MyXRecyclerView xrcv_enforce;
    private ItemAdapter adapter;
    private List<Object> beanList = new ArrayList<Object>();
    private TimePickerView timePickerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_item_enforce_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xrcv_enforce = (MyXRecyclerView) view.findViewById(R.id.xrcv_enforce);
        xrcv_enforce.setLoadingListener(this);
        adapter = new ItemAdapter(beanList, context);
        adapter.setListener(new ItemListener() {
            @Override
            public void onItemClick(int P) {
                EnforceListBean bean = (EnforceListBean) beanList.get(P - 1);
                Intent intent = new Intent(aty, EnforceDetailActivity.class);
                intent.putExtra("EnforceListBean", bean);
                aty.startActivity(intent);
            }
        });
        xrcv_enforce.setAdapter(adapter);

        btn_query = (Button) view.findViewById(R.id.btn_query);
        tev_time = (TextView) view.findViewById(R.id.tev_time);
        tev_person = (TextView) view.findViewById(R.id.tev_person);
        edt_referNumber = (EditText) view.findViewById(R.id.edt_referNumber);
        timePickerView = TimePickerUtil.getYMTimePicker(aty, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tev_time.setText(DateUtil.getFormatDate(date, DateUtil.YM_CHINESE));
            }
        });
        tev_time.setOnClickListener(noFastClickLisener);
        btn_query.setOnClickListener(noFastClickLisener);
        tev_person.setOnClickListener(noFastClickLisener);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    private NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case R.id.tev_time:
                    timePickerView.show();
                    break;
                case R.id.btn_query:
                    PAGE_NUM = 1;
                    doQueryEnforceList();
                    break;
                case R.id.tev_person:
                    if (queryTypeAlertView == null) {
                        final String[] arr = new String[]{"中队", "本人", "全部"};
                        queryTypeAlertView = new AlertView("经办人", null, null, null, arr, aty, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                tev_person.setText(arr[position]);
                            }
                        });
                    }
                    queryTypeAlertView.show();
                    break;

            }

        }
    };


    // TODO :暂时不用 参数 InATypeID 输入 3(扣押申请),4(解除扣押申请),6(先行登记保存)
    private void doQueryEnforceList() {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(HttpApi.URL_ENFORCELIST)
                .addParams("UserID", app.userID)
                .addParams("DepartmentID", app.DepartmentID)
                .addParams("Count", "10")
                .addParams("Page", PAGE_NUM + "")
                .addParams("ReferenceNumberInA", getText(edt_referNumber));
        String monthYear = getText(tev_time);
        if (!isEmpty(monthYear) && monthYear.length() == 8) {
            postFormBuilder.addParams("Year", monthYear.substring(0, 4));
            postFormBuilder.addParams("Month", monthYear.substring(5, 7));
        }

        String AppType = getText(tev_person);

        if (isEmpty(AppType) || AppType.equals("全部")) {

        } else if (AppType.equals("本人")) {
            postFormBuilder.addParams("AppType", "1");

        } else if (AppType.equals("中队")) {
            postFormBuilder.addParams("AppType", "2");
        }
        postFormBuilder.build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                xrcv_enforce.onComplete();
                if (PAGE_NUM == 1) {
                    beanList.clear();
                }
                if (bean.State) {
                    if (bean.total > 0) {
                        beanList.addAll(bean.getResultBeanList(EnforceListBean.class));
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


        });

    }


    @Override
    public void onRefresh() {
        PAGE_NUM = 1;
        doQueryEnforceList();
    }

    @Override
    public void onLoadMore() {
        PAGE_NUM++;
        doQueryEnforceList();
    }
}
