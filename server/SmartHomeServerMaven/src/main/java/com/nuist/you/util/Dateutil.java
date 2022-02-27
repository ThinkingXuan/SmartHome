package com.nuist.you.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dateutil {


    public static String DateStringFormat(Date date, String format) {
        if (date == null) {
            return "";
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getNowHourDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(SocketBase.TIME_FORMAT_STR_HOUR);
        return sdf.format(date);
    }

    public static String getTodayDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(SocketBase.TIME_FORMAT_STR_DAY);
        return sdf.format(date);
    }

    public static String getYesterDayDate() {

        SimpleDateFormat sdf = new SimpleDateFormat(SocketBase.TIME_FORMAT_STR_DAY);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        return sdf.format(calendar.getTime());
    }

    public static String getMonthDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(SocketBase.TIME_FORMAT_STR_MONTH);
        return sdf.format(date);
    }

    public static String getYearDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(SocketBase.TIME_FORMAT_STR_YEAR);
        return sdf.format(date);
    }


}
