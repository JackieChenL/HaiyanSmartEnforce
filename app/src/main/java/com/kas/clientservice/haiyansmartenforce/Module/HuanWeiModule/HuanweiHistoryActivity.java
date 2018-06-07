package com.kas.clientservice.haiyansmartenforce.Module.HuanWeiModule;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.HuanweiAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.HuanweiListEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HuanweiHistoryActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.lv_huanweiCheck_history)
    ListView listView;
    @BindView(R.id.iv_header_refresh)
    ImageView iv_fresh;

    List<HuanweiListEntity.BoardBean> list;
    HuanweiListAdapter huanweiListAdapter;
    int type = 0;
    String id = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huanwei_history;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        iv_back.setOnClickListener(this);
        iv_fresh.setOnClickListener(this);
        iv_fresh.setVisibility(View.VISIBLE);
        tv_title.setText("历史记录");
        type = getIntent().getIntExtra("type",0);
        Log.i(TAG, "initResAndListener: "+type);
        switch (type) {
            case 5:
                id = UserSingleton.USERINFO.getReviewNameID();
                break;
            case 6:
                id = UserSingleton.USERINFO.getCheckNameID();
                break;
            case 7:
                id = UserSingleton.USERINFO.getChangeNameID();
                break;
        }
        initListView();
        loadData();


    }

    private void initListView() {
        list = new ArrayList<>();
        huanweiListAdapter = new HuanweiListAdapter(list, mContext);
        listView.setAdapter(huanweiListAdapter);
        huanweiListAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,HuanweiHistoryDetailActivity.class);
                intent.putExtra("id",list.get(i).getID());
                startActivity(intent);
            }
        });
    }
    private void loadData() {
        RetrofitClient.createService(HuanweiAPI.class)
                .httpGetHistoryList(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<BaseEntity<HuanweiListEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: "+responeThrowable);
                    }

                    @Override
                    public void onNext(BaseEntity<HuanweiListEntity> s) {
                        list.clear();
                        list.addAll(s.getRtn().getBoard());
//                        Log.i(TAG, "onNext: "+s);
                        huanweiListAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.iv_header_refresh:
                loadData();
                break;

        }
    }
}
