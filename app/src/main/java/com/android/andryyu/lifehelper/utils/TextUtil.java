package com.android.andryyu.lifehelper.utils;


import android.text.TextUtils;

/**
 * Created by yufei on 2017/3/16.
 */

public class TextUtil {

    /**
     * 裁剪新闻的 Source 数据
     *
     * @param source
     * @return
     */
    public static String clipNewsSource(String source) {
        if (TextUtils.isEmpty(source)) {
            return source;
        }
        int i = source.indexOf("-");
        if (i != -1) {
            return source.substring(0, i);
        }
        return source;
    }

    /**
     * 安全的 String 返回
     *
     * @param prefix 默认字段
     * @param obj 拼接字段 (需检查)
     */
    public static String safeText(String prefix, String obj) {
        if (TextUtils.isEmpty(obj)) return "";
        return TextUtils.concat(prefix, obj).toString();
    }

    public static String safeText(String msg) {
        if (null == msg) {
            return "";
        }
        return safeText("", msg);
    }

    /**
     * 匹配掉错误信息
     */
    public static String replaceCity(String city) {
        city = safeText(city).replaceAll("(?:省|市|自治区|特别行政区|地区|盟)", "");
        return city;
    }
}
