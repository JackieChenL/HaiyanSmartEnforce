package com.kas.clientservice.haiyansmartenforce.tcsf.util;

import android.content.Context;
import android.graphics.Color;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;


public class TimePickerUtil {

    public static TimePickerView getYMDTimePicker(Context context) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 1, 0, 1, 0, 0);
        Calendar endDate = DateUtil.str2Calendar(DateUtil.currentTime(), DateUtil.YMDHM);
        TimePickerView timePickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (v != null && v instanceof TextView) {
                    ((TextView) v).setText(DateUtil.getFormatDate(date, DateUtil.YMD));
                }

            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setContentSize(22)
                .setSubmitColor(Color.parseColor("#2fb2e7"))
                .setCancelColor(Color.parseColor("#2fb2e7"))
                .setLineSpacingMultiplier(1.6f)
                .setRangDate(startDate, endDate)
                .build();
        return timePickerView;
    }


}
