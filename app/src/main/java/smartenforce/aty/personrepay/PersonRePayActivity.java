package smartenforce.aty.personrepay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import smartenforce.adapter.ExitListAdapter;
import smartenforce.aty.parking.HistoryActivity;
import smartenforce.base.HttpApi;
import smartenforce.base.NetResultBean;
import smartenforce.base.ShowTitleActivity;
import smartenforce.bean.tcsf.ArrearsBean;
import smartenforce.bean.tcsf.TcListBeanResult;
import smartenforce.impl.BeanCallBack;

public class PersonRePayActivity extends ShowTitleActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Spinner sp_province, sp_ABC;
    private EditText et_cp_num;
    private TextView tev_query;
    private RecyclerView rv;
    String[] arr_province;
    String[] arr_abc;
    String province = "浙";
    String A2Z = "F";
    private ArrearsListAdapter adapter;
    private ArrayList<ArrearsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repay_query);

    }

    @Override
    protected void findViews() {
        sp_province = (Spinner) findViewById(R.id.sp_province);
        sp_ABC = (Spinner) findViewById(R.id.sp_ABC);
        et_cp_num = (EditText) findViewById(R.id.et_cp_num);
        tev_query = (TextView) findViewById(R.id.tev_query);
        rv = (RecyclerView) findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(aty);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initDataAndAction() {
        tev_title.setText("欠费查询");
        sp_province.setOnItemSelectedListener(this);
        sp_ABC.setOnItemSelectedListener(this);
        et_cp_num.setTransformationMethod(new UpperCaseTransform());
        tev_query.setOnClickListener(this);
        arr_province = getResources().getStringArray(R.array.provinceName);
        arr_abc = getResources().getStringArray(R.array.A2Z);

        adapter = new ArrearsListAdapter(list, aty);
        adapter.setOnItemClickListener(new ArrearsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int p) {
                Intent intent = new Intent(aty, WebViewPayActivity.class);
                intent.putExtra("ArrearsBean", list.get(p));
                startActivity(intent);
            }
        });
        rv.setAdapter(adapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_province:
                province = arr_province[position];
                break;
            case R.id.sp_ABC:
                A2Z = arr_abc[position];
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        closeKeybord();
        switch (v.getId()) {
            case R.id.tev_query:
                doQueryList();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String cp = getText(et_cp_num);
        String carNumber = province + A2Z + cp;
        if (carNumber.length()==7){
            doQueryList();
        }


    }

    private void doQueryList() {
        String cp = getText(et_cp_num);
        String carNumber = province + A2Z + cp;
        if (carNumber.length()!=7){
            warningShow("车牌号格式错误");
            return;
        }
        OkHttpUtils.post().url(HttpApi.URL_ARREARAGE)
                .addParams("carnum", carNumber)
                .build().execute(new BeanCallBack(aty, "查询中") {
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
                list.addAll(bean.getResultBeanList(ArrearsBean.class));
            } else {
                show("该车无历史欠费记录");
            }
        } else {
            warningShow(bean.ErrorMsg);
        }

        adapter.notifyDataSetChanged();
    }



    public class UpperCaseTransform extends ReplacementTransformationMethod {
        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }
    }

}
