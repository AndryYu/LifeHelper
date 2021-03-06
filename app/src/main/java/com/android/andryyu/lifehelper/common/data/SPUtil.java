package com.android.andryyu.lifehelper.common.data;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.andryyu.lifehelper.BaseApplication;


/**
 * Created by hugo on 2016/2/19 0019.
 *
 * 设置相关 包括 sp 的写入
 */
public class SPUtil {

    private static String USER_SETTINGS = "USER_SETTINGS";
    private static String APP_MODULE = "APP_MODULE";

    public static final String DEFAULT_STRING = "";
    public static final int DEFAULT_INT = 0;
    public static final boolean DEFAULT_BOOLEAN = false;
    public static final long DEFAULT_LONG = 0;
    public static final float DEFAULT_FLOAT = 0.0f;

    public static final String CITY_NAME = "城市";//选择城市
    public static final String HOUR = "current_hour";//当前小时

    public static final String CHANGE_ICONS = "change_icons";//切换图标
    public static final String CLEAR_CACHE = "clear_cache";//清空缓存
    public static final String AUTO_UPDATE = "change_update_time"; //自动更新时长
    public static final String NOTIFICATION_MODEL = "notification_model";
    public static final String ANIM_START = "animation_start";

    public static int ONE_HOUR = 1000 * 60 * 60;

    private SharedPreferences mPrefs;

    public static SPUtil getInstance() {
        return SPHolder.sInstance;
    }

    private static class SPHolder {
        private static final SPUtil sInstance = new SPUtil();
    }

    private SPUtil() {
        mPrefs = BaseApplication.getContext().getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE);
    }

    public SPUtil putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).apply();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    public SPUtil putString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
        return this;
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

    public String getString(String key) {
        return mPrefs.getString(key, DEFAULT_STRING);
    }

    public SPUtil putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPrefs.getBoolean(key, defValue);
    }

    // 设置当前小时
    public void setCurrentHour(int h) {
        mPrefs.edit().putInt(HOUR, h).apply();
    }

    public int getCurrentHour() {
        return mPrefs.getInt(HOUR, 0);
    }

    // 图标种类相关
    public void setIconType(int type) {
        mPrefs.edit().putInt(CHANGE_ICONS, type).apply();
    }

    public int getIconType() {
        return mPrefs.getInt(CHANGE_ICONS, 0);
    }

    // 自动更新时间 hours
    public void setAutoUpdate(int t) {
        mPrefs.edit().putInt(AUTO_UPDATE, t).apply();
    }

    public int getAutoUpdate() {
        return mPrefs.getInt(AUTO_UPDATE, 3);
    }

    //当前城市
    public void setCityName(String name) {
        mPrefs.edit().putString(CITY_NAME, name).apply();
    }

    public String getCityName() {
        return mPrefs.getString(CITY_NAME, "武汉");
    }

    //  通知栏模式 默认为常驻
    public void setNotificationModel(int t) {
        mPrefs.edit().putInt(NOTIFICATION_MODEL, t).apply();
    }

    public int getNotificationModel() {
        return mPrefs.getInt(NOTIFICATION_MODEL, Notification.FLAG_ONGOING_EVENT);
    }

    // 首页 Item 动画效果 默认关闭

    public void setMainAnim(boolean b) {
        mPrefs.edit().putBoolean(ANIM_START, b).apply();
    }

    public boolean getMainAnim() {
        return mPrefs.getBoolean(ANIM_START, false);
    }

    /** 应用主题 */
    public void setAppModule(int  module){
        mPrefs.edit().putInt(APP_MODULE, module).apply();
    }
    public int getAppModule(){
        return mPrefs.getInt(APP_MODULE, 0);
    }
}
