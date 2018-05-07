package com.kas.clientservice.haiyansmartenforce.Module.History;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

/**
 * Created by 12976 on 2018/4/27.
 */

public class HistoryActivity extends BaseActivity implements View.OnClickListener
{
    @BindView(R.id.lv_history)
    ListView lv_history;
    @BindView(R.id.iv_heaer_back)
    ImageView iv_heaer_back;
    @BindView(R.id.tv_header_title)
    TextView tv_title;
    @Override
    protected int getLayoutId() {
        return R.layout.acvtivity_history;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initResAndListener(){
        super.initResAndListener();
//        lv_history.setOnClickListener(this);
        iv_heaer_back.setOnClickListener(this);
        tv_title.setText("历史记录");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_heaer_back:
                finish();
                break;
        }
    }

}
