package com.android.andryyu.mdsdk.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by yufei on 2017/2/19.
 */
public class ToastUtils {

    /**
     * <p>showInThread</p>
     * @param context
     * @param message
     * @Description:   线程中调用Toast
     */
    public static void showInThread(final Context context, final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
    }
}
