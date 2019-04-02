package smartenforce.util;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String Y = "yyyy";
    public static final String YM = "yyyy-MM";
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMDH = "yyyy-MM-dd HH";
    public static final String YMDHM = "yyyy-MM-dd HH:mm";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String MDHMS = "MM-dd HH:mm:ss";
    public static final String Y_CHINESE = "yyyy年";
    public static final String YM_CHINESE = "yyyy年MM月";
    public static final String YMD_CHINESE = "yyyy年MM月dd日";
    public static final String YMDH_CHINESE = "yyyy年MM月dd日HH时";
    public static final String YMDHM_CHINESE = "yyyy年MM月dd日HH时mm分";
    public static final String YMDHMS_CHINESE = "yyyy年MM月dd日HH时mm分ss秒";

    public final static long Time30M = 30 * 60 * 1000;
    public final static long Time1H = 60 * 60 * 1000;
    public final static long Time1M = 60 * 1000;
    public final static long Time1S =1000;
    public final static long DAY1 = 24 * 60 * 60 * 1000;

    public static String currentTime() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat(YMDHMS);
        return simpleDateFormat.format(new Date());
    }

  public static String getFormatDate(Date date , String pattern){
      try{
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
          return simpleDateFormat.format(date);
      }catch (Exception e){
          return currentTime();
      }
  }


    //获取距离当前日多少天日期格式
    public static String showDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(YMD);
        String result = format.format(today) + " 00:00";
        return result;
    }

    //巡查名字生成
    public static String createXCName() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return "XC" + simpleDateFormat.format(new Date());
    }

    public static Calendar str2Calendar(String dateStr, String pattern) {
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            date = sdf.parse(dateStr);
            calendar.setTime(date);
        } catch (Exception e) {
        } finally {
            return calendar;
        }

    }

    public static Calendar date2Calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
        } finally {
            return calendar;
        }

    }

    private static Calendar getCalendar(String timeStr) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YMDHMS);
        try {
            Date date = simpleDateFormat.parse(timeStr);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("erro", "getCalendar:" + timeStr);
        }
        return calendar;
    }

    private static long getTimmeLong(Calendar startCalendar, Calendar endCalendar) {
        if (startCalendar.get(Calendar.HOUR_OF_DAY) < 8) {
            startCalendar.set(Calendar.HOUR_OF_DAY, 8);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.MILLISECOND, 0);
        }
        if (startCalendar.get(Calendar.HOUR_OF_DAY) >= 20) {
            startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.get(Calendar.DAY_OF_MONTH) + 1);
            startCalendar.set(Calendar.HOUR_OF_DAY, 8);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.MILLISECOND, 0);
        }

        if (endCalendar.get(Calendar.HOUR_OF_DAY) < 8) {
            endCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.get(Calendar.DAY_OF_MONTH) - 1);
            endCalendar.set(Calendar.HOUR_OF_DAY, 20);
            endCalendar.set(Calendar.MINUTE, 0);
            endCalendar.set(Calendar.MILLISECOND, 0);
        }
        if (endCalendar.get(Calendar.HOUR_OF_DAY) >= 20) {
            endCalendar.set(Calendar.HOUR_OF_DAY, 20);
            endCalendar.set(Calendar.MINUTE, 0);
            endCalendar.set(Calendar.MILLISECOND, 0);
        }
        long len = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        //判断是否包含一整段免费停车时间
        if (len > 12 * Time1H && len < DAY1 && startCalendar.get(Calendar.HOUR_OF_DAY) < 20 && endCalendar.get(Calendar.HOUR_OF_DAY) >= 8) {
            len -= 12 * Time1H;
        }
        return len;
    }

    private static int cal(long len) {
        int money;
        if (len >= 4 * Time1H) {
            money = 18;
        } else if (len > Time1H) {
            money = ((int) Math.ceil((double) len / Time1H)) * 5 - 2;
        } else if (len > Time30M) {
            money = 3;
        } else {
            money = 0;
        }
        return money;
    }


    public static long calMoney(String endTimeStr, String startTimeStr) {
        Calendar startCalendar = getCalendar(startTimeStr);
        Calendar endCalendar = getCalendar(endTimeStr);
        long timeLong = getTimmeLong(startCalendar, endCalendar);
        int money = 0;
        if (timeLong >= DAY1) {
            int day = (int) (timeLong / DAY1);
            startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.get(Calendar.DAY_OF_MONTH) + day);
            timeLong = getTimmeLong(startCalendar, endCalendar);
            money += day * 18;
        }
        money += cal(timeLong);
        return money;

    }

    public static String getTimeLenth(String endTimeStr, String startTimeStr) {
        Calendar startCalendar = getCalendar(startTimeStr);
        Calendar endCalendar = getCalendar(endTimeStr);
        long len = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        long day = len / DAY1;
        long day_ = len % DAY1;

        long h = day_ / Time1H;
        long h_ = day_ % Time1H;

        long m = h_ / Time1M;
        long m_ = h_ % Time1M;

        long s = h_ / Time1S;
        return day + "天" + h + "小时" + m + "分钟" + s + "秒";
    }








}
