package com.kas.clientservice.haiyansmartenforce.Module.Laws;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class LawsActivity extends BaseActivity implements View.OnClickListener,TimePickerDialog.TimePickerDialogInterface {
    @BindView(R.id.iv_heaer_back)
    ImageView iv_heaer_back;
    @BindView(R.id.tv_header_title)
    TextView tv_header_title;
    @BindView(R.id.lv_law_list)
    ListView lv_law_list;
    @BindView(R.id.tv_laws_check_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_laws_check_endTime)
    TextView tv_endTime;
    @BindView(R.id.tv_laws_check_search)
    TextView tv_search;
    @BindView(R.id.et_laws_check_title)
    EditText et_title;
    @BindView(R.id.et_laws_check_content)
    EditText et_content;
    @BindView(R.id.et_laws_check_id)
    EditText et_id;

    List<LawsBean> retList;
    String startTime = "";
    String endTime = "";
    LawsListAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_laws;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        iv_heaer_back.setOnClickListener(this);
        tv_header_title.setText("法律法规");
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("type", "getlist");
//        map.put("TitleReg", "城市");
//        map.put("MaintextReg", "");
//        map.put("IssuedNumberReg", "");
//        map.put("IssueDateRegStart", "");
//        map.put("IssueDateRegEnd", "");
        tv_search.setOnClickListener(this);
        retList = new ArrayList<>();
        adapter = new LawsListAdapter(mContext,retList);
        lv_law_list.setAdapter(adapter);
        lv_law_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it=new Intent(LawsActivity.this,LawsDetailActivity.class);
                it.putExtra("bean", retList.get(i));
                startActivity(it);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_laws_check_startTime:
                choseStartTime();
                break;
            case R.id.tv_laws_check_endTime:
                choseEndTime();
                break;
            case R.id.tv_laws_check_search:
                boolean b = false;
                if (!et_content.getText().toString().trim().equals("")) {
                    b = true;
                }
                if (!et_id.getText().toString().trim().equals("")) {
                    b = true;
                }
                if (!et_title.getText().toString().trim().equals("")) {
                    b = true;
                }
                if (!startTime.trim().equals("")) {
                    b = true;
                }
                if (!endTime.trim().equals("")) {
                    b = true;
                }
                if (b) {
                    search();
                }else {
                    ToastUtils.showToast(mContext,"请至少选择一个搜索条件");
                }
        }
    }

    private void search() {
        showLoadingDialog();
        retList.clear();
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"/Use/Ashx/LawHandle.ashx")
                .addParams("type","getlist")
                .addParams("TitleReg",et_title.getText().toString())
                .addParams("MaintextReg",et_content.getText().toString())
                .addParams("IssuedNumberReg",et_id.getText().toString())
                .addParams("IssueDateRegStart",startTime)
                .addParams("IssueDateRegEnd",endTime)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                dismissLoadingDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissLoadingDialog();
                Log.i(TAG, "onResponse: "+response);
                Gson gson=new Gson();
                List<LawsBean> list = gson.fromJson(response,
                        new TypeToken<List<LawsBean>>() {
                        }.getType());
                retList.addAll(list);
                if(list==null||list.size()==0){
                    Toast.makeText(LawsActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    retList.addAll(list);
                    Log.i(TAG, "onResponse: "+retList.size());
                    adapter.notifyDataSetChanged();
                    ListViewFitParent.setListViewHeightBasedOnChildren(lv_law_list);
                }
            }
        });
    }

    int flag = 0;

    private void choseEndTime() {
        flag = 2;
        choseTime();
    }

    private void choseStartTime() {
        flag = 1;
        choseTime();
    }

    private void choseTime() {
        timePickerDialog.showPPw(lv_law_list);
    }

    TimePickerDialog timePickerDialog;

    @Override
    public void positiveListener() {
        String year = timePickerDialog.getYear() + "";
        String months = timePickerDialog.getMonth() + "";
        String day = timePickerDialog.getDay() + "";
        String hour = timePickerDialog.getHour() + "";
        String minutes = timePickerDialog.getMinute() + "";
        if (flag == 1) {
            tv_startTime.setText(year + "-" + months + "-" + day + " " + hour + ":" + minutes);
            startTime = tv_startTime.getText().toString();
        }
        if (flag == 2) {
            tv_endTime.setText(year + "-" + months + "-" + day + " " + hour + ":" + minutes);
            endTime = tv_endTime.getText().toString();
        }
    }

    @Override
    public void negativeListener() {

    }

}
