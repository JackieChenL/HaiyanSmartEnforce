package com.kas.clientservice.haiyansmartenforce.Module.CityCheck;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Entity.CityCheckListEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.ListViewFitParent;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class CityCheckSearchActivity extends BaseActivity implements View.OnClickListener,TimePickerDialog.TimePickerDialogInterface {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_citySearch_list_starttime)
    TextView tv_startTime;
    @BindView(R.id.tv_citySearch_list_endtime)
    TextView tv_endTime;
    @BindView(R.id.tv_city_search_list_add)
    TextView tv_add;
    @BindView(R.id.tv_city_search_list_query)
    TextView tv_query;
    @BindView(R.id.lv_city_search_list)
    ListView listView;

    TimePickerDialog timePickerDialog;
    private String startTime = "";
    private String endTime = "";
    CityCheckAdapter cityCheckAdapter;
    List<CityCheckListEntity.KSBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_check_search;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("市容督查");
        iv_back.setOnClickListener(this);
        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tv_query.setOnClickListener(this);

        timePickerDialog = new TimePickerDialog(mContext);
        cityCheckAdapter = new CityCheckAdapter(mContext,list);
        listView.setAdapter(cityCheckAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,CityCheckAddActivity.class);
                intent.putExtra("json",gson.toJson(list.get(i)));
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
            case R.id.tv_citySearch_list_starttime:
                choseStartTime();
                break;
            case R.id.tv_citySearch_list_endtime:
                choseEndTime();
                break;
            case R.id.tv_city_search_list_query:
                query();
                break;
            case R.id.tv_city_search_list_add:
                startActivity(new Intent(mContext, CityCheckAddActivity.class));
                break;
        }
    }

    private void query() {
        list.clear();
        showLoadingDialog();
        OkHttpUtils.post().url(RequestUrl.baseUrl_leader+"/mobile/SourceListForInspector.ashx")
                .addParams("UserID", UserSingleton.USERINFO.getName().UserID)
                .addParams("StartTime",startTime)
                .addParams("EndTime",endTime)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: "+e.toString());
                dismissLoadingDialog();
                showNetErrorToast();
            }

            @Override
            public void onResponse(String response, int id) {
                dismissLoadingDialog();
                Log.i(TAG, "onResponse: "+response);
                CityCheckListEntity cityCheckListEntity = gson.fromJson(response,CityCheckListEntity.class);
                if (cityCheckListEntity.getKS()!=null&&cityCheckListEntity.getKS().size()>0) {
                    list.addAll(cityCheckListEntity.getKS());
                    cityCheckAdapter.notifyDataSetChanged();
                    ListViewFitParent.setListViewHeightBasedOnChildren(listView);
                }else {
                    ToastUtils.showToast(mContext,"暂无数据");
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
