package smartenforce.aty.function3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.adapter.ItemAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.CaseBean;
import smartenforce.bean.InvestigateBean;
import smartenforce.bean.InvestigateDetailBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.intf.ItemListener;
import smartenforce.widget.FullyLinearLayoutManager;


public class InvestigateDetailActivity extends ShowTitleActivity {

    private TextView tev_ReferenceNumber, tev_EntryTimeSou, tev_AddressSou, tev_NameForShort, tev_ContentSou;
    private InvestigateBean investigateBean;


    private ItemAdapter adapter;
    private List<Object> list = new ArrayList<Object>();
    private RecyclerView rcv_list;

    private InvestigateDetailBean investigateDetailBean;
    private int EntOrCitiSou;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigate_detail);

    }

    @Override
    protected void findViews() {
        tev_ReferenceNumber = (TextView) findViewById(R.id.tev_ReferenceNumber);
        tev_EntryTimeSou = (TextView) findViewById(R.id.tev_EntryTimeSou);
        tev_AddressSou = (TextView) findViewById(R.id.tev_AddressSou);
        tev_NameForShort = (TextView) findViewById(R.id.tev_NameForShort);
        tev_ContentSou = (TextView) findViewById(R.id.tev_ContentSou);
        rcv_list = (RecyclerView) findViewById(R.id.rcv_list);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("调查取证详情");
        investigateBean = (InvestigateBean) getIntent().getSerializableExtra("InvestigateBean");
        adapter = new ItemAdapter(list, aty);
        adapter.setListener(new ItemListener() {
            @Override
            public void onItemClick(int p) {
                CaseBean caseBean = ((CaseBean) list.get(p));
                if (caseBean.type.equals("现场勘查")) {
                    startActivity(new Intent(aty, InquestItemDetailActivity.class).putExtra("ID", caseBean.id));
                }

            }
        });
        adapter.setButtonListner(new ItemAdapter.ButtonListener() {
            @Override
            public void onAddPictureClick(int p) {
                CaseBean caseBean = ((CaseBean) list.get(p));
                if (caseBean.type.equals("现场勘查")) {
                    int id = caseBean.id;
                    String address = investigateBean.CSAddress;
                    startActivity(new Intent(aty, AddPictureActivity.class).putExtra("ID", id).putExtra("ADDRESS", address));
                }
            }

            @Override
            public void onAddGraphClick(int p) {
                int id = ((CaseBean) list.get(p)).id;
                startActivity(new Intent(aty, AddGraphActivity.class).putExtra("ID", id));
            }
        });
        rcv_list.setLayoutManager(new FullyLinearLayoutManager(aty));
        rcv_list.setAdapter(adapter);
        getDetailInfo();

    }


    @Override
    protected void onResume() {
        super.onResume();
        getCaseInfoList();

    }

    //详情
    private void getDetailInfo() {
        OkHttpUtils.post().url(HttpApi.URL_SOURCEDETAIL).addParams("SourceID", investigateBean.ID + "")
                .build().execute(new BeanCallBack(aty, "获取详情中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State && bean.total == 1) {
                    investigateDetailBean = bean.getResultBeanList(InvestigateDetailBean.class).get(0);
                    fillInvestigateDetail();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });
    }

    //获取证据列表
    private void getCaseInfoList() {
        OkHttpUtils.post().url(HttpApi.URL_INVESTIGATIONALL).addParams("ID", investigateBean.ID + "")
                .addParams("SortType", investigateBean.SortTypeID + "")
                .build().execute(new BeanCallBack(aty, null) {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    ArrayList beanList = bean.getResultBeanList(CaseBean.class);
                    list.clear();
                    list.addAll(beanList);
                    adapter.notifyDataSetChanged();
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });


    }


    private void fillInvestigateDetail() {
        tev_ReferenceNumber.setText(investigateDetailBean.ReferenceNumber);
        tev_EntryTimeSou.setText(investigateDetailBean.EntryTimeSou);
        tev_AddressSou.setText(investigateDetailBean.AddressSou);
        tev_NameForShort.setText(investigateDetailBean.NameThL);
        tev_ContentSou.setText(investigateDetailBean.ContentSou);
        EntOrCitiSou = investigateDetailBean.EntOrCitiSou;
        if (EntOrCitiSou == 1 || EntOrCitiSou == 2) {
            tev_title_right.setText("新增");
            tev_title_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(aty, AddInquestActivity.class);
                    intent.putExtra("TYPE", EntOrCitiSou);
                    intent.putExtra("InvestigateBean", investigateBean);
                    startActivity(intent);

                }
            });
        }

    }

    //笔录类型选择
    private void showInvestTypeChoose() {
        new AlertView("笔录类型", null, null, null, new String[]{"现场勘查", "强制措施"}, aty, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    Intent intent = new Intent(aty, AddInquestActivity.class);
                    intent.putExtra("TYPE", EntOrCitiSou);
                    intent.putExtra("InvestigateBean", investigateBean);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(aty, AddEnforceActivity.class);
                    intent.putExtra("InvestigateDetailBean", investigateDetailBean);
                    startActivity(intent);

                }
            }
        }).show();


    }

}




















