package com.kas.clientservice.haiyansmartenforce.tcsf.impl;

import android.view.View;

import java.util.Calendar;

/**
 * 快速点击事件特殊处理
 */
public abstract class NoFastClickLisener implements View.OnClickListener {
    private static final long interval = 2000l;
    private long lastClick = 0;

    @Override
    public void onClick(View v) {
        long currentMillis = Calendar.getInstance().getTimeInMillis();
        if (currentMillis - lastClick > interval) {
            lastClick = currentMillis;
            onNoFastClickListener(v);
        }else{

        }

    }
    public abstract void onNoFastClickListener(View v);
}
