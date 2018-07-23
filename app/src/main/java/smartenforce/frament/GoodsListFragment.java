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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import smartenforce.adapter.ItemAdapter;
import smartenforce.aty.function5.GoodsDetailActivity;
import smartenforce.base.BaseFragment;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.bean.GoodListBean;
import smartenforce.impl.BeanCallBack;
import smartenforce.impl.NoFastClickLisener;
import smartenforce.intf.ItemListener;
import smartenforce.widget.MyXRecyclerView;
import smartenforce.zxing.ScanActivity;

import static android.app.Activity.RESULT_OK;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class GoodsListFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    private View view;
    private int PAGE_NUM = 1;
    private MyXRecyclerView xrcv_goods;
    private ItemAdapter adapter;
    private List<Object> beanList = new ArrayList<>();

    private EditText edt_goods_name;
    private TextView tev_status_type;
    private EditText edt_code;
    private TextView tev_code_icon;
    private Button btn_query;

    private AlertView queryTypeAlertView=null;

    private AlertView queryStatusTypeAlertView=null;

    private int  CurrentInt=-1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_item_goods_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xrcv_goods = (MyXRecyclerView) view.findViewById(R.id.xrcv_goods);
        xrcv_goods.setLoadingListener(this);
        adapter = new ItemAdapter(beanList, context);
        adapter.setListener(new ItemListener() {
            @Override
            public void onItemClick(int P) {
                GoodListBean bean = (GoodListBean) beanList.get(P - 1);
                Intent it = new Intent(aty, GoodsDetailActivity.class);
                it.putExtra("GoodListBean", bean);
                startActivity(it);
            }
        });
        xrcv_goods.setAdapter(adapter);

        edt_goods_name = (EditText) view.findViewById(R.id.edt_goods_name);
        tev_status_type = (TextView) view.findViewById(R.id.tev_status_type);
        edt_code = (EditText) view.findViewById(R.id.edt_code);
        tev_code_icon = (TextView) view.findViewById(R.id.tev_code_icon);
        btn_query = (Button) view.findViewById(R.id.btn_query);
        tev_code_icon.setOnClickListener(noFastClickLisener);
        tev_status_type.setOnClickListener(noFastClickLisener);
        btn_query.setOnClickListener(noFastClickLisener);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }


    /**
     * //TODO:参数 AppType 输入1(本人添加的物品);2(本中队添加物品（不含本人的）);不提供值时（中队和个人都显示）
     */
    private void doQueryGoodsList() {
        OkHttpUtils.post().url(HttpApi.URL_WITHHOLDGOODLIST)
                .addParams("DepartmentID", app.DepartmentID)
                .addParams("UserID", app.userID)
                .addParams("Type", "list")
                .addParams("Count", "10")
                .addParams("Page", PAGE_NUM + "")
                .addParams("NameWit", getText(edt_goods_name))
                .addParams("NumberInt", getText(edt_code))
                .addParams("CurrentInt", CurrentInt+"")
                .build().execute(new BeanCallBack(aty, "查询中") {
            @Override
            public void handleBeanResult(NetResultBean bean) {
                xrcv_goods.onComplete();
                if (PAGE_NUM == 1) {
                    beanList.clear();
                }
                if (bean.State) {
                    if (bean.total > 0) {
                        beanList.addAll(bean.getResultBeanList(GoodListBean.class));
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
        doQueryGoodsList();
    }

    @Override
    public void onLoadMore() {
        PAGE_NUM++;
        doQueryGoodsList();
    }


    private NoFastClickLisener noFastClickLisener = new NoFastClickLisener() {
        @Override
        public void onNofastClickListener(View v) {
            closeKeybord();
            switch (v.getId()) {
                case  R.id.btn_query :
                    PAGE_NUM=1;
                    doQueryGoodsList();
                    break;
                case  R.id.tev_code_icon:
                    startActivityForResult(new Intent(aty, ScanActivity.class), REQUEST_CODE);
                    break;

                case R.id.tev_status_type:
                    if (queryStatusTypeAlertView == null) {
                        final String[] arr=new String[]{"全部","待审核","待处理","待解除","已处理","待入库","入库","出库"};
                        queryStatusTypeAlertView = new AlertView("物品状态", null, null, null, arr, aty, AlertView.Style.Alert, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                tev_status_type.setText(arr[position]);
                                CurrentInt=position;
                            }
                        });
                    }
                    queryStatusTypeAlertView.show();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (scanResult != null) {
                    String result = scanResult.getContents();
                    edt_code.setText(result);
                }
            }
        }
    }


}
