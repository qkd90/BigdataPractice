package com.data.data.hmly.service.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by guoshijie on 2015/12/10.
 */
public class DateUtils extends com.zuipin.util.DateUtils {

    private static StringBuilder builder = new StringBuilder();

    public static Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        builder.setLength(0);
        builder.append(calendar.get(Calendar.HOUR))
            .append(":")
            .append(calendar.get(Calendar.MINUTE));
        return builder.toString();
    }

    public static String getTimeDuration(Long time) {
        return getTimeDuration(new Date(time));
    }

    public static String getTimeDuration(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        builder.setLength(0);
        builder.append(calendar.get(Calendar.HOUR))
            .append("h")
            .append(Calendar.MINUTE)
            .append("m");
        return builder.toString();
    }

    public static String getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        builder.setLength(0);
        builder.append(calendar.get(Calendar.YEAR))
            .append("-")
            .append(calendar.get(Calendar.MONTH) + 1)
            .append("-")
            .append(calendar.get(Calendar.DAY_OF_MONTH));
        return builder.toString();
    }

    public static String parsePrettyTimeFromMinute(int minute) {
        builder.setLength(0);
        builder.append(minute / 60).append("小时");
        builder.append(minute % 60).append("分");
        return builder.toString();
    }

}
