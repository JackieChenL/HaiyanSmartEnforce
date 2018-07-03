package smartenforce.projectutil;

import android.content.Context;
import android.graphics.Color;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import smartenforce.util.DateUtil;


public class TimePickerUtil {

//    private static TimePickerView YMDHMTimePicker=null;
//    private static TimePickerView YMDTimePicker=null;

    public static TimePickerView getYMDHMTimePicker(Context context) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 1, 0, 1, 0, 0);
        Calendar endDate = DateUtil.date2Calendar(new Date());
        TimePickerView YMDHMTimePicker = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
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
                .setLineSpacingMultiplier(1.5f)
                .setRangDate(startDate, endDate)
                .setDate(endDate)
                .build();
        return YMDHMTimePicker;
    }


    public static TimePickerView getYMDTimePicker(Context context, final TextWatcher watcher) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 1, 0, 1, 0, 0);
        Calendar endDate = DateUtil.date2Calendar(new Date());
        TimePickerView YMDTimePicker = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (v != null && v instanceof TextView) {
                    ((TextView) v).setText(DateUtil.getFormatDate(date, DateUtil.YMD));
                    if (watcher != null) {
                        watcher.afterTextChanged(null);
                    }
                }

            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(false)
                .setContentSize(22)
                .setSubmitColor(Color.parseColor("#2fb2e7"))
                .setCancelColor(Color.parseColor("#2fb2e7"))
                .setLineSpacingMultiplier(1.5f)
                .setRangDate(startDate, endDate)
                .setDate(endDate)
                .build();
        return YMDTimePicker;
    }

    public static TimePickerView getYMDHTimePicker(Context context, final TimePickerView.OnTimeSelectListener onTimeSelectListener) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 1, 0, 1, 0, 0);
        Calendar endDate = DateUtil.date2Calendar(new Date());
        TimePickerView timePickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (onTimeSelectListener != null) {
                    onTimeSelectListener.onTimeSelect(date, v);
                }

            }
        })
                .setType(new boolean[]{true, true, true, true, false, false})
                .setLabel("年", "月", "日", "时", "", "")
                .isCenterLabel(false)
                .setContentSize(22)
                .setSubmitColor(Color.parseColor("#2fb2e7"))
                .setCancelColor(Color.parseColor("#2fb2e7"))
                .setLineSpacingMultiplier(1.5f)
                .setRangDate(startDate, endDate)
                .setDate(endDate)
                .build();
        return timePickerView;
    }


    public static TimePickerView getYMTimePicker(Context context, final TimePickerView.OnTimeSelectListener onTimeSelectListener) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 1, 0, 1, 0, 0);
        Calendar endDate = DateUtil.date2Calendar(new Date());
        TimePickerView timePickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                    if (onTimeSelectListener != null) {
                        onTimeSelectListener.onTimeSelect(date, v);
                    }
            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("年", "月", "", "", "", "")
                .isCenterLabel(false)
                .setContentSize(22)
                .setSubmitColor(Color.parseColor("#2fb2e7"))
                .setCancelColor(Color.parseColor("#2fb2e7"))
                .setLineSpacingMultiplier(1.5f)
                .setRangDate(startDate, endDate)
                .setDate(endDate)
                .build();
        return timePickerView;
    }

    public static TimePickerView getYMDTimePicker(Context context) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - 1, 0, 1, 0, 0);
        Calendar endDate = DateUtil.str2Calendar(DateUtil.currentTime(), DateUtil.YMDHM);
        TimePickerView timePickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (v != null && v instanceof TextView) {
                    ((TextView) v).setText(DateUtil.getFormatDate(date,DateUtil.YMD));
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

    public static TimePickerView getTimePicker(Context context, Calendar startDate, Calendar endDate, final String showLable, final TimePickerView.OnTimeSelectListener onTimeSelectListener) {
        TimePickerView.Builder builder = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (v != null && v instanceof TextView) {
                    ((TextView) v).setText(DateUtil.getFormatDate(date, showLable));
                }
                if (onTimeSelectListener != null) {
                    onTimeSelectListener.onTimeSelect(date, v);
                }
            }
        })
                .isCenterLabel(false)
                .setContentSize(22)
                .setSubmitColor(Color.parseColor("#2fb2e7"))
                .setCancelColor(Color.parseColor("#2fb2e7"))
                .setLineSpacingMultiplier(1.5f)
                .setRangDate(startDate, endDate)
                .setDate(endDate);
        if (showLable.equals(DateUtil.Y) || showLable.equals(DateUtil.Y_CHINESE)) {
            builder.setType(new boolean[]{true, false, false, false, false, false});
            builder.setLabel("年", "", "", "", "", "");
        } else if (showLable.equals(DateUtil.YM) || showLable.equals(DateUtil.YM_CHINESE)) {
            builder.setType(new boolean[]{true, true, false, false, false, false});
            builder.setLabel("年", "月", "", "", "", "");
        } else if (showLable.equals(DateUtil.YMD) || showLable.equals(DateUtil.YMD_CHINESE)) {
            builder.setType(new boolean[]{true, true, true, false, false, false});
            builder.setLabel("年", "月", "日", "", "", "");
        } else if (showLable.equals(DateUtil.YMDH) || showLable.equals(DateUtil.YMDH_CHINESE)) {
            builder.setType(new boolean[]{true, true, true, true, false, false});
            builder.setLabel("年", "月", "日", "时", "", "");
        } else if (showLable.equals(DateUtil.YMDHM) || showLable.equals(DateUtil.YMDHM_CHINESE)) {
            builder.setType(new boolean[]{true, true, true, true, true, false});
            builder.setLabel("年", "月", "日", "时", "分", "");
        } else if (showLable.equals(DateUtil.YMDHMS) || showLable.equals(DateUtil.YMDHMS_CHINESE)) {
            builder.setType(new boolean[]{true, true, true, true, true, true});
            builder.setLabel("年", "月", "日", "时", "分", "秒");
        } else {

        }

        return builder.build();
    }


    /**
     *
     * @param context
     * @param maxQueryYear 最多查询年数
     * @param showLable 查询返回时间格式
     * @param onTimeSelectListener 监听时间选定事件
     * @return
     */
    public static TimePickerView getDefaultQueryTimePickerView(Context context,int maxQueryYear, String showLable, TimePickerView.OnTimeSelectListener onTimeSelectListener) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR) - maxQueryYear, 0, 1, 0, 0);
        Calendar endDate = DateUtil.date2Calendar(new Date());
        return getTimePicker(context,startDate,endDate,showLable,onTimeSelectListener);
    }
}
