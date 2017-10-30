package com.android.andryyu.lifehelper.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.android.andryyu.lifehelper.BaseApplication;

/**
 * Created by yufei on 2017/2/19.
 */
public class ToastUtil {

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

    public static void showShort(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showShort(int msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int msg) {
        Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
