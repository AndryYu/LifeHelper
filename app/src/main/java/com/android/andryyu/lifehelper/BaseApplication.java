package com.android.andryyu.lifehelper;

import android.app.Application;
import android.content.Context;

import com.android.andryyu.lifehelper.dagger.components.DaggerNetComponent;
import com.android.andryyu.lifehelper.dagger.components.NetComponent;
import com.android.andryyu.lifehelper.dagger.modules.NetModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by yufei on 2017/3/12.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private static Context context;
    private NetComponent netComponent;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        initTypeFace();
        initNet();
    }

    /**
     * <p>initTypeFace</p>
     * @Description:    初始化默认字体
     */
    private void initTypeFace() {
        CalligraphyConfig calligraphyConfig =new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/PMingLiU.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
        CalligraphyConfig.initDefault(calligraphyConfig);
    }

    /**
     * <p>initNet</p>
     */
    private void initNet(){
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }
}
