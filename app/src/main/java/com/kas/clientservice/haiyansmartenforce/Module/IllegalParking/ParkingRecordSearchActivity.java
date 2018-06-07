package com.kas.clientservice.haiyansmartenforce.Module.IllegalParking;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.API.ParkingRecordSearchAPI;
import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.Base.BaseEntity;
import com.kas.clientservice.haiyansmartenforce.Entity.ParkingSearchEntity;
import com.kas.clientservice.haiyansmartenforce.Http.ExceptionHandle;
import com.kas.clientservice.haiyansmartenforce.Http.MySubscriber;
import com.kas.clientservice.haiyansmartenforce.Http.RetrofitClient;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.User.UserSingleton;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;
import com.kas.clientservice.haiyansmartenforce.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ParkingRecordSearchActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.TimePickerDialogInterface {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_parkingRecord_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_parkingRecord_endTime)
    TextView tv_endTime;
    @BindView(R.id.tv_parkingRecord_btn)
    TextView tv_btn;
    @BindView(R.id.tv_parkingRecord_position)
    EditText editText;
    @BindView(R.id.sp_province_search)
    Spinner sp_prov;
    @BindView(R.id.sp_ABC_search)
    Spinner sp_abc;
    @BindView(R.id.lv_illegalParkingSearch)
    ListView listView;
    @BindView(R.id.et_illegalparkingcommit_num)
    EditText et_num;

    String province = "";
    String abc = "";
    List<ParkingSearchEntity.BoardBean> list;
    IllegalParkingSearchAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_parking_record_search;
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

        tv_startTime.setText(TimeUtils.getFormedTime("yyyy-MM-dd")+" 00:00:00");
        tv_endTime.setText(TimeUtils.getFormedTime("yyyy-MM-dd HH:mm:ss"));
        timePickerDialog = new TimePickerDialog(mContext);

        sp_prov.setSelection(getResources().getStringArray(R.array.provinceName).length-1);
        sp_abc.setSelection(getResources().getStringArray(R.array.A2Z).length-1);
        sp_prov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                province = getResources().getStringArray(R.array.provinceName)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        sp_abc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                abc = getResources().getStringArray(R.array.A2Z)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        initList();
    }

    private void initList() {
        list = new ArrayList<>();
        adapter = new IllegalParkingSearchAdapter(list, mContext);
        listView.setAdapter(adapter);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_parkingRecord_startTime:
                choseStartTime();
                break;
            case R.id.tv_parkingRecord_endTime:
                choseEndTime();
                break;
            case R.id.tv_parkingRecord_btn:
                submit();
                break;
        }
    }

    private void submit() {
        RetrofitClient.createService(ParkingRecordSearchAPI.class)
                .httpParkingSearch(UserSingleton.USERINFO.getLawEnforcementOfficialsId(),
                        province + abc + et_num.getText().toString(),
                        tv_startTime.getText().toString(),
                        tv_endTime.getText().toString(),
                        editText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<BaseEntity<ParkingSearchEntity>>(mContext) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        Log.i(TAG, "onError: " + responeThrowable.toString());
                    }

                    @Override
                    public void onNext(BaseEntity<ParkingSearchEntity> stringBaseEntity) {
                        Log.i(TAG, "onNext: " + stringBaseEntity);
                        list.clear();
                        list.addAll(stringBaseEntity.getRtn().getBoard());
                        adapter.notifyDataSetChanged();
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
