package com.kas.clientservice.haiyansmartenforce.Module.CaseCommit;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.CaseSearchEntity;
import com.kas.clientservice.haiyansmartenforce.Http.RequestUrl;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;
import com.kas.clientservice.haiyansmartenforce.Utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class CaseSearchActivity extends BaseActivity implements View.OnClickListener,TimePickerDialog.TimePickerDialogInterface{
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_caseSearch_code)
    EditText et_code;
    @BindView(R.id.tv_caseSearch_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_caseSearch_endTime)
    TextView tv_endTime;
    @BindView(R.id.tv_caseSearch_btn)
    TextView tv_btn;
    @BindView(R.id.lv_caseSearch)
    ListView listView;

    List<CaseSearchEntity> list = new ArrayList<>();
    CaseSearchAdapter caseSearchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_search;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("记录查询");
        iv_back.setOnClickListener(this);
        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_btn.setOnClickListener(this);

        tv_startTime.setText(TimeUtils.getFormedTime("yyyy-MM-dd")+" 00:00");
        tv_endTime.setText(TimeUtils.getFormedTime("yyyy-MM-dd HH:mm"));
        timePickerDialog = new TimePickerDialog(mContext);

        initAdapter();
    }

    private void initAdapter() {
        caseSearchAdapter = new CaseSearchAdapter(list,mContext);
        listView.setAdapter(caseSearchAdapter);
        caseSearchAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,CaseDetailActivity.class);
                intent.putExtra("entity",gson.toJson(list.get(i)));
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
            case R.id.tv_caseSearch_startTime:
                choseStartTime();
                break;
            case R.id.tv_caseSearch_endTime:
                choseEndTime();
                break;
            case R.id.tv_caseSearch_btn:
                submit();
                break;
        }
    }

    private void submit() {
        showLoadingDialog();

        Log.i(TAG, "submit: id="+UserSingleton.USERINFO.getPublicUsersID()+"  "+tv_startTime.getText().toString()+"  "+tv_endTime.getText().toString()+" code="+et_code.getText().toString());
        OkHttpUtils.post().url(RequestUrl.baseUrl)
                .addParams("optionName", "zmnsearchproject")
                .addParams("userid", UserSingleton.USERINFO.getPublicUsersID())
                .addParams("projcode", "")
                .addParams("starttime", tv_startTime.getText().toString())
                .addParams("endtime", tv_endTime.getText().toString())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.i(TAG, "onError: " + e.toString());
                dismissLoadingDialog();
            }

            @Override
            public void onResponse(final String response, int id) {
                Log.i(TAG, "onResponse: " + response);
                BaseEntity entity = gson.fromJson(response,BaseEntity.class);
                if (entity.isState()) {
                    CaseSearchEntity[] caseSearchEntity = gson.fromJson(gson.toJson(entity.getRtn()),CaseSearchEntity[].class);
                    list.clear();
                    Collections.addAll(list,caseSearchEntity);
                    Log.i(TAG, "onResponse: "+list.size());
                    caseSearchAdapter.notifyDataSetChanged();

                }else {
                    ToastUtils.showToast(mContext,"查询失败");
                }
                dismissLoadingDialog();
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
        }
        if (flag == 2) {
            tv_endTime.setText(year + "-" + months + "-" + day + " " + hour + ":" + minutes);
        }

    }

    @Override
    public void negativeListener() {

    }
}
