package com.ly723.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Description 获取当前日期、时间
 * @Author LiYang
 */
public class DateUtil {

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public static String getDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public static String getTime() {
        DateFormat dateFormat = SimpleDateFormat.getTimeInstance();
        return dateFormat.format(new Date());
    }

    public static String getDateAndTime(long time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date(time));
    }
}
