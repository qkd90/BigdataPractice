package com.zuipin.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtils {
    public final static String[] SUPPORTED_FORMATS = {"yyyy-MM-dd", "MM/dd/yyyy HH:mm:ss", "MM-dd-yyyy HH:mm:ss", "MM-dd-yyyy hh:mm:ss a", "MM-dd-yyyy", "MM-dd-yy",
            "MM/dd/yyyy", "MM.dd.yyyy", "MM.dd.yy", "MMddyyyy", "MMddyy"};
    public static SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat FORMAT_STOCKPLACE_DATE = new SimpleDateFormat("MM月dd日");
    public static long nd = 1000 * 24 * 60 * 60;                            // 一天的毫秒数
    public static long nh = 1000 * 60 * 60;                                // 一小时的毫秒数
    public static long nm = 1000 * 60;                                    // 一分钟的毫秒数
    public static long ns = 1000;                                        // 一秒钟的毫秒数

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return date;
    }
    public static Date getStartDay(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        date = calendar.getTime();
        return date;
    }
    public static Date getEndDay(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        date = calendar.getTime();
        return date;
    }

    public static String getWeek(int index) {
        String str[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        return str[index];
    }


    public static Long getDateDiffLong(Date d1, Date d2) {
        Long diff = d1.getTime() - d2.getTime();
        return diff;
    }

    public static String getDateDiffString(Date d1, Date d2) {
        Long diff = d1.getTime() - d2.getTime();
        long day = diff / nd;
        long hour = diff % nd / nh;
        long minute = diff % nd % nh / nm;
        long second = diff % nd % nh % nm / ns;
        return "" + day + "天" + hour + "时" + minute + "分" + second + "秒";
    }

    public static Long getDateDiffHour(Date d1, Date d2) {
        Long diff = d1.getTime() - d2.getTime();
        long hour = diff / nh;
        return hour;
    }

    public static Long getDateDiffMin(Date d1, Date d2) {
        Long diff = d1.getTime() - d2.getTime();
        long min = diff % nd % nh / nm;
        return min;
    }

    public static Date date(int year, int month, int day) {
        return date(year, month, day, 0, 0, 0);
    }

    public static Date date(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date add(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, value);
        return calendar.getTime();
    }

    public static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    public static int age(Date dateOfBirth, Date targetDate) {
        Calendar birth = calendar(dateOfBirth);
        Calendar target = calendar(targetDate);
        int age = target.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if ((birth.get(Calendar.MONTH) > target.get(Calendar.MONTH))
                || (birth.get(Calendar.MONTH) == target.get(Calendar.MONTH) && birth.get(Calendar.DAY_OF_MONTH) > target.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatShortDate(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static Date parseShortTime(String dateTime) throws ParseException {
        return SHORT_DATE_FORMAT.parse(dateTime);
    }

    public static Date parse(String dateTime, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateTime);
    }

    public static String formatDate(Date date) {
        return FULL_DATE_FORMAT.format(date);
    }

    public static Date toFullDate(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return FULL_DATE_FORMAT.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date toDate(String string) {
        if (string == null) {
            return null;
        }
        string = string.trim();
        SimpleDateFormat sdf = null;
        Date one = null;
        for (String format : SUPPORTED_FORMATS) {
            sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            try {
                one = sdf.parse(string);
                return one;
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static String getLastMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONTH); // 上个月月�?
        if (month == 0) {
            year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
            year = cal.get(Calendar.YEAR);
        }
        String endDay = year + "-" + month + "-01";
        return endDay;
    }

    public static String getLastMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int year = 0;
        int month = cal.get(Calendar.MONTH); // 上个月月�?
        // int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数
        int day = getLastMonthDayLenth();
        if (month == 0) {
            year = cal.get(Calendar.YEAR) - 1;
            month = 12;
        } else {
            year = cal.get(Calendar.YEAR);
        }
        String endDay = year + "-" + month + "-" + day;
        return endDay;
    }

    public static int getLastMonthDayLenth() {
        Calendar calendar = new GregorianCalendar();
        Calendar calendar1 = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, 1);
        return calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    // public static void main(String[] args) {
    // System.out.println(DateUtils.getLastMonthDayLenth());
    // }

    public static String nDaysAfterOneDateString(String basicDate, int n) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = null;
        try {
            tmpDate = df.parse(basicDate);
        } catch (Exception e) {
            // 日期型字符串格式错误
        }
        long nDay = (tmpDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n) * (24 * 60 * 60 * 1000);
        tmpDate.setTime(nDay);

        return df.format(tmpDate);
    }

    public static Integer DayDiff(Date firstDay, Date secondDay) {
        Long day = 60L * 60 * 24;
        Long diff = (firstDay.getTime() - secondDay.getTime()) / 1000;
        Long diffDay = diff / day;
        return diffDay.intValue();
    }

	/*
	 * public static void main(String[] args) { System.out.println(DateUtils.DayDiff(DateUtils.toDate("2012-03-09"), DateUtils.toDate("2011-11-25"))); }
	 */

    /**
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2012-8-8
     * @功能说明：获得系统日期
     */
    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * @return
     * @author:lihh
     * @email:lhhui@xiangyu.cn
     * @创建日期:2012-11-15
     * @功能说明：获取系统当前时间
     */
    public static String getCurrentSysDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = format.format(new Date(System.currentTimeMillis()));
        return currentDate;
    }

    /**
     * @return
     * @author:lihh
     * @email:lhhui@xiangyu.cn
     * @创建日期:2012-11-15
     * @功能说明：获取系统当前时间
     */
    public static String getSysShortDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = format.format(new Date(System.currentTimeMillis()));
        return currentDate;
    }

    public static Date add(int CALENDARFIELD, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(CALENDARFIELD, amount);
        return calendar.getTime();
    }

    /**
     * 获取时间开销
     *
     * @param t1
     * @param t2
     * @return
     */
    public static long getTimeSpent(long t1, long t2) {

        long t3 = t2 - t1;
        long seconds = t3 / (1000);
        return seconds;

    }

    /**
     * @param dateTime
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2012-10-9
     * @功能说明：获得某日固定时间
     */
    public static Date getFixedTime(Long dateTime) {
        String date = formatDate(getDate());
        return new Date(toDate(date).getTime() + dateTime);
    }

    /**
     * @param date
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2012-9-24
     * @功能说明：格式化
     */
    public static String formatStockPlaceDate(Date date) {
        return FORMAT_STOCKPLACE_DATE.format(date);
    }

    /**
     * @param date
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2012-9-24
     * @功能说明：计算时间差
     */
    public static String getHourMin(Long date) {
        long hour = date % nd / nh;// 计算差多少小时
        long min = date % nd % nh / nm;// 计算差多少分钟
        return hour + "小时" + min + "分钟";
    }

    /**
     * @param sysDate
     * @param date
     * @param addTime
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2012-9-24
     * @功能说明：获得两个日期的时间差
     */
    public static Long getTimePoor(Date sysDate, Date date, Long addTime) {
        return getDateForZero(date, addTime).getTime() - sysDate.getTime();
    }

    /**
     * @param date
     * @param addTime
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2012-9-24
     * @功能说明：获得格式化后的凌晨0.00
     */
    public static Date getDateForZero(Date date, Long addTime) {
        return toDate(formatDate((new Date(date.getTime() + addTime))));
    }

    public static Date addStockPlaceTime(Date date, Long addDate) {
        return new Date(date.getTime() + addDate);
    }

    @SuppressWarnings("unchecked")
    public static List getTimeList(String date1, String date2) throws ParseException {
        long dayTime = 0l;
        List list = new ArrayList();
        Date beginDate = SHORT_DATE_FORMAT.parse(date1);
        Date endDate = SHORT_DATE_FORMAT.parse(date2);
        endDate = new Date(endDate.getTime() + nd);
        dayTime = (endDate.getTime() - beginDate.getTime()) / nd;
        list.add(dayTime);
        list.add(beginDate);
        list.add(endDate);
        return list;
    }

    public static List<String> getAllDay(String d1, String d2) {
        List<String> result = new ArrayList<String>();
        try {
            Date date1 = SHORT_DATE_FORMAT.parse(d1);
            Date date2 = SHORT_DATE_FORMAT.parse(d2);

            while (date1.before(date2)) {
                result.add(SHORT_DATE_FORMAT.format(date1));
                date1.setTime(date1.getTime() + 60 * 60 * 24 * 1000);
            }
            result.add(SHORT_DATE_FORMAT.format(date1));
            return result;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return new ArrayList<String>();
    }

    public static Date getDate(String str, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            if (str == null) {
                return null;
            }
            return df.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new IllegalArgumentException("非正确的日期字符串!");
        }
    }

	/*
	 * public static void main(String[] args) { Date fixedDate = getFixedTime(nh * 18); Date sysDate = getDate(); if (fixedDate.compareTo(sysDate) < 0) { String stockPlaceTime =
	 * formatStockPlaceDate(addStockPlaceTime(sysDate, nd)); String stockPlaceHour = getHourMin(getTimePoor(sysDate, fixedDate, nh * 12)); String stockPlace = "预计送达日期" +
	 * stockPlaceTime + ",请在" + stockPlaceHour + "内下单"; System.out.println(stockPlace); } else { String stockPlaceTime = formatStockPlaceDate(sysDate); String stockPlaceHour =
	 * getHourMin(fixedDate.getTime() - sysDate.getTime()); String stockPlace = "预计送达日期" + stockPlaceTime + ",请在" + stockPlaceHour + "内下单"; System.out.println(stockPlace); } }
	 */

    /**
     * 计算两个日期相差的天数，只考虑年月日，不考虑时分秒(如果加上时分秒，可能导致天数计算错误)
     *
     * @param before
     * @param end
     * @param format 日期格式 eg:yyyyMMdd yyyy-MM-dd
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2013-3-20
     * @功能说明：int 相差的天数
     */
    public static int getDateDiff(String before, String end, String format) {
        Date beforeDate = getDate(before, format);
        Date endDate = getDate(end, format);
        return getDateDiff(beforeDate, endDate);
    }

    /**
     * @param before
     * @param after
     * @return int 相差的天数
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2013-3-20
     * @功能说明：计算两个日期相差的天数，只考虑年月日，不考虑时分秒(如果加上时分秒，可能导致天数计算错误)
     */
    public static int getDateDiff(Date before, Date after) {
        if (before == null || after == null)
            return 0;
        Calendar calendar1 = new GregorianCalendar();
        Calendar calendar2 = new GregorianCalendar();
        calendar1.setTime(before);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        calendar2.setTime(after);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        int diff = (int) ((calendar2.getTime().getTime() - calendar1.getTime().getTime()) / (24 * 60 * 60 * 1000));
        return diff;
    }

    /**
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2013-3-20
     * @功能说明：获取当前时间
     */
    public static Timestamp getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    /**
     * @param date
     * @param count
     * @author:abbott
     * @email:rhgui@xiangyu.cn
     * @创建日期:2013-3-28
     * @功能说明：获得给定时间推后或向前count天的日期
     */
    public static Date getDateForCount(Date date, int count) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, count);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        return date;
    }

    /**
     * 获得下周星期一的日期
     */
    public static Date getFirstMonday(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int mondayPlus = 0;
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            mondayPlus = -6;
        } else {
            mondayPlus = 2 - dayOfWeek;
        }
        cd.add(GregorianCalendar.DATE, mondayPlus + 7 * 1);
        return cd.getTime();
    }

    /**
     * 获得下周星期六的日期
     */
    public static Date getFirstSaturday(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int mondayPlus = 0;
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 7) {
            mondayPlus = 7;
        } else {
            mondayPlus = 7 - dayOfWeek;
        }
        cd.add(GregorianCalendar.DATE, mondayPlus);
        return cd.getTime();
    }

    /**
     * 判断指定时间是否是周末
     *
     * @param date 指定的时间
     * @return true:是周末,false:非周末
     */
    public static boolean isWeekend(Date date) {
        boolean isWeekend = false;
        isWeekend = (getWeek(date) - 1 == 0 || getWeek(date) - 1 == 6);
        return isWeekend;
    }

    /**
     * 求出指定时间那天是星期几
     *
     * @param date 指定的时间
     * @return 1-7
     */
    public static int getWeek(Date date) {
        int week = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        week = cal.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    public static int getDateHours() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定日期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDateBeginTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 功能描述：获取每周星期一的年月日
     *
     * @return
     * @author : Teny_lu 刘鹰
     * @E_Mail : liuying5590@163.com
     * @CreatedTime : 2014年12月8日-上午11:35:40
     */
    public static String getMondayByCurrWeek() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        return sdf.format(cal.getTime());
    }

    /**
     * 功能描述：获取本月第一天的年月日
     *
     * @return
     * @author : Teny_lu 刘鹰
     * @E_Mail : liuying5590@163.com
     * @CreatedTime : 2014年12月8日-上午11:35:40
     */
    public static String getMondayByCurrMonth() {
        String nowTime = formatShortDate(new Date());
        String pinjieMonday = org.apache.commons.lang3.StringUtils.substring(nowTime, 0, 8);
        String firstMonday = pinjieMonday + "01";
        return firstMonday;
    }

    /**
     * 功能描述：取得时间范围
     *
     * @return 时间数组长度为2，第一个值为起始时间，第二个值为结束时间
     * @author caiys
     * @date 2015年11月6日 下午5:06:55
     */
    public static Date[] getRangeDate() {
        // 起始时间 - 当前时间第二天
        Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
        String dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
        dateStart = DateUtils.getDate(dateStartStr, "yyyy-MM-dd");    // 除去时分秒
        // 结束时间 - 当前时间次月最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStart);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        Date dateEnd = calendar.getTime();
        return new Date[]{dateStart, dateEnd};
    }


}
