package com.kas.clientservice.haiyansmartenforce.Module.Leader;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.LeaderAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.LeaderSearchListEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LeaderCheckCaseActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface {

    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_leader_check_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_leader_check_endTime)
    TextView tv_endTime;
    @BindView(R.id.et_leader_check_id)
    EditText tv_id;
    @BindView(R.id.et_leader_check_name)
    EditText tv_name;
    @BindView(R.id.et_leader_check_activity)
    EditText tv_activity;
    @BindView(R.id.et_leader_check_position)
    EditText et_position;
    @BindView(R.id.lv_leader_check)
    ListView listView;
    @BindView(R.id.tv_leader_check_search)
    TextView tv_search;

    String startTime = "";
    String endTime = "";
    List<LeaderSearchListEntity.KSBean> list;
    Case_Examine_Adapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_leader_check_case;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("案件审批");
        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        timePickerDialog = new TimePickerDialog(mContext);

        initAdapter();
    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter =  new Case_Examine_Adapter(mContext,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,LeaderCaseSearchDetailActivity.class);
                intent.putExtra("caseId",gson.toJson(list.get(i).getCaseID()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_leader_check_startTime:
                choseStartTime();
                break;
            case R.id.tv_leader_check_endTime:
                choseEndTime();
                break;
            case R.id.tv_leader_check_search:
                search();
                break;
        }

    }

    private void search() {
        Log.i(TAG, "search: "+UserSingleton.USERINFO.getName().UserID);
        RetrofitClient.createService(LeaderAPI.class, RequestUrl.baseUrl_leader)
                .httpGetCaseDealList(UserSingleton.USERINFO.getName().UserID,
                        startTime,
                        endTime,
                        tv_name.getText().toString()+"",
                        et_position.getText().toString()+"",
                        tv_id.getText().toString()+"",
                        tv_activity.getText().toString()+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<String>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: "+responeThrowable.toString());
                        showNetErrorToast();
                    }

                    @Override
                    public void onNext(String baseEntity) {
                        Log.i(TAG, "onNext: "+baseEntity);
                        LeaderSearchListEntity entity = gson.fromJson(baseEntity,LeaderSearchListEntity.class);
                        if (entity.getKS()!=null) {
                            if (entity.getKS().size()==0) {
                                ToastUtils.showToast(mContext,"暂无待办案件");
                            }
                            list.clear();
                            list.addAll(entity.getKS());
                            adapter.notifyDataSetChanged();
                            ListViewFitParent.setListViewHeightBasedOnChildren(listView);
                        }else {
                            showNetErrorToast();
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

    TimePickerDialog timePickerDialog;

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
