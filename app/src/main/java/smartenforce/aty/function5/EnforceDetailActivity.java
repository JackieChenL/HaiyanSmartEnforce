package smartenforce.aty.function5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.adapter.ShowTitleAdapter;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.EnforceDetailBean;
import smartenforce.bean.EnforceGoodsBean;
import smartenforce.bean.EnforceListBean;
import smartenforce.bean.InAInfoBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;
import smartenforce.widget.FullyLinearLayoutManager;


public class EnforceDetailActivity extends ShowTitleActivity {
    private EnforceListBean enforceListBean;
    private TextView tev_sqlx, tev_address, tev_start_time, tev_end_time;
    private TextView tev_describe, tev_referNumber, tev_sqyj;
    private RecyclerView rcv_list;
    private ShowTitleAdapter adapter;
    private List<Object> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enforce_detail);
    }

    @Override
    protected void findViews() {
        tev_sqlx = (TextView) findViewById(R.id.tev_sqlx);
        tev_sqyj = (TextView) findViewById(R.id.tev_sqyj);
        tev_address = (TextView) findViewById(R.id.tev_address);
        tev_start_time = (TextView) findViewById(R.id.tev_start_time);
        tev_end_time = (TextView) findViewById(R.id.tev_end_time);
        tev_describe = (TextView) findViewById(R.id.tev_describe);
        tev_referNumber = (TextView) findViewById(R.id.tev_referNumber);
        rcv_list = (RecyclerView) findViewById(R.id.rcv_list);

    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("详情");
        enforceListBean = (EnforceListBean) getIntent().getSerializableExtra("EnforceListBean");
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(aty);
        rcv_list.setLayoutManager(manager);
        queryDetail();
    }

    private void queryDetail() {
        OkHttpUtils.post().url(HttpApi.URL_ENFORCE_DETAIL)
                .addParams("InAID", enforceListBean.InternalApproveID + "")
                .addParams("InATypeID", enforceListBean.InATypeIDInA + "")
                .build().execute(new BeanCallBack(aty, "获取详情中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                if (bean.State) {
                    fillContent(bean);
                } else {
                    warningShow(bean.ErrorMsg);
                }
            }
        });


    }

    private void fillContent(NetResultBean bean) {
        try {
            EnforceDetailBean detailBean = bean.getResultBeanList(EnforceDetailBean.class).get(0);
            final InAInfoBean inAInfoBean = detailBean.InAInfo.get(0);
            tev_sqlx.setText(inAInfoBean.NameType);
            tev_address.setText(inAInfoBean.NameWar);
            tev_start_time.setText(inAInfoBean.ExecuteTime);
            tev_end_time.setText(inAInfoBean.EndTime);
            tev_sqyj.setText(inAInfoBean.ApplicationInA);
            tev_referNumber.setText(inAInfoBean.ReferenceNumberInA);
            tev_describe.setText(inAInfoBean.ReasonBasisInA);
            List<EnforceGoodsBean> enforceGoodsList = detailBean.WhgInfo;
            for (int i = 0; i < enforceGoodsList.size(); i++) {
                String nameCuS = enforceGoodsList.get(i).NameCuS;
                if (nameCuS.equals("待处理") || nameCuS.equals("待入库") || nameCuS.equals("已入库")) {
                    tev_title_right.setText("解除扣押");
                    tev_title_right.setOnClickListener(new NoFastClickLisener() {
                        @Override
                        public void onNofastClickListener(View v) {
                            Intent intent = new Intent(aty, ReleaseEnforceActivity.class);
                            intent.putExtra("EnforceListBean", enforceListBean);
                            startActivityForResult(intent, REQUESTCODE.RELEASE_ENFORSE);
                        }
                    });
                    break;
                }
            }
            list.addAll(enforceGoodsList);
            adapter = new ShowTitleAdapter(list, aty);
            adapter.setListener(new ItemListener() {
                @Override
                public void onItemClick(int P) {
                    EnforceGoodsBean bean = (EnforceGoodsBean) list.get(P);
                    startActivity(new Intent(aty, EnforceGoodsDetailActivity.class).putExtra("EnforceGoodsBean", bean));
                }
            });
            rcv_list.setAdapter(adapter);
        } catch (Exception e) {
            warningShow("数据错误");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE.RELEASE_ENFORSE && resultCode == RESULT_OK) {
            finish();
        }
    }
}




















