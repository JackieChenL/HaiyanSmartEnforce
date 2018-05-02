package com.kas.clientservice.haiyansmartenforce.Module.History;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kas.clientservice.haiyansmartenforce.Base.BaseActivity;
import com.kas.clientservice.haiyansmartenforce.R;

import butterknife.BindView;

public class HistoryDetails extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_heaer_back)
    ImageView iv_heaer_back;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_details;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initResAndListener(){
        super.initResAndListener();
        iv_heaer_back.setOnClickListener(this);
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
