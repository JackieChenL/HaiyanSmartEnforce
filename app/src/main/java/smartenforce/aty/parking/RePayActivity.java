package smartenforce.aty.parking;

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
import smartenforce.adapter.ExitListAdapter;
import smartenforce.base.NetResultBean;
import smartenforce.bean.tcsf.TcListBeanResult;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;

import smartenforce.base.HttpApi;
import smartenforce.base.ShowTitleActivity;
import smartenforce.impl.BeanCallBack;

public class RePayActivity extends ShowTitleActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Spinner sp_province, sp_ABC;
    private EditText et_cp_num;
    private TextView tev_query;
    private RecyclerView rv;


    String[] arr_province;
    String[] arr_abc;
    String province = "浙";
    String A2Z = "F";
    private ExitListAdapter adapter;
    private ArrayList<TcListBeanResult> list = new ArrayList<TcListBeanResult>();
    private int clickPostion=-1;

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

        adapter = new ExitListAdapter(list, aty);
        adapter.setOnItemClickListener(new ExitListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int p) {
                clickPostion=p;
                Intent intent = new Intent(aty, HistoryActivity.class);
                intent.putExtra("TcListBeanResult", list.get(p));
                intent.putExtra("ISREPAY", true);
                startActivityForResult(intent, 100);
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


    private void doQueryList() {
        String cp = getText(et_cp_num);
        String carNumber = province + A2Z + cp;
        if (carNumber.length()!=7){
            warningShow("车牌号格式错误");
            return;
        }
        OkHttpUtils.post().url(HttpApi.URL_PARK_LIST)
                .addParams("Opername", getOpername())
                .addParams("type", "3")
                .addParams("carnum", carNumber)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            //补收成功，刷新列表
            if (clickPostion!=-1){
                list.remove(clickPostion);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
