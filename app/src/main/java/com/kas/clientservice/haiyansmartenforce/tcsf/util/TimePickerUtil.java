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

    public static TimePickerView getYMDHMTimePicker(Context context) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 1, 0, 1, 0, 0);
        Calendar endDate = DateUtil.str2Calendar(DateUtil.currentTime(), DateUtil.YMDHM);
        TimePickerView timePickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (v != null && v instanceof TextView) {
                    ((TextView) v).setText(DateUtil.getFormatDate(date, DateUtil.YMDHM));
                }

            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "")
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
