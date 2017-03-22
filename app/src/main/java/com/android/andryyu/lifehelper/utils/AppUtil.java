package com.android.andryyu.lifehelper.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by yufei on 2017/3/21.
 */

public class AppUtil {

    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
