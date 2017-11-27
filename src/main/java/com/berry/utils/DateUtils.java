package com.berry.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 计算周的时候是根据 周一为每周的第一天 ，  每年的第一周最少有几天   odps函数  weekofyear  4天以上 包含4天
 * Description (日期工具类)
 * Create at: 2017/11/23 9:46
 */
public class DateUtils {


    /**
     * 根据日期字符串判断当月第几周
     *
     * @param date_str 格式 yyyy-MM-dd
     * @return
     */
    public static Integer getWeekOfMonth(String date_str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(date_str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            //第几周
            int week = calendar.get(Calendar.WEEK_OF_MONTH);
            //第几天，从周日开始
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            return week;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据日期字符串获取是当年的第几周
     *
     * @param date_str 格式 yyyy-MM-dd
     * @return
     */
    public static Integer getWeekOfYear(String date_str) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(date_str);
            Calendar calendar = Calendar.getInstance();
            //        设置周一是一周的开始
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            //        每年的第一周最少有几天   odps函数  weekofyear  4天以上
            calendar.setMinimalDaysInFirstWeek(4);
            calendar.setTime(date);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取日期所在周的周一  日期为null  则显示 当前日期的周一
     *
     * @param date_str
     * @return
     */
    public static String getWeekMonday(String date_str) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            if (StringUtils.isNotEmpty(date_str)) {
                calendar.setTime(sdf.parse(date_str));
            }

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == 1) {
                //周日
                calendar.add(Calendar.DATE, -6);
            } else {
                //day_of_week ==2  周一
                calendar.add(Calendar.DATE, -(dayOfWeek - 2));
            }
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取上一周的周一
     *
     * @return 如 2017-11-06
     */
    public static String getLastWeekMonday() {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == 1) {
                //周日
                calendar.add(Calendar.DATE, -13);
            } else {
                //day_of_week ==2  周一
                calendar.add(Calendar.DATE, -(dayOfWeek + 5));
            }
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取上一周的周日
     *
     * @return
     */
    public static String getLastWeekSunday() {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(getLastWeekMonday()));
            calendar.add(Calendar.DATE, +6);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取上上一周的周一
     *
     * @return 如 2017-11-06
     */
    public static String getLastLastWeekMonday() {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                //周日
                calendar.add(Calendar.DATE, -20);
            } else {
                calendar.add(Calendar.DATE, -(dayOfWeek + 12));
            }
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前日期的字符串 如  20171116
     *
     * @return
     */
    public static String getCurrentTimeDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }


    /**
     * 获取当前日期的字符串 如  20171116135043
     *
     * @return
     */
    public static String getCurrentTimeSec() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }


    /**
     * 获取当前日期的字符串 如  2017-11-22 15:12:33
     *
     * @return
     */
    public static String getCurrentTimetandard() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }


    /**
     * 根据年、周数获取该周的具体日期
     *
     * @param year    year可以为null
     * @param weekNo
     * @return
     */
    public static List<String> getListWeekDaysByWeekNo(Integer year, Integer weekNo) {
        if (weekNo == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (year != null) {
            calendar.set(Calendar.YEAR, year);
        }
        calendar.set(Calendar.WEEK_OF_YEAR, weekNo);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //每年的第一周最少有几天   odps函数  weekofyear  4天以上
        calendar.setMinimalDaysInFirstWeek(4);
        List result = new ArrayList();
        // 1表示周日，2表示周一，7表示周六
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        //周一
        result.add(sf.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, 3);
        //周二
        result.add(sf.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, 4);
        //周三
        result.add(sf.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, 5);
        //周四
        result.add(sf.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, 6);
        //周五
        result.add(sf.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        //周六
        result.add(sf.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        //周日
        result.add(sf.format(calendar.getTime()));

        return result;
    }

}