package com.kas.clientservice.haiyansmartenforce.tcsf.util;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil{

    public final static long Time30M=30*60*1000;
    public final static long Time1H=60*60*1000;
    public final static long Time1M=60*1000;
    public final static long Time1S=60*60*1000;
    public final static long DAY1=24*60*60*1000;

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

   private static Calendar getCalendar(String timeStr){
       Calendar calendar=Calendar.getInstance();
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       try {
           Date date=simpleDateFormat.parse(timeStr);
           calendar.setTime(date);
       } catch (ParseException e) {
           e.printStackTrace();
           Log.e("erro","getCalendar:"+timeStr);
       }
       return calendar;
   }


   private static long getTimmeLong(Calendar startCalendar,Calendar endCalendar){
       if (startCalendar.get(Calendar.HOUR_OF_DAY)<8){
           startCalendar.set(Calendar.HOUR_OF_DAY,8);
           startCalendar.set(Calendar.MINUTE,0);
           startCalendar.set(Calendar.MILLISECOND,0);
       }
       if (startCalendar.get(Calendar.HOUR_OF_DAY)>=20){
           startCalendar.set(Calendar.DAY_OF_MONTH,startCalendar.get(Calendar.DAY_OF_MONTH)+1);
           startCalendar.set(Calendar.HOUR_OF_DAY,8);
           startCalendar.set(Calendar.MINUTE,0);
           startCalendar.set(Calendar.MILLISECOND,0);
       }

       if (endCalendar.get(Calendar.HOUR_OF_DAY)<8){
           endCalendar.set(Calendar.DAY_OF_MONTH,startCalendar.get(Calendar.DAY_OF_MONTH)-1);
           endCalendar.set(Calendar.HOUR_OF_DAY,20);
           endCalendar.set(Calendar.MINUTE,0);
           endCalendar.set(Calendar.MILLISECOND,0);
       }
       if (endCalendar.get(Calendar.HOUR_OF_DAY)>=20){
           endCalendar.set(Calendar.HOUR_OF_DAY,20);
           endCalendar.set(Calendar.MINUTE,0);
           endCalendar.set(Calendar.MILLISECOND,0);
       }
       long len=endCalendar.getTimeInMillis()-startCalendar.getTimeInMillis();
       //判断是否包含一整段免费停车时间
       if(len>12*Time1H&&len<DAY1&&startCalendar.get(Calendar.HOUR_OF_DAY)<20&&endCalendar.get(Calendar.HOUR_OF_DAY)>=8){
            len-=12*Time1H;
       }
       return len;
   }

   private static int cal(long len){
     int time_len;
        if(len>=4*Time1H){
           time_len=18;
       }else if(len>Time1H){
           time_len=((int)Math.ceil((double) len/Time1H))*5-2;
       }else if(len>Time30M){
           time_len=3;
       }else{
           time_len=0;
       }
         return time_len;
   }



    public static long  calMoney(String endTimeStr,String startTimeStr) {
        Calendar startCalendar=getCalendar(startTimeStr);
        Calendar endCalendar=getCalendar(endTimeStr);
        long timeLong=getTimmeLong(startCalendar,endCalendar);
        int money=0;
        if (timeLong>=DAY1){
            int day=(int)(timeLong/DAY1);
            startCalendar.set(Calendar.DAY_OF_MONTH,startCalendar.get(Calendar.DAY_OF_MONTH)+day);
            timeLong=getTimmeLong(startCalendar,endCalendar);
            money+=day*18;
        }
        money+=cal(timeLong);
           return  money;

    }

    public static String  getTimeLenth(String endTimeStr,String startTimeStr) {
        Calendar startCalendar=getCalendar(startTimeStr);
        Calendar endCalendar=getCalendar(endTimeStr);
        long len=endCalendar.getTimeInMillis()-startCalendar.getTimeInMillis();
        long day=len/DAY1;
        long day_=len%DAY1;

        long h=day_/Time1H;
        long h_=day_%Time1H;

        long m=h_/Time1M;
        long m_=h_%Time1M;

        long s=h_/Time1S;
           return day+"天"+h+"小时"+m+"分钟"+s+"秒";
    }



}
