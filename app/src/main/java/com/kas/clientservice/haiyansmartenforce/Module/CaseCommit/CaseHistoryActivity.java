package com.kas.clientservice.haiyansmartenforce.Module.CaseCommit;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

public class CaseHistoryActivity extends BaseActivity {
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_back;
    @BindView(R.id.lv_caseHistory)
    ListView listView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_history;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
    protected void initResAndListener() {
        super.initResAndListener();

        tv_title.setText("我的上报");


    }
}
