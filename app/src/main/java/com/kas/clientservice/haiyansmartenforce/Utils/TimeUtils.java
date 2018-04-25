package com.kas.clientservice.haiyansmartenforce.Utils;

import com.kas.clientservice.haiyansmartenforce.Entity.CurrentTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述：
 * 时间：2018-04-18
 * 公司：COMS
 */

public class TimeUtils {
    public static CurrentTime getTime(){
        Calendar c = Calendar.getInstance();//
        CurrentTime currentTime = new CurrentTime();
        currentTime.mYear = c.get(Calendar.YEAR); // 获取当前年份
        currentTime.mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        currentTime.mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        currentTime.mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        currentTime.mHour = c.get(Calendar.HOUR_OF_DAY);//时
        currentTime.mMinute = c.get(Calendar.MINUTE);//分

        return currentTime;
    }

    public static String getFormedTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getFormedTime(String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }



}
