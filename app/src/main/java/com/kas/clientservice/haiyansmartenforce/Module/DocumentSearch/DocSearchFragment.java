package com.kas.clientservice.haiyansmartenforce.Module.DocumentSearch;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.Base.BaseFragment;
import com.kas.clientservice.haiyansmartenforce.Entity.DocSearchListEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 描述：
 * 时间：2018-08-28
 * 公司：COMS
 */

public class DocSearchFragment extends BaseFragment {
    @BindView(R.id.lv_docSearch)
    ListView listView;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    int position;
    List<DocSearchListEntity.RtnBean> list;
    DocSearchListAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_doc_search;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        position = getArguments().getInt("position");

        list = new ArrayList<>();
        adapter = new DocSearchListAdapter(mContext,list);
        listView.setEmptyView(ll_empty);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,DocDetaiActivity.class);
                intent.putExtra("id",list.get(position).getOfficeID());
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);

        if (position == 0) {

            loadData();
        }else {
            loadData1();
        }
    }

    private void loadData1() {
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"mobileapi/staff/OfficeIsReadList.ashx")
                .addParams("UserID","1259")
                .addParams("DepartmentID", "8")
//                .addParams("UserID", UserSingleton.USERINFO.getName().UserID)
//                .addParams("DepartmentID", UserSingleton.USERINFO.getName().DepartmentID)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                ToastUtils.showToast(mContext,"网络错误");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                DocSearchListEntity entity = new Gson().fromJson(response,DocSearchListEntity.class);
                if (entity.isState()) {
                    if (entity.getRtn()!=null) {
                        list.addAll(entity.getRtn());
                        adapter.notifyDataSetChanged();
                    }else {
                        ToastUtils.showToast(mContext,"暂无数据");
                    }
                }else {
                    ToastUtils.showToast(mContext,entity.getErrorMsg());
                }
            }
        });
    }

    private void loadData() {
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"mobileapi/staff/officewaitereadlist.ashx")
                .addParams("UserID","1259")
                .addParams("DepartmentID", "8")
//                .addParams("UserID", UserSingleton.USERINFO.getName().UserID)
//                .addParams("DepartmentID", UserSingleton.USERINFO.getName().DepartmentID)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                ToastUtils.showToast(mContext,"网络错误");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "onResponse: "+response);
                DocSearchListEntity entity = new Gson().fromJson(response,DocSearchListEntity.class);
                if (entity.isState()) {
                    if (entity.getRtn()!=null) {
                        list.addAll(entity.getRtn());
                        adapter.notifyDataSetChanged();
                    }else {
                        ToastUtils.showToast(mContext,"暂无数据");
                    }
                }else {
                    ToastUtils.showToast(mContext,entity.getErrorMsg());
                }
            }
        });
    }
}
