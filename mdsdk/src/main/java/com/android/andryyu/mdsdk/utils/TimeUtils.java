package com.android.andryyu.mdsdk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yufei on 2017/2/19.
 */
public class TimeUtils {

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
}
