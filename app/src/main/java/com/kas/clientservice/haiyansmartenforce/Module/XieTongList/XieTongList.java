package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.MyApplication;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import okhttp3.Call;
import smartenforce.widget.MyXRecyclerView;

/**
 * Created by DELL_Zjcoms02 on 2018/6/25.
 */

public class XieTongList extends BaseActivity implements View.OnClickListener,XRecyclerView.LoadingListener{
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
//    @BindView(R.id.ltv_ajbl)
//    ListView ltv_ajbl;
    @BindView(R.id.ltv_ajbl)
    MyXRecyclerView ltv_ajbl;
    XieTongListAdapter adapter;
    Intent intent;
    MyApplication app;
    int PAGE_NUM = 1;
    ArrayList<Project_Info.RtnBean> list=new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_xietong_list;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();
        tv_title.setText("案件办理");
        app =(MyApplication)getApplication();

        iv_back.setOnClickListener(this);
        initAdapter();
        query();

    }
    private void initAdapter(){
        adapter=new XieTongListAdapter(list,mContext);
        adapter.setOnItemClickListener(new XieTongListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int p) {
                Project_Info.RtnBean bean= list.get(p);
                startActivity(new Intent(mContext,XieTongDetalis.class).putExtra("projcode",bean.projcode).putExtra("state",bean.state));
            }
        });

        ltv_ajbl.setAdapter(adapter);

        ltv_ajbl.setLoadingListener(this);
        ltv_ajbl.setPullRefreshEnabled(true);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_heaer_back:
                finish();
                break;

        }

    }

    //TODO
    private void query() {
        showLoadingDialog();
        String code = UserSingleton.USERINFO.szcg.UserDefinedCode;
        Log.i(TAG, "query: "+code);
        OkHttpUtils.post().url(XTURL.URLLIST)
                .addParams("optionName", XTURL.haiyanlist)
                .addParams("targetDepartCode", UserSingleton.USERINFO.szcg.UserDefinedCode)
                .addParams("state",3+"")
                .addParams("Page",PAGE_NUM+"")
                .addParams("Count",10+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dismissLoadingDialog();
                ltv_ajbl.onComplete();
                Log.i(TAG, "onError: "+e.toString());
                ToastUtils.showToast(mContext,"程序异常");
            }
            @Override
            public void onResponse(String response, int id) {
                dismissLoadingDialog();
                ltv_ajbl.onComplete();
                if (PAGE_NUM==1){
                    list.clear();
                }
                    Project_Info info=gson.fromJson(response, Project_Info.class);
                    if(info.isState()){
                        Project_Info.RtnBean[] bean=gson.fromJson(gson.toJson(info.getRtn()), Project_Info.RtnBean[].class);
                        Collections.addAll(list,bean);
                    }else {
                        ToastUtils.showToast(mContext,"获取失败");
                    }
                adapter.notifyDataSetChanged();

                }


        });
    }

    @Override
    public void onRefresh() {
        PAGE_NUM = 1;
        query();
    }

    @Override
    public void onLoadMore() {
        PAGE_NUM++;
        query();
    }
}
