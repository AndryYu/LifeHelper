package com.android.andryyu.lifehelper.utils.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.android.andryyu.lifehelper.utils.FileUtil;
import com.android.andryyu.lifehelper.utils.ToastUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yufei on 2017/2/19.
 */
public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    private static AppCrashHandler instance;

    /**
     * <p>getInstance</p>
     *
     * @return
     * @Description: 懒汉模式（线程安全）
     */
    public static  AppCrashHandler getInstance() {
        if (instance == null) {
            synchronized (AppCrashHandler.class) {
                if (instance == null)
                    instance = new AppCrashHandler();
            }
        }
        return instance;
    }

    /**
     * <p>setCrashHandler</p>
     *
     * @param context
     * @Description: 捕获程序异常崩溃
     */
    public void setCrashHandler(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * <p>obtainPhoneInfo</p>
     * @param context
     * @Description:    获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     * @return
     */
    private HashMap<String, String> obtainPhoneInfo(Context context) {
        HashMap<String, String> map = new HashMap<String, String>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);
        map.put("MODEL", "" + Build.MODEL);     //手机版本
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);//sdk版本
        map.put("PRODUCT", "" + Build.PRODUCT);//手机厂商
        return map;
    }

    /**
     * <p>obtainExceptionInfo</p>
     * @param throwable
     * @Description     获取异常信息
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

    /**
     * <p>handleException</p>
     * @param throwable
     * @Description:    处理异常
     */
    public void handleException(Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, String> entry: obtainPhoneInfo(context).entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            builder.append(key).append("=").append(value + "\n");
        }
        builder.append(obtainExceptionInfo(throwable));
        String error = builder.toString();
        FileUtil.writeLogCrashContent(context, error);
        ToastUtil.showInThread(context, String.format("%s\n%s", "很抱歉，程序遭遇异常，即将退出！", builder.toString()));

    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
