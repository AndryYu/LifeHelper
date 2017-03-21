package com.android.andryyu.lifehelper.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yufei on 2017/2/19.
 */
public class TimeUtil {

    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /**
     * <p>getCurTimeString</p>
     * @Description：   获取当前时间
     * @return 时间字符串
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * <p>date2String</p>
     * @param time Date类型时间
     * @return 时间字符串
     * @Description:        将Date类型转化成格式为yyyy-MM-dd HH:mm:ss<
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * <p>date2String</p>
     * @param time   Date类型时间
     * @param format 时间格式
     * @Description:        将Date类型转为时间字符串
     * @return 时间字符串
     *
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }
}
