package com.kas.clientservice.haiyansmartenforce.Module.SmartEnforce;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;
import com.kas.clientservice.haiyansmartenforce.Utils.TimePickerDialog;

import butterknife.BindView;

public class SearchDiscoverActivity extends BaseActivity implements View.OnClickListener,TimePickerDialog.TimePickerDialogInterface {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.tv_searchDiscover_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_searchDiscover_endTime)
    TextView tv_endTime;
    @BindView(R.id.et_searchDiscover_content)
    EditText et_content;
    @BindView(R.id.lv_searchDiscover)
    ListView listView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_discover;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("巡查发现");
        iv_back.setOnClickListener(this);

        timePickerDialog = new TimePickerDialog(mContext);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_heaer_back:
                finish();
                break;
            case R.id.tv_searchDiscover_status:
                showPPW();
                break;
            case R.id.tv_searchDiscover_startTime:
                choseStartTime();
                break;
            case R.id.tv_searchDiscover_endTime:
                choseEndTime();
                break;
        }
    }

    private void showPPW() {

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
