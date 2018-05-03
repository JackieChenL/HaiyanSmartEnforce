package com.kas.clientservice.haiyansmartenforce.tcsf.impl;

import android.view.View;

import java.util.Calendar;

/**
 * 快速点击事件特殊处理
 */
public class NoFastClickLisener implements View.OnClickListener {
    private static final long interval = 1000l;
    private long lastClick = 0;

    @Override
    public void onClick(View v) {
        long currentMillis = Calendar.getInstance().getTimeInMillis();
        if (currentMillis - lastClick > interval) {
            lastClick = currentMillis;
            onClick(v);
        }

    }
}
