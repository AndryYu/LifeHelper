package com.android.andryyu.lifehelper.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import java.util.List;

/**
 * Created by yufei on 2017/3/21.
 */

public class AppUtil {

    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static int dp2px(Context paramContext, float paramFloat) {
        float scale = paramContext.getResources().getDisplayMetrics().density;
        return (int)(0.5F + paramFloat * scale);
    }

    public static int getWindowWidth(Context paramContext) {
        return getWindowManager(paramContext).getDefaultDisplay().getWidth();
    }

    public static WindowManager getWindowManager(Context paramContext) {
        return (WindowManager)paramContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
}
