package com.android.andryyu.lifehelper;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.android.andryyu.lifehelper.data.SPUtil;
import com.android.andryyu.lifehelper.di.components.DaggerNetComponent;
import com.android.andryyu.lifehelper.di.components.NetComponent;
import com.android.andryyu.lifehelper.di.modules.NetModule;
import com.android.andryyu.lifehelper.utils.exception.AppCrashHandler;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by yufei on 2017/3/12.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static Context context;
    private NetComponent netComponent;

    private String token = "";


    public static Context getContext(){
        return context;
    }

    public static BaseApplication get(Context context){
        return (BaseApplication)context.getApplicationContext();
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        initNet();
        AppCrashHandler.getInstance().setCrashHandler(this);
        initAppNightMode();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * <p>initNet</p>
     */
    private void initNet(){
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }

    public NetComponent getNetComponent(){
        return netComponent;
    }

    /**
     * <p>initAppNightMode</p>
     * @Description:    判断主题样式
     */
    private void initAppNightMode(){
        if (SPUtil.getInstance().getAppModule() == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
