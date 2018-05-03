package com.kas.clientservice.haiyansmartenforce.tcsf.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil{

    /**
     * 友好的转换显示与当前日期间隔 TODO
     * @return
     */
    public static String getFriendlyTime(String dateStr){
        return null;
    }

    /**
     * 时间格式化转化 TODO
      * @param date
     * @param parttern
     * @return
     */
    public static String formatDate(String date,String parttern){
        return null;
    }



//
public static String currentTime() {
        SimpleDateFormat simpleDateFormat;
       simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
