package com.android.andryyu.lifehelper.utils;

import android.content.Context;

/**
 * Created by yufei on 2017/10/28.
 */

public class dimmenUtils {

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);  //+0.5是为了向上取整
    }
}
