package com.kas.clientservice.haiyansmartenforce.Module.CaseCommit;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kas.clientservice.haiyansmartenforce.API.CaseTypeAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.CaseTypeEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CaseTypeActivity extends BaseActivity {
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.lv_caseType)
    ListView listView;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.tv_caseType_back)
    TextView tv_back;
    @BindView(R.id.tv_caseType_currentType)
    TextView tv_currentType;

    List<CaseTypeEntity> list;
    CaseTypeAdapter caseTypeAdapter;

    boolean isBigClass = true;
    String bigClass = "";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_type;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText("大小类选择");
        tv_back.setVisibility(View.GONE);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBigClass = true;
                loadBigClassData();
                tv_back.setVisibility(View.GONE);
                tv_currentType.setText("请选择大类");
            }
        });

        initAdapter();
        loadBigClassData();
    }

    private void initAdapter() {
        list = new ArrayList<>();
        caseTypeAdapter = new CaseTypeAdapter(list, mContext);
        listView.setAdapter(caseTypeAdapter);
        caseTypeAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isBigClass) {

                    isBigClass = false;
                    tv_back.setVisibility(View.VISIBLE);
                    tv_currentType.setText("当前大类："+list.get(i).getName());
                    bigClass = list.get(i).getCode();
                    Log.i(TAG, "onItemClick: "+list.get(i).getCode());
                    loadSmallClassData(list.get(i).getCode());
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("TypeName",list.get(i).getName());
                    intent.putExtra("BigClass",bigClass);
                    intent.putExtra("SmallClass",list.get(i).getName());
//                    intent.putExtra("TypeCode",list.get(i).getCode());
                    Log.i(TAG, "onItemClick: "+list.get(i).getName());
                    setResult(Constants.RESULTCODE_CASE_TYPE,intent);
                    finish();
                }
            }
        });
    }

    private void loadBigClassData() {
        RetrofitClient.createService(CaseTypeAPI.class,"http://117.149.146.131:86/")
                .httpGetCaseType(RequestUrl.getBig, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseEntity<List<CaseTypeEntity>>>(mContext) {

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                    }

                    @Override
                    public void onNext(BaseEntity<List<CaseTypeEntity>> stringBaseEntity) {
                        Log.i(TAG, "onNext: " + new Gson().toJson(stringBaseEntity));
                        list.clear();
                        list.addAll(stringBaseEntity.getRtn());
                        caseTypeAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadSmallClassData(String type) {
        RetrofitClient.createService(CaseTypeAPI.class,"http://117.149.146.131:86/")
                .httpGetCaseTypeSub(RequestUrl.getSmall, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseEntity<List<CaseTypeEntity>>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                    }

                    @Override
                    public void onNext(BaseEntity<List<CaseTypeEntity>> stringBaseEntity) {
                        Log.i(TAG, "onNext: " + new Gson().toJson(stringBaseEntity));
                        list.clear();
                        list.addAll(stringBaseEntity.getRtn());
                        caseTypeAdapter.notifyDataSetChanged();
                    }
                });
    }

}
