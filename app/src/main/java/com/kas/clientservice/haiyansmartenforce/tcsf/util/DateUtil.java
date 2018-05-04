package com.kas.clientservice.haiyansmartenforce.tcsf.util;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil{

    public final static long Time30M=30*60*1000;
    public final static long Time1H=60*60*1000;

    public static String currentTime() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }


    public static String getCost(String endTimeStr,String startTimeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStr="";
        try {
            long endTime= simpleDateFormat.parse(endTimeStr).getTime();
            long startTime= simpleDateFormat.parse(startTimeStr).getTime();
            long time=endTime-startTime;
            if (time<=Time30M){
                retStr="0";
            }else if (time>Time30M&&time<=Time1H){
                retStr="3";
            }else{
//                Log.e("TTT",time+","+ (int)Math.ceil(time)+","+(int)Math.ceil(time/Time1H));
                retStr= String.valueOf(((int)Math.ceil((double) time/Time1H))*5);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.e("计费出错：","startTimeStr:"+startTimeStr+",endTimeStr"+endTimeStr);
        }

        return retStr+"元";
    }



}
