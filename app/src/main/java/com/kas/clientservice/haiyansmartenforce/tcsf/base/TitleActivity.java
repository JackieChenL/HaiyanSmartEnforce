package com.kas.clientservice.haiyansmartenforce.tcsf.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kas.clientservice.haiyansmartenforce.R;

public abstract class TitleActivity extends BaseActivity {
    protected ImageView iv_heaer_back;
    protected TextView tv_header_title;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        findViewBy();
        iv_heaer_back = (ImageView) findViewById(R.id.iv_heaer_back);
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        iv_heaer_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleTxt();
        initData();
    }

    protected abstract void findViewBy();

    protected abstract void setTitleTxt();

    protected abstract void initData();


}
