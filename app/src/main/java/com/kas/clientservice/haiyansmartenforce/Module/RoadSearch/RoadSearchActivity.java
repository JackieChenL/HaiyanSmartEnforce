package com.kas.clientservice.haiyansmartenforce.Module.RoadSearch;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.RoadSeachAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.RoadSearchEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;
import com.kas.clientservice.haiyansmartenforce.tcsf.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RoadSearchActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.lv_roadSearch)
    ListView listView;
    @BindView(R.id.et_roadSearch)
    EditText editText;
    @BindView(R.id.tv_roadSearch_btn)
    TextView tv_btn;

    List<RoadSearchEntity> list = new ArrayList<>();
    RoadSearchAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_road_search;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("路段查询");
        iv_back.setOnClickListener(this);
        tv_btn.setOnClickListener(this);

        adapter = new RoadSearchAdapter(list,mContext);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("Road",list.get(i).road);
                intent.putExtra("RoadId",list.get(i).ID);
                setResult(Constants.RESULTCODE_ROAD,intent);
                finish();
            }
        });
        loadData();
    }

    private void loadData() {
        RetrofitClient.createService(RoadSeachAPI.class)
                .httpRoadSearch(editText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseEntity<RoadSearchEntity[]>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: "+responeThrowable.toString());
                    }

                    @Override
                    public void onNext(BaseEntity<RoadSearchEntity[]> s) {
                        Log.i(TAG, "onNext: "+gson.toJson(s));
                        if (s.isState()) {
                            list.clear();
                            Collections.addAll(list, s.getRtn());
                            Log.i(TAG, "onNext: "+list.size());
                            adapter.notifyDataSetChanged();
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
            case R.id.tv_roadSearch_btn:
                if (editText.getText().toString().trim().equals("")) {
                    ToastUtil.show(mContext,"请输入关键字");
                }else {
                    loadData();
                }
                break;
        }
    }
}
