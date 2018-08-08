package com.kas.clientservice.haiyansmartenforce.Module.Ledger;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.AreaEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.LedgerListEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class LedgerListActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface {

    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_ledger_list_starttime)
    TextView tv_startTime;
    @BindView(R.id.tv_ledger_list_endtime)
    TextView tv_endTime;
    @BindView(R.id.tv_ledger_list_add)
    TextView tv_add;
    @BindView(R.id.tv_ledger_list_query)
    TextView tv_query;
    @BindView(R.id.lv_ledger_list)
    ListView listView;
    @BindView(R.id.mr_ledger_list)
    MaterialRefreshLayout materialRefreshLayout;
    @BindView(R.id.sp_ledger_list)
    Spinner spinner;
    TimePickerDialog timePickerDialog;
    private String startTime = "";
    private String endTime = "";

    int page = 1;
    int pageSize = 10;
    String name = "";
    int areaId = -1;
    ArrayAdapter<AreaEntity.KSBean> arrayAdapter;
    List<AreaEntity.KSBean> area = new ArrayList<>();
    List<LedgerListEntity.RowsBean> list = new ArrayList<>();
    LedgerListAdapter ledgerListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ledger_list;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("台账列表");
        iv_back.setOnClickListener(this);
        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_query.setOnClickListener(this);

        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                Log.i(TAG, "onRefresh: ");
                list.clear();
                page = 1;
                query(page);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                Log.i(TAG, "onRefreshLoadMore: ");
                super.onRefreshLoadMore(materialRefreshLayout);
                page++;
                query(page);
            }
        });

        timePickerDialog = new TimePickerDialog(mContext);
        ledgerListAdapter = new LedgerListAdapter(mContext, list);
        listView.setAdapter(ledgerListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, LedgerDetailActivity.class);
                intent.putExtra("json", gson.toJson(list.get(i)));
                startActivity(intent);
            }
        });
        getArea();
    }

    public void getArea() {
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "mobile/getare.ashx")
                .addParams("UserID", "1")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
            }

            @Override
            public void onResponse(String response, int id_) {
                Log.i(TAG, "onResponse: " + response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    AreaEntity.KSBean mbig = new AreaEntity.KSBean("-----全部-----", -1);
                    area.add(mbig);
                    JSONArray jsonArray = object.getJSONArray("KS");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        Log.i(TAG, "onResponse: " + name);
                        AreaEntity.KSBean mybig = new AreaEntity.KSBean(name, id);
                        area.add(mybig);
                    }
                    arrayAdapter = new ArrayAdapter<AreaEntity.KSBean>(mContext, android.R.layout.simple_spinner_item, area);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);

//                    query();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_ledger_list_starttime:
                choseStartTime();
                break;
            case R.id.tv_ledger_list_endtime:
                choseEndTime();
                break;
            case R.id.tv_ledger_list_query:
                query(page);
                break;
            case R.id.tv_ledger_list_add:
                startActivity(new Intent(mContext, LedgerAddActivity.class));
                break;
        }
    }

    private void query(int page) {
        showLoadingDialog();
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader + "/mobile/GetLedgerList.ashx")
                .addParams("UserID", UserSingleton.USERINFO.getName().UserID)
                .addParams("page", page + "")
                .addParams("pagesize", pageSize + "")
                .addParams("Name", name)
                .addParams("AreaID", areaId + "")
                .addParams("Datefrom", startTime + "")
                .addParams("Dateto", endTime)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
                dismissLoadingDialog();
                showNetErrorToast();

                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissLoadingDialog();
                Log.i(TAG, "onResponse: " + response);
                LedgerListEntity entity = gson.fromJson(response, LedgerListEntity.class);
                if (entity.getStatus().equals("ok")) {
                    if (entity.getRows() != null) {
                        list.addAll(entity.getRows());
                        ledgerListAdapter.notifyDataSetChanged();
//                        ListViewFitParent.setListViewHeightBasedOnChildren(listView);
                    }
                }
                materialRefreshLayout.finishRefresh();
                materialRefreshLayout.finishRefreshLoadMore();
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
        timePickerDialog.showPPw(listView);
    }


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
