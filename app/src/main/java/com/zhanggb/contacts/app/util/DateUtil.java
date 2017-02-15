package com.zhanggb.contacts.app.util;

import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhangwentao on 15/9/22.
 * Description : 时间
 * Version :2.0
 */
public class DateUtil {
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;

    public static String fromTime(int time) {
        if (time < ONE_MINUTE) {
            return time + "秒";
        } else if (time == ONE_MINUTE) {
            return "1 分钟";
        } else if (time > ONE_MINUTE && time < ONE_HOUR) {
            return time / ONE_MINUTE + "分钟" + time % ONE_MINUTE + "秒";
        } else if (time > ONE_HOUR && time < ONE_DAY) {
            return time / ONE_HOUR + "小时" + (time % ONE_HOUR) / ONE_MINUTE + "分钟" + (time % ONE_HOUR) % ONE_MINUTE + "秒";
        } else if (time == ONE_HOUR) {
            return "1小时";
        }
        return "";
    }

    /**
     * 获取现在时间
     *
     * @return
     */
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = simpleDateFormat.format(date);
        return currentTime;
    }

    public static String getTime(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String currentTime = simpleDateFormat.format(date);
        return currentTime;
    }

    public static String getTime(String format, long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String currentTime = simpleDateFormat.format(date);
        return currentTime;
    }

    public static String fromTime(String s) {
        long time = Long.valueOf(s);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(time);
        return date;
    }

    public static String createTime(long time) {
        StringBuilder passTime = new StringBuilder();
        long min = 0;
        long sec = 0;
        if (time >= 60) {
            min = time / 60;
        }
        if (min >= 10) {
            passTime.append(min + "'");
        } else {
            passTime.append("0" + min + "'");
        }
        sec = time % 60;
        if (sec >= 10) {
            passTime.append(sec + "''");
        } else {
            passTime.append("0" + sec + "''");
        }
        return passTime.toString();
    }


    public static String getMediaTime(int timeLong) {
        int minute = timeLong / 60;
        int second = timeLong % 60;
        minute %= 60;
        String time = String.format("%02d:%02d", minute, second);
        return time;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(Date date) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsYesterday(Date date) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

}
