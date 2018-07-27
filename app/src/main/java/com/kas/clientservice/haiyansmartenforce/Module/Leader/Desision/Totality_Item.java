package com.kas.clientservice.haiyansmartenforce.Module.Leader.Desision;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.Vp_Totality_Item;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Module.Leader.DetailsListActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class Totality_Item extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    TextView tv_title_name;
    ImageView iv_title_back;
    TextView tv_totality_item_time;
    TextView tv_totality_item_act;
    ListView lv_totality_item;
    LinearLayout ll_totality_item_num;
    TextView totality_item_num;
    List<Vp_Totality_Item> list;
    String actName;
    int actID;
    String start_time;
    String end_time;

    @Override
    protected int getLayoutId() {
        return R.layout.totality;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        initRes();
        tv_title_name.setText("业务决策");
        Intent intent = this.getIntent();
        actName = intent.getStringExtra("actName");
        actID = intent.getIntExtra("actID", 0);
        start_time = intent.getStringExtra("start_time");
        end_time = intent.getStringExtra("end_time");
        tv_totality_item_time.setText(start_time + "--" + end_time);
        tv_totality_item_act.setText(actName);
        //ToolModel model=new ToolModel(this);
        //model.TotalityItem(start_time, end_time,actID, new TotalityItem());
        netWorkRequest(start_time, end_time, actID);
    }

    public static Handler mHandlerBrck;

    private void netWorkRequest(String startTime, String endTime, int actID) {
//        OkHttpUtils.TotalityItem(startTime, endTime, actID, mHandlerBrck, Constant.TotalityItem);
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"Mobile/DepartmentActionCaseStatistic.ashx")
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .addParams("actID", actID+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                showNetErrorToast();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                Date(response);
            }
        });

    }


    private void Date(String response) {
        list = new ArrayList<Vp_Totality_Item>();
        int num = 0;
        try {
            JSONObject object = new JSONObject(response.toString());
            JSONArray array = object.getJSONArray("KS");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object2 = array.getJSONObject(i);
                String depName = object2.getString("depName");
                int caseNum = object2.getInt("caseNum");
                num += caseNum;
                Vp_Totality_Item totality_Item = new Vp_Totality_Item(depName, caseNum);
                list.add(totality_Item);
            }
            if (num == 0) {
                ll_totality_item_num.setVisibility(View.GONE);
            } else {
                ll_totality_item_num.setVisibility(View.VISIBLE);
                totality_item_num.setText(num + "");
            }
            Totality_Item_Adapter adapter = new Totality_Item_Adapter(Totality_Item.this, list);
            lv_totality_item.setAdapter(adapter);
            ListViewFitParent.setListViewHeightBasedOnChildren(lv_totality_item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRes() {
        tv_title_name = (TextView) findViewById(R.id.tv_header_title);
        iv_title_back = (ImageView) findViewById(R.id.iv_heaer_back);
        tv_totality_item_time = (TextView) findViewById(R.id.tv_totality_item_time);
        tv_totality_item_act = (TextView) findViewById(R.id.tv_totality_item_act);
        lv_totality_item = (ListView) findViewById(R.id.lv_totality_item);
        ll_totality_item_num = (LinearLayout) findViewById(R.id.ll_totality_item_num);
        totality_item_num = (TextView) findViewById(R.id.totality_item_num);
        iv_title_back.setOnClickListener(this);
        lv_totality_item.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setClass(Totality_Item.this, DetailsListActivity.class);
        intent.putExtra("what", Constants.WhatDecisionTotailsDetailsPersonage);
        intent.putExtra("depName", list.get(arg2).getDepName());
        intent.putExtra("actID", actID + "");
        intent.putExtra("StartTime", start_time);
        intent.putExtra("EndTime", end_time);
        startActivity(intent);
    }
}