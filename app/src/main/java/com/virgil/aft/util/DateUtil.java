package com.virgil.aft.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.text.TextUtils;

/**
 * Created by liuwujing on 15/2/3.
 */
public class DateUtil {
    public static void main(String[] args){
        formatDateTimeString("20140728114509");
    }

    public static String formatDateTimeString(String timeStr) {
        LogUtil.i("datetime before "+timeStr);
        if (timeStr == null) {
            return null;
        }
        if (timeStr.length() < 14) {
            return timeStr;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm月dd日 HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String str = (dateFormat).format(getCalendarByDateTimeStr(timeStr).getTime());
        return str;
    }
    public static Calendar getCalendarByDateTimeStr(String dateStr)
    {
        if (TextUtils.isEmpty(dateStr) || dateStr.length() < 8) {
            return null;
        }
        while (dateStr.length() < 14)
        {
            dateStr += "0";
        }
        Calendar calendar = getLocalCalendar();
        int year = StringUtil.toInt(dateStr.substring(0, 4));
        int month = StringUtil.toInt(dateStr.substring(4, 6));
        int day = StringUtil.toInt(dateStr.substring(6, 8));
        int hour = StringUtil.toInt(dateStr.substring(8, 10));
        int min = StringUtil.toInt(dateStr.substring(10, 12));
        int second = 0;
        if (dateStr.length() >= 14) {
            second = StringUtil.toInt(dateStr.substring(12, 14));
        }
        calendar.set(year, month - 1, day, hour, min, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }
    public static Calendar getLocalCalendar()
    {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        return localCalendar;
    }
}
